package com.myxiaoapp.utils;

import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * @author ken
 * @time 2014/10/27 输入法和输入框弹出后，获取输入框的顶部坐标，主要用于 计算scrollview移动的距离
 */

public class InputUtils {

	// private static Rect r;
	private static int Y;

	public static int getY(View v) {
		if (Y == 0) {
			Rect r = new Rect();
			v.getGlobalVisibleRect(r);
			Y = r.top;

			// Log.v("height", "heightY="+Y);
		}
		return Y;
	}

	public static void setY(int y) {
		Y = y;
	}

	public static int getY() {
		return Y;
	}
}
