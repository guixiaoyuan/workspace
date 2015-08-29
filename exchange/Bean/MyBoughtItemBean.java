package com.deeal.exchange.Bean;

import android.content.Context;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Sunqiyong on 2015/7/14.
 */
public class MyBoughtItemBean {
    private String imv_goods = "";
    private String name_goods = "";
    private String price_goods = "";
    private String condition_goods = "";
    private String contact_seller = "";
    private String old_price = "";
    private String orderID ;
    private String sellerID ;
    private String buyerID;
    private String merchandiseID;

    private String get_bought_list_host = "http://192.168.1.151:5000/get_bought_list";


    public MyBoughtItemBean() {

    }

    public String getImv_goods() {
        return imv_goods;
    }

    public void setImv_goods(String imv_goods) {
        this.imv_goods = imv_goods;
    }

    public String getName_goods() {
        return name_goods;
    }

    public void setName_goods(String name_goods) {
        this.name_goods = name_goods;
    }

    public String getPrice_goods() {
        return price_goods;
    }

    public void setPrice_goods(String price_goods) {
        this.price_goods = price_goods;
    }

    public String getCondition_goods() {
        return condition_goods;
    }

    public void setCondition_goods(String condition_goods) {
        this.condition_goods = condition_goods;
    }

    public String getContact_seller() {
        return contact_seller;
    }

    public void setContact_seller(String contact_seller) {
        this.contact_seller = contact_seller;
    }

    public String getOld_price() {
        return old_price;
    }

    public void setOld_price(String old_price) {
        this.old_price = old_price;
    }

    /**
     * 解析从服务器传来的json数据
     *
     * @param jsonData json数据
     * @return 一个集合，里面是MyBoughtItemBean类，MyBoughtItemBean类里面是从json里面解析出来的数据
     */
    public ArrayList<MyBoughtItemBean> parseSearchjson(String jsonData) {

        ArrayList<MyBoughtItemBean> items = new ArrayList<MyBoughtItemBean>();
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            if (jsonObject.getString("status").equals("SUCCESS")) {
                JSONArray data = new JSONArray(jsonObject.getString("data"));
                for (int i = 0; i < data.length(); i++) {
                    JSONObject json = data.getJSONObject(i);
                    MyBoughtItemBean item = new MyBoughtItemBean();
                    JSONArray imvs = new JSONArray(json.getString("imgPath"));
                    item.setOrderID(json.getString("orderID"));
                    item.setImv_goods(imvs.getString(0));
                    item.setName_goods(json.getString("info"));
                    item.setOld_price(json.getString("oldPrice"));
                    item.setPrice_goods(json.getString("currentPrice"));
                    item.setCondition_goods(json.getString("orderState"));
                    item.setContact_seller(json.getString("merchandiseID"));
                    item.setMerchandiseID(json.getString("merchandiseID"));
                    item.setSellerID(json.getString("sellerID"));
                    item.setBuyerID(json.getString("buyerID"));
                    items.add(item);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return items;
    }


    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getSellerID() {
        return sellerID;
    }

    public void setSellerID(String sellerID) {
        this.sellerID = sellerID;
    }

    public String getMerchandiseID() {
        return merchandiseID;
    }

    public void setMerchandiseID(String merchandiseID) {
        this.merchandiseID = merchandiseID;
    }

    public String getBuyerID() {
        return buyerID;
    }

    public void setBuyerID(String buyerID) {
        this.buyerID = buyerID;
    }
}
