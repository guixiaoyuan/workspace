package com.deeal.exchange.tools;

import android.util.Log;
import android.widget.Toast;

import com.deeal.exchange.application.MyApplication;
import com.deeal.exchange.application.Path;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2015/8/26.
 */
public class Price {
    public  void upPrice(String taskID,String price) {
        String JsonReault = " ";
        try {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("tokenID", MyApplication.mUser.getTokenId());
                jsonObject.put("taskID",taskID);
                jsonObject.put("price",price);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonReault = jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestParams params = new RequestParams();
        params.addBodyParameter("param",JsonReault);
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST, Path.expresspay, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

            }

            @Override
            public void onFailure(HttpException e, String s) {
                e.printStackTrace();
            }
        });
    }

}
