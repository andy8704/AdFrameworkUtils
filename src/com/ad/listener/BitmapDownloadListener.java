package com.ad.listener;

import android.graphics.Bitmap;

/**
 * 
 * @类名称: BitmapDownloadListener
 * @描述: 图片下载的监听器
 * @开发者: andy.xu
 * @时间: 2014-7-23 下午4:59:31
 * 
 */
public interface BitmapDownloadListener {

	/**
	 * 
	 * @方法: onDownloading
	 * @描述: 下载中
	 * @参数
	 * @返回值类型 void
	 * @捕获异常
	 */
	public void onDownloading();

	/**
	 * 
	 * @方法: onDownloadComplete
	 * @描述: 下载完成
	 * @参数 @param bmp
	 * @返回值类型 void
	 * @捕获异常
	 */
	public void onDownloadComplete(final Bitmap bmp);
}
