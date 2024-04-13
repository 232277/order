package com.dyc.order.cashier.view;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.dyc.order.cashier.R;
import com.dyc.order.cashier.function.CountDown;
import com.dyc.order.cashier.base.MyApplication;
import com.dyc.order.cashier.mvp.fragment.base.BaseDialogContentFragment;
import com.dyc.order.cashier.mvp.activity.base.BaseCountDownActivity;
import com.dyc.administrator.toollibrary.utils.MLogger;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

/**
 * func:
 * author:丁语成 on 2020/3/26 10:24
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class CountDownDialog extends DialogFragment {
	public static int DIALOG_COUNT_DOWN_TIME = 30;
	private MLogger logger = MLogger.getLogger(this.getClass());
	protected CountDown countDown;
	protected TextView countDownStr;
	protected TextView title;
	protected ImageView close;
	private ConstraintLayout rootLayout;
	private FragmentManager manager;
	private BaseDialogContentFragment fragment;
	protected View.OnClickListener onCloseClickListener;
	protected CountDown.OnCountDownListener countDownListener;
	protected DialogInterface.OnDismissListener dismissListener;
	protected Activity activity;

	private int backGround;
	private CharSequence titleStr;
	private boolean countFinish = false;

	public CountDownDialog(){}

	public CountDownDialog(BaseDialogContentFragment content, Activity activity){
		this.fragment = content;
		this.activity = activity;
		countDown = new CountDown(
				DIALOG_COUNT_DOWN_TIME,
				BaseCountDownActivity.BASE_COUNT_DELAY,
				BaseCountDownActivity.BASE_COUNT_PERIOD);
	}

	public static CountDownDialog newInstance(BaseDialogContentFragment content, Activity activity) {
		Bundle args = new Bundle();
		CountDownDialog fragment = new CountDownDialog(content, activity);
		fragment.setArguments(args);
		return fragment;
	}

	public void setCloseOnClick(View.OnClickListener listener){
		onCloseClickListener = listener;
		if (close != null && onCloseClickListener != null){
			close.setOnClickListener(listener);
		}
	}

	public void setDismissListener(DialogInterface.OnDismissListener dismissListener) {
		this.dismissListener = dismissListener;
	}

	public boolean isCountFinish() {
		return countFinish;
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.dialog_base_count_down_layout, container);
		countDownStr = v.findViewById(R.id.countDownStr);
		rootLayout = v.findViewById(R.id.rootLayout);
		title = v.findViewById(R.id.titleBar);
		close = v.findViewById(R.id.close);
		close.setOnClickListener(mv -> dismiss());
		setCloseOnClick(onCloseClickListener);
		setTitle(titleStr);
		setBackGround(backGround);
		countFinish = false;
		return v;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		countDown.setListener(new CountDown.OnCountDownListener() {
			@Override
			public void onCountDown(long countTime) {
				CountDownDialog.this.onCountDown(countTime);
			}

			@Override
			public void onCountFinish() {
				CountDownDialog.this.onCountFinish();
				dismiss();
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		logger.info("isAdded:" + isAdded());
		if(isAdded()){
			manager = getChildFragmentManager();
		}
		if (fragment != null){
			setContentLayout(fragment);
		}
		setWindow();
		countDown.restartCountDown();
	}

	private void setWindow(){
		Window window = getDialog().getWindow();
		WindowManager.LayoutParams para = window.getAttributes();
		Display display = window.getWindowManager().getDefaultDisplay();
		Point point = new Point();
		display.getRealSize(point);
		para.width = point.x/5 * 4;
		para.height = WindowManager.LayoutParams.WRAP_CONTENT;
		window.setAttributes(para);
	}

	public void setBackGround(int backGround){
		this.backGround = backGround;
		if (rootLayout != null && backGround > 0){
			rootLayout.setBackgroundResource(backGround);
		}
	}

	public void setTitle(CharSequence str){
		titleStr = str;
		if (title != null && titleStr != null){
			title.setText(str);
		}
	}

	public void setContentLayout(BaseDialogContentFragment fragment){
		if (manager != null){
			logger.info("replace content");
			manager.beginTransaction().replace(R.id.contentLayout, fragment).commit();
			fragment.setDialog(this);
		}else {
			logger.info("manager null");
			this.fragment = fragment;
		}
	}

	protected void onCountDown(long countTime){
		SpannableString time = new SpannableString(String.format(Locale.getDefault(), "(%ds)", countTime));
		time.setSpan(new ForegroundColorSpan(
				ResourcesCompat.getColor(MyApplication.getContext().getResources(),
								R.color.normal_item_text_color_red, null)),
				1, time.length()-2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		activity.runOnUiThread(()->countDownStr.setText(time));
		if (countDownListener != null){
			countDownListener.onCountDown(countTime);
		}
	}

	protected void onCountFinish(){
		if (countDownListener != null){
			countDownListener.onCountFinish();
		}
		countFinish = true;
		dismiss();
	}

	public void restartCountDown(){
		logger.info("restart count down");
		countDown.restartCountDown();
	}

	public void restartCountDown(long time){
		countDown.restartCountDown(time);
	}

	@Override
	public void dismiss() {
		super.dismiss();
	}

	@Override
	public void onDismiss(@NonNull DialogInterface dialog) {
		super.onDismiss(dialog);
		logger.info("dismiss");
		countDown.pauseCountDown();
		if (dismissListener != null){
			dismissListener.onDismiss(getDialog());
		}
	}
}
