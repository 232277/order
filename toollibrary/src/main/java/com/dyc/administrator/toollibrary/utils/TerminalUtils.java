package com.dyc.administrator.toollibrary.utils;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.lang.reflect.Method;
import java.util.List;

/**
 * func:
 * author:丁语成 on 2020/4/14 15:03
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class TerminalUtils {
	public static String getDeviceSN(){
		String serial = null;
		try {
			Class<?> c = Class.forName("android.os.SystemProperties");
			Method get =c.getMethod("get", String.class);
			serial = (String)get.invoke(c, "ro.serialno");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return serial;
	}
}
