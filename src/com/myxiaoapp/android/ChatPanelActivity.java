package com.myxiaoapp.android;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
  
import android.widget.Toast;

import com.myxiaoapp.adapter.ChatMsgAdapter; 
import com.myxiaoapp.listener.OnResponseListener;
import com.myxiaoapp.model.BaseModel; 
import com.myxiaoapp.model.ChatItem; 
import com.myxiaoapp.model.HttpRequestParams;
import com.myxiaoapp.model.User;
import com.myxiaoapp.network.AsyncHttpPost;
import com.myxiaoapp.network.HttpRequestParam;
import com.myxiaoapp.network.XYClient;
import com.myxiaoapp.utils.Constant;
import com.myxiaoapp.utils.Constant.RequestUrl;
import com.myxiaoapp.utils.DataBaseHelper;
import com.myxiaoapp.utils.DataManager;
import com.myxiaoapp.utils.SQLiteHelper;
import com.myxiaoapp.utils.Constant.RequestId;
import com.myxiaoapp.view.ExpressionPanel;
import com.myxiaoapp.view.ExpressionPanel.OnExpressionClickListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;

public class ChatPanelActivity extends CommonActivity implements
		OnClickListener, OnTouchListener, OnExpressionClickListener, OnResponseListener  {
	public static final String ACTION_RECEIVE_CHAT = "com.myxiaoapp.android.CharPanelActivity";
	private static final String TAG = "mydebug";

	private ListView mChatList;
	private ChatMsgAdapter mAdapter;

	private EditText mInputText;
	private Button mSend;
	private ImageView mAddAttach;
	private ImageView mAddExpression;

	private ExpressionPanel mExpressionPanel;
  
	private String myId;
	private String myName;
	private String myPortraitUrl; 
	private Bitmap myPortraitMap;
	private String myToSendMessage;
	
	private String otherId;
	private String otherName;
	private String otherPortraitUrl;
	private Bitmap otherPortraitMap;
	
	private String message;
	
	private InputMethodManager imm;

	private ImageLoader imageLoader;
	private DisplayImageOptions options;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat_panel);
		// setActionBarTitle("聊天");
		init();
	    DataManager.getInstance().setmFrontChat(otherId);
	    Log.d(TAG, "otherId="+otherId+",otherName="+otherName);
	    setActionBarTitle(otherName);
	}

	@Override
	protected void onResume() {
		super.onResume(); 
	}

	private void init() {
		mChatList = (ListView) findViewById(R.id.lv_chat_msg);
		mChatList.setSelection(mChatList.getBottom());
		mChatList.setOnTouchListener(this);
		mInputText = (EditText) findViewById(R.id.et_input_text); 
		mInputText.setOnClickListener(this);
		mInputText.setOnTouchListener(this);
		
		mSend = (Button) findViewById(R.id.btn_send);
		mSend.setOnClickListener(this);
		
		mAddAttach = (ImageView) findViewById(R.id.iv_add_attach);
		mAddAttach.setOnClickListener(this);
		
		mAddExpression = (ImageView) findViewById(R.id.iv_add_expression);
		mAddExpression.setOnClickListener(this);
		mAddExpression.setOnTouchListener(this);
		mExpressionPanel = (ExpressionPanel) findViewById(R.id.expression_panel);
		mExpressionPanel.startWork(getSupportFragmentManager()); 
		mExpressionPanel.setOnExpressionClickListener(this);
		getActionBarRightButton().setImageDrawable(
				getResources().getDrawable(R.drawable.icon_chat_title_bar));
		getActionBarRightButton().setOnClickListener(this);
		getActionBarRightButton().setVisibility(View.VISIBLE);
		showBackButton();
 

		// test
		// mSecondUser.setChatUserId(SECOND_USER_ID);
		// mSecondUser.setChatChannelId(SECOND_USER_CHANNEL_ID);
		mAdapter = new ChatMsgAdapter(this);

		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		myId = XiaoYuanApp.getLoginUser(this).userBean.getUid();
		myName = XiaoYuanApp.getLoginUser(this).userBean.getUsername();
		myPortraitUrl = XiaoYuanApp.getLoginUser(this).userBean.getS_portrait();
		Log.d(TAG, "url="+myPortraitUrl);

		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));
		options = new DisplayImageOptions.Builder().cacheInMemory(true)
				.cacheOnDisk(true).build();
		otherId = getIntent().getStringExtra("fromUserId");
	    otherName = getIntent().getStringExtra("fromName");
	    otherPortraitUrl = getIntent().getStringExtra("fromPortrait");
	    
	    message = getIntent().getStringExtra("message");
	    Log.d(TAG, "mU="+myPortraitUrl+",oU="+otherPortraitUrl);
		imageLoader.loadImage(myPortraitUrl, mBitMapListener);
		imageLoader.loadImage(otherPortraitUrl, mBitMapListener);
	}
	ImageLoadingListener mBitMapListener = new ImageLoadingListener() {
		
		@Override
		public void onLoadingStarted(String imageUri, View view) {
		}
		
		@Override
		public void onLoadingFailed(String imageUri, View view,
				FailReason failReason) {
		}
		
		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if(imageUri.equals(myPortraitUrl)){

				myPortraitMap = loadedImage;
			}else if(imageUri.equals(otherPortraitUrl)){

				otherPortraitMap = loadedImage;
			} 
			Log.d(TAG, "imageUri="+imageUri);
			//Log.d(TAG, )
			if(myPortraitMap != null && otherPortraitMap != null){
				mAdapter.setPortrait(myPortraitMap, otherPortraitMap);
				mChatList.setAdapter(mAdapter); 
				readHistory();
				registerReceiver();
				if(message != null){
					addChatItem(getIntent());
					
				}
			}
		}
		
		@Override
		public void onLoadingCancelled(String imageUri, View view) {
		}
	};
	private void addChatItem(Intent intent){
		String data = intent.getStringExtra("data"); 
		Log.d(TAG, "data="+data);
		mAdapter.addChatItem(ChatItem.json2ChatItem(data));
        scrollListViewToBottom();
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ibtn_right:
			startActivity(new Intent(this, HomePageActivity.class));
			break;
		case R.id.et_input_text:
			if (mExpressionPanel.getVisibility() == View.VISIBLE) {
				mExpressionPanel.setVisibility(View.GONE);
			}
			mAddAttach.setVisibility(View.GONE);
			mSend.setVisibility(View.VISIBLE);
			break;
		case R.id.iv_add_attach:
			break;
		case R.id.btn_send:
			String text = mInputText.getText().toString().trim();
			if (!TextUtils.isEmpty(text)) {
				// String toWho =
				// XiaoYuanApp.getLoginUser(this).getUserId().equals(
				// mFirstUser.getUserId()) ? mSecondUser.getChatUserId()
				// : mFirstUser.getChatUserId();
				// String fromWho =
				// XiaoYuanApp.getLoginUser(this).getChatUserId();
				//
				// ChatHelper chatHelper = new ChatHelper();
				// ChatMessage chatMessage = new ChatMessage();
				// chatMessage.setMessage(text);
				// chatMessage.setTime(System.currentTimeMillis());
				// chatMessage.setFromWho(fromWho);
				// chatMessage.setToWho(toWho);
				// chatMessage.setToUser(mSecondUser.getUserId());
				// chatMessage.setFromUser(mFirstUser.getUserId());
				// chatMessage.setToUserName(mSecondUser.getName());
				// chatMessage.setFromUserName(mFirstUser.getName());
				// String send = ChatMessage.createMsgJson(chatMessage);
				// Log.d(TAG, "send >>> " + send);
				// chatHelper.pushMessage(toWho, mSecondUser.getChatChannelId(),
				// send);
				// updateChatList(chatMessage);
				// mInputText.setText("");
				// ChatHelper.writeChatMessage2DB(this, chatMessage, 1);
				sendMessage();
			}
			break;
		case R.id.iv_add_expression:
			int visibility = mExpressionPanel.getVisibility();
			if (visibility == View.VISIBLE) {
				mExpressionPanel.setVisibility(View.GONE);
			} else {
				imm.hideSoftInputFromWindow(mInputText.getWindowToken(), 0);
				try {
					Thread.sleep(80);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				mExpressionPanel.setVisibility(View.VISIBLE);
			}

			break;
		default:
			break;
		}
	}
	private void readHistory() { 
		mAdapter.setChatItem(DataBaseHelper.readChat(otherId));
        scrollListViewToBottom();
    }

    private void registerReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_RECEIVE_CHAT);
        registerReceiver(mChatReceiver, filter);
    }

    private void sendMessage() {
        myToSendMessage = mInputText.getText().toString();
        mInputText.setText("");
        sendHttpRequest();
        ChatItem item = new ChatItem();
        item.setFromUserId(myId);
        item.setFromUserName(myName);
        item.setToUserId(otherId);
        item.setToUserName(otherName); 
        item.setIsMeChat(true);
        item.setMessage(myToSendMessage);
        item.setmPortrait(myPortraitUrl);
        item.setFromPortrait(otherPortraitUrl);
        item.setTime(System.currentTimeMillis());
        
       // item.setmPortrait(myPortraitMap);
        mAdapter.addChatItem(item);
        DataBaseHelper.saveChat(item);
        scrollListViewToBottom();
    }
    
    public void sendHttpRequest() {
   // 	new AsyncHttpPost("Pushchat", this, myId, otherId, myToSendMessage, myName, otherName, myPortraitUrl, XGPushConfig.getToken(this)).post();
    	new XYClient().post(
    			RequestId.ID_PUSH_MSG, 
    			RequestUrl.URL_PUSH_MSG, 
    			HttpRequestParams.getPushChat(myId, otherId, myToSendMessage, myName, otherName, myPortraitUrl, XGPushConfig.getToken(this)),
    			this);
    }
    private void scrollListViewToBottom() { 
    	mChatList.post(new Runnable() {
            @Override
            public void run() {
            	mChatList.setSelection(mAdapter.getCount() - 1);
            }
        });
    }
     

    public BroadcastReceiver mChatReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			addChatItem(intent); 
		}
    	
    };
	/* 
	 * @see android.view.View.OnTouchListener#onTouch(android.view.View, android.view.MotionEvent)
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		return false;
	}

	/* 
	 * @see com.myxiaoapp.view.ExpressionPanel.OnExpressionClickListener#onExpressionClick(int, java.lang.String)
	 */
	@Override
	public void onExpressionClick(int resId, String str) {
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
	public void onReceiveSuccess(String rec, final int id) {
	}

	/* 
	 * @see com.myxiaoapp.listener.OnResponseListener#onReceiveFailure(java.lang.String)
	 */
	@Override
	public void onReceiveFailure(String rec) {
	}
	
 
}
