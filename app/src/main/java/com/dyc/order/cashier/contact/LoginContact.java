package com.dyc.order.cashier.contact;

import android.annotation.SuppressLint;

import com.dyc.order.cashier.R;
import com.dyc.order.cashier.constant.MessageField;
import com.dyc.order.cashier.constant.ServerAddress;
import com.dyc.order.cashier.base.iml.BaseContact;
import com.dyc.order.cashier.data.local.DataCache;
import com.dyc.order.cashier.data.response.LoginInfoData;
import com.dyc.order.cashier.data.response.MerchantInfoData;
import com.dyc.administrator.toollibrary.utils.MD5Util;
import com.dyc.administrator.toollibrary.utils.MLogger;
import com.rxjava.rxlife.RxLife;

import androidx.lifecycle.LifecycleOwner;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import rxhttp.wrapper.param.RxHttp;

/**
 * func: 登录
 * author:丁语成 on 2020/2/14 14:52
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public interface LoginContact {
	class LoginModel implements BaseContact.BaseModel {
		private MLogger logger = MLogger.getLogger(this.getClass());

		public Observable<LoginInfoData> Login(String account, String password) {
			String pwd = MD5Util.getStringMD5(password);
			return RxHttp.postJson(ServerAddress.LOGIN_ADDRESS)
					.add(MessageField.PHONE_NUMBER,account)
					.add(MessageField.PASSWORD, pwd)
					.asResponse(LoginInfoData.class);
		}
	}

	interface LoginView extends BaseContact.BaseView {
		void onSuccess();
	}

	class LoginPresent<M extends LoginModel, V extends LoginView> extends BaseContact.BasePresent<M, V> {
		@SuppressLint("CheckResult")
		public void login(String account, String pwd) {
			getView().showLoading(getView().getActivity().getString(R.string.dialog_pls_wait));
			getModel().Login(account, pwd)
					.subscribeOn(Schedulers.newThread())
					.observeOn(AndroidSchedulers.mainThread())
					.as(RxLife.as((LifecycleOwner) getView().getActivity()))
					.subscribe(loginResponseData -> {
						logger.info("success:" + loginResponseData.toMap());
						DataCache.setLoginInfoData(loginResponseData);
						DataCache.addData(MessageField.PHONE_NUMBER, account);
						DataCache.addData(MessageField.PASSWORD, pwd);
						getView().hideDialog();
						getView().onSuccess();
						getMerchantInfo();
					}, throwable -> {
						logger.info("error");
						logger.info(throwable.toString());
						onError(throwable);
					});
		}

		public void getMerchantInfo() {
			RxHttp.get(ServerAddress.GET_MERCHANT_INFO_ADDRESS)
					.setHeader(MessageField.AUTHORIZATION, DataCache.getLoginInfoData().getToken())
					.asResponse(MerchantInfoData.class)
					.subscribeOn(Schedulers.newThread())
					.observeOn(AndroidSchedulers.mainThread())
					.as(RxLife.as((LifecycleOwner) getView().getActivity()))
					.subscribe(info -> {
						logger.info("success get merchant:" + info);
						DataCache.setMerchantInfoData(info);
						test();
					}, throwable -> {
						logger.info("error get merchant:" + throwable);
					});
		}

		private void test() {
//			RxHttp.get(ServerAddress.MEMBER_POINT_CARD_OPEN)
//					.addHeader(MessageField.AUTHORIZATION, DataCache.getLoginInfoData().getToken())
//					.asResponse(String.class)
//					.observeOn(Schedulers.newThread())
//					.subscribeOn(AndroidSchedulers.mainThread())
//					.as(RxLife.as((LifecycleOwner) getView().getActivity()))
//					.subscribe(res -> {
//						logger.info("成功获取商家开通储值卡功能结果" + res);
//					}, throwable -> {
//						logger.info("获取能否使用");
//						throwable.printStackTrace();
//					});
		}
	}
}
