package com.dyc.order.cashier.mvp.activity.base;

import android.view.KeyEvent;

import com.dyc.order.cashier.base.iml.BaseContact;
import com.dyc.administrator.toollibrary.utils.KeyCodeUtil;

/**
 * func:
 * author:丁语成 on 2020/3/19 13:52
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public abstract class BaseOutScanActivity<
		M extends BaseContact.BaseModel,
		V extends BaseContact.BaseView,
		P extends BaseContact.BasePresent>
		extends BaseCountDownActivity<M,V,P> {
	protected KeyCodeUtil mKeyCodeToChar = new KeyCodeUtil();

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		int keyCode = event.getKeyCode();
		int acion = event.getAction();
		if (keyCode == KeyEvent.KEYCODE_ENTER && acion == KeyEvent.ACTION_DOWN) {
			//扫码完成后
			onInputFinish(mKeyCodeToChar.getEncode());
			mKeyCodeToChar.reset();
		} else {
			mKeyCodeToChar.append(keyCode, acion);
		}
		return super.dispatchKeyEvent(event);
	}

	public abstract void onInputFinish(String keySequence);
}
