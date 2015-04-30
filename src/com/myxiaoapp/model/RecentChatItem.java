package com.myxiaoapp.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
@Table(name = "RecentChatData")
public class RecentChatItem extends Model{
	@Column(name = "timestamp")
	public long timestamp;
	
	@Column(name = "userId")
	public int userId;
	
	@Column(name = "chatUserId")
	public int chatUserId; 
	
	@Column(name = "chatUserName")
	public String chatUserName;
	
	@Column(name = "chatUserPortrait")
	public String chatUserPortrait;
	
	@Column(name = "recentMessage")
	public String recentMessage;
	
	@Column(name = "unReadedCount")
	public int unReadedCount;
 
}
