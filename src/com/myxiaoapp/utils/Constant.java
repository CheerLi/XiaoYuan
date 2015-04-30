package com.myxiaoapp.utils;

import android.content.Context;
import android.util.Log;
import android.view.WindowManager;

import com.myxiaoapp.android.MyXiaoApp;
import com.myxiaoapp.android.R; 
public class Constant {
	public static final int FLAG_ME = 1;
	public static final int FLAG_Discovery = 2;
	public static final int FLAG_CAMPUS = 3;
	/**
	 * 设备系统，2表示Android，修改需慎重。
	 */
	public static final int ANDROID = 2;

	/**
	 * App版本号
	 */
	public static final String APP_VERSION = "v0.1";

	public static final int HTTP_GET = 1;
	public static final int HTTP_POST = 2;

	public static final String TEST_SERVER = "http://120.24.76.148";
	public static final String CHECK_PHONE_URL = "/servlet/CheckPhone";

	/**
	 * 默认字符集
	 */
	public static final String charSet = "UTF-8";

	public static final String SHARE_PRE_LOGIN_INFO = "login_information";
	public static final String SHARE_PREFS_CHAT_INFO = "chat_infomation";

	/*
	 * 表情相关
	 */
	public static final int EXPRESSION_NUMBER = 50;
	public static final int START_ID = R.drawable.emoji_000;
	
	/*
	 * 头像
	 */
	public static final int IO_BUFFER_SIZE = 1024;
	public static int deviceWidth;
	public static int deviceHeight;
	
	static {
		WindowManager wm = (WindowManager) MyXiaoApp.getInstance().getBaseContext()
                .getSystemService(Context.WINDOW_SERVICE);
		deviceWidth = wm.getDefaultDisplay().getWidth();
		deviceHeight = wm.getDefaultDisplay().getHeight();
	//	Log.d(TAG, "width="+deviceWidth+","+"height="+deviceWidth);
	}
	
	public static class RequestId {
        public static final int ID_REGISTER = 100;
        public static final int ID_LOGIN = 101;
        public static final int ID_GET_SHORT_MSG = 102;
        public static final int ID_CHECK_PHONE_NUM = 103;
        public static final int ID_UPDATE_PASSWORD = 104;
        
        public static final int ID_CAMPUS_CODE = 105; 
        public static final int ID_SCHOOL_INFO = 106;
        public static final int ID_UPDATE_MSG = 107;
        public static final int ID_DEL_MSG = 108;
        public static final int ID_VERIFY = 109;
        
        public static final int ID_REPLY = 200;
        
        public static final int ID_DEL_COMMENT = 201;
        public static final int ID_ADD_LIKE = 202;
        public static final int ID_FOLLOW = 203;
        public static final int ID_CANCEL_FOLLOW = 204;
        public static final int ID_SETREMARK = 205;
        
        public static final int ID_SHIELDING = 206;
        public static final int ID_FOLLOWS_LIST = 207;
        public static final int ID_FANS_LIST = 208;
        public static final int ID_GET_INFO = 209;
        public static final int ID_UPDATE_INFO = 300;
        
        public static final int ID_BACK_STATE = 301;
        public static final int ID_FEEDBACK = 302;
        public static final int ID_NEARBY_USERS = 303;
        public static final int ID_SET_TOKEN = 304;
        public static final int ID_PUSH_MSG = 305;
        
    }

    public static class RequestUrl {
        public static final String URL_SERVER = "http://120.24.76.148";
        
        public static final String URL_REGISTER = URL_SERVER + "/yaf/index.php/Register";
        public static final String URL_LOGIN = URL_SERVER + "/yaf/index.php/Login";
        public static final String URL_GET_SHORT_MSG = URL_SERVER + "/yaf/index.php/Getshortmsg";
        public static final String URL_CHECK_PHONE_NUM = URL_SERVER + "/yaf/index.php/Checkphonenum";
        public static final String URL_UPDATE_PASSWORD = URL_SERVER + "/yaf/index.php/Updatepassword";
        
        public static final String URL_CAMPUS_CODE = URL_SERVER + "/yaf/index.php/Campuscode"; 
        public static final String URL_SCHOOL_INFO = URL_SERVER + "/yaf/index.php/Schoolinfo";
        public static final String URL_UPDATE_MSG = URL_SERVER + "/yaf/index.php/Schoolmsg/updatemsg";
        public static final String URL_DEL_MSG = URL_SERVER + "/yaf/index.php/Schoolmsg/delmsg";
        public static final String URL_VERIFY = URL_SERVER + "*";
        
        public static final String URL_REPLY = URL_SERVER + "/yaf/index.php/Comments/reply";
        
        public static final String URL_DEL_COMMENT = URL_SERVER + "/yaf/index.php/Comments/delcomment";
        public static final String URL_ADD_LIKE = URL_SERVER + "/yaf/index.php/Likes/addlike";
        public static final String URL_FOLLOW = URL_SERVER + "/yaf/index.php/Relation/follow";
        public static final String URL_CANCEL_FOLLOW = URL_SERVER + "/yaf/index.php/Relation/cancelfollow";
        public static final String URL_SETREMARK = URL_SERVER + "/yaf/index.php/Relation/setremark";
        
        public static final String URL_SHIELDING = URL_SERVER + "/yaf/index.php/Relation/shielding";
        public static final String URL_FOLLOWS_LIST = URL_SERVER + "/yaf/index.php/Followslist";
        public static final String URL_FANS_LIST = URL_SERVER + "/yaf/index.php/Fanslist";
        public static final String URL_GET_INFO = URL_SERVER + "/yaf/index.php/Getinfo";
        public static final String URL_UPDATE_INFO = URL_SERVER + "/yaf/index.php/Userinfo/updateinfo";
        
        public static final String URL_BACK_STATE = URL_SERVER + "/yaf/index.php/Backstate";
        public static final String URL_FEEDBACK = URL_SERVER + "/yaf/index.php/Feedback";
        public static final String URL_NEARBY_USERS = URL_SERVER + "/yaf/index.php/Place/nearbyusers";
        public static final String URL_SET_TOKEN = URL_SERVER + "/yaf/index.php/Push/Settoken";
        public static final String URL_PUSH_MSG = URL_SERVER + "/yaf/index.php/Push/Pushmsg";
    }

    public static class ResultCode {
        public static final int RESULT_SUCCESS = 20;
        public static final int RESULT_FAILURE = 40;
    }
}
