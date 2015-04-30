package com.myxiaoapp.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.myxiaoapp.android.R;
import com.myxiaoapp.model.NearPersonBean;
import com.myxiaoapp.model.User;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class CampusPeopleAdapter extends BaseAdapter {
	private final String TAG = "CampusPeopleAdapter";
	// test data
	// Object[][] data = { { "轻风抚面", "我要飞得更高", "1000m", R.drawable.male },
	// { "轻风抚面", "我要飞得更高", "1000m", R.drawable.female },
	// { "轻风抚面", "我要飞得更高", "1000m", R.drawable.male },
	// { "轻风抚面", "我要飞得更高", "1000m", R.drawable.female },
	// { "轻风抚面", "我要飞得更高", "1000m", R.drawable.male },
	// { "轻风抚面", "我要飞得更高", "1000m", R.drawable.female },
	// { "轻风抚面", "我要飞得更高", "1000m", R.drawable.male },
	// { "轻风抚面", "我要飞得更高", "1000m", R.drawable.female },
	// { "轻风抚面", "我要飞得更高", "1000m", R.drawable.male },
	// { "轻风抚面", "我要飞得更高", "1000m", R.drawable.female } };

	private ImageLoader imageLoader;
	private DisplayImageOptions options;
	private Context mContext;

	private List<NearPersonBean> users;

	public CampusPeopleAdapter(Context context) {
		mContext = context;
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
		options = new DisplayImageOptions.Builder().cacheInMemory(true)
				.cacheOnDisk(true).build();
	}

	public CampusPeopleAdapter(Context context, List<NearPersonBean> users) {
		mContext = context;
		this.users = users;
	}

	@Override
	public int getCount() {
		if (users == null) {
			return 0;
		}
		return users.size();
	}

	@Override
	public Object getItem(int position) {
		if (users == null) {
			return null;
		}
		return users.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		NearPersonBean user = (NearPersonBean) getItem(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.campus_people_list_item, null);
			viewHolder = new ViewHolder();
			viewHolder.portrait = (ImageView) convertView
					.findViewById(R.id.portrait);
			viewHolder.nickname = (TextView) convertView
					.findViewById(R.id.nickname);
			viewHolder.personalSign = (TextView) convertView
					.findViewById(R.id.personal_sign);
			viewHolder.distance = (TextView) convertView
					.findViewById(R.id.distance);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.nickname.setText(user.getName());
		int sexId = 0;
		if (user.getSex().equals(User.SEX_MALE)) {
			sexId = R.drawable.male;
		} else if (user.getSex().equals(User.SEX_FEMALE)) {
			sexId = R.drawable.female;
		}
		Drawable right = null;
		if (sexId != 0) {
			right = mContext.getResources().getDrawable(sexId);
		}
		viewHolder.nickname.setCompoundDrawablesWithIntrinsicBounds(null, null,
				right, null);
		viewHolder.personalSign.setText(user.getMoto());
		viewHolder.distance.setText(user.getDistance());
		imageLoader.displayImage(user.getS_portrait(), viewHolder.portrait,options);
		return convertView;
	}

	private class ViewHolder {
		ImageView portrait;
		TextView nickname;
		TextView personalSign;
		TextView distance;
	}

	public void setData(List<NearPersonBean> users) {
		this.users = users;
		notifyDataSetChanged();
	}
	public void clear(){
		users.clear();
	}
	public void addData(List<NearPersonBean> user) {
		Log.d(TAG,user.toString());
		this.users.addAll(user);
		notifyDataSetChanged();
	}

}
