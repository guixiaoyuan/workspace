package com.deeal.exchange.tools;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.deeal.exchange.application.Path;
import com.deeal.exchange.model.User;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;

import io.rong.imlib.model.UserInfo;

/**
 * Created by Administrator on 2015/7/31.       用于缓存用户的信息到本地端数据库、
 */
public class UserInfoDAO {
    private User mUserInfo;
    private DbUtils mDbUtils ;
    private Context mContext;

    public UserInfoDAO(Context context){
        mContext = context;
        mDbUtils = DbUtils.create(context);
    }

    /**
     * 本地缓存一条用户信息
     * @param userInfo
     */
    public void createUser(User userInfo){
        mUserInfo = userInfo;
        Log.e("username",userInfo.getUserNickname());
        try {
            User user= mDbUtils.findFirst(Selector.from(User.class).where("userID","==",userInfo.getUserID()));
            if(user == null){
                mDbUtils.save(mUserInfo);
            }else{
                mDbUtils.delete(User.class, WhereBuilder.b("userID","==",userInfo.getUserID()));
                mDbUtils.save(mUserInfo);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }

    }

    /**
     * 根据userid返回本地获取的用户信息
     * @param userid
     * @return the UserInfo we need to show
     * @throws DbException
     */
    public UserInfo findUserByUserId(String userid) throws DbException {
        User user = mDbUtils.findFirst(Selector.from(User.class).where("userID", "==", userid));
        if(user == null){
            return null;
        }else{
            return new UserInfo(user.getUserID(),user.getUserNickname(), Uri.parse(PathUtils.createPortriateUrl(userid,user.getUserHeadUrl())));
        }
    }
}
