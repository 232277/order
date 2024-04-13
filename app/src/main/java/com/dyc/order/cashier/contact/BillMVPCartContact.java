package com.dyc.order.cashier.contact;

import com.dyc.order.cashier.constant.MessageField;
import com.dyc.order.cashier.constant.ServerAddress;
import com.dyc.order.cashier.data.local.DataCache;
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
 * author:丁语成 on 2020/4/1 11:50
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public interface BillMVPCartContact {

	interface BillMVPModel extends BaseMemberInfoContact.BaseMemberInfoModel{
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
					.asResponse(ActivityInfoData.class)
					.observeOn(AndroidSchedulers.mainThread())
					.subscribeOn(Schedulers.newThread());
		}
	}

	interface BillMVPView extends BaseMemberInfoContact.BaseMemberInfoView{
		default void getActivityInfoSuccess(ActivityInfoData activityInfoData){}
		default void getActivityInfoFail(Throwable throwable){}
		default void getPointConfigInfoSuccess(PointConfigData pointConfigData){}
		default void getPointConfigInfoFail(Throwable throwable){}
	}

	abstract class BillMVPPresent<M extends BillMVPModel, V extends BillMVPView> extends BaseMemberInfoContact.BaseMemberInfoPresent<M,V>{
		public long activityTotal;

		public void getActivityInfo(){
			getModel().getActivityInfo()
					.as(RxLife.as((LifecycleOwner)getView().getActivity()))
					.subscribe(activityInfos -> {
						logger.info("get activities success:" + activityInfos);
						activityTotal = activityInfos.total;
						getView().getActivityInfoSuccess(activityInfos);
					}, throwable -> {
						throwable.printStackTrace();
						getView().getActivityInfoFail(throwable);
					});
		}

		public void getPointsConfiInfo(){
			getModel().getPointsConfigInfo()
					.as(RxLife.as((LifecycleOwner)getView().getActivity()))
					.subscribe(pointConfigInfo -> {
						logger.info("get pointConfigInfo success:" + pointConfigInfo);
						getView().getPointConfigInfoSuccess(pointConfigInfo);
					}, throwable -> {
						throwable.printStackTrace();
						getView().getPointConfigInfoFail(throwable);
					});
		}
	}
}
