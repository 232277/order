package com.dyc.order.cashier.data.response;

import java.util.Arrays;
import java.util.List;

/**
 * func: 商品信息列表
 * author:丁语成 on 2020/2/18 20:56
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class GoodsInfoList{
	private Integer total;//总数
	private GoodsDetailData[] rows;//商品表

	public List<GoodsDetailData> toList(){
		return Arrays.asList(rows);
	}

	public static class GoodsDetailData {
		private int id;//商品id
		private Integer merchantId;//商户id
		private String name;//品名
		private String barcode;//商品条码
		private String firstLetter;//首字母
		private String standard;
		private String unit;//单位
		private Integer typeId;//类型码
		private String typeName;//类型名
		private String brand;//品牌
		private String imgTemp;//图片缓冲地址
		private String pic;
		private String[] pictures;//图片
		private Integer supplierId;//供应商ID
		private String supplierName;//供应商名
		private Double cost;//进货价
		private Double avgCost;//平均成本
		private Double totalCost;//总成本金额，库存金额
		private Double price;//售价
		private Double memberPrice;//会员价
		private Double soldNum;//已销售数
		private Integer status;//商品状态 0启用 1停用
		private Integer saleType;//0按件 1称重
		private Double number;//库存
		private Double limitNum;//库存预警
		private String createTime;//创建时间
		private Double saleNum;//已售
		private String remark;//备注
		private GoodsActivityInfos[] activityInfos;//活动信息

		public static class GoodsActivityInfos {
			private int id; //活动id
			private String name;//活动名
			private Integer type;//活动类型 1特价 2满金额减 3满件数打折 4满件数赠送
			private String rule;//活动规则
			private Double discountPrice;//折扣价
			private Double discountAmount;
			private boolean giftFlag;//赠送标志0不是 1是

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

			public Integer getType() {
				return type;
			}

			public void setType(Integer type) {
				this.type = type;
			}

			public String getRule() {
				return rule;
			}

			public void setRule(String rule) {
				this.rule = rule;
			}

			public Double getDiscountPrice() {
				return discountPrice;
			}

			public void setDiscountPrice(Double discountPrice) {
				this.discountPrice = discountPrice;
			}

			public boolean isGiftFlag() {
				return giftFlag;
			}

			public void setGiftFlag(boolean giftFlag) {
				this.giftFlag = giftFlag;
			}

			public Double getDiscountAmount() {
				return discountAmount;
			}

			public void setDiscountAmount(Double discountAmount) {
				this.discountAmount = discountAmount;
			}
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public Integer getMerchantId() {
			return merchantId;
		}

		public void setMerchantId(Integer merchantId) {
			this.merchantId = merchantId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getBarcode() {
			return barcode;
		}

		public void setBarcode(String barcode) {
			this.barcode = barcode;
		}

		public String getFirstLetter() {
			return firstLetter;
		}

		public void setFirstLetter(String firstLetter) {
			this.firstLetter = firstLetter;
		}

		public String getStandard() {
			return standard;
		}

		public void setStandard(String standard) {
			this.standard = standard;
		}

		public String getUnit() {
			return unit;
		}

		public void setUnit(String unit) {
			this.unit = unit;
		}

		public Integer getTypeId() {
			return typeId;
		}

		public void setTypeId(Integer typeId) {
			this.typeId = typeId;
		}

		public String getTypeName() {
			return typeName;
		}

		public void setTypeName(String typeName) {
			this.typeName = typeName;
		}

		public String getBrand() {
			return brand;
		}

		public void setBrand(String brand) {
			this.brand = brand;
		}

		public String getImgTemp() {
			return imgTemp;
		}

		public void setImgTemp(String imgTemp) {
			this.imgTemp = imgTemp;
		}

		public String getPic() {
			return pic;
		}

		public void setPic(String pic) {
			this.pic = pic;
		}

		public String[] getPictures() {
			return pictures;
		}

		public void setPictures(String[] pictures) {
			this.pictures = pictures;
		}

		public Integer getSupplierId() {
			return supplierId;
		}

		public void setSupplierId(Integer supplierId) {
			this.supplierId = supplierId;
		}

		public String getSupplierName() {
			return supplierName;
		}

		public void setSupplierName(String supplierName) {
			this.supplierName = supplierName;
		}

		public Double getCost() {
			return cost;
		}

		public void setCost(Double cost) {
			this.cost = cost;
		}

		public Double getAvgCost() {
			return avgCost;
		}

		public void setAvgCost(Double avgCost) {
			this.avgCost = avgCost;
		}

		public Double getTotalCost() {
			return totalCost;
		}

		public void setTotalCost(Double totalCost) {
			this.totalCost = totalCost;
		}

		public Double getPrice() {
			return price;
		}

		public void setPrice(Double price) {
			this.price = price;
		}

		public Double getMemberPrice() {
			return memberPrice;
		}

		public void setMemberPrice(Double memberPrice) {
			this.memberPrice = memberPrice;
		}

		public Double getSoldNum() {
			return soldNum;
		}

		public void setSoldNum(Double soldNum) {
			this.soldNum = soldNum;
		}

		public Integer getStatus() {
			return status;
		}

		public void setStatus(Integer status) {
			this.status = status;
		}

		public Integer getSaleType() {
			return saleType;
		}

		public void setSaleType(Integer saleType) {
			this.saleType = saleType;
		}

		public Double getNumber() {
			return number;
		}

		public void setNumber(Double number) {
			this.number = number;
		}

		public Double getLimitNum() {
			return limitNum;
		}

		public void setLimitNum(Double limitNum) {
			this.limitNum = limitNum;
		}

		public String getCreateTime() {
			return createTime;
		}

		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}

		public Double getSaleNum() {
			return saleNum;
		}

		public void setSaleNum(Double saleNum) {
			this.saleNum = saleNum;
		}

		public GoodsActivityInfos[] getActivityInfos() {
			return activityInfos;
		}

		public void setActivityInfos(GoodsActivityInfos[] activityInfos) {
			this.activityInfos = activityInfos;
		}
	}

	@Override
	public String toString() {
		return "total:" + total + " rows:" + rows[0].toString();
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public GoodsDetailData[] getRows() {
		return rows;
	}

	public void setRows(GoodsDetailData[] rows) {
		this.rows = rows;
	}
}
