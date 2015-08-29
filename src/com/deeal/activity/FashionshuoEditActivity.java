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

public class FashionshuoEditActivity extends Activity {

	private ImageButton btn_fashionshuoedit_return;
	private TextView tv_publish_shuo;
	private EditText et_fashionshuo_content;
	private ImageView img_fashionshuo_photo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fashionshuoedit);
		inititems();
		setListeners();
	}

	private void inititems() {
		this.btn_fashionshuoedit_return = (ImageButton) findViewById(R.id.btn_fashionshuoedit_return);
		this.tv_publish_shuo = (TextView) findViewById(R.id.tv_publish_shuo);
		this.et_fashionshuo_content = (EditText) findViewById(R.id.et_fashionshuo_content);
		this.img_fashionshuo_photo = (ImageView) findViewById(R.id.img_fashionshuo_photo);
	}

	private void setListeners() {
		btn_fashionshuoedit_return.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		tv_publish_shuo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

			}
		});
	}
}
