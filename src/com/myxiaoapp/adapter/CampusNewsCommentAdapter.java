package com.myxiaoapp.adapter;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.myxiaoapp.android.R;
import com.myxiaoapp.model.CampusCrclCmtBean;

public class CampusNewsCommentAdapter extends BaseAdapter {

	private static final String TAG = "CampusNewsCommentAdapter";
	Context mContext;
	int count = 0;
	List<CampusCrclCmtBean> datas;
	public CampusNewsCommentAdapter(Context mContext) {
		this.mContext = mContext;
		this.datas = new ArrayList<CampusCrclCmtBean>();
	}
	public void setComments(List<CampusCrclCmtBean> datas){
		this.datas = datas;
		Log.d(TAG, datas.toString());
	}
	
	public List<CampusCrclCmtBean> getComments(){
		return datas;
	}
	public void addItem(String name, String name_by, String comt_info){
		JSONObject jo = new JSONObject();
		try {
			jo.put("name", name);
			jo.put("name_by", name_by);
			jo.put("comt_info", comt_info);
			Gson gson = new Gson();
			CampusCrclCmtBean b = gson.fromJson(jo.toString(), CampusCrclCmtBean.class);
			datas.add(b);
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		
	}
	
	public void deleteItem(int position){
		datas.remove(position);
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
	public Object getItem(int position) {

		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder mViewHolder;
		CampusCrclCmtBean bean = datas.get(position);
		int flag ;//0：评论，，1.回复
		flag = (bean.getName_by() == null ? 0:1);
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.campus_news_reply_comment_item, null);
			mViewHolder = new ViewHolder();
			mViewHolder.replyPeople = (TextView) convertView
					.findViewById(R.id.reply_people);
			mViewHolder.label = (TextView)convertView.findViewById(R.id.reply);
			mViewHolder.commentPeople = (TextView) convertView
					.findViewById(R.id.comment_people);
			mViewHolder.content = (TextView) convertView
					.findViewById(R.id.comment);
			convertView.setTag(mViewHolder);
		} else {
			mViewHolder = (ViewHolder) convertView.getTag();
		}
		
		if(flag == 1){	//回复
			mViewHolder.label.setVisibility(View.VISIBLE);
			mViewHolder.commentPeople.setVisibility(View.VISIBLE);
			mViewHolder.replyPeople.setText(bean.getName());
			mViewHolder.commentPeople.setText(bean.getName_by());
			mViewHolder.content.setText(bean.getComt_info());
		}else {		//评论
			mViewHolder.label.setVisibility(View.GONE);
			mViewHolder.commentPeople.setVisibility(View.GONE);	
			mViewHolder.replyPeople.setText(bean.getName());
			mViewHolder.content.setText(bean.getComt_info());
		}
		return convertView;

	}

	private class ViewHolder {
		TextView replyPeople;
		TextView label;
		TextView commentPeople;
		TextView content;
	}
 
}
