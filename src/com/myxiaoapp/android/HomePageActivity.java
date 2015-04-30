package com.myxiaoapp.android;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu.OnMenuItemClickListener;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.myxiaoapp.adapter.PhotoAdapter;
import com.myxiaoapp.utils.Constant; 
import com.myxiaoapp.utils.Constant.RequestId;
import com.myxiaoapp.utils.Constant.RequestUrl;
import com.myxiaoapp.listener.OnResponseListener;
import com.myxiaoapp.model.FocusFansBean;
import com.myxiaoapp.model.HttpRequestParams;
import com.myxiaoapp.model.MomentBean;
import com.myxiaoapp.model.User;
import com.myxiaoapp.model.UserBean;
import com.myxiaoapp.model.UserInfoBean;
import com.myxiaoapp.network.AsyncHttpPost;
import com.myxiaoapp.network.XYClient;
import com.myxiaoapp.view.CircleImageView;

/**
 * 个人主页
 * 
 * @author JiangZhenJie
 * @date 2014-10-20
 */
public class HomePageActivity extends CommonActivity implements
		OnClickListener, OnMenuItemClickListener, OnResponseListener, OnItemClickListener {

	private final String TAG = "HomePageActivity";
//	private User userInfo;
	private UserInfoBean bean;
	private UserBean user;
	private CircleImageView mPortrait; // 头像
	private TextView mName; // 昵称或备注
	private TextView mSchool; // 学校
	private TextView mPersonalSign; // 个性签名
	private LinearLayout mFocus; // 他的关注
	private TextView fol_counts;
	private LinearLayout mFans; // 他的粉丝
	private TextView fan_counts;
	// private TextView mAlbum;//相册标签提示
	private GridView mPhotoAlbum; // 相册
	
	private LinearLayout personSign;
	private TextView mTextMood;
	private GridView mPhotoMood;// 心情附带的照片
	private PhotoAdapter photoAdapter;
	private Button mGoFocus; // 关注
	private Button mGoChat; // 聊天
	private Button mExit;// 退出登录，隐藏
	

	// private int leftMargin;
	// private LinearLayout mOperateLayout;

	// private LinearLayout mOperateLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_page);
		setActionBarTitle(R.string.title_activity_details);
		bean = (UserInfoBean) getIntent().getSerializableExtra("bean");
		user = bean.getUser();
		initView();
		CampusFragment.selectedUser = null;
		fan_counts.setText(user.getFan_counts());
		fol_counts.setText(user.getFol_counts());
		mName.setText(user.getName());
	/*	if(userBean.getSex().equals("2"))
			mName.setBackgroundResource(R.drawable.female);
		else 
			mName.setBackgroundResource(R.drawable.male);
	*///	mSchool.setText(user.getCollege());
		mPersonalSign.setText(user.getMoto());
		List<MomentBean> lastMoments= bean.getLast_moments();
		if( lastMoments.size() > 0){
			photoAdapter.setLastPics(lastMoments.get(0).getM_spictures(), lastMoments.get(0).getM_pictures());
			
		}
	}

	private void initView() {
		mPortrait = (CircleImageView) findViewById(R.id.portrait);
		mName = (TextView) findViewById(R.id.name);
		mSchool = (TextView) findViewById(R.id.school);
		mPersonalSign = (TextView) findViewById(R.id.person_sign);
		mFocus = (LinearLayout) findViewById(R.id.tv_focus_number);
		mFocus.setOnClickListener(this);
		fol_counts = (TextView)findViewById(R.id.foc_counts);
		mFans = (LinearLayout) findViewById(R.id.tv_fans_number);
		mFans.setOnClickListener(this);
		fan_counts = (TextView)findViewById(R.id.fan_counts);
		// mAlbum = (TextView)findViewById(R.id.myAlbum);
		mPhotoAlbum = (GridView) findViewById(R.id.gv_photo_album);
		mPhotoAlbum.setAdapter(new PhotoAdapter(this, Constant.FLAG_ME,
				Constant.FLAG_CAMPUS));
		personSign = (LinearLayout)findViewById(R.id.go_person_campus);
		personSign.setOnClickListener(this);
		photoAdapter = new PhotoAdapter(this,Constant.FLAG_ME);
		mTextMood = (TextView) findViewById(R.id.campus_mood);
	//	mTextMood.setOnClickListener(this);
		mPhotoMood = (GridView) findViewById(R.id.gv_photo_mood);
		mPhotoMood.setAdapter(photoAdapter);
	//	mPhotoMood.setOnItemClickListener(this);
		mGoFocus = (Button) findViewById(R.id.go_focus);
		mGoFocus.setOnClickListener(this);
		mGoChat = (Button) findViewById(R.id.go_chat);
		mGoChat.setOnClickListener(this);
		if(user.getUid().equals(XiaoYuanApp.getLoginUser(this).userBean.getUid())){
			mGoFocus.setVisibility(View.GONE);
			mGoChat.setVisibility(View.GONE);
		}
		// mExit = (Button)findViewById(R.id.exit);
		// mExit.setVisibility(View.GONE);

		/*
		 * mPhotoAlbum = (GridView) findViewById(R.id.gv_photo_album);
		 * mPhotoAlbum.setAdapter(new PhotoAdapter(this, 0)); mGoFocus =
		 * (Button) findViewById(R.id.go_focus);
		 * mGoFocus.setOnClickListener(this); mGoChat = (Button)
		 * findViewById(R.id.go_chat); mGoChat.setOnClickListener(this);
		 */// mOperateLayout = (LinearLayout) findViewById(R.id.ll_operate);
			// mOperateLayout.setVisibility(View.VISIBLE);

		showMenuButton(R.menu.details, this);
		showBackButton();

	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.personal_sign:
			break;
		case R.id.tv_focus_number: 
			intent = new Intent(this, PersonListActivity.class);
			intent.putExtra("user_id", XiaoYuanApp.getLoginUser(this).userBean.getUid());
			intent.putExtra("follow_id", user.getUid());
			intent.setFlags(1);
			startActivity(intent);
			break;
		case R.id.tv_fans_number:
			intent = new Intent(this, PersonListActivity.class);
			intent.putExtra("user_id", XiaoYuanApp.getLoginUser(this).userBean.getUid());
			intent.putExtra("follow_id", user.getUid());
			intent.setFlags(0);
			startActivity(intent);
			break;
		case R.id.go_chat:
			intent = new Intent(this, ChatPanelActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("fromUserId", user.getUid());
            intent.putExtra("fromName", user.getName());
            intent.putExtra("fromPortrait", user.getS_portrait());
			startActivity(intent);
			break;
		case R.id.go_focus:
			//new AsyncHttpPost("follow",this, XiaoYuanApp.getLoginUser(this).userBean.getUid(),user.getUid()).post();
			new XYClient().post(
					RequestId.ID_FOLLOW, 
					RequestUrl.URL_FOLLOW,
					HttpRequestParams.focusFriendsParams(XiaoYuanApp.getLoginUser(this).userBean.getUid(),user.getUid()), 
					this);
			break; 
		case R.id.go_person_campus:
			Intent i = new Intent(this, CampusNewsActivity.class);
			i.setFlags(Integer.valueOf( user.getUid() ));
			startActivity( i );
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.action_note:
			startActivity(new Intent(this, RemarkActivity.class));
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
		mGoFocus.setText("已关注");
	}

	/* 
	 * @see com.myxiaoapp.listener.OnResponseListener#onReceiveFailure(java.lang.String)
	 */
	@Override
	public void onReceiveFailure(String rec) {
	}

	/* 
	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Log.d(TAG, "itemclick");
		Intent i = new Intent(this, CampusNewsActivity.class);
		i.setFlags(Integer.valueOf( user.getUid() ));
		startActivity( i );
	}
}
