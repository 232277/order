package com.dyc.administrator.toollibrary.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.mindpipe.android.logging.log4j.LogConfigurator;

/**
 * func:
 * author:丁语成 on 2020/2/13 11:32
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class MLogger {
	private static final byte[] RANDOM = new byte[]{17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17};
	private static final int PACKET_SIZE = 16;
	private static final String TAG = "MLogger";
	private static String lastConfigDate = "";
	private static String packageName;
	private static String cachePath;
	private static String logPath;
	private Logger inner;
	private boolean isEncrypt;
	private static int logLevel;
	private static boolean isConfig;

	MLogger() {
	}

	public static void config(Context context) {
		if (context == null) {
			throw new IllegalArgumentException("Context cannot be null");
		} else {
			packageName = context.getPackageName();
			if (context.getExternalCacheDir() != null) {
				cachePath = context.getExternalCacheDir().toString();
			} else {
				cachePath = context.getCacheDir().toString();
			}

			Log.d("MLogger", "Package name : " + packageName);
			Log.d("MLogger", "Cache path : " + cachePath);
			isConfig = true;
		}
	}

	public static void config(Context context, String savedPath) {
		config(context);
		logPath = savedPath;
	}

	public static void config(Context context, String savedPath, int level) {
		config(context);
		logLevel = level;
		logPath = savedPath;
	}

	public static MLogger getLogger(String tag) {
		MLogger logger = new MLogger();
		logger.inner = Logger.getLogger(tag);
		if (packageName == null) {
			throw new IllegalStateException("You must be call [config] first!");
		} else {
			return logger;
		}
	}

	public static MLogger getLogger(Class clazz) {
		MLogger logger = new MLogger();
		logger.inner = Logger.getLogger(clazz);
		if (packageName == null) {
			throw new IllegalStateException("You must be call [config] first!");
		} else {
			return logger;
		}
	}

	public static MLogger getEncipherLogger(String tag) {
		MLogger logger = getLogger(tag);
		logger.isEncrypt = true;
		return logger;
	}

	public static MLogger getEncipherLogger(Class clazz) {
		MLogger logger = getLogger(clazz);
		logger.isEncrypt = true;
		return logger;
	}

	public void debug(Object message) {
		checkConfig();
		this.inner.debug(this.getMsg(message));
	}

	public void debug(Object message, Throwable t) {
		checkConfig();
		this.inner.debug(this.getMsg(message), t);
	}

	public void info(Object message) {
		checkConfig();
		this.inner.info(this.getMsg(message));
	}

	public void info(Object message, Throwable t) {
		checkConfig();
		this.inner.info(this.getMsg(message), t);
	}

	public void warn(Object message) {
		checkConfig();
		this.inner.warn(this.getMsg(message));
	}

	public void warn(Object message, Throwable t) {
		checkConfig();
		this.inner.warn(this.getMsg(message), t);
	}

	public void error(Object message) {
		checkConfig();
		this.inner.error(this.getMsg(message));
	}

	public void error(Object message, Throwable t) {
		checkConfig();
		this.inner.error(this.getMsg(message), t);
	}

	private Object getMsg(Object msg) {
		if (!this.isEncrypt) {
			return msg;
		} else {
			return msg == null ? null : encryptMsg(msg);
		}
	}

	public static String encryptMsg(Object msg) {
		String strMsg = msg.toString();
		int len = strMsg.length();
		int mod = len % 16;
		if (mod != 0) {
			strMsg = String.format("%" + (len + 16 - mod) + "s", strMsg);
		}

		byte[] bytes = strMsg.getBytes();
		int counts = bytes.length / 16;
		StringBuilder builder = new StringBuilder();

		for(int i = 0; i < counts; ++i) {
			byte[] data = new byte[16];
			System.arraycopy(bytes, i * 16, data, 0, 16);
			builder.append(HexUtils.bytesToHexString(SecurityUtils.encrypt3DES(RANDOM, data)));
		}

		return builder.toString();
	}

	public static void setLevel(int level) {
		logLevel = level;
		notifyConfigChanged();
	}

	public static boolean isConfig() {
		return isConfig;
	}

	private static LogConfigurator newConfigurator() {
		LogConfigurator configurator = new LogConfigurator();
		configurator.setRootLevel(Level.ALL);
		configurator.setLevel("org.apache", Level.DEBUG);
		configurator.setFilePattern("%d %-5p %10t [%c{3}] %m%n");
		configurator.setLogCatPattern("%m%n");
		configurator.setMaxFileSize(2097152L);
		configurator.setMaxBackupSize(10);
		configurator.setImmediateFlush(true);
		configurator.setUseLogCatAppender(true);
		configurator.setUseFileAppender(true);
		configurator.setResetConfiguration(true);
		configurator.setInternalDebugging(false);
		return configurator;
	}

	private static void checkConfig() {
		String var0 = "MLogger";
		synchronized("MLogger") {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String today = formatter.format(new Date());
			if (TextUtils.isEmpty(lastConfigDate) || !today.equals(lastConfigDate)) {
				Log.i("MLogger", "Trigger log configuration! >>> " + Thread.currentThread());
				String directory = logPath;
				String fileName = today + "_.log";
				lastConfigDate = today;
				LogConfigurator configurator = newConfigurator();
				if (TextUtils.isEmpty(directory)) {
					directory = cachePath;
				}

				configurator.setFileName(directory + File.separator + fileName);
				Level level;
				switch(logLevel) {
					case 1:
						level = Level.DEBUG;
						break;
					case 2:
						level = Level.INFO;
						break;
					case 3:
						level = Level.WARN;
						break;
					case 4:
						level = Level.ERROR;
						break;
					default:
						level = Level.ALL;
				}

				configurator.setRootLevel(level);
				configurator.configure();
			}

		}
	}

	private static void notifyConfigChanged() {
		lastConfigDate = null;
		checkConfig();
	}
}

