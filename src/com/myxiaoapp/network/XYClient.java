/**
 * 2015年4月15日
 * ken
 */
package com.myxiaoapp.network;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.myxiaoapp.listener.OnResponseListener;
import com.myxiaoapp.model.HttpResponseHandler;

/**
 * @author ken
 *
 */
public class XYClient extends AsyncHttpClient {
	
	private static final String TAG = "XYClient";

	public RequestHandle post(final int ID_REQUEST, final String URL_REQUEST, RequestParams params,OnResponseListener listener) {
		HttpResponseHandler handler = new HttpResponseHandler(listener, ID_REQUEST);
		Log.d(TAG, "id="+ID_REQUEST+",url="+URL_REQUEST+",params="+params.toString());
		return super.post(URL_REQUEST, params, handler);
	}
}
