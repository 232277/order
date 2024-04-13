package com.dyc.order.cashier.data.fields;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * func: 支付请求域
 * author:丁语成 on 2020/2/24 10:39
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class PaymentRequestFields implements Parcelable {
	Boolean flag;//直接收款标志
	public Order order;
	public ProductInfoList[] productInfoList;
	public Boolean transProp;//true正常支付 false再次支付
	public static class Order implements Parcelable{
		public Double erase;//抹零
//		public Double discount;//特价价格
		public Integer orderSource;//订单来源 0现金 1主扫 3被扫 5银行 6刷脸 7储值卡
		public Integer payType;//支付方式
		public Double totalNum;//商品总数
		public Double transAmount;//实付金额
		public String authCode;//授权码（被扫）
//		public Double deliveryFee;//配送费
		public String[] couponList;
		public Integer memberId;//会员id
		public Integer merchantId;//商户id
		public String orderNo;//订单号,不可填，后台赋值
		public Integer points;
		public String remark;//备注
		public Boolean self;//是否为自助模式
		public String terminalCd;//终端号
		public String terminalSn;//终端序列号
		public Double totalAmount;//交易总金额
//		public Double productAmount;

		public Order(){
		}

		public Order(Double erase, Integer orderSource, String orderNo, Integer payType, Double totalNum, Double transAmount, String authCode, Integer memberId, String remark, String terminalCd, String terminalSn, Double totalAmount) {
			if (erase > 0){
				this.erase = erase;
			}
			this.orderSource = orderSource;
			this.orderNo = orderNo;
			this.payType = payType;
			this.totalNum = totalNum;
			this.transAmount = transAmount;
			this.authCode = authCode;
			this.memberId = memberId;
			this.remark = remark;
			this.terminalCd = terminalCd;
			this.terminalSn = terminalSn;
			this.totalAmount = totalAmount;
		}

		public String getOrderNo() {
			return orderNo;
		}

		public void setOrderNo(String orderNo) {
			this.orderNo = orderNo;
		}

		public Integer getPayType() {
			return payType;
		}

		public void setPayType(Integer payType) {
			this.payType = payType;
		}

		public Double getErase() {
			return erase;
		}

		public void setErase(Double erase) {
			this.erase = erase;
		}

		public Integer getOrderSource() {
			return orderSource;
		}

		public void setOrderSource(Integer orderSource) {
			this.orderSource = orderSource;
		}

		public Double getTotalNum() {
			return totalNum;
		}

		public void setTotalNum(Double totalNum) {
			this.totalNum = totalNum;
		}

		public Double getTransAmount() {
			return transAmount;
		}

		public void setTransAmount(Double transAmount) {
			this.transAmount = transAmount;
		}

		public String getAuthCode() {
			return authCode;
		}

		public void setAuthCode(String authCode) {
			this.authCode = authCode;
		}

		public Integer getMemberId() {
			return memberId;
		}

		public void setMemberId(Integer memberId) {
			this.memberId = memberId;
		}

		public Integer getMerchantId() {
			return merchantId;
		}

		public void setMerchantId(Integer merchantId) {
			this.merchantId = merchantId;
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

		public Double getTotalAmount() {
			return totalAmount;
		}

		public void setTotalAmount(Double totalAmount) {
			this.totalAmount = totalAmount;
		}

		public Integer getPoints() {
			return points;
		}

		public void setPoints(Integer points) {
			this.points = points;
		}

		public String[] getCouponList() {
			return couponList;
		}

		public void setCouponList(String[] couponList) {
			this.couponList = couponList;
		}

		public Boolean isSelf() {
			return self;
		}

		public void setSelf(Boolean self) {
			this.self = self;
		}

//		public Double getProductAmount() {
//			return productAmount;
//		}
//
//		public void setProductAmount(Double productAmount) {
//			this.productAmount = productAmount;
//		}


		@Override
		public int describeContents() {
			return 0;
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			dest.writeValue(this.erase);
			dest.writeValue(this.orderSource);
			dest.writeValue(this.payType);
			dest.writeValue(this.totalNum);
			dest.writeValue(this.transAmount);
			dest.writeString(this.authCode);
			dest.writeStringArray(this.couponList);
			dest.writeValue(this.memberId);
			dest.writeValue(this.merchantId);
			dest.writeString(this.orderNo);
			dest.writeValue(this.points);
			dest.writeString(this.remark);
			dest.writeValue(this.self);
			dest.writeString(this.terminalCd);
			dest.writeString(this.terminalSn);
			dest.writeValue(this.totalAmount);
		}

		protected Order(Parcel in) {
			this.erase = (Double) in.readValue(Double.class.getClassLoader());
			this.orderSource = (Integer) in.readValue(Integer.class.getClassLoader());
			this.payType = (Integer) in.readValue(Integer.class.getClassLoader());
			this.totalNum = (Double) in.readValue(Double.class.getClassLoader());
			this.transAmount = (Double) in.readValue(Double.class.getClassLoader());
			this.authCode = in.readString();
			this.couponList = in.createStringArray();
			this.memberId = (Integer) in.readValue(Integer.class.getClassLoader());
			this.merchantId = (Integer) in.readValue(Integer.class.getClassLoader());
			this.orderNo = in.readString();
			this.points = (Integer) in.readValue(Integer.class.getClassLoader());
			this.remark = in.readString();
			this.self = (Boolean) in.readValue(Boolean.class.getClassLoader());
			this.terminalCd = in.readString();
			this.terminalSn = in.readString();
			this.totalAmount = (Double) in.readValue(Double.class.getClassLoader());
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

	public static class ProductInfoList implements Parcelable{
		public Double sellNum;//销售数量
		public Double transAmount;//实收金额
		public Double unitPrice;//单价
		public Double changeNumber;//单价
		public Boolean priceChangeFlag;//改价标志
		public Integer productId;//商品id
		public String productName;//商品名
		public String remark;//备注
		public Double totalAmount;//总金额
//		public Integer priceType;
//		public Double subTotal;
//		public Double viewPrice;

		public ProductInfoList(){}

		public ProductInfoList(Double sellNum, Double transAmount, Double unitPrice) {
			this.sellNum = sellNum;
			this.transAmount = transAmount;
			this.unitPrice = unitPrice;
		}

		public ProductInfoList(Double sellNum, Double transAmount, Double unitPrice, Double changeNumber,Boolean priceChangeFlag, Integer productId, String productName, String remark, Double totalAmount) {
			this.sellNum = sellNum;
			this.transAmount = transAmount;
			this.unitPrice = unitPrice;
			this.changeNumber = changeNumber;
			this.priceChangeFlag = priceChangeFlag;
			this.productId = productId;
			this.productName = productName;
			this.remark = remark;
			this.totalAmount = totalAmount;
		}

		public Boolean getPriceChangeFlag() {
			return priceChangeFlag;
		}

		public void setPriceChangeFlag(Boolean priceChangeFlag) {
			this.priceChangeFlag = priceChangeFlag;
		}

		public Double getSellNum() {
			return sellNum;
		}

		public void setSellNum(Double sellNum) {
			this.sellNum = sellNum;
		}

		public Double getTransAmount() {
			return transAmount;
		}

		public void setTransAmount(Double transAmount) {
			this.transAmount = transAmount;
		}

		public Double getUnitPrice() {
			return unitPrice;
		}

		public void setUnitPrice(Double unitPrice) {
			this.unitPrice = unitPrice;
		}

		public Double getChangeNumber() {
			return changeNumber;
		}

		public void setChangeNumber(Double changeNumber) {
			this.changeNumber = changeNumber;
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

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		public Double getTotalAmount() {
			return totalAmount;
		}

		public void setTotalAmount(Double totalAmount) {
			this.totalAmount = totalAmount;
		}

//		public Integer getPriceType() {
//			return priceType;
//		}
//
//		public Double getSubTotal() {
//			return subTotal;
//		}
//
//		public void setPriceType(Integer priceType) {
//			this.priceType = priceType;
//		}
//
//		public void setSubTotal(Double subTotal) {
//			this.subTotal = subTotal;
//		}
//
//		public Double getViewPrice() {
//			return viewPrice;
//		}
//
//		public void setViewPrice(Double viewPrice) {
//			this.viewPrice = viewPrice;
//		}


		@Override
		public int describeContents() {
			return 0;
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			dest.writeValue(this.sellNum);
			dest.writeValue(this.transAmount);
			dest.writeValue(this.unitPrice);
			dest.writeValue(this.changeNumber);
			dest.writeValue(this.priceChangeFlag);
			dest.writeValue(this.productId);
			dest.writeString(this.productName);
			dest.writeString(this.remark);
			dest.writeValue(this.totalAmount);
		}

		protected ProductInfoList(Parcel in) {
			this.sellNum = (Double) in.readValue(Double.class.getClassLoader());
			this.transAmount = (Double) in.readValue(Double.class.getClassLoader());
			this.unitPrice = (Double) in.readValue(Double.class.getClassLoader());
			this.changeNumber = (Double) in.readValue(Double.class.getClassLoader());
			this.priceChangeFlag = (Boolean) in.readValue(Boolean.class.getClassLoader());
			this.productId = (Integer) in.readValue(Integer.class.getClassLoader());
			this.productName = in.readString();
			this.remark = in.readString();
			this.totalAmount = (Double) in.readValue(Double.class.getClassLoader());
		}

		public static final Creator<ProductInfoList> CREATOR = new Creator<ProductInfoList>() {
			@Override
			public ProductInfoList createFromParcel(Parcel source) {
				return new ProductInfoList(source);
			}

			@Override
			public ProductInfoList[] newArray(int size) {
				return new ProductInfoList[size];
			}
		};
	}

	public Boolean isFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public ProductInfoList[] getProductInfoList() {
		return productInfoList;
	}

	public void setProductInfoList(ProductInfoList[] productInfoList) {
		this.productInfoList = productInfoList;
	}

	public Boolean isTransProp() {
		return transProp;
	}

	public void setTransProp(Boolean transProp) {
		this.transProp = transProp;
	}

	public Boolean getFlag() {
		return flag;
	}

	public Boolean getTransProp() {
		return transProp;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeValue(this.flag);
		dest.writeParcelable(this.order, flags);
		dest.writeTypedArray(this.productInfoList, flags);
		dest.writeValue(this.transProp);
	}

	public PaymentRequestFields() {
	}

	protected PaymentRequestFields(Parcel in) {
		this.flag = (Boolean) in.readValue(Boolean.class.getClassLoader());
		this.order = in.readParcelable(Order.class.getClassLoader());
		this.productInfoList = in.createTypedArray(ProductInfoList.CREATOR);
		this.transProp = (Boolean) in.readValue(Boolean.class.getClassLoader());
	}

	public static final Creator<PaymentRequestFields> CREATOR = new Creator<PaymentRequestFields>() {
		@Override
		public PaymentRequestFields createFromParcel(Parcel source) {
			return new PaymentRequestFields(source);
		}

		@Override
		public PaymentRequestFields[] newArray(int size) {
			return new PaymentRequestFields[size];
		}
	};
}
