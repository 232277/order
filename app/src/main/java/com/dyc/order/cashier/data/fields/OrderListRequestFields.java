package com.dyc.order.cashier.data.fields;

/**
 * func: 订单列表请求bean
 * author:丁语成 on 2020/2/28 11:32
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class OrderListRequestFields {
	private Integer pageNum;
	private Integer pageSize;
	private Integer cashierId;
	private String cashierName;
	private String endTime;
	private Integer memberId;
	private Integer merchantId;
	private String merchantName;
	private String orderByColumn;
	private String orderNo;
	private Integer orderSource;
	private Integer payType;
	private String startTime;
	private String terminalCd;
	private String terminalSn;
	private Integer transFlag;
	private String transFlags;
	private Integer transType;

	public OrderListRequestFields() {
	}

	public OrderListRequestFields(Integer pageNum, Integer pageSize, String endTime, String startTime) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.endTime = endTime;
		this.startTime = startTime;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getCashierId() {
		return cashierId;
	}

	public void setCashierId(Integer cashierId) {
		this.cashierId = cashierId;
	}

	public String getCashierName() {
		return cashierName;
	}

	public void setCashierName(String cashierName) {
		this.cashierName = cashierName;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Integer getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getOrderByColumn() {
		return orderByColumn;
	}

	public void setOrderByColumn(String orderByColumn) {
		this.orderByColumn = orderByColumn;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getTerminalCd() {
		return terminalCd;
	}

	public void setTerminalCd(String terminalCd) {
		this.terminalCd = terminalCd;
	}

	public String getTerminalSn() {
		return terminalSn;
	}

	public void setTerminalSn(String terminalSn) {
		this.terminalSn = terminalSn;
	}

	public Integer getTransFlag() {
		return transFlag;
	}

	public void setTransFlag(Integer transFlag) {
		this.transFlag = transFlag;
	}

	public String getTransFlags() {
		return transFlags;
	}

	public void setTransFlags(String transFlags) {
		this.transFlags = transFlags;
	}

	public Integer getTransType() {
		return transType;
	}

	public void setTransType(Integer transType) {
		this.transType = transType;
	}
}
