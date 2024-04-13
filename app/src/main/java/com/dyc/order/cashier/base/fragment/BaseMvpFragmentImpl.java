package com.dyc.order.cashier.base.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dyc.order.cashier.base.iml.BaseContact;
import com.dyc.simplemvplibrary.BaseMvpFragment;

import org.apache.log4j.Logger;

import androidx.annotation.Nullable;

/**
 * func:
 * author:丁语成 on 2020/2/14 19:27
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public abstract class BaseMvpFragmentImpl<M extends BaseContact.BaseModel, V extends BaseContact.BaseView, P extends BaseContact.BasePresent> extends BaseMvpFragment<M, V, P> implements BaseContact.BaseView {
	protected Logger logger = Logger.getLogger(this.getClass());

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
		logger.info(this.toString() + "onCreateView");
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onRelease() {
		super.onRelease();
	}

	@Nullable
	@Override
	public Context getContext() {
		return super.getContext();
	}

	public Activity getHostActivity(){
		return getActivity();
	}
}