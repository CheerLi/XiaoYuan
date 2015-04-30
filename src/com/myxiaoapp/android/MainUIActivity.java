package com.myxiaoapp.android;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.PopupMenu.OnMenuItemClickListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
  












import com.activeandroid.ActiveAndroid;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode; 
import com.google.gson.Gson;
import com.myxiaoapp.adapter.CampusPeopleAdapter;
import com.myxiaoapp.listener.OnResponseListener;
import com.myxiaoapp.model.HttpRequestParams;
import com.myxiaoapp.model.NearPersonList;
import com.myxiaoapp.network.AsyncHttpPost;
import com.myxiaoapp.network.XYClient;
import com.myxiaoapp.utils.LocationHelper;
import com.myxiaoapp.utils.Constant.RequestId;
import com.myxiaoapp.utils.Constant.RequestUrl;
import com.myxiaoapp.utils.LocationHelper.GetLocationListener;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushManager;

public class MainUIActivity extends CommonActivity implements
		OnPageChangeListener, OnClickListener, OnMenuItemClickListener,OnResponseListener, BDLocationListener {
	LocationClient loc;
	private Context mContext;
	private RadioGroup mTabGroup;
	private RadioButton mRbCampus;
	private RadioButton mRbDiscovery;
	private RadioButton mRbChat;
	private RadioButton mRbMe;
	private ViewPager mViewPager;
	private List<Fragment> mFragments;

	private int mCurrentTab = 1;
	private ImageView mCurSexImage;

	public static final String TAG_CAMPUS = "campus";
	public static final String TAG_DISCOVERY = "discovery";
	public static final String TAG_CHAT = "chat";
	public static final String TAG_ME = "me";
	private static final String TAG = "MainUIActivity";

	private int getUser = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_ui);
		mContext = this;
		init();
		final String uid = XiaoYuanApp.getLoginUser(this).userBean.getUid();
		Context context = getApplicationContext();
		XGPushManager.registerPush(context, uid, new XGIOperateCallback() {
			
			@Override
			public void onSuccess(Object arg0, int arg1) {
				//new AsyncHttpPost("Settoken", MainUIActivity.this, uid, arg0.toString()).post();
				new XYClient().post(
						RequestId.ID_SET_TOKEN, 
						RequestUrl.URL_SET_TOKEN, 
						HttpRequestParams.setToken(uid, arg0.toString()),
						MainUIActivity.this);
				Log.d(TAG, arg0.toString());
			}
			
			@Override
			public void onFail(Object arg0, int arg1, String arg2) {
			}
		});
		startBackstate();
		mApp.destoryOtherLaunchActivitys();

	}
	
	@Override
	protected void onResume() {
		super.onResume(); 
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy(); 
	}

	private void init() {
		CampusFragment campusFragment = new CampusFragment();
		DiscoveryFragment discoveryFragment = new DiscoveryFragment();
		ChatFragment chatFragment = new ChatFragment();
		MyHomePageFragment meFragment = new MyHomePageFragment();
		mFragments = new ArrayList<Fragment>();
		mFragments.add(campusFragment);
		mFragments.add(discoveryFragment);
		mFragments.add(chatFragment);
		mFragments.add(meFragment);

		mViewPager = (ViewPager) findViewById(R.id.vp_main_ui);
		mViewPager
				.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
		mViewPager.setOnPageChangeListener(this);
		mTabGroup = (RadioGroup) findViewById(R.id.tab_group);
		mRbCampus = (RadioButton) findViewById(R.id.rb_campus);
		mRbCampus.setOnClickListener(this);
		mRbDiscovery = (RadioButton) findViewById(R.id.rb_discovery);
		mRbDiscovery.setOnClickListener(this);
		mRbChat = (RadioButton) findViewById(R.id.rb_chat);
		mRbChat.setOnClickListener(this);
		mRbMe = (RadioButton) findViewById(R.id.rb_me);
		mRbMe.setOnClickListener(this);

		showMenuButton(this);
		// mActionBar.setDisplayHomeAsUpEnabled(false);
		// mActionBar.setDisplayOptions(mActionBar.getDisplayOptions()
		// | ActionBar.DISPLAY_SHOW_CUSTOM);

		setTab(0);
		
	}
	private void startBackstate(){
		loc = new LocationClient(this.getApplicationContext());
		loc.registerLocationListener(this);
		LocationClientOption locOpt = new LocationClientOption();
	//	locOpt.setLocationMode(LocationMode.Hight_Accuracy);
		locOpt.setScanSpan(100000);
	//	locOpt.setCoorType("gcj02");
	//	locOpt.setIsNeedAddress(false);
		loc.setLocOption(locOpt);
		loc.start();
	} 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// int menuRes = R.menu.campus_people;
		// switch (mCurrentTab) {
		// case 1:
		// menuRes = R.menu.campus_people;
		// break;
		// case 2:
		// menuRes = R.menu.discovery;
		// break;
		// case 3:
		// menuRes = R.menu.chat;
		// break;
		// case 4:
		// menuRes = R.menu.me;
		// break;
		// default:
		//
		// break;
		// }
		// getMenuInflater().inflate(menuRes, menu);
		return true;
	}

	private void setTab(int position) {
		switch (position) {
		case 0:
			// mActionBar.setTitle(R.string.campus_people);
			// mActionBar.setCustomView(mCurSexImage);
			mCurrentTab = 1;
			mRbCampus.setChecked(true);
			setActionBarTitle(R.string.campus_people);
			Drawable rightDrawable = getResources().getDrawable(
					R.drawable.icon_campus_title_right);
			getActionBarTitle().setCompoundDrawablePadding(10);
			getActionBarTitle().setCompoundDrawablesWithIntrinsicBounds(null,
					null, rightDrawable, null);
			getActionBarRightButton().setVisibility(View.VISIBLE);
			break;

		case 1:
			// mActionBar.setTitle(R.string.discovery);
			// mActionBar.setCustomView(null);
			mCurrentTab = 2;
			mRbDiscovery.setChecked(true);
			setActionBarTitle(R.string.discovery);
			getActionBarTitle().setCompoundDrawablesWithIntrinsicBounds(null,
					null, null, null);
			getActionBarRightButton().setVisibility(View.INVISIBLE);
			break;
		case 2:
			// mActionBar.setTitle(R.string.chat);
			// mActionBar.setCustomView(null);
			mCurrentTab = 3;
			mRbChat.setChecked(true);
			setActionBarTitle(R.string.chat);
			getActionBarTitle().setCompoundDrawablesWithIntrinsicBounds(null,
					null, null, null);
			getActionBarRightButton().setVisibility(View.INVISIBLE);
			break;
		case 3:
			// mActionBar.setTitle(R.string.me);
			// mActionBar.setCustomView(null);
			mCurrentTab = 4;
			mRbMe.setChecked(true);
			setActionBarTitle(R.string.me);
			getActionBarTitle().setCompoundDrawablesWithIntrinsicBounds(null,
					null, null, null);
			getActionBarRightButton().setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}
		supportInvalidateOptionsMenu();
	}

	private class ViewPagerAdapter extends FragmentPagerAdapter {

		public ViewPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return mFragments.get(position);
		}

		@Override
		public int getCount() {
			return mFragments.size();
		}

	}

	@Override
	public void onPageSelected(int arg0) {
		setTab(arg0);
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rb_campus:
			mRbCampus.setChecked(true);
			mViewPager.setCurrentItem(0, false);
			break;
		case R.id.rb_discovery:
			mRbDiscovery.setChecked(true);
			mViewPager.setCurrentItem(1, false);
			break;
		case R.id.rb_chat:
			mRbChat.setChecked(true);
			mViewPager.setCurrentItem(2, false);
			break;
		case R.id.rb_me:
			mRbMe.setChecked(true);
			mViewPager.setCurrentItem(3, false);
			break;
		case R.id.ibtn_right:
			if (mCurrentTab == 1) {
				showMenu(R.menu.campus_people, this);
			} else if (mCurrentTab == 4) {
				showMenu(R.menu.me, this);
			}
		}
	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		mCurSexImage = new ImageView(this);
		switch (item.getItemId()) {
		case R.id.action_display_male_only:
			mCurSexImage.setImageResource(R.drawable.male);
			// mActionBar.setCustomView(mCurSexImage, new LayoutParams(
			// ActionBar.LayoutParams.WRAP_CONTENT,
			// ActionBar.LayoutParams.MATCH_PARENT));
			break;
		case R.id.action_display_female_only:
			mCurSexImage.setImageResource(R.drawable.female);
			// mActionBar.setCustomView(mCurSexImage, new LayoutParams(
			// ActionBar.LayoutParams.WRAP_CONTENT,
			// ActionBar.LayoutParams.MATCH_PARENT));
			break;
		case R.id.action_display_all:
			// mActionBar.setCustomView(null);
			break;
		case R.id.action_function_settings:
			startActivity(new Intent(mContext, SettingActivity.class));
			break;

		case R.id.action_feedback:
			startActivity(new Intent(mContext, FeedbackActivity.class));
			break;
		case R.id.action_add_focus:
			startActivity(new Intent(mContext, AddNewFriendActivity.class));
			break;
		default:
			break;
		}
		return true;
	}

	/* 
	 * @see com.myxiaoapp.listener.OnResponseListener#onFailure(int)
	 */
	@Override
	public void onFailure(int statusCode) {
	}

	/* 
	 * @see com.myxiaoapp.listener.OnResponseListener#onReceiveSuccess(java.lang.String, java.lang.String)
	 */
	@Override
	public void onReceiveSuccess(String rec, final int ID) {
		switch(ID){
		case RequestId.ID_BACK_STATE:
			Log.d(TAG, "回传成功");
			break;
		case RequestId.ID_SET_TOKEN:
			Log.d(TAG, "设置Token成功");
			break;
		case RequestId.ID_NEARBY_USERS:
			Gson gson = new Gson();
			CampusPeopleAdapter adapter = ((CampusFragment) mFragments.get(0)).getAdapter();
			NearPersonList personlist = gson.fromJson(rec, NearPersonList.class);
			adapter.clear();
			adapter.addData(personlist.getData());
			break;
			default:break;
		}
	}

	/* 
	 * @see com.myxiaoapp.listener.OnResponseListener#onReceiveFailure(java.lang.String)
	 */
	@Override
	public void onReceiveFailure(String rec) {
	}

	/* 
	 * @see com.baidu.location.BDLocationListener#onReceiveLocation(com.baidu.location.BDLocation)
	 */
	@Override
	public void onReceiveLocation(BDLocation arg0) {
		XiaoYuanApp.setLoc(arg0);
		Log.i(TAG, "lat="+arg0.getLatitude()+"lng="+arg0.getLongitude());
		//new AsyncHttpPost("Backstate", MainUIActivity.this, XiaoYuanApp.getLoginUser(MainUIActivity.this).userBean.getUid(), arg0.getLatitude() + "", arg0.getLongitude()+ "").post();
		new XYClient().post(
				RequestId.ID_BACK_STATE, 
				RequestUrl.URL_BACK_STATE, 
				HttpRequestParams.updateLocationParams(XiaoYuanApp.getLoginUser(MainUIActivity.this).userBean.getUid(), arg0.getLatitude() + "", arg0.getLongitude()+ ""), 
				this);
		if(getUser == 0){
			getUser = 1;
			getNearUsers(arg0);
		}
	}
	private void getNearUsers(BDLocation loc){ 
		String lat = loc.getLatitude() + "";
		String lng = loc.getLongitude() + "";
		Log.d(TAG, "lat="+lat+",lng="+lng);
		//	new AsyncHttpPost("nearbyusers",this,lat,lng,"1").post();
		new XYClient().post(
				RequestId.ID_NEARBY_USERS, 
				RequestUrl.URL_NEARBY_USERS, 
				HttpRequestParams.nearbyUserParams(lat, lng, "1"), 
				this);
	}
}
