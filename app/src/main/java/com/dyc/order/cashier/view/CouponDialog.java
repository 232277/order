package com.dyc.order.cashier.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.dyc.order.cashier.R;
import com.dyc.order.cashier.adapter.CouponListAdapter;
import com.dyc.order.cashier.constant.ServerAddress;
import com.dyc.order.cashier.function.CountDown;
import com.dyc.order.cashier.base.MyApplication;
import com.dyc.order.cashier.mvp.activity.base.BaseCountDownActivity;
import com.dyc.order.cashier.data.local.PictureCache;
import com.dyc.order.cashier.data.response.CouponListData;
import com.dyc.order.cashier.data.response.MemberInfoData;
import com.dyc.administrator.toollibrary.utils.MLogger;
import com.dyc.administrator.toollibrary.utils.NumUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

/**
 * func:
 * author:丁语成 on 2020/3/26 16:42
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class CouponDialog extends Dialog {
	private MLogger logger = MLogger.getLogger(this.getClass());
	private TextView title;
	private ImageView close;
	private ImageView closeL;
	private ImageView avatar;
	private ConstraintLayout memberInfoView;
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
	private boolean hideMember;
	private String couponCode;

	public CouponDialog(@NonNull Context context, MemberInfoData memberInfoData,
						CouponListData enableCouponListData, Activity activity) {
		super(context);
		this.memberInfoData = memberInfoData;
		this.enableCouponListData = enableCouponListData;
		countDown = new CountDown();
		this.activity = activity;
	}

	public void setData(MemberInfoData memberInfoData,
						CouponListData enableCouponListData){
		this.memberInfoData = memberInfoData;
		this.enableCouponListData = enableCouponListData;
		initData();
		if (enableCouponAdapter != null){
			enableCouponAdapter.setHideCheckBox(!hideMember);
		}
	}

	public void setHideMember(boolean hideMember) {
		this.hideMember = hideMember;
	}

	public void hideMemberInfo(boolean hide){
		memberInfoView.setVisibility(hide ? View.GONE : View.VISIBLE);
		logger.info("hide:" + hide);
		couponNum.setVisibility((hide
				|| enableCouponListData == null
				|| enableCouponListData.total == 0) ? View.INVISIBLE : View.VISIBLE);
		close.setVisibility(hide ? View.GONE : View.VISIBLE);
		closeL.setVisibility(hide ? View.VISIBLE : View.GONE);
		title.setVisibility(hide ? View.VISIBLE : View.GONE);
		if (enableCouponAdapter != null){
			enableCouponAdapter.setHideCheckBox(!hide);
		}
	}

	public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
		this.onDismissListener = onDismissListener;
	}

	public CouponListData.CouponData getDiscountInfo(){
		if (enableCouponAdapter != null){
			CouponListData.CouponData data = enableCouponAdapter.getEnableCouponInfo();
			if (data != null){
				couponCode = data.couponCode;
			}else {
				couponCode = null;
			}
			return data;
		}
		return null;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setWindow();
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
		setContentView(R.layout.dialog_coupon_list_layout);
		memberInfoView = findViewById(R.id.memberInfoView);
		title = findViewById(R.id.title);
		close = findViewById(R.id.close);
		closeL = findViewById(R.id.closeL);
		avatar = findViewById(R.id.avatar);
		memberName = findViewById(R.id.name);
		memberLevel = findViewById(R.id.levelLabel);
		memberPhone = findViewById(R.id.phone);
		memberPoint = findViewById(R.id.point);
		memberBalance = findViewById(R.id.balance);
		couponNum = findViewById(R.id.couponNum);
		enableCoupon = findViewById(R.id.enableCoupon);
		initData();
		hideMemberInfo(hideMember);
		countDown.startCountDown(CountDownDialog.DIALOG_COUNT_DOWN_TIME,
				BaseCountDownActivity.BASE_COUNT_DELAY,
				BaseCountDownActivity.BASE_COUNT_PERIOD);
	}

	private void initData(){
		close.setOnClickListener(v -> dismiss());
		closeL.setOnClickListener(v -> dismiss());
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
		if (enableCouponListData != null){
			ArrayList<CouponListData.CouponData> enableData = new ArrayList<>(new ArrayList<>(Arrays.asList(enableCouponListData.rows)));

			if (!enableData.isEmpty()){
				logger.info("enableData:" + enableData.size());
				enableCoupon.setLayoutManager(new LinearLayoutManager(getContext()));
				enableCouponAdapter = new CouponListAdapter(R.layout.adapter_coupon_item_layout, enableData);
				enableCouponAdapter.setListener(data -> dismiss());
				if (couponCode != null){
					enableCouponAdapter.setCheckedCoupon(couponCode);
					couponCode = null;
				}
				enableCoupon.setAdapter(enableCouponAdapter);
				enableCouponAdapter.setEmptyView(R.layout.coupon_dialog_coupon_empty_view);
				couponNum.setText(String.format(Locale.getDefault(), "%s(%d)",
						MyApplication.getStringFromR(R.string.label_member_dialog_coupon),
						enableData.size()));
			}
			enableCoupon.setVisibility(enableData.isEmpty() ? View.GONE : View.VISIBLE);
		}else {
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
//		lp.y = 96;
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
}
