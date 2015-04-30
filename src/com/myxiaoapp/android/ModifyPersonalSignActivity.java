package com.myxiaoapp.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

/**
 * 修改个性签名
 * 
 * @author JiangZhenJie
 * @date 2014-9-28
 */
public class ModifyPersonalSignActivity extends CommonActivity {

	private static final int MODIFY_NICKNAME = 1;
	private static final int MODIFY_SCHOOL = 2;
	private static final int MODIFY_PERSONAL_SIGN = 3;
	int flag ;
	private EditText modify;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modify_personal_sign);
		modify = (EditText)findViewById(R.id.modify);
		Intent intent = getIntent();
		flag = intent.getFlags();
		switch(flag){
		case MODIFY_NICKNAME:
			setActionBarTitle("修改昵称");
			break;
		case MODIFY_SCHOOL:
			setActionBarTitle("修改学校");
			break;
		case MODIFY_PERSONAL_SIGN:
			setActionBarTitle("修改个性签名");
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.modify_personal_sign, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_submit) {
			Intent intent = new Intent();
			intent.putExtra("modify", modify.getText().toString());
			setResult(flag, intent);
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
