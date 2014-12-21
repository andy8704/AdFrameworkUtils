package com.ad.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import android.util.DisplayMetrics;

/**
 * 
 * @ClassName: TelePhone
 * @Description: manager telephone call & dial
 * @author andy.xu
 * @date 2014-3-11 上午10:47:43
 * 
 */
public class TelePhone {

	private Context m_Context = null;

	public TelePhone(final Context _context) {
		if (null == _context)
			return;

		m_Context = _context;
	}

	/**
	 * call telephone
	 * 
	 * @param _szPhoneNum
	 *            [in] phoneNum
	 **/
	public void TelePhoneCall(final String _szPhoneNum) {

		if (null == _szPhoneNum)
			return;

		TelePhoneMode(TELEMODE.CALL_PHONE, _szPhoneNum);
	}

	/**
	 * dial telephone
	 * 
	 * @param _szPhoneNum
	 *            [in] phoneNum
	 **/
	public void TelePhoneDial(final String _szPhoneNum) {

		if (null == _szPhoneNum)
			return;

		TelePhoneMode(TELEMODE.DIAL_PHONE, _szPhoneNum);
	}

	private enum TELEMODE {
		CALL_PHONE, DIAL_PHONE
	};

	/**
	 * 跳转到系统通讯录界面
	 */
	public void getSystemContact() {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		intent.setData(ContactsContract.Contacts.CONTENT_URI);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		m_Context.startActivity(intent);

	}

	private void TelePhoneMode(final TELEMODE _mode, final String _szPhoneNum) {

		Intent myIntent = null;
		if (TELEMODE.CALL_PHONE == _mode) {
			myIntent = new Intent(Intent.ACTION_CALL);
		} else if (TELEMODE.DIAL_PHONE == _mode) {
			myIntent = new Intent(Intent.ACTION_DIAL);
		}
		myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		if (!TextUtils.isEmpty(_szPhoneNum)) {
			String szPhone = "tel:" + _szPhoneNum.trim();
			Uri uri = Uri.parse(szPhone);
			if (null == uri)
				return;

			myIntent.setData(uri);
		}
		if (null == m_Context)
			return;

		m_Context.startActivity(myIntent);
	}

	/**
	 * 获取本机的电话号码
	 * 
	 * @param context
	 * @return
	 */
	public static String getTelphoneNum(final Context context) {

		if (null == context)
			return null;

		TelephonyManager telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

		if (null == telManager) {
			return null;
		}
		if (telManager.getSimState() == TelephonyManager.SIM_STATE_READY) {
			return telManager.getLine1Number();
		}

		return null;
	}

	/** 获取 IMEI 手机唯一标示 */

	public static final String getIMEIAddress(final Context context) {

		if (null == context)
			return null;

		TelephonyManager telphoneManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		if (null == telphoneManager)
			return null;

		return telphoneManager.getDeviceId();
	}

	/**
	 * 取得SIM卡的唯一标识 ismi
	 * 
	 * @param context
	 * @return
	 */
	public static String getMobileCardId(final Context context) {
		String subscriberId = null;
		if (null == context) {
			return subscriberId;
		}
		TelephonyManager telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

		if (null == telManager) {
			return subscriberId;
		}
		if (telManager.getSimState() == TelephonyManager.SIM_STATE_READY) {
			subscriberId = telManager.getSubscriberId();
		}

		if (!TextUtils.isEmpty(subscriberId)) {
			subscriberId = subscriberId.toUpperCase();
		}

		return subscriberId;
	}

	/**
	 * 获取手机制式
	 * 
	 * @param context
	 * @return 包括GSM, CDMA和未知（UNKNOW）
	 */
	public static String getPhoneType(Context context) {
		String phoneType = null;
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		if (null != tm) {
			int nNetType = tm.getNetworkType();
			switch (nNetType) {
			default:
				phoneType = "UNKNOWN";
				break;
			case TelephonyManager.NETWORK_TYPE_1xRTT:
				phoneType = "1xRTT";
				break;
			case TelephonyManager.NETWORK_TYPE_CDMA:
				phoneType = "CDMA";
				break;
			case TelephonyManager.NETWORK_TYPE_EDGE:
				phoneType = "EDGE";
				break;
			case TelephonyManager.NETWORK_TYPE_EVDO_0:
				phoneType = "EVDO_0";
				break;
			case TelephonyManager.NETWORK_TYPE_EVDO_A:
				phoneType = "EVDO_A";
				break;
			case TelephonyManager.NETWORK_TYPE_EVDO_B:
				phoneType = "EVDO_B";
				break;
			case TelephonyManager.NETWORK_TYPE_GPRS:
				phoneType = "GPRS";
				break;
			case TelephonyManager.NETWORK_TYPE_HSDPA:
				phoneType = "HSDPA";
				break;
			case TelephonyManager.NETWORK_TYPE_HSPA:
				phoneType = "HSPA";
				break;
			case TelephonyManager.NETWORK_TYPE_HSUPA:
				phoneType = "HSUPA";
				break;
			case TelephonyManager.NETWORK_TYPE_IDEN:
				phoneType = "IDEN";
				break;
			case TelephonyManager.NETWORK_TYPE_UMTS:
				phoneType = "UMTS";
				break;
			case TelephonyManager.NETWORK_TYPE_UNKNOWN:
				phoneType = "UNKNOWN";
				break;

			}
		}
		// int type = tm.getPhoneType();
		// if (type == TelephonyManager.PHONE_TYPE_CDMA) {
		// phoneType = "CDMA";
		// } else if (type == TelephonyManager.PHONE_TYPE_GSM) {
		// phoneType = "GSM";
		// } else {
		// phoneType = "UNKNOW";
		// }
		return phoneType;
	}

