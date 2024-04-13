package com.dyc.order.cashier.contact;

import android.graphics.Bitmap;

import com.dyc.order.cashier.constant.MessageField;
import com.dyc.order.cashier.constant.ServerAddress;
import com.dyc.order.cashier.base.MyApplication;
import com.dyc.order.cashier.base.iml.BaseContact;
import com.dyc.order.cashier.data.local.DataCache;
import com.dyc.order.cashier.data.fields.AddOrChangeMemberFields;
import com.dyc.order.cashier.data.response.FaceConfirmData;
import com.dyc.order.cashier.data.response.MemberInfoData;
import com.dyc.order.cashier.data.response.MemberLevelData;
import com.dyc.administrator.toollibrary.utils.MLogger;
import com.google.gson.JsonParser;
import com.rxjava.rxlife.RxLife;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import androidx.lifecycle.LifecycleOwner;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.utils.GsonUtil;

import static android.content.Context.MODE_PRIVATE;

/**
 * func:
 * author:丁语成 on 2020/3/20 17:21
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public interface MemberContact {
	interface MemberModel extends BaseContact.BaseModel, CouponContact.CouponModel {
		MLogger logger =MLogger.getLogger(MemberModel.class);
		String TEMP_PIC_NAME = "bitmap.jpg";

		/**
		 * 会员等级
		 * @param pageSize
		 * @return
		 */
		default Observable<MemberLevelData> getMemberLevels(int pageSize){
			return RxHttp.get(ServerAddress.GET_MEMBER_LEVEL_LIST)
					.setHeader(MessageField.AUTHORIZATION, DataCache.getLoginInfoData().getToken())
					.add(MessageField.PAGE_NUM, 1)
					.add(MessageField.PAGE_SIZE, pageSize)
					.asResponse(MemberLevelData.class)
					.subscribeOn(Schedulers.newThread())
					.observeOn(AndroidSchedulers.mainThread());
		}

		/**
		 * 上传会员人脸
		 * @param picture 人脸图
		 * @param memberId 会员ID
		 * @return
		 * @throws FileNotFoundException
		 */
		default Observable<String> uploadMemberFace(Bitmap picture, int memberId) throws FileNotFoundException {
			FileOutputStream outputStream;
			outputStream = MyApplication.getContext().openFileOutput(TEMP_PIC_NAME, MODE_PRIVATE);
			picture.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

			return RxHttp.postForm(ServerAddress.UPLOAD_MEMBER_FACE_PIC_ADDRESS)
					.setHeader(MessageField.AUTHORIZATION, DataCache.getLoginInfoData().getToken())
					.addFile(MessageField.FILE, new File(MyApplication.getContext().getFilesDir(), TEMP_PIC_NAME))
					.add(MessageField.MEMBER_ID, memberId)
					.asResponse(String.class)
					.subscribeOn(Schedulers.newThread())
					.observeOn(AndroidSchedulers.mainThread());
		}

		/**
		 * 通过人脸图片取会员信息
		 * @param picture 会员人脸图片
		 * @return
		 * @throws FileNotFoundException
		 */
		default Observable<List<FaceConfirmData>> getMemberByFace(Bitmap picture) throws FileNotFoundException {
			FileOutputStream outputStream = null;
			outputStream = MyApplication.getContext().openFileOutput(TEMP_PIC_NAME, MODE_PRIVATE);
			picture.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

			return RxHttp.postForm(ServerAddress.GET_MEMBER_BY_FACE_PIC_ADDRESS)
					.setHeader(MessageField.AUTHORIZATION, DataCache.getLoginInfoData().getToken())
					.addFile(MessageField.FILE, new File(MyApplication.getContext().getFilesDir(), TEMP_PIC_NAME))
					.asResponseList(FaceConfirmData.class)
					.subscribeOn(Schedulers.newThread())
					.observeOn(AndroidSchedulers.mainThread());
		}

		/**
		 * 精确查询会员信息
		 * @param identity 会员标识
		 * @return
		 */
		default Observable<MemberInfoData> getMemberInfo(String identity) {
			return RxHttp.get(ServerAddress.QUERY_MEMBER_INFO_EXACT + identity)
					.setHeader(MessageField.AUTHORIZATION, DataCache.getLoginInfoData().getToken())
					.asResponse(MemberInfoData.class)
					.subscribeOn(Schedulers.newThread())
					.observeOn(AndroidSchedulers.mainThread());
		}

		/**
		 * 通过ID查询会员信息
		 * @param id 会员标识
		 * @return
		 */
		default Observable<MemberInfoData> getMemberInfoById(int id) {
			return RxHttp.get(ServerAddress.QUERY_MEMBER_INFO_BY_ID + id)
					.setHeader(MessageField.AUTHORIZATION, DataCache.getLoginInfoData().getToken())
					.asResponse(MemberInfoData.class)
					.subscribeOn(Schedulers.newThread())
					.observeOn(AndroidSchedulers.mainThread());
		}

		/**
		 * 新增会员
		 * @param fields 会员域
		 * @return
		 */
		default Observable<Integer> addMember(AddOrChangeMemberFields fields) {
			return requestChangeOrAdd(fields, ServerAddress.ADD_MEMBER);
		}

		/**
		 * 增加或修改会员
		 * @param fields 会员信息对象
		 * @param path 服务器地址
		 * @return
		 */
		default Observable<Integer> requestChangeOrAdd(AddOrChangeMemberFields fields, String path){
			return RxHttp.postJson(path)
					.setHeader(MessageField.AUTHORIZATION, DataCache.getLoginInfoData().getToken())
					.addAll(new JsonParser().parse(GsonUtil.toJson(fields)).getAsJsonObject())
					.asResponse(Integer.class)
					.subscribeOn(Schedulers.newThread())
					.observeOn(AndroidSchedulers.mainThread());
		}

		/**
		 * 生成一个会员卡号
		 * @return
		 */
		default Observable<String> generateCardNo() {
			return RxHttp.get(ServerAddress.GENERATE_MEMBER_CARD_NO)
					.setHeader(MessageField.AUTHORIZATION, DataCache.getLoginInfoData().getToken())
					.asResponse(String.class)
					.subscribeOn(Schedulers.newThread())
					.observeOn(AndroidSchedulers.mainThread());
		}


	}

	interface MemberView extends BaseContact.BaseView, CouponContact.CouponView{
		default void getMemberLevelsSuccess(MemberLevelData levelData){}
		default void getMemberLevelsFail(Throwable throwable){}
		default void uploadMemberFaceSuccess(String res){}
		default void uploadMemberFaceFail(Throwable throwable){}
		default void getMemberByFaceSuccess(FaceConfirmData faceConfirmData){}
		default void getMemberByFaceFail(Throwable throwable){}
		default void getMemberSuccess(MemberInfoData memberInfoData){}
		default void getMemberFail(Throwable throwable){}
		default void getMemberByIdSuccess(MemberInfoData memberInfoData){}
		default void getMemberByIdFail(Throwable throwable){}
		default void addMemberSuccess(Integer id){}
		default void addMemberFail(Throwable throwable){}
		default void onGetMemberCardNo(String code){}
		default void getMemberCardNoFail(Throwable throwable){}

	}

	abstract class MemberPresent<M extends MemberModel, V extends MemberView> extends BaseContact.BasePresent<M, V>{
		public final static int PAGE_SIZE = 30;
		private CouponContact.CouponPresent<M,V> couponPresent;

		@Override
		public void afterInit() {
			super.afterInit();
			logger.info("优惠券Present初始化");
			couponPresent = new CouponContact.CouponPresent<M, V>() {
			};
			couponPresent.registerModel(getModel());
			couponPresent.registerView(getView());
			couponPresent.afterInit();
		}

		public void getMemberLevels(){
			getModel().getMemberLevels(PAGE_SIZE)
					.as(RxLife.as((LifecycleOwner) getView().getActivity()))
					.subscribe(levelData -> {
						logger.info("success get level data:" + levelData);
						getView().getMemberLevelsSuccess(levelData);
					}, throwable -> {
						logger.info("fail get level data:");
						throwable.printStackTrace();
						getView().getMemberLevelsFail(throwable);
					});
		}

		public void uploadMemberFace(Bitmap picture, int memberId){
			try {
				getModel().uploadMemberFace(picture, memberId)
						.as(RxLife.as((LifecycleOwner) getView().getActivity()))
						.subscribe(res -> {
							logger.info("success pos member face:" + res);
							getView().uploadMemberFaceSuccess(res);
						}, throwable -> getView().uploadMemberFaceFail(throwable));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				getView().uploadMemberFaceFail(e);
			}
		}

		public void getMemberByFace(Bitmap picture){
			try {
				getModel().getMemberByFace(picture)
						.as(RxLife.as((LifecycleOwner) getView().getActivity()))
						.subscribe(faceConfirmData -> {
							logger.info("success get member by face:" + faceConfirmData);
							getView().getMemberByFaceSuccess(faceConfirmData.get(0));
						}, throwable -> getView().getMemberByFaceFail(throwable));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				getView().getMemberByFaceFail(e);
			}
		}

		public void addMember(AddOrChangeMemberFields fields) {
			getModel().addMember(fields)
					.as(RxLife.as((LifecycleOwner) getView().getActivity()))
					.subscribe(id -> {
						logger.info("success add member:" + (id == null ? "null" : id));
						getView().addMemberSuccess(id);
					}, throwable -> getView().addMemberFail(throwable));
		}

		public void generateCardNo(){
			getModel().generateCardNo()
					.as(RxLife.as((LifecycleOwner) getView().getActivity()))
					.subscribe(str -> {
						logger.info("success get card code:" + str);
						getView().onGetMemberCardNo(str);
					}, throwable -> getView().getMemberCardNoFail(throwable));
		}

		public void getMember(String phone){
			getModel().getMemberInfo(phone)
					.as(RxLife.as((LifecycleOwner) getView().getActivity()))
					.subscribe(memberInfoData -> {
						logger.info("success get member info:" + memberInfoData.getUsername());
						getView().getMemberSuccess(memberInfoData);
					}, throwable -> {
						logger.warn("fail get member info:" + throwable);
						throwable.printStackTrace();
						getView().getMemberFail(throwable);
					});
		}

		public void getMemberById(int id){
			getModel().getMemberInfoById(id)
					.as(RxLife.as((LifecycleOwner) getView().getActivity()))
					.subscribe(memberInfoData -> {
						logger.info("success get member by id info:" + memberInfoData.getUsername());
						getView().getMemberByIdSuccess(memberInfoData);
					}, throwable -> {
						logger.warn("fail get member by id info:" + throwable);
						throwable.printStackTrace();
						getView().getMemberByIdFail(throwable);
					});
		}

		public void getCoupon(double amount, String phone, int[] productIds){
			couponPresent.getCoupon(amount, phone, productIds);
		}

		public void getAllCoupon(String phone, String status){
			couponPresent.getAllCoupon(phone, status);
		}
	}
}
