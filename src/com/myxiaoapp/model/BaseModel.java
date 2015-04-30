package com.myxiaoapp.model;

public class BaseModel {

	/**
	 * 返回状态码
	 */
	public int errno;

	/**
	 * 返回状态信息
	 */
	public String message;

	/**
	 * @return the errno
	 */
	public int getErrno() {
		return errno;
	}

	/**
	 * @param errno the errno to set
	 */
	public void setErrno(int errno) {
		this.errno = errno;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	public void set(String key, String value) {
		try {
			getClass().getField(key).set(this, value);
		} catch (Exception e) {
		}
	}

	/* 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BaseModel [errno=" + errno + ", message=" + message + "]";
	}

	/**
	 * @return
	 */
	public int getResultCode() {
		
		return errno;
	}

	
}