	/**
	 * 获取手机ROM大小
	 * 
	 * @return
	 */
	public static String getPhoneRom() {
		String path = Environment.getDataDirectory().getPath();
		StatFs statFs = new StatFs(path);
		long blockSize = statFs.getBlockSize();
		long totalBlocks = statFs.getBlockCount();
		String romSize = String.valueOf(blockSize * totalBlocks / 1024);

		return romSize;
	}

	/**
	 * 获取手机RAM大小 从文件里读取到的信息格式为 MemTotal: 407924 kB
	 * 
	 * @return
	 */
	public static String getPhoneRam() {
		String ramSize = "0";
		String str1 = "/proc/meminfo";
		String str2 = "";
		try {
			FileReader fr = new FileReader(str1);
			BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
			while ((str2 = localBufferedReader.readLine()) != null) {
				if (str2.contains("MemTotal")) {
					ramSize = str2.substring(9, str2.length()).replace(" ", "").replace("kB", "");
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ramSize;
	}

	/**
	 * 获取基带版本
	 * 
	 * @return
	 */
	public static String getBaseband() {
		String result = "";
		try {
			Class cl = Class.forName("android.os.SystemProperties");
			Object invoker = cl.newInstance();
			Method m = cl.getMethod("get", new Class[] { String.class, String.class });
			result = (String) m.invoke(invoker, new Object[] { "gsm.version.baseband", "no message" });
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取序列号
	 * 
	 * @return
	 */
	public static String getSerial() {
		String serial = "";
		try {
			Class cl = Class.forName("android.os.SystemProperties");
			Object invoker = cl.newInstance();

			Method get = cl.getMethod("get", String.class);
			serial = (String) get.invoke(cl, "ro.serialno");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return serial;
	}

	/**
	 * 获取内核版本
	 * 
	 * @return
	 */
	public static String getCoreVersion() {
		Process process = null;
		try {
			process = Runtime.getRuntime().exec("cat /proc/version");
		} catch (IOException e) {
			e.printStackTrace();
		}
		InputStream outs = process.getInputStream();
		InputStreamReader isrout = new InputStreamReader(outs);
		BufferedReader brout = new BufferedReader(isrout, 8 * 1024);
		String result = "";
		String line;
		try {
			while ((line = brout.readLine()) != null) {
				result += line;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (result != "") {
			String Keyword = "version ";
			int index = result.indexOf(Keyword);
			line = result.substring(index + Keyword.length());
			index = line.indexOf(" ");
			result = line.substring(0, index);
		}
		return result;
	}

	// 运营商标识
	public static final String USER_OPERATOR_CHINA_MOBILE = "中国移动";
	public static final String USER_OPERATOR_CHINA_UNICOM = "中国联通";
	public static final String USER_OPERATOR_CHINA_TELECOM = "中国电信";
	public static final String USER_OPERATOR_CHINA_TIETONG = "中国铁通";

	/**
	 * 获取手机运营商
	 * 
	 * @param imsi
	 * @return
	 */
	public static String getProvidersName(Context context, String imsi) {
		String providersName = null;
		if (null == context) {
			return null;
		}
		if (null != imsi && !TextUtils.isEmpty(imsi)) {
			if (imsi.startsWith("46000") || imsi.startsWith("46002") || imsi.startsWith("46007")) {
				// 中国移动
				providersName = USER_OPERATOR_CHINA_MOBILE;
			} else if (imsi.startsWith("46001") || imsi.startsWith("46006")) {
				// 中国联通
				providersName = USER_OPERATOR_CHINA_UNICOM;
			} else if (imsi.startsWith("46003") || imsi.startsWith("46005")) {
				// 中国电信
				providersName = USER_OPERATOR_CHINA_TELECOM;
			} else if (imsi.startsWith("46020")) {
				// 中国铁通
				providersName = USER_OPERATOR_CHINA_TIETONG;
			} else {
				// 中国移动
				providersName = USER_OPERATOR_CHINA_MOBILE;
			}
		}
		return providersName;
	}

	/**
	 * 获取手机的MAC地址
	 * 
	 * @param context
	 * @return
	 */
	public static String getMobileMac(final Context context) {

		if (null == context)
			return null;

		WifiManager wifiMng = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		if (null == wifiMng || null == wifiMng.getConnectionInfo()) {
			return null;
		}
		WifiInfo wifiInfor = wifiMng.getConnectionInfo();
		return wifiInfor.getMacAddress();
	}

	/**
	 * 获取手机模式
	 * 
	 * @return
	 */
	public static String getMobileType() {
		return Build.MODEL;
	}

	/**
	 * 获取厂商名称
	 * 
	 * @return
	 */
	public static String getMobileProduct() {
		return Build.PRODUCT;
	}

	/**
	 * 获取主板
	 * 
	 * @return
	 */
	public static String getMobileBoard() {
		return Build.BOARD;
	}

	/**
	 * 获取系统版本
	 * 
	 * @return
	 */
	public static String getMobileVersion() {
		return Build.VERSION.RELEASE;
	}

	/**
	 * 获取系统定制商
	 * 
	 * @return
	 */
	public static String getMobileCustomer() {
		return Build.BRAND;
	}

	/**
	 * 获取build time
	 * 
	 * @return
	 */
	public static String getMobileBuildTime() {
		return String.valueOf(Build.TIME);
	}

	/**
	 * 获取设备的名称
	 * 
	 * @return
	 */
	public static String getMobileName() {
		return Build.DEVICE;
	}

	/**
	 * 获取手机的分辨率
	 * 
	 * @param context
	 * @return
	 */
	public static DisplayMetrics getMobileDisplay(final Context context) {
		if (null == context)
			return null;

		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm;
	}

	/**
	 * 获取当前安装的软件版本（开发者自己定义的）
	 * 
	 * @param context
	 * @return
	 */
	public static String getSoftCurVersion(final Context context) {
		if (null == context)
			return null;
		PackageManager pkm = context.getPackageManager();
		if (null == pkm) {
			return null;
		}
		try {
			PackageInfo packageInfo = pkm.getPackageInfo(context.getPackageName(), 0);
			if (null != packageInfo)
				return packageInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取当前安装的软件基本信息（开发者自己定义的）
	 * 
	 * @param context
	 * @return
	 */
	public static PackageInfo getCurSoftInfo(final Context context) {
		if (null == context)
			return null;
		PackageManager pkm = context.getPackageManager();
		if (null == pkm) {
			return null;
		}
		try {
			return pkm.getPackageInfo(context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取当前的位置
	 * 
	 * @param context
	 * @return
	 */
	public static Location getCurLocation(final Context context) {

		if (null == context)
			return null;

		LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		if (null == manager) {
			return null;
		}

		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		String provider = manager.getBestProvider(criteria, true);
		if (TextUtils.isEmpty(provider)) {
			provider = LocationManager.NETWORK_PROVIDER;
		}
		return manager.getLastKnownLocation(provider);
	}

	/**
	 * 获取基站字符串
	 * 
	 * @param context
	 * @return
	 */
	public static String getCellLocaltion(final Context context) {
		if (null == context)
			return null;

		/** 调用API获取基站信息 */

		TelephonyManager mTelNet = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		if (null == mTelNet)
			return null;

		final int nPhoneType = mTelNet.getPhoneType();
		String str = "";
		if (nPhoneType == TelephonyManager.PHONE_TYPE_GSM) {
			GsmCellLocation location = (GsmCellLocation) mTelNet.getCellLocation();
			if (location == null)
				return null;
			String operator = mTelNet.getNetworkOperator();
			if (!TextUtils.isEmpty(operator)) {
				String mcc = operator.substring(0, 3);
				String mnc = operator.substring(3);
				int cid = location.getCid();
				int lac = location.getLac();
				str = mcc + ":" + mnc + ":" + cid + ":" + lac;
			}
		} else if (nPhoneType == TelephonyManager.PHONE_TYPE_CDMA) {
			// 电信双卡双待手机可能会出现ClassCastException，所以对异常进行如此处理
			try {
				CdmaCellLocation location = (CdmaCellLocation) mTelNet.getCellLocation();
				if (location == null)
					return null;
				String operator = mTelNet.getNetworkOperator();
				if (!TextUtils.isEmpty(operator)) {
					String mcc = operator.substring(0, 3);
					String mnc = operator.substring(3);
					int cid = location.getBaseStationLongitude();
					int lac = location.getBaseStationLatitude();
					str = mcc + ":" + mnc + ":" + cid + ":" + lac;
				}
			} catch (ClassCastException e) {
				GsmCellLocation location = (GsmCellLocation) mTelNet.getCellLocation();
				if (location == null)
					return null;
				String operator = mTelNet.getNetworkOperator();
				if (!TextUtils.isEmpty(operator)) {
					String mcc = operator.substring(0, 3);
					String mnc = operator.substring(3);
					int cid = location.getCid();
					int lac = location.getLac();
					str = mcc + ":" + mnc + ":" + cid + ":" + lac;
				}
			}
		}
		return str;
	}

	public static void onGetSmsActivity(final Context context, final long threadId) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		// intent.setAction(Intent.ACTION_MAIN);
		// intent.setType("vnd.android-dir/mms-sms");
		intent.setData(Uri.parse("content://mms-sms/conversations/" + threadId));// 此为号码
		context.startActivity(intent);
	}
}
