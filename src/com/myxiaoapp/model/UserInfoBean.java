/**
 * 2015年4月8日
 * ken
 */
package com.myxiaoapp.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author ken
 *
 */
public class UserInfoBean implements Serializable{
	private UserBean user;
	private List<LastPicBean> last_pic_list;
	private List<MomentBean> last_moments;
	/**
	 * @return the user
	 */
	public UserBean getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(UserBean user) {
		this.user = user;
	}
	/**
	 * @return the last_pic_list
	 */
	public List<LastPicBean> getLast_pic_list() {
		return last_pic_list;
	}
	/**
	 * @param last_pic_list the last_pic_list to set
	 */
	public void setLast_pic_list(List<LastPicBean> last_pic_list) {
		this.last_pic_list = last_pic_list;
	}
	/**
	 * @return the last_moments
	 */
	public List<MomentBean> getLast_moments() {
		return last_moments;
	}
	/**
	 * @param last_moments the last_moments to set
	 */
	public void setLast_moments(List<MomentBean> last_moments) {
		this.last_moments = last_moments;
	}
	/* 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserInfoBean [user=" + user + ", last_pic_list="
				+ last_pic_list + ", last_moments=" + last_moments + "]";
	}
	 
	
	
}
