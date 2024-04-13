package com.dyc.order.cashier.data.response;

/**
 * func:
 * author:丁语成 on 2020/4/3 10:23
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */

import android.os.Parcel;
import android.os.Parcelable;

/**
 * {"code":"response.success","msg":"操作成功",
 * "data":
 * {
 * 	"orderNo":"563186453744734208",
 * 	"order":
 * 	{	"orderNo":"563186453744734208","merchantId":1,"transType":0,"transAmount":50.000,
 * 		"payType":0,"memberId":63,"cashierId":1,"totalAmount":50.000,"totalNum":5.0,
 * 		"discount":0.000,"memberDiscount":0.000,"reduceDiscount":0,"erase":0.0,"terminalCd":"unknown",
 * 		"terminalSn":"unknown","orderSource":2,"transFlag":0,"merchantName":"","expireMinute":20
 * 		},
 * 	"orderDetailList":
 * 		[
 * 		{
 * 		"id":3886,"merchantId":1,"productId":287,"orderNo":"563186453744734208","unitPrice":10.00,
 * 		"originPrice":10.00,"viewPrice":10.00,"barcode":"6948385001646","standard":"118g",
 * 		"brand":"6948385001646","productName":"明菲丽美国护肤甘油",
 * 		"pic":"http://inventory.51cpay.com/product/7eaf292295701a35b4cdded0236b830c.jpg",
 * 		"num":5.00,"totalAmount":50.00,"discountAmount":0.00,"transAmount":50.00,"costAmount":0.00,
 * 		"profit":0.00,"ableRefundNum":5,"saleType":0,
 * 		"picUrl":"http://inventory.51cpay.com/product/7eaf292295701a35b4cdded0236b830c.jpg",
 * 		"createTime":"2020-04-03 10:21:54"
 * 		}
 * 		]
 * 	},"serverTime":1585880514096}
 *
 *
 * 	    {"code":"response.success","msg":"操作成功","data":{"orderNo":"563188021256802304","order":{"orderNo":"563188021256802304","merchantId":1,"transType":0,"transAmount":20.000,"payType":0,"memberId":63,"cashierId":1,"totalAmount":30.000,"totalNum":3.0,"discount":10.000,"memberDiscount":0.000,"couponDiscount":10.00,"reduceDiscount":0,"erase":0.0,"terminalCd":"unknown","terminalSn":"unknown","orderSource":2,"transFlag":0,"merchantName":"","expireMinute":20},"orderDetailList":[{"id":3887,"merchantId":1,"productId":287,"orderNo":"563188021256802304","unitPrice":6.66,"originPrice":10.00,"viewPrice":10.00,"barcode":"6948385001646","standard":"118g","brand":"6948385001646","productName":"明菲丽美国护肤甘油","pic":"http://inventory.51cpay.com/product/7eaf292295701a35b4cdded0236b830c.jpg","num":3.00,"totalAmount":30.00,"discountAmount":10.00,"transAmount":20.00,"costAmount":0.00,"profit":0.00,"ableRefundNum":3,"saleType":0,"picUrl":"http://inventory.51cpay.com/product/7eaf292295701a35b4cdded0236b830c.jpg","createTime":"2020-04-03 10:28:07"}],"orderCouponInfoList":[{"id":60,"orderNo":"563188021256802304","productIds":"287","couponCode":"21212230","couponName":"test","couponId":1,"originPrice":30.000,"discount":10.00}]},"serverTime":1585880887819}
 */
public class PlaceOrderData implements Parcelable{
	public String orderNo;
	public Order order;
	public OrderActivityInfo[] orderActivityInfoList;
	public OrderCouponInfo[] orderCouponInfoList;
	public OrderDetail[] orderDetailList;

