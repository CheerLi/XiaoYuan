/**
 * 2015年4月10日
 * ken
 */
package com.myxiaoapp.android;

import android.app.Application;
 

/**
 * @author ken
 *
 */
public class MyXiaoApp extends Application {
	private static MyXiaoApp instance;

    public static MyXiaoApp getInstance() {
        return instance;
    }
    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        instance = this;
    }
}