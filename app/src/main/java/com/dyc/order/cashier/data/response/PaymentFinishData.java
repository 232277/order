package com.dyc.order.cashier.data.response;

/**
 * func:
 * author:丁语成 on 2020/3/11 11:06
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */

/**
 * {
 * "status":true,
 * "orderNo":"554873308248821760",
 * "printInfo":{
 * "order":
 * {
 * "orderNo":"554873308248821760",
 * "merchantId":1,"transType":2,"transTime":"2020-03-11 11:48:25",
 * "transAmount":100.00,"payType":0,"cashierId":1,
 * "totalAmount":90.00,"totalNum":5.00,"discount":0.00,
 * "erase":0.00,"terminalCd":"D1V0120000008","terminalSn":"D1V0120000008",
 * "orderSource":2,"transFlag":1,"clinchTime":"2020-03-11 11:48:25",
 * "createTime":"2020-03-11 11:48:25","merchantName":"阳光便利店12345",
 * "username":"李东东","identified":false,"merchantAddress":"福州软件园"
 * }
 * }
 * }
 *
 * {
 * "status":true,"msg":"状态:OK成功",
 * "orderNo":"554898844136263680",
 * "printInfo":{
 * "order":{
 * "orderNo":"554898844136263680","merchantId":1,"transType":2,
 * "transTime":"2020-03-11 13:29:56","transAmount":166.50,
 * "payType":3,"cashierId":1,"totalAmount":166.50,"totalNum":4.00,
 * "discount":0.00,"erase":0.00,"terminalCd":"D1V0120000008",
 * "terminalSn":"D1V0120000008","platformNo":"283500554898844169818112",
 * "orderSource":2,"transFlag":1,"clinchTime":"2020-03-11 13:29:56",
 * "createTime":"2020-03-11 13:29:53","merchantName":"阳光便利店12345",
 * "username":"李东东","identified":false,"merchantAddress":"福州软件园"
 * }
 * }
 * }
 *
 * 	 请求付款成功:
 * 	 {
 * 	 "status":true,
 * 	 "msg":"状态:OK成功",
 * 	 "orderNo":"554917216018255872",
 * 	 "printInfo":
 * 	 {
 * 	 "order":
 * 	 {"orderNo":"554917216018255872",
 * 	 "merchantId":1,"transType":0,
 * 	 "transTime":"2020-03-11 14:42:55",
 * 	 "transAmount":45.00,"payType":3,
 * 	 "cashierId":1,"totalAmount":45.00,
 * 	 "totalNum":2.00,"discount":0.00,
 * 	 "erase":0.00,"terminalCd":"D1V0120000008",
 * 	 "terminalSn":"D1V0120000008","platformNo":
 * 	 "283500554917216060198912","orderSource":2,
 * 	 "transFlag":1,"clinchTime":"2020-03-11 14:42:55",
 * 	 "createTime":"2020-03-11 14:42:54",
 * 	 "merchantName":"阳光便利店12345","username":"李东东",
 * 	 "identified":false,"merchantAddress":"福州软件园"},
 * 	 "orderDetailList":
 * 	 [
 * 	 {
 * 	 "merchantId":1,"productId":89,"orderNo":"554917216018255872",
 * 	 "unitPrice":22.50,"originPrice":22.50,"barcode":"10000009",
 * 	 "standard":"1个","unit":"个","brand":"浪漫の樱花",
 * 	 "productName":"【财神财气旺旺旺】汽车车内饰品导航架重力感应车载汽车手机支架",
 * 	 "num":1.00,"totalAmount":22.50,"discountAmount":0.00,"transAmount":22.50,
 * 	 "costAmount":83.2800,"profit":-60.7800,
 * 	 "erase":0.00,"createTime":"2020-03-11 14:42:54"
 * 	 },{
 * 	 "merchantId":1,"productId":93,"orderNo":"554917216018255872",
 * 	 "unitPrice":22.50,"originPrice":22.50,
 * 	 "barcode":"133","standard":"2个",
 * 	 "unit":"个","brand":"SEIWA",
 * 	 "productName":"【2个装】女款卡通小熊兔子小鸡公鸡仔公仔毛绒球汽车钥匙扣",
 * 	 "num":1.00,"totalAmount":22.50,"discountAmount":0.00,
 * 	 "transAmount":22.50,"costAmount":0.0000,"profit":22.5000,"erase":0.00,
 * 	 "createTime":"2020-03-11 14:42:54"}]
 * 	 }
 * 	 }
 */
public class PaymentFinishData {
	public boolean status;
	public String msg;
	public String orderNo;
	public String codeUrl;
	public PrintInfo printInfo;

	public static class PrintInfo{
		public OrderListQueryData.OrderDetailData.Order order;
		public OrderListQueryData.OrderDetailData.OrderDetail[] orderDetailList;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getCodeUrl() {
		return codeUrl;
	}

	public void setCodeUrl(String codeUrl) {
		this.codeUrl = codeUrl;
	}

	public boolean isStatus() {
		return status;
	}

	public PrintInfo getPrintInfo() {
		return printInfo;
	}

	public void setPrintInfo(PrintInfo printInfo) {
		this.printInfo = printInfo;
	}
}
