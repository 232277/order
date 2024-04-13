package com.dyc.order.cashier.view;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RemoteViews;

import com.dyc.order.cashier.R;

import androidx.core.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * func:
 * author:丁语成 on 2020/4/17 11:32
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class MyNotification {
	public final static int DOWNLOAD_COMPLETE = -2;
	public final static int DOWNLOAD_FAIL = -1;

	private Context mContext;
	private NotificationCompat.Builder mBuilder;
	private NotificationManager notificationManager;

	private ProgressBar progressBar;
	private ImageView img;
	private String titleStr;   //通知标题
	private String contentStr; //通知内容
	private int iconID;        //通知栏图标
	private RemoteViews remoteView = null;  //自定义的通知栏视图

	private String channelId;
	private int notificationID;//通知的唯一标示ID
	private PendingIntent contentIntent; //点击通知后的动作
	private long when;
	/**
	 *
	 * @param context Activity或Service上下文
	 * @param contentIntent  点击通知后的动作
	 * @param id    通知的唯一标示ID
	 */
	public MyNotification(Context context,PendingIntent contentIntent,int id, String channelId) {
		mContext = context;
		notificationID = id;
		this.contentIntent = contentIntent;
		this.notificationManager = (NotificationManager)mContext.getSystemService(NOTIFICATION_SERVICE);
		this.channelId = channelId;
		mBuilder = new NotificationCompat.Builder(mContext, channelId);
	}

	/**
	 * 显示自定义通知
	 * @param icoId 自定义视图中的图片ID
	 * @param titleStr 通知栏标题
	 * @param layoutId 自定义布局文件ID
	 */
	public void showCustomizeNotification(int icoId, String titleStr, int layoutId) {
		notificationManager = (NotificationManager)mContext.getSystemService(NOTIFICATION_SERVICE);
		when = System.currentTimeMillis();
		//通知渠道NotificationChannel
		NotificationChannel notificationChannel = new NotificationChannel(channelId,
				"乱七八糟的其他信息",
				NotificationManager.IMPORTANCE_DEFAULT);
		notificationChannel.setSound(null, null);
		notificationManager.createNotificationChannel(notificationChannel);
		mBuilder.setChannelId(channelId);
		//添加自定义视图
		remoteView = new RemoteViews(
				mContext.getPackageName(),layoutId);
		mBuilder.setContent(remoteView);
		mBuilder.setOnlyAlertOnce(true);
		mBuilder.setContentIntent(contentIntent);
		mBuilder.setContentTitle(titleStr);
		mBuilder.setSmallIcon(icoId);
		//不可消除
//		mBuilder.setOngoing(true);
		mBuilder.setWhen(when);
		mBuilder.setLargeIcon(
				BitmapFactory.decodeResource(mContext.getResources(),icoId));
		mBuilder.setAutoCancel(true);
		//更新Notification
		notificationManager.notify(notificationID, mBuilder .build());
	}

	/**
	 * 更改自定义布局文件中的进度条的值
	 * @param p 进度值(0~100)
	 */
	public void changeProgressStatus(int p) {
//		mBuilder.setProgress(100, p, true);
		remoteView.setProgressBar(R.id.progress, 100, p, true);
		notificationManager.notify(notificationID, mBuilder.build());
	}

	/**
	 * 点击动作
	 * @param intent 点击动作
	 */
	public void changeContentIntent(PendingIntent intent) {
		this.contentIntent = intent;
		mBuilder.setContentIntent(intent);
	}

	/**
	 * 显示系统默认格式通知
	 * @param iconId 通知栏图标ID
	 * @param titleText 通知栏标题
	 * @param contentStr 通知栏内容
	 */
	public void showDefaultNotification(int iconId,String titleText,String contentStr) {
		this.titleStr=titleText;
		this.contentStr=contentStr;
		this.iconID=iconId;
		changeNotificationText(contentStr);
	}
	/**
	 * 改变默认通知栏的通知内容
	 * @param content
	 */
	public void changeNotificationText(String content)
	{
		mBuilder.setContentInfo(content);

		// 设置setLatestEventInfo方法,如果不设置会App报错异常
		//  NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		//注册此通知
		// 如果该NOTIFICATION_ID的通知已存在，会显示最新通知的相关信息 ，比如tickerText 等
		notificationManager.notify(notificationID, mBuilder.build());
	}

	/**
	 * 移除通知
	 */
	public void removeNotification()
	{
		// 取消的只是当前Context的Notification
		notificationManager.cancel(notificationID);
	}

}
