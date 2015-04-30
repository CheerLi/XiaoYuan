package com.myxiaoapp.utils;

import android.util.Log;

public class LogUtils {
	public static final boolean DEBUG = true;

	public static void LogError(String tag, String msg, boolean isUpload) {
		Log.e(tag, msg);
		if (isUpload) {

		}
	}
}
