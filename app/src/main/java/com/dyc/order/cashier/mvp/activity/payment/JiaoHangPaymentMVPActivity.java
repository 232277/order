package com.dyc.order.cashier.mvp.activity.payment;

import android.content.Intent;
import android.view.View;

import com.dyc.order.cashier.R;
import com.dyc.order.cashier.constant.ActivityOperationField;
import com.dyc.order.cashier.contact.ThirdPartPayContact;
import com.dyc.order.cashier.mvp.activity.base.BaseJiaoHangThirdPartPayActivity;

import androidx.annotation.Nullable;

/**
 * func:
 * author:丁语成 on 2020/6/29 15:48
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class JiaoHangPaymentMVPActivity extends BaseJiaoHangThirdPartPayActivity<
        ThirdPartPayContact.ThirdPartPayModel,
		ThirdPartPayContact.ThirdPartPayView,
		ThirdPartPayContact.ThirdPartPayPresent
		> implements ThirdPartPayContact.ThirdPartPayView {

	@Override
	public void onInitView(View view) {
		super.onInitView(view);
//		if (memberInfoData == null){
//			logger.info("非会员，不显示储值");
//			memberCardPay.setVisibility(View.INVISIBLE);
//		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		if (requestCode == RECHARGE_SCAN){
			if (resultCode == RESULT_OK){
				if (data != null){
					memberStoreCode = data.getStringExtra(ActivityOperationField.MEMBER_STORE_CODE);
					if (memberStoreCode != null){
						memberCardPay();
					}
				}else {
					showToast(R.string.label_member_store_fail_decode);
				}
			}
		}else {
			super.onActivityResult(requestCode, resultCode, data);
		}
	}

	@Override
	public ThirdPartPayContact.ThirdPartPayModel initM() {
		return new ThirdPartPayContact.ThirdPartPayModel() {
		};
	}

	@Override
	public ThirdPartPayContact.ThirdPartPayPresent initP() {
		return new ThirdPartPayContact.ThirdPartPayPresent() {
		};
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}

