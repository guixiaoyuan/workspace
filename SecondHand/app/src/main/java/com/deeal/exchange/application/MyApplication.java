package com.deeal.exchange.application;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import com.ab.global.AbAppConfig;
import com.deeal.exchange.model.City;
import com.deeal.exchange.model.LocationInfo;
import com.deeal.exchange.model.User;
import com.deeal.exchange.style.StyleHelper;
import com.deeal.exchange.tools.UserInfoDAO;

import cn.jpush.android.api.JPushInterface;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;


/**
 * Created by dading on 2015/7/8.
 */
public class MyApplication extends Application {
    /**
     * 应用主题
     */
    public static int style = 0;
    private SharedPreferences mSharedPreferences;
    public static User mUser = null;
    public static LocationInfo mLocationInfo = null;
    public Boolean userPasswordRemember = false;
    public boolean isFirstStart = true;
    public static City currentCity = new City();
    public static StyleHelper styleHelper = null;

    @Override
    public void onCreate() {
        super.onCreate();
        /*DeadObjectException 异常出现，一般原因
        app进程不存在，在底层回调时找不到callback
        ipc进程崩溃也会出现改异常
        遇到该异常时，首先要检查app中的进程
        如果你的 app 有多进程，比如除了com.app进程外，你还有com.app.remote，
        那么在 RongIM.init 时，除了主进程其他进程不要做初始化，即在if(getPid() != “com.app.remote”)后再作init().
        sdk底层有ipc进程和push进程，每个进程在启动创建时，都会走一次Application的onCreate()，
        所以在RongIM.init()初始化后，消息注册等注册相关的逻辑前，要加上进程判断，只允许主进程做这些注册。
        */
        try {
            String Pid = getCurProcessName(this);
            if (Pid.equals("com.deeal.exchange")) {
                RongIM.init(this);
                JPushInterface.setDebugMode(true);
//                JPushInterface.init(this);
                mSharedPreferences = getSharedPreferences(AbAppConfig.SHARED_PATH,
                        Context.MODE_PRIVATE);
                initLoginParams();
                updateLoginParams(mUser);
                Log.e("app", "main");
            } else {
                Log.e("app", Pid);
            }
            if (Pid.equals("com.deeal.exchange:ipc") || Pid.equals("io.rong.push")) {

                RongIM.init(this);
            }
        } catch (Exception e) {
            Log.e("app", "1111111111111111111111111111111111111111");
        }
        styleHelper = new StyleHelper(style);
    }

    /**
     * 上次登录参数
     */
    public void initLoginParams() {
        SharedPreferences preferences = getSharedPreferences(
                AbAppConfig.SHARED_PATH, Context.MODE_PRIVATE);
        String userName = preferences.getString(Constant.USERNAMECOOKIE, "");
        String userPwd = preferences.getString(Constant.USERPASSWORDCOOKIE,
                "");
        String userToken = preferences.getString(Constant.USERTAKNE, "-1");
        Boolean isLogin = preferences.getBoolean("islogin", false);
        Boolean userPwdRemember = preferences.getBoolean(
                Constant.USERPASSWORDREMEMBERCOOKIE, false);
        String ryTokenID = preferences.getString("ryTokenID", "-1");
        String userid = preferences.getString("userId", "-1");
        String userNickName = preferences.getString("userNickName", "无名氏");
        String userHead = preferences.getString("userHead", "-1");
        style = preferences.getInt("style", 0);

        mUser = new User();
        mUser.setTel(userName);
        mUser.setUserPwd(userPwd);
        mUser.setIsLogin(isLogin);
        mUser.setTokenId(userToken);
        mUser.setRyTokenID(ryTokenID);
        mUser.setUserID(userid);
        mUser.setUserHeadUrl(userHead);
        mUser.setUserNickname(userNickName);
        userPasswordRemember = userPwdRemember;
    }


    /**
     * 更新用户登陆参数
     *
     * @param user
     */
    public void updateLoginParams(User user) {
        mUser = user;
        if (userPasswordRemember) {
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putString(Constant.USERNAMECOOKIE, user.getTel());
            editor.putString(Constant.USERPASSWORDCOOKIE, user.getUserPwd());
            editor.putBoolean("islogin", user.getIsLogin());
            editor.putBoolean(Constant.ISFIRSTSTART, false);
            editor.putString(Constant.USERTAKNE, user.getTokenId());
            editor.putString("ryTokenID", user.getRyTokenID());
            editor.putString("userId", user.getUserID());
            editor.putString("userNickName", user.getUserNickname());
            editor.putString("userHead", user.getUserHeadUrl());
            UserInfo mUserInfo = new UserInfo(user.getUserID(), user.getUserNickname(), Uri.parse(Path.IMGPATH + user.getUserHeadUrl()));
            UserInfoDAO mUserInfoDAO = new UserInfoDAO(getApplicationContext());
            mUserInfoDAO.createUser(user);
            editor.commit();
        } else {
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putBoolean(Constant.ISFIRSTSTART, false);
            editor.commit();
        }
        isFirstStart = false;
    }

    public void updateStyle(int style) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt("style", style);
        editor.commit();
    }


    /**
     * 获得当前进程号
     *
     * @param context
     * @return
     */
    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }



    @Override
    public void onTerminate() {
        super.onTerminate();
        System.exit(0);
    }
}
