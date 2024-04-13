package com.dyc.order.cashier.data.response;

/**
 * func:
 * author:丁语成 on 2020/4/1 16:39
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */

/**
 * 活动详情之后字段的变动        activityDetail
 *  productId->identity  identity根据过滤类型 分为条码/品类
 * product_name->name 商品名称/品类名称
 *
 * {"code":"response.success",
 * "msg":"操作成功",
 * "data":{
 * "total":1,
 * "rows":[
 * {
 * "id":27,"merchantIds":"1","merchantId":1,"name":"周末满减",
 * "type":3,
 * 		"rule":"[{\"threshold\":100,\"discount\":50
 * 		}]",
 * 	"cycleRule":"*","startDetailTime":"000000","endDetailTime":"235900",
 * 	"startTime":"2020-04-09 00:00:00","endTime":"2020-05-29 23:59:59",
 * 	"status":true,"auditStatus":true,"isDeleted":0,"filterType":0,
 * 	"createTime":"2020-04-07 11:10:22","updateBy":0,"updateTime":"2020-04-09 11:22:48",
 * 	"merchantName":"一米阳光便利店","docMakerName":"李东东","auditorName":"李东东"
 * 	}
 * 	]
 * 	},"serverTime":1586422855923}
 */
public class ActivityInfoData {
	public Object info;
	public ActivityRow[] rows;
	public long total;
	public static class ActivityRow{
		public ActivityDetail[] activityDetailList;
		public static class ActivityDetail{
			public int activityId;
			public String barcode;
			public double discountAmount;
			public double discountPrice;
			public boolean giftFlag;
			public int id;
			public double limitNum;
			public String pic;
			public double price;
			public String identity;
			public String name;
		}
		public boolean auditStatus;
		public String auditTime;
		public int auditor;
		public String auditorName;
		public String createTime;
		public String cycleRule;
		public int docMaker;
		public String docMakerName;
		public String endDetailTime;
		public String endTime;
		public String filter;
		public Integer filterType;//0全品，1品类过滤，2商品过滤
		public Integer id;
		public Integer isDeleted;//0:否 1:是
		public String makeTime;
		public int merchantId;
//		public int[] merchantIdList;//参与的活动ID列表，用来判断是否存在互斥的门店
		public String merchantIds;
		public String merchantName;
		public String name;
		public String rule;
		public String startDetailTime;
		public String startTime;
		public boolean status;
		public int type;
		public int updateBy;
		public String updateTime;
		public String updater;
	}
}
