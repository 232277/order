package com.dyc.administrator.toollibrary.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.centerm.administrator.toollibrary.R;

/**
 * @auth luoban <luoban@centerm.com>
 * @date 19-4-23.上午11:34
 */
public class NumberPadView extends FrameLayout implements View.OnClickListener {
    public static int DELETE = 10;
    public static int CLEAR = 10;
    public static int CONFIRM = 10;

    public interface OnNumberInputListener {
        void onInput(int num);

        void onConfirm();

        void onDelete();

        void onClear();

        void onAction(int num);
    }

    private OnNumberInputListener mListener;

    public NumberPadView(Context context) {
        super(context);
        init();
    }

    public NumberPadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.number_pad, this);
        findViewById(R.id.btn_0).setOnClickListener(this);
        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
        findViewById(R.id.btn_3).setOnClickListener(this);
        findViewById(R.id.btn_4).setOnClickListener(this);
        findViewById(R.id.btn_5).setOnClickListener(this);
        findViewById(R.id.btn_6).setOnClickListener(this);
        findViewById(R.id.btn_7).setOnClickListener(this);
        findViewById(R.id.btn_8).setOnClickListener(this);
        findViewById(R.id.btn_9).setOnClickListener(this);
        findViewById(R.id.delete).setOnClickListener(this);
        findViewById(R.id.clear).setOnClickListener(this);
        findViewById(R.id.confirm).setOnClickListener(this);
    }

    public void setOnNumInputListener(OnNumberInputListener listener) {
        mListener = listener;
    }

    @Override
    public void onClick(View view) {
        if (mListener == null) {
            return;
        }
        int id = view.getId();
        if (id == R.id.btn_0) {
            onInput(0);
        } else if (id == R.id.btn_1) {
            onInput(1);
        } else if (id == R.id.btn_2) {
            onInput(2);
        } else if (id == R.id.btn_3) {
            onInput(3);
        } else if (id == R.id.btn_4) {
            onInput(4);
        } else if (id == R.id.btn_5) {
            onInput(5);
        } else if (id == R.id.btn_6) {
            onInput(6);
        } else if (id == R.id.btn_7) {
            onInput(7);
        } else if (id == R.id.btn_8) {
            onInput(8);
        } else if (id == R.id.btn_9) {
            onInput(9);
        } else if (id == R.id.delete) {
            mListener.onDelete();
            mListener.onAction(DELETE);
        } else if (id == R.id.clear) {
            mListener.onClear();
            mListener.onAction(CLEAR);
        } else if (id == R.id.confirm) {
            mListener.onConfirm();
            mListener.onAction(CONFIRM);
        } else if (id == R.id.zeroLeft){

        } else if (id == R.id.zeroRight){

        }
    }

    private void onInput(int num){
        if (mListener != null){
            mListener.onAction(num);
            mListener.onInput(num);
        }
    }
}
