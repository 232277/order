package com.dyc.order.cashier;

/**
 * func:
 * author:丁语成 on 2020/4/22 17:12
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class UselessCode {
	private static final String TAG = "UselessCode";
//	public void takePhoto(){
//		//打开相机的Intent
//		Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//		//这句作用是如果没有相机则该应用不会闪退，要是不加这句则当系统没有相机应用的时候该应用会闪退
//		if(takePhotoIntent.resolveActivity(getPackageManager())!=null){
//			//创建用来保存照片的文件
//			File imageFile = createImageFile();
//			if (imageFile != null){
//				logger.info("create img file success");
//			}
//			if(imageFile!=null){
//				if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.N){
//					//7.0以上要通过FileProvider将File转化为Uri
//					mImageUri = FileProvider.getUriForFile(this,getString(R.string.provider_name),imageFile);
//				}else {
//					//7.0以下则直接使用Uri的fromFile方法将File转化为Uri
//					mImageUri = Uri.fromFile(imageFile);
//				}
//				//将用于输出的文件Uri传递给相机
//				takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
//				//打开相机
//				startActivityForResult(takePhotoIntent, TAKE_PHOTO_REQUEST_CODE);
//			}
//		}
//	}
//
//	/**
//	 * 创建用来存储图片的文件，以时间来命名就不会产生命名冲突
//	 * @return 创建的图片文件
//	 */
//	private File createImageFile() {
//		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
//				.format(new Date());
//		String imageFileName = "JPEG_"+timeStamp+"_";
//		File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//		File imageFile = null;
//		try {
//			imageFile = File.createTempFile(imageFileName,".jpg",storageDir);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return imageFile;
//	}
//
//	else if(requestCode == TAKE_PHOTO_REQUEST_CODE && resultCode == RESULT_OK){
//		showLoading(R.string.label_register_member_face_processing);
//		new Thread(()->{
//			/*如果拍照成功，将Uri用BitmapFactory的decodeStream方法转为Bitmap*/
//			Bitmap bitmap = null;
//			try {
//				bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(mImageUri));
//				List<Bitmap> res = FaceDectTask.cutFaces(bitmap, 1);
//				if (res != null && !res.isEmpty()){
//					faceBitmap = bitmap;
//					runOnUiThread(()->{
//						facePic.setImageBitmap(res.get(0));
//						facePic.setVisibility(View.VISIBLE);
//						recognize.setText(R.string.label_register_member_retry_face_recognize);
//					});
//					logger.error("success get");
//				}else {
//					showToast(R.string.tip_face_recog_fail);
//					logger.error("success get but no face");
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//				showToast(R.string.tip_face_recog_fail);
//			}finally {
//				hideDialog();
//			}
//		}).start();
//	}


	public static void fun(){
//		Observable.create(new ObservableOnSubscribe< Integer >() {
//			@Override
//			public void subscribe(ObservableEmitter< Integer > e) throws Exception {
//				e.onNext(1);
//				e.onNext(2);
//				e.onNext(3);
//				e.onComplete();
//			}
//		})
//				.repeatWhen(new Function < Observable < Object > , ObservableSource<? >> () {
//					@Override
//					public ObservableSource <? > apply(Observable < Object > objectObservable) throws Exception {
////						return Observable.empty();
////						return Observable.error(new Exception("404"));
//						return Observable.just(1, 2, 3, 4, 5);
//					}
//				})
//				.subscribe(new Observer < Integer > () {
//					@Override
//					public void onSubscribe(Disposable d) {
//						System.out.println(TAG + "===================onSubscribe ");
//					}
//
//					@Override
//					public void onNext(Integer integer) {
//						System.out.println(TAG + "===================onNext " + integer);
//					}
//
//					@Override
//					public void onError(Throwable e) {
//						System.out.println(TAG + "===================onError ");
//					}
//
//					@Override
//					public void onComplete() {
//						System.out.println(TAG + "===================onComplete ");
//					}
//				});
	}

	public static void main(String[] args){
//		fun();

	}
}
