package com.myxiaoapp.android;

import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URLEncoder;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.myxiaoapp.adapter.PhotoAdapter;
import com.myxiaoapp.listener.OnResponseListener;
import com.myxiaoapp.model.HttpRequestParams;
import com.myxiaoapp.model.MomentBean;
import com.myxiaoapp.model.User;
import com.myxiaoapp.model.UserBean;
import com.myxiaoapp.model.UserInfoBean;
import com.myxiaoapp.network.AsyncHttpPost;
import com.myxiaoapp.network.XYClient;
import com.myxiaoapp.utils.Constant;
import com.myxiaoapp.utils.Constant.RequestId;
import com.myxiaoapp.utils.Constant.RequestUrl;
import com.myxiaoapp.utils.JSONHelper;

/**
 * 注意：修改布局文件时请保留id不变 —— mark by jzj
 */
public class MyHomePageFragment extends Fragment implements OnClickListener, OnResponseListener, OnItemClickListener {

	private static final String TAG = "MyHomePageFragment";

	private ImageView mPortrait; // 头像
	private RelativeLayout nameLayout;
	private TextView mName; // 昵称或备注
	private RelativeLayout schoolLayout;
	private TextView mSchool; // 学校
	private LinearLayout signLayout;
	private TextView mPersonalSign; // 个性签名
	private LinearLayout mFocus; // 他的关注
	private TextView fol_counts;
	private LinearLayout mFans; // 他的粉丝
	private TextView fan_counts;
//	private GridView mPhotoAlbum; // 个人相册
	private TextView mTextMood;
	private GridView mPhotoMood;// 校园心情附带的照片
	private PhotoAdapter photoAdapter;

	private Button mGoFocus; // 关注
	private Button mGoChat; // 聊天
	private LinearLayout personCampus;// 个人校园圈
	private UserInfoBean userInfoBean;
	private UserBean userBean;
	
	private static final int MODIFY_NICKNAME = 1;
	private static final int MODIFY_SCHOOL = 2;
	private static final int MODIFY_PERSONAL_SIGN = 3;

