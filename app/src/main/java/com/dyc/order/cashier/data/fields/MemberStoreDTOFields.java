package com.dyc.order.cashier.data.fields;

/**
 * func:
 * author:丁语成 on 2020/6/22 17:30
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class MemberStoreDTOFields {
	private String authCode;
	private Integer memberId;
	private Integer orderSource;
	private Integer payType;
	private Integer rechargeRuleId;
	private double totalAmount;
	private double transAmount;

	public MemberStoreDTOFields(){}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Integer getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(Integer orderSource) {
		this.orderSource = orderSource;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public Integer getRechargeRuleId() {
		return rechargeRuleId;
	}

	public void setRechargeRuleId(Integer rechargeRuleId) {
		this.rechargeRuleId = rechargeRuleId;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public double getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(double transAmount) {
		this.transAmount = transAmount;
	}
}
