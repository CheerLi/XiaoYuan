package com.myxiaoapp.android;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 功能设置
 * 
 * @author JiangZhenJie
 * @date 2014-9-25
 */
public class SettingActivity extends CommonActivity implements OnClickListener,
		OnCheckedChangeListener {

	private CheckBox mNewMessageSetting;
	private CheckBox mChatMessageSetting;
	private CheckBox mNewFocusSetting;
	private CheckBox mVoiceSetting;
	private CheckBox mShakeSetting;
	private CheckBox mNewsUpdateSetting;
	private CheckBox mPhoneSetting;
	private CheckBox mLocationSetting;

	private TextView mExit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		setActionBarTitle(R.string.function_settings);
		initView();
	}

	private void initView() {

		showBackButton();

		int[] layoutIds = { R.id.setting_new_message,
				R.id.setting_chat_message, R.id.setting_new_focus,
				R.id.setting_voice, R.id.setting_shake,
				R.id.setting_news_update, R.id.setting_phone,
				R.id.setting_location };

		int[][] textIds = {
				{ R.string.setting_new_message_remain,
						R.string.setting_new_message_remain_tips },
				{ R.string.setting_chat_message,
						R.string.setting_chat_message_tips },
				{ R.string.setting_new_focus, R.string.setting_new_focus_tips },
				{ R.string.setting_voice, R.string.setting_voice_tips },
				{ R.string.setting_shake, R.string.setting_shake_tips },
				{ R.string.setting_campus_circle_update,
						R.string.setting_close_no_tips },
				{ R.string.setting_can_find_me_by_phone,
						R.string.setting_phone_tips },
				{ R.string.setting_location_available,
						R.string.setting_location_tips } };

		CheckBox[] checkBoxs = { mNewMessageSetting, mChatMessageSetting,
				mNewFocusSetting, mVoiceSetting, mShakeSetting,
				mNewsUpdateSetting, mPhoneSetting, mLocationSetting };

		for (int i = 0; i < layoutIds.length; i++) {
			RelativeLayout r = (RelativeLayout) findViewById(layoutIds[i]);
			((TextView) r.findViewById(R.id.setting_title))
					.setText(textIds[i][0]);
			((TextView) r.findViewById(R.id.setting_tips))
					.setText(textIds[i][1]);
			checkBoxs[i] = (CheckBox) r.findViewById(R.id.checkbox);
		}

		mExit = (TextView) findViewById(R.id.tv_exit);
		mExit.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_exit:
			// showExitDialog();
			break;
		// case R.id.setting_check_update:
		// Utils.showProgressDialog(this, true);
		// break;
		// case R.id.setting_about:
		// startActivity(new Intent(this, AboutUsActivity.class));
		// break;
		case R.id.ibtn_left:
			finish();
			break;
		default:
			break;
		}
	}

	private void showExitDialog() {
		Builder builder = new Builder(this);
		View view = LayoutInflater.from(this).inflate(
				R.layout.setting_exit_dialog, null);
		builder.setView(view);
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// switch (buttonView.getId()) {
		// case R.id.setting_new_message_remain:
		// // if (isChecked) {
		// // mNewFocusLayout.setVisibility(View.VISIBLE);
		// // mChatSettingLayout.setVisibility(View.VISIBLE);
		// // } else {
		// // mNewFocusLayout.setVisibility(View.GONE);
		// // mChatSettingLayout.setVisibility(View.GONE);
		// // }
		// break;
		//
		// default:
		// break;
		// }
	}

	private class SettingAdapter extends BaseAdapter {

		private Context mContext;

		// data
		int[][] data = {};

		public SettingAdapter(Context c) {
			this.mContext = c;
		}

		@Override
		public int getCount() {
			return data.length;
		}

		@Override
		public Object getItem(int position) {
			return data[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.setting_common_layout, null);
				viewHolder = new ViewHolder();
				viewHolder.checkbox = (CheckBox) convertView
						.findViewById(R.id.checkbox);
				viewHolder.title = (TextView) convertView
						.findViewById(R.id.setting_title);
				viewHolder.tips = (TextView) convertView
						.findViewById(R.id.setting_tips);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.title.setText(data[position][0]);
			viewHolder.tips.setText(data[position][1]);
			return convertView;
		}

		private class ViewHolder {
			CheckBox checkbox;
			TextView title;
			TextView tips;
		}

	}

}
