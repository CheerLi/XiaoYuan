package com.myxiaoapp.android;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.PopupMenu.OnMenuItemClickListener;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * @author JiangZhenJie
 * @date 2014-9-26
 */
public abstract class CommonActivity extends BaseActivity {

	private ImageButton mActionBarLeftButton;
	private TextView mActionBarTitle;
	private ImageButton mActionBarRightButton;
	private TextView mActionBarRightText;

	private ActionBar mActionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = LayoutInflater.from(this).inflate(
				R.layout.common_title_bar, null);
		mActionBar = getSupportActionBar();
		mActionBarLeftButton = (ImageButton) view.findViewById(R.id.ibtn_left);
		mActionBarTitle = (TextView) view.findViewById(R.id.tv_title);
		mActionBarRightButton = (ImageButton) view
				.findViewById(R.id.ibtn_right);
		mActionBarRightText = (TextView) view
				.findViewById(R.id.ibtn_right_text);

		mActionBar.setDisplayShowCustomEnabled(true);
		mActionBar.setDisplayHomeAsUpEnabled(false);
		mActionBar.setDisplayShowHomeEnabled(false);
		mActionBar.setDisplayShowTitleEnabled(false);
		mActionBar.setCustomView(view);

	}

	/**
	 * 设置Activity的标题
	 * 
	 * @param title
	 *            标题
	 */
	protected void setActionBarTitle(String title) {
		mActionBarTitle.setText(title);
	}

	/**
	 * 设置Activity的标题
	 * 
	 * @param res
	 *            资源id
	 */
	protected void setActionBarTitle(int res) {
		String title = getResources().getString(res);
		setActionBarTitle(title);
	}

	/**
	 * 显示一个popupmenu
	 * 
	 * @param anchor
	 *            menu停靠在anchor旁边
	 * @param menuId
	 *            menu的资源id
	 * @param listener
	 *            menu的监听器
	 */
	public void showMenu(View anchor, int menuId,
			OnMenuItemClickListener listener) {
		PopupMenu popupMenu = new PopupMenu(this, anchor);
		MenuInflater inflater = popupMenu.getMenuInflater();
		inflater.inflate(menuId, popupMenu.getMenu());
		popupMenu.setOnMenuItemClickListener(listener);
		popupMenu.show();
	}

	public void showMenu(int menuId, OnMenuItemClickListener listener) {
		showMenu(mActionBarRightButton, menuId, listener);
	}

	/**
	 * 标题栏左边的按钮显示为返回键
	 */
	public void showBackButton() {
		mActionBarLeftButton.setImageDrawable(getResources().getDrawable(
				R.drawable.icon_back));
		mActionBarLeftButton.setVisibility(View.VISIBLE);
		mActionBarLeftButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	/**
	 * 标题栏右边的按钮显示为菜单键
	 * 
	 * @param menuId
	 *            菜单的资源id
	 * @param listener
	 *            菜单项的监听器
	 */
	public void showMenuButton(final int menuId,
			final OnMenuItemClickListener listener) {
		showMenuButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showMenu(mActionBarRightButton, menuId, listener);
			}
		});
	}

	/**
	 * 标题栏右边的按钮显示为菜单键，自定义监听
	 * 
	 * @param listener
	 */
	public void showMenuButton(OnClickListener listener) {
		mActionBarRightButton.setImageDrawable(getResources().getDrawable(
				R.drawable.title_bar_right));
		mActionBarRightButton.setVisibility(View.VISIBLE);
		mActionBarRightButton.setOnClickListener(listener);
	}

	// /**
	// * 设置是否显示右分割线。 如果标题栏右边包含菜单项，建议显示右边分割线
	// *
	// * @param b
	// * true 显示右边分割线 false 不显示右边分割线
	// */
	// protected void setRightDividerVisible(boolean b) {
	// if (b) {
	// mRightDivider.setVisibility(View.VISIBLE);
	// } else {
	// mRightDivider.setVisibility(View.GONE);
	// }
	//
	// }

	public ImageButton getActionBarLeftButton() {
		mActionBarLeftButton.setVisibility(View.VISIBLE);
		return mActionBarLeftButton;
	}

	public TextView getActionBarTitle() {
		return mActionBarTitle;
	}

	public ImageButton getActionBarRightButton() {
		mActionBarRightButton.setVisibility(View.VISIBLE);
		return mActionBarRightButton;
	}

	public TextView getActionBarRightText() {
		mActionBarRightText.setVisibility(View.VISIBLE);
		return mActionBarRightText;
	}
	// /**
	// * ActionBar添加更多自定义组件。<br/>
	// * <b>注意：如非必要，请使用菜单项代替自定义组件，此方法未够完善，可能出现适配问题！</b>
	// * @param view
	// */
	// protected void addCustomView(View view) {
	// RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
	// RelativeLayout.LayoutParams.WRAP_CONTENT,
	// RelativeLayout.LayoutParams.MATCH_PARENT);
	// params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
	// mLayout.addView(view, params);
	// }

}
