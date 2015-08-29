package com.deeal.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.deeal.tools.HomePageManager;
import com.example.deeal.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ViewSwitcher.ViewFactory;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MainActivity extends Activity implements ViewFactory,
		OnTouchListener {

	private ImageButton btnSlidingMenu;
	private ImageButton btnFashion;
	private SlidingMenu slidLog;
	private Button btnLogin;
	private Button btnRegister;
	private ImageView user_head;
	private TextView text_personalorder;

	private ListView mantype_list, womantype_list;

	private LinearLayout mMyCenter, mMyOrder, mMyCollection, mTimeWardrobe,
			mAddrManagement, mCustomerServiceMessage, mSettings, mtimeline;

	/**
	 * ImagaSwitcher 的引用
	 */
	private ImageSwitcher mImageSwitcher;
	/**
	 * 图片id数组
	 */
	private int[] imgIds;
	/**
	 * 当前选中的图片id序号
	 */
	private int currentPosition;
	/**
	 * 按下点的X坐标
	 */
	private float downX;
	/**
	 * 装载点点的容器
	 */
	private LinearLayout linearLayout;
	/**
	 * 点点数组
	 */
	private ImageView[] tips;
	private int DuringTime = 5000;
	public static final int TO_LOGIN = 1;
	public static final int TO_REGISTER = 2;

	private String[] types = { "T恤", "衬衫", "大衣", "外套", "卫衣", "毛衣", "羽绒服" };
	public static int MALE_TYPE = 0;
	public static int FEMALE_TYPE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		try {
			initItems();

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		imgIds = new int[] { R.drawable.item01, R.drawable.item02,
				R.drawable.item03, R.drawable.item04 };
		// 实例化ImageSwitcher
		mImageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitchShow);

		// 设置Factory
		mImageSwitcher.setFactory(this);
		// 设置OnTouchListener，我们通过Touch事件来切换图片

		mImageSwitcher.setOnTouchListener(this);

		linearLayout = (LinearLayout) findViewById(R.id.viewGroup);

		tips = new ImageView[imgIds.length];

		for (int i = 0; i < imgIds.length; i++) {
			ImageView mImageView = new ImageView(this);
			tips[i] = mImageView;
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));
			layoutParams.rightMargin = 3;
			layoutParams.leftMargin = 3;

			mImageView
					.setBackgroundResource(R.drawable.page_indicator_unfocused_show);
			linearLayout.addView(mImageView, layoutParams);
		}

		/*
		 * 初始化选中第一个图片 currentPosition = 0 ；
		 */
		mImageSwitcher.setImageResource(imgIds[currentPosition]);
		setImageBackground(currentPosition);
		mImageSwitcher.postDelayed(new Runnable() {
			public void run() {
				if (currentPosition == imgIds.length - 1) {
					currentPosition = 0;
				} else {
					currentPosition++;
				}
				mImageSwitcher.setImageResource(imgIds[currentPosition]);
				mImageSwitcher.postDelayed(this, DuringTime);
				setImageBackground(currentPosition);
			}
		}, DuringTime);

	}

	private void initlisttype() {

//		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
//		for (int i = 0; i < types.length; i++) {
//			HashMap<String, Object> map = new HashMap<String, Object>();
//			map.put("type", types[i]);
//			data.add(map);
//		}
//		String[] from = { "type" };
//		int[] to = { R.id.type };
//		ListAdapter adapter = new SimpleAdapter(this, data,
//				R.layout.main_list_item, from, to);
//		mantype_list.setAdapter(adapter);
//		mantype_list.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				// TODO Auto-generated method stu
//
//				startOrderActivity(MALE_TYPE);
//			}
//		});
//		womantype_list.setAdapter(adapter);
//		womantype_list.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				// TODO Auto-generated method stub
//
//				startOrderActivity(FEMALE_TYPE);
//			}
//		});
		ArrayList<ListView> lv = new ArrayList<ListView>();
		lv.add(mantype_list);
		lv.add(womantype_list);
		HomePageManager hpm = new HomePageManager(lv, this);
		hpm.updateData();
	}

	private void initItems() {
		this.btnSlidingMenu = (ImageButton) findViewById(R.id.btn_sliding);

		this.btnFashion = (ImageButton) findViewById(R.id.btn_fashion);
		this.slidLog = new SlidingMenu(this);
		this.slidLog.setMode(SlidingMenu.LEFT);
		// this.slidLog.setBehindOffsetRes(R.dimen.sliding_menu_offset);
		this.slidLog.setBehindOffset(getWindowManager().getDefaultDisplay()
				.getWidth() / 3);
		this.slidLog.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		this.slidLog.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		this.slidLog.setMenu(R.layout.sliding_menu_log);

		this.btnLogin = (Button) findViewById(R.id.btnLogin);
		this.btnRegister = (Button) findViewById(R.id.btnReg);

		this.btnLogin.setAlpha(100);
		this.btnRegister.setAlpha(100);
		// this.btnPersionalCenter.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

		this.mMyCenter = (LinearLayout) findViewById(R.id.ll_PersionalCenter);
		this.mMyOrder = (LinearLayout) findViewById(R.id.ll_MyOrder);
		this.mMyCollection = (LinearLayout) findViewById(R.id.ll_MyCollection);
		this.mTimeWardrobe = (LinearLayout) findViewById(R.id.ll_TimeWardrobe);
		this.mAddrManagement = (LinearLayout) findViewById(R.id.ll_AddrManagement);
		this.mCustomerServiceMessage = (LinearLayout) findViewById(R.id.ll_customservicemessage);
		this.mSettings = (LinearLayout) findViewById(R.id.ll_Settings);
		this.text_personalorder = (TextView) findViewById(R.id.tv_personalorder);
		this.mantype_list = (ListView) findViewById(R.id.mantype_list);
		this.womantype_list = (ListView) findViewById(R.id.womantype_list);
		this.user_head = (ImageView) findViewById(R.id.user_head);
		//this.text_personalorder = (TextView) findViewById(R.id.tv_personalorder);

		initlisttype();
		this.mTimeWardrobe.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startTimeLineActivity();

			}
		});
		this.user_head.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startEditMyInfoActivity();
			}
		});
		this.text_personalorder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startpersonalorderActivity();
			}
		});

		this.btnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startLoginActivity();

			}
		});

		this.btnRegister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startRegister();

			}
		});

		this.btnSlidingMenu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				slidLog.toggle(true);
			}
		});

		this.btnFashion.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startFashionActivity();
			}
		});

		this.mMyCenter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startMyCenter();
			}
		});

		this.mMyOrder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startMyOrderActivity();
			}
		});

		this.mMyCollection.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startMyCollectionActivity();
			}
		});

		this.mSettings.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startSettingActivity();
			}
		});
		this.mAddrManagement.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startAddrManageActivity();
			}
		});
		this.mCustomerServiceMessage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startCustomerServiceMessage();
			}
		});
	}

	/*
	 * the under voids is to create new Activity
	 */

	public void startTimeLineActivity() {
		Intent intent = new Intent();
		intent.setClass(this, ActivityTimeline.class);
		startActivity(intent);
	}

	public void startEditMyInfoActivity() {
		Intent intent = new Intent();
		intent.setClass(this, EditMyInfoActivity.class);
		startActivity(intent);
	}

	public void startLoginActivity() {
		Intent intent = new Intent();
		intent.setClass(this, LoginActivity.class);
		startActivityForResult(intent, TO_LOGIN);
		intent = null;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub

		if (Activity.RESULT_OK == resultCode) {
			switch (requestCode) {
			case TO_LOGIN:
				doLoginResult(data);
				break;
			case TO_REGISTER:
				break;
			default:
				break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	private void doLoginResult(Intent data){
		String TOKEN = "token";
		String token = data.getStringExtra(TOKEN);
	}

	public void startRegister() {
		Intent intent = new Intent();
		intent.setClass(this, RegisterActivity.class);
		startActivityForResult(intent, TO_REGISTER);
		intent = null;
	}

	public void startMyCenter() {
		Intent intent = new Intent();
		intent.setClass(this, MyCenterActivity.class);
		startActivity(intent);
	}

	public void startAddrManageActivity() {
		Intent intent = new Intent();
		intent.setClass(this, AddrManageActivity.class);
		startActivity(intent);
	}

	public void startCustomerServiceMessage() {
		Intent intent = new Intent();
		intent.setClass(this, CustomerServiceMessageActivity.class);
		startActivity(intent);

	}

	public void startSettingActivity() {
		Intent intent = new Intent();
		intent.setClass(this, SettingActivity.class);
		startActivity(intent);
	}

	public void startFashionActivity() {
		Intent intent = new Intent();
		intent.setClass(this, FashionActivity.class);
		startActivity(intent);
	}

	public void startMyOrderActivity() {
		Intent intent = new Intent();
		intent.setClass(this, MyOrderActivity.class);
		startActivity(intent);
	}

	private void startMyCollectionActivity() {
		Intent intent = new Intent();
		intent.setClass(this, MyCollectionActivity.class);
		startActivity(intent);
	}

	private void startOrderActivity(int type) {
		Intent intent = new Intent();
		intent.setClass(this, MaterialActivity.class);
		Bundle bundle = new Bundle();
		bundle.putInt("cloth_type", type);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	private void startpersonalorderActivity() {
		Intent intent = new Intent();
		intent.setClass(this, PersonalorderActivity.class);
		startActivity(intent);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_MENU:
			slidLog.toggle(true);
			break;
		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onBackPressed() {
		if (slidLog.isMenuShowing()) {
			slidLog.showContent();
		} else {
			super.onBackPressed();
		}
	}

	/**
	 * 设置选中的tip的背景
	 * 
	 * @param selectItems
	 */
	private void setImageBackground(int selectItems) {
		for (int i = 0; i < tips.length; i++) {
			if (i == selectItems) {
				tips[i].setBackgroundResource(R.drawable.page_indicator_focused_show);
			} else {
				tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused_show);
			}
		}
	}

	@Override
	public View makeView() {
		final ImageView i = new ImageView(this);
		i.setBackgroundColor(0xff000000);
		i.setScaleType(ImageView.ScaleType.CENTER_CROP);
		i.setLayoutParams(new ImageSwitcher.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		return i;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			// 手指按下的X坐标
			downX = event.getX();
			break;
		}
		case MotionEvent.ACTION_UP: {
			float lastX = event.getX();
			// ̧抬起的时候的X坐标大于按下的时候就显示上一张图片
			if (lastX > downX) {
				if (currentPosition > 0) {
					// 设置动画
					mImageSwitcher.setInAnimation(AnimationUtils.loadAnimation(
							getApplication(), R.anim.left_in));
					mImageSwitcher.setOutAnimation(AnimationUtils
							.loadAnimation(getApplication(), R.anim.right_out));
					currentPosition--;
					mImageSwitcher.setImageResource(imgIds[currentPosition
							% imgIds.length]);
					setImageBackground(currentPosition);
				} else {
					Toast.makeText(getApplication(), "这是第一张图片",
							Toast.LENGTH_SHORT).show();
				}
			}

			if (lastX < downX) {
				if (currentPosition < imgIds.length - 1) {
					mImageSwitcher.setInAnimation(AnimationUtils.loadAnimation(
							getApplication(), R.anim.right_in));
					mImageSwitcher.setOutAnimation(AnimationUtils
							.loadAnimation(getApplication(), R.anim.lift_out));
					currentPosition++;
					mImageSwitcher.setImageResource(imgIds[currentPosition]);
					setImageBackground(currentPosition);
				} else {
					Toast.makeText(getApplication(), "已经是最后一张图片",
							Toast.LENGTH_SHORT).show();
					currentPosition = 0;
					mImageSwitcher.setInAnimation(AnimationUtils.loadAnimation(
							getApplication(), R.anim.right_in));
					mImageSwitcher.setOutAnimation(AnimationUtils
							.loadAnimation(getApplication(), R.anim.lift_out));
					mImageSwitcher.setImageResource(imgIds[currentPosition]);
					setImageBackground(currentPosition);
				}
			}
		}

			break;
		}

		return true;
	}
}
