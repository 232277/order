package com.dyc.order.cashier.mvp.activity.base;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dyc.order.cashier.constant.ActivityOperationField;
import com.dyc.order.cashier.constant.JianHangConstants;
import com.dyc.order.cashier.constant.MessageField;
import com.dyc.order.cashier.constant.OrderFieldConstant;
import com.dyc.order.cashier.contact.RechargeAndPaymentContact;
import com.dyc.order.cashier.data.fields.ConfirmOrderFields;
import com.dyc.order.cashier.data.fields.PaymentRequestFields;
import com.dyc.order.cashier.data.local.MemberCenter;
import com.dyc.order.cashier.data.local.ShoppingCart;
import com.dyc.order.cashier.data.response.BarCodeData;
import com.dyc.order.cashier.data.response.MemberInfoData;
import com.dyc.order.cashier.data.response.PaymentFinishData;
import com.dyc.order.cashier.data.response.PlaceOrderData;
import com.dyc.order.cashier.mvp.activity.other.ShoppingCartActivity;
import com.dyc.order.cashier.mvp.activity.payment.BillMVPActivity;
import com.dyc.order.cashier.mvp.activity.payment.PaymentResultActivity;
import com.dyc.order.cashier.mvp.activity.recharge.RechargeAmountChooseActivity;
import com.dyc.order.cashier.mvp.activity.recharge.RechargeScanActivity;
import com.dyc.order.cashier.mvp.fragment.NormalDialogFragment;
import com.dyc.order.cashier.view.CountDownDialog;
import com.dyc.administrator.toollibrary.utils.DipUtil;
import com.dyc.administrator.toollibrary.utils.NumUtils;
import com.dyc.administrator.toollibrary.view.BadgeView;
import com.dyc.order.cashier.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

