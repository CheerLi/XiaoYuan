package com.myxiaoapp.android;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.myxiaoapp.listener.OnResponseListener;
import com.myxiaoapp.model.HttpRequestParams;
import com.myxiaoapp.model.UserBean;
import com.myxiaoapp.network.AsyncHttpPost;
import com.myxiaoapp.network.XYClient;
import com.myxiaoapp.utils.ACache;
import com.myxiaoapp.utils.Constant;
import com.myxiaoapp.utils.Constant.RequestUrl;
import com.myxiaoapp.utils.JSONHelper;
import com.myxiaoapp.utils.LocationHelper;
import com.myxiaoapp.utils.Constant.RequestId;
import com.myxiaoapp.utils.LocationHelper.GetLocationListener;

/**
 * 登录
 * 
 * @author liqihang
 * @date 2014-9-7
 * 
 * 
 * @author liqihang
 * @date 2014-10-15
 * @modify 优化监听代码
 */

public class LoginActivity extends BaseActivity implements OnClickListener,
		OnTouchListener, OnResponseListener {

	private static final String TAG = "mydebug";
	private static final int WHAT_LOGIN_FAILURE = 2;
	private static final int WHAT_LOGIN_SUCCESS = 1;
	private TextView forgetPassword;
	private XiaoYuanApp mApp;
	private Context mContext;
//	private LinearLayout mInputLayout;
	private Button mLoginSubmit;
	private ScrollView mScrollView;
	private EditText password;
	private TextView register;
	private EditText username;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 接口测试
		try {
			interfaceTest();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		setContentView(R.layout.activity_login);
		getSupportActionBar().hide();

		mContext = this;
		mApp = (XiaoYuanApp) getApplication();
//		mInputLayout = (LinearLayout) findViewById(R.id.inputLayout);

		mScrollView = (ScrollView) findViewById(R.id.scrollview);

		username = (EditText) findViewById(R.id.username);
		username.setOnTouchListener(this);
	//	username.setText("admin");// 测试账号

		password = (EditText) findViewById(R.id.password);
		password.setOnTouchListener(this);
	//	password.setText("admin");// 测试密码

		register = (TextView) findViewById(R.id.register);
		register.setOnClickListener(this);

		forgetPassword = (TextView) findViewById(R.id.forget_password);
		forgetPassword.setOnClickListener(this);

		mLoginSubmit = (Button) findViewById(R.id.submit);
		mLoginSubmit.setOnClickListener(this);

		mApp.addLaunchActivity(this);

		if(login()){
			startMainUIActivity();
		}
	}

	private void interfaceTest() throws UnsupportedEncodingException,
			FileNotFoundException {

		// 1.校园圈信息获取
		// CampusCircle hp = new CampusCircle(this, "10001", "xiaoyuan", "16",
		// "1", "1");
		// hp.post();

		// // 2.取消关注
		// CancelFocus cancelFocus = new CancelFocus(this, "10001", "10002");
		// cancelFocus.post();
		//
		// // 3.删除评论
		// DelComment delComment = new DelComment(this, "1", "10001");
		// delComment.post();
		//
		// // 4.删除校园圈信息
		// DelMsg delMsg = new DelMsg(this, "10001", "1");
		// delMsg.post();

		// 5.粉丝列表
		// FansList fansList = new FansList(this, "10001", "10001", "20");
		// fansList.post();

		// 6.关注朋友
	/*	// FocusFriends focusFriends = new FocusFriends(this, "10001", "10002");
		// focusFriends.post();

		// 7.关注列表
		// FocusList focusList = new FocusList(this,"10001","10001", "20");
		// focusList.post();

		// 8.获取用户信息
		// GetUserInfo getUserInfo = new GetUserInfo(this, "10001", "10001");
		// getUserInfo.post();

		// 9.备注好友
		// RemarkFriends remarkFriends = new RemarkFriends(this, "10001",
		// "10002", "小红");
		// remarkFriends.post();

		// 10.回复功能
		// Reply reply = new Reply(mContext, "16", "回复功能 测试","10001", "10002");
		// reply.post();

		// 11.屏蔽某人
		// ShiledFriends shiledFriends = new ShiledFriends(this, "10001",
		// "10002");
		// shiledFriends.post();
*/
		// 12.回传更新地理位置
/*		LocationHelper locationHelper = new LocationHelper(this);
		locationHelper.getLocation(new GetLocationListener() {

			@Override
			public void location(double latitude, double longitude) {
				new AsyncHttpPost("Backstate", LoginActivity.this,XiaoYuanApp.getLoginUser(LoginActivity.this).userBean.getUid(), latitude + "", longitude+ "").post();
			}
		});
*/		 
		// 13.上传校园圈信息
		// String[] path = new String[2];
		// path[0]=Environment.getExternalStorageDirectory()+"/test.jpg";
		// path[1]=Environment.getExternalStorageDirectory()+"/test.jpg";
		// UploadMsg uploadMsg = new UploadMsg(this, "10001", "测试上传校园圈信息接口", 2,
		// path);
		// uploadMsg.post();

		// 用户相册
		// UserAlbum userAlbum = new UserAlbum(this, "10001", "10001", 8);
	}

	/*
	 * 返回状态码 如200表示连接成功
	 */
	private boolean check() {
		if (TextUtils.isEmpty(username.getText())) {
			Toast.makeText(this, "账号不能为空", Toast.LENGTH_SHORT).show();
			return false;
		} else if (TextUtils.isEmpty(password.getText())) {
			Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	protected void loginSubmit() {
		if(check()) {
//			new AsyncHttpPost("login", this,
//					this.username.getText().toString(), this.password.getText()
//							.toString()).post();
			new XYClient().post(
					RequestId.ID_LOGIN, 
					RequestUrl.URL_LOGIN, 
					HttpRequestParams.loginParams(this.username.getText().toString(), this.password.getText().toString()), 
					this);
		}
	}
	private void startMainUIActivity(){

		startActivity(new Intent(LoginActivity.this, MainUIActivity.class));
		finish();
	}
	private boolean login(){
		ACache loginCache = ACache.get(this);
		String login = loginCache.getAsString("login");
		if(login != null && login.equals("1")){
			UserBean userBean = (UserBean) loginCache.getAsObject("userBean");
			saveUserInfo(userBean);
			return true;
		}else {
			return false;
		}
	}
	private void saveUserInfo(UserBean userBean){
		mApp.setLoginUser(userBean);
		mApp.saveInfo(userBean, Constant.SHARE_PRE_LOGIN_INFO);
	}
	private void loginSuccess(UserBean userBean) {
		Log.d(TAG, userBean.toString());
		ACache loginCache = ACache.get(this);
		loginCache.put("login", "1");
		loginCache.put("userBean", userBean);
		saveUserInfo(userBean);
		startMainUIActivity();
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.register:
			/*
			 * startActivity(new Intent(LoginActivity.this,
			 * VerifyActivity.class));//调到校园认证部分，供测试 break;
			 */startActivity(new Intent(LoginActivity.this,
					RegisterVerifyActivity.class));
			break;
		case R.id.forget_password:
			
			startActivity(new Intent(LoginActivity.this,
					ForgetPWDVerifyActivity.class));
			break;
		case R.id.submit:
			mLoginSubmit.setText("登录中...");
			loginSubmit();
			break;
		default:
			break;
		}

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		v.performClick();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (v.getId() == username.getId())
				username.requestFocus();
			else if (v.getId() == password.getId())
				password.requestFocus();
			break;
	/*	case MotionEvent.ACTION_UP:
			mScrollView.postDelayed(new Runnable() {

				@Override
				public void run() {
					mScrollView.smoothScrollTo(0, mInputLayout.getTop() - 20);
				}
			}, 200);
			break;
	*/	default:
			return false;
		}
		return false;
	}

	/*
	 * @see com.myxiaoapp.listener.OnResponseListener#onFailure(int)
	 */
	@Override
	public void onFailure(int statusCode) {
	}

	/*
	 * @see
	 * com.myxiaoapp.listener.OnResponseListener#onReceiveSuccess(java.lang.
	 * String)
	 */
	@Override
	public void onReceiveSuccess(String rec,final int ID) {
		switch(ID){
		case RequestId.ID_LOGIN:
			Gson gson = new Gson();
			UserBean userBean = null;
			try {
				userBean = gson.fromJson(new JSONObject(rec).getJSONObject("data").toString(), UserBean.class);
			} catch (JsonSyntaxException | JSONException e) {
				
				e.printStackTrace();
			}
			if(userBean != null)
				loginSuccess(userBean);
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
	}

}
