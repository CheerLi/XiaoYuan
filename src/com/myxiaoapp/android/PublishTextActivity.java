package com.myxiaoapp.android;

import com.myxiaoapp.listener.OnResponseListener;
import com.myxiaoapp.model.HttpRequestParams;
import com.myxiaoapp.model.User;
import com.myxiaoapp.model.UserBean;
import com.myxiaoapp.network.AsyncHttpPost;
import com.myxiaoapp.network.XYClient;
import com.myxiaoapp.utils.Constant.RequestId;
import com.myxiaoapp.utils.Constant.RequestUrl;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

/**
 * 发布文字
 * 
 * @author JiangZhenJie
 * @date 2014-9-28
 */
public class PublishTextActivity extends CommonActivity implements OnResponseListener {
	private final String TAG = "PublishTextActivity";
	private EditText mPublishText;
	private User user;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_publish_text);
		setActionBarTitle(R.string.title_activity_publish_text);
		mPublishText = (EditText) findViewById(R.id.publish_text);
		user = XiaoYuanApp.getLoginUser(this);
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
			Log.d(TAG,"发布心情");
			//new AsyncHttpPost("updatemsg",this,user.userBean.getUid(), mPublishText.getText().toString()).post();
			new XYClient().post(
					RequestId.ID_UPDATE_MSG, 
					RequestUrl.URL_UPDATE_MSG, 
					HttpRequestParams.uploadMsg(user.userBean.getUid(), mPublishText.getText().toString(), 0, null), 
					this);
			finish();
			break;

		default:
			break;
		}
		return true;
	}

	/* 
	 * @see com.myxiaoapp.listener.OnResponseListener#onFailure(int)
	 */
	@Override
	public void onFailure(int statusCode) {
	}

	/* 
	 * @see com.myxiaoapp.listener.OnResponseListener#onReceiveSuccess(java.lang.String, java.lang.String)
	 */
	@Override
	public void onReceiveSuccess(String rec, final int ID) {
		Log.d(TAG, "纯文字发布成功");
	}

	/* 
	 * @see com.myxiaoapp.listener.OnResponseListener#onReceiveFailure(java.lang.String)
	 */
	@Override
	public void onReceiveFailure(String rec) {
		Log.d(TAG, "纯文字发布失败 rec="+rec);
	}

}
