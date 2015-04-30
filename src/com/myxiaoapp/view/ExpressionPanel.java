package com.myxiaoapp.view;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.myxiaoapp.android.R;
import com.myxiaoapp.utils.Constant;
import com.myxiaoapp.utils.Utils;

public class ExpressionPanel extends LinearLayout {

	private static final String TAG = "mydebug";

	private Context context;

	private int[] expressionIds;
	private int numColumns = 0;
	private int numRows = 0;
	private int viewPagerHeight = 0;
	private int indicatorHeight = 0;
	private int pageCount = 0;

	private ViewPager viewPager;
	private LinearLayout indicatorLayout;
	private CirclePageIndicator indicator;

	private OnExpressionClickListener clickListener;

	public ExpressionPanel(Context context) {
		super(context);
		this.context = context;
		init();
	}

	public ExpressionPanel(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}

	private void init() {
		numColumns = getNumColumns();
		numRows = getNumRows();
		pageCount = Constant.EXPRESSION_NUMBER / (numColumns * numRows) + 1;
		int padding = Utils.dpChangePix(context, 5);
		this.setPadding(padding, padding, padding, padding);
		expressionIds = new int[Constant.EXPRESSION_NUMBER];
		for (int i = 0, id = Constant.START_ID; i < Constant.EXPRESSION_NUMBER; i++) {
			expressionIds[i] = id;
			id += 1;
		}
		this.setOrientation(VERTICAL);
		this.setBackgroundResource(R.color.bg_expression_panel);
		viewPager = new ViewPager(context);
		viewPager.setId(R.id.vp_expression_page);
		viewPager.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				viewPagerHeight));
		addView(viewPager);
		indicatorLayout = new LinearLayout(context);
		indicatorLayout.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, indicatorHeight));
		indicatorLayout.setOrientation(HORIZONTAL);
		indicatorLayout.setGravity(Gravity.CENTER);
		addView(indicatorLayout);
	}

	public void startWork(FragmentManager fm) {
		List<Fragment> fragments = new LinkedList<Fragment>();
		for (int i = 0; i < pageCount; i++) {
			ExpressionFragment fragment = new ExpressionFragment();
			Bundle args = new Bundle();
			args.putInt("page", i);
			fragment.setArguments(args);
			fragments.add(fragment);
		}
		ExpressionPagerAdapter adapter = new ExpressionPagerAdapter(fm,
				fragments);
		viewPager.setAdapter(adapter);
		indicator = new CirclePageIndicator(context);
		indicator.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		int padding = Utils.dpChangePix(context, 2);
		indicator.setPadding(padding, padding, padding, padding);
		indicator.setPageColor(getResources().getColor(
				R.color.indicator_page_color));
		indicator.setFillColor(getResources().getColor(
				R.color.indicator_fill_color));
		indicator.setViewPager(viewPager);
		indicatorLayout.addView(indicator);
	}

	public void setOnExpressionClickListener(
			OnExpressionClickListener clickListener) {
		this.clickListener = clickListener;
	}

	private int getNumColumns() {
		WindowManager manager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics metrics = new DisplayMetrics();
		manager.getDefaultDisplay().getMetrics(metrics);
		int width = metrics.widthPixels;
		Drawable drawable = getResources().getDrawable(Constant.START_ID);
		int ret = width / drawable.getIntrinsicWidth();
		return ret - 2;
	}

	private int getNumRows() {
		WindowManager manager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics metrics = new DisplayMetrics();
		manager.getDefaultDisplay().getMetrics(metrics);
		int height = metrics.heightPixels / 3;
		Drawable drawable = getResources().getDrawable(Constant.START_ID);
		int rowHeight = drawable.getIntrinsicHeight();
		int ret = height / rowHeight;
		indicatorHeight = rowHeight;
		viewPagerHeight = height - rowHeight;
		return ret - 1;
	}

	private class ExpressionPagerAdapter extends FragmentPagerAdapter {

		private List<Fragment> fragments;

		public ExpressionPagerAdapter(FragmentManager fm,
				List<Fragment> fragments) {
			super(fm);
			this.fragments = fragments;
		}

		@Override
		public Fragment getItem(int position) {
			return fragments.get(position);
		}

		@Override
		public int getCount() {
			return fragments.size();
		}
	}

	private class ExpressionFragment extends Fragment {

		private GridView gridview;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View view = inflate(getActivity(),
					R.layout.expression_panel_fragment, null);
			int page = getArguments().getInt("page");
			gridview = (GridView) view.findViewById(R.id.gv_expression);
			gridview.setNumColumns(numColumns);
			gridview.setAdapter(new GridViewAdapter(page));
			gridview.setOnItemClickListener(listener);
			return view;
		}

		OnItemClickListener listener = new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				int resId = (int) id;
				String str = Utils.getFaceStr(resId);
				clickListener.onExpressionClick(resId, str);
			}
		};
	}

	private class GridViewAdapter extends BaseAdapter {

		private List<Integer> resIds;
		private int count = 0;

		public GridViewAdapter(int page) {
			count = numRows * numColumns;
			resIds = new LinkedList<Integer>();
			for (int i = 0, j = page * count + Constant.START_ID; i < count
					&& j < Constant.EXPRESSION_NUMBER + Constant.START_ID; i++, j++) {
				resIds.add(Integer.valueOf(j));
			}
		}

		@Override
		public int getCount() {
			return resIds.size();
		}

		@Override
		public Object getItem(int position) {
			Drawable drawable = getResources()
					.getDrawable(resIds.get(position));
			return drawable;
		}

		@Override
		public long getItemId(int position) {
			return resIds.get(position);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView = new ImageView(context);
			Drawable drawable = (Drawable) getItem(position);
			imageView.setImageDrawable(drawable);
			return imageView;
		}
	}

	public static interface OnExpressionClickListener {
		public void onExpressionClick(int resId, String str);
	}

	@Override
	public void setVisibility(int visibility) {
		super.setVisibility(visibility);
		viewPager.setCurrentItem(0);
	}
}
