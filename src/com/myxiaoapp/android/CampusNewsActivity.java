package com.myxiaoapp.android;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.preference.PreferenceManager.OnActivityResultListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.myxiaoapp.adapter.CampusNewsAdapter;
import com.myxiaoapp.listener.OnResponseListener;
import com.myxiaoapp.model.CampusCircleBean;
import com.myxiaoapp.model.HttpRequestParams;
import com.myxiaoapp.model.HttpResponseHandler;
import com.myxiaoapp.model.MomentBean;
import com.myxiaoapp.model.User;
import com.myxiaoapp.network.AsyncHttpPost;
import com.myxiaoapp.network.XYClient;
import com.myxiaoapp.utils.ACache;
import com.myxiaoapp.utils.CacheHelper;
import com.myxiaoapp.utils.Constant;
import com.myxiaoapp.utils.Constant.RequestId;
import com.myxiaoapp.utils.Constant.RequestUrl;
import com.myxiaoapp.utils.JSONHelper;
import com.myxiaoapp.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pulltorefresh.library.PullToRefreshBase;
import com.pulltorefresh.library.PullToRefreshBase.Mode;
import com.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.pulltorefresh.library.PullToRefreshListView;

/**
 * modify:新增OnItemClickListener接口，监听listview
 * 
 * @author:liqihang
 */
public class CampusNewsActivity extends CommonActivity implements
		OnClickListener, OnItemClickListener, OnLongClickListener,
		OnResponseListener,OnRefreshListener<ListView>, OnCancelListener,Serializable {
	private final String CONSTANTLOG = "CampusCircle:";
	private static final String TAG = "CampusNewsActivity";
	private static final String MAXPAGE = "10";
	ACache campusCache;
	private Context mContext;
	private ListView mNewsList;
	private CampusNewsAdapter mAdapter;
	private PullToRefreshListView mPullToRefreshListView;
	private ImageButton mSendNews; 
	private User user;
	private String username;
	private int mFlag; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_campus_news);
		setActionBarTitle("校园圈");
		//updateHandler = 
		ACache cache = ACache.get(this, "Schoolinfo");
		if(cache.getAsString("minId") == null){
			cache.put("minId", "0");
		}
		if(cache.getAsString("maxId") == null){
			cache.put("maxId", "0");
		}
		mContext = this;
		// 获取校园圈列表信息

		Intent in = getIntent();
		mFlag = in.getFlags();
		if (mFlag == Constant.FLAG_Discovery) {

			/*
			 * View view = LayoutInflater.from(this).inflate(
			 * R.layout.send_campus_news_button, mNewsList, false); mSendNews =
			 * (ImageButton) view.findViewById(R.id.send_campus_news);
			 * mSendNews.setOnClickListener(this);
			 * mSendNews.setOnLongClickListener(this);
			 */
			/*
			 * mActionBarRightButton.setImageDrawable(getResources().getDrawable(
			 * R.drawable.send_campus_news));
			 * mActionBarRightButton.setVisibility(View.VISIBLE);
			 * mActionBarRightButton.setOnClickListener(this);
			 * mActionBarRightButton.setOnLongClickListener(this);
			 */// showImageButton(this);
				// View view =
				// LayoutInflater.from(this).inflate(R.layout.send_campus_news_button);
				// getActionBarRightButton().set
				// View view = LayoutInflater.from(this).inflate(
				// R.layout.send_campus_news_button, mNewsList, false);
				// // mSendNews = (ImageButton)
				// view.findViewById(R.id.send_campus_news);
				// //
				// //// addCustomView(view);
			mSendNews = getActionBarRightButton();
			mSendNews.setImageDrawable(getResources().getDrawable(
					R.drawable.send_campus_news));
			mSendNews.setOnClickListener(this);
			mSendNews.setOnLongClickListener(this);
		}

		showBackButton();
		init();
	}
	public String getUid(){
		return username;
	}
	private void init() { 	
		campusCache = ACache.get(this, "CampusNews");
		mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.campus_news_list);
		mPullToRefreshListView.setMode(Mode.BOTH);
		mPullToRefreshListView.setOnRefreshListener(this);
		mNewsList = mPullToRefreshListView.getRefreshableView();
		mAdapter = new CampusNewsAdapter(this,mFlag);
		mNewsList.setAdapter(mAdapter);
		mNewsList.setOnItemClickListener(this);
		
		user = XiaoYuanApp.getLoginUser(this);
		Log.d("mydebug", "user id=" + user.userBean.getUid());
		username = "xiaoyuan";
		if(mFlag == Constant.FLAG_CAMPUS || mFlag == Constant.FLAG_Discovery)
			username = "xiaoyuan";
		else if(mFlag == Constant.FLAG_ME )
			username = user.userBean.getUid();
		else {
			username = mFlag+"";
		}
	//	new AsyncHttpPost("Schoolinfo", this, ""	+ user.userBean.getUid(), username , null, null, MAXPAGE).post();
		
		//test
		//发布消息
