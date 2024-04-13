package com.dyc.order.cashier.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dyc.order.cashier.R;

import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * func:
 * author:丁语成 on 2020/6/28 16:28
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class NormalTopBar extends ConstraintLayout {
	private ConstraintLayout mainLayout;
	private TextView title;
	private ImageView arrow;
	private TextView countDownStr;
	private LinearLayout leftView;

	public NormalTopBar(Context context) {
		super(context);
		init(context, null);
	}

	public NormalTopBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public NormalTopBar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs){
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.NormalTopBar);
		String titleStr = array.getString(R.styleable.NormalTopBar_normal_top_bar_title_str);
		int backgroundImg = array.getResourceId(R.styleable.NormalTopBar_normal_top_bar_background, R.drawable.gradient_search_goods_top_bar);
		array.recycle();
		View v = LayoutInflater.from(getContext()).inflate(R.layout.custom_normal_top_bar_layout, this);
		mainLayout = findViewById(R.id.topBar);
		title = findViewById(R.id.topBarTitle);
		arrow = findViewById(R.id.backArrow);
		countDownStr = findViewById(R.id.countDownStr);
		leftView = findViewById(R.id.topBarLeft);
		if (backgroundImg > 0){
			mainLayout.setBackgroundResource(backgroundImg);
		}
		if (titleStr != null){
			title.setText(titleStr);
		}
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
	}

	public void setTitleStr(CharSequence str){
		if (title != null){
			title.setText(str);
		}
	}

	public void setCountDownStr(CharSequence str){
		if (countDownStr != null){
			countDownStr.setText(str);
		}
	}

	public void setLeftViewListener(OnClickListener listener){
		if (leftView != null){
			leftView.setOnClickListener(listener);
		}
	}
}