	public static class Order implements Parcelable{
		public Boolean ableRefund;
		public String address;
		public String applyCancelTime;
		public String avatar;
		public String cancelRemark;
		public Integer cashierId;
		public String clinchTime;
		public Double couponDiscount;
		public String createTime;
		public String deliveryCode;
		public Double deliveryFee;
		public String deliveryTime;
		public Integer deliveryType;
		public Double discount;
		public Double erase;
		public Integer expireMinute;
		public Boolean identified;
		public Integer lastRefundStatus;
		public Integer levelId;
		public String levelName;
		public Double memberDiscount;
		public Integer memberId;
		public String memberName;
		public String memberPhone;
		public Double memberPoints;
		public String merchantAddress;
		public Integer merchantId;
		public String merchantName;
		public Double numDiscount;
		public Integer obtainPoints;
		public String orderNo;
		public String orderPhone;
		public Integer orderSource;
		public String originOrderNo;
		public Double overage;
		public Integer payType;
		public String planDeliveryTime;
		public String platformNo;
		public Double pointDiscount;
		public Integer points;
		public String receiver;
		public String receiverPhone;
		public Double reduceDiscount;
		public String refundEndTime;
		public Integer refundStatus;
		public String remark;
		public String terminalCd;
		public String terminalSn;
		public Double totalAmount;
		public Double totalNum;
		public Double transAmount;
		public Integer transFlag;
		public String transTime;
		public Integer transType;
		public String username;


		@Override
		public int describeContents() {
			return 0;
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			dest.writeValue(this.ableRefund);
			dest.writeString(this.address);
			dest.writeString(this.applyCancelTime);
			dest.writeString(this.avatar);
			dest.writeString(this.cancelRemark);
			dest.writeValue(this.cashierId);
			dest.writeString(this.clinchTime);
			dest.writeValue(this.couponDiscount);
			dest.writeString(this.createTime);
			dest.writeString(this.deliveryCode);
			dest.writeValue(this.deliveryFee);
			dest.writeString(this.deliveryTime);
			dest.writeValue(this.deliveryType);
			dest.writeValue(this.discount);
			dest.writeValue(this.erase);
			dest.writeValue(this.expireMinute);
			dest.writeValue(this.identified);
			dest.writeValue(this.lastRefundStatus);
			dest.writeValue(this.levelId);
			dest.writeString(this.levelName);
			dest.writeValue(this.memberDiscount);
			dest.writeValue(this.memberId);
			dest.writeString(this.memberName);
			dest.writeString(this.memberPhone);
			dest.writeValue(this.memberPoints);
			dest.writeString(this.merchantAddress);
			dest.writeValue(this.merchantId);
			dest.writeString(this.merchantName);
			dest.writeValue(this.numDiscount);
			dest.writeValue(this.obtainPoints);
			dest.writeString(this.orderNo);
			dest.writeString(this.orderPhone);
			dest.writeValue(this.orderSource);
			dest.writeString(this.originOrderNo);
			dest.writeValue(this.overage);
			dest.writeValue(this.payType);
			dest.writeString(this.planDeliveryTime);
			dest.writeString(this.platformNo);
			dest.writeValue(this.pointDiscount);
			dest.writeValue(this.points);
			dest.writeString(this.receiver);
			dest.writeString(this.receiverPhone);
			dest.writeValue(this.reduceDiscount);
			dest.writeString(this.refundEndTime);
			dest.writeValue(this.refundStatus);
			dest.writeString(this.remark);
			dest.writeString(this.terminalCd);
			dest.writeString(this.terminalSn);
			dest.writeValue(this.totalAmount);
			dest.writeValue(this.totalNum);
			dest.writeValue(this.transAmount);
			dest.writeValue(this.transFlag);
			dest.writeString(this.transTime);
			dest.writeValue(this.transType);
			dest.writeString(this.username);
		}

		public Order() {
		}

