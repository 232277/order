package com.dyc.order.cashier.constant;


import com.dyc.order.cashier.R;
import com.dyc.order.cashier.base.MyApplication;

/**
 * func: 响应错误
 * author:丁语成 on 2020/2/18 20:30
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public enum ResponseError {
	UNKNOW(R.string.error_str_unknow_fail, R.string.error_code_unknow_code),
	UNPACK_FAIL(R.string.error_str_unpack_fail, R.string.error_code_unpack_error_code),
	NET_ERROR(R.string.error_net_error_no_net, R.string.error_code_net_no_net_error_code),
	UNKNOW_HOST(R.string.error_net_error_unknown_host, R.string.error_code_unknow_host_error_code),
	SOCKET_TIME_OUT(R.string.error_net_error_socket_timeout, R.string.error_code_socket_timeout_error_code),
	UN_AUTHORIZATION(R.string.error_login_un_authorization, R.string.error_code_un_authorization_error_code);

	int errorStrId;
	int errorCodeStrId;

	ResponseError(int errorStrId, int errorCodeStrId){
		this.errorStrId = errorStrId;
		this.errorCodeStrId = errorCodeStrId;
	}

	public  String getErrorStr(){
		return MyApplication.getContext().getString(errorStrId);
	}

	public int getErrorStrId() {
		return errorStrId;
	}

	public String getErrorCodeStr(){
		return MyApplication.getContext().getString(errorCodeStrId);
	}

	public int getErrorCodeStrId() {
		return errorCodeStrId;
	}
}
