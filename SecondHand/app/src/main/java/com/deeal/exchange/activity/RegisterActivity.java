package com.deeal.exchange.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.activity.AbActivity;
import com.ab.fragment.AbAlertDialogFragment;
import com.ab.util.AbDialogUtil;
import com.ab.util.AbStrUtil;
import com.ab.util.AbToastUtil;
import com.deeal.exchange.R;
import com.deeal.exchange.application.Constant;
import com.deeal.exchange.application.Path;
import com.deeal.exchange.tools.MD5;
import com.deeal.exchange.view.slidr.Slidr;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;


public class RegisterActivity extends AbActivity {

    private EditText userName = null;
    private EditText userPwd = null;
    private EditText userPwd2 = null;
    private EditText etPhoneCode = null;
    private CheckBox checkBox = null;

    private ImageButton mClear1;
    private ImageButton mClear2;
    private ImageButton mClear3;

    private HttpUtils mHttpUtils = null;
    private TextView tvTitle;
    private Button btBack;
    private Button btGetPhoneCode;
    private Timer mTimer = null;
    private TimerTask mTimerTask = null;
    private static int delay = 1 * 1000; // 1s
    private static int period = 1 * 1000; // 1s
    private static int count = 120;
    private static final int UPDATE_TEXTVIEW = 99;

