package com.deeal.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.deeal.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.thinkland.sdk.sms.SMSCaptcha;

public class FindpasswordActivity extends Activity {
	
	private Timer mTimer = null;
	private TimerTask mTimerTask = null;
	private static final int UPDATE_TEXTVIEW = 99;
	private static int count = 120;
	private SMSCaptcha captcha;
	String userName;
	String userPhone;
	private static int delay = 1 * 1000; // 1s
	private static int period = 1 * 1000; // 1s

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_findpassword);
		ViewUtils.inject(this);
		et_userName.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				if (s.length() == 11) {
					bt_send_captcha.setEnabled(true);
				} else {
					bt_send_captcha.setEnabled(false);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub

			}
		});
		captcha = SMSCaptcha.getInstance();
		bt_send_captcha.setEnabled(false);
	}
	@ViewInject(R.id.et_userName)
	private EditText et_userName;
	@ViewInject(R.id.et_captcha)
	private EditText et_captcha;
	@ViewInject(R.id.et_newpassword)
	private EditText et_newpassword;
	@ViewInject(R.id.bt_send_captcha)
	private Button bt_send_captcha;
	
	@OnClick(R.id.btn_findpassword_return)
	public void btnfindpasswordClick(View args){
		finish();
	}
	@OnClick(R.id.bt_relogin)
	public void btRelogin(View args){
		finish();
	}
	@OnClick(R.id.bt_send_captcha)
	public void btSendCaptcha(View args){
		userPhone = getName();
		startTimer();
	}
	private String getName() {
		// TODO Auto-generated method stub
		userName = et_userName.getText().toString();
		return userName;
	}
	// 发送按钮定时操作
	/*
	 * 启动Timer
	 */
	private void startTimer() {

		stopTimer();
		// 输入框不可用，获取验证码按钮不可用
		et_userName.setEnabled(false);
		bt_send_captcha.setEnabled(false);

		if (mTimer == null) {
			mTimer = new Timer();
		}
		if (mTimerTask == null) {
			mTimerTask = new TimerTask() {
				@Override
				public void run() {
					Message message = Message.obtain(handler, UPDATE_TEXTVIEW);
					handler.sendMessage(message);
					count--;
				}
			};
		}

		if (mTimer != null && mTimerTask != null)
			mTimer.schedule(mTimerTask, delay, period);
	}
	/**
	 * 停止Timer
	 */
	private void stopTimer() {

		bt_send_captcha.setEnabled(true);
		et_userName.setEnabled(true);

		if (mTimer != null) {
			mTimer.cancel();
			mTimer = null;
		}

		if (mTimerTask != null) {
			mTimerTask.cancel();
			mTimerTask = null;
		}

		count = 60;
		bt_send_captcha.setText("获取验证码");

	}

	/**
	 * 更新倒计时
	 */
	private void updateTextView() {

		// 停止Timer
		if (count == 0) {
			stopTimer();
			return;
		}

		if (count < 10) {
			bt_send_captcha.setText("0" + count + "s后可重新发送");
		} else {
			bt_send_captcha.setText(count + "后可重新发送");
		}
	}

	/*** 处理UI线程更新Handle **/
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			case UPDATE_TEXTVIEW:
				updateTextView();
				break;
			default:
				break;
			}
		};
	};

	public void setchecktextListeners1() {
		et_userName.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				if (s.length() == 11) {
					bt_send_captcha.setEnabled(true);

				} else {
					bt_send_captcha.setEnabled(false);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub

			}
		});

	}
	public void setchecktextListeners() {
		et_userName.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				if (s.length() == 11) {
					bt_send_captcha.setEnabled(true);

				} else {
					bt_send_captcha.setEnabled(false);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

}
