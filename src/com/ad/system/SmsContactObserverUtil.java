package com.ad.system;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.CallLog.Calls;

/**
 * 
 * 
 * @类名称: SmsContactObserverUtil
 * @描述: 系统的短信， 联系人监听器
 * @开发者: andy.xu
 * @时间: 2014-8-19 下午4:25:49
 * 
 */
public class SmsContactObserverUtil {

	private Activity mActivity = null;

	private SmsMmsContentObserver mSmsMmsObserver = null;
	private ContactsContentObserver mContactsObserver = null;

	public SmsContactObserverUtil(final Activity activity) {
		mActivity = activity;
	}

	/**
	 * 
	 * @描述: 获取未读短信的游标
	 * @参数 @return
	 * @返回值 Cursor
	 * @异常
	 */
	public Cursor onGetUnRedSmsCursor() {
		return mActivity.getContentResolver().query(Uri.parse("content://sms"), null, "type = 1 and read = 0", null, null);
	}

	/**
	 * 
	 * @描述: 获取未接来电的游标
	 * @参数 @return
	 * @返回值 Cursor
	 * @异常
	 */
	public Cursor onGetMissCall() {
		String[] projection = new String[] { CallLog.Calls.NUMBER, CallLog.Calls.CACHED_NAME, CallLog.Calls.TYPE, CallLog.Calls.DATE };
		return mActivity.getContentResolver().query(Calls.CONTENT_URI, projection, "type = 3 and new = 1", null, CallLog.Calls.DATE + " DESC ");
	}

	/**
	 * 
	 * @描述:注册系统短信数据库变化监听器
	 * @参数
	 * @返回值 void
	 * @异常
	 */
	public void onRegisterSmsObserver(final ObserverListener listener) {
		if (null == mSmsMmsObserver)
			mSmsMmsObserver = new SmsMmsContentObserver();
		mSmsMmsObserver.onSetObserverListener(listener);
		mActivity.getContentResolver().registerContentObserver(Uri.parse("content://sms"), true, mSmsMmsObserver);
	}

	/**
	 * 
	 * @描述:移除短信数据库变化监听器
	 * @参数
	 * @返回值 void
	 * @异常
	 */
	public void unRegisterSmsObserver() {
		if (null != mSmsMmsObserver)
			mActivity.getContentResolver().unregisterContentObserver(mSmsMmsObserver);
	}

	/**
	 * 
	 * @描述:注册 系统联系人数据库变化监听器
	 * @参数
	 * @返回值 void
	 * @异常
	 */
	public void onRegisterContactObserver(final ObserverListener listener) {
		if (null == mContactsObserver)
			mContactsObserver = new ContactsContentObserver();
		mContactsObserver.onSetObserverListener(listener);
		mActivity.getContentResolver().registerContentObserver(Calls.CONTENT_URI, true, mContactsObserver);
	}

	/**
	 * 
	 * @描述:移除联系人数据库变化监听器
	 * @参数
	 * @返回值 void
	 * @异常
	 */
	public void unRegisterContactObserver() {
		if (null != mContactsObserver)
			mActivity.getContentResolver().unregisterContentObserver(mContactsObserver);
	}
}
