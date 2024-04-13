package com.dyc.order.cashier.mvp.activity.coupon;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dyc.order.cashier.R;
import com.dyc.order.cashier.adapter.CouponListAdapter;
import com.dyc.order.cashier.constant.ActivityOperationField;
import com.dyc.order.cashier.contact.CouponContact;
import com.dyc.order.cashier.data.local.DataCache;
import com.dyc.order.cashier.data.response.CouponListData;
import com.dyc.order.cashier.mvp.activity.base.BaseCountDownActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class CouponMVPActivity extends BaseCountDownActivity<
        CouponContact.CouponModel,
		CouponContact.CouponView,
		CouponContact.CouponPresent> implements CouponContact.CouponView {
	private LinearLayout topLeftView;
	private TextView countDownStr;
	private ConstraintLayout canUseCouponLayout;
	private LinearLayout uselessCouponLayout;
	private RecyclerView usefulCoupon;
	private RecyclerView uselessCoupon;
	private TextView couponNum;
	private Button doNotUseCoupon;

	private CouponListAdapter enableCouponAdapter;
	private CouponListAdapter uselessCouponAdapter;
	private CouponListData enableCoupon;
	private List<CouponListData.CouponData> enableCoupons;
	List<CouponListData.CouponData> uselessCoupons;
	private Handler handler= new Handler();


	@Override
	public int getLayoutId() {
		return R.layout.activity_coupon_mvp;
	}

	@Override
	public void onInitView(View view) {
		countDownStr = findViewById(R.id.countDownStr);
		topLeftView = findViewById(R.id.topBarLeft);
		canUseCouponLayout = findViewById(R.id.canUseCouponLayout);
		uselessCouponLayout = findViewById(R.id.uselessCouponLayout);
		usefulCoupon = findViewById(R.id.usefulCoupon);
		uselessCoupon = findViewById(R.id.uselessCoupon);
		couponNum = findViewById(R.id.couponNum);
		doNotUseCoupon = findViewById(R.id.doNotUseCoupon);
		topLeftView.setOnClickListener(v->finish());
		doNotUseCoupon.setOnClickListener(v->{
			if (enableCouponAdapter != null){
				enableCouponAdapter.setCheckedPos(-1);
			}
			finish();
		});
		showActionBar(false);
		startCountDown();
		runOnUiThread(this::initData);
	}

	private void initData(){
		enableCoupon = (CouponListData) DataCache.get(ActivityOperationField.USEFUL_COUPON);
		if (enableCoupon != null){
			List<CouponListData.CouponData> allCoupon = Arrays.asList(enableCoupon.rows);
			enableCoupons = new ArrayList<>();
			uselessCoupons = new ArrayList<>();
			allCoupon.forEach(couponData -> {
				if (couponData.ableToUse){
					enableCoupons.add(couponData);
				}else {
					uselessCoupons.add(couponData);
				}
			});

			if (!enableCoupons.isEmpty()){
				couponNum.setText(String.format(Locale.getDefault(), "(%d)", enableCoupon.total));
				RecyclerView.LayoutManager enableCouponLayoutManager = new LinearLayoutManager(this);
				enableCouponAdapter = new CouponListAdapter(R.layout.adapter_coupon_item_layout, enableCoupons);
				usefulCoupon.setLayoutManager(enableCouponLayoutManager);
				usefulCoupon.setAdapter(enableCouponAdapter);
			}else {
				canUseCouponLayout.setVisibility(View.GONE);
			}

			if (!uselessCoupons.isEmpty()){
				RecyclerView.LayoutManager uselessCouponLayoutManager = new LinearLayoutManager(this);
				uselessCouponAdapter = new CouponListAdapter(R.layout.adapter_coupon_item_layout, uselessCoupons);
				uselessCoupon.setLayoutManager(uselessCouponLayoutManager);
				uselessCoupon.setAdapter(uselessCouponAdapter);
			}else {
				uselessCouponLayout.setVisibility(View.GONE);
			}
		}else {
			canUseCouponLayout.setVisibility(View.GONE);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (enableCoupon.total > 0){
			DataCache.addData(ActivityOperationField.CHOSEN_COUPON, enableCouponAdapter.getEnableCouponInfo());
		}
	}

	@Override
	protected void onCountDown(long countTime) {
		super.onCountDown(countTime);
		countDownStr.setText(getFormatCountDownStr(countTime));
	}

	@Override
	public void getCouponSuccess(CouponListData listData) {

	}

	@Override
	public void getCouponFail(Throwable throwable) {

	}

	@Override
	public void getAllCouponSuccess(CouponListData listData) {

	}

	@Override
	public void getAllCouponFail(Throwable throwable) {

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
