package com.dyc.order.cashier.data.discount;

import com.dyc.order.cashier.data.local.ShoppingCart;

import java.util.List;
import java.util.Map;

/**
 * func:
 * author:丁语成 on 2020/4/10 14:43
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class DiscountStratageContext {
	private Stratage stratage;
	private Map<StratageRule, Double> suitableRules;

	public DiscountStratageContext(Stratage stratage) {
		this.stratage = stratage;
	}

	public double excute(List<ShoppingCart.ShoppingCartItem> items){
		suitableRules = null;
		if (stratage != null && items != null && !items.isEmpty()){
			double discount = stratage.excute(items);
			if (suitableRules == null){
				suitableRules = stratage.getSuitableRules();
			}else if (stratage.getSuitableRules() != null){
				suitableRules.putAll(stratage.getSuitableRules());
			}
			return discount;
		}
		return 0;
	}

	public Map<StratageRule, Double> getSuitableRules() {
		return suitableRules;
	}

	public StratageRule getSuitableStrageRule(ShoppingCart.ShoppingCartItem item){
		return stratage.getSuitableStrageRule(item);
	}

	public Stratage getStratage() {
		return stratage;
	}

	public void setStratage(Stratage stratage) {
		this.stratage = stratage;
	}
}
