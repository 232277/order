package com.dyc.administrator.toollibrary.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.centerm.administrator.toollibrary.R;

import androidx.appcompat.widget.AppCompatEditText;

/**
 * func:左边有固定文字EditText
 * author:丁语成 on 2020/2/23 16:44
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class FirstFixEditText extends AppCompatEditText {
	private String fixedText;
	private View.OnClickListener mListener;
	private int leftPadding;

	public FirstFixEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setFixedText(String text) {
		fixedText = text;
		leftPadding = getPaddingLeft();
		int left = (int) getPaint().measureText(fixedText) + leftPadding;
		setPadding(left, getPaddingTop(), getPaddingBottom(), getPaddingRight());
		invalidate();
	}

	public void setDrawableClick(View.OnClickListener listener) {
		mListener = listener;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (!TextUtils.isEmpty(fixedText)) {
			canvas.drawText(fixedText, leftPadding, getBaseline(), getPaint());
//            查看文字的基线，以及view的中线
//            canvas.drawLine(0, getBaseline(), getMeasuredWidth(), getBaseline(), p);
//            canvas.drawLine(0, getMeasuredHeight() / 2, getMeasuredWidth(), getMeasuredHeight() / 2, p);

			Paint p = new Paint();
			p.setStrokeWidth(0.5f);
			p.setColor(getResources().getColor(R.color.toollibrary_dialog_edit_background,null));
			canvas.drawLine(0, this.getHeight()-2, this.getWidth()-2, this.getHeight()-2, p);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (mListener != null && getCompoundDrawables()[2] != null) {
			switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					int i = getMeasuredWidth() - getCompoundDrawables()[2].getIntrinsicWidth();
					if (event.getX() > i) {
						mListener.onClick(this);
						return true;
					}
					break;
				case MotionEvent.ACTION_UP:
					break;
				default:
					break;
			}
		}
		return super.onTouchEvent(event);
	}

	@Override
	public void setText(CharSequence text, BufferType type) {
		super.setText(text, type);
		if (text != null && text.length() >0){
			this.setSelection(text.length());
		}
	}
}

