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
import com.myxiaoapp.model.FocusFansBean;
import com.myxiaoapp.model.User;
import com.nostra13.universalimageloader.core.ImageLoader;

public class PersonAdapter extends BaseAdapter {

	private Context mContext;

	private List<FocusFansBean> users;
	private ImageLoader imageLoader;
	private final String TAG = "PersonAdapter";
	public PersonAdapter(Context context) {
		mContext = context;
		imageLoader = ImageLoader.getInstance();
	}

	public PersonAdapter(Context context, List<FocusFansBean> users) {
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
		FocusFansBean user = (FocusFansBean) getItem(position);
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
		viewHolder.personalSign.setText(user.moto);
		viewHolder.distance.setVisibility(View.GONE);
		imageLoader.displayImage(user.s_portrait, viewHolder.portrait);

		return convertView;
	}

	private class ViewHolder {
		ImageView portrait;
		TextView nickname;
		TextView personalSign;
		TextView distance;
	}

	public void setData(List<FocusFansBean> users) {
		this.users = users;
	//	Log.d(TAG,"user number="+users.size());
		notifyDataSetChanged();
	}

	public void addData(FocusFansBean user) {
		this.users.add(user);
		notifyDataSetChanged();
	}

}
