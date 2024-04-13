package com.dyc.order.cashier.base.iml;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;

import com.dyc.order.cashier.R;
import com.dyc.order.cashier.constant.ResponseError;
import com.dyc.order.cashier.error.MyParseExcepetion;
import com.dyc.administrator.toollibrary.utils.DialogFactory;
import com.dyc.administrator.toollibrary.utils.MLogger;
import com.dyc.administrator.toollibrary.utils.ToastUtils;
import com.dyc.simplemvplibrary.Model;
import com.dyc.simplemvplibrary.PresenterImpl;
import com.dyc.simplemvplibrary.View;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import rxhttp.wrapper.exception.ParseException;

import static com.blankj.utilcode.util.StringUtils.getString;

/**
 * func:
 * author:丁语成 on 2020/2/13 11:15
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public interface BaseContact {

	interface BaseModel extends Model {
		default void stopRequest(){}
	}

	interface BaseView extends View, DialogsView{
		Activity getActivity();

		Context getContext();

		default void showToast(String msg) {
			getActivity().runOnUiThread(()-> ToastUtils.showToast(getActivity(), msg));
		}

		default void showNotifyDialog(int textId, int imgId) {
			showNotifyDialog(getString(textId), imgId);
		}

		default void showNotifyDialog(CharSequence text, int imgId, long existTime) {
			getActivity().runOnUiThread(()->DialogFactory.showNotifyDialog(getActivity(), text, imgId, existTime, null));
		}

		default void showNotifyDialog(Context context, CharSequence msg, int imgId, long existTime, DialogInterface.OnDismissListener dismissListener){
			getActivity().runOnUiThread(()->DialogFactory.showNotifyDialog(getActivity(), msg, imgId, existTime, dismissListener));
		}

		default void showMessageDialog(CharSequence title, CharSequence msg) {
			getActivity().runOnUiThread(()->DialogFactory.showMessageDialog(getActivity(), title, msg));
		}

		default void updateLoadingDrawable(Drawable drawable){
			getActivity().runOnUiThread(()-> DialogFactory.updatePrograssDialogImg(drawable));
		}

		default void updateLoadingMsg(CharSequence str){
			getActivity().runOnUiThread(()-> DialogFactory.updatePrograssDialogMsg(str));
		}

		default void showLoading(CharSequence msg, DialogInterface.OnDismissListener dismissListener){
			getActivity().runOnUiThread(()->DialogFactory.showPrograssDialog(getActivity(), msg, dismissListener));
		}

		default void onError(CharSequence code, CharSequence msg){
			DialogFactory.showNotifyDialog(getActivity(), msg, R.drawable.fail);
		}

		default void onErrorAndFinish(CharSequence code, CharSequence msg){
			DialogFactory.showNotifyDialog(getActivity(), msg, R.drawable.fail, new DialogInterface.OnDismissListener() {
				@Override
				public void onDismiss(DialogInterface dialog) {
					getActivity().finish();
				}
			});
		}
	}

	abstract class BasePresent<M extends BaseModel, V extends BaseView> extends PresenterImpl<M, V> {
		protected MLogger logger = MLogger.getLogger(this.getClass());

		@Override
		public void afterInit() {
		}

		public void onError(Throwable throwable){
			getView().hideDialog();
			CharSequence code = ResponseError.UNKNOW.getErrorCodeStr();
			CharSequence msg = ResponseError.UNKNOW.getErrorStr();
			if (throwable instanceof ParseException){
				code = ((ParseException) throwable).getErrorCode();
				msg = throwable.getMessage();
			}else if (throwable instanceof MyParseExcepetion){
				code = ((MyParseExcepetion)throwable).getCode();
				msg = ((MyParseExcepetion)throwable).getMsg();
			} else if(throwable instanceof SocketTimeoutException){
				code = ResponseError.SOCKET_TIME_OUT.getErrorCodeStr();
				msg = ResponseError.SOCKET_TIME_OUT.getErrorStr();
			}else if (throwable instanceof UnknownHostException){
				code = ResponseError.UNKNOW_HOST.getErrorCodeStr();
				msg = ResponseError.UNKNOW_HOST.getErrorStr();
			}
			logger.error("error code:" + code + " error msg:" + msg);
			logger.error("--------------throwable-------------------:\n" + throwable);
			throwable.printStackTrace();
			getView().onError(code, msg);
		}

		@Override
		protected void onViewDestroy() {
		}
	}
}
