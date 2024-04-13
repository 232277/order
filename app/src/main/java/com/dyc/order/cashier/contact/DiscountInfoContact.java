package com.dyc.order.cashier.contact;

import com.dyc.order.cashier.constant.MessageField;
import com.dyc.order.cashier.constant.ServerAddress;
import com.dyc.order.cashier.base.iml.BaseContact;
import com.dyc.order.cashier.data.local.ActivityCenter;
import com.dyc.order.cashier.data.local.DataCache;
import com.dyc.order.cashier.data.local.PointsConfigCenter;
import com.dyc.order.cashier.data.response.ActivityInfoData;
import com.dyc.order.cashier.data.response.PointConfigData;
import com.rxjava.rxlife.RxLife;

import androidx.lifecycle.LifecycleOwner;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import rxhttp.wrapper.param.RxHttp;

/**
 * func:
 * author:丁语成 on 2020/3/31 17:21
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public interface DiscountInfoContact {
	interface DiscountInfoModel extends BaseContact.BaseModel{
		default Observable<PointConfigData> getPointsConfigInfo(){
			return RxHttp.get(ServerAddress.GET_MERCHANT_POINT_CONFIG)
					.setHeader(MessageField.AUTHORIZATION, DataCache.getLoginInfoData().getToken())
					.asResponse(PointConfigData.class)
					.observeOn(AndroidSchedulers.mainThread())
					.subscribeOn(Schedulers.newThread());
		}

		default Observable<ActivityInfoData> getActivityInfo(){
			return RxHttp.get(ServerAddress.GET_ACTIVITY_INFO_ADDRESS
					+ DataCache.getLoginInfoData().getMerchantInfoDTO().getId())
					.setHeader(MessageField.AUTHORIZATION, DataCache.getLoginInfoData().getToken())
					.asResponse(ActivityInfoData .class)
					.observeOn(AndroidSchedulers.mainThread())
					.subscribeOn(Schedulers.newThread());
		}
	}

	interface DiscountInfoView extends BaseContact.BaseView{
		default void getGoodsActivityInfoSuccess(ActivityInfoData activityInfoData){}
		default void getGoodsActivityInfoFail(Throwable throwable){}
		default void getPointConfigInfoSuccess(PointConfigData pointConfigData){}
		default void getPointConfigInfoFail(Throwable throwable){}
	}

	abstract class DiscountInfoPresent<M extends DiscountInfoModel, V extends DiscountInfoView> extends BaseContact.BasePresent<M,V>{
		public void getActivityInfo(){
			try {
				getModel().getActivityInfo()
						.as(RxLife.as((LifecycleOwner)getView().getActivity()))
						.subscribe(activityInfos -> {
							logger.info("get activityInfo success:" + activityInfos);
							ActivityCenter.setActivityInfoData(activityInfos);
							getView().getGoodsActivityInfoSuccess(activityInfos);
						}, throwable -> {
							logger.info("get activityInfo fail");
							throwable.printStackTrace();
							ActivityCenter.setActivityInfoData(null);
							getView().getGoodsActivityInfoFail(throwable);
						});
			}catch (Exception e){
				e.printStackTrace();
			}
		}

		public void getPointsConfiInfo(){
			getModel().getPointsConfigInfo()
					.as(RxLife.as((LifecycleOwner)getView().getActivity()))
					.subscribe(pointConfigInfo -> {
						logger.info("get pointConfigInfo success:" + pointConfigInfo);
						PointsConfigCenter.setPointConfigData(pointConfigInfo);
						getView().getPointConfigInfoSuccess(pointConfigInfo);
					}, throwable -> {
						throwable.printStackTrace();
						PointsConfigCenter.setPointConfigData(null);
						getView().getPointConfigInfoFail(throwable);
					});
		}
	}
}
