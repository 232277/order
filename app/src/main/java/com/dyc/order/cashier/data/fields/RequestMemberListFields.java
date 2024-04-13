package com.dyc.order.cashier.data.fields;

/**
 * func: 请求会员列表bean
 * author:丁语成 on 2020/3/5 14:29
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class RequestMemberListFields {
	private int pageNum;
	private int pageSize;
	private String beginTime;
	private String cardNo;
	private String createTime;
	private String endTime;
	private String identity;
	private String levelName;
	private Integer merchantId;
	private String merchantName;
	private String orderByColumn;
	private String phoneNumber;
	private String sex;
	private String username;

	public RequestMemberListFields(){}

	public RequestMemberListFields(int pageNum, int pageSize, Integer merchantId, String orderByColumn) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.merchantId = merchantId;
		this.orderByColumn = orderByColumn;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
