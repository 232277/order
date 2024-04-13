package com.dyc.order.cashier.contact;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import com.blankj.utilcode.util.AppUtils;
import com.dyc.order.cashier.R;
import com.dyc.order.cashier.base.MyApplication;
import com.dyc.order.cashier.base.iml.BaseContact;

import java.io.File;

import androidx.core.content.FileProvider;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import rxhttp.wrapper.entity.Progress;
import rxhttp.wrapper.param.RxHttp;

import static com.blankj.utilcode.util.StringUtils.getString;

/**
 * func:
 * author:丁语成 on 2020/4/16 22:30
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public interface UpdateAppContact {
	interface UpdateAppModel extends BaseContact.BaseModel {
	}

	interface UpdateAppView extends BaseContact.BaseView{
		default void onDownloadSuccess(String path){}
		default void onDownloading(Progress progress){}
		default void onDownloadFail(Throwable throwable){}
	}

	abstract class UpdateAppPresent<M extends UpdateAppModel, V extends UpdateAppView> extends BaseContact.BasePresent<M,V>{
		private static String test = "http://update.9158.com/miaolive/Miaolive.apk";
		private Disposable disposable;

		public void downloadApp(String filePath, String url){
			//文件存储路径
			File file = new File(filePath);
			downloadApp(file, url);
		}

		public void downloadApp(String url){
			downloadApp(MyApplication.getContext().getExternalCacheDir() + "/"
							+ getString(R.string.app_name) + ".apk"
					, url);
		}

		public void downloadApp(File file, String url){
			long length = file.length();
			if (file.exists()){
				file.delete();
			}
			disposable = RxHttp.get(url)
					.subscribeOn(Schedulers.io())
					.setRangeHeader(length, true)
					.asDownload(file.getPath(), progress -> {
						getView().onDownloading(progress);
					}, AndroidSchedulers.mainThread()) //指定主线程回调
					.subscribe(s -> {
						//s为String类型，这里为文件存储路径
						logger.info("下载完成");
						getView().onDownloadSuccess(s);
					}, throwable -> {
						logger.info("下载失败");
						throwable.printStackTrace();
						//下载失败，处理相关逻辑
						getView().onDownloadFail(throwable);
					});
		}

		public void installApk(String saveFileName){
			logger.info("开始安装");
			AppUtils.installApp(saveFileName);
		}

		public Intent installApkIntent(String saveFileName) {
			File apkFile = new File(saveFileName);
			if (!apkFile.exists()) {
				return null;
			}
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
				intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
				Uri uri = FileProvider.getUriForFile(MyApplication.getContext(),
						getString(R.string.provider_name), apkFile);
				intent.setDataAndType(uri, "application/vnd.android.package-archive");
			} else {
				intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
			}
			return intent;
		}

		public void stopRequest(){
			if (disposable != null && !disposable.isDisposed()){
				disposable.dispose();
			}
			getModel().stopRequest();
		}

		@Override
		public void destroy() {
			super.destroy();
			stopRequest();
		}
	}
}
