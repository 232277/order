package com.dyc.order.cashier.mvp.activity.base;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;

import com.dyc.order.cashier.constant.ActivityOperationField;
import com.dyc.order.cashier.constant.OrderFieldConstant;
import com.dyc.order.cashier.contact.BaseMemberCardPayContact;
import com.dyc.order.cashier.data.fields.ConfirmOrderFields;
import com.dyc.order.cashier.data.fields.PaymentRequestFields;
import com.dyc.order.cashier.data.local.ActivityCenter;
import com.dyc.order.cashier.data.local.DataCache;
import com.dyc.order.cashier.data.local.MemberCenter;
import com.dyc.order.cashier.data.local.ShoppingCart;
import com.dyc.order.cashier.data.response.MemberInfoData;
import com.dyc.order.cashier.data.response.PlaceOrderData;
import com.dyc.order.cashier.mvp.activity.member.AddMemberByCodeActivity2;
import com.dyc.order.cashier.mvp.activity.recharge.RechargeAmountChooseActivity;
import com.dyc.order.cashier.mvp.activity.recharge.RechargeScanActivity;
import com.dyc.order.cashier.mvp.fragment.InputMemberFragment;
import com.dyc.order.cashier.mvp.fragment.NormalDialogFragment;
import com.dyc.order.cashier.view.CountDownDialog;
import com.dyc.administrator.toollibrary.view.BadgeView;
import com.dyc.administrator.toollibrary.view.NumKeyBoard;
import com.dyc.order.cashier.R;

