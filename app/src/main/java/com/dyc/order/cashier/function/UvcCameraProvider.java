package com.dyc.order.cashier.function;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.usb.UsbDevice;
import android.text.TextUtils;
import android.view.Surface;
import android.view.TextureView;

import com.dyc.order.cashier.constant.CameraConstant;
import com.dyc.administrator.toollibrary.utils.BitmapUtils;
import com.dyc.administrator.toollibrary.utils.MLogger;
import com.jiangdg.usbcamera.UVCCameraHelper;
import com.serenegiant.usb.common.AbstractUVCCameraHandler;
import com.serenegiant.usb.widget.CameraViewInterface;

import java.nio.ByteBuffer;

/**
 * func:
 * author:丁语成 on 2020/4/27 18:33
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class UvcCameraProvider {
	private MLogger logger = MLogger.getLogger(this.getClass());
	public static int DEFAULT_WIDTH = 640;
	public static int DEFAULT_HEIGHT = 480;
	private UVCCameraHelper mCameraHelper;
	private CameraViewInterface mUVCCameraView;
	private UvcCameraListener uvcCameraListener;

	private CameraConstant.CameraDeviceInfo cameraDeviceInfo;
	private Activity activity;

	private boolean isPreview;
	private boolean isRequest;

	public UvcCameraProvider(Activity activity, CameraViewInterface mUVCCameraView){
		init(activity, mUVCCameraView);
	}

	public void setUvcCameraListener(UvcCameraListener uvcCameraListener) {
		this.uvcCameraListener = uvcCameraListener;
	}

	public void takePic(boolean stopPreview){
		if (mCameraHelper != null){
			String path = UVCCameraHelper.ROOT_PATH +"USBCamera/images/temp" + UVCCameraHelper.SUFFIX_JPEG;
			mCameraHelper.capturePicture(path, picPath -> {
				if(TextUtils.isEmpty(picPath)) {
					return;
				}
//				File file = new File(picPath);
				logger.info("pic path" + picPath);
				Bitmap bitmap = BitmapFactory.decodeFile(picPath);
				bitmap = BitmapUtils.rotate(bitmap, cameraDeviceInfo.rotation);
				if (uvcCameraListener != null){
					uvcCameraListener.onGetPic(bitmap);
				}
				if (stopPreview){
					mCameraHelper.stopPreview();
				}
			});
		}
	}

	public CameraConstant.CameraDeviceInfo getCameraDeviceInfo() {
		return cameraDeviceInfo;
	}

	public void startPreview(){
		if (mCameraHelper != null){
			mCameraHelper.startPreview(mUVCCameraView);
		}
	}

	public void stopPreview(){
		if (mCameraHelper != null){
			mCameraHelper.stopPreview();
		}
	}

	public void onResume(){
		if (mCameraHelper != null){
			mCameraHelper.registerUSB();
		}
	}

	public void onStop(){
		if (mCameraHelper != null){
			mCameraHelper.unregisterUSB();
		}
	}

	public void release(){
		if (mCameraHelper != null){
			mCameraHelper.release();
		}
	}

	private void init(Activity activity, CameraViewInterface uvcCameraView){
		this.mUVCCameraView = uvcCameraView;
		mUVCCameraView.setCallback(mCallback);
		this.activity = activity;
		mCameraHelper = UVCCameraHelper.getInstance();
		//预览大小
		mCameraHelper.setDefaultPreviewSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		// set default frame format，defalut is UVCCameraHelper.Frame_FORMAT_MPEG
		// if using mpeg can not record mp4,please try yuv
//		mCameraHelper.setDefaultFrameFormat(UVCCameraHelper.FRAME_FORMAT_MJPEG);
		// step.1 initialize UVCCameraHelper
		mCameraHelper.initUSBMonitor(activity, mUVCCameraView, onMyDevConnectListener);
		//取摄像头旋转
		for (UsbDevice device : mCameraHelper.getUsbDeviceList()){
			logger.info("device name:" + device.getProductName());
			cameraDeviceInfo = CameraConstant.getCameraInfoNow(device.getProductName());
			if (cameraDeviceInfo != null){
				break;
			}
		}
		if (cameraDeviceInfo == null){
			cameraDeviceInfo = CameraConstant.CameraDeviceInfo.AOBI;
		}else if (cameraDeviceInfo == CameraConstant.CameraDeviceInfo.AOBI){
			//TODO 似乎是老的一批机器会摄像头镜像，新的暂时没发现
//			logger.info("奥比摄像头需要镜像");
//			((TextureView)this.mUVCCameraView).setRotationY(180);
		}
		//等于0会显示不出来
		if (cameraDeviceInfo.getRotation() != 0){
			((TextureView)this.mUVCCameraView).setRotation(cameraDeviceInfo.getRotation());
		}
		//帧监听
		mCameraHelper.setOnPreviewFrameListener(new AbstractUVCCameraHandler.OnPreViewResultListener() {
			@Override
			public void onPreviewResult(ByteBuffer frame) {
				if (uvcCameraListener != null){
					uvcCameraListener.onPreview(frame);
				}
			}
		});
	}

	private UVCCameraHelper.OnMyDevConnectListener onMyDevConnectListener = new UVCCameraHelper.OnMyDevConnectListener() {

		@Override
		public void onAttachDev(UsbDevice device) {
			// request open permission(must have)
			if (device != null){
				logger.info("attach device: device name:" + device.getDeviceName()
						+ " product name:" + device.getProductName());
				if (mCameraHelper != null
						&& cameraDeviceInfo.getProductName().equals(device.getProductName())
						&& mCameraHelper.getUSBMonitor() != null) {
					mCameraHelper.getUSBMonitor().requestPermission(device);
					logger.info("request permission" + device.getDeviceName());
				}
			}
		}

		@Override
		public void onDettachDev(UsbDevice device) {
			// close camera(must have)
			if (mCameraHelper != null
					&& cameraDeviceInfo.getProductName().equals(device.getProductName())
					&& mCameraHelper.getUSBMonitor() != null
			&& isRequest){
				isRequest = false;
				mCameraHelper.closeCamera();
			}
		}

		@Override
		public void onConnectDev(UsbDevice device, boolean isConnected) {
			logger.info("connect device name:" + device.getDeviceName());
//			if (isConnected){
//				isPreview = true;
////				mCameraHelper.startPreview(mUVCCameraView);
//			}else {
//				isPreview = false;
//			}
		}

		@Override
		public void onDisConnectDev(UsbDevice device) {

		}
	};

	private CameraViewInterface.Callback mCallback = new CameraViewInterface.Callback(){
		@Override
		public void onSurfaceCreated(CameraViewInterface view, Surface surface) {
			mCameraHelper.registerUSB();
			// must have
			if (!isPreview && mCameraHelper.isCameraOpened()) {
				logger.info("startPreview");
				mCameraHelper.startPreview(mUVCCameraView);
				isPreview = true;
			}else {
				logger.info("not in preview mode");
			}
		}

		@Override
		public void onSurfaceChanged(CameraViewInterface view, Surface surface, int width, int height) {
		}

		@Override
		public void onSurfaceDestroy(CameraViewInterface view, Surface surface) {
			mCameraHelper.unregisterUSB();
			// must have
			if (isPreview && mCameraHelper.isCameraOpened()) {
				logger.info("stopPreview");
				mCameraHelper.stopPreview();
				isPreview = false;
			}else {
				logger.info("no need to stop preview");
			}
		}
	};

	public interface UvcCameraListener{
		default void onPreview(ByteBuffer byteBuffer){}
		void onGetPic(Bitmap bitmap);
	}
}