/**
 * func:
 * author:丁语成 on 2020/6/30 15:53
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public abstract class BaseInsidePayActivity <
		M extends RechargeAndPaymentContact.RechargeAndPaymentModel,
		V extends RechargeAndPaymentContact.RechargeAndPaymentView,
		P extends RechargeAndPaymentContact.RechargeAndPaymentPresent> extends
		BaseOutScanActivity<M, V, P> implements RechargeAndPaymentContact.RechargeAndPaymentView, View.OnClickListener {
	protected static final int RECHARGE_SCAN = 10;
	private BadgeView customShowCode;
	private BadgeView customScan;
	private BadgeView facePay;
	private BadgeView memberCardPay;
	private TextView countDownStr;
	private TextView payNoticeStr;
	private ImageView img;
	private ImageView code;
	private ConstraintLayout noticeView;
	private LinearLayout clearCart;

	private MemberInfoData memberInfoData;
	private String memberStoreCode;
	private boolean onScan = true;
	private PaymentRequestFields fields;
	private PlaceOrderData placeOrderData;
	private boolean doClearCart = false;
	private ArrayList<BadgeView> imgs = new ArrayList<>(4);

	@Override
	public int getLayoutId() {
		return R.layout.activity_payment;
	}

	@Override
	public void onInitView(View view) {
		customShowCode = findViewById(R.id.customShowCode);
		customScan = findViewById(R.id.customScanCode);
		facePay = findViewById(R.id.facePay);
		memberCardPay = findViewById(R.id.memberCardPay);
		countDownStr = findViewById(R.id.countDown);
		payNoticeStr = findViewById(R.id.payNoticeStr);
		img = findViewById(R.id.noticeImg);
		code = findViewById(R.id.code);
		noticeView = findViewById(R.id.noticeView);
		clearCart = findViewById(R.id.clearCart);
		initData();
		initImg();
	}

	private void initData(){
		customShowCode.setOnClickListener(this);
		customScan.setOnClickListener(this);
		facePay.setOnClickListener(this);
		memberCardPay.setOnClickListener(this);
		showNormalActionBar(getString(R.string.label_top_bar_pay));
		clearCart.setOnClickListener(v -> {
			showClearCartDialog();
		});

		memberInfoData = MemberCenter.getMemberInfoData();
		fields = getIntent().getParcelableExtra(ActivityOperationField.PAYMENT_REQUEST);
		startCountDown();
	}

	private void initImg(){
		imgs.add(customShowCode);
		imgs.add(customScan);
		imgs.add(facePay);
		imgs.add(memberCardPay);
		setBtnState(customShowCode);
	}

	@Override
	protected void onResume() {
		super.onResume();
		memberStoreCode = getIntent().getStringExtra(ActivityOperationField.MEMBER_STORE_CODE);
	}

	private void showClearCartDialog(){
		NormalDialogFragment fragment = NormalDialogFragment.newInstance(NormalDialogFragment.ONLY_MSG);
		CountDownDialog dialog = CountDownDialog.newInstance(fragment, this);
		fragment.setMsg(getString(R.string.label_payment_sure_not_pay_and_clear_cart))
				.setRightBtn(getString(R.string.toolibrary_btn_confirm), v -> {
					clearCart();
					dialog.dismiss();
				})
				.setLeftBtn(getString(R.string.toolibrary_btn_cancel), v -> {
					dialog.dismiss();
				});
		dialog.setTitle(getString(R.string.label_payment_did_not_pay_for_now_short));
		dialog.show(getSupportFragmentManager(), "CLEAR_CART_DIALOG");
	}

	private void clearCart(){
		ShoppingCart.clearCartNow();
		ShoppingCart.setIsMember(false);
		finishActivity(ShoppingCartActivity.class);
		finishActivity(BillMVPActivity.class);
		finish();
	}

	@Override
	public void toResult(double amount, String msg, boolean res, PaymentFinishData paymentFinishData) {
		showResDialog(res, msg);
	}

	private void showResDialog(boolean res, String errorMsg){
		logger.info("show res dialog");
		if (res){
			Intent intent = new Intent(this, PaymentResultActivity.class);
			intent.putExtra(MessageField.AMOUNT, fields.getOrder().getTransAmount());
			startActivity(intent);
		}else {
			runOnUiThread(()->{
				NormalDialogFragment fragment = NormalDialogFragment.newInstance(NormalDialogFragment.MSG_TOP_OF_IMG);
				CountDownDialog dialog = CountDownDialog.newInstance(fragment, this);
				dialog.show(getSupportFragmentManager(), "RES_DIALOG");
				SpannableString spannableString = new SpannableString(
						getString(R.string.toollibrary_label_concurrency_sign)
								+ NumUtils.remain2NumWithoutZero(fields.getOrder().getTransAmount()));
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
							dialog.dismiss();
							startFacePay();
						});
				dialog.setDismissListener(dialog1 -> {
//					showLoading(R.string.tip_cancel_order);
					getPresenter().cancelOrder(placeOrderData.getOrderNo());
				});
				dialog.setBackGround(R.drawable.circle_corner_count_down_dialog_fail_pay);
				dialog.setTitle(getString(R.string.label_payment_pay_fail));
			});
		}
	}

	/**
	 * 客户展示二维码（被扫）
	 */
	private void CustomShowCodePay(){
		payNoticeStr.setText(R.string.label_payment_pls_show);
		img.setVisibility(View.VISIBLE);
		code.setVisibility(View.INVISIBLE);
		img.setImageResource(R.mipmap.img_show_code);
	}

	/**
	 * 扫码完成
	 * @param keySequence 码
	 */
	@Override
	public void onInputFinish(String keySequence) {
		if (onScan){
			//开单来的支付
			showLoading(R.string.dialog_pls_wait);
			if (fields.getOrder() != null){
				fields.getOrder().setAuthCode(keySequence);
				fields.getOrder().setPayType(OrderFieldConstant.PayTypeInRequest.SCAN_CODE.getNum());
				getPresenter().doPay(fields);
			}
		}
	}

	/**
	 * 终端显示二维码（主扫）
	 */
	private void showCodePay(){
		if (fields != null){
			fields.getOrder().setPayType(OrderFieldConstant.PayTypeInRequest.SHOW_CODE.getNum());
			getPresenter().getPayCode(fields);
		}
	}

	/**
	 * 展示二维码
	 * @param data 码对象
	 */
	@Override
	public void showCode(BarCodeData data) {
		payNoticeStr.setText(R.string.label_payment_pls_scan_code_pay);
		code.setVisibility(View.VISIBLE);
		img.setVisibility(View.INVISIBLE);
		code.setImageBitmap(QRCodeEncoder.syncEncodeQRCode(
				data.getCodeUrl(), DipUtil.dip2px(this, 132),
				Color.parseColor("#000000")));
//		BitmapFactory.decodeResource(getResources(), R.drawable.logo_home))
	}

	/**
	 * 启动建行人脸支付
	 */
	private void startFacePay(){
		getPresenter().placeOrder(fields);
	}

	@SuppressLint("MissingPermission")
	protected void memberCardPay(){
//		myOrderNo = "P10" + "DIV012000099999"() + payType + System.currentTimeMillis();
		ConfirmOrderFields confirmOrderFields = new ConfirmOrderFields();
		confirmOrderFields.setOrderNo(placeOrderData.getOrderNo());
		confirmOrderFields.setThirdPartFlag(0);
		confirmOrderFields.setAuthCode(memberStoreCode);
		confirmOrderFields.setPayType(OrderFieldConstant.PayTypeInRequest.MEMBER_CARD.getNum());
		showLoading(R.string.dialog_pls_wait);
		getPresenter().placeOrderConfirm(confirmOrderFields);
	}

	protected void memberCardScan(){
		if (memberInfoData != null){
			if (memberInfoData.getOverage() < fields.getOrder().getTransAmount()){
				logger.info("会员储值不足, 执行充值");
				startActivity(new Intent(this, RechargeAmountChooseActivity.class));
			}else {
				logger.info("会员储值充足，进行储值支付");
				Intent intent = new Intent(this, RechargeScanActivity.class);
				intent.putExtra(ActivityOperationField.PAYMENT_REQUEST, fields);
//				startActivity(intent);
				startActivityForResult(intent, RECHARGE_SCAN);
			}
		}
	}

	@Override
	public void onPlaceOrderSuccess(PlaceOrderData placeOrderData) {
		this.placeOrderData = placeOrderData;
		String orderNo = "P10" + System.currentTimeMillis();
		Intent intent = new Intent();
		ComponentName componetName = new ComponentName(
				"com.ccb.smartpos.bankpay",
				"com.ccb.smartpos.bankpay.ui.MainActivity");
		intent.setComponent(componetName);
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put(JianHangConstants.AMOUNT, NumUtils.extendToTwelve(fields.getOrder().getTransAmount()));
//			jsonObject.put(JianHangConstants.AMOUNT, NumUtils.extendToTwelve(0.01));
			jsonObject.put(JianHangConstants.NEED_PRINT, "false");
			jsonObject.put(JianHangConstants.LS_ORDER_NO, orderNo);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Bundle bundle = new Bundle();
		bundle.putString(JianHangConstants.APP_NAME, "建行收单应用");
		bundle.putString(JianHangConstants.TRANS_ID, "建行刷脸付");
		bundle.putString(JianHangConstants.TRANS_DATA, jsonObject.toString());
		intent.putExtras(bundle);
		startActivityForResult(intent, 1);
	}

	@Override
	public void onPlaceOrderFail(Throwable throwable) {
		showNotifyDialog(throwable.getMessage(), R.drawable.fail);
	}

	/**
	 * 人脸支付结果
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 */
	private String resultMsg;
	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		resultMsg = getString(R.string.error_str_unknow_fail);
		showLoading(R.string.dialog_pls_wait);
		if (data != null){
			Bundle bundle = data.getExtras();
			if (bundle != null){
				String appResultCode = bundle.getString("resultCode");
				resultMsg = bundle.getString("resultMsg");
				logger.info("appResultCode:" + appResultCode + "requestCode:" + requestCode);
				if ("0".equals(appResultCode)){
					onSuccessPay(bundle);
					return;
				}
			}
		}
		onScan = true;
		showResDialog(false, resultMsg);
	}

	protected void onSuccessPay(Bundle bundle){
		String transData = bundle.getString("transData");
		logger.info("transData:" + transData);
		getPresenter().placeOrderConfirm(null,
				placeOrderData.getOrderNo(), OrderFieldConstant.PayType.FACE_PAY.getNum());
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

	@Override
	public void onCancelOrderSuceess() {
		hideDialog();
		if (doClearCart){
			clearCart();
		}
	}

	@Override
	public void onCancelOrderFail(Throwable throwable) {
		hideDialog();
		if (doClearCart){
			clearCart();
		}
	}

	@Override
	public void onClick(View v) {
		setBtnState(v);
		switch (v.getId()){
			case R.id.customShowCode:
				onScan = true;
				CustomShowCodePay();
				break;
			case R.id.customScanCode:
				showCodePay();
				onScan = false;
				break;
			case R.id.facePay:
				onScan = false;
				pauseCountDown();
				setCountDown(BASE_COUNT_DOWN_TIMES);
				startFacePay();
				break;
//			case R.id.memberCardPay:
////				onScan = false;
////				showToast(R.string.tip_in_develop);
//				startActivity(new Intent(this, RechargeAmountChooseActivity.class));
//				break;
		}
	}

	private void setBtnState(View imgView){
		for (BadgeView img : imgs){
			if (img.equals(imgView)){
				img.setChecked(true);
			}else {
				img.setChecked(false);
			}
		}
	}

	private void setCountDown(long countTime){
		runOnUiThread(()->countDownStr.setText(String.format(Locale.getDefault(), "%ds", countTime)));
	}

	@Override
	protected void onCountDown(long countTime) {
		super.onCountDown(countTime);
		setCountDown(countTime);
	}
}
