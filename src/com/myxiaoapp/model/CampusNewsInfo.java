package com.myxiaoapp.model;

import java.util.List;

/**
 * 校园圈实体了
 * 
 * @author JiangZhenJie
 * @date 2014-11-9
 */

public class CampusNewsInfo {

	private String userId;
	private String userName;
	private String portraitUrl;
	private String newsContent;
	private List<String> imagesUrl;
	private Long time;
	private int commentCount;
	private int likeCount;

	public CampusNewsInfo() {
		super();
	}

	public CampusNewsInfo(String userId, String userName, String portraitUrl,
			String newsContent, List<String> imagesUrl, Long time,
			int commentCount, int likeCount) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.portraitUrl = portraitUrl;
		this.newsContent = newsContent;
		this.imagesUrl = imagesUrl;
		this.time = time;
		this.commentCount = commentCount;
		this.likeCount = likeCount;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPortraitUrl() {
		return portraitUrl;
	}

	public void setPortraitUrl(String portraitUrl) {
		this.portraitUrl = portraitUrl;
	}

	public String getNewsContent() {
		return newsContent;
	}

	public void setNewsContent(String newsContent) {
		this.newsContent = newsContent;
	}

	public List<String> getImagesUrl() {
		return imagesUrl;
	}

	public void setImagesUrl(List<String> imagesUrl) {
		this.imagesUrl = imagesUrl;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

}
