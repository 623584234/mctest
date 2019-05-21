package com.mc.util.wx;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONObject;

@SuppressWarnings("deprecation")
public class httpsUtil {
	/**
	 * 编写Get请求的方法。但没有参数传递的时候，可以使用Get请求
	 * 
	 * @param url 需要请求的URL
	 * @return 将请求URL后返回的数据，转为JSON格式，并return
	 */
	public static String doGetStr(String url) throws ClientProtocolException, IOException {
		@SuppressWarnings("resource")
		DefaultHttpClient client = new DefaultHttpClient();// 获取DefaultHttpClient请求
		HttpGet httpGet = new HttpGet(url);// HttpGet将使用Get方式发送请求URL
		JSONObject jsonObject = null;
		HttpResponse response = client.execute(httpGet);// 使用HttpResponse接收client执行httpGet的结果
		HttpEntity entity = response.getEntity();// 从response中获取结果，类型为HttpEntity
		String result =null;
		if (entity != null) {
			result =EntityUtils.toString(entity, "UTF-8");// HttpEntity转为字符串类型
		}
		return result;
	}

	/**
	 * 编写Post请求的方法。当我们需要参数传递的时候，可以使用Post请求
	 * 
	 * @param url    需要请求的URL
	 * @param outStr 需要传递的参数
	 * @return 将请求URL后返回的数据，转为JSON格式，并return
	 */
	public static String doPostStr(String url, String outStr) throws ClientProtocolException, IOException {
		@SuppressWarnings("resource")
		DefaultHttpClient client = new DefaultHttpClient();// 获取DefaultHttpClient请求
		HttpPost httpost = new HttpPost(url);// HttpPost将使用Get方式发送请求URL
		JSONObject jsonObject = null;
		httpost.setEntity(new StringEntity(outStr, "UTF-8"));// 使用setEntity方法，将我们传进来的参数放入请求中
		HttpResponse response = client.execute(httpost);// 使用HttpResponse接收client执行httpost的结果
		String result = EntityUtils.toString(response.getEntity(), "UTF-8");// HttpEntity转为字符串类型
		jsonObject = JSONObject.fromObject(result);// 字符串类型转为JSON类型
		return jsonObject.toString();
	}

	public static JSONObject doPostJson(String url, JSONObject jsonObject) throws IOException {
		String body = "";
		// 创建httpclient对象
		CloseableHttpClient client = HttpClients.createDefault();
		// 创建post方式请求对象
		HttpPost httpPost = new HttpPost(url);
		// 装填参数
		StringEntity s = new StringEntity(jsonObject.toString(), "utf-8");
		s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
				"application/json"));
		// 设置参数到请求对象中
		httpPost.setEntity(s);
		System.out.println("请求地址：" + url);
//        System.out.println("请求参数："+nvps.toString());
		// 设置header信息
		// 指定报文头【Content-type】、【User-Agent】
//        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
		httpPost.setHeader("Content-type", "application/json");
		httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
		// 执行请求操作，并拿到结果（同步阻塞）
		CloseableHttpResponse response = client.execute(httpPost);
		// 获取结果实体
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			// 按指定编码转换结果实体为String类型
			body = EntityUtils.toString(entity, "utf-8");
		}
		EntityUtils.consume(entity);
		// 释放链接
		response.close();
		@SuppressWarnings("static-access")
		JSONObject json = jsonObject.fromObject(body);
		return json;
	}
	
	public static String getJsonData(JSONObject jsonParam,String urls) {
		StringBuffer sb=new StringBuffer();
		try {
			;
			// 创建url资源
			URL url = new URL(urls);
			// 建立http连接
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 设置允许输出
			conn.setDoOutput(true);
                        // 设置允许输入
                        conn.setDoInput(true);
                       // 设置不用缓存
                       conn.setUseCaches(false);
                       // 设置传递方式
                       conn.setRequestMethod("POST");
                       // 设置维持长连接
                        conn.setRequestProperty("Connection", "Keep-Alive");
                       // 设置文件字符集:
                       conn.setRequestProperty("Charset", "UTF-8");
                       // 转换为字节数组
                       byte[] data = (jsonParam.toString()).getBytes();
                      // 设置文件长度
                       conn.setRequestProperty("Content-Length", String.valueOf(data.length));
                      // 设置文件类型:
                      conn.setRequestProperty("Content-Type", "application/json;");
                        // 开始连接请求
                       conn.connect();		
                    OutputStream out = new DataOutputStream(conn.getOutputStream()) ;
			// 写入请求的字符串
			out.write((jsonParam.toString()).getBytes());
			out.flush();
			out.close();
 
			System.out.println(conn.getResponseCode());
			
			// 请求返回的状态
			if (HttpURLConnection.HTTP_OK == conn.getResponseCode()){
				System.out.println("连接成功");
				// 请求返回的数据
				InputStream in1 = conn.getInputStream();
				try {
				      String readLine=new String();
				      BufferedReader responseReader=new BufferedReader(new InputStreamReader(in1,"UTF-8"));
				      while((readLine=responseReader.readLine())!=null){
				        sb.append(readLine).append("\n");
				      }
				      responseReader.close();
				      System.out.println(sb.toString());
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} else {
				System.out.println("error++");
				
			}
 
		} catch (Exception e) {
 
		}
		
		return sb.toString();
 

	}
}
