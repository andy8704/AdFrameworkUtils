package com.ad.pckManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

/***
 * @author andy.xu
 * @version 0.1
 * @time 2011/11/5
 * **/

public class MyPackageManager {

	public static enum PACKAGE_TYPE {
		ALL_APPS, SYSTEM_APPS, USER_APPS
	}

	public static ArrayList<PackageInfo> getAllInstallApps(final Context context, final PACKAGE_TYPE type) {

		if (null == context || null == type)
			return null;

		switch (type) {
		default:
			return null;
		case ALL_APPS:
			return getAllInstallApps(context);
		case SYSTEM_APPS:
			return getAllSystemInstallApps(context);
		case USER_APPS:
			return getAllInstallExtraApps(context);
		}
	}

	public static ArrayList<MyPackageInfo> getAllInstallAppsInfo(final Context context, final PACKAGE_TYPE type) {

		if (null == context || null == type)
			return null;

		switch (type) {
		default:
			return null;
		case ALL_APPS:
			return getAllInstallAppsInfo(context);
		case SYSTEM_APPS:
			return getAllSystemInstallAppsInfo(context);
		case USER_APPS:
			return getAllInstallExtraAppsInfo(context);
		}
	}

	/**
	 * 判断一个包是否已经安装
	 **/

	public static boolean isPackageHaveInstall(final Context context, final String packageName) {

		if (null == context || TextUtils.isEmpty(packageName))
			return false;

		List<MyPackageInfo> packageList = getAllInstallAppsInfo(context, PACKAGE_TYPE.USER_APPS);
		if (null == packageList || packageList.isEmpty())
			return false;

		MyPackageInfo searchPackage = new MyPackageInfo();
		searchPackage.packageName = packageName;
		return packageList.contains(searchPackage);
	}

	public static MyPackageInfo getPackageHaveInstall(final Context context, final String packageName) {

		if (null == context || TextUtils.isEmpty(packageName))
			return null;

		List<MyPackageInfo> packageList = getAllInstallAppsInfo(context, PACKAGE_TYPE.USER_APPS);
		if (null == packageList || packageList.isEmpty())
			return null;

		for (MyPackageInfo ele : packageList) {

			if (null == ele)
				continue;

			if (TextUtils.equals(ele.packageName, packageName))
				return ele;
		}

		return null;
	}

