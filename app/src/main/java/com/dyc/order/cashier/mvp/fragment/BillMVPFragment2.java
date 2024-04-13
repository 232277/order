package com.dyc.order.cashier.mvp.fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dyc.order.cashier.constant.ActivityOperationField;
import com.dyc.order.cashier.constant.OrderFieldConstant;
import com.dyc.order.cashier.contact.PaymentActivityContact;
import com.dyc.order.cashier.data.fields.PaymentRequestFields;
import com.dyc.order.cashier.data.local.PayAppCenter;
import com.dyc.order.cashier.data.response.ActivityInfoData;
import com.dyc.order.cashier.data.response.CouponListData;
import com.dyc.order.cashier.data.response.MemberInfoData;
import com.dyc.order.cashier.data.response.PlaceOrderData;
import com.dyc.administrator.toollibrary.utils.DipUtil;
import com.dyc.administrator.toollibrary.utils.MLogger;
import com.dyc.administrator.toollibrary.utils.NumUtils;
import com.dyc.order.cashier.R;
import com.dyc.order.cashier.mvp.activity.payment.BillMVPActivity;
import com.dyc.order.cashier.mvp.activity.payment.JianHangPaymentMVPActivity;
import com.dyc.order.cashier.mvp.activity.payment.JiaoHangPaymentMVPActivity;
import com.dyc.order.cashier.mvp.activity.payment.PaymentMVPActivity;
import com.dyc.order.cashier.data.discount.DiscountStratageContext;
import com.dyc.order.cashier.data.discount.StratageRule;
import com.dyc.order.cashier.data.local.ActivityCenter;
import com.dyc.order.cashier.data.local.DataCache;
import com.dyc.order.cashier.data.local.MemberCenter;
import com.dyc.order.cashier.data.local.PointsConfigCenter;
import com.dyc.order.cashier.data.local.ShoppingCart;
import com.dyc.order.cashier.data.response.PointConfigData;
import com.dyc.simplemvplibrary.BaseMvpFragment;

import java.util.Locale;
import java.util.Map;

