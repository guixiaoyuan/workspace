package com.deeal.exchange.activity;

import android.app.Activity;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class FindPwdActivity extends Activity implements View.OnClickListener, TextWatcher {

    private TextView tvTitle;
    private Button btBack;

    private EditText et_phone_number;
    private EditText et_code_number;
    private EditText et_new_pwd;
    private EditText et_firm_new_pwd;
    private Button getcode;
    private Button sure;


    private Timer mTimer = null;
    private TimerTask mTimerTask = null;
    private static int delay = 1 * 1000; // 1s
    private static int period = 1 * 1000; // 1s
    private static int count = 120;
    private static final int UPDATE_TEXTVIEW = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pwd);
        initsms();
        inittitle();
        initwidget();

        et_phone_number.addTextChangedListener(this);


    }

    private void initwidget() {
        et_phone_number = (EditText) this.findViewById(R.id.et_phone_find_pwd);
        et_code_number = (EditText) this.findViewById(R.id.et_code_find_pwd);
        et_new_pwd = (EditText) this.findViewById(R.id.et_new_pwd_find_pew);
        et_firm_new_pwd = (EditText) this.findViewById(R.id.et_firm_pwd_find_pwd);

        getcode = (Button) this.findViewById(R.id.bt_GetCode_find_pwd);
        sure = (Button) this.findViewById(R.id.bt_ensure_find_pwd);

        getcode.setEnabled(false);

        getcode.setOnClickListener(this);
        sure.setOnClickListener(this);

    }


    private void inittitle() {
        tvTitle = (TextView) FindPwdActivity.this.findViewById(R.id.tvTitle);
        btBack = (Button) FindPwdActivity.this.findViewById(R.id.btBack);
        tvTitle.setText("找回密码");
        btBack.setVisibility(View.VISIBLE);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_GetCode_find_pwd:
                    try {
                        Log.i("+++++++","1");
                        SMSSDK.getVerificationCode("86", et_phone_number.getText().toString().trim());
                        Log.i("+++++++", "2");
                    } catch (Exception e) {
                        Log.e("sms", e + "");
                    }
            break;
            case R.id.bt_ensure_find_pwd:
                    sureFindPwd();
            break;
        }

    }

    private void sureFindPwd() {
        String phone = et_phone_number.getText().toString().trim();
        String code = et_code_number.getText().toString().trim();
        String pwd1 = et_new_pwd.getText().toString().trim();
        String pwd2 = et_firm_new_pwd.getText().toString().trim();

        /**
         * 用户名不能为空
         */
        if (TextUtils.isEmpty(phone)) {
            AbToastUtil.showToast(FindPwdActivity.this,
                    R.string.error_name);
            et_phone_number.setFocusable(true);
            et_phone_number.requestFocus();
            return;
        }
        /**
         * 用户名只能为数字
         */
        if (!AbStrUtil.isNumberLetter(phone)) {
            AbToastUtil.showToast(FindPwdActivity.this,
                    R.string.error_name_expr);
            et_phone_number.setFocusable(true);
            et_phone_number.requestFocus();
            return;
        }
        /**
         * 验证码不能为空
         */
        if (TextUtils.isEmpty(code)) {
            AbToastUtil.showToast(FindPwdActivity.this,
                    R.string.error_phone_code);
            et_code_number.setFocusable(true);
            et_code_number.requestFocus();
            return;
        }
        /**
         * 新密码不能为空
         */
        if (TextUtils.isEmpty(pwd1)) {
            AbToastUtil.showToast(FindPwdActivity.this,
                    R.string.error_pwd_new);
            et_new_pwd.setFocusable(true);
            et_new_pwd.requestFocus();
            return;
        }
        /**
         * 确认密码不能为空
         */
        if (TextUtils.isEmpty(pwd2)) {
            AbToastUtil.showToast(FindPwdActivity.this,
                    R.string.error_pwd_new_sure);
            et_firm_new_pwd.setFocusable(true);
            et_firm_new_pwd.requestFocus();
            return;
        }
        /**
         * 密码长度长度不能少于6个字符
         */
        if (AbStrUtil.strLength(pwd1) < 6) {
            AbToastUtil.showToast(FindPwdActivity.this,
                    R.string.error_pwd_length1);
            et_new_pwd.setFocusable(true);
            et_new_pwd.requestFocus();
            return;
        }
        /**
         * 密码长度不能多余20个字符
         */
        if (AbStrUtil.strLength(pwd1) > 20) {
            AbToastUtil.showToast(FindPwdActivity.this,
                    R.string.error_pwd_length2);
            et_new_pwd.setFocusable(true);
            et_new_pwd.requestFocus();
            return;
        }
        /**
         * 两次密码输入不一致
         */
        if (!pwd1.equals(pwd2)) {
            AbToastUtil.showToast(FindPwdActivity.this,
                    R.string.error_pwd_match);
            et_firm_new_pwd.setFocusable(true);
            et_firm_new_pwd.requestFocus();
            return;
        }

        SMSSDK.submitVerificationCode("86", et_phone_number.getText().toString(), et_code_number.getText().toString());

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String phonenumber;
        phonenumber = et_phone_number.getText().toString().trim();
        if(phonenumber.length() != 11 ){
            getcode.setEnabled(false);
        }else {
            getcode.setEnabled(true);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }




    private void regist(String name, String pwd) {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
        params.addBodyParameter("tel", name);
        params.addBodyParameter("password", MD5.getMD5(pwd));
        httpUtils.send(HttpRequest.HttpMethod.POST, Path.findpwd, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                try {
                    JSONObject result = new JSONObject(responseInfo.result);
                    String status = result.getString("status");
                    if (status.equals("SUCCESS")) {
                        Toast.makeText(FindPwdActivity.this,"密码修改成功！",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(FindPwdActivity.this, s, Toast.LENGTH_LONG).show();
            }
        });
    }


    private void initsms() {
        SMSSDK.initSDK(this, Constant.SMSAPPKEY, Constant.SMSAPPSRCRET);//���ض�����֤��sdk
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
        SMSSDK.registerEventHandler(eh); //ע����Żص�
    }


    // 发送按钮定时操作

    /**
     * 启动Timer
     */
    private void startTimer() {
        stopTimer();
        // 输入框不可用，获取验证码按钮不可用
        et_phone_number.setEnabled(false);
        getcode.setEnabled(false);

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

        getcode.setEnabled(true);
        et_phone_number.setEnabled(true);

        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }

        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }

        count = 60;
        getcode.setText("再次获取");
    }

    /**
     * 更新倒计时
     */
    private void updateTextView() {
        // ֹͣTimer
        if (count == 0) {
            stopTimer();
            return;
        }
        if (count < 10) {
            getcode.setText("0" + count + "s后可重新发送");
        } else {
            getcode.setText(count + "s后可重新发送");
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
                    Toast.makeText(getApplicationContext(), "提交验证码成功", Toast.LENGTH_SHORT).show();
                    regist(et_phone_number.getText().toString().trim(), et_new_pwd.getText()
                            .toString().trim());

                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    Toast.makeText(getApplicationContext(), "验证码已经发送", Toast.LENGTH_SHORT).show();
                    startTimer();
                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {//返回支持发送验证码的国家列表
                    Toast.makeText(getApplicationContext(), "获取国家列表成功", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(FindPwdActivity.this, "验证码错误" + data.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    };



}
