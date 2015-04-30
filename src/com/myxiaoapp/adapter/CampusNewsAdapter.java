package com.myxiaoapp.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myxiaoapp.android.CampusNewsActivity;
import com.myxiaoapp.android.CampusNewsDetailsActivity;
import com.myxiaoapp.android.HomePageActivity;
import com.myxiaoapp.android.R;
import com.myxiaoapp.android.XiaoYuanApp;
import com.myxiaoapp.listener.OnResponseListener;
import com.myxiaoapp.model.CampusCircleBean;
import com.myxiaoapp.model.MomentBean;
import com.myxiaoapp.network.AsyncHttpPost;
import com.myxiaoapp.utils.Constant;
import com.myxiaoapp.view.CircleImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class CampusNewsAdapter extends BaseAdapter implements OnClickListener {
	private final String TAG = "CampusNewsAdapter";
	private Context mContext;
	private String CONSTANTLOG = "CampusNewsAdapter:";
	// test data
	private int count = 10;
	private List<MomentBean> datas;
	private ImageLoader imageLoader;
	private DisplayImageOptions options;
	private final int flag;

	public CampusNewsAdapter(Context c, int flag) {
		mContext = c;
		this.flag = flag;
		datas = new ArrayList<MomentBean>();
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
		options = new DisplayImageOptions.Builder().cacheInMemory(true)
				.cacheOnDisk(true).build();
	}
	public int getMinId(){
		return Integer.parseInt( datas.get(datas.size()-1).getM_id() );
	}
	public void addHeadData(List<MomentBean> beanList) {
		datas.addAll(0, beanList);
	}

	public void addFootData(List<MomentBean> beanList){
		datas.addAll(beanList);
	}
	public void update(int position, MomentBean bean){
		datas.set(position, bean);
	}
	public void clear(){
		datas.clear();
	}
	/* 
	 * @see android.widget.BaseAdapter#notifyDataSetChanged()
	 */
	@Override
	public void notifyDataSetChanged() {
		
		super.notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public MomentBean getItem(int position) {
		return datas.get(position);
	}
	public void remove(int position){
		datas.remove(position);
		notifyDataSetChanged();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// ArrayList<String> data = datas.get(position);
		Log.d("mydebug", CONSTANTLOG + "getView");
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.campus_news_item, null);
			viewHolder = new ViewHolder();
			/*
			 * // 封面 viewHolder.coverLayout = (RelativeLayout) convertView
			 * .findViewById(R.id.rl_cover); viewHolder.myPortrait =
			 * (CircleImageView) convertView .findViewById(R.id.ci_my_portrait);
			 * viewHolder.myName = (TextView) convertView
			 * .findViewById(R.id.tv_my_name); viewHolder.mySign = (TextView)
			 * convertView .findViewById(R.id.tv_personal_sign);
			 */
			// 校园圈
			viewHolder.commonNewsLayout = (LinearLayout) convertView
					.findViewById(R.id.ll_common_news);
			viewHolder.commonNewsLayout.setOnClickListener(this);

			viewHolder.portrait = (ImageView) convertView
					.findViewById(R.id.portrait);
			viewHolder.portrait.setOnClickListener(this);

			viewHolder.userName = (TextView) convertView
					.findViewById(R.id.tv_user_name);
			// viewHolder.userName.setText(data.get(0));

			viewHolder.dateTime = (TextView) convertView
					.findViewById(R.id.tv_time);
			// viewHolder.dateTime.setText(data.get(1));

			viewHolder.newsContent = (TextView) convertView
					.findViewById(R.id.tv_content);
			// viewHolder.newsContent.setText(data.get(2));

			viewHolder.newsPhotos = (GridView) convertView
					.findViewById(R.id.news_photos);

			viewHolder.newsPhotos.setAdapter(new PhotoAdapter(mContext,
					Constant.FLAG_CAMPUS));
			
			viewHolder.like_c = (TextView)convertView.findViewById(R.id.like);
			viewHolder.like_c.setOnClickListener(this);
		//	viewHolder.like = (TextView) convertView.findViewById(R.id.tv_like);
			// viewHolder.like.setText(data.get(data.size()-2));

			viewHolder.comment_c = (TextView)convertView.findViewById(R.id.comment);
			viewHolder.comment_c.setOnClickListener((CampusNewsActivity)mContext);
	//		viewHolder.comment = (TextView) convertView
	//				.findViewById(R.id.tv_comment);
			// viewHolder.comment.setText(data.get(data.size()-1));

			viewHolder.delete = (TextView) convertView
					.findViewById(R.id.delete);
			viewHolder.delete.setOnClickListener(this);
			if (((CampusNewsActivity)mContext).getUid().equals(XiaoYuanApp.getLoginUser(mContext).userBean.getUid())) {
				viewHolder.delete.setVisibility(View.VISIBLE);
			}

			viewHolder.photoAdapter = new PhotoAdapter(mContext,
					Constant.FLAG_CAMPUS);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.commonNewsLayout.setTag(position);
		viewHolder.like_c.setTag(datas.get(position).getM_id());
		viewHolder.comment_c.setTag(position);
		viewHolder.delete.setTag(position);
		/*
		 * if (position == 0) { // 显示封面
		 * viewHolder.commonNewsLayout.setVisibility(View.GONE);
		 * viewHolder.coverLayout.setVisibility(View.VISIBLE); } else { // 显示校园圈
		 * viewHolder.commonNewsLayout.setVisibility(View.VISIBLE);
		 * viewHolder.coverLayout.setVisibility(View.GONE); }
		 */
		viewHolder.commonNewsLayout.setVisibility(View.VISIBLE);
		Log.d(TAG, position+"bean="+datas.get(position));
		setData(datas.get(position), viewHolder);
		return convertView;
	}

	private void setData(MomentBean bean, ViewHolder holder) {
		// bean.setM_spictures(bean.getM_spictures());
		// bean.setM_pictures(bean.getM_pictures());
		// bean.setS_portrait(bean.getS_portrait());
		bean.setM_info(bean.getM_info());
		Log.d(TAG, "setData=" + bean.getM_id());
		holder.userName.setText(bean.getName());
		SimpleDateFormat sdf=new SimpleDateFormat("MM-dd hh:mm");    
		String sd = sdf.format(new Date(Long.parseLong(bean.getM_time())*1000));
		holder.dateTime.setText(sd);
		if(bean.getM_info().equals("")){
			holder.newsContent.setVisibility(View.GONE);
		}else{
			holder.newsContent.setText(bean.getM_info());
		}	
		holder.like_c.setText(bean.getM_likesnum());
		holder.comment_c.setText(bean.getM_commentnum());
		holder.newsPhotos.setAdapter(holder.photoAdapter);
		holder.photoAdapter.setSPics(bean.getM_spictures());
		holder.photoAdapter.setPics(bean.getM_pictures());
		holder.photoAdapter.notifyDataSetChanged();
		imageLoader.displayImage(bean.getS_portrait(), holder.portrait, options);
	}

	private class ViewHolder {
		// 封面
		// RelativeLayout coverLayout;
		// CircleImageView myPortrait;
		// TextView myName;
		// TextView mySign;

		// 校园圈列表
		LinearLayout commonNewsLayout;
		ImageView portrait;
		TextView userName;
		TextView dateTime;
		TextView newsContent;
		GridView newsPhotos;
		TextView like_c;
	//	TextView like;
//		TextView comment;
		TextView comment_c;
		TextView delete;
		PhotoAdapter photoAdapter;
	}

	@Override
	public void onClick(View v) {
		Log.d(TAG,v.getId()+"is clicked");
		switch (v.getId()) {
		case R.id.comment:
		case R.id.ll_common_news:
			Intent intent = new Intent(mContext, CampusNewsDetailsActivity.class);
			int position = Integer.parseInt( v.getTag().toString() );
			MomentBean b = (MomentBean) getItem(position);
			Log.d(TAG, "comment position="+position);
			Log.d(TAG, "b="+b.toString());
			intent.putExtra("bean", b);
			mContext.startActivity(intent);
			break;
		case R.id.portrait:
			mContext.startActivity(new Intent(mContext, HomePageActivity.class));
			break;
		case R.id.delete:
			((CampusNewsActivity)mContext).dialog(v.getTag().toString());
			break;
		case R.id.like:
			Log.d(TAG,"点赞");
			((CampusNewsActivity)mContext).addLike(v.getTag().toString());
		//	new AsyncHttpPost("addlike", this, v.getTag().toString(), XiaoYuanApp.getLoginUser(mContext).userBean.getUid()).post();
			TextView like = (TextView)v.findViewById(R.id.like);
			like.setText( (Integer.parseInt(like.getText().toString() )+1 )+"");
			break; 
		default:
			break;
		}
	} 

}
