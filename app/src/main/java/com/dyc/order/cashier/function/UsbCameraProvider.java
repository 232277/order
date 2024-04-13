package com.dyc.order.cashier.function;

import android.app.Activity;
import android.graphics.SurfaceTexture;
import android.hardware.usb.UsbDevice;
import android.view.Surface;
import android.view.TextureView;

import com.dyc.administrator.toollibrary.utils.MLogger;
import com.serenegiant.usb.USBMonitor;
import com.serenegiant.usb.UVCCamera;

import java.nio.ByteBuffer;
import java.util.Timer;

/**
 * func:
 * author:丁语成 on 2020/4/24 16:04
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class UsbCameraProvider {
	private MLogger logger = MLogger.getLogger(this.getClass());
	private Object mSync;
	private USBMonitor usbMonitor;
	private UVCCamera uvcCamera;
	private String cameraDeviceName;

	private int previewWidth;
	private int previewHeight;
	private int previewMode;
	private int imageFormat;
	private CameraWorkListener listener;
	private Timer timer;
	
	private TextureView textureView;
	private Surface previewSurface;
	private Activity activity;

	public UsbCameraProvider(Activity activity, TextureView textureView){
		this(activity, textureView, "/dev/bus/usb/001/005");
	}

	public UsbCameraProvider(Activity activity, TextureView textureView, String cameraDeviceName){
		mSync = new Object();
		this.activity = activity;
		this.textureView = textureView;
		this.cameraDeviceName = cameraDeviceName;
		usbMonitor = new USBMonitor(activity, mOnDeviceConnectListener);
		usbMonitor.register();
		previewWidth = UVCCamera.DEFAULT_PREVIEW_WIDTH;
		previewHeight = UVCCamera.DEFAULT_PREVIEW_HEIGHT;
		previewMode = UVCCamera.FRAME_FORMAT_MJPEG;
		imageFormat = UVCCamera.PIXEL_FORMAT_RGB565;
	}

	public void startCamera(){
		synchronized (mSync){
			if (usbMonitor == null){
				return;
			}
			if (!usbMonitor.isRegistered()){
				usbMonitor.register();
			}
			boolean foundCamera = false;
			if (usbMonitor != null){
				for (UsbDevice device : usbMonitor.getDeviceList()){
					if (cameraDeviceName.equals(device.getDeviceName())){
						usbMonitor.requestPermission(device);
						logger.info("found camera");
						foundCamera = true;
						break;
					}
				}
			}
			if (listener != null){
				listener.onConnect(foundCamera);
			}
		}
	}

	private synchronized void startPreview(){
		synchronized (mSync) {
			if (uvcCamera != null) {
				if (!usbMonitor.isRegistered()){
					usbMonitor.register();
				}
				uvcCamera.startPreview();
			}
		}
	}

	public synchronized void releaseCamera() {
		synchronized (mSync) {
			if (uvcCamera != null) {
				try {
					uvcCamera.close();
					uvcCamera.destroy();
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
			if (previewSurface != null) {
				previewSurface.release();
				previewSurface = null;
			}
		}
	}

	public synchronized void stopCamera(){
		if (uvcCamera != null) {
			uvcCamera.stopPreview();
		}
		if (usbMonitor != null) {
			usbMonitor.unregister();
		}
	}

	public synchronized void destroyCamera(){
		synchronized (mSync) {
			releaseCamera();
			if (usbMonitor != null) {
				usbMonitor.destroy();
				usbMonitor = null;
			}
			if (timer != null){
				timer.cancel();
				timer = null;
			}
			textureView = null;
		}
	}

	private final USBMonitor.OnDeviceConnectListener mOnDeviceConnectListener = new USBMonitor.OnDeviceConnectListener() {
		@Override
		public void onAttach(final UsbDevice device) {
			logger.info("USB_DEVICE_ATTACHED");
		}

		@Override
		public void onConnect(final UsbDevice device, final USBMonitor.UsbControlBlock ctrlBlock, final boolean createNew) {
			logger.info("USB_DEVICE_CONNECT");
			releaseCamera();
			final UVCCamera camera = new UVCCamera();
			try {
				camera.open(ctrlBlock);
			}catch (Exception e){
				e.printStackTrace();
				if (listener != null){
					listener.onError(e);
				}
				camera.close();
				camera.destroy();
				return;
			}
			if (previewSurface != null) {
				previewSurface.release();
				previewSurface = null;
				logger.info("release exist");
			}
			try {
				camera.setPreviewSize(previewWidth, previewHeight, imageFormat);
			} catch (final IllegalArgumentException e) {
				e.printStackTrace();
				// fallback to YUV mode
				try {
					camera.setPreviewSize(previewWidth, previewHeight, UVCCamera.DEFAULT_PREVIEW_MODE);
				} catch (final IllegalArgumentException e1) {
					e1.printStackTrace();
					camera.destroy();
					return;
				}
			}
			if (textureView == null){
				return;
			}
			final SurfaceTexture st = textureView.getSurfaceTexture();
			if (st != null) {
				previewSurface = new Surface(st);
				camera.setPreviewDisplay(previewSurface);
				camera.setFrameCallback(frame -> {
					if (listener != null){
						listener.onFrameData(frame);
					}
				}, imageFormat/*UVCCamera.PIXEL_FORMAT_NV21*/);
				logger.info("setFrameCallback");
				camera.startPreview();
			}
			synchronized (mSync) {
				uvcCamera = camera;
			}
			logger.info("opened");
		}

		@Override
		public void onDisconnect(final UsbDevice device, final USBMonitor.UsbControlBlock ctrlBlock) {
			logger.info("USB_DEVICE_DISCONNECT");
			// XXX you should check whether the coming device equal to camera device that currently using
			releaseCamera();
		}

		@Override
		public void onCancel(UsbDevice device) {
			logger.info("USB_DEVICE_CANCEL");
		}

		@Override
		public void onDettach(final UsbDevice device) {
			logger.info("USB_DEVICE_DETACHED");
		}
	};

	public USBMonitor getUsbMonitor() {
		return usbMonitor;
	}

	public void setPreviewSize(int width, int height){
		previewWidth = width;
		previewHeight = height;
	}

	public void setPreviewMode(int previewMode) {
		this.previewMode = previewMode;
	}

	public void setImageFormat(int imageFormat) {
		this.imageFormat = imageFormat;
	}

	public void setListener(CameraWorkListener listener) {
		this.listener = listener;
	}

	public interface CameraWorkListener{
		default void onConnect(boolean foundCamera){}
		default void onFrameData(ByteBuffer frameBuffer){}
		default void onError(Exception e){}
	}
}
