package com.dyc.order.cashier.function;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.media.FaceDetector;
import android.os.AsyncTask;

import com.dyc.order.cashier.data.local.PictureCache;
import com.dyc.administrator.toollibrary.utils.BitmapUtils;
import com.dyc.administrator.toollibrary.utils.MLogger;

import java.io.ByteArrayOutputStream;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * func:
 * author:丁语成 on 2020/3/30 16:44
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class FaceDectTask extends AsyncTask<Void, Void, Boolean>{
	private MLogger logger = MLogger.getLogger(this.getClass());
	private Bitmap oriBitmap;
	private byte[] data;
	private int width;
	private int height;
	private int maxFaceNum;
	private boolean is565Data;
	private float rotation;
	private OnGetFacesListener listener;


	public FaceDectTask(byte[] data, int width, int height, int maxFaceNum, OnGetFacesListener listener) {
		this(data, width, height, maxFaceNum, false, listener);
	}

	public FaceDectTask(byte[] data, int width, int height, int maxFaceNum, boolean is565Data, OnGetFacesListener listener) {
		this(data, width, height, maxFaceNum, false, 0, listener);
	}

	public FaceDectTask(byte[] data, int width, int height, int maxFaceNum, boolean is565Data, float rotation, OnGetFacesListener listener) {
		this.data = data;
		this.width = width;
		this.height = height;
		this.maxFaceNum = maxFaceNum;
		this.is565Data = is565Data;
		this.rotation = rotation;
		this.listener = listener;
	}

	@Override
	protected Boolean doInBackground(Void... voids) {
		try {
			oriBitmap = byteToBitmap(data, width, height, rotation, is565Data);
			List<Bitmap> res = cutFaces(oriBitmap, maxFaceNum, is565Data);
			if (res != null && !res.isEmpty()){
				logger.warn("found face");
				if (listener != null){
					listener.onGetFaces(res, PictureCache.lessenImageByPix(oriBitmap, 480));
				}
			}else {
				if (listener != null){
					listener.onFail(oriBitmap);
				}
				logger.warn("not found face");
			}
			return res != null && !res.isEmpty();
		}catch (Exception e){
			e.printStackTrace();
		}
		return false;
	}

	public static Bitmap byteToBitmap(byte[] data, int width, int height){
		return byteToBitmap(data, width, height, 0, false);
	}

	public static Bitmap byteToBitmap(byte[] data, int width, int height, float rotation, boolean is565Data){
		YuvImage yuvimage;
		byte[] rawImage;
		BitmapFactory.Options options = new BitmapFactory.Options();
		if (is565Data){
			rawImage = data;
			options.inPreferredConfig = Bitmap.Config.RGB_565;
		}else {
			yuvimage = new YuvImage(
					data,
					ImageFormat.NV21,
					width,
					height,
					null);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			yuvimage.compressToJpeg(new Rect(0, 0, yuvimage.getWidth(), yuvimage.getHeight()), 100, baos);// 80--JPG图片的质量[0-100],100最高
			rawImage = baos.toByteArray();
			options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		}

		options.inSampleSize = 32;
		options.inMutable = true;
		SoftReference<Bitmap> rawBitmap = new SoftReference<Bitmap>(BitmapFactory.decodeByteArray(rawImage, 0, rawImage.length, options));
		if (rotation != 0){
//			Matrix m = new Matrix();
//			m.setRotate(rotation, rawBitmap.get().getWidth()/2, rawBitmap.get().getHeight()/2);
//			return Bitmap.createBitmap(rawBitmap.get(), 0, 0,
//					rawBitmap.get().getWidth(), rawBitmap.get().getHeight(), m, true);
			return BitmapUtils.rotate(rawBitmap.get(), rotation);
		}
		return rawBitmap.get();

	}

	public static List<Bitmap> cutFaces(byte[] bytes, int maxFaceNum, int width, int height) {
		return cutFaces(byteToBitmap(bytes, width, height), maxFaceNum);
	}

	public static List<Bitmap> cutFaces(Bitmap bitmap, int maxFaceNum){
		return cutFaces(bitmap, maxFaceNum, false);
	}

	public static List<Bitmap> cutFaces(Bitmap bitmap, int maxFaceNum, boolean is565Pic){
		Bitmap bitmap565 = bitmap;
		if (!is565Pic){
			bitmap565 = bitmap.copy(Bitmap.Config.RGB_565, true);
		}
		FaceDetector.Face[] faces = new FaceDetector.Face[maxFaceNum];
		ArrayList<Bitmap> res = new ArrayList<>(maxFaceNum);
		FaceDetector faceDetector = new FaceDetector(bitmap565.getWidth(), bitmap565.getHeight(), maxFaceNum);
		int faceNum = faceDetector.findFaces(bitmap565, faces);
		if (faceNum > 0) {
			for (FaceDetector.Face face : faces) {
				if (face != null) {
					PointF point = new PointF();
					face.getMidPoint(point);
					res.add(Bitmap.createBitmap(bitmap565,
							(int) Math.max(point.x - 2 * face.eyesDistance(), 0),
							(int) Math.max(point.y - 2 * face.eyesDistance(), 0),
							2 * (int)face.eyesDistance(), 2 * (int)face.eyesDistance()));
				}
			}
		}
		return res;
	}

	public interface OnGetFacesListener{
		void onGetFaces(List<Bitmap> faces, Bitmap oriBitmap);
		default void onFail(Bitmap oriPic){}
	}
}