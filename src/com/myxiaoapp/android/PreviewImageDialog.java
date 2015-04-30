package com.myxiaoapp.android;

import uk.co.senab.photoview.PhotoViewAttacher;

import com.myxiaoapp.utils.Constant;
import com.myxiaoapp.utils.MyGestureListener;
import com.myxiaoapp.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener; 

import android.util.Log;
import android.view.View.OnClickListener;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import android.os.Handler;
import android.provider.SyncStateContract.Constants;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

public class PreviewImageDialog extends Dialog implements OnClickListener{
	private  final static String TAG = "PreviewImageDialog";
	private Context mContext;
	private ImageView mPreviewImage;
	private PhotoViewAttacher mAttacher;
	private GestureDetector mGestureDetector; 

	private String mUrl;

	private Bitmap mBitmap;

	public PreviewImageDialog(Context context,String imageUrl) {
		super(context, android.R.style.Theme);
		this.mContext = context;
		this.mUrl = imageUrl;
		this.mGestureDetector = new GestureDetector(mContext, new MyGestureListener(this)); 
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setOwnerActivity((Activity)mContext);
		setContentView(R.layout.activity_preview_image);
		
		mPreviewImage = (ImageView) findViewById(R.id.preview_image);
		Log.d(TAG,"image w="+mPreviewImage.getWidth()+",h="+mPreviewImage.getHeight());
		mPreviewImage.setOnClickListener(this);
		mPreviewImage.setLayoutParams(new android.widget.RelativeLayout.LayoutParams(XiaoYuanApp.deviceWidth,XiaoYuanApp.deviceHeight));
		mAttacher = new PhotoViewAttacher(mPreviewImage);
		ImageLoaderConfiguration config = ImageLoaderConfiguration
				.createDefault(mContext);
		ImageLoader.getInstance().init(config);
		loadImage();
	}
	private void loadImage() {
		// new Thread(new Runnable() {
		//
		// @Override
		// public void run() {
		BitmapFactory.Options decodingOptions = new Options();
		decodingOptions.inSampleSize = 1;
		// mBitmap = BitmapFactory.decodeFile(mUrl, options);
		Builder builder = new Builder();
		builder.decodingOptions(decodingOptions);
		ImageLoader.getInstance().loadImage(mUrl, builder.build(), listener);
		new Handler().postDelayed(new Runnable(){

			@Override
			public void run() {
				Log.d(TAG,"image w="+mPreviewImage.getWidth()+",h="+mPreviewImage.getHeight());
			}
			
		}, 1000);
		// mHandler.sendEmptyMessage(0);
		// }
		// }).start();
	}
	@Override
	protected void onStop() {
		Log.d(TAG, "dialog stop");
		if (mBitmap != null) {
		//	mBitmap.recycle();
		}
		super.onStop();

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
			Utils.showProgressDialog(mContext, R.string.loading, true);
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
	
	@Override
    public boolean onTouchEvent(MotionEvent event) {
		return mGestureDetector.onTouchEvent(event); 
    } 
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		mGestureDetector.onTouchEvent(ev); //让GestureDetector响应触碰事件  
		super.dispatchTouchEvent(ev); //让Activity响应触碰事件 
		return false;	
	}
}
