package com.dyc.order.cashier.mvp.activity.recharge;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dyc.order.cashier.adapter.RechargeAmountAdapter;
import com.dyc.order.cashier.constant.ActivityOperationField;
import com.dyc.order.cashier.constant.OrderFieldConstant;
import com.dyc.order.cashier.contact.MemberRechargeContact;
import com.dyc.order.cashier.data.fields.MemberStoreDTOFields;
import com.dyc.order.cashier.data.local.MemberCenter;
import com.dyc.order.cashier.data.local.PayAppCenter;
import com.dyc.order.cashier.data.response.MemberInfoData;
import com.dyc.order.cashier.data.response.PlaceOrderData;
import com.dyc.order.cashier.data.response.RechargeRuleData;
import com.dyc.administrator.toollibrary.adapter.MyFlexboxLayoutManager;
import com.dyc.administrator.toollibrary.utils.NumUtils;
import com.dyc.order.cashier.R;
import com.dyc.order.cashier.mvp.activity.base.BaseCountDownActivity;
import com.google.android.flexbox.FlexDirection;

import java.util.Collections;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

/**
 * func:
 * author:丁语成 on 2020/6/22 10:14
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class RechargeAmountChooseActivity extends BaseCountDownActivity<
        MemberRechargeContact.MemberRechargeModel,
		MemberRechargeContact.MemberRechargeView,
		MemberRechargeContact.MemberRechargePresent> implements MemberRechargeContact.MemberRechargeView {
	private TextView countDownStr;
	private LinearLayout leftView;
	private TextView remainRecharge;
	private RecyclerView amountView;
	private Button rechargeNow;

	private MemberInfoData memberInfoData;
	private RechargeAmountAdapter adapter;
	private RechargeRuleData chosenRule;

	@Override
	public int getLayoutId() {
		return R.layout.activity_recharge_amount_choose;
	}

	@Override
	public void onInitView(View view) {
		showActionBar(false);
		countDownStr = findViewById(R.id.countDownStr);
		leftView = findViewById(R.id.topBarLeft);
		remainRecharge = findViewById(R.id.amountRemain);
		amountView = findViewById(R.id.amountChooseView);
		rechargeNow = findViewById(R.id.rechargeNow);
		leftView.setOnClickListener(v -> onBackPressed());
	}

	@Override
	public void doAfterInitView(View v) {
		super.doAfterInitView(v);
		startCountDown();

		memberInfoData = MemberCenter.getMemberInfoData();

		//取消更新动画
		((SimpleItemAnimator)amountView.getItemAnimator()).setSupportsChangeAnimations(false);
		amountView.setNestedScrollingEnabled(false);
		adapter = new RechargeAmountAdapter();
		MyFlexboxLayoutManager flexboxLayoutManager = new MyFlexboxLayoutManager(this);
		flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
		amountView.setLayoutManager(flexboxLayoutManager);
		amountView.setAdapter(adapter);
		adapter.setEmptyView(R.layout.adapter_empty_view_choose_recharge_amount);
		adapter.setListener(rechargeRuleData -> {
			chosenRule = rechargeRuleData;
			placeRechargeOrder(rechargeRuleData);
		});

		if (memberInfoData == null){
			exit(R.string.label_member_store_not_a_member);
		}else {
			remainRecharge.setText(String.format("%s%s",
					getString(R.string.toollibrary_label_concurrency_sign),
					NumUtils.remain2NumWithoutZero(memberInfoData.getOverage())));
			showLoading(R.string.dialog_pls_wait);
			getPresenter().getMemberCanUsePointCard();
		}
	}

	protected void placeRechargeOrder(RechargeRuleData rechargeRuleData){
		runOnUiThread(()->showLoading(R.string.dialog_pls_wait));
		MemberStoreDTOFields fields = new MemberStoreDTOFields();
		fields.setPayType(OrderFieldConstant.PayType.UNKNOWN.getNum());
		fields.setMemberId(memberInfoData.getId());
		fields.setRechargeRuleId(rechargeRuleData.getId());
		fields.setOrderSource(OrderFieldConstant.OrderSource.POS.getNum());
		fields.setTotalAmount(rechargeRuleData.getFaceValue());
		fields.setTransAmount(rechargeRuleData.getSellingPrice());
		getPresenter().memberStorePay(fields);
	}

	@Override
	public void onPlaceRechargeOrderSuccess(double amount, PlaceOrderData placeOrderData) {
		hideDialog();
		Intent intent = new Intent();
		PayAppCenter.PayAppName payAppName = PayAppCenter.getPayAppName();
		if (payAppName == PayAppCenter.PayAppName.JIANHANG
		|| payAppName == PayAppCenter.PayAppName.JIANHANG_2){
			intent.setClass(this, JianHangRechargeMVPActivity.class);
		}else if (payAppName == PayAppCenter.PayAppName.JIAOHANG){
			intent.setClass(this, JiaoHangRechargeMVPActivity.class);
		}else {
			intent.setClass(this, RechargeMVPActivity.class);
		}
		intent.putExtra(ActivityOperationField.RECHARGE_AMOUNT_DATA, chosenRule);
		intent.putExtra(ActivityOperationField.PLACE_ORDER_DATA, placeOrderData);
		startActivity(intent);
	}

	@Override
	public void onPlaceRechargeOrderFail(double amount, String msg, PlaceOrderData placeOrderData) {
		hideDialog();
		showNotifyDialog(msg, R.drawable.fail);
	}

	@Override
	public void canUsePointCard() {
		getPresenter().getMemberPointCardRechargeRule(memberInfoData.getId());
	}

	@Override
	public void canNotUsePointCard() {
		hideDialog();
		logger.info("未开通储值功能");
		setEmptyView(R.string.label_member_store_merchant_can_not_use_store, 0);
		exit(R.string.label_member_store_merchant_can_not_use_store);
	}

	@Override
	public void errorGetCanUsePointCard(Throwable throwable) {
		exit(R.string.label_member_store_fail_get_recharge_list);
	}

	@Override
	public void getPointCardRechargeRuleSuccess(List<RechargeRuleData> datas) {
		hideDialog();
		logger.info("规则数:" + datas.size());
		Collections.sort(datas, (d1, d2)->{
			if ((d1 == null && d2 == null)){
				return 0;
			}
			if (d1 != null && d2 != null){
				if (d1.getFaceValue() == null && d2.getFaceValue() == null){
					return 0;
				}
			}
			if (d1 == null || d1.getFaceValue() == null){
				return -1;
			}
			if (d2 == null || d2.getFaceValue() == null){
				return 1;
			}
			return d1.getFaceValue().compareTo(d2.getFaceValue());
		});
		if (!datas.isEmpty()){
			runOnUiThread(()->{
				adapter.setNewData(datas);
				adapter.notifyDataSetChanged();
			});
		}else {
			setEmptyView(R.string.label_member_store_no_enable_recharge_rule, 0);
		}
	}

	@Override
	public void getPointCardRechargeRuleFail(Throwable throwable) {
		exit(R.string.label_member_store_fail_get_recharge_list);
	}

	private void setEmptyView(int strId, int img){
		setEmptyView(getString(strId), img);
	}

	private void setEmptyView(CharSequence str, int img){
		((TextView)adapter.getEmptyLayout().findViewById(R.id.notice)).setText(str);
		ImageView imgView = adapter.getEmptyLayout().findViewById(R.id.img);
		if (img > 0){
			imgView.setVisibility(View.VISIBLE);
			imgView.setImageResource(img);
		}else {
			imgView.setVisibility(View.GONE);
		}
	}

	@Override
	protected void onCountDown(long countTime) {
		super.onCountDown(countTime);
		runOnUiThread(()->countDownStr.setText(getFormatCountDownStr(countTime)));
	}

	@Override
	public MemberRechargeContact.MemberRechargeModel initM() {
		return new MemberRechargeContact.MemberRechargeModel() {
		};
	}

	@Override
	public MemberRechargeContact.MemberRechargePresent initP() {
		return new MemberRechargeContact.MemberRechargePresent() {
		};
	}
}
