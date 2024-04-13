package com.dyc.order.cashier.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.dyc.order.cashier.R;

import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;

/**
 * func:
 * author:丁语成 on 2020/4/20 11:43
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class MemberSignOnDialog extends Dialog {
	private ImageView img;
	private TextView text;
	private Timer timer;

	public MemberSignOnDialog(@NonNull Context context) {
		super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_member_sign_on);
		img = findViewById(R.id.img);
		text = findViewById(R.id.text);
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (isShowing()){
					dismiss();
				}
			}
		}, 2000);
	}

	public void setText(CharSequence str){
		if (text != null){
			text.setText(str);
		}
	}

	public void setImg(int id){
		if (img != null){
			img.setImageResource(id);
		}
	}
}
