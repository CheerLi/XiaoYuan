package com.myxiaoapp.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.myxiaoapp.android.CampusNewsActivity;
import com.myxiaoapp.android.MyHomePageFragment;
import com.myxiaoapp.android.PreviewImageActivity;
import com.myxiaoapp.android.PreviewImageDialog;
import com.myxiaoapp.android.R;
import com.myxiaoapp.model.LastPicBean;
import com.myxiaoapp.utils.Constant;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader; 
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * 校园圈 界面 相册GridView的Adapter
 * 
 * @author JiangZhenJie
 * @date 2014-9-20
 * @param flag
 *            flag = 0 :校园圈 flag = 1 :“个人资料”和“我”
 */
public class PhotoAdapter extends BaseAdapter implements OnClickListener {

	private Context mContext;
	private int flag;
	private ImageLoader imageLoader;
	private DisplayImageOptions options;
/*	List<Integer> resList = new ArrayList<Integer>();
	int[] resIds = { R.drawable.show_pic_1, R.drawable.show_pic_2,
			R.drawable.show_pic_3, R.drawable.show_pic_4, R.drawable.show_pic_5 };
*/
	List<String> spics = new ArrayList<String>();
	List<String> pics = new ArrayList<String>();
	/**
	 * @param context
	 * @param flag2
	 */
	public PhotoAdapter(Context context, int flag) {
		this.mContext = context;
		this.flag = flag;
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
		options = new DisplayImageOptions.Builder().cacheInMemory(true)
				.cacheOnDisk(true).build();
	}
	
	
	/**
	 * 该构造函数解决查看别人的个人信息与查看自己的个人信息时，个人相册的最后一张是否加添加功能 当查看别人的个人相册时，调用该构造方法
	 * 
	 * @param context
	 * @param flag1
	 *            Constant.FLAG_ME
	 * @param flag2
	 *            Constant.FLAG_CAMPUS
	 */
	public PhotoAdapter(Context context, int flag1, int flag2) {
		this(context, flag2);
	}
	
	public void setSPics(List<String> m_spictures){
		if(m_spictures == null) return;
		spics.clear();
		for (String i : m_spictures) {
			spics.add(i);
		}
	}
	public void setPics(List<String> m_pictures){
		if(m_pictures == null) return;
		pics.clear();
		for (String i : m_pictures) {
			pics.add(i);
		}
	}
	

	public void setLastPics(List<String> m_spictures, List<String> m_pictures){
		spics = m_spictures;
		pics = m_pictures;
		this.notifyDataSetChanged();
	}


	@Override
	public int getCount() {
		return spics.size();
	}

	@Override
	public Object getItem(int position) {
		return spics.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.grid_photos_item, null);
			viewHolder = new ViewHolder();
			viewHolder.imageView = (ImageView) convertView
					.findViewById(R.id.photo);
			viewHolder.imageView.setOnClickListener(this);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
/*8
		int resId = (Integer) getItem(position);
		Drawable drawable = mContext.getResources().getDrawable(resId);
		viewHolder.imageView.setImageDrawable(drawable);
*/		Log.d("PhotoAdapter",spics.get(position));
		viewHolder.imageView.setTag(pics.get(position));
		imageLoader.displayImage(spics.get(position), viewHolder.imageView,options);
		return convertView;
	}

	private class ViewHolder {
		ImageView imageView;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.photo:
			if(flag == Constant.FLAG_ME) { 
			}else{
				ImageView imageView = (ImageView) v;
				new PreviewImageDialog(mContext,(String) imageView.getTag()).show();
	/*			ImageView imageView = (ImageView) v;
				Intent intent = new Intent(mContext, PreviewImageActivity.class);
				intent.putExtra("image_url", (String) imageView.getTag());
				mContext.startActivity(intent);
	 */			
			}
			break;


		default:
			break;
		}
	}


}