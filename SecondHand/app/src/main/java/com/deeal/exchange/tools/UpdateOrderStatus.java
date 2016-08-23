package com.deeal.exchange.tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ab.fragment.AbAlertDialogFragment;
import com.ab.util.AbDialogUtil;
import com.deeal.exchange.R;
import com.deeal.exchange.activity.MainActivity;
import com.deeal.exchange.application.MyApplication;
import com.deeal.exchange.application.Path;
import com.deeal.exchange.view.zxing.decoding.FinishListener;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * Created by gxy on 2015/8/13.
 * 更新订单状态
 */
public class UpdateOrderStatus {

    private String staus;
    private String order;
    private Context context;

    public UpdateOrderStatus(String status, String order, Context context) {
        this.staus = status;
        this.order = order;
        this.context = context;
    }

    public void updataOrderStatus() {
        final RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("tokenID", MyApplication.mUser.getTokenId());
        requestParams.addBodyParameter("status", staus);
        requestParams.addBodyParameter("orderID", order);
        final HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.POST, Path.updateorderstatus, requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                try {
                    JSONObject jsonObject = new JSONObject(responseInfo.result);
                    if (jsonObject.getString("status") != "SUCCESS") {
                        httpUtils.send(HttpRequest.HttpMethod.POST, Path.updateorderstatus, requestParams, new RequestCallBack<String>() {

                            @Override
                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(responseInfo.result);
                                    if (jsonObject.getString("status") != "SUCCESS") {
                                        AbDialogUtil.showAlertDialog(context, "错误提示", "对不起，支付出现问题，请电话联系我们", new AbAlertDialogFragment.AbDialogOnClickListener() {
                                            @Override
                                            public void onPositiveClick() {
                                                ((Activity)context).finish();
                                            }

                                            @Override
                                            public void onNegativeClick() {
                                                ((Activity)context).finish();
                                            }
                                        });
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onFailure(HttpException e, String s) {

                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

}
