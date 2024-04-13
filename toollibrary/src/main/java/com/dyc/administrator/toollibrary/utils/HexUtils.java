package com.dyc.administrator.toollibrary.utils;

/**
 * func:
 * author:丁语成 on 2020/2/12 10:54
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class HexUtils {
	public HexUtils() {
	}

	public static byte[] hexStringToByte(String hex) {
		int len = hex.length() / 2;
		byte[] result = new byte[len];
		char[] achar = hex.toUpperCase().toCharArray();

		for(int i = 0; i < len; ++i) {
			int pos = i * 2;
			result[i] = (byte)(toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
		}

		return result;
	}

	public static final String bytesToHexString(byte[] bArray) {
		StringBuffer sb = new StringBuffer(bArray.length);
		int j = 0;

		for(int i = 0; i < bArray.length; ++i) {
			String sTemp = Integer.toHexString(255 & bArray[i]);
			if (sTemp.length() < 2) {
				sb.append(0);
			}

			sb.append(sTemp.toUpperCase());
			++j;
		}

		return sb.toString();
	}

	private static byte toByte(char c) {
		byte b = (byte)"0123456789ABCDEF".indexOf(c);
		return b;
	}

	public static byte[] int2bytes(int num) {
		byte[] b = new byte[4];

		for(int i = 0; i < 4; ++i) {
			b[i] = (byte)(num >>> 24 - i * 8);
		}

		return b;
	}

	public static int bytes2int(byte[] b) {
		int mask = 255;
		int res = 0;

		for(int i = 0; i < 4; ++i) {
			res <<= 8;
			int temp = b[i] & mask;
			res |= temp;
		}

		return res;
	}

	public static int bytes2short(byte[] b) {
		int mask = 255;
		int res = 0;

		for(int i = 0; i < 2; ++i) {
			res <<= 8;
			int temp = b[i] & mask;
			res |= temp;
		}

		return res;
	}

	public static String bcd2str(byte[] bcds) {
		char[] ascii = "0123456789abcdef".toCharArray();
		byte[] temp = new byte[bcds.length * 2];

		for(int i = 0; i < bcds.length; ++i) {
			temp[i * 2] = (byte)(bcds[i] >> 4 & 15);
			temp[i * 2 + 1] = (byte)(bcds[i] & 15);
		}

		StringBuffer res = new StringBuffer();

		for(int i = 0; i < temp.length; ++i) {
			res.append(ascii[temp[i]]);
		}

		return res.toString().toUpperCase();
	}

	public static byte[] str2bcd(String asc) {
		int len = asc.length();
		int mod = len % 2;
		if (mod != 0) {
			asc = "0" + asc;
			len = asc.length();
		}

		byte[] abt = new byte[len];
		if (len >= 2) {
			len /= 2;
		}

		byte[] bbt = new byte[len];
		abt = asc.getBytes();

		for(int p = 0; p < asc.length() / 2; ++p) {
			int j;
			if (abt[2 * p] >= 48 && abt[2 * p] <= 57) {
				j = abt[2 * p] - 48;
			} else if (abt[2 * p] >= 97 && abt[2 * p] <= 122) {
				j = abt[2 * p] - 97 + 10;
			} else {
				j = abt[2 * p] - 65 + 10;
			}

			int k;
			if (abt[2 * p + 1] >= 48 && abt[2 * p + 1] <= 57) {
				k = abt[2 * p + 1] - 48;
			} else if (abt[2 * p + 1] >= 97 && abt[2 * p + 1] <= 122) {
				k = abt[2 * p + 1] - 97 + 10;
			} else {
				k = abt[2 * p + 1] - 65 + 10;
			}

			int a = (j << 4) + k;
			byte b = (byte)a;
			bbt[p] = b;
		}

		return bbt;
	}
}