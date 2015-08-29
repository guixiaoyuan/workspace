package com.deeal.activity;

import com.deeal.fragment.Fragment_order_all;
import com.deeal.fragment.Fragment_order_assess;
import com.deeal.fragment.Fragment_order_receive;
import com.deeal.fragment.Fragment_order_unpay;
import com.deeal.fragment.Fragment_order_unship;
import com.example.deeal.R;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

@SuppressLint("NewApi")
public class MyOrderActivity extends FragmentActivity implements
		OnClickListener {

	private RelativeLayout myorder_tab1, myorder_tab2, myorder_tab3,
			myorder_tab4, myorder_tab5;
	private TextView tv_tab1, tv_tab2, tv_tab3, tv_tab4, tv_tab5;
	private ViewPager mViewPager;
	private myPagerAdapter adapter;
	private ImageButton bt_back;
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myorder);
		bt_back=(ImageButton) findViewById(R.id.btn_myorder_return);
		bt_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
				
			}
		});
		inititems();
		setListeners();
	}
	
	
	

	@SuppressLint("NewApi")
	private void inititems() {
		this.myorder_tab1 = (RelativeLayout) findViewById(R.id.myorder_tab1);
		this.myorder_tab2 = (RelativeLayout) findViewById(R.id.myorder_tab2);
		this.myorder_tab3 = (RelativeLayout) findViewById(R.id.myorder_tab3);
		this.myorder_tab4 = (RelativeLayout) findViewById(R.id.myorder_tab4);
		this.myorder_tab5 = (RelativeLayout) findViewById(R.id.myorder_tab5);

		this.tv_tab1 = (TextView) findViewById(R.id.tv_tab1);
		this.tv_tab2 = (TextView) findViewById(R.id.tv_tab2);
		this.tv_tab3 = (TextView) findViewById(R.id.tv_tab3);
		this.tv_tab4 = (TextView) findViewById(R.id.tv_tab4);
		this.tv_tab5 = (TextView) findViewById(R.id.tv_tab5);
		settab();
		this.mViewPager = (ViewPager) findViewById(R.id.myorder_viewpager);
		adapter = new myPagerAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(adapter);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				resettab();
				switch (position) {
				case 0:
					tv_tab1.setBackground(getResources().getDrawable(
							R.drawable.tabselecor));
					tv_tab1.setTextColor(Color.BLACK);
					break;
				case 1:
					tv_tab2.setBackground(getResources().getDrawable(
							R.drawable.tabselecor));
					tv_tab2.setTextColor(Color.BLACK);

					break;
				case 2:
					tv_tab3.setBackground(getResources().getDrawable(
							R.drawable.tabselecor));
					tv_tab3.setTextColor(Color.BLACK);

					break;
				case 3:
					tv_tab4.setBackground(getResources().getDrawable(
							R.drawable.tabselecor));
					tv_tab4.setTextColor(Color.BLACK);

					break;
				case 4:
					tv_tab5.setBackground(getResources().getDrawable(
							R.drawable.tabselecor));
					tv_tab5.setTextColor(Color.BLACK);

					break;
				}
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

	public void resettab() {
		tv_tab1.setBackground(null);
		tv_tab2.setBackground(null);
		tv_tab3.setBackground(null);
		tv_tab4.setBackground(null);
		tv_tab5.setBackground(null);
		tv_tab1.setTextColor(Color.GRAY);
		tv_tab2.setTextColor(Color.GRAY);
		tv_tab3.setTextColor(Color.GRAY);
		tv_tab4.setTextColor(Color.GRAY);
		tv_tab5.setTextColor(Color.GRAY);
	}
	public void settab() {
		tv_tab1.setBackground(getResources().getDrawable(
				R.drawable.tabselecor));
		tv_tab2.setBackground(null);
		tv_tab3.setBackground(null);
		tv_tab4.setBackground(null);
		tv_tab5.setBackground(null);
		tv_tab1.setTextColor(Color.BLACK);
		tv_tab2.setTextColor(Color.GRAY);
		tv_tab3.setTextColor(Color.GRAY);
		tv_tab4.setTextColor(Color.GRAY);
		tv_tab5.setTextColor(Color.GRAY);
	}

	public void setListeners() {
		myorder_tab1.setOnClickListener(this);
		myorder_tab2.setOnClickListener(this);
		myorder_tab3.setOnClickListener(this);
		myorder_tab4.setOnClickListener(this);
		myorder_tab5.setOnClickListener(this);
	
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.myorder_tab1:
			
			mViewPager.setCurrentItem(0, true);
			break;
		case R.id.myorder_tab2:
			
			mViewPager.setCurrentItem(1,true);
			break;
		case R.id.myorder_tab3:
		
			mViewPager.setCurrentItem(2,true );
			break;
		case R.id.myorder_tab4:
			
			mViewPager.setCurrentItem(3,true);
			break;
		case R.id.myorder_tab5:
		
			mViewPager.setCurrentItem(4,true);
			break;
			
		
		}

	}

	
}

class myPagerAdapter extends FragmentPagerAdapter {
	private Fragment fragment;
	private Fragment_order_all mFragment_order_all =new Fragment_order_all();
	private Fragment_order_assess mFragment_order_assess =new Fragment_order_assess();
	private Fragment_order_receive mFragment_order_receive = new Fragment_order_receive();
	private Fragment_order_unpay mFragment_order_unpay =new  Fragment_order_unpay();
	private Fragment_order_unship mFragment_order_unship =new Fragment_order_unship();
	
	public myPagerAdapter(android.support.v4.app.FragmentManager fragmentManager) {
		super(fragmentManager);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int pageid) {
		// TODO Auto-generated method stub
		switch (pageid) {
		case 0:
			fragment = mFragment_order_all;
			break;
		case 1:
			fragment = mFragment_order_unpay;
			break;
		case 2:
			fragment = mFragment_order_unship;
			break;
		case 3:
			fragment = mFragment_order_receive;
			break;
		case 4:
			fragment = mFragment_order_assess;
			break;
		}
		return fragment;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 5;
	}
}
