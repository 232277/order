package com.dyc.order.cashier.data.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * func:
 * author:丁语成 on 2020/6/23 10:00
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class RechargeRuleData implements Parcelable {
	private String effectiveTime;
	private Double faceValue;
	private int id;
	private String invalidTime;
	private Double sellingPrice;

	public String getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(String effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public Double getFaceValue() {
		return faceValue;
	}

	public void setFaceValue(Double faceValue) {
		this.faceValue = faceValue;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInvalidTime() {
		return invalidTime;
	}

	public void setInvalidTime(String invalidTime) {
		this.invalidTime = invalidTime;
	}

	public Double getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(Double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.effectiveTime);
		dest.writeValue(this.faceValue);
		dest.writeInt(this.id);
		dest.writeString(this.invalidTime);
		dest.writeValue(this.sellingPrice);
	}

	public RechargeRuleData() {
	}

	protected RechargeRuleData(Parcel in) {
		this.effectiveTime = in.readString();
		this.faceValue = (Double) in.readValue(Double.class.getClassLoader());
		this.id = in.readInt();
		this.invalidTime = in.readString();
		this.sellingPrice = (Double) in.readValue(Double.class.getClassLoader());
	}

	public static final Parcelable.Creator<RechargeRuleData> CREATOR = new Parcelable.Creator<RechargeRuleData>() {
		@Override
		public RechargeRuleData createFromParcel(Parcel source) {
			return new RechargeRuleData(source);
		}

		@Override
		public RechargeRuleData[] newArray(int size) {
			return new RechargeRuleData[size];
		}
	};
}
