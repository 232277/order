package com.dyc.administrator.toollibrary.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.blankj.utilcode.util.StringUtils;
import com.dyc.administrator.toollibrary.view.LoadProgressDialog;
import com.dyc.administrator.toollibrary.view.NotifyDialog;

/**
 * func:
 * author:丁语成 on 2020/2/11 14:19
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class DialogFactory {
	private static Dialog dialog;

	public static void showNotifyDialog(Context context, int msgId, int imgId, DialogInterface.OnDismissListener dismissListener){
		showNotifyDialog(context, StringUtils.getString(msgId), imgId, dismissListener);
	}

	public static void showNotifyDialog(Context context, CharSequence msg, int imgId, DialogInterface.OnDismissListener dismissListener){
		showNotifyDialog(context, msg, imgId, 2, dismissListener);
	}

	public static void showNotifyDialog(Context context, int msgId, int imgId){
		showNotifyDialog(context, StringUtils.getString(msgId), imgId);
	}

	public static void showNotifyDialog(Context context, CharSequence msg, int imgId){
		showNotifyDialog(context, msg, imgId, 2, null);
	}

	public static void showNotifyDialog(Context context, CharSequence msg, int imgId, long existTime, DialogInterface.OnDismissListener dismissListener){
		dialog  = new NotifyDialog(context, msg, imgId, existTime);
		dialog.setOnDismissListener(dismissListener);
		show();
	}

	public static void showMessageDialog(Context context, int titleId, int msgId){
		showMessageDialog(context, StringUtils.getString(titleId), StringUtils.getString(msgId));
	}

	public static void showMessageDialog(Context context, CharSequence title, CharSequence msg){
		dialog = new AlertDialog.Builder(context)
				.setTitle("提示")
				.setMessage(msg)
				.setPositiveButton("确定", (dialog1, which) -> {
					dialog1.dismiss();
				}).create();
		show();
	}

	public static void updatePrograssDialogImg(Drawable drawable){
		((LoadProgressDialog)dialog).setDrawable(drawable);
	}

	public static void updatePrograssDialogMsg(int msgId){
		updatePrograssDialogMsg(StringUtils.getString(msgId));
	}

	public static void updatePrograssDialogMsg(CharSequence msg){
		((LoadProgressDialog)dialog).setMessage(msg);
	}

	public static void showPrograssDialog(Context context, int msgId, DialogInterface.OnDismissListener dismissListener){
		showPrograssDialog(context, StringUtils.getString(msgId), dismissListener);
	}

	public static void showPrograssDialog(Context context, int msgId){
		showPrograssDialog(context, StringUtils.getString(msgId));
	}

	public static void showPrograssDialog(Context context, CharSequence msg){
		showPrograssDialog(context, msg, null);
	}

	public static void showPrograssDialog(Context context, CharSequence msg, DialogInterface.OnDismissListener dismissListener){
		dialog = new LoadProgressDialog(context, msg);
		dialog.setOnDismissListener(dismissListener);
		show();
	}

	public static void show(){
		if (dialog != null && !dialog.isShowing()){
			dialog.show();
		}
	}

	public static void dismiss(){
		if (dialog != null){
			dialog.dismiss();
		}
	}

	public static void setDialogWidthAndHeight(Context context, Dialog dialog, int widthDp, int heightDp){
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels;// 屏幕宽度（像素）
		int height= dm.heightPixels; // 屏幕高度（像素）
		float density = dm.density;//屏幕密度（0.75 / 1.0 / 1.5）
		int densityDpi = dm.densityDpi;//屏幕密度dpi（120 / 160 / 240）
		WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
		layoutParams.width = (int) (widthDp*densityDpi/160);
		layoutParams.height = (int) (heightDp*densityDpi/160);
		dialog.getWindow().setAttributes(layoutParams);
	}
}
