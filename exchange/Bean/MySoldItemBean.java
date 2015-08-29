package com.deeal.exchange.Bean;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Sunqiyong on 2015/7/15.
 */
public class MySoldItemBean {

    private String imv_goods = "";//商品图片
    private String name_goods = "";//商品名
    private String price_goods = "";//商品价格
    private String condition_goods = "";//商品的交易状态
    private String contact_buy = "";//买家的ID
    private String old_price = "";
    private String orderID;
    private String buyerID;
    private String merchandiseID;


    public MySoldItemBean() {

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

    public String getcontact_buy() {
        return contact_buy;
    }

    public void setcontact_buy(String contact_buy) {
        this.contact_buy = contact_buy;
    }


    /**
     * 解析我卖的商品的列表信息json
     *
     * @param json_data 需要解析的商品json
     * @return 返回一个MySoldItemBean的集合，每一个MySoldItemBean类，表示一个商品订单
     */
    public ArrayList<MySoldItemBean> parseSearchjson(String json_data) {
        ArrayList<MySoldItemBean> items = new ArrayList<MySoldItemBean>();
        try {
            JSONObject jsonObject = new JSONObject(json_data);
            if (jsonObject.getString("status").equals("SUCCESS")) {
                JSONArray data = new JSONArray(jsonObject.getString("data"));
                for (int i = 0; i < data.length(); i++) {
                    JSONObject json = data.getJSONObject(i);
                    MySoldItemBean item = new MySoldItemBean();
                    JSONArray mivs = new JSONArray(json.getString("imgPath"));
                    item.setOrderID(json.getString("orderID"));
                    item.setImv_goods(mivs.getString(0));
                    item.setName_goods(json.getString("info"));
                    item.setOld_price(json.getString("oldPrice"));
                    item.setPrice_goods(json.getString("currentPrice"));
                    item.setCondition_goods(json.getString("orderState"));
                    item.setcontact_buy(json.getString("merchandiseID"));
                    item.setBuyerID(json.getString("buyerID"));
                    item.setMerchandiseID(json.getString("merchandiseID"));
                    items.add(item);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return items;
    }

    public String getOld_price() {
        return old_price;
    }

    public void setOld_price(String old_price) {
        this.old_price = old_price;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getBuyerID() {
        return buyerID;
    }

    public void setBuyerID(String buyerID) {
        this.buyerID = buyerID;
    }

    public String getMerchandiseID() {
        return merchandiseID;
    }

    public void setMerchandiseID(String merchandiseID) {
        this.merchandiseID = merchandiseID;
    }
}
