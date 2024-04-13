package com.dyc.simplemvplibrary;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

/**
 * func:
 * author:丁语成 on 2020/2/14 19:44
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public abstract class BaseActivity extends ActivityCollector implements InteractiveView {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		int layoutId = getLayoutId();
		if (layoutId > 0) {
			this.setContentView(layoutId);
		}

		this.doBeforeInitView(getContentView(this));
		this.onInitView(getContentView(this));
	}

	@Override
	public void doBeforeInitView(View v){
	}

	@Override
	public void doAfterInitView(View v) {
	}

	public static View getContentView(Activity activity) {
		ViewGroup view = (ViewGroup) activity.getWindow().getDecorView();
		FrameLayout content = view.findViewById(android.R.id.content);
		return content.getChildAt(0);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		onRelease();
	}

	@Override
	public void onRelease() {
	}
}

