package com.dyc.order.cashier.data.fields;


import com.dyc.order.cashier.data.response.GoodsInfoList;

/**
 * func:新增/修改商品相关的域
 * author:丁语成 on 2020/2/21 15:55
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class GoodsRequestComonInfo {
	private String barcode;//条码
	private String brand;//品牌
	private Double cost;//进价
	private Integer customer;//是否收银机端
	private String description;//描述
	private String[] imgs;//图片地址
	private Double limitNum;//库存预警
	private Double memberPrice;//会员价
	private Integer merchantId;//商户id
	private String name;//商品名
	private Double number;//库存
	private Double price;//售价
	private String remark;//商品备注
	private Integer saleType;//销售方式
	private String standard;//规格
	private Integer status;//商品状态
	private Integer supplierId;//供应商id
	private Integer type;//商品品类
	private String unit;//商品单位

	public GoodsRequestComonInfo(){}

	public GoodsRequestComonInfo(GoodsInfoList.GoodsDetailData detailData){
		barcode = detailData.getBarcode() == null ? "" : detailData.getBarcode();
		brand = detailData.getBrand() == null ? "" : detailData.getBarcode();
		cost = detailData.getCost();
		imgs = detailData.getPictures() == null ? new String[]{""} : detailData.getPictures();
		limitNum = detailData.getLimitNum();
		memberPrice = detailData.getMemberPrice();
		merchantId = detailData.getMerchantId();
		name = detailData.getName() == null ? "" : detailData.getName();
		number = detailData.getNumber();
		price = detailData.getPrice();
		saleType = detailData.getSaleType();
		standard = detailData.getStandard() == null ? "" : detailData.getStandard();
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

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Integer getCustomer() {
		return customer;
	}

	public void setCustomer(Integer customer) {
		this.customer = customer;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String[] getImgs() {
		return imgs;
	}

	public void setImgs(String[] imgs) {
		this.imgs = imgs;
	}

	public Double getLimitNum() {
		return limitNum;
	}

	public void setLimitNum(Double limitNum) {
		this.limitNum = limitNum;
	}

	public Double getMemberPrice() {
		return memberPrice;
	}

	public void setMemberPrice(Double memberPrice) {
		this.memberPrice = memberPrice;
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

	public Double getNumber() {
		return number;
	}

	public void setNumber(Double number) {
		this.number = number;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
}
