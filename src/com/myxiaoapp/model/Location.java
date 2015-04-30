package com.myxiaoapp.model;

public class Location {
	/**
	 * 纬度
	 */
	public double latitude = 0.0;
	/**
	 * 经度
	 */
	public double longitude = 0.0;

	@Override
	public String toString() {
		String ret = latitude + "," + longitude;
		return ret;
	}
}
