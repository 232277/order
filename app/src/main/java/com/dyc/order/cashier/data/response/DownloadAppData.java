package com.dyc.order.cashier.data.response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * func:app应用信息
 * author:丁语成 on 2020/6/2 10:34
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class DownloadAppData implements Serializable {
	public String title;
	public String content;
	public String url;
	public String md5;
	public String versionCode;

	public static DownloadAppData parse(String response) {

		try {
			JSONObject repJson = new JSONObject(response);
			String title = repJson.optString("title");
			String content = repJson.optString("content");
			String url = repJson.optString("url");
			String md5 = repJson.optString("md5");
			String versionCode = repJson.optString("versionCode");

			DownloadAppData bean = new DownloadAppData();
			bean.title = title;
			bean.content = content;
			bean.url = url;
			bean.md5 = md5;
			bean.versionCode = versionCode;

			return bean;
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;
	}

}
