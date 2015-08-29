package com.deeal.exchange.tools;

import com.deeal.exchange.application.MyApplication;
import com.deeal.exchange.application.Path;
import com.deeal.exchange.model.MerchandiseInfo;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * Created by Administrator on 2015/8/10.
 */
public class MySoldJson {
    /*删除订单*/
    public void deleteOrder(String orderID,HttpSuccess httpSuccess) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("tokenID", MyApplication.mUser.getTokenId());
        params.addBodyParameter("orderID", orderID);
        upMethod(params, Path.deleteorder, httpSuccess);
    }

    /*更新订单状态*/
    public void updateOrderStatus(String status,String orderID,HttpSuccess httpSuccess){
        RequestParams params = new RequestParams();
        params.addBodyParameter("tokenID", MyApplication.mUser.getTokenId());
        params.addBodyParameter("status",status);
        params.addBodyParameter("orderID",orderID);
        upMethod(params, Path.updateorderstatus, httpSuccess);
    }

    public void upMethod(RequestParams params, final String uploadHost, final HttpSuccess httpSuccess) {

        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST, uploadHost, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                httpSuccess.onsuccess(new MerchandiseInfo());
            }

            @Override
            public void onFailure(HttpException e, String s) {
                e.printStackTrace();
                httpSuccess.onfail();
            }
        });

    }
}
