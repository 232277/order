package com.dyc.order.cashier.data.discount;

import com.dyc.order.cashier.data.local.ShoppingCart;

import java.util.List;
import java.util.Map;

/**
 * func:
 * author:丁语成 on 2020/4/10 14:58
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public interface Stratage {
	double excute(List<ShoppingCart.ShoppingCartItem> items);
	StratageRule getSuitableStrageRule(ShoppingCart.ShoppingCartItem item);
	Map<StratageRule, Double> getSuitableRules();
}
