package com.dyc.order.cashier.contact;

/**
 * func: 收款
 * author:丁语成 on 2020/2/24 17:39
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public interface PaymentActivityContact {
	interface PaymentActivityModel extends RechargeAndPaymentContact.RechargeAndPaymentModel {
	}

	interface PaymentActivityView extends RechargeAndPaymentContact.RechargeAndPaymentView {
	}

	abstract class PaymentActivityPresent<M extends PaymentActivityModel, V extends PaymentActivityView> extends RechargeAndPaymentContact.RechargeAndPaymentPresent<M,V> {
	}
}
