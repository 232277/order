package com.dyc.order.cashier.data.parser;

import com.dyc.order.cashier.constant.ResponseCode;
import com.dyc.order.cashier.data.response.ResponseData;
import com.dyc.administrator.toollibrary.utils.MLogger;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import rxhttp.wrapper.annotation.Parser;
import rxhttp.wrapper.entity.ParameterizedTypeImpl;
import rxhttp.wrapper.exception.ParseException;
import rxhttp.wrapper.parse.AbstractParser;
import rxhttp.wrapper.utils.GsonUtil;

/**
 * func: 通用解析
 * author:丁语成 on 2020/2/19 11:51
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
@Parser(name = "Response", wrappers = {List.class})
public class ResponseParser<T> extends AbstractParser<T> {
	private MLogger logger = MLogger.getLogger(this.getClass());

	public ResponseParser() {
	}

	public ResponseParser(@NotNull Type type) {
		super(type);
	}

	//省略构造方法
	@Override
	public T onParse(okhttp3.Response response) throws IOException {
		//第一步，解析code、msg字段，把data当成String对象
		final Type type = ParameterizedTypeImpl.get(ResponseData.class, String.class);
		ResponseData<String> data = convert(response, type);
		T t = null;
		if (ResponseCode.SUCCESS.equals(data.getCode())) {
			//第二步，取出data字段，转换成我们想要的对象
			t = GsonUtil.getObject(data.getData(), mType);
		}else {
			throw new ParseException(String.valueOf(data.getCode()), data.getMsg(), response);
		}

		if (ResponseCode.SUCCESS.equals(data.getCode()) && t== null) {
			try {
				t = (T)data.toString();
			}catch (Exception e){
				e.printStackTrace();
				throw new ParseException(String.valueOf(data.getCode()), data.getMsg(), response);
			}
		}
		return t;
	}
}

