package com.dyc.order.cashier.contact;

/**
 * func:
 * author:丁语成 on 2020/6/30 17:51
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public interface ThirdPartPayContact {
	interface ThirdPartPayModel extends BaseMemberCardPayContact.BaseMemberCardPayModel {
	}

	interface ThirdPartPayView extends BaseMemberCardPayContact.BaseMemberCardPayView {
	}

	abstract class ThirdPartPayPresent<M extends ThirdPartPayContact.ThirdPartPayModel, V extends ThirdPartPayContact.ThirdPartPayView> extends BaseMemberCardPayContact.BaseMemberCardPayPresent<M, V> {
	}
}
