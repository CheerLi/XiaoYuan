/**
 * 2015年3月31日
 * ken
 */
package com.myxiaoapp.model;

import java.util.List;

/**
 * @author ken
 * 
 */
public class CampusCircleBean {
	private List<MomentBean> data;

	  


	/**
	 * @return the data
	 */
	public List<MomentBean> getData() {
		return data;
	}




	/**
	 * @param data the data to set
	 */
	public void setData(List<MomentBean> data) {
		this.data = data;
	}




	@Override
	public String toString() {
		return "CampusCircleBean [data=" + data + "]";
	}
}
