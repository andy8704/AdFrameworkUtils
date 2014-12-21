package com.ad.system.sms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;

public class SMSManager {

	public static final String SMS_ALL = "content://sms"; // 所有短信
	public static final String SMS_INBOX = "content://sms/inbox"; // 收件箱
	public static final String SMS_SENT = "content://sms/sent"; // 发送的短信
	public static final String SMS_DRAFT = "content://sms/draft"; // 草稿
	public static final String SMS_OUTBOX = "content://sms/outbox"; // 发件箱
	public static final String SMS_FAILED = "content://sms/failed"; // 发送失败
	public static final String SMS_QUEUED = "content://sms/queued"; // 发送队列

	private Context mContext = null;

	public SMSManager(Context context) {

		mContext = context;
	}

	/**
	 * 
	 * @描述: 获取所有的短信界面的信息（发件人 < -- > 最后短信的内容）
	 * @参数 @return
	 * @返回值 List<ThreadsInfo>
	 * @异常
	 */
	public List<ThreadsInfo> onGetAllThreads() {

		Uri uri = Uri.parse(SMS_ALL);
		// Cursor cur = mContext.getContentResolver().query(uri,
		// new String[] { " * from threads --" }, null, null, null);

		Cursor cur = mContext
				.getContentResolver()
				.query(uri,
						new String[] { " threads._id, threads.date, threads.message_count, threads.recipient_ids, threads.snippet, sms.address from threads, sms where sms.thread_id = threads._id group by threads._id order by threads.date DESC -- " },
						null, null, null);

		if (null == cur)
			return null;

		List<ThreadsInfo> data = new ArrayList<ThreadsInfo>(cur.getCount());
		while (cur.moveToNext()) {

			// String temp = "";
			// for (int i = 0; i < cur.getColumnCount(); i++) {
			//
			// temp += cur.getColumnName(i) + ":" + cur.getString(i) + "\n";
			// }
			//
			// Log.i("==>", temp);

			ThreadsInfo info = new ThreadsInfo();
			info = info.setData(cur);

			info = onGetPeopleInfo(info);
			// info.unread_message_count = getUnreadMessageNumber(info._id);

			if (null != info)
				data.add(info);
		}

		if (null != cur)
			cur.close();

		return data;
	}

	/**
	 * 
	 * @描述: 获取当前个人的所有短信列表
	 * @参数 @param threads_id
	 * @参数 @return
	 * @返回值 List<SMSInfo>
	 * @异常
	 */
	public List<SMSInfo> onGetOnePersonInfo(final String threads_id) {

		if (TextUtils.isEmpty(threads_id))
			return null;

		return getSmsInfo("content://sms/conversations/" + threads_id);
	}

	private ThreadsInfo onGetPeopleInfo(ThreadsInfo info) {

		if (null == info)
			return null;

		if (TextUtils.isEmpty(info.address))
			return info;

		String number = info.address.replace("+86", "").replace("+8", "").replace("+", "");

		Cursor cur = mContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
				new String[] { ContactsContract.PhoneLookup.DISPLAY_NAME }, ContactsContract.CommonDataKinds.Phone.NUMBER + " like '%" + number + "%'", null,
				null);
		if (null != cur && cur.moveToNext()) {
			info.recipient_names = cur.getString(cur.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
		}

		if (null != cur)
			cur.close();

		return info;
	}

	// private ThreadsInfo onGetPeople(ThreadsInfo info) {
	//
	// if (null == info)
	// return null;
	//
	// if (null == info.person)
	// return info;
	//
	// Cursor cur = mContext.getContentResolver().query(
	// ContactsContract.Contacts.CONTENT_URI,
	// null, ContactsContract.Contacts._ID + " = " + info.person, null, null);
	// if (null != cur && cur.moveToNext()) {
	// info.name = cur.getString(cur
	// .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
	// }
	//
	// if (null != cur)
	// cur.close();
	//
	// return info;
	// }

	/***
	 * get all sms
	 * **/
	public List<SMSInfo> onGetAllSms() {

		return getSmsInfo(SMS_ALL);
	}

