package com.ad.system;

import com.ad.util.AdConstantDefine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.telephony.TelephonyManager;

/**
 * 
 * 
 * @类名称: LockReceive
 * @描述: 锁屏启动
 * @开发者: andy.xu
 * @时间: 2014-8-20 下午2:09:11
 * @desp: <br>
 *        receiver android:name="com.akrismina.lockscreen.broadcast.LockReceive"
 *        android:process=":background" </br> <br>
 *        intent-filter android:priority="2147483647" </br> <br>
 *        action android:name="android.intent.action.SCREEN_OFF"</br> <br>
 *        category android:name="android.intent.category.HOME" </br>
 * 
 */
public class LockReceive extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		{
			try {

				abortBroadcast();

				if (isSystemLockUsed(context))
					return;

				if (isCanGetLock(context) == false)
					return;

				Intent curIntent = new Intent(AdConstantDefine.SYSTEM_SCREEN_ON_BROADCAST);
				context.sendBroadcast(curIntent);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private boolean isCanGetLock(final Context context) {

		if (null == context)
			return true;

		TelephonyManager telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		if (null == telManager)
			return true;

		switch (telManager.getCallState()) {
		default:
			break;
		// 待机中
		case TelephonyManager.CALL_STATE_IDLE:
			break;
		// 通话中
		case TelephonyManager.CALL_STATE_OFFHOOK:
			// 拨号中
		case TelephonyManager.CALL_STATE_RINGING:
			return false;
		}
		return true;
	}

	private boolean isSystemLockUsed(final Context context) {

		try {
			int LockPatternEnabled = Settings.System.getInt(context.getContentResolver(), Settings.System.LOCK_PATTERN_ENABLED);

			if (LockPatternEnabled == 1)
				return true;
		} catch (SettingNotFoundException e) {
			e.printStackTrace();
		}

		return false;
	}
}
