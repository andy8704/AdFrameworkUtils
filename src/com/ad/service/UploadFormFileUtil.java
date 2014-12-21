package com.ad.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import android.text.TextUtils;
import android.util.Pair;

/**
 * 
 * @ClassName: AD_UploadFileUtil
 * @Description: 上传文件到服务器
 * @author andy.xu
 * @date 2014-3-19 上午11:57:15
 * 
 */
public class UploadFormFileUtil {

	private static final int BLOCK_SIZE = 8 * 1024;

	/**
	 * 返回的关键字
	 */
	private static final String RESPOND_KEY = "form_respond";

	/**
	 * 提交一个云端同步的数据
	 * 
	 * @param path
	 *            [in]
	 * @param paramStr
	 * @param file
	 * @return 提交后的数据返回：{'head':{'success':'1','userId':'10000'},'noteInfo':{
	 *         cloudId:1,id:1, easyNoteTitle: ’好人一生平安’}"
	 * @throws IOException
	 */
	public static String uploadFile(String urlPath, final Map<String, String> paramStr, FormFile file) throws IOException {

		if (TextUtils.isEmpty(urlPath) || null == paramStr || paramStr.isEmpty())
			return null;

		final String BOUNDARY = UUID.randomUUID().toString(); // 边界标识
		final String endline = "--" + BOUNDARY + "--\r\n";// 数据结束标志
		String charSet = "UTF-8";

		int fileDataLength = 0;
		if (null != file) {
			StringBuilder fileExplain = new StringBuilder();
			fileExplain.append("--");
			fileExplain.append(BOUNDARY);
			fileExplain.append("\r\n");
			fileExplain.append("Content-Disposition: form-data;name=\"" + file.getParameterName() + "\";filename=\"" + file.getFilname() + "\"\r\n");
			fileExplain.append("Content-Type: " + file.getContentType() + "; charset=" + charSet + "\r\n\r\n");
			fileExplain.append("\r\n");
			fileDataLength += fileExplain.length();
			if (file.getInStream() != null) {
				fileDataLength += file.getFile().length();
			} else {
				fileDataLength += file.getData().length;
			}
		}

		StringBuilder textEntity = new StringBuilder();
		for (String ele : paramStr.keySet()) {
			if (!TextUtils.isEmpty(ele)) {
				textEntity.append("--");
				textEntity.append(BOUNDARY);
				textEntity.append("\r\n");
				textEntity.append("Content-Disposition: form-data; name=\"" + ele + "\"\r\n\r\n");
				textEntity.append(paramStr.get(ele));
				textEntity.append("\r\n");
			}
		}

		int dataLength = textEntity.toString().getBytes().length + fileDataLength + endline.getBytes().length;

		URL url = new URL(urlPath);
		int port = url.getPort() == -1 ? 80 : url.getPort();
		Socket socket = new Socket();
		InetSocketAddress socAddress = new InetSocketAddress(url.getHost(), port);
		socket.connect(socAddress, 30000);
		socket.setSoTimeout(30000);
		DataOutputStream outStream = new DataOutputStream(socket.getOutputStream());
		String requestmethod = "POST " + url.getPath() + " HTTP/1.1\r\n";
		outStream.write(requestmethod.getBytes("UTF-8"));
		String accept = "Accept: image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*\r\n";
		outStream.write(accept.getBytes("UTF-8"));
		String language = "Accept-Language: zh-CN\r\n";
		outStream.write(language.getBytes("UTF-8"));
		String contenttype = "Content-Type: multipart/form-data;" + " charset=" + charSet + ";boundary=" + BOUNDARY + "\r\n";
		outStream.write(contenttype.getBytes("UTF-8"));
		String contentlength = "Content-Length: " + dataLength + "\r\n";
		outStream.write(contentlength.getBytes("UTF-8"));
		String alive = "Connection: Keep-Alive\r\n";
		outStream.write(alive.getBytes("UTF-8"));
		String host = "Host: " + url.getHost() + ":" + port + "\r\n";
		outStream.write(host.getBytes("UTF-8"));

		// 写完HTTP请求头后根据HTTP协议再写一个回车换行
		outStream.write("\r\n".getBytes("UTF-8"));
		// 把所有文本类型的实体数据发送出来
		outStream.write(textEntity.toString().getBytes("UTF-8"));
		// 把所有文件类型的实体数据发送出来
		if (null != file) {
			StringBuilder fileEntity = new StringBuilder();
			fileEntity.append("--");
			fileEntity.append(BOUNDARY);
			fileEntity.append("\r\n");
			fileEntity.append("Content-Disposition: form-data;name=\"" + file.getParameterName() + "\";filename=\"" + file.getFilname() + "\"\r\n");
			fileEntity.append("Content-Type: " + file.getContentType() + "\r\n\r\n");
			outStream.write(fileEntity.toString().getBytes("UTF-8"));
			InputStream stream = file.getInStream();
			if (stream != null) {
				byte[] buffer = new byte[BLOCK_SIZE];
				int len = 0;
				while ((len = stream.read(buffer, 0, BLOCK_SIZE)) != -1) {
					outStream.write(buffer, 0, len);
				}
				file.getInStream().close();
			} else {
				outStream.write(file.getData(), 0, file.getData().length);
			}
			outStream.write("\r\n".getBytes("UTF-8"));
		}

		// 下面发送数据结束标志，表示数据已经结束
		outStream.write(endline.getBytes("UTF-8"));
		outStream.flush();
		socket.shutdownOutput();

		// DataInputStream inStream = new DataInputStream(new
		// BufferedInputStream(socket.getInputStream()));
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			isr = new InputStreamReader(socket.getInputStream(), "UTF-8");
			br = new BufferedReader(isr);
		} catch (IOException e) {
			e.printStackTrace();
		}

