package com.dyc.order.cashier.data.response;

/**
 * func: 商品类型
 * author:丁语成 on 2020/2/21 17:49
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class GoodsTypeInfo {
	private String createTime;//分类创建时间
	private int id;//分类id
	private String name;//分类名
	private int parentId;//父级别
	private GoodsTypeInfo[] subCategory;//商品表

	public GoodsTypeInfo(){}

	public GoodsTypeInfo(String createTime, int id, String name, int parentId, GoodsTypeInfo[] subCategory) {
		this.createTime = createTime;
		this.id = id;
		this.name = name;
		this.parentId = parentId;
		this.subCategory = subCategory;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

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

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public GoodsTypeInfo[] getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(GoodsTypeInfo[] subCategory) {
		this.subCategory = subCategory;
	}
}