	/**
	 * 获取除了系统之外所有安装的Apk软件
	 **/
	private static ArrayList<PackageInfo> getAllInstallExtraApps(final Context context) {

		if (null == context)
			return null;

		PackageManager packageManager = context.getPackageManager();
		if (null == packageManager)
			return null;

		List<PackageInfo> tempApps = packageManager.getInstalledPackages(0);
		if (null == tempApps || tempApps.isEmpty())
			return null;

		ArrayList<PackageInfo> allInstallApps = new ArrayList<PackageInfo>(tempApps.size());

		for (PackageInfo ele : tempApps) {
			if (null == ele)
				continue;

			if ((ele.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0)
				allInstallApps.add(ele);
		}

		return allInstallApps;
	}

	/**
	 * 获取所有安装的Apk软件
	 **/
	private static ArrayList<PackageInfo> getAllInstallApps(final Context context) {

		if (null == context)
			return null;

		PackageManager packageManager = context.getPackageManager();
		if (null == packageManager)
			return null;

		List<PackageInfo> tempApps = packageManager.getInstalledPackages(0);
		if (null == tempApps || tempApps.isEmpty())
			return null;

		return (ArrayList<PackageInfo>) tempApps;
	}

	/**
	 * 获取系统所有安装的Apk软件
	 **/
	private static ArrayList<PackageInfo> getAllSystemInstallApps(final Context context) {

		if (null == context)
			return null;

		PackageManager packageManager = context.getPackageManager();
		if (null == packageManager)
			return null;

		List<PackageInfo> tempApps = packageManager.getInstalledPackages(0);
		if (null == tempApps || tempApps.isEmpty())
			return null;

		ArrayList<PackageInfo> allInstallApps = new ArrayList<PackageInfo>(tempApps.size());

		for (PackageInfo ele : tempApps) {
			if (null == ele)
				continue;

			if (ele.applicationInfo.flags == ApplicationInfo.FLAG_SYSTEM)
				allInstallApps.add(ele);
		}

		return allInstallApps;
	}

	private static ArrayList<MyPackageInfo> getAllInstallExtraAppsInfo(final Context context) {

		if (null == context)
			return null;

		PackageManager packageManager = context.getPackageManager();
		if (null == packageManager)
			return null;

		ArrayList<PackageInfo> data = getAllInstallExtraApps(context);
		if (null == data || data.isEmpty())
			return null;

		ArrayList<MyPackageInfo> packageInfoList = new ArrayList<MyPackageInfo>(data.size());
		for (PackageInfo ele : data) {
			if (null == ele)
				continue;

			MyPackageInfo packageInfo = new MyPackageInfo();
			packageInfo.icon = packageManager.getApplicationIcon(ele.applicationInfo);
			packageInfo.name = packageManager.getApplicationLabel(ele.applicationInfo).toString();
			packageInfo.packageName = ele.applicationInfo.packageName;
			packageInfo.version = ele.versionName;
			packageInfo.versionCode = ele.versionCode;
			packageInfo.apkFilePath = ele.applicationInfo.sourceDir;
			if (null != ele.applicationInfo.sourceDir || TextUtils.isEmpty(ele.applicationInfo.sourceDir)) {
				File file = new File(ele.applicationInfo.sourceDir);
				if (file.exists()) {
					packageInfo.size = file.length();
				}
			}
			packageInfoList.add(packageInfo);
		}

		return packageInfoList;
	}

	private static ArrayList<MyPackageInfo> getAllInstallAppsInfo(final Context context) {

		if (null == context)
			return null;

		PackageManager packageManager = context.getPackageManager();
		if (null == packageManager)
			return null;

		ArrayList<PackageInfo> data = getAllInstallApps(context);
		if (null == data || data.isEmpty())
			return null;

		ArrayList<MyPackageInfo> packageInfoList = new ArrayList<MyPackageInfo>(data.size());
		for (PackageInfo ele : data) {
			if (null == ele)
				continue;

			MyPackageInfo packageInfo = new MyPackageInfo();
			packageInfo.icon = packageManager.getApplicationIcon(ele.applicationInfo);
			packageInfo.name = packageManager.getApplicationLabel(ele.applicationInfo).toString();
			packageInfo.packageName = ele.applicationInfo.packageName;
			packageInfo.version = ele.versionName;
			packageInfo.versionCode = ele.versionCode;

			packageInfoList.add(packageInfo);
		}

		return packageInfoList;
	}

	private static ArrayList<MyPackageInfo> getAllSystemInstallAppsInfo(final Context context) {

		if (null == context)
			return null;

		PackageManager packageManager = context.getPackageManager();
		if (null == packageManager)
			return null;

		ArrayList<PackageInfo> data = getAllSystemInstallApps(context);
		if (null == data || data.isEmpty())
			return null;

		ArrayList<MyPackageInfo> packageInfoList = new ArrayList<MyPackageInfo>(data.size());
		for (PackageInfo ele : data) {
			if (null == ele)
				continue;

			MyPackageInfo packageInfo = new MyPackageInfo();
			packageInfo.icon = packageManager.getApplicationIcon(ele.applicationInfo);
			packageInfo.name = packageManager.getApplicationLabel(ele.applicationInfo).toString();
			packageInfo.packageName = ele.applicationInfo.packageName;
			packageInfo.version = ele.versionName;
			packageInfo.versionCode = ele.versionCode;

			packageInfoList.add(packageInfo);
		}

		return packageInfoList;
	}

	// 获取系统安装的service

	public static List<ActivityManager.RunningServiceInfo> getRunningServiceInfo(final Context context) {

		if (null == context)
			return null;

		ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		if (null == manager)
			return null;

		List<ActivityManager.RunningServiceInfo> serviceList = manager.getRunningServices(100);
		if (null == serviceList || serviceList.isEmpty())
			return null;

		return serviceList;
	}

	/**
	 * 启动当前的软件
	 * 
	 * @param context
	 *            [in] 设备上下文
	 * @param packageName
	 *            [in] 启动的软件的包名
	 */
	public static void onStartCurPackage(final Context context, final String packageName) {
		if (null == context || TextUtils.isEmpty(packageName))
			return;

		try {
			Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
			if (null != intent)
				context.startActivity(intent);
		} catch (Exception e) {
		}
	}
}