		int nSleepTime = 0;

		while (socket.getInputStream().available() <= 0) {
			try {
				if (nSleepTime > 300) {
					outStream.close();
					br.close();
					isr.close();
					socket.close();
					return null;
				}
				Thread.sleep(100);
				nSleepTime++;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		String data = null;
		String line = null;
		while (null != (line = br.readLine())) {
			if (!TextUtils.isEmpty(line)) {
				if (TextUtils.equals(line.trim(), RESPOND_KEY)) {
					data = br.readLine();
				}
			}
		}

		outStream.close();
		br.close();
		isr.close();
		socket.close();
		return data;
	}

	public static List<String> onGetCloudFromService(String urlPath, final Map<String, String> paramStr) throws Exception {

		if (TextUtils.isEmpty(urlPath) || null == paramStr || paramStr.isEmpty())
			return null;

		final String BOUNDARY = UUID.randomUUID().toString(); // 边界标识
		final String endline = "--" + BOUNDARY + "--\r\n";// 数据结束标志

		URL url = new URL(urlPath);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		// conn.setReadTimeout(TIME_OUT);
		// conn.setConnectTimeout(TIME_OUT);
//		conn.setDoInput(true);
//		conn.setDoOutput(true);
		conn.setUseCaches(false);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Charset", "utf-8");
		conn.setRequestProperty("connection", "keep-alive");
		conn.setRequestProperty("Content-Type", "multipart/form-data" + ";boundary=" + BOUNDARY);
		DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
		// 写完HTTP请求头后根据HTTP协议再写一个回车换行
		outStream.write("\r\n".getBytes("UTF-8"));
		// 把所有文本类型的实体数据发送出来
		StringBuilder textEntity = new StringBuilder();
		for (String ele : paramStr.keySet()) {// 构造文本类型参数的实体数据
			if (!TextUtils.isEmpty(ele)) {
				textEntity.append("--");
				textEntity.append(BOUNDARY);
				textEntity.append("\r\n");
				textEntity.append("Content-Disposition: form-data; name=\"" + ele + "\"\r\n\r\n");
				textEntity.append(paramStr.get(ele));
				textEntity.append("\r\n");
			}
		}

		outStream.write(textEntity.toString().getBytes("UTF-8"));
		// 把所有文件类型的实体数据发送出来
		// 下面发送数据结束标志，表示数据已经结束
		outStream.write(endline.getBytes("UTF-8"));
		int nRes = conn.getResponseCode();
		if (nRes == 200) {
			outStream.flush();
			outStream.close();
			// DataInputStream inStream = new DataInputStream(new
			// BufferedInputStream(conn.getInputStream()));
			InputStreamReader isr = null;
			BufferedReader br = null;
			try {
				isr = new InputStreamReader(conn.getInputStream(), "UTF-8");
				br = new BufferedReader(isr);
			} catch (IOException e) {
				e.printStackTrace();
			}
			List<String> data = new ArrayList<String>();
			String line = null;
			while (null != (line = br.readLine())) {
				if (!TextUtils.isEmpty(line))
					data.add(line);
			}
			br.close();
			isr.close();
			conn.disconnect();
			return data;
		}
		outStream.close();
		conn.disconnect();
		return null;
	}

	/**
	 * 发送数据到服务器端
	 * 
	 * @param path
	 * @param paramStr
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static List<String> onGetCloudFromService(String path, final Map<String, String> paramStr, final List<Pair<String, List<String>>> ParamData)
			throws Exception {

		if (TextUtils.isEmpty(path) || null == paramStr || paramStr.isEmpty())
			return null;

		final String BOUNDARY = UUID.randomUUID().toString(); // 边界标识
		final String endline = "--" + BOUNDARY + "--\r\n";// 数据结束标志

		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		// conn.setReadTimeout(TIME_OUT);
		// conn.setConnectTimeout(TIME_OUT);
//		conn.setDoInput(true);
//		conn.setDoOutput(true);
		conn.setUseCaches(false);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Charset", "utf-8");
		conn.setRequestProperty("connection", "keep-alive");
		conn.setRequestProperty("Content-Type", "multipart/form-data" + ";boundary=" + BOUNDARY);
		DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
		// 写完HTTP请求头后根据HTTP协议再写一个回车换行
		outStream.write("\r\n".getBytes("UTF-8"));
		// 把所有文本类型的实体数据发送出来
		StringBuilder textEntity = new StringBuilder();
		for (String ele : paramStr.keySet()) {// 构造文本类型参数的实体数据
			if (!TextUtils.isEmpty(ele)) {
				textEntity.append("--");
				textEntity.append(BOUNDARY);
				textEntity.append("\r\n");
				textEntity.append("Content-Disposition: form-data; name=\"" + ele + "\"\r\n\r\n");
				textEntity.append(paramStr.get(ele));
				textEntity.append("\r\n");
			}
		}
		outStream.write(textEntity.toString().getBytes("UTF-8"));

		if (null != ParamData && !ParamData.isEmpty()) {
			for (Pair<String, List<String>> ele : ParamData) {
				if (null != ele) {
					String key = ele.first;
					List<String> value = ele.second;
					if (!TextUtils.isEmpty(key) && null != value && !value.isEmpty()) {
						for (String itemStr : value) {
							if (!TextUtils.isEmpty(itemStr)) {
								textEntity = new StringBuilder();
								textEntity.append("--");
								textEntity.append(BOUNDARY);
								textEntity.append("\r\n");
								textEntity.append("Content-Disposition: form-data; name=\"" + key + "\"\r\n\r\n");
								textEntity.append(itemStr);
								textEntity.append("\r\n");
								outStream.write(textEntity.toString().getBytes("UTF-8"));
							}
						}
					}
				}
			}
		}

		// 把所有文件类型的实体数据发送出来
		// 下面发送数据结束标志，表示数据已经结束
		outStream.write(endline.getBytes("UTF-8"));
		int nRes = conn.getResponseCode();
		if (nRes == 200) {
			outStream.flush();
			outStream.close();
			// DataInputStream inStream = new DataInputStream(new
			// BufferedInputStream(conn.getInputStream()));
			InputStreamReader isr = null;
			BufferedReader br = null;
			try {
				isr = new InputStreamReader(conn.getInputStream(), "UTF-8");
				br = new BufferedReader(isr);
			} catch (IOException e) {
				e.printStackTrace();
			}
			List<String> data = new ArrayList<String>();
			String line = null;
			while (null != (line = br.readLine())) {
				if (!TextUtils.isEmpty(line))
					data.add(line);
			}
			br.close();
			isr.close();
			conn.disconnect();
			return data;
		}
		outStream.close();
		conn.disconnect();
		return null;
	}

	/**
	 * @param path
	 *            [in]
	 * @param param
	 *            [in]
	 * @param filePath
	 *            [in]
	 * @return 提交后的数据返回：{'head':{'success':'1','userId':'10000'},'noteInfo':{
	 *         cloudId:1,id:1, easyNoteTitle: ’好人一生平安’}"
	 */
	public static String uploadFile(String path, final Map<String, String> param, final String filePath) throws IOException {

		return uploadFile(path, param, getCurFile(filePath));
	}

	/**
	 * 获取该记事对应的文件信息
	 * 
	 * @param model
	 * @return
	 */
	public static FormFile getCurFile(final String filePath) {

		if (!TextUtils.isEmpty(filePath)) {
			File file = new File(filePath);
			if (file.isFile() && file.exists()) {
				return new FormFile(file.getName(), file, "image", "application/octet-stream");
			}
		}
		return null;
	}

	public static FormFile getFormFile(final String filePath, final String paramName) {
		if (TextUtils.isEmpty(filePath))
			return null;

		if (!TextUtils.isEmpty(paramName)) {
			File file = new File(filePath);
			if (file.isFile() && file.exists()) {
				return new FormFile(file.getName(), file, paramName, "application/octet-stream");
			}
		}
		return null;
	}

	// /////////////////////////////////////////////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * 提交记事数据文件
	 * 
	 * @param serviceUrl
	 *            [in] 服务器的url
	 * @param param
	 *            [in] 数据参数
	 * @param fileList
	 *            [in] 文件列表
	 * @return
	 * @throws IOException
	 */
	public static String onUploadData(final String serviceUrl, Map<String, String> param, final List<Pair<String, List<String>>> ParamData,
			final List<FormFile> fileList) throws IOException {
		if (TextUtils.isEmpty(serviceUrl))
			return null;

		final String BOUNDARY = UUID.randomUUID().toString(); // 边界标识
		final String endline = "--" + BOUNDARY + "--\r\n";// 数据结束标志
		String charSet = "UTF-8";

		int fileDataLength = 0;
		StringBuilder fileExplain = new StringBuilder();
		if (null != fileList && !fileList.isEmpty()) {
			for (FormFile file : fileList) {
				if (null != file) {
					fileExplain.append("--");
					fileExplain.append(BOUNDARY);
					fileExplain.append("\r\n");
					fileExplain.append("Content-Disposition: form-data;name=\"" + file.getParameterName() + "\";filename=\"" + file.getFilname() + "\"\r\n");
					fileExplain.append("Content-Type: " + file.getContentType() + "; charset=" + charSet + "\r\n\r\n");
					fileExplain.append("\r\n");
					fileDataLength += fileExplain.length();
					if (file.getInStream() != null) {
						fileDataLength += file.getFile().length();
					} else {
						fileDataLength += file.getData().length;
					}
				}
			}
		}

		StringBuilder paramExplain = new StringBuilder();
		if (null != ParamData && !ParamData.isEmpty()) {
			for (Pair<String, List<String>> ele : ParamData) {
				if (null != ele) {
					String key = ele.first;
					List<String> value = ele.second;
					if (!TextUtils.isEmpty(key) && null != value && !value.isEmpty()) {
						for (String itemStr : value) {
							if (!TextUtils.isEmpty(itemStr)) {
								paramExplain.append("--");
								paramExplain.append(BOUNDARY);
								paramExplain.append("\r\n");
								paramExplain.append("Content-Disposition: form-data; name=\"" + key + "\"\r\n\r\n");
								paramExplain.append(itemStr);
								paramExplain.append("\r\n");
							}
						}
					}
				}
			}
		}

		StringBuilder textEntity = new StringBuilder();
		for (String ele : param.keySet()) {
			if (!TextUtils.isEmpty(ele)) {
				textEntity.append("--");
				textEntity.append(BOUNDARY);
				textEntity.append("\r\n");
				textEntity.append("Content-Disposition: form-data; name=\"" + ele + "\"\r\n\r\n");
				textEntity.append(param.get(ele));
				textEntity.append("\r\n");
			}
		}

		int dataLength = textEntity.toString().getBytes().length + fileDataLength + paramExplain.toString().getBytes().length + endline.getBytes().length;

		URL url = new URL(serviceUrl);
		int port = url.getPort() == -1 ? 80 : url.getPort();
		Socket socket = new Socket();
		InetSocketAddress socAddress = new InetSocketAddress(url.getHost(), port);
		// socket.connect(socAddress, 30000);
		socket.connect(socAddress);
		// socket.setSoTimeout(30000);
		DataOutputStream outStream = new DataOutputStream(socket.getOutputStream());
		String requestmethod = "POST " + url.getPath() + " HTTP/1.1\r\n";
		outStream.write(requestmethod.getBytes("UTF-8"));
		String accept = "Accept: image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*\r\n";
		outStream.write(accept.getBytes("UTF-8"));
		String language = "Accept-Language: zh-CN\r\n";
		outStream.write(language.getBytes("UTF-8"));
		String contenttype = "Content-Type: multipart/form-data;" + " charset=" + charSet + ";boundary=" + BOUNDARY + "\r\n";
		outStream.write(contenttype.getBytes("UTF-8"));
		String contentlength = "Content-Length: " + dataLength + "\r\n";
		outStream.write(contentlength.getBytes("UTF-8"));
		String alive = "Connection: Keep-Alive\r\n";
		outStream.write(alive.getBytes("UTF-8"));
		String host = "Host: " + url.getHost() + ":" + port + "\r\n";
		outStream.write(host.getBytes("UTF-8"));

		// 写完HTTP请求头后根据HTTP协议再写一个回车换行
		outStream.write("\r\n".getBytes("UTF-8"));
		// 把所有文本类型的实体数据发送出来
		outStream.write(textEntity.toString().getBytes("UTF-8"));
		// 把所有文件类型的实体数据发送出来
		if (null != ParamData && !ParamData.isEmpty()) {
			for (Pair<String, List<String>> ele : ParamData) {
				if (null != ele) {
					String key = ele.first;
					List<String> value = ele.second;
					if (!TextUtils.isEmpty(key) && null != value && !value.isEmpty()) {
						for (String itemStr : value) {
							if (!TextUtils.isEmpty(itemStr)) {
								textEntity = new StringBuilder();
								textEntity.append("--");
								textEntity.append(BOUNDARY);
								textEntity.append("\r\n");
								textEntity.append("Content-Disposition: form-data; name=\"" + key + "\"\r\n\r\n");
								textEntity.append(itemStr);
								textEntity.append("\r\n");
								outStream.write(textEntity.toString().getBytes("UTF-8"));
							}
						}
					}
				}
			}
		}

		if (null != fileList && !fileList.isEmpty()) {
			for (FormFile ele : fileList) {
				if (null != ele) {
					StringBuilder fileEntity = new StringBuilder();
					fileEntity.append("--");
					fileEntity.append(BOUNDARY);
					fileEntity.append("\r\n");
					fileEntity.append("Content-Disposition: form-data;name=\"" + ele.getParameterName() + "\";filename=\"" + ele.getFilname() + "\"\r\n");
					fileEntity.append("Content-Type: " + ele.getContentType() + "\r\n\r\n");
					outStream.write(fileEntity.toString().getBytes("UTF-8"));
					InputStream stream = ele.getInStream();
					if (stream != null) {
						byte[] buffer = new byte[BLOCK_SIZE];
						int len = 0;
						while ((len = stream.read(buffer, 0, BLOCK_SIZE)) != -1) {
							outStream.write(buffer, 0, len);
						}
						ele.getInStream().close();
					} else {
						outStream.write(ele.getData(), 0, ele.getData().length);
					}
					outStream.write("\r\n".getBytes("UTF-8"));
				}
			}
		}

		// 下面发送数据结束标志，表示数据已经结束
		outStream.write(endline.getBytes("UTF-8"));
		outStream.flush();
		socket.shutdownOutput();

		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			isr = new InputStreamReader(socket.getInputStream(), "UTF-8");
			br = new BufferedReader(isr);
		} catch (IOException e) {
			e.printStackTrace();
		}

		int nSleepTime = 0;

		while (socket.getInputStream().available() <= 0) {
			try {
				if (nSleepTime > 30) {
					outStream.close();
					br.close();
					isr.close();
					socket.close();
					return null;
				}
				Thread.sleep(100);
				nSleepTime++;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		String data = null;
		String line = null;
		while (null != (line = br.readLine())) {
			if (!TextUtils.isEmpty(line)) {
				if (TextUtils.equals(line.trim(), RESPOND_KEY)) {
					data = br.readLine();
				}
			}
		}

		outStream.close();
		br.close();
		isr.close();
		socket.close();
		return data;
	}

	/**
	 * 从服务器端获取数据，并且写入文件，返回该文件的路径
	 * 
	 * @param serviceUrl
	 * @param paramStr
	 * @return
	 */
	// public static String onGetNoteInfoToFile(final String serviceUrl, final
	// Map<String, String> paramStr) {
	//
	// if (TextUtils.isEmpty(serviceUrl) || null == paramStr ||
	// paramStr.isEmpty())
	// return null;
	//
	// final String BOUNDARY = UUID.randomUUID().toString(); // 边界标识
	// final String endline = "--" + BOUNDARY + "--\r\n";// 数据结束标志
	//
	// try {
	// URL url = new URL(serviceUrl);
	// HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	// // conn.setReadTimeout(TIME_OUT);
	// // conn.setConnectTimeout(TIME_OUT);
	// conn.setDoInput(true);
	// conn.setDoOutput(true);
	// conn.setUseCaches(false);
	// conn.setRequestMethod("POST");
	// conn.setRequestProperty("Charset", "utf-8");
	// conn.setRequestProperty("connection", "keep-alive");
	// conn.setRequestProperty("Content-Type", "multipart/form-data" +
	// ";boundary=" + BOUNDARY);
	// DataOutputStream outStream = new
	// DataOutputStream(conn.getOutputStream());
	// // 写完HTTP请求头后根据HTTP协议再写一个回车换行
	// outStream.write("\r\n".getBytes("UTF-8"));
	//
	// // 把所有文本类型的实体数据发送出来
	// StringBuilder textEntity = new StringBuilder();
	// for (String ele : paramStr.keySet()) {// 构造文本类型参数的实体数据
	// if (!TextUtils.isEmpty(ele)) {
	// textEntity.append("--");
	// textEntity.append(BOUNDARY);
	// textEntity.append("\r\n");
	// textEntity.append("Content-Disposition: form-data; name=\"" + ele +
	// "\"\r\n\r\n");
	// textEntity.append(paramStr.get(ele));
	// textEntity.append("\r\n");
	// }
	// }
	//
	// outStream.write(textEntity.toString().getBytes("UTF-8"));
	// // 把所有文件类型的实体数据发送出来
	// // 下面发送数据结束标志，表示数据已经结束
	// outStream.write(endline.getBytes("UTF-8"));
	// int nRes = conn.getResponseCode();
	// if (nRes == 200) {
	// outStream.flush();
	// outStream.close();
	// InputStreamReader isr = null;
	// BufferedReader br = null;
	// try {
	// isr = new InputStreamReader(conn.getInputStream(), "UTF-8");
	// br = new BufferedReader(isr);
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	//
	// File file = new File(PathUtil.EASYNOTE_ROOT() + "easyfone");
	// if (!file.exists())
	// file.createNewFile();
	//
	// PrintWriter write = new PrintWriter(file, "UTF-8");
	// // BufferedWriter buffWrite = new BufferedWriter(new
	// // FileWriter(file));
	// String line = null;
	// while (null != (line = br.readLine())) {
	// if (!TextUtils.isEmpty(line)) {
	// write.write(line);
	// write.write("\n");
	// }
	// }
	// write.close();
	// br.close();
	// isr.close();
	// conn.disconnect();
	// return file.getAbsolutePath();
	// }
	// outStream.close();
	// conn.disconnect();
	// } catch (Exception e) {
	// Log.e("cloudTag:", e.toString());
	// }
	//
	// return null;
	// }

	// public static String onGetNoteInfoToFile(final String serviceUrl, final
	// Map<String, String> paramStr,
	// final List<Pair<String, List<String>>> ParamData) {
	//
	// if (TextUtils.isEmpty(serviceUrl) || null == paramStr ||
	// paramStr.isEmpty())
	// return null;
	//
	// final String BOUNDARY = UUID.randomUUID().toString(); // 边界标识
	// final String endline = "--" + BOUNDARY + "--\r\n";// 数据结束标志
	//
	// try {
	// URL url = new URL(serviceUrl);
	// HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	// // conn.setReadTimeout(TIME_OUT);
	// // conn.setConnectTimeout(TIME_OUT);
	// conn.setDoInput(true);
	// conn.setDoOutput(true);
	// conn.setUseCaches(false);
	// conn.setRequestMethod("POST");
	// conn.setRequestProperty("Charset", "utf-8");
	// conn.setRequestProperty("connection", "keep-alive");
	// conn.setRequestProperty("Content-Type", "multipart/form-data" +
	// ";boundary=" + BOUNDARY);
	// DataOutputStream outStream = new
	// DataOutputStream(conn.getOutputStream());
	// // 写完HTTP请求头后根据HTTP协议再写一个回车换行
	// outStream.write("\r\n".getBytes("UTF-8"));
	// // 把所有文本类型的实体数据发送出来
	// StringBuilder textEntity = new StringBuilder();
	// for (String ele : paramStr.keySet()) {// 构造文本类型参数的实体数据
	// if (!TextUtils.isEmpty(ele)) {
	// textEntity.append("--");
	// textEntity.append(BOUNDARY);
	// textEntity.append("\r\n");
	// textEntity.append("Content-Disposition: form-data; name=\"" + ele +
	// "\"\r\n\r\n");
	// textEntity.append(paramStr.get(ele));
	// textEntity.append("\r\n");
	// }
	// }
	// outStream.write(textEntity.toString().getBytes("UTF-8"));
	//
	// if (null != ParamData && !ParamData.isEmpty()) {
	// for (Pair<String, List<String>> ele : ParamData) {
	// if (null != ele) {
	// String key = ele.first;
	// List<String> value = ele.second;
	// if (!TextUtils.isEmpty(key) && null != value && !value.isEmpty()) {
	// for (String itemStr : value) {
	// if (!TextUtils.isEmpty(itemStr)) {
	// textEntity = new StringBuilder();
	// textEntity.append("--");
	// textEntity.append(BOUNDARY);
	// textEntity.append("\r\n");
	// textEntity.append("Content-Disposition: form-data; name=\"" + key +
	// "\"\r\n\r\n");
	// textEntity.append(itemStr);
	// textEntity.append("\r\n");
	// outStream.write(textEntity.toString().getBytes("UTF-8"));
	// }
	// }
	// }
	// }
	// }
	// }
	// // 把所有文件类型的实体数据发送出来
	// // 下面发送数据结束标志，表示数据已经结束
	// outStream.write(endline.getBytes("UTF-8"));
	// int nRes = conn.getResponseCode();
	//
	// if (nRes == 200) {
	// outStream.flush();
	// outStream.close();
	//
	// InputStreamReader isr = null;
	// BufferedReader br = null;
	// try {
	// isr = new InputStreamReader(conn.getInputStream(), "UTF-8");
	// br = new BufferedReader(isr);
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// File file = new File(PathUtil.EASYNOTE_ROOT() + "easyfone");
	// if (!file.exists())
	// file.createNewFile();
	//
	// PrintWriter write = new PrintWriter(file, "UTF-8");
	// String line = null;
	// while (null != (line = br.readLine())) {
	// if (!TextUtils.isEmpty(line)) {
	// write.write(line);
	// write.write("\n");
	// }
	// }
	// write.close();
	// isr.close();
	// br.close();
	// // inStream.close();
	// conn.disconnect();
	// return file.getAbsolutePath();
	// }
	// outStream.close();
	// conn.disconnect();
	// } catch (Exception e) {
	// Log.e("cloudTag:", e.toString());
	// }
	//
	// return null;
	// }

	/**
	 * 上传数据（标签， 目录）
	 * 
	 * @param serviceUrl
	 * @param params
	 * @return
	 */
	public static String onUploadData(final String serviceUrl, final Map<String, String> params) {
		if (TextUtils.isEmpty(serviceUrl) || null == params || params.isEmpty())
			return null;

		try {
			return onUploadData(serviceUrl, params, null, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
