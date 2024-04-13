package com.dyc.administrator.toollibrary.utils;

import android.content.Context;

/**
 * func:
 * author:丁语成 on 2020/3/13 11:07
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class DipUtil {
	public static int dip2px(Context context, float dipValue) {
		float m=context.getResources().getDisplayMetrics().density ;
		return (int)(dipValue * m + 0.5f) ;
	}

	public static int px2dip(Context context, float pxValue) {
		float m=context.getResources().getDisplayMetrics().density ;
		return (int)(pxValue / m + 0.5f) ;
	}

	/**
	 * 将px值转换为sp值，保证文字大小不变
	 * DisplayMetrics类中属性scaledDensity
	 */
	public static int px2sp(Context context, float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 * DisplayMetrics类中属性scaledDensity
	 */
	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}
}
