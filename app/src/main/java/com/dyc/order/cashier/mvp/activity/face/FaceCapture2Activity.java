package com.dyc.order.cashier.mvp.activity.face;

import android.graphics.Bitmap;
import android.hardware.camera2.CameraCharacteristics;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dyc.order.cashier.R;
import com.dyc.order.cashier.constant.ActivityOperationField;
import com.dyc.order.cashier.function.Camera2Provider;
import com.dyc.order.cashier.base.activity.BaseMvpActivityimpl;
import com.dyc.order.cashier.data.local.DataCache;
import com.dyc.administrator.toollibrary.utils.MLogger;
import com.dyc.simplemvplibrary.Model;
import com.dyc.simplemvplibrary.Presenter;

public class FaceCapture2Activity extends BaseMvpActivityimpl implements View.OnClickListener {
	private MLogger logger = MLogger.getLogger(this.getClass());
	public static int SUCCESS_GET_FACE = 0;
	public static int FAIL_GET_FACE = -1;

	private TextureView previewView;
	private ImageView arrow;
	private TextView title;
	private View takePhoto;
	private View reTakePhoto;
	private View confirm;

	private Camera2Provider camera2Provider;
	private Bitmap bitmap;

//	private BarCodeManager barCodeManager;

	@Override
	public int getLayoutId() {
		return R.layout.activity_face_capture;
	}

	@Override
	public void onInitView(View view) {
		showActionBar(false);
		previewView = findViewById(R.id.textureView);
		arrow = findViewById(R.id.arrow);
		title = findViewById(R.id.title);
		takePhoto = findViewById(R.id.takePhoto);
		reTakePhoto = findViewById(R.id.reTakePhoto);
		confirm = findViewById(R.id.confirm);

//		try {
//			barCodeManager = BarCodeManager.getManager(this);
//			if (barCodeManager != null){
//				barCodeManager.releaseCamera();
//			}
//			barCodeManager = null;
//		} catch (DeviceBaseException e) {
//			e.printStackTrace();
//		}

		new Thread(()->{
			camera2Provider = new Camera2Provider(this);
			camera2Provider.setOnImageStateListener(new Camera2Provider.OnImageStateListener() {
				@Override
				public void onGetImage(Bitmap bitmap) {
					setBtnVisibility(false);
					takePhoto.setOnClickListener(FaceCapture2Activity.this);
					FaceCapture2Activity.this.bitmap = bitmap;
				}
			});
			camera2Provider.initTexture(previewView, CameraCharacteristics.LENS_FACING_FRONT);
		}).start();

		setBtnVisibility(true);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.leftView:
			case R.id.title:
			case R.id.arrow:
				finish();
				break;
			case R.id.jump:
				break;
			case R.id.takePhoto:
				camera2Provider.takePhoto();
				takePhoto.setOnClickListener(null);
				break;
			case R.id.reTakePhoto:
				camera2Provider.startPreview();
				setBtnVisibility(true);
				break;
			case R.id.confirm:
				DataCache.addData(ActivityOperationField.FACE_PIC_INFO, bitmap);
				setResult(SUCCESS_GET_FACE);
				finish();
				break;
		}
	}

	private void setBtnVisibility(boolean isTakePhoto){
		runOnUiThread(()->{
			takePhoto.setVisibility(isTakePhoto ? View.VISIBLE : View.GONE);
			reTakePhoto.setVisibility(isTakePhoto ? View.INVISIBLE : View.VISIBLE);
			confirm.setVisibility(isTakePhoto ? View.INVISIBLE : View.VISIBLE);
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (camera2Provider != null){
			camera2Provider.close();
		}
	}

	@Override
	public Model initM() {
		return null;
	}

	@Override
	public Presenter initP() {
		return null;
	}
}
