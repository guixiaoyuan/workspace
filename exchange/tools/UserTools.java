package com.deeal.exchange.tools;

import android.util.Log;

import com.deeal.exchange.application.MyApplication;
import com.deeal.exchange.application.Path;
import com.deeal.exchange.model.User;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2015/8/5.
 */
public class UserTools {
    private onGetUserInfo onGetUserInfo;
    public UserTools(onGetUserInfo onGetUserInfo){
        this.onGetUserInfo = onGetUserInfo;
    }

    /**
     * 根據userID從服務器獲取用戶信息
     * @param userID
     */
    public void getUserInfoByID(final String userID){
        HttpUtils httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
        params.addBodyParameter("tokenID", MyApplication.mUser.getTokenId());
        params.addBodyParameter("userID", userID);
        httpUtils.send(HttpRequest.HttpMethod.POST, Path.get_user_by_id,params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.e("result",responseInfo.result+"asdfsdfasd");
                User user = new User();
                try {
                    JSONObject jsonObject = new JSONObject(responseInfo.result);
                    JSONObject data = jsonObject.getJSONObject("data");
                    user.setUserNickname(data.get("userName").toString());
                    //user.setUserIntro(jsonObject.get("info").toString());
                    user.setUserHeadUrl(data.get("portraitPath").toString());
                    //user.setGender(jsonObject.getInt("gender"));
                    //user.setUserHome(jsonObject.get("residence").toString());
                    //user.setTel(jsonObject.get("tel").toString());
                    user.setUserID(userID);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                onGetUserInfo.onGetUserInfo(user);
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }
}
