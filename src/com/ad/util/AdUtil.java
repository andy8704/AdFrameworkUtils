package com.ad.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.text.TextUtils;
import android.text.format.DateFormat;

/**
 * 
 * 
 * @类名称: AdUtil
 * @描述: 常用的基本函数
 * @开发者: andy.xu
 * @时间: 2014-8-20 上午11:51:26
 * 
 */
public class AdUtil {
	

	/**
	 * 判断是否为中文
	 * 
	 * @param str
	 *            要判断的字符串
	 * @return
	 */
	public static boolean isChinaChar(String str) {
		if (TextUtils.isEmpty(str)) {
			return false;
		}
		// 生成一个Pattern,同时编译一个正则表达式
		boolean isChina = str.matches("[\u4E00-\u9FA5]");
		return isChina;
	}

	// 检查Email地址是否合法

	public static boolean isValidEmail(final String emailAddr) {

		if (TextUtils.isEmpty(emailAddr))
			return false;

		Pattern pattern = Pattern.compile("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(emailAddr);
		return matcher.matches();
	}

	/**
	 * 验证手机号的合法性
	 * 
	 * @param mail
	 *            手机号码
	 * @return boolean true:合法 false:不合法
	 */
	public static boolean isValidMobile(String mobile) {
		if (TextUtils.isEmpty(mobile)) {
			return false;
		}
		Pattern p = Pattern.compile("^0{0,1}(13[0-9]|15[0-9]|18[0-9])[0-9]{8}$");
		Matcher m = p.matcher(mobile);
		return m.matches();
	}

	/**
	 * 取得与系统时间格式一致
	 * 
	 * @return
	 */
	public static String getTime(long time, final Context context) {
		String formatTime = null;
		// 24小时制格式化
		SimpleDateFormat hour24Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 12小时制格式化
		SimpleDateFormat hour12Format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		// 毫秒数转化为日期
		Date date = new Date(Long.valueOf(time));
		if (DateFormat.is24HourFormat(context)) {
			// 24小时制，直接转化
			formatTime = hour24Format.format(date);
		} else {
			// 12小时制，要做处理
			formatTime = hour12Format.format(date);
			String hourStr = hour24Format.format(date).substring(11, 13);
			if (Integer.valueOf(hourStr) < 12) {
				formatTime += " AM";
			} else {
				formatTime += " PM";
			}
		}
		return formatTime;
	}

}
