package com.dyc.administrator.toollibrary.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.centerm.administrator.toollibrary.R;

import java.text.DecimalFormat;

/**
 * func:
 * author:丁语成 on 2020/2/25 11:47
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class QuantityView extends FrameLayout implements View.OnClickListener, TextWatcher {
	private ImageView mMinus;
	private ImageView mPlus;
	private TextView mQuantity;
	private double lastChange;
	private double quantity = 0;
	private int MAX = 10000;
	private OnQauntityChangeListener mListener;

	public QuantityView(Context context) {
		super(context);
		init();
	}

	public QuantityView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.toollibrary_quantity_view_layout, this);
		mMinus = findViewById(R.id.quantity_minus);
		mPlus = findViewById(R.id.quantity_plus);
		mQuantity = findViewById(R.id.quantity_quantity);
		mMinus.setOnClickListener(this);
		mPlus.setOnClickListener(this);
		mQuantity.addTextChangedListener(this);
	}

	private void updateTextView() {
		if (quantity > 0){
			DecimalFormat format = new DecimalFormat("##########.##");
			mQuantity.setText(format.format(quantity));
			mMinus.setVisibility(VISIBLE);
		}else {
			mQuantity.setText("");
			mMinus.setVisibility(INVISIBLE);
		}
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		if (id == R.id.quantity_plus) {
			plus();
		} else if (id == R.id.quantity_minus) {
			minus();
		}
	}

	public void initQuantity(double quantity){
		this.quantity = quantity;
		mQuantity.removeTextChangedListener(this);
		updateTextView();
		mQuantity.addTextChangedListener(this);
	}

	private void plus() {
		lastChange = 1;
		if (quantity < MAX) {
			++quantity;
			updateTextView();
		}
	}

	private void minus() {
		lastChange = -1;
		if (quantity > 0) {
			--quantity;
			updateTextView();
		}else {
			quantity = 0;
		}
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity){
		lastChange = quantity - this.quantity;
		this.quantity = quantity;
		updateTextView();
	}

	public void setOnAmountChangeListener(OnQauntityChangeListener onAmountChangeListener) {
		this.mListener = onAmountChangeListener;
	}

	@Override
	public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

	}

	@Override
	public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

	}

	@Override
	public void afterTextChanged(Editable s) {
		if (s.toString().isEmpty()) {
			quantity = 0;
		} else {
			quantity = Double.parseDouble(s.toString());
		}
		if (quantity > MAX) {
			quantity = MAX;
		}
		mQuantity.removeTextChangedListener(this);
		updateTextView();
		mQuantity.addTextChangedListener(this);
		if (mListener != null) {
			mListener.onQuantityChanged(this, quantity, lastChange);
		}
	}

	public interface OnQauntityChangeListener {
		void onQuantityChanged(View view, double amount, double lastChange);
	}
}

