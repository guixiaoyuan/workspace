package com.deeal.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.deeal.model.TagInfo;
import com.deeal.view.TagImageView;
import com.example.deeal.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class VerifyActivity extends Activity {
	// 返回按钮
	@ViewInject(R.id.back_imgButton)
	private ImageButton back_button;
	// 客服
	@ViewInject(R.id.tv_service)
	private TextView tv_service;
	// 显示照片
	@ViewInject(R.id.deside_image)
	private ImageView deside_image;
	
	// deside_image外面的layout
	@ViewInject(R.id.deside_relativeLayout)
	private RelativeLayout deside_relativeLayout;

	// 定制的2个view
	private ImageView img_transport;
	private EditText tag_text;

	// TagInfo存储的信息
	private Point point;
	private String info;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sample_deside);
		
		float scale = this.getResources().getDisplayMetrics().density;
		
		// 注入
		ViewUtils.inject(this);
		intiView();
		// 获取上个界面的图片
		Intent intent = getIntent();
		ArrayList<TagInfo> tagInfoList = new ArrayList<TagInfo>();
		if (intent != null) {
			String path = intent.getStringExtra("path");
			deside_image.setBackgroundDrawable(Drawable.createFromPath(path));
			
			Bundle bundle = intent.getExtras();
			ArrayList<String> tagList = (ArrayList<String>) bundle
					.getSerializable("tagList");
			for (String s : tagList) {
				TagInfo i = TagInfo.createFromString(s);

				tagInfoList.add(i);
			}
			for (int i = 0; i < tagInfoList.size(); i++) {
				TagInfo tagInfo = tagInfoList.get(i);
				point = tagInfo.getPt();
				info = tagInfo.getInfo();
				int x = point.x;
				int y = point.y;

				RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(
						ViewGroup.LayoutParams.WRAP_CONTENT,
						ViewGroup.LayoutParams.WRAP_CONTENT);
				

				params1.leftMargin = x;
				params1.topMargin = y-(int)(40*scale+0.5f);
				// 动态创建ImageView
				ImageView dot = new ImageView(this);
				dot.setImageResource(R.drawable.dot1);
				/*
				 * if(info!=null||!info.trim().equals("")){
				 * Log.e("~~~~~~~~~~~~~~~~~~~~~~~~~~",info);
				 * RelativeLayout.LayoutParams params2 = new
				 * RelativeLayout.LayoutParams(
				 * ViewGroup.LayoutParams.WRAP_CONTENT,
				 * ViewGroup.LayoutParams.WRAP_CONTENT);
				 * Log.e("~~~~~~~~~~~~~~~~~~~~~~~~~~",tag_text.toString());
				 * tag_text.setText(info);
				 * params2.addRule(RelativeLayout.ALIGN_RIGHT,
				 * R.id.img_transport);
				 * deside_relativeLayout.addView(img_transport, params1);
				 * deside_relativeLayout.addView(tag_text, params2); }else
				 */
				deside_relativeLayout.addView(dot, params1);
			}
			
			

		} else
			Toast.makeText(this, "传入的intent为空", 0).show();
	}

	@OnClick(R.id.back_imgButton)
	private void backBtnClick(View v) {
		/*
		 * Intent intent=new Intent(); intent.setClass(VerifyActivity.this,
		 * AddTagActivity.class); startActivity(intent);
		 */
		finish();
	}

	private void intiView() {
		this.back_button.getBackground().setAlpha(50);
		// this.tv_service.getBackground().setAlpha(50);
		/*
		 * View view= LayoutInflater.from(this).inflate(R.layout.tag, null);
		 * RelativeLayout relativeLayout=(RelativeLayout)
		 * view.findViewById(R.id.tag_layout);
		 * //relativeLayout.removeAllViews(); img_transport=(ImageView)
		 * view.findViewById(R.id.img_transport); tag_text=(EditText)
		 * view.findViewById(R.id.tag_text);
		 * Log.e("sadfasfdsafgasdfsad     init",img_transport.toString());
		 */
	}
}
