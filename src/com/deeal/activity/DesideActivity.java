package com.deeal.activity;

import com.example.deeal.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class DesideActivity extends Activity {
	
	ImageView iv_deside_add;
	LinearLayout deside_ll;
	Button bt_close,bt_deside;
	ImageButton bt_return;
	RelativeLayout deside_rl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_deside);
		 iv_deside_add= (ImageView) findViewById(R.id.iv_deside_add);
		 deside_ll=(LinearLayout) findViewById(R.id.deside_ll);
		 deside_rl=(RelativeLayout) findViewById(R.id.deside_rl);
		 bt_close=(Button) findViewById(R.id.bt_close);
		 bt_deside= (Button) findViewById(R.id.bt_deside);
		 bt_deside.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startPayActivity();
			}
		});
		 bt_return = (ImageButton) findViewById(R.id.bt_return);
		 bt_return.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		 bt_close.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				deside_rl.setVisibility(View.INVISIBLE);
			}
		});
		 iv_deside_add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				deside_ll.setVisibility(View.VISIBLE);
			}
		});
	}
	public void startPayActivity(){
		Intent i = new Intent();
		i.setClass(this, PayActivity.class);
		startActivity(i);
	}

}
