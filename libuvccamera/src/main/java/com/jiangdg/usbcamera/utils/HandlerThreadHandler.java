package com.jiangdg.usbcamera.utils;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

/**
 * func:
 * author:丁语成 on 2020/4/23 22:44
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */

public class HandlerThreadHandler extends Handler {
	private static final String TAG = "HandlerThreadHandler";

	public static final HandlerThreadHandler createHandler() {
		return createHandler("HandlerThreadHandler");
	}

	public static final HandlerThreadHandler createHandler(String name) {
		HandlerThread thread = new HandlerThread(name);
		thread.start();
		return new HandlerThreadHandler(thread.getLooper());
	}

	public static final HandlerThreadHandler createHandler(Callback callback) {
		return createHandler("HandlerThreadHandler", callback);
	}

	public static final HandlerThreadHandler createHandler(String name, Callback callback) {
		HandlerThread thread = new HandlerThread(name);
		thread.start();
		return new HandlerThreadHandler(thread.getLooper(), callback);
	}

	private HandlerThreadHandler(Looper looper) {
		super(looper);
	}

	private HandlerThreadHandler(Looper looper, Callback callback) {
		super(looper, callback);
	}
}

