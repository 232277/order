package com.dyc.administrator.toollibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

/**
 * func:
 * author:丁语成 on 2020/2/12 17:41
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class ViewUtils {
	private static MLogger logger = MLogger.getLogger(ViewUtils.class);

	public static void showSnackbar(View view, int id) {
		Snackbar.make(view, id, 1000).show();
	}

	public static void hideKeyboard(Activity context) {
		InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isActive() && context.getCurrentFocus() != null && context.getCurrentFocus().getWindowToken() != null) {
			imm.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), 2);
		}
	}

	public void showSoftInputFromWindow(Activity context, EditText editText){
		editText.setFocusable(true);
		editText.setFocusableInTouchMode(true);
		editText.requestFocus();
		context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
	}

	public static void setBackgroundAlpha(Activity activity, float bgAlpha) {
		WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
		lp.alpha = bgAlpha;
		activity.getWindow().setAttributes(lp);
	}

	public static void getAndroiodScreenProperty(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		wm.getDefaultDisplay().getRealMetrics(dm);
		int width = dm.widthPixels;         // 屏幕宽度（像素）
		int height = dm.heightPixels;       // 屏幕高度（像素）
		float density = dm.density;         // 屏幕密度（0.75 / 1.0 / 1.5）
		int densityDpi = dm.densityDpi;     // 屏幕密度dpi（120 / 160 / 240）
		// 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
		int screenWidth = (int) (width / density);  // 屏幕宽度(dp)
		int screenHeight = (int) (height / density);// 屏幕高度(dp)

		DisplayMetrics dm2 = context.getResources().getDisplayMetrics();

		logger.info("屏幕宽度（像素）：" + width);
		logger.info("屏幕高度（像素）：" + height);
		logger.info("屏幕宽度（像素 context）：" + dm2.widthPixels);
		logger.info("屏幕高度（像素 context）：" + dm2.heightPixels);
		logger.info("屏幕密度（0.75 / 1.0 / 1.5）：" + density);
		logger.info("屏幕密度dpi（120 / 160 / 240）：" + densityDpi);
		logger.info("屏幕宽度（dp）：" + screenWidth);
		logger.info("屏幕高度（dp）：" + screenHeight);
	}
}