package com.dyc.order.cashier.data.response;

import java.util.Arrays;
import java.util.List;

/**
 * func: 会员列表
 * author:丁语成 on 2020/3/5 14:51
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class MemberListData {
	private int total;
	private Object info;
	private MemberInfoData[] rows;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public Object getInfo() {
		return info;
	}

	public void setInfo(Object info) {
		this.info = info;
	}

	public MemberInfoData[] getRows() {
		return rows;
	}

	public List<MemberInfoData> getMemberList(){
		if (rows != null && rows.length > 0){
			return Arrays.asList(rows);
		}
		return null;
	}

	public void setRows(MemberInfoData[] rows) {
		this.rows = rows;
	}
}
