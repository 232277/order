package com.dyc.order.cashier.mvp.activity.recharge;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.dyc.order.cashier.constant.ActivityOperationField;
import com.dyc.order.cashier.contact.RechargeScanContact;
import com.dyc.order.cashier.data.fields.PaymentRequestFields;
import com.dyc.order.cashier.data.local.MemberCenter;
import com.dyc.order.cashier.data.response.MemberInfoData;
import com.dyc.order.cashier.view.NormalTopBar;
import com.dyc.administrator.toollibrary.utils.NumUtils;
import com.dyc.order.cashier.R;
import com.dyc.order.cashier.mvp.activity.base.BaseOutScanActivity;
import com.centerm.dev.barcode.BarCodeManager;
import com.centerm.dev.barcode.ScanParamter;
import com.centerm.dev.util.HexUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/**
 * func:
 * author:丁语成 on 2020/6/28 10:27
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class RechargeScanActivity extends BaseOutScanActivity<
        RechargeScanContact.RechargeAndScanModel,
		RechargeScanContact.RechargeAndScanView,
		RechargeScanContact.RechargeAndScanPresent> implements RechargeScanContact.RechargeAndScanView {
	private final static String PAY_CODE_PREFIX = "paycode-";

	private NormalTopBar topBar;
	private TextView transAmount;

	private PaymentRequestFields fields;
	private int memberId;
	private String code;

	private BarCodeManager barCodeManager;

	@Override
	public int getLayoutId() {
		return R.layout.activity_recharge_scan;
	}

	@Override
	public void onInitView(View view) {
		showActionBar(false);
		topBar = findViewById(R.id.normalTopBar);
		transAmount = findViewById(R.id.transAmount);
		topBar.setLeftViewListener(v -> finish());
		startCountDown();
		initData();
	}

	private void initData(){
		fields = getIntent().getParcelableExtra(ActivityOperationField.PAYMENT_REQUEST);
		if (fields == null){
			exit(R.string.label_member_store_wrong_pay_fields);
		}else {
			transAmount.setText(getString(R.string.label_member_store_trans_amount,
					NumUtils.remain2NumWithoutZero(fields.getOrder().getTransAmount())));
			doScan();
		}
	}

	private void doScan(){
		try {
			if (barCodeManager != null){
				barCodeManager.releaseCamera();
			}
			barCodeManager = BarCodeManager.getManager(this);
			ScanParamter paramter = new ScanParamter();
//			paramter.setTimeout((int)BaseCountDownActivity.BASE_COUNT_DOWN_TIMES);
			paramter.setShowPreview(false);
//			paramter.setPreviewSize(new Point(640, 480));
			barCodeManager.releaseCamera();
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					barCodeManager.scanQRCode(paramter, new BarCodeManager.ScanCallback() {
						@Override
						public void onCaptured(byte[] bytes, int i) {
							if (bytes != null){
								String res = HexUtil.charBytesToString(bytes);
								scanResHandle(res);
							}else {
								logger.info("取得的码数组为空");
								showToast(R.string.label_member_store_wrong_code_type);
							}
						}

						@Override
						public void onFailed(int i) {
							//超时重启
							if (i == -3){
								doScan();
							}
							logger.info("扫码失败:" + i);
						}
					});
					logger.info("启动扫码");
				}
			}, 500);
		}catch (Exception e){
			e.printStackTrace();
			showToast(R.string.tip_scan_exception);
		}
	}

	@Override
	public void onInputFinish(String keySequence) {
		scanResHandle(keySequence);
//		getPresenter().placeOrderConfirm(fields);
	}

	private void setRes(String keySequence){
		getIntent().putExtra(ActivityOperationField.MEMBER_STORE_CODE, keySequence);
		setResult(RESULT_OK, getIntent());
		finish();
	}

	private void scanResHandle(String keySequence){
		logger.info("scan res:" + keySequence);
		if (isValidCode(keySequence)){
			code = keySequence;
			runOnUiThread(()-> {
				showLoading(R.string.dialog_pls_wait);
			});
			getPresenter().getMemberInfoById(memberId);
		}else {
			logger.info("无效码类型");
			showToast(R.string.label_member_store_wrong_code_type);
			doScan();
		}
	}

	@Override
	public void getMemberByIdSuccess(MemberInfoData memberInfoData) {
		hideDialog();
		if (memberInfoData != null && (memberInfoData.getId() == MemberCenter.getMemberInfoData().getId().intValue())){
			setRes(code);
		}else {
			showToast(R.string.label_member_store_code_do_not_match_member);
			logger.info("储值码与会员不匹配");
			doScan();
		}
	}

	@Override
	public void getMemberByIdFail(Throwable throwable) {
		hideDialog();
		showToast(getString(R.string.label_member_store_get_member_by_code_fail));
		logger.info("取会员信息失败");
		doScan();
	}

	private boolean isValidCode(String keySequence){
		boolean res;
		if (TextUtils.isEmpty(keySequence)){
			res = false;
		}else {
			try {
				if (keySequence.startsWith(PAY_CODE_PREFIX)){
					StringBuilder sb = new StringBuilder(keySequence);
					sb.delete(0, sb.indexOf("-") + 1);
					memberId = Integer.parseInt(sb.substring(0, sb.indexOf("-")));
					//解码没错就行了，实际上用不掉这个时间
					Date date = new SimpleDateFormat(getString(R.string.toolibrary_date_format_year_month_day_hour_second_mini_seconds_no_separate), Locale.getDefault())
							.parse(sb.substring(sb.lastIndexOf("-") + 1));
					res = true;
				}else {
					res = false;
				}
			}catch (Exception e){
				e.printStackTrace();
				res = false;
			}
		}

		return res;
	}

	@Override
	protected void onCountDown(long countTime) {
		super.onCountDown(countTime);
		runOnUiThread(()->topBar.setCountDownStr(getFormatCountDownStr(countTime)));
	}

	@Override
	public RechargeScanContact.RechargeAndScanModel initM() {
		return new RechargeScanContact.RechargeAndScanModel() {
		};
	}

	@Override
	public RechargeScanContact.RechargeAndScanPresent initP() {
		return new RechargeScanContact.RechargeAndScanPresent() {
		};
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (barCodeManager != null){
			try {
				barCodeManager.releaseCamera();
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}
}
