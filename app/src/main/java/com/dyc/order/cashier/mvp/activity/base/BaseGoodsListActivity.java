package com.dyc.order.cashier.mvp.activity.base;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import com.dyc.order.cashier.R;
import com.dyc.order.cashier.contact.BaseGetGoodsContact;
import com.dyc.order.cashier.data.local.ShoppingCart;

/**
 * func:
 * author:丁语成 on 2020/3/24 13:46
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public abstract class BaseGoodsListActivity<
		M extends BaseGetGoodsContact.BaseGetGoodsModel,
		V extends BaseGetGoodsContact.BaseGetGoodsView,
		P extends BaseGetGoodsContact.BaseGetGoodsPresent
		>
		extends BaseOutScanActivity<M, V, P>
		implements BaseGetGoodsContact.BaseGetGoodsView {

	@Override
	public void doBeforeInitView(View v) {
		logger.info("doBeforeInitView in BaseGoodsList");
		super.doBeforeInitView(v);
		showActionBar(true);
		if (getTopBar() != null){
			getTopBar().setRightBtnClickListener(view -> {
				clearCart();
			});
		}
	}

	@Override
	public void onInitView(View view) {
		startCountDown();
	}

	@Override
	public void doAfterInitView(View v) {
		super.doAfterInitView(v);
		getActivityInfo();
	}

	public void getActivityInfo(){
		getPresenter().getActivityInfo();
	}

	public void clearCart(){
		ShoppingCart.clearCartNow();
	}

	@Override
	protected void onCountDown(long countTime) {
		super.onCountDown(countTime);
		changeMyActionBar(countTime);
	}

	protected void changeMyActionBar(long countTime){
		SpannableString spannableString = new SpannableString(getString(R.string.label_scan_goods_clear_shopping_cart, countTime));
		spannableString.setSpan(new ForegroundColorSpan(
				getResources().getColor(R.color.normal_item_text_color_red, null)),
				6, 6 + (""+countTime).length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		changeActionBar(null, spannableString);
	}

	@Override
	public void pauseCountDown() {
		super.pauseCountDown();
		changeMyActionBar(super.getTotalCount());
	}

	@Override
	protected void onCountFinish() {
		super.onCountFinish();
	}

	@Override
	public void onInputFinish(String keySequence) {
	}


}
