package com.myxiaoapp.utils;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;


public class MyGestureListener extends SimpleOnGestureListener {
	private String TAG ="MyGestureListener";
	private Activity mActivity; 
    private Dialog mDialog;
	public MyGestureListener(Activity activity) { 
		mActivity = activity; 
    } 
    public MyGestureListener(Dialog dialog){
    	mDialog = dialog;
    }
 /*
    @Override
    public boolean onDown(MotionEvent e) { 
        Toast.makeText(mContext, "DOWN " + e.getAction(), Toast.LENGTH_SHORT).show(); 
        return false; 
 
    } 
 
    @Override
    public void onShowPress(MotionEvent e) { 
        Toast.makeText(mContext, "SHOW " + e.getAction(), Toast.LENGTH_SHORT).show();            
 
    } 
 
    @Override
    public boolean onSingleTapUp(MotionEvent e) { 
        Toast.makeText(mContext, "SINGLE UP " + e.getAction(), Toast.LENGTH_SHORT).show(); 
        return false; 
    } 
*/ 
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, 
            float distanceX, float distanceY) { 

    	Log.d(TAG, ""+distanceX+","+distanceY);
    	if(distanceX + 40.0 < 0 && (distanceY + 30.0 < 0 || distanceY - 30.0 < 0)){
    		if( null != mActivity ) mActivity.finish();
    		else {mDialog.dismiss();mDialog.onBackPressed();}
    	}
       // Toast.makeText(mContext, "SCROLL " + e2.getAction(), Toast.LENGTH_SHORT).show(); 
        return false; 
    } 
 /*
    @Override
    public void onLongPress(MotionEvent e) { 
        Toast.makeText(mContext, "LONG " + e.getAction(), Toast.LENGTH_SHORT).show(); 
    } 
 
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,  float velocityY) { 
        Toast.makeText(mContext, "FLING " + e2.getAction(), Toast.LENGTH_SHORT).show(); 
        return false; 
    } 
 
     @Override
    public boolean onDoubleTap(MotionEvent e) { 
        Toast.makeText(mContext, "DOUBLE " + e.getAction(), Toast.LENGTH_SHORT).show(); 
        return false; 
    } 
 
    @Override
    public boolean onDoubleTapEvent(MotionEvent e) { 
        Toast.makeText(mContext, "DOUBLE EVENT " + e.getAction(), Toast.LENGTH_SHORT).show(); 
        return false; 
 
    } 
 
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) { 
        Toast.makeText(mContext, "SINGLE CONF " + e.getAction(), Toast.LENGTH_SHORT).show(); 
        return false; 
    }
    */ 
}
