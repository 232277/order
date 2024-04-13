package com.dyc.order.cashier.mvp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;

import com.dyc.order.cashier.R;
import com.dyc.order.cashier.function.Camera2Provider;
import com.dyc.order.cashier.function.UvcCameraProvider;
import com.dyc.administrator.toollibrary.utils.MLogger;
import com.serenegiant.usb.widget.UVCCameraTextureView;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {
	private MLogger logger = MLogger.getLogger(this.getClass());
	private TextureView textureView;
//	private CameraPreview cameraPreview;
	private Camera2Provider camera2Provider;
	private UvcCameraProvider uvcCameraProvider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		getSupportActionBar().hide();
		textureView = findViewById(R.id.textureView);
		uvcCameraProvider = new UvcCameraProvider(this, (UVCCameraTextureView)textureView);
		uvcCameraProvider.setUvcCameraListener(bitmap ->
				runOnUiThread(
						()-> {
							logger.info("get bitmap : " + bitmap.toString());
							((ImageView)findViewById(R.id.res)).setImageBitmap(bitmap);
						})
		);

//		cameraPreview = findViewById(R.id.cameraPreview);
//		cameraPreview.setOnSavedPicListener(file -> {
//			if (file!=null){
//				logger.info("file:" + file.toString() + " length:" + file.length());
//			}else {
//				logger.info("file null");
//			}
//		});

//		textureView = findViewById(R.id.textureView);
//		camera2Provider = new Camera2Provider(this);
//		camera2Provider.initTexture(textureView, CameraCharacteristics.LENS_FACING_FRONT);
//		camera2Provider.setOnImageStateListener(new Camera2Provider.OnImageStateListener() {
//			@Override
//			public void onGetImage(Bitmap bitmap) {
//				runOnUiThread(()->{
//					((ImageView)findViewById(R.id.res)).setImageBitmap(PictureCache.lessenImageByPix(bitmap,480));
//				});
//			}
//		});
	}

	@Override
	protected void onResume() {
		super.onResume();
//		cameraPreview.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
//		cameraPreview.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
//		camera2Provider.close();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.start:
//				camera2Provider.startPreview();
				break;
			case R.id.stop:
//				camera2Provider.stopPreview();
				break;
			case R.id.takePhoto:
//				cameraPreview.takePicture();
//				camera2Provider.takePhoto();
				break;
		}
	}
}
