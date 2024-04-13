package com.dyc.order.cashier.data.discount;

import com.dyc.order.cashier.data.response.ActivityInfoData;
import com.dyc.order.cashier.data.response.GoodsInfoList;
import com.dyc.order.cashier.data.local.ShoppingCart;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * func:
 * author:丁语成 on 2020/6/5 16:25
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class SpecialPriceStratage extends BaseStratage {

	@Override
	public StratageRule getRuleFormGoodsActivityInfo(GoodsInfoList.GoodsDetailData.GoodsActivityInfos activityInfo) {
		return null;
	}

	@Override
	public StratageRule getSuitableStrageRule(ShoppingCart.ShoppingCartItem item) {
		return null;
	}

	@Override
	public StratageRule getDiscountRule(ShoppingCart.ShoppingCartItem item, List<ActivityInfoData.ActivityRow> activityRows) {
		return null;
	}

	@Override
	public StratageRule analysizeJsonRule(String rule, int id) {
		return null;
	}

	@Override
	public Map<StratageRule, Double> getSuitableRules() {
		return null;
	}

	@Override
	protected Comparator<StratageRule> getStrageRuleComparator() {
		return null;
	}
}
