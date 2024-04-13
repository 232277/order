package com.dyc.order.cashier.mvp.activity.recharge;

import android.view.View;

import com.dyc.order.cashier.R;
import com.dyc.order.cashier.constant.ActivityOperationField;
import com.dyc.order.cashier.contact.ThirdPartPayContact;
import com.dyc.order.cashier.data.local.DataCache;
import com.dyc.order.cashier.data.response.PlaceOrderData;
import com.dyc.order.cashier.data.response.RechargeRuleData;
import com.dyc.order.cashier.mvp.activity.base.BaseJiaoHangThirdPartPayActivity;

/**
 * func:
 * author:丁语成 on 2020/6/22 10:25
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class JiaoHangRechargeMVPActivity extends BaseJiaoHangThirdPartPayActivity<
        ThirdPartPayContact.ThirdPartPayModel,
		ThirdPartPayContact.ThirdPartPayView,
		ThirdPartPayContact.ThirdPartPayPresent
		> implements ThirdPartPayContact.ThirdPartPayView {
	private RechargeRuleData rechargeRuleData;
	private PlaceOrderData placeOrderData;
	private boolean paySuccess = false;

	@Override
	public void onInitView(View view) {
		super.onInitView(view);
		memberCardPay.setVisibility(View.INVISIBLE);
		rechargeRuleData = getIntent().getParcelableExtra(ActivityOperationField.RECHARGE_AMOUNT_DATA);
		placeOrderData = getIntent().getParcelableExtra(ActivityOperationField.PLACE_ORDER_DATA);
		if (rechargeRuleData != null && memberInfoData != null){
			payAmount = rechargeRuleData.getSellingPrice() == null
					? rechargeRuleData.getFaceValue()
					: rechargeRuleData.getSellingPrice();
			amount.setText(String.format("%s%s",
					getString(R.string.toollibrary_label_concurrency_sign),
					payAmount));
		} else {
			exit(R.string.label_member_store_wrong_recharge_data);
		}
	}

	@Override
	public void doAfterInitView(View v) {
		//不需要去获取商家储值是否开启
//		super.doAfterInitView(v);
	}

	@Override
	public void onPlaceOrderConfirmSuceess() {
		hideDialog();
		logger.info("充值成功");
		memberInfoData.setOverage(memberInfoData.getOverage() + rechargeRuleData.getFaceValue());
		DataCache.addData(ActivityOperationField.RECHARGE_RES, true);
		finishActivity(RechargeAmountChooseActivity.class);
		finish();
	}

	@Override
	public void onPlaceOrderConfirmOrderFail(Throwable throwable) {
		hideDialog();
		showResDialog(false, getString(R.string.label_member_store_recharge_fail));
	}

	@Override
	protected void doOnDismiss() {
		showLoading(R.string.tip_cancel_order);
		if (placeOrderData != null && closeOrder){
			logger.info("close order");
			getPresenter().cancelOrder(placeOrderData.getOrderNo());
		}else {
			hideDialog();
		}
	}

	@Override
	public void onBackPressed() {
		if (!paySuccess){
			super.onBackPressed();
		}
	}

	@Override
	public ThirdPartPayContact.ThirdPartPayModel initM() {
		return new ThirdPartPayContact.ThirdPartPayModel() {
		};
	}

	@Override
	public ThirdPartPayContact.ThirdPartPayPresent initP() {
		return new ThirdPartPayContact.ThirdPartPayPresent() {
		};
	}
}
