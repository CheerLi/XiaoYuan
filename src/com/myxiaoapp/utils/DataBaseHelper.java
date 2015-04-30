/**
 * 2015年4月11日
 * ken
 */
package com.myxiaoapp.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
 




import android.util.Log;

import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
import com.myxiaoapp.model.Chat;
import com.myxiaoapp.model.ChatItem;
import com.myxiaoapp.model.RecentChatItem;

/**
 * @author ken
 *
 */
public class DataBaseHelper {
	private final static String TAG = "DataBaseHelper";
    public static void saveChat(final JSONObject json) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Chat chat = new Chat();
                    chat.fromUserId = json.getString("fromUserId");
                    chat.fromName = json.getString("fromName");
                    chat.toUserId = json.getString("toUserId");
                    chat.toName = json.getString("toUserName");
                    chat.message = json.getString("message");
                    chat.time = System.currentTimeMillis() + "";
                    chat.save();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void saveChat(ChatItem item) {
        Chat chat = new Chat();
        chat.message = item.getMessage();
        chat.fromUserId = item.getFromUserId();
        chat.fromName = item.getFromUserName();
        chat.fromPortrait = item.getFromPortrait();
        chat.toUserId = item.getToUserId();
        chat.toName = item.getToUserName();
        chat.toPortrait = item.getmPortrait();
        chat.time = System.currentTimeMillis() + "";
        chat.save();
        Log.d(TAG, "保存成功="+chat.message+","+chat.fromUserId+","+chat.fromName+","+chat.fromPortrait+","+chat.toUserId+","+chat.toName+","+chat.toPortrait+","+chat.time);
   /*     updateRecentChat(System.currentTimeMillis(), 
       		Integer.valueOf(item.getFromUserId()), 
        		Integer.valueOf(item.getToUserId()), 
        		item.getToUserName(), 
       		item.getmPortrait(), 
        		item.getMessage());
   */
    }
    private static void updateRecentChat(
    		long timestamp, 
    		int userId, 
    		int chatUserId, 
    		String chatUserName, 
    		String chatUserPortrait, 
    		String recentMessage){
    	if(new Select().from(RecentChatItem.class).where("userId = ? and chatUserId = ? ",
    			userId,chatUserId).execute().size()  > 0){
	    	new Update(RecentChatItem.class)
	    		.set("timestamp = ? and chatUserName = ? and chatUserPortrait = ? and recentMessage = ? ",
	    				timestamp,chatUserName,chatUserPortrait,recentMessage)
	    		.where("userId = ? and chatUserId = ? ", userId,chatUserId)
	    		.execute();
    	}else {

        	RecentChatItem item = new RecentChatItem();
        	
        	item.timestamp = timestamp;
        	item.userId = userId;
        	item.chatUserId = chatUserId;
        	item.chatUserName = chatUserName;
        	item.chatUserPortrait = chatUserPortrait;
        	item.recentMessage = recentMessage;
    		item.save();
    	}
    }
    public static List<ChatItem> readRecentChatList(){
    	int loginId = DataManager.getInstance().getLoginUserId();
/*
    	List<RecentChatItem> list = new Select()
    								.from(RecentChatItem.class)
    								.execute();
 */		
    	List<Chat> recentChatItems = new Select()
 										.from(Chat.class)
 										.where("( not exists (select 1 from test where ((fromUserId=test.fromUserId and toUserId=test.toUserId) or(fromUserId=test.toUserId and toUserId=test.fromUserId)) and id>test.id) and (fromUserId = ? or toUserID = ? ) )",loginId,loginId)
 										.execute();
    	Log.d(TAG, "size="+recentChatItems.size());
    	List<ChatItem> chatItems = new ArrayList<>();
    	for (Chat chat : recentChatItems) {
            chatItems.add(ChatItem.chat2ChatItem(chat));
        }
    	return chatItems;
    }
    public static List<ChatItem> readChat(String otherUserId) {
        List<ChatItem> chatItems = new ArrayList<>();
        int loginId = DataManager.getInstance().getLoginUserId();
        int otherId = Integer.valueOf(otherUserId);
        Log.d(TAG, "loginId="+loginId+",otherId="+otherId);
        List<Chat> chats = new Select().
                from(Chat.class).
                where("(fromUserId = ? and toUserId = ? ) or (fromUserId = ? and toUserId = ? )", 
                		loginId, otherId, otherId, loginId)
                .execute();
      
        for (Chat chat : chats) {
            chatItems.add(ChatItem.chat2ChatItem(chat));
        }
        Collections.sort(chatItems, new Comparator<ChatItem>() {
            @Override
            public int compare(ChatItem lhs, ChatItem rhs) {
                return (int) (lhs.getTime() - rhs.getTime());
            }
        });

        return chatItems;
    }
}	