		protected Order(Parcel in) {
			this.ableRefund = (Boolean) in.readValue(Boolean.class.getClassLoader());
			this.address = in.readString();
			this.applyCancelTime = in.readString();
			this.avatar = in.readString();
			this.cancelRemark = in.readString();
			this.cashierId = (Integer) in.readValue(Integer.class.getClassLoader());
			this.clinchTime = in.readString();
			this.couponDiscount = (Double) in.readValue(Double.class.getClassLoader());
			this.createTime = in.readString();
			this.deliveryCode = in.readString();
			this.deliveryFee = (Double) in.readValue(Double.class.getClassLoader());
			this.deliveryTime = in.readString();
			this.deliveryType = (Integer) in.readValue(Integer.class.getClassLoader());
			this.discount = (Double) in.readValue(Double.class.getClassLoader());
			this.erase = (Double) in.readValue(Double.class.getClassLoader());
			this.expireMinute = (Integer) in.readValue(Integer.class.getClassLoader());
			this.identified = (Boolean) in.readValue(Boolean.class.getClassLoader());
			this.lastRefundStatus = (Integer) in.readValue(Integer.class.getClassLoader());
			this.levelId = (Integer) in.readValue(Integer.class.getClassLoader());
			this.levelName = in.readString();
			this.memberDiscount = (Double) in.readValue(Double.class.getClassLoader());
			this.memberId = (Integer) in.readValue(Integer.class.getClassLoader());
			this.memberName = in.readString();
			this.memberPhone = in.readString();
			this.memberPoints = (Double) in.readValue(Double.class.getClassLoader());
			this.merchantAddress = in.readString();
			this.merchantId = (Integer) in.readValue(Integer.class.getClassLoader());
			this.merchantName = in.readString();
			this.numDiscount = (Double) in.readValue(Double.class.getClassLoader());
			this.obtainPoints = (Integer) in.readValue(Integer.class.getClassLoader());
			this.orderNo = in.readString();
			this.orderPhone = in.readString();
			this.orderSource = (Integer) in.readValue(Integer.class.getClassLoader());
			this.originOrderNo = in.readString();
			this.overage = (Double) in.readValue(Double.class.getClassLoader());
			this.payType = (Integer) in.readValue(Integer.class.getClassLoader());
			this.planDeliveryTime = in.readString();
			this.platformNo = in.readString();
			this.pointDiscount = (Double) in.readValue(Double.class.getClassLoader());
			this.points = (Integer) in.readValue(Integer.class.getClassLoader());
			this.receiver = in.readString();
			this.receiverPhone = in.readString();
			this.reduceDiscount = (Double) in.readValue(Double.class.getClassLoader());
			this.refundEndTime = in.readString();
			this.refundStatus = (Integer) in.readValue(Integer.class.getClassLoader());
			this.remark = in.readString();
			this.terminalCd = in.readString();
			this.terminalSn = in.readString();
			this.totalAmount = (Double) in.readValue(Double.class.getClassLoader());
			this.totalNum = (Double) in.readValue(Double.class.getClassLoader());
			this.transAmount = (Double) in.readValue(Double.class.getClassLoader());
			this.transFlag = (Integer) in.readValue(Integer.class.getClassLoader());
			this.transTime = in.readString();
			this.transType = (Integer) in.readValue(Integer.class.getClassLoader());
			this.username = in.readString();
		}

		public static final Creator<Order> CREATOR = new Creator<Order>() {
			@Override
			public Order createFromParcel(Parcel source) {
				return new Order(source);
			}

			@Override
			public Order[] newArray(int size) {
				return new Order[size];
			}
		};
	}

	public static class OrderActivityInfo implements Parcelable {
		public Integer activityId;
		public String activityName;
		public Integer activityType;
		public Double discountAmount;
		public Integer merchantId;
		public String orderNo;
		public Double originPrice;


		@Override
		public int describeContents() {
			return 0;
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			dest.writeValue(this.activityId);
			dest.writeString(this.activityName);
			dest.writeValue(this.activityType);
			dest.writeValue(this.discountAmount);
			dest.writeValue(this.merchantId);
			dest.writeString(this.orderNo);
			dest.writeValue(this.originPrice);
		}

