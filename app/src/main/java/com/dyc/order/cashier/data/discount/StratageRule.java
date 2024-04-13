package com.dyc.order.cashier.data.discount;

import com.dyc.administrator.toollibrary.utils.MLogger;

import java.util.List;

/**
 * func:
 * author:丁语成 on 2020/4/10 16:33
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public abstract class StratageRule {
	private static MLogger logger = MLogger.getLogger(StratageRule.class);
	public double id;
	public double threshold;
	public double discount;
	private StratageRule higherDiscountRule;

	public double getDiscount(double amount){
		if (amount < threshold){
			//首次调用的时候低于限制就没有优惠
			return getDiscount(amount, 0);
		}else {
			return getDiscount(amount, discount);
		}
	}

	protected double getDiscount(double amount, double lastDiscount){
		if (amount < threshold){
			//不满足此级条件
			return lastDiscount;
		}else if (higherDiscountRule != null){
			//满足此级且有下级
			return higherDiscountRule.getDiscount(amount, discount);
		}else {
			//没有下级但满足此级条件
			return discount;
		}
	}

	public double getMatchedThreshold(double discount){
		if (this.discount == discount){
			return threshold;
		}else if (higherDiscountRule != null){
			return higherDiscountRule.getMatchedThreshold(discount);
		}else {
			return 0;
		}
	}

	public void assembleRule(List rules){
		if (rules == null || rules.isEmpty()){
			return;
		}
		//逐级设定下级
		StratageRule stratageRule = null;
		for (Object ruleObj : rules){
			StratageRule rule = (StratageRule)ruleObj;
			if (stratageRule != null){
				stratageRule.setHigherDiscountRule(rule);
				stratageRule = rule;
			}else {
				id = rule.id;
				threshold = rule.threshold;
				discount = rule.discount;
				stratageRule = this;
			}
		}
	}

	public void setHigherDiscountRule(StratageRule higherDiscountRule) {
		this.higherDiscountRule = higherDiscountRule;
	}

	public StratageRule getHigherDiscountRule() {
		return higherDiscountRule;
	}

	public String getDescribStr(){
		return super.toString();
	}

	public double getId() {
		return id;
	}

	public void setId(double id) {
		this.id = id;
	}

	public double getThreshold() {
		return threshold;
	}

	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}
}
