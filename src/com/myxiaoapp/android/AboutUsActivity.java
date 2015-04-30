package com.myxiaoapp.android;

import android.os.Bundle;

public class AboutUsActivity extends CommonActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_us);
		setActionBarTitle(R.string.about_us);
	}
}
