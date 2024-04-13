package com.dyc.order.cashier.data.response;

/**
 * func: 订单列表
 * author:丁语成 on 2020/2/24 12:15
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class OrderListQueryData{
	public Object info;
	public OrderDetailData[] rows;
	public int total;

	public class OrderDetailData{
		public Order order;
		public OrderActivityInfo[] orderActivityInfoList;
		public OrderDetail[] orderDetailList;
		public RefundOrder[] refundOrderList;
		public String orderNo;
		public double refundTotalPrice;

		public class RefundOrder {

			/**
			 * address :
			 * cancelRemark :
			 * cashierId : 0
			 * clinchTime :
			 * couponDiscount : 0
			 * createTime :
			 * deliveryCode :
			 * deliveryFee : 0
			 * deliveryTime :
			 * deliveryType : 0
			 * detail :
			 * discount : 0
			 * erase : 0
			 * memberDiscount : 0
			 * memberId : 0
			 * merchantId : 0
			 * numDiscount : 0
			 * orderNo :
			 * orderPhone :
			 * orderSource : 0
			 * payType : 0
			 * planDeliveryTime : 23:00 / 2019-05-01 00:00:00
			 * platformNo :
			 * receiver :
			 * receiverPhone :
			 * reduceDiscount : 0
			 * remark :
			 * terminalCd :
			 * terminalSn :
			 * totalAmount : 0
			 * totalNum : 0
			 * transAmount : 0
			 * transFlag : 0
			 * transTime :
			 * transType : 0
			 */

			private String address;
			private String cancelRemark;
			private int cashierId;
			private String clinchTime;
			private int couponDiscount;
			private String createTime;
			private String deliveryCode;
			private int deliveryFee;
			private String deliveryTime;
			private int deliveryType;
			private String detail;
			private int discount;
			private int erase;
			private int memberDiscount;
			private int memberId;
			private int merchantId;
			private int numDiscount;
			private String orderNo;
			private String orderPhone;
			private int orderSource;
			private int payType;
			private String planDeliveryTime;
			private String platformNo;
			private String receiver;
			private String receiverPhone;
			private int reduceDiscount;
			private String remark;
			private String terminalCd;
			private String terminalSn;
			private int totalAmount;
			private int totalNum;
			private double transAmount;
			private int transFlag;
			private String transTime;
			private int transType;

			public String getAddress() {
				return address;
			}

			public void setAddress(String address) {
				this.address = address;
			}

			public String getCancelRemark() {
				return cancelRemark;
			}

			public void setCancelRemark(String cancelRemark) {
				this.cancelRemark = cancelRemark;
			}

			public int getCashierId() {
				return cashierId;
			}

			public void setCashierId(int cashierId) {
				this.cashierId = cashierId;
			}

			public String getClinchTime() {
				return clinchTime;
			}

			public void setClinchTime(String clinchTime) {
				this.clinchTime = clinchTime;
			}

			public int getCouponDiscount() {
				return couponDiscount;
			}

			public void setCouponDiscount(int couponDiscount) {
				this.couponDiscount = couponDiscount;
			}

			public String getCreateTime() {
				return createTime;
			}

			public void setCreateTime(String createTime) {
				this.createTime = createTime;
			}

			public String getDeliveryCode() {
				return deliveryCode;
			}

			public void setDeliveryCode(String deliveryCode) {
				this.deliveryCode = deliveryCode;
			}

			public int getDeliveryFee() {
				return deliveryFee;
			}

			public void setDeliveryFee(int deliveryFee) {
				this.deliveryFee = deliveryFee;
			}

			public String getDeliveryTime() {
				return deliveryTime;
			}

			public void setDeliveryTime(String deliveryTime) {
				this.deliveryTime = deliveryTime;
			}

			public int getDeliveryType() {
				return deliveryType;
			}

			public void setDeliveryType(int deliveryType) {
				this.deliveryType = deliveryType;
			}

			public String getDetail() {
				return detail;
			}

			public void setDetail(String detail) {
				this.detail = detail;
			}

			public int getDiscount() {
				return discount;
			}

			public void setDiscount(int discount) {
				this.discount = discount;
			}

			public int getErase() {
				return erase;
			}

			public void setErase(int erase) {
				this.erase = erase;
			}

			public int getMemberDiscount() {
				return memberDiscount;
			}

			public void setMemberDiscount(int memberDiscount) {
				this.memberDiscount = memberDiscount;
			}

			public int getMemberId() {
				return memberId;
			}

			public void setMemberId(int memberId) {
				this.memberId = memberId;
			}

			public int getMerchantId() {
				return merchantId;
			}

			public void setMerchantId(int merchantId) {
				this.merchantId = merchantId;
			}

			public int getNumDiscount() {
				return numDiscount;
			}

			public void setNumDiscount(int numDiscount) {
				this.numDiscount = numDiscount;
			}

			public String getOrderNo() {
				return orderNo;
			}

			public void setOrderNo(String orderNo) {
				this.orderNo = orderNo;
			}

			public String getOrderPhone() {
				return orderPhone;
			}

			public void setOrderPhone(String orderPhone) {
				this.orderPhone = orderPhone;
			}

			public int getOrderSource() {
				return orderSource;
			}

			public void setOrderSource(int orderSource) {
				this.orderSource = orderSource;
			}

			public int getPayType() {
				return payType;
			}

			public void setPayType(int payType) {
				this.payType = payType;
			}

			public String getPlanDeliveryTime() {
				return planDeliveryTime;
			}

			public void setPlanDeliveryTime(String planDeliveryTime) {
				this.planDeliveryTime = planDeliveryTime;
			}

			public String getPlatformNo() {
				return platformNo;
			}

			public void setPlatformNo(String platformNo) {
				this.platformNo = platformNo;
			}

			public String getReceiver() {
				return receiver;
			}

			public void setReceiver(String receiver) {
				this.receiver = receiver;
			}

			public String getReceiverPhone() {
				return receiverPhone;
			}

			public void setReceiverPhone(String receiverPhone) {
				this.receiverPhone = receiverPhone;
			}

			public int getReduceDiscount() {
				return reduceDiscount;
			}

			public void setReduceDiscount(int reduceDiscount) {
				this.reduceDiscount = reduceDiscount;
			}

			public String getRemark() {
				return remark;
			}

			public void setRemark(String remark) {
				this.remark = remark;
			}

			public String getTerminalCd() {
				return terminalCd;
			}

			public void setTerminalCd(String terminalCd) {
				this.terminalCd = terminalCd;
			}

			public String getTerminalSn() {
				return terminalSn;
			}

			public void setTerminalSn(String terminalSn) {
				this.terminalSn = terminalSn;
			}

			public int getTotalAmount() {
				return totalAmount;
			}

			public void setTotalAmount(int totalAmount) {
				this.totalAmount = totalAmount;
			}

			public int getTotalNum() {
				return totalNum;
			}

			public void setTotalNum(int totalNum) {
				this.totalNum = totalNum;
			}

			public double getTransAmount() {
				return transAmount;
			}

			public void setTransAmount(int transAmount) {
				this.transAmount = transAmount;
			}

			public int getTransFlag() {
				return transFlag;
			}

			public void setTransFlag(int transFlag) {
				this.transFlag = transFlag;
			}

			public String getTransTime() {
				return transTime;
			}

			public void setTransTime(String transTime) {
				this.transTime = transTime;
			}

			public int getTransType() {
				return transType;
			}

			public void setTransType(int transType) {
				this.transType = transType;
			}
		}

		public class Order{
			public boolean ableRefund;
			public int refundStatus;
			public String address;
			public String cancelRemark;
			public int cashierId;
			public String clinchTime;
			public double couponDiscount;
			public String createTime;
			public String deliveryCode;
			public double deliveryFee;
			public String deliveryTime;
			public int deliveryType;
			public String detail;
			public double discount;
			public double erase;
			public int expireMinute;
			public boolean identified;
			public int lastRefundStatus;
			public int levelId;
			public String levelName;
			public double memberDiscount;
			public Integer memberId;
			public String memberPhone;
			public String memberName;
			public String avatar;
			public String merchantAddress;
			public int merchantId;
			public String merchantName;
			public double numDiscount;
			public String orderNo;
			public String orderPhone;
			public int orderSource;//0=收银机,1=微信,2=终端,3=C端小程序
			public String originOrderNo;
			public double overage;
			public int payType; //0=现金,1=主扫,3=被扫,5=银行卡,6=刷脸，7储值卡【预留】
			public String planDeliveryTime;
			public String platformNo;
			public int points;
			public String receiver;
			public String receiverPhone;
			public double reduceDiscount;
			public String remark;
			public String terminalCd;
			public String terminalSn;
			public double totalAmount;
			public double totalNum;
			public double transAmount;
			public int transFlag;//0=未支付,1=已支付,-1支付失败，2交易关闭
			public String transTime;
			public int transType;//0=销售,1=退货,2=直接收款
			public String username;
		}

		public class OrderActivityInfo{
			public int activityId;
			public String activityName;
			public int activityType;
			public String createTime;
			public double discountAmount;
			public int id;
			public int merchantId;
			public String orderNo;
			public String updateTime;
		}

		public class OrderDetail{
			public long ableRefundNum; // 可退货数量
			public long id;
			public String barcode;
			public String brand;
			public double costAmount;
			public String createTime;
			public double discountAmount;
			public double erase;
			public int merchantId;
			public double num;
			public String orderNo;
			public double originPrice;
			public String picUrl;
			public int productId;
			public String productName;
			public double profit;
			public String remark;
			public String standard;
			public double totalAmount;
			public double transAmount;
			public String unit;
			public double unitPrice;
			public double refundPrice;
			public boolean damage;
			public double refundAmount;
			public String updateTime;
		}
	}
}
