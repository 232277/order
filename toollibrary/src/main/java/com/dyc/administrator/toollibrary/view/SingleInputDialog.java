package com.dyc.administrator.toollibrary.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.centerm.administrator.toollibrary.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

/**
 * func:
 * author:丁语成 on 2020/3/2 14:33
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class SingleInputDialog extends AlertDialog {
	private Button cancel;
	private Button confirm;
	private FirstFixEditText editText;
	private TextView title;

	public SingleInputDialog(@NonNull Context context) {
		super(context);
	}

	public SingleInputDialog(@NonNull Context context, int themeResId) {
		super(context, themeResId);
	}

	public SingleInputDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.toollibrary_single_input_dialog_layout);

		title = findViewById(R.id.title);
		editText = findViewById(R.id.editText);
		cancel = findViewById(R.id.cancel);
		cancel.setOnClickListener(v -> dismiss());
		confirm = findViewById(R.id.confirm);
		setMyWindow();
		showSoftInputFromWindow(editText);
	}


	public void showSoftInputFromWindow(EditText editText){
		editText.setFocusable(true);
		editText.setFocusableInTouchMode(true);
		editText.requestFocus();
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
	}

	public void setCancelListener(View.OnClickListener listener){
		cancel.setOnClickListener(listener);
	}

	public void setConfirmListener(View.OnClickListener listener){
		confirm.setOnClickListener(listener);
	}

	public void setTitleStr(String str){
		title.setText(str);
	}

	public void setHintText(String str){
		editText.setHint(str);
	}

	public void setFirstFixStr(String str){
		editText.setFixedText(str);
	}

	public void setEditTextStr(String str){
		editText.setText(str);
	}

	public String getEditTextStr(){
		return editText.getText().toString();
	}

	public void setInputType(int type){
		editText.setInputType(type);
	}

	private void setMyWindow(){
		Window window = getWindow();
		WindowManager.LayoutParams params = window.getAttributes();
		params.width = WindowManager.LayoutParams.WRAP_CONTENT;
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		params.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN;
		params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
		params.dimAmount = 0.5f;
		window.setAttributes(params);
		window.setBackgroundDrawableResource(android.R.color.transparent);
	}
}
