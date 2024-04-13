package com.dyc.administrator.toollibrary.utils;

/**
 * func:
 * author:丁语成 on 2020/2/17 7:23
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class KeyValue<Key, Value> {
	private Key key;
	private Value value;

	public KeyValue(){
	}

	public KeyValue(Key key, Value value) {
		this.key = key;
		this.value = value;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public Value getValue() {
		return value;
	}

	public void setValue(Value value) {
		this.value = value;
	}
}
