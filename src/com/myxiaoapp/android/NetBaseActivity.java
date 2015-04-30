package com.myxiaoapp.android;

import org.apache.http.Header;
import org.apache.http.client.methods.HttpHead;
import org.json.JSONArray;

import android.os.Bundle;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.ResponseHandlerInterface;
import com.myxiaoapp.network.HttpHelper;
import com.myxiaoapp.network.IHttpCallBacker;
import com.myxiaoapp.utils.Constant;

/**
 * 网络操作基类，所有进行网络通信的界面都集成在此类
 * 
 * @author JiangZhenJie
 * @date 2014-9-7
 */
public abstract class NetBaseActivity extends BaseActivity implements
		IHttpCallBacker {

	private HttpHelper mHttpHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mHttpHelper = new HttpHelper(this, this);
	}

	public void sendRequest(ResponseHandlerInterface responseHandler, int msgId) {
		sendRequest(responseHandler, msgId, Constant.HTTP_GET, true);
	}

	public void sendRequest(ResponseHandlerInterface responseHandler) {
		sendRequest(responseHandler, 0);
	}

	public void sendRequest(ResponseHandlerInterface responseHandler,
			int msgId, int httpType, boolean isShowDialog) {
		mHttpHelper.sendRequest(responseHandler, httpType);
	}

}
