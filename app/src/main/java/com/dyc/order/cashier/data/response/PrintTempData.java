package com.dyc.order.cashier.data.response;

/**
 * func:
 * author:丁语成 on 2020/3/11 10:42
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class PrintTempData {
	public String createTime;
	public String footer;//模板底部文本,最多300
	public String header;//模板头部文本,最多300
	public int id;
	public int merchantId;
	public String merchantName;
	public String pic;//图片文件名，最多300,分号分割
	public String templateName;
	public int type;//1=小票 2=模板
	public String updateTime;

	@Override
	public String toString() {
		return "createTime: " +createTime + " footer: " + footer + " header: " + header + " id: " + id
				+ " merchantId: " + merchantId + " merchantName: " + merchantName + " pic: " + pic
				+ " templateName: " + templateName + " type: " + type + " updateTime: " + updateTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getFooter() {
		return footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(int merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
}
