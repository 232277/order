package com.dyc.order.cashier.data.local;

import com.dyc.order.cashier.data.response.LoginInfoData;
import com.dyc.order.cashier.data.response.MerchantInfoData;
import com.dyc.order.cashier.data.response.PrintTempData;
import com.dyc.administrator.toollibrary.utils.MLogger;

import java.util.HashMap;
import java.util.Map;

/**
 * func: 数据缓存
 * author:丁语成 on 2020/2/18 15:43
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class DataCache {
	private static MLogger logger = MLogger.getLogger(DataCache.class);
	private static LoginInfoData loginInfoData;
	private static MerchantInfoData merchantInfoData;
	private static PrintTempData printTempData;
//	private final static Map<String, Object> loginDataCashe = new HashMap<>(50);
	private final static Map<String, Object> cache = new HashMap<>(50);

	public static Object removeData(String key){
		return cache.remove(key);
	}

	public static boolean contains(String key){
		return cache.containsKey(key);
	}

	public static void addData(String key, Object value){
		cache.put(key, value);
	}

	public static void addAll(Map<String, Object> map){
		cache.putAll(map);
	}

	public static void clear(){
		cache.clear();
	}

	public static void setLoginInfoData(LoginInfoData data){
		loginInfoData = data;
	}

	public static LoginInfoData getLoginInfoData() {
		return loginInfoData;
	}

	public static MerchantInfoData getMerchantInfoData() {
		return merchantInfoData;
	}

	public static void setMerchantInfoData(MerchantInfoData merchantInfoData) {
		DataCache.merchantInfoData = merchantInfoData;
	}

	public static PrintTempData getPrintTempData() {
		return printTempData;
	}

	public static void setPrintTempData(PrintTempData printTempData) {
		DataCache.printTempData = printTempData;
	}

	public static void clearLoginData(){
		loginInfoData = null;
	}

	public static Object get(String key){
		if (cache.containsKey(key)){
			return cache.get(key);
		}
		return null;
	}
}
