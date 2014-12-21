package com.ad.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.text.TextUtils;

/**
 * 
 * @ClassName: HttpClientUtil
 * @Description: 网络交互接口
 * @author andy.xu
 * @date 2014-3-10 下午9:15:22
 * 
 */
public class HttpClientUtil {

	// url连接符
	public static final String QUESTION_SIGN = "?";
	public static final String LINK_SIGN = "&";
	public static final String EQUAL_SIGN = "=";
	public static final String FENHAO_SIGN = ";";
	public static final String POINT_SIGN = ".";
	public static final String DOUHAO_SIGN = ",";

	/**
	 * 通过post方式请求URL
	 * 
	 * @param url
	 *            请求URL
	 * @param params
	 *            post参数
	 * @return json字符串
	 */
	public static String getJsonPost(String url, String params, int connTimeOut, int soTimeOut) {
		if (TextUtils.isEmpty(url) || TextUtils.isEmpty(params)) {
			return null;
		}
		// 要返回的json字符串
		String resultStr = null;
		// HTTP client
		HttpClient httpClient = new DefaultHttpClient();
		try {
			// HTTP post
			HttpPost reqPost = new HttpPost(new URI(url));
			// post 参数
			StringEntity postEntity = new StringEntity(params, HTTP.UTF_8);
			reqPost.setEntity(postEntity);
			// 通过网络与服务器建立连接的超时时间:5秒钟
			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connTimeOut);
			// Socket读数据的超时时间，即从服务器获取响应数据需要等待的时间:10秒钟
			httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeOut);
			// 向服务器发送请求命令
			HttpResponse response = httpClient.execute(reqPost);
			// 判断请求是否成功
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// 200表示请求成功
				HttpEntity entity = response.getEntity();
				if (null != entity) {
					resultStr = EntityUtils.toString(entity, HTTP.UTF_8);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return resultStr;
	}

	/**
	 * 通过post方式请求URL
	 * 
	 * @param url
	 *            请求URL
	 * @param params
	 *            post NameValuePair List参数
	 * @return json字符串
	 */
	public static String getJsonPost(String url, List<NameValuePair> params, int connTimeOut, int soTimeOut) {
		if (TextUtils.isEmpty(url) || null == params || params.size() == 0) {
			return null;
		}
		// 要返回的json字符串
		String resultStr = null;
		// HTTP client
		HttpClient httpClient = new DefaultHttpClient();
		try {
			// post请求参数
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			for (NameValuePair p : params) {
				formparams.add(p);
			}
			UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(formparams, HTTP.UTF_8);
			// HTTP post
			HttpPost reqPost = new HttpPost(new URI(url));
			reqPost.setEntity(postEntity);
			// 通过网络与服务器建立连接的超时时间:10秒钟
			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connTimeOut);
			// Socket读数据的超时时间，即从服务器获取响应数据需要等待的时间:10秒钟
			httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeOut);
			// 向服务器发送请求命令
			HttpResponse response = httpClient.execute(reqPost);
			// 判断请求是否成功
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// 200表示请求成功
				HttpEntity entity = response.getEntity();
				if (null != entity) {
					resultStr = EntityUtils.toString(entity, HTTP.UTF_8);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return resultStr;
	}

	/**
	 * 通过get方式请求URL
	 * 
	 * @param url
	 *            请求URL
	 * @param params
	 *            post参数
	 * @return json字符串
	 */
	public static String getJsonGet(String url, String params) {
		// 要返回的json字符串
		String resultStr = null;
		if (TextUtils.isEmpty(url)) {
			return resultStr;
		}
		if (!TextUtils.isEmpty(params)) {
			url += QUESTION_SIGN + params;
		}
		return null;
	}

}
