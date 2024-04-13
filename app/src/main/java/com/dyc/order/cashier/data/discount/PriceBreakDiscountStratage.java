package com.dyc.order.cashier.data.discount;

import android.text.TextUtils;

import com.dyc.order.cashier.R;
import com.dyc.order.cashier.constant.GoodsActivityConstant;
import com.dyc.order.cashier.data.response.ActivityInfoData;
import com.dyc.order.cashier.data.response.GoodsInfoList;
import com.dyc.order.cashier.data.local.ShoppingCart;
import com.dyc.administrator.toollibrary.utils.NumUtils;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import rxhttp.wrapper.utils.GsonUtil;

import static com.blankj.utilcode.util.StringUtils.getString;

/**
 * func:
 * author:丁语成 on 2020/4/10 15:02
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class PriceBreakDiscountStratage extends BaseStratage {

	public PriceBreakDiscountStratage(){}

	public PriceBreakDiscountStratage(List<ActivityInfoData.ActivityRow> activityRows) {
		super(activityRows);
	}

	@Override
	protected Comparator<StratageRule> getStrageRuleComparator() {
		return (o1, o2) -> {
			if (o1.discount > o2.discount) {
				return 1;
			} else if (o1.discount == o2.discount) {
				return 0;
			} else {
				return -1;
			}
		};
	}

	@Override
	public StratageRule getRuleFormGoodsActivityInfo(GoodsInfoList.GoodsDetailData.GoodsActivityInfos activityInfo) {
		if (activityInfo.getType() == GoodsActivityConstant.ACTIVITY_TYPE_PRICE_BREAK_PRICE){
			return analysizeJsonRule(activityInfo.getRule(), -1);
		}
		return null;
	}

	@Override
	public StratageRule getDiscountRule(ShoppingCart.ShoppingCartItem item, List<ActivityInfoData.ActivityRow> activityRows){
		StratageRule rule = null;
		int type;
		for (ActivityInfoData.ActivityRow row : activityRows){
			if (GoodsActivityConstant.ACTIVITY_TYPE_PRICE_BREAK_PRICE == row.type){
				if (row.filterType == GoodsActivityConstant.FILTER_TYPE_SINGLE_GOODS) {
					//单品筛选
					if (row.activityDetailList != null) {
						//商品表
						ArrayList<ActivityInfoData.ActivityRow.ActivityDetail> details
								= new ArrayList<>(Arrays.asList(row.activityDetailList));
						for (ActivityInfoData.ActivityRow.ActivityDetail detail : details) {
							if (detail.identity.equals(item.getData().getBarcode())) {
								//有满减的商品的满减规则
								rule = generateDiscountRule(row);
							}
						}
					}
				} else if (row.filterType == GoodsActivityConstant.FILTER_TYPE_ONE_TYPE) {
					//品类筛选
					if (row.activityDetailList != null) {
						//商品表
						ArrayList<ActivityInfoData.ActivityRow.ActivityDetail> details
								= new ArrayList<>(Arrays.asList(row.activityDetailList));
						for (ActivityInfoData.ActivityRow.ActivityDetail detail : details) {
							if (detail.identity.equals(item.getData().getTypeName())) {
								//有满减的商品的满减规则
								rule = generateDiscountRule(row);
							}
						}
					}
				} else if (row.filterType == GoodsActivityConstant.FILTER_TYPE_ALL_TYPE) {
					//全品
					if (rule == null) {
						rule = generateDiscountRule(row);
					}
				}
			}
		}
		return rule;
	}

	@Override
	public PriceBreakDiscountRule analysizeJsonRule(String rule, int id){
		if (!TextUtils.isEmpty(rule)){
			List<PriceBreakDiscountRule> priceBreakDiscountRules = GsonUtil.fromJson(rule,
					new TypeToken<List<PriceBreakDiscountRule>>(){}.getType());
			if (priceBreakDiscountRules != null && !priceBreakDiscountRules.isEmpty()){
				priceBreakDiscountRules.forEach(item -> {
					item.id = id;
				});
				PriceBreakDiscountRule res = new PriceBreakDiscountRule();
				res.assembleRule(priceBreakDiscountRules);
				return res;
			}
		}
		return null;
	}

	/**
	 * func:满减规则
	 * author:丁语成 on 2020/4/10 11:45
	 * mail:dingyucheng@centerm.com
	 * tel:18279114677
	 */
	public static class PriceBreakDiscountRule extends StratageRule {

		@Override
		public String getDescribStr() {
			return getString(R.string.label_member_dialog_coupon_price_condition_format,
					NumUtils.remain2NumWithoutZero(threshold),
					NumUtils.remain2NumWithoutZero(discount));
		}
	}
}
