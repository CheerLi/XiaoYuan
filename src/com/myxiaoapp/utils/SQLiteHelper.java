package com.myxiaoapp.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.myxiaoapp.android.XiaoYuanApp;
import com.myxiaoapp.model.User;

public class SQLiteHelper extends SQLiteOpenHelper {

	private static final int VERSION = 1;
	// private static final String dbName = XiaoYuanApp.getLoginUser(context)
	// .getUserId() + ".db";;

	public static final String TABLE_CHAT_MESSAGE = "chat_message";
	public static final String TABLE_RECENT_CHAT = "recent_chat";

	public SQLiteHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	public SQLiteHelper(Context context) {
		this(context, getDBName(context), null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// 创建chat_message表
		String sql = "create table " + TABLE_CHAT_MESSAGE + " ("
				+ "times int, " + "fromUser varchar(64), "
				+ "toUser varchar(64), "
				+ "messages varchar(255) , isReaded int" + ")";
		db.execSQL(sql);

		// 创建recent_chat表
		sql = "create table " + TABLE_RECENT_CHAT + " ("
				+ "times int , userId varchar(64) , "
				+ "chatUserId varchar(64) , " + "chatChannelId varchar(64), "
				+ "chatUserName varchar(64) ,"
				+ "recentMessage varchar(255) , unReadedCount int)";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	private static String getDBName(Context context) {
		User loginUser = XiaoYuanApp.getLoginUser(context);
		return loginUser.userBean.getUid() + ".db";
	}

}
