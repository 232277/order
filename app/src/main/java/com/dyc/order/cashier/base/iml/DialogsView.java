package com.dyc.order.cashier.base.iml;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;

import com.blankj.utilcode.util.StringUtils;
import com.dyc.order.cashier.base.MyApplication;
import com.dyc.administrator.toollibrary.utils.DialogFactory;

/**
 * func:
 * author:丁语成 on 2020/2/24 17:26
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public interface DialogsView {
	void showToast(String msg);

	default void showToast(int msgId){
		if (msgId > 0){
			showToast(MyApplication.getContext().getString(msgId));
		}
	}

	default void showNotifyDialog(Context context, int msgId, int imgId, DialogInterface.OnDismissListener dismissListener){
		showNotifyDialog(context, StringUtils.getString(msgId), imgId, dismissListener);
	}

	default void showNotifyDialog(Context context, CharSequence msg, int imgId, DialogInterface.OnDismissListener dismissListener){
		showNotifyDialog(context, msg, imgId, 2, dismissListener);
	}

	void showNotifyDialog(Context context, CharSequence msg, int imgId, long existTime, DialogInterface.OnDismissListener dismissListener);

	void showNotifyDialog(int textId, int imgId);

	default void showNotifyDialog(CharSequence text, int imgId){
		showNotifyDialog(text, imgId, 2);
	}

	void showNotifyDialog(CharSequence text, int imgId, long existTime);

	void showMessageDialog(CharSequence title, CharSequence msg);

	default void showMessageDialog(int titleId, int msgId){
		showMessageDialog(MyApplication.getContext().getString(titleId), MyApplication.getContext().getString(msgId));
	}

	default void updateLoadingDrawable(int drawableId){
		updateLoadingDrawable(MyApplication.getContext().getDrawable(drawableId));
	}

	void updateLoadingDrawable(Drawable drawable);
	
	void updateLoadingMsg(CharSequence str);
	
	default void updateLoadingMsg(int msgId){
		updateLoadingMsg(StringUtils.getString(msgId));
	}

	default void showLoading(CharSequence msg){
		showLoading(msg, null);
	}

	default void showLoading(int msgId){
		showLoading(MyApplication.getContext().getString(msgId));
	}

	default void showLoading(int msgId, DialogInterface.OnDismissListener dismissListener){
		showLoading(StringUtils.getString(msgId), dismissListener);
	}

	void showLoading(CharSequence msg, DialogInterface.OnDismissListener dismissListener);

	default void hideDialog(){
		DialogFactory.dismiss();
	}
}
