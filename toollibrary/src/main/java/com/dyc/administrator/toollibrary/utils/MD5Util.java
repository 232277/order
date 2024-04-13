package com.dyc.administrator.toollibrary.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * func:
 * author:丁语成 on 2020/2/12 10:53
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class MD5Util {
	protected static MessageDigest messagedigest = null;
	protected static char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

	public MD5Util() {
	}

	public static String getStringMD5(String str) {
		return getMD5String(str.getBytes());
	}

	public static boolean checkStringMD5(String str, String md5Str) {
		String md5 = getStringMD5(str);
		return md5.equals(md5Str.trim());
	}

	public static String getFileMD5(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		byte[] buffer = new byte[1048576];
		boolean var3 = false;

		int numRead;
		while((numRead = fis.read(buffer)) > 0) {
			messagedigest.update(buffer, 0, numRead);
		}

		fis.close();
		return bufferToHex(messagedigest.digest());
	}

	public static boolean checkFileMD5(File file, String md5Str) {
		String md5 = null;

		try {
			md5 = getFileMD5(file);
		} catch (IOException var4) {
			var4.printStackTrace();
		}

		return md5 == null ? false : md5.equals(md5Str.trim());
	}

	public static String getMD5String(byte[] bytes) {
		messagedigest.update(bytes);
		return bufferToHex(messagedigest.digest());
	}

	private static String bufferToHex(byte[] bytes) {
		return bufferToHex(bytes, 0, bytes.length);
	}

	private static String bufferToHex(byte[] bytes, int m, int n) {
		StringBuffer stringbuffer = new StringBuffer(2 * n);
		int k = m + n;

		for(int l = m; l < k; ++l) {
			appendHexPair(bytes[l], stringbuffer);
		}

		return stringbuffer.toString();
	}

	private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
		char c0 = hexDigits[(bt & 240) >> 4];
		char c1 = hexDigits[bt & 15];
		stringbuffer.append(c0);
		stringbuffer.append(c1);
	}

	static {
		try {
			messagedigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException var1) {
			var1.printStackTrace();
		}

	}
}