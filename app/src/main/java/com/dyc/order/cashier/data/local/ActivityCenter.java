package com.dyc.order.cashier.data.local;

import com.dyc.order.cashier.data.discount.DiscountStratageContext;
import com.dyc.order.cashier.data.discount.PriceBreakDiscountStratage;
import com.dyc.order.cashier.data.discount.StratageRule;
import com.dyc.order.cashier.data.response.ActivityInfoData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * func:
 * author:丁语成 on 2020/4/10 10:47
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class ActivityCenter {
	public static ActivityInfoData activityInfoData;
	public static List<ActivityInfoData.ActivityRow> activityInfos = new ArrayList<>();

	public static StratageRule getSuitableStrageRule(ShoppingCart.ShoppingCartItem item){
		if (activityInfoData != null){
			return new DiscountStratageContext(
					new PriceBreakDiscountStratage(activityInfos)).getSuitableStrageRule(item);
		}else {
			return null;
		}
	}

	public static DiscountStratageContext getPriceBreakDiscountContext(@NotNull List<ShoppingCart.ShoppingCartItem> item){
		if (activityInfoData != null){
			return new DiscountStratageContext(new PriceBreakDiscountStratage(activityInfos));
		}else {
			return getPriceBreakContextByGoodsOwnActivityInfo(item);
		}
	}

	public static double getPriceBreakDiscount(@NotNull List<ShoppingCart.ShoppingCartItem> item){
		if (activityInfoData != null){
			return new DiscountStratageContext(new PriceBreakDiscountStratage(activityInfos)).excute(item);
		}else {
			return getPriceBreakByGoodsOwnActivityInfo(item);
		}
	}

	public static DiscountStratageContext getPriceBreakContextByGoodsOwnActivityInfo(@NotNull List<ShoppingCart.ShoppingCartItem> item){
		if (item.isEmpty()){
			return null;
		}else {
			return new DiscountStratageContext(new PriceBreakDiscountStratage(activityInfos));
		}
	}

	public static double getPriceBreakByGoodsOwnActivityInfo(@NotNull List<ShoppingCart.ShoppingCartItem> item){
		if (item.isEmpty()){
			return 0;
		}else {
			return new DiscountStratageContext(new PriceBreakDiscountStratage(activityInfos)).excute(item);
		}
	}

	public static ActivityInfoData getActivityInfoData() {
		return activityInfoData;
	}

	public static void setActivityInfoData(ActivityInfoData activityInfoData) {
		ActivityCenter.activityInfoData = activityInfoData;
		if (activityInfoData != null){
			activityInfos = new ArrayList<>(Arrays.asList(activityInfoData.rows));
		}
	}

	public static List<ActivityInfoData.ActivityRow> getActivityInfos() {
		return activityInfos;
	}
}