//		new AsyncHttpPost("updatemsg",this,"10002", "测试10002").post();
		//删除校缘圈信息
	//	new AsyncHttpPost("delmsg", this, "68", "10001").post();
		
		//评论校缘圈
	//	new AsyncHttpPost("reply", this, "68", "测试评论", "10002", "10001").post();
		
		//删除评论
	//	new AsyncHttpPost("delcomment", this, "37", "10002").post();
		
		//点赞
	//	new AsyncHttpPost("addlike", this, "68", "10002").post();
		
	}

	 
 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.campus_news, menu);
		if (mFlag != Constant.FLAG_ME) { // 从校内附近的人进来
			menu.removeItem(R.id.action_information);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_information:
			startActivity(new Intent(this, MessageActivity.class));
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ibtn_right:
			startActivityForResult(new Intent(mContext,
					SelectPicGroupActivity.class), 1);
			break;
		
		default:
			break;
		}
	}
	@Override
	public boolean onLongClick(View v) {
		switch (v.getId()) {
		case R.id.ibtn_right:
			startActivity(new Intent(mContext, PublishTextActivity.class));
			break;

		default:
			break;
		}
		return true;
	}
	public void addLike(final String msg_id){
	//	new AsyncHttpPost("addlike", this, v.getTag().toString(), XiaoYuanApp.getLoginUser(mContext).userBean.getUid()).post();
		new XYClient().post(
				RequestId.ID_ADD_LIKE, 
				RequestUrl.URL_ADD_LIKE, 
				HttpRequestParams.addLike(msg_id, XiaoYuanApp.getLoginUser(mContext).userBean.getUid()), 
				this);
		
	}	
	public void dialog(final String position){
		final String mid = mAdapter.getItem(Integer.valueOf(position )).getM_id();
		Log.d(TAG,"mid="+mid);
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setMessage("确认删除吗？");

		builder.setTitle("提示");
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				mAdapter.remove(Integer.valueOf(position ));
				//new AsyncHttpPost("delmsg", CampusNewsActivity.this, mid, XiaoYuanApp.getLoginUser(CampusNewsActivity.this).userBean.getUid()).post();
				new XYClient().post(
						RequestId.ID_DEL_MSG, 
						RequestUrl.URL_DEL_MSG, 
						HttpRequestParams.delMsg(mid, XiaoYuanApp.getLoginUser(CampusNewsActivity.this).userBean.getUid()), 
						CampusNewsActivity.this);
			}
		});

		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		builder.create().show();
	} 
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && data != null) {
			data.setClass(this, PublishImageActivity.class);
			startActivity(data);
		}
	}

	private void completeRefresh(){
	/*	new Handler().postDelayed(new Runnable(){

		@Override
		public void run() {
	*/		mPullToRefreshListView.onRefreshComplete();
/*
		}
		
	}, 1000);
*/		
	}
	/*
	 * @see com.myxiaoapp.listener.OnResponseListener#onFailure(int)
	 */
	@Override
	public void onFailure(int statusCode) {
		Log.d(TAG,""+statusCode);
		completeRefresh();
	}

	/*
	 * @see
	 * com.myxiaoapp.listener.OnResponseListener#onReceiveSuccess(java.lang.
	 * String)
	 */
	@Override
	public void onReceiveSuccess(final String rec,final int id) {
		Log.d(TAG, "onReceiveSuccess"+id);
		switch(id){
		case RequestId.ID_SCHOOL_INFO: 
					CampusCircleBean bean;
					Gson gson = new Gson();
					bean = gson.fromJson(rec, CampusCircleBean.class);
					ACache cache = CacheHelper.getCache(CampusNewsActivity.this,"Schoolinfo");
					String maxId = cache.getAsString("maxId");
				    String minId = cache.getAsString("minId");
					if(bean.getData().size() > 0){
						List<MomentBean> datas = bean.getData();
						int size = datas.size();
						int max = Integer.parseInt( datas.get(0).getM_id() );
						int min = Integer.parseInt( datas.get(size-1).getM_id() );
						if(maxId == null){
							cache.put("maxId", max+"");
							cache.put("minId", min+"");
							mAdapter.addHeadData(bean.getData());
						}else {
							if(max > Integer.parseInt(maxId)){
								mAdapter.addHeadData(bean.getData());
								cache.put("maxId", max+"");
							}else {
								mAdapter.addFootData(bean.getData());
								cache.put("minId", min+"");
							}
						}
						mAdapter.notifyDataSetChanged();
		/*				for(int i = 0; i < size; i++){
							cache.put(datas.get(i).getM_id(),datas.get(i));
						}
	*/					Log.d(TAG, "minId="+datas.get(size-1).getM_id()+",maxId="+datas.get(0).getM_id());
					}
					completeRefresh();
					
					break;
		case RequestId.ID_DEL_MSG:
			break; 
		case RequestId.ID_ADD_LIKE:
			Log.d(TAG, "点赞成功");
			break;
		default:break;
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
		completeRefresh();
	}

	/* 
	 * @see com.pulltorefresh.library.PullToRefreshBase.OnRefreshListener#onRefresh(com.pulltorefresh.library.PullToRefreshBase)
	 */
	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		if (mPullToRefreshListView.isHeaderShown()) { // 下拉 
			Log.d(TAG, "header");
			ACache cache= CacheHelper.getCache(this, "Schoolinfo");
			String maxId = cache.getAsString("maxId");
		//	new AsyncHttpPost("Schoolinfo", this, ""+ user.userBean.getUid(), username , maxId, "1", MAXPAGE).post();
			new XYClient().post(
					RequestId.ID_SCHOOL_INFO, 
					RequestUrl.URL_SCHOOL_INFO, 
					HttpRequestParams.campusCircle(user.userBean.getUid(), username , maxId, "1", MAXPAGE), 
					this);
		} else if (mPullToRefreshListView.isFooterShown()) { // 上拉
			Log.d(TAG, "footer"); 
			ACache cache = CacheHelper.getCache(this, "Schoolinfo");
			int minId = Integer.parseInt( cache.getAsString("minId") );
			Log.d(TAG, "minId="+minId+",adapter id="+mAdapter.getMinId());
			if(minId == mAdapter.getMinId()){
			//	new AsyncHttpPost("Schoolinfo", this, ""+ user.userBean.getUid(), username , minId+"", "2", MAXPAGE).post();
			new XYClient().post(
					RequestId.ID_SCHOOL_INFO, 
					RequestUrl.URL_SCHOOL_INFO, 
					HttpRequestParams.campusCircle(user.userBean.getUid(), username , minId+"", "2", MAXPAGE),
					this);
			}else {
				List<MomentBean> beanList = readCache(minId);
				mAdapter.addFootData(beanList);
				mAdapter.notifyDataSetChanged();
			}
		}
	}
	private List<MomentBean> readCache(int minId){
		int num = Integer.parseInt( MAXPAGE );
		List<MomentBean> beanList = new ArrayList<MomentBean>();
		while( num > 0){
			num--;
			ACache cache = ACache.get(this, "Schoolinfo");
			minId--;
			MomentBean bean = (MomentBean)cache.getAsObject(minId+""); 
			if(bean == null) continue;
			beanList.add(bean);
		}
		return beanList;
	}

	/* 
	 * @see android.content.DialogInterface.OnCancelListener#onCancel(android.content.DialogInterface)
	 */
	@Override
	public void onCancel(DialogInterface dialog) {
		mPullToRefreshListView.onRefreshComplete();
	}

	/* 
	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override 
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(this, CampusNewsDetailsActivity.class); 
		intent.putExtra("bean",(MomentBean)mAdapter.getItem(position)); 
		startActivity(intent);
		
	}
	/* 
	 * @see android.support.v4.app.FragmentActivity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, "onResume");
		mAdapter.clear();
		mAdapter.notifyDataSetChanged();
		//new AsyncHttpPost("Schoolinfo", this, ""	+ user.userBean.getUid(), username , null, null, MAXPAGE).post();
		new XYClient().post(
				RequestId.ID_SCHOOL_INFO, 
				RequestUrl.URL_SCHOOL_INFO,
				HttpRequestParams.campusCircle(user.userBean.getUid(), username , null, null, MAXPAGE), 
				this);
	}
	
	/* 
	 * @see android.app.Activity#onRestart()
	 */
	@Override
	protected void onRestart() {
		
		super.onRestart();
		Log.d(TAG, "onRestart");
	}
	 
}
