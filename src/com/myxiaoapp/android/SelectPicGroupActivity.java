package com.myxiaoapp.android;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.myxiaoapp.adapter.PicGroupAdapter;
import com.myxiaoapp.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * 选择相册
 * 
 * @author JiangZhenJie
 * @date 2014-9-28
 */
public class SelectPicGroupActivity extends CommonActivity implements
		OnItemClickListener {

	private Context mContext;
	private GridView mGroupGrid;
	private HashMap<String, ArrayList<CharSequence>> mGroups;
	private List<String> mKeyList;

	private int maxSelect;

	private static final int SCAN_FINISH = 1;
	private Handler mHandler;

	private static class SelectHandler extends Handler {
		private WeakReference<SelectPicGroupActivity> mOuter;

		public SelectHandler(SelectPicGroupActivity ac) {
			mOuter = new WeakReference<SelectPicGroupActivity>(ac);
		}

		@Override
		public void handleMessage(Message msg) {
			SelectPicGroupActivity ac = mOuter.get();
			if (ac == null)
				return;

			switch (msg.what) {
			case SCAN_FINISH:
				Utils.dismissProgressDialog();
				ac.mGroupGrid.setAdapter(new PicGroupAdapter(ac.mContext,
						ac.mGroups, ac.mKeyList));
				break;

			default:
				break;
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_pic_group);
		setActionBarTitle(R.string.photo_album);

		mContext = this;
		mGroupGrid = (GridView) findViewById(R.id.select_pic_group_main_grid);
		mGroupGrid.setOnItemClickListener(this);
		mHandler = new SelectHandler(this);
		loadIamges();

		ImageLoaderConfiguration config = ImageLoaderConfiguration
				.createDefault(this);
		ImageLoader.getInstance().init(config);

		Intent intent = getIntent();
		maxSelect = intent.getIntExtra("max_select", 9); // 最多可选择

		showBackButton();
	}

	private void loadIamges() {
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			Toast.makeText(this, R.string.external_storage_diable,
					Toast.LENGTH_SHORT).show();
			return;
		}

		Utils.showProgressDialog(this, R.string.loading, false);

		new Thread(new Runnable() {

			@Override
			public void run() {
				Uri imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				Cursor cursor = getContentResolver().query(
						imageUri,
						null,
						MediaStore.Images.Media.MIME_TYPE + "=? or "
								+ MediaStore.Images.Media.MIME_TYPE + "=?",
						new String[] { "image/jpeg", "image/png" }, null);
				mGroups = new HashMap<String, ArrayList<CharSequence>>();
				while (cursor.moveToNext()) {
					String path = cursor.getString(cursor
							.getColumnIndex(MediaStore.Images.Media.DATA));
					String parentPath = new File(path).getParentFile()
							.getName();
					if (mGroups.containsKey(parentPath)) {
						mGroups.get(parentPath).add(path);
					} else {
						ArrayList<CharSequence> childList = new ArrayList<CharSequence>();
						childList.add(path);
						mGroups.put(parentPath, childList);
					}
				}
				cursor.close();
				mKeyList = new ArrayList<String>();
				Set<String> keySet = mGroups.keySet();
				for (Iterator<String> iterator = keySet.iterator(); iterator
						.hasNext();) {
					String str = iterator.next();
					mKeyList.add(str);
				}
				mHandler.sendEmptyMessage(SCAN_FINISH);
			}
		}).start();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent i = new Intent(this, SelectPicturesActivity.class);
		i.putExtra("max_select", maxSelect);
		i.putCharSequenceArrayListExtra("picture_group",
				mGroups.get(mKeyList.get(position)));
		startActivityForResult(i, maxSelect);
	}

	/**
	 * 返回用户选择的图片结果
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		setResult(resultCode, data);
		finish();
	}
}
