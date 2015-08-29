package com.deeal.exchange.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.deeal.exchange.R;
import com.deeal.exchange.application.Constant;
import com.deeal.exchange.application.MyApplication;

public class LanucherActivity extends
        Activity {
    private boolean isLogin;
    private RelativeLayout relativeLayout;

    private TextView english, chinese;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lanucher);
        isLogin = MyApplication.mUser.getIsLogin();
        intianim();
        myThread.start();

    }

    private void intianim() {
        english = (TextView) findViewById(R.id.english);
        chinese = (TextView) findViewById(R.id.chinese);
        english.startAnimation(AnimationUtils.loadAnimation(this, R.anim.in_from_left));
        chinese.startAnimation(AnimationUtils.loadAnimation(this, R.anim.in_from_right));
    }

    /**
     * 两秒动画效果后执行操作
     */
    private Thread myThread = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(2000);
                myHandler.sendEmptyMessage(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });
    private Handler myHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            checkIsLogin(isLogin);
            return false;
        }
    });

    public void checkIsLogin(boolean isLogin) {
        if (isLogin) {
            Intent intent = new Intent();
            intent.setClass(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent();
            intent.setClass(this, LoginActivity.class);
            startActivityForResult(intent, Constant.GOTOLOGIN);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.GOTOLOGIN) {
            if (resultCode == RESULT_OK) {
                checkIsLogin(MyApplication.mUser.getIsLogin());
            }
        }
    }
}
