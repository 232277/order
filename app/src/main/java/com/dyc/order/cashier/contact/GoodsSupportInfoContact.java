package com.dyc.order.cashier.contact;

import com.dyc.order.cashier.R;
import com.dyc.order.cashier.constant.MessageField;
import com.dyc.order.cashier.constant.ServerAddress;
import com.dyc.order.cashier.base.iml.BaseContact;
import com.dyc.order.cashier.data.local.DataCache;
import com.dyc.order.cashier.data.response.GoodsTypeInfo;
import com.dyc.order.cashier.data.response.SupplierInfo;
import com.rxjava.rxlife.RxLife;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LifecycleOwner;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import rxhttp.wrapper.param.RxHttp;

/**
 * func:
 * author:丁语成 on 2020/3/18 11:22
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public interface GoodsSupportInfoContact {
	interface GoodsSupportInfoModel extends BaseContact.BaseModel{
		List<SupplierInfo> suppliers = new ArrayList<>();
		List<GoodsTypeInfo> goodsTypes = new ArrayList<>();

		default List<String> getSuppliers() {
			List<String> res = new ArrayList<>();
			if (suppliers != null && !suppliers.isEmpty()){
				for (SupplierInfo info : suppliers){
					res.add(info.getName());
				}
			}
			return res;
		}

		default void setSuppliers(List<SupplierInfo> suppliers) {
			this.suppliers.clear();
			this.suppliers.addAll(suppliers);
		}

		default List<String> getGoodsTypes() {
			List<String> res = new ArrayList<>();
			if (goodsTypes != null && !goodsTypes.isEmpty()){
				for (GoodsTypeInfo info : goodsTypes){
					res.add(info.getName());
				}
			}
			return res;
		}

		default void setGoodsTypes(List<GoodsTypeInfo> goodsTypes) {
			this.suppliers.clear();
			this.goodsTypes.addAll(goodsTypes);
		}

		default int getGoodsTypeId(String goodsTypeName) {
			for (GoodsTypeInfo info : goodsTypes){
				if (info.getName().equals(goodsTypeName)){
					return info.getId();
				}
			}
			return 1;
		}

		default int getSupplierId(String supplierName) {
			for (SupplierInfo info : suppliers){
				if (info.getName().equals(supplierName)){
					return info.getId();
				}
			}
			return 1;
		}

		default String assembleSupplierFields(List<String> conditions){
			if (conditions == null || conditions.isEmpty()) {
				return null;
			}
			StringBuilder builder = new StringBuilder();
			for (String condition : conditions){
				builder.append(getSupplierId(condition)).append(",");
			}
			return builder.substring(0, builder.length() - 1);
		}

		default String assembleGoodsTypeFields(List<String> conditions){
			if (conditions == null || conditions.isEmpty()) {
				return null;
			}
			StringBuilder builder = new StringBuilder();
			for (String condition : conditions){
				builder.append(getGoodsTypeId(condition)).append(",");
			}
			return builder.substring(0, builder.length() - 1);
		}


		default Observable<List<SupplierInfo>> getSupplier() {
			return RxHttp.get(ServerAddress.GOODS_SUPPLIERS_ADDRESS)
					.setHeader(MessageField.AUTHORIZATION, DataCache.getLoginInfoData().getToken())
					.asResponseList(SupplierInfo.class)
					.subscribeOn(Schedulers.newThread())
					.observeOn(AndroidSchedulers.mainThread());
		}

		default Observable<List<GoodsTypeInfo>> getGoodsTypeInfo() {
			return RxHttp.get(ServerAddress.GOODS_TYPES_ADDRESS)
					.setHeader(MessageField.AUTHORIZATION, DataCache.getLoginInfoData().getToken())
					.asResponseList(GoodsTypeInfo.class)
					.subscribeOn(Schedulers.newThread())
					.observeOn(AndroidSchedulers.mainThread());
		}
	}

	interface GoodsSupportInfoView extends BaseContact.BaseView{
		default void onGetSuplierSuccess(List<String> suppliers){}
		default void onGetSuplierFail(Throwable throwable){showToast(R.string.dialog_operate_fail);}
		default void onGetGoodsTypeSuccess(List<String> goodsTypes){}
		default void onGetGoodsTypeFail(Throwable throwable){showToast(R.string.dialog_operate_fail);}
	}

	abstract class GoodsSupportInfoPresent<M extends  GoodsSupportInfoModel, V extends GoodsSupportInfoView> extends BaseContact.BasePresent<M, V>{
		//供应商和类型
		public List<String> getGoodsTypes(){
			return getModel().getGoodsTypes();
		}
		public List<String> getSuppliers(){
			return getModel().getSuppliers();
		}
		public int getGoodsTypeId(String goodsTypeName){
			return getModel().getGoodsTypeId(goodsTypeName);
		}
		public int getSupplierId(String supplierName){
			return getModel().getSupplierId(supplierName);
		}
		public String assembleGoodsTypeFields(List<String> conditions){
			return getModel().assembleGoodsTypeFields(conditions);
		}
		public String assembleSupplierFields(List<String> conditions){
			return getModel().assembleSupplierFields(conditions);
		}

		public void getGoodsSupplierInfo() {
			getModel().getSupplier()
					.as(RxLife.as((LifecycleOwner) getView().getActivity()))
					.subscribe(supplierInfoList -> {
						logger.info("get goods supplier success");
						getView().hideDialog();
						getModel().setSuppliers(supplierInfoList);
						getView().onGetSuplierSuccess(getModel().getSuppliers());
					}, throwable -> {
						getView().hideDialog();
						getView().onGetSuplierFail(throwable);
						logger.info(throwable.toString());
						throwable.printStackTrace();
					});
		}

		public void getGoodsTypeInfo(){
			getModel().getGoodsTypeInfo()
					.as(RxLife.as((LifecycleOwner) getView().getActivity()))
					.subscribe(goodsTypeInfoList -> {
						logger.info("get goods types success");
						getView().hideDialog();
						getModel().setGoodsTypes(goodsTypeInfoList);
						getView().onGetGoodsTypeSuccess(getModel().getGoodsTypes());
					}, throwable -> {
						getView().hideDialog();
						getView().onGetGoodsTypeFail(throwable);
						logger.info(throwable.toString());
						throwable.printStackTrace();
					});
		}
	}
}