import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * func:
 * author:丁语成 on 2020/6/3 13:56
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class BillMVPFragment2 extends BaseMvpFragment<
        PaymentActivityContact.PaymentActivityModel,
		PaymentActivityContact.PaymentActivityView,
		PaymentActivityContact.PaymentActivityPresent> implements PaymentActivityContact.PaymentActivityView {
	private MLogger logger = MLogger.getLogger(this.getClass());
	private View oriPriceDivider;
	private View goodsDiscountDivider;
	private View activityDiscountDivider;
	private View couponDiscountDivider;
	private TextView totalPrice;
	private TextView oriPrice;
	private TextView goodsDiscountStr;
	private TextView goodsDiscount;
	private TextView activityDiscountStr;
	private TextView activityDiscount;
	//	private TextView activityLabel;
	private LinearLayout activityLabel;
	private TextView couponLabel;
	private ImageView couponArrow;
	private TextView pointNum;
	private TextView pointDiscount;
	private CheckBox usePoint;
	private ConstraintLayout pointLayout;
	private Button goPay;

	private PaymentRequestFields paymentRequestFields;
	private MemberInfoData memberInfoData;
	private long couponNum;
	private CouponListData.CouponData couponData;
	private double couponDiscountNum;
	private double activityDiscountNum;
	private double pointDiscountNum;
	private PayAppCenter.PayAppName payAppName;

	private BillMVPActivity activity;

	public BillMVPFragment2(){}

	public static BillMVPFragment2 newInstance(BillMVPActivity activity) {
		BillMVPFragment2 fragment = new BillMVPFragment2();
		fragment.setActivity(activity);
		return fragment;
	}

	public void setActivity(BillMVPActivity activity) {
		this.activity = activity;
	}

	@Override
	public int getLayoutId() {
		return R.layout.fragment_bill_mvp;
	}

	@Override
	public void onInitView(View view) {
		oriPriceDivider = view.findViewById(R.id.oriPriceDivider);
		goodsDiscountDivider = view.findViewById(R.id.goodsDiscountDivider);
		activityDiscountDivider = view.findViewById(R.id.activityDiscountDivider);
		couponDiscountDivider = view.findViewById(R.id.couponDiscountDivider);
		totalPrice = view.findViewById(R.id.totalPrice);
		oriPrice = view.findViewById(R.id.oriPrice);
		goodsDiscountStr = view.findViewById(R.id.goodsDiscountStr);
		goodsDiscount = view.findViewById(R.id.goodsDiscount);
		activityDiscountStr = view.findViewById(R.id.activityDiscountStr);
		activityDiscount = view.findViewById(R.id.activityDiscount);
		activityLabel = view.findViewById(R.id.activityLabel);
		couponLabel = view.findViewById(R.id.couponLabel);
		couponArrow = view.findViewById(R.id.couponArrow);
		pointNum = view.findViewById(R.id.point);
		pointDiscount = view.findViewById(R.id.pointDiscount);
		usePoint = view.findViewById(R.id.usePoint);
		pointLayout = view.findViewById(R.id.pointLayout);
		goPay = view.findViewById(R.id.goPay);
		initData();
	}

	private void initData(){
		oriPrice.setText(String.format("%s%s",
				getString(R.string.toollibrary_label_concurrency_sign),
				NumUtils.remain2NumWithoutZero(ShoppingCart.getOriPrice())));
		setTotalPrice();
		setGoodsDiscount();
		setActivityDiscount();
		setCoupon();
		setPoint();
		usePoint.setOnCheckedChangeListener((buttonView, isChecked) -> {
			setTotalPrice();
		});
		goPay.setOnClickListener(v -> {
			goPay();
		});
	}

	/**
	 * 进行交易
	 */
	private void goPay(){
		paymentRequestFields = ShoppingCart.getPaymentRequest(MemberCenter.getMemberInfoData() == null
				? -1 : MemberCenter.getMemberInfoData().getId());
		if (couponData != null && couponData.couponVO != null){
			paymentRequestFields.getOrder().couponList = new String[]{couponData.couponCode};
		}
		if (usePoint.isChecked() && pointDiscountNum > 0){
			//需要使用的积分有小数则加1
			int integerPoint = (int)(pointDiscountNum / PointsConfigCenter.getPointsToMoneyRate());
			if (pointDiscountNum / PointsConfigCenter.getPointsToMoneyRate() > integerPoint){
				integerPoint += 1;
			}
			paymentRequestFields.getOrder().points = integerPoint;
		}
		paymentRequestFields.getOrder().setTransAmount(
				NumUtils.remain2Num(Double.parseDouble(totalPrice.getText().toString().substring(1))));
		paymentRequestFields.getOrder().setPayType(OrderFieldConstant.PayType.UNKNOWN.getNum());
//		DataCache.addData(ActivityOperationField.PAYMENT_REQUEST, paymentRequestFields);
		showLoading(R.string.label_payment_placing_order);
		payAppName = PayAppCenter.payWayChoose();

		if (payAppName == PayAppCenter.PayAppName.ZHAOHANG){
			Intent intent = new Intent(activity, PaymentMVPActivity.class);
			intent.putExtra(ActivityOperationField.PAYMENT_REQUEST, paymentRequestFields);
			startActivity(intent);
		}else {
			//下单
			getPresenter().placeOrder(paymentRequestFields);
		}
	}

	@Override
	public void onPlaceOrderSuccess(PlaceOrderData placeOrderData) {
		hideDialog();
		if (placeOrderData != null){
			Intent intent = new Intent();
			intent.putExtra(ActivityOperationField.PLACE_ORDER_DATA, placeOrderData);
			intent.putExtra(ActivityOperationField.PAYMENT_REQUEST, paymentRequestFields);
			DataCache.removeData(ActivityOperationField.CHOSEN_COUPON);
			//判断使用的收单
			PackageManager packageManager = activity.getPackageManager();
			if (packageManager == null){
				intent.setClass(activity, PaymentMVPActivity.class);
			}else {
				switch (payAppName){
					case JIANHANG:
					case JIANHANG_2:
						intent.setClass(activity, JianHangPaymentMVPActivity.class);
						logger.info("使用建行收单");
						break;
					case JIAOHANG:
						intent.setClass(activity, JiaoHangPaymentMVPActivity.class);
						logger.info("使用交行收单");
						break;
					case ZHAOHANG:
					default:
						intent.setClass(activity, PaymentMVPActivity.class);
				}
			}
			startActivity(intent);
		}else {
			logger.info("下单数据为空，下单失败");
			showNotifyDialog(R.string.label_payment_fail_place_order, R.drawable.fail);
		}
	}

	@Override
	public void onPlaceOrderFail(Throwable throwable) {
		hideDialog();
		logger.info("下单失败");
		showNotifyDialog(throwable.getMessage(), R.drawable.fail);
	}

	/**
	 * 计算总价
	 */
	private void setTotalPrice(){
		if (isAdded()){
			double totalPriceNum = ShoppingCart.getTotalPrice() - couponDiscountNum - activityDiscountNum;
			totalPriceNum = Math.max(0, totalPriceNum);
			if (usePoint.isChecked()){
				totalPriceNum -= pointDiscountNum;
			}
			totalPrice.setText(String.format("%s%s",
					getString(R.string.toollibrary_label_concurrency_sign),
					NumUtils.remain2NumWithoutZero(totalPriceNum)));
		}
	}

	/**
	 * 计算商品折扣
	 */
	private void setGoodsDiscount(){
		if (isAdded()) {
			double totalDiscount = ShoppingCart.getTotalDiscountPrice();
			if (totalDiscount <= 0) {
				goodsDiscount.setText(String.format(Locale.getDefault(), "%s%d",
						getString(R.string.toollibrary_label_concurrency_sign), 0));
			} else {
				goodsDiscount.setText(String.format("-%s%s",
						getString(R.string.toollibrary_label_concurrency_sign),
						NumUtils.remain2NumWithoutZero(totalDiscount)));
			}
			goodsDiscountStr.setVisibility(totalDiscount <= 0 ? View.GONE : View.VISIBLE);
			goodsDiscount.setVisibility(totalDiscount <= 0 ? View.GONE : View.VISIBLE);
			goodsDiscountDivider.setVisibility(totalDiscount <= 0 ? View.GONE : View.VISIBLE);
		}
	}

	/**
	 * 设置优惠券金额
	 */
	private void setCoupon(){
		SpannableString spannableString;
		//有选券
		if (couponDiscountNum > 0){
			spannableString  = new SpannableString(String.format("-%s%s",
					getString(R.string.toollibrary_label_concurrency_sign),
					NumUtils.remain2NumWithoutZero(couponDiscountNum)));
			spannableString.setSpan(new ForegroundColorSpan(
							getResources().getColor(R.color.dialog_price_text_red, null)),
					0, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
			spannableString.setSpan(new AbsoluteSizeSpan(DipUtil.dip2px(activity, 15f)),
					0, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
			couponLabel.setBackgroundResource(R.color.toollibrary_white);
			couponLabel.setText(spannableString);
		}else if (couponNum <= 0){
			//没有可用券
			couponLabel.setText(R.string.label_bill_mvp_no_enable_coupon);
			couponArrow.setOnClickListener(null);
			couponLabel.setOnClickListener(null);
		}else {
			//有券，没选
			spannableString = new SpannableString(getString(R.string.label_bill_mvp_coupon_enable_num, couponNum));
			spannableString.setSpan(new ForegroundColorSpan(
					getResources().getColor(R.color.toollibrary_white, null)
			), 0, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
			spannableString.setSpan(new AbsoluteSizeSpan(DipUtil.dip2px(activity, 13f)),
					0, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
			couponLabel.setBackgroundResource(R.drawable.circle_corner_coupon_enable);
			couponLabel.setText(spannableString);
		}
		couponArrow.setOnClickListener(v -> activity.showCoupons());
		couponLabel.setOnClickListener(v -> activity.showCoupons());
	}

	/**
	 * 设置积分金额
	 */
	private void setPoint(){
		if (isAdded()){
			if (MemberCenter.getMemberInfoData() != null){
				int points = MemberCenter.getMemberInfoData().getPoints();
				if (points > 0){
					pointDiscountNum = NumUtils.remain2Num((double)points * PointsConfigCenter.getPointsToMoneyRate());
					double total = ShoppingCart.getTotalPrice() - couponDiscountNum - activityDiscountNum;
					total = Math.max(0, total);
					pointDiscountNum = pointDiscountNum > total ? total : pointDiscountNum;
					if (pointDiscountNum > 0){
						pointDiscount.setText(String.format("-%s%s",
								getString(R.string.toollibrary_label_concurrency_sign), NumUtils.remain2NumWithoutZero(pointDiscountNum)));
						//需要使用的积分有小数则加1
						int integerPoint = (int)(pointDiscountNum / PointsConfigCenter.getPointsToMoneyRate());
						if (pointDiscountNum / PointsConfigCenter.getPointsToMoneyRate() > integerPoint){
							integerPoint += 1;
						}
						pointNum.setText(getString(R.string.label_bill_mvp_point_already_have, integerPoint));
						pointNum.setVisibility(View.VISIBLE);
						couponDiscountDivider.setVisibility(View.VISIBLE);
						pointLayout.setVisibility(View.VISIBLE);
						return;
					}
				}
			}
			couponDiscountDivider.setVisibility(View.GONE);
			pointLayout.setVisibility(View.GONE);
		}
	}

	/**
	 * 设置活动优惠
	 */
	public void setActivityDiscount(){
		ActivityInfoData activityInfoData = ActivityCenter.getActivityInfoData();
		boolean showActivityLayout = false;
		if (activityInfoData != null){
			DiscountStratageContext discountStratageContext = ActivityCenter.getPriceBreakDiscountContext(ShoppingCart.getCartItems());
//			activityDiscountNum = ActivityCenter.getPriceBreakDiscount(ShoppingCart.getCartItems());
			activityDiscountNum = discountStratageContext.excute(ShoppingCart.getCartItems());
			Map<StratageRule, Double> activities = discountStratageContext.getSuitableRules();
			if (activityDiscountNum > 0){
				activityDiscount.setText(String.format("-%s%s",
						getString(R.string.toollibrary_label_concurrency_sign),
						NumUtils.remain2NumWithoutZero(activityDiscountNum)));
				showActivityLayout = true;

				if (activityLabel.getVisibility() != View.VISIBLE){
					activityLabel.setVisibility(View.VISIBLE);
					for (Map.Entry<StratageRule, Double> e : activities.entrySet()){
						if (e.getValue() == null || e.getValue() == 0){
							continue;
						}
						logger.info("增加标签" + e.getKey().id);
						TextView textView = new TextView(activityLabel.getContext());
						textView.setTextSize(9);
						textView.setTextColor(getResources().getColor(R.color.member_price_label_color, null));
						textView.setBackgroundResource(R.drawable.circle_corner_bill_mvp_activity_label);
						textView.setText(getString(
								R.string.label_bill_mvp_activity_announce,
								NumUtils.remain2NumWithoutZero(e.getKey().getMatchedThreshold(e.getValue())),
								NumUtils.remain2NumWithoutZero(e.getValue())
						));
						LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
						lp.setMargins(0, 5, 0, 0);
						//view要设置margin的view
						textView.setLayoutParams(lp);
						activityLabel.addView(textView);
					}
				}
			}
		}else {
			activityLabel.setVisibility(View.INVISIBLE);
		}
		activityDiscountDivider.setVisibility(showActivityLayout ? View.VISIBLE : View.GONE);
		activityDiscountStr.setVisibility(showActivityLayout ? View.VISIBLE : View.GONE);
		activityDiscount.setVisibility(showActivityLayout ? View.VISIBLE : View.GONE);
	}

	/**
	 * 设置优惠券信息变换
	 * @param data 获取到的用户优惠券
	 * @param couponNum 优惠券总数
	 */
	public void setCouponDiscount(CouponListData.CouponData data, long couponNum){
		this.couponData = data;
		if (couponData != null && couponData.couponVO != null){
			couponDiscountNum = couponData.couponVO.discount;
		}else {
			couponDiscountNum = 0;
		}
		this.couponNum = couponNum;
		setCoupon();
		setPoint();
		setTotalPrice();
	}

	/**
	 * 会员状态变动
	 * 会员状态改变时先设置积分再设置总价才能保证积分显示正确
	 * @param isMember 是否为会员
	 * @param memberInfoData 会员信息
	 */
	public void onMemberStateChanged(boolean isMember, MemberInfoData memberInfoData){
		this.memberInfoData = memberInfoData;
		setGoodsDiscount();
		setPoint();
		setTotalPrice();
	}

	/**
	 * 活动信息变动
	 * 活动信息发生改变时需先设置活动优惠等保证积分抵扣额显示正确
	 * @param activityInfoData 活动信息
	 */
	public void onActivityInfoChanged(ActivityInfoData activityInfoData) {
		setActivityDiscount();
		setPoint();
		setTotalPrice();
	}

	/**
	 * 积分设置状态变化
	 * 积分与元比例变化故先设置积分再设置总价
	 * @param pointConfigData
	 */
	public void onPointConfigChanged(PointConfigData pointConfigData){
		setPoint();
		setTotalPrice();
	}

	@Override
	public PaymentActivityContact.PaymentActivityModel initM() {
		return new PaymentActivityContact.PaymentActivityModel() {
		};
	}

	@Override
	public PaymentActivityContact.PaymentActivityPresent initP() {
		return new PaymentActivityContact.PaymentActivityPresent() {
		};
	}
}

