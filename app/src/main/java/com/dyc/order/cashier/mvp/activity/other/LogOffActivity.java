package com.dyc.order.cashier.mvp.activity.other;

import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dyc.order.cashier.R;
import com.dyc.order.cashier.constant.MessageField;
import com.dyc.order.cashier.data.local.DataCache;
import com.dyc.order.cashier.mvp.activity.base.BaseCountDownActivity;
import com.dyc.simplemvplibrary.Model;
import com.dyc.simplemvplibrary.Presenter;

import java.util.Locale;

public class LogOffActivity extends BaseCountDownActivity {
	private TextView countDownStr;
	private EditText account;
	private EditText password;
	private Button confirm;

	@Override
	public int getLayoutId() {
		return R.layout.activity_log_off;
	}

	@Override
	public void onInitView(View view) {
		findViewById(R.id.leftView).setOnClickListener(v -> finish());
		countDownStr = findViewById(R.id.countDownStr);
		account = findViewById(R.id.account);
		password = findViewById(R.id.pwd);
		confirm = findViewById(R.id.confirm);
		account.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
		account.setText(DataCache.getLoginInfoData().getPhone());
		confirm.setOnClickListener(v -> {
			if (!DataCache.get(MessageField.PHONE_NUMBER).equals(account.getText().toString())
			|| !DataCache.get(MessageField.PASSWORD).equals(password.getText().toString())){
				showToast(R.string.tip_wrong_account_or_pwd);
			}else {
				loginTimeOut(-1);
			}
		});
		startCountDown(BASE_COUNT_DOWN_TIMES, BASE_COUNT_DELAY, BASE_COUNT_PERIOD);
//		showNormalActionBar(getString(R.string.label_top_bar_exit));
		showActionBar(false);
	}

	private void setCountDownStr(long countTime){
		SpannableString time = new SpannableString(String.format(Locale.getDefault(), "(%ds)", countTime));
		time.setSpan(new ForegroundColorSpan(
						getResources().getColor(R.color.normal_item_text_color_red, null)),
				1, time.length()-2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		runOnUiThread(()->countDownStr.setText(time));
	}

	@Override
	protected void onCountDown(long countTime) {
		super.onCountDown(countTime);
		setCountDownStr(countTime);
	}

	@Override
	public void restartCountDown() {
		super.restartCountDown();
		setCountDownStr(getTotalCount());
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
