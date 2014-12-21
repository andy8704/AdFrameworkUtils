package com.ad.system;

import com.ad.util.AdConstantDefine;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.Intent;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

/**
 * 
 * @类名称: MinaSystemNotificationListener
 * @描述: 监听系统的通知栏的消息数据(发出去的是一个广播， action: "systemNotifyBroadcast ", 参数是：packageName  &  notifyTxt )
 * @开发者: andy.xu
 * @时间: 2014-6-18 下午12:55:58
 * @desp : 在manifest中注册一个service : <br>
 *       android:name=
 *       "com.akrismina.lockscreen.broadcast.MinaSystemNotificationListener"
 *       </br> <br>
 *       android
 *       :permission="android.permission.BIND_NOTIFICATION_LISTENER_SERVICE"
 *       </br> 事件的响应需要过滤intent-filter : <br>
 *       action android:name=
 *       "android.service.notification.NotificationListenerService"</br>
 * 
 */
@SuppressLint("NewApi")
public class SystemNotificationListener extends NotificationListenerService {

	@SuppressLint("Override")
	@Override
	public void onNotificationPosted(StatusBarNotification sbn) {
		if (null != sbn) {
			String packageName = sbn.getPackageName();
			Notification notify = sbn.getNotification();
			String notificationTxt = (String) notify.tickerText;
			Intent intent = new Intent(AdConstantDefine.SYSTEM_NOTIFY_BROADCAST);
			intent.putExtra(AdConstantDefine.PACKAGE_NAME, packageName);
			intent.putExtra(AdConstantDefine.NOTIFY_TXT, notificationTxt);
			sendBroadcast(intent);
		}
	}

	@Override
	public void onNotificationRemoved(StatusBarNotification sbn) {

	}

}
