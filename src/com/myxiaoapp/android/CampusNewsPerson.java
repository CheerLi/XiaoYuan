/**
 * 2014年11月12日
 * ken
 */
package com.myxiaoapp.android;

import com.myxiaoapp.adapter.CampusNewsAdapter;
import com.myxiaoapp.utils.Constant;
import com.myxiaoapp.utils.Utils;
import com.pulltorefresh.library.PullToRefreshBase;
import com.pulltorefresh.library.PullToRefreshListView;
import com.pulltorefresh.library.PullToRefreshBase.Mode;
import com.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.widget.ListView;

/**
 * @author ken
 * 
 */
public class CampusNewsPerson extends CommonActivity {

	private Context mContext;
	private ListView mListView;
	private PullToRefreshListView mPullToRefreshListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_campus_person);
		setActionBarTitle("Angelababy");
		mContext = this;
		init();
	}

	private void init() {
		showBackButton();
		mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.campus_news_list);
		mPullToRefreshListView.setMode(Mode.BOTH);
		mPullToRefreshListView.setOnRefreshListener(refreshListener);
		mListView = mPullToRefreshListView.getRefreshableView();
		mListView.setAdapter(new CampusNewsAdapter(this,Constant.FLAG_Discovery));
	}

	private OnRefreshListener<ListView> refreshListener = new OnRefreshListener<ListView>() {

		@Override
		public void onRefresh(PullToRefreshBase<ListView> refreshView) {
			if (mPullToRefreshListView.isHeaderShown()) { // 上拉
				Utils.showProgressDialog(mContext, R.string.refreshing, true,
						cancelListener);
			} else if (mPullToRefreshListView.isFooterShown()) { // 下拉
				Utils.showProgressDialog(mContext, R.string.loading, true,
						cancelListener);
			}
		}
	};
	private OnCancelListener cancelListener = new OnCancelListener() {

		@Override
		public void onCancel(DialogInterface dialog) {
			mPullToRefreshListView.onRefreshComplete();
		}
	};
}
