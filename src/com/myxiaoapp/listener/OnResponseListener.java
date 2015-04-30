package com.myxiaoapp.listener;

/**
 * 监听后台数据返回接口
 * 
 * @author ken
 * @date 2014-11-06
 */
public interface OnResponseListener {

	/**
	 * 出错，没有数据返回时调用，如404或500等错误
	 */
	void onFailure(int statusCode);

	/**
	 * 返回正确数据
	 */
	void onReceiveSuccess(String rec, final int id);

	/*
	 * 返回失败数据
	 */
	void onReceiveFailure(String rec);
}