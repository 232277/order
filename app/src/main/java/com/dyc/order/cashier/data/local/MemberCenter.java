package com.dyc.order.cashier.data.local;

import com.dyc.order.cashier.data.response.GoodsInfoList;
import com.dyc.order.cashier.data.response.MemberInfoData;
import com.dyc.order.cashier.data.response.MemberLevelData;
import com.dyc.administrator.toollibrary.utils.MLogger;
import com.dyc.administrator.toollibrary.utils.NumUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * func:
 * author:丁语成 on 2020/4/9 18:07
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class MemberCenter {
	public static MLogger logger = MLogger.getLogger(MemberCenter.class);
	public static MemberInfoData memberInfoData;
	public static MemberLevelData memberLevelData;
	public static ArrayList<MemberLevelData.Level> levels = new ArrayList<>();

	public static double getMemberPrice(GoodsInfoList.GoodsDetailData data) {
		if (data == null || data.getMemberPrice() == null){
			return 0;
		}else if (memberInfoData == null){
			return data.getMemberPrice();
		}else {
			return NumUtils.remain2Num(getMemberPrice(data, memberInfoData.getLevelId()));
		}
	}

	public static double getMemberPrice(GoodsInfoList.GoodsDetailData data, int levelId){
		MemberLevelData.Level level = getLevel(levelId);
		if (data == null){
			return 0;
		}
		if (level == null){
			return data.getMemberPrice();
		}
		if (level.enableMemberPrice){
			return data.getMemberPrice() * level.memberDiscount;
		}else {
			return data.getPrice();
		}
	}

	public static MemberLevelData.Level getLowestLevel(){
		return getLevel((o1, o2) -> {
			if (o1 == null || o2 == null || o1.level == null || o2.level == null){
				return 0;
			}
			return o1.level.compareTo(o2.level);
		});
	}

	public static MemberLevelData.Level getLevel(Comparator<MemberLevelData.Level> comparator){
		if (levels.isEmpty()){
			return null;
		}
		MemberLevelData.Level target = null;
		for (MemberLevelData.Level level : levels){
			//target <= level的条件
			if (comparator.compare(target, level) >= 0){
				target = level;
			}
		}
		return target;
	}

	public static MemberLevelData.Level getLevel(int levelId){
		if (levels.isEmpty()){
			return null;
		}
		for (MemberLevelData.Level level : levels){
			if (level.id == levelId){
				return level;
			}
		}
		return null;
	}

	public static void setMemberInfoData(MemberInfoData memberInfoData) {
		logger.info("setMemberInfoData");
		MemberCenter.memberInfoData = memberInfoData;
	}

	public static MemberInfoData getMemberInfoData() {
		return memberInfoData;
	}

	public static void setMemberLevelData(MemberLevelData memberLevelData) {
		MemberCenter.memberLevelData = memberLevelData;
		if (MemberCenter.memberLevelData != null){
			logger.info("setMemberLevelData");
			levels = new ArrayList<>(Arrays.asList(memberLevelData.rows));
		}
	}

	public static MemberLevelData getMemberLevelData() {
		return memberLevelData;
	}

	public static ArrayList<MemberLevelData.Level> getLevels() {
		return levels;
	}
}
