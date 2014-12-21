
package com.ad.util;

import java.util.List;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.telephony.SmsManager;
import android.text.TextUtils;

import com.example.adframeworkutil.R;

/**
 * 发送短信类
 * 
 * @author andy.xu
 */
public class SendSMS {
	
	private final static String SMS_BODY_SIGN = "sms_body";
	private final static String SMS_TYPE_SIGN = "vnd.android-dir/mms-sms";
	private final static String IMAGE_TYPE_SIGN = "image/jpeg";

    private Context m_Context;

    /**
     * 构造函数
     */
    public SendSMS(Context context) {
        this.m_Context = context;
    }

    /**
     * 发送信息
     * 
     * @param szSmsBody the SMS body of send
     */

    public void onSendSMS(String szSmsBody) {
        if(null == m_Context){
            return;
        }

        if (TextUtils.isEmpty(szSmsBody))
            return;

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (szSmsBody != null)
            intent.putExtra(SMS_BODY_SIGN, szSmsBody.trim());
        intent.setType(SMS_TYPE_SIGN);
        m_Context.startActivity(intent);
    }

    public void onSendSMSToTel(final String szSmsBody, final String telNum) {
        if(null == m_Context){
            return;
        }

        if (TextUtils.isEmpty(telNum))
            return;

        /*
         * Intent intent = new Intent(Intent.ACTION_VIEW);
         * intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
         */
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("smsto:" + telNum));
        if (szSmsBody != null)
            intent.putExtra(SMS_BODY_SIGN, szSmsBody.trim());
        // intent.setType(CommonDefine.SMS_TYPE_SIGN);
        m_Context.startActivity(intent);
    }

    public void onSendSMS(final String szTitle, final String szSmsBody) {
        if(null == m_Context){
            return;
        }

        if (TextUtils.isEmpty(szSmsBody) || TextUtils.isEmpty(szTitle))
            return;

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (szSmsBody != null)
            intent.putExtra(SMS_BODY_SIGN, szSmsBody.trim());

        if (null != szTitle)
            intent.putExtra(Intent.EXTRA_SUBJECT, szTitle);

        if (null != szSmsBody)
            intent.putExtra(Intent.EXTRA_TEXT, szSmsBody.trim());

        intent.setType(SMS_TYPE_SIGN);
        m_Context.startActivity(intent);
    }

    // public void onSendToShare(String szBody){
    // Intent intent = new Intent(Intent.ACTION_SEND);
    // if(szBody != null)
    // intent.putExtra(android.content.Intent.EXTRA_TEXT, szBody.trim());
    // intent.setType("*/*");
    // m_Context.startActivity(intent);
    // }

    // public void onSendToShare(String szBody, String szStream){
    // Intent intent = new Intent(Intent.ACTION_SEND);
    // // if(szBody != null)
    // // intent.putExtra(android.content.Intent.EXTRA_TEXT, szBody.trim());
    // // if(szStream != null)
    // // intent.putExtra(android.content.Intent.EXTRA_STREAM, "file://" +
    // szStream.trim());
    //
    // Uri uri = Uri.parse("file://" + szStream.trim());
    // intent.putExtra("sms_body",szBody.trim() );
    // intent.putExtra(Intent.EXTRA_STREAM, uri);
    //
    // intent.setType("image/jpeg");
    // m_Context.startActivity(intent);
    // }

    public void onSendMMS(String szTitle, String szBody, String szImagePath) {
        if(null == m_Context){
            return;
        }

        if (TextUtils.isEmpty(szBody) || TextUtils.isEmpty(szTitle)
                || TextUtils.isEmpty(szImagePath))
            return;

        Intent intent = new Intent(Intent.ACTION_SEND);
        // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = null;
        if (null != szImagePath)
            uri = Uri.parse("file://" + szImagePath.trim());

        if (null != szBody)
            intent.putExtra(SMS_BODY_SIGN, szBody.trim());

        if (null != uri)
            intent.putExtra(Intent.EXTRA_STREAM, uri);

        if (null != szTitle)
            intent.putExtra(Intent.EXTRA_SUBJECT, szTitle);

        if (null != szBody)
            intent.putExtra(Intent.EXTRA_TEXT, szBody.trim());

        intent.setType(IMAGE_TYPE_SIGN);
        m_Context.startActivity(intent);
    }

    public void onSendMMS(String szBody, String szImagePath) {
        if(null == m_Context){
            return;
        }

        if (TextUtils.isEmpty(szBody) || TextUtils.isEmpty(szImagePath))
            return;

        Intent intent = new Intent(Intent.ACTION_SEND);
        // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.parse("file://" + szImagePath.trim());
        intent.putExtra(SMS_BODY_SIGN, szBody.trim());
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.putExtra(Intent.EXTRA_SUBJECT, szBody.trim());
        intent.putExtra(Intent.EXTRA_TEXT, szBody.trim());

        intent.setType(IMAGE_TYPE_SIGN);
        m_Context.startActivity(intent);
    }

    public void onSendEMail(String szBody, String szImagePath) {
        if(null == m_Context){
            return;
        }

        Intent intent = new Intent(Intent.ACTION_SEND);
        Uri uri = Uri.parse("file://" + szImagePath.trim());
        intent.putExtra(Intent.EXTRA_SUBJECT, szBody.trim());
        intent.putExtra(Intent.EXTRA_TEXT, szBody.trim());
        intent.putExtra(Intent.EXTRA_STREAM, uri);

        intent.setType("image/jpeg");
        m_Context.startActivity(intent);
    }

    public void onSendMail(String userMail, String content) {
        
        if(null == m_Context){
            return;
        }

        if (TextUtils.isEmpty(userMail))
            return;

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {
            userMail
        });
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.putExtra(Intent.EXTRA_SUBJECT, "");
        intent.setType(SMS_TYPE_SIGN);
        Intent.createChooser(intent, "请选择邮件客户端");
        m_Context.startActivity(intent);

    }
    
    public void shareByEmailText(String userMail) {
        if (null == m_Context)
            return;

        Uri uri = Uri.parse("mailto:");
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra(Intent.EXTRA_SUBJECT, "");
        intent.putExtra(Intent.EXTRA_TEXT, "");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {userMail});
        m_Context.startActivity(intent);
    }

    // 直接发送短信

    public void onSendSmsImmediate(final String content, final String telphoneNum,
            final SmsActionListener listener) {

        if (TextUtils.isEmpty(telphoneNum))
            return;

        SmsManager smsManager = SmsManager.getDefault();
        if (null == smsManager)
            return;

        if (TextUtils.isEmpty(content)) {
            return;
        }
        List<String> devidesContent = smsManager.divideMessage(content);
        if (null == devidesContent || devidesContent.isEmpty())
            return;

        PendingIntent sendIntent = registerSmsBackState(listener);
        for (String ele : devidesContent) {
            if (!TextUtils.isEmpty(ele)) {
                smsManager.sendTextMessage(telphoneNum, null, ele, sendIntent, null);
            }
        }
        addSmsToData(content, telphoneNum);
    }

    public void unregisterReceiver() {
        if (null != mReceive) {
            m_Context.unregisterReceiver(mReceive);
        }
    }

    /***
     * 把发送短息的数据添加到系统的短信数据库中
     **/
    private void addSmsToData(final String content, final String telphoneNum) {

        if (TextUtils.isEmpty(content) || TextUtils.isEmpty(telphoneNum))
            return;

        ContentValues values = new ContentValues();

        // 当前日期时间
        values.put("date", System.currentTimeMillis());

        // 阅读状态
        values.put("read", false);

        // 1为收， 2为发
        values.put("type", 2);

        // 送达号码
        values.put("address", telphoneNum);

        // 送达内容
        values.put("body", content);
        m_Context.getContentResolver().insert(Uri.parse("content://sms/sent"), values);
    }

    private BroadcastReceiver mReceive = null;

    private PendingIntent registerSmsBackState(final SmsActionListener listener) {

        String SENT_SMS_ACTION = "SENT_SMS_ACTION";
        Intent sentIntent = new Intent(SENT_SMS_ACTION);
        PendingIntent sentPI = PendingIntent.getBroadcast(m_Context, 0, sentIntent, 0);
        // register the Broadcast Receivers
        mReceive = new BroadcastReceiver() {
            @Override
            public void onReceive(Context _context, Intent _intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        if (null != listener) {
                            listener.onSmsListener(true);
                        }
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        break;
                }
            }
        };
        m_Context.registerReceiver(mReceive, new IntentFilter(SENT_SMS_ACTION));

        return sentPI;
    }

    public interface SmsActionListener {

        public void onSmsListener(boolean bFlag);
    }

    private MessageTypeListener messageListener = null;

    public void setMessageTypeListener(MessageTypeListener messageTypeListener) {
        messageListener = messageTypeListener;
    }

    public static enum MESSAGE_STATU_TYPE {
        MESSAGE_OK, MESSAGE_ERROR_GENERIC_FAILURE, MESSAGE_ERROR_RADIO_OFF, MESSAGE_ERROR_NULL_PDU
    }

    public interface MessageTypeListener {
        public void onMessageListener(MESSAGE_STATU_TYPE type);
    }

    // 发送短信并返回短信状态

    public void onSendSmsAndGetStatus(final String content, final String telphoneNum,
            final MessageTypeListener listener) {

        messageListener = listener;

        if (TextUtils.isEmpty(telphoneNum))
            return;

        SmsManager smsManager = SmsManager.getDefault();
        if (null == smsManager)
            return;

        if (TextUtils.isEmpty(content)) {
            return;
        }
        List<String> devidesContent = smsManager.divideMessage(content);
        if (null == devidesContent || devidesContent.isEmpty())
            return;

        PendingIntent sendIntent = registerSmsBackReuslt(content, telphoneNum, listener);
        for (String ele : devidesContent) {
            if (!TextUtils.isEmpty(ele)) {
                smsManager.sendTextMessage(telphoneNum, null, ele, sendIntent, null);
            }
        }
    }

    private BroadcastReceiver mResultStatusReceive = null;

    private PendingIntent registerSmsBackReuslt(final String content, final String telphoneNum,
            final MessageTypeListener listener) {

        String SENT_SMS_ACTION = "SENT_SMS_ACTION";
        Intent sentIntent = new Intent(SENT_SMS_ACTION);
        PendingIntent sentPI = PendingIntent.getBroadcast(m_Context, 0, sentIntent, 0);
        // register the Broadcast Receivers
        mResultStatusReceive = new BroadcastReceiver() {
            @Override
            public void onReceive(Context _context, Intent _intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        if (null != messageListener) {
                            messageListener.onMessageListener(MESSAGE_STATU_TYPE.MESSAGE_OK);
                        }
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        if (null != messageListener) {
                            messageListener
                                    .onMessageListener(MESSAGE_STATU_TYPE.MESSAGE_ERROR_GENERIC_FAILURE);
                        }
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        if (null != messageListener) {
                            messageListener
                                    .onMessageListener(MESSAGE_STATU_TYPE.MESSAGE_ERROR_RADIO_OFF);
                        }
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        if (null != messageListener) {
                            messageListener
                                    .onMessageListener(MESSAGE_STATU_TYPE.MESSAGE_ERROR_NULL_PDU);
                        }
                        break;
                }
            }
        };
        m_Context.registerReceiver(mResultStatusReceive, new IntentFilter(SENT_SMS_ACTION));

        return sentPI;
    }

    public void unregisterMessageStatusReceiver() {
        if (null != mResultStatusReceive) {
            m_Context.unregisterReceiver(mResultStatusReceive);
        }
    }
}
