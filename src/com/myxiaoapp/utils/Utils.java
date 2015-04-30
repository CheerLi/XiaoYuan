package com.myxiaoapp.utils;

import java.net.FileNameMap;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * @author ken
 */
public class Utils {

	private static final String TAG = "mydebug";

	private static ProgressDialog mProgressDialog = null;

	/**
	 * 显示一个进度框
	 * 
	 * @param context
	 *            上下文对象
	 * @param msgId
	 *            显示的字符串
	 * @param cancelable
	 *            是否可以取消
	 * @param listener
	 *            监听取消
	 */
	public static void showProgressDialog(Context context, int msgId,
			boolean cancelable, OnCancelListener listener) {
		mProgressDialog = new ProgressDialog(context);
		mProgressDialog.setCancelable(cancelable);
		if (msgId != -1) {
			mProgressDialog.setMessage(context.getResources().getString(msgId));
		}
		mProgressDialog.setOnCancelListener(listener);
		mProgressDialog.show();
	}

	/**
	 * 显示一个进度框
	 * 
	 * @param context
	 *            上下文对象
	 * @param msgId
	 *            显示的字符串
	 * @param cancelable
	 *            是否可以取消
	 */
	public static void showProgressDialog(Context context, int msgId,
			boolean cancelable) {
		showProgressDialog(context, msgId, cancelable, null);
	}

	public static void dismissProgressDialog() {
		if (mProgressDialog != null) {
			mProgressDialog.dismiss();
		}
	}

	public static void showProgressDialog(Context context, boolean cancelable) {
		showProgressDialog(context, -1, cancelable);
	}

	/**
	 * 
	 * @Method: dpChangePix
	 * @Description: 将输入的DP值转换成PIX值
	 * @throws
	 * @param dp
	 *            输入要转换的DP值
	 * @return
	 */
	public static int dpChangePix(Context c, float dp) {
		float DENSITY = c.getResources().getDisplayMetrics().density;
		// DP*本机的屏幕密度，再加上0.5来四舍五入到最接近的数。
		return (int) (dp * DENSITY + 0.5f);
	}

	/**
	 * 
	 * @Method: pixChangeDp
	 * @Description: 将输入的PIX值转换成DP值
	 * @throws
	 * @param pix
	 *            输入要转换的PIX值
	 * @return
	 */
	public static int pixChangeDp(Context c, float pix) {
		float DENSITY = c.getResources().getDisplayMetrics().density;
		return (int) (pix / DENSITY + 0.5f);
	}

	/**
	 * 
	 * @author ken
	 * @Description:设置去滑动条的listview的总高度
	 * @param listView
	 */
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		// params.height =
		// totalHeight+(listView.getDividerHeight()*(listAdapter.getCount()-1));
		params.height = totalHeight + 50;

		listView.setLayoutParams(params);

	}

	/**
	 * 对参数按键进行从小到大的排序
	 * 
	 * @param paramStr
	 * @return
	 */
	public static String sortParams(String paramStr) {
		String[] ss = paramStr.split("&");
		List<String> list = new ArrayList<String>();
		for (String s : ss) {
			list.add(s);
		}
		Collections.sort(list, new StringsComparator());
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			String str = list.get(i);
			sb.append(str + "&");
		}
		sb.deleteCharAt(sb.toString().length() - 1);
		return sb.toString();
	}

	public static class StringsComparator implements Comparator<String> {

		@Override
		public int compare(String lhs, String rhs) {
			return lhs.compareTo(rhs);
		}
	}

	public static String formatTime(Long timetamp) {
		String ret = "";
		Date date = new Date(timetamp);
		SimpleDateFormat format = new SimpleDateFormat("HH-mm-ss", Locale.CHINA);
		ret = format.format(date);
		return ret;
	}

	private static SparseArray<String> faceMap = new SparseArray<String>();

	public static void initFaceMap() {
		for (int i = 0; i < Constant.EXPRESSION_NUMBER; i++) {
			faceMap.put(Constant.START_ID + i, "[" + i + "]");
		}
	}

	public static int getFaceResId(String value) {
		if (faceMap == null || faceMap.size() == 0)
			return 0;
		for (int i = Constant.START_ID; i < Constant.START_ID + faceMap.size(); i++) {
			String v = faceMap.get(i);
			if (value.equals(v)) {
				return i;
			}
		}
		return 0;
	}

	public static String getFaceStr(int id) {
		if (faceMap != null) {
			return faceMap.get(id);
		}
		return null;
	}

	private static final String EMOTION_URL = "\\[(\\S+?)\\]";

	/**
	 * 将String转化成SpannableString
	 * 
	 * @param message
	 * @return
	 */
	public static CharSequence convertStringToSpannableString(Context context,
			String message) {
		SpannableString spanStr = SpannableString.valueOf(message);
		Pattern pattern = Pattern.compile(EMOTION_URL);
		Matcher matcher = pattern.matcher(spanStr);
		while (matcher.find()) {
			String findStr = matcher.group(0);
			int start = matcher.start();
			int end = matcher.end();
			int id = getFaceResId(findStr);
			Bitmap bm = BitmapFactory
					.decodeResource(context.getResources(), id);
			if (bm != null) {
				ImageSpan imageSpan = new ImageSpan(context, bm);
				spanStr.setSpan(imageSpan, start, end,
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
		}
		return spanStr;
	}

	public static String getContentType(String path) {
		FileNameMap fileNameMap = URLConnection.getFileNameMap();
		String contentType = fileNameMap.getContentTypeFor(path);
		return contentType;
	}

}
