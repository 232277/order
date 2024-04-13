package com.dyc.order.cashier.data.response;

/**
 * func: 会员等级
 * author:丁语成 on 2020/3/3 12:31
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class MemberLevelData {
	public Object info;
	public Level[] rows;
	public Integer total;

	public class Level{
		public Integer accumulativePointsThreshold;
		public Boolean enableMemberPrice;
		public Integer id;
		public Integer level;
		public String levelName;
		public Integer merchantId;
		public Double memberDiscount;//会员折扣率
		public Double obtainMoney;//每次购物获得的积分
		public Double obtainPoints;//每次购物获得的积分所需的钱
		public Boolean status;
		public Double storeThreshold;//达到这个等级需要的累计储值门槛


		@Override
		public String toString() {
			return " id:" + id
					+ " levelName:" + levelName
					+ " merchantId:" + merchantId
					+ " accumulativePointsThreshold:" + accumulativePointsThreshold
					+ " enableMemberPrice:" + enableMemberPrice
					+ " memberDiscount:" + memberDiscount
					+ " obtainMoney:" + obtainMoney
					+ " obtainPoints:" + obtainPoints
					+ " status:" + status
					+ " storeThreshold:" + storeThreshold
					+ "\n";
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
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

		public Integer getAccumulativePointsThreshold() {
			return accumulativePointsThreshold;
		}

		public void setAccumulativePointsThreshold(Integer accumulativePointsThreshold) {
			this.accumulativePointsThreshold = accumulativePointsThreshold;
		}

		public Boolean isEnableMemberPrice() {
			return enableMemberPrice;
		}

		public void setEnableMemberPrice(Boolean enableMemberPrice) {
			this.enableMemberPrice = enableMemberPrice;
		}

		public Double getMemberDiscount() {
			return memberDiscount;
		}

		public void setMemberDiscount(Double memberDiscount) {
			this.memberDiscount = memberDiscount;
		}

		public Double getObtainMoney() {
			return obtainMoney;
		}

		public void setObtainMoney(Double obtainMoney) {
			this.obtainMoney = obtainMoney;
		}

		public Double getObtainPoints() {
			return obtainPoints;
		}

		public void setObtainPoints(Double obtainPoints) {
			this.obtainPoints = obtainPoints;
		}

		public Boolean isStatus() {
			return status;
		}

		public void setStatus(Boolean status) {
			this.status = status;
		}

		public Double getStoreThreshold() {
			return storeThreshold;
		}

		public void setStoreThreshold(Double storeThreshold) {
			this.storeThreshold = storeThreshold;
		}
	}

	public Object getInfo() {
		return info;
	}

	public void setInfo(Object info) {
		this.info = info;
	}

	public Level[] getRows() {
		return rows;
	}

	public void setRows(Level[] rows) {
		this.rows = rows;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	@Override
	public String toString() {
		StringBuilder levels = new StringBuilder();
		for (Level level : rows){
			levels.append(level.toString());
		}
		return "total:" + total + " rows" + levels.toString() + "info:" + info;
	}

}
