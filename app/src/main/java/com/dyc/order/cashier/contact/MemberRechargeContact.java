package com.dyc.order.cashier.contact;

import com.dyc.order.cashier.constant.MessageField;
import com.dyc.order.cashier.constant.ServerAddress;
import com.dyc.order.cashier.base.iml.BaseContact;
import com.dyc.order.cashier.data.fields.MemberStoreDTOFields;
import com.dyc.order.cashier.data.local.DataCache;
import com.dyc.order.cashier.data.response.MemberInfoData;
import com.dyc.order.cashier.data.response.PlaceOrderData;
import com.dyc.order.cashier.data.response.RechargeRuleData;
import com.google.gson.JsonParser;
import com.rxjava.rxlife.RxLife;

import java.util.List;

import androidx.lifecycle.LifecycleOwner;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.utils.GsonUtil;


/**
 * func:
 * author:丁语成 on 2020/6/22 11:02
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public interface MemberRechargeContact {
	interface MemberRechargeModel extends BaseContact.BaseModel{

		default Observable<PlaceOrderData> memberStorePay(MemberStoreDTOFields fields){
			return RxHttp.postJson(ServerAddress.MEMBER_POINT_CARD_PAY)
					.addHeader(MessageField.AUTHORIZATION, DataCache.getLoginInfoData().getToken())
					.addAll(new JsonParser().parse(GsonUtil.toJson(fields)).getAsJsonObject())
					.asResponse(PlaceOrderData.class)
					.observeOn(AndroidSchedulers.mainThread())
					.subscribeOn(Schedulers.newThread());
		}

		default Observable<Boolean> getMemberCanUsePointCard(){
			return RxHttp.get(ServerAddress.MEMBER_POINT_CARD_OPEN)
					.addHeader(MessageField.AUTHORIZATION, DataCache.getLoginInfoData().getToken())
					.asResponse(Boolean.class)
					.observeOn(AndroidSchedulers.mainThread())
					.subscribeOn(Schedulers.newThread());
		}

		default Observable<List<RechargeRuleData>> getMemberPointCardRechargeRule(long id){
			return RxHttp.get(ServerAddress.MEMBER_POINT_CARD_RECHARGE_RULE + id)
					.addHeader(MessageField.AUTHORIZATION, DataCache.getLoginInfoData().getToken())
					.asResponseList(RechargeRuleData.class)
					.observeOn(AndroidSchedulers.mainThread())
					.subscribeOn(Schedulers.newThread());
		}

		default Observable<List<RechargeRuleData>> getMemberPointCardRechargeRule(MemberInfoData memberInfoData){
			return getMemberPointCardRechargeRule(memberInfoData.getId());
			//TODO 防止后续需要使用更多的会员信息
//			return RxHttp.get(ServerAddress.MEMBER_POINT_CARD_RECHARGE_RULE + memberInfoData.getId())
//					.addHeader(MessageField.AUTHORIZATION, DataCache.getLoginInfoData().getToken())
//					.asResponse(String.class)
//					.observeOn(Schedulers.newThread())
//					.subscribeOn(AndroidSchedulers.mainThread());
		}
	}

	interface MemberRechargeView extends BaseContact.BaseView{
		default void canUsePointCard(){}
		default void canNotUsePointCard(){}
		default void errorGetCanUsePointCard(Throwable throwable){}

		default void getPointCardRechargeRuleSuccess(List<RechargeRuleData> datas){}
		default void getPointCardRechargeRuleFail(Throwable throwable){}

		default void onPlaceRechargeOrderFail(double amount, String msg, PlaceOrderData placeOrderData) {
			hideDialog();
		}

		default void onPlaceRechargeOrderSuccess(double amount, PlaceOrderData placeOrderData) {
			hideDialog();
		}
	}

	abstract class MemberRechargePresent<M extends MemberRechargeModel, V extends MemberRechargeView> extends BaseContact.BasePresent<M,V>{
		public void getMemberCanUsePointCard(){
			getModel().getMemberCanUsePointCard()
					.as(RxLife.as((LifecycleOwner) getView().getActivity()))
					.subscribe(res -> {
						logger.info("成功获取商家开通储值卡功能结果" + res);
						if (res != null){
							if (res){
								getView().canUsePointCard();
							}else {
								getView().canNotUsePointCard();
							}
						}
					}, throwable -> {
						logger.info("获取能否使用失败");
						throwable.printStackTrace();
						getView().errorGetCanUsePointCard(throwable);
					});
		}

		public void getMemberPointCardRechargeRule(long memberId){
			getModel().getMemberPointCardRechargeRule(memberId)
					.as(RxLife.as((LifecycleOwner) getView().getActivity()))
					.subscribe(res -> {
						logger.info("成功获取会员储值卡充值规则列表:" + res);
						getView().getPointCardRechargeRuleSuccess(res);
					}, throwable -> {
						logger.info("获取会员储值卡充值规则列表失败");
						throwable.printStackTrace();
						getView().getPointCardRechargeRuleFail(throwable);
					});
		}

		public void getMemberPointCardRechargeRule(MemberInfoData memberInfoData){
			getMemberPointCardRechargeRule(memberInfoData.getId());
		}

		public void memberStorePay(MemberStoreDTOFields fields){
			getModel().memberStorePay(fields)
					.as(RxLife.as((LifecycleOwner) getView().getActivity()))
					.subscribe(res -> {
						logger.info("充值成功:" + res);
						getView().onPlaceRechargeOrderSuccess(fields.getTransAmount(), res);
					}, throwable -> {
						logger.info("充值失败");
						throwable.printStackTrace();
						getView().onPlaceRechargeOrderFail(fields.getTransAmount(), throwable.getMessage(), null);
					});
		}
	}
}
