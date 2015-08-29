package com.deeal.activity;

import com.example.deeal.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ToggleButton;

public class SettingActivity extends Activity {
	private Button bt_unregister;
	private ImageButton bt_setting_return,
			bt_changepassword, bt_customer_service;
	private ToggleButton bt_notification_setting;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		inititems();
		setListeners();
	}

	/*
	 *
	 */

	private void inititems() {
		this.bt_changepassword = (ImageButton) findViewById(R.id.bt_changepassword);
		this.bt_customer_service = (ImageButton) findViewById(R.id.bt_customer_service);
		this.bt_notification_setting = (ToggleButton) findViewById(R.id.bt_notification_setting);
		this.bt_setting_return = (ImageButton) findViewById(R.id.btn_setting_return);
		this.bt_unregister = (Button) findViewById(R.id.bt_unregister);
	}

	/*
	 *
	 */
	private void setListeners() {
		
		/*
		 *
		 */
		bt_changepassword.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				start_changepassword();
			}
		});

		/*
		 *
		 */
		bt_customer_service.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				start_customer_service();
			}
		});

		/*
		 *
		 */
		bt_notification_setting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {	
				start_notification_setting();
			}
		});
		
		/*
		 *
		 */
		bt_setting_return.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				setting_return();
			}
		});
		
		/*
		 *
		 */
		bt_unregister.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				unregister();
			}
		});
	}
	
	/*
	 * 
	 */
	public void unregister(){
		finish();
	}
	
	/*
	 * 
	 */
	public void setting_return(){
		finish();
	}
	
	/*
	 * 
	 */
	public void start_notification_setting(){
		
	}
	
	/*
	 * 
	 */
	public void start_customer_service(){
		Intent i = new Intent();
		i.setClass(this, CustomerServiceMessageActivity.class);
		startActivity(i);
		finish();
	}
	
	/*
	 * 
	 */
	public void start_changepassword(){
		Intent i = new Intent();
		System.out.println("bbbbbbbbbbbbbbbbbbb");
		i.setClass(this, ModifyPasswordActivity.class);
		startActivity(i);
		finish();
	}
}
