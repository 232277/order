package com.dyc.order.cashier.mvp.activity.other;

import android.app.Activity;
import android.graphics.Paint;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dyc.order.cashier.contact.BaseGetGoodsContact;
import com.dyc.order.cashier.data.response.GoodsInfoList;
import com.dyc.administrator.toollibrary.utils.MLogger;
import com.dyc.order.cashier.R;
import com.dyc.order.cashier.mvp.activity.base.BaseGoodsListActivity;
import com.dyc.order.cashier.data.local.DataCache;
import com.dyc.order.cashier.data.local.PictureCache;
import com.dyc.order.cashier.data.local.ShoppingCart;
import com.dyc.order.cashier.data.fields.GetGoodsField;
import com.dyc.administrator.toollibrary.adapter.MyFlexboxLayoutManager;
import com.dyc.administrator.toollibrary.utils.DipUtil;
import com.dyc.administrator.toollibrary.utils.KeyBoardUtils;
import com.dyc.administrator.toollibrary.utils.NumUtils;
import com.dyc.administrator.toollibrary.view.NumberPadView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.JustifyContent;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class SearchGoodsActivity extends BaseGoodsListActivity<
        BaseGetGoodsContact.BaseGetGoodsModel,
		BaseGetGoodsContact.BaseGetGoodsView,
		BaseGetGoodsContact.BaseGetGoodsPresent> implements BaseGetGoodsContact.BaseGetGoodsView {
	private LinearLayout topBarLeft;
	private TextView countDownStr;
	private NumberPadView numberPadView;
	private RecyclerView goodsList;
	private SearchGoodsAdapter adapter;
	private EditText goodsIdentity;
	private GetGoodsField field;

	@Override
	public int getLayoutId() {
		return R.layout.activity_search_goods;
	}

	@Override
	public void onInitView(View view) {
		super.onInitView(view);
		showActionBar(false);
		topBarLeft = findViewById(R.id.topBarLeft);
		countDownStr = findViewById(R.id.countDownStr);
		numberPadView = findViewById(R.id.numPad);
		goodsList = findViewById(R.id.goodsListLayout);
		goodsIdentity = findViewById(R.id.goodsIdentity);
		topBarLeft.setOnClickListener(v->finish());
		initData();
	}

	private void initData(){
		KeyBoardUtils.ShowKeyboard(goodsIdentity);
		goodsIdentity.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
		numberPadView.setOnNumInputListener(new NumberPadView.OnNumberInputListener() {
			@Override
			public void onInput(int num) {
				goodsIdentity.append(""+num);
			}

			@Override
			public void onConfirm() {
				setFieldsAndGet();
			}

			@Override
			public void onDelete() {
				int index = goodsIdentity.getSelectionStart();
				if (index > 0){
					goodsIdentity.getText().delete(index-1, index);
				}
			}

			@Override
			public void onClear() {
				goodsIdentity.setText("");
			}

			@Override
			public void onAction(int num) {

			}
		});
		MyFlexboxLayoutManager flexManager = new MyFlexboxLayoutManager(this);
		flexManager.setFlexDirection(FlexDirection.ROW);
		flexManager.setJustifyContent(JustifyContent.FLEX_START);
		adapter = new SearchGoodsAdapter(R.layout.adapter_search_goods_list_item_layout, this, null);
		adapter.setOnItemClickListener((adapter, view, position) -> {
			GoodsInfoList.GoodsDetailData detailData = (GoodsInfoList.GoodsDetailData)adapter.getItem(position);
			ShoppingCart.addToShoppingCart(new ShoppingCart.ShoppingCartItem(detailData, 1));
			finish();
		});
		goodsList.setLayoutManager(flexManager);
		goodsList.setAdapter(adapter);
		adapter.setEmptyView(R.layout.adapter_search_goods_empty_layout);
	}

	private void setFieldsAndGet(){
		logger.info("setFieldsAndGet");
		if (!TextUtils.isEmpty(goodsIdentity.getText()) && goodsIdentity.getText().length() == 6){
			if (field == null){
				field = new GetGoodsField();
			}
			field.words = goodsIdentity.getText().toString();
			field.merchantId = DataCache.getMerchantInfoData().getId();
			getPresenter().getGoodsList(0, field);
		}else {
			showToast(R.string.label_search_goods_input_goods_code);
		}
	}

	@Override
	public void onGetGoodsListSuccess(GoodsInfoList list) {
		ArrayList<GoodsInfoList.GoodsDetailData> data =  new ArrayList<>(Arrays.asList(list.getRows()));
		for (int i = 0; i < data.size(); ++i){
			GoodsInfoList.GoodsDetailData detailData = data.get(i);
			if (detailData == null || detailData.getStatus() == null || detailData.getStatus() != 0){
				data.remove(detailData);
				--i;
			}
		}

		adapter.setNewData(data);
		if (data.isEmpty()){
			setEmptyView(getString(R.string.dialog_scan_goods_not_found_goods), R.mipmap.img_not_found);
		}else {
			setEmptyView(getString(R.string.label_search_goods_input_goods_code), R.mipmap.img_can_scan);
		}
	}

	@Override
	public void onGetGoodsListFail(Throwable throwable) {
		adapter.setNewData(new ArrayList<>());
		logger.info(throwable.getMessage());
		throwable.printStackTrace();
		setEmptyView(getString(R.string.dialog_scan_goods_not_found_goods), R.mipmap.img_not_found);
	}

	private void setEmptyView(CharSequence str, int img){
		((TextView)adapter.getEmptyLayout().findViewById(R.id.faceGoodsToScanner)).setText(str);
		if (img > 0){
			((ImageView)adapter.getEmptyLayout().findViewById(R.id.img)).setImageResource(img);
		}
	}

	@Override
	protected void onCountDown(long countTime) {
		runOnUiThread(()->countDownStr.setText(getFormatCountDownStr(countTime)));
	}

	@Override
	public void pauseCountDown() {
		super.pauseCountDown();
		changeActionBar(getString(R.string.label_top_bar_search_goods), null);
	}

	@Override
	protected void onCountFinish() {
		super.onCountFinish();
		runOnUiThread(()->adapter.notifyDataSetChanged());
	}

	public static class SearchGoodsAdapter extends BaseQuickAdapter<GoodsInfoList.GoodsDetailData, SearchGoodsAdapter.ViewHolder> {
		private Activity activity;
		private MLogger logger = MLogger.getLogger(SearchGoodsAdapter.class);

		public SearchGoodsAdapter(int layoutResId, Activity activity,  @Nullable List<GoodsInfoList.GoodsDetailData> data) {
			super(layoutResId, data);
			this.activity = activity;
		}

		@Override
		protected void convert(@NotNull SearchGoodsActivity.SearchGoodsAdapter.ViewHolder holder, GoodsInfoList.GoodsDetailData data) {
			ShoppingCart.ShoppingCartItem item = new ShoppingCart.ShoppingCartItem(ShoppingCart.isIsMember(), 1, data);
			String picPath = null;
			if (data.getPictures() != null && !TextUtils.isEmpty(data.getPictures()[0])){
				picPath = data.getPictures()[0];
			}else if (!TextUtils.isEmpty(data.getPic())){
				picPath = data.getPic();
			}
			logger.info("图片地址:" + picPath);
			if (!TextUtils.isEmpty(picPath)){
				PictureCache.setBitMapForImageView(
						picPath, holder.getView(R.id.goodsIcon), 120);
			}

			holder.setText(R.id.goodsName, data.getName());
			TextView oriPrice = holder.getView(R.id.oriPrice);
			if (item.getPriceType() != ShoppingCart.ShoppingCartItem.NONE){
				oriPrice.getPaint().setFlags(0);
				oriPrice.getPaint().setAntiAlias(true);//抗锯齿
				oriPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
				oriPrice.setText(String.format("%s%s",
						activity.getString(R.string.toollibrary_label_concurrency_sign),
						NumUtils.remain2NumWithoutZero(data.getPrice())));
				oriPrice.setVisibility(View.VISIBLE);
			}else {
				oriPrice.setVisibility(View.INVISIBLE);
			}
//			holder.setText(R.id.specialPrice, String.format("%s%s",
//					activity.getString(R.string.toollibrary_label_concurrency_sign),
//					NumUtils.remain2NumWithoutZero(item.getActualPrice())));
			setSpecialPrice(holder, String.format("%s%s",
					activity.getString(R.string.toollibrary_label_concurrency_sign),
					NumUtils.remain2NumWithoutZero(item.getActualPrice())), item.getActualPrice());
		}

		private void setSpecialPrice(SearchGoodsActivity.SearchGoodsAdapter.ViewHolder holder, String string, double price){
			int intLen = NumUtils.getIntegerPart(price).length();
			int pointLen = NumUtils.getPointPart(price).length();
			SpannableString spannableString = new SpannableString(string);
			spannableString.setSpan(new AbsoluteSizeSpan(DipUtil.sp2px(activity, 13f)),
					0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
			spannableString.setSpan(new AbsoluteSizeSpan(DipUtil.sp2px(activity, 17f)),
					1, 1 + intLen, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
			if (pointLen > 0){
				spannableString.setSpan(new AbsoluteSizeSpan(DipUtil.sp2px(activity, 13f)),
						1 + intLen, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
			}
			((TextView)holder.getView(R.id.specialPrice)).setText(spannableString);
		}

		public static class ViewHolder extends BaseViewHolder{
			public ViewHolder(@NotNull View view) {
				super(view);
			}
		}
	}

	@Override
	public BaseGetGoodsContact.BaseGetGoodsModel initM() {
		return new BaseGetGoodsContact.BaseGetGoodsModel() {
		};
	}

	@Override
	public BaseGetGoodsContact.BaseGetGoodsPresent initP() {
		return new BaseGetGoodsContact.BaseGetGoodsPresent() {
		};
	}
}
