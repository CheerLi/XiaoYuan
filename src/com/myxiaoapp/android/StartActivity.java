package com.myxiaoapp.android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.myxiaoapp.utils.LocationHelper;
import com.myxiaoapp.utils.LocationHelper.GetLocationListener;
import com.myxiaoapp.utils.Utils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.tencent.android.tpush.XGPushManager;

/**
 * 启动Activity,主要完成以下事情：
 * <ol>
 * <li>检查版本更新,版本更新需要分两种，强制更新和可选更新
 * <li>检查用户是否需要重新登录
 * <li>通知服务器上线，并拉取用户离线信息
 * </ol>
 * 
 * @author JiangZhenJie
 * @date 2014-9-7
 */
public class StartActivity extends BaseActivity {
	private static final String TAG = "mydebug";
	private ImageLoader imageLoader;
	private DisplayImageOptions options;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		mApp = (XiaoYuanApp) getApplication();
		mApp.addLaunchActivity(this);
		// Utils.showProgressDialog(this, false);
		Utils.initFaceMap();
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(getApplicationContext()));
		options = new DisplayImageOptions.Builder().cacheInMemory(true)
				.cacheOnDisk(true).build();
		// test
		// startActivity(new Intent(this, PushDemoActivity.class));
		// finish();
		
		startActivity(new Intent(this, LoginActivity.class));
		finish();
	}

	// private void checkChatId() {
	// SharedPreferences sharePref = getSharedPreferences(
	// Constant.SHARE_PRE_CHAT_INFO, MODE_PRIVATE);
	// String chatUserId = sharePref.getString("chat_user_id", null);
	// String chatChannelId = sharePref.getString("chat_channel_id", null);
	// if(chatUserId != null && chatChannelId != null){
	// // XiaoYuanApp.loginUser = new User();
	// XiaoYuanApp.getLoginUser(this).setChatUserId(chatUserId);
	// XiaoYuanApp.getLoginUser(this).setChatChannelId(chatChannelId);
	// }
	// }

}
