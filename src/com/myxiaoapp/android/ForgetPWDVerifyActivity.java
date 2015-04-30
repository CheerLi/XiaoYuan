package com.myxiaoapp.android;

import com.loopj.android.http.AsyncHttpClient;
import com.myxiaoapp.listener.OnResponseListener;
import com.myxiaoapp.model.HttpRequestParams;
import com.myxiaoapp.model.HttpResponseHandler;
import com.myxiaoapp.network.AsyncHttpPost;
import com.myxiaoapp.network.XYClient;
import com.myxiaoapp.utils.Constant.RequestId;
import com.myxiaoapp.utils.Constant.RequestUrl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 找回密码 输入手机号码
 * 
 * @author liqihang
 * @time 2014-9-12
 * 
 */
public class ForgetPWDVerifyActivity extends CommonActivity implements
		OnClickListener, OnResponseListener {
	private Button nextbtn_reset_password;
	private EditText phonenumber_input;
	private String phoneNumber;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgetpwd_phone_verify);
		setActionBarTitle(R.string.resetpwd_tip);
		showBackButton();
		setActionBarTitle(R.string.forgetpwd_actionbar_title);
		init();
	}

	private void init() {
		phonenumber_input = (EditText) findViewById(R.id.phonenumber_input);
		nextbtn_reset_password = (Button) findViewById(R.id.forgetpwd_next);
		nextbtn_reset_password.setOnClickListener(this);

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
		phoneNumber = phonenumber_input.getText().toString();
		if (phoneNumber.equals("")) {
			Toast.makeText(ForgetPWDVerifyActivity.this, "请输入手机号码",
					Toast.LENGTH_LONG).show();
			return false;
		}
		return true;
	}

	@Override
	public void onClick(View v) {
		if (check()) {
			final String phoneNumber = phonenumber_input.getText().toString();
			//new AsyncHttpPost("Checkphonenum", this, phoneNumber).post();
			new XYClient().post(
					RequestId.ID_CHECK_PHONE_NUM, 
					RequestUrl.URL_CHECK_PHONE_NUM, 
					HttpRequestParams.checkPhoneParams(phoneNumber), 
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
		Intent mIntent = new Intent(ForgetPWDVerifyActivity.this,
				ForgetPWDInputVerifyNumber.class);
		mIntent.putExtra("phoneNumber", phoneNumber);
		startActivity(mIntent);
		finish();
	}

	/* 
	 * @see com.myxiaoapp.listener.OnResponseListener#onReceiveFailure(java.lang.String)
	 */
	@Override
	public void onReceiveFailure(String rec) {
		Toast.makeText(this, "手机未注册或格式错误", Toast.LENGTH_SHORT).show();
	}
}
