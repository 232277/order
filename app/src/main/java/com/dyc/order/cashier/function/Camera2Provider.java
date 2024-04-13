package com.dyc.order.cashier.function;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;

import com.dyc.administrator.toollibrary.utils.MLogger;
import com.dyc.administrator.toollibrary.utils.PermissionUtils;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;


/**
 * func:
 * author:丁语成 on 2020/4/20 21:50
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class Camera2Provider {
	private final static MLogger logger = MLogger.getLogger(Camera2Provider.class);
	public final static String PREVIEW_THREAD_NAME = "camera2 preview";
	public final static String PHOTO_THREAD_NAME = "camera2 take photo";
	private Activity context;
	private TextureView textureView;

	private Size previewSize;
	private String cameraId;
	private Handler cameraHandler;
	private CameraManager cameraManager;
	private CameraDevice cameraDevice;
	private CaptureRequest.Builder captureBuilder;
	private CameraCaptureSession cameraCaptureSession;
	private ImageReader imageReader;
	private OnImageStateListener onImageStateListener;

	public Camera2Provider(Activity activity) {
		this.context = activity;
		HandlerThread handlerThread = new HandlerThread(PREVIEW_THREAD_NAME);
		handlerThread.start();
		cameraHandler = new Handler(handlerThread.getLooper());
	}

	public void setOnImageStateListener(OnImageStateListener onImageStateListener) {
		this.onImageStateListener = onImageStateListener;
	}

	public void initTexture(TextureView textureView, int facing) {
		this.textureView = textureView;
		textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
			@Override
			public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
				openCamera(facing);
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

	public void openCamera(int facing) {
		cameraManager = context.getSystemService(CameraManager.class);
		try {
			String[] ids = cameraManager.getCameraIdList();
			for (String id : ids) {
				//取得相机属性
				CameraCharacteristics characteristics = cameraManager.getCameraCharacteristics(id);
				Integer currentFacing = characteristics.get(CameraCharacteristics.LENS_FACING);
				if (currentFacing != null && facing == currentFacing) {
					StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
					if (map != null) {
						//获取相机支持的最大拍照尺寸
//						chooseOptimalSize()
//						previewSize = Collections.max(Arrays.asList(map.getOutputSizes(ImageFormat.JPEG)),
//								(o1, o2) -> Long.signum(o1.getWidth() * o1.getHeight() - o2.getHeight() * o2.getWidth()));
						previewSize = new Size(textureView.getMeasuredWidth(), textureView.getHeight());
						imageReader = ImageReader.newInstance(previewSize.getWidth(), previewSize.getHeight(),
								ImageFormat.JPEG, 2);
						imageReader.setOnImageAvailableListener(imageCaptureListener, cameraHandler);
						cameraId = id;
						break;
					}
				}
			}
			String[] permissions = new String[]{Manifest.permission.CAMERA};
			PermissionUtils permissionUtils = PermissionUtils.getInstance();
			permissionUtils.chekPermissions(context, permissions, new PermissionUtils.IPermissionsResult() {
				@SuppressLint("MissingPermission")
				@Override
				public void passPermissons() {
					try {
						cameraManager.openCamera(cameraId, stateCallback, cameraHandler);
						logger.info("open camera " + cameraId);
					} catch (CameraAccessException e) {
						e.printStackTrace();
					}
				}

				@Override
				public void forbitPermissons() {

				}
			});
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	private CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
		@Override
		public void onOpened(@NonNull CameraDevice camera) {
			logger.info("opened");
			cameraDevice = camera;
			SurfaceTexture surfaceTexture = textureView.getSurfaceTexture();
			surfaceTexture.setDefaultBufferSize(previewSize.getWidth(), previewSize.getHeight());
			Surface previewSurface = new Surface(surfaceTexture);
			try {
				captureBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
				captureBuilder.addTarget(previewSurface);
				cameraDevice.createCaptureSession(Arrays.asList(previewSurface, imageReader.getSurface()), captureStateCallBack, cameraHandler);
			}catch (Exception e){
				e.printStackTrace();
			}
		}

		@Override
		public void onDisconnected(@NonNull CameraDevice camera) {
			camera.close();
		}

		@Override
		public void onError(@NonNull CameraDevice camera, int error) {
			camera.close();
		}
	};

	private CameraCaptureSession.StateCallback captureStateCallBack = new CameraCaptureSession.StateCallback() {
		@Override
		public void onConfigured(@NonNull CameraCaptureSession session) {
			cameraCaptureSession = session;
			startPreview();
		}

		@Override
		public void onConfigureFailed(@NonNull CameraCaptureSession session) {
		}
	};

	public void startPreview(){
		try {
			cameraCaptureSession.setRepeatingRequest(captureBuilder.build(),
					new CameraCaptureSession.CaptureCallback() {}, cameraHandler);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public void stopPreview(){
		try {
			cameraCaptureSession.stopRepeating();
		} catch (CameraAccessException e) {
			e.printStackTrace();
		}
	}

	public void takePhoto(){
		takePhoto(true);
	}

	public void takePhoto(boolean stopPreview){
		if (cameraCaptureSession != null){
			try {
				CaptureRequest.Builder builder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
//				builder.setTag();
				builder.addTarget(imageReader.getSurface());
				cameraCaptureSession.capture(builder.build(), new CameraCaptureSession.CaptureCallback() {
					@Override
					public void onCaptureCompleted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull TotalCaptureResult result) {
						super.onCaptureCompleted(session, request, result);
					}
				}, cameraHandler);
				if (stopPreview){
					stopPreview();
				}
			} catch (CameraAccessException e) {
				e.printStackTrace();
			}
		}
	}

	private ImageReader.OnImageAvailableListener imageCaptureListener = new ImageReader.OnImageAvailableListener() {

		@Override
		public void onImageAvailable(ImageReader reader) {
			//获取最新的一帧的Image
			Image image = reader.acquireLatestImage();
			logger.info("capture");
			//因为是ImageFormat.JPEG格式，所以 image.getPlanes()返回的数组只有一个，也就是第0个。
			ByteBuffer byteBuffer = image.getPlanes()[0].getBuffer();
			byte[] bytes = new byte[byteBuffer.remaining()];
			byteBuffer.get(bytes);
			//ImageFormat.JPEG格式直接转化为Bitmap格式。
			Bitmap temp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
			Matrix matrix = new Matrix();
//			matrix.setRotate(-90, 0,0);
//			matrix.setScale(-1,1);
			//因为摄像机数据默认是横的，所以需要旋转90度。
			Bitmap newBitmap = Bitmap.createBitmap(temp, 0, 0, temp.getWidth(), temp.getHeight(), matrix, false);
			//抛出去展示或存储。
			if (onImageStateListener != null){
				onImageStateListener.onGetImage(newBitmap);
			}
			//一定需要close，否则不会收到新的Image回调。
			image.close();
		}
	};

	public void close(){
		if (cameraCaptureSession != null){
			cameraCaptureSession.close();
			cameraCaptureSession = null;
		}
		if (imageReader != null){
			imageReader.close();
			imageReader = null;
		}
		if (cameraDevice != null){
			cameraDevice.close();
			cameraDevice = null;
		}
	}

	/**
	 * 获取一个合适的相机预览尺寸
	 *
	 * @param choices           支持的预览尺寸列表
	 * @param textureViewWidth  相对宽度
	 * @param textureViewHeight 相对高度
	 * @param maxWidth          可以选择的最大宽度
	 * @param maxHeight         可以选择的最大高度
	 * @param aspectRatio       宽高比
	 * @return 最佳预览尺寸
	 */
	private static Size chooseOptimalSize(Size[] choices, int textureViewWidth, int textureViewHeight,
										  int maxWidth, int maxHeight, Size aspectRatio) {
		List<Size> bigEnough = new ArrayList<>();
		List<Size> notBigEnough = new ArrayList<>();
		int w = aspectRatio.getWidth();
		int h = aspectRatio.getHeight();
		for (Size option : choices) {
			if (option.getWidth() <= maxWidth && option.getHeight() <= maxHeight &&
					option.getHeight() == option.getWidth() * h / w) {
				if (option.getWidth() >= textureViewWidth &&
						option.getHeight() >= textureViewHeight) {
					bigEnough.add(option);
				} else {
					notBigEnough.add(option);
				}
			}
		}
		if (bigEnough.size() > 0) {
			return Collections.min(bigEnough, (lhs, rhs)-> Long.signum(
					(long) lhs.getWidth() * lhs.getHeight()
							- (long) rhs.getWidth() * rhs.getHeight()));
		} else if (notBigEnough.size() > 0) {
			return Collections.max(notBigEnough, (lhs, rhs)-> Long.signum(
					(long) lhs.getWidth() * lhs.getHeight()
							- (long) rhs.getWidth() * rhs.getHeight()));
		} else {
			logger.info("Couldn't find any suitable preview size");
			return choices[0];
		}
	}


	public interface OnImageStateListener{
		void onGetImage(Bitmap bitmap);
		default void onPreviewData(byte[] data){}
		default void onOpen(){}
	}
}
