package com.dyc.order.cashier.data.local;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.dyc.order.cashier.base.MyApplication;
import com.dyc.order.cashier.constant.JiaoHangConstants;
import com.dyc.administrator.toollibrary.utils.MLogger;
import com.dyc.order.cashier.constant.JianHangConstants;

import java.util.List;

/**
 * func:
 * author:丁语成 on 2020/6/23 13:51
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class PayAppCenter {
	public static MLogger logger = MLogger.getLogger(PayAppCenter.class);
	private static PayAppName payAppName;

	public static PayAppName payWayChoose(){
		boolean found = false;
		//判断使用的收单
		PackageManager packageManager = MyApplication.getContext().getPackageManager();
		if (packageManager == null){
			payAppName = PayAppName.ZHAOHANG;
		}else {
			List<PackageInfo> packages = packageManager.getInstalledPackages(0);
			for (PackageInfo packageInfo : packages) {
				String packageName = packageInfo.packageName;
//				logger.info("packageName:" + packageName);
				//不中断循环，找到后面两个收单的时候会覆盖找到交行收单的结果，既优先使用建行收单
				if (JiaoHangConstants.JIAOHANG_APP_NAME.equals(packageName)){
					logger.info("使用交行收单");
					payAppName = PayAppName.JIAOHANG;
					found = true;
				}
//				//TODO 改了APPNAME记得改回来
				if (JianHangConstants.JIANHANG_APP_NAME.equals(packageName)){
					logger.info("使用建行收单");
					payAppName = PayAppName.JIANHANG;
					found = true;
				}
				if (JianHangConstants.JIANHANG_FACE_APP_NAME.equals(packageName)){
					logger.info("使用建行收单2");
					payAppName = PayAppName.JIANHANG_2;
					found = true;
				}
			}
		}
		if (!found){
			payAppName = PayAppName.ZHAOHANG;
		}
		return payAppName;
	}

	public static PayAppName getPayAppName() {
		return payAppName;
	}

	public static void setPayAppName(PayAppName payAppName) {
		PayAppCenter.payAppName = payAppName;
	}

	public enum PayAppName{
		/**
		 * 收单package
		 */
		JIAOHANG(JiaoHangConstants.JIAOHANG_APP_NAME, JiaoHangConstants.JIAOHANG_CLASS_NAME),
		JIANHANG_2(JianHangConstants.JIANHANG_FACE_APP_NAME, JianHangConstants.JIANHANG_FACE_CLASS_NAME),
		JIANHANG(JianHangConstants.JIANHANG_APP_NAME, JianHangConstants.JIANHANG_CLASS_NAME),
		ZHAOHANG("", "");

		private String payPackageName;
		private String payActivityName;

		PayAppName(String payPackageName, String payActivityName){
			this.payPackageName = payPackageName;
			this.payActivityName = payActivityName;
		}

		public String getPayActivityName() {
			return payActivityName;
		}

		public String getPayPackageName() {
			return payPackageName;
		}
	}
}