    /**
     * from_name：登陆注册activity传过来的参数
     */
    private String from_name = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initsms();
        initTitle();
        inititems();
        checkform();
    }

    /**
     * 检查用户输入信息
     */
    private void checkform() {
        Button agreementBtn = (Button) findViewById(R.id.agreementBtn);
        agreementBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AbDialogUtil.showAlertDialog(RegisterActivity.this, "用户注册协议",
                        getResources().getString(R.string.abort),
                        new AbAlertDialogFragment.AbDialogOnClickListener() {

                            @Override
                            public void onPositiveClick() {
                                // TODO Auto-generated method stub
                                checkBox.setChecked(true);
                            }

                            @Override
                            public void onNegativeClick() {
                                // TODO Auto-generated method stub
                                checkBox.setChecked(false);
                            }
                        });
            }
        });

        Button registerBtn = (Button) this.findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new RegisterOnClickListener());

        userName.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                String str = userName.getText().toString().trim();
                int length = str.length();
                if (length > 0) {
                    mClear1.setVisibility(View.VISIBLE);
                    if (!AbStrUtil.isNumber(str)) {
                        str = str.substring(0, length - 1);
                        userName.setText(str);
                        String str1 = userName.getText().toString().trim();
                        userName.setSelection(str1.length());
                        AbToastUtil.showToast(RegisterActivity.this,
                                R.string.error_name_expr);
                    }
                    mClear1.postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            mClear1.setVisibility(View.INVISIBLE);
                        }

                    }, 5000);

                } else {
                    mClear1.setVisibility(View.INVISIBLE);
                }
                if (length != 11) {
                    btGetPhoneCode.setEnabled(false);
                } else {
                    btGetPhoneCode.setEnabled(true);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {

            }
        });

        etPhoneCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String strcode = etPhoneCode.getText().toString().trim();
                int length = strcode.length();
                if (length > 0) {
                    if (!AbStrUtil.isNumber(strcode)) {
                        AbToastUtil.showToast(RegisterActivity.this,
                                R.string.error_phone_code_expr);

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        userPwd.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                String str = userPwd.getText().toString().trim();
                int length = str.length();
                if (length > 0) {
                    mClear2.setVisibility(View.VISIBLE);
                    if (!AbStrUtil.isNumberLetter(str)) {
                        str = str.substring(0, length - 1);
                        userPwd.setText(str);
                        String str1 = userPwd.getText().toString().trim();
                        userPwd.setSelection(str1.length());
                        AbToastUtil.showToast(RegisterActivity.this,
                                R.string.error_name_expr);
                    }

                    mClear2.postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            mClear2.setVisibility(View.INVISIBLE);
                        }

                    }, 5000);
                } else {
                    mClear2.setVisibility(View.INVISIBLE);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {

            }
        });

        userPwd2.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                String str = userPwd2.getText().toString().trim();
                int length = str.length();
                if (length > 0) {
                    mClear3.setVisibility(View.VISIBLE);
                    if (!AbStrUtil.isNumberLetter(str)) {
                        str = str.substring(0, length - 1);
                        userPwd2.setText(str);
                        String str1 = userPwd2.getText().toString().trim();
                        userPwd2.setSelection(str1.length());
                        AbToastUtil.showToast(RegisterActivity.this,
                                R.string.error_name_expr);
                    }
                    mClear3.postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            mClear3.setVisibility(View.INVISIBLE);
                        }

                    }, 5000);
                } else {
                    mClear3.setVisibility(View.INVISIBLE);
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {

            }
        });


        mClear1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                userName.setText("");
            }
        });

        mClear2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                userPwd.setText("");
            }
        });

        mClear3.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                userPwd2.setText("");
            }
        });
    }

    private void inititems() {
        userName = (EditText) this.findViewById(R.id.userName);
        userPwd = (EditText) this.findViewById(R.id.userPwd);
        userPwd2 = (EditText) this.findViewById(R.id.userPwd2);
        etPhoneCode = (EditText) this.findViewById(R.id.etPhoneCode);

        checkBox = (CheckBox) findViewById(R.id.register_check);
        mClear1 = (ImageButton) findViewById(R.id.clearName);
        mClear2 = (ImageButton) findViewById(R.id.clearPwd);
        mClear3 = (ImageButton) findViewById(R.id.clearPwd2);

        Intent intent = getIntent();
        from_name = intent.getStringExtra("guide");

        //发送短信验证码
        btGetPhoneCode = (Button) findViewById(R.id.btGetPhoneCode);
        btGetPhoneCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    SMSSDK.getVerificationCode("86", userName.getText().toString().trim());
                }catch (Exception e ){
                    Log.e("sms",e+"");
                }
            }
        });
    }


    public class RegisterOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            final String mStr_name = userName.getText().toString().trim();
            final String mStr_pwd = userPwd.getText().toString().trim();
            final String mStr_pwd2 = userPwd2.getText().toString().trim();
            final String mStr_phonecode = etPhoneCode.getText().toString().trim();
            if (TextUtils.isEmpty(mStr_name)) {
                AbToastUtil.showToast(RegisterActivity.this,
                        R.string.error_name);
                userName.setFocusable(true);
                userName.requestFocus();
                return;
            }

            if (!AbStrUtil.isNumberLetter(mStr_name)) {
                AbToastUtil.showToast(RegisterActivity.this,
                        R.string.error_name_expr);
                userName.setFocusable(true);
                userName.requestFocus();
                return;
            }

            if (AbStrUtil.strLength(mStr_name) < 3) {
                AbToastUtil.showToast(RegisterActivity.this,
                        R.string.error_name_length1);
                userName.setFocusable(true);
                userName.requestFocus();
                return;
            }

            if (AbStrUtil.strLength(mStr_name) > 20) {
                AbToastUtil.showToast(RegisterActivity.this,
                        R.string.error_name_length2);
                userName.setFocusable(true);
                userName.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(mStr_pwd)) {
                AbToastUtil
                        .showToast(RegisterActivity.this, R.string.error_pwd);
                userPwd.setFocusable(true);
                userPwd.requestFocus();
                return;
            }

            if (AbStrUtil.strLength(mStr_pwd) < 6) {
                AbToastUtil.showToast(RegisterActivity.this,
                        R.string.error_pwd_length1);
                userPwd.setFocusable(true);
                userPwd.requestFocus();
                return;
            }


            if (TextUtils.isEmpty(mStr_pwd2)) {
                AbToastUtil.showToast(RegisterActivity.this,
                        R.string.error_pwd);
                userPwd2.setFocusable(true);
                userPwd2.requestFocus();
                return;
            }

            if (AbStrUtil.strLength(mStr_pwd2) < 6) {
                AbToastUtil.showToast(RegisterActivity.this,
                        R.string.error_pwd_length1);
                userPwd2.setFocusable(true);
                userPwd2.requestFocus();
                return;
            }

            if (AbStrUtil.strLength(mStr_pwd2) > 20) {
                AbToastUtil.showToast(RegisterActivity.this,
                        R.string.error_pwd_length2);
                userPwd2.setFocusable(true);
                userPwd2.requestFocus();
                return;
            }

            if (!mStr_pwd2.equals(mStr_pwd)) {
                AbToastUtil.showToast(RegisterActivity.this,
                        R.string.error_pwd_match);
                userPwd2.setFocusable(true);
                userPwd2.requestFocus();
                return;
            }

            if (AbStrUtil.isEmpty(mStr_phonecode)) {
                AbToastUtil.showToast(RegisterActivity.this,
                        R.string.phonecode);
                etPhoneCode.setFocusable(true);
                etPhoneCode.requestFocus();
                return;
            }

            if (!checkBox.isChecked()) {
                AbToastUtil.showToast(RegisterActivity.this,
                        R.string.error_agreement);
                return;
            }
            SMSSDK.submitVerificationCode("86", userName.getText().toString(), etPhoneCode.getText().toString());

        }
    }

    /**
     * 向服务器 发送注册请求 接收回调
     *
     * @param name 电话号码
     * @param pwd  用户密码
     */
    private void regist(String name, String pwd) {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
        params.addBodyParameter("tel", name);
        params.addBodyParameter("password", MD5.getMD5(pwd));//对密码进行加密处理
        httpUtils.send(HttpRequest.HttpMethod.POST, Path.registerurl, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                try {
                    JSONObject result = new JSONObject(responseInfo.result);
                    String status = result.getString("status");
                    if (status.equals("SUCCESS")) {
                        AbDialogUtil.showAlertDialog(RegisterActivity.this, "注册成功", "是否前往登陆界面", new AbAlertDialogFragment.AbDialogOnClickListener() {
                            @Override
                            public void onPositiveClick() {
                                Intent data = new Intent();
                                Bundle bundle = new Bundle();
                                bundle.putString("tel", userName.getText().toString().trim());
                                bundle.putString("pwd", userPwd.getText().toString().trim());
                                data.putExtras(bundle);
                                setResult(RESULT_OK, data);
                                finish();
                            }

                            @Override
                            public void onNegativeClick() {

                            }
                        });
                    } else {
                        AbToastUtil.showToast(RegisterActivity.this, "对不起注册失败……请再试一次");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(RegisterActivity.this, s, Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * 加载标题栏
     */
    private void initTitle() {
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(getString(R.string.register));
        btBack = (Button) findViewById(R.id.btBack);
        btBack.setVisibility(View.VISIBLE);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
    }

    /**
     * 初始化sharesdk短信验证码组件
     */
    private void initsms() {
        SMSSDK.initSDK(this, Constant.SMSAPPKEY, Constant.SMSAPPSRCRET);//加载短信验证码sdk
        EventHandler eh = new EventHandler() {
            @Override
            public void afterEvent(final int event, final int result, final Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                smshandler.sendMessage(msg);
            }
        };
        SMSSDK.registerEventHandler(eh); //注册短信回调
    }


    // 发送按钮定时操作

    /**
     * 启动Timer
     */
    private void startTimer() {
        stopTimer();
        // 输入框不可用，获取验证码按钮不可用
        userName.setEnabled(false);
        btGetPhoneCode.setEnabled(false);

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

        btGetPhoneCode.setEnabled(true);
        userName.setEnabled(true);

        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }

        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }

        count = 60;
        btGetPhoneCode.setText("获取验证码");
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
            btGetPhoneCode.setText("0" + count + "s后可重新发送");
        } else {
            btGetPhoneCode.setText(count + "s后可重新发送");
        }
    }

    /**
     * 处理UI线程更新Handle *
     */
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {

            switch (msg.what) {
                case UPDATE_TEXTVIEW:
                    updateTextView();
                    break;
                case 22:

                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 短信验证码消息接收后处理UI线程
     */
    private Handler smshandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            Log.e("event", "event=" + event);
            if (result == SMSSDK.RESULT_COMPLETE) {
                //短信注册成功后，返回MainActivity,然后提示新好友
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//提交验证码成功
                    //Toast.makeText(getApplicationContext(), "提交验证码成功", Toast.LENGTH_SHORT).show();
                    regist(userName.getText().toString().trim(), userPwd.getText()
                            .toString().trim());
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    Toast.makeText(getApplicationContext(), "验证码已经发送", Toast.LENGTH_SHORT).show();
                    startTimer();
                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {//返回支持发送验证码的国家列表
                    Toast.makeText(getApplicationContext(), "获取国家列表成功", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(RegisterActivity.this, "验证码错误" + data.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }
}
