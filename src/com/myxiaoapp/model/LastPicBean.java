/**
 * 2014-11-15
 * JiangZhenJie
 */
package com.myxiaoapp.model;

import java.io.Serializable;

/**
 * @author JiangZhenJie
 * 
 */
public class LastPicBean implements Serializable{
	public String p_id;
	public String p_url;
	public String s_url;
	public String p_time;
	public String p_type;
	public String user_id;
	public String messages_id;
	public String is_close;

	/**
	 * @return the p_id
	 */
	public String getP_id() {
		return p_id;
	}

	/**
	 * @param p_id the p_id to set
	 */
	public void setP_id(String p_id) {
		this.p_id = p_id;
	}

	/**
	 * @return the p_url
	 */
	public String getP_url() {
		return p_url;
	}

	/**
	 * @param p_url the p_url to set
	 */
	public void setP_url(String p_url) {
		this.p_url = p_url;
	}

	/**
	 * @return the s_url
	 */
	public String getS_url() {
		return s_url;
	}

	/**
	 * @param s_url the s_url to set
	 */
	public void setS_url(String s_url) {
		this.s_url = s_url;
	}

	/**
	 * @return the p_time
	 */
	public String getP_time() {
		return p_time;
	}

	/**
	 * @param p_time the p_time to set
	 */
	public void setP_time(String p_time) {
		this.p_time = p_time;
	}

	/**
	 * @return the p_type
	 */
	public String getP_type() {
		return p_type;
	}

	/**
	 * @param p_type the p_type to set
	 */
	public void setP_type(String p_type) {
		this.p_type = p_type;
	}

	/**
	 * @return the user_id
	 */
	public String getUser_id() {
		return user_id;
	}

	/**
	 * @param user_id the user_id to set
	 */
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	/**
	 * @return the messages_id
	 */
	public String getMessages_id() {
		return messages_id;
	}

	/**
	 * @param messages_id the messages_id to set
	 */
	public void setMessages_id(String messages_id) {
		this.messages_id = messages_id;
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

	@Override
	public String toString() {
		return "LastPicBean [p_id=" + p_id + ", p_url=" + p_url + ", s_url="
				+ s_url + ", p_time=" + p_time + ", p_type=" + p_type
				+ ", user_id=" + user_id + ", message_id=" + messages_id
				+ ", is_close=" + is_close + "]";
	}

}
