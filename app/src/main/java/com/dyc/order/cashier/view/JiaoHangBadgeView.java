package com.dyc.order.cashier.view;

import android.content.Context;
import android.util.AttributeSet;

import com.dyc.order.cashier.R;
import com.dyc.administrator.toollibrary.view.BadgeView;

/**
 * func:
 * author:丁语成 on 2020/4/23 11:48
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class JiaoHangBadgeView extends BadgeView {
	public JiaoHangBadgeView(Context context) {
		super(context);
	}

	public JiaoHangBadgeView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected int getLayoutId() {
		return R.layout.custom_jiaohang_badge_view_layout;
	}
}
