package com.dyc.order.cashier.mvp.activity.face;

import androidx.annotation.NonNull;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.hardware.Camera;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dyc.order.cashier.R;
import com.dyc.order.cashier.constant.ActivityOperationField;
import com.dyc.order.cashier.function.CameraHelper;
import com.dyc.order.cashier.function.CameraListener;
import com.dyc.order.cashier.function.FaceDectTask;
import com.dyc.order.cashier.base.activity.BaseMvpActivityimpl;
import com.dyc.order.cashier.data.local.DataCache;
import com.dyc.administrator.toollibrary.utils.MLogger;
import com.dyc.simplemvplibrary.Model;
import com.dyc.simplemvplibrary.Presenter;

public class FaceCaptureActivity extends BaseMvpActivityimpl implements View.OnClickListener {
	private MLogger logger = MLogger.getLogger(this.getClass());
	public static int SUCCESS_GET_FACE = 0;
	public static int FAIL_GET_FACE = -1;

	private TextureView previewView;
	private ImageView arrow;
	private TextView title;
	private View takePhoto;
	private View reTakePhoto;
	private View confirm;

	private Bitmap facePic;
	private byte[] rawData;

	private CameraHelper cameraHelper;
	private Camera.Size size;

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

		try {
			cameraHelper = new CameraHelper.Builder()
					.setContext(this)
					.previewViewSize(new Point(previewView.getMeasuredWidth(),previewView.getMeasuredHeight()))
					.rotation(getWindowManager().getDefaultDisplay().getRotation())
					.specificCameraId(Camera.CameraInfo.CAMERA_FACING_FRONT)
					.isMirror(true)
					.previewOn(previewView)
					.cameraListener(new CameraListener() {
						@Override
						public void onPreview(byte[] data, Camera camera) {
							rawData = data;
							size = camera.getParameters().getPreviewSize();
						}
					})
					.build(true);
			if (cameraHelper.checkPermissions()){
				cameraHelper.init();
				cameraHelper.start();
			}else {
				cameraHelper.requestPermission(this);
			}
		}catch (Exception e){
			e.printStackTrace();
			setResult(FAIL_GET_FACE);
			finish();
		}
	}

	private void takePic(){
		setBtnVisibility(false);
		cameraHelper.stop();
		takePhoto.setOnClickListener(null);
		facePic = FaceDectTask.byteToBitmap(rawData, size.width, size.height);
	}

	private void restartCamera(){
		cameraHelper.init();
		cameraHelper.start();
		takePhoto.setOnClickListener(this);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		cameraHelper.init();
		cameraHelper.start();
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
				takePic();
				break;
			case R.id.reTakePhoto:
				restartCamera();
				setBtnVisibility(true);
				break;
			case R.id.confirm:
				DataCache.addData(ActivityOperationField.FACE_PIC_INFO, facePic);
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
		if (cameraHelper != null){
			cameraHelper.release();
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
