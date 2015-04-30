package com.myxiaoapp.android;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.myxiaoapp.listener.OnResponseListener;
import com.myxiaoapp.model.HttpRequestParams;
import com.myxiaoapp.model.HttpResponseHandler;
import com.myxiaoapp.model.RegisterInfo;
import com.myxiaoapp.network.AsyncHttpPost;
import com.myxiaoapp.network.SingleAsyncClient;
import com.myxiaoapp.network.XYClient;
import com.myxiaoapp.utils.Constant.RequestId;
import com.myxiaoapp.utils.Constant.RequestUrl;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 获取手机身份验证 输入手机号 获取手机验证码（60秒没有收到验证码,获取失败，可重新获取）
 * 
 * 进入下一步
 */
public class RegisterVerifyActivity extends CommonActivity implements
		OnClickListener, OnResponseListener {

	private Button registerNext;
	private EditText phoneNumber;
	//private final String url = "http://120.24.76.148/yaf/index.php/Checkphonenum";
	private String phone;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_register_phone_verify);
		setActionBarTitle(R.string.title_activity_register_verify);
		showBackButton();
		init();
	}

	private void init() {
		mApp.addLaunchActivity(this);
		phoneNumber = (EditText) findViewById(R.id.phonenumber_input);
		registerNext = (Button) findViewById(R.id.register_next);
		registerNext.setOnClickListener(this);

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

	// 获取验证码
	private boolean check(String phone) {
		if (phone.equals("")) {
			Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	@Override
	public void onClick(View v) {

		phone = phoneNumber.getText().toString();
		if (check(phone)) {
			//new AsyncHttpPost("Checkphonenum", this,phone).post();
			new XYClient().post(
					RequestId.ID_CHECK_PHONE_NUM,  
					RequestUrl.URL_CHECK_PHONE_NUM, 
					HttpRequestParams.checkPhoneParams(phone), 
					this);
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
		RegisterInfo.setPhone(phone);
		Intent intent = new Intent(RegisterVerifyActivity.this,
				RegisterInputVerifyActivity.class);
		intent.putExtra("phone", phone);
		startActivity(intent);
		finish();
	}

	/* 
	 * @see com.myxiaoapp.listener.OnResponseListener#onReceiveFailure(java.lang.String)
	 */
	@Override
	public void onReceiveFailure(String rec) {
		Toast.makeText(RegisterVerifyActivity.this, "手机已注册或格式错误",
				Toast.LENGTH_SHORT).show();
	}

}
