package com.dyc.order.cashier.mvp.activity.payment;

import android.content.Intent;
import android.view.View;

import com.dyc.order.cashier.R;
import com.dyc.order.cashier.constant.ActivityOperationField;
import com.dyc.order.cashier.contact.BaseMemberInfoContact;
import com.dyc.order.cashier.data.local.DataCache;
import com.dyc.order.cashier.data.local.MemberCenter;
import com.dyc.order.cashier.data.response.ActivityInfoData;
import com.dyc.order.cashier.data.response.CouponListData;
import com.dyc.order.cashier.data.response.MemberInfoData;
import com.dyc.order.cashier.data.response.MemberLevelData;
import com.dyc.order.cashier.data.response.PointConfigData;
import com.dyc.order.cashier.mvp.activity.base.BaseMemberInfoActivity;
import com.dyc.order.cashier.mvp.fragment.BillMVPFragment2;
import com.dyc.order.cashier.mvp.activity.coupon.CouponSeparateMVPActivity;

public class BillMVPActivity extends BaseMemberInfoActivity<
		BaseMemberInfoContact.BaseMemberInfoModel,
		BaseMemberInfoContact.BaseMemberInfoView,
		BaseMemberInfoContact.BaseMemberInfoPresent
		> implements BaseMemberInfoContact.BaseMemberInfoView{

	private BillMVPFragment2 fragment;

	@Override
	public int getLayoutId() {
		return R.layout.activity_bill_mvp;
	}

	@Override
	public void doBeforeInitView(View v) {
//		showNormalActionBar(getString(R.string.label_top_bar_bill));
		showActionBar(false);
	}

	@Override
	public void onInitView(View view) {
		super.onInitView(view);
		showCountDownStr(false);
		fragment = BillMVPFragment2.newInstance(this);
		findViewById(R.id.leftView).setOnClickListener(v -> finish());
		setContentFragment(fragment);
	}

	@Override
	public void doAfterInitView(View v) {
		super.doAfterInitView(v);
		MemberInfoData memberInfoData = MemberCenter.getMemberInfoData();
		if (memberInfoData != null){
			fragment.onMemberStateChanged(true, memberInfoData);
			showMemberInfo(true);
		}
		getPresenter().getPointConfigInfo();
	}

	@Override
	public void onResume() {
		super.onResume();
		restartCountDown();
		couponInfo = (CouponListData.CouponData) DataCache.get(ActivityOperationField.CHOSEN_COUPON);
		logger.info("couponInfo:" + ((couponInfo == null) ? "为空" : couponInfo.couponCode));
		if (couponInfo != null){
			setCouponDiscount(couponInfo, enableCoupon == null ? 0 : enableCoupon.total);
		}
	}

	@Override
	public void onMemberStateChanged(boolean isMember, MemberInfoData memberInfoData) {
		runOnUiThread(()->fragment.onMemberStateChanged(isMember, memberInfoData));
		getCoupon(false);
	}

	@Override
	public void showMemberDetailInfo() {
		showCoupons();
	}

	@Override
	public void showCoupons() {
		pauseCountDown();
		if (couponInfo != null){
			DataCache.addData(ActivityOperationField.CHOSEN_COUPON, couponInfo);
		}
		startActivity(new Intent(this, CouponSeparateMVPActivity.class));
	}

	@Override
	public void setCouponDiscount(CouponListData.CouponData data, long total) {
		fragment.setCouponDiscount(data, total);
	}

	@Override
	public void getGoodsActivityInfoSuccess(ActivityInfoData activityInfoData) {
		super.getGoodsActivityInfoSuccess(activityInfoData);
		runOnUiThread(()->fragment.onActivityInfoChanged(activityInfoData));
	}

	@Override
	public void getPointConfigInfoSuccess(PointConfigData pointConfigData) {
		super.getPointConfigInfoSuccess(pointConfigData);
		logger.info("成功获取积分设置");
		runOnUiThread(()->fragment.onPointConfigChanged(pointConfigData));
	}

	@Override
	public void getMemberLevelsSuccess(MemberLevelData levelData) {
		super.getMemberLevelsSuccess(levelData);
		setDiscountAndPoints();
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
	protected void changeMyActionBar(long countTime) {
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		DataCache.removeData(ActivityOperationField.CHOSEN_COUPON);
	}
}
