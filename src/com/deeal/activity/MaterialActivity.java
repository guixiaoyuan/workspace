package com.deeal.activity;

import com.deeal.fragment.Fragment_clothmaterical_selector;
import com.deeal.fragment.Fragment_clothtype_selector;
import com.deeal.model.FemalRes;
import com.deeal.view.CustomView;
import com.example.deeal.R;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class MaterialActivity extends Activity {

	private FragmentManager fm;
	private FragmentTransaction ft;
	
	private ImageButton btReturn;
	private Button btToEnsureOrder;
	private ImageView fontImg,backImg;
	private CustomView mCustomView;
	private LinearLayout mLayout;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_material);
		Intent i = getIntent();
		int type = i.getExtras().getInt("type");
		inititems();
		
		/*mCustomView = new CustomView(this);
		mCustomView.setDrawingCacheEnabled(true);
		
		 * MALE_TYPE 男装
		 * FEMALE_TYPE 女装
		 
		if(type==MainActivity.MALE_TYPE){
			mLayout.addView(mCustomView);
		}
		else{
			mLayout.addView(mCustomView);
		}*/
	}

	public void inititems() {

		this.mLayout = (LinearLayout) findViewById(R.id.layout);
		this.btReturn = (ImageButton) findViewById(R.id.btn_material_return);
		btReturn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		this.btToEnsureOrder = (Button) findViewById(R.id.bt_ensure);
		btToEnsureOrder.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startDesideActivity();
			}
		});
		fm = getFragmentManager();
		ft = fm.beginTransaction();
//		//ft.replace(R.id.clothmaterial_selector_container,
//				new Fragment_clothmaterical_selector());
		ft.replace(R.id.clothtype_selector_container,
				new Fragment_clothtype_selector());
		ft.commit();
	}
	private void startDesideActivity(){
		Intent i = new Intent();
		i.setClass(this, DesideActivity.class);
		startActivity(i);
	}
}
