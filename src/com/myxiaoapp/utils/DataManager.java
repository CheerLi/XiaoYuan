/**
 * 2015年4月11日
 * ken
 */
package com.myxiaoapp.utils;

import com.myxiaoapp.android.XiaoYuanApp;
import com.myxiaoapp.model.User;

import android.app.Activity;
import android.content.Context;

/**
 * @author ken
 *
 */
public class DataManager {
	private static DataManager mDataManager;

    private static XiaoYuanApp mXiaoYuanApp;
    private Context mContext;
 
    private User mLoginFriend; 

    /**
     * 主Activity，用于退出登录时使用
     */
    private Activity mMainActivity;

    /**
     * 在屏幕前面的聊天界面中的对方的ID
     */
    private String mFrontChat;

    private DataManager() {
    	if(mLoginFriend == null)
    		mLoginFriend = XiaoYuanApp.getLoginUser(XiaoYuanApp.getContext());
    }
 
    public static DataManager getInstance() {

        if (mDataManager == null) {
            synchronized (DataManager.class) {
                if (mDataManager == null) {
                    mDataManager = new DataManager();
                }
            }
        }

        return mDataManager;
    }
 
    public Context getContext() {
    	if(mContext == null)
    		mContext = XiaoYuanApp.getContext();
        return mContext;
    }


    public User getLoginFriend() {
        return mLoginFriend == null ? new User() : mLoginFriend;
    }

    public void setLoginFriend(User loginFriend) {
        mLoginFriend = loginFriend;
    }
  

    public void setMainActivity(Activity ac) {
        mMainActivity = ac;
    }

    public Activity getMainActivity(){
        return mMainActivity;
    }
 
    
    /**
	 * @return the mFrontChat
	 */
	public String getmFrontChat() {
		return mFrontChat;
	}

	/**
	 * @param mFrontChat the mFrontChat to set
	 */
	public void setmFrontChat(String mFrontChat) {
		this.mFrontChat = mFrontChat;
	}

	public int getLoginUserId(){
    	return Integer.valueOf( mLoginFriend.userBean.getUid() );
    }
}
