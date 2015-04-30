/**
 * 2015年4月6日
 * ken
 */
package com.myxiaoapp.model;

import java.util.List;

/**
 * @author ken
 *
 */
public class NearPersonList {
	private List<NearPersonBean> data;

	/**
	 * @return the data
	 */
	public List<NearPersonBean> getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(List<NearPersonBean> data) {
		this.data = data;
	}

	/* 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "NearPersonList [data=" + data + "]";
	}
	
}
