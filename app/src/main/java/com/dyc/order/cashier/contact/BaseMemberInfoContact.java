package com.dyc.order.cashier.contact;

import android.graphics.Bitmap;

/**
 * func:
 * author:丁语成 on 2020/4/1 10:37
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public interface BaseMemberInfoContact {
	interface BaseMemberInfoModel extends BaseGetGoodsContact.BaseGetGoodsModel, MemberContact.MemberModel {
	}

	interface BaseMemberInfoView extends BaseGetGoodsContact.BaseGetGoodsView, MemberContact.MemberView {
	}

	abstract class BaseMemberInfoPresent<M extends BaseMemberInfoModel, V extends BaseMemberInfoView> extends BaseGetGoodsContact.BaseGetGoodsPresent<M,V>{
		private MemberContact.MemberPresent<M,V> present;

		@Override
		public void afterInit() {
			super.afterInit();
			present = new MemberContact.MemberPresent<M,V>() {
			};
			present.registerModel(getModel());
			present.registerView(getView());
			present.afterInit();
		}

		public void getMemberLevels(){
			present.getMemberLevels();
		}

		public void getMember(String phone){
			present.getMember(phone);
		}

		public void getMemberByFace(Bitmap picture){
			present.getMemberByFace(picture);
		}

		public void getCounpon(double amount, String phone, int[] productIds){
			present.getCoupon(amount, phone, productIds);
		}

		public void getAllCoupon(String phone){
			present.getAllCoupon(phone, ""+0);
		}
	}
}
