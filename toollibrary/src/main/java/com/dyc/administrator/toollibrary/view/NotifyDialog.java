package com.dyc.administrator.toollibrary.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.centerm.administrator.toollibrary.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * func:
 * author:丁语成 on 2020/2/22 14:34
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class NotifyDialog extends Dialog {
	private CharSequence message;
	private int imgId;
	private TextView textView;
	private ImageView img;
	private Context context;
	private long existTime;

	public NotifyDialog(Context context, CharSequence message, int imgId) {
		this(context, message, imgId, 2);
	}

	public NotifyDialog(Context context, CharSequence message, int imgId, long existTime) {
		super(context, R.style.Tool_Library_LoadProgressDialog);
		this.message = message;
		this.imgId = imgId;
		this.existTime = existTime;
		this.context = context.getApplicationContext();
	}

	public void setDrawable(int animId){
		if (animId > 0){
			setDrawable(context.getDrawable(animId));
		}else {
			setDrawable(null);
			Log.d("NotifyDialog", "setDrawable: null");
		}
	}

	public void setDrawable(Drawable drawable){
		Message msg = new Message();
		msg.what = 1;
		msg.obj = drawable;
		handler.sendMessage(msg);
	}

	public void setMessage(CharSequence message) {
		if (message != null){
			Message msg = new Message();
			msg.what = 0;
			msg.obj = message;
			this.message = message;
			handler.sendMessage(msg);
		}
	}

	private Handler handler = new Handler((Message msg)->{
		if (msg.what == 0){
			textView.setText((CharSequence) msg.obj);
		}else if (msg.what == 1){
			if (msg.obj != null){
				img.setImageDrawable((Drawable)msg.obj);
				img.setVisibility(View.VISIBLE);
			}else {
				img.setVisibility(View.GONE);
			}
		}else {
			try {
				dismiss();
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		return false;
	});

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.toollibrary_notify_dialog_layout);
		textView = findViewById(R.id.text);
		img = findViewById(R.id.img);
		setMessage(message);
		setDrawable(imgId);
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				Message msg = new Message();
				msg.what = 2;
				handler.sendMessage(msg);
			}
		}, existTime * 1000);
	}
}

