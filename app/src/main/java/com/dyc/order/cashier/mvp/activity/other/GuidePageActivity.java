package com.dyc.order.cashier.mvp.activity.other;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.dyc.order.cashier.R;
import com.dyc.order.cashier.data.local.DataCache;
import com.dyc.order.cashier.data.local.MemberCenter;
import com.dyc.order.cashier.data.local.ShoppingCart;
import com.dyc.order.cashier.mvp.activity.base.BaseDesktopActivity;
import com.centerm.dev.error.DeviceBaseException;
import com.centerm.dev.facerecog.FaceRecognizeManager;
import com.dyc.simplemvplibrary.Model;
import com.dyc.simplemvplibrary.Presenter;

public class GuidePageActivity extends BaseDesktopActivity {
	private TextView welcomeStr;
	private View mask;
	private FaceRecognizeManager faceRecognizeManager;
	private View.OnClickListener onClickListener = v -> {
		logger.info("click");
		startActivity(new Intent(GuidePageActivity.this,
				ShoppingCartActivity.class));
		mask.setOnClickListener(null);
	};

	@Override
	public int getLayoutId() {
		return R.layout.activity_guide_page;
	}

	@Override
	public void onInitView(View view) {
		showActionBar(false);
		welcomeStr = findViewById(R.id.welcomeStr);
		mask = findViewById(R.id.mask);
		if (DataCache.getLoginInfoData() != null &&
				DataCache.getLoginInfoData().getMerchantInfoDTO() != null){
			welcomeStr.setText(getString(R.string.label_guide_page_welcome_str, DataCache.getLoginInfoData().getMerchantInfoDTO().getName()));
		}else {
			welcomeStr.setText(getString(R.string.label_guide_page_welcome_str, getString(R.string.label_guide_page_default_shop_name)));
		}
		try {
			faceRecognizeManager = FaceRecognizeManager.getManager(this);
			faceRecognizeManager.releaseCamera();
		} catch (DeviceBaseException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		mask.setOnClickListener(onClickListener);
		ShoppingCart.clearCartNow();
		ShoppingCart.setIsMember(false);
		MemberCenter.setMemberInfoData(null);
	}

	@Override
	public Model initM() {
		return null;
	}

	@Override
	public Presenter initP() {
		return null;
	}

	@Override
	public void onUserInteraction() {
		super.onUserInteraction();
	}
}
