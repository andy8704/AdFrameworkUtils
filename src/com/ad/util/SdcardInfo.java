package com.ad.util;

import android.os.Environment;

/**
 * 
 * @ClassName: EJ_SdcardInfo
 * @Description: 系统sdcard的信息
 * @author andy.xu
 * @date 2014-4-25 下午2:41:49
 * 
 */
public class SdcardInfo {

	/**
	 * sdcard的路径
	 */
	public String sdcardPath;
	/**
	 * 总大小
	 */
	public long totalSize;

	/**
	 * 可用大小
	 */
	public long freeSize;

	public SdcardInfo() {
		sdcardPath = Environment.getExternalStorageDirectory().getAbsolutePath();
		totalSize = 0;
		freeSize = 0;
	}
}
