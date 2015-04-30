package com.myxiaoapp.android;

import org.apache.http.Header;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.myxiaoapp.model.HttpRequestParams;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ForgetPWDInputVerifyNumber extends CommonActivity implements
		OnClickListener {
	private ActionBar mActionBar;
	private TextView phone_number;
	private TextView verify_number;
	private Button mButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgetpwd_input_verifynumber);
		setActionBarTitle(R.string.resetpwd_tip);
		showBackButton();
		init();
	}

	private void init() {
		mActionBar = getSupportActionBar();
		verify_number = (TextView) findViewById(R.id.verify_number);
		phone_number = (TextView) findViewById(R.id.phone_number);
		Intent mIntent = getIntent();
		String phoneNumber = mIntent.getStringExtra("phoneNumber");
		phone_number.setText(phoneNumber);
		mButton = (Button) findViewById(R.id.resetpwd_button);
		mButton.setOnClickListener(this);

	}

	private boolean check() {
		// 检查验证码是否正确
		return true;
	}

	@Override
	public void onClick(View v) {
		if (check()) {
			AsyncHttpClient client = new AsyncHttpClient();
			Intent mIntent = getIntent();
			mIntent.setClass(this, ForgetPWDSetPWDActivity.class);
			startActivity(mIntent);
			finish();
		}
	}

}
