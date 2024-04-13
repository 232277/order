package com.dyc.order.cashier.view;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.dyc.order.cashier.contact.UpdateAppContact;
import com.dyc.administrator.toollibrary.utils.MLogger;
import com.dyc.order.cashier.R;
import com.dyc.order.cashier.data.response.DownloadAppData;
import com.dyc.simplemvplibrary.BaseMVP;

import java.io.File;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import rxhttp.wrapper.entity.Progress;

/**
 * func:应用更新对话框
 * author:丁语成 on 2020/6/2 10:39
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class UpdateVersionShowDialog extends DialogFragment implements BaseMVP<
        UpdateAppContact.UpdateAppModel,
		UpdateAppContact.UpdateAppView,
		UpdateAppContact.UpdateAppPresent>, UpdateAppContact.UpdateAppView {
	private static final String TAG = "UpdateVersionShowDialog";
	private static final String KEY_DOWNLOAD_BEAN = "download_bean";
	private static final MLogger logger = MLogger.getLogger(UpdateVersionShowDialog.class);

	private DownloadAppData mDownloadBean;
	private UpdateVersionShowDialogListener mListener;
	private UpdateAppContact.UpdateAppPresent present;

	private TextView tvTitle;
	private TextView tvContent;
	private TextView tvUpdate;

	public static UpdateVersionShowDialog show(FragmentActivity activity, DownloadAppData bean) {
		Bundle bundle = new Bundle();
		bundle.putSerializable(KEY_DOWNLOAD_BEAN, bean);

		UpdateVersionShowDialog dialog = new UpdateVersionShowDialog();
		dialog.setArguments(bundle);

		dialog.show(activity.getSupportFragmentManager(), "updateVersionShowDialog");

		return dialog;
	}

	public void setListener(UpdateVersionShowDialogListener listener) {
		this.mListener = listener;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle arguments = getArguments();
		if (arguments != null) {
			mDownloadBean = (DownloadAppData) arguments.getSerializable(KEY_DOWNLOAD_BEAN);
		}
		present = initP();
		if (present != null) {
			//将Model层注册到Presenter中
			present.registerModel(initM());
			//将View层注册到Presenter中
			present.registerView(this);
			present.afterInit();
		}
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.dialog_update_app_version, container, false);
		bindEvents(view);
		return view;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
		getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		getDialog().setCanceledOnTouchOutside(false);
	}

	private void bindEvents(View view) {
		tvTitle = view.findViewById(R.id.tv_title);
		tvContent = view.findViewById(R.id.tv_content);
		tvUpdate = view.findViewById(R.id.tv_update);

		final DownloadAppData bean = mDownloadBean;
		if (bean != null) {
			tvTitle.setText(bean.title);
			tvContent.setText(bean.content);
			tvUpdate.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(final View v) {
					v.setEnabled(false);
					logger.info("apk地址：" + getActivity().getExternalCacheDir() + "/target.apk");
					File targetFile = new File(getActivity().getExternalCacheDir(), "target.apk");
					if (targetFile.getParentFile() != null)
					present.downloadApp(targetFile, bean.url);
				}
			});
		}
	}

	@Override
	public void onDownloading(Progress progress) {
		tvUpdate.setText(String.format(Locale.getDefault(), "%d%%", progress.getProgress()));
	}

	@Override
	public void onDownloadSuccess(String path) {
		File apkFile = new File(path);
		Log.w(TAG, "success =" + apkFile.getAbsolutePath());
		mListener = null;
		tvUpdate.setEnabled(true);
		dismiss();
		present.installApk(path);
	}

	@Override
	public void onDownloadFail(Throwable throwable) {
		Toast.makeText(getActivity(), R.string.label_update_version_dialog_file_download_failed, Toast.LENGTH_SHORT).show();
		tvUpdate.setEnabled(true);
	}

	@Override
	public void onDestroy() {
		if (present != null){
			present.destroy();
		}
		super.onDestroy();
	}

	@Override
	public void onDismiss(DialogInterface dialog) {
		super.onDismiss(dialog);
		if (mListener != null) {
			mListener.onDismiss();
		}
	}

	@Override
	public UpdateAppContact.UpdateAppModel initM() {
		return new UpdateAppContact.UpdateAppModel() {};
	}

	@Override
	public UpdateAppContact.UpdateAppView initV() {
		return this;
	}

	@Override
	public UpdateAppContact.UpdateAppPresent initP() {
		return new UpdateAppContact.UpdateAppPresent() {
		};
	}

	public interface UpdateVersionShowDialogListener {
		void onDismiss();
	}
}
