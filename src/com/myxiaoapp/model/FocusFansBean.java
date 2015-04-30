/**
 * 2014-11-15
 * JiangZhenJie
 */
package com.myxiaoapp.model;

import java.io.Serializable;
import java.util.List;

/**
 * 获取粉丝和关注者共同使用该Bean
 * 
 * @author JiangZhenJie
 * 
 */
public class FocusFansBean implements Serializable{


	public String uid;
	public String name;
	public String college;
	public String portrait;
	public String s_portrait;
	public String sex;
	public String moto;
	public String lat;
	public String lng;
	public String validate;
	public String updatetime;
	public String register_time;
	public List<PicListBean> last_pic_list;
	public List<LastMomentBean> last_moments;
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
	/**
	 * @return the college
	 */
	public String getCollege() {
		return college;
	}
	/**
	 * @param college the college to set
	 */
	public void setCollege(String college) {
		this.college = college;
	}
	/**
	 * @return the portrait
	 */
	public String getPortrait() {
		return portrait;
	}
	/**
	 * @param portrait the portrait to set
	 */
	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}
	 
	/**
	 * @return the s_portrait
	 */
	public String getS_portrait() {
		return s_portrait;
	}
	/**
	 * @param s_portrait the s_portrait to set
	 */
	public void setS_portrait(String s_portrait) {
		this.s_portrait = s_portrait;
	}
	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}
	/**
	 * @param sex the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}
	/**
	 * @return the moto
	 */
	public String getMoto() {
		return moto;
	}
	/**
	 * @param moto the moto to set
	 */
	public void setMoto(String moto) {
		this.moto = moto;
	}
	/**
	 * @return the lat
	 */
	public String getLat() {
		return lat;
	}
	/**
	 * @param lat the lat to set
	 */
	public void setLat(String lat) {
		this.lat = lat;
	}
	/**
	 * @return the lng
	 */
	public String getLng() {
		return lng;
	}
	/**
	 * @param lng the lng to set
	 */
	public void setLng(String lng) {
		this.lng = lng;
	}
	/**
	 * @return the validate
	 */
	public String getValidate() {
		return validate;
	}
	/**
	 * @param validate the validate to set
	 */
	public void setValidate(String validate) {
		this.validate = validate;
	}
	/**
	 * @return the updatetime
	 */
	public String getUpdatetime() {
		return updatetime;
	}
	/**
	 * @param updatetime the updatetime to set
	 */
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	/**
	 * @return the register_time
	 */
	public String getRegister_time() {
		return register_time;
	}
	/**
	 * @param register_time the register_time to set
	 */
	public void setRegister_time(String register_time) {
		this.register_time = register_time;
	}
	/**
	 * @return the last_pic_list
	 */
	public List<PicListBean> getLast_pic_list() {
		return last_pic_list;
	}
	/**
	 * @param last_pic_list the last_pic_list to set
	 */
	public void setLast_pic_list(List<PicListBean> last_pic_list) {
		this.last_pic_list = last_pic_list;
	}
	/**
	 * @return the last_moments
	 */
	public List<LastMomentBean> getLast_moments() {
		return last_moments;
	}
	/**
	 * @param last_moments the last_moments to set
	 */
	public void setLast_moments(List<LastMomentBean> last_moments) {
		this.last_moments = last_moments;
	}
	/* 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FocusFansBean [uid=" + uid + ", name=" + name + ", college="
				+ college + ", portrait=" + portrait + ", s_portrait="
				+ s_portrait + ", sex=" + sex + ", moto=" + moto + ", lat="
				+ lat + ", lng=" + lng + ", validate=" + validate
				+ ", updatetime=" + updatetime + ", register_time="
				+ register_time + ", last_pic_list=" + last_pic_list
				+ ", last_moments=" + last_moments + "]";
	}
	 
	
}
