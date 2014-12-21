package com.ad.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.text.TextUtils;

/**
 * 
 * 
 * @类名称: MountSdcard
 * @描述: 获取系统所有的外接的可用的sdcard
 * @开发者: andy.xu
 * @时间: 2014-8-20 上午11:40:26
 * 
 */
public class MountSdcard {
	/**
	 * 
	 * @描述: 获取系统所有支持的可用的sdcard
	 * @参数 @param context
	 * @参数 @return
	 * @返回值 List<SdcardInfo>
	 * @异常
	 */
	public List<SdcardInfo> onGetMountSdcardPath(final Context context) {
		if (null == context)
			return null;

		String[] allDirList = onGetAllSdcardPath(context);
		if (null == allDirList || allDirList.length == 0)
			return null;

		List<SdcardInfo> mountSdcardList = new ArrayList<SdcardInfo>(allDirList.length);
		for (String ele : allDirList) {
			if (!TextUtils.isEmpty(ele)) {
				boolean bState = checkSdcardMount(ele, context);
				if (bState) {
					StatFs curFile = new StatFs(ele);
					SdcardInfo sdcardInfo = new SdcardInfo();
					sdcardInfo.sdcardPath = ele;
					int sdkint = android.os.Build.VERSION.SDK_INT;
					if (sdkint < 18) {
						int blockSize = curFile.getBlockSize();
						sdcardInfo.totalSize = (blockSize / 1024) * (curFile.getBlockCount() / 1024);
						sdcardInfo.freeSize = (blockSize / 1024) * (curFile.getAvailableBlocks() / 1024);
					} else {
						sdcardInfo.totalSize = curFile.getTotalBytes() / (1024 * 1024);
						sdcardInfo.freeSize = curFile.getAvailableBytes() / (1024 * 1024);
					}
					mountSdcardList.add(sdcardInfo);
				}
			}
		}
		return mountSdcardList;
	}

	// 获取系统给的所有sdcard的路径
	private String[] onGetAllSdcardPath(final Context context) {
		if (null == context)
			return null;

		StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
		try {
			return (String[]) storageManager.getClass().getMethod("getVolumePaths").invoke(storageManager);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private boolean checkSdcardMount(String dirMountPoint, final Context context) {
		if (TextUtils.isEmpty(dirMountPoint) || null == context)
			return false;

		String state = null;
		StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
		try {
			state = (String) storageManager.getClass().getMethod("getVolumeState", String.class).invoke(storageManager, dirMountPoint);
			return Environment.MEDIA_MOUNTED.equals(state);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
