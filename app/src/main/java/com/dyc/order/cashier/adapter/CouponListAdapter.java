package com.dyc.order.cashier.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dyc.administrator.toollibrary.utils.TimeFormatUtil;
import com.dyc.order.cashier.R;
import com.dyc.order.cashier.base.MyApplication;
import com.dyc.order.cashier.data.response.CouponListData;
import com.dyc.administrator.toollibrary.utils.MLogger;
import com.dyc.administrator.toollibrary.utils.NumUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static com.blankj.utilcode.util.StringUtils.getString;

/**
 * func:
 * author:丁语成 on 2020/3/27 8:57
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class CouponListAdapter extends BaseQuickAdapter<CouponListData.CouponData, CouponListAdapter.ViewHolder> {
	private MLogger logger = MLogger.getLogger(this.getClass());
	private int checkedPos = -1;
	private ArrayList<Integer> detailPos = new ArrayList<>();
	private boolean hideCheckBox = false;
	private OnCheckedListener listener;
	private List<CouponListData.CouponData> allData;
	private int rotateDegree = 0;

	public CouponListAdapter(int layoutResId, @Nullable List<CouponListData.CouponData> data) {
		super(layoutResId, data);
		allData = new ArrayList<>(data);
		sort(allData);
	}

	public CouponListAdapter(int layoutResId, @Nullable List<CouponListData.CouponData> data, boolean useAll){
		super(layoutResId, null);
		if (data != null && useAll){
			sort(data);
			replaceData(data);
		}else {
			setNewData(data);
		}
	}

	public void setHideCheckBox(boolean hideCheckBox) {
		this.hideCheckBox = hideCheckBox;
		notifyDataSetChanged();
	}

	public int getCheckedPos() {
		return checkedPos;
	}

	public void setCheckedPos(int checkedPos) {
		this.checkedPos = checkedPos;
		notifyDataSetChanged();
	}

	public void setCheckedCoupon(String couponCode){
		logger.info("已选择的优惠券码:" + couponCode);
		List<CouponListData.CouponData> dataList = getData();
		for (int i = 0; i < dataList.size(); ++i){
			if (couponCode.equals(dataList.get(i).couponCode)){
				setCheckedPos(i);
				notifyItemChanged(i);
				break;
			}
		}
	}

	public void setNewData(List<CouponListData.CouponData> datas){
		if (datas != null){
			allData = new ArrayList<>(datas);
			sort(allData);
			replaceData(allData.size() > 20
					? new ArrayList<>(allData.subList(0, 20))
					:  new ArrayList<>(allData));
		}
	}

	private void sort(List<CouponListData.CouponData> allData){
		if (allData != null && !allData.isEmpty()){
			allData.sort((o1, o2) -> {
				int res = 0;
				if (o1 == o2){
					return 0;
				}else if (o1 == null){
					res = -1;
				}else if (o2 == null){
					res = 1;
				}else if (!o1.ableToUse && !o2.ableToUse){
					if (o1.couponVO == null || o2.couponVO == null){
						return 0;
					}
					//均不可用
					if (o1.couponVO.discount > o2.couponVO.discount){
						return 1;
					}else if (o1.couponVO.discount < o2.couponVO.discount){
						return -1;
					}else {
						return 0;
					}
				}else if (!o1.ableToUse){
					return -1;
				}else if (!o2.ableToUse){
					return 1;
				}else {
					//均可用
					if (o1.couponVO.discount > o2.couponVO.discount){
						return 1;
					}else if (o1.couponVO.discount < o2.couponVO.discount){
						return -1;
					}else {
						return 0;
					}
				}
				return res;
			});
			notifyDataSetChanged();
		}
	}

	public boolean loadMore(){
		if (getItemCount() < allData.size()){
			int size = getDefItemCount();
			int endPos = allData.size();
			if (size + 20 < allData.size()){
				endPos = size + 20;
			}
			addData(allData.subList(size, endPos));
			return true;
		}else {
			return false;
		}
	}

	public void setListener(OnCheckedListener listener) {
		this.listener = listener;
	}

	public CouponListData.CouponData getEnableCouponInfo(){
		if (checkedPos != -1){
			return getItem(checkedPos);
		}
		return null;
	}

	@Override
	protected void convert(@NotNull CouponListAdapter.ViewHolder holder, CouponListData.CouponData couponData) {
		if (couponData != null && couponData.couponVO != null){
			CouponListData.CouponData.CouponDetail detail = couponData.couponVO;
			holder.setText(R.id.discount, NumUtils.remain2NumWithoutZero(detail.discount));
			holder.setText(R.id.priceCondition,
					getString(R.string.label_member_dialog_coupon_price_condition_format,
							NumUtils.remain2NumWithoutZero(detail.threshold),
							NumUtils.remain2NumWithoutZero(detail.discount)));
			setChecked(holder, couponData);
			//详情显示
			LinearLayout thisDetailLayout = holder.findView(R.id.detailLayout);
			if (thisDetailLayout != null) {
				thisDetailLayout.setVisibility(detailPos.contains(holder.getLayoutPosition())
						? View.VISIBLE : View.GONE);
			}
			ImageView arrow = holder.findView(R.id.arrow);
			if (arrow != null) {
				arrow.setOnClickListener(v -> {
					arrow.setPivotX(arrow.getWidth()/2);
					arrow.setPivotY(arrow.getHeight()/2);//支点在图片中心
					LinearLayout detailLayout = holder.findView(R.id.detailLayout);
					TextView detailText = holder.findView(R.id.detailText);
					if (detailLayout != null && detailText != null) {
						if (couponData.ableToUse){
							detailLayout.setVisibility(detailLayout.getVisibility() == View.GONE
									? View.VISIBLE : View.GONE);
						}
						detailText.setVisibility(detailText.getVisibility() == View.GONE
								? View.VISIBLE : View.GONE);
						if (detailLayout.getVisibility() == View.GONE){
							detailPos.remove((Object)holder.getLayoutPosition());
						}else {
							detailPos.add(holder.getLayoutPosition());
						}
						rotateDegree = rotateDegree == 0 ? 180 : 0;
						arrow.setRotation(rotateDegree);
					}
				});
			}
			String dateStr;
			if (couponData.invalidTime != null){
				dateStr = TimeFormatUtil.toFormatDate(couponData.effectiveTime, "MM/dd")
						+ "~" + TimeFormatUtil.toFormatDate(couponData.invalidTime, "MM/dd");
			}else {
				dateStr = TimeFormatUtil.toFormatDate(couponData.effectiveTime, "MM/dd")
						+ "~" + getString(R.string.label_coupon_activity_no_invalid_time);
			}
			if (dateStr != null){
				holder.setText(R.id.expireTime, dateStr);
			}
			holder.setText(R.id.detailText,getString(R.string.dialog_coupon_explain_first_line,
					NumUtils.remain2NumWithoutZero(detail.threshold),
					NumUtils.remain2NumWithoutZero(detail.discount)) + "\n"
					+ getString(R.string.dialog_coupon_explain_second_line) + "\n"
					+ getString(R.string.dialog_coupon_explain_third_line));
			setEnable(holder, couponData, couponData.ableToUse);
		}
	}

	private void setChecked(ViewHolder holder, CouponListData.CouponData couponData){
		TextView checked = holder.getView(R.id.checked);
		int itemPos = getItemPosition(couponData);
		if (hideCheckBox){
			checked.setVisibility(View.GONE);
		}else {
			//需要选择按钮
			checked.setVisibility(View.VISIBLE);
			if (couponData.ableToUse){
				//可用优惠券
				checked.setOnClickListener(v -> {
					//改变当前
					checked.setBackgroundResource(R.drawable.stroke_coupon_mvp_useful_coupon_already_choose);
					checked.setTextColor(MyApplication.getContext().getResources().getColor(R.color.coupon_chosen_btn_color, null));
					checked.setText(R.string.label_coupon_activity_chosen);
					//计算位置
					int itemPosNow = getItemPosition(couponData);
					if (checkedPos != itemPosNow){
						//之前点的另一个
						int lastPos = checkedPos;
						//更新上个选择的view
						if (lastPos != -1){
							getWeakRecyclerView().get().post(()->notifyItemChanged(lastPos));
						}
						if (listener != null){
							listener.onChecked(couponData);
						}
					}
					checkedPos = itemPosNow;
				});
			}else {
				//不可用优惠券
				checked.setOnClickListener(null);
			}

			if (checkedPos == itemPos){
				checked.setBackgroundResource(R.drawable.stroke_coupon_mvp_useful_coupon_already_choose);
				checked.setTextColor(MyApplication.getContext().getResources().getColor(R.color.coupon_chosen_btn_color, null));
				checked.setText(R.string.label_coupon_activity_chosen);
			}else if (!couponData.ableToUse){
				checked.setBackgroundResource(R.drawable.stroke_coupon_mvp_useless_coupon_btn);
				checked.setTextColor(MyApplication.getContext().getResources().getColor(R.color.normal_item_text_color_hint, null));
				checked.setText(R.string.label_coupon_activity_choose);
			}else {
				checked.setBackgroundResource(R.drawable.circle_corner_coupon_mvp_useful_coupon_can_choose);
				checked.setTextColor(MyApplication.getContext().getResources().getColor(R.color.WHITE, null));
				checked.setText(R.string.label_coupon_activity_choose);
			}
		}
	}

	private void setEnable(BaseViewHolder holder, CouponListData.CouponData data, boolean enable){
		if (enable){
			logger.info("setBackgroundColor");
			holder.setImageResource(R.id.background, R.mipmap.bg_coupon);
			holder.setTextColor(R.id.rangeCondition, R.color.normal_item_text_color);
			holder.setTextColor(R.id.expireTime, R.color.normal_item_text_color);
		}else {
			holder.setImageResource(R.id.background, R.mipmap.bg_coupon_unavailable);
			holder.setTextColor(R.id.rangeCondition, R.color.coupon_useless_gray);
			holder.setTextColor(R.id.expireTime, R.color.coupon_useless_gray);
			holder.setGone(R.id.detailLayout, false);
		}
		holder.setGone(R.id.disReason, enable);
		holder.setGone(R.id.divideLine, enable);
		holder.setText(R.id.disReason, data.disableReason);
	}

	public interface OnCheckedListener{
		void onChecked(CouponListData.CouponData data);
	}

	public static class ViewHolder extends BaseViewHolder{

		public ViewHolder(@NotNull View view) {
			super(view);
		}
	}
}
