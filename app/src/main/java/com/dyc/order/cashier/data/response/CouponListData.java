package com.dyc.order.cashier.data.response;

/**
 * func:
 * author:丁语成 on 2020/3/26 15:27
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class CouponListData {
	public CouponData[] rows;
	public long total;

	public static class CouponData{
		public boolean ableToUse;
		public String couponCode;
		public long couponId;
		public CouponDetail couponVO;

		public static class CouponDetail{
			public int couponType;
			public String createTime;
			public double discount;
			public String effectiveTime;
			public String excludeActivityType;
			public long expireTime;
			public int filterType;
			public long id;
			public String invalidTime;
			public long limitNum;
			public String merchantIds;
			public String name;
			public int num;
			public String obtainBeginTime;
			public String obtainEndTime;
			public int obtainWay;
			public long remainNum;
			public boolean status;
			public int targetMember;
			public double threshold;
			public long topMerchantId;
			public String updateTime;
			public String useWay;
		}

		public String createTime;
		public String disableReason;
		public String effectiveTime;
		public long id;
		public String invalidTime;
		public String merchantIds;
		public String phone;
		public boolean status;
		public long topMerchantId;
		public String updateTime;
	}
}