		public OrderActivityInfo() {
		}

		protected OrderActivityInfo(Parcel in) {
			this.activityId = (Integer) in.readValue(Integer.class.getClassLoader());
			this.activityName = in.readString();
			this.activityType = (Integer) in.readValue(Integer.class.getClassLoader());
			this.discountAmount = (Double) in.readValue(Double.class.getClassLoader());
			this.merchantId = (Integer) in.readValue(Integer.class.getClassLoader());
			this.orderNo = in.readString();
			this.originPrice = (Double) in.readValue(Double.class.getClassLoader());
		}

		public static final Creator<OrderActivityInfo> CREATOR = new Creator<OrderActivityInfo>() {
			@Override
			public OrderActivityInfo createFromParcel(Parcel source) {
				return new OrderActivityInfo(source);
			}

			@Override
			public OrderActivityInfo[] newArray(int size) {
				return new OrderActivityInfo[size];
			}
		};
	}

	public static class OrderCouponInfo implements Parcelable{
		public String couponCode;
		public Integer couponId;
		public String couponName;
		public String createTime;
		public Double discount;
		public Integer id;
		public String orderNo;
		public Double originPrice;
		public String productIds;
		public String updateTime;


		@Override
		public int describeContents() {
			return 0;
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			dest.writeString(this.couponCode);
			dest.writeValue(this.couponId);
			dest.writeString(this.couponName);
			dest.writeString(this.createTime);
			dest.writeValue(this.discount);
			dest.writeValue(this.id);
			dest.writeString(this.orderNo);
			dest.writeValue(this.originPrice);
			dest.writeString(this.productIds);
			dest.writeString(this.updateTime);
		}

		public OrderCouponInfo() {
		}

		protected OrderCouponInfo(Parcel in) {
			this.couponCode = in.readString();
			this.couponId = (Integer) in.readValue(Integer.class.getClassLoader());
			this.couponName = in.readString();
			this.createTime = in.readString();
			this.discount = (Double) in.readValue(Double.class.getClassLoader());
			this.id = (Integer) in.readValue(Integer.class.getClassLoader());
			this.orderNo = in.readString();
			this.originPrice = (Double) in.readValue(Double.class.getClassLoader());
			this.productIds = in.readString();
			this.updateTime = in.readString();
		}

		public static final Creator<OrderCouponInfo> CREATOR = new Creator<OrderCouponInfo>() {
			@Override
			public OrderCouponInfo createFromParcel(Parcel source) {
				return new OrderCouponInfo(source);
			}

			@Override
			public OrderCouponInfo[] newArray(int size) {
				return new OrderCouponInfo[size];
			}
		};
	}

	public static class OrderDetail implements Parcelable{
		public Double ableRefundNum;
		public String barcode;
		public String brand;
		public Double costAmount;
		public String createTime;
		public Double discountAmount;
		public Double erase;
		public Integer id;
		public Integer merchantId;
		public Double num;
		public String orderNo;
		public Double originPrice;
		public String pic;
		public String picUrl;
		public Integer productId;
		public String productName;
		public Double profit;
		public String remark;
		public Integer saleType;
		public String standard;
		public Double totalAmount;
		public Double transAmount;
		public String unit;
		public Double unitPrice;
		public String updateTime;
		public Double viewPrice;


