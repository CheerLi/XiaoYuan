/**
 * 2014年11月4日
 * ken
 */
package com.myxiaoapp.model;

/**
 * @author ken
 * 
 */
public class RegisterInfo {
	static private int schoolCode; // 学校代码
	static private String picturePath; // 本地图片地址
	static private int sex; // 1男，2女
	static private String phone;// 手机号
	static private String username;// 用户名
	static private String password;// 密码
	static private String nickname;// 昵称

	public static void setSchoolCode(int mSchoolCode) {
		schoolCode = mSchoolCode;
	}

	public static void setDetail(String mPicturePath, int mSex,
			String mUsername, String mPassword, String mNickname) {
		picturePath = mPicturePath;
		sex = mSex;
		username = mUsername;
		password = mPassword;
		nickname = mNickname;
	}

	public static void setPhone(String mPhone) {
		phone = mPhone;
	}

	public static int getSchoolCode() {
		return schoolCode;
	}

	public static String getPicturePath() {
		return picturePath;
	}

	public static int getSex() {
		return sex;
	}

	public static String getPhone() {
		return phone;
	}

	public static String getUsername() {
		return username;
	}

	public static String getPassword() {
		return password;
	}

	public static String getNickname() {
		return nickname;
	}

	public static void recycle() {
		schoolCode = 0;
		picturePath = null;
		sex = 0;
		phone = null;
		username = null;
		password = null;
		nickname = null;
	}
}
