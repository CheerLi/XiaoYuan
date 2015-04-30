package com.myxiaoapp.android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MessageActivity extends CommonActivity implements
		OnItemClickListener {

	private ListView mLvMessages;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);
		setActionBarTitle("消息");
		mLvMessages = (ListView) findViewById(R.id.lv_message);
		mLvMessages.setAdapter(new MessageAdapter(this));
		mLvMessages.setOnItemClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.message, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private class MessageAdapter extends BaseAdapter {

		private Context mContext;

		public MessageAdapter(Context c) {
			this.mContext = c;
		}

		@Override
		public int getCount() {
			return 10;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.lv_message_item, null);
				holder = new ViewHolder();
				holder.portrait = (ImageView) convertView
						.findViewById(R.id.portrait);
				holder.userName = (TextView) convertView
						.findViewById(R.id.username);
				holder.message = (TextView) convertView
						.findViewById(R.id.message);
				holder.news = (TextView) convertView
						.findViewById(R.id.news_source);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			return convertView;
		}

		class ViewHolder {
			ImageView portrait;
			TextView userName;
			TextView message;
			TextView news;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		startActivity(new Intent(this, CampusNewsDetailsActivity.class));
	}
}
