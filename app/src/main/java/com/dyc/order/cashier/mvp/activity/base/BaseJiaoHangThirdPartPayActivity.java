package com.dyc.order.cashier.mvp.activity.base;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.dyc.order.cashier.constant.ActivityOperationField;
import com.dyc.order.cashier.constant.JiaoHangConstants;
import com.dyc.order.cashier.constant.OrderFieldConstant;
import com.dyc.order.cashier.contact.ThirdPartPayContact;
import com.dyc.order.cashier.data.fields.ConfirmOrderFields;
import com.dyc.order.cashier.data.local.MemberCenter;
import com.dyc.order.cashier.data.local.PayAppCenter;
import com.dyc.administrator.toollibrary.view.BadgeView;
import com.dyc.order.cashier.R;

import androidx.annotation.Nullable;

/**
 * func:
 * author:丁语成 on 2020/6/29 15:05
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public abstract class BaseJiaoHangThirdPartPayActivity <
		M extends ThirdPartPayContact.ThirdPartPayModel,
		V extends ThirdPartPayContact.ThirdPartPayView,
		P extends ThirdPartPayContact.ThirdPartPayPresent> extends
		BaseThirdPartPayActivity<M, V, P> implements ThirdPartPayContact.ThirdPartPayView, View.OnClickListener {
	protected BadgeView wechat;
	protected BadgeView aliCode;
	protected BadgeView saoyisao;
	protected TextView amount;

	protected boolean isAli = false;

	@Override
	public int getLayoutId() {
		return R.layout.activity_jiao_hang_payment;
	}

	@Override
	public void onInitView(View view) {
		findViewById(R.id.backArrow).setOnClickListener(v->onBackPressed());
		wechat = findViewById(R.id.wechatCode);
		aliCode = findViewById(R.id.aliCode);
		saoyisao = findViewById(R.id.saoyisao);
		memberCardPay = findViewById(R.id.memberCardPay);
		amount = findViewById(R.id.amount);
		findViewById(R.id.saoyisaoLayout).setOnClickListener(this);
		wechat.setOnClickListener(this);
		aliCode.setOnClickListener(this);
		memberCardPay.setOnClickListener(this);
		showActionBar(false);
		initData();
	}

	private void initData(){
		imgs.add(wechat);
		imgs.add(aliCode);
		imgs.add(saoyisao);
		imgs.add(memberCardPay);
		memberInfoData = MemberCenter.getMemberInfoData();
		fields = getIntent().getParcelableExtra(ActivityOperationField.PAYMENT_REQUEST);
		placeOrderData = getIntent().getParcelableExtra(ActivityOperationField.PLACE_ORDER_DATA);
		if (fields != null && fields.getOrder() != null){
			payAmount = fields.getOrder().getTransAmount();
			amount.setText(String.format("%s%s",
					getString(R.string.toollibrary_label_concurrency_sign),
					fields.getOrder().getTransAmount()));
		}

		startCountDown();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (memberInfoChanged){
			amount.setText(String.format("%s%s",
					getString(R.string.toollibrary_label_concurrency_sign),
					payAmount));
		}
	}

	@Override
	protected void startThirdPartPay(){
		logger.info("start third part pay");
		String orderNo = "P10" + System.currentTimeMillis();
		Intent intent = new Intent();
		ComponentName comp = new ComponentName(
				PayAppCenter.getPayAppName().getPayPackageName(),
				PayAppCenter.getPayAppName().getPayActivityName());
		intent.setComponent(comp);
		Bundle data = new Bundle();

		String transCode;
		if (payType == OrderFieldConstant.PayTypeInRequest.SHOW_CODE.getNum()){
			if (isAli){
				transCode = JiaoHangConstants.JIAOHANG_ALI_SHOW_CODE;
			}else {
				transCode = JiaoHangConstants.JIAOHANG_WX_SHOW_CODE;
			}
		}else if (payType == OrderFieldConstant.PayTypeInRequest.SCAN_CODE.getNum()){
			transCode = JiaoHangConstants.JIAOHANG_SCAN_CODE;
		}else {
			transCode = JiaoHangConstants.JIAOHANG_FACE_PAY;
		}
		data.putString(JiaoHangConstants.TRANS_CODE, transCode);
			data.putString(JiaoHangConstants.TRANS_AMT, ""+fields.getOrder().getTransAmount());
//		data.putString(JiaoHangConstants.TRANS_AMT, "0.01");
		data.putString(JiaoHangConstants.CALLER_ID, getPackageName());
		data.putString(JiaoHangConstants.CALLER_SECRET,getPackageName());
		data.putString(JiaoHangConstants.CONTROL_INFO, "00000000");
		intent.putExtras(data);
		try {
			startActivityForResult(intent, 1);
		}catch (ActivityNotFoundException e){
			e.printStackTrace();
			foundApp = false;
		}
	}

	/**
	 * 第三方支付结果
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 */
	private String resultMsg;
	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		resultMsg = getString(R.string.error_str_unknow_fail);
		if (!foundApp){
			exit(R.string.dialog_pls_install_jiaohang_app);
		}else if (data != null && data.getExtras() != null) {
			showLoading(R.string.dialog_pls_wait);
			Bundle bundle = data.getExtras();
			String appResultCode = bundle.getString(JiaoHangConstants.RESP_CODE);
			if ("00".equals(appResultCode)){
				doOnThirdPaySuccess(bundle);
			}else {
				showResDialog(false, resultMsg);
			}
		}else {
			showResDialog(false, resultMsg);
		}
	}

	@SuppressLint("MissingPermission")
	protected void doOnThirdPaySuccess(Bundle bundle){
		resultMsg = bundle.getString(JiaoHangConstants.RESP_MSG);
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()){
			sb.append(key).append(":").append(bundle.get(key)).append("  ");
		}
		logger.info(sb);
		platformNo = bundle.getString(JiaoHangConstants.ORDER_NO);
		//聚合码扫码的时候sys_order_no才是订单号,order_no是支付码
		String sys_order_no = bundle.getString(JiaoHangConstants.SYS_ORDER_NO);
		if (!TextUtils.isEmpty(sys_order_no)){
			platformNo = sys_order_no;
		}
		String channel = bundle.getString(JiaoHangConstants.SCAN_CHANNEL);
		//主被扫
		payType = JiaoHangConstants.getPayTypeInConfirm(payType, channel);
		updateLoadingMsg(R.string.dialog_in_confirm_res);
		ConfirmOrderFields fields = new ConfirmOrderFields();
		ConfirmOrderFields.Transaction transaction = new ConfirmOrderFields.Transaction();
		transaction.setOrderNo(placeOrderData.getOrderNo());
		transaction.setOriginAmount(payAmount);
		transaction.setPayType(payType);
		transaction.setPlatformNo(platformNo);
		transaction.setTerminalId("DIV012000099999");
		transaction.setTransAmount(payAmount);
		transaction.setTransFlag(OrderFieldConstant.TransFlag.ALREADY_PAY.getNum());
		transaction.setTransDate(bundle.getString(JiaoHangConstants.TRANS_TIME));
		transaction.setTransTime(bundle.getString(JiaoHangConstants.TRANS_TIME2));
		transaction.setBatchNo(bundle.getString(JiaoHangConstants.BATCH_NO));
		fields.setPayType(payType);
		fields.setOrderNo(placeOrderData.getOrderNo());
		fields.setTransaction(transaction);
		fields.setThirdPartFlag(1);
		getPresenter().placeOrderConfirm(fields);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		setBtnState(v);
		switch (v.getId()){
			case R.id.saoyisaoLayout:
				CustomShowCodePay();
				break;
			case R.id.aliCode:
				this.isAli = true;
				showCodePay();
				break;
			case R.id.wechatCode:
				this.isAli = false;
				showCodePay();
				break;
		}
	}
}

