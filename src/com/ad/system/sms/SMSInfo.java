package com.ad.system.sms;

import java.io.Serializable;

import android.database.Cursor;
import android.text.TextUtils;

public class SMSInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5516584600213904393L;

	public String id; // ϵͳ�����ֶ�
	public String thread_id; // ��ţ�ͬһ�������˵�id��ͬ
	public String address; // �������ֻ����
	public String person; // ��ϵ���б��е���ţ�İ����ΪNULL
	public String date; // ��������
	public String protocol; // Э�� 0:sms 1:mms
	public String read; // 0:δ�� 1:�Ѷ�
	public String status; // -1������ 0: complete 64: pending 128: failed
	public String type; // ALL = 0; INBOX = 1; SENT = 2; DRAFT = 3; OUTBOX = 4;
	// FAILED = 5; QUEUED = 6;
	public String body; // ��������
	public String service_center; // ���ŷ������ĺ���༭
	public String subject; // ��������

	public SMSInfo setData(final Cursor cur) {

		if (null == cur)
			return null;

		for (int i = 0; i < cur.getColumnCount(); i++) {

			String curName = cur.getColumnName(i);
			if (!TextUtils.isEmpty(curName)) {
				if (curName.equalsIgnoreCase("_id"))
					id = cur.getString(i);
				else if (curName.equalsIgnoreCase("thread_id"))
					thread_id = cur.getString(i);
				else if (curName.equalsIgnoreCase("address"))
					address = cur.getString(i);
				else if (curName.equalsIgnoreCase("person"))
					person = cur.getString(i);
				else if (curName.equalsIgnoreCase("date"))
					date = cur.getString(i);
				else if (curName.equalsIgnoreCase("protocol"))
					protocol = cur.getString(i);
				else if (curName.equalsIgnoreCase("read"))
					read = cur.getString(i);
				else if (curName.equalsIgnoreCase("status"))
					status = cur.getString(i);
				else if (curName.equalsIgnoreCase("type"))
					type = cur.getString(i);
				else if (curName.equalsIgnoreCase("body"))
					body = cur.getString(i);
				else if (curName.equalsIgnoreCase("service_center"))
					service_center = cur.getString(i);
				else if (curName.equalsIgnoreCase("subject"))
					subject = cur.getString(i);
			}
		}
		return this;
	}
}
