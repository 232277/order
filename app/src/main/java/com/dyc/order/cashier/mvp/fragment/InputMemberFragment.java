package com.dyc.order.cashier.mvp.fragment;

import android.content.Intent;
import android.os.Bundle;

import android.text.InputFilter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.dyc.order.cashier.R;
import com.dyc.order.cashier.constant.ActivityOperationField;
import com.dyc.order.cashier.mvp.fragment.base.BaseDialogContentFragment;
import com.dyc.administrator.toollibrary.utils.DipUtil;
import com.dyc.administrator.toollibrary.utils.MLogger;
import com.dyc.administrator.toollibrary.utils.ToastUtils;
import com.dyc.administrator.toollibrary.view.NumberPadView;
import com.dyc.order.cashier.mvp.activity.member.AddMemberByCodeActivity2;

public class InputMemberFragment extends BaseDialogContentFragment {
	private MLogger logger = MLogger.getLogger(this.getClass());
	private OnConfirmListener onConfirmListener;
	private EditText memberPhone;

	public InputMemberFragment() {
	}

	public static InputMemberFragment newInstance() {
		return new InputMemberFragment();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public void setOnConfirmListener(OnConfirmListener onConfirmListener) {
		this.onConfirmListener = onConfirmListener;
	}

	public NumberPadView.OnNumberInputListener getOnNumberInputListener() {
		return onNumberInputListener;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_input_member, container, false);
		memberPhone = view.findViewById(R.id.memberPhone);
		//11位输入限制
		memberPhone.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
		memberPhone.setOnKeyListener((v, keyCode, event) -> {
			restartCountDown();
			return false;
		});
		memberPhone.setOnEditorActionListener((v, actionId, event) -> {
			logger.info("confirm");
			if (memberPhone.getText() != null && memberPhone.getText().length() == 11){
				//手机号正确才给响应
				if (onConfirmListener != null){
					onConfirmListener.onConfirm(memberPhone.getText().toString());
				}
				return true;
			}else {
				ToastUtils.showToast(getContext(), R.string.tip_member_phone_format_no_correct);
				return false;
			}
		});
		Intent intent = new Intent(getActivity(), AddMemberByCodeActivity2.class);
		//跳转注册
		view.findViewById(R.id.notMemberLayout).setOnClickListener(v -> {
			intent.putExtra(ActivityOperationField.SCAN_CODE_REGISTER, true);
//			startActivity(new Intent(getActivity(), AddMemberActivity.class));
			startActivity(intent);
			dismiss();
		});
		//跳转登录
		view.findViewById(R.id.scanLogin).setOnClickListener(v -> {
			intent.putExtra(ActivityOperationField.SCAN_CODE_REGISTER, false);
//			startActivity(new Intent(getActivity(), AddMemberActivity.class));
			startActivity(intent);
			dismiss();
		});
		return view;
	}

	private NumberPadView.OnNumberInputListener onNumberInputListener = new NumberPadView.OnNumberInputListener() {
		@Override
		public void onInput(int num) {
			if (memberPhone != null){
				memberPhone.append(""+num);
				restartCountDown();
			}
		}

		@Override
		public void onConfirm() {
			logger.info("confirm");
			if (memberPhone != null){
				if (memberPhone.getText() != null && memberPhone.getText().length() == 11){
					if (onConfirmListener != null){
						onConfirmListener.onConfirm(memberPhone.getText().toString());
					}
				}else {
					ToastUtils.showToast(getContext(), R.string.tip_member_phone_format_no_correct);
				}
			}
		}

		@Override
		public void onDelete() {
			if (memberPhone != null){
				int index = memberPhone.getSelectionStart();
				if (index > 0){
					memberPhone.getText().delete(index-1, index);
				}
			}
		}

		@Override
		public void onClear() {
			if (memberPhone != null){
				memberPhone.setText("");
			}
		}

		@Override
		public void onAction(int num) {

		}
	};

	@Override
	public void onResume() {
		super.onResume();
		setWindow();
	}

	private void setWindow(){
		dialog.getDialog().setCanceledOnTouchOutside(false);
		Window window = dialog.getDialog().getWindow();
		window.setBackgroundDrawableResource(android.R.color.transparent);
		window.setWindowAnimations(com.centerm.administrator.toollibrary.R.style.toollibraryBottomDialog);
		window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
				WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.gravity = Gravity.BOTTOM;
		lp.y = DipUtil.dip2px(getContext(), 300);
		lp.dimAmount=0.0f;
		window.setAttributes(lp);
		window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
	}

	public interface OnConfirmListener{
		void onConfirm(String phone);
	}
}
