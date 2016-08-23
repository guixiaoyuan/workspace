package com.deeal.exchange.fragment;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ab.fragment.AbAlertDialogFragment;
import com.ab.util.AbDialogUtil;
import com.ab.util.AbToastUtil;
import com.deeal.exchange.R;
import com.deeal.exchange.activity.CourierServeActivity;
import com.deeal.exchange.activity.LoginActivity;
import com.deeal.exchange.activity.MyAccountActivity;
import com.deeal.exchange.activity.MyBoughtActivity;
import com.deeal.exchange.activity.MyCollectionActivity;
import com.deeal.exchange.activity.MyNeedActivity;
import com.deeal.exchange.activity.MyPersonInfoActivity;
import com.deeal.exchange.activity.MyPublishActivity;
import com.deeal.exchange.activity.MySoldActivity;
import com.deeal.exchange.activity.PhotoViewActivity;
import com.deeal.exchange.activity.SettingActivity;
import com.deeal.exchange.application.MyApplication;
import com.deeal.exchange.application.Path;
import com.deeal.exchange.model.User;
import com.deeal.exchange.tools.BitmapHelper;
import com.deeal.exchange.tools.PathUtils;
import com.deeal.exchange.view.CircleImageView;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

import static com.deeal.exchange.application.Constant.CHANGEUSERINFO;
import static com.deeal.exchange.application.Constant.GOTOLOGIN;
import static com.deeal.exchange.application.Constant.GOTOSETTING;


public class MyPageFragment extends Fragment {
    /**
     *
     */
    private View rootview;
    private Intent intent = null;
    /**
     * 判断用户是否登陆
     */
    private Boolean isLogin = true;
    /**
     * 个人信息
     */
    private RelativeLayout rlGoUserinfo;
    private CircleImageView imgUserHead;
    private TextView tvUserNickname;
    private TextView tvUserIntro;
    /**
     * 我的发布
     */
    private RelativeLayout rlGoMyPublish;
    /**
     * 显示我发布的数量
     */
    private TextView tvMyPublishNum;
    /**
     * 我卖出的
     */
    private RelativeLayout rlGoMySold;
    /**
     * 显示我卖出的数量
     */
    private TextView tvMySoldNum;
    /**
     * 我买到的
     */
    private RelativeLayout rlGoMyBought;
    /**
     * 显示我买到的数量
     */
    private TextView tvMyBoughtNum;
    /**
     * 我的需求
     */
    private RelativeLayout rlGoMyNeed;
    private TextView tvMyNeedNum;

    /**
     * 快递服务
     */
    private RelativeLayout rlGoCourierServe;
    private TextView tvCourierServeNum;
    /**
     * 我的收藏
     */
    private RelativeLayout rlGoMyCollection;
    private TextView tvMyCollection;
    /**
     * 钱包
     */
    private RelativeLayout rlGoMyWallet;
    /**
     * 设置
     */
    private RelativeLayout rlGoSetting;
    private User user;
    private BitmapUtils bitmapUtils;

    private String headerUrl;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = new Intent();
        user = MyApplication.mUser;
        if(user == null){
            isLogin = false;
        }else{
            isLogin = user.getIsLogin();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                  Bundle savedInstanceState) {
        bitmapUtils = BitmapHelper.getBitmapUtils(getActivity().getApplicationContext());
        if(rootview == null){
            rootview = inflater.inflate(R.layout.fragment_my_page,null);
            inititems();
        }
        ViewGroup parent = (ViewGroup) rootview.getParent();
        if(parent != null){
            parent.removeView(rootview);
        }
        updatauserinfo(MyApplication.mUser);
        return rootview;
    }

