package com.deeal.activity;

import com.example.deeal.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.deeal.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class EditMyInfoActivity extends Activity {
	public static final int TO_SELECT_PHOTO = 1;

	@OnClick(R.id.back_button)
	public void back_button(View arg0) {
		finish();
	}

	@OnClick(R.id.add_head)
	public void add_head(View arg0) {
		Intent intent = new Intent();
		intent.setClass(this, SelectPhotoActivity.class);
		startActivityForResult(intent, TO_SELECT_PHOTO);
		intent = null;
	}
	
	@OnClick(R.id.personalInfoSave)
	public void personalInfoSave(View arg0) {
		Toast.makeText(this, "ÒÑ±£´æ", Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_myinfo);
		ViewUtils.inject(this);
	}
	

}
