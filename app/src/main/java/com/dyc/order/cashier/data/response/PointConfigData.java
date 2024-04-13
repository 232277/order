package com.dyc.order.cashier.data.response;

/**
 * func:
 * author:丁语成 on 2020/5/19 16:31
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class PointConfigData {
	public String clearDate;
	public String createTime;
	public Double deductRate;
//	public MemberLevelList[] memberLevelLists;
	public Integer merchantId;
	public boolean refundObtainPoints;
	public boolean refundUsedPoints;
	public String updateTime;

//	public static class MemberLevelList{
//		public Integer accumulativePointsThreshold;
//		public Boolean enableMemberPrice;
//		public Long id;
//		public Integer level;
//		public String levelName;
//		public Double memberDiscount;
//		public Integer merchantId;
//		public Double obtainMoney;
//		public Double obtainPoints;
//		public Boolean status;
//		public Double storeThreshold;
//
//		public Integer getAccumulativePointsThreshold() {
//			return accumulativePointsThreshold;
//		}
//
//		public void setAccumulativePointsThreshold(Integer accumulativePointsThreshold) {
//			this.accumulativePointsThreshold = accumulativePointsThreshold;
//		}
//
//		public Boolean getEnableMemberPrice() {
//			return enableMemberPrice;
//		}
//
//		public void setEnableMemberPrice(Boolean enableMemberPrice) {
//			this.enableMemberPrice = enableMemberPrice;
//		}
//
//		public Long getId() {
//			return id;
//		}
//
//		public void setId(Long id) {
//			this.id = id;
//		}
//
//		public Integer getLevel() {
//			return level;
//		}
//
//		public void setLevel(Integer level) {
//			this.level = level;
//		}
//
//		public String getLevelName() {
//			return levelName;
//		}
//
//		public void setLevelName(String levelName) {
//			this.levelName = levelName;
//		}
//
//		public Double getMemberDiscount() {
//			return memberDiscount;
//		}
//
//		public void setMemberDiscount(Double memberDiscount) {
//			this.memberDiscount = memberDiscount;
//		}
//
//		public Integer getMerchantId() {
//			return merchantId;
//		}
//
//		public void setMerchantId(Integer merchantId) {
//			this.merchantId = merchantId;
//		}
//
//		public Double getObtainMoney() {
//			return obtainMoney;
//		}
//
//		public void setObtainMoney(Double obtainMoney) {
//			this.obtainMoney = obtainMoney;
//		}
//
//		public Double getObtainPoints() {
//			return obtainPoints;
//		}
//
//		public void setObtainPoints(Double obtainPoints) {
//			this.obtainPoints = obtainPoints;
//		}
//
//		public Boolean getStatus() {
//			return status;
//		}
//
//		public void setStatus(Boolean status) {
//			this.status = status;
//		}
//
//		public Double getStoreThreshold() {
//			return storeThreshold;
//		}
//
//		public void setStoreThreshold(Double storeThreshold) {
//			this.storeThreshold = storeThreshold;
//		}
//	}

	public String getClearDate() {
		return clearDate;
	}

	public void setClearDate(String clearDate) {
		this.clearDate = clearDate;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Double getDeductRate() {
		return deductRate;
	}

	public void setDeductRate(Double deductRate) {
		this.deductRate = deductRate;
	}

//	public MemberLevelList[] getMemberLevelLists() {
//		return memberLevelLists;
//	}
//
//	public void setMemberLevelLists(MemberLevelList[] memberLevelLists) {
//		this.memberLevelLists = memberLevelLists;
//	}

	public Integer getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}

	public boolean isRefundObtainPoints() {
		return refundObtainPoints;
	}

	public void setRefundObtainPoints(boolean refundObtainPoints) {
		this.refundObtainPoints = refundObtainPoints;
	}

	public boolean isRefundUsedPoints() {
		return refundUsedPoints;
	}

	public void setRefundUsedPoints(boolean refundUsedPoints) {
		this.refundUsedPoints = refundUsedPoints;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
}