		@Override
		public int describeContents() {
			return 0;
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			dest.writeValue(this.ableRefundNum);
			dest.writeString(this.barcode);
			dest.writeString(this.brand);
			dest.writeValue(this.costAmount);
			dest.writeString(this.createTime);
			dest.writeValue(this.discountAmount);
			dest.writeValue(this.erase);
			dest.writeValue(this.id);
			dest.writeValue(this.merchantId);
			dest.writeValue(this.num);
			dest.writeString(this.orderNo);
			dest.writeValue(this.originPrice);
			dest.writeString(this.pic);
			dest.writeString(this.picUrl);
			dest.writeValue(this.productId);
			dest.writeString(this.productName);
			dest.writeValue(this.profit);
			dest.writeString(this.remark);
			dest.writeValue(this.saleType);
			dest.writeString(this.standard);
			dest.writeValue(this.totalAmount);
			dest.writeValue(this.transAmount);
			dest.writeString(this.unit);
			dest.writeValue(this.unitPrice);
			dest.writeString(this.updateTime);
			dest.writeValue(this.viewPrice);
		}

		public OrderDetail() {
		}

		protected OrderDetail(Parcel in) {
			this.ableRefundNum = (Double) in.readValue(Double.class.getClassLoader());
			this.barcode = in.readString();
			this.brand = in.readString();
			this.costAmount = (Double) in.readValue(Double.class.getClassLoader());
			this.createTime = in.readString();
			this.discountAmount = (Double) in.readValue(Double.class.getClassLoader());
			this.erase = (Double) in.readValue(Double.class.getClassLoader());
			this.id = (Integer) in.readValue(Integer.class.getClassLoader());
			this.merchantId = (Integer) in.readValue(Integer.class.getClassLoader());
			this.num = (Double) in.readValue(Double.class.getClassLoader());
			this.orderNo = in.readString();
			this.originPrice = (Double) in.readValue(Double.class.getClassLoader());
			this.pic = in.readString();
			this.picUrl = in.readString();
			this.productId = (Integer) in.readValue(Integer.class.getClassLoader());
			this.productName = in.readString();
			this.profit = (Double) in.readValue(Double.class.getClassLoader());
			this.remark = in.readString();
			this.saleType = (Integer) in.readValue(Integer.class.getClassLoader());
			this.standard = in.readString();
			this.totalAmount = (Double) in.readValue(Double.class.getClassLoader());
			this.transAmount = (Double) in.readValue(Double.class.getClassLoader());
			this.unit = in.readString();
			this.unitPrice = (Double) in.readValue(Double.class.getClassLoader());
			this.updateTime = in.readString();
			this.viewPrice = (Double) in.readValue(Double.class.getClassLoader());
		}

		public static final Creator<OrderDetail> CREATOR = new Creator<OrderDetail>() {
			@Override
			public OrderDetail createFromParcel(Parcel source) {
				return new OrderDetail(source);
			}

			@Override
			public OrderDetail[] newArray(int size) {
				return new OrderDetail[size];
			}
		};

		public Double getAbleRefundNum() {
			return ableRefundNum;
		}

		public void setAbleRefundNum(Double ableRefundNum) {
			this.ableRefundNum = ableRefundNum;
		}

		public String getBarcode() {
			return barcode;
		}

		public void setBarcode(String barcode) {
			this.barcode = barcode;
		}

		public String getBrand() {
			return brand;
		}

		public void setBrand(String brand) {
			this.brand = brand;
		}

		public Double getCostAmount() {
			return costAmount;
		}

		public void setCostAmount(Double costAmount) {
			this.costAmount = costAmount;
		}

		public String getCreateTime() {
			return createTime;
		}

		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}

		public Double getDiscountAmount() {
			return discountAmount;
		}

		public void setDiscountAmount(Double discountAmount) {
			this.discountAmount = discountAmount;
		}

		public Double getErase() {
			return erase;
		}

