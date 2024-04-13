package com.dyc.order.cashier.contact;

import com.dyc.order.cashier.data.fields.MemberStoreDTOFields;

/**
 * func:
 * author:丁语成 on 2020/6/28 10:30
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public interface RechargeAndPaymentContact {
	interface RechargeAndPaymentModel extends BaseTransactionContact.BaseTransactionModel, MemberRechargeContact.MemberRechargeModel {
	}

	interface RechargeAndPaymentView extends BaseTransactionContact.BaseTransactionView, MemberRechargeContact.MemberRechargeView {
	}

	abstract class RechargeAndPaymentPresent<M extends RechargeAndPaymentModel, V extends RechargeAndPaymentView> extends BaseTransactionContact.BaseTransactionPresent<M, V> {
		protected MemberRechargeContact.MemberRechargePresent<M,V> memberRechargePresent;

		@Override
		public void afterInit() {
			super.afterInit();
			memberRechargePresent = new MemberRechargeContact.MemberRechargePresent<M, V>() {
			};
			memberRechargePresent.registerModel(getModel());
			memberRechargePresent.registerView(getView());
		}

		public void getMemberCanUsePointCard(){
			memberRechargePresent.getMemberCanUsePointCard();
		}

		public void memberStorePay(MemberStoreDTOFields fields){
			memberRechargePresent.memberStorePay(fields);
		}
	}
}
