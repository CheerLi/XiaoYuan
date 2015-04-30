/**
 * 2015年4月1日
 * ken
 */
package com.myxiaoapp.model;

import java.io.Serializable;

/**
 * @author ken
 * 
 */
public class CampusCrclZanBean implements Serializable{
	
	private String uid;
	private String name;
	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}
	/**
	 * @param uid the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/* 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CampusCrclZanBean [uid=" + uid + ", name=" + name + "]";
	}
	
}
