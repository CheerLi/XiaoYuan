/**
 * 2014-11-16
 * JiangZhenJie
 */
package com.myxiaoapp.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.myxiaoapp.android.R;
import com.myxiaoapp.android.SelectPicGroupActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

public class PicGroupAdapter extends BaseAdapter {

	private Context mContext;
	private HashMap<String, ArrayList<CharSequence>> mGroups;
	private List<String> mKeyList;

	public PicGroupAdapter(Context context,
			HashMap<String, ArrayList<CharSequence>> groups,
			List<String> keyList) {
		this.mContext = context;
		this.mGroups = groups;
		this.mKeyList = keyList;
	}

	@Override
	public int getCount() {
		if (mGroups != null) {
			return mGroups.keySet().size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		if (mGroups != null) {
			List<CharSequence> images = mGroups.get(mKeyList.get(position));
			return images;
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.pic_group_item, parent, false);
			holder = new ViewHolder();
			holder.imagesCount = (TextView) convertView
					.findViewById(R.id.group_count);
			holder.groupName = (TextView) convertView
					.findViewById(R.id.group_title);
			holder.groupImage = (ImageView) convertView
					.findViewById(R.id.group_image);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		@SuppressWarnings("unchecked")
		List<String> images = (List<String>) getItem(position);
		holder.imagesCount.setText(images.size() + "");
		holder.groupName.setText(mKeyList.get(position));
		ImageLoader.getInstance().displayImage("file:///" + images.get(0),
				holder.groupImage);
		holder.groupImage.setTag(images.get(0));
		return convertView;
	}

	private class ViewHolder {
		TextView imagesCount;
		TextView groupName;
		ImageView groupImage;
	}

}