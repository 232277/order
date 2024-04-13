package com.dyc.administrator.toollibrary.utils;

import android.util.Log;

import com.centerm.administrator.toollibrary.R;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Locale;

import static com.blankj.utilcode.util.StringUtils.getString;

/**
 * func:
 * author:丁语成 on 2020/3/19 17:02
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class NumUtils {
	private final static DecimalFormat decimalFormat = new DecimalFormat(getString(R.string.toolibrary_double_2point_format));

	public static double remain2Num(double num){
		BigDecimal bg = new BigDecimal(num);
		return bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

		public static String remain2Zero(double num){
			return String.format(Locale.getDefault(), "%.2f", num);
		}

	public static String remain2NumWithoutZero(double num){
		return decimalFormat.format(num);
	}

	public static String extendToTwelve(double num){
		String remainTwo = remain2Zero(num);
		StringBuilder res = new StringBuilder();
		res.append(remainTwo.replace(".", ""));
		if (res.length() < 12){
			int lenNow = res.length();
			for (int i = 0; i < 12 - lenNow; ++i){
				res.insert(0, 0);
			}
		}
		return res.toString();
	}

	public static String getIntegerPart(double num){
		String str = remain2NumWithoutZero(num);
		int pos = str.indexOf(".");
		if (pos != -1){
			return str.substring(0, pos + 1);
		}else {
			return str;
		}
	}

	public static String getPointPart(double num){
		String str = remain2NumWithoutZero(num);
		int pos = str.indexOf(".");
		if (pos != -1){
			return str.substring(pos + 1);
		}else {
			return "";
		}
	}
}
