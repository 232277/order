package com.dyc.administrator.toollibrary.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.centerm.administrator.toollibrary.R;
import com.dyc.administrator.toollibrary.utils.MLogger;

/**
 * func:
 * author:丁语成 on 2020/2/16 17:04
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class ImageTextView extends FrameLayout implements View.OnClickListener {
	private MLogger logger = MLogger.getLogger(this.getClass());
	private String mTitle;
	private int mIconSrc;
	private ColorStateList mIconColor;
	private ColorStateList mTextColor;
	private ImageView mIconView;
	private TextView mTitleView;
	private int mTextSize;
	private OnClickListener mOnclickListener;
	private Context context;

	public ImageTextView(Context context) {
		super(context);
		init();
		this.context = context;
	}

	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.toollibrary_image_text_layout, this);
	}

	public void setOnClickListener(View.OnClickListener listener) {
		mOnclickListener = listener;
	}

	public ImageTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ImageTextView);
		mIconSrc = array.getResourceId(R.styleable.ImageTextView_it_icon, 0);
		mIconColor = (array.getColorStateList(R.styleable.ImageTextView_it_icon_color));
		mTitle = array.getString(R.styleable.ImageTextView_it_title_text);
		mTextColor = (array.getColorStateList(R.styleable.ImageTextView_it_title_textColor));
		mTextSize = array.getDimensionPixelSize(R.styleable.ImageTextView_it_title_textSize,0);
		array.recycle();
		init();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	public void setIconAndTitleColor(int icon, int textColor) {
		mIconView.setImageResource(icon);
		mTitleView.setTextColor(context.getResources().getColorStateList(textColor));
	}

	private void apply() {
		if (mIconView != null && mIconSrc != 0) {
			mIconView.setImageResource(mIconSrc);
			if (mIconColor != null){
				mIconView.setImageTintList(mIconColor);
			}
		}

		if (mTitleView != null && !TextUtils.isEmpty(mTitle)) {
			mTitleView.setText(mTitle);
			if (mTextSize != 0){
				mTitleView.setTextSize(TypedValue.COMPLEX_UNIT_PX,mTextSize);
			}else {
				mTitleView.setEnabled(false);
			}
			if (mTextColor != null){
				mTitleView.setTextColor(mTextColor);
			}
		}

		if (mOnclickListener != null){
			this.setClickable(true);
			super.setOnClickListener(this);
		}
	}


	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		mIconView = findViewById(R.id.image_text_btn_image);
		mTitleView = (TextView)findViewById(R.id.image_text_btn_text);
		apply();
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		super.setOnClickListener(null);
		this.setClickable(false);
		mIconView = null;
		mTitleView = null;
	}

	@Override
	public void onClick(View view) {
		try {
			if (mOnclickListener != null) {
				mOnclickListener.onClick(view);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}

