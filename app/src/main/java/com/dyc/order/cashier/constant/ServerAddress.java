package com.dyc.order.cashier.constant;

import rxhttp.wrapper.annotation.DefaultDomain;

/**
 * func: 接口地址
 * author:丁语成 on 2020/2/13 14:43
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class ServerAddress {
	@DefaultDomain
//	public static String BASE_URL = "https://inventory.51cpay.com/inventory-app/";
	public static String BASE_URL = "https://xyt.51cpay.com/inventory-app/";
//	public static String BASE_URL = "http://xytex.51cpay.com/inventory-app/";
//	public static String BASE_URL = "http://172.30.200.204:8089/inventory-app/";
//	public static String BASE_URL = "http://172.30.12.228:8089/inventory-app/";

	public static String LOGIN_ADDRESS = "oauth/token";
//	public static String LOGIN_ADDRESS = "inventory-app/api/coupon/getAbleToUseCoupon";

	public static String GET_PRINT_TEMP_EXACT = "printTemplate/detailByMerchantId/";
	/**
	 * 商品类
	 */
	public static String GOODS_DETAIL_BY_LAST_SIX_ADDRESS = "api/product/productInfo/scan/end";

	public static String GOODS_DETAIL_BY_ID_ADDRESS = "api/product/productInfo/detail/";

	public static String GOODS_BARCODE_OR_NAME_QUERY_ADDRESS = "api/product/productInfo/queryList";

	public static String GET_GOODS_INFO_BACKWARD_ADDRESS = "api/product/productInfo/scan/get";

	public static String GET_GOODS_INFO_THIRD_PART_ADDRESS = "api/product/productInfo/scan/";

	public static String GOODS_LIST_ADDRESS = "api/product/productInfo/list";

	public static String GOODS_SUPPLIERS_ADDRESS = "api/product/productInfo/supplier";

	public static String GOODS_TYPES_ADDRESS = "api/product/productInfo/type";

	public static String ADD_GOODS_ADDRESS = "api/product/productInfo/add";

	public static String UPLOAD_PICTURE_ADDRESS = "api/product/productInfo/upload";

	public static String CHANGE_PRODUCT_INFO_ADDRESS = "api/product/productInfo/edit";

	public static String GENERATE_BARCODE_ADDRESS = "api/product/productInfo/genBarcode";
	/**
	 * 用户类
	 */
	public static String RESET_PASSWORD_ADDRESS = "system/user/profile/resetPwd";

	public static String CHANGE_USER_INFO_ADDRESS = "system/user/profile/update";

	public static String UPLOAD_AVATAR_ADDRESS = "system/user/profile/uploadAvatar";
	/**
	 * 目前又不需要补充前缀了，暂时保留，以免之后又要加前缀
	 */
	public static String AVATAR_ADDRESS = "";

	public static String SHOW_USER_INFO_ADDRESS = "system/user/profile/info";

	public static String GET_MERCHANT_INFO_ADDRESS = "api/merchantInfo/detail";

	public static String CHANGE_MERCHANT_INFO_ADDRESS = "api/merchantInfo/edit";

	public static String GET_TERMINAL_INFO_ADDRESS = "api/terminalInfo/detailBySn/";
	/**
	 * 订单类
	 */
	public static String CANCEL_ORDER_ADDRESS = "api/order/closeOrder/";

	public static String PLACE_ORDER_ADDRESS = "api/cashier/pay";

	public static String PLACE_ORDER_ONLY_ADDRESS = "api/cashier/order";

	public static String PLACE_ORDER_CONFIRM_ADDRESS = "api/cashier/payForCashier";

	public static String ORDER_CALLBACK_ADDRESS = "api/cashier/doPay";

	public static String PAYMENT_ONLY_ADDRESS = "api/cashier/payForCashier";

	public static String PLACE_ORDER_ONLY_CASHIER_ADDRESS = "api/cashier/order";

	public static String QUERY_ORDER_ADDRESS = "api/cashier/queryStatus/";
	/**
	 * 会员类
	 */
	public static String CHECK_MEMBER_REGISTER_STATE_ADDRESS = "api/memberInfo/getMemberByIdentifier/";

	public static String GET_REGISTER_MEMBER_ADDRESS = "api/memberInfo/getCustomerRegister";

	public static String CHANGE_MEMBER_INFO = "api/memberInfo/edit";

	public static String ADD_MEMBER = "api/memberInfo/add";

	public static String GET_MEMBER_STATICS = "api/memberInfo/statistic";

	public static String QUERY_MEMBER_LIST = "api/memberInfo/list";

	public static String QUERY_MEMBER_INFO_ABSTRACT = "api/memberInfo/detailByIdentity";

	public static String GET_MEMBER_BY_FACE_PIC_ADDRESS = "api/memberInfo/face/compare";

	public static String UPLOAD_MEMBER_FACE_PIC_ADDRESS = "api/memberInfo/face/get";

	public static String QUERY_MEMBER_INFO_EXACT = "api/memberInfo/detailByPreciseInfo/";

	public static String QUERY_MEMBER_INFO_BY_ID = "api/memberInfo/detail/";

	public static String GENERATE_MEMBER_CARD_NO = "api/memberInfo/getRandomCaroNo";

	public static String GET_MEMBER_LEVEL_LIST = "api/memberLevel/list";

	public static String GET_MEMBER_ABLE_USE_COUPON = "api/coupon/getAbleToUseCoupon";

	public static String GET_MEMBER_COUPONS = "api/coupon/getMemberCoupons";

	public static String GET_ORDER_DETAIL_INFO = "api/order/detail/";

	public static String GET_ORDER_LIST = "api/order/list";

	public static String GET_ORDER_CLOSE_ORDER = "api/order/closeOrder";

	public static String REFUND_BY_ORDER = "api/cashier/refundByOrder";

	public static String REFUSE_REFUND = "api/order/refuseRefund/";

	public static String CONFIRM_REFUND = "api/order/confirmRefund/";

	public static String FINISH_REFUND = "api/order/finishRefund/";

	public static String ORDER_RECEIVE = "api/order/receive/";

	public static String ORDER_SEND = "api/order/send";

	public static String MEMBER_POINT_CARD_OPEN = "api/storeCard/merchantOpenStore";
	/**
	 * 店铺会员储值充值规则
	 */
	public static String MEMBER_POINT_CARD_RECHARGE_RULE = "api/storeCard/rechargeRule/list/";
	/**
	 * 会员储值支付
	 */
	public static String MEMBER_POINT_CARD_PAY = "api/cashier/store";
	/**
	 * 储值支付二维码获取
	 */
	public static String MEMBER_POINT_CARD_PAY_SHOW_CODE = "api/cashier/store";
	/**
	 * 退货地址
	 */
	public static String APPLY_OFFLINE_REFUND = "api/cashier/refundByOrder";
	public static String MEMBERINFO_DETAIL = "api/memberInfo/detail/";
	/**
	 * 统计
 	 */
	public static String SALE_STATICS = "api/report/baseSaleInfo";
	/**
	 * 返回各个状态订单的数量
	 */
	public static String ORDER_COUNT = "api/order/count";
	/**
	 * APP端今日数据
 	 */
	public static String REPORT_GETTODAYDATA = "api/report/getTodayData";
	/**
	 * 获取周数据
 	 */
	public static String GET_WEEK = "api/report/getWeekDays";
	/**
	 * 促销活动
	 */
	public static String GET_ACTIVITY_INFO_ADDRESS = "api/activityInfo/list/";
	/**
	 * 商户类
	 */
	public static String GET_MERCHANT_POINT_CONFIG = "api/merchant/point/config/detail";
}
