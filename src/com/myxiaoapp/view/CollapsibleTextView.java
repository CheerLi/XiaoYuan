package com.myxiaoapp.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.myxiaoapp.android.R;
import com.myxiaoapp.utils.Utils;

/**
 * @author JiangZhenJie
 * 
 */
public class CollapsibleTextView extends LinearLayout {

	private static final String TAG = "mydebug";

	private TextView mRealTextView;
	private TextView mCollapseText;

	private int mCollapseLine = 6;
	private int mMaxLines;
	private boolean isCollapsed = false;

	public CollapsibleTextView(Context context) {
		super(context);
		init(context);
	}

	public CollapsibleTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public CollapsibleTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
		setOrientation(LinearLayout.VERTICAL);
		mRealTextView = new TextView(context);
		mCollapseText = new TextView(context);
		mRealTextView.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		mCollapseText.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		mCollapseText.setVisibility(View.GONE);
		mCollapseText.setTextColor(Color.BLUE);
		mCollapseText.setBackground(getResources().getDrawable(
				R.drawable.bg_collapse_text));
		mCollapseText.setClickable(true);
		mCollapseText.setOnClickListener(mClickListener);
		int padding = Utils.dpChangePix(context, 2);
		mCollapseText.setPadding(padding * 2, padding, padding * 2, padding);
		addView(mRealTextView);
		addView(mCollapseText);
	}

	public void setText(final CharSequence text) {
		mRealTextView.setText(text);
		mRealTextView.post(new Runnable() {
			@Override
			public void run() {
				mMaxLines = mRealTextView.getLineCount();
				if (mMaxLines > mCollapseLine) {
					mRealTextView.setMaxLines(mCollapseLine);
					mCollapseText.setVisibility(View.VISIBLE);
					mCollapseText.setText("展开");
				}
			}
		});
	}

	public void setText(int resId) {
		String text = getResources().getString(resId);
		setText(text);
	}

	public void setCollapseLine(int line) {
		mCollapseLine = line;
	}

	public void setTextColor(int color) {
		mRealTextView.setTextColor(color);
	}

	private OnClickListener mClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (v == mCollapseText) {
				if (isCollapsed) {
					mRealTextView.setMaxLines(mCollapseLine);
					mCollapseText.setText("展开");
					isCollapsed = false;
				} else {
					mRealTextView.setMaxLines(mMaxLines);
					mCollapseText.setText("收起");
					isCollapsed = true;
				}
			}
		}
	};
}
