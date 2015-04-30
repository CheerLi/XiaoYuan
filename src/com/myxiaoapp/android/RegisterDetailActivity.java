package com.myxiaoapp.android;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.myxiaoapp.listener.OnResponseListener;
import com.myxiaoapp.model.HttpRequestParams;
import com.myxiaoapp.model.RegisterInfo;
import com.myxiaoapp.model.UserBean;
import com.myxiaoapp.network.AsyncHttpPost;
import com.myxiaoapp.network.XYClient;
import com.myxiaoapp.utils.ACache;
import com.myxiaoapp.utils.Constant;
import com.myxiaoapp.utils.Constant.RequestId;
import com.myxiaoapp.utils.Constant.RequestUrl;
import com.myxiaoapp.view.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * 个人详细信息 头像 性别 昵称
 * 
 * @author liqihang
 * @time 2014-9-12
 */
public class RegisterDetailActivity extends CommonActivity implements
		OnClickListener, OnResponseListener {
	private static final String TAG = "mydebug";

	private Context context;

	private Button nextbtn;
	private CircleImageView photo_head;
	private String headPhotUri;
	private RadioGroup sex_group;
	private RadioButton sex_button;
	private EditText username;
	private EditText password;
	private EditText rePassword;

	private static final int REQUEST_CODE_SELECE_HEAD_PHOTO = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		context = this;
		setContentView(R.layout.activity_register_detail);
		setActionBarTitle(R.string.register);
		photo_head = (CircleImageView) findViewById(R.id.photo_head);
		photo_head.setOnClickListener(this);
		sex_group = (RadioGroup) findViewById(R.id.radio_group);
		username = (EditText) findViewById(R.id.username);
		username.setText("test");
		password = (EditText) findViewById(R.id.password);
		password.setText("123");
		rePassword = (EditText) findViewById(R.id.password_reset);
		rePassword.setText("123");
		showBackButton();

		mApp.addLaunchActivity(this);

		// 下一步：进入到获取验证码及设置密码
		nextbtn = (Button) findViewById(R.id.nextbtn);
		nextbtn.setOnClickListener(this);

		ImageLoaderConfiguration imgConfiguration = ImageLoaderConfiguration
				.createDefault(this);
		ImageLoader.getInstance().init(imgConfiguration);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private boolean check() {
		sex_button = (RadioButton) findViewById(sex_group
				.getCheckedRadioButtonId());
		sex_button = (RadioButton) findViewById(R.id.man);
		sex_button.setSelected(true);
		if (null == photo_head.getDrawable()) {
			Toast.makeText(RegisterDetailActivity.this, "请选择头像",
					Toast.LENGTH_LONG).show();
			return false;
		} else if (null == sex_button) {
			Toast.makeText(RegisterDetailActivity.this, "请选择性别",
					Toast.LENGTH_LONG).show();
			return false;
		} else if (username.getText().toString().equals("")) {
			Toast.makeText(RegisterDetailActivity.this, "请输入昵称",
					Toast.LENGTH_LONG).show();
			return false;
		} else if (password.getText().toString().equals("")
				|| rePassword.getText().toString().equals("")) {
			Toast.makeText(RegisterDetailActivity.this, "请输入密码",
					Toast.LENGTH_LONG).show();
			return false;
		} else if (!password.getText().toString()
				.equals(rePassword.getText().toString())) {
			Toast.makeText(RegisterDetailActivity.this, "两次密码不一样",
					Toast.LENGTH_LONG).show();
			return false;
		}
		return true;
		/*
		 * AsyncHttpClient client = SingleAsyncClient.getSingleClient();
		 * HttpResponseBody checkRegisterRespone = new HttpResponseBody();
		 * client.post("http://120.24.76.148/yaf/index.php/CheckPhoneNum",
		 * HttpRequestParams.checkRegisterParams() , checkRegisterRespone); int
		 * statusCode = checkRegisterRespone.getStatusCode(); if(20 ==
		 * statusCode ){ //手机号可以注册 return true; }else if(43 == statusCode){
		 * //昵称不合法（已存在） Toast.makeText(RegisterDetailActivity.this,
		 * "昵称已存在，请换一个", Toast.LENGTH_LONG).show(); return false; }else if(45 ==
		 * statusCode){//手机号为空号 Toast.makeText(RegisterDetailActivity.this,
		 * "手机号不存在，请重新输入", Toast.LENGTH_LONG).show(); return false;
		 * 
		 * } Toast.makeText(RegisterDetailActivity.this, "网络异常，请稍等...",
		 * Toast.LENGTH_LONG).show(); return false;
		 */}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.nextbtn:
			if (check()) {
				// String picturePath =
				// Environment.getExternalStorageDirectory()
				// + "/test.jpg";
				// //头像照片路径
				int sex = RegisterInfo.getSex(); // 性别x
				String usr = username.getText().toString();// 用户名
				String pwd = password.getText().toString(); // 密码
				RegisterInfo.setDetail(headPhotUri, sex,RegisterInfo.getPhone(), pwd, usr);// 把注册信息加载到静态注册信息类中

				//new AsyncHttpPost("Register", this).post();
				new XYClient().post(
						RequestId.ID_REGISTER, 
						RequestUrl.URL_REGISTER, 
						HttpRequestParams.registerParams(), 
						this);
				// AsyncHttpClient client = new AsyncHttpClient();
				// final HttpResponseHandler responseHandler = new
				// HttpResponseHandler();
				// client.post("http://120.24.76.148/yaf/index.php/register",
				// HttpRequestParams.registerParams(), responseHandler);
				// responseHandler.setOnResponseListener(new
				// OnResponseListener() {
				//
				// @Override
				// public void onFailure() {
				// Toast.makeText(RegisterDetailActivity.this, "注册失败",
				// Toast.LENGTH_LONG).show();
				// }
				//
				// @Override
				// public void onReceive() {
				// startActivity(new Intent(RegisterDetailActivity.this,
				// RegisterSuccessActivity.class));
				// finish();
				// }
				//
				// });

			}
			break;

		case R.id.photo_head:

			Intent intent = new Intent(context, SelectPicGroupActivity.class);
			intent.putExtra("max_select", 1);
			startActivityForResult(intent, REQUEST_CODE_SELECE_HEAD_PHOTO);

			break;
		default:
			break;

		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE_SELECE_HEAD_PHOTO
				&& resultCode == RESULT_OK) {
			ArrayList<CharSequence> images = data
					.getCharSequenceArrayListExtra("images");
			String imageUri = images.get(0).toString();
			Bitmap bitmap = ImageLoader.getInstance().loadImageSync(imageUri);
			headPhotUri = imageUri.substring("file:///".length(),
					imageUri.length());
			photo_head.setImageBitmap(bitmap);
		}
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
	public void onReceiveSuccess(String rec, final int ID) {
		Log.d(TAG, "rec="+rec);
		Gson gson = new Gson();
		UserBean userBean = null;
		try {
			userBean = gson.fromJson(new JSONObject(rec).getJSONObject("data").toString(), UserBean.class);
			registerSuccess(userBean);
			saveUserInfo(userBean);
		} catch (JsonSyntaxException | JSONException e) {
			
			e.printStackTrace();
		}
			
		startActivity(new Intent(this, RegisterSuccessActivity.class));
		finish();
	}
	private void registerSuccess(UserBean userBean) {
		Log.d(TAG, userBean.toString());
		ACache loginCache = ACache.get(this);
		loginCache.put("login", "1");
		loginCache.put("userBean", userBean); 
		startMainUIActivity();
	}
	private void startMainUIActivity(){

		startActivity(new Intent(this, MainUIActivity.class));
		finish();
	}
	private void saveUserInfo(UserBean userBean){
		mApp.setLoginUser(userBean);
		mApp.saveInfo(userBean, Constant.SHARE_PRE_LOGIN_INFO);
	}
	/*
	 * @see
	 * com.myxiaoapp.listener.OnResponseListener#onReceiveFailure(java.lang.
	 * String)
	 */
	@Override
	public void onReceiveFailure(String rec) {
		Toast.makeText(this, "注册失败", Toast.LENGTH_LONG).show();
	}

}
