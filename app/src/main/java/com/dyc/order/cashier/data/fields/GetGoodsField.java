package com.dyc.order.cashier.data.fields;

/**
 * func:
 * author:丁语成 on 2020/3/12 10:26
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class GetGoodsField {
	public int pageNum;
	public int pageSize;
	public String barcode;
	public String brand;
	public String customer;
	public Integer id;
	public Integer merchantId;
	public String name;
	public String orderByColumn;//排序列,例如 amount desc,saled asc
	public String saleType;//逗号隔开，可多选，0按件，1称重
	public String status;//逗号隔开，可多选，0启用，1停用
	public String supplierId;//逗号隔开，可多选
	public String type;//逗号隔开，可多选
	public String words;//查询词【条码/名称模糊搜索】

	public GetGoodsField(){}

	public GetGoodsField(int pageNum, int pageSize) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
	}

	public GetGoodsField(int pageNum, int pageSize, String status, String supplierId, String type) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.status = status;
		this.supplierId = supplierId;
		this.type = type;
	}
}
