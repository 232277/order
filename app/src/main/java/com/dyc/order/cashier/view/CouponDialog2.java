package com.dyc.order.cashier.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.dyc.order.cashier.adapter.CouponListAdapter;
import com.dyc.order.cashier.constant.ServerAddress;
import com.dyc.order.cashier.function.CountDown;
import com.dyc.administrator.toollibrary.utils.MLogger;
import com.dyc.administrator.toollibrary.utils.NumUtils;
import com.dyc.order.cashier.R;
import com.dyc.order.cashier.base.MyApplication;
import com.dyc.order.cashier.mvp.activity.base.BaseCountDownActivity;
import com.dyc.order.cashier.data.local.PictureCache;
import com.dyc.order.cashier.data.response.CouponListData;
import com.dyc.order.cashier.data.response.MemberInfoData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

/**
 * func:
 * author:丁语成 on 2020/5/28 16:57
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class CouponDialog2 extends Dialog {
	private MLogger logger = MLogger.getLogger(this.getClass());
	private ImageView closeL;
	private ImageView avatar;
	private TextView countDownStr;
	private TextView memberName;
	private TextView memberLevel;
	private TextView memberPhone;
	private TextView memberPoint;
	private TextView memberBalance;
	private TextView couponNum;
	private RecyclerView enableCoupon;
	private MemberInfoData memberInfoData;
	private CouponListData enableCouponListData;
	private DialogInterface.OnDismissListener onDismissListener;
	private CountDown countDown;
	private CouponListAdapter enableCouponAdapter;
	private Activity activity;

	public CouponDialog2(@NonNull Context context, MemberInfoData memberInfoData,
						CouponListData enableCouponListData, Activity activity) {
		super(context);
		this.memberInfoData = memberInfoData;
		this.enableCouponListData = enableCouponListData;
		countDown = new CountDown();
		this.activity = activity;
	}

	public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
		this.onDismissListener = onDismissListener;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setWindow();
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
		setContentView(R.layout.dialog_coupon2_list_layout);
		closeL = findViewById(R.id.closeL);
		avatar = findViewById(R.id.avatar);
		countDownStr = findViewById(R.id.countDownStr);
		memberName = findViewById(R.id.name);
		memberLevel = findViewById(R.id.levelLabel);
		memberPhone = findViewById(R.id.phone);
		memberPoint = findViewById(R.id.point);
		memberBalance = findViewById(R.id.balance);
		couponNum = findViewById(R.id.couponNum);
		enableCoupon = findViewById(R.id.enableCoupon);
		countDown.startCountDown(CountDownDialog.DIALOG_COUNT_DOWN_TIME,
				BaseCountDownActivity.BASE_COUNT_DELAY,
				BaseCountDownActivity.BASE_COUNT_PERIOD);
		initData(true);
	}

	public void setData(MemberInfoData memberInfoData,
						CouponListData enableCouponListData){
		this.memberInfoData = memberInfoData;
		this.enableCouponListData = enableCouponListData;
		initData(false);
		if (enableCouponAdapter != null){
			enableCouponAdapter.setHideCheckBox(true);
		}
	}

	private void initData(boolean first){
		closeL.setOnClickListener(v -> dismiss());
		if (!first){
			countDown.restartCountDown();
		}else {
			countDown.setListener(new CountDown.OnCountDownListener() {
				@Override
				public void onCountDown(long countTime) {
					activity.runOnUiThread(()->{
						countDownStr.setText(CountDown.getFormatCountDownStr(countTime));
					});
				}

				@Override
				public void onCountFinish() {
					dismiss();
				}
			});
		}

		//取消刷新动画
		((SimpleItemAnimator)enableCoupon.getItemAnimator()).setSupportsChangeAnimations(false);
		//会员信息
		if (memberInfoData != null){
			if (!TextUtils.isEmpty(memberInfoData.getAvatar())){
				PictureCache.setBitMapForImageView(ServerAddress.AVATAR_ADDRESS + memberInfoData.getAvatar(),
						avatar, 50);
			}
			memberName.setText(memberInfoData.getUsername());
			memberLevel.setText(memberInfoData.getLevelName());
			memberPhone.setText(memberInfoData.getPhoneNumber());
			memberPoint.setText(String.format(Locale.getDefault(), "%d", memberInfoData.getPoints()));
			memberBalance.setText(NumUtils.remain2NumWithoutZero(memberInfoData.getOverage()));
		}
		//可用的优惠券不能为空
		if (enableCouponListData != null && enableCouponListData.total != 0){
			ArrayList<CouponListData.CouponData> enableData = new ArrayList<>(new ArrayList<>(Arrays.asList(enableCouponListData.rows)));

			if (!enableData.isEmpty()){
				logger.info("enableData:" + enableData.size());
				enableCoupon.setLayoutManager(new LinearLayoutManager(getContext()));
				enableCouponAdapter = new CouponListAdapter(R.layout.coupon_choose_adapter_item_layout, enableData);
				enableCouponAdapter.setListener(data -> dismiss());
				enableCouponAdapter.setHideCheckBox(true);
				enableCoupon.setAdapter(enableCouponAdapter);
				enableCouponAdapter.setEmptyView(R.layout.coupon_dialog_coupon_empty_view);
				couponNum.setText(String.format(Locale.getDefault(), "%s(%d)",
						MyApplication.getStringFromR(R.string.label_member_dialog_coupon),
						enableData.size()));
			}
			couponNum.setVisibility(enableData.isEmpty() ? View.INVISIBLE : View.VISIBLE);
			enableCoupon.setVisibility(enableData.isEmpty() ? View.GONE : View.VISIBLE);
		}else {
			couponNum.setVisibility(View.INVISIBLE);
			enableCoupon.setVisibility(View.GONE);
		}
	}

	private void setWindow(){
		Window window = getWindow();
		window.setBackgroundDrawableResource(android.R.color.transparent);
		window.setGravity(Gravity.BOTTOM);
		window.setWindowAnimations(com.centerm.administrator.toollibrary.R.style.toollibraryBottomDialog);
		window.getDecorView().setPadding(0, 0, 0, 0);
		//设置对话框大小
		window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

		WindowManager.LayoutParams lp=this.getWindow().getAttributes();
		lp.dimAmount=0.5f;
		lp.y = 0;
		this.getWindow().setAttributes(lp);
		this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
	}

	@Override
	public void dismiss() {
		super.dismiss();
		if (onDismissListener != null){
			onDismissListener.onDismiss(this);
		}
	}

	@Override
	public boolean onTouchEvent(@NonNull MotionEvent event) {
		if (countDown != null){
			countDown.restartCountDown();
		}
		return super.onTouchEvent(event);
	}
}

