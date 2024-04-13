package com.dyc.administrator.toollibrary.view;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.centerm.administrator.toollibrary.R;
import com.dyc.administrator.toollibrary.utils.DipUtil;

import java.util.HashMap;
import java.util.Map;

import androidx.core.content.ContextCompat;

/**
 * func:
 * author:丁语成 on 2020/3/10 11:16
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class BottomListItemDialog extends AlertDialog {
	private LinearLayout rootLayout;
	private Map<String, TextView> items = new HashMap<>();

	public BottomListItemDialog(Context context) {
		super(context);
	}

	public BottomListItemDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	public BottomListItemDialog(Context context, int themeResId) {
		super(context, themeResId);
	}

	public void addItems(int textSize, String text, int textColor, View.OnClickListener listener){
		TextView item = new TextView(getContext());
		rootLayout.addView(item);
		item.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
		item.setText(text);
		item.setTextColor(ContextCompat.getColorStateList(getContext(), textColor));
		ViewGroup.LayoutParams lp = item.getLayoutParams();
		float screenHeight = getContext().getResources().getDisplayMetrics().heightPixels;
		lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
		lp.height = (int)screenHeight / 10;
		item.setGravity(Gravity.CENTER);
		item.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
		item.setLayoutParams(lp);
		item.setBackgroundColor(getContext().getResources().getColor(R.color.toollibrary_white));
		item.setOnClickListener(listener);

		View divider = new View(getContext());
		rootLayout.addView(divider);
		divider.setBackgroundColor(getContext().getResources().getColor(R.color.toollibrary_divider_gray));
		ViewGroup.LayoutParams lp2 = divider.getLayoutParams();
		lp2.width = ViewGroup.LayoutParams.MATCH_PARENT;
		lp2.height = DipUtil.dip2px(getContext(), 1.5f);
		divider.setLayoutParams(lp2);
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.toollibrary_bottom_list_item_dialog_layout);
		setWindow();
		rootLayout = findViewById(R.id.mainView);
	}

	private void setWindow(){
		Window window = getWindow();
		window.setBackgroundDrawableResource(android.R.color.transparent);
		window.setGravity(Gravity.BOTTOM);
		window.setWindowAnimations(R.style.toollibraryBottomDialog);
		//设置对话框大小
		window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

		WindowManager.LayoutParams lp=this.getWindow().getAttributes();
		lp.dimAmount=0.5f;
		this.getWindow().setAttributes(lp);
		this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
	}
}
