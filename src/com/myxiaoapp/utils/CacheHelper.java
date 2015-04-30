/**
 * 2015年4月9日
 * ken
 */
package com.myxiaoapp.utils;

import com.myxiaoapp.android.CampusNewsActivity;

import android.content.Context;

/**
 * @author ken
 *
 */
public class CacheHelper {
	public static ACache getCache(Context mContext, String key){
		return ACache.get(mContext,key);
	}
}
