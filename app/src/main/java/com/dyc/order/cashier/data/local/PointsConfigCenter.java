package com.dyc.order.cashier.data.local;

import com.dyc.order.cashier.data.response.PointConfigData;
import com.dyc.administrator.toollibrary.utils.MLogger;

/**
 * func:
 * author:丁语成 on 2020/5/19 16:50
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class PointsConfigCenter {
	private static MLogger logger = MLogger.getLogger(PointsConfigCenter.class);
	private static PointConfigData pointConfigData;

	public static PointConfigData getPointConfigData() {
		return pointConfigData;
	}

	public static void setPointConfigData(PointConfigData pointConfigData) {
		PointsConfigCenter.pointConfigData = pointConfigData;
	}

	public static double getPointsToMoneyRate(){
		if (pointConfigData != null && pointConfigData.deductRate != null){
			return pointConfigData.deductRate;
		}else {
			return 1d;
		}
	}
}
