package com.deeal.exchange.activity;


import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.activity.AbActivity;
import com.ab.fragment.AbAlertDialogFragment;
import com.ab.util.AbDialogUtil;

import com.deeal.exchange.R;
import com.deeal.exchange.application.MyApplication;
import com.deeal.exchange.view.Titlebar;
import com.deeal.exchange.view.slidr.Slidr;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;

import io.rong.imkit.RongIM;


public class SettingActivity extends AbActivity implements View.OnClickListener {

    private RelativeLayout rlCheckUpdate;
    private RelativeLayout rlConnectToKefu;
    private RelativeLayout rlAbout;
    private RelativeLayout rlChangeColor;
    private Button btLoginout;
    private MyApplication application;
    private int style = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initTitle();
        initItems();
        application = (MyApplication) getApplication();
        //Slidr.attach(this);
    }

    private void initTitle() {
        Titlebar titlebar = new Titlebar(this);
        titlebar.setTitle(getString(R.string.setting));
        titlebar.getbtback();
    }

    private void initItems() {
        rlCheckUpdate = (RelativeLayout) findViewById(R.id.rlCheckUpdate);
        rlCheckUpdate.setOnClickListener(this);
        rlConnectToKefu = (RelativeLayout) findViewById(R.id.rlConnectToKefu);
        rlConnectToKefu.setOnClickListener(this);
        rlAbout = (RelativeLayout) findViewById(R.id.rlAbout);
        rlAbout.setOnClickListener(this);
        btLoginout = (Button) findViewById(R.id.btLogout);
        btLoginout.setOnClickListener(this);
        rlChangeColor = (RelativeLayout) findViewById(R.id.rlChangeColor);
        rlChangeColor.setOnClickListener(this);
        if(application.mUser==null){
            btLoginout.setVisibility(View.GONE);
        }else{
            if (!application.mUser.getIsLogin()) {
                btLoginout.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlCheckUpdate:
                checkforUpdate();
                break;
            case R.id.btLogout:
                logout();
                break;
            case R.id.rlConnectToKefu:
                connectToKefu();
                break;
            case R.id.rlAbout:
                GoAbout();
                break;
            case R.id.rlChangeColor:
                ChangeColor();
                break;
        }
    }

    private void ChangeColor() {
        View dialogChooseColor = View.inflate(this,R.layout.dialog_choose_color,null);
        RadioGroup rgColor = (RadioGroup) dialogChooseColor.findViewById(R.id.rgColor);
        final TextView tvTitle = (TextView) dialogChooseColor.findViewById(R.id.tvTitle);
        final Button btEnsure = (Button) dialogChooseColor.findViewById(R.id.btEnsure);
        btEnsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.style =style;
                MyApplication application = (MyApplication) getApplication();
                application.updateStyle(style);
                application.initLoginParams();
                AbDialogUtil.removeDialog(SettingActivity.this);
            }
        });
        rgColor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rbDefalut:
                        style = 0;
                        tvTitle.setBackgroundColor(getResources().getColor(R.color.blue));
                        break;
                    case R.id.rbPink:
                        style = 1;
                        tvTitle.setBackgroundColor(getResources().getColor(R.color.accent));
                        break;
                    case R.id.rbDark:
                        style = 2;
                        tvTitle.setBackgroundColor(getResources().getColor(R.color.gray_dark));
                        break;
                }

            }
        });
        AbDialogUtil.removeDialog(dialogChooseColor);
        AbDialogUtil.showAlertDialog(dialogChooseColor);
    }

    private void connectToKefu(){
        if(RongIM.getInstance() != null){
            RongIM.getInstance().startCustomerServiceChat(this, "KEFU1438679404365 ", "客服聊天");
        }
    }


    private void GoAbout() {
        Intent intent = new Intent();
        intent.setClass(this,AbortActivity.class);
        startActivity(intent);
    }

    /**
     * 退出登陆
     */
    private void logout() {
        AbDialogUtil.showAlertDialog(this, getString(R.string.text_logout), "是否退出当前账号？", new AbAlertDialogFragment.AbDialogOnClickListener() {
            @Override
            public void onPositiveClick() {
                MyApplication.mUser.setIsLogin(false);
                MyApplication.mUser.setTokenId("");
                application.updateLoginParams(MyApplication.mUser);
                application.initLoginParams();
                setResult(1);
                RongIM.getInstance().logout();
                finish();
            }

            @Override
            public void onNegativeClick() {

            }
        });

    }

    /**
     * 检查更新
     */
    private void checkforUpdate() {
        UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
            @Override
            public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
                switch (updateStatus) {
                    case UpdateStatus.Yes: // has update
                        UmengUpdateAgent.showUpdateDialog(SettingActivity.this, updateInfo);
                        break;
                    case UpdateStatus.No: // has no update
                        Toast.makeText(SettingActivity.this, "没有更新", Toast.LENGTH_SHORT).show();
                        break;
                    case UpdateStatus.NoneWifi: // none wifi
                        Toast.makeText(SettingActivity.this, "没有wifi连接， 只在wifi下更新", Toast.LENGTH_SHORT).show();
                        break;
                    case UpdateStatus.Timeout: // time out
                        Toast.makeText(SettingActivity.this, "超时", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        UmengUpdateAgent.forceUpdate(this);
    }
}
