package com.myxiaoapp.android;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * 选择图片
 * 
 * @author JiangZhenJie
 * @date 2014-9-28
 */
public class SelectPicturesActivity extends CommonActivity implements
		OnCheckedChangeListener, OnClickListener {

	private GridView mPicturesGrid;
	private Context mContext;
	private ArrayList<CharSequence> mPictureUrls;
	private ArrayList<CharSequence> mSelected;
	private String mSelectTips = "完成";

	private TextView mFinish;
	private int maxSelect;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_pictures);
		setActionBarTitle("图片");
		mContext = this;

		Intent i = getIntent();
		mPictureUrls = i.getCharSequenceArrayListExtra("picture_group");
		mSelected = new ArrayList<CharSequence>();

		ImageLoaderConfiguration config = ImageLoaderConfiguration
				.createDefault(this);
		ImageLoader.getInstance().init(config);

		Intent intent = getIntent();
		maxSelect = intent.getIntExtra("max_select", 1); // 最多可选择

		mPicturesGrid = (GridView) findViewById(R.id.gridview_photos);
		PicturesAdapter adapter = new PicturesAdapter(mContext, maxSelect);
		mPicturesGrid.setAdapter(adapter);

		showBackButton();
		mFinish = getActionBarRightText();
		mFinish.setText(mSelectTips);
		mFinish.setOnClickListener(this);
	}

	private class PicturesAdapter extends BaseAdapter {

		private Context mContext;
		private int selectType;

		public PicturesAdapter(Context c, int selectType) {
			this.mContext = c;
			this.selectType = selectType;
		}

		@Override
		public int getCount() {
			if (mPictureUrls != null)
				return mPictureUrls.size();
			else
				return 0;
		}

		@Override
		public Object getItem(int position) {
			if (mPictureUrls != null) {
				return mPictureUrls.get(position);
			} else {
				return null;
			}
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			String imageUrl = "file:///"
					+ mPictureUrls.get(position).toString();
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.select_picture_item, parent, false);
				holder = new ViewHolder();
				holder.picture = (ImageView) convertView
						.findViewById(R.id.picture);
				holder.picture.setOnClickListener(SelectPicturesActivity.this);
				holder.checkbox = (CheckBox) convertView
						.findViewById(R.id.checkbox);
				holder.checkbox
						.setOnCheckedChangeListener(SelectPicturesActivity.this);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.checkbox.setTag(imageUrl);
			holder.picture.setTag(imageUrl);
			holder.picture.setImageResource(R.drawable.pictures_no);
			ImageLoader.getInstance().displayImage(imageUrl, holder.picture,
					mImageLoadingListener);
			if (mSelected.contains(imageUrl)) {
				holder.checkbox.setChecked(true);
			} else {
				holder.checkbox.setChecked(false);
			}
			return convertView;
		}

		private class ViewHolder {
			ImageView picture;
			CheckBox checkbox;
		}

	}

	private ImageLoadingListener mImageLoadingListener = new ImageLoadingListener() {

		@Override
		public void onLoadingStarted(String imageUri, View view) {
		}

		@Override
		public void onLoadingFailed(String imageUri, View view,
				FailReason failReason) {
		}

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			ImageView imageView = (ImageView) mPicturesGrid
					.findViewWithTag(imageUri);
			if (loadedImage != null && imageView != null) {
				imageView.setImageBitmap(loadedImage);
			}
		}

		@Override
		public void onLoadingCancelled(String imageUri, View view) {
		}
	};

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		CheckBox checkBox = (CheckBox) buttonView;
		String url = (String) checkBox.getTag();
		if (isChecked && !mSelected.contains(url)) {
			mSelected.add(url);
		} else if (mSelected.contains(url)) {
			mSelected.remove(url);
		}
		if (mSelected.size() == 0) {
			mSelectTips = "完成";
		} else {
			mSelectTips = "完成(" + mSelected.size() + "/" + maxSelect + ")";
		}
		mFinish.setText(mSelectTips);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.picture:
			ImageView imageView = (ImageView) v;
			Intent intent = new Intent(this, PreviewImageActivity.class);
			intent.putExtra("image_url", (String) imageView.getTag());
			startActivity(intent);
			break;
		case R.id.ibtn_right_text:
			if (mSelected.size() > maxSelect) {
				Toast.makeText(mContext, "最多选择" + maxSelect + "张图片",
						Toast.LENGTH_SHORT).show();
			} else if (mSelected.size() > 0) {
				/*
				 * Intent i = new Intent(this, PublishImageActivity.class);
				 * i.putCharSequenceArrayListExtra("images", mSelected);
				 * startActivity(i); finish();
				 */
				Intent data = new Intent();
				data.putCharSequenceArrayListExtra("images", mSelected);
				setResult(RESULT_OK, data);
				finish();
			}
			break;
		default:
			break;
		}
	}

}
