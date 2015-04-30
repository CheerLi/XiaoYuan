package com.myxiaoapp.android;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.myxiaoapp.adapter.ChatListAdapter; 
import com.myxiaoapp.model.ChatItem;
import com.myxiaoapp.model.RecentChatItem;
import com.myxiaoapp.model.User;
import com.myxiaoapp.utils.DataBaseHelper;
import com.myxiaoapp.utils.SQLiteHelper;

public class ChatFragment extends Fragment implements OnItemClickListener {

	private static final String TAG = "mydebug";

	private ListView mChatList;
	private List<ChatItem> mRecentChats;
 

	private static final int WHAT_UPDATE_RECENT_CHAT = 1; 

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_chat, container, false);
		init(view);
	//	updateRecentChat();
		return view;
	} 
	
	@Override
	public void onResume() {
		super.onResume(); 
	//	updateRecentChat();
	}

	@Override
	public void onPause() { 
		super.onPause();
	}

	private void init(View view) {
		mChatList = (ListView) view.findViewById(R.id.chat_list);
		mChatList.setAdapter(new ChatListAdapter(getActivity()));
		mChatList.setOnItemClickListener(this); 
	}

	/**
	 * 收到消息，更新未读数和最近记录
	 * 
	 * @author JiangZhenJie
	 * @date 2014-11-7
	 */
	private class MessageReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
	//		updateRecentChat();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		ChatItem recentChatItem = mRecentChats.get(position);
		Intent intent = new Intent(getActivity(), ChatPanelActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("fromUserId", recentChatItem.getFromUserId());
        intent.putExtra("fromName", recentChatItem.getFromName());
        intent.putExtra("fromPortrait", recentChatItem.getFromPortrait());
		startActivity(intent);
	}

	/**
	 * 更新最近聊天列表
	 */
/*	private void updateRecentChat() {
		mRecentChats = DataBaseHelper.readRecentChatList();
		((ChatListAdapter)mChatList.getAdapter()).setChatList(mRecentChats);
	}
*/ 
}
