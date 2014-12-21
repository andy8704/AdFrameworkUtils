package com.ad.system.sms;

import java.io.Serializable;

import android.database.Cursor;
import android.text.TextUtils;

/**
 * 
 * 
 * @类名称: ThreadsInfo
 * @描述:
 * @开发者: andy.xu
 * @时间: 2014-8-20 下午4:32:55
 * 
 */
public class ThreadsInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6478494677790688047L;

	public String _id;
	public String date; // 最新时间
	public String message_count; // 短信的条数
	public String recipient_addresses; // 发件人的电话
	public String snippet; // 最后一条短信内容
	public String read; // 读取状态
	public String recipient_names; // 发件人的名称
	public String address;// 发件人的电话
	public String unread_message_count; // 未读短信统计

	public ThreadsInfo() {

		_id = null;
		date = null;
		message_count = null;
		recipient_addresses = null;
		snippet = null;
		read = null;
		recipient_names = null;
		address = null;
		unread_message_count = null;
	}

	public ThreadsInfo setData(final Cursor cur) {

		if (null == cur)
			return null;

		for (int i = 0; i < cur.getColumnCount(); i++) {

			String curName = cur.getColumnName(i);
			if (!TextUtils.isEmpty(curName)) {
				if (curName.equalsIgnoreCase("_id"))
					_id = cur.getString(i);
				else if (curName.equalsIgnoreCase("date"))
					date = cur.getString(i);
				else if (curName.equalsIgnoreCase("message_count"))
					message_count = cur.getString(i);
				else if (curName.equalsIgnoreCase("recipient_addresses"))
					recipient_addresses = cur.getString(i);
				else if (curName.equalsIgnoreCase("snippet"))
					snippet = cur.getString(i);
				else if (curName.equalsIgnoreCase("read"))
					read = cur.getString(i);
				else if (curName.equalsIgnoreCase("recipient_names"))
					recipient_names = cur.getString(i);
				else if (curName.equalsIgnoreCase("address"))
					address = cur.getString(i);
				else if (curName.equalsIgnoreCase("unread_message_count"))
					unread_message_count = cur.getString(i);
			}
		}

		return this;
	}
}
