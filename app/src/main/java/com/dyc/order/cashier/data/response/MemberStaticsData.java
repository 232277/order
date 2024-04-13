package com.dyc.order.cashier.data.response;

/**
 * func: 会员统计数据
 * author:丁语成 on 2020/3/5 14:13
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class MemberStaticsData {
	private int addMemberNum;//今日新增会员
	private int totalMemberNum;//会员总数

	public int getAddMemberNum() {
		return addMemberNum;
	}

	public void setAddMemberNum(int addMemberNum) {
		this.addMemberNum = addMemberNum;
	}

	public int getTotalMemberNum() {
		return totalMemberNum;
	}

	public void setTotalMemberNum(int totalMemberNum) {
		this.totalMemberNum = totalMemberNum;
	}

	@Override
	public String toString() {
		return "addMemberNum:" + addMemberNum + " totalMemberNum" + totalMemberNum;
	}
}
