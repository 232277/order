package com.dyc.order.cashier.data.response;

/**
 * func: 支付响应
 * author:丁语成 on 2020/2/25 17:44
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class BarCodeData {
	private boolean status;
	private String msg;
	private String orderNo;
	private String codeUrl;

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getCodeUrl() {
		return codeUrl;
	}

	public void setCodeUrl(String codeUrl) {
		this.codeUrl = codeUrl;
	}
}
