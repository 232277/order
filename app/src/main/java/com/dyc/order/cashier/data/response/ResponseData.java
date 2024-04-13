package com.dyc.order.cashier.data.response;

/**
 * func: 通用响应
 * author:丁语成 on 2020/2/19 14:32
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class ResponseData<T> {
	protected String code;//响应码
	protected String msg;//操作消息
	protected T data;//data
	protected Long serverTime;//服务器时间

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

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Long getServerTime() {
		return serverTime;
	}

	public void setServerTime(Long serverTime) {
		this.serverTime = serverTime;
	}
}
