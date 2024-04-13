package com.dyc.order.cashier.contact;

import com.dyc.order.cashier.constant.MessageField;
import com.dyc.order.cashier.constant.ServerAddress;
import com.dyc.order.cashier.base.iml.BaseContact;
import com.dyc.order.cashier.data.local.DataCache;
import com.dyc.order.cashier.data.fields.PaymentRequestFields;
import com.dyc.order.cashier.data.response.BarCodeData;
import com.dyc.order.cashier.data.response.PaymentFinishData;
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
 * func: 收款中主扫
 * author:丁语成 on 2020/2/24 10:18
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public interface ShowCodeFragContact {
	interface ShowCodeModel extends BaseContact.BaseModel{
		MLogger logger = MLogger.getLogger(ShowCodeModel.class);
		String timeOut = "3";

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

	interface ShowCodeView extends BaseTransactionContact.BaseTransactionView {
		void showCode(BarCodeData data);
	}

	abstract class ShowCodePresent<M extends ShowCodeModel, V extends ShowCodeView> extends BaseContact.BasePresent<M, V>{

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
