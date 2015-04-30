/**
 * 2014年11月5日
 * ken
 */
package com.myxiaoapp.network;

import com.loopj.android.http.AsyncHttpClient;

/**
 * @author ken
 * 
 */
public class SingleAsyncClient {

	private static AsyncHttpClient singleClient = new AsyncHttpClient();

	public static AsyncHttpClient getSingleClient() {
		return singleClient;
	}
}
