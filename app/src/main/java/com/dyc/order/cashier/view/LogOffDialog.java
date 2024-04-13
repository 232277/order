package com.dyc.order.cashier.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.dyc.order.cashier.R;
import com.dyc.order.cashier.mvp.activity.other.LogOffActivity;
import com.dyc.administrator.toollibrary.utils.DipUtil;

/**
 * func:
 * author:丁语成 on 2020/4/9 11:42
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class LogOffDialog extends Dialog {
	private Activity activity;

	public LogOffDialog(Context context, Activity activity) {
		super(context);
		this.activity = activity;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setWindow();
		setContentView(R.layout.dialog_log_off);
		findViewById(R.id.rectangle).setOnClickListener(v -> {
			dismiss();
			activity.startActivity(new Intent(activity, LogOffActivity.class));
		});
	}

	private void setWindow(){
		Window window = getWindow();
		window.setBackgroundDrawableResource(R.color.transport);
		window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
		WindowManager.LayoutParams params = window.getAttributes();
		window.setGravity(Gravity.START | Gravity.TOP);
		params.x = DipUtil.dip2px(activity, 8);
		params.y = DipUtil.dip2px(activity, 47);
		window.setAttributes(params);
		window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
	}
}