		public void setErase(Double erase) {
			this.erase = erase;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public Integer getMerchantId() {
			return merchantId;
		}

		public void setMerchantId(Integer merchantId) {
			this.merchantId = merchantId;
		}

		public Double getNum() {
			return num;
		}

		public void setNum(Double num) {
			this.num = num;
		}

		public String getOrderNo() {
			return orderNo;
		}

		public void setOrderNo(String orderNo) {
			this.orderNo = orderNo;
		}

		public Double getOriginPrice() {
			return originPrice;
		}

		public void setOriginPrice(Double originPrice) {
			this.originPrice = originPrice;
		}

		public String getPic() {
			return pic;
		}

		public void setPic(String pic) {
			this.pic = pic;
		}

		public String getPicUrl() {
			return picUrl;
		}

		public void setPicUrl(String picUrl) {
			this.picUrl = picUrl;
		}

		public Integer getProductId() {
			return productId;
		}

		public void setProductId(Integer productId) {
			this.productId = productId;
		}

		public String getProductName() {
			return productName;
		}

		public void setProductName(String productName) {
			this.productName = productName;
		}

		public Double getProfit() {
			return profit;
		}

		public void setProfit(Double profit) {
			this.profit = profit;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		public Integer getSaleType() {
			return saleType;
		}

		public void setSaleType(Integer saleType) {
			this.saleType = saleType;
		}

		public String getStandard() {
			return standard;
		}

		public void setStandard(String standard) {
			this.standard = standard;
		}

		public Double getTotalAmount() {
			return totalAmount;
		}

		public void setTotalAmount(Double totalAmount) {
			this.totalAmount = totalAmount;
		}

		public Double getTransAmount() {
			return transAmount;
		}

		public void setTransAmount(Double transAmount) {
			this.transAmount = transAmount;
		}

		public String getUnit() {
			return unit;
		}

		public void setUnit(String unit) {
			this.unit = unit;
		}

		public Double getUnitPrice() {
			return unitPrice;
		}

		public void setUnitPrice(Double unitPrice) {
			this.unitPrice = unitPrice;
		}

		public String getUpdateTime() {
			return updateTime;
		}

		public void setUpdateTime(String updateTime) {
			this.updateTime = updateTime;
		}

		public Double getViewPrice() {
			return viewPrice;
		}

		public void setViewPrice(Double viewPrice) {
			this.viewPrice = viewPrice;
		}
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.orderNo);
		dest.writeParcelable(this.order, flags);
		dest.writeTypedArray(this.orderActivityInfoList, flags);
		dest.writeTypedArray(this.orderCouponInfoList, flags);
		dest.writeTypedArray(this.orderDetailList, flags);
	}

	public PlaceOrderData() {
	}

	protected PlaceOrderData(Parcel in) {
		this.orderNo = in.readString();
		this.order = in.readParcelable(Order.class.getClassLoader());
		this.orderActivityInfoList = in.createTypedArray(OrderActivityInfo.CREATOR);
		this.orderCouponInfoList = in.createTypedArray(OrderCouponInfo.CREATOR);
		this.orderDetailList = in.createTypedArray(OrderDetail.CREATOR);
	}

	public static final Creator<PlaceOrderData> CREATOR = new Creator<PlaceOrderData>() {
		@Override
		public PlaceOrderData createFromParcel(Parcel source) {
			return new PlaceOrderData(source);
		}

		@Override
		public PlaceOrderData[] newArray(int size) {
			return new PlaceOrderData[size];
		}
	};

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public OrderActivityInfo[] getOrderActivityInfoList() {
		return orderActivityInfoList;
	}

	public void setOrderActivityInfoList(OrderActivityInfo[] orderActivityInfoList) {
		this.orderActivityInfoList = orderActivityInfoList;
	}

	public OrderCouponInfo[] getOrderCouponInfoList() {
		return orderCouponInfoList;
	}

	public void setOrderCouponInfoList(OrderCouponInfo[] orderCouponInfoList) {
		this.orderCouponInfoList = orderCouponInfoList;
	}

	public OrderDetail[] getOrderDetailList() {
		return orderDetailList;
	}

	public void setOrderDetailList(OrderDetail[] orderDetailList) {
		this.orderDetailList = orderDetailList;
	}
}
