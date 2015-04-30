package com.myxiaoapp.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.myxiaoapp.listener.OnResponseListener;
import com.myxiaoapp.model.HttpRequestParams;
import com.myxiaoapp.model.HttpResponseHandler;
import com.myxiaoapp.network.AsyncHttpPost;
import com.myxiaoapp.network.XYClient;
import com.myxiaoapp.utils.Constant.RequestId;
import com.myxiaoapp.utils.Constant.RequestUrl;

public class ForgetPWDSetPWDActivity extends CommonActivity implements
		OnClickListener, OnResponseListener {
	private ActionBar mActionBar;
	private TextView phone_number;
	private TextView password;
	private TextView re_password;
	private Button mButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgetpwd_pwd_set);
		showBackButton();
		setActionBarTitle(R.string.resetpwd_actionbar_title);
		init();

	}

	private void init() {
		mActionBar = getSupportActionBar();
		phone_number = (TextView) findViewById(R.id.phone_number);
		Intent mIntent = getIntent();
		String phoneNumber = mIntent.getStringExtra("phoneNumber");
		phone_number.setText(phoneNumber);
		password = (TextView) findViewById(R.id.password);
		re_password = (TextView) findViewById(R.id.re_password);
		mButton = (Button) findViewById(R.id.forgetpwd_complete);
		mButton.setOnClickListener(this);
	}

	private boolean check() {
		if (null == password.getText()) {
			Toast.makeText(this, "请输入密码", Toast.LENGTH_LONG).show();
			return false;
		} else if (null == re_password.getText()) {
			Toast.makeText(this, "请再次输入密码", Toast.LENGTH_LONG).show();
			return false;
		} else if (re_password.getText().toString()
				.equals(password.getText().toString())) {
			Toast.makeText(this, "两次密码不一样", Toast.LENGTH_LONG).show();
			return false;
		}
		return true;
	}

	@Override
	public void onClick(View v) {
		mButton.setText("开始重设...");
		if (check()) {
			AsyncHttpClient client = new AsyncHttpClient();
			Intent mIntent = getIntent();
			String phoneNumber = mIntent.getStringExtra("phoneNumber");
			String password = this.password.getText().toString();
			//new AsyncHttpPost("forgetPassword",this,phoneNumber, password).post();
			new XYClient().post(
					RequestId.ID_UPDATE_PASSWORD, 
					RequestUrl.URL_UPDATE_PASSWORD, 
					HttpRequestParams.setPasswordParams(phoneNumber, password), 
					this);
			mButton.setText("确认");
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
		Toast.makeText(ForgetPWDSetPWDActivity.this, "请用新密码登录",
				Toast.LENGTH_LONG).show(); 
		startActivity(new Intent(ForgetPWDSetPWDActivity.this,
				LoginActivity.class));
		finish();
	}

	/* 
	 * @see com.myxiaoapp.listener.OnResponseListener#onReceiveFailure(java.lang.String)
	 */
	@Override
	public void onReceiveFailure(String rec) {
	}
}
