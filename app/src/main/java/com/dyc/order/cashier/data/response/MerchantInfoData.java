package com.dyc.order.cashier.data.response;


import com.dyc.order.cashier.data.fields.ChangeMerchantInfoFields;

/**
 * func: 商户信息
 * author:丁语成 on 2020/2/23 10:07
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class MerchantInfoData {
	private int id;
	private int parentId;
	private String name;
	private int type;
	private int prop;
	private int industry;
	private int status;
	private int isDelete;
	private String contactName;
	private String institutionCode;
	private String phoneNumber;
	private String businessHour;
	private String location;
	private String address;
	private double latitude;
	private double longitude;
	private String createBy;
	private String updateBy;
	private String createTime;
	private String updateTime;
	private String logo;

	/**
	 *"code":"response.success",
	 * "msg":"操作成功",
	 * "data":{
	 * "id":1,"parentId":0,
	 * "name":"阳光便利店1234","type":0,
	 * "prop":0,"industry":0,"status":0,
	 * "isDeleted":0,"contactName":"李东航ddd",
	 * "institutionCode":"101","phoneNumber":"18850713988",
	 * "businessHour":"00:00-23:59",
	 * "location":"福建省,福州市,台江区",
	 * "address":"1111333","latitude":26.0994840,
	 * "longitude":119.2448740,"createBy":"","updateBy":"",
	 * "createTime":"2019-11-14 14:12:16","updateTime":"2020-02-21 14:41:18"
	 * },
	 * "serverTime":1582423736809}
	 */

	public MerchantInfoData(){}

	public void changeMerchantInfoData(ChangeMerchantInfoFields data){
		address = data.getAddress();
		contactName = data.getContactName();
		id = data.getId();
		location = data.getLocation();
		logo = data.getLogo();
		name = data.getName();
		parentId = data.getParentId();
		phoneNumber = data.getPhoneNumber();
		prop = data.getProp();
		status = data.getStatus();
		type = data.getType();
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}


	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getProp() {
		return prop;
	}

	public void setProp(int prop) {
		this.prop = prop;
	}

	public int getIndustry() {
		return industry;
	}

	public void setIndustry(int industry) {
		this.industry = industry;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getInstitutionCode() {
		return institutionCode;
	}

	public void setInstitutionCode(String institutionCode) {
		this.institutionCode = institutionCode;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getBusinessHour() {
		return businessHour;
	}

	public void setBusinessHour(String businessHour) {
		this.businessHour = businessHour;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
}