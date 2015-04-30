package com.myxiaoapp.model;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.myxiaoapp.utils.Constant;

/**
 * 
 * @author JiangZhenJie
 * 
 */
public class UserBean extends BaseModel implements Serializable{
	private String uid;
	private String sid;
	private String name;
	private String username;
	private String cellphone;
	private String college;
	private String portrait;
	private String s_portrait;
	private String sex;
	private String moto;
	private String lat;
	private String lng;
	private String validate;
	private String register_time;
	private String is_close;
	private String fol_counts;
	private String fan_counts;
	
	/**
	 * @return the fol_counts
	 */
	public String getFol_counts() {
		return fol_counts;
	}
	/**
	 * @param fol_counts the fol_counts to set
	 */
	public void setFol_counts(String fol_counts) {
		this.fol_counts = fol_counts;
	}
	/**
	 * @return the fan_counts
	 */
	public String getFan_counts() {
		return fan_counts;
	}
	/**
	 * @param fan_counts the fan_counts to set
	 */
	public void setFan_counts(String fan_counts) {
		this.fan_counts = fan_counts;
	}
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
	 * @return the sid
	 */
	public String getSid() {
		return sid;
	}
	/**
	 * @param sid the sid to set
	 */
	public void setSid(String sid) {
		this.sid = sid;
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
		try {
			this.name = URLDecoder.decode(name,Constant.charSet);
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		} 
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) { 
		try {
			this.username = URLDecoder.decode(username,Constant.charSet);
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
	}
	/**
	 * @return the cellphone
	 */
	public String getCellphone() {
		return cellphone;
	}
	/**
	 * @param cellphone the cellphone to set
	 */
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
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
		try {
			this.college = URLDecoder.decode(college,Constant.charSet);
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
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
	 * @return the is_close
	 */
	public String getIs_close() {
		return is_close;
	}
	/**
	 * @param is_close the is_close to set
	 */
	public void setIs_close(String is_close) {
		this.is_close = is_close;
	}
	/* 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserBean [uid=" + uid + ", sid=" + sid + ", name=" + name
				+ ", username=" + username + ", cellphone=" + cellphone
				+ ", college=" + college + ", portrait=" + portrait
				+ ", s_portrait=" + s_portrait + ", sex=" + sex + ", moto="
				+ moto + ", lat=" + lat + ", lng=" + lng + ", validate="
				+ validate + ", register_time=" + register_time + ", is_close="
				+ is_close + ", fol_counts=" + fol_counts + ", fan_counts="
				+ fan_counts + ", errno=" + errno + ", message=" + message
				+ ", getFol_counts()=" + getFol_counts() + ", getFan_counts()="
				+ getFan_counts() + ", getUid()=" + getUid() + ", getSid()="
				+ getSid() + ", getName()=" + getName() + ", getUsername()="
				+ getUsername() + ", getCellphone()=" + getCellphone()
				+ ", getCollege()=" + getCollege() + ", getPortrait()="
				+ getPortrait() + ", getS_portrait()=" + getS_portrait()
				+ ", getSex()=" + getSex() + ", getMoto()=" + getMoto()
				+ ", getLat()=" + getLat() + ", getLng()=" + getLng()
				+ ", getValidate()=" + getValidate() + ", getRegister_time()="
				+ getRegister_time() + ", getIs_close()=" + getIs_close()
				+ ", getErrno()=" + getErrno() + ", getMessage()="
				+ getMessage() + ", toString()=" + super.toString()
				+ ", getResultCode()=" + getResultCode() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + "]";
	}
	 
	
	
	
}
