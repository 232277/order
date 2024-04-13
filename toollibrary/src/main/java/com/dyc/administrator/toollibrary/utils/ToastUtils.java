package com.dyc.administrator.toollibrary.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * func:
 * author:丁语成 on 2020/2/12 17:36
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class ToastUtils {
	private static Toast toast = null;

	public ToastUtils() {
	}

	public static Toast showToast(Context context, String text) {
		if (toast == null) {
			toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		} else {
			toast.setText(text);
			toast.setDuration(Toast.LENGTH_SHORT);
		}

		toast.show();
		return toast;
	}

	public static Toast showToast(Context context, int id) {
		if (toast == null) {
			toast = Toast.makeText(context, id, Toast.LENGTH_SHORT);
		} else {
			toast.setText(id);
			toast.setDuration(Toast.LENGTH_SHORT);
		}

		toast.show();
		return toast;
	}
}