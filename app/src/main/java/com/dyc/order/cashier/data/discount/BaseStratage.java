package com.dyc.order.cashier.data.discount;

import com.dyc.order.cashier.data.response.ActivityInfoData;
import com.dyc.order.cashier.data.response.GoodsInfoList;
import com.dyc.administrator.toollibrary.utils.MLogger;
import com.dyc.order.cashier.data.local.ShoppingCart;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static com.blankj.utilcode.util.StringUtils.getString;

/**
 * func:
 * author:丁语成 on 2020/6/5 17:40
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public abstract class BaseStratage implements Stratage {
	protected MLogger logger = MLogger.getLogger(this.getClass());
	protected List<ActivityInfoData.ActivityRow> activityRows;
	protected Map<StratageRule, Double> suitableRules;

	public BaseStratage(){
	}

	public BaseStratage(List<ActivityInfoData.ActivityRow> activityRows) {
		this.activityRows = activityRows;
	}

	public Map<StratageRule, Double> getSuitableRules() {
		return suitableRules;
	}

	/**
	 * 策略顺序比较器
	 * @return
	 */
	protected abstract Comparator<StratageRule> getStrageRuleComparator();

	@Override
	public double excute(List<ShoppingCart.ShoppingCartItem> items) {
		suitableRules = new TreeMap<>(getStrageRuleComparator());
		Map<StratageRule, Double> calMap = new TreeMap<>(getStrageRuleComparator());
		StratageRule rule;
		for (ShoppingCart.ShoppingCartItem item : items){
			List<ActivityInfoData.ActivityRow> activityRows = this.activityRows;
			rule = null;
			if(activityRows != null && !activityRows.isEmpty()){
				//使用全部活动信息
				rule = getDiscountRule(item, activityRows);
			}else {
				//这个商品没活动信息
				if (item.getData() == null || item.getData().getActivityInfos() == null
						|| item.getData().getActivityInfos().length < 1){
					continue;
				}
				GoodsInfoList.GoodsDetailData.GoodsActivityInfos[] activityInfos = item.getData().getActivityInfos();
				for (GoodsInfoList.GoodsDetailData.GoodsActivityInfos activityInfo : activityInfos){
					rule = getRuleFormGoodsActivityInfo(activityInfo);
					if (rule != null){
						break;
					}
				}
			}
			if (rule != null){
				calMap.merge(rule,item.getNum() * item.getData().getPrice(),
						(oldV, newV) -> oldV + item.getNum() * item.getData().getPrice());
			}
		}

		double totalDiscount = 0;
		for (Map.Entry<StratageRule, Double> entry : calMap.entrySet()){
			double thisDiscount = entry.getKey().getDiscount(entry.getValue());
			if (thisDiscount > 0){
//				logger.info("增加：" + entry.getKey().id + " 优惠：" + entry.getValue());
				suitableRules.put(entry.getKey(), thisDiscount);
			}
			totalDiscount += thisDiscount;
		}
		return totalDiscount;
	}

	/**
	 * 从商品自身活动信息获取规则
	 * @param activityInfo
	 * @return
	 */
	public abstract StratageRule getRuleFormGoodsActivityInfo(GoodsInfoList.GoodsDetailData.GoodsActivityInfos activityInfo);


	/**
	 * 获取合适的优惠规则
	 * @param item
	 * @return
	 */
	@Override
	public StratageRule getSuitableStrageRule(ShoppingCart.ShoppingCartItem item) {
		return getDiscountRule(item, activityRows);
	}

	public abstract StratageRule getDiscountRule(ShoppingCart.ShoppingCartItem item, List<ActivityInfoData.ActivityRow> activityRows);

	public StratageRule generateDiscountRule(ActivityInfoData.ActivityRow activityRow){
		if (activityRow != null){
			return analysizeJsonRule(activityRow.rule, activityRow.id);
		}
		return null;
	}

	public abstract StratageRule analysizeJsonRule(String rule, int id);

	public void setActivityRows(List<ActivityInfoData.ActivityRow> activityRows) {
		this.activityRows = activityRows;
	}

	public List<ActivityInfoData.ActivityRow> getActivityRows() {
		return activityRows;
	}
}
