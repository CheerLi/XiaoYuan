/**
 * 2015年4月6日
 * ken
 */
package com.myxiaoapp.model;

/**
 * @author ken
 *
 */
public class NearPersonBean {
	private String uid;
	private String name;
	private String college;
	private String portrait;
	private String s_portrait;
	private String sex;
	private String moto;
	private String validate;
	private String register_time;
	private String distance;
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
	 * @return the distance
	 */
	public String getDistance() {
		return distance;
	}
	/**
	 * @param distance the distance to set
	 */
	public void setDistance(String distance) {
		this.distance = distance;
	}
	/* 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "NearPersonBean [uid=" + uid + ", name=" + name + ", college="
				+ college + ", portrait=" + portrait + ", s_portrait="
				+ s_portrait + ", sex=" + sex + ", moto=" + moto
				+ ", validate=" + validate + ", register_time=" + register_time
				+ ", distance=" + distance + "]";
	}
	
}
