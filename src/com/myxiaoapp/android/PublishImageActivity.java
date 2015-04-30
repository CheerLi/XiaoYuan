package com.myxiaoapp.android;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.myxiaoapp.listener.OnResponseListener;
import com.myxiaoapp.model.HttpRequestParams;
import com.myxiaoapp.model.User;
import com.myxiaoapp.network.AsyncHttpPost;
import com.myxiaoapp.network.XYClient;
import com.myxiaoapp.utils.Constant.RequestId;
import com.myxiaoapp.utils.Constant.RequestUrl;
import com.myxiaoapp.utils.InputUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * 发布图片
 * 
 * @author JiangZhenJie
 * @date 2014-9-28
 */
public class PublishImageActivity extends CommonActivity implements
		OnClickListener, OnGlobalLayoutListener, OnResponseListener {
	private final String CONSTANTLOG = "publishImageActivity:";
	private final String TAG = "PublishImageActivity";
	private RelativeLayout publish_image_layout;
	private LinearLayout change_linear;
	private EditText mText;
	private ImageView mEmoji;
	private ImageView mInput;
	private ViewPager mEmojiPager;
	private GridView mImagesGrid;
	private ArrayList<CharSequence> mUrls;
	private ArrayList<Integer> mEmojis;
//	private ImageButton mCompleteEdit;
	InputMethodManager inputManager;// 软键盘
//	private LinearLayout bottomLinear;
//	private int inputY;
	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_publish_image);
		setActionBarTitle(R.string.publish_image);
		publish_image_layout = (RelativeLayout) findViewById(R.id.publish_image_layout);
		change_linear = (LinearLayout) findViewById(R.id.change_linear);
		change_linear.getViewTreeObserver().addOnGlobalLayoutListener(this);
		mText = (EditText) findViewById(R.id.publish_image_input);
		mText.setOnClickListener(this);
		inputManager = (InputMethodManager) mText.getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		// mPicture = (ImageView) findViewById(R.id.picture);
		// mPicture.setOnClickListener(this);
		mEmoji = (ImageView) findViewById(R.id.emoji_tip);
		mEmoji.setOnClickListener(this);
		mInput = (ImageView) findViewById(R.id.inputMethod_tip);
		mInput.setVisibility(View.GONE);
		mInput.setOnClickListener(this);
		mImagesGrid = (GridView) findViewById(R.id.grid_image);
		mImagesGrid.setAdapter(new PhotoAdapter(this));
		mImagesGrid.setVisibility(View.VISIBLE);
		mEmojis = new ArrayList<Integer>();
		mEmojiPager = (ViewPager) findViewById(R.id.grid_pager);
		mEmojiPager.setAdapter(new mGEmojiPagerAdapter(this));
		mEmojiPager.setVisibility(View.GONE);
//		bottomLinear = (LinearLayout) findViewById(R.id.change_linear);
		Intent in = getIntent();
		mUrls = in.getCharSequenceArrayListExtra("images");
	/*	mCompleteEdit = getActionBarRightButton();
		mCompleteEdit.setImageDrawable(getResources().getDrawable(
				R.drawable.complete_campus_news));
		mCompleteEdit.setVisibility(View.VISIBLE);
		mCompleteEdit.setOnClickListener(this);
	*/	ImageLoaderConfiguration config = ImageLoaderConfiguration
				.createDefault(this);
		ImageLoader.getInstance().init(config);

		user = XiaoYuanApp.getLoginUser(this);
		
		/* 接口测试 */
