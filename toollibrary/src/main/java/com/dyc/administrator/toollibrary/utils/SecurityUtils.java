package com.dyc.administrator.toollibrary.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * func:
 * author:丁语成 on 2020/2/13 11:33
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class SecurityUtils {
	private static final String ALGORITHM = "DES";
	private static String ZERO16 = "0000000000000000";

	public SecurityUtils() {
	}

	public static String encryptDES(String key, String data) {
		if (key != null && data != null) {
			if (key.length() == 16 && data.length() % 16 == 0) {
				return bcd2str(encryptDES(hexStringToByte(key.toUpperCase()), hexStringToByte(data.toUpperCase())));
			} else {
				throw new IllegalArgumentException("Input params is illegal. key:" + key + " >>> data:" + data);
			}
		} else {
			throw new IllegalArgumentException("Key or data cannot be null");
		}
	}

	public static byte[] encryptDES(byte[] key, byte[] data) {
		try {
			SecretKey deskey = new SecretKeySpec(key, "DES");
			Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
			cipher.init(1, deskey);
			return cipher.doFinal(data);
		} catch (Exception var4) {
			var4.printStackTrace();
			return null;
		}
	}

	public static String decryptDES(String key, String data) {
		return bcd2str(decryptDES(hexStringToByte(key.toUpperCase()), hexStringToByte(data.toUpperCase())));
	}

	public static byte[] decryptDES(byte[] key, byte[] data) {
		try {
			SecretKey deskey = new SecretKeySpec(key, "DES");
			Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
			cipher.init(2, deskey);
			return cipher.doFinal(data);
		} catch (Exception var4) {
			var4.printStackTrace();
			return null;
		}
	}

	public static String encrypt3DES(String key, String source) {
		return bcd2str(encrypt3DES(hexStringToByte(key.toUpperCase()), hexStringToByte(source.toUpperCase())));
	}

	public static byte[] encrypt3DES(byte[] key, byte[] source) {
		byte[] cursorSourceBytes = new byte[8];
		System.arraycopy(source, 0, cursorSourceBytes, 0, 8);
		byte[] keyLeft = new byte[8];
		System.arraycopy(key, 0, keyLeft, 0, 8);
		byte[] keyRight = new byte[8];
		System.arraycopy(key, 8, keyRight, 0, 8);
		byte[] encryptResultBytes = encryptDES(keyLeft, cursorSourceBytes);
		byte[] decryptResultbytes = decryptDES(keyRight, encryptResultBytes);
		byte[] cursorResultBytes = encryptDES(keyLeft, decryptResultbytes);
		if (source.length > 8) {
			byte[] tempSourceBytes = new byte[source.length - 8];
			System.arraycopy(source, 8, tempSourceBytes, 0, source.length - 8);
			byte[] subRelultBytes = encrypt3DES(key, tempSourceBytes);
			byte[] resultBytes = new byte[cursorResultBytes.length + subRelultBytes.length];
			System.arraycopy(cursorResultBytes, 0, resultBytes, 0, cursorResultBytes.length);
			System.arraycopy(subRelultBytes, 0, resultBytes, cursorResultBytes.length, subRelultBytes.length);
			return resultBytes;
		} else {
			return cursorResultBytes;
		}
	}

	public static String decrypt3DES(String key, String source) {
		return bcd2str(decrypt3DES(hexStringToByte(key.toUpperCase()), hexStringToByte(source.toUpperCase())));
	}

	public static byte[] decrypt3DES(byte[] key, byte[] source) {
		byte[] keyleft = new byte[8];
		System.arraycopy(key, 0, keyleft, 0, 8);
		byte[] keyright = new byte[8];
		System.arraycopy(key, 8, keyright, 0, 8);
		byte[] cursorSrouceBytes = new byte[8];
		System.arraycopy(source, 0, cursorSrouceBytes, 0, 8);
		byte[] leftencrypt1 = decryptDES(keyleft, cursorSrouceBytes);
		byte[] rightdecrypt2 = encryptDES(keyright, leftencrypt1);
		byte[] leftencrypt3 = decryptDES(keyleft, rightdecrypt2);
		if (source.length > 8) {
			byte[] subSourceBytes = new byte[source.length - 8];
			System.arraycopy(source, 8, subSourceBytes, 0, source.length - 8);
			byte[] subResultBytes = decrypt3DES(key, subSourceBytes);
			byte[] resultBytes = new byte[subResultBytes.length + leftencrypt3.length];
			System.arraycopy(leftencrypt3, 0, resultBytes, 0, leftencrypt3.length);
			System.arraycopy(subResultBytes, 0, resultBytes, leftencrypt3.length, subResultBytes.length);
			return resultBytes;
		} else {
			return leftencrypt3;
		}
	}

	public static String ansiDESMac(String key, String vector, String source, boolean isHex) {
		return mac(isHex ? hexStringToByte(source.toUpperCase()) : source.getBytes(), hexStringToByte(key.toUpperCase()), hexStringToByte(vector.toUpperCase()));
	}

	public static byte[] pboc3DesMac(byte[] source, byte[] key, byte[] vector) {
		byte[] orginal = vector;
		byte[] leftKey = new byte[8];
		byte[] rightKey = new byte[8];
		System.arraycopy(key, 8, rightKey, 0, 8);
		System.arraycopy(key, 0, leftKey, 0, 8);

		byte[] encryptLeftKeySrc;
		for(int i = 0; i < source.length; i += 8) {
			encryptLeftKeySrc = new byte[8];
			System.arraycopy(source, i, encryptLeftKeySrc, 0, encryptLeftKeySrc.length);
			orginal = xor(orginal, encryptLeftKeySrc);
			orginal = encryptDES(leftKey, orginal);
		}

		byte[] debyrightKeySrc = decryptDES(rightKey, orginal);
		encryptLeftKeySrc = encryptDES(leftKey, debyrightKeySrc);
		byte[] result = new byte[8];
		System.arraycopy(encryptLeftKeySrc, 0, result, 0, 8);
		return result;
	}

	public static String mac(byte[] source, byte[] key, byte[] vector) {
		byte[] cursorSourceBytes = new byte[8];
		System.arraycopy(source, 0, cursorSourceBytes, 0, source.length > 8 ? 8 : source.length);
		byte[] sourceLeftXor = xor(cursorSourceBytes, vector);
		byte[] sourceLeftEncrypt = encryptDES(key, sourceLeftXor);
		if (source.length > 8) {
			byte[] tempBytes = new byte[source.length - 8];
			System.arraycopy(source, 8, tempBytes, 0, source.length - 8);
			return mac(tempBytes, key, sourceLeftEncrypt);
		} else {
			return bcd2str(sourceLeftEncrypt);
		}
	}

	public static String pbocMacDES(String key, String vector, String source, boolean isHex) {
		byte[] sourceFilledBytes = fillBytes(isHex ? hexStringToByte(source.toUpperCase()) : source.getBytes());
		return mac(sourceFilledBytes, hexStringToByte(key.toUpperCase()), hexStringToByte(vector.toUpperCase()));
	}

	public static byte[] fillBytes(byte[] sourceBytes) {
		int mod = sourceBytes.length % 8;
		byte[] sourceFilledBytes = new byte[sourceBytes.length + (8 - mod)];
		System.arraycopy(sourceBytes, 0, sourceFilledBytes, 0, sourceBytes.length);
		if (mod == 0) {
			byte[] fillBytes = hexStringToByte("8000000000000000");
			System.arraycopy(fillBytes, 0, sourceFilledBytes, sourceBytes.length, fillBytes.length);
		} else {
			for(int i = 0; i < 8 - mod; ++i) {
				sourceFilledBytes[sourceBytes.length + i] = hexStringToByte(i == 0 ? "80" : "00")[0];
			}
		}

		return sourceFilledBytes;
	}

	public static String ansiMac3DES(String source, String key, String vector, boolean isHex) {
		return mac3Des(isHex ? hexStringToByte(source.toUpperCase()) : source.getBytes(), hexStringToByte(key.toUpperCase()), hexStringToByte(vector.toUpperCase()));
	}

	public static String pbocMac3DES(String key, String vector, String source, boolean isHex) {
		byte[] sourceFilledBytes = fillBytes(isHex ? hexStringToByte(source.toUpperCase()) : source.getBytes());
		return mac3Des(sourceFilledBytes, hexStringToByte(key.toUpperCase()), hexStringToByte(vector.toUpperCase()));
	}

	public static String mac3Des(byte[] source, byte[] key, byte[] vector) {
		byte[] cursorSourceBytes = new byte[8];
		System.arraycopy(source, 0, cursorSourceBytes, 0, source.length >= 8 ? 8 : source.length);
		byte[] cursorSourceXor = xor(cursorSourceBytes, vector);
		if (source.length > 8) {
			byte[] cursorKey = new byte[8];
			System.arraycopy(key, 0, cursorKey, 0, 8);
			byte[] sourceLeftEncrypt = encryptDES(cursorKey, cursorSourceXor);
			byte[] tempBytes = new byte[source.length - 8];
			System.arraycopy(source, 8, tempBytes, 0, source.length - 8);
			return mac3Des(tempBytes, key, sourceLeftEncrypt);
		} else {
			return bcd2str(encrypt3DES(key, cursorSourceXor));
		}
	}

	public static String diversify(String key, String source) {
		return bcd2str(diversify(hexStringToByte(key.toUpperCase()), hexStringToByte(source.toUpperCase())));
	}

	public static byte[] diversify(byte[] key, byte[] source) {
		byte[] cursorSourceBytes = new byte[8];
		System.arraycopy(source, 0, cursorSourceBytes, 0, source.length > 8 ? 8 : source.length);
		byte[] leftDivBytes = encrypt3DES(key, cursorSourceBytes);

		for(int i = 0; i < cursorSourceBytes.length; ++i) {
			cursorSourceBytes[i] = (byte)(~cursorSourceBytes[i]);
		}

		byte[] rightDivBytes = encrypt3DES(key, cursorSourceBytes);
		byte[] resultBytes = new byte[leftDivBytes.length + rightDivBytes.length];
		System.arraycopy(leftDivBytes, 0, resultBytes, 0, leftDivBytes.length);
		System.arraycopy(rightDivBytes, 0, resultBytes, leftDivBytes.length, rightDivBytes.length);
		if (source.length > 8) {
			byte[] tempBytes = new byte[source.length - 8];
			System.arraycopy(source, 8, tempBytes, 0, source.length - 8);
			return diversify(resultBytes, tempBytes);
		} else {
			return resultBytes;
		}
	}

	public static String diversifyByDoubleOneWay(String source, String key) {
		return diversifyDouble(source, key);
	}

	private static String diversifyDouble(String source, String key) {
		byte[] keyleft = hexStringToByte(key.substring(0, key.length() / 2).toUpperCase());
		byte[] keyright = hexStringToByte(key.substring(key.length() / 2).toUpperCase());
		byte[] sourceBytes = hexStringToByte(source.toUpperCase());
		byte[] sourceUnDes = decryptDES(keyleft, sourceBytes);
		byte[] sourceunDesDes = encryptDES(keyright, sourceUnDes);
		byte[] sourceunDesDesUnDes = decryptDES(keyleft, sourceunDesDes);
		byte[] keyleftXor = xor(sourceBytes, sourceunDesDesUnDes);
		return bcd2str(keyleftXor);
	}

	public static String xor(String xor1, String xor2) {
		return bcd2str(xor(hexStringToByte(xor1), hexStringToByte(xor2)));
	}

	public static byte[] xor(byte[] hexSource1, byte[] hexSource2) {
		int length = hexSource1.length;
		byte[] xor = new byte[length];

		for(int i = 0; i < length; ++i) {
			xor[i] = (byte)(hexSource1[i] ^ hexSource2[i]);
		}

		return xor;
	}

	private static String bcd2str(byte[] bcds) {
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

	private static byte[] hexStringToByte(String hex) {
		int len = hex.length() / 2;
		byte[] result = new byte[len];
		char[] achar = hex.toCharArray();

		for(int i = 0; i < len; ++i) {
			int pos = i * 2;
			result[i] = (byte)(toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
		}

		return result;
	}

	public static String encryptECB(String key, String source) {
		return bcd2str(encryptECB(hexStringToByte(key.toUpperCase()), hexStringToByte(source.toUpperCase())));
	}

	public static byte[] encryptECB(byte[] key, byte[] source) {
		byte[] cursorSourntBytes = new byte[8];
		System.arraycopy(source, 0, cursorSourntBytes, 0, source.length > 8 ? 8 : source.length);
		byte[] currorEncryptResult = key.length > 8 ? encrypt3DES(key, cursorSourntBytes) : encryptDES(key, cursorSourntBytes);
		if (source.length > 8) {
			byte[] nextSource = new byte[source.length - 8];
			System.arraycopy(source, 8, nextSource, 0, source.length - 8);
			byte[] subEncryptResult = encryptECB(key, nextSource);
			byte[] encryptResult = new byte[currorEncryptResult.length + subEncryptResult.length];
			System.arraycopy(currorEncryptResult, 0, encryptResult, 0, currorEncryptResult.length);
			System.arraycopy(subEncryptResult, 0, encryptResult, currorEncryptResult.length, subEncryptResult.length);
			return encryptResult;
		} else {
			return currorEncryptResult;
		}
	}

	public static String decryptECB(String key, String source) {
		return bcd2str(decryptECB(hexStringToByte(key.toUpperCase()), hexStringToByte(source.toUpperCase())));
	}

	public static byte[] decryptECB(byte[] key, byte[] source) {
		byte[] cursorSourntBytes = new byte[8];
		System.arraycopy(source, 0, cursorSourntBytes, 0, source.length > 8 ? 8 : source.length);
		byte[] currorDecryptResult = key.length > 8 ? decrypt3DES(key, cursorSourntBytes) : decryptDES(key, cursorSourntBytes);
		if (source.length > 8) {
			byte[] nextSource = new byte[source.length - 8];
			System.arraycopy(source, 8, nextSource, 0, source.length - 8);
			byte[] subEncryptResult = decryptECB(key, nextSource);
			byte[] encryptResult = new byte[currorDecryptResult.length + subEncryptResult.length];
			System.arraycopy(currorDecryptResult, 0, encryptResult, 0, currorDecryptResult.length);
			System.arraycopy(subEncryptResult, 0, encryptResult, currorDecryptResult.length, subEncryptResult.length);
			return encryptResult;
		} else {
			return currorDecryptResult;
		}
	}

	public static String encryptCBC(String key, String vector, String source) {
		return bcd2str(encryptCBC(hexStringToByte(key.toUpperCase()), hexStringToByte(vector != null ? vector : "0000000000000000"), hexStringToByte(source.toUpperCase())));
	}

	public static byte[] encryptCBC(byte[] key, byte[] vector, byte[] source) {
		byte[] cursorSourntBytes = new byte[8];
		System.arraycopy(source, 0, cursorSourntBytes, 0, source.length > 8 ? 8 : source.length);
		byte[] xorResultBytes = xor(cursorSourntBytes, vector);
		byte[] currorEncryptResult = key.length > 8 ? encrypt3DES(key, xorResultBytes) : encryptDES(key, xorResultBytes);
		if (source.length > 8) {
			byte[] nextSource = new byte[source.length - 8];
			System.arraycopy(source, 8, nextSource, 0, source.length - 8);
			byte[] subEncryptResult = encryptCBC(key, currorEncryptResult, nextSource);
			byte[] encryptResult = new byte[currorEncryptResult.length + subEncryptResult.length];
			System.arraycopy(currorEncryptResult, 0, encryptResult, 0, currorEncryptResult.length);
			System.arraycopy(subEncryptResult, 0, encryptResult, currorEncryptResult.length, subEncryptResult.length);
			return encryptResult;
		} else {
			return currorEncryptResult;
		}
	}

	public static String decryptCBC(String key, String vector, String source) {
		return bcd2str(decryptCBC(hexStringToByte(key.toUpperCase()), hexStringToByte(vector != null ? vector : "0000000000000000"), hexStringToByte(source.toUpperCase())));
	}

	public static byte[] decryptCBC(byte[] key, byte[] vector, byte[] source) {
		byte[] decryptBytes = new byte[8];
		System.arraycopy(source, 0, decryptBytes, 0, source.length > 8 ? 8 : source.length);
		byte[] decryptResult = key.length > 8 ? decrypt3DES(key, decryptBytes) : decryptDES(key, decryptBytes);
		byte[] result = xor(decryptResult, vector);
		if (source.length > 8) {
			byte[] nextSource = new byte[source.length - 8];
			System.arraycopy(source, 8, nextSource, 0, source.length - 8);
			byte[] subDecryptResult = decryptCBC(key, decryptBytes, nextSource);
			byte[] encryptResult = new byte[result.length + subDecryptResult.length];
			System.arraycopy(result, 0, encryptResult, 0, result.length);
			System.arraycopy(subDecryptResult, 0, encryptResult, result.length, subDecryptResult.length);
			return encryptResult;
		} else {
			return result;
		}
	}

	private static byte toByte(char c) {
		byte b = (byte)"0123456789ABCDEF".indexOf(c);
		return b;
	}

	public static boolean isHexademical(String name, String value, int length) throws Exception {
		if (null == value || value.length() != length && length > 0) {
			throw new Exception(name + "长度应为" + length);
		} else {
			String texts = "0123456789abcdefABCDEF";
			int len = value.length();

			for(int i = 0; i < len; ++i) {
				if (texts.indexOf(value.charAt(i)) == -1) {
					throw new Exception(name + "包含的字符应为16进制字符");
				}
			}

			return true;
		}
	}

	public static boolean isAlphanumeric(String name, String value, int length) throws Exception {
		if (null == value || value.length() != length && length > 0) {
			throw new Exception(name + "长度应为" + length);
		} else {
			String texts = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
			int len = value.length();

			for(int i = 0; i < len; ++i) {
				if (texts.indexOf(value.charAt(i)) == -1) {
					throw new Exception(name + "包含的字符应为数字或字母");
				}
			}

			return true;
		}
	}

	public static String xorDes(String source, String key) {
		return xorDes(hexStringToByte(source.toUpperCase()), hexStringToByte(key.toUpperCase()));
	}

	public static String xorDes(byte[] source, byte[] key) {
		int position = 0;
		byte[] oper1 = new byte[8];
		System.arraycopy(source, position, oper1, 0, 8);
		position = position + 8;

		for(int i = 1; i < source.length / 8; ++i) {
			byte[] oper2 = new byte[8];
			System.arraycopy(source, position, oper2, 0, 8);
			oper1 = xor(oper1, oper2);
			position += 8;
		}

		byte[] sourceLeftEncrypt = encryptDES(key, oper1);
		return bcd2str(sourceLeftEncrypt);
	}
}

