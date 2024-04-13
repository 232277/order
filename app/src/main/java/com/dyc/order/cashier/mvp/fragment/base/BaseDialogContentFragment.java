package com.dyc.order.cashier.mvp.fragment.base;

import com.dyc.order.cashier.view.CountDownDialog;

import androidx.fragment.app.Fragment;

/**
 * func:
 * author:丁语成 on 2020/3/26 14:38
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public abstract class BaseDialogContentFragment extends Fragment {
	protected CountDownDialog dialog;

	public void dismiss(){
		if (dialog != null){
			dialog.dismiss();
		}
	}

	public void restartCountDown(){
		if (dialog != null){
			dialog.restartCountDown();
		}
	}

	public void setDialog(CountDownDialog dialog) {
		this.dialog = dialog;
	}
}
