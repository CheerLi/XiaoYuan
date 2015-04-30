package com.myxiaoapp.android;

import com.myxiaoapp.model.RegisterInfo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * 学校信息 学校所在省 学校所在市 学校名称 用户协议
 * 
 * @author liqihang
 * @date 2014-9-12
 */
public class RegisterSchoolActivity extends CommonActivity implements
		OnClickListener {

	private Button nextbtn_school;
	private Spinner province;
	private Spinner city;
	private Spinner school;
	private CheckBox contract;

	@SuppressLint("ShowToast")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_school);
		setActionBarTitle(R.string.register);
		province = (Spinner) findViewById(R.id.spinner_province);
		province.setSelection(1);
		city = (Spinner) findViewById(R.id.spinner_city);
		city.setSelection(1);
		school = (Spinner) findViewById(R.id.spinner_school);
		school.setSelection(1);
		contract = (CheckBox) findViewById(R.id.contract);
		contract.setChecked(true);// 默认签署用户协议
		showBackButton();
		mApp.addLaunchActivity(this);

		// 下一步：进入到个人详细信息设置部分
		nextbtn_school = (Button) findViewById(R.id.nextbtn);
		nextbtn_school.setOnClickListener(this);

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
		if (province.getSelectedItem().equals("请选择")) {
			Toast.makeText(RegisterSchoolActivity.this, "请选择省份",
					Toast.LENGTH_LONG).show();
			return false;
		} else if (city.getSelectedItem().equals("请 选择")) {
			Toast.makeText(RegisterSchoolActivity.this, "请选择城市",
					Toast.LENGTH_LONG).show();
			return false;
		} else if (school.getSelectedItem().equals("请选择")) {
			Toast.makeText(RegisterSchoolActivity.this, "请选择学校",
					Toast.LENGTH_LONG).show();
			return false;
		} else if (!contract.isChecked()) {
			Toast.makeText(RegisterSchoolActivity.this, "是否同意用户协议",
					Toast.LENGTH_LONG).show();
			return false;
		}
		return true;
	}

	@Override
	public void onClick(View v) {

		if (check()) {
			RegisterInfo.setSchoolCode(1);// 学校代码
			startActivity(new Intent(RegisterSchoolActivity.this,
					RegisterDetailActivity.class));
		}
	}

}
