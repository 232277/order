package com.dyc.administrator.toollibrary.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.lang.ref.SoftReference;
import java.nio.ByteBuffer;
import java.util.List;

/**
 * func:
 * author:丁语成 on 2020/4/28 10:26
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class BitmapUtils {
	public static Bitmap mirror(Bitmap src){
		Matrix m = new Matrix();
		m.postScale(-1, 1); // 镜像水平翻转
		return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), m, true);
	}

	public static Bitmap rotate(Bitmap src, int x, int y, float degree, boolean filter){
		if(src == null){
			return null;
		}
		Matrix m = new Matrix();
		m.setRotate(degree, x/2, y/2);
		return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), m, filter);
	}

	public static Bitmap rotate(Bitmap src,float degree){
		if(src == null){
			return null;
		}
		return rotate(src, src.getWidth()/2, src.getHeight()/2, degree, true);
	}

	public static Bitmap ByteBufferToBitmap(ByteBuffer frameBuffer){
		if(frameBuffer == null){
			return null;
		}
		return ByteBufferToBitmap(frameBuffer, 640, 480, Bitmap.Config.RGB_565);
	}

	public static Bitmap ByteBufferToBitmap(ByteBuffer frameBuffer, int width, int height, Bitmap.Config config){
		if(frameBuffer == null){
			return null;
		}
		Bitmap bmp = Bitmap.createBitmap(width, height, config);
		bmp.copyPixelsFromBuffer(frameBuffer);
		return bmp;
	}

	public static Bitmap DrawableToBitmap(Drawable drawable) {

		// 获取 drawable 长宽
		int width = drawable.getIntrinsicWidth();
		int heigh = drawable.getIntrinsicHeight();

		drawable.setBounds(0, 0, width, heigh);

		// 获取drawable的颜色格式
		Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
				: Bitmap.Config.RGB_565;
		// 创建bitmap
		Bitmap bitmap = Bitmap.createBitmap(width, heigh, config);
		// 创建bitmap画布
		Canvas canvas = new Canvas(bitmap);
		// 将drawable 内容画到画布中
		drawable.draw(canvas);
		return bitmap;
	}
}
