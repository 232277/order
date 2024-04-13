package com.dyc.order.cashier.mvp.activity.base;

import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import com.dyc.order.cashier.constant.ActivityOperationField;
import com.dyc.order.cashier.constant.MessageField;
import com.dyc.order.cashier.constant.OrderFieldConstant;
import com.dyc.order.cashier.contact.ThirdPartPayContact;
import com.dyc.order.cashier.data.local.DataCache;
import com.dyc.order.cashier.data.local.ShoppingCart;
import com.dyc.order.cashier.mvp.activity.other.ShoppingCartActivity;
import com.dyc.order.cashier.mvp.activity.payment.BillMVPActivity;
import com.dyc.order.cashier.mvp.activity.payment.PaymentResultActivity;
import com.dyc.order.cashier.mvp.fragment.NormalDialogFragment;
import com.dyc.order.cashier.view.CountDownDialog;
import com.dyc.administrator.toollibrary.utils.NumUtils;
import com.dyc.administrator.toollibrary.view.BadgeView;
import com.dyc.order.cashier.R;

import java.util.ArrayList;
import java.util.List;

/**
 * func:
 * author:丁语成 on 2020/6/29 15:32
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public abstract class BaseThirdPartPayActivity <
		M extends ThirdPartPayContact.ThirdPartPayModel,
		V extends ThirdPartPayContact.ThirdPartPayView,
		P extends ThirdPartPayContact.ThirdPartPayPresent> extends
		BaseMemberCardPayActivity<M, V, P> implements ThirdPartPayContact.ThirdPartPayView, View.OnClickListener {
	/**
	 * 支付方式钮数组
	 */
	protected List<BadgeView> imgs = new ArrayList<>(4);
	/**
	 * 操作控制
	 */
	protected boolean doClearCart = false;
	protected boolean foundApp = true;
	/**
	 * 交易相关
	 */
	protected String platformNo;
	protected String myOrderNo;

	/**
	 * 客户展示二维码（被扫）
	 */
	protected void CustomShowCodePay(){
		payType = OrderFieldConstant.PayTypeInRequest.SCAN_CODE.getNum();
		startThirdPartPay();
	}

	/**
	 * 终端显示二维码（主扫）
	 */
	protected void showCodePay(){
		payType = OrderFieldConstant.PayTypeInRequest.SHOW_CODE.getNum();
		startThirdPartPay();
	}

	/**
	 * 启动人脸支付
	 */
	protected void startFacePay(){
		payType = OrderFieldConstant.PayTypeInRequest.FACE.getNum();
		startThirdPartPay();
	}

	protected abstract void startThirdPartPay();

	@Override
	protected void onResume() {
		super.onResume();
		if (DataCache.contains(ActivityOperationField.RECHARGE_RES)){
			if ((boolean)DataCache.get(ActivityOperationField.RECHARGE_RES)){
				DataCache.removeData(ActivityOperationField.RECHARGE_RES);
				showNotifyDialog(R.string.label_member_store_recharge_success, R.mipmap.icon_toast_success);
			}
		}
	}

	@Override
	public void onPlaceOrderConfirmSuceess() {
		hideDialog();
		showResDialog(true, null);
	}

	@Override
	public void onPlaceOrderConfirmOrderFail(Throwable throwable) {
		hideDialog();
		showResDialog(false, throwable.getMessage());
	}

	protected boolean closeOrder = true;
	protected void showResDialog(boolean res, String errorMsg){
		hideDialog();
		logger.info("show res dialog");
		if (res){
			Intent intent = new Intent(this, PaymentResultActivity.class);
			intent.putExtra(MessageField.AMOUNT, payAmount);
			startActivity(intent);
			finish();
		}else {
			runOnUiThread(()->{
				closeOrder = true;
				NormalDialogFragment fragment = NormalDialogFragment.newInstance(NormalDialogFragment.MSG_TOP_OF_IMG);
				CountDownDialog dialog = CountDownDialog.newInstance(fragment, this);
				dialog.show(getSupportFragmentManager(), "RES_DIALOG");
				SpannableString spannableString = new SpannableString(
						getString(R.string.toollibrary_label_concurrency_sign)
								+ NumUtils.remain2NumWithoutZero(payAmount));
				spannableString.setSpan(new ForegroundColorSpan(
								getResources().getColor(R.color.normal_item_text_color_red, null)),
						0, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
				fragment.setMsg(spannableString)
						.setImg(R.mipmap.icon_payment_failure)
						.setLeftBtn(getString(R.string.label_payment_did_not_pay_for_now_short), v -> {
							dialog.dismiss();
							doClearCart = true;
						})
						.setRightBtn(getString(R.string.label_payment_repay), v -> {
							closeOrder = true;
							dialog.dismiss();
//							startThirdPartPay(false);
						});
				dialog.setCloseOnClick(v -> {
					dialog.dismiss();
					doClearCart = true;
				});
				dialog.setDismissListener(dialog1 -> {
					doOnDismiss();
				});
				dialog.setBackGround(R.drawable.circle_corner_count_down_dialog_fail_pay);
				dialog.setCancelable(false);
				dialog.setTitle(getString(R.string.label_payment_pay_fail));
			});
		}
	}

	protected void doOnDismiss(){
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
		logger.info("onBackPressed");
		if (placeOrderData != null){
			logger.info("close order");
			showLoading(R.string.dialog_pls_wait);
			getPresenter().cancelOrder(placeOrderData.getOrderNo());
		}
	}

	@Override
	public void onCancelOrderSuceess() {
		if (memberInfoChanged){
			logger.info("取消订单->会员信息改变");
			memberInfoChanged = false;
			super.onCancelOrderSuceess();
		}else {
			hideDialog();
			restartCountDown();
			if (doClearCart){
				clearCart();
			}else {
				finish();
			}
		}
	}

	@Override
	public void onCancelOrderFail(Throwable throwable) {
		if (memberInfoChanged){
			logger.info("取消订单->会员信息改变");
			super.onCancelOrderFail(throwable);
		}else {
			hideDialog();
			restartCountDown();
			if (doClearCart){
				clearCart();
			}else {
				finish();
			}
		}
	}

	protected void clearCart(){
		ShoppingCart.clearCartNow();
		ShoppingCart.setIsMember(false);
		finishActivity(ShoppingCartActivity.class);
		finishActivity(BillMVPActivity.class);
		finish();
	}

	protected void setBtnState(View imgView){
		for (BadgeView img : imgs){
			if (img.equals(imgView)){
				img.setChecked(true);
			}else {
				img.setChecked(false);
			}
		}
	}

	/**
	 * 扫码完成
	 * @param keySequence 码
	 */
	@Override
	public void onInputFinish(String keySequence) {
	}
}
