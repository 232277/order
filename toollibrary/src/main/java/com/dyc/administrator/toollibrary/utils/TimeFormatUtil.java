package com.dyc.administrator.toollibrary.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * func:
 * author:丁语成 on 2020/5/29 17:46
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class TimeFormatUtil {

	public static String toFormatDate(String dateStr, String formatStr){
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		SimpleDateFormat format2=new SimpleDateFormat(formatStr, Locale.getDefault());
		Date d1 = null;
		try {
			d1 = format.parse(dateStr);
		} catch (Exception e) {
			return null;
		}
		return format2.format(d1);
	}
}
