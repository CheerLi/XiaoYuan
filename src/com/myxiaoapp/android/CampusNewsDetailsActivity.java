package com.myxiaoapp.android;

import java.net.URLEncoder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.myxiaoapp.adapter.CampusNewsCommentAdapter;
import com.myxiaoapp.adapter.PhotoAdapter;
import com.myxiaoapp.listener.OnResponseListener;
import com.myxiaoapp.model.CampusCrclCmtBean;
import com.myxiaoapp.model.HttpRequestParams;
import com.myxiaoapp.model.MomentBean;
import com.myxiaoapp.network.AsyncHttpPost;
import com.myxiaoapp.network.XYClient;
import com.myxiaoapp.utils.Constant;
import com.myxiaoapp.utils.Constant.RequestUrl;
import com.myxiaoapp.utils.InputUtils;
import com.myxiaoapp.utils.Utils;
import com.myxiaoapp.utils.Constant.RequestId;
import com.myxiaoapp.view.MyGridView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * @author : liqihang
 * @time : 2014/9/19
 * 
 *       第四张图，查看单条详细校园心情，可进行举报，评论，点赞
 */

public class CampusNewsDetailsActivity extends CommonActivity implements
		OnKeyListener, OnClickListener, OnGlobalLayoutListener,OnResponseListener,OnItemClickListener,OnItemLongClickListener {
	private final String TAG = "CampusNewsDetailsActivity";
	private final int COMMENT = 1;
	private final int REPLY = 0;
	private int sendType;
	private ImageView mPortrait;// 头像
	private TextView mContent;// 心情内容
	private TextView time;
	private MyGridView mNewsPhotos;// 心情照片
	private TextView mLike;// 评论图标
	private TextView mComment;// 点赞图标
	private TextView mLikePeople;// 点赞的人
	private ListView mCommentList;// 评论内容
	private PhotoAdapter mPhotoAdapter;//照片适配器
	private CampusNewsCommentAdapter mCommentAdapter;//评论适配器
	private RelativeLayout mLayoutInput;// 输入框，点击评论时弹出
	private ScrollView mScrollView;
	private EditText mEditText;// 输入框，默认隐藏
	private Button mChatOutput;
	InputMethodManager inputManager;// 软键盘，默认隐藏
	RelativeLayout mRelativeLayout;// 屏幕，监听屏幕被点击
	private int clickY; // 点击所在y坐标
	private int inputY;// 输入框y坐标
	private int inputStatus;// 输入框状态，0=隐藏，1=显示
	private int count;// 初始为0，记录layout的次数，因为点击评论后，输入框弹出来会layout两次
	private MomentBean bean;
	private ImageLoader imageLoader;
	private DisplayImageOptions options; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_campus_news_details);
		showBackButton();
		Intent intent = getIntent(); 
		bean = (MomentBean) intent.getSerializableExtra("bean"); 
		Log.d(TAG, bean.toString());
		setActionBarTitle(bean.getName());
		init();
	}

	private void init() {
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));
		options = new DisplayImageOptions.Builder().cacheInMemory(true)
				.cacheOnDisk(true).build();

		mPortrait = (ImageView) findViewById(R.id.portrait);
		mPortrait.setOnClickListener(this);
		imageLoader.displayImage(bean.getS_portrait(), mPortrait, options);
		
		mContent = (TextView) findViewById(R.id.mood);
		mContent.setText(bean.getM_info());
		
		time = (TextView)findViewById(R.id.time);
		time.setText(bean.getM_time());
		mNewsPhotos = (MyGridView) findViewById(R.id.campus_comment);
		mPhotoAdapter = new PhotoAdapter(this, Constant.FLAG_CAMPUS);
		mPhotoAdapter.setSPics(bean.getM_spictures());
		mPhotoAdapter.setPics(bean.getM_pictures());
		mNewsPhotos.setAdapter(mPhotoAdapter);
		
		
		mComment = (TextView) findViewById(R.id.number_of_people_comment);
		mComment.setOnClickListener(this);
		mComment.setText(bean.getM_commentnum());
	
		mLike = (TextView) findViewById(R.id.number_of_people_like);
		mLike.setOnClickListener(this);
		mLike.setText(bean.getM_likesnum());
		mLike.setTag(bean.getM_id());
		
		mLikePeople = (TextView) findViewById(R.id.people_like);
		
		mCommentList = (ListView) findViewById(R.id.comment_list);
		mCommentAdapter = new CampusNewsCommentAdapter(this);
		mCommentAdapter.setComments(bean.getComment_list());
		mCommentList.setAdapter(mCommentAdapter);
		mCommentList.setOnItemClickListener(this);
		mCommentList.setOnItemLongClickListener(this);
		
		mLayoutInput = (RelativeLayout) findViewById(R.id.layout_chat_input);
		mScrollView = (ScrollView) findViewById(R.id.scroll);
		mEditText = (EditText) findViewById(R.id.chat_input);
		Utils.setListViewHeightBasedOnChildren(mCommentList);
		mChatOutput = (Button) findViewById(R.id.chat_output);
		mChatOutput.setOnClickListener(this);
		inputManager = (InputMethodManager) mEditText.getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		mRelativeLayout = (RelativeLayout) findViewById(R.id.layout_canpus_news_detail);
		mRelativeLayout.setOnClickListener(this);

		mRelativeLayout.getViewTreeObserver().addOnGlobalLayoutListener(this);
		// topYComment = mLikePeople.getScrollY()+mLikePeople.getHeight();
		// Log.v("height", ""+topYComment);
	}

	/**
	 * 举报
	 * 
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		return true;
	}

	@Override
	public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
		// if(event.getAction() == KeyEvent.)
		return false;
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.portrait:
			new PreviewImageDialog(this,bean.getPortrait()).show();
			break;
		case R.id.chat_output:
	//		mCommentAdapter
			String name = XiaoYuanApp.getLoginUser(this).userBean.getName();
			String name_by = "";
			String comment = mEditText.getText().toString();
			if(sendType == COMMENT){
			//	new AsyncHttpPost("reply",this, bean.getM_id(),comment , XiaoYuanApp.getLoginUser(this).userBean.getUid(), null )
			//	.post();
				new XYClient().post(
						RequestId.ID_REPLY, 
						RequestUrl.URL_REPLY, 
						HttpRequestParams.reply(bean.getM_id(),comment , XiaoYuanApp.getLoginUser(this).userBean.getUid(), null ), 
						this);
			}else if(sendType == REPLY){
				CampusCrclCmtBean cb = (CampusCrclCmtBean) mCommentAdapter.getItem(Integer.parseInt( v.getTag().toString() ));
				name_by = cb.getName();
				//new AsyncHttpPost("reply",this, bean.getM_id(),mEditText.getText().toString(), XiaoYuanApp.getLoginUser(this).userBean.getUid(), cb.getUid() )
				//.post();
				new XYClient().post(
						RequestId.ID_REPLY, 
						RequestUrl.URL_REPLY, 
						HttpRequestParams.reply(bean.getM_id(), mEditText.getText().toString(), XiaoYuanApp.getLoginUser(this).userBean.getUid(), cb.getUid()), 
						this);
			}
			mCommentAdapter.addItem(name,name_by,comment);
			mCommentAdapter.notifyDataSetChanged();
	        bean.setComment_list(mCommentAdapter.getComments());
	        
			((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);  
			
    		Utils.setListViewHeightBasedOnChildren(mCommentList);
    		//Utils.setListViewHeightBasedOnChildren(mPullToRefreshListView);
    		mEditText.setText("");
			break;
		case R.id.layout_canpus_news_detail:
			// 隐藏软键盘
			inputManager.hideSoftInputFromWindow(mEditText.getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);

			// 隐藏输入框
			mLayoutInput.setVisibility(View.GONE);
			inputStatus = 0;
			break;

		case R.id.number_of_people_comment:
			mLayoutInput.setVisibility(View.VISIBLE);
			inputStatus = 1; //设置
			sendType = COMMENT;
			inputManager.showSoftInput(mEditText, 0);
			mScrollView.fullScroll(View.FOCUS_DOWN);
			mEditText.requestFocus();
			break;
		case R.id.number_of_people_like:
			//new AsyncHttpPost("addlike", this, v.getTag().toString(), XiaoYuanApp.getLoginUser(this).userBean.getUid()).post();
			new XYClient().post(
					RequestId.ID_ADD_LIKE, 
					RequestUrl.URL_ADD_LIKE,
					HttpRequestParams.addLike(v.getTag().toString(), XiaoYuanApp.getLoginUser(this).userBean.getUid()), 
					this);
			TextView like = (TextView)v.findViewById(R.id.number_of_people_like);
			like.setText( (Integer.parseInt(like.getText().toString() )+1 )+"");
			break; 
		default:
			break;
		}

	}

	/*
	 * @Override public void onItemClick(AdapterView<?> parent, View view, int
	 * position, long id) {
	 * 
	 * if (mLayoutInput.getVisibility() == View.GONE) {
	 * mLayoutInput.setVisibility(View.VISIBLE); }
	 * 
	 * if (!mEditText.isFocused()) { mEditText.requestFocus(); }
	 * 
	 * if (inputManager.isActive(mEditText)) {
	 * inputManager.showSoftInput(mEditText, 0); }
	 * 
	 * clickY = ClickUtils.getRect(view).top+view.getHeight();//被点击该条评论的底部Y坐标l
	 * count = 0; //初始化为0，忽略之前已经layout的次数 // inputStatus = 1;//设置输入框为显示状态 }
	 */@Override
	public void onGlobalLayout() {
		/*
		 * count=2时，输入框最后一次layout，输入法也已经弹出来，并且此时输入框已经在输入法的上面
		 */
		count++;

		if (0 == inputY && 2 == count) { // 当获取输入框坐标后(inputY>0)，以后点击不再重复获取，因为输入框弹出后的坐标是一个定值
			inputY = InputUtils.getY(mLayoutInput); // 获取输入框弹出后的y坐标
			mScrollView.scrollBy(0, clickY - inputY);
			inputStatus = 1;
		} else if (1 == inputStatus || 2 == count) {
			Log.v("ii", inputStatus + "," + count + "," + clickY);
			mScrollView.scrollBy(0, clickY - inputY);
			inputStatus = 1;
		}

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
			case RequestId.ID_DEL_COMMENT:
				Log.d(TAG, "删除评论成功");
				break;
			case RequestId.ID_ADD_LIKE:
				Log.d(TAG, "点赞成功");
				break;
			case RequestId.ID_REPLY:
				Log.d(TAG, "评论成功");
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
	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		mLayoutInput.setVisibility(View.VISIBLE);
		sendType = REPLY;
		mChatOutput.setTag(position);
		inputManager.showSoftInput(mEditText, 0);
		mScrollView.fullScroll(View.FOCUS_DOWN);
		mEditText.requestFocus();
	}

	/* 
	 * @see android.widget.AdapterView.OnItemLongClickListener#onItemLongClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		String msg_id = bean.getM_id();
		String comt_id =  ((CampusCrclCmtBean)mCommentAdapter.getItem(position)).getComt_id();
		String uid_by =  ((CampusCrclCmtBean)mCommentAdapter.getItem(position)).getUid();
		String user_id = XiaoYuanApp.getLoginUser(CampusNewsDetailsActivity.this).userBean.getUid();
		if(uid_by.equals(user_id)){
			createDialog(msg_id,comt_id,uid_by,position);
		}
		return true;
	}
	
	public void createDialog(final String msg_id, final String comt_id, final String uid_by, final int position){
		final CharSequence[] items = {"deleted"};   
		AlertDialog.Builder builder = new AlertDialog.Builder(this);  
		//builder.setTitle("Pick a color");  
		builder.setItems(items, new DialogInterface.OnClickListener() {  
		    public void onClick(DialogInterface dialog, int item) {  
		        Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();  
		     //   new AsyncHttpPost("delcomment", CampusNewsDetailsActivity.this, msg_id, comt_id, uid_by)
		     //   .post();
		        new XYClient().post(
		        		RequestId.ID_DEL_COMMENT, 
		        		RequestUrl.URL_DEL_COMMENT, 
		        		HttpRequestParams.delComment( msg_id, comt_id, uid_by), 
		        		CampusNewsDetailsActivity.this);
		        mCommentAdapter.deleteItem(position);
		        mCommentAdapter.notifyDataSetChanged();
		        bean.setComment_list(mCommentAdapter.getComments());
		    }  
		});  
		AlertDialog alert = builder.create();
		alert.show();
	} 
	 

}
