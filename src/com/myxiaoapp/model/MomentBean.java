/**
 * 2014-11-15
 * JiangZhenJie
 */
package com.myxiaoapp.model;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.myxiaoapp.utils.Constant;

/**
 * @author JiangZhenJie
 * 
 */
public class MomentBean implements Serializable{
	/**
	 * 
	 */
	private String name;
	private String portrait;
	private String s_portrait;
	private String m_id;
	private String m_type;
	private String m_info;
	private List<String> m_pictures;
	private List<String> m_spictures;
	private String m_time;
	private String m_collectnum;
	private String m_commentnum;
	// private String m_transpondnum;
	private String m_likesnum;
	private String m_readnum;
	private String m_label;
	private String user_id;
	private String is_del;
	private String is_close;

	private List<CampusCrclCmtBean> comment_list;
	private List<CampusCrclZanBean> likes_list;
	
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
		this.s_portrait = Constant.TEST_SERVER+s_portrait;
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
		if(m_pictures == null) return;
		int i = 0;
		int size = m_pictures.size();
		while(i < size){
			String picUrl = Constant.TEST_SERVER + m_pictures.get(i);
			m_pictures.set(i, picUrl);
			i++;
		}
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
		if( m_spictures == null) return;
		int i = 0;
		int size = m_spictures.size();
		while(i < size){
			String picUrl = Constant.TEST_SERVER + m_spictures.get(i);
			m_spictures.set(i, picUrl);
			i++;
		}
		this.m_spictures = m_spictures;
	}


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
		try {
			this.m_info = URLDecoder.decode(m_info,Constant.charSet);
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
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
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");    
		String sd = sdf.format(new Date(Long.parseLong(m_time)*1000));
		this.m_time = sd;
		Log.d("mydebug", this.m_time);
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
	 * @return the comment_list
	 */
	public List<CampusCrclCmtBean> getComment_list() {
		return comment_list;
	}


	/**
	 * @param comment_list the comment_list to set
	 */
	public void setComment_list(List<CampusCrclCmtBean> comment_list) {
		this.comment_list = comment_list;
	}


	/**
	 * @return the likes_list
	 */
	public List<CampusCrclZanBean> getLikes_list() {
		return likes_list;
	}


	/**
	 * @param likes_list the likes_list to set
	 */
	public void setLikes_list(List<CampusCrclZanBean> likes_list) {
		this.likes_list = likes_list;
	}


	/* 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MomentBean [name=" + name + ", portrait=" + portrait
				+ ", s_portrait=" + s_portrait + ", m_id=" + m_id + ", m_type="
				+ m_type + ", m_info=" + m_info + ", m_pictures=" + m_pictures
				+ ", m_spictures=" + m_spictures + ", m_time=" + m_time
				+ ", m_collectnum=" + m_collectnum + ", m_commentnum="
				+ m_commentnum + ", m_likesnum=" + m_likesnum + ", m_readnum="
				+ m_readnum + ", m_label=" + m_label + ", user_id=" + user_id
				+ ", is_del=" + is_del + ", is_close=" + is_close
				+ ", comment_list=" + comment_list + ", likes_list="
				+ likes_list + "]";
	}

 

	/* 
	 * @see android.os.Parcelable#describeContents()
	 */
/*	@Override
	public int describeContents() {
		
		return 0;
	}

*/
	/* 
	 * @see android.os.Parcelable#writeToParcel(android.os.Parcel, int)
	 */
/*	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeString(portrait);
		dest.writeString(s_portrait);
		dest.writeString(m_id);
		dest.writeString(m_type);
		dest.writeString(m_info);
		dest.writeList(m_pictures);
		dest.writeList(m_spictures);
		dest.writeString(m_time);
		dest.writeString(m_collectnum);
		dest.writeString(m_commentnum);
		dest.writeString(m_likesnum);
		dest.writeString(m_readnum);
		dest.writeString(m_label);
		dest.writeString(user_id);
		dest.writeString(is_del);
		dest.writeString(is_close); 
		dest.writeList(comment_list);
		dest.writeList(likes_list);	
	}
*/
}
