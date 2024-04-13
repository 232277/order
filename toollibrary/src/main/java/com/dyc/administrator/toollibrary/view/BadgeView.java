package com.dyc.administrator.toollibrary.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.FileUtils;
import com.centerm.administrator.toollibrary.R;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * func:
 * author:丁语成 on 2020/4/13 16:09
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class BadgeView extends ConstraintLayout{
	private ImageView img;
	private TextView text;
	private ImageView badgeImg;

	private CharSequence str;
	private int textSize = -1;
	private ColorStateList textColor;
	private int mainImg;
	private int badgeImage;
	private int backgroundImg;
	private int backgroundImgChecked;

	public BadgeView(Context context) {
		super(context);
	}

	public BadgeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	protected int getLayoutId(){
		return R.layout.toollibrary_badge_view_layout;
	}

	private void init(Context context, @Nullable AttributeSet attrs){
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BadgeView);
		str = array.getString(R.styleable.BadgeView_badge_view_text);
		textSize = array.getDimensionPixelSize(R.styleable.BadgeView_badge_view_text_size, 15);
		textColor = array.getColorStateList(R.styleable.BadgeView_badge_view_text_color);
		mainImg = array.getResourceId(R.styleable.BadgeView_badge_view_img, 0);
		badgeImage = array.getResourceId(R.styleable.BadgeView_badge_view_badge_img, 0);
		backgroundImg = array.getResourceId(R.styleable.BadgeView_badge_view_background, 0);
		backgroundImgChecked = array.getResourceId(R.styleable.BadgeView_badge_view_background_checked, 0);
		array.recycle();
		LayoutInflater.from(getContext()).inflate(getLayoutId(), this);
		img = findViewById(R.id.img);
		text = findViewById(R.id.txt);
		badgeImg = findViewById(R.id.badgeImg);
		setChecked(false);
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
	}

	public void setImg(int id){
		img.setImageResource(id);
	}

	public void setText(CharSequence str){
		text.setText(str);
	}

	public void setTextColor(ColorStateList textColor) {
		this.textColor = textColor;
	}

	public void setTextSize(int textSize) {
		this.textSize = textSize;
	}

	public void setBadgeImg(int id){
		badgeImg.setImageResource(id);
	}

	public void setBackgroundImg(int backgroundImg) {
		this.backgroundImg = backgroundImg;
	}

	public void setBackgroundImgChecked(int backgroundImgChecked) {
		this.backgroundImgChecked = backgroundImgChecked;
	}

	private void apply(boolean checked){
		if (mainImg != 0){
			img.setImageResource(mainImg);
		}
		if (badgeImage != 0){
			badgeImg.setImageResource(badgeImage);
		}
		badgeImg.setVisibility(checked ? VISIBLE : GONE);
		if (backgroundImg != 0 && backgroundImgChecked != 0){
			setBackgroundResource(checked ? backgroundImgChecked : backgroundImg);
		}
		if (str != null){
			text.setVisibility(VISIBLE);
			text.setText(str);
		}else {
			text.setVisibility(GONE);
		}
		if (textColor != null){
			text.setTextColor(textColor);
		}
		if (textSize != -1){
			text.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
		}
	}

	public void setChecked(boolean checked){
		apply(checked);
	}
}
