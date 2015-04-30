package com.myxiaoapp.model;

import com.myxiaoapp.android.XiaoYuanApp;

import android.content.Context;

/**
 * 因为聊天中要用到百度云相关信息，抽象一个User模型类，区别UserBean
 * 
 * @author JiangZhenJie
 * 
 */
public class User {

	public static final String SEX_MALE = "1";
	public static final String SEX_FEMALE = "2";

	public UserBean userBean;
	public ChatIdBean chatIdBean; 
	/**
	 * @return
	 */
	public String getFriendName() {
		return userBean.getUsername();
	}
	 
}
