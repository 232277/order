package com.dyc.order.cashier.mvp.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import com.dyc.order.cashier.R;
import com.dyc.order.cashier.data.response.MemberInfoData;
import com.dyc.order.cashier.mvp.fragment.base.BaseDialogContentFragment;
import com.dyc.administrator.toollibrary.utils.DipUtil;
import com.dyc.administrator.toollibrary.utils.ToastUtils;
import com.dyc.administrator.toollibrary.view.NumberPadView;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;


public class MemberFaceFragment extends BaseDialogContentFragment {
	private RoundedImageView faceImg;
//	private NumberPadView numPad;
	private EditText first;
	private EditText second;
	private EditText third;
	private EditText four;
	private ArrayList<EditText> numList = new ArrayList<>(4);
	private MemberInfoData infoData;
	private boolean empty;
	private String lastFour;
	private OnConfirmListener onConfirmListener;
	private Bitmap facePic;

	public MemberFaceFragment(){}

	public MemberFaceFragment(MemberInfoData infoData, Bitmap facePic) {
		this.infoData = infoData;
		this.facePic = facePic;
	}

	public static MemberFaceFragment newInstance(MemberInfoData infoData, Bitmap facePic) {
		MemberFaceFragment fragment = new MemberFaceFragment(infoData, facePic);
		return fragment;
	}

	public NumberPadView.OnNumberInputListener getOnNumberInputListener() {
		return onNumberInputListener;
	}

	public void setOnConfirmListener(OnConfirmListener onConfirmListener) {
		this.onConfirmListener = onConfirmListener;
	}

	private NumberPadView.OnNumberInputListener onNumberInputListener = new NumberPadView.OnNumberInputListener() {
		@Override
		public void onInput(int num) {
			for (EditText edit : numList){
				if (TextUtils.isEmpty(edit.getText())){
					edit.setText(""+num);
					break;
				}
			}
			restartCountDown();
		}

		@Override
		public void onConfirm() {
			empty = false;
			numList.forEach(editText -> empty = TextUtils.isEmpty(editText.getText()));
			if (empty){
				getActivity().runOnUiThread(()->
						ToastUtils.showToast(getContext(),
								R.string.tip_pls_input_correct_phone_last_four));
				return;
			}
			lastFour = "";
			numList.forEach(editText -> lastFour += editText.getText());
			boolean res = false;
			if (lastFour.equals(
					infoData.getPhoneNumber()
							.substring(infoData.getPhoneNumber().length() - 4))){
				res = true;
			}
			if (onConfirmListener != null){
				onConfirmListener.onConfirm(res, infoData);
			}
			dismiss();
		}

		@Override
		public void onDelete() {
			for (int i = numList.size() - 1; i >= 0; --i){
				if (!TextUtils.isEmpty(numList.get(i).getText())){
					numList.get(i).setText("");
					break;
				}
			}
		}

		@Override
		public void onClear() {
			numList.forEach(editText -> editText.setText(""));
		}

		@Override
		public void onAction(int num) {
			restartCountDown();
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_member_face, container, false);
		faceImg = view.findViewById(R.id.faceImg);
		faceImg.setImageBitmap(facePic);
		first = view.findViewById(R.id.first);
		second = view.findViewById(R.id.second);
		third = view.findViewById(R.id.third);
		four = view.findViewById(R.id.four);
//		numPad = view.findViewById(R.id.numPad);
		numList.add(first);
		numList.add(second);
		numList.add(third);
		numList.add(four);
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		setWindow();
	}

	private void setWindow(){
		WindowManager.LayoutParams wlp = dialog.getDialog().getWindow().getAttributes();
		dialog.getDialog().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
				WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
		wlp.gravity = Gravity.BOTTOM;
		wlp.dimAmount=0.0f;
		wlp.y = DipUtil.dip2px(getContext(), 300);
		dialog.getDialog().getWindow().setAttributes(wlp);
		dialog.getDialog().setCanceledOnTouchOutside(false);
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
	}

	@Override
	public void onDetach() {
		super.onDetach();
		infoData = null;
		numList.forEach(editText -> editText.setText(""));
	}

	public interface OnConfirmListener{
		void onConfirm(boolean res, MemberInfoData memberInfoData);
	}
}