    /**
     * 加载界面并绑定点击事件
     */
    private void inititems(){
        imgUserHead = (CircleImageView) rootview.findViewById(R.id.imgUserHead);
        imgUserHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!headerUrl.isEmpty()){
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), PhotoViewActivity.class);
                    intent.putExtra("imgName", headerUrl);
                    startActivity(intent);
                }else {
                    return;
                }
            }
        });
        tvUserNickname = (TextView) rootview.findViewById(R.id.tvUserName);
        tvUserIntro = (TextView) rootview.findViewById(R.id.tvUserIntroduction);
        rlGoUserinfo = (RelativeLayout) rootview.findViewById(R.id.rlGoUserInfo);
        rlGoUserinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                goActivity(isLogin,MyPersonInfoActivity.class);
            }
        });
        rlGoMyPublish = (RelativeLayout) rootview.findViewById(R.id.rlGoMyPublish);
        rlGoMyPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(isLogin, MyPublishActivity.class);
            }
        });
        rlGoMyBought = (RelativeLayout) rootview.findViewById(R.id.rlGoMyBought);
        rlGoMyBought.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(isLogin, MyBoughtActivity.class);
            }
        });
        rlGoMySold = (RelativeLayout) rootview.findViewById(R.id.rlGoMySold);
        rlGoMySold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(isLogin, MySoldActivity.class);
            }
        });
        rlGoMyNeed = (RelativeLayout) rootview.findViewById(R.id.rlGoMyNeed);
        rlGoMyNeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(isLogin, MyNeedActivity.class);
            }
        });
        rlGoMyCollection = (RelativeLayout) rootview.findViewById(R.id.rlGoMyCollection);
        rlGoMyCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(isLogin, MyCollectionActivity.class);
            }
        });
        rlGoCourierServe = (RelativeLayout) rootview.findViewById(R.id.rlGoCourierserve);
        rlGoCourierServe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(isLogin, CourierServeActivity.class);
            }
        });
        rlGoMyWallet = (RelativeLayout) rootview.findViewById(R.id.rlGoMyWallet);
        rlGoMyWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(isLogin, MyAccountActivity.class);
                //钱包
            }
        });
        rlGoSetting = (RelativeLayout) rootview.findViewById(R.id.rlGoSetting);
        rlGoSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(getActivity(), SettingActivity.class);
                startActivityForResult(i, GOTOSETTING);
            }
        });
        getUserInfo();
    }

    /**
     * 更新用户信息
     */
    private void updatauserinfo(User muser) {
        if (muser == null) {
            imgUserHead.setImageResource(R.mipmap.default_head);
            tvUserNickname.setText("请登陆");
        } else {
            if (muser.getUserHeadUrl() != null) {
                headerUrl = muser.getUserHeadUrl();
                bitmapUtils.display(imgUserHead, PathUtils.createPortriateUrl(muser.getUserID(),headerUrl));
            }
            if (muser.getUserNickname() != null) {
                tvUserNickname.setText(muser.getUserNickname());
            }
            if (muser.getUserIntro() != null) {
                tvUserIntro.setText(muser.getUserIntro());
            }
        }
    }

    /**
     * 从服务器获取用户信息
     */
    private void getUserInfo() {
        if (isLogin) {
            RequestParams params = new RequestParams();
            params.addBodyParameter("tokenID", MyApplication.mUser.getTokenId());
            HttpUtils httpUtils = new HttpUtils();
            httpUtils.send(HttpRequest.HttpMethod.POST, Path.getuserinfo, params, new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    AbDialogUtil.removeDialog(getActivity());
                    JSONObject result;

                    FragmentActivity activity = getActivity();
                    if (null == activity){
                        return;
                    }
                    MyApplication application = (MyApplication) activity.getApplication();
                    try {
                        result = new JSONObject(responseInfo.result);
                        String status = result.get("status").toString();
                        if (status.equals("SUCCESS")) {
                            String data = result.getString("data");
                            User muser = new User();
                            muser.getinfofromjson(data);
                            MyApplication.mUser.getinfofromjson(data);
                            updatauserinfo(muser);
                            application.updateLoginParams(MyApplication.mUser);
                        } else {
                            //token过期 重新登陆 更新用户登陆状态
                            MyApplication.mUser.setIsLogin(false);
                            application.updateLoginParams(MyApplication.mUser);
                            application.initLoginParams();
                            AbDialogUtil.showAlertDialog(getActivity(), "错误", "对不起用户账号已过期，请重新登陆", new AbAlertDialogFragment.AbDialogOnClickListener() {
                                @Override
                                public void onPositiveClick() {
                                    Intent intent = new Intent();
                                    intent.setClass(getActivity(), LoginActivity.class);
                                    startActivityForResult(intent, GOTOLOGIN);
                                }

                                @Override
                                public void onNegativeClick() {

                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailure(HttpException e, String s) {
                    AbToastUtil.showToast(getActivity(), "对不起更新用户信息失败");
                }
            });
        }
    }


    /**
     * 如果未登陆则跳转到登陆界面，如果登陆则跳转到指定界面
     * @param isLogin
     */
    private void goActivity(Boolean isLogin,Class activity){
        if (isLogin){
            intent.setClass(getActivity(), activity);
            startActivityForResult(intent, CHANGEUSERINFO);
        }
        else{
            intent.setClass(getActivity(), LoginActivity.class);
            startActivityForResult(intent, GOTOLOGIN);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GOTOLOGIN) {
            if (resultCode == -1) {
                isLogin = MyApplication.mUser.getIsLogin();
                Log.e("isLogin", isLogin + "");
                getUserInfo();
            }
        }
        if (requestCode == CHANGEUSERINFO) {
            if (resultCode == Activity.RESULT_OK) {
                getUserInfo();
            }
        }
        if (requestCode == GOTOSETTING) {
            if (resultCode == 1) {
                isLogin = MyApplication.mUser.getIsLogin();
                intent.setClass(getActivity(), LoginActivity.class);
                startActivityForResult(intent, GOTOLOGIN);
            }
        }
    }
}
