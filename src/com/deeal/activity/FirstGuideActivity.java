package com.deeal.activity;

import java.util.ArrayList;

import com.deeal.tools.GuidePagerAdapter;
import com.example.deeal.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class FirstGuideActivity extends Activity {

	private ViewPager viewPager;
	private ImageButton bt_goMain;
	/** װ��ҳ��ʾ��view������ */
	private ArrayList<View> pageViews;
	private ImageView dot1, dot2, dot3, dot4;
	private ArrayList<ImageView> dots;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_firstguide);
		SharedPreferences setting = getSharedPreferences("Version0.1",
				Activity.MODE_PRIVATE);

		inititems();
		setting.edit().putBoolean("FIRST", false).commit();
		LayoutInflater inflater = getLayoutInflater();
		pageViews = new ArrayList<View>();
		pageViews.add(inflater.inflate(R.layout.guide1, null));
		pageViews.add(inflater.inflate(R.layout.guide2, null));
		pageViews.add(inflater.inflate(R.layout.guide3, null));
		pageViews.add(inflater.inflate(R.layout.guide4, null));

		dots = new ArrayList<ImageView>();
		dots.add(dot1);
		dots.add(dot2);
		dots.add(dot3);
		dots.add(dot4);

		viewPager.setAdapter(new GuidePagerAdapter(pageViews));
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				for (int i = 0; i < 4; i++) {
					dots.get(i).setBackgroundResource(
							R.drawable.page_indicator_unfocused);
				}
				dots.get(position).setBackgroundResource(
						R.drawable.page_indicator_focused);
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

	private void inititems() {
		this.dot1 = (ImageView) findViewById(R.id.dot1);
		this.dot2 = (ImageView) findViewById(R.id.dot2);
		this.dot3 = (ImageView) findViewById(R.id.dot3);
		this.dot4 = (ImageView) findViewById(R.id.dot4);
		this.viewPager = (ViewPager) findViewById(R.id.viewpager);
		this.bt_goMain=(ImageButton) findViewById(R.id.bt_gomain);
		bt_goMain.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				goMain();
			}
		});
	}
	public void goMain(){
		Intent intent=new Intent();
		intent.setClass(this, MainActivity.class);
		startActivity(intent);
		finish();
	}
}
