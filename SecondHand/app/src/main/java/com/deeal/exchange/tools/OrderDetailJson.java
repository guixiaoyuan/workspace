package com.deeal.exchange.tools;

import android.util.Log;

import com.deeal.exchange.Bean.AddressListItemBean;
import com.deeal.exchange.Bean.CommodityListItemBean;
import com.deeal.exchange.Bean.OrderDetailBean;
import com.deeal.exchange.application.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Administrator on 2015/8/7.
 */

public class OrderDetailJson {

    public static String createOrderDetailJson(CommodityListItemBean comm, AddressListItemBean itemBean) throws JSONException {
        String json = "";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("tokenID", MyApplication.mUser.getTokenId());
        jsonObject.put("merchandiseID", comm.getMerchandiseID());
        Log.i("OrderDetailJson", comm.getMerchandiseID());
        jsonObject.put("orderState", "1");
        jsonObject.put("sellerID",comm.getUserid());
        jsonObject.put("buyerID",MyApplication.mUser.getUserID() );
        jsonObject.put("addressID", itemBean.getAddressID());
        jsonObject.put("alipayID", "0");

        json = jsonObject.toString();
        return json;
    }
}
