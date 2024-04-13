package com.dyc.order.cashier.contact;

import com.dyc.order.cashier.constant.MessageField;
import com.dyc.order.cashier.constant.ServerAddress;
import com.dyc.order.cashier.base.iml.BaseContact;
import com.dyc.order.cashier.data.local.DataCache;
import com.dyc.order.cashier.data.fields.GetGoodsField;
import com.dyc.order.cashier.data.response.GoodsInfoList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rxjava.rxlife.RxLife;

import java.util.Map;

import androidx.lifecycle.LifecycleOwner;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.utils.GsonUtil;

/**
 * func: 获取商品列表基类
 * author:丁语成 on 2020/3/2 13:36
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public interface BaseGetGoodsContact {
	interface BaseGetGoodsModel extends BaseContact.BaseModel, DiscountInfoContact.DiscountInfoModel {


		default Observable<GoodsInfoList.GoodsDetailData> getGoodsByLastSix(String lastSix){
			return RxHttp.get(ServerAddress.GOODS_DETAIL_BY_LAST_SIX_ADDRESS)
					.setHeader(MessageField.AUTHORIZATION, DataCache.getLoginInfoData().getToken())
					.add(MessageField.BARCODE, lastSix)
					.add(MessageField.MERCHANT_ID, DataCache.getLoginInfoData().getMerchantInfoDTO().getId())
					.asResponse(GoodsInfoList.GoodsDetailData.class)
					.subscribeOn(Schedulers.newThread())
					.observeOn(AndroidSchedulers.mainThread());
		}

		/**
		 * 普通的列表查询
		 * @param pageNum 页码
		 * @return
		 */
		default Observable<GoodsInfoList> getGoodsList(int pageNum){
			return getGoodsList(new GetGoodsField(pageNum, BaseGetGoodsPresent.getPageSize()));
		}

		/**
		 * 普通的列表查询
		 * @param fields 请求bean
		 * @return
		 */
		default Observable<GoodsInfoList> getGoodsList(GetGoodsField fields){
			return RxHttp.get(ServerAddress.GOODS_LIST_ADDRESS)
					.setHeader(MessageField.AUTHORIZATION, DataCache.getLoginInfoData().getToken())
					.addAll(new Gson().fromJson(GsonUtil.toJson(fields), new TypeToken<Map<String, String>>() { }.getType()))
					.add(MessageField.PAGE_SIZE, BaseGetGoodsPresent.getPageSize())
					.asResponse(GoodsInfoList.class)
					.subscribeOn(Schedulers.newThread())
					.observeOn(AndroidSchedulers.mainThread());
		}

		/**
		 * 指定商品信息
		 * @param id 商品id
		 * @return
		 */
		default Observable<GoodsInfoList.GoodsDetailData> getGoods(int id) {
			return RxHttp.get(ServerAddress.GOODS_DETAIL_BY_ID_ADDRESS + id)
					.setHeader(MessageField.AUTHORIZATION, DataCache.getLoginInfoData().getToken())
					.asResponse(GoodsInfoList.GoodsDetailData.class)
					.subscribeOn(Schedulers.newThread())
					.observeOn(AndroidSchedulers.mainThread());
		}

		/**
		 * 取具体商品，从后台取
		 * @param code 条码
		 * @return
		 */
		default Observable<GoodsInfoList.GoodsDetailData> getGoods(String code) {
			return RxHttp.get(ServerAddress.GET_GOODS_INFO_BACKWARD_ADDRESS)
					.setHeader(MessageField.AUTHORIZATION, DataCache.getLoginInfoData().getToken())
					.add(MessageField.BARCODE, code)
					.asResponse(GoodsInfoList.GoodsDetailData.class)
					.subscribeOn(Schedulers.newThread())
					.observeOn(AndroidSchedulers.mainThread());
		}
	}

	interface BaseGetGoodsView extends BaseContact.BaseView, DiscountInfoContact.DiscountInfoView {
		default void onGetGoodsSuccess(GoodsInfoList.GoodsDetailData data){}
		default void onGetGoodsFail(Throwable throwable){}
		default void onGetGoodsListSuccess(GoodsInfoList list){}
		default void onGetGoodsListFail(Throwable throwable){}
	}

	abstract class BaseGetGoodsPresent<M extends BaseGetGoodsModel, V extends BaseGetGoodsView> extends BaseContact.BasePresent<M ,V> {
		private DiscountInfoContact.DiscountInfoPresent<M,V> discountInfoPresent;

		@Override
		public void afterInit() {
			super.afterInit();
			discountInfoPresent = new DiscountInfoContact.DiscountInfoPresent<M, V>() {};
			discountInfoPresent.registerModel(getModel());
			discountInfoPresent.registerView(getView());
		}
		//每页商品数
		private static int pageSize = 30;
		private int total = 0;

		public void getPointConfigInfo(){discountInfoPresent.getPointsConfiInfo();}

		public void getActivityInfo(){
			discountInfoPresent.getActivityInfo();
		}

		public void getGoodsByLastSix(String lastSix){
			getModel().getGoodsByLastSix(lastSix)
					.as(RxLife.as((LifecycleOwner)getView().getActivity()))
					.subscribe(goodsDetail -> {
						logger.info("getGoodsByLastSix success");
						getView().onGetGoodsSuccess(goodsDetail);
					}, throwable -> {
						logger.info("getGoodsByLastSix fail");
						throwable.printStackTrace();
						getView().onGetGoodsListFail(throwable);
					});
		}

		/**
		 * 取商品列表
		 * @param sizeNow 当前已取得的商品数
		 */
		public void getGoodsList(int sizeNow) {
			logger.info("get goods: sizeNow: " + sizeNow);
			getGoodsList(sizeNow, new GetGoodsField());
		}

		/**
		 * 取商品列表
		 * @param sizeNow 当前已取得商品数
		 * @param words 条码或名称关键字，模糊匹配
		 */
		public void getGoodsList(int sizeNow, String words) {
			GetGoodsField field = new GetGoodsField();
			field.words = words;
			getGoodsList(sizeNow, field);
		}

		/**
		 * 取列表
		 * @param sizeNow 当前已取得商品数量
		 * @param field 请求bean
		 */
		public void getGoodsList(int sizeNow, GetGoodsField field) {
			logger.info("get goods: sizeNow: " + sizeNow);
			field.pageNum = sizeNow / pageSize + 1;
			field.pageSize = pageSize;
			getModel().getGoodsList(field)
					.as(RxLife.as((LifecycleOwner)getView().getActivity()))
					.subscribe(this::successGetGoodsList, throwable -> {
						getView().onGetGoodsListFail(throwable);
						onError(throwable);
					});
		}

		/**
		 * 根据id取具体商品
		 * @param id 商品id
		 */
		public void getGoods(int id) {
			getModel().getGoods(id)
					.as(RxLife.as((LifecycleOwner)getView().getActivity()))
					.subscribe(goodsInfo->{
						getView().onGetGoodsSuccess(goodsInfo);
					}, throwable -> {
						getView().onGetGoodsFail(throwable);
						onError(throwable);
					});
		}

		/**
		 * 取具体商品，从后台取
		 * @param code 商品条码
		 */
		public void getGoods(String code) {
			getModel().getGoods(code)
					.as(RxLife.as((LifecycleOwner)getView().getActivity()))
					.subscribe(goodsInfo->{
						getView().hideDialog();
						getView().onGetGoodsSuccess(goodsInfo);
					}, throwable -> {
						getView().hideDialog();
						getView().onGetGoodsFail(throwable);
						logger.warn("didn't get goods");
						throwable.printStackTrace();
					});
		}

		public void successGetGoodsList(GoodsInfoList goodsInfo){
			logger.info("get goods success");
			getView().hideDialog();
			total = goodsInfo.getTotal();
			getView().onGetGoodsListSuccess(goodsInfo);
		}

		public static int getPageSize() {
			return pageSize;
		}

		public static void setPageSize(int pageSize) {
			BaseGetGoodsPresent.pageSize = pageSize;
		}

		public int getTotal() {
			return total;
		}
	}
}
