package com.dyc.administrator.toollibrary.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.centerm.administrator.toollibrary.R;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * func:
 * author:丁语成 on 2020/2/22 8:39
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class InputDialog extends Dialog {
	private TextView title;
	private TextView secTitle;
	private TextView secTitleContent;
	private TextView star1;
	private TextView editLabel1;
	private FirstFixEditText edit1;
	private TextView endFix1;
	private TextView star2;
	private TextView editLabel2;
	private FirstFixEditText edit2;
	private TextView endFix2;
	private Button cancel;
	private Button confirm;

	public InputDialog(@NonNull Context context) {
		super(context);
	}

	public InputDialog(@NonNull Context context, int themeResId) {
		super(context, themeResId);
	}

	protected InputDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.toollibrary_input_dialog_layout);
		title = findViewById(R.id.title);
		secTitle = findViewById(R.id.secTitle);
		secTitleContent = findViewById(R.id.secTitleContent);
		star1 = findViewById(R.id.star1);
		editLabel1 = findViewById(R.id.editLabel1);
		edit1 = findViewById(R.id.edit1);
		endFix1 = findViewById(R.id.endFix1);
		star2 = findViewById(R.id.star2);
		editLabel2 = findViewById(R.id.editLabel2);
		edit2 = findViewById(R.id.edit2);
		endFix2 = findViewById(R.id.endFix2);
		cancel = findViewById(R.id.cancel);
		confirm = findViewById(R.id.confirm);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		showSoftInputFromWindow(edit1);
	}

	public void showSoftInputFromWindow(EditText editText){
		editText.setFocusable(true);
		editText.setFocusableInTouchMode(true);
		editText.requestFocus();
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
	}

	public InputDialog enableStar2(boolean enable){
		return enableView(star2, enable);
	}

	public InputDialog enableStar1(boolean enable){
		return enableView(star1, enable);
	}

	private InputDialog enableView(View view, boolean enable){
		view.setEnabled(enable);
		return this;
	}

	public InputDialog setEdit1FirstFix(String str){
		edit1.setFixedText(str);
		return this;
	}

	public InputDialog setEndFix1(String str, int textSize, int textColor){
		endFix1.setText(str);
		endFix1.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
		endFix1.setTextColor(getContext().getResources().getColorStateList(textColor));
		return this;
	}

	public InputDialog setEdit2FirstFix(String str){
		edit2.setFixedText(str);
		return this;
	}

	public InputDialog setEndFix2(String str, int textSize, int textColor){
		endFix2.setText(str);
		endFix2.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
		endFix2.setTextColor(getContext().getResources().getColorStateList(textColor));
		return this;
	}

	public InputDialog setEdit1Hint(String str){
		edit1.setHint(str);
		return this;
	}

	public InputDialog setEdit2Hint(String str){
		edit2.setHint(str);
		return this;
	}

	public InputDialog setLabel1Str(String str){
		editLabel1.setText(str);
		return this;
	}

	public InputDialog setLabel2Str(String str){
		editLabel2.setText(str);
		return this;
	}

	public InputDialog setTitleStr(String str){
		title.setText(str);
		return this;
	}

	public InputDialog setSecTitleStr(String str){
		secTitle.setText(str);
		secTitle.setVisibility(View.VISIBLE);
		secTitleContent.setVisibility(View.VISIBLE);
		return this;
	}

	public InputDialog setSecTitleContent(String str){
		secTitleContent.setText(str);
		secTitle.setVisibility(View.VISIBLE);
		secTitleContent.setVisibility(View.VISIBLE);
		return this;
	}

	public InputDialog setEditText1InputType(int type){
		setEditTextInputType(edit1, type);
		return this;
	}

	public InputDialog setEditText2InputType(int type){
		setEditTextInputType(edit2, type);
		return this;
	}

	private InputDialog setEditTextInputType(EditText editText, int type){
		editText.setInputType(type);
		return this;
	}

	public InputDialog setEdit1TextWatcher(TextWatcher textWatcher){
		setEditTextWatcher(edit1, textWatcher);
		return this;
	}

	public InputDialog setEdit2TextWatcher(TextWatcher textWatcher){
		setEditTextWatcher(edit2, textWatcher);
		return this;
	}

	private InputDialog setEditTextWatcher(EditText editText, TextWatcher textWatcher){
		editText.addTextChangedListener(textWatcher);
		return this;
	}

	public InputDialog setEdit1Focusable(Boolean focusable){
		setEditFocusable(edit1, focusable);
		return this;
	}

	public InputDialog setEdit2Focusable(Boolean focusable){
		setEditFocusable(edit2, focusable);
		return this;
	}

	private InputDialog setEditFocusable(EditText editText, Boolean focusable){
		editText.setFocusable(focusable);
		return this;
	}

	public String getEdit1String(){
		return getEditString(edit1);
	}

	public String getEdit2String(){
		return getEditString(edit2);
	}

	private String getEditString(EditText editText){
		if (editText != null){
			return editText.getText().toString();
		}
		return null;
	}

	public InputDialog setEdit1String(String str){
		setEditString(edit1, str);
		return this;
	}

	public InputDialog setEdit2String(String str){
		setEditString(edit2, str);
		return this;
	}

	private InputDialog setEditString(EditText editText, String str){
		editText.setText(str);
		editText.setSelection(editText.getText().length());
		return this;
	}

	public InputDialog setCancelOnclick(View.OnClickListener listener) {
		cancel.setOnClickListener(listener);
		return this;
	}

	public InputDialog setConfirmOnClick(View.OnClickListener listener) {
		confirm.setOnClickListener(listener);
		return this;
	}

	public TextView getTitle() {
		return title;
	}

	public InputDialog setTitle(TextView title) {
		this.title = title;
		return this;
	}

	public TextView getSecTitle() {
		return secTitle;
	}

	public InputDialog setSecTitle(TextView secTitle) {
		this.secTitle = secTitle;
		return this;
	}

	public TextView getEditLabel1() {
		return editLabel1;
	}

	public InputDialog setEditLabel1(TextView editLabel1) {
		this.editLabel1 = editLabel1;
		return this;
	}

	public TextView getEditLabel2() {
		return editLabel2;
	}

	public InputDialog setEditLabel2(TextView editLabel2) {
		this.editLabel2 = editLabel2;
		return this;
	}

	public TextView getStar1() {
		return star1;
	}

	public void setStar1(TextView star1) {
		this.star1 = star1;
	}

	public FirstFixEditText getEdit1() {
		return edit1;
	}

	public void setEdit1(FirstFixEditText edit1) {
		this.edit1 = edit1;
	}

	public TextView getStar2() {
		return star2;
	}

	public void setStar2(TextView star2) {
		this.star2 = star2;
	}

	public FirstFixEditText getEdit2() {
		return edit2;
	}

	public void setEdit2(FirstFixEditText edit2) {
		this.edit2 = edit2;
	}

	public Button getCancel() {
		return cancel;
	}

	public InputDialog setCancel(Button cancel) {
		this.cancel = cancel;
		return this;
	}

	public Button getConfirm() {
		return confirm;
	}

	public InputDialog setConfirm(Button confirm) {
		this.confirm = confirm;
		return this;
	}
}
