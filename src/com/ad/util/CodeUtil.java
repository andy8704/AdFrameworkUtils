package com.ad.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

import android.util.Base64;

/**
 * 
 * 
 * @类名称: CodeUtil
 * @描述: 文本操作
 * @开发者: andy.xu
 * @时间: 2014-8-18 下午5:17:25
 * 
 */
public class CodeUtil {
	
	/**
	 * MD5加密(32)
	 * 
	 * @param plainText
	 *            要加密的字符串
	 * @return
	 */
	public final static String MD5(String plainText) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = plainText.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
		}
		return "";
	}

	/**
	 * base64编码
	 * 
	 * @param str
	 * @return
	 */
	public static String base64Encode(String str) {
		try {
			str = Base64.encodeToString(str.getBytes("UTF-8"), Base64.DEFAULT);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * base64 解码
	 * 
	 * @param str
	 * @return
	 */
	public static String base64Decode(String str) {
		byte[] strByte = Base64.decode(str, Base64.DEFAULT);
		return strByte.toString();
	}
}
