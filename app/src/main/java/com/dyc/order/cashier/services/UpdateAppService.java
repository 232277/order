package com.dyc.order.cashier.services;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.dyc.order.cashier.R;
import com.dyc.order.cashier.mvp.activity.other.LoginMVPActivity;
import com.dyc.order.cashier.contact.UpdateAppContact;
import com.dyc.order.cashier.view.MyNotification;
import com.dyc.administrator.toollibrary.utils.MLogger;
import com.dyc.simplemvplibrary.BaseMVP;

import rxhttp.wrapper.entity.Progress;

/**
 * func:
 * author:丁语成 on 2020/4/17 11:29
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class UpdateAppService extends Service implements BaseMVP<
		UpdateAppContact.UpdateAppModel,
		UpdateAppContact.UpdateAppView,
		UpdateAppContact.UpdateAppPresent>, UpdateAppContact.UpdateAppView {
	private final static MLogger logger = MLogger.getLogger(UpdateAppService.class);
	private final static int DOWNLOAD_COMPLETE = 1;
	private final static int DOWNLOAD_FAIL = -1;

	//自定义通知栏类
	private MyNotification myNotification;
	private Intent updateIntent = null;
	private PendingIntent updatePendingIntent = null;

	private String filePathString; //下载文件绝对路径(包括文件名)

	private UpdateAppContact.UpdateAppPresent present;

	public UpdateAppService() {
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i("service","onStartCommand");
		updateIntent = new Intent(this, LoginMVPActivity.class);
		PendingIntent updatePendingIntent = PendingIntent.getActivity(this,0,updateIntent,0);
		myNotification = new MyNotification(this, updatePendingIntent, 1, getPackageName());

		myNotification.showCustomizeNotification(R.mipmap.ic_launcher, "测试下载", R.layout.custom_my_notification_view);

		filePathString = getExternalCacheDir() + "/" + getString(R.string.app_name) + ".apk";
		present.downloadApp(filePathString);

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDownloading(Progress progress) {
		//下载进度回调,0-100，仅在进度有更新时才会回调，最多回调101次，最后一次回调文件存储路径
		int currentProgress = progress.getProgress(); //当前进度 0-100
		long currentSize = progress.getCurrentSize(); //当前已下载的字节大小
		long totalSize = progress.getTotalSize();     //要下载的总字节大小
		updateHandler.sendEmptyMessage(currentProgress);
	}

	@Override
	public void onDownloadSuccess(String path) {
		updateHandler.sendEmptyMessage(DOWNLOAD_COMPLETE);
	}

	@Override
	public void onDownloadFail(Throwable throwable) {
		updateHandler.sendEmptyMessage(DOWNLOAD_FAIL);
	}

	private Handler updateHandler = new  Handler(msg->{
		switch(msg.what){
			case DOWNLOAD_COMPLETE:
				//点击安装PendingIntent
				Intent installIntent = present.installApkIntent(filePathString);
				updatePendingIntent = PendingIntent.getActivity(UpdateAppService.this, 0, installIntent, 0);
				myNotification.changeContentIntent(updatePendingIntent);
				myNotification.changeNotificationText("下载完成，请点击安装！");
				//停止服务
				//  myNotification.removeNotification();
				stopSelf();
				break;
			case DOWNLOAD_FAIL:
				//下载失败
//				myNotification.changeProgressStatus(DOWNLOAD_FAIL);
				myNotification.changeNotificationText("文件下载失败！");
				stopSelf();
				break;
			default:  //下载中
//				myNotification.changeNotificationText(msg.what+"%");
				myNotification.changeProgressStatus(msg.what);
		}
		return true;
	});

	@Override
	public void onCreate() {
		super.onCreate();
		present = initP();
		if (present != null) {
			//将Model层注册到Presenter中
			present.registerModel(initM());
			//将View层注册到Presenter中
			present.registerView(this);
			present.afterInit();
		}
	}

	@Override
	public void onDestroy() {
		if (present != null){
			present.destroy();
		}
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		Log.i("service","onBind");
		return null;
	}

	@Override
	public Activity getActivity() {
		return null;
	}

	@Override
	public Context getContext() {
		return this;
	}

	@Override
	public UpdateAppContact.UpdateAppModel initM() {
		return new UpdateAppContact.UpdateAppModel() {
			@Override
			public void stopRequest() {

			}
		};
	}

	@Override
	public UpdateAppContact.UpdateAppView initV() {
		return this;
	}

	@Override
	public UpdateAppContact.UpdateAppPresent initP() {
		return new UpdateAppContact.UpdateAppPresent() {
		};
	}
}