package com.dyc.order.cashier.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * func:
 * author:丁语成 on 2020/3/27 14:38
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class JianHangConstants {
	public final static String APP_NAME = "appName";
	public final static String AUTH_INDEX = "authIndex";
	public final static String TRANS_ID = "transId";
	public final static String TRANS_DATA = "transData";
	public final static String AMOUNT = "amt";
	public final static String NEED_PRINT = "isNeedPrintReceipt";
	public final static String COUNTER_NO = "counterNo";
	public final static String ORDER_NO = "orderNo";
	public final static String LS_ORDER_NO = "lsOrderNo";//第三方订单号
	public final static String PROJECT_TAG = "projectTag";
	public static final String RESULT_CODE = "resultCode";
	public static final String RESULT_MSG = "resultMsg";
	public static final String WX_ALI_PAY_RUNION_NO = "wxAliPayUnionNo";
	public static final String PLATFOTM_NO = "platformNo";
	public static final String PAY_CHANNEL = "payChannel";
	public static final String TRANS_CODE = "trans_code";
	public static final String TRANS_AMT = "trans_amt";
	public static final String CALLER_ID = "caller_id";
	public static final String CALLER_SECRET = "caller_secret";
	public static final String CONTROL_INFO = "control_info";
	public static final String RESP_CODE = "resp_code";
	public static final String RESP_MSG = "resp_msg";
	public static final String PLAT_TRANS_NO = "plat_trans_no";
	public static final String ORDER_ID = "order_id";
	public static final String SCAN_CHANNEL = "scan_channel";
	public static final String TERMINAL_ID = "terminalID";
	public static final String BATCH_NO = "batchNo";
	public static final String REF_NO = "refNo";
	public static final String TRANS_DATE = "transDate";
	public static final String TRANS_TIME = "transTime";
	public static final String TRACE_NO = "traceNo";
	public static final String CARD_NO = "cardNo";


	public static final String FACE_PAY = "建行刷脸付";
	public static final String SCAN_CODE_PAY = "聚合扫码支付";
	public static final String SHOW_CODE_PAY = "二维码收款";

	public static final String JIANHANG_FACE_APP_NAME = "com.ccb.smartpos.bankpay";
	public static final String JIANHANG_FACE_CLASS_NAME = "com.ccb.smartpos.bankpay.ui.MainActivity";

	public static final String JIANHANG_APP_NAME = "com.ccb.smartpos.facepay";
	public static final String JIANHANG_CLASS_NAME = "com.ccb.smartpos.facepay.ui.MainActivity";

	public static int getPayTypeInConfirm(int payTypeInRequest, String channel){
		for (PayType payType : payTypes){
			if (payType.payTypeInRequest == payTypeInRequest && payType.payChannel.equals(channel)){
				return payType.payTypeInConfirm;
			}
		}
		return OrderFieldConstant.PayType.UNKNOWN.getNum();
	}

	public static List<PayType> payTypes = new ArrayList<PayType>(){{
		add(PayType.CHANNEL_ALIPAY_SCAN_CODE);
		add(PayType.CHANNEL_ALIPAY_SHOW_CODE);
		add(PayType.CHANNEL_WECHAT_SCAN_CODE);
		add(PayType.CHANNEL_WECHAT_SHOW_CODE);
		add(PayType.CHANNEL_UNIONCODE_SCAN_CODE);
		add(PayType.CHANNEL_UNIONCODE_SHOW_CODE);
		add(PayType.CHANNEL_DRAGON_SCAN_CODE);
		add(PayType.CHANNEL_DRAGON_SHOW_CODE);
		add(PayType.CHANNEL_FACE_PAY);
	}};
	public enum PayType{
		CHANNEL_ALIPAY_SCAN_CODE(OrderFieldConstant.PayTypeInRequest.SCAN_CODE.getNum(),
				"CHANNEL_ALIPAY", 3),
		CHANNEL_ALIPAY_SHOW_CODE(OrderFieldConstant.PayTypeInRequest.SHOW_CODE.getNum(),
				"CHANNEL_ALIPAY", 1),
		CHANNEL_WECHAT_SCAN_CODE(OrderFieldConstant.PayTypeInRequest.SCAN_CODE.getNum(),
				"CHANNEL_WEPAY", 4),
		CHANNEL_WECHAT_SHOW_CODE(OrderFieldConstant.PayTypeInRequest.SHOW_CODE.getNum(),
				"CHANNEL_WEPAY", 2),
		CHANNEL_UNIONCODE_SCAN_CODE(OrderFieldConstant.PayTypeInRequest.SCAN_CODE.getNum(),
				"CHANNEL_UNIONCODEPAY", 10),
		CHANNEL_UNIONCODE_SHOW_CODE(OrderFieldConstant.PayTypeInRequest.SHOW_CODE.getNum(),
				"CHANNEL_UNIONCODEPAY", 10),
		CHANNEL_DRAGON_SCAN_CODE(OrderFieldConstant.PayTypeInRequest.SCAN_CODE.getNum(),
				"CHANNEL_DRAGONPAY", 9),
		CHANNEL_DRAGON_SHOW_CODE(OrderFieldConstant.PayTypeInRequest.SHOW_CODE.getNum(),
				"CHANNEL_DRAGONPAY", 9),
		CHANNEL_FACE_PAY(OrderFieldConstant.PayTypeInRequest.FACE.getNum(),
				"CHANNEL_UNIONPAY", 6);
		int payTypeInRequest;
		String payChannel;
		int payTypeInConfirm;

		PayType(int payTypeInRequest, String payChannel, int payTypeInConfirm) {
			this.payTypeInRequest = payTypeInRequest;
			this.payChannel = payChannel;
			this.payTypeInConfirm = payTypeInConfirm;
		}
	}
}
