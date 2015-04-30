package com.myxiaoapp.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import android.util.Log;

/**
 * 加密解密相关都写在这里，后期需要使用NDK提高安全性。
 * 
 * @author JiangZhenJie
 * @date 2014-9-26
 */
public class EncryptUtils {
	/**
	 * MD5加密前缀
	 */
	public static final String PREIX = "xiaoyuan";
	/**
	 * MD5加密Key
	 */
	public static final String KEY = "bd44977f4225b957923ddefa781e8f93";

	/**
	 * MD5签名
	 * 
	 * @param paramStr
	 *            key-value pair，注意value需要url encode.
	 * @return
	 */
	public static String signByMd5(String paramStr) {
		// sign=md5(PREFEX +PARAMS+ KEY); 生成32位小写字符串
		paramStr = Utils.sortParams(paramStr);
		if (LogUtils.DEBUG) {
			Log.i("mydebug", paramStr);
		}
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			String input = PREIX + paramStr + KEY;
			byte[] b = digest.digest(input.getBytes("utf-8"));
			return bytesToHex(b);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

	/**
	 * 将bytes数组转换成小写的十六进制
	 * 
	 * @param bytes
	 * @return
	 */
	public static String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars).toLowerCase(Locale.ENGLISH);
	}

}
