package com.dyc.order.cashier.data.parser;

import com.dyc.order.cashier.constant.ResponseCode;
import com.dyc.order.cashier.data.response.NoDataResponse;
import com.dyc.administrator.toollibrary.utils.MLogger;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;

import rxhttp.wrapper.exception.ParseException;
import rxhttp.wrapper.parse.AbstractParser;

/**
 * func:
 * author:丁语成 on 2020/4/3 14:04
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */

//@Parser(name = "NoDataResponse")
public class NoDataParser extends AbstractParser<NoDataResponse> {
	private MLogger logger = MLogger.getLogger(this.getClass());

	public NoDataParser() {
		super();
	}

	public NoDataParser(@NotNull Type type) {
		super(type);
	}

	//省略构造方法
	@Override
	public NoDataResponse onParse(okhttp3.Response response) throws IOException {
		NoDataResponse data = convert(response, NoDataResponse.class);
		logger.info(response.headers());
		if (!ResponseCode.SUCCESS.equals(data.getCode())) {//code不成功，说明数据不正确，抛出异常
			throw new ParseException(String.valueOf(data.getCode()), data.getMsg(), response);
		}
		return data;
	}
}