	public Map<String, List<SMSInfo>> onGetAllSmsMap() {

		Uri uri = Uri.parse(SMS_ALL);
		Cursor cur = mContext.getContentResolver().query(uri, null, null, null, "thread_id DESC");

		Map<String, List<SMSInfo>> data = new HashMap<String, List<SMSInfo>>();

		while (null != cur && cur.moveToNext()) {

			SMSInfo info = new SMSInfo();
			info = info.setData(cur);
			if (null != info && !TextUtils.isEmpty(info.thread_id)) {
				if (data.containsKey(info.thread_id)) {
					data.get(info.thread_id).add(info);
				} else {
					List<SMSInfo> temp = new ArrayList<SMSInfo>();
					temp.add(info);
					data.put(info.thread_id, temp);
				}
			}
		}

		if (null != cur)
			cur.close();

		return data;
	}

	/**
	 * get inbox sms
	 **/
	public List<SMSInfo> onGetInboxSms() {

		return getSmsInfo(SMS_INBOX);
	}

	/***
	 * get outbox sms
	 ***/
	public List<SMSInfo> onGetOutBoxSms() {

		return getSmsInfo(SMS_OUTBOX);
	}

	/**
	 * get sent sms
	 ***/
	public List<SMSInfo> onGetSentSms() {

		return getSmsInfo(SMS_SENT);
	}

	/***
	 * get draft sms
	 ***/
	public List<SMSInfo> onGetDraftSms() {

		return getSmsInfo(SMS_DRAFT);
	}

	/***
	 * get failed sms
	 * **/
	public List<SMSInfo> onGetFailedSms() {

		return getSmsInfo(SMS_FAILED);
	}

	/**
	 * get queued sms
	 * **/
	public List<SMSInfo> onGetQueuedSms() {

		return getSmsInfo(SMS_QUEUED);
	}

	/**
	 * get sms of kinds of type *
	 **/
	private List<SMSInfo> getSmsInfo(final String smsType) {

		if (TextUtils.isEmpty(smsType))
			return null;

		Uri uri = Uri.parse(smsType);
		Cursor cur = mContext.getContentResolver().query(uri, null, null, null, "date ASC");

		List<SMSInfo> data = new ArrayList<SMSInfo>();

		while (null != cur && cur.moveToNext()) {

			SMSInfo info = new SMSInfo();
			info = info.setData(cur);
			if (null != info)
				data.add(info);
		}

		if (null != cur)
			cur.close();

		return data;
	}

	public List<ThreadsInfo> onGetUnreadMessageState() {

		Uri uri = Uri.parse(SMS_ALL);
		Cursor cur = mContext
				.getContentResolver()
				.query(uri,
						new String[] { " threads._id, threads.date, threads.message_count, threads.recipient_ids, threads.snippet, sms.address from threads, sms where sms.thread_id = threads._id group by threads._id order by threads.date DESC -- " },
						null, null, null);

		if (null == cur)
			return null;

		List<ThreadsInfo> data = new ArrayList<ThreadsInfo>(cur.getCount());
		while (cur.moveToNext()) {

			ThreadsInfo info = new ThreadsInfo();
			info = info.setData(cur);

			info = onGetPeopleInfo(info);
			info.unread_message_count = getUnreadMessageNumber(info._id);

			if (null != info)
				data.add(info);
		}

		if (null != cur)
			cur.close();

		return data;
	}

	/**
	 * count unread sms number
	 */
	private String getUnreadMessageNumber(final String smsType) {
		if (TextUtils.isEmpty(smsType))
			return null;

		int counter = 0;
		Uri uri = Uri.parse("content://sms");
		Cursor cur = mContext.getContentResolver().query(uri, null, "sms.read = 0 and sms.thread_id = " + smsType, null, null);

		counter = cur.getCount();
		if (counter > 0) {
			return String.valueOf(counter);
		} else {
			return null;
		}
	}

	public void deleteMessageContent(final String threadId, final String messageId) {
		if (TextUtils.isEmpty(threadId) || TextUtils.isEmpty(messageId))
			return;
		Uri uri = Uri.parse("content://sms/conversations/" + threadId);

		mContext.getContentResolver().delete(uri, "_id = " + messageId, null);
	}
}
