package com.mc.util;

import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

public class HttpUtil {

	public static String sendGet(String url) throws Exception {
		Connection conn = Jsoup.connect(url);

		Response response = conn.timeout(100000).ignoreContentType(true).method(Method.GET).execute();
		String respJsonStr = response.body();

		return respJsonStr;
	}

	public static String sendPost(String url, Map<String, String> param) throws Exception {
		Connection conn = Jsoup.connect(url);

		Response response = conn.timeout(1000000).ignoreContentType(true).method(Method.POST).data(param).execute();
		String respJsonStr = response.body();

		return respJsonStr;
	}

}
