package com.myxiaoapp.network;

import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import android.content.Context;
import android.os.Handler;

import com.loopj.android.http.ResponseHandlerInterface;

/**
 * Http帮助类，用于发送Http请求等
 * 
 * @author JiangZhenJie
 * @date 2014-9-10
 */
public class HttpHelper {

	private Context mContext;
	private IHttpCallBacker mCallBack;

	public HttpHelper(Context c, IHttpCallBacker callBack) {
		this.mContext = c;
		this.mCallBack = callBack;
	}

	public void sendRequest(final ResponseHandlerInterface responseHandler,
			final int httpType) {
		new Thread(new Runnable() {

			@Override
			public void run() {

			}
		}).start();
	}

	public HttpParams getToPost(String url) {
		HttpParams params = new BasicHttpParams();
		return null;
	}

	public void loaderCampusNews(Handler handler) {

	}

}
