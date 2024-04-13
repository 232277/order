package com.dyc.order.cashier.constant;

import com.dyc.order.cashier.R;

import java.util.ArrayList;
import java.util.List;

/**
 * func: 订单状态类型等
 * author:丁语成 on 2020/3/4 14:30
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class OrderFieldConstant {

	//发请求时的支付方式 0=现金,1=主扫,3=被扫,5=银行卡,6=刷脸，7储值卡【预留】
	public enum PayTypeInRequest{
		CASH(0),
		SHOW_CODE(1),
		SCAN_CODE(3),
		BANK_CARD(5),
		FACE(6),
		MEMBER_CARD(7);
		private int num;
		PayTypeInRequest(int num){
			this.num = num;
		}

		public int getNum() {
			return num;
		}

		public void setNum(int num) {
			this.num = num;
		}
	}


	//0=现金,1=支付宝,2=微信,3=银行卡,4=刷脸
	public static List<PayType> payTypes = new ArrayList<PayType>(){{
		add(PayType.UNKNOWN);
		add(PayType.CASH);
		add(PayType.ALI_PAY_ACTIVE);
		add(PayType.WECHAT_PAY_ACTIVE);
		add(PayType.ALI_PAY_PASSIVE);
		add(PayType.WECHAT_PAY_PASSIVE);
		add(PayType.BANK_CARD);
		add(PayType.FACE_PAY);
		add(PayType.DEBIT_CARD);
		add(PayType.WX_MINI_PROGRAM);
	}};
	public enum PayType{
		UNKNOWN(-1, R.string.label_enum_unknown_pay_str, R.drawable.fail),
		CASH(0, R.string.label_enum_cash_pay_str, R.drawable.fail),
		ALI_PAY_ACTIVE(1, R.string.label_enum_ali_pay_str, R.drawable.fail),
		WECHAT_PAY_ACTIVE(2, R.string.label_enum_order_source_wechat_str, R.drawable.fail),
		ALI_PAY_PASSIVE(3, R.string.label_enum_ali_pay_str, R.drawable.fail),
		WECHAT_PAY_PASSIVE(4, R.string.label_enum_order_source_wechat_str, R.drawable.fail),
		BANK_CARD(5, R.string.label_enum_bank_card_pay_str, R.drawable.fail),
		FACE_PAY(6, R.string.label_enum_face_pay_str, R.drawable.fail),
		DEBIT_CARD(7, R.string.label_enum_debit_card_pay_str, R.drawable.fail),
		WX_MINI_PROGRAM(8, R.string.label_enum_order_source_wechat_str, R.drawable.fail);
		//改掉了，不确定之后有没有
//		MEMBER_CARD_PAY(5, R.string.label_enum_member_card_pay_str);

		private int num;
		private int strId;
		private int iconId;
		PayType(int num, int strId, int iconId){
			this.num = num;
			this.strId = strId;
			this.iconId = iconId;
		}

		public int getNum() {
			return num;
		}

		public void setNum(int num) {
			this.num = num;
		}

		public int getStrId() {
			return strId;
		}

		public void setStrId(int strId) {
			this.strId = strId;
		}

		public int getIconId() {
			return iconId;
		}

		public void setIconId(int iconId) {
			this.iconId = iconId;
		}
	}

	//0=收银机,1=微信,2=终端
	public static List<OrderSource> orderSources = new ArrayList<OrderSource>(){{
		add(OrderSource.CASHIER);
		add(OrderSource.WECHAT);
		add(OrderSource.POS);
	}};
	public enum OrderSource{
		CASHIER(0, R.string.label_enum_order_source_cashier_str),
		WECHAT(1, R.string.label_enum_order_source_wechat_str),
		POS(2, R.string.label_enum_order_source_pos_str);
		//改掉了，不确定之后有没有
//		APPLET(3, R.string.label_enum_cash_pay_str);

		int num;
		int strId;
		OrderSource(int num, int strId){
			this.num = num;
			this.strId = strId;
		}

		public int getNum() {
			return num;
		}

		public void setNum(int num) {
			this.num = num;
		}

		public int getStrId() {
			return strId;
		}

		public void setStrId(int strId) {
			this.strId = strId;
		}
	}


	//TODO 暂时别用这个，要和后台确认下transflag和transflags的规则，文档上还没写
	//0=未支付,1=已支付,-1支付失败，2交易关闭
	public static List<TransFlag> transFlags = new ArrayList<TransFlag>(){{
		add(TransFlag.NOT_PAY);
		add(TransFlag.TO_DELIVER);
		add(TransFlag.IN_DELIVER);
		add(TransFlag.SELF_FETCH);
		add(TransFlag.APPLY_REFUND);
		add(TransFlag.APPLY_CANCEL);
		add(TransFlag.REFUND_DENIED);
		add(TransFlag.BUYER_CANCEL_REFUND);
		add(TransFlag.ALREADY_REFUND);
		add(TransFlag.CLOSED);
		add(TransFlag.ALREADY_PAY);
	}};
	public enum TransFlag{
		NOT_PAY(0, R.string.label_enum_trans_flag_not_pay),
		TO_DELIVER(10, R.string.label_enum_trans_flag_to_deliver),
		IN_DELIVER(12, R.string.label_enum_trans_flag_in_deliver),
		SELF_FETCH(11, R.string.label_enum_trans_flag_self_fetch),
		APPLY_REFUND(8, R.string.label_enum_trans_flag_apply_refund),
		IN_REFUND(5, R.string.label_enum_trans_flag_in_refund),
		APPLY_CANCEL(13, R.string.label_enum_trans_flag_apply_cancel),
		REFUND_DENIED(6, R.string.label_enum_trans_flag_refund_denied),
		BUYER_CANCEL_REFUND(9, R.string.label_enum_trans_flag_buyer_cancel_refund),
		ALREADY_REFUND(7, R.string.label_enum_trans_flag_already_refund),
		CLOSED(2, R.string.label_enum_trans_flag_close_pay),
		ALREADY_PAY(1, R.string.label_enum_trans_flag_already_pay);


		int num;
		int strId;
		TransFlag(int num, int strId){
			this.num = num;
			this.strId = strId;
		}

		public int getNum() {
			return num;
		}

		public void setNum(int num) {
			this.num = num;
		}

		public int getStrId() {
			return strId;
		}

		public void setStrId(int strId) {
			this.strId = strId;
		}
	}


    public static int getTransFlagName(int transFlag) {
        int state = -1;
        if (transFlag == OrderFieldConstant.TransFlag.NOT_PAY.getNum()) {
            state = TransFlag.NOT_PAY.strId;
        } else if (transFlag == OrderFieldConstant.TransFlag.TO_DELIVER.getNum()) {
            state = OrderFieldConstant.TransFlag.TO_DELIVER.strId;
        } else if (transFlag == OrderFieldConstant.TransFlag.IN_DELIVER.getNum()) {
            state = OrderFieldConstant.TransFlag.IN_DELIVER.strId;
        } else if (transFlag == OrderFieldConstant.TransFlag.SELF_FETCH.getNum()) {
            state = OrderFieldConstant.TransFlag.SELF_FETCH.strId;
        } else if (transFlag == OrderFieldConstant.TransFlag.APPLY_REFUND.getNum()) {
            state = OrderFieldConstant.TransFlag.APPLY_REFUND.strId;
        } else if (transFlag == OrderFieldConstant.TransFlag.IN_REFUND.getNum()) {
            state = OrderFieldConstant.TransFlag.IN_REFUND.strId;
        } else if (transFlag == OrderFieldConstant.TransFlag.APPLY_CANCEL.getNum()) {
            state = OrderFieldConstant.TransFlag.APPLY_CANCEL.strId;
        } else if (transFlag == OrderFieldConstant.TransFlag.REFUND_DENIED.getNum()) {
            state = OrderFieldConstant.TransFlag.REFUND_DENIED.strId;
        } else if (transFlag == OrderFieldConstant.TransFlag.BUYER_CANCEL_REFUND.getNum()) {
            state = OrderFieldConstant.TransFlag.BUYER_CANCEL_REFUND.strId;
        } else if (transFlag == OrderFieldConstant.TransFlag.ALREADY_REFUND.getNum()) {
            state = OrderFieldConstant.TransFlag.ALREADY_REFUND.strId;
        } else if (transFlag == OrderFieldConstant.TransFlag.CLOSED.getNum()) {
            state = OrderFieldConstant.TransFlag.CLOSED.strId;
        } else if (transFlag == OrderFieldConstant.TransFlag.ALREADY_PAY.getNum()) {
            state = TransFlag.ALREADY_PAY.strId;
        }
        return state;
    }

    //0=销售,1=退货,2=直接收款
    public static List<TransType> transTypes = new ArrayList<TransType>() {{
        add(TransType.SALE);
        add(TransType.REFUND);
        add(TransType.DIRECT_PAY);
    }};

    public enum TransType {
        SALE(0, R.string.label_enum_trans_type_sale),
        REFUND(1, R.string.label_enum_trans_type_refund),
        DIRECT_PAY(2, R.string.label_enum_trans_type_direct_pay);

		int num;
		int strId;
		TransType(int num, int strId){
			this.num = num;
			this.strId = strId;
		}

		public int getNum() {
			return num;
		}

		public void setNum(int num) {
			this.num = num;
		}

		public int getStrId() {
			return strId;
		}

		public void setStrId(int strId) {
			this.strId = strId;
		}
	}

	public enum RefundStatus {
		CAN_NOT_REFUND(-1, -1, -1, -1),
		NOT_REFUND_YET(0, -1, -1, -1),
		PART_REFUND(1, R.string.label_enum_refund_status_part_refund, R.drawable.stroke_text_order_list_part_refund, R.color.order_list_refund_stroke_blue),
		ALL_REFUND(2, R.string.label_enum_refund_status_all_refund, R.drawable.stroke_text_order_list_part_refund, R.color.order_list_refund_stroke_blue);

		int num;
		int strId;
		int strokeColorId;
		int textColorId;

		RefundStatus(int num, int strId, int strokeColorId, int textColorId) {
			this.num = num;
			this.strId = strId;
			this.strokeColorId = strokeColorId;
			this.textColorId = textColorId;
		}

		public int getNum() {
			return num;
		}

		public void setNum(int num) {
			this.num = num;
		}

		public int getStrId() {
			return strId;
		}

		public void setStrId(int strId) {
			this.strId = strId;
		}

		public int getStrokeColorId() {
			return strokeColorId;
		}

		public void setStrokeColorId(int strokeColorId) {
			this.strokeColorId = strokeColorId;
		}

		public int getTextColorId() {
			return textColorId;
		}

		public void setTextColorId(int textColorId) {
			this.textColorId = textColorId;
		}
	}


	public static List<PriceType> priceTypes = new ArrayList<PriceType>(){{
		add(PriceType.ORI_PRICE);
		add(PriceType.MEMBER_PRICE);
		add(PriceType.ACTIVITY_PRICE);
		add(PriceType.CHANGED_PRICE);
	}};
	public enum PriceType{
    	ORI_PRICE(0),
		MEMBER_PRICE(1),
		ACTIVITY_PRICE(2),
		CHANGED_PRICE(3);

    	private int num;
    	PriceType(int num){
    		this.num = num;
		}
	}
}