/*		String pictures[] = new String[2];
		pictures[0] = "Environment.getExternalStorageDirectory()" + "/test.jpg";
		pictures[1] = "Environment.getExternalStorageDirectory()" + "/test.jpg";
		User loginUser = XiaoYuanApp.getLoginUser(this);
		AsyncHttpPost upload = new AsyncHttpPost("updatemsg", this,
				loginUser.userBean.uid, "上传校园圈接口测试", 2, pictures);
		upload.post();
*/
}

	/**
	 * 照片适配器
	 * 
	 * @author ken
	 * 
	 */
	private class PhotoAdapter extends BaseAdapter implements OnClickListener {

		private Context mContext;

		public PhotoAdapter(Context context) {
			mContext = context;
		}

		@Override
		public int getCount() {
			if (mUrls != null) {
				return mUrls.size();
			}
			return 0;
		}

		@Override
		public Object getItem(int position) {
			if (mUrls != null) {
				return mUrls.get(position);
			}
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.grid_pictures_sends, null);
				viewHolder = new ViewHolder();
				viewHolder.imageView = (ImageView) convertView
						.findViewById(R.id.photo);
				viewHolder.del = (ImageView) convertView
						.findViewById(R.id.del_picture);
				viewHolder.del.setOnClickListener(this);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			Log.d(TAG, "url path="+(String) getItem(position));
			viewHolder.del.setTag(position);
			ImageLoader.getInstance().displayImage((String) getItem(position),
					viewHolder.imageView);
			return convertView;
		}

		private class ViewHolder {
			ImageView imageView;
			ImageView del;
		}

		/*
		 * @see android.view.View.OnClickListener#onClick(android.view.View)
		 */
		@Override
		public void onClick(View v) {
			int position = (int) v.getTag();
			delPic(position);
		}
		
		private void delPic(int position){
			mUrls.remove(position);
			this.notifyDataSetChanged();
		}
	}

	/**
	 * 表情适配器
	 * 
	 * @author ken
	 * 
	 */
	private class mGEmojiPagerAdapter extends PagerAdapter {

		private Context mContext;
		private final int COUNT = 55;
		private final int HS = 3;
		private final int VS = 7;
		private final int PAGES;

		/**
		 * @param publishImageActivity
		 */
		public mGEmojiPagerAdapter(Context mContext) {
			this.mContext = mContext;
			if (0 == (COUNT % (HS * VS)))
				PAGES = COUNT / (HS * VS);
			else
				PAGES = COUNT / (HS * VS) + 1;

			for (int i = 0; i <= COUNT; i++) {

				try {
					Class c = R.drawable.class;
					int id;
					if (i < 10) {
						id = c.getDeclaredField("emoji_00" + i).getInt(null);
					} else {
						id = c.getDeclaredField("emoji_0" + i).getInt(null);
					}

					mEmojis.add(id);
				} catch (IllegalAccessException e) {

					e.printStackTrace();
				} catch (IllegalArgumentException e) {

					e.printStackTrace();
				} catch (NoSuchFieldException e) {

				}
			}

		}

		@Override
		public int getCount() {

			return PAGES;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {

			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
			object = null;
		}

		@Override
		public Object instantiateItem(ViewGroup container,
				final int position_pager) {
			View view = LayoutInflater.from(mContext).inflate(
					R.layout.grid_pager_emojis, null);
			GridView EmojisGrid = (GridView) view.findViewById(R.id.grid_emoji);
			EmojisGrid.setAdapter(new BaseAdapter() {

				@Override
				public int getCount() {
					if (PAGES == position_pager + 1) {
						return mEmojis.size() - position_pager * HS * VS;
					} else
						return HS * VS;
				}

				@Override
				public Object getItem(int position) {

					return mEmojis.get(position);
				}

				@Override
				public long getItemId(int position) {

					return position;
				}

				@Override
				public View getView(int position_grid, View convertView,
						ViewGroup parent) {

					View v = LayoutInflater.from(mContext).inflate(
							R.layout.grid_emojis_item, null);
					ImageView ev = (ImageView) v.findViewById(R.id.emoji);
					// Log.i("mydebug",
					// "publishImage:position="+position_pager*HS*VS+position_grid);
					ev.setBackgroundResource(mEmojis.get(position_pager * HS
							* VS + position_grid));
					return v;
				}

			});
			((ViewPager) container).addView(view);
			return view;
		}

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.publish_text, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_send:
			Log.d(TAG, "发布图文");
		//	new AsyncHttpPost("updatemsg",this,user.userBean.getUid(), mText.getText().toString(), mUrls.size(), mUrls).post();
			new XYClient().post(
					RequestId.ID_UPDATE_MSG, 
					RequestUrl.URL_UPDATE_MSG, 
					HttpRequestParams.uploadMsg(user.userBean.getUid(), mText.getText().toString(), mUrls.size(), mUrls), 
					this);
			Log.d(TAG, "pic size="+mUrls.size());
			finish();
			break;

		default:
			break;
		}
		return true;
	}
	/*
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.publish_image_input:
			// inputY = InputUtils.getY(bottomLinear);
			mEmojiPager.setVisibility(View.GONE);
			inputManager.showSoftInput(mText, 0);
			break;
		case R.id.emoji_tip:
			// if(0 != inputY)
			// mEmojiPager.setLayoutParams(new
			// LayoutParams(XiaoYuanApp.deviceWidth,inputY));
			mEmoji.setVisibility(View.GONE);
			mInput.setVisibility(View.VISIBLE);

			mEmojiPager.setVisibility(View.VISIBLE);
			inputManager.hideSoftInputFromWindow(mText.getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
			break;

		case R.id.inputMethod_tip:
			mEmoji.setVisibility(View.VISIBLE);
			mInput.setVisibility(View.GONE);

			mEmojiPager.setVisibility(View.GONE);
			inputManager.showSoftInput(mText, 0);
			break;
		default:
			break;
		}
	}

	/*
	 * @see
	 * android.view.ViewTreeObserver.OnGlobalLayoutListener#onGlobalLayout()
	 */
	@Override
	public void onGlobalLayout() {

		int Y;
		Rect r = new Rect();
		change_linear.getGlobalVisibleRect(r);
		Y = r.top;
		Log.i("mydebug", Y + "," + XiaoYuanApp.deviceHeight);
		if (0 == InputUtils.getY() && Y < XiaoYuanApp.deviceHeight - 200) {
			InputUtils.setY(Y);
			LayoutParams params = mEmojiPager.getLayoutParams();
			params.height = XiaoYuanApp.deviceHeight - Y
					- change_linear.getHeight();
			mEmojiPager.setLayoutParams(params);
		}

	}

	/*
	 * @see com.myxiaoapp.listener.OnResponseListener#onFailure(int)
	 */
	@Override
	public void onFailure(int statusCode) {
	}

	/*
	 * @see
	 * com.myxiaoapp.listener.OnResponseListener#onReceiveSuccess(java.lang.
	 * String)
	 */
	@Override
	public void onReceiveSuccess(String rec, final int ID) {
		Log.d(TAG, "rec"+rec);
	}

	/*
	 * @see
	 * com.myxiaoapp.listener.OnResponseListener#onReceiveFailure(java.lang.
	 * String)
	 */
	@Override
	public void onReceiveFailure(String rec) {
	}

}
