package com.dyc.order.cashier.mvp.activity.member;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dyc.order.cashier.constant.ActivityOperationField;
import com.dyc.order.cashier.contact.RegisterMemberContact;
import com.dyc.order.cashier.data.local.DataCache;
import com.dyc.order.cashier.data.local.MemberCenter;
import com.dyc.order.cashier.data.response.MemberInfoData;
import com.dyc.order.cashier.mvp.activity.face.FaceCaptureByUvcActivity;
import com.dyc.administrator.toollibrary.utils.DipUtil;
import com.dyc.order.cashier.R;
import com.dyc.order.cashier.mvp.activity.base.BaseCountDownActivity;
import com.centerm.dev.error.DeviceBaseException;
import com.centerm.dev.facerecog.FaceRecognizeManager;

import java.util.Timer;
import java.util.TimerTask;

import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

/**
 * func:
 * author:丁语成 on 2020/6/10 15:06
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class AddMemberByCodeActivity2 extends BaseCountDownActivity<
        RegisterMemberContact.RegisterMemberModel,
		RegisterMemberContact.RegisterMemberView,
		RegisterMemberContact.RegisterMemberPresent>
		implements RegisterMemberContact.RegisterMemberView {
	public static long MEMBER_REGISTER_COUNT_DOWN_TIMES = 300;
	private LinearLayout leftView;
	private TextView countDownStr;
	private TextView registerMember;
	private ImageView code;
	private Button finishRegister;
	private MemberInfoData memberInfoData;
	private String identifier;
	private boolean loginState;
	private FaceRecognizeManager faceRecognizeManager;
	private boolean isRegister;

	@Override
	public int getLayoutId() {
		return R.layout.activity_add_member_by_code;
	}

	@Override
	public void onInitView(View view) {
		showActionBar(false);
		leftView = findViewById(R.id.leftView);
		countDownStr = findViewById(R.id.countDownStr);
		registerMember = findViewById(R.id.registerMember);
		code = findViewById(R.id.code);
		finishRegister = findViewById(R.id.finishRegister);
		init();
	}

	private void init(){
		startCountDown(MEMBER_REGISTER_COUNT_DOWN_TIMES, 0, BASE_COUNT_PERIOD);
		leftView.setOnClickListener(v -> finish());
		finishRegister.setOnClickListener(registerClickListener);

		isRegister = getIntent().getBooleanExtra(ActivityOperationField.SCAN_CODE_REGISTER, false);
		if (!isRegister){
			registerMember.setText(R.string.label_register_member_member_login);
			finishRegister.setVisibility(View.INVISIBLE);
		}
	}

	private View.OnClickListener registerClickListener = v->{
		finishRegister.setOnClickListener(null);
		if (TextUtils.isEmpty(identifier)) {
			showToast(R.string.label_register_member_did_not_get_address);
			getPresenter().getRegisteAddress();
		}else if (loginState){
			if (!memberInfoData.isIdentified() && memberInfoData.getNew() != null && memberInfoData.getNew()){
				Timer timer = new Timer();
				timer.schedule(new TimerTask() {
					@Override
					public void run() {
						DataCache.addData(ActivityOperationField.MEMBER_INFO_CHANGED, true);
						startActivity(new Intent(AddMemberByCodeActivity2.this, FaceCaptureByUvcActivity.class));
						finish();
					}
				}, 1000);
			}
		}else {
			showToast(R.string.label_register_member_did_not_register_success);
		}
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				finishRegister.setOnClickListener(registerClickListener);
			}
		}, 1000);
	};

	@Override
	public void doAfterInitView(View v) {
		super.doAfterInitView(v);
		getPresenter().getRegisteAddress();
		try {
			faceRecognizeManager = FaceRecognizeManager.getManager(this);
			faceRecognizeManager.releaseCamera();
		} catch (DeviceBaseException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void getRegisterAddressSuccess(String address) {
		identifier = address.substring(address.lastIndexOf("/") + 1);
		code.setImageBitmap(QRCodeEncoder.syncEncodeQRCode(
				address, DipUtil.dip2px(this, 162),
				Color.parseColor("#000000")));
		getPresenter().checkRegisterState(identifier);
	}

	@Override
	public void getRegisterAddressFail(Throwable throwable) {
		identifier = null;
		getPresenter().onError(throwable);
	}

	@Override
	public void onRegisterSuccess(MemberInfoData memberInfoData) {
		this.memberInfoData = memberInfoData;
		MemberCenter.setMemberInfoData(memberInfoData);
		if (memberInfoData.getNew() != null){
			logger.info("新会员：" + memberInfoData.getNew());
			if (!memberInfoData.getNew()){
				//老会员
				DataCache.addData(ActivityOperationField.MEMBER_INFO_CHANGED, true);
				finish();
			}else {
				finishRegister.setVisibility(View.VISIBLE);
//				Timer timer = new Timer();
//				timer.schedule(new TimerTask() {
//					@Override
//					public void run() {
//						startActivity(new Intent(AddMemberByCodeActivity2.this, FaceCaptureByUvcActivity.class));
//						finish();
//					}
//				}, 1000);
			}
		}else {
			logger.info("非新会员");
			DataCache.addData(ActivityOperationField.MEMBER_INFO_CHANGED, true);
			finish();
		}
		loginState = true;
	}

	@Override
	public void onRegisterFail(Throwable throwable) {
		identifier = null;
		memberInfoData = null;
		loginState = false;
		//取过一个地址
		getPresenter().getRegisteAddress();
	}

	@Override
	protected void onCountDown(long countTime) {
		super.onCountDown(countTime);
		runOnUiThread(()->countDownStr.setText(getFormatCountDownStr(countTime)));
	}

	@Override
	public RegisterMemberContact.RegisterMemberModel initM() {
		return new RegisterMemberContact.RegisterMemberModel() {
		};
	}

	@Override
	public RegisterMemberContact.RegisterMemberPresent initP() {
		return new RegisterMemberContact.RegisterMemberPresent(){
		};
	}
}

