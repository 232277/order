package com.dyc.order.cashier.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.dyc.order.cashier.R;

/**
 * func:
 * author:丁语成 on 2020/3/30 19:41
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class FaceMaskView extends View {
	private Bitmap bitmap;
	private int mPadding = 32;

	public FaceMaskView(Context context) {
		super(context);
	}

	public FaceMaskView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}


	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);

		if (bitmap == null) {
			createWindowFrame();
		}
		canvas.drawBitmap(bitmap, 0, 0, null);
	}

	protected void createWindowFrame() {
		bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
		Canvas osCanvas = new Canvas(bitmap);

		RectF outerRectangle = new RectF(0, 0, getWidth(), getHeight());

		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(getResources().getColor(R.color.toollibrary_white, null));
		osCanvas.drawRect(outerRectangle, paint);

		paint.setColor(Color.WHITE);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
		float centerX = getWidth() / 2;
		float centerY = getHeight() / 2;
		float radius = getHeight() / 2 - mPadding;
		osCanvas.drawCircle(centerX, centerY, radius, paint);
	}

	public Rect getMaskRect() {
		int w = getWidth();
		int h = getHeight();
		int radius = getHeight() / 2 - mPadding;
		int left = w / 2 - radius;
		int top = h / 2 - radius;
		int right = w / 2 + radius;
		int bottom = h / 2 + radius;
		return new Rect(left, top, right, bottom);
	}

	@Override
	public boolean isInEditMode() {
		return true;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		bitmap = null;
	}
}