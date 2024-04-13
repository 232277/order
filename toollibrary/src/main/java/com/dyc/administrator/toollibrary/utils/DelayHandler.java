package com.dyc.administrator.toollibrary.utils;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import java.util.PrimitiveIterator;


/**
 * func:
 * author:丁语成 on 2020/4/27 11:15
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class DelayHandler {
	private Handler handler;
	private HandlerThread handlerThread;
	private Runnable runnable;

	public DelayHandler(String threadName){
		handlerThread = new HandlerThread(threadName);
		handler = new Handler(handlerThread.getLooper());
	}

	public DelayHandler(HandlerThread handlerThread) {
		this.handlerThread = handlerThread;
		this.handler = new Handler(handlerThread.getLooper());
	}

	public void postDelayed(Runnable runnable){
		postDelayed(runnable, 2000);
	}

	public void postDelayed(Runnable runnable, long delay){
		if (runnable != null){
			handler.removeCallbacks(runnable);
		}
		this.runnable = runnable;
		handler.postDelayed(runnable, delay);
	}
}
