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
public class FocusListBean extends BaseModel {
	public List<FocusFansBean> data;

	/**
	 * @return the data
	 */
	public List<FocusFansBean> getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(List<FocusFansBean> data) {
		this.data = data;
	}
 
	
}
