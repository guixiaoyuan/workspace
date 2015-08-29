package com.deeal.activity;

import com.example.deeal.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class FashionshowEditActivity extends Activity {
	
	private ImageButton btn_return;
	private TextView tv_pulish;
	private EditText et_fashionshow_title,et_fashionshow_content;
	private ImageView img_fashionshow_cover;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fashionshowedit);
		inititems();
	}
	private void inititems(){
		this.btn_return=(ImageButton) findViewById(R.id.btn_fashionshowedit_return);
		this.tv_pulish=(TextView) findViewById(R.id.tv_publish_show);
		this.et_fashionshow_content=(EditText) findViewById(R.id.et_fashionshow_content);
		this.et_fashionshow_title=(EditText) findViewById(R.id.et_fashionshow_title);
		this.img_fashionshow_cover=(ImageView) findViewById(R.id.img_fashionshow_cover);
		btn_return.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		tv_pulish.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}

}
