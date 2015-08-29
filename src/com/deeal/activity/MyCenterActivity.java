package com.deeal.activity;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.deeal.fragment.FragmentMycenterMessages;
import com.deeal.fragment.FragmentMycenterThings;
import com.deeal.model.mycenter.MyCenterInfo;
import com.deeal.tools.mycenter.MyCenterManager;
import com.deeal.view.RoundImageView;
import com.example.deeal.R;

@SuppressLint("NewApi")
public class MyCenterActivity extends FragmentActivity implements
		OnClickListener {

	// 添加中心信息
	private MyCenterInfo myCenterInfo;
	// 设置中心信息
	private MyCenterManager setMyCenterInfo;
	private View view;
	private RoundImageView img_user_head;

	private RelativeLayout mycenterTab1, mycenterTab2;
	private TextView tv_user_name, tvTab1, tvTab2, tvFriendsNum, tvFansNum;
	private ViewPager mViewPager;
	private ImageButton bt_return, bt_edit;
	private mycenterPagerAdapter mPagerAdapter;
	private Map<String, Object> dataMap = new HashMap<String, Object>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mycenter);
		inititems();
	}

	private void inititems() {
		// BitmapUtils bitmapUtils = new BitmapUtils(getApplicationContext());

		// this.imgUrl = "http://bbs.lidroid.com/static/image/common/logo.png";

		this.mycenterTab1 = (RelativeLayout) findViewById(R.id.mycenter_tab1);
		this.mycenterTab2 = (RelativeLayout) findViewById(R.id.mycenter_tab2);
		this.img_user_head = (RoundImageView) findViewById(R.id.img_user_head);
		this.tv_user_name = (TextView) findViewById(R.id.tv_user_name);
		this.tvTab1 = (TextView) findViewById(R.id.tv_mycenter_tab1);
		this.tvTab2 = (TextView) findViewById(R.id.tv_mycenter_tab2);
		this.bt_edit = (ImageButton) findViewById(R.id.btn_mycenter_edit);
		this.bt_return = (ImageButton) findViewById(R.id.btn_mycenter_return);
		this.tvFansNum = (TextView) findViewById(R.id.tv_fans_number);
		this.tvFriendsNum = (TextView) findViewById(R.id.tv_friends_number);
		settab();

		// //////////////
/*		view = LayoutInflater.from(getApplicationContext()).inflate(
				R.layout.info_item, null);
		RoundImageView item_image_head = (RoundImageView) view
				.findViewById(R.id.image_head);
		TextView tv_id = (TextView) view.findViewById(R.id.tv_id);
		RoundImageView item_image_show = (RoundImageView) view
				.findViewById(R.id.image_show);
		TextView item_time = (TextView) view.findViewById(R.id.item_time);
		TextView tv_store_num = (TextView) view.findViewById(R.id.tv_store_num);
		TextView tv_prefer_num = (TextView) view
				.findViewById(R.id.tv_prefer_num);
		TextView tv_comment_num = (TextView) view
				.findViewById(R.id.tv_comment_num);
		TextView item_username = (TextView) view
				.findViewById(R.id.item_username);
		TextView tv_comment_content = (TextView) view
				.findViewById(R.id.tv_comment_content);*/

		
		this.mViewPager = (ViewPager) findViewById(R.id.mycenter_viewpager);
		mPagerAdapter = new mycenterPagerAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int pageid) {
				// TODO Auto-generated method stub

				switch (pageid) {
				case 0:
					resettab();
					tvTab1.setBackground(getResources().getDrawable(
							R.drawable.tabselecor_black));
					break;
				case 1:
					resettab();
					tvTab2.setBackground(getResources().getDrawable(
							R.drawable.tabselecor_black));
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
		tvTab1.setOnClickListener(this);
		tvTab2.setOnClickListener(this);
		bt_edit.setOnClickListener(this);
		bt_return.setOnClickListener(this);
		tvFansNum.setOnClickListener(this);
		tvFriendsNum.setOnClickListener(this);

		// 实例化获取中心信息的类
		setMyCenterInfo = new MyCenterManager(null, MyCenterActivity.this);
		// 得到中心类的实体类
		myCenterInfo = setMyCenterInfo.setMyCenterInfo();

		// 设置头像
		// bitmapUtils.display(img_user_head, imgUrl);

		// //////////////////////////////////测试
		setMyCenterInfo.getUserInfo(tv_user_name, img_user_head);
		setMyCenterInfo.getFriendCount(tvFriendsNum);
		setMyCenterInfo.getFansCount(tvFansNum);

	}

	private void resettab() {
		tvTab1.setBackground(null);
		tvTab2.setBackground(null);
	}

	private void settab() {
		tvTab1.setBackground(getResources().getDrawable(
				R.drawable.tabselecor_black));
		tvTab2.setBackground(null);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.tv_mycenter_tab1:

			mViewPager.setCurrentItem(0);
			break;
		case R.id.tv_mycenter_tab2:

			mViewPager.setCurrentItem(1);
			break;
		case R.id.btn_mycenter_edit:
			startEdit();
			break;
		case R.id.btn_mycenter_return:
			finish();
			break;
		case R.id.tv_fans_number:
			startFansActivity();
			break;
		case R.id.tv_friends_number:
			startFriendsActivity();
			break;
		}
	}

	public void startEdit() {
		Intent i = new Intent();
		i.setClass(this, FashionshuoEditActivity.class);
		startActivity(i);
	}

	public void startFansActivity() {
		Intent i = new Intent();
		i.setClass(this, FansListActivity.class);
		startActivity(i);
	}

	public void startFriendsActivity() {
		Intent i = new Intent();
		i.setClass(this, FriendsListActivity.class);
		startActivity(i);
	}

}

class mycenterPagerAdapter extends FragmentPagerAdapter {
	private Fragment fragment;
	private FragmentMycenterThings mFragment_mycenter_things = new FragmentMycenterThings();
	private FragmentMycenterMessages mFragment_mycenter_message = new FragmentMycenterMessages();

	public mycenterPagerAdapter(
			android.support.v4.app.FragmentManager fragmentManager) {
		super(fragmentManager);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public Fragment getItem(int pageid) {
		// TODO Auto-generated method stub
		switch (pageid) {
		case 0:
			fragment = mFragment_mycenter_things;
			break;
		case 1:
			fragment = mFragment_mycenter_message;
			break;
		}
		return fragment;
	}

}
