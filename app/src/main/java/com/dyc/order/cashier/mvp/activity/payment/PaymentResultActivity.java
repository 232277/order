package com.dyc.order.cashier.mvp.activity.payment;

import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.dyc.order.cashier.R;
import com.dyc.order.cashier.constant.MessageField;
import com.dyc.order.cashier.data.local.ShoppingCart;
import com.dyc.order.cashier.mvp.activity.other.ShoppingCartActivity;
import com.dyc.order.cashier.mvp.activity.base.BaseCountDownActivity;
import com.dyc.administrator.toollibrary.utils.NumUtils;
import com.dyc.simplemvplibrary.Model;
import com.dyc.simplemvplibrary.Presenter;

import java.util.Locale;

public class PaymentResultActivity extends BaseCountDownActivity {
	public static long RESULT_COUNT_DOWN_TIME = 5;
	private TextView countDownStr;
	private TextView amount;

	@Override
	public int getLayoutId() {
		return R.layout.activity_payment_result;
	}

	@Override
	public void onInitView(View view) {
		countDownStr = view.findViewById(R.id.countDownStr);
		amount = view.findViewById(R.id.amount);
		findViewById(R.id.gotIt).setOnClickListener(v -> {
			exit();
			finish();
		});
		Intent intent = getIntent();
		if(intent != null){
			double amount = intent.getDoubleExtra(MessageField.AMOUNT, -1);
			this.amount.setText(String.format("%s%s",
					getString(R.string.toollibrary_label_concurrency_sign),
					NumUtils.remain2NumWithoutZero(amount)));
		}
		startCountDown(RESULT_COUNT_DOWN_TIME,
				BaseCountDownActivity.BASE_COUNT_DELAY, BaseCountDownActivity.BASE_COUNT_PERIOD);
		showNormalActionBar(getString(R.string.label_top_bar_pay));
	}

	@Override
	protected void onCountDown(long countTime) {
		super.onCountDown(countTime);
		SpannableString spannableString = new SpannableString(String.format(Locale.getDefault(),
				"(%ds)", countTime));
		spannableString.setSpan(new ForegroundColorSpan(
						getResources().getColor(R.color.normal_item_text_color_red, null)),
				1, spannableString.toString().indexOf("s"), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		runOnUiThread(()->{ countDownStr.setText(spannableString); });
	}

	@Override
	protected void onCountFinish() {
		super.onCountFinish();
		exit();
	}

	private void exit(){
		ShoppingCart.clearCartNow();
		ShoppingCart.setIsMember(false);
		finishActivity(ShoppingCartActivity.class);
		finishActivity(BillMVPActivity.class);
		finishActivity(PaymentMVPActivity.class);
		finishActivity(JiaoHangPaymentMVPActivity.class);
		finishActivity(JianHangPaymentMVPActivity.class);
	}

	@Override
	public Model initM() {
		return null;
	}

	@Override
	public Presenter initP() {
		return null;
	}
}
