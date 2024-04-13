package com.dyc.order.cashier.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import com.dyc.administrator.toollibrary.view.BadgeView;
import com.dyc.order.cashier.R;

/**
 * func:
 * author:丁语成 on 2020/6/22 10:31
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class AmountChooseBadgeView extends BadgeView {
	private TextView text;
	private TextView smallText;

	public AmountChooseBadgeView(Context context) {
		super(context);
	}

	public AmountChooseBadgeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		text = findViewById(R.id.text);
		smallText = findViewById(R.id.smallText);
	}

	@Override
	protected int getLayoutId() {
		return R.layout.custom_amount_choose_badge_view_layout;
	}

	@Override
	public void setText(CharSequence str) {
		if (text != null){
			text.setText(str);
		}
	}

	public void setSmallText(CharSequence str){
		if (smallText != null){
			if (TextUtils.isEmpty(str)){
				smallText.setVisibility(GONE);
			}else {
				smallText.setVisibility(VISIBLE);
				smallText.setText(str);
			}
		}
	}
}
