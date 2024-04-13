package com.dyc.order.cashier.error;

/**
 * func:
 * author:丁语成 on 2020/5/27 16:55
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class MyParseExcepetion extends Exception{
	private String code;
	private String msg;

	public MyParseExcepetion(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
