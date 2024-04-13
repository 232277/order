package com.dyc.order.cashier.data.response;


import com.dyc.order.cashier.constant.MessageField;

import java.util.HashMap;
import java.util.Map;

/**
 * func: 登录响应
 * author:丁语成 on 2020/2/18 11:19
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class LoginInfoData{
	private String token;
	private String phone;
	private String loginId;
	private String name;
	private String roleNames;
	private MerchantInto merchantInfoDTO;

	public Map<String, Object> toMap() {
		return new HashMap<String, Object>() {{
			put(MessageField.TOKEN, getToken());
			put(MessageField.PHONE, getPhone());
			put(MessageField.LOGIN_ID, getLoginId());
			put(MessageField.PERSON_NAME, getName());
			put(MessageField.ROLE_NAMES, getRoleNames());
			put(MessageField.SHOP_ID, getMerchantInfoDTO().getId());
			put(MessageField.SHOP_NAME, getMerchantInfoDTO().getName());
			put(MessageField.PARENT_ID, getMerchantInfoDTO().getParentId());
			put(MessageField.SHOP_TYPE, getMerchantInfoDTO().getType());
			put(MessageField.CONTACT_NAME, getMerchantInfoDTO().getContactName());
			put(MessageField.CONTACT_PHONE, getMerchantInfoDTO().getPhoneNumber());
			put(MessageField.ADDRESS, getMerchantInfoDTO().getAddress());
		}};
	}

	public static class MerchantInto{
		private int id;
		private String name;
		private int parentId;
		private int type;
		private String contactName;
		private String phoneNumber;
		private String address;

		/**
		 {
		 "code":"response.success","msg":"操作成功",
		 "getData()":{
		 "token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbXBsb3llZUlkIjoxLCJpc0Nhc2hpZXIiOiJ0cnVlIiwiZXhwIjoxNTg0NjIyMDkyfQ.eECNJBF8-nEjNj8QFAqysFrMN61cE9nOU5Xp69QIh88",
		 "phone":"18850713988","loginId":"18850713988","name":"李东","roleNames":"总店管理员,店长,收银员,操作员",
		 "merchantInfoDTO":{
		 "id":1,"name":"阳光便利店","parentId":0,"type":0,
		 "contactName":"李东航ddd","phoneNumber":"18850713988","address":"1111333"
		 },
		 "menus":[]
		 }
		 ,"serverTime":1582030092136
		 }
		 */

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

		public int getParentId() {
			return parentId;
		}

		public void setParentId(int parentId) {
			this.parentId = parentId;
		}

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}

		public String getContactName() {
			return contactName;
		}

		public void setContactName(String contactName) {
			this.contactName = contactName;
		}

		public String getPhoneNumber() {
			return phoneNumber;
		}

		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	public MerchantInto getMerchantInfoDTO() {
		return merchantInfoDTO;
	}

	public void setMerchantInfoDTO(MerchantInto merchantInfoDTO) {
		this.merchantInfoDTO = merchantInfoDTO;
	}
}