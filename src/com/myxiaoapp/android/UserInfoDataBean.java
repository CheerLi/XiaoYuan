/**
 * 2015年4月8日
 * ken
 */
package com.myxiaoapp.android;

import com.myxiaoapp.model.UserInfoBean;

/**
 * @author ken
 *
 */
public class UserInfoDataBean {
	private UserInfoBean data;

	/**
	 * @return the data
	 */
	public UserInfoBean getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(UserInfoBean data) {
		this.data = data;
	}

	/* 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserInfoDataBean [data=" + data + "]";
	}
	
}
