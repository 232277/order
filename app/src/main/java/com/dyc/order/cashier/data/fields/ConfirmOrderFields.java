package com.dyc.order.cashier.data.fields;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * func:
 * author:丁语成 on 2020/5/18 16:26
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class ConfirmOrderFields implements Parcelable{
	public String authCode;
	public String orderNo;
	public Integer payType;
	public Integer thirdPartFlag;
	public Transaction transaction;

	public static class Transaction implements Parcelable {
		public String orderNo;
		public Double originAmount;
		public Integer payType;
		public String platformNo;
		public String terminalId;
		public Double transAmount;
		public Integer transFlag;
		public String authCode;
		public String batchNo;
		public String cardNo;
		public String cardinPutMethod;
		public String merchantName;
		public String merchantOrderNo;
		public Double oldAmount;
		public String operatorId;
		public String refNo;
		public String scanOrderId;
		public String traceNo;
		public String transDate;
		public String transTime;
		public String transType;
		public String unionNo;
		public String remark;

		public String getOrderNo() {
			return orderNo;
		}

		public void setOrderNo(String orderNo) {
			this.orderNo = orderNo;
		}

		public Double getOriginAmount() {
			return originAmount;
		}

		public void setOriginAmount(Double originAmount) {
			this.originAmount = originAmount;
		}

		public Integer getPayType() {
			return payType;
		}

		public void setPayType(Integer payType) {
			this.payType = payType;
		}

		public String getPlatformNo() {
			return platformNo;
		}

		public void setPlatformNo(String platformNo) {
			this.platformNo = platformNo;
		}

		public String getTerminalId() {
			return terminalId;
		}

		public void setTerminalId(String terminalId) {
			this.terminalId = terminalId;
		}

		public Double getTransAmount() {
			return transAmount;
		}

		public void setTransAmount(Double transAmount) {
			this.transAmount = transAmount;
		}

		public Integer getTransFlag() {
			return transFlag;
		}

		public void setTransFlag(Integer transFlag) {
			this.transFlag = transFlag;
		}

		public String getAuthCode() {
			return authCode;
		}

		public void setAuthCode(String authCode) {
			this.authCode = authCode;
		}

		public String getBatchNo() {
			return batchNo;
		}

		public void setBatchNo(String batchNo) {
			this.batchNo = batchNo;
		}

		public String getCardNo() {
			return cardNo;
		}

		public void setCardNo(String cardNo) {
			this.cardNo = cardNo;
		}

		public String getCardinPutMethod() {
			return cardinPutMethod;
		}

		public void setCardinPutMethod(String cardinPutMethod) {
			this.cardinPutMethod = cardinPutMethod;
		}

		public String getMerchantName() {
			return merchantName;
		}

		public void setMerchantName(String merchantName) {
			this.merchantName = merchantName;
		}

		public String getMerchantOrderNo() {
			return merchantOrderNo;
		}

		public void setMerchantOrderNo(String merchantOrderNo) {
			this.merchantOrderNo = merchantOrderNo;
		}

		public Double getOldAmount() {
			return oldAmount;
		}

		public void setOldAmount(Double oldAmount) {
			this.oldAmount = oldAmount;
		}

		public String getOperatorId() {
			return operatorId;
		}

		public void setOperatorId(String operatorId) {
			this.operatorId = operatorId;
		}

		public String getRefNo() {
			return refNo;
		}

		public void setRefNo(String refNo) {
			this.refNo = refNo;
		}

		public String getScanOrderId() {
			return scanOrderId;
		}

		public void setScanOrderId(String scanOrderId) {
			this.scanOrderId = scanOrderId;
		}

		public String getTraceNo() {
			return traceNo;
		}

		public void setTraceNo(String traceNo) {
			this.traceNo = traceNo;
		}

		public String getTransDate() {
			return transDate;
		}

		public void setTransDate(String transDate) {
			this.transDate = transDate;
		}

		public String getTransTime() {
			return transTime;
		}

		public void setTransTime(String transTime) {
			this.transTime = transTime;
		}

		public String getTransType() {
			return transType;
		}

		public void setTransType(String transType) {
			this.transType = transType;
		}

		public String getUnionNo() {
			return unionNo;
		}

		public void setUnionNo(String unionNo) {
			this.unionNo = unionNo;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		@Override
		public int describeContents() {
			return 0;
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			dest.writeString(this.orderNo);
			dest.writeValue(this.originAmount);
			dest.writeValue(this.payType);
			dest.writeString(this.platformNo);
			dest.writeString(this.terminalId);
			dest.writeValue(this.transAmount);
			dest.writeValue(this.transFlag);
			dest.writeString(this.authCode);
			dest.writeString(this.batchNo);
			dest.writeString(this.cardNo);
			dest.writeString(this.cardinPutMethod);
			dest.writeString(this.merchantName);
			dest.writeString(this.merchantOrderNo);
			dest.writeValue(this.oldAmount);
			dest.writeString(this.operatorId);
			dest.writeString(this.refNo);
			dest.writeString(this.scanOrderId);
			dest.writeString(this.traceNo);
			dest.writeString(this.transDate);
			dest.writeString(this.transTime);
			dest.writeString(this.transType);
			dest.writeString(this.unionNo);
			dest.writeString(this.remark);
		}

		public Transaction() {
		}

		protected Transaction(Parcel in) {
			this.orderNo = in.readString();
			this.originAmount = (Double) in.readValue(Double.class.getClassLoader());
			this.payType = (Integer) in.readValue(Integer.class.getClassLoader());
			this.platformNo = in.readString();
			this.terminalId = in.readString();
			this.transAmount = (Double) in.readValue(Double.class.getClassLoader());
			this.transFlag = (Integer) in.readValue(Integer.class.getClassLoader());
			this.authCode = in.readString();
			this.batchNo = in.readString();
			this.cardNo = in.readString();
			this.cardinPutMethod = in.readString();
			this.merchantName = in.readString();
			this.merchantOrderNo = in.readString();
			this.oldAmount = (Double) in.readValue(Double.class.getClassLoader());
			this.operatorId = in.readString();
			this.refNo = in.readString();
			this.scanOrderId = in.readString();
			this.traceNo = in.readString();
			this.transDate = in.readString();
			this.transTime = in.readString();
			this.transType = in.readString();
			this.unionNo = in.readString();
			this.remark = in.readString();
		}

		public static final Creator<Transaction> CREATOR = new Creator<Transaction>() {
			@Override
			public Transaction createFromParcel(Parcel source) {
				return new Transaction(source);
			}

			@Override
			public Transaction[] newArray(int size) {
				return new Transaction[size];
			}
		};
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
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

	public Integer getThirdPartFlag() {
		return thirdPartFlag;
	}

	public void setThirdPartFlag(Integer thirdPartFlag) {
		this.thirdPartFlag = thirdPartFlag;
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.authCode);
		dest.writeString(this.orderNo);
		dest.writeValue(this.payType);
		dest.writeValue(this.thirdPartFlag);
		dest.writeParcelable(this.transaction, flags);
	}

	public ConfirmOrderFields() {
	}

	protected ConfirmOrderFields(Parcel in) {
		this.authCode = in.readString();
		this.orderNo = in.readString();
		this.payType = (Integer) in.readValue(Integer.class.getClassLoader());
		this.thirdPartFlag = (Integer) in.readValue(Integer.class.getClassLoader());
		this.transaction = in.readParcelable(Transaction.class.getClassLoader());
	}

	public static final Creator<ConfirmOrderFields> CREATOR = new Creator<ConfirmOrderFields>() {
		@Override
		public ConfirmOrderFields createFromParcel(Parcel source) {
			return new ConfirmOrderFields(source);
		}

		@Override
		public ConfirmOrderFields[] newArray(int size) {
			return new ConfirmOrderFields[size];
		}
	};
}
