package com.dyc.order.cashier.data.fields;

import com.dyc.order.cashier.data.response.GoodsInfoList;

/**
 * func:修改商品信息的bean
 * author:丁语成 on 2020/2/21 16:11
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class ChangeGoodsInfoFields extends GoodsRequestComonInfo {
	private int id;//商品ID

	public ChangeGoodsInfoFields() {
		super();
	}

	public ChangeGoodsInfoFields(GoodsInfoList.GoodsDetailData detailData) {
		super(detailData);
		this.id = detailData.getId();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
