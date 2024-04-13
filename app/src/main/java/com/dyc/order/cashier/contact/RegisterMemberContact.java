package com.dyc.order.cashier.contact;

import com.dyc.order.cashier.constant.MessageField;
import com.dyc.order.cashier.constant.ResponseCode;
import com.dyc.order.cashier.constant.ServerAddress;
import com.dyc.order.cashier.mvp.activity.member.AddMemberByCodeActivity;
import com.dyc.order.cashier.base.iml.BaseContact;
import com.dyc.order.cashier.data.local.DataCache;
import com.dyc.order.cashier.data.response.MemberInfoData;
import com.dyc.order.cashier.data.response.ResponseData;
import com.dyc.order.cashier.error.MyParseExcepetion;
import com.rxjava.rxlife.RxLife;

import java.util.concurrent.TimeUnit;

import androidx.lifecycle.LifecycleOwner;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.parse.SimpleParser;

/**
 * func:
 * author:丁语成 on 2020/5/27 14:11
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public interface RegisterMemberContact {
	interface RegisterMemberModel extends BaseContact.BaseModel{
		default Observable<MemberInfoData> checkRegisterState(String identifier){
			return RxHttp.get(ServerAddress.CHECK_MEMBER_REGISTER_STATE_ADDRESS + identifier)
					.setHeader(MessageField.AUTHORIZATION, DataCache.getLoginInfoData().getToken())
					.asResponse(MemberInfoData.class)
					.timeout(AddMemberByCodeActivity.MEMBER_REGISTER_COUNT_DOWN_TIMES, TimeUnit.SECONDS)
					.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread());
		}

		default Observable<ResponseData> getRegisteAddress(){
			return RxHttp.get(ServerAddress.GET_REGISTER_MEMBER_ADDRESS)
					.setHeader(MessageField.AUTHORIZATION, DataCache.getLoginInfoData().getToken())
//					.asResponse(String.class)
					.asParser(SimpleParser.get(ResponseData.class))
					.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread());
		}
	}

	interface RegisterMemberView extends BaseContact.BaseView{
		default void getRegisterAddressSuccess(String address){}
		default void getRegisterAddressFail(Throwable throwable){}
		default void onRegisterSuccess(MemberInfoData str){}
		default void onRegisterFail(Throwable throwable){}
	}

	abstract class RegisterMemberPresent<M extends RegisterMemberModel, V extends RegisterMemberView> extends BaseContact.BasePresent<M,V>{
		private Disposable disposable;

		public void getRegisteAddress(){
			getModel().getRegisteAddress()
					.as(RxLife.as((LifecycleOwner) getView().getActivity()))
					.subscribe(responseData -> {
						if (ResponseCode.SUCCESS.equals(responseData.getCode())) {
							logger.info("获取会员注册地址成功" + responseData.getData());
							getView().getRegisterAddressSuccess((String) responseData.getData());
						}else {
							throw new MyParseExcepetion(responseData.getCode(), responseData.getMsg());
						}
					}, throwable -> {
						logger.info("获取会员注册地址失败");
						throwable.printStackTrace();
						getView().getRegisterAddressFail(throwable);
					});
		}

		public void checkRegisterState(String identifier){
			disposable = getModel().checkRegisterState(identifier)
					.as(RxLife.as((LifecycleOwner) getView().getActivity()))
					.subscribe(memberInfoData->{
						logger.info("注册成功:" + memberInfoData);
						getView().onRegisterSuccess(memberInfoData);
					}, throwable -> {
						logger.info("注册失败");
						getView().onRegisterFail(throwable);
					});
		}

		public void stopRequest(){
			if (disposable != null && !disposable.isDisposed()){
				disposable.dispose();
			}
		}
	}
}
