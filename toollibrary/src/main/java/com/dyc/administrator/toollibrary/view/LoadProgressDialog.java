package com.dyc.administrator.toollibrary.view;

/**
 * func:
 * author:丁语成 on 2020/2/11 14:51
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.centerm.administrator.toollibrary.R;


public class LoadProgressDialog extends Dialog {
	private CharSequence message;
	private boolean canCancel;
	private TextView textView;
	private ProgressBar progressBar;
	private Context context;

	public LoadProgressDialog(Context context, CharSequence message) {
		this(context, message, false);
	}

	public LoadProgressDialog(Context context, CharSequence message, boolean canCancel) {
		super(context, R.style.Tool_Library_LoadProgressDialog);
		this.message = message;
		this.canCancel = canCancel;
		this.context = context.getApplicationContext();
	}

	public void setDrawable(int animId){
		setDrawable(context.getDrawable(animId));
	}

	public void setDrawable(Drawable drawable){
		Message msg = new Message();
		msg.what = 1;
		msg.obj = drawable;
		handler.sendMessage(msg);
	}

	public void setMessage(CharSequence message) {
		Message msg = new Message();
		msg.what = 0;
		msg.obj = message;
		this.message = message;
		handler.sendMessage(msg);
	}

	private Handler handler = new Handler((Message msg)->{
		if (msg.what == 0){
			textView.setText((CharSequence)msg.obj);
		}else if (msg.what == 1){
			progressBar.setIndeterminateDrawable((Drawable)msg.obj);
		}
		return false;
	});

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.toollibrary_progress_dialog_layout);
		textView = findViewById(R.id.message);
		progressBar = findViewById(R.id.animation);
        setCancelable(canCancel);
		setCanceledOnTouchOutside(canCancel);
		textView.setText(message);
	}
}

