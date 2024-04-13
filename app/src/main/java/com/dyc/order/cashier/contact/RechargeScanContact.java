package com.dyc.order.cashier.contact;

import com.dyc.order.cashier.data.fields.MemberStoreDTOFields;

/**
 * func:
 * author:丁语成 on 2020/6/29 9:55
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public interface RechargeScanContact {
	interface RechargeAndScanModel extends BaseTransactionContact.BaseTransactionModel, MemberRechargeContact.MemberRechargeModel, MemberContact.MemberModel {
	}

	interface RechargeAndScanView extends BaseTransactionContact.BaseTransactionView, MemberRechargeContact.MemberRechargeView, MemberContact.MemberView {
	}

	abstract class RechargeAndScanPresent<M extends RechargeAndScanModel, V extends RechargeAndScanView> extends BaseTransactionContact.BaseTransactionPresent<M, V> {
		private MemberRechargeContact.MemberRechargePresent<M,V> rechargePresent;
		private MemberContact.MemberPresent<M,V> memberPresent;

		@Override
		public void afterInit() {
			super.afterInit();
			rechargePresent = new MemberRechargeContact.MemberRechargePresent<M, V>() {
			};
			rechargePresent.registerModel(getModel());
			rechargePresent.registerView(getView());

			memberPresent = new MemberContact.MemberPresent<M, V>() {
			};
			memberPresent.registerModel(getModel());
			memberPresent.registerView(getView());
		}

		public void memberStorePay(MemberStoreDTOFields fields){
			rechargePresent.memberStorePay(fields);
		}

		public void getMemberInfoById(int id){
			memberPresent.getMemberById(id);
		}
	}
}
