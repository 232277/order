package com.dyc.order.cashier.data.local;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Handler;
import android.util.LruCache;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.dyc.order.cashier.base.MyApplication;
import com.dyc.order.cashier.constant.MessageField;
import com.dyc.administrator.toollibrary.utils.BitmapUtils;
import com.dyc.administrator.toollibrary.utils.MLogger;
import com.rxjava.rxlife.RxLife;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import rxhttp.wrapper.param.RxHttp;

import static com.dyc.administrator.toollibrary.utils.DipUtil.px2dip;

/**
 * func: 图片缓存
 * author:丁语成 on 2020/3/5 20:51
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class PictureCache {
	private static MLogger logger = MLogger.getLogger(PictureCache.class);
	private static LruCache<String, Bitmap> urlToBitmap = new LruCache<>((int)Runtime.getRuntime().maxMemory() / 8);

	public static boolean containsImg(String url){
		return urlToBitmap.get(url) != null;
	}

	public static void setBitMapForImageView(String url, ImageView imageView){
		setBitMapForImageView(url, imageView, 150);
	}

	public static void setBitMapForImageView(String url, ImageView imageView, int size){
		if (imageView != null){
			if (containsImg(url)){
				Handler handler = new Handler();
				handler.post(()->{imageView.setImageBitmap(urlToBitmap.get(url));});
			}else {
				Glide.with(imageView)
						.load(url)
				.addListener(new RequestListener<Drawable>() {
					@Override
					public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
						return false;
					}

					@Override
					public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
						urlToBitmap.put(url, lessenImage(BitmapUtils.DrawableToBitmap(resource), 150));
						return false;
					}
				})
				.into(imageView);
			}
		}
	}

	public static Bitmap getBitmap(String url){
		if (url != null && urlToBitmap.get(url) != null){
			return urlToBitmap.get(url);
		}
		return null;
	}

	public static Bitmap getBitmapFromPath(String path){
		return getBitmapFromPath(path, 320f);
	}

	public static Bitmap getBitmapFromPath(String path, float size){
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Bitmap bitmap;
		//缩放比。只用高或者宽其中一个数据进行计算
		options.inJustDecodeBounds = false;
		int be = (int)(options.outHeight / size);
		if (be <= 0)
			be = 1;
		//重新读入图片，注意此时已经把 options.inJustDecodeBounds 设回 false 了
		options.inSampleSize = be;
		bitmap=BitmapFactory.decodeFile(path,options);
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		//缩放后
		System.out.println(w+" "+h);
		return bitmap;
	}

	public static void saveBitmapToPath(String uri, Bitmap bitmap){
		if (uri == null || bitmap == null){
			return;
		}
		File f = new File(Environment.getExternalStorageDirectory().getPath(), ""+System.currentTimeMillis());
		if (f.exists()) {
			f.delete();
		}
		try {
			FileOutputStream out = new FileOutputStream(f);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
			out.flush();
			out.close();
			logger.info("已存储:" + uri);
		} catch (FileNotFoundException e) {
			logger.info(e);
		} catch (IOException e) {
			logger.warn(e);
		}
	}

	public static boolean addBitMap(String url, LifecycleOwner lifecycleOwner){
		if (url != null && !containsImg(url) && urlToBitmap.size() < 20){
			getBitmap(url, lifecycleOwner, bitmap -> {
				if (bitmap != null){
					urlToBitmap.put(url, lessenImage(bitmap, 150));
				}
			});
			return true;
		}
		return false;
	}

	public interface OnBitmapDownloadFinishListener{
		void onFinish(Bitmap bitmap);
	}

	public static void getBitmap(String url, LifecycleOwner lifecycleOwner, OnBitmapDownloadFinishListener listener){
		if (url != null){
			url = url.trim();
//			url = url.replace("http", "https");
			if (!"".equals(url)){
				RxHttp.get(url)
						.add(MessageField.TOKEN, DataCache.getLoginInfoData().getToken())
									.asBitmap()
									.subscribeOn(Schedulers.io())
									.observeOn(AndroidSchedulers.mainThread())
									.as(RxLife.as(lifecycleOwner))
									.subscribe(bitmap -> {
										if (bitmap != null){
											listener.onFinish(bitmap);
										}
									}, throwable -> {
										logger.info("error");
										logger.warn(throwable);
							throwable.printStackTrace();
						});
			}
		}
	}


	public static Bitmap lessenImage(Bitmap bitmap, int dp){
		int be = (int)(px2dip(MyApplication.getContext(), bitmap.getWidth()) / dp);
		if (be <= 0)
			be = 1;
		Matrix matrix = new Matrix();
		matrix.setScale(((float)1/be), ((float)1/be));
		bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),bitmap.getHeight(), matrix, true);
		return bitmap;
	}

	public static Bitmap lessenImageByPix(Bitmap bitmap, int pixelWidth){
		return lessenImage(bitmap, px2dip(MyApplication.getContext(), pixelWidth));
	}
}
