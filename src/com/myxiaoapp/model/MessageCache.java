package com.myxiaoapp.model;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;
import java.util.List;

import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;

public class MessageCache {
	private static final int DEFAULT_CAPACITY = 20;
	private static final int DEFAULT_MORE = 10;

	private static final String ABSOLUTE_PATH = Environment
			.getExternalStorageDirectory().getAbsolutePath();

	private static final String CACHE_PATH = "/xiaoyuan/cache/";

	private List<String> memCache;
	private int capacity;
	private String userId;

	public MessageCache(String userId) {
		memCache = new LinkedList<String>();
		capacity = DEFAULT_CAPACITY;
		this.userId = userId;
	}

	public void more() {
		capacity += DEFAULT_CAPACITY;
		Handler handler = new Handler() {
			public void handleMessage(android.os.Message msg) {

			};
		};

		new Thread() {
			public void run() {

			};
		}.start();
	}

	public List<String> getMessageCache() {
		return this.memCache;
	}

	public void addMessage(final String msg) {
		if (!TextUtils.isEmpty(msg.trim())) {
			memCache.add(0, msg);
		}
		if (memCache.size() <= capacity) {
			return;
		}
		new Thread(new Runnable() {

			@Override
			public void run() {
				RandomAccessFile accessFile = null;
				try {
					File file = new File(ABSOLUTE_PATH + CACHE_PATH + userId);
					accessFile = new RandomAccessFile(file, "rw");
					accessFile.seek(0);
					accessFile.write(msg.getBytes());
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (accessFile != null) {
						try {
							accessFile.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}).start();
	}
}
