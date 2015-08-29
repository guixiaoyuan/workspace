package com.deeal.activity;

import com.example.deeal.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class GuideActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);
		boolean flag = this.isFirstEnter();
		if (flag)
			mHandler.sendEmptyMessageDelayed(SWITCH_GUIDACTIVITY, 1000);
		else
			mHandler.sendEmptyMessageDelayed(SWITCH_MAINACTIVITY, 1000);
	}

	// ***********************************************************************
	// �ж�Ӧ���Ƿ���μ��أ���ȡSharedPreferences ���ֶ�
	// ***********************************************************************


	private boolean isFirstEnter() {

		 SharedPreferences setting = getSharedPreferences("Version0.1", Activity.MODE_PRIVATE);
	        Boolean user_first = setting.getBoolean("FIRST",true);
	        if(user_first){//��һ��
	       return true;
	        }else{
	       return false;
	        }
	}

	// ****************************************
	// Handler:��ת����ͬҳ��
	// ****************************************
	private final static int SWITCH_MAINACTIVITY = 1000; // ��ҳ

	private final static int SWITCH_GUIDACTIVITY = 1001; // ��������
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case SWITCH_MAINACTIVITY:
				
				Intent intent = new Intent();
				intent.setClass(GuideActivity.this, MainActivity.class);
				GuideActivity.this.startActivity(intent);
				GuideActivity.this.finish();
				break;
			case SWITCH_GUIDACTIVITY:
				Intent intents = new Intent();
				intents.setClass(GuideActivity.this, FirstGuideActivity.class);
				GuideActivity.this.startActivity(intents);
				GuideActivity.this.finish();
				break;
			}
			super.handleMessage(msg);
		};
	};

}
