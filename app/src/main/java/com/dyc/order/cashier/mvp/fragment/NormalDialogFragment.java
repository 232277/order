package com.dyc.order.cashier.mvp.fragment;

import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dyc.order.cashier.R;
import com.dyc.order.cashier.mvp.fragment.base.BaseDialogContentFragment;
import com.dyc.administrator.toollibrary.utils.MLogger;

public class NormalDialogFragment extends BaseDialogContentFragment {
	public final static int ONLY_MSG = 0;
	public final static int MSG_BOTTOM_OF_IMG = 1;
	public final static int MSG_TOP_OF_IMG = 2;

	private int fragType = MSG_BOTTOM_OF_IMG;
	private MLogger logger = MLogger.getLogger(this.getClass());

	private TextView msg;
	private ImageView img;
	private Button left;
	private Button right;
	private Button single;

	private CharSequence msgStr;
	private int imgId;
	private CharSequence leftBtnStr;
	private CharSequence rightBtnStr;
	private CharSequence singleBtnStr;
	private View.OnClickListener singleListener;
	private View.OnClickListener leftListener;
	private View.OnClickListener rightListener;

	public NormalDialogFragment(){}

	public NormalDialogFragment(int fragType) {
		this.fragType = fragType;
	}

	public static NormalDialogFragment newInstance(int fragType) {
		return new NormalDialogFragment(fragType);
	}

	public NormalDialogFragment setMsg(CharSequence string){
		msgStr = string;
		if (msg != null){
			msg.setText(string);
			msg.setVisibility(View.VISIBLE);
		}
		return this;
	}

	public NormalDialogFragment setImg(int imgId) {
		this.imgId = imgId;
		if (img != null){
			img.setImageResource(imgId);
			img.setVisibility(View.VISIBLE);
		}
		return this;
	}

	public NormalDialogFragment setSingleBtn(CharSequence str, View.OnClickListener listener){
		singleBtnStr = str;
		singleListener = listener;
		if (single != null && singleBtnStr != null){
			single.setText(str);
			single.setOnClickListener(listener);
			single.setVisibility(View.VISIBLE);
			if (left != null){
				left.setVisibility(View.GONE);
			}
			if (right != null){
				right.setVisibility(View.GONE);
			}
			logger.info("singleBtn visible");
		}
		return this;
	}

	public NormalDialogFragment setLeftBtn(CharSequence str, View.OnClickListener listener){
		leftBtnStr = str;
		leftListener = listener;
		if (left != null && leftBtnStr != null){
			left.setText(str);
			left.setOnClickListener(listener);
			left.setVisibility(View.VISIBLE);
			logger.info("leftBtn visible");
		}
		return this;
	}

	public NormalDialogFragment setRightBtn(CharSequence str, View.OnClickListener listener){
		rightBtnStr = str;
		rightListener = listener;
		if (right != null && rightBtnStr != null){
			right.setText(str);
			right.setOnClickListener(listener);
			right.setVisibility(View.VISIBLE);
			logger.info("rightBtn visible");
		}
		return this;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		int layoutId = R.layout.fragment_normal_dialog;
		switch (fragType){
			case ONLY_MSG:
				layoutId = R.layout.fragment_normal_dialog_msg_only;
				break;
			case MSG_BOTTOM_OF_IMG:
				layoutId = R.layout.fragment_normal_dialog;
				break;
			case MSG_TOP_OF_IMG:
				layoutId = R.layout.fragment_normal_dialog_msg_top_of_img;
				break;
		}
		View view = inflater.inflate(layoutId, container, false);
		msg = view.findViewById(R.id.msg);
		img = view.findViewById(R.id.img);
		left = view.findViewById(R.id.leftBtn);
		right = view.findViewById(R.id.rightBtn);
		single = view.findViewById(R.id.singleBtn);
		setMsg(msgStr);
		setImg(imgId);
		setLeftBtn(leftBtnStr, leftListener);
		setRightBtn(rightBtnStr, rightListener);
		setSingleBtn(singleBtnStr, singleListener);
		return view;
	}


	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}
}
