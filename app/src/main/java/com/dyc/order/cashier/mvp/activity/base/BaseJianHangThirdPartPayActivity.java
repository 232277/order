package com.dyc.order.cashier.mvp.activity.base;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dyc.order.cashier.constant.ActivityOperationField;
import com.dyc.order.cashier.constant.JianHangConstants;
import com.dyc.order.cashier.constant.OrderFieldConstant;
import com.dyc.order.cashier.contact.ThirdPartPayContact;
import com.dyc.order.cashier.data.fields.ConfirmOrderFields;
import com.dyc.order.cashier.data.local.MemberCenter;
import com.dyc.order.cashier.data.local.PayAppCenter;
import com.dyc.order.cashier.view.JianHangBadgeView;
import com.dyc.administrator.toollibrary.utils.NumUtils;
import com.dyc.administrator.toollibrary.view.BadgeView;
import com.dyc.order.cashier.R;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.Nullable;

/**
 * func:
 * author:丁语成 on 2020/6/23 14:28
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public abstract class BaseJianHangThirdPartPayActivity<
		M extends ThirdPartPayContact.ThirdPartPayModel,
		V extends ThirdPartPayContact.ThirdPartPayView,
		P extends ThirdPartPayContact.ThirdPartPayPresent> extends
		BaseThirdPartPayActivity<M, V, P> implements ThirdPartPayContact.ThirdPartPayView, View.OnClickListener {
	protected final static int JIANHANG_PAY = 1;
	protected JianHangBadgeView customShowCode;
	protected JianHangBadgeView customScan;
	protected BadgeView facePay;
//	protected BadgeView memberCardPay;
	protected TextView memberCardPayStr;
	protected Button confirm;

	@Override
	public int getLayoutId() {
		return R.layout.activity_jian_hang_payment_mvp;
	}

	@Override
	public void onInitView(View view) {
		customShowCode = findViewById(R.id.customShowCode);
		customScan = findViewById(R.id.customScanCode);
		facePay = findViewById(R.id.facePay);
		memberCardPay = findViewById(R.id.memberCardPay);
		memberCardPayStr = findViewById(R.id.memberCardPayStr);
		confirm = findViewById(R.id.confirm);
		confirm.setOnClickListener(this);
		customShowCode.setOnClickListener(this);
		customScan.setOnClickListener(this);
		facePay.setOnClickListener(this);
		memberCardPay.setOnClickListener(this);
		showNormalActionBar(getString(R.string.label_top_bar_pay));
		startCountDown();
		initData();
		initImg();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	protected void initData(){
		fields = getIntent().getParcelableExtra(ActivityOperationField.PAYMENT_REQUEST);
		memberInfoData = MemberCenter.getMemberInfoData();
		placeOrderData = getIntent().getParcelableExtra(ActivityOperationField.PLACE_ORDER_DATA);
		if (fields != null && fields.getOrder() != null){
			payAmount = fields.getOrder().getTransAmount();
		}
	}

	protected void initImg(){
		customShowCode.addSmallImg(R.mipmap.icon_payment_longpay_s);
		customShowCode.addSmallImg(R.mipmap.icon_payment_wechat_s);
		customShowCode.addSmallImg(R.mipmap.icon_payment_ali_s);
		customShowCode.addSmallImg(R.mipmap.icon_payment_unionpay_s);
		customScan.addSmallImg(R.mipmap.icon_payment_longpay_s);
		customScan.addSmallImg(R.mipmap.icon_payment_wechat_s);
		customScan.addSmallImg(R.mipmap.icon_payment_ali_s);
		customScan.addSmallImg(R.mipmap.icon_payment_unionpay_s);
		imgs.add(customShowCode);
		imgs.add(customScan);
		imgs.add(facePay);
		imgs.add(memberCardPay);
	}

	@Override
	@SuppressLint("MissingPermission")
	protected void startThirdPartPay(){
		logger.info("start third part pay");
		//没权限到不了这
		myOrderNo = "P10" + "DIV012000099999" + payType + System.currentTimeMillis();
		Intent intent = new Intent();
		//TODO 改了APPNAME/FACENAME记得改回来
		ComponentName componetName = new ComponentName(
				PayAppCenter.getPayAppName().getPayPackageName(),
				PayAppCenter.getPayAppName().getPayActivityName());
		intent.setComponent(componetName);
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put(JianHangConstants.AMOUNT, NumUtils.extendToTwelve(payAmount));
//			jsonObject.put(JianHangConstants.AMOUNT, NumUtils.extendToTwelve(0.01));
			jsonObject.put(JianHangConstants.NEED_PRINT, "false");
			jsonObject.put(JianHangConstants.LS_ORDER_NO, myOrderNo);
//				jsonObject.put(JianHangConstants.PROJECT_TAG, "CentermSmartorder");
//				jsonObject.put(JianHangConstants.AUTH_INDEX, "0000001");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Bundle bundle = new Bundle();
		//支付类型
		String transId;
		if (payType == OrderFieldConstant.PayTypeInRequest.SHOW_CODE.getNum()){
			transId = JianHangConstants.SHOW_CODE_PAY;
		}else if (payType == OrderFieldConstant.PayTypeInRequest.SCAN_CODE.getNum()){
			transId = JianHangConstants.SCAN_CODE_PAY;
		}else {
			transId = JianHangConstants.FACE_PAY;
		}
		bundle.putString(JianHangConstants.APP_NAME, "建行收单应用");
		bundle.putString(JianHangConstants.TRANS_ID, transId);
		bundle.putString(JianHangConstants.TRANS_DATA, jsonObject.toString());
		for (String k : bundle.keySet()){
			logger.info(k + ":" + bundle.get(k) + " ");
		}
		intent.putExtras(bundle);
		try {
			startActivityForResult(intent, JIANHANG_PAY);
		}catch (ActivityNotFoundException e){
			e.printStackTrace();
			foundApp = false;
		}
	}

	/**
	 * 人脸支付结果
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 */
	protected String resultMsg;
	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		logger.info("requestCode:" + requestCode + " resultCode:" + resultCode);
		resultMsg = getString(R.string.error_str_unknow_fail);
		if (!foundApp){
			exit(R.string.dialog_pls_install_jianhang_app);
		}else if (data != null){
			showLoading(R.string.dialog_pls_wait);
			Bundle bundle = data.getExtras();
			if (bundle != null){
				String appResultCode = bundle.getString(JianHangConstants.RESULT_CODE);
				resultMsg = bundle.getString(JianHangConstants.RESULT_MSG);
				logger.info("appResultCode:" + appResultCode + " requestCode:" + requestCode);
				for (String k : bundle.keySet()){
					logger.info(k + ":" + bundle.get(k) + " ");
				}
				try {
					logger.info("paytype:" + payType);
					if ("0".equals(appResultCode)){
						doOnThirdPaySuccess(bundle);
					}else {
						showResDialog(false, resultMsg);
					}
				} catch (Exception e) {
					e.printStackTrace();
					showResDialog(false, getString(R.string.label_payment_jianhang_fail_get_authcode));
				}
			}else {
				showResDialog(false, resultMsg);
			}
		}else {
			showResDialog(false, resultMsg);
		}
	}

	@SuppressLint("MissingPermission")
	protected ConfirmOrderFields assembleCommonFields(){
		ConfirmOrderFields fields = new ConfirmOrderFields();
		ConfirmOrderFields.Transaction transaction = new ConfirmOrderFields.Transaction();
		transaction.setOrderNo(placeOrderData.getOrderNo());
		transaction.setOriginAmount(payAmount);
		transaction.setPayType(payType);
		transaction.setPlatformNo(myOrderNo);
		transaction.setTransAmount(payAmount);
		transaction.setTransFlag(OrderFieldConstant.TransFlag.ALREADY_PAY.getNum());
		transaction.setUnionNo(platformNo);
		transaction.setTerminalId("DIV012000099999");
		fields.setPayType(payType);
		fields.setOrderNo(placeOrderData.getOrderNo());
		fields.setTransaction(transaction);
		fields.setThirdPartFlag(1);
		return fields;
	}

	@SuppressLint("MissingPermission")
	public void doOnThirdPaySuccess(Bundle bundle){
		String transData = bundle.getString(JianHangConstants.TRANS_DATA);
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(transData);
			logger.info("json first:" + transData);
			platformNo = jsonObject.optString(JianHangConstants.WX_ALI_PAY_RUNION_NO, null);
			String channel = jsonObject.optString(JianHangConstants.PAY_CHANNEL, null);
			//主被扫
			payType = JianHangConstants.getPayTypeInConfirm(payType, channel);
			ConfirmOrderFields fields = assembleCommonFields();
			ConfirmOrderFields.Transaction transaction =fields.getTransaction();
//			ConfirmOrderFields fields = new ConfirmOrderFields();
//			ConfirmOrderFields.Transaction transaction = new ConfirmOrderFields.Transaction();
//			transaction.setOrderNo(placeOrderData.getOrderNo());
//			transaction.setOriginAmount(this.fields.order.transAmount);
//			transaction.setPayType(payType);
//			transaction.setPlatformNo(myOrderNo);
//			transaction.setTerminalId(bundle.getString(JianHangConstants.COUNTER_NO));
//			transaction.setTransAmount(this.fields.order.transAmount);
//			transaction.setTransFlag(OrderFieldConstant.TransFlag.ALREADY_PAY.getNum());
//			transaction.setUnionNo(platformNo);
			transaction.setTerminalId("DIV012000099999");
			transaction.setCardNo(jsonObject.optString(JianHangConstants.CARD_NO, null));
			transaction.setBatchNo(jsonObject.optString(JianHangConstants.BATCH_NO, null));
			transaction.setTraceNo(jsonObject.optString(JianHangConstants.TRACE_NO, null));
			transaction.setRefNo(jsonObject.optString(JianHangConstants.REF_NO, null));
			transaction.setTransDate(jsonObject.optString(JianHangConstants.TRANS_DATE, null));
			transaction.setTransTime(jsonObject.optString(JianHangConstants.TRANS_TIME, null));
			transaction.setRemark(jsonObject.optString(JianHangConstants.TERMINAL_ID, null));
//			fields.setPayType(payType);
//			fields.setOrderNo(placeOrderData.getOrderNo());
			fields.setTransaction(transaction);
			fields.setThirdPartFlag(1);
			getPresenter().placeOrderConfirm(fields);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		setBtnState(v);
		switch (v.getId()){
			case R.id.customShowCode:
				CustomShowCodePay();
				break;
			case R.id.customScanCode:
				showCodePay();
				break;
			case R.id.facePay:
				startFacePay();
				break;
		}
	}
}
