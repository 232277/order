package com.dyc.order.cashier.contact;

import com.dyc.order.cashier.constant.MessageField;
import com.dyc.order.cashier.constant.ServerAddress;
import com.dyc.order.cashier.data.fields.PaymentRequestFields;
import com.dyc.order.cashier.data.response.PaymentFinishData;
import com.dyc.order.cashier.data.response.PlaceOrderData;
import com.dyc.order.cashier.base.iml.BaseContact;
import com.dyc.order.cashier.data.fields.ConfirmOrderFields;
import com.dyc.order.cashier.data.local.DataCache;
import com.dyc.order.cashier.data.response.BarCodeData;
import com.dyc.administrator.toollibrary.utils.MLogger;
import com.google.gson.JsonParser;
import com.rxjava.rxlife.RxLife;

import androidx.lifecycle.LifecycleOwner;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.utils.GsonUtil;

/**
 * func: 支付基类
 * author:丁语成 on 2020/2/26 22:38
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public interface BaseTransactionContact {
	interface BaseTransactionModel extends BaseContact.BaseModel {
		MLogger logger = MLogger.getLogger(ShowCodeFragContact.ShowCodeModel.class);
		String timeOut = "3";

		default Observable<PlaceOrderData> placeOrder(PaymentRequestFields fields){
			fields.setTransProp(false);
			return RxHttp.postJson(ServerAddress.PLACE_ORDER_ONLY_ADDRESS)
					.setHeader(MessageField.AUTHORIZATION, DataCache.getLoginInfoData().getToken())
					.addAll(new JsonParser().parse(GsonUtil.toJson(fields)).getAsJsonObject())
					.asResponse(PlaceOrderData.class)
					.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread());
		}

		default Observable<String> placeOrderConfirm(ConfirmOrderFields fields){
			return RxHttp.postJson(ServerAddress.PLACE_ORDER_CONFIRM_ADDRESS)
					.setHeader(MessageField.AUTHORIZATION, DataCache.getLoginInfoData().getToken())
					.addAll(new JsonParser().parse(GsonUtil.toJson(fields)).getAsJsonObject())
					.asResponse(String.class)
					.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread());
		}

		default Observable<String> placeOrderConfirm(String platFormOrderNo, String orderNo, int payType){
			return RxHttp.postJson(ServerAddress.PLACE_ORDER_CONFIRM_ADDRESS)
					.setHeader(MessageField.AUTHORIZATION, DataCache.getLoginInfoData().getToken())
					.add(MessageField.PLATFORM_NO, platFormOrderNo)
					.add(MessageField.ORDER_NO, orderNo)
					.add(MessageField.PAY_TYPE, payType)
					.asResponse(String.class)
					.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread());
		}

		default Observable<String> cancelOrder(String orderNo){
			return RxHttp.postJson(ServerAddress.CANCEL_ORDER_ADDRESS + orderNo)
					.setHeader(MessageField.AUTHORIZATION, DataCache.getLoginInfoData().getToken())
					.asResponse(String.class)
					.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread());
		}

		default Observable<PaymentFinishData> doPay(PaymentRequestFields fields){
			return RxHttp.postJson(ServerAddress.PLACE_ORDER_ADDRESS)
					.setHeader(MessageField.AUTHORIZATION, DataCache.getLoginInfoData().getToken())
					.addAll(new JsonParser().parse(GsonUtil.toJson(fields)).getAsJsonObject())
					.asResponse(PaymentFinishData.class)
					.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread());
		}

		default Observable<BarCodeData> getPayCode(PaymentRequestFields fields) {
			logger.info("获取paycode rxhttp");
			return RxHttp.postJson(ServerAddress.PLACE_ORDER_ADDRESS)
					.setHeader(MessageField.AUTHORIZATION, DataCache.getLoginInfoData().getToken())
					.addAll(new JsonParser().parse(GsonUtil.toJson(fields)).getAsJsonObject())
					.asResponse(BarCodeData.class)
					.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread());
		}

		default Observable<PaymentFinishData> queryOrder(String orderNo) {
			logger.info("查询" + ServerAddress.QUERY_ORDER_ADDRESS + orderNo);
			return RxHttp.postJson(ServerAddress.QUERY_ORDER_ADDRESS + orderNo + "/" + timeOut)
					.setHeader(MessageField.AUTHORIZATION, DataCache.getLoginInfoData().getToken())
					.add(MessageField.ORDER_NO, orderNo)
					.asResponse(PaymentFinishData.class)
					.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread());
		}
	}

	interface BaseTransactionView extends BaseContact.BaseView{
		default void showCode(BarCodeData data){}

		default void onPlaceOrderSuccess(PlaceOrderData placeOrderData){ }

		default void onPlaceOrderFail(Throwable throwable){}

		default void onPlaceOrderConfirmSuceess(){}

		default void onPlaceOrderConfirmOrderFail(Throwable throwable){}

		default void onCancelOrderSuceess(){}

		default void onCancelOrderFail(Throwable throwable){}

		default void onPayFail(double amount, String msg, PaymentFinishData paymentFinishData) {
			hideDialog();
			toResult(amount,msg, false, paymentFinishData);
		}

		default void onPaySuccess(double amount, PaymentFinishData paymentFinishData) {
			hideDialog();
			toResult(amount, null, true, paymentFinishData);
		}

		default void toResult(double amount, String msg, boolean res, PaymentFinishData paymentFinishData){

		}
	}

	abstract class BaseTransactionPresent<M extends BaseTransactionModel, V extends BaseTransactionView> extends BaseContact.BasePresent<M,V>{

		public void placeOrder(PaymentRequestFields fields){
			getModel().placeOrder(fields)
					.subscribeOn(Schedulers.newThread())
					.observeOn(Schedulers.newThread())
					.as(RxLife.as((LifecycleOwner)getView().getActivity()))
					.subscribe(res -> {
						logger.info("placeOrder 成功:" + res);
						getView().onPlaceOrderSuccess(res);
					}, throwable -> {
						logger.info("placeOrder 失败");
						throwable.printStackTrace();
						getView().onPlaceOrderFail(throwable);
					});
		}

		public void placeOrderConfirm(String platFormOrderNo, String orderNo, int payType){
			getModel().placeOrderConfirm(platFormOrderNo, orderNo, payType)
					.subscribeOn(Schedulers.newThread())
					.observeOn(Schedulers.newThread())
					.as(RxLife.as((LifecycleOwner)getView().getActivity()))
					.subscribe(res -> {
						logger.info("placeOrderConfirm 成功");
						getView().onPlaceOrderConfirmSuceess();
					}, throwable -> {
						logger.info("placeOrderConfirm 失败");
						throwable.printStackTrace();
						getView().onPlaceOrderConfirmOrderFail(throwable);
					});
		}

		public void placeOrderConfirm(ConfirmOrderFields fields){
			getModel().placeOrderConfirm(fields)
					.subscribeOn(Schedulers.newThread())
					.observeOn(Schedulers.newThread())
					.as(RxLife.as((LifecycleOwner)getView().getActivity()))
					.subscribe(res -> {
						logger.info("placeOrderConfirm 成功");
						getView().onPlaceOrderConfirmSuceess();
					}, throwable -> {
						logger.info("placeOrderConfirm 失败");
						throwable.printStackTrace();
						getView().onPlaceOrderConfirmOrderFail(throwable);
					});
		}

		public void cancelOrder(String orderNo){
			getModel().cancelOrder(orderNo)
					.subscribeOn(Schedulers.newThread())
					.observeOn(Schedulers.newThread())
					.as(RxLife.as((LifecycleOwner)getView().getActivity()))
					.subscribe(res -> {
						logger.info("cancelOrder success");
						getView().onCancelOrderSuceess();
					}, throwable -> {
						logger.info("cancelOrder fail");
						throwable.printStackTrace();
						getView().onCancelOrderFail(throwable);
					});
		}

		public void doPay(PaymentRequestFields fields) {
			getModel().doPay(fields)
					.subscribeOn(Schedulers.newThread())
					.observeOn(Schedulers.newThread())
					.as(RxLife.as((LifecycleOwner)getView().getActivity()))
					.subscribe(res -> {
						logger.info("doPay 请求付款成功:" + res);
						if (res.getStatus()){
							getView().onPaySuccess(fields.getOrder().getTransAmount(), res);
						}else {
							getView().onPayFail(fields.getOrder().getTransAmount(), res.getMsg(), res);
						}
					}, throwable -> {
						logger.info("doPay 请求付款失败");
						throwable.printStackTrace();
						getView().onPayFail(fields.getOrder().getTransAmount(), throwable.getMessage(), null);
					});
		}

		public void getPayCode(PaymentRequestFields fields) {
			getModel().getPayCode(fields)
					.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread())
					.as(RxLife.as((LifecycleOwner)getView().getActivity()))
					.subscribe(paymentData -> {
						logger.info("请求付款码成功");
						getView().showCode(paymentData);
						doQuery(paymentData.getOrderNo(), fields);
					}, this::onError);
		}

		private void doQuery(String orderNo, PaymentRequestFields fields){
			getModel().queryOrder(orderNo)
					.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread())
					.as(RxLife.as((LifecycleOwner)getView().getActivity()))
					.subscribe(data -> {
						logger.info("show code res:" + data);
						if (data.getStatus()){
							getView().onPaySuccess(fields.getOrder().getTransAmount(), data);
						}else {
							retry(orderNo, fields);
						}
					}, throwable -> {
						logger.info("error when query");
						logger.info(throwable);
						retry(orderNo, fields);
					});
		}

		private void retry(String orderNo, PaymentRequestFields fields){
			doQuery(orderNo, fields);
		}
	}
}
