package com.myxiaoapp.android;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class FeedbackActivity extends CommonActivity {

	private EditText mFeedback;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);
		setActionBarTitle(R.string.feedback);
		mFeedback = (EditText) findViewById(R.id.et_feedback);
	}

}
