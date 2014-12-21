package com.ad.system;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

/**
 * 
 * 
 * @类名称: PhoneState
 * @描述: 
 * @开发者: andy.xu
 * @时间: 2014-8-20 上午11:33:05
 *
 */
public class PhoneState {

	private TelephonyManager teleManager;

	private static PhoneState mSelf;
	private Context mContext;

	public CallState callstate;

	public enum CallState {
		idle, offhook, ringing
	}

	private PhoneState() {

	}

	public void registerPhoneListener(Context context) {
		mContext = context;
		teleManager = (TelephonyManager) context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
		RegisterListener();
	}

	public static PhoneState getInstance() {
		if (mSelf == null) {
			mSelf = new PhoneState();
		}
		return mSelf;
	}

	// 注册电话状态监听器
	public void RegisterListener() {
		teleManager.listen(new MyPhoneStateListener(), PhoneStateListener.LISTEN_CALL_STATE);
	}

	class MyPhoneStateListener extends PhoneStateListener {
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			super.onCallStateChanged(state, incomingNumber);
			switch (state) {
			// 空闲 没有电话打入或者打出 处于挂机空闲状态
			case TelephonyManager.CALL_STATE_IDLE:

				// 未接电话
				if (callstate == CallState.ringing) {

				}
				// 接电话
				else if (callstate == CallState.offhook) {

				}
				callstate = CallState.idle;
				break;
			// 摘机 有电话打出或者打入 按接听接或者其它操作拨打或接听来电
			case TelephonyManager.CALL_STATE_OFFHOOK:
				callstate = CallState.offhook;
				break;
			// 振铃 在此处我需要在振铃的时候自动接听电话并且打开扬声器和新启动一个Activity
			case TelephonyManager.CALL_STATE_RINGING:

				callstate = CallState.ringing;
				break;
			} // 保存当前电话状态

		}
	}

	public CallState getCallstate() {
		return callstate;
	}

}
