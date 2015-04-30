/**
 * 2015年4月14日
 * ken
 */
package com.myxiaoapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * @author ken
 *
 */
public class PersonGridView extends GridView {

	/**
	 * @param context
	 */
	public PersonGridView(Context context) {
		super(context);
		
	}
	public PersonGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PersonGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	@Override
    public boolean onTouchEvent(MotionEvent ev) {
            return false;// false 自己不处理此次事件以及后续的事件，那么向上传递给外层view
    }

}
