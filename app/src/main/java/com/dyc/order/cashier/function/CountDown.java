package com.dyc.order.cashier.function;

import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.dyc.administrator.toollibrary.utils.MLogger;
import com.dyc.order.cashier.R;
import com.dyc.order.cashier.base.MyApplication;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/**
 * func:
 * author:丁语成 on 2020/3/25 15:25
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class CountDown {
	private MLogger logger = MLogger.getLogger(this.getClass());
	private long totalCount;
	private long countTime;
	private long delay;
	private long period;
	private Timer timer;
	private OnCountDownListener listener;
	
	public CountDown(){}

	public CountDown(long totalCount, long delay, long period) {
		this(totalCount, delay, period, null);
	}

	public CountDown(long totalCount, long delay, long period, OnCountDownListener listener) {
		this.totalCount = totalCount;
		this.countTime = totalCount;
		this.delay = delay;
		this.period = period;
		this.listener = listener;
	}

	public static SpannableString getFormatCountDownStr(long countTime){
		SpannableString time = new SpannableString(String.format(Locale.getDefault(), "(%ds)", countTime));
		time.setSpan(new ForegroundColorSpan(
						MyApplication.getContext().getResources().getColor(R.color.normal_item_text_color_red, null)),
				1, time.length()-2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		return time;
	}

	public void startCountDown(){
		startCountDown(countTime, delay, period);
	}

	public void startCountDown(long countTime, long delay, long period){
		totalCount = countTime;
		this.countTime = countTime;
		this.delay = delay;
		this.period = period;
		timer = new Timer();

		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				onCountDown(--CountDown.this.countTime);
				if (CountDown.this.countTime <= 0){
					onCountFinish();
				}
			}
		}, delay, period);
	}

	public void pauseCountDown(){
		if (timer != null){
			handler.removeCallbacks(runnable);
			timer.cancel();
		}
	}

	private Runnable runnable = new Runnable() {
		@Override
		public void run() {
			handler.sendEmptyMessage(1);
		}
	};

	private Handler handler = new Handler(msg -> {
		restartCountDown();
		return false;
	});

	public void restartCountDown(long delay){
		handler.removeCallbacks(runnable);
		handler.postDelayed(runnable, delay);
	}

	public void restartCountDown(){
		countTime = totalCount;
		pauseCountDown();
		startCountDown();
	}

	public void onCountDown(long countTime){
		if (listener != null){
			listener.onCountDown(countTime);
		}
	}

	public void onCountFinish(){
		countTime = totalCount;
		if (listener != null){
			listener.onCountFinish();
		}
	}
	
	public interface OnCountDownListener{
		void onCountDown(long countTime);
		void onCountFinish();
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public long getCountTime() {
		return countTime;
	}

	public void setCountTime(long countTime) {
		this.countTime = countTime;
	}

	public long getDelay() {
		return delay;
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}

	public long getPeriod() {
		return period;
	}

	public void setPeriod(long period) {
		this.period = period;
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public OnCountDownListener getListener() {
		return listener;
	}

	public void setListener(OnCountDownListener listener) {
		this.listener = listener;
	}
}
