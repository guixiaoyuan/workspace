package com.deeal.activity;


import com.example.deeal.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class CustomerServiceMessageActivity extends Activity {
	private ImageButton bt_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_custonmer_service_message);
		bt_back=(ImageButton) findViewById(R.id.btn_customerservicemessage_return);
		bt_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
				
			}
		});
	}
	
}

