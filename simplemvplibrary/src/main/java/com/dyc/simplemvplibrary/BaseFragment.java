package com.dyc.simplemvplibrary;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


/**
 * func:
 * author:丁语成 on 2020/2/14 20:25
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public abstract class BaseFragment extends Fragment implements InteractiveView {

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
		int layoutId = this.getLayoutId();
		View view = null;
		if (layoutId > 0) {
			view = inflater.inflate(layoutId, (ViewGroup)null);
		}
		Log.e("base", view == null ? "view null" : "view not null");
		this.doBeforeInitView(view);
		this.onInitView(view);
		return view == null ? super.onCreateView(inflater, container, savedInstanceState) : view;
	}

	public static View getContentView(Activity activity) {
		ViewGroup view = (ViewGroup) activity.getWindow().getDecorView();
		FrameLayout content = view.findViewById(android.R.id.content);
		return content.getChildAt(0);
	}

	@Override
	public void doBeforeInitView(View v) {
	}

	@Override
	public void doAfterInitView(View v) {
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		onRelease();
	}

	@Override
	public void onRelease() {

	}
}
