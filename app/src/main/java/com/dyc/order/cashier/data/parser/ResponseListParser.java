package com.dyc.order.cashier.data.parser;

import com.dyc.order.cashier.constant.ResponseCode;
import com.dyc.order.cashier.constant.ResponseError;
import com.dyc.order.cashier.data.response.ResponseData;
import com.dyc.administrator.toollibrary.utils.MLogger;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import rxhttp.wrapper.entity.ParameterizedTypeImpl;
import rxhttp.wrapper.exception.ParseException;
import rxhttp.wrapper.parse.AbstractParser;

/**
 * func: data[]类型列表解析
 * author:丁语成 on 2020/2/22 0:19
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
//@Parser(name = "ResponseList")
public class ResponseListParser<T> extends AbstractParser<List<T>> {
	private MLogger logger = MLogger.getLogger(this.getClass());

	public ResponseListParser() {
	}

	public ResponseListParser(@NotNull Type type) {
		super(type);
	}

	//省略构造方法
	@Override
	public List<T> onParse(okhttp3.Response response) throws IOException {
		try {
			final Type type = ParameterizedTypeImpl.get(ResponseData.class, List.class, mType); //获取泛型类型
			ResponseData<List<T>> data = convert(response, type);
			List<T> t = data.getData(); //获取data字段
			logger.info(t);
			if (!ResponseCode.SUCCESS.equals(data.getCode()) || t == null) {//code不成功，说明数据不正确，抛出异常
				throw new ParseException(String.valueOf(data.getCode()), data.getMsg(), response);
			}
			return t;
		}catch (Exception e){
			logger.error(e);
			throw new ParseException(ResponseError.UNPACK_FAIL.getErrorStr(), ResponseError.UNPACK_FAIL.getErrorCodeStr(), response);
		}
	}
}

