package com.ad.system;

import com.ad.util.AdConstantDefine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;

/**
 * 
 * 
 * @类名称: BootReceive
 * @描述:
 * @开发者: andy.xu
 * @时间: 2014-8-20 下午2:02:44
 * @desp: <br>
 *        receiver android:name="com.akrismina.lockscreen.broadcast.BootReceive"
 *        android:exported="true" android:process=":background" </br>
 *        
 *       <br> action android:name="android.intent.action.BOOT_COMPLETED" </br>
 */
public class BootReceive extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent arg1) {

		try {
			int LockPatternEnabled = Settings.System.getInt(context.getContentResolver(), Settings.System.LOCK_PATTERN_ENABLED);
			if (LockPatternEnabled == 1)
				return;
		} catch (SettingNotFoundException e) {
			e.printStackTrace();
		}

		Intent intent = new Intent(AdConstantDefine.SYSTEM_BOOT_BROADCAST);
		context.sendBroadcast(intent);

		/*
		 * Settings.Secure.putInt(context.getContentResolver(),
		 * Settings.Secure.LOCK_PATTERN_ENABLED, 0);
		 * Settings.Secure.putInt(context.getContentResolver(),
		 * Settings.Secure.LOCK_PATTERN_VISIBLE, 0);
		 */
	}

}