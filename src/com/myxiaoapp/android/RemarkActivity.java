package com.myxiaoapp.android;

import android.os.Bundle;

public class RemarkActivity extends CommonActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_remark);
		setActionBarTitle(R.string.remark);
		showBackButton();
	}

}
