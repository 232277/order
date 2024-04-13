package com.dyc.order.cashier.adapter;

import android.graphics.Paint;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dyc.order.cashier.R;
import com.dyc.order.cashier.base.MyApplication;
import com.dyc.order.cashier.data.discount.StratageRule;
import com.dyc.order.cashier.data.local.ActivityCenter;
import com.dyc.order.cashier.data.local.PictureCache;
import com.dyc.order.cashier.data.local.ShoppingCart;
import com.dyc.order.cashier.data.response.GoodsInfoList;
import com.dyc.administrator.toollibrary.utils.DipUtil;
import com.dyc.administrator.toollibrary.utils.MLogger;
import com.dyc.administrator.toollibrary.utils.NumUtils;
import com.dyc.administrator.toollibrary.view.QuantityView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.blankj.utilcode.util.StringUtils.getString;

/**
 * func:
 * author:丁语成 on 2020/3/19 16:59
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class ScanGoodsListAdapter extends BaseQuickAdapter<ShoppingCart.ShoppingCartItem, ScanGoodsListAdapter.ViewHolder> {
	private MLogger logger = MLogger.getLogger(this.getClass());

	public ScanGoodsListAdapter(@Nullable List<ShoppingCart.ShoppingCartItem> data) {
		super(R.layout.adapter_scan_goods_list_adapter_item_layout, data);
	}

	public void updateItem(ShoppingCart.ShoppingCartItem item){
		if (item != null && item.getData() != null && item.getData().getBarcode()!= null){
			String itemBarCode = item.getData().getBarcode();
			boolean notFound = true;
			for (int i = 0; i < getData().size(); ++i){
				if (getItem(i) != null
						&& getItem(i).getData() != null
						&& itemBarCode.equals(getItem(i).getData().getBarcode())){
					notifyItemChanged(i);
					notFound = false;
				}
			}
			if (notFound){
				addData(item);
			}
		}
	}

	@Override
	protected void convert(@NotNull ViewHolder holder, ShoppingCart.ShoppingCartItem item) {
		GoodsInfoList.GoodsDetailData data = item.getData();
		if (data != null){
			ShoppingCart.ShoppingCartItem actualItem = ShoppingCart.getItem(data);
			if (actualItem != null){
				holder.setText(R.id.goodsName, data.getName());

				String picPath = null;
				if (data.getPictures() != null && !TextUtils.isEmpty(data.getPictures()[0])){
					picPath = data.getPictures()[0];
				}else if (!TextUtils.isEmpty(data.getPic())){
					picPath = data.getPic();
				}
				if (!TextUtils.isEmpty(picPath)){
					//有图且和当前不同
					if (!picPath.equals(holder.iconUrl)){
						holder.setIconUrl(picPath);
						ImageView img = (ImageView)holder.findView(R.id.goodsIcon);
						img.setImageResource(R.drawable.pic_no_picture);
						PictureCache.setBitMapForImageView(picPath, img, 85);
					}
				}else {
					//没有图
					holder.setIconUrl("");
					holder.setImageResource(R.id.goodsIcon, R.drawable.pic_no_picture);
				}

				//价格
				String concurrency = getString(R.string.toollibrary_label_concurrency_sign);
				TextView priceLabel = holder.findView(R.id.priceLabel);
				boolean hasSpecialPrice = actualItem.getPriceType() != ShoppingCart.ShoppingCartItem.NONE;
				if (hasSpecialPrice && actualItem.getActualPrice() < data.getPrice()){
					//价格标签
					if (actualItem.getPriceType() == ShoppingCart.ShoppingCartItem.MEMBER){
						priceLabel.setText(R.string.label_scan_goods_member_price_label);
					}else {
						priceLabel.setText(R.string.label_scan_goods_activity_price_label);
					}
					priceLabel.setVisibility(hasSpecialPrice ? View.VISIBLE : View.INVISIBLE);
				}
				//优惠价
				holder.setText(R.id.discountPrice,
						concurrency + NumUtils.remain2NumWithoutZero(actualItem.getActualPrice()));
				setDisountPrice(holder,
						concurrency + NumUtils.remain2NumWithoutZero(actualItem.getActualPrice())
						, actualItem.getActualPrice());
				//原价
				setStrike(holder.findView(R.id.oriPrice), actualItem.getActualPrice() < data.getPrice());
				holder.setText(R.id.goodsName, data.getName())
						.setText(R.id.oriPrice,
								concurrency + NumUtils.remain2NumWithoutZero(data.getPrice()));
				//活动信息
				StratageRule rule = ActivityCenter.getSuitableStrageRule(actualItem);
				if (rule != null){
					holder.setText(R.id.activityLabel, rule.getDescribStr());
					logger.info("activity rule not null");
				}else {
					logger.info("activity rule null");
				}
				holder.setVisible(R.id.activityLabel, rule != null);
				//加减view
				QuantityView quantityView = holder.findView(R.id.quantity);
				quantityView.initQuantity(actualItem.getNum());
				quantityView.setOnAmountChangeListener((view, amount, lastChange) -> {
					ShoppingCart.addToShoppingCart(new ShoppingCart.ShoppingCartItem(item.getData(), lastChange));
					if (amount <= 0){
						remove(item);
					}
				});
				//删除
				holder.findView(R.id.delete).setOnClickListener(v -> {
					ShoppingCart.addToShoppingCart(new ShoppingCart.ShoppingCartItem(actualItem.getData(), -actualItem.getNum()));
					remove(item);
				});
			}
		}else {
			logger.info("data null");
		}
	}

	private void setDisountPrice(ViewHolder holder, String string, double price){
		int intLen = NumUtils.getIntegerPart(price).length();
		int pointLen = NumUtils.getPointPart(price).length();
		SpannableString spannableString = new SpannableString(string);
		spannableString.setSpan(new AbsoluteSizeSpan(DipUtil.sp2px(MyApplication.getContext(), 13f)),
				0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		spannableString.setSpan(new AbsoluteSizeSpan(DipUtil.sp2px(MyApplication.getContext(), 17f)),
				1, 1 + intLen, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		if (pointLen > 0){
			spannableString.setSpan(new AbsoluteSizeSpan(DipUtil.sp2px(MyApplication.getContext(), 13f)),
					1 + intLen, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		}
		((TextView)holder.getView(R.id.discountPrice)).setText(spannableString);
	}

	private void setStrike(TextView textView, boolean strike){
		textView.getPaint().setFlags(0);
		textView.getPaint().setAntiAlias(true);//抗锯齿
		if (strike){
			textView.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
			textView.setVisibility(View.VISIBLE);
		}else {
			textView.setPaintFlags(Paint.ANTI_ALIAS_FLAG);
			textView.setVisibility(View.GONE);
		}
	}

	public class ViewHolder extends BaseViewHolder{
		private String iconUrl;

		public ViewHolder(@NotNull View view) {
			super(view);
		}

		public String getIconUrl() {
			return iconUrl;
		}

		public void setIconUrl(String iconUrl) {
			this.iconUrl = iconUrl;
		}
	}
}
