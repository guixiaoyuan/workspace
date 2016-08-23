package com.deeal.exchange.activity;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.activity.AbActivity;
import com.ab.util.AbDialogUtil;
import com.ab.util.AbSharedUtil;
import com.ab.util.AbStrUtil;
import com.ab.util.AbToastUtil;
import com.deeal.exchange.application.Constant;
import com.deeal.exchange.application.MyApplication;
import com.deeal.exchange.R;
import com.deeal.exchange.application.Path;
import com.deeal.exchange.model.User;
import com.deeal.exchange.tools.MD5;
import com.deeal.exchange.view.slidr.Slidr;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

import static com.lidroid.xutils.http.client.HttpRequest.*;
public class LoginActivity extends AbActivity {

    private EditText userName = null;
    private EditText userPwd = null;
    private String mStr_name = null;
    private String mStr_pwd = null;
    private ImageButton mClear1;
    private ImageButton mClear2;
    private MyApplication application;
    private Button loginBtn = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AbSharedUtil.putBoolean(LoginActivity.this,
                Constant.USERPASSWORDREMEMBERCOOKIE, true);
        application = (MyApplication) getApplication();
        userName = (EditText) this.findViewById(R.id.userName);
        userPwd = (EditText) this.findViewById(R.id.userPwd);
        mClear1 = (ImageButton) findViewById(R.id.clearName);
        mClear2 = (ImageButton) findViewById(R.id.clearPwd);
        loginBtn = (Button) this.findViewById(R.id.loginBtn);
        TextView register = (TextView) this.findViewById(R.id.registerBtn);
        loginBtn.setOnClickListener(new LoginOnClickListener());
        TextView pwdBtn = (TextView) findViewById(R.id.pwdBtn);
        pwdBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,
                        FindPwdActivity.class);
                startActivity(intent);
            }
        });


        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,
                        RegisterActivity.class);
                startActivityForResult(intent, Constant.GOTOREGISTER);
            }
        });
        userName.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                String str = userName.getText().toString().trim();
                int length = str.length();
                if (length > 0) {
                    mClear1.setVisibility(View.VISIBLE);
                    if (!AbStrUtil.isNumberLetter(str)) {
                        str = str.substring(0, length - 1);
                        userName.setText(str);
                        String str1 = userName.getText().toString().trim();
                        userName.setSelection(str1.length());
                        AbToastUtil.showToast(LoginActivity.this,
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
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

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
                        AbToastUtil.showToast(LoginActivity.this,
                                R.string.error_pwd_expr);
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
    }


    public class LoginOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v == loginBtn) {
                mStr_name = userName.getText().toString();
                mStr_pwd = userPwd.getText().toString();

                if (TextUtils.isEmpty(mStr_name)) {
                    AbToastUtil.showToast(LoginActivity.this,
                            R.string.error_name);
                    userName.setFocusable(true);
                    userName.requestFocus();
                    return;
                }

                if (!AbStrUtil.isNumberLetter(mStr_name)) {
                    AbToastUtil.showToast(LoginActivity.this,
                            R.string.error_name_expr);
                    userName.setFocusable(true);
                    userName.requestFocus();
                    return;
                }

                if (AbStrUtil.strLength(mStr_name) < 3) {
                    AbToastUtil.showToast(LoginActivity.this,
                            R.string.error_name_length1);
                    userName.setFocusable(true);
                    userName.requestFocus();
                    return;
                }

                if (AbStrUtil.strLength(mStr_name) > 20) {
                    AbToastUtil.showToast(LoginActivity.this,
                            R.string.error_name_length2);
                    userName.setFocusable(true);
                    userName.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(mStr_pwd)) {
                    AbToastUtil.showToast(LoginActivity.this,
                            R.string.error_pwd);
                    userPwd.setFocusable(true);
                    userPwd.requestFocus();
                    return;
                }

                if (AbStrUtil.strLength(mStr_pwd) < 6) {
                    AbToastUtil.showToast(LoginActivity.this,
                            R.string.error_pwd_length1);
                    userPwd.setFocusable(true);
                    userPwd.requestFocus();
                    return;
                }

                if (AbStrUtil.strLength(mStr_pwd) > 20) {
                    AbToastUtil.showToast(LoginActivity.this,
                            R.string.error_pwd_length2);
                    userPwd.setFocusable(true);
                    userPwd.requestFocus();
                    return;
                }
                login(mStr_name, mStr_pwd);
            }

        }
    }


    /*private void inittitle() {
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(getString(R.string.login));
        btBack = (Button) findViewById(R.id.btBack);
        btBack.setVisibility(View.VISIBLE);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }*/

    /**
     * @param mStr_name
     * @param mStr_pwd
     */
    private void login(final String mStr_name, final String mStr_pwd) {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
        params.addBodyParameter("tel", mStr_name);
        params.addBodyParameter("password", MD5.getMD5(mStr_pwd));
        httpUtils.send(HttpMethod.POST, Path.loginurl, params, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                AbDialogUtil.showLoadDialog(LoginActivity.this, R.mipmap.loading, "正在登陆。。。");
            }

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result.toString();
                String token = null;
                String ryTokenID = null;
                try {
                    JSONObject json = new JSONObject(result);
                    String status = json.get("status").toString();
                    if (status.equals("SUCCESS")) {
                        JSONObject data = json.getJSONObject("data");
                        token = data.get("tokenID").toString();
                        JSONObject rydata = data.getJSONObject("ryTokenID");
                        ryTokenID= rydata.getString("token");
                        String userid = rydata.get("userId").toString();
                        User user = new User();
                        user.setTel(mStr_name);
                        user.setUserPwd(mStr_pwd);
                        user.setTokenId(token);
                        user.setIsLogin(true);
                        user.setRyTokenID(ryTokenID);
                        user.setUserID(userid);
                        application.updateLoginParams(user);
                        application.initLoginParams();
                        AbDialogUtil.removeDialog(LoginActivity.this);
                        Log.e("isLogin", MyApplication.mUser.getIsLogin() + "");

                        //根据登录用户刷新融云服务器连接
                        RongIM.connect(ryTokenID, new RongIMClient.ConnectCallback() {
                            @Override
                            public void onTokenIncorrect() {

                            }

                            @Override
                            public void onSuccess(String s) {
                                setResult(RESULT_OK);
                                finish();
                            }

                            @Override
                            public void onError(RongIMClient.ErrorCode errorCode) {
                                Toast.makeText(LoginActivity.this,"对不起连接失败",Toast.LENGTH_SHORT).show();
                            }
                        });

                    }else{
                        AbDialogUtil.removeDialog(LoginActivity.this);
                        Toast.makeText(LoginActivity.this,"对不起登陆失败",Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(LoginActivity.this, s, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.GOTOREGISTER) {
            if (resultCode == RESULT_OK) {
                userName.setText(data.getExtras().get("tel").toString());
                userPwd.setText(data.getExtras().get("pwd").toString());
            }
        }
    }
}
