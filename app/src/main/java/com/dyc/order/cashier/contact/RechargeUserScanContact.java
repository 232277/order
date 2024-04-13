package com.dyc.order.cashier.contact;

import com.dyc.order.cashier.constant.MessageField;
import com.dyc.order.cashier.constant.ServerAddress;
import com.dyc.order.cashier.data.fields.MemberStoreDTOFields;
import com.dyc.order.cashier.data.local.DataCache;
import com.dyc.order.cashier.data.response.MemberInfoData;
import com.rxjava.rxlife.RxLife;

import androidx.lifecycle.LifecycleOwner;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import rxhttp.wrapper.param.RxHttp;

/**
 * func:
 * author:丁语成 on 2020/7/2 13:48
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public interface RechargeUserScanContact {
	interface RechargeUserScanModel extends BaseTransactionContact.BaseTransactionModel, MemberRechargeContact.MemberRechargeModel {
		default Observable<String> getStorePayCode(MemberInfoData memberInfoData){
			return RxHttp.get(ServerAddress.MEMBER_POINT_CARD_PAY_SHOW_CODE)
					.add(MessageField.AUTH_CODE, DataCache.getLoginInfoData().getToken())
					.asResponse(String.class)
					.subscribeOn(AndroidSchedulers.mainThread())
					.observeOn(Schedulers.newThread());
		}

		default Observable<String> queryStorePayCode(String orderNo){
			return RxHttp.get(ServerAddress.MEMBER_POINT_CARD_PAY_SHOW_CODE)
					.add(MessageField.AUTH_CODE, DataCache.getLoginInfoData().getToken())
					.add(MessageField.ORDER_NO, orderNo)
					.asResponse(String.class)
					.subscribeOn(AndroidSchedulers.mainThread())
					.observeOn(Schedulers.newThread());
		}
	}

	interface RechargeUserScanView extends BaseTransactionContact.BaseTransactionView, MemberRechargeContact.MemberRechargeView {
		default void onGetStorePayCodeSuccess(String code){}
		default void onGetStorePayCodeFail(Throwable throwable){}
		default void onStorePaySuccess(String code){}
		default void onStorePayFail(String msg){}
		default void onQueryStorePayFail(Throwable throwable){}
	}

	abstract class RechargeUserScanPresent<M extends RechargeUserScanModel, V extends RechargeUserScanView> extends BaseTransactionContact.BaseTransactionPresent<M, V> {
		protected MemberRechargeContact.MemberRechargePresent<M,V> memberRechargePresent;

		@Override
		public void afterInit() {
			super.afterInit();
			memberRechargePresent = new MemberRechargeContact.MemberRechargePresent<M, V>() {
			};
			memberRechargePresent.registerModel(getModel());
			memberRechargePresent.registerView(getView());
		}

		public void getMemberCanUsePointCard(){
			memberRechargePresent.getMemberCanUsePointCard();
		}

		public void memberStorePay(MemberStoreDTOFields fields){
			memberRechargePresent.memberStorePay(fields);
		}

		public void getStorePayCode(MemberInfoData memberInfoData){
			getModel().getStorePayCode(memberInfoData)
					.as(RxLife.as((LifecycleOwner)getView().getActivity()))
					.subscribe(code -> {
						logger.info("获取储值支付二维码成功:" + code);
						getView().onGetStorePayCodeSuccess(code);
					}, throwable -> {
						throwable.printStackTrace();
						logger.info("获取储值支付二维码失败:" + throwable.getMessage());
						getView().onGetStorePayCodeFail(throwable);
					});
		}

		public void queryStorePayCode(String orderNo){
			getModel().queryStorePayCode(orderNo)
					.as(RxLife.as((LifecycleOwner)getView().getActivity()))
					.subscribe(res -> {
						logger.info("获取储值支付状态成功:" + res);
						if (storePayResJudge(res)){
							logger.info("储值支付成功");
							getView().onStorePaySuccess(res);
						}else {
							logger.info("储值支付失败");
							getView().onStorePayFail(res);
						}
					}, throwable -> {
						throwable.printStackTrace();
						logger.info("获取储值支付状态失败:" + throwable.getMessage());
						getView().onQueryStorePayFail(throwable);
					});
		}

		private boolean storePayResJudge(String res){
			return true;
		}
	}
}
