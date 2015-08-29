/**
 * 
 */
package com.deeal.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.deeal.model.RegisterInfo;
import com.deeal.tools.register.PermissionTokenGenerator;
import com.deeal.tools.register.Register;
import com.example.deeal.R;
import com.thinkland.sdk.sms.SMSCaptcha;
import com.thinkland.sdk.util.BaseData.ResultCallBack;

/**
 * @author Administrator
 * 
 */
public class RegisterActivity extends Activity {

	private EditText registerID, registerCode, registerPassword,
			repeatPassword;
	private ImageButton btn_return;
	private Button btn_reg, btSendsmscode;
	private SMSCaptcha captcha;
	private String user_phone;
	private String name, code, password, repeatpassword;
	private Timer mTimer = null;
	private TimerTask mTimerTask = null;
	private static int delay = 1 * 1000; // 1s
	private static int period = 1 * 1000; // 1s
	private static int count = 120;
	private static final int UPDATE_TEXTVIEW = 99;
	private boolean coderesult;
	private Intent lastIntent;

	private static int REGISTER_OK = 0;
	private static int SMSCODE_ERROR = 1;
	private static int PWD_CHECK_ERROR = 2;
	private static int USERNAME_FORMAT_ERROR = 3;
	
	private static String SMSCODE_SHOULD_NOT_NULL = "短信验证码不能为空！";
	private static String SMSCODE_SHOULD_ERROR = "短信验证码错误！";
	private static String PWD_DOUBLE_CHECK_ERROR = "两次密码输入不一致！";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		this.lastIntent = getIntent();
		captcha = SMSCaptcha.getInstance();
		inititems();
		setListeners();
		btSendsmscode.setEnabled(false);
		btn_reg.setEnabled(false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}

	public void inititems() {
		this.btn_reg = (Button) this.findViewById(R.id.btn_reg);
		this.btn_return = (ImageButton) this.findViewById(R.id.btn_return);
		this.registerCode = (EditText) this.findViewById(R.id.register_code);
		this.registerID = (EditText) this.findViewById(R.id.register_id);
		this.registerPassword = (EditText) this
				.findViewById(R.id.register_password);
		this.repeatPassword = (EditText) findViewById(R.id.register_repeat_password);
		this.btSendsmscode = (Button) this.findViewById(R.id.bt_send_message);

	}

	private void setListeners() {
		/*
		 * set the background changedwhen user touch this ImageButton
		 */

		btn_return.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		btn_reg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// 注册验证码识别，0为验证成功，1为验证失败
				String code = getCode();
				
				if (null == code) {
					Toast.makeText(RegisterActivity.this, SMSCODE_SHOULD_NOT_NULL, Toast.LENGTH_LONG).show();
				} else {
					captcha.commitCaptcha(user_phone, getCode(),
							new ResultCallBack() {

								@Override
								public void onResult(int code, String arg1,
										String arg2) {
									// TODO Auto-generated method stub
									if (code == 0) {
										coderesult = true;
										try {
											register();
										} catch (Exception e) {
											// TODO: handle exception
											System.out.println(e.getMessage());
											e.printStackTrace();
										}
										
									} else {
										coderesult = false;
									}
								}
							});
				}
			}
		});
		btSendsmscode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				user_phone = getPhone();
				startTimer();
				captcha.sendCaptcha(user_phone, new ResultCallBack() {
					@Override
					public void onResult(int code, String arg1, String arg2) {
						// TODO Auto-generated method stub
						if (code == 0) {
							Toast.makeText(RegisterActivity.this, "发送成功", 0)
									.show();
							btn_reg.setEnabled(true);

						} else {
							Toast.makeText(RegisterActivity.this, "发送失败", 0)
									.show();
						}
					}
				});

			};
		});

		registerID.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				if (s.length() == 11) {
					btSendsmscode.setEnabled(true);
				} else {
					btSendsmscode.setEnabled(false);
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

	private int check(boolean coderesult, String pw, String repw) {
		if (!coderesult) {
			Toast.makeText(this, SMSCODE_SHOULD_ERROR, 0).show();
			return SMSCODE_ERROR;
		} else if (/*pw != repw*/!pw.equals(repw)) {
			Toast.makeText(this, PWD_DOUBLE_CHECK_ERROR, 0).show();
			return PWD_CHECK_ERROR;
		} else {
			Toast.makeText(this, "注册成功", 0).show();
			return REGISTER_OK;
		}
	}

	public void register() {
		name = getPhone();
		String tel = getPhone();
		password = getPassword();
		repeatpassword = getRepeatPassword();
		
		PermissionTokenGenerator ptg = new PermissionTokenGenerator();
		if (REGISTER_OK == check(coderesult, password, repeatpassword)) {
			
			RegisterInfo registerInfo = new RegisterInfo(name, password,
					repeatpassword, tel, ptg.getRegToken());
			/*Toast.makeText(this, "帐号" + name + "\n密码" + password, 0).show();*/
			Register reg = new Register(registerInfo, this,
					this.lastIntent);
			reg.doRegister();
		}
	}

	// 发送按钮定时操作
	/*
	 * 启动Timer
	 */
	private void startTimer() {

		stopTimer();
		// 输入框不可用，获取验证码按钮不可用
		registerID.setEnabled(false);
		btSendsmscode.setEnabled(false);

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

		btSendsmscode.setEnabled(true);
		registerID.setEnabled(true);

		if (mTimer != null) {
			mTimer.cancel();
			mTimer = null;
		}

		if (mTimerTask != null) {
			mTimerTask.cancel();
			mTimerTask = null;
		}

		count = 60;
		btSendsmscode.setText("获取验证码");

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
			btSendsmscode.setText("0" + count + "s后可重新发送");
		} else {
			btSendsmscode.setText(count + "后可重新发送");
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

	public void setchecktextListeners() {
		registerID.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				if (s.length() == 11) {
					btSendsmscode.setEnabled(true);

				} else {
					btSendsmscode.setEnabled(false);
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

	private String getPhone() {
		name = registerID.getText().toString();
		return name;
	}

	private String getCode() {
		code = registerCode.getText().toString();
		return code;
	}

	private String getPassword() {
		password = registerPassword.getText().toString();
		
		return PermissionTokenGenerator.Encrypt(password);
	}

	private String getRepeatPassword() {
		repeatpassword = repeatPassword.getText().toString();
		return PermissionTokenGenerator.Encrypt(repeatpassword);
	}

}
