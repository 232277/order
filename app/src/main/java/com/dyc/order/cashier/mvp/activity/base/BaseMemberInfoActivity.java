package com.dyc.order.cashier.mvp.activity.base;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dyc.order.cashier.constant.ActivityOperationField;
import com.dyc.order.cashier.constant.ServerAddress;
import com.dyc.order.cashier.contact.BaseMemberInfoContact;
import com.dyc.order.cashier.function.FaceDectTask;
import com.dyc.order.cashier.function.UvcCameraProvider;
import com.dyc.order.cashier.view.CountDownDialog;
import com.dyc.order.cashier.view.CouponDialog2;
import com.dyc.order.cashier.view.MemberSignOnDialog;
import com.dyc.administrator.toollibrary.utils.NumUtils;
import com.dyc.order.cashier.R;
import com.dyc.order.cashier.mvp.activity.member.AddMemberByCodeActivity2;
import com.dyc.order.cashier.mvp.activity.payment.BillMVPActivity;
import com.dyc.order.cashier.mvp.activity.other.SearchGoodsActivity;
import com.dyc.order.cashier.data.local.DataCache;
import com.dyc.order.cashier.data.local.MemberCenter;
import com.dyc.order.cashier.data.local.PictureCache;
import com.dyc.order.cashier.data.local.ShoppingCart;
import com.dyc.order.cashier.data.fields.AddOrChangeMemberFields;
import com.dyc.order.cashier.data.response.CouponListData;
import com.dyc.order.cashier.data.response.FaceConfirmData;
import com.dyc.order.cashier.data.response.MemberInfoData;
import com.dyc.order.cashier.data.response.MemberLevelData;
import com.dyc.order.cashier.mvp.fragment.InputMemberFragment;
import com.dyc.order.cashier.mvp.fragment.MemberFaceFragment;
import com.dyc.order.cashier.mvp.fragment.NormalDialogFragment;
import com.dyc.administrator.toollibrary.utils.DipUtil;
import com.dyc.administrator.toollibrary.utils.MLogger;
import com.dyc.administrator.toollibrary.view.NumKeyBoard;
import com.serenegiant.usb.widget.CameraViewInterface;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * func:
 * author:丁语成 on 2020/4/1 8:56
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public abstract class BaseMemberInfoActivity<
		M extends BaseMemberInfoContact.BaseMemberInfoModel,
		V extends BaseMemberInfoContact.BaseMemberInfoView,
		P extends BaseMemberInfoContact.BaseMemberInfoPresent
		>
		extends BaseGoodsListActivity<M, V, P>
		implements BaseMemberInfoContact.BaseMemberInfoView, View.OnClickListener{
	public MLogger logger = MLogger.getLogger(this.getClass());
	//会员
	public ConstraintLayout memberFunLayout;
	public ImageView memberAvatar;
	public TextView memberFun;
	public TextView memberName;
	public TextView memberLevel;
	public TextView memberPhone;
	public View goSearch;
	public TextView countDownStr;
	protected boolean detectFace = true;
	//相机
	public UvcCameraProvider uvcCameraProvider;
	public TextureView previewView;
	public long lastTime = 0;
	public Bitmap facePic;
	//dialog
	public CountDownDialog memberDialog;
	private NumKeyBoard numKeyBoard;
	private CountDownDialog faceDialog;
	public FaceConfirmData faceConfirmData;
	//优惠券
	protected boolean showCoupon;
	protected CouponListData enableCoupon;
	protected CouponDialog2 couponDialog;
	public CouponListData.CouponData couponInfo;

	protected ExecutorService executor;
	protected Timer timer;
	public Fragment fragment;

	@Override
	public void onInitView(View view) {
		super.onInitView(view);
		memberFunLayout = findViewById(R.id.memberFunLayout);
		memberAvatar = findViewById(R.id.memberAvatar);
		memberFun = findViewById(R.id.registerMember);
		memberName = findViewById(R.id.memberName);
		memberLevel = findViewById(R.id.levelLabel);
		memberPhone = findViewById(R.id.memberPhone);
		goSearch = findViewById(R.id.goSearch);
		previewView = findViewById(R.id.textureView);
		countDownStr = findViewById(R.id.countDownStr);
		initData();
	}

	@Override
	public void doAfterInitView(View v) {
		super.doAfterInitView(v);
		//取会员等级信息
		getPresenter().getMemberLevels();
	}

	private void initData(){
		goSearch.setOnClickListener(this);
		memberFunLayout.setOnClickListener(this);
		executor = Executors.newCachedThreadPool();

	}

	public void showCountDownStr(boolean show){
		countDownStr.setVisibility(show ? View.VISIBLE : View.GONE);
		goSearch.setVisibility(show ? View.VISIBLE : View.GONE);
	}

	@Override
	protected void onCountDown(long countTime) {
		super.onCountDown(countTime);
		SpannableString time = new SpannableString(String.format(Locale.getDefault(), "(%ds)", countTime));
		time.setSpan(new ForegroundColorSpan(
						getResources().getColor(R.color.normal_item_text_color_red, null)),
				1, time.length()-2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		runOnUiThread(()->countDownStr.setText(time));
	}

	@Override
	public void restartCountDown() {
		super.restartCountDown();
		SpannableString time = new SpannableString(String.format(Locale.getDefault(), "(%ds)", getTotalCount()));
		time.setSpan(new ForegroundColorSpan(
						getResources().getColor(R.color.normal_item_text_color_red, null)),
				1, time.length()-2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		runOnUiThread(()->countDownStr.setText(time));
	}

	public void setContentFragment(Fragment fragment){
		logger.info("setContentFragment");
		FragmentManager fragmentManager = getSupportFragmentManager();
		if (fragmentManager != null){
			FragmentTransaction tx = fragmentManager.beginTransaction();
			tx.replace(R.id.contentFrag, fragment);
			tx.commit();
			this.fragment = fragment;
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		hideDialogs();
		//若添加了会员则查询一次确认会员信息
		if (DataCache.contains(ActivityOperationField.ADD_MEMBER)
				&& DataCache.get(ActivityOperationField.ADD_MEMBER) != null){
			showToast(R.string.dialog_pls_wait);
			getPresenter().getMember(((AddOrChangeMemberFields)
					DataCache.get(ActivityOperationField.ADD_MEMBER)).getPhoneNumber());
		}
		if (MemberCenter.getMemberInfoData() != null){
			showMemberInfo(true);
		}else {
			showMemberInfo(false);
		}
	}

	protected void hideDialogs(){
		if (numKeyBoard != null){
			numKeyBoard.dismiss();
		}
		if (memberDialog != null){
			memberDialog.dismiss();
		}
		if (faceDialog != null){
			faceDialog.dismiss();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.memberFunLayout:
				showInputMemberDialog();
				break;
			case R.id.goSearch:
				startActivity(new Intent(this, SearchGoodsActivity.class));
				break;
		}
	}

	public void initCamera(){
		logger.info("initCamera");
		executor.execute(()->{
			try {
				uvcCameraProvider = new UvcCameraProvider(this, (CameraViewInterface) previewView);
				uvcCameraProvider.setUvcCameraListener(new UvcCameraProvider.UvcCameraListener() {
					@Override
					public void onPreview(ByteBuffer byteBuffer) {
						if ((System.currentTimeMillis() - lastTime > 2000 || lastTime == 0) && detectFace){
							uvcCameraProvider.takePic(false);
							lastTime = System.currentTimeMillis();
						}
					}

					@Override
					public void onGetPic(Bitmap bitmap) {
						//显示
						runOnUiThread(()->((ImageView)findViewById(R.id.test)).setImageBitmap(bitmap));
						//人脸检测
						List<Bitmap> bitmaps = FaceDectTask.cutFaces(bitmap, 1, false);
						if (bitmaps != null && !bitmaps.isEmpty()){
							logger.info("get face");
							detectFace = false;
							facePic = bitmap;
							getPresenter().getMemberByFace(bitmap);
						}else {
							logger.info("no face");
						}
					}
				});
				uvcCameraProvider.onResume();
			}catch (Exception e){
				e.printStackTrace();
			}
		});
	}

	public void startCamera(){
		startPreview();
	}

	public void startPreview(){
		//摄像机线程
		try {
			executor.execute(()->{
				if (uvcCameraProvider != null){
					uvcCameraProvider.startPreview();
				}
			});
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public void stopPreview(){
		logger.info("stop preview");
		if (uvcCameraProvider != null){
			uvcCameraProvider.stopPreview();
		}
	}

	@Override
	public void getMemberByFaceSuccess(FaceConfirmData faceConfirmData){
		logger.warn("succes get member by face");
		this.faceConfirmData = faceConfirmData;
		getPresenter().getMember(faceConfirmData.phoneNumber);
	}

	@Override
	public void getMemberByFaceFail(Throwable throwable) {
		logger.warn("got face but no member face");
		detectFace = true;
	}

	@Override
	public void getMemberSuccess(MemberInfoData memberInfoData) {
		if (faceConfirmData != null){
			faceConfirmData = null;
			showConfirmMemberFaceDialog(memberInfoData);
		}else {
			if (DataCache.contains(ActivityOperationField.ADD_MEMBER)){
				DataCache.removeData(ActivityOperationField.ADD_MEMBER);
//				showNotifyDialog(R.string.dialog_member_register_add_member_success, R.mipmap.icon_guide_hi);
			}
			memberDialog.dismiss();
			MemberCenter.setMemberInfoData(memberInfoData);
			showMemberInfo(true);
			MemberSignOnDialog memberSignOnDialog = new MemberSignOnDialog(this);
			memberSignOnDialog.show();
			memberSignOnDialog.setImg(R.mipmap.icon_member_shibie_hi_s);
			String str = getString(R.string.label_scan_goods_dialog_member_login_success_notice,
					memberInfoData.getUsername(), memberInfoData.getMerchantName());
			SpannableString spannableString = new SpannableString(str);
			spannableString.setSpan(new AbsoluteSizeSpan(DipUtil.sp2px(this, 17f)),
					0, memberInfoData.getUsername().length(),
					Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
			spannableString.setSpan(new AbsoluteSizeSpan(DipUtil.sp2px(this, 12f)),
					memberInfoData.getUsername().length(), spannableString.length(),
					Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
			memberSignOnDialog.setText(spannableString);
		}
	}

	@Override
	public void getMemberFail(Throwable throwable) {
		DataCache.removeData(ActivityOperationField.ADD_MEMBER);
		MemberCenter.setMemberInfoData(null);
		//普通getMember
		if (faceConfirmData == null){
			if (memberDialog != null){
				memberDialog.dismiss();
			}
			NormalDialogFragment fragment = NormalDialogFragment.newInstance(NormalDialogFragment.MSG_BOTTOM_OF_IMG);
			fragment.setMsg(getString(R.string.dialog_scan_goods_member_not_exist_go_register))
					.setImg(R.mipmap.pop_member_not)
					.setSingleBtn(getString(R.string.label_scan_goods_btn_go_register), v -> {
						Intent intent = new Intent(BaseMemberInfoActivity.this, AddMemberByCodeActivity2.class);
						intent.putExtra(ActivityOperationField.SCAN_CODE_REGISTER, true);
						startActivity(intent);						fragment.dismiss();
					});
			CountDownDialog dialog = CountDownDialog.newInstance(fragment, this);
			dialog.show(getSupportFragmentManager(), "GET_MEMBER_FAIL");
			dialog.setTitle(getString(R.string.dialog_scan_goods_member_not_exist));
		}else {
			//人脸识别后的getMember
			logger.info("start camera getMemberFail");
			detectFace = true;
			faceConfirmData = null;
		}
	}

	/**
	 * 人脸确认
	 */
	public void showConfirmMemberFaceDialog(MemberInfoData memberInfoData){
		try {
			logger.warn("show member face");
			if (memberDialog != null){
				hasConfirmed = true;
				memberDialog.dismiss();
			}
			MemberFaceFragment memberFaceFrag = MemberFaceFragment.newInstance(memberInfoData, facePic);
			memberFaceFrag.setOnConfirmListener((res, memberInfoData1) -> {
				if (res){
					//手机尾号正确，则修改会员状态
					MemberCenter.setMemberInfoData(memberInfoData1);
					showMemberInfo(true);
				}else {
					//手机尾号错误，显示确认错误对话框
					showConfirmMemberFailDialog();
				}
				memberFaceFrag.dismiss();
			});
			numKeyBoard = new NumKeyBoard(this, memberFaceFrag.getOnNumberInputListener());
			numKeyBoard.show();
			faceDialog = CountDownDialog.newInstance(memberFaceFrag, this);
			faceDialog.setCloseOnClick(v -> {
				faceDialog.dismiss();
			});
			faceDialog.setDismissListener(dialog -> {
				numKeyBoard.dismiss();
			});
			faceDialog.setCancelable(true);
			faceDialog.show(getSupportFragmentManager(), "MEMBER_FACE_CONFIRM");
		}catch (Exception e){
			//如果显示人脸确认框过程中跳转导致报错则重开摄像头
			e.printStackTrace();
			detectFace = true;
		}
	}

	/**
	 * 确认会员人脸失败
	 */
//	private boolean restartCamera = true;
	public void showConfirmMemberFailDialog(){
		NormalDialogFragment fragment = NormalDialogFragment.newInstance(NormalDialogFragment.MSG_BOTTOM_OF_IMG);
		fragment.setMsg(getString(R.string.label_scan_goods_dialog_phone_not_match_face))
				.setImg(R.mipmap.pop_member_mismatch)
				.setLeftBtn(getString(R.string.label_scan_goods_dialog_already_are_member_go_login), v -> {
					//登录
					fragment.dismiss();
					showInputMemberDialog();
				})
				.setRightBtn(getString(R.string.label_scan_goods_dialog_not_member_yet_blue_part), v -> {
					//注册
					fragment.dismiss();
//			startActivity(new Intent(BaseMemberInfoActivity.this, AddMemberActivity.class));
					startActivity(new Intent(BaseMemberInfoActivity.this, AddMemberByCodeActivity2.class));
				});
		CountDownDialog dialog = CountDownDialog.newInstance(fragment, this);
		dialog.setCloseOnClick(v -> detectFace = true);
		dialog.setDismissListener(i -> {
			detectFace = true;
		});
		dialog.show(getSupportFragmentManager(), "CONFIRM_MEMBER_FAIL");
		dialog.setTitle(getString(R.string.label_scan_goods_dialog_title_member_login));
	}

	public abstract void onMemberStateChanged(boolean isMember, MemberInfoData memberInfoData);

	/**
	 * 显示会员信息
	 * @param show 是否显示
	 */
	public void showMemberInfo(boolean show){
		MemberInfoData memberInfoData = MemberCenter.getMemberInfoData();
		if (show){
			if (!TextUtils.isEmpty(memberInfoData.getAvatar())){
				logger.info("头像地址：" + ServerAddress.AVATAR_ADDRESS + memberInfoData.getAvatar());
				PictureCache.setBitMapForImageView(ServerAddress.AVATAR_ADDRESS + memberInfoData.getAvatar(),
						memberAvatar);
			}
			String name = memberInfoData.getUsername();
			if (name.length() > 5){
				name = name.substring(0,5) + "...";
			}
			memberName.setText(name);
			memberLevel.setText(memberInfoData.getLevelName());
			memberPhone.setText(memberInfoData.getPhoneNumber());
			ShoppingCart.setIsMember(true);
		}else {
			detectFace = true;
			memberAvatar.setImageResource(R.mipmap.img_user_l);
			ShoppingCart.setIsMember(false);
			setDiscountAndPoints();
		}
		onMemberStateChanged(show, memberInfoData);
		memberFunLayout.setOnClickListener(show ? v -> showMemberDetailInfo() : this);
		memberFun.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
		memberName.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
		memberLevel.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
		memberPhone.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
	}

	/**
	 * 没会员时显示会员最低优惠或最低可获取积分
	 */
	protected void setDiscountAndPoints(){
		if (this instanceof BillMVPActivity){
			logger.info("结算页面");
			if(ShoppingCart.getMemberDiscountPrice() > 0){
				logger.info("会员优惠价格大于0");
//				logger.info("url type");
//				memberFun.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
//				memberFun.getPaint().setAntiAlias(true);//抗锯齿
				memberFun.setText(String.format(Locale.getDefault(),
						"%s%s",
						getString(R.string.label_scan_goods_member_register_or_login),
						getString(R.string.label_scan_goods_member_can_get_discount,
								NumUtils.remain2NumWithoutZero(
										ShoppingCart.getMemberDiscountPrice()
								))));
			}else {
				MemberLevelData.Level level = MemberCenter.getLowestLevel();
				if (level != null && level.obtainPoints != null && level.obtainPoints > 0){
					logger.info("会员可获取积分大于0");
//					memberFun.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
//					memberFun.getPaint().setAntiAlias(true);//抗锯齿
					memberFun.setText(String.format(Locale.getDefault(),
							"%s%s",
							getString(R.string.label_scan_goods_member_register_or_login),
							getString(R.string.label_scan_goods_member_can_get_points,
									NumUtils.remain2NumWithoutZero(
											MemberCenter.getLowestLevel().obtainPoints
									))));
				}else {
					logger.info("无优惠");
				}
			}
		}else {
			logger.info("非结算页面");
			memberFun.getPaint().setFlags(Paint.ANTI_ALIAS_FLAG);
		}
	}

	public abstract void showMemberDetailInfo();

	/**
	 * 会员手机号输入
	 */
	private boolean memberDissmiss = false;
	private boolean hasConfirmed = false;
	public void showInputMemberDialog(){
		memberFunLayout.setEnabled(false);
		memberDissmiss = false;
		pauseCountDown();
		detectFace = false;
		InputMemberFragment frag = InputMemberFragment.newInstance();
		frag.setOnConfirmListener(phone -> {
			hasConfirmed = true;
			getPresenter().getMember(phone);
		});
		memberDialog = CountDownDialog.newInstance(frag, this);
		NumKeyBoard numKeyBoard = new NumKeyBoard(this, frag.getOnNumberInputListener());
		numKeyBoard.show();
		memberDialog.show(getSupportFragmentManager(), "MEMBER_DIALOG");
		memberDialog.setContentLayout(frag);
		memberDialog.setTitle(getString(R.string.label_scan_goods_dialog_title_member));
		memberDialog.setCloseOnClick(v->{
			detectFace = true;
			memberDialog.dismiss();
		});
		memberDialog.setDismissListener(dialog -> {
			restartCountDown();
			memberFunLayout.setOnClickListener(this);
			if (!memberDissmiss){
				memberDissmiss = true;
				numKeyBoard.dismiss();
			}
			if (!hasConfirmed){
				detectFace = true;
			}else {
				hasConfirmed = false;
			}
		});
		numKeyBoard.setOnDismissListener(dialog -> {
			if (!memberDissmiss){
				memberDissmiss = true;
				memberDialog.dismiss();
			}
			memberFunLayout.setEnabled(true);
		});
	}

	@Override
	public void getMemberLevelsSuccess(MemberLevelData levelData) {
		logger.info("getMemberLevelsSuccess");
		MemberCenter.setMemberLevelData(levelData);
	}

	@Override
	public void getMemberLevelsFail(Throwable throwable) {
		logger.info("getMemberLevelsFail");
		MemberCenter.setMemberLevelData(null);
	}

	public void getCoupon(){
		getCoupon(true);
	}

	/**
	 * 获取优惠券
	 */
	public void getCoupon(boolean showCoupon){
		MemberInfoData memberInfoData = MemberCenter.getMemberInfoData();
		if (memberInfoData != null){
			this.showCoupon = showCoupon;
			logger.info("取可用优惠券");
			getPresenter().getCounpon(ShoppingCart.getTotalPrice(), memberInfoData.getPhoneNumber(), ShoppingCart.getCartGoodsId());
		}
	}

	@Override
	public void getCouponSuccess(CouponListData listData) {
		enableCoupon = listData;
		DataCache.addData(ActivityOperationField.USEFUL_COUPON, enableCoupon);
		onGetCoupon(listData);
	}

	private int canUseNum = 0;
	public void onGetCoupon(CouponListData listData){
		if (enableCoupon != null && showCoupon){
			showCoupons();
		}
		List<CouponListData.CouponData> datas;

		if (enableCoupon != null && enableCoupon.rows != null){
			datas = Arrays.asList(enableCoupon.rows);
			datas.forEach(coupon -> {
				if(coupon.ableToUse){
					++canUseNum;
				}
			});
		}
		setCouponDiscount(couponInfo, canUseNum);
		canUseNum = 0;
	}

	public abstract void setCouponDiscount(CouponListData.CouponData data, long total);

	/**
	 * 优惠券选择窗显示
	 */
	public void showCoupons(){
		pauseCountDown();
		MemberInfoData memberInfoData = MemberCenter.getMemberInfoData();
		if (couponDialog == null){
			couponDialog = new CouponDialog2(this, memberInfoData, enableCoupon, this);
		}
		couponDialog.show();
		couponDialog.setData(memberInfoData, enableCoupon);
		couponDialog.setOnDismissListener(dialog -> {
			restartCountDown();
		});
	}

	@Override
	public void getCouponFail(Throwable throwable) {
		restartCountDown();
		showToast(R.string.tip_get_member_enable_coupon_fail);
		logger.info("getCouponFail");
		DataCache.removeData(ActivityOperationField.USEFUL_COUPON);
	}

	@Override
	public void clearCart() {
		super.clearCart();
		ShoppingCart.setIsMember(false);
		MemberCenter.setMemberInfoData(null);
		showMemberInfo(false);
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (timer != null){
			timer.cancel();
		}
		if (uvcCameraProvider != null){
			uvcCameraProvider.release();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
