package com.ad.system;

import android.database.ContentObserver;
import android.os.Handler;

/**
 * 
 * 
 * @类名称: ContactsContentObserver
 * @描述: 系统联系人监听器
 * @开发者: andy.xu
 * @时间: 2014-8-19 下午4:22:55
 * 
 */
public class ContactsContentObserver extends ContentObserver {

	private ObserverListener mListener = null;

	public ContactsContentObserver() {
		super(new Handler());
	}

	@Override
	public void onChange(boolean selfChange) {
		super.onChange(selfChange);

		if (null != mListener) {
			mListener.onChange();
		}
	}

	public void onSetObserverListener(final ObserverListener listener) {
		mListener = listener;
	}

}
