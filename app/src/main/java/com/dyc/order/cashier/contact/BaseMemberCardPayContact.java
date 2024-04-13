package com.dyc.order.cashier.contact;

/**
 * func:
 * author:丁语成 on 2020/7/1 9:35
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public interface BaseMemberCardPayContact {
	interface BaseMemberCardPayModel extends RechargeAndPaymentContact.RechargeAndPaymentModel, MemberContact.MemberModel {
	}

	interface BaseMemberCardPayView extends RechargeAndPaymentContact.RechargeAndPaymentView, MemberContact.MemberView {
	}

	abstract class BaseMemberCardPayPresent<M extends BaseMemberCardPayContact.BaseMemberCardPayModel, V extends BaseMemberCardPayContact.BaseMemberCardPayView> extends RechargeAndPaymentContact.RechargeAndPaymentPresent<M, V> {
		private MemberContact.MemberPresent<M,V> memberPresent;

		@Override
		public void afterInit() {
			super.afterInit();
			memberPresent = new MemberContact.MemberPresent<M, V>() {
			};
			memberPresent.registerModel(getModel());
			memberPresent.registerView(getView());
		}

		public void getMemberInfo(int id){
			memberPresent.getMemberById(id);
		}

		public void getMemberInfo(String phone){
			memberPresent.getMember(phone);
		}
	}
}
