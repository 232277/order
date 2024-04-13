package com.dyc.administrator.toollibrary.utils;

import android.view.KeyEvent;

/**
 * func:
 * author:丁语成 on 2020/3/19 13:53
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class KeyCodeUtil {
	private boolean mShiftOn = false;
	private StringBuffer mBuffer;

	public KeyCodeUtil() {
		mBuffer = new StringBuffer();
	}

	public char getChar(int keyCode, int acion) {
		if (keyCode == KeyEvent.KEYCODE_SHIFT_LEFT) {
			if (acion == KeyEvent.ACTION_DOWN) {
				mShiftOn = true;
			} else if (acion == KeyEvent.ACTION_UP) {
				mShiftOn = false;
			}
			return 0;
		}
		if (acion == KeyEvent.ACTION_UP) {
			return 0;
		}
		char outChar = 0;
		if (keyCode >= KeyEvent.KEYCODE_A && keyCode <= KeyEvent.KEYCODE_Z) {
			//字母
			outChar = (char) ('A' + keyCode - KeyEvent.KEYCODE_A + (mShiftOn ? 0 : 32));
		} else if (keyCode >= KeyEvent.KEYCODE_0 && keyCode <= KeyEvent.KEYCODE_9) {
			if (mShiftOn) {
				switch (keyCode) {
					case KeyEvent.KEYCODE_1:
						outChar = '!';
						break;
					case KeyEvent.KEYCODE_2:
						outChar = '@';
						break;
					case KeyEvent.KEYCODE_3:
						outChar = '#';
						break;
					case KeyEvent.KEYCODE_4:
						outChar = '$';
						break;
					case KeyEvent.KEYCODE_5:
						outChar = '%';
						break;
					case KeyEvent.KEYCODE_6:
						outChar = '^';
						break;
					case KeyEvent.KEYCODE_7:
						outChar = '&';
						break;
					case KeyEvent.KEYCODE_8:
						outChar = '*';
						break;
					case KeyEvent.KEYCODE_9:
						outChar = '(';
						break;
					case KeyEvent.KEYCODE_0:
						outChar = ')';
						break;
				}
			} else {
				//数字
				outChar = (char) ('0' + keyCode - KeyEvent.KEYCODE_0);
			}
		} else {
			//其他符号
			switch (keyCode) {
				case KeyEvent.KEYCODE_PERIOD:
					if (mShiftOn) {
						outChar = '>';
					} else {
						outChar = '.';
					}
					break;
				case KeyEvent.KEYCODE_MINUS:
					if (mShiftOn) {
						outChar = '_';
					} else {
						outChar = '-';
					}
					break;
				case KeyEvent.KEYCODE_SLASH:
					if (mShiftOn) {
						outChar = '?';
					} else {
						outChar = '/';
					}
					break;
				case KeyEvent.KEYCODE_BACKSLASH:
					if (mShiftOn) {
						outChar = '|';
					} else {
						outChar = 92;
					}
					break;
				case KeyEvent.KEYCODE_SEMICOLON:
					if (mShiftOn) {
						outChar = ':';
					} else {
						outChar = ';';
					}
					break;
				case KeyEvent.KEYCODE_EQUALS:
					if (mShiftOn) {
						outChar = '+';
					} else {
						outChar = '=';
					}
					break;
				case KeyEvent.KEYCODE_PLUS:
					outChar = '+';
					break;
				case KeyEvent.KEYCODE_COMMA:
					if (mShiftOn) {
						outChar = '<';
					} else {
						outChar = ',';
					}
					break;
				case KeyEvent.KEYCODE_APOSTROPHE:
					if (mShiftOn) {
						outChar = '"';//单引号
					} else {
						outChar = 39;//单引号
					}
					break;
				case KeyEvent.KEYCODE_LEFT_BRACKET:
					if (mShiftOn) {
						outChar = '{';
					} else {
						outChar = '[';
					}
					break;
				case KeyEvent.KEYCODE_RIGHT_BRACKET:
					if (mShiftOn) {
						outChar = '}';
					} else {
						outChar = ']';
					}
					break;
				case KeyEvent.KEYCODE_STAR:
					outChar = '*';
					break;
				case KeyEvent.KEYCODE_AT:
					outChar = '@';
					break;
				case KeyEvent.KEYCODE_GRAVE:
					if (mShiftOn) {
						outChar = '~';
					} else {
						outChar = '`';
					}
					break;
				default:
					outChar = 0;
					break;
			}
		}
		return outChar;
	}

	public void append(int keyCode, int acion) {
		char outChar = getChar(keyCode, acion);
		if (outChar != 0)
			mBuffer.append(outChar);
	}

	public String getEncode() {
		return mBuffer.toString();
	}

	public void reset() {
		mBuffer = new StringBuffer();
	}
}
