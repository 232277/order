package com.dyc.order.cashier.mvp.activity.face;

import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dyc.order.cashier.R;
import com.dyc.order.cashier.contact.MemberContact;
import com.dyc.order.cashier.function.UvcCameraProvider;
import com.dyc.order.cashier.mvp.activity.base.BaseCountDownActivity;
import com.dyc.order.cashier.data.local.MemberCenter;
import com.serenegiant.usb.widget.CameraViewInterface;

import java.nio.ByteBuffer;
import java.util.Timer;
import java.util.TimerTask;

public class FaceCaptureByUvcActivity extends BaseCountDownActivity<
        MemberContact.MemberModel,
		MemberContact.MemberView,
		MemberContact.MemberPresent>
		implements
//		CameraDialog.CameraDialogParent,
		View.OnClickListener,
		MemberContact.MemberView {
	public static int SUCCESS_GET_FACE = 0;
	public static int FAIL_GET_FACE = -1;

	private TextView countDownStr;
	private TextureView textureView;
	private ImageView loading;
	private ImageView picRes;
	private ImageView arrow;
	private TextView title;
	private View takePhoto;
	private View reTakePhoto;
	private View confirm;

	private Timer timer;
	private UvcCameraProvider uvcCameraProvider;
	private boolean takePic = false;
	private Bitmap facePic;

	@Override
	public int getLayoutId() {
		return R.layout.activity_face_capture;
	}

	@Override
	public void onInitView(View view) {
		showActionBar(false);
		countDownStr = findViewById(R.id.countDownStr);
		textureView = findViewById(R.id.textureView);
		loading = findViewById(R.id.loadingImg);
		picRes = findViewById(R.id.picRes);
		arrow = findViewById(R.id.arrow);
		title = findViewById(R.id.title);
		takePhoto = findViewById(R.id.takePhoto);
		reTakePhoto = findViewById(R.id.reTakePhoto);
		confirm = findViewById(R.id.confirm);

		init();
	}

	private void init(){
		logger.info("初始化");
		uvcCameraProvider = new UvcCameraProvider(this, (CameraViewInterface)textureView);
		takePhoto.setOnClickListener(this);
		setLoading(true);
		startCountDown();
	}

	@Override
	protected void onResume() {
		super.onResume();
		logger.info("可视化");
		uvcCameraProvider.setUvcCameraListener(new UvcCameraProvider.UvcCameraListener() {
			@Override
			public void onPreview(ByteBuffer byteBuffer) {
				if (loading.getVisibility() == View.VISIBLE){
					loading.setVisibility(View.INVISIBLE);
				}
				takePic = true;
			}

			@Override
			public void onGetPic(Bitmap bitmap) {
				facePic = bitmap;
				FaceCaptureByUvcActivity.this.runOnUiThread(() -> {
					//TODO 似乎是老的一批机器会摄像头镜像，新的暂时没发现
//					if (uvcCameraProvider.getCameraDeviceInfo() == CameraConstant.CameraDeviceInfo.AOBI){
//						facePic = BitmapUtils.mirror(facePic);
//					}
					picRes.setVisibility(View.VISIBLE);
					picRes.setImageBitmap(facePic);
				});
				logger.info("set picRes");
			}
		});
	}

	private void takePic(){
		logger.info("拍照");
		setBtnVisibility(false);
		takePic = false;
		takePhoto.setOnClickListener(null);
		if (uvcCameraProvider != null){
			uvcCameraProvider.takePic(true);
		}
	}

	private void restartCamera(){
		logger.info("重开摄像头");
		if (uvcCameraProvider != null){
			uvcCameraProvider.startPreview();
		}
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				takePhoto.setOnClickListener(FaceCaptureByUvcActivity.this);
			}
		}, 1200);
	}

	@Override
	public void onClick(View v) {
		logger.info("点击的view id:" + v.getId());
		switch (v.getId()){
			case R.id.leftView:
			case R.id.title:
			case R.id.arrow:
				finish();
				break;
			case R.id.jump:
				finish();
				break;
			case R.id.takePhoto:
				if (takePic){
					takePic();
				}
				break;
			case R.id.reTakePhoto:
				restartCamera();
				setBtnVisibility(true);
				break;
			case R.id.confirm:
				showLoading(R.string.dialog_pls_wait);
				new Thread(()->{
					getPresenter().uploadMemberFace(facePic, MemberCenter.getMemberInfoData().getId());
				}).start();
//				DataCache.addData(ActivityOperationField.FACE_PIC_INFO, facePic);
//				setResult(SUCCESS_GET_FACE);
				break;
		}
	}

	@Override
	public void uploadMemberFaceSuccess(String res) {
		hideDialog();
		finish();
	}

	@Override
	public void uploadMemberFaceFail(Throwable throwable) {
		hideDialog();
		finish();
	}

	private void setBtnVisibility(boolean isTakePhoto){
		logger.info("修改按钮显示");
		runOnUiThread(()->{
			takePhoto.setVisibility(isTakePhoto ? View.VISIBLE : View.GONE);
			reTakePhoto.setVisibility(isTakePhoto ? View.INVISIBLE : View.VISIBLE);
			confirm.setVisibility(isTakePhoto ? View.INVISIBLE : View.VISIBLE);

			setLoading(isTakePhoto);
		});
	}

	private void setLoading(boolean showLoading){
		if (showLoading){
			if (picRes != null && loading != null){
				picRes.setVisibility(View.GONE);
				loading.setVisibility(View.VISIBLE);
				loading.setImageResource(R.drawable.anim_camera_loading);
				AnimationDrawable anim = (AnimationDrawable) loading.getDrawable();
				anim.start();
			}
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		if (timer != null){
			timer.cancel();
		}
		if (uvcCameraProvider != null){
			uvcCameraProvider.release();
		}
		super.onDestroy();
	}

	@Override
	protected void onCountDown(long countTime) {
		super.onCountDown(countTime);
		runOnUiThread(()->countDownStr.setText(getFormatCountDownStr(countTime)));
	}

//	/**
//	 * to access from CameraDialog
//	 * @return
//	 */
//	@Override
//	public USBMonitor getUSBMonitor() {
//		return null;
//	}
//
//	@Override
//	public void onDialogResult(boolean canceled) { }

	@Override
	public MemberContact.MemberModel initM() {
		return new MemberContact.MemberModel() {
		};
	}

	@Override
	public MemberContact.MemberPresent initP() {
		return new MemberContact.MemberPresent() {
		};
	}
}
