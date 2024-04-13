package com.dyc.order.cashier.mvp.activity.base;

import android.view.KeyEvent;

import com.dyc.order.cashier.base.activity.BaseMvpActivityimpl;
import com.dyc.order.cashier.base.iml.BaseContact;
import com.dyc.simplemvplibrary.ActivityCollector;

/**
 * func:
 * author:丁语成 on 2020/3/25 17:00
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public abstract class BaseDesktopActivity<
		M extends BaseContact.BaseModel,
		V extends BaseContact.BaseView,
		P extends BaseContact.BasePresent
		> extends BaseMvpActivityimpl<M,V,P> {

	@Override
	public void onBackPressed() {
		if (ActivityCollector.getUnFinishActivitySize() > 2){
			super.onBackPressed();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_MENU){
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onKeyLongPress(int keyCode, KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_MENU){
			return true;
		}
		return super.onKeyLongPress(keyCode, event);
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		int keyCode = event.getKeyCode();
		if (keyCode == KeyEvent.KEYCODE_MENU || keyCode == KeyEvent.KEYCODE_HOME) {
			return true;
		}
		return super.dispatchKeyEvent(event);
	}
}
