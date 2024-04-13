package com.dyc.order.cashier.data.response;


import com.dyc.order.cashier.data.fields.AddOrChangeMemberFields;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * func: 单个会员信息
 * author:丁语成 on 2020/3/2 17:27
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class MemberInfoData {
	private double accumulativeMoney;
	private double accumulativePoints;
	private String avatar;
	private String birthDate;
	private String cardNo;
	private int couponNum;
	private String createTime;
	private boolean enableMemberPrice;
	private String faceFeature;
	private String faceId;
	private String facePicture;
	private Integer id;
	private Boolean identified;
	private String lastPayTime;
	private Integer levelId;
	private String levelName;
	private Integer merchantId;
	private String merchantName;
	private double overage;
	private String phoneNumber;
	private int points;
	private PreferenceVo[] preferenceVo;
	private String sex;
	private Integer status;
	private Double totalAmount;
	private Integer totalTimes;
	private String username;
	private Boolean isNew;
	private Double memberDiscount;

	
	public MemberInfoData(){
	}

	public MemberInfoData(AddOrChangeMemberFields fields){
		username = fields.getUsername();
		birthDate = fields.getBirhDate();
		cardNo = fields.getCardNo();
		createTime = fields.getCreateTime();
		faceId = fields.getFaceId();
		id = fields.getId();
		levelId = fields.getLevelId();
		merchantId = fields.getMerchantId();
		phoneNumber =fields.getPhoneNumber();
		sex = fields.getSex();
		status = fields.getStatus();
	}

	public List<String> getPrefers(){
		if (preferenceVo != null && preferenceVo.length != 0){
			Set<String> res = new TreeSet<>();
			for (PreferenceVo prefer : preferenceVo){
				if (!"".equals(prefer.getTypeName())){
					res.add(prefer.getTypeName());
				}
			}
			return new ArrayList<>(res);
		}
		return null;
	}

	public static class PreferenceVo{
		private Integer purchaseNum;
		private Integer purchaseTotalNum;
		private String typeId;
		private String typeName;

		public Integer getPurchaseNum() {
			return purchaseNum;
		}

		public void setPurchaseNum(Integer purchaseNum) {
			this.purchaseNum = purchaseNum;
		}

		public Integer getPurchaseTotalNum() {
			return purchaseTotalNum;
		}

		public void setPurchaseTotalNum(Integer purchaseTotalNum) {
			this.purchaseTotalNum = purchaseTotalNum;
		}

		public String getTypeId() {
			return typeId;
		}

		public void setTypeId(String typeId) {
			this.typeId = typeId;
		}

		public String getTypeName() {
			return typeName;
		}

		public void setTypeName(String typeName) {
			this.typeName = typeName;
		}
	}

	public double getAccumulativeMoney() {
		return accumulativeMoney;
	}

	public void setAccumulativeMoney(double accumulativeMoney) {
		this.accumulativeMoney = accumulativeMoney;
	}

	public double getAccumulativePoints() {
		return accumulativePoints;
	}

	public void setAccumulativePoints(double accumulativePoints) {
		this.accumulativePoints = accumulativePoints;
	}

	public int getCouponNum() {
		return couponNum;
	}

	public void setCouponNum(int couponNum) {
		this.couponNum = couponNum;
	}

	public boolean isEnableMemberPrice() {
		return enableMemberPrice;
	}

	public void setEnableMemberPrice(boolean enableMemberPrice) {
		this.enableMemberPrice = enableMemberPrice;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Boolean getIdentified() {
		return identified;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
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

	public String getFaceFeature() {
		return faceFeature;
	}

	public void setFaceFeature(String faceFeature) {
		this.faceFeature = faceFeature;
	}

	public String getFaceId() {
		return faceId;
	}

	public void setFaceId(String faceId) {
		this.faceId = faceId;
	}

	public String getFacePicture() {
		return facePicture;
	}

	public void setFacePicture(String facePicture) {
		this.facePicture = facePicture;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean isIdentified() {
		return identified;
	}

	public void setIdentified(Boolean identified) {
		this.identified = identified;
	}

	public String getLastPayTime() {
		return lastPayTime;
	}

	public void setLastPayTime(String lastPayTime) {
		this.lastPayTime = lastPayTime;
	}

	public Integer getLevelId() {
		return levelId;
	}

	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public PreferenceVo[] getPreferenceVo() {
		return preferenceVo;
	}

	public void setPreferenceVo(PreferenceVo[] preferenceVo) {
		this.preferenceVo = preferenceVo;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Integer getTotalTimes() {
		return totalTimes;
	}

	public void setTotalTimes(Integer totalTimes) {
		this.totalTimes = totalTimes;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public double getOverage() {
		return overage;
	}

	public void setOverage(double overage) {
		this.overage = overage;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public Boolean getNew() {
		return isNew;
	}

	public void setNew(Boolean aNew) {
		isNew = aNew;
	}
}
