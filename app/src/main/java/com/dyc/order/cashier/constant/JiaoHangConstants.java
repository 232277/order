package com.dyc.order.cashier.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * func:
 * author:丁语成 on 2020/4/16 9:01
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class JiaoHangConstants {
	public static final String TRANS_CODE = "trans_code";
	public static final String TRANS_AMT = "trans_amt";
	public static final String CALLER_ID = "caller_id";
	public static final String CALLER_SECRET = "caller_secret";
	public static final String CONTROL_INFO = "control_info";
	public static final String RESP_CODE = "resp_code";
	public static final String RESP_MSG = "resp_msg";
	public static final String SYS_ORDER_NO = "sys_order_no";
	public static final String PLAT_TRANS_NO = "plat_trans_no";
	public static final String ORDER_NO = "order_no";
	public static final String SCAN_CHANNEL = "scan_channel";
	public static final String TRANS_TIME = "trans_time";
	public static final String TRANS_TIME2 = "trans_time2";
	public static final String BATCH_NO = "batch_no";

	public static final String JIAOHANG_APP_NAME = "com.centerm.epos.bocomshanghai";
	public static final String JIAOHANG_CLASS_NAME = "com.centerm.component.pay.BocomPayEntryActivity";

	public static final String JIAOHANG_WX_SHOW_CODE = "T00010";
	public static final String JIAOHANG_ALI_SHOW_CODE = "T00011";
	public static final String JIAOHANG_SCAN_CODE = "T00009";
	public static final String JIAOHANG_FACE_PAY = "T00015";

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
		add(PayType.CHANNEL_FACE_PAY);
	}};
	public enum PayType{
		CHANNEL_ALIPAY_SCAN_CODE(OrderFieldConstant.PayTypeInRequest.SCAN_CODE.getNum(),
				"1", 3),
		CHANNEL_ALIPAY_SHOW_CODE(OrderFieldConstant.PayTypeInRequest.SHOW_CODE.getNum(),
				"1", 1),
		CHANNEL_WECHAT_SCAN_CODE(OrderFieldConstant.PayTypeInRequest.SCAN_CODE.getNum(),
				"2", 2),
		CHANNEL_WECHAT_SHOW_CODE(OrderFieldConstant.PayTypeInRequest.SHOW_CODE.getNum(),
				"2", 4),
		CHANNEL_UNIONCODE_SCAN_CODE(OrderFieldConstant.PayTypeInRequest.SCAN_CODE.getNum(),
				"3", 10),
		CHANNEL_UNIONCODE_SHOW_CODE(OrderFieldConstant.PayTypeInRequest.SHOW_CODE.getNum(),
				"3", 10),
		CHANNEL_FACE_PAY(OrderFieldConstant.PayTypeInRequest.FACE.getNum(),
				"4", 6);
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
