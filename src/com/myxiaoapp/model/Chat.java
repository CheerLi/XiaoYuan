/**
 * 2015年4月11日
 * ken
 */
package com.myxiaoapp.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
 

/**
 * @author ken
 *
 */
@Table(name = "ChatData")
public class Chat extends Model {

    @Column(name = "fromUserId")
    public String fromUserId;

    @Column(name = "fromName")
    public String fromName;
    
    @Column(name = "fromPortrait")
    public String fromPortrait;
    
    @Column(name = "toUserId")
    public String toUserId;

    @Column(name = "toName")
    public String toName;
    
    @Column(name = "toPortrait")
    public String toPortrait;

    @Column(name = "message")
    public String message;

    @Column(name = "time")
    public String time;

}
