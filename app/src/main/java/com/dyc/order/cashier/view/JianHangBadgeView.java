package com.dyc.order.cashier.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dyc.administrator.toollibrary.utils.DipUtil;
import com.dyc.administrator.toollibrary.view.BadgeView;
import com.dyc.order.cashier.R;

/**
 * func:
 * author:丁语成 on 2020/5/22 17:12
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class JianHangBadgeView extends BadgeView {
	private LinearLayout smallImgs;

	public JianHangBadgeView(Context context) {
		super(context);
	}

	public JianHangBadgeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		smallImgs = findViewById(R.id.smallImg);
	}

	@Override
	protected int getLayoutId() {
		return R.layout.custom_jianhang_badge_view_layout;
	}

	public void addSmallImg(int id){
		if (smallImgs != null){
			smallImgs.setVisibility(VISIBLE);
			ImageView imageView = new ImageView(getContext());
			imageView.setImageResource(id);
			imageView.setPadding(DipUtil.dip2px(getContext(), 4.5f), 0,
					DipUtil.dip2px(getContext(), 4.5f), 0);
			smallImgs.addView(imageView);
		}
	}
}
