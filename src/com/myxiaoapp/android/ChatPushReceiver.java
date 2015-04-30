/**
 * 2015年4月13日
 * ken
 */
package com.myxiaoapp.android;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.myxiaoapp.utils.DataBaseHelper;
import com.myxiaoapp.utils.DataManager;
import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushReceiver;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;

/**
 * @author ken
 *
 */
public class ChatPushReceiver extends XGPushBaseReceiver {

	private static final String TAG = "ChatPushReceiver";

	/* 
	 * @see com.tencent.android.tpush.XGPushBaseReceiver#onDeleteTagResult(android.content.Context, int, java.lang.String)
	 */
	@Override
	public void onDeleteTagResult(Context arg0, int arg1, String arg2) {
	}

	/* 
	 * @see com.tencent.android.tpush.XGPushBaseReceiver#onNotifactionClickedResult(android.content.Context, com.tencent.android.tpush.XGPushClickedResult)
	 */
	@Override
	public void onNotifactionClickedResult(Context arg0,
			XGPushClickedResult arg1) {
	}

	/* 
	 * @see com.tencent.android.tpush.XGPushBaseReceiver#onNotifactionShowedResult(android.content.Context, com.tencent.android.tpush.XGPushShowedResult)
	 */
	@Override
	public void onNotifactionShowedResult(Context arg0, XGPushShowedResult arg1) {
	}

	/* 
	 * @see com.tencent.android.tpush.XGPushBaseReceiver#onRegisterResult(android.content.Context, int, com.tencent.android.tpush.XGPushRegisterResult)
	 */
	@Override
	public void onRegisterResult(Context arg0, int arg1,
			XGPushRegisterResult arg2) {
	}

	/* 
	 * @see com.tencent.android.tpush.XGPushBaseReceiver#onSetTagResult(android.content.Context, int, java.lang.String)
	 */
	@Override
	public void onSetTagResult(Context arg0, int arg1, String arg2) {
	}

	/* 
	 * @see com.tencent.android.tpush.XGPushBaseReceiver#onTextMessage(android.content.Context, com.tencent.android.tpush.XGPushTextMessage)
	 */
	@Override
	public void onTextMessage(Context arg0, XGPushTextMessage arg1) { 
		Log.d(TAG,"receive message " + arg1.getContent());

        try {
            JSONObject json = new JSONObject(arg1.getContent());
            String msgType = json.getString("msg_type");
            if (msgType.equals("chat")) {               //接收到聊天信息
                json = json.getJSONObject("msg");
                handleChatMessage(arg0, json);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
	}
	private void handleChatMessage(Context context, JSONObject json) throws JSONException {
        DataBaseHelper.saveChat(json);
        String fromUserId = json.getString("fromUserId");
        String fromName = json.getString("fromName");
        String fromPortrait = json.getString("fromPortrait");
        String message = json.getString("message");
        Log.d(TAG, json.toString());
        if (DataManager.getInstance() != null && fromUserId.equals(DataManager.getInstance().getmFrontChat() )) {
            Intent intent = new Intent(ChatPanelActivity.ACTION_RECEIVE_CHAT);
            intent.putExtra("data", json.toString());
            context.sendBroadcast(intent);
        } else {
            Intent intent = new Intent(context, ChatPanelActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("fromUserId", fromUserId);
            intent.putExtra("fromName", fromName);
            intent.putExtra("fromPortrait", fromPortrait);
            intent.putExtra("message",message);
            Log.d(TAG, "data="+json.toString());
            intent.putExtra("data", json.toString());
            pushNotification(context, intent, "收到聊天", fromName, message); 
        }
    }
	private void pushNotification(Context context, Intent intent, String ticker, String title, String contentText) throws JSONException {
        Log.d(TAG,"push notification");
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent[] intents = new Intent[1];
        intents[0] = intent;
        PendingIntent pendingIntent = PendingIntent.getActivities(context, R.string.app_name, intents, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new Notification.Builder(context)
                .setAutoCancel(true)
                .setTicker(ticker)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                .setContentText(contentText)
                .setDefaults(Notification.DEFAULT_SOUND
                        | Notification.DEFAULT_LIGHTS)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent).getNotification();

        notification.flags = Notification.FLAG_AUTO_CANCEL;
        manager.notify(R.string.app_name, notification);
    }
	/* 
	 * @see com.tencent.android.tpush.XGPushBaseReceiver#onUnregisterResult(android.content.Context, int)
	 */
	@Override
	public void onUnregisterResult(Context arg0, int arg1) {
	}

}
