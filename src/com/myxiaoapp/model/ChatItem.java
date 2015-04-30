package com.myxiaoapp.model;

import org.json.JSONException;
import org.json.JSONObject;

import com.myxiaoapp.android.R;
import com.myxiaoapp.utils.DataManager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class ChatItem {
	private static final String TAG = "ChatItem";
	private String fromUserId;
    private String fromName;
    private String fromPortrait;
    private String toUserId;
    private String toUserName;
    private long time;
    private String message;
    private String mPortrait; 
    private boolean isMeChat;

    /**
	 * @return the fromPortrait
	 */
	public String getFromPortrait() {
		return fromPortrait;
	}

	/**
	 * @param fromPortrait the fromPortrait to set
	 */
	public void setFromPortrait(String fromPortrait) {
		this.fromPortrait = fromPortrait;
	}

	/**
	 * @return the fromName
	 */
	public String getFromName() {
		return fromName;
	}

	/**
	 * @param fromName the fromName to set
	 */
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	/**
	 * @return the mPortrait
	 */
	public String getmPortrait() {
		return mPortrait;
	}

	/**
	 * @param mPortrait the mPortrait to set
	 */
	public void setmPortrait(String mPortrait) {
		this.mPortrait = mPortrait;
	}

	public String getFromUserId() {
        return fromUserId;
    }

    public String getFromUserName() {
        return fromName;
    }

    public String getToUserId() {
        return toUserId;
    }

    public String getToUserName() {
        return toUserName;
    }

    public long getTime() {
        return time;
    }

    public String getMessage() {
        return message;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public void setFromUserName(String fromUserName) {
        this.fromName = fromUserName;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getIsMeChat() {
        return isMeChat;
    }

    public void setIsMeChat(boolean b) {
        isMeChat = b;
    }
 
 
 
	/**
	 * @param isMeChat the isMeChat to set
	 */
	public void setMeChat(boolean isMeChat) {
		this.isMeChat = isMeChat;
	}

	public static ChatItem chat2ChatItem(Chat chat) {
        ChatItem chatItem = new ChatItem();
        chatItem.fromUserId = chat.fromUserId;
        chatItem.fromName = chat.fromName;
        chatItem.toUserId = chat.toUserId;
        chatItem.toUserName = chat.toName;
        chatItem.message = chat.message;
        chatItem.time = Long.parseLong(chat.time);
     //   chatItem.mPortrait = chat.
        chatItem.isMeChat = chatItem.fromUserId.equals(DataManager.getInstance().getLoginUserId() + "");
        // TODO portrait
        return chatItem;
    }

    public static ChatItem json2ChatItem(String jsonStr) {
        ChatItem item = new ChatItem();
        try {
        	Log.d(TAG, jsonStr);
            JSONObject json = new JSONObject(jsonStr);
            item.fromUserId = json.getString("fromUserId");
            item.fromName = json.getString("fromName");
            item.toUserId = json.getString("toUserId");
            item.toUserName = json.getString("toUserName");
            item.message = json.getString("message");
            item.time = System.currentTimeMillis();
            item.setIsMeChat(false); 
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public String toString() {
        return "ChatItem{" +
                "fromUserId='" + fromUserId + '\'' +
                ", fromUserName='" + fromName + '\'' +
                ", toUserId='" + toUserId + '\'' +
                ", toUserName='" + toUserName + '\'' +
                ", time=" + time +
                ", message='" + message + '\'' +
                '}';
    }
}
