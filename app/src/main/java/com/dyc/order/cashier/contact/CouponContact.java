package com.dyc.order.cashier.contact;

import com.dyc.order.cashier.constant.MessageField;
import com.dyc.order.cashier.constant.ServerAddress;
import com.dyc.order.cashier.base.iml.BaseContact;
import com.dyc.order.cashier.data.local.DataCache;
import com.dyc.order.cashier.data.response.CouponListData;
import com.rxjava.rxlife.RxLife;

import androidx.lifecycle.LifecycleOwner;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import rxhttp.wrapper.param.RxHttp;

/**
 * func:
 * author:丁语成 on 2020/5/28 18:53
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public interface CouponContact {
	interface CouponModel extends BaseContact.BaseModel{
		default Observable<CouponListData> getCoupon(double amount, String phone, int[] productIds){
			return RxHttp.postJson(ServerAddress.GET_MEMBER_ABLE_USE_COUPON)
					.setHeader(MessageField.AUTHORIZATION, DataCache.getLoginInfoData().getToken())
					.add(MessageField.AMOUNT, amount)
					.add(MessageField.PHONE, phone)
					.add(MessageField.PRODUCT_IDS, productIds)
					.asResponse(CouponListData.class);
		}

		default Observable<CouponListData> getAllCoupon(String phone,  String status){
			return RxHttp.get(ServerAddress.GET_MEMBER_COUPONS)
					.setHeader(MessageField.AUTHORIZATION, DataCache.getLoginInfoData().getToken())
					.add(MessageField.PHONE, phone)
					.add(MessageField.STATUS, status)
					.asResponse(CouponListData.class);
		}
	}

	interface CouponView extends BaseContact.BaseView{
		default void getCouponSuccess(CouponListData listData){}
		default void getCouponFail(Throwable throwable){}
		default void getAllCouponSuccess(CouponListData listData){}
		default void getAllCouponFail(Throwable throwable){}
	}

	abstract class CouponPresent<M extends CouponModel, V extends CouponView> extends BaseContact.BasePresent<M,V> {
		public void getCoupon(double amount, String phone, int[] productIds){
			getModel().getCoupon(amount, phone, productIds)
					.subscribeOn(Schedulers.newThread())
					.observeOn(AndroidSchedulers.mainThread())
					.as(RxLife.as((LifecycleOwner) getView().getActivity()))
					.subscribe(info -> {
						logger.info("success get coupon:" + info);
						getView().getCouponSuccess(info);
					}, throwable -> {
						logger.info("error get coupon:" + throwable);
						throwable.printStackTrace();
						getView().getCouponFail(throwable);
					});
		}

		public void getAllCoupon(String phone, String status){
			getModel().getAllCoupon(phone, status)
					.subscribeOn(Schedulers.newThread())
					.observeOn(AndroidSchedulers.mainThread())
					.as(RxLife.as((LifecycleOwner) getView().getActivity()))
					.subscribe(info -> {
						logger.info("success get coupon:" + info);
						getView().getAllCouponSuccess(info);
					}, throwable -> {
						logger.info("error get coupon:" + throwable);
						throwable.printStackTrace();
						getView().getAllCouponFail(throwable);
					});
		}
	}
}