/**
 * func:
 * author:丁语成 on 2020/7/1 9:33
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public abstract class BaseMemberCardPayActivity<
		M extends BaseMemberCardPayContact.BaseMemberCardPayModel,
		V extends BaseMemberCardPayContact.BaseMemberCardPayView,
		P extends BaseMemberCardPayContact.BaseMemberCardPayPresent>
		extends BaseOutScanActivity<M,V,P> implements BaseMemberCardPayContact.BaseMemberCardPayView, View.OnClickListener {
	protected static final int RECHARGE_SCAN = 10;
	/**
	 * 交易前的相关数据类
	 */
	protected MemberInfoData memberInfoData;
	protected PaymentRequestFields fields;
	protected PlaceOrderData placeOrderData;
	/**
	 * 交易中生成的数据
	 */
	protected int payType;
	protected double payAmount;
	protected String memberStoreCode;
	/**
	 * 会员相关
	 */
	protected boolean memberInfoChanged;
	protected CountDownDialog memberDialog;
	protected BadgeView memberCardPay;

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.memberCardPay){
			//是会员就支付判断，不是就输手机号登录
			if (memberInfoData == null){
				showInputMemberDialog();
			}else {
				memberCardScan();
			}
		}
	}

	/**
	 * 会员手机号输入
	 */
	private boolean memberDissmiss = false;
	public void showInputMemberDialog(){
		memberCardPay.setEnabled(false);
		memberDissmiss = false;
		pauseCountDown();
		InputMemberFragment frag = InputMemberFragment.newInstance();
		frag.setOnConfirmListener(phone -> {
			showLoading(R.string.dialog_member_store_loading_member_info);
			getPresenter().getMemberInfo(phone);
			memberDialog.dismiss();
		});
		memberDialog = CountDownDialog.newInstance(frag, this);
		NumKeyBoard numKeyBoard = new NumKeyBoard(this, frag.getOnNumberInputListener());
		numKeyBoard.show();
		memberDialog.show(getSupportFragmentManager(), "MEMBER_DIALOG");
		memberDialog.setContentLayout(frag);
		memberDialog.setTitle(getString(R.string.label_scan_goods_dialog_title_member));
		memberDialog.setCloseOnClick(v->{
			memberDialog.dismiss();
		});
		memberDialog.setDismissListener(dialog -> {
			restartCountDown();
			memberCardPay.setOnClickListener(BaseMemberCardPayActivity.this);
			if (!memberDissmiss){
				memberDissmiss = true;
				numKeyBoard.dismiss();
			}
		});
		numKeyBoard.setOnDismissListener(dialog -> {
			if (!memberDissmiss){
				memberDissmiss = true;
				memberDialog.dismiss();
			}
			memberCardPay.setEnabled(true);
		});
	}

	@Override
	public void doAfterInitView(View v) {
		super.doAfterInitView(v);
		showLoading(R.string.dialog_pls_wait);
		getPresenter().getMemberCanUsePointCard();
	}

	@Override
	public void canUsePointCard() {
		hideDialog();
		if (memberCardPay != null){
			memberCardPay.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void canNotUsePointCard() {
		hideDialog();
		if (memberCardPay != null){
			memberCardPay.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (DataCache.get(ActivityOperationField.MEMBER_INFO_CHANGED) != null){
			if ((boolean)DataCache.get(ActivityOperationField.MEMBER_INFO_CHANGED)){
				DataCache.removeData(ActivityOperationField.MEMBER_INFO_CHANGED);
				logger.info("会员信息变化:" + (MemberCenter.getMemberInfoData() == null));
				getMemberSuccess(MemberCenter.getMemberInfoData());
			}
		}
	}

	@Override
	public void getMemberSuccess(MemberInfoData memberInfoData) {
		if (memberInfoData != null){
			this.memberInfoData = memberInfoData;
			updateLoadingMsg(R.string.dialog_member_store_cancel_last_order);
			memberInfoChanged = true;
			logger.info("进行取消订单->会员信息改变");
			getPresenter().cancelOrder(placeOrderData.getOrderNo());
		}
	}

	@Override
	public void getMemberFail(Throwable throwable) {
		hideDialog();
		NormalDialogFragment fragment = NormalDialogFragment.newInstance(NormalDialogFragment.MSG_BOTTOM_OF_IMG);
		fragment.setMsg(getString(R.string.dialog_scan_goods_member_not_exist_go_register))
				.setImg(R.mipmap.pop_member_not)
				.setSingleBtn(getString(R.string.label_scan_goods_btn_go_register), v -> {
//						startActivity(new Intent(BaseMemberInfoActivity.this, AddMemberActivity.class));
					Intent intent = new Intent(BaseMemberCardPayActivity.this, AddMemberByCodeActivity2.class);
					intent.putExtra(ActivityOperationField.SCAN_CODE_REGISTER, true);
					startActivity(intent);
					fragment.dismiss();
				});
		CountDownDialog dialog = CountDownDialog.newInstance(fragment, this);
		dialog.show(getSupportFragmentManager(), "GET_MEMBER_FAIL");
		dialog.setTitle(getString(R.string.dialog_scan_goods_member_not_exist));
	}

	@Override
	public void onCancelOrderSuceess() {
		MemberCenter.setMemberInfoData(memberInfoData);
		ShoppingCart.setIsMember(true);
		placeOrderData = null;
		fields = ShoppingCart.getPaymentRequest(memberInfoData.getId());
		fields.getOrder().setTransAmount(fields.getOrder().getTransAmount()
				- ActivityCenter.getPriceBreakDiscount(ShoppingCart.getCartItems()));
		payAmount = fields.getOrder().getTransAmount();
		updateLoadingMsg(R.string.dialog_member_re_place_order);
		getPresenter().placeOrder(fields);
	}

	@Override
	public void onCancelOrderFail(Throwable throwable) {
		hideDialog();
		memberInfoChanged = false;
		memberInfoData = null;
		showNotifyDialog(R.string.label_member_store_cancel_order_fail, R.drawable.fail);
	}

	@Override
	public void onPlaceOrderSuccess(PlaceOrderData placeOrderData) {
		hideDialog();
		this.placeOrderData = placeOrderData;
		memberCardScan();
	}

	@Override
	public void onPlaceOrderFail(Throwable throwable) {
		hideDialog();
		exit(R.string.label_member_store_re_place_order_fail);
	}

	@SuppressLint("MissingPermission")
	protected void memberCardPay(){
//		myOrderNo = "P10" + "DIV012000099999"() + payType + System.currentTimeMillis();
		payType = OrderFieldConstant.PayTypeInRequest.MEMBER_CARD.getNum();
		ConfirmOrderFields confirmOrderFields = new ConfirmOrderFields();
		confirmOrderFields.setOrderNo(placeOrderData.getOrderNo());
		confirmOrderFields.setThirdPartFlag(0);
		confirmOrderFields.setAuthCode(memberStoreCode);
		confirmOrderFields.setPayType(payType);
		showLoading(R.string.dialog_pls_wait);
		getPresenter().placeOrderConfirm(confirmOrderFields);
	}

	protected void memberCardScan(){
		if (memberInfoData != null){
			if (memberInfoData.getOverage() < fields.getOrder().getTransAmount()){
				logger.info("会员储值不足, 执行充值");
				showOverageNotEnoughDialog();
//				startActivity(new Intent(this, RechargeAmountChooseActivity.class));
			}else {
				logger.info("会员储值充足，进行储值支付");
				Intent intent = new Intent(this, RechargeScanActivity.class);
				intent.putExtra(ActivityOperationField.PAYMENT_REQUEST, fields);
//				startActivity(intent);
				startActivityForResult(intent, RECHARGE_SCAN);
			}
		}
	}

	private void showOverageNotEnoughDialog(){
		NormalDialogFragment fragment = NormalDialogFragment.newInstance(NormalDialogFragment.ONLY_MSG);
		fragment.setMsg(getString(R.string.dialog_member_overage_not_enough))
				.setLeftBtn(getString(R.string.toolibrary_btn_cancel), v -> {
					fragment.dismiss();
				})
				.setRightBtn(getString(R.string.toolibrary_btn_confirm), v -> {
					startActivity(new Intent(this, RechargeAmountChooseActivity.class));
					fragment.dismiss();
				});
		CountDownDialog countDownDialog = CountDownDialog.newInstance(fragment, this);
		countDownDialog.setTitle(getString(R.string.label_member_store_member_store_str));
		countDownDialog.show(getSupportFragmentManager(), "OVERAGE_NOT_ENOUGH");
	}

	@Override
	public void finish() {
		hideDialog();
		super.finish();
	}
}
