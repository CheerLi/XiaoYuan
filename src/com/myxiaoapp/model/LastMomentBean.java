/**
 * 2014-11-15
 * JiangZhenJie
 */
package com.myxiaoapp.model;

import java.util.List;

/**
 * @author JiangZhenJie
 * 
 */
public class LastMomentBean {
	public String m_id;
	public String m_type;
	public String m_info;
	public List<String> m_pictures;
	public List<String> m_spictures;
	public String m_time;
	public String m_collectnum;
	public String m_commentnum;
	// public String m_transpondnum;
	public String m_likesnum;
	public String m_readnum;
	public String m_label;
	public String user_id;
	public String is_del;
	public String is_close;

	/**
	 * @return the m_id
	 */
	public String getM_id() {
		return m_id;
	}

	/**
	 * @param m_id the m_id to set
	 */
	public void setM_id(String m_id) {
		this.m_id = m_id;
	}

	/**
	 * @return the m_type
	 */
	public String getM_type() {
		return m_type;
	}

	/**
	 * @param m_type the m_type to set
	 */
	public void setM_type(String m_type) {
		this.m_type = m_type;
	}

	/**
	 * @return the m_info
	 */
	public String getM_info() {
		return m_info;
	}

	/**
	 * @param m_info the m_info to set
	 */
	public void setM_info(String m_info) {
		this.m_info = m_info;
	}

	/**
	 * @return the m_pictures
	 */
	public List<String> getM_pictures() {
		return m_pictures;
	}

	/**
	 * @param m_pictures the m_pictures to set
	 */
	public void setM_pictures(List<String> m_pictures) {
		this.m_pictures = m_pictures;
	}

	/**
	 * @return the m_spictures
	 */
	public List<String> getM_spictures() {
		return m_spictures;
	}

	/**
	 * @param m_spictures the m_spictures to set
	 */
	public void setM_spictures(List<String> m_spictures) {
		this.m_spictures = m_spictures;
	}

	/**
	 * @return the m_time
	 */
	public String getM_time() {
		return m_time;
	}

	/**
	 * @param m_time the m_time to set
	 */
	public void setM_time(String m_time) {
		this.m_time = m_time;
	}

	/**
	 * @return the m_collectnum
	 */
	public String getM_collectnum() {
		return m_collectnum;
	}

	/**
	 * @param m_collectnum the m_collectnum to set
	 */
	public void setM_collectnum(String m_collectnum) {
		this.m_collectnum = m_collectnum;
	}

	/**
	 * @return the m_commentnum
	 */
	public String getM_commentnum() {
		return m_commentnum;
	}

	/**
	 * @param m_commentnum the m_commentnum to set
	 */
	public void setM_commentnum(String m_commentnum) {
		this.m_commentnum = m_commentnum;
	}

	/**
	 * @return the m_likesnum
	 */
	public String getM_likesnum() {
		return m_likesnum;
	}

	/**
	 * @param m_likesnum the m_likesnum to set
	 */
	public void setM_likesnum(String m_likesnum) {
		this.m_likesnum = m_likesnum;
	}

	/**
	 * @return the m_readnum
	 */
	public String getM_readnum() {
		return m_readnum;
	}

	/**
	 * @param m_readnum the m_readnum to set
	 */
	public void setM_readnum(String m_readnum) {
		this.m_readnum = m_readnum;
	}

	/**
	 * @return the m_label
	 */
	public String getM_label() {
		return m_label;
	}

	/**
	 * @param m_label the m_label to set
	 */
	public void setM_label(String m_label) {
		this.m_label = m_label;
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
	 * @return the is_del
	 */
	public String getIs_del() {
		return is_del;
	}

	/**
	 * @param is_del the is_del to set
	 */
	public void setIs_del(String is_del) {
		this.is_del = is_del;
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
		return "MomentBean [m_id=" + m_id + ", m_type=" + m_type + ", m_info="
				+ m_info + ", m_pictures=" + m_pictures + ", m_spictures="
				+ m_spictures + ", m_time=" + m_time + ", m_collectnum="
				+ m_collectnum + ", m_commentnum=" + m_commentnum
				+ ", m_likesnum=" + m_likesnum + ", m_readnum=" + m_readnum
				+ ", m_label=" + m_label + ", user_id=" + user_id + ", is_del="
				+ is_del + ", is_close=" + is_close + "]";
	}

}
