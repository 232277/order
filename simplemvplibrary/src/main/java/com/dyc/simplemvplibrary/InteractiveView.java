package com.dyc.simplemvplibrary;

import android.os.Bundle;
import android.view.View;

/**
 * func:
 * author:丁语成 on 2020/2/14 19:50
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public interface InteractiveView {

	int getLayoutId();

	void doBeforeInitView(View v);

	void onInitView(View view);

	void doAfterInitView(View view);

	void onRelease();
}
