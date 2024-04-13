package com.dyc.order.cashier.data.response;

/**
 * func:
 * author:丁语成 on 2020/4/3 14:14
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class NoDataResponse {
	protected String code;//响应码
	protected String msg;//操作消息
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

	public Long getServerTime() {
		return serverTime;
	}

	public void setServerTime(Long serverTime) {
		this.serverTime = serverTime;
	}
}
