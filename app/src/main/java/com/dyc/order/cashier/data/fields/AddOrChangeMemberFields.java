package com.dyc.order.cashier.data.fields;

/**
 * func: 增加会员bean
 * author:丁语成 on 2020/3/2 17:41
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class AddOrChangeMemberFields {
	private String cardNo;
	private String phoneNumber;
	private Integer status;
	private String birhDate;
	private String createBy;
	private String createTime;
	private String faceId;
	private Integer id;
	private Integer levelId;
	private Integer merchantId;
	private Object params;
	private String remark;
	private String searchValue;
	private String sex;
	private String updateBy;
	private String updateTime;
	private String username;


	public AddOrChangeMemberFields(){
		//默认正常
		status = 0;
	}

	public AddOrChangeMemberFields(String cardNo, String phoneNumber, Integer status, String birhDate, String createBy, String createTime, String faceId, Integer id, Integer levelId, Integer merchantId, Object params, String remark, String searchValue, String sex, String updateBy, String updateTime, String username) {
		this.cardNo = cardNo;
		this.phoneNumber = phoneNumber;
		this.status = status;
		this.birhDate = birhDate;
		this.createBy = createBy;
		this.createTime = createTime;
		this.faceId = faceId;
		this.id = id;
		this.levelId = levelId;
		this.merchantId = merchantId;
		this.params = params;
		this.remark = remark;
		this.searchValue = searchValue;
		this.sex = sex;
		this.updateBy = updateBy;
		this.updateTime = updateTime;
		this.username = username;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getBirhDate() {
		return birhDate;
	}

	public void setBirhDate(String birhDate) {
		this.birhDate = birhDate;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getFaceId() {
		return faceId;
	}

	public void setFaceId(String faceId) {
		this.faceId = faceId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getLevelId() {
		return levelId;
	}

	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}

	public Integer getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}

	public Object getParams() {
		return params;
	}

	public void setParams(Object params) {
		this.params = params;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
