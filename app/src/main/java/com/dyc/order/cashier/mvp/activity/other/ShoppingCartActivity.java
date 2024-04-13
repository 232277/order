package com.dyc.order.cashier.mvp.activity.other;

import android.text.TextUtils;
import android.view.View;

import com.dyc.order.cashier.R;
import com.dyc.order.cashier.contact.BaseMemberInfoContact;
import com.dyc.order.cashier.data.local.MemberCenter;
import com.dyc.order.cashier.data.response.ActivityInfoData;
import com.dyc.order.cashier.data.response.CouponListData;
import com.dyc.order.cashier.data.response.MemberInfoData;
import com.dyc.order.cashier.mvp.activity.base.BaseMemberInfoActivity;
import com.dyc.order.cashier.mvp.fragment.ShoppingCartMVPFragment;

public class ShoppingCartActivity extends BaseMemberInfoActivity<
        BaseMemberInfoContact.BaseMemberInfoModel,
		BaseMemberInfoContact.BaseMemberInfoView,
		BaseMemberInfoContact.BaseMemberInfoPresent
		> implements BaseMemberInfoContact.BaseMemberInfoView{
	private ShoppingCartMVPFragment fragment;

	@Override
	public int getLayoutId() {
		return R.layout.activity_shopping_cart;
	}

	@Override
	public void onInitView(View view) {
		super.onInitView(view);
	}

	@Override
	public void doAfterInitView(View view) {
		super.doAfterInitView(view);
		fragment = ShoppingCartMVPFragment.newInstance(this);
		fragment.setActivity(this);
		setContentFragment(fragment);
		findViewById(R.id.textureView).setOnClickListener(v -> {
			v.setVisibility(v.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		if (MemberCenter.getMemberInfoData() == null){
			//TODO 回头记得改回来
			initCamera();
		}
	}

	@Override
	public void getGoodsActivityInfoSuccess(ActivityInfoData activityInfoData) {
		super.getGoodsActivityInfoSuccess(activityInfoData);
		runOnUiThread(()->fragment.onActivityInfoChanged(activityInfoData));
	}

	/**
	 * 显示会员详情/优惠券
	 */
	@Override
	public void showMemberDetailInfo() {
		pauseCountDown();
		getCoupon();
	}

	@Override
	public void onMemberStateChanged(boolean isMember, MemberInfoData memberInfoData) {
		if (isMember){
			stopPreview();
		}else {
			logger.info("会员状态改变，重开摄像头");
			startCamera();
		}
		runOnUiThread(()->fragment.onMemberStateChanged(isMember, memberInfoData));
	}

	@Override
	public void onInputFinish(String keySequence) {
		super.onInputFinish(keySequence);
		if (TextUtils.isDigitsOnly(keySequence)){
			fragment.onInputFinish(keySequence);
		}else {
			getPresenter().getMember(keySequence);
		}
	}

	@Override
	protected void onCountFinish() {
		super.onCountFinish();
		fragment.onCountFinish();
	}

	@Override
	public void clearCart() {
		super.clearCart();
		fragment.clearCart();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		//TODO 引起插扫码枪之类的崩溃
//		clearCart();
//		ShoppingCart.clearCartNow();
//		ShoppingCart.setIsMember(false);
//		MemberCenter.setMemberInfoData(null);
	}

	@Override
	public BaseMemberInfoContact.BaseMemberInfoModel initM() {
		return new BaseMemberInfoContact.BaseMemberInfoModel() {
		};
	}

	@Override
	public BaseMemberInfoContact.BaseMemberInfoPresent initP() {
		return new BaseMemberInfoContact.BaseMemberInfoPresent() {
		};
	}

	@Override
	public void setCouponDiscount(CouponListData.CouponData data, long total) {
	}
}
