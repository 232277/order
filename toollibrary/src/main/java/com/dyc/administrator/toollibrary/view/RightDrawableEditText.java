package com.dyc.administrator.toollibrary.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.centerm.administrator.toollibrary.R;
import com.dyc.administrator.toollibrary.utils.DipUtil;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;

/**
 * func:
 * author:丁语成 on 2020/2/20 20:29
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class RightDrawableEditText extends AppCompatEditText implements View.OnFocusChangeListener, TextWatcher {
	private Drawable rightDrawable;
	private Drawable mClearDrawable;//右端图标
	private Drawable leftDrawble;
	private boolean hasFoucs;//焦点
	private Listener listener;
	private boolean noImgSet;

	public RightDrawableEditText(Context context) {
		this(context, null);
	}

	public RightDrawableEditText(Context context, AttributeSet attrs) {
		// 这里构造方法也很重要，不加这个很多属性不能再XML里面定义
		this(context, attrs, android.R.attr.editTextStyle);

	}

	public RightDrawableEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setmClearDrawable(int drawableId){
		mClearDrawable = ContextCompat.getDrawable(getContext(), drawableId);
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		init();
	}

	private void init() {
		rightDrawable = getCompoundDrawables()[2];
		mClearDrawable = ContextCompat.getDrawable(getContext(), R.drawable.toollibrary_icon_del);

		//大小一致
		if (rightDrawable == null){
			noImgSet = true;
			rightDrawable = mClearDrawable;
		}
//		mClearDrawable.setBounds(0, 0, rightDrawable.getIntrinsicWidth(), rightDrawable.getIntrinsicWidth());
//		rightDrawable.setBounds(0, 0, rightDrawable.getIntrinsicWidth(), rightDrawable.getIntrinsicHeight());

		setRightDrawableBounds(DipUtil.dip2px(getContext(), 15)
				, DipUtil.dip2px(getContext(), 15));
		if (getCompoundDrawables()[0] != null){
			leftDrawble = getCompoundDrawables()[0];
			setLeftDrawableBounds(DipUtil.dip2px(getContext(), 12)
					,DipUtil.dip2px(getContext(), 12));
		}
		changeRightIcon(false);

		// 设置焦点改变的监听
		setOnFocusChangeListener(this);
		// 设置输入框里面内容发生改变的监听
		addTextChangedListener(this);
	}

	@Override
	public void setText(CharSequence text, BufferType type) {
		super.setText(text, type);
		this.setSelection(getText().length());
	}

	public void setLeftDrawableBounds(int right, int bottom){
		if (leftDrawble != null){
			leftDrawble.setBounds(0,0, right, bottom);
		}
	}

	public void setRightDrawableBounds(int right, int bottom){
		if (mClearDrawable != null){
			mClearDrawable.setBounds(0, 0, right, bottom);
		}
		if (rightDrawable != null){
			rightDrawable.setBounds(0, 0, right, bottom);
		}
	}

	public void setRightBtnListener(Listener listener) {
		this.listener = listener;
	}

	/**
	 * 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件 当我们按下的位置 在 EditText的宽度 -
	 * 图标到控件右边的间距 - 图标的宽度 和 EditText的宽度 - 图标到控件右边的间距之间我们就算点击了图标，竖直方向就没有考虑
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_UP) {
			if (getCompoundDrawables()[2] != null) {

				boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight()) && (event.getX() < ((getWidth() - getPaddingRight())));

				if (touchable) {
					Log.e("RightEditText", getText().toString());
					//有字则清除
					if (listener == null || !"".equals(getText().toString())){
						Log.e("RightEditText", "clean");
						this.setText("");
						changeRightIcon(false);
					}else {
						Log.e("RightEditText", "scan");
						listener.onClick();
					}
				}
			}
		}

		return super.onTouchEvent(event);
	}

	/**
	 * 当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏
	 */
	public void onFocusChange(View v, boolean hasFocus) {
		this.hasFoucs = hasFocus;
		if (hasFocus) {
			changeRightIcon(getText().length() > 0);
		} else {
			changeRightIcon(false);
		}
	}

	private void changeRightIcon(boolean hasText){
		if (noImgSet && !hasText){
			setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], null, getCompoundDrawables()[3]);
		}else {
			Drawable right = hasText ? mClearDrawable : rightDrawable;
			setCompoundDrawables(leftDrawble, getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
		}
	}

	/**
	 * 当输入框里面内容发生变化的时候回调的方法
	 */
	@Override
	public void onTextChanged(CharSequence s, int start, int count, int after) {
		if (hasFoucs) {
			changeRightIcon(s.length() > 0);
		}
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {

	}

	@Override
	public void afterTextChanged(Editable s) {

	}

	public interface Listener{
		void onClick();
	}
}
