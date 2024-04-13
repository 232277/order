package com.dyc.order.cashier.function;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.FaceDetector;
import android.media.ImageReader;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;

import com.dyc.order.cashier.R;
import com.dyc.order.cashier.base.iml.BaseContact;
import com.dyc.administrator.toollibrary.utils.MLogger;
import com.dyc.administrator.toollibrary.utils.ToastUtils;
import com.dyc.simplemvplibrary.BaseMvpActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * func:
 * author:丁语成 on 2020/3/26 15:02
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class MyCamera2 implements LifecycleObserver {
	private final static ArrayList<String> permissions = new ArrayList<String>() {{
		add(Manifest.permission.CAMERA);
		add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
	}};
	private MLogger logger = MLogger.getLogger(this.getClass());
	private final static int MAX_FACES = 3;

	private CameraManager cameraManager;
	private CameraDevice cameraDevice;
	private HandlerThread cameraBackGroundThread;
	private Handler cameraHandler;
	private String usedCameraId;
	private CameraCharacteristics cameraCharacteristics;
	private Size previewSize;
	private Size captureSize;
	private ImageReader imageReader;
	private CameraCaptureSession cameraCaptureSession;

	private FaceDetector faceDetector;
	private TextureView textureView;
	private BaseMvpActivity activity;

	public MyCamera2(BaseMvpActivity activity) {
		this.activity = activity;
	}

	/**
	 * 	p10
	 *  屏幕宽度（像素）：800
	 *  屏幕高度（像素）：1184
	 *  屏幕密度（0.75 / 1.0 / 1.5）：2.0
	 *  屏幕密度dpi（120 / 160 / 240）：320
	 *  屏幕宽度（dp）：400
	 *  屏幕高度（dp）：592
	 */
	@OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
	private void onCreate() {
		logger.info("face detect onCreate");
		cameraManager = activity.getSystemService(CameraManager.class);
		textureView = activity.findViewById(R.id.textureView);
		textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
			@Override
			public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
				setupCamera(width, height);
			}

			@Override
			public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
			}

			@Override
			public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
				return false;
			}

			@Override
			public void onSurfaceTextureUpdated(SurfaceTexture surface) {
			}
		});
	}

	@OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
	private void onPause() {
		logger.info("face detect onPause");
		closeCamera();
	}

	public void closeCamera(){
		if (cameraDevice != null){
			cameraDevice.close();
		}
	}

	private CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
		@Override
		public void onOpened(@NonNull CameraDevice camera) {
			MyCamera2.this.cameraDevice = camera;
			createCaptureSession(cameraDevice);
		}

		@Override
		public void onDisconnected(@NonNull CameraDevice camera) {
			closeCamera();
			cameraDevice = null;
			stopBackgroundThread();
		}

		@Override
		public void onError(@NonNull CameraDevice camera, int error) {
			BaseContact.BaseView view = (BaseContact.BaseView)activity;
			view.showNotifyDialog(R.string.tip_fail_open_camera,R.drawable.fail);
			closeCamera();
			cameraDevice = null;
			stopBackgroundThread();
		}
	};
	public void openCamera() {
		if (checkPermission()) {
			try {
				usedCameraId = Integer.toString(CameraCharacteristics.LENS_FACING_FRONT);
				cameraCharacteristics = cameraManager.getCameraCharacteristics(usedCameraId);
				cameraManager.openCamera(usedCameraId,
						stateCallback, cameraHandler);
			} catch (CameraAccessException e) {
				e.printStackTrace();
			}
		}
	}

	//创建预览会话
	private void createCaptureSession(CameraDevice cameraDevice){
		try {
			final CaptureRequest.Builder mCaptureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
			Surface surface = new Surface(textureView.getSurfaceTexture());
			mCaptureRequestBuilder.addTarget(surface);
			//自动对焦
			mCaptureRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);

			cameraDevice.createCaptureSession(Arrays.asList(surface, imageReader.getSurface()), new CameraCaptureSession.StateCallback() {
				@Override
				public void onConfigured(CameraCaptureSession session) {
					cameraCaptureSession  = session;
					try {
						session.setRepeatingRequest(mCaptureRequestBuilder.build(), new CameraCaptureSession.CaptureCallback() {
							@Override
							public void onCaptureCompleted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull TotalCaptureResult result) {
								super.onCaptureCompleted(session, request, result);
							}
						}, cameraHandler);
					} catch (CameraAccessException e) {
						e.printStackTrace();
					}
				}

				@Override
				public void onConfigureFailed(CameraCaptureSession session) {
					//开启预览会话失败
					ToastUtils.showToast(activity, R.string.tip_fail_open_camera);
					logger.warn("开启预览会话失败 onConfigureFailed");
				}
			}, cameraHandler);

		} catch (CameraAccessException e) {
			e.printStackTrace();
		}
	}



	//设置相机参数
	public void setupCamera(int width, int height){
		logger.info("setupCamera");
		String[] cameraList;
		try {
			cameraList = cameraManager.getCameraIdList();
			if (cameraList.length == 0) {
				//没有可用相机
				return;
			}
			//取相机信息
			for (String cameraId : cameraList) {
				CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraId);
				if (cameraCharacteristics.get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_FRONT) {
					usedCameraId = cameraId;
					this.cameraCharacteristics = cameraCharacteristics;
					break;
				}
			}

			StreamConfigurationMap map = cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
			//获取相机支持的最大拍照尺寸
			captureSize = Collections.max(Arrays.asList(map.getOutputSizes(ImageFormat.JPEG)),
					(o1, o2) -> Long.signum(o1.getWidth() * o1.getHeight() - o2.getHeight() * o2.getWidth()));

			//设置TextureView的缓冲区大小
			textureView.getSurfaceTexture().setDefaultBufferSize(width, height);

			//开启预览之前，需要通过ImageReader获取预览帧数据：
			imageReader = ImageReader.newInstance(captureSize.getWidth(), captureSize.getHeight(),
					ImageFormat.JPEG, 2);
			//初始化handler及线程
			cameraHandler = startBackgroundThread();
			imageReader.setOnImageAvailableListener(reader -> {
				logger.info("imageReader available");
				cameraHandler.post(() -> reader.acquireLatestImage());
			}, cameraHandler);
			openCamera();
		} catch (CameraAccessException e) {
			e.printStackTrace();
		}
	}

	private boolean checkPermission() {
		ArrayList<String> deniedPermission = new ArrayList<>(2);
		for (String permission : permissions) {
			if (ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_DENIED) {
				deniedPermission.add(permission);
			}
		}

		if (!deniedPermission.isEmpty()) {
			activity.requestPermissions((String[]) deniedPermission.toArray(), 999);
		}

		return permissions.isEmpty();
	}

	private Handler startBackgroundThread() {
		cameraBackGroundThread = new HandlerThread("CameraBackground");
		cameraBackGroundThread.start();
		cameraHandler = new Handler(cameraBackGroundThread.getLooper());
		return cameraHandler;
	}

	private void stopBackgroundThread() {
		cameraBackGroundThread.quitSafely();
		try {
			cameraBackGroundThread.join();
			cameraBackGroundThread = null;
			cameraHandler = null;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

//	/**
//	 * 取摄像头id和statiics
//	 */
//	private void getCameraIdAndStatics(){
//		try {
//			String[] ids = cameraManager.getCameraIdList();
//			for (String id : ids){
//				CameraCharacteristics statics = cameraManager.getCameraCharacteristics(id);
//				if (isHardwareLevelSupported(statics, CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_FULL)){
//					if (statics.get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_FRONT){
//						logger.info("getFront camera");
//						usedCameraId = id;
//						cameraCharacteristics = statics;
//					}else {
//						logger.info("getBack camera");
//					}
//				}
//			}
//		} catch (CameraAccessException e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 判断相机的 Hardware Level 是否大于等于指定的 Level。
//	 */
//	private boolean isHardwareLevelSupported(CameraCharacteristics characteristics, int requiredLevel) {
//		ArrayList<Integer> sortedLevels = new ArrayList<Integer>(){{
//			add(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_LEGACY);
//			add(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_LIMITED);
//			add(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_FULL);
//			add(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_3);
//		}};
//		int deviceLevel = characteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL);
//		if (requiredLevel == deviceLevel) {
//			return true;
//		}
//		for (int sortedLevel : sortedLevels) {
//			if (requiredLevel == sortedLevel) {
//				return true;
//			} else if (deviceLevel == sortedLevel) {
//				return false;
//			}
//		}
//		return false;
//	}
