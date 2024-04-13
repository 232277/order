package com.dyc.order.cashier.constant;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * func:
 * author:丁语成 on 2020/4/28 10:12
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class CameraConstant {
	public static List<CameraDeviceInfo> infos = new ArrayList<CameraDeviceInfo>(){{
		add(CameraDeviceInfo.HUAJIE);
		add(CameraDeviceInfo.AOBI);
		add(CameraDeviceInfo.YUNCONG);
	}};

	public static CameraDeviceInfo getCameraInfoNow(String productName){
		if (productName == null || TextUtils.isEmpty(productName)){
			return  null;
		}
		for (CameraDeviceInfo info : infos){
			if (productName.equals(info.getProductName())){
				return info;
			}
		}
		return null;
	}

	public enum CameraDeviceInfo{
		HUAJIE("/dev/bus/usb/001/005", "A200 HD Video device", -90, 640, 480),
		AOBI("/dev/bus/usb/001/005","USB 2.0 Camera",  0, 640, 480),
		YUNCONG("/dev/bus/usb/001/005","暂无",  0, 640, 480);

		public String name;
		public String productName;
		public float rotation;
		public int previewWidth;
		public int previewHeight;
		CameraDeviceInfo(String name, String productName, float rotation, int previewWidth, int previewHeight){
			this.name = name;
			this.productName = productName;
			this.rotation = rotation;
			this.previewWidth = previewWidth;
			this.previewHeight = previewHeight;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getProductName() {
			return productName;
		}

		public void setProductName(String productName) {
			this.productName = productName;
		}

		public float getRotation() {
			return rotation;
		}

		public void setRotation(float rotation) {
			this.rotation = rotation;
		}
	}
}
