package com.dyc.order.cashier.data.fields;

import com.dyc.order.cashier.data.response.MerchantInfoData;

/**
 * func: 修改商户信息bean
 * author:丁语成 on 2020/2/23 10:45
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class ChangeMerchantInfoFields {
	private String address;
	private String contactName;
	private int id;
	private String location;
	private String logo;
	private String name;
	private String orderByColumn;
	private int pageNum;
	private int pageSize;
	private int parentId;
	private String phoneNumber;
	private int prop;
	private int status;
	private int type;

	public ChangeMerchantInfoFields(){}

	public ChangeMerchantInfoFields(MerchantInfoData data){
		address = data.getAddress();
		contactName = data.getContactName();
		id = data.getId();
		location = data.getLocation();
		logo = data.getLogo();
		name = data.getName();
		orderByColumn = "asc";
		pageNum = 1;
		pageSize = 10;
		parentId = data.getParentId();
		phoneNumber = data.getPhoneNumber();
		prop = data.getProp();
		status = data.getStatus();
		type = data.getType();
	}

	/**
	 * {
	 * 	"address": "",
	 * 	"contactName": "",
	 * 	"id": 0,
	 * 	"location": "",
	 * 	"logo": "",
	 * 	"name": "",
	 * 	"orderByColumn": "",
	 * 	"pageNum": 1,
	 * 	"pageSize": 20,
	 * 	"parentId": 0,
	 * 	"phoneNumber": "",
	 * 	"prop": 0,
	 * 	"status": 0,
	 * 	"type": 0
	 * }
	 */

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrderByColumn() {
		return orderByColumn;
	}

	public void setOrderByColumn(String orderByColumn) {
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

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getProp() {
		return prop;
	}

	public void setProp(int prop) {
		this.prop = prop;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
