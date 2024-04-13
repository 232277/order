package com.dyc.order.cashier.mvp.activity.recharge;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dyc.order.cashier.R;
import com.dyc.order.cashier.constant.ActivityOperationField;
import com.dyc.order.cashier.contact.RechargeUserScanContact;
import com.dyc.order.cashier.data.fields.PaymentRequestFields;
import com.dyc.order.cashier.data.local.MemberCenter;
import com.dyc.order.cashier.data.response.MemberInfoData;
import com.dyc.order.cashier.view.NormalTopBar;
import com.dyc.order.cashier.mvp.activity.base.BaseCountDownActivity;

import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

/**
 * func:
 * author:丁语成 on 2020/7/2 11:08
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class RechargeUserScanActivity extends BaseCountDownActivity<
        RechargeUserScanContact.RechargeUserScanModel,
		RechargeUserScanContact.RechargeUserScanView,
		RechargeUserScanContact.RechargeUserScanPresent> implements RechargeUserScanContact.RechargeUserScanView {
	private NormalTopBar topBar;
	private TextView notice;
	private ImageView codeView;
	private Button toCustomShowCode;

	private String code;
	private PaymentRequestFields fields;
	private MemberInfoData memberInfoData;

	@Override
	public int getLayoutId() {
		return R.layout.activity_recharge_user_scan;
	}

	@Override
	public void onInitView(View view) {
		topBar = findViewById(R.id.topBar);
		notice = findViewById(R.id.notice);
		codeView = findViewById(R.id.codeView);
		toCustomShowCode = findViewById(R.id.toCustomShowCode);
		initData();
	}

	private void initData(){
		fields = getIntent().getParcelableExtra(ActivityOperationField.PAYMENT_REQUEST);
		if (fields == null){
			exit(R.string.label_member_store_wrong_pay_fields);
		}
		memberInfoData = MemberCenter.getMemberInfoData();
		if (memberInfoData == null){
			exit(R.string.label_member_store_not_a_member);
		}
	}

	@Override
	public void onGetStorePayCodeSuccess(String code) {
		this.code = code;
		codeView.setImageBitmap(QRCodeEncoder.syncEncodeQRCode(code, 200));
		getPresenter().queryStorePayCode(code);
	}

	@Override
	public void onGetStorePayCodeFail(Throwable throwable) {
		hideDialog();
		showToast(R.string.label_member_store_get_member_by_code_fail);
	}

	@Override
	public void onStorePaySuccess(String code) {

	}

	@Override
	public void onStorePayFail(String msg) {

	}

	@Override
	public void onQueryStorePayFail(Throwable throwable) {

	}

	@Override
	public void doAfterInitView(View v) {
		super.doAfterInitView(v);
		showLoading(R.string.dialog_pls_wait);
		getPresenter().getStorePayCode(memberInfoData);
	}

	@Override
	public RechargeUserScanContact.RechargeUserScanModel initM() {
		return new RechargeUserScanContact.RechargeUserScanModel(){};
	}

	@Override
	public RechargeUserScanContact.RechargeUserScanPresent initP() {
		return new RechargeUserScanContact.RechargeUserScanPresent(){};
	}

}
