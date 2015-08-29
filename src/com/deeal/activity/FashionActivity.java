package com.deeal.activity;

import java.util.ArrayList;

import com.deeal.tools.TabAdapter;
import com.example.deeal.R;
import com.viewpagerindicator.TabPageIndicator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class FashionActivity extends FragmentActivity {
	private ViewPager mViewPager;
	private TabPageIndicator mTabPageIndicator;
	private TabAdapter mAdapter;
	private ImageButton btn_return, btn_edit;
	private int pageid = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fashion);
		initView();
		setListeners();
	}

	private void initView() {
		this.mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
		mTabPageIndicator = (TabPageIndicator) findViewById(R.id.id_indicator);
		mAdapter = new TabAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mAdapter);
		mTabPageIndicator.setViewPager(mViewPager, 0);
		this.btn_return = (ImageButton) findViewById(R.id.btn_fashion_return);
		this.btn_edit = (ImageButton) findViewById(R.id.btn_fashion_edit);
	}

	private void setListeners() {
		btn_return.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		btn_edit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				startedit(pageid);
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

	private void startedit(int position) {
		ArrayList<Class> editclass = new ArrayList<Class>();
		editclass.add(FashionshowEditActivity.class);
		editclass.add(FashionshuoEditActivity.class);
		Intent intent = new Intent();
		intent.setClass(this, editclass.get(position));
		startActivity(intent);
	}

}
