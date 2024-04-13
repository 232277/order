package com.dyc.order.cashier.constant;

/**
 * func:
 * author:丁语成 on 2020/5/18 10:59
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class ActivityInfoConstant {
	public enum ActivityType{
		SPECIAL_PRICE(1),
		DISCOUNT(2),
		PRICE_BREAK_DISCOUNT(3),
		FULL_DISCOUNT(4),
		FULL_SEND(5);

		private int num;
		ActivityType(int num){
			this.num = num;
		}

		public int getNum() {
			return num;
		}
	}
}
