/**
 * 2014年11月6日
 * ken
 */
package com.myxiaoapp.network;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.myxiaoapp.listener.OnResponseListener;
import com.myxiaoapp.model.HttpRequestParams;
import com.myxiaoapp.model.HttpResponseHandler;
import com.myxiaoapp.model.RegisterInfo;
import com.myxiaoapp.utils.Constant;

/**
 * 所有接口的基类，继承OnReceiveResponseListener接口，当接受后台返回数据时，回调原接口的OnReceive方法
 * 
 * @author ken
 * 
 */

public class AsyncHttpPost {
	private final String TAG = "AsyncHttpPost";
	AsyncHttpClient client; // 单例连接，登录之后，一直用同一个连接和后台交互 
	String url = "http://120.24.76.148"; // 请求后台的url,由子类根据自己的需求初始化
	RequestParams params; // 请求后台的参数,同样由子类根据自己的需求初始化
	HttpResponseHandler responseHandler; // 保存返回数据

	AsyncHttpPost(OnResponseListener mOnResponseListener,final int ID_REQUEST) {
		this.client = SingleAsyncClient.getSingleClient();
		this.responseHandler = new HttpResponseHandler(mOnResponseListener,ID_REQUEST);
	}
//
//	public AsyncHttpPost(String iName, OnResponseListener mOnResponseListener,
//			String user_id, String follow_id, String msg_id, String msg_tyle,
//			String page_size) {
//		this(mOnResponseListener, iName);
//		switch (iName) {
//		
//		case "Schoolinfo":
//			this.url = url + "/yaf/index.php/Schoolinfo";
//			this.params = HttpRequestParams.campusCircle(user_id, follow_id,
//					msg_id, msg_tyle, page_size);
//			break;
//		default:
//			break;
//		}
//	}
//
//	public AsyncHttpPost( String iName, OnResponseListener mOnResponseListener,
//			String arg1, String arg2) { 
//		this(mOnResponseListener, iName);
//		switch (iName) {
//		case "Settoken":
//			this.url = url + "/yaf/index.php/Push/Settoken";
//			this.params = HttpRequestParams.setToken(arg1,arg2);
//			break;
//		case "updatemsg":
//			this.url = url + "/yaf/index.php/Schoolmsg/updatemsg";
//			this.params = HttpRequestParams.uploadMsg(arg1, arg2, 0, null);
//			break;
//		case "forgetPassword":
//			this.url = url + "/yaf/index.php/forgetPassword";
//			this.params = HttpRequestParams.setPasswordParams(arg1, arg2);
//			break;
//		case "addlike":
//			this.url = url + "/yaf/index.php/Likes/addlike";
//			this.params = HttpRequestParams.addLike(arg1, arg2);
//			break;
//		case "cancelfollow":
//			this.url = url + "/yaf/index.php/Relation/cancelfollow";
//			this.params = HttpRequestParams.cancelFocusParams(arg1, arg2);
//			break;
//		case "delmsg":
//			this.url = url + "/yaf/index.php/Schoolmsg/delmsg";
//			this.params = HttpRequestParams.delMsg(arg1, arg2);
//			break;
//		case "follow":
//			this.url = url + "/yaf/index.php/Relation/follow";
//			this.params = HttpRequestParams.focusFriendsParams(arg1, arg2);
//			break;
//		case "Getinfo":
//			url = url + "/yaf/index.php/Getinfo";
//			params = HttpRequestParams.getUserInfoParams(arg1, arg2);
//			break;
//		case "login":
//			this.url = url + "/yaf/index.php/login";
//			this.params = HttpRequestParams.loginParams(arg1, arg2);
//			break;
//		case "Shielding":
//			this.url = url + "/yaf/index.php/Shielding";
//			this.params = HttpRequestParams.shiledFriendsParams(arg1, arg2);
//			break;
//		default:
//			break;
//		}
//		Log.d(TAG,params.toString());
//	}
//
//	public AsyncHttpPost(String iName, OnResponseListener mOnResponseListener,
//			String user_id, String follow_id, int page_size) {
//		this(mOnResponseListener, iName);
//		this.url = "*";
//		this.params = HttpRequestParams.userAlbumParams(user_id, follow_id,
//				page_size);
//	}
//
//	public AsyncHttpPost(String iName, OnResponseListener mOnResponseListener,
//			String arg1, String arg2, String arg3, String arg4) {
//		this(mOnResponseListener, iName);
//		switch (iName) {
//		case "reply":
//			this.url = url + "/yaf/index.php/Comments/reply";
//			try {
//				this.params = HttpRequestParams.reply(arg1, arg2, arg3, arg4);
//			} catch (UnsupportedEncodingException e) {
//
//				e.printStackTrace();
//			}
//			break;
//
//		default:
//			break;
//		}
//	}
//
//	public AsyncHttpPost(String iName, OnResponseListener mOnResponseListener,
//			String arg1, String arg2, String arg3) {
//		this(mOnResponseListener, iName);
//		switch (iName) {
//		case "delcomment":
//			this.url = url + "/yaf/index.php/Comments/delcomment";
//			this.params = HttpRequestParams.delComment(arg1, arg2, arg3);
//			break;
//		case "nearbyusers":
//			this.url = url + "/yaf/index.php/Place/nearbyusers";
//			this.params = HttpRequestParams.nearbyUserParams(arg1, arg2, arg3);
//			break;
//		case "Fanslist":
//			this.url = url + "/yaf/index.php/Fanslist";
//			this.params = HttpRequestParams.fansListParams(arg1, arg2, arg3);
//			break;
//		case "Followslist":
//			this.url = url + "/yaf/index.php/Followslist";
//			this.params = HttpRequestParams.focusListParams(arg1, arg2, arg3);
//			break;
//		case "setremark":
//			this.url = url + "/yaf/index.php/Relation/setremark";
//			this.params = HttpRequestParams.remarkFriendsParams(arg1, arg2,
//					arg3);
//			break;
//		case "Backstate":
//			this.url = url + "/yaf/index.php/Backstate";
//			this.params = HttpRequestParams.updateLocationParams(arg1, arg2,
//					arg3);
//		default:
//			break;
//		}
//	}
//
//	public AsyncHttpPost(String iName, OnResponseListener mOnResponseListener) {
//		this(mOnResponseListener, iName);
//		switch (iName) {
//		case "Register":
//			this.url = Constant.TEST_SERVER + "/yaf/index.php/Register";
//			this.params = HttpRequestParams.registerParams();
//			break;
//		default:
//			break;
//		}
//	}
//
//	/**
//	 * @param string
//	 * @param registerInputVerifyActivity
//	 * @param phone
//	 */
//	public AsyncHttpPost(String iName,OnResponseListener mOnResponseListener,
//			String arg1) {
//		this(mOnResponseListener, iName);
//		switch(iName){
//		case "Checkphonenum":
//			this.url = url + "/yaf/index.php/Checkphonenum";
//			this.params = HttpRequestParams.checkPhoneParams(arg1);
//			break;
//		case "Campuscode":
//			this.url = url+ "/yaf/index.php/Campuscode";
//			this.params = HttpRequestParams.getVerifyParams(RegisterInfo.getPhone());
//			break;
//		case "verifyImage":
//			this.url = url + "arg1";	//因为验证码图片地址不唯一，通过参数传递
//			this.params = null;
//			break;
//		case "Getshortmsg":
//			this.url = url + "/yaf/index.php/Getshortmsg";
//			this.params = HttpRequestParams.getVerifyParams(arg1);
//		default:
//			break;
//		}
//	}
//
//	/**
//	 * @param string
//	 * @param publishImageActivity
//	 * @param uid
//	 * @param string2
//	 * @param size
//	 * @param mUrls
//	 */
//	public AsyncHttpPost(String iName, OnResponseListener mOnResponseListener, String uid,
//			String text, int size, ArrayList<CharSequence> mUrls) {
//		this(mOnResponseListener, iName);
//		switch(iName){
//		//校缘圈发送图文，与前面纯文字的请求参数不一样
//		case "updatemsg":
//			this.url = url + "/yaf/index.php/Schoolmsg/updatemsg";
//			this.params = HttpRequestParams.uploadMsg(uid, text, size, mUrls);
//			Log.d(TAG, "size="+size);
//			break;
//		default: break;
//		}
//	}
//
//	/**
//	 * @param string
//	 * @param myHomePageFragment
//	 * @param uid
//	 * @param object
//	 * @param object2
//	 * @param object3
//	 * @param string2
//	 * @param string3
//	 * @param object4
//	 */
//	public AsyncHttpPost(String iName, OnResponseListener mOnResponseListener,
//			String arg1, String arg2, String arg3, String arg4,
//			String arg5, String arg6, String arg7) {
//		this(mOnResponseListener, iName);
//		switch(iName){
//			case "updateinfo":
//				this.url = url + "/yaf/index.php/Userinfo/updateinfo";
//				this.params = HttpRequestParams.updateinfo(arg1, arg2, arg3, arg4, arg5, arg6, arg7);
//			break;
//			case "Pushchat":
//				this.url = url + "/yaf/index.php/Push/Pushmsg";
//				this.params = HttpRequestParams.getPushChat(arg1, arg2, arg3, arg4, arg5, arg6, arg7);
//				break;
//			default:break;
//		} 
//		Log.d(TAG,params.toString());
//	}
// 
//
//	/**
//	 * 向后台发送数据
//	 */
//	public void post() {
//		client.post(url, params, responseHandler);
//		Log.d(TAG, "发送="+url);
//	}
}
