package com.myxiaoapp.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myxiaoapp.android.R;
import com.myxiaoapp.android.XiaoYuanApp; 
import com.myxiaoapp.model.ChatItem;
import com.myxiaoapp.model.User;
import com.myxiaoapp.utils.Utils;
import com.myxiaoapp.view.CircleImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class ChatMsgAdapter extends BaseAdapter {

	private static final String TAG = "ChatMsgAdapter";
	private Context mContext;
    private List<ChatItem> mChatItems;
	private ImageLoader imageLoader;
	private DisplayImageOptions options; 
	private Bitmap myPortrait;
	private Bitmap otherPortrait;
    public ChatMsgAdapter(Context context) {
        mContext = context;
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
		options = new DisplayImageOptions.Builder().cacheInMemory(true)
				.cacheOnDisk(true).build();
    }

    public void setChatItem(List<ChatItem> chatItems) {
        mChatItems = chatItems;
        notifyDataSetChanged();
    }

    public void addChatItem(ChatItem chatItem) {
        if (mChatItems == null) {
            mChatItems = new ArrayList<>();
        }
        mChatItems.add(chatItem);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mChatItems == null ? 0 : mChatItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mChatItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.chat_msg_list_item, parent, false);
            holder = new ViewHolder();
            holder.myChatLayout = (RelativeLayout) convertView.findViewById(R.id.ll_my_chat);
            holder.myPortraitView = (ImageView) convertView.findViewById(R.id.iv_my_portrait);
            holder.myMessageView = (TextView) convertView.findViewById(R.id.tv_my_chat_msg);
            holder.hisChatLayout = (RelativeLayout) convertView.findViewById(R.id.ll_his_chat);
            holder.hisMessageView = (TextView) convertView.findViewById(R.id.tv_his_chat_msg);
            holder.hisPortraitView = (ImageView) convertView.findViewById(R.id.iv_his_portrait);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ChatItem item = mChatItems.get(position);
        
        if (item.getIsMeChat()) { 
            holder.myChatLayout.setVisibility(View.VISIBLE);
            holder.hisChatLayout.setVisibility(View.INVISIBLE);
            holder.myMessageView.setText(item.getMessage());
            holder.myPortraitView.setImageBitmap(myPortrait);
        } else {
 
            holder.hisChatLayout.setVisibility(View.VISIBLE);
            holder.myChatLayout.setVisibility(View.INVISIBLE);
            holder.hisMessageView.setText(item.getMessage());
            holder.hisPortraitView.setImageBitmap(otherPortrait);
        }
        Log.d(TAG, item.getMessage());
        return convertView;
    }

    private class ViewHolder {
        RelativeLayout myChatLayout;
        RelativeLayout hisChatLayout;
        ImageView myPortraitView;
        ImageView hisPortraitView;
        TextView myMessageView;
        TextView hisMessageView;
    }

	/**
	 * @param myPortraitMap
	 * @param otherPortraitMap
	 */
	public void setPortrait(Bitmap myPortraitMap, Bitmap otherPortraitMap) {
		myPortrait = myPortraitMap;
		otherPortrait = otherPortraitMap;
	}
}