	private static final int WHAT_GET_USER_INFO = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_home_page, container,
				false);
		findView(view);
		getDetail();
		return view;
	}

	private void findView(View view) {
		mPortrait = (ImageView) view.findViewById(R.id.portrait);
		nameLayout = (RelativeLayout)view.findViewById(R.id.nameLayout);
		nameLayout.setOnClickListener(this);
		mName = (TextView) view.findViewById(R.id.name);
		mName.setOnClickListener(this);
		
		schoolLayout = (RelativeLayout)view.findViewById(R.id.schoolLayout);
		schoolLayout.setOnClickListener(this);
		mSchool = (TextView) view.findViewById(R.id.school);
		
		signLayout = (LinearLayout)view.findViewById(R.id.signLayout);
		signLayout.setOnClickListener(this);
		mPersonalSign = (TextView) view.findViewById(R.id.person_sign);
		
		mFocus = (LinearLayout) view.findViewById(R.id.tv_focus_number);
		mFocus.setOnClickListener(this);
		fol_counts = (TextView)view.findViewById(R.id.foc_counts);
		
		mFans = (LinearLayout) view.findViewById(R.id.tv_fans_number);
		mFans.setOnClickListener(this);
		fan_counts = (TextView)view.findViewById(R.id.fan_counts);
	//	mPhotoAlbum = (GridView) view.findViewById(R.id.gv_photo_album);
	//	mPhotoAlbum.setAdapter(new PhotoAdapter(getActivity(), Constant.FLAG_ME));

		mTextMood = (TextView) view.findViewById(R.id.campus_mood);
	//	mTextMood.setOnClickListener(this);
		mPhotoMood = (GridView) view.findViewById(R.id.gv_photo_mood);
		photoAdapter = new PhotoAdapter(getActivity(),Constant.FLAG_ME);
		mPhotoMood.setAdapter(photoAdapter);
	//	mPhotoMood.setOnItemClickListener(this);
		mGoFocus = (Button) view.findViewById(R.id.go_focus);
		mGoFocus.setVisibility(View.GONE);
		mGoChat = (Button) view.findViewById(R.id.go_chat);
		mGoChat.setVisibility(View.GONE);
		personCampus = (LinearLayout) view.findViewById(R.id.go_person_campus);
		personCampus.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		User loginUser = XiaoYuanApp.getLoginUser(getActivity());
		Intent intent;
		switch (v.getId()) {
		case R.id.nameLayout:
			Intent iName = new Intent(getActivity(), ModifyPersonalSignActivity.class);
			iName.setFlags(MODIFY_NICKNAME);
			startActivityForResult(iName, MODIFY_NICKNAME);
			break;
		case R.id.schoolLayout:
			Intent iSchool = new Intent(getActivity(), ModifyPersonalSignActivity.class);
			iSchool.setFlags(MODIFY_SCHOOL);
			startActivityForResult(iSchool, MODIFY_SCHOOL);
			break;
		case R.id.signLayout:
			Intent iSign = new Intent(getActivity(), ModifyPersonalSignActivity.class);
			iSign.setFlags(MODIFY_PERSONAL_SIGN);
			startActivityForResult(iSign, MODIFY_PERSONAL_SIGN);
			break;
		case R.id.tv_fans_number:
			intent = new Intent(getActivity(), PersonListActivity.class);
			intent.putExtra("user_id", loginUser.userBean.getUid());
			intent.putExtra("follow_id", loginUser.userBean.getUid());
			intent.setFlags(0);
			startActivity(intent);
			break;
		case R.id.tv_focus_number:
			intent = new Intent(getActivity(), PersonListActivity.class);
			intent.putExtra("user_id", loginUser.userBean.getUid());
			intent.putExtra("follow_id", loginUser.userBean.getUid());
			intent.setFlags(1);
			startActivity(intent);
			break;
		case R.id.go_person_campus:
			Intent i = new Intent(getActivity(), CampusNewsActivity.class);
			i.setFlags(Constant.FLAG_ME);
			startActivity( i );
			break;
		default:
			break;
		}
	}

	
	public String getUid(){
		return userBean.getUid();
	}
	private void getDetail() {
		User user = XiaoYuanApp.getLoginUser(getActivity()); 
		Log.d(TAG, user.userBean.getUid());
		//new AsyncHttpPost("Getinfo", this,user.userBean.getUid(), user.userBean.getUid()).post();
		new XYClient().post(
				RequestId.ID_GET_INFO, 
				RequestUrl.URL_GET_INFO, 
				HttpRequestParams.getUserInfoParams(user.userBean.getUid(), user.userBean.getUid()), 
				this);
	}

	private void updateUI() {
		if (userBean == null)
			return;
		fol_counts.setText(userBean.getFol_counts());
		fan_counts.setText(userBean.getFan_counts());
		mName.setText(userBean.getName());
		//mSchool.setText(userBean.getCollege());
		String moto = userBean.getMoto();
		if (TextUtils.isEmpty(moto) || moto.equals("null")) {
			moto = "签名还在酝酿中...";
		}
		
		mPersonalSign.setText(moto);
		List<MomentBean> lastMoments= userInfoBean.getLast_moments();
		if(lastMoments.size() > 0){
			photoAdapter.setLastPics(lastMoments.get(0).getM_spictures(), lastMoments.get(0).getM_pictures());
		}
	}

	/*
	 * @see com.myxiaoapp.listener.OnResponseListener#onFailure(int)
	 */
	@Override
	public void onFailure(int statusCode) {
		Log.d(TAG, statusCode+"");
	}

	/*
	 * @see
	 * com.myxiaoapp.listener.OnResponseListener#onReceiveSuccess(java.lang.
	 * String)
	 */
	@Override
	public void onReceiveSuccess(String rec, final int ID) {
		Log.d(TAG, "rec="+rec);
		switch(ID){
		case RequestId.ID_GET_INFO:
			Gson gson = new Gson();
			userInfoBean = gson.fromJson(rec, UserInfoDataBean.class).getData();
			Log.d(TAG, userInfoBean.toString());
			userBean = userInfoBean.getUser();
			userBean.setName(userBean.getName());
			userBean.setCollege(userBean.getCollege());
			updateUI();
			break;
		default:
			break;
		}
	}

	/*
	 * @see
	 * com.myxiaoapp.listener.OnResponseListener#onReceiveFailure(java.lang.
	 * String)
	 */
	@Override
	public void onReceiveFailure(String rec) {
		Log.d(TAG, rec);
	}
	/* 
	 * @see android.support.v4.app.Fragment#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {	
		super.onActivityResult(requestCode, resultCode, data);
		if(data == null) return ;
		Log.d(TAG,"resultCode ="+resultCode);
		String modify = data.getStringExtra("modify");
		String nickName = null;
		String school = null;
		String moto = null;
		if(resultCode == MODIFY_NICKNAME){
			if(modify == mName.getText().toString() || modify.equals("")) return;
			mName.setText(modify);
			nickName = modify;
		}else if(resultCode == MODIFY_SCHOOL){
			if(modify == mSchool.getText().toString() || modify.equals("")) return;
			mSchool.setText(modify);
			school = modify;
		}else if(resultCode == MODIFY_PERSONAL_SIGN){
			if(modify == mPersonalSign.getText().toString() || modify.equals("")) return;
			mPersonalSign.setText(modify);
			moto = modify;
		} 
		 
//			new AsyncHttpPost("updateinfo", this,XiaoYuanApp.getLoginUser(getActivity()).userBean.getUid(), 
//					null,
//					null,
//					null,
//					nickName,
//					moto,
//					null)
//			.post(); 
			new XYClient().post(
					RequestId.ID_UPDATE_INFO, 
					RequestUrl.URL_UPDATE_INFO,
					HttpRequestParams.updateinfo(XiaoYuanApp.getLoginUser(getActivity()).userBean.getUid(), 
					null,
					null,
					null,
					nickName,
					moto,
					null), 
					this);
		Log.d(TAG, "向后台修改信息");
	}

	/* 
	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent i = new Intent(getActivity(), CampusNewsActivity.class);
		i.setFlags(Constant.FLAG_ME);
		startActivity( i );
	}
}
