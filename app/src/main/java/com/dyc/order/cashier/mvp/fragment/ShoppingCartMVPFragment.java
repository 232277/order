package com.dyc.order.cashier.mvp.fragment;

import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.TextView;

import com.dyc.order.cashier.R;
import com.dyc.order.cashier.adapter.ScanGoodsListAdapter;
import com.dyc.order.cashier.contact.BaseGetGoodsContact;
import com.dyc.order.cashier.view.CountDownDialog;
import com.dyc.order.cashier.base.MyApplication;
import com.dyc.order.cashier.data.local.ActivityCenter;
import com.dyc.order.cashier.data.local.ShoppingCart;
import com.dyc.order.cashier.data.response.ActivityInfoData;
import com.dyc.order.cashier.data.response.GoodsInfoList;
import com.dyc.order.cashier.data.response.MemberInfoData;
import com.dyc.order.cashier.mvp.activity.other.SearchGoodsActivity;
import com.dyc.order.cashier.mvp.activity.other.ShoppingCartActivity;
import com.dyc.order.cashier.mvp.activity.payment.BillMVPActivity;
import com.dyc.administrator.toollibrary.utils.DipUtil;
import com.dyc.administrator.toollibrary.utils.MLogger;
import com.dyc.administrator.toollibrary.utils.NumUtils;
import com.dyc.simplemvplibrary.BaseMvpFragment;

import java.util.ArrayList;


