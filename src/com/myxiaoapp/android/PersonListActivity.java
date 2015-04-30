package com.myxiaoapp.android;

import java.lang.ref.WeakReference;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message; 
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.gson.Gson;
import com.myxiaoapp.adapter.PersonAdapter;
import com.myxiaoapp.listener.OnResponseListener;
import com.myxiaoapp.model.FansListBean;
import com.myxiaoapp.model.FocusFansBean;
import com.myxiaoapp.model.FocusListBean;
import com.myxiaoapp.model.HttpRequestParams;
import com.myxiaoapp.model.UserInfoBean;
import com.myxiaoapp.network.AsyncHttpPost;
import com.myxiaoapp.network.XYClient;
import com.myxiaoapp.utils.Constant.RequestId;
import com.myxiaoapp.utils.Constant.RequestUrl;
import com.myxiaoapp.utils.JSONHelper;
import com.myxiaoapp.utils.Utils;

/**
 * 关注的人列表和粉丝列表公用此Activity
 * 
 * @author JiangZhenJie
 * @date 2014-9-26
 */
public class PersonListActivity extends CommonActivity implements
		OnResponseListener,OnItemClickListener {

	private static final String TAG = "PersonListActivity";
	private static final int DEFAULT_PAGE_SIZE = 20;

	private ListView mPersonsList;

	private FansListBean fansBean;
	private FocusListBean focusBean;

	private String user_id;
	private String follow_id;
	private int flag;

	private static final int WHAT_REQUEST_PERSON = 0x123;

	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person_list);
		mPersonsList = (ListView) findViewById(R.id.lv_person);
		mPersonsList.setOnItemClickListener(this);
		PersonAdapter adapter = new PersonAdapter(this);
		mPersonsList.setAdapter(adapter);

		Intent intent = getIntent();
		user_id = intent.getStringExtra("user_id");
		follow_id = intent.getStringExtra("follow_id");
		flag = intent.getFlags();// 0表示显示粉丝， 1表示显示关注
		if( flag == 0){
			setActionBarTitle("粉丝");
		}else{
			setActionBarTitle("关注");
		}
		if (TextUtils.isEmpty(user_id) || TextUtils.isEmpty(follow_id)) {
			throw new IllegalArgumentException(
					"you should pass the user_id and follow_id to PersonListActivity");
		}
		Utils.showProgressDialog(this, true);
		request();
	}

	private void request() {
		if (flag == 0) {
//			AsyncHttpPost fansList = new AsyncHttpPost("Fanslist", this,
//					user_id, follow_id, DEFAULT_PAGE_SIZE + "");
//			fansList.post();
			new XYClient().post(
					RequestId.ID_FANS_LIST, 
					RequestUrl.URL_FANS_LIST, 
					HttpRequestParams.fansListParams(user_id, follow_id, DEFAULT_PAGE_SIZE + ""), 
					this);
		} else if (flag == 1) {
//			AsyncHttpPost focusList = new AsyncHttpPost("Followslist", this,
//					user_id, follow_id, DEFAULT_PAGE_SIZE + "");
//			focusList.post();
			new XYClient().post(
					RequestId.ID_FOLLOWS_LIST, 
					RequestUrl.URL_FOLLOWS_LIST, 
					HttpRequestParams.focusListParams(user_id, follow_id, DEFAULT_PAGE_SIZE + ""), 
					this);
		}
		
		//test interface
		
		//关注好友
	//	new AsyncHttpPost("follow", this, user_id, "10005").post();
		
		//取消关注
	//	new AsyncHttpPost("cancelfollow",this, user_id,"10005").post();
	}

	private void updateUI() {
		List<FocusFansBean> beans = null;
		if (flag == 0) {
			beans = fansBean.data;
		} else if (flag == 1) {
			beans = focusBean.data;
		}
		PersonAdapter adapter = (PersonAdapter) mPersonsList.getAdapter();
		adapter.setData(beans); 
	}

	/*
	 * @see com.myxiaoapp.listener.OnResponseListener#onFailure(int)
	 */
	@Override
	public void onFailure(int statusCode) {

		Utils.dismissProgressDialog();
	}

	/*
	 * @see
	 * com.myxiaoapp.listener.OnResponseListener#onReceiveSuccess(java.lang.
	 * String)
	 */
	@Override
	public void onReceiveSuccess(String rec, final int ID) {

		Utils.dismissProgressDialog();
		Log.d(TAG,rec);
		Gson gson = new Gson();
		switch(ID){
		case RequestId.ID_FANS_LIST:
		case RequestId.ID_FOLLOWS_LIST:
			if (flag == 0) { 
				fansBean = (FansListBean) gson.fromJson(rec, FansListBean.class);
			} else if (flag == 1) {
				focusBean = (FocusListBean) gson.fromJson(rec, FocusListBean.class);
			}
			updateUI();
			break;
		case RequestId.ID_GET_INFO:
			Log.d(TAG, "GetInfo");
			UserInfoBean userInfoBean = gson.fromJson(rec, UserInfoDataBean.class).getData();
			Intent intent = new Intent(this, HomePageActivity.class); 
			intent.putExtra("bean", userInfoBean);
			startActivity(intent); 
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

		Utils.dismissProgressDialog();
		Log.d(TAG, rec);
	}

	/* 
	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
		FocusFansBean bean = null;
		if( flag == 0){
			bean = fansBean.data.get(position);
		}else if(flag == 1){
			bean = focusBean.data.get(position);
		}
		String uid = bean.getUid();
//		new AsyncHttpPost("Getinfo", this,XiaoYuanApp.getLoginUser(this).userBean.getUid(), uid)
//		.post();
		new XYClient().post(
				RequestId.ID_GET_INFO,  
				RequestUrl.URL_GET_INFO, 
				HttpRequestParams.getUserInfoParams(XiaoYuanApp.getLoginUser(this).userBean.getUid(), uid), 
				this);
	}

}
