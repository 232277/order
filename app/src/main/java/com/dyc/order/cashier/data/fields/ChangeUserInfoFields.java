package com.dyc.order.cashier.data.fields;

/**
 * func: 修改用户信息bean
 * author:丁语成 on 2020/2/23 15:02
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class ChangeUserInfoFields {
	private String phone;
	private String email;
	private String sex;
	private String userName;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
