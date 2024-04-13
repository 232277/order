package com.dyc.order.cashier.mvp.activity.coupon;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dyc.order.cashier.R;
import com.dyc.order.cashier.adapter.CouponListAdapter;
import com.dyc.order.cashier.constant.ActivityOperationField;
import com.dyc.order.cashier.contact.CouponContact;
import com.dyc.order.cashier.data.local.DataCache;
import com.dyc.order.cashier.data.local.MemberCenter;
import com.dyc.order.cashier.data.local.ShoppingCart;
import com.dyc.order.cashier.data.response.CouponListData;
import com.dyc.order.cashier.mvp.activity.base.BaseCountDownActivity;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CouponSeparateMVPActivity extends BaseCountDownActivity<
        CouponContact.CouponModel,
		CouponContact.CouponView,
		CouponContact.CouponPresent> implements CouponContact.CouponView {
	private LinearLayout topLeftView;
	private TextView countDownStr;
	private RefreshLayout refreshLayout;
	private RecyclerView couponDataView;
	private TextView usefulCouponNum;
	private TextView uselessCouponNum;
	private View usefulLine;
	private View uselessLine;
	private Button doNotUseCoupon;
//	private AVLoadingIndicatorView loading;

	private boolean showUseful = true;
	private CouponListAdapter couponAdapter;
	private CouponListData couponData;
	private CouponListData.CouponData chosenCoupon;
	private List<CouponListData.CouponData> enableCoupons;
	private List<CouponListData.CouponData> uselessCoupons;

	@Override
	public int getLayoutId() {
		return R.layout.activity_coupon_separate_mvp;
	}

	@Override
	public void onInitView(View view) {
		countDownStr = findViewById(R.id.countDownStr);
		topLeftView = findViewById(R.id.topBarLeft);
		refreshLayout = findViewById(R.id.couponView);
		couponDataView = findViewById(R.id.couponDataView);
		usefulCouponNum = findViewById(R.id.usefulCouponNum);
		uselessCouponNum = findViewById(R.id.uselessCouponNum);
		usefulLine = findViewById(R.id.usefulLine);
		uselessLine = findViewById(R.id.uselessLine);
		doNotUseCoupon = findViewById(R.id.doNotUseCoupon);
//		loading = findViewById(R.id.loadingAim);
		showActionBar(false);
		startCountDown();
		init();
	}

	private void init(){
		topLeftView.setOnClickListener(v->finish());
		usefulCouponNum.setOnClickListener(v -> {
			setChooseType(true);
			changeShownData(true);
		});
		uselessCouponNum.setOnClickListener(v -> {
			setChooseType(false);
			changeShownData(false);
		});
		doNotUseCoupon.setOnClickListener(v->{
			if (couponAdapter != null){
				couponAdapter.setCheckedPos(-1);
				chosenCoupon = null;
				DataCache.removeData(ActivityOperationField.CHOSEN_COUPON);
			}
			finish();
		});
		refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
			@Override
			public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
				if (!couponAdapter.loadMore()){
					refreshLayout.setNoMoreData(true);
					refreshLayout.finishLoadMoreWithNoMoreData();
				}else {
					refreshLayout.finishLoadMore(true);
				}
			}

			@Override
			public void onRefresh(@NonNull RefreshLayout refreshLayout) {
				if (showUseful){
					changeShownData(true);
				}else {
					changeShownData(false);
				}
			}
		});
	}

	@Override
	public void doAfterInitView(View v) {
		super.doAfterInitView(v);
		refreshLayout.autoRefresh();
		if (MemberCenter.getMemberInfoData() != null){
			getPresenter().getCoupon(ShoppingCart.getTotalPrice(),
					MemberCenter.getMemberInfoData().getPhoneNumber(), ShoppingCart.getCartGoodsId());
		}
	}

	@Override
	public void getCouponSuccess(CouponListData listData) {
		refreshLayout.finishRefresh(true);
		couponData = listData;
		initData();
	}

	@Override
	public void getCouponFail(Throwable throwable) {
		refreshLayout.finishRefresh(false);
		showNotifyDialog(R.string.label_coupon_activity_fail_get_coupon, R.drawable.fail);
		initData();
	}

	private void initData(){
		enableCoupons = new ArrayList<>();
		uselessCoupons = new ArrayList<>();
		//有数据
		if (couponData != null){
			List<CouponListData.CouponData> allCoupon = Arrays.asList(couponData.rows);

			allCoupon.forEach(couponData -> {
				if (couponData.ableToUse){
					enableCoupons.add(couponData);
				}else {
					uselessCoupons.add(couponData);
				}
			});

			if (!enableCoupons.isEmpty()){
				//有可用优惠券
				couponAdapter = new CouponListAdapter(R.layout.coupon_choose_adapter_item_layout, enableCoupons, false);
				chosenCoupon = (CouponListData.CouponData)DataCache.get(ActivityOperationField.CHOSEN_COUPON);
				if (chosenCoupon != null){
					couponAdapter.setCheckedCoupon(chosenCoupon.couponCode);
				}
			}else if (!uselessCoupons.isEmpty()){
				//有不可用优惠券
				couponAdapter = new CouponListAdapter(R.layout.coupon_choose_adapter_item_layout, uselessCoupons, false);
				setChooseType(false);
			}else {
				//啥都没
				couponAdapter = new CouponListAdapter(R.layout.coupon_choose_adapter_item_layout, new ArrayList<>(), false);
			}
			couponAdapter.setListener(data -> {
				chosenCoupon = data;
				DataCache.addData(ActivityOperationField.CHOSEN_COUPON, chosenCoupon);
				if (chosenCoupon != null){
					logger.info("点击优惠券：" + chosenCoupon.couponCode);
				}else {
					logger.info("选择优惠券时未取得优惠券");
				}
				finish();
			});
		}else {
			couponAdapter = new CouponListAdapter(R.layout.coupon_choose_adapter_item_layout, new ArrayList<>(), false);
		}
		usefulCouponNum.setText(getString(R.string.label_coupon_activity_useful_coupon_num, enableCoupons.size()));
		uselessCouponNum.setText(getString(R.string.label_coupon_activity_useless_coupon_num, uselessCoupons.size()));
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		couponDataView.setLayoutManager(layoutManager);
		couponDataView.setAdapter(couponAdapter);
		couponAdapter.setEmptyView(R.layout.adapter_empty_view_no_coupon_now);
		//取消更新动画
		((SimpleItemAnimator)couponDataView.getItemAnimator()).setSupportsChangeAnimations(false);
	}

	private void changeShownData(boolean enable){
		if (couponAdapter != null){
			if (enable){
				couponAdapter.setNewData(enableCoupons);
				if (chosenCoupon != null){
					logger.info("选择的券码值---changeShownData:" + chosenCoupon.couponCode);
					couponAdapter.setCheckedCoupon(chosenCoupon.couponCode);
				}else {
					couponAdapter.setCheckedPos(-1);
				}
			}else {
				couponAdapter.setNewData(uselessCoupons);
				couponAdapter.setCheckedPos(-1);
			}

			refreshLayout.finishRefresh();
		}
	}

	private void setChooseType(boolean showUseful){
		this.showUseful = showUseful;
		if (showUseful){
			usefulCouponNum.setTextColor(getResources().getColor(R.color.dialog_btn_blue, null));
			uselessCouponNum.setTextColor(getResources().getColor(R.color.normal_item_text_color_gray_dark, null));
		}else {
			uselessCouponNum.setTextColor(getResources().getColor(R.color.dialog_btn_blue, null));
			usefulCouponNum.setTextColor(getResources().getColor(R.color.normal_item_text_color_gray_dark, null));
		}
		usefulLine.setVisibility(showUseful ? View.VISIBLE : View.INVISIBLE);
		uselessLine.setVisibility(showUseful ? View.INVISIBLE : View.VISIBLE);
	}

	@Override
	protected void onCountDown(long countTime) {
		super.onCountDown(countTime);
		runOnUiThread(()->countDownStr.setText(getFormatCountDownStr(countTime)));
	}

	@Override
	public CouponContact.CouponModel initM() {
		return new CouponContact.CouponModel() {
		};
	}

	@Override
	public CouponContact.CouponPresent initP() {
		return new CouponContact.CouponPresent() {
		};
	}
}
