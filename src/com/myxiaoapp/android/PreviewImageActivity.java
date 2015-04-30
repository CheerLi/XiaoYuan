package com.myxiaoapp.android;

import uk.co.senab.photoview.PhotoViewAttacher;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.myxiaoapp.utils.Utils;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * 预览图片
 * 
 * @author JiangZhenJie
 * @date 2014-9-28
 */
public class PreviewImageActivity extends CommonActivity implements
		OnClickListener {

	private ImageView mPreviewImage;
	private PhotoViewAttacher mAttacher;

	private String mUrl;

	private Bitmap mBitmap;

	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preview_image);
		setActionBarTitle("预览图片");
		mContext = this;

		mPreviewImage = (ImageView) findViewById(R.id.preview_image);
		mPreviewImage.setOnClickListener(this);
		mAttacher = new PhotoViewAttacher(mPreviewImage);
		Intent i = getIntent();
		mUrl = i.getStringExtra("image_url");
		// file:////storage/sdcard0/eglsgame/herogamexiaomi/images/android-logo-shine.png_225x225
		ImageLoaderConfiguration config = ImageLoaderConfiguration
				.createDefault(this);
		ImageLoader.getInstance().init(config);
		loadImage();
	}

	private void loadImage() {
		// new Thread(new Runnable() {
		//
		// @Override
		// public void run() {
		BitmapFactory.Options decodingOptions = new Options();
		decodingOptions.inSampleSize = 2;
		// mBitmap = BitmapFactory.decodeFile(mUrl, options);
		Builder builder = new Builder();
		builder.decodingOptions(decodingOptions);
		ImageLoader.getInstance().loadImage(mUrl, builder.build(), listener);

		// mHandler.sendEmptyMessage(0);
		// }
		// }).start();
	}

	@Override
	protected void onDestroy() {
		if (mBitmap != null) {
			mBitmap.recycle();
		}
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.preview_image:
			break;

		default:
			break;
		}
	}

	private ImageLoadingListener listener = new ImageLoadingListener() {

		@Override
		public void onLoadingStarted(String imageUri, View view) {
			Utils.showProgressDialog(mContext, R.string.loading, false);
		}

		@Override
		public void onLoadingFailed(String imageUri, View view,
				FailReason failReason) {

		}

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			Utils.dismissProgressDialog();
			mBitmap = loadedImage;
			mPreviewImage.setImageBitmap(mBitmap);
			mAttacher.update();
		}

		@Override
		public void onLoadingCancelled(String imageUri, View view) {

		}
	};
}
