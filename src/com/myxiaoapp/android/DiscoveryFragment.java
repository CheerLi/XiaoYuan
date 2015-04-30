package com.myxiaoapp.android;

import com.myxiaoapp.utils.Constant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class DiscoveryFragment extends Fragment implements OnClickListener {

	private RelativeLayout mMyCourseTableLayout;
	private RelativeLayout mCampusMoodLayout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_discovery, container,
				false);
		mMyCourseTableLayout = (RelativeLayout) view
				.findViewById(R.id.my_class_table_layout);
		mMyCourseTableLayout.setOnClickListener(this);
		mCampusMoodLayout = (RelativeLayout) view
				.findViewById(R.id.campus_mood_layout);
		mCampusMoodLayout.setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View v) {
		// Log.i("mydebug", "here");
		switch (v.getId()) {
		case R.id.my_class_table_layout:
			startActivity(new Intent(getActivity(), CourseTableActivity.class));
			break;
		case R.id.campus_mood_layout:
			Intent intent = new Intent(getActivity(), CampusNewsActivity.class);
			intent.setFlags(Constant.FLAG_Discovery);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
}
