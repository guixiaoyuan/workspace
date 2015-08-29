package com.deeal.activity;

import com.deeal.tools.TabAdapter;
import com.example.deeal.R;
import com.viewpagerindicator.TabPageIndicator;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class MyCollectionActivity extends FragmentActivity{
	private ViewPager mViewPager;
	private TabPageIndicator mTabPageIndicator;
	private TabAdapter mAdapter;
	private ImageButton btn_return;
	private int pageid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mycollection);
		initView();
		setListeners();
	}
	private void initView() {
		this.mViewPager = (ViewPager) findViewById(R.id.id_mycollection_viewpager);
		mTabPageIndicator = (TabPageIndicator) findViewById(R.id.id_mycollection_indicator);
		mAdapter = new TabAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mAdapter);
		mTabPageIndicator.setViewPager(mViewPager, 0);
		this.btn_return = (ImageButton) findViewById(R.id.btn_mycollection_return);
		
	}
	private void setListeners() {
		btn_return.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	
		mTabPageIndicator.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				pageid = arg0;
				
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});

	}


}
