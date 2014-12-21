package com.ad.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;

/**
 * 
 * 
 * @类名称 ForumNetListenerBasicActivity
 * @描述: 带有网络监听器的基础
 * @开发者: andy.xu
 * @时间: 2014-8-18 下午3:37:26
 * 
 */
public abstract class NetListenerBasicActivity extends Activity {

	private NetReceiver mNetReceiver = null;

	/**
	 * 
	 * @函数名称:网络处于连接状�?
	 * @描述:
	 * @参数
	 * @返回�?void
	 * @异常
	 */
	public abstract void onConnected();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		onRegisterNetBroadcast();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		onRemoveRegisterNetBroadcast();
	}

	/**
	 * 
	 * @函数名称: 网络中断
	 * @描述:
	 * @参数
	 * @返回�?void
	 * @异常
	 */
	public void onConnectAbort() {
		Toast toast = Toast.makeText(getApplicationContext(), "网络中断，请打开网络!", Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	/**
	 * 
	 * @函数名称:添加网络监听�?
	 * @描述:
	 * @参数
	 * @返回�?void
	 * @异常
	 */
	public void onRegisterNetBroadcast() {

		if (null == mNetReceiver) {
			mNetReceiver = new NetReceiver();
		}
		mNetReceiver.onSetListener(new NetStateListener() {
			@Override
			public void onConnectState(boolean bConnectFlag) {
				if (bConnectFlag)
					onConnected();
				else
					onConnectAbort();
			}
		});
		IntentFilter mFilter = new IntentFilter();
		mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		registerReceiver(mNetReceiver, mFilter);
	}

	/**
	 * 
	 * @函数名称:
	 * @描述:
	 * @参数
	 * @返回�?void
	 * @异常
	 */
	public void onRemoveRegisterNetBroadcast() {
		if (null != mNetReceiver)
			unregisterReceiver(mNetReceiver);
	}

	/**
     * */
	public class NetReceiver extends BroadcastReceiver {

		private Context mContext = null;

		private boolean mIsConnect = false;

		private NetStateListener mListener = null;

		@Override
		public void onReceive(Context context, Intent intent) {

			mContext = context;

			ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo gprs = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

			if (!gprs.isConnected() && !wifi.isConnected()) {

				if (null != mListener)
					mListener.onConnectState(false);

				mIsConnect = false;
			} else {

				if (null != mListener)
					mListener.onConnectState(true);
				mIsConnect = false;
			}
		}

		public synchronized boolean isConnect() {
			return mIsConnect;
		}

		public synchronized void netSettingOper() {

		}

		public void onSetListener(final NetStateListener listener) {
			mListener = listener;
		}
	}

	public interface NetStateListener {
		public void onConnectState(boolean bConnectFlag);
	}
}
