package com.myxiaoapp.android;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.myxiaoapp.utils.Utils;

public class CourseTableActivity extends CommonActivity implements
		OnItemSelectedListener {

	private TableLayout mCourseTable;
	private SpinnerAdapter mSpinnerAdapter;
	private Spinner mWeekSpinner;
	private String[] mWeekList;

	private static final int[] colsId = { R.id.col_1, R.id.col_2, R.id.col_3,
			R.id.col_4, R.id.col_5, R.id.col_6, R.id.col_7 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course_table);
		setActionBarTitle("我的课表");

		init();
		createDropDownNavigation();
	}

	private void init() {
		mCourseTable = (TableLayout) findViewById(R.id.course_table);
		for (int i = 0; i < 6; i++) {
			View view = LayoutInflater.from(this).inflate(
					R.layout.course_table_row, null);
			for (int j = 0; j < 7; j++) {
				TextView tv = (TextView) view.findViewById(colsId[j]);
				tv.getLayoutParams().height = Utils.dpChangePix(this,
						40 * 12 / 6);
				if (i % 2 == 0) {
					if (j % 2 == 0)
						tv.setBackgroundColor(Color.WHITE);
					else
						tv.setBackgroundColor(Color.GRAY);
				} else {
					if (j % 2 == 0)
						tv.setBackgroundColor(Color.GRAY);
					else
						tv.setBackgroundColor(Color.WHITE);
				}

			}

			mCourseTable.addView(view, i);
		}
	}

	private void createDropDownNavigation() {
		mWeekSpinner = (Spinner) LayoutInflater.from(this).inflate(
				R.layout.week_drop_down_spinner, null);
		mSpinnerAdapter = ArrayAdapter.createFromResource(this,
				R.array.week_list,
				android.R.layout.simple_spinner_dropdown_item);
		mWeekSpinner.setAdapter(mSpinnerAdapter);
		mWeekSpinner.setOnItemSelectedListener(this);
		// addCustomView(mWeekSpinner);
		mWeekList = getResources().getStringArray(R.array.week_list);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		((TextView) view).setTextColor(Color.WHITE);
		Toast.makeText(this, mWeekList[position], Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {

	}

}
