package com.myxiaoapp.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.myxiaoapp.model.RegisterInfo;

/**
 * 注册成功页面 开始体验 进行校内验证
 * 
 * @author liqihang
 * @time 2014/9/12
 */
public class RegisterSuccessActivity extends CommonActivity implements
		OnClickListener {
	private Button startPlay;
	private Button verifySchool;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_register_success);
		setActionBarTitle(R.string.register);
		showBackButton();

		mApp.addLaunchActivity(this);

		startPlay = (Button) findViewById(R.id.start_play);
		startPlay.setOnClickListener(this);

		verifySchool = (Button) findViewById(R.id.verify_school);
		verifySchool.setOnClickListener(this);

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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.start_play:
			RegisterInfo.recycle(); // 回收RegisterInfo类保存的注册信息
			startActivity(new Intent(RegisterSuccessActivity.this,
					MainUIActivity.class));
			break;
		case R.id.verify_school:
			startActivity(new Intent(RegisterSuccessActivity.this,
					VerifyActivity.class));
			break;
		default:
			break;
		}

	}
}
