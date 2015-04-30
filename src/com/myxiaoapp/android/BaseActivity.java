package com.myxiaoapp.android;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/**
 * 所有Activity的基类
 * 
 * @author JiangZhenJie
 * @date 2014-9-7
 */
public class BaseActivity extends ActionBarActivity {

	public XiaoYuanApp mApp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mApp = (XiaoYuanApp) getApplication();
	}
}
