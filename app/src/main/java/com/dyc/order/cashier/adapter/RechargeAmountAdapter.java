package com.dyc.order.cashier.adapter;

import com.blankj.utilcode.util.StringUtils;
import com.dyc.administrator.toollibrary.utils.NumUtils;
import com.dyc.order.cashier.R;
import com.dyc.order.cashier.data.response.RechargeRuleData;
import com.dyc.order.cashier.view.AmountChooseBadgeView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * func:
 * author:丁语成 on 2020/6/23 10:56
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class RechargeAmountAdapter extends BaseQuickAdapter<RechargeRuleData, BaseViewHolder> {
	private OnClickListener listener;
	private int checkedPos = -1;

	public RechargeAmountAdapter(){
		this(R.layout.adapter_recharge_amount_item_layout);
	}

	public RechargeAmountAdapter(@Nullable List<RechargeRuleData> data){
		this(R.layout.adapter_recharge_amount_item_layout, data);
	}

	public RechargeAmountAdapter(int layoutResId, @Nullable List<RechargeRuleData> data) {
		super(layoutResId, data);
	}

	public RechargeAmountAdapter(int layoutResId) {
		super(layoutResId);
	}

	public void setListener(OnClickListener listener) {
		this.listener = listener;
	}

	@Override
	protected void convert(@NotNull BaseViewHolder holder, RechargeRuleData rechargeRuleData) {
		if (rechargeRuleData != null){
			Double faceValue = rechargeRuleData.getFaceValue();
			Double sellPrice = rechargeRuleData.getSellingPrice();
			if (sellPrice != null && faceValue != null){
				AmountChooseBadgeView badgeView = holder.getView(R.id.badge);
				badgeView.setText(NumUtils.remain2NumWithoutZero(faceValue)
				+ StringUtils.getString(R.string.toollibrary_label_concurrency_sign_chinese));
				if (faceValue > sellPrice){
					badgeView.setSmallText(StringUtils
							.getString(R.string.label_member_store_price_format_str,
									NumUtils.remain2NumWithoutZero(sellPrice)));
				}
				if (listener != null){
					badgeView.setOnClickListener(v -> {
						//之前点的另一个
						int lastPos = checkedPos;
						checkedPos = getItemPosition(rechargeRuleData);
						badgeView.setChecked(true);
						//更新上个选择的view
						if (lastPos != -1){
							getWeakRecyclerView().get().post(()->notifyItemChanged(lastPos));
						}
						listener.onClick(rechargeRuleData);
					});
				}
				if (checkedPos != getItemPosition(rechargeRuleData)){
					badgeView.setChecked(false);
				}
			}
		}
	}

	public interface OnClickListener{
		void onClick(RechargeRuleData rechargeRuleData);
	}
}
