package com.dyc.order.cashier.data.response;

/**
 * func: 供应商
 * author:丁语成 on 2020/2/21 17:20
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class SupplierInfo {
	private int id;
	private String address;
	private String contactName;
	private String name;
	private String phoneNumber;
	private String remark;
	private String valid;//0有效 1已删除

	public SupplierInfo(){}

	public SupplierInfo(int id, String address, String contactName, String name, String phoneNumber, String remark, String valid) {
		this.id = id;
		this.address = address;
		this.contactName = contactName;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.remark = remark;
		this.valid = valid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}
}
