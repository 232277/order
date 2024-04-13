package com.dyc.order.cashier.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dyc.order.cashier.R;

import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * func:
 * author:丁语成 on 2020/3/23 10:56
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class ActionBarTopBarLayout extends ConstraintLayout {
	private ImageView backGround;
	private ImageView arrow;
	private View leftView;
	private TextView title;
	private TextView rightCornerBtn;
	private View rightCornerBtnBack;

	public ActionBarTopBarLayout(Context context) {
		super(context);
		init();
	}

	public ActionBarTopBarLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init(){
		View v = LayoutInflater.from(getContext()).inflate(R.layout.custom_action_bar_top_bar_layout, this);
		backGround = v.findViewById(R.id.backGround);
		arrow = v.findViewById(R.id.icon);
		title = v.findViewById(R.id.title);
		leftView = v.findViewById(R.id.leftView);
		rightCornerBtn = v.findViewById(R.id.rightCornerBtn);
		rightCornerBtnBack = v.findViewById(R.id.rightCornerBtnBack);
	}

	public void setLeftBtnClickListener(OnClickListener listener){
		if (arrow != null){
			arrow.setOnClickListener(listener);
		}
	}

	public void setArrow(int id, OnClickListener listener){
		if (id > 0){
			arrow.setImageResource(id);
//			arrow.setOnClickListener(listener);
//			title.setOnClickListener(listener);
			leftView.setOnClickListener(listener);
		}
	}

	public void setTitleStr(CharSequence titleStr){
		title.setText(titleStr);
	}

	public void setRightBtnStr(CharSequence btnStr){
		if (TextUtils.isEmpty(btnStr)){
			if (rightCornerBtn != null){
				rightCornerBtn.setVisibility(GONE);
			}
		}else {
			if (rightCornerBtn != null){
				rightCornerBtn.setVisibility(VISIBLE);
				rightCornerBtn.setText(btnStr);
			}

		}
	}

	public void setRightBtnClickListener(OnClickListener listener){
		if (rightCornerBtn != null){
			rightCornerBtn.setOnClickListener(listener);
		}
	}

	public void setBackGroundImg(int id){
		backGround.setImageResource(id);
	}

	public ImageView getArrow() {
		return arrow;
	}

	public TextView getTitle() {
		return title;
	}

	public TextView getRightCornerBtn() {
		return rightCornerBtn;
	}

	public View getRightCornerBtnBack() {
		return rightCornerBtnBack;
	}
}
