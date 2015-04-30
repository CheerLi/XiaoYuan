package com.myxiaoapp.android;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.Toast;
 



import com.activeandroid.ActiveAndroid;
import com.baidu.location.BDLocation;
import com.myxiaoapp.model.BaseModel;
import com.myxiaoapp.model.ChatIdBean;
import com.myxiaoapp.model.User;
import com.myxiaoapp.model.UserBean;
import com.myxiaoapp.utils.Constant;

/**
 * 全局应用程序类：用于保存和调用全局应用配置
 * 
 * @author JiangZhenJie
 * @date 2014-9-7
 */
public class XiaoYuanApp extends com.activeandroid.app.Application {

	private static final String TAG = "mydebug";
	/**
	 * 设备识别码IMEI
	 */
	public static String deviceId;
	/**
	 * App版本号
	 */
	public static String appVersion;
	/**
	 * 设备系统
	 */
	public static int deviceOS;
	/**
	 * 设备软件系统版本
	 */
	public static String deviceVersion;

	/**
	 * 登录用户
	 */
	private static User loginUser;

	/**
	 * 设备分辨率
	 */
	public static int deviceWidth;
	public static int deviceHeight;

	private static Context mContext;

	private static boolean isLogin = false;

	private static BDLocation loc;
	@Override
	public void onCreate() {
		super.onCreate();
		mContext = getApplicationContext();
		ActiveAndroid.initialize(this);
		initData();

	}

	public static Context getContext() {
		return mContext;
	}

	/**
	 * 保存在运行的Activity，以便关闭时能关闭所有。
	 */
	private List<Activity> mLaunchActivitys;

	public void addLaunchActivity(Activity ac) {
		if (mLaunchActivitys == null) {
			mLaunchActivitys = new ArrayList<Activity>();
		}
		mLaunchActivitys.add(ac);
	}

	public void destoryOtherLaunchActivitys() {
		if (mLaunchActivitys != null) {
			for (Activity ac : mLaunchActivitys) {
				ac.finish();
			}
		}
	}

	public void initData() {
		TelephonyManager manager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		deviceId = manager.getDeviceId();
		deviceOS = Constant.ANDROID;
		appVersion = Constant.APP_VERSION;
		deviceVersion = android.os.Build.VERSION.RELEASE;
		WindowManager mWindowManager = (WindowManager) mContext
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		mWindowManager.getDefaultDisplay().getMetrics(outMetrics);
		deviceWidth = outMetrics.widthPixels;
		deviceHeight = outMetrics.heightPixels;
	}

	/**
	 * 保存登录信息 暂时不用，请先保留，不要删
	 */
	// private void saveLoginInfo() {
	// if (loginUser == null)
	// return;
	// SharedPreferences sharedPref = getSharedPreferences(
	// Constant.SHARE_PRE_LOGIN_INFO, MODE_PRIVATE);
	// Editor editor = sharedPref.edit();
	// editor.putString("user_id", loginUser.getUserId());
	// editor.putString("user_name", loginUser.getUserName());
	// editor.putString("name", loginUser.getName());
	// editor.putString("sex", loginUser.getSex());
	// editor.putString("portraitUrl", loginUser.getPortraitUrl());
	// editor.putString("remarkName", loginUser.getRemarkName());
	// editor.putString("schoolName", loginUser.getSchoolName());
	// editor.putString("cellphone", loginUser.getCellphone());
	// editor.putString("signature", loginUser.getSignature());
	// editor.putInt("followersCount", loginUser.getFollowersCount());
	// editor.putInt("fansCount", loginUser.getFansCount());
	// editor.putInt("newsCount", loginUser.getNewsCount());
	// editor.putString("chat_user_id", loginUser.getChatUserId());
	// editor.putString("chat_channel_id", loginUser.getChatChannelId());
	// // editor.putString("userLocation",
	// // loginUser.getUserLocation().toString());
	// editor.putInt("relation", loginUser.getRelation());
	// editor.commit();
	// Log.i(TAG, "save login info success");
	// }

	/**
	 * 保存信息
	 * 
	 * @param base
	 * @param pName
	 */
	public void saveInfo(final BaseModel base, final String pName) {
		new Thread() {
			public void run() {

				SharedPreferences sharedPref = getSharedPreferences(pName,
						MODE_PRIVATE);
				Editor editor = sharedPref.edit();
				Class<?> cls = base.getClass();
				Field[] fields = cls.getDeclaredFields();
				try {
					for (Field f : fields) {
						f.setAccessible(true);
						String fName = f.getName();
						Object value = f.get(base);
						if (value instanceof String) {
							String valueStr = (String) value;
							editor.putString(fName, valueStr);
						} else if (value instanceof List) {
							@SuppressWarnings("unchecked")
							List<String> valueList = (List<String>) value;
							Set<String> valueSet = new TreeSet<String>();
							Iterator<String> iterator = valueList.iterator();
							while (iterator.hasNext()) {
								String str = iterator.next();
								valueSet.add(str);
							}
							editor.putStringSet(fName, valueSet);
						}
					}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
				editor.commit();

			};
		}.start();
	}

	/**
	 * 得到登录用户实体
	 * 
	 * @param context
	 * @return 有可能返回空值，注意判断！
	 */
	public static User getLoginUser(Context context) {
		if (!isLogin) {
			// Toast.makeText(context, "登录超时，请重新登录!",
			// Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(context, LoginActivity.class);
			context.startActivity(intent);
			((Activity) context).finish();
			return null;
		} else {
			// isLogin = true loginUser = null read prefs
		}
		return loginUser;
	}
	 

	public static boolean isLogin() {
		return isLogin;
	}

	/**
	 * 只能在这里更改isLogin的值
	 * 
	 * @param userBean
	 */
	public void setLoginUser(UserBean userBean) {
		if (userBean == null) {
			isLogin = false;
			return;
		} else {
			if (loginUser == null) {
				loginUser = new User();
			}
			loginUser.userBean = userBean;
			isLogin = true;
		}
	}

	public void setLoginUserChatId(ChatIdBean chatIdBean) {
		if (chatIdBean == null) {
			return;
		} else {
			if (loginUser == null) {
				loginUser = new User();
			}
			loginUser.chatIdBean = chatIdBean;
		}
	}

	public static ChatIdBean readChatId(Context context) {
		SharedPreferences sharedPref = context.getSharedPreferences(
				Constant.SHARE_PREFS_CHAT_INFO, Context.MODE_PRIVATE);
		String chatUserId = sharedPref.getString("chat_user_id", null);
		String chatChannelId = sharedPref.getString("chat_channel_id", null);
		ChatIdBean bean = new ChatIdBean();
		bean.chatUserId = chatUserId;
		bean.chatChannelId = chatChannelId;
		return bean;
	}

	/**
	 * @return the loc
	 */
	public static BDLocation getLoc() {
		return loc;
	}

	/**
	 * @param loc the loc to set
	 */
	public static void setLoc(BDLocation loc) {
		XiaoYuanApp.loc = loc;
	}
	
	
}
