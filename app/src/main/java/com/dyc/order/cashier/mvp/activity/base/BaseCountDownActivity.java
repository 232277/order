package com.dyc.order.cashier.mvp.activity.base;

import android.text.SpannableString;

import com.dyc.order.cashier.base.iml.BaseContact;
import com.dyc.order.cashier.function.CountDown;

/**
 * func:
 * author:丁语成 on 2020/3/20 10:52
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public abstract class BaseCountDownActivity <
		M extends BaseContact.BaseModel,
		V extends BaseContact.BaseView,
		P extends BaseContact.BasePresent
		> extends BaseDesktopActivity<M,V,P> {
	public static long BASE_COUNT_DOWN_TIMES = 120;
	public static long BASE_COUNT_DELAY = 0;
	public static long BASE_COUNT_PERIOD = 1000;
	public static long BASE_RECOUNT_DELAY = 1000;
	protected int resumeTimes = 0;
	protected CountDown baseCountDown;

	protected void startCountDown(){
		if (baseCountDown != null){
			startCountDown(baseCountDown.getCountTime(), baseCountDown.getDelay(), baseCountDown.getPeriod());
		}else {
			startCountDown(BASE_COUNT_DOWN_TIMES, BASE_COUNT_DELAY, BASE_COUNT_PERIOD);
		}
	}

	protected void startCountDown(long countTime, long delay, long period){
		baseCountDown = new CountDown(countTime, delay, period, new CountDown.OnCountDownListener() {
			@Override
			public void onCountDown(long countTime) {
				BaseCountDownActivity.this.onCountDown(countTime);
			}

			@Override
			public void onCountFinish() {
				BaseCountDownActivity.this.onCountFinish();
			}
		});
		baseCountDown.startCountDown(countTime, delay, period);
	}

	public void pauseCountDown(){
		if (baseCountDown != null){
			baseCountDown.pauseCountDown();
		}
	}

	public void restartCountDown(){
		if (baseCountDown != null) {
			baseCountDown.restartCountDown(BASE_RECOUNT_DELAY);
		}
	}

	protected void onCountDown(long countTime){
	}

	protected void onCountFinish(){
		finish();
	}

	@Override
	protected void onPause() {
		super.onPause();
		pauseCountDown();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (resumeTimes != 0){
			restartCountDown();
		}
		++resumeTimes;
	}

	@Override
	public void onUserInteraction() {
		super.onUserInteraction();
		pauseCountDown();
		restartCountDown();
	}

	protected void exit(int msgId){
		hideDialog();
		onErrorAndFinish("1", getString(msgId));
//		Timer timer = new Timer();
//		timer.schedule(new TimerTask() {
//			@Override
//			public void run() {
//				finish();
//			}
//		}, 2200);
	}

	public long getTotalCount() {
		if (baseCountDown != null){
			return baseCountDown.getTotalCount();
		}
		return -1;
	}

	public long getCountTime() {
		if (baseCountDown != null) {
			return baseCountDown.getCountTime();
		}
		return -1;
	}

	public long getDelay() {
		if (baseCountDown != null) {
			return baseCountDown.getDelay();
		}
		return -1;
	}

	public long getPeriod() {
		if (baseCountDown != null) {
			return baseCountDown.getPeriod();
		}
		return -1;
	}

	public static SpannableString getFormatCountDownStr(long countTime){
		return CountDown.getFormatCountDownStr(countTime);
	}


	public CountDown getBaseCountDown() {
		return baseCountDown;
	}

	public void setBaseCountDown(CountDown countDown) {
		this.baseCountDown = countDown;
	}
}
