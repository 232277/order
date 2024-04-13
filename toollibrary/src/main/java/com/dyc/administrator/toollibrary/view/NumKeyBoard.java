package com.dyc.administrator.toollibrary.view;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;

import com.centerm.administrator.toollibrary.R;

/**
 * func:
 * author:丁语成 on 2020/4/9 8:53
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class NumKeyBoard extends AlertDialog {
	private NumberPadView numberPadView;
	private NumberPadView.OnNumberInputListener listener;

	public NumKeyBoard(Context context, NumberPadView.OnNumberInputListener listener) {
		super(context);
		this.listener = listener;
	}

	public NumKeyBoard(Context context, boolean cancelable, OnCancelListener cancelListener, NumberPadView.OnNumberInputListener listener) {
		super(context, cancelable, cancelListener);
		this.listener = listener;
	}

	public NumKeyBoard(Context context, int themeResId, NumberPadView.OnNumberInputListener listener) {
		super(context, themeResId);
		this.listener = listener;
	}

	public void setListener(NumberPadView.OnNumberInputListener listener) {
		this.listener = listener;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
		setContentView(R.layout.my_num_keyboard);
		numberPadView = findViewById(R.id.numPad);
		if (listener != null){
			numberPadView.setOnNumInputListener(listener);
		}
		setWindow();
		startAnimation(true);
	}

	private void setWindow(){
		Window window = getWindow();
		window.setBackgroundDrawableResource(android.R.color.transparent);
		window.setGravity(Gravity.BOTTOM);
//		window.setWindowAnimations(R.style.toollibraryBottomDialog);
		window.getDecorView().setPadding(0, 0, 0, 0);
		//设置对话框大小
		window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

		WindowManager.LayoutParams lp=this.getWindow().getAttributes();
		lp.dimAmount=0.5f;
//		lp.y = 96;
		this.getWindow().setAttributes(lp);
		this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
	}

	private void startAnimation(boolean out){
		if (out){
			numberPadView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.toollibrary_bottom_dialog_out));
		}else {
			numberPadView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.toollibrary_bottom_dialog_anim_in));
		}
	}

	@Override
	public void dismiss() {
		super.dismiss();
		startAnimation(false);
	}
}
