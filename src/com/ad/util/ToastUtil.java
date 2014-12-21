package com.ad.util;

import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 
 * @类名称: ToastUtil
 * @描述: 提示框的显示工具类
 * @开发者: andy.xu
 * @时间: 2014-7-22 下午7:12:36
 * 
 */
public class ToastUtil {

	/**
	 * 
	 * @方法: onShowThreadToast
	 * @描述: 线程中显示Toast
	 * @参数 @param context
	 * @参数 @param str
	 * @返回值类型 void
	 * @捕获异常
	 */
	public static void onShowThreadToast(final Context context, final String str) {
		if (TextUtils.isEmpty(str) || null == context)
			return;

		Looper.prepare();
		onShowToast(context, str);
		Looper.loop();
	}

	/**
	 * 
	 * @方法: onShowToast
	 * @描述: 居中显示toast
	 * @参数 @param context
	 * @参数 @param str
	 * @返回值类型 void
	 * @捕获异常
	 */
	public static void onShowToast(final Context context, final String str) {
		if (TextUtils.isEmpty(str) || null == context)
			return;
		Toast toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	/**
	 * 
	 * @方法: onShowImgToast
	 * @描述: 居中显示图片模式的toast
	 * @参数 @param context
	 * @参数 @param nImgResourceId
	 * @返回值类型 void
	 * @捕获异常
	 */
	public static void onShowImgToast(final Context context, final int nImgResourceId) {
		if (null == context)
			return;

		Toast toast = new Toast(context);
		ImageView img = new ImageView(context);
		img.setImageResource(nImgResourceId);
		toast.setView(img);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	/**
	 * 
	 * @方法: onShowThreadImgToast
	 * @描述: 线程中居中显示图片模式的toast
	 * @参数 @param context
	 * @参数 @param nImgResourceId
	 * @返回值类型 void
	 * @捕获异常
	 */
	public static void onShowThreadImgToast(final Context context, final int nImgResourceId) {
		if (null == context)
			return;

		Looper.prepare();
		onShowImgToast(context, nImgResourceId);
		Looper.loop();
	}
}
