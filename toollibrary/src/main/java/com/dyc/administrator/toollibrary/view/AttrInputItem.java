package com.dyc.administrator.toollibrary.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;

import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.centerm.administrator.toollibrary.R;
import com.dyc.administrator.toollibrary.utils.MLogger;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

/**
 * func:
 * author:丁语成 on 2020/2/20 16:55
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class AttrInputItem extends ConstraintLayout {
	private MLogger logger = MLogger.getLogger(this.getClass());
	private TextView star;
	private TextView title;
	private FirstFixEditText editText;
	private TextView text1;
	private TextView text2;
	private ImageView endImg;
	private View divider;

	private boolean starEnable;
	private String titleStr;
	private float titleSize;
	private ColorStateList titleColor;
	private String text1Str;
	private float text1Size;
	private boolean text1Pressed;
	private String text2Str;
	private float text2Size;
	private boolean text2Pressed;
	private String hintText;
	private float editTextSize;
	private boolean focusable;
	private int endImgId;
	private int inputType = InputType.TYPE_CLASS_TEXT;
	private boolean dividerEnable = false;
	private ColorStateList dividerColor;
	private Context context;

	public AttrInputItem(Context context) {
		super(context);
		this.context = context;
	}

	public AttrInputItem(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.AttrInputItem);
		starEnable = array.getBoolean(R.styleable.AttrInputItem_attr_star_enable, false);
		titleStr = array.getString(R.styleable.AttrInputItem_attr_title);
		titleSize = array.getDimensionPixelSize(R.styleable.AttrInputItem_attr_title_size, 0);
		titleColor = array.getColorStateList(R.styleable.AttrInputItem_attr_title_color);
		text1Str = array.getString(R.styleable.AttrInputItem_attr_text1_text);
		text1Size = array.getDimensionPixelSize(R.styleable.AttrInputItem_attr_text1_text_size, 0);
		text2Str = array.getString(R.styleable.AttrInputItem_attr_text2_text);
		text2Size = array.getDimensionPixelSize(R.styleable.AttrInputItem_attr_text2_text_size, 0);
		endImgId = array.getResourceId(R.styleable.AttrInputItem_attr_img, 0);
		hintText = array.getString(R.styleable.AttrInputItem_attr_hint_text);
		editTextSize = array.getDimensionPixelSize(R.styleable.AttrInputItem_attr_edit_text_size,0);
		focusable = array.getBoolean(R.styleable.AttrInputItem_attr_edit_text_focusable, true);
		dividerEnable = array.getBoolean(R.styleable.AttrInputItem_attr_divider_enable, false);
		dividerColor = array.getColorStateList(R.styleable.AttrInputItem_attr_divider_color);
		array.recycle();
		init();
	}

	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.toollibrary_attr_input_item_layout, this);
		star = findViewById(R.id.star);
		title = findViewById(R.id.title);
		text1 = findViewById(R.id.text1);
		text2 = findViewById(R.id.text2);
		endImg = findViewById(R.id.img);
		editText = findViewById(R.id.editText);
		divider = findViewById(R.id.divider);
		apply();
	}

	public void setEditFirstFix(String str) {
		if (str != null) {
			editText.setFixedText(str);
		}
	}

	public void setEditTextColor(int color){
		editText.setTextColor(context.getResources().getColorStateList(color));
	}

	public void setEditTextInputType(int inputType){
		this.inputType = inputType;
		editText.setInputType(inputType);
	}

	public void setEditFilter(InputFilter filter){
		editText.setFilters(new InputFilter[]{filter});
	}

	public void setEditTextStr(String str){
		if (str != null && editText != null){
			editText.setText(str);
			if (str.length() > 0 ){
				editText.setSelection(str.length());
			}
		}
	}

	public String getEditTextStr(){
		if (editText != null){
			logger.info("edit get:" + editText.getText().toString());
			return editText.getText().toString();
		}
		return "";
	}

	public void setEditInputEnable(boolean enable){
		if (enable){
			editText.setInputType(inputType);
		}else {
			editText.setInputType(InputType.TYPE_NULL);
		}
	}

	public void setEndImgVisibility(int visibility){
		endImg.setVisibility(visibility);
	}

	public void setText1Visibility(int visibility){
		setTexVisibility(text1, visibility);
	}

	public void setText2Visibility(int visibility){
		setTexVisibility(text2, visibility);
	}

	public void setTexVisibility(TextView textView, int visibility){
		textView.setVisibility(visibility);
	}

	public void setText1Pressed(boolean pressed){
		setTextPressed(text1, pressed);
		text1Pressed = pressed;
	}

	public void setText2Pressed(boolean pressed){
		setTextPressed(text2, pressed);
		text2Pressed = pressed;
	}

	private void setTextPressed(TextView textView, boolean pressed){
		if (pressed){
			textView.setBackground(ContextCompat.getDrawable(context, R.drawable.toollibrary_stroke_btn_pressed));
			textView.setTextColor(context.getResources().getColorStateList(R.color.toollibrary_them_blue));
		}else {
			textView.setBackground(ContextCompat.getDrawable(context, R.drawable.toollibrary_stroke_btn_normal));
			textView.setTextColor(context.getResources().getColorStateList(R.color.toollibrary_gray));
		}
	}

	public void setText1Listener(OnClickListener listener){
		text1.setOnClickListener(listener);
	}

	public void setText2Listener(OnClickListener listener){
		text2.setOnClickListener(listener);
	}

	public void setEndImgOnClickListener(OnClickListener listener){
		endImg.setOnClickListener(listener);
	}

	public void setTextWatcher(TextWatcher textWatcher){
		editText.addTextChangedListener(textWatcher);
	}

	private void apply() {
		if (starEnable) {
			star.setVisibility(VISIBLE);
		} else {
			star.setVisibility(GONE);
		}
		title.setText(titleStr);
		title.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize);
		if (titleColor != null) {
			title.setTextColor(titleColor);
		}
		if (text1Str == null) {
			text1.setVisibility(GONE);
		}
		text1.setText(text1Str);
		text1.setTextSize(TypedValue.COMPLEX_UNIT_PX, text1Size);
		if (text2Str == null) {
			text2.setVisibility(GONE);
		}
		text2.setText(text2Str);
		text2.setTextSize(TypedValue.COMPLEX_UNIT_PX, text2Size);
		if (endImgId == 0) {
			endImg.setVisibility(GONE);
		}
		endImg.setImageResource(endImgId);
		if (hintText == null) {
			editText.setVisibility(GONE);
		}
		editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, editTextSize);
		editText.setHint(hintText);
		editText.setFocusable(focusable);
		if (dividerColor != null) {
			divider.setBackgroundColor(dividerColor.getDefaultColor());
		}
		divider.setVisibility(dividerEnable ? VISIBLE : GONE);
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		super.setOnClickListener(null);
		this.setClickable(false);
		title = null;
		editText = null;
		text1 = null;
		text2 = null;
		endImg = null;
	}

	public String getTitleStr() {
		return titleStr;
	}

	public void setTitleStr(String titleStr) {
		this.titleStr = titleStr;
		apply();
	}

	public String getText1Str() {
		return text1Str;
	}

	public void setText1Str(String text1Str) {
		this.text1Str = text1Str;
		apply();
	}

	public String getText2Str() {
		return text2Str;
	}

	public void setText2Str(String text2Str) {
		this.text2Str = text2Str;
		apply();
	}

	public String getHintText() {
		return hintText;
	}

	public void setHintText(String hintText) {
		this.hintText = hintText;
		apply();
	}

	public FirstFixEditText getEditText() {
		return editText;
	}

	public void setEditText(FirstFixEditText editText) {
		this.editText = editText;
	}

	public boolean isText1Pressed() {
		return text1Pressed;
	}

	public boolean isText2Pressed() {
		return text2Pressed;
	}
}
