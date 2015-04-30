package com.myxiaoapp.unittest;

import com.loopj.android.http.RequestParams;
import com.myxiaoapp.android.XiaoYuanApp;
import com.myxiaoapp.model.HttpRequestParams;
import com.myxiaoapp.utils.Constant;

import android.telephony.TelephonyManager;
import android.test.AndroidTestCase;
import android.util.Log;

public class UnitTest extends AndroidTestCase {
	public void testCheckPhoneParams() {
		RequestParams params = HttpRequestParams
				.checkPhoneParams("18825193807");
		Log.i("mydebug", params.toString());
	}
}
