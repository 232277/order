package com.dyc.order.cashier.adapter;

import android.graphics.Paint;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.dyc.administrator.toollibrary.utils.DipUtil;
import com.dyc.administrator.toollibrary.utils.NumUtils;
import com.dyc.order.cashier.R;
import com.dyc.order.cashier.base.MyApplication;
import com.dyc.order.cashier.data.local.PictureCache;
import com.dyc.order.cashier.data.local.ShoppingCart;
import com.dyc.order.cashier.data.response.GoodsInfoList;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * func:
 * author:丁语成 on 2020/6/9 11:39
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class RecommendGoodsAdapter extends BaseQuickAdapter<ShoppingCart.ShoppingCartItem, RecommendGoodsAdapter.ViewHolder> {

	public RecommendGoodsAdapter(){
		this(R.layout.adapter_recommend_goods_item_layout);
	}

	public RecommendGoodsAdapter(@Nullable List<ShoppingCart.ShoppingCartItem> data){
		this(R.layout.adapter_recommend_goods_item_layout, data);
	}

	public RecommendGoodsAdapter(int layoutResId) {
		super(layoutResId);
	}

	public RecommendGoodsAdapter(int layoutResId, @Nullable List<ShoppingCart.ShoppingCartItem> data) {
		super(layoutResId, data);
	}

	@Override
	protected void convert(@NotNull ViewHolder viewHolder, ShoppingCart.ShoppingCartItem shoppingCartItem) {
		shoppingCartItem.setMember(true);
		GoodsInfoList.GoodsDetailData data = shoppingCartItem.getData();
		ImageView imageView = viewHolder.getView(R.id.img);
		if (data != null){
			String picPath = null;
			if (data.getPictures() != null && !TextUtils.isEmpty(data.getPictures()[0])){
				picPath = data.getPictures()[0];
			}else if (!TextUtils.isEmpty(data.getPic())){
				picPath = data.getPic();
			}
			if (!TextUtils.isEmpty(picPath)){
				//有图且和当前不同
				if (!picPath.equals(viewHolder.imgUrl)){
					PictureCache.setBitMapForImageView(picPath, imageView, 48);
					viewHolder.imgUrl = picPath;
				}
			}else {
				//没有图
				imageView.setImageResource(R.drawable.pic_no_picture);
			}

			String name = data.getName();
			if (TextUtils.isEmpty(name)){
				name = StringUtils.getString(R.string.label_scan_goods_no_name_goods);
			} else if (name.length() > 6){
				name = name.substring(0, 6) + "...";
			}
			String concurrency = StringUtils.getString(R.string.toollibrary_label_concurrency_sign);
			viewHolder.setText(R.id.name, name);
			viewHolder.setVisible(R.id.specialPrice,
					shoppingCartItem.getPriceType() != ShoppingCart.ShoppingCartItem.NONE);
			setDisountPrice(viewHolder,
					concurrency + NumUtils.remain2NumWithoutZero(shoppingCartItem.getActualPrice()),
					shoppingCartItem.getActualPrice());
//			viewHolder.setText(R.id.specialPrice,
//					concurrency + NumUtils.remain2NumWithoutZero(shoppingCartItem.getActualPrice()));
			setStrike(viewHolder.findView(R.id.oriPrice), shoppingCartItem.getActualPrice() < data.getPrice());
			viewHolder.setText(R.id.oriPrice,
					concurrency + NumUtils.remain2NumWithoutZero(data.getPrice()));
			viewHolder.itemView.setOnClickListener(v -> ShoppingCart.addToShoppingCart(new ShoppingCart.ShoppingCartItem(data, 1)));
		}
	}

	private void setDisountPrice(ViewHolder holder, String string, double price){
		int intLen = NumUtils.getIntegerPart(price).length();
		int pointLen = NumUtils.getPointPart(price).length();
		SpannableString spannableString = new SpannableString(string);
		spannableString.setSpan(new AbsoluteSizeSpan(DipUtil.sp2px(MyApplication.getContext(), 13f)),
				0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		spannableString.setSpan(new AbsoluteSizeSpan(DipUtil.sp2px(MyApplication.getContext(), 14f)),
				1, 1 + intLen, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		if (pointLen > 0){
			spannableString.setSpan(new AbsoluteSizeSpan(DipUtil.sp2px(MyApplication.getContext(), 11f)),
					1 + intLen, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		}
		((TextView)holder.getView(R.id.specialPrice)).setText(spannableString);
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
		public String imgUrl;

		public ViewHolder(@NotNull View view) {
			super(view);
		}
	}
}
