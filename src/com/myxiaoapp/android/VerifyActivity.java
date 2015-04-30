package com.myxiaoapp.android;

import java.io.UnsupportedEncodingException;

import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.myxiaoapp.listener.OnResponseListener;
import com.myxiaoapp.model.HttpRequestParams;
import com.myxiaoapp.model.HttpResponseHandler;
import com.myxiaoapp.model.RegisterInfo;
import com.myxiaoapp.network.AsyncHttpPost;
import com.myxiaoapp.network.SingleAsyncClient;
import com.myxiaoapp.network.XYClient;
import com.myxiaoapp.utils.Constant.RequestId;
import com.myxiaoapp.utils.Constant.RequestUrl;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 校内认证
 * 
 * @author JiangZhenJie
 * @date 2014-10-4
 */
public class VerifyActivity extends CommonActivity implements OnClickListener, OnResponseListener {

	private TextView mStuNum;
	private EditText mStuPasswd;
	private EditText mVerifyCode;
	private ImageView mVerifyImage;
	private TextView pullVerify;
	private Button mVerifySubmit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_verify);
		setActionBarTitle(R.string.school_verify);
		init();
		getVerifyImage();
	}

	@SuppressLint("ResourceAsColor")
	private void init() {
		mStuNum = (EditText) findViewById(R.id.student_num);
		mStuPasswd = (EditText) findViewById(R.id.student_passwd);
		mVerifyCode = (EditText) findViewById(R.id.verify_code);
		mVerifyImage = (ImageView) findViewById(R.id.verify_image);
		pullVerify = (TextView) findViewById(R.id.pull_verifyImage);
		pullVerify.setOnClickListener(this);
		mVerifySubmit = (Button) findViewById(R.id.vertify_submit);
		mVerifySubmit.setOnClickListener(this);
		mVerifySubmit.setClickable(false); // 初始为不可点击状态
		mVerifySubmit.setBackgroundColor(R.color.verify_false);

	}

	private void getVerifyImage() {
		//new AsyncHttpPost("CampusCode", this, RegisterInfo.getPhone()).post();
		new XYClient().post(
				RequestId.ID_CAMPUS_CODE, 
				RequestUrl.URL_CAMPUS_CODE, 
				HttpRequestParams.getVerifyParams(RegisterInfo.getPhone()), 
				this);
	}

	private boolean check(String student_code, String student_pwd,
			String verify_code) {
		if (student_code.equals("")) {
			Toast.makeText(this, "请输入学号", Toast.LENGTH_SHORT).show();
			return false;
		} else if (student_pwd.equals("")) {
			Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
			return false;
		} else if (verify_code.equals("")) {
			Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	private void verify(String student_code, String student_pwd, String verify_code) {
		//new AsyncHttpPost("*", this,student_code.toString(), student_pwd.toString(),verify_code.toString()).post();
//		new XYClient().post(
//				RequestId.ID_VERIFY, 
//				RequestUrl.URL_VERIFY, 
//				null, 
//				this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.vertify_submit:
			String student_code = mStuNum.getText().toString();
			String student_pwd = mStuPasswd.getText().toString();
			String verify_code = mVerifyCode.getText().toString();
			if (check(student_code, student_pwd, verify_code)) {
				verify(student_code, student_pwd, verify_code);
			}
			break;
		case R.id.pull_verifyImage:
			getVerifyImage();
			break;
		default:
			break;
		}
	}

	/* 
	 * @see com.myxiaoapp.listener.OnResponseListener#onFailure(int)
	 */
	@Override
	public void onFailure(int statusCode) {
	}

	/* 
	 * @see com.myxiaoapp.listener.OnResponseListener#onReceiveSuccess(java.lang.String, java.lang.String)
	 */
	@Override
	public void onReceiveSuccess(String rec, final int ID) {
		switch(ID){
		case 0:
			byte[] img = rec.getBytes();
			Bitmap bm = BitmapFactory.decodeByteArray(img, 0,
					img.length);
			mVerifyImage.setImageBitmap(bm);
			mVerifySubmit.setClickable(true);
			mVerifySubmit
					.setBackgroundResource(R.drawable.bg_verify_button);
			break;
		case RequestId.ID_CAMPUS_CODE:
			try {
				JSONObject jo = new JSONObject(rec);
				String picUrl = jo.getString("img_url").toString();
				//new AsyncHttpPost("verifyImage", this, picUrl).post();
				new XYClient().post(
						0, 
						picUrl, 
						null, 
						this);
			} catch (JSONException e) {
				e.printStackTrace();
			} 
			break;
		case RequestId.ID_VERIFY:
			try {
				JSONObject jo = new JSONObject(rec);
				if (jo.getString("errno").equals("20")) {
					RegisterInfo.recycle();// 回收RegisterInfo类保存的注册信息
					startActivity(new Intent(VerifyActivity.this,
							MainUIActivity.class));
					finish();
				} else
					Toast.makeText(VerifyActivity.this, "验证失败",
							Toast.LENGTH_LONG).show();
			} catch (JSONException e) {

				e.printStackTrace();
			}  
			break;
		default:
			break;
		}
	}

	/* 
	 * @see com.myxiaoapp.listener.OnResponseListener#onReceiveFailure(java.lang.String)
	 */
	@Override
	public void onReceiveFailure(String rec) {
	}
}
