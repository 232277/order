package com.dyc.simplemvplibrary;

import android.app.Activity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * func:
 * author:丁语成 on 2020/2/23 15:21
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public abstract class ActivityCollector extends AppCompatActivity {
	public final static List<Activity> activities = new ArrayList<>();

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addActivity(this);
	}

	public synchronized static void addActivity(Activity activity){
		activities.add(activity);
	}

	public synchronized static void finishActivity(Class activityClass){
		synchronized (activities){
			for (Activity activity : activities){
				if (activity.getClass() == activityClass){
					activity.finish();
				}
			}
		}
	}

	public synchronized static void finishAll(){
		for (Activity activity : activities){
			if (!activity.isFinishing()){
				activity.finish();
			}
		}
		activities.clear();
	}

	/**
	 * 取Activity
	 * @param targetActivity 需要查询的Activity, 比如MainActivity.class
	 * @return
	 */
	public synchronized static Activity getActivity(Class<?> targetActivity) {
		for (Activity activity : activities) {
			// 判断是否是自身或者子类
			if (activity.getClass().isAssignableFrom(targetActivity)) {
				return activity;
			}
		}
		return null;
	}

	public synchronized static int getActivitiesSize(){
		return activities.size();
	}

	public synchronized static int getUnFinishActivitySize(){
		int count = 0;
		for (Activity activity : activities){
			if (!activity.isFinishing()){
				++count;
			}
		}
		return count;
	}

	public synchronized static boolean isEmpty(){
		return activities.isEmpty();
	}

	@Override
	protected synchronized void onDestroy() {
		super.onDestroy();
		synchronized (activities) {
			activities.remove(this);
		}
	}
}