public class ShoppingCartMVPFragment extends BaseMvpFragment<
        BaseGetGoodsContact.BaseGetGoodsModel,
		BaseGetGoodsContact.BaseGetGoodsView,
		BaseGetGoodsContact.BaseGetGoodsPresent
		> implements BaseGetGoodsContact.BaseGetGoodsView, View.OnClickListener{
	private MLogger logger = MLogger.getLogger(this.getClass());
	private RecyclerView goodsList;
//	private RecyclerView recommendList;
	private TextView totalNum;
	private TextView totalAmount;
	private TextView goBill;

	private ScanGoodsListAdapter goodsListAdapter;
//	private RecommendGoodsAdapter recommendGoodsAdapter;
	private String code;
	private ShoppingCartActivity activity;

	public ShoppingCartMVPFragment(){}

	public static ShoppingCartMVPFragment newInstance(ShoppingCartActivity activity) {
		ShoppingCartMVPFragment fragment = new ShoppingCartMVPFragment();
		fragment.setActivity(activity);
		return fragment;
	}

	public void setActivity(ShoppingCartActivity activity) {
		logger.info("设置ShoppingActivity:" + (activity == null));
		this.activity = activity;
	}

	@Override
	public int getLayoutId() {
		return R.layout.fragment_shopping_cart;
	}

	@Override
	public void onInitView(View view) {
		goodsList = view.findViewById(R.id.goodsList);
//		recommendList = view.findViewById(R.id.recommendList);
		totalNum = view.findViewById(R.id.totalNum);
		totalAmount = view.findViewById(R.id.totalAmount);
		goBill = view.findViewById(R.id.goBill);
		initData();
	}

	private void initData(){
		//取消更新动画
		((SimpleItemAnimator)goodsList.getItemAnimator()).setSupportsChangeAnimations(false);
		goBill.setOnClickListener(this);
		goodsListAdapter = new ScanGoodsListAdapter(new ArrayList<>(ShoppingCart.getCartNow().values()));
		goodsList.setAdapter(goodsListAdapter);
		goodsList.setLayoutManager(new LinearLayoutManager(activity));
		goodsListAdapter.setEmptyView(R.layout.adapter_scan_goods_empty_view);

//		recommendGoodsAdapter = new RecommendGoodsAdapter(new ArrayList<>(ShoppingCart.getCartNow().values()));
//		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
//		linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
//		recommendList.setAdapter(recommendGoodsAdapter);
//		recommendList.setLayoutManager(linearLayoutManager);

		ShoppingCart.addGoodsChangeListener(goodsChangeListener);
		setTotal();
	}

	/**
	 * 购物车添加商品监听
	 */
	private ShoppingCart.OnGoodsChangeListener goodsChangeListener = (changeNum, item) -> {
		logger.info("add goods");
		if (isAdded()){
			activity.runOnUiThread(()->{
				setTotal();
				goodsListAdapter.updateItem(item);
//				recommendGoodsAdapter.addData(item);
			});
		}
	};

	/**
	 * 总数量修改
	 */
	public void setTotal(){
		if (ShoppingCart.getTotalNum() > 0){
			goBill.setOnClickListener(this);
			goBill.setBackgroundResource(R.drawable.circle_corner_btn_go_bill);
		}else {
			goBill.setOnClickListener(null);
			goBill.setBackgroundResource(R.drawable.circle_corner_btn_go_bill_disable);
		}
		totalNum.setText(String.format("%s %s",
				getString(R.string.label_scan_goods_goods_num_total),
				NumUtils.remain2NumWithoutZero(ShoppingCart.getTotalNum())));
		setTotalAmount();
	}

	/**
	 * 总价格修改
	 */
	public void setTotalAmount(){
		double totalPrice = ShoppingCart.getTotalPrice();
		totalPrice -= ActivityCenter.getPriceBreakDiscount(ShoppingCart.getCartItems());
		SpannableString totalAmountStr =
				new SpannableString(
						getString(R.string.toollibrary_label_concurrency_sign) + NumUtils.remain2NumWithoutZero(totalPrice));
		if (totalPrice > 0){
			totalAmountStr.setSpan(new AbsoluteSizeSpan(DipUtil.sp2px(MyApplication.getContext(), 15f)),
					0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
			totalAmountStr.setSpan(new AbsoluteSizeSpan(DipUtil.sp2px(MyApplication.getContext(), 20f)),
					1, 1 + NumUtils.getIntegerPart(totalPrice).length(),
					Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
			totalAmountStr.setSpan(new AbsoluteSizeSpan(DipUtil.sp2px(MyApplication.getContext(), 15f)),
					NumUtils.getIntegerPart(totalPrice).length() + 1, totalAmountStr.length(),
					Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		}
		totalAmount.setText(totalAmountStr);
	}

	public void onActivityInfoChanged(ActivityInfoData activityInfoData){
		if (goodsListAdapter != null && ActivityCenter.getActivityInfoData() != null){
			activity.runOnUiThread(()-> goodsListAdapter.notifyDataSetChanged());
		}
	}

	/**
	 * 会员状态变化
	 * @param isMember 是否为会员
	 * @param memberInfoData 会员信息
	 */
	public void onMemberStateChanged(boolean isMember, MemberInfoData memberInfoData){
		ShoppingCart.setIsMember(isMember);
		goodsListAdapter.notifyDataSetChanged();
		setTotal();
	}

	/**
	 * 清空数据
	 */
	public void clearCart(){
		activity.runOnUiThread(()-> goodsListAdapter.setNewData(new ArrayList<>()));
	}

	/**
	 * 倒计时结束,
	 */
	public void onCountFinish() {
	}

	/**
	 * 扫码结束
	 * @param code 码
	 */
	public void onInputFinish(String code){
		this.code = code;
		getPresenter().getGoods(code);
	}

	@Override
	public void onGetGoodsSuccess(GoodsInfoList.GoodsDetailData data) {
		if (data != null && data.getStatus() != null && data.getStatus() == 0){
			if (!ShoppingCart.contains(data)){
				ShoppingCart.addToShoppingCart(new ShoppingCart.ShoppingCartItem(data, 1));
			}else {
				ShoppingCart.addToShoppingCart(new ShoppingCart.ShoppingCartItem(data, 1));
			}
		}else {
			showNotFoundDialog();
		}
	}

	@Override
	public void onGetGoodsFail(Throwable throwable) {
		showNotFoundDialog();
	}

	private void showNotFoundDialog(){
		NormalDialogFragment fragment = NormalDialogFragment.newInstance(NormalDialogFragment.ONLY_MSG);
		fragment.setMsg(activity.getString(R.string.dialog_scan_goods_not_found_format_str, code))
				.setSingleBtn(activity.getString(R.string.toolibrary_btn_confirm), v -> {
					fragment.dismiss();
				});
		CountDownDialog countDownDialog = CountDownDialog.newInstance(fragment, activity);
		countDownDialog.setTitle(activity.getString(R.string.dialog_scan_goods_not_found_goods));
		countDownDialog.show(getFragmentManager(), "GOODS_NOT_FOUND");
	}

	private boolean gotoBill = true;
	private void goBill(){
		if (ShoppingCart.getCartNow().isEmpty()){
			showToast(R.string.tip_no_goods_in_cart);
			return;
		}
		gotoBill = true;
		toPay();
		//显示会员可便宜xxx的对话框，目前是说不要了
//		double memberDiscountPirce = ShoppingCart.getMemberDiscountPrice();
//		//没有登录会员且会员价便宜
//		if (memberDiscountPirce > 0 && MemberCenter.getMemberInfoData() == null){
//			NormalDialogFragment fragment = NormalDialogFragment.newInstance(NormalDialogFragment.MSG_BOTTOM_OF_IMG);
//			fragment.setMsg(activity.getString(R.string.label_scan_goods_dialog_member_can_get_discount,
//					NumUtils.remain2NumWithoutZero(ShoppingCart.getMemberDiscountPrice())))
//					.setImg(R.mipmap.pop_member_discount)
//					.setLeftBtn(activity.getString(R.string.label_scan_goods_btn_already_are_member), v -> {
//						gotoBill = false;
//						fragment.dismiss();
//						activity.showInputMemberDialog();
//					})
//					.setRightBtn(activity.getString(R.string.label_scan_goods_btn_go_register), v2 -> {
//						gotoBill = false;
//						fragment.dismiss();
////						startActivity(new Intent(activity, AddMemberActivity.class));
//						startActivity(new Intent(activity, AddMemberByCodeActivity.class));
//					});
//			CountDownDialog dialog = CountDownDialog.newInstance(fragment,activity);
//			dialog.show(getFragmentManager(), "MEMBER_DISCOUNT");
//			dialog.setTitle(activity.getString(R.string.label_scan_goods_dialog_title_member));
//			dialog.setDismissListener(dialog1 -> {
//				if (!dialog.isCountFinish() && gotoBill){
//					toPay();
//				}
//			});
//		}else {
//			toPay();
//		}
	}

	private void toPay(){
		Intent intent = new Intent(activity, BillMVPActivity.class);
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.goBill:
				goBill();
				break;
			case R.id.goSearch:
				startActivity(new Intent(activity, SearchGoodsActivity.class));
				break;
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
