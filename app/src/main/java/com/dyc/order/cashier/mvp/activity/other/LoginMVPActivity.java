package com.dyc.order.cashier.mvp.activity.other;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.IBinder;
import android.provider.Settings;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dyc.order.cashier.contact.LoginContact;
import com.dyc.administrator.toollibrary.utils.PermissionUtils;
import com.dyc.administrator.toollibrary.utils.ViewUtils;
//import com.centerm.cpay.appcloud.remote.IVersionInfoCallback;
//import com.centerm.cpay.appcloud.remote.IVersionInfoProvider;
import com.dyc.order.cashier.R;
import com.dyc.order.cashier.base.MyApplication;
import com.dyc.order.cashier.base.activity.BaseMvpActivityimpl;
import com.dyc.order.cashier.data.response.DownloadAppData;
import com.dyc.order.cashier.view.UpdateVersionShowDialog;

import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class LoginMVPActivity extends BaseMvpActivityimpl<LoginContact.LoginModel, LoginContact.LoginView, LoginContact.LoginPresent> implements LoginContact.LoginView {
	private Button login;
	private EditText account;
	private EditText pwd;
	private boolean canUpdate;
//	private VersionInfo info;
	private DownloadAppData downloadAppData;

	@Override
	public int getLayoutId() {
		return R.layout.activity_login;
	}

	@Override
	public void onInitView(View view) {
		account = view.findViewById(R.id.account);
		pwd = view.findViewById(R.id.password);
		account.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
		pwd.setFilters(new InputFilter[]{new InputFilter.LengthFilter(12)});

		login = view.findViewById(R.id.login);
		login.setOnClickListener((View v) -> {
			String accountStr = account.getText().toString();
			String pwdStr = pwd.getText().toString();
			if (accountStr.equals("")) {
				showToast(R.string.tip_account_null);
				return;
			}
			if (pwdStr.equals("")) {
				showToast(R.string.tip_pwd_null);
				return;
			}
			new Thread(() -> {
				getPresenter().login(accountStr, pwdStr);
			}).start();
		});
		showActionBar(false);
		ViewUtils.getAndroiodScreenProperty(this);
	}

	@Override
	public void doAfterInitView(View v) {
		super.doAfterInitView(v);
		bindUpdateService();
		PermissionUtils.getInstance().chekPermissions(this, MyApplication.NEEDED_PERMISSIO,
				new PermissionUtils.IPermissionsResult() {
					@Override
					public void passPermissons() {
						logger.info("权限申请通过");
					}

					@Override
					public void forbitPermissons() {
						logger.info("permission denied");
						finishAll();
					}
				});
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		PermissionUtils.getInstance().onRequestPermissionsResult(this, requestCode, permissions, grantResults);
	}

	@Override
	public void onSuccess() {
		if (canUpdate) {
			logger.info("发现新版本");
//			downloadAppData = new DownloadAppData();
//			downloadAppData.content = getString(R.string.label_update_version_dialog_word_update_content) + ": " + info.getUpdateInfo();
////			downloadAppData.md5 = info.getAppMd5();
//			downloadAppData.title = getString(R.string.label_update_version_dialog_sentence_new_version_found) + info.getVersionName();
//			downloadAppData.url = info.getDownloadUrl();
//			downloadAppData.versionCode = String.valueOf(info.getVersionCode());

			if (!getPackageManager().canRequestPackageInstalls()){
				Uri packageURI = Uri.parse("package:" + getPackageName());
				Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
				startActivityForResult(intent, GET_UNKNOWN_APP_SOURCES);
			}else {
				UpdateVersionShowDialog updateVersionShowDialog = UpdateVersionShowDialog.show(LoginMVPActivity.this, downloadAppData);
				updateVersionShowDialog.setListener(() -> handlerPost());
			}
		} else {
			logger.info("无需更新");
			startActivity(new Intent(this, GuidePageActivity.class));
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == GET_UNKNOWN_APP_SOURCES && resultCode == RESULT_OK){
			if (checkSelfPermission(Manifest.permission.INSTALL_PACKAGES) == PackageManager.PERMISSION_GRANTED){
				UpdateVersionShowDialog updateVersionShowDialog = UpdateVersionShowDialog.show(LoginMVPActivity.this, downloadAppData);
				updateVersionShowDialog.setListener(() -> handlerPost());
			}else {
				logger.info("无应用安装权限");
				canUpdate = false;
//				info = null;
				downloadAppData = null;
				startActivity(new Intent(this, GuidePageActivity.class));
			}
		}
	}

	private void bindUpdateService(){
		if (checkPackInfo("com.centerm.cpay.applicationshop")) {
			Intent intent = new Intent();
			intent.setAction("com.centerm.cpay.appcloud.REMOTE_SERVICE");
			intent.setPackage("com.centerm.cpay.applicationshop");
			bindService(intent, connection, Context.BIND_AUTO_CREATE);
		}
	}

	public boolean checkPackInfo(String packname) {
		PackageInfo packageInfo = null;
		try {
			packageInfo = getPackageManager().getPackageInfo(packname, 0);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return packageInfo != null;
	}

	private ServiceConnection connection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.w("tag", "服务已连接");

//			IVersionInfoProvider versionInfoProvider = IVersionInfoProvider.Stub.asInterface(service);
//			try {
//				versionInfoProvider.getLatestVersion(getPackageName(), new IVersionInfoCallback.Stub() {
//					@Override
//					public void onSuccess(VersionInfo info) throws RemoteException {
//						LoginMVPActivity.this.info = info;
//						logger.info(info.toString());
//						long verCode = Integer.MAX_VALUE;
//						try {
//							PackageManager packageManager = getPackageManager();
//							PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
//							verCode = packageInfo.getLongVersionCode();
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//
//						canUpdate = info.getVersionCode() > verCode;
////						canUpdate = true;
//						logger.info("当前版本:" + verCode + " 服务器版本:" + info.getVersionCode());
//					}
//
//					@Override
//					public void onError(int errorCode, String errorInfo) throws RemoteException {
//						canUpdate = false;
//						logger.info("【获取失败】错误码：" + errorCode + "  错误信息：" + errorInfo);
//					}
//				});
//			} catch (RemoteException e) {
//				e.printStackTrace();
//			}
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.w("tag", "服务已断开");
		}
	};

	private void handlerPost() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				startActivity(new Intent(LoginMVPActivity.this, GuidePageActivity.class));
			}
		}, 1000);
	}

//	@Override
//	public void onBackPressed() {
//	}
//
//	@Override
//	public boolean dispatchKeyEvent(KeyEvent event) {
//		if (KeyEvent.KEYCODE_MENU == event.getKeyCode() || KeyEvent.KEYCODE_HOME == event.getKeyCode()){
//			return false;
//		}
//		return super.dispatchKeyEvent(event);
//	}

	@Override
	public LoginContact.LoginModel initM() {
		return new LoginContact.LoginModel();
	}

	@Override
	public LoginContact.LoginPresent initP() {
		return new LoginContact.LoginPresent();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
