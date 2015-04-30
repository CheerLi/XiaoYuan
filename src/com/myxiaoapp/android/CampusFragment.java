package com.myxiaoapp.android;
/**
 * 校内的人
 * author lqh
 */
import java.util.LinkedList;
import java.util.List;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.google.gson.Gson;
import com.myxiaoapp.adapter.CampusPeopleAdapter;
import com.myxiaoapp.listener.OnResponseListener;
import com.myxiaoapp.model.HttpRequestParams;
import com.myxiaoapp.model.Location;
import com.myxiaoapp.model.NearPersonBean;
import com.myxiaoapp.model.NearPersonList;
import com.myxiaoapp.model.User;
import com.myxiaoapp.model.UserInfoBean;
import com.myxiaoapp.network.AsyncHttpPost;
import com.myxiaoapp.network.XYClient;
import com.myxiaoapp.utils.Constant.RequestId;
import com.myxiaoapp.utils.Constant.RequestUrl;
import com.myxiaoapp.utils.LocationHelper;
import com.myxiaoapp.utils.LocationHelper.GetLocationListener;
import com.myxiaoapp.utils.Utils;
import com.pulltorefresh.library.PullToRefreshBase;
import com.pulltorefresh.library.PullToRefreshBase.Mode;
import com.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.pulltorefresh.library.PullToRefreshListView;

public class CampusFragment extends Fragment implements OnClickListener,
		OnItemClickListener, OnRefreshListener<ListView>, OnResponseListener {

	private static final String TAG = "CampusFragment";
	private ListView mPeopleList;
	CampusPeopleAdapter peopleAdapter;
	private PullToRefreshListView mPullToRefreshListView;
	private List<NearPersonBean> mCampusUsers;
	public static NearPersonBean selectedUser; 
	// private EditText mSearch;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_campus_people,
				container, false); 
		mPullToRefreshListView = (PullToRefreshListView) view
				.findViewById(R.id.people_list);
		mPullToRefreshListView.setMode(Mode.PULL_FROM_START);
		mPullToRefreshListView.setOnRefreshListener(this);
		mPeopleList = mPullToRefreshListView.getRefreshableView();
		peopleAdapter = new CampusPeopleAdapter(
				getActivity());
		mPeopleList.setAdapter(peopleAdapter);
		mPeopleList.setOnItemClickListener(this);

		// mSearch = (EditText) view.findViewById(R.id.et_search);
		// mSearch.clearFocus();

		mCampusUsers = new LinkedList<NearPersonBean>();
		// test
		// User user1 = new User("589396412218359559", "4042017386297853232",
		// "10089", "jiang", "jiang", "1", null, null,
		// "深圳大学", "18825193807", "write the code change the world!", 125,
		// 120, 130, null, 2);
		// User user2 = new User("606844474145847127", "4600684025511915918",
		// "10001", "admin", "admin", "1", null, null,
		// "深圳大学", "18825193807", "write the code,change the world!", 125,
		// 120, 130, null, 2);
		// User user3 = new User(null, null, "10004", "Jie", "Cool", "1", null,
		// null,
		// "深圳大学", "18825193807", "write the code change the world!", 125,
		// 120, 130, null, 2);
		// User user4 = new User(null, null, "10005", "JieGe", "Miss", "1",
		// null, null,
		// "深圳大学", "18825193807", "write the code change the world!", 125,
		// 120, 130, null, 2);
		//
		// mCampusUsers.add(user1);
		// mCampusUsers.add(user2);
		// mCampusUsers.add(user3);
		// mCampusUsers.add(user4);

		peopleAdapter.setData(mCampusUsers);
		//new AsyncHttpPost("nearbyusers",this,"22.533381","113.929829","1").post();
//		new XYClient().post(
//				RequestId.ID_NEARBY_USERS, 
//				RequestUrl.URL_NEARBY_USERS, 
//				HttpRequestParams.nearbyUserParams("22.533381","113.929829","1"), 
//				this); 
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		default:
			break;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		selectedUser = mCampusUsers.get(position - 1);
	//	new AsyncHttpPost("Getinfo", this,XiaoYuanApp.getLoginUser(getActivity()).userBean.getUid(), selectedUser.getUid())
	//	.post();
		new XYClient().post(
				RequestId.ID_GET_INFO, 
				RequestUrl.URL_GET_INFO,
				HttpRequestParams.getUserInfoParams(XiaoYuanApp.getLoginUser(getActivity()).userBean.getUid(), selectedUser.getUid()),
				this);
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

	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		if(mPullToRefreshListView.isHeaderShown()){

			getNearUsers(XiaoYuanApp.getLoc());
		}else if(mPullToRefreshListView.isFooterShown()){
			
		}
		//mPullToRefreshListView.
	}

	/* 
	 * @see com.myxiaoapp.listener.OnResponseListener#onFailure(int)
	 */
	@Override
	public void onFailure(int statusCode) {
		Log.d(TAG, "statusCode="+statusCode);
		mPullToRefreshListView.onRefreshComplete();
	}


	public CampusPeopleAdapter getAdapter(){
		return peopleAdapter;
	}
	@Override
	public void onReceiveSuccess(String rec,final int id) {
		mPullToRefreshListView.onRefreshComplete();
		Log.d(TAG,"rec="+rec);
		Gson gson = new Gson();
		switch(id){
		case RequestId.ID_NEARBY_USERS:
			NearPersonList personlist = gson.fromJson(rec, NearPersonList.class);
			peopleAdapter.clear();
			peopleAdapter.addData(personlist.getData());
			break;
		case RequestId.ID_GET_INFO:
			UserInfoBean userInfoBean = gson.fromJson(rec, UserInfoDataBean.class).getData();
			Intent intent = new Intent(getActivity(), HomePageActivity.class); 
			intent.putExtra("bean", userInfoBean);
			startActivity(intent); 
			break;
		case RequestId.ID_BACK_STATE:
			Log.d(TAG, "回传经纬度成功");
			break;
		}
	}

	/* 
	 * @see com.myxiaoapp.listener.OnResponseListener#onReceiveFailure(java.lang.String)
	 */
	@Override
	public void onReceiveFailure(String rec) {
		mPullToRefreshListView.onRefreshComplete();
		Log.d(TAG, "fail="+rec);
	}
 

}
