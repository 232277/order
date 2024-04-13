package com.dyc.order.cashier.base.activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dyc.order.cashier.R;
import com.dyc.order.cashier.data.local.DataCache;
import com.dyc.order.cashier.mvp.activity.other.LoginMVPActivity;
import com.dyc.order.cashier.view.ActionBarTopBarLayout;
import com.dyc.order.cashier.view.LogOffDialog;
import com.dyc.order.cashier.base.iml.BaseContact;
import com.dyc.administrator.toollibrary.utils.DialogFactory;
import com.dyc.administrator.toollibrary.utils.MLogger;
import com.dyc.simplemvplibrary.BaseMvpActivity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;


/**
 * func: 基类
 * author:丁语成 on 2020/2/14 19:27
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public abstract class BaseMvpActivityimpl<M extends BaseContact.BaseModel, V extends BaseContact.BaseView, P extends BaseContact.BasePresent> extends BaseMvpActivity<M, V, P> implements BaseContact.BaseView {
	protected MLogger logger = MLogger.getLogger(this.getClass());
	public static final int TAKE_PHOTO_REQUEST_CODE = 1;
	public static final int GET_UNKNOWN_APP_SOURCES = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		logger.info(this.toString() + "onCreate");
		super.onCreate(savedInstanceState);
	}

	@Override
	public void doBeforeInitView(View v) {
		super.doBeforeInitView(v);
	}

	public void loginTimeOut(){
		loginTimeOut(R.string.error_login_un_authorization);
	}

	public void loginTimeOut(int strId){
		if (strId > 0){
			showToast(strId);
		}
		DataCache.clear();
		DataCache.clearLoginData();
		finishAll();
		startActivity(new Intent(this, LoginMVPActivity.class));
	}

	public void showNormalActionBar(CharSequence string){
		changeActionBarToTopBar();
		ActionBarTopBarLayout actionBarTopBarLayout = getTopBar();
		actionBarTopBarLayout.getRightCornerBtnBack().setVisibility(View.GONE);
		actionBarTopBarLayout.setArrow(R.mipmap.arrow, v -> onBackPressed());
		actionBarTopBarLayout.setTitleStr(string);
		actionBarTopBarLayout.setBackGroundImg(R.drawable.action_bar_gradient);
	}

	public void showActionBar(CharSequence str){
		showActionBar(true, str);
	}

	public void showActionBar(boolean show){
		if (DataCache.getLoginInfoData() != null
				&& DataCache.getLoginInfoData().getMerchantInfoDTO() != null){
			showActionBar(show, DataCache.getLoginInfoData().getMerchantInfoDTO().getName());
		}else {
			if (show){
				//需要读取logindata却读不到说明登录失效
				loginTimeOut();
			}else {
				showActionBar(false, null);
			}
		}
	}

	public void showActionBar(boolean show, CharSequence title){
		showActionBar(show, title, null);
	}

	private LogOffDialog logOffDialog;
	public void showActionBar(boolean show, CharSequence title, String btnStr) {
		logger.info(this.getClass().getName() + " actionBar:" + show);
		ActionBar actionBar = getSupportActionBar();
		if (show){
			if (actionBar != null){
				changeActionBarToTopBar();
				changeActionBar(title, btnStr);
				getTopBar().setLeftBtnClickListener(v -> {
					if (logOffDialog == null){
						logOffDialog = new LogOffDialog(this, this);
					}
					logOffDialog.show();
				});
				actionBar.show();
			}
		}else {
			if (actionBar != null){
				actionBar.hide();
			}
		}
	}

	private void showLogOffDialog(){

	}

	private void changeActionBarToTopBar(){
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null){
			actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
			actionBar.setElevation(0);
			actionBar.setDisplayShowHomeEnabled(false);
			actionBar.setDisplayShowCustomEnabled(true);
			actionBar.setDisplayShowTitleEnabled(false);
			actionBar.setCustomView(R.layout.action_bar_layout);
			Toolbar parent =(Toolbar) actionBar.getCustomView().getParent();
			parent.setPadding(0,0,0,0);
			parent.setContentInsetsAbsolute(0,0);
			actionBar.show();
		}
	}

	public void changeActionBar(CharSequence title, CharSequence btnStr){
		runOnUiThread(()->{
			ActionBar actionBar = getSupportActionBar();
			if(actionBar != null && actionBar.getCustomView() != null){
				ActionBarTopBarLayout actionBarTopBarLayout = actionBar.getCustomView().findViewById(R.id.topBar);
				if (actionBarTopBarLayout != null){
					if (title != null){
						actionBarTopBarLayout.setTitleStr(title);
					}
					actionBarTopBarLayout.setRightBtnStr(btnStr);
				}
			}
		});
	}

	public ActionBarTopBarLayout getTopBar(){
		ActionBar actionBar = getSupportActionBar();
		if(actionBar != null && actionBar.getCustomView() != null) {
			return actionBar.getCustomView().findViewById(R.id.topBar);
		}
		return null;
	}

	@Override
	public Activity getActivity() {
		return this;
	}

	@Override
	public Context getContext() {
		return this;
	}

	@Override
	protected void onPause() {
		super.onPause();
		DialogFactory.dismiss();
	}

	@Override
	public void onRelease() {
		super.onRelease();
	}
}
