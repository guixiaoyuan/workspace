package com.deeal.exchange.tools;


import com.deeal.exchange.Bean.AddressListItemBean;
import com.deeal.exchange.application.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by weixianbin on 2015/7/17.
 */
public class AddressJson {
    private String addressID;

    public void setAddressID(String addressID) {
        this.addressID = addressID;
    }

    public String getAddressID() {
        return addressID;
    }


    /*编辑后得到的json数据*/
    public static String getEditJson(AddressListItemBean itemBean) {
        String jsonresult = " ";   //定义返回字符串
        try {
            JSONObject jsonObj = new JSONObject();   //address对象，json形式
            jsonObj.put("addressName", itemBean.getAddressName());
            jsonObj.put("description", itemBean.getmAddress());
            jsonObj.put("tokenID", MyApplication.mUser.getTokenId());
            jsonObj.put("province", itemBean.getmProvince());
            jsonObj.put("city", itemBean.getmCity());
            jsonObj.put("area", itemBean.getmArea());
            jsonObj.put("tel", itemBean.getmPhoneNumber());
            jsonObj.put("isdefault", itemBean.getIsSetDefault());
            jsonObj.put("zipCode", itemBean.getmPostalcode());
            jsonObj.put("userName", itemBean.getmConsignee());
            jsonObj.put("addressID", itemBean.getAddressID());
            jsonresult = jsonObj.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonresult;
    }

    /*创建新的json数据*/

    public static String getCreateJson(AddressListItemBean itemBean) {
        String jsonresult = " ";   //定义返回字符串

        try {
            JSONObject jsonObj = new JSONObject();   //address对象，json形式
            jsonObj.put("addressName", itemBean.getAddressName());
            jsonObj.put("description", itemBean.getmAddress());
            jsonObj.put("tokenID", MyApplication.mUser.getTokenId());
            jsonObj.put("province", itemBean.getmProvince());
            jsonObj.put("city", itemBean.getmCity());
            jsonObj.put("area", itemBean.getmArea());
            jsonObj.put("tel", itemBean.getmPhoneNumber());
            jsonObj.put("isdefault", itemBean.getIsSetDefault());
            jsonObj.put("zipCode", itemBean.getmPostalcode());
            jsonObj.put("userName", itemBean.getmConsignee());
            jsonresult = jsonObj.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonresult;
    }


    /*解析服务器传来的的json数据*/
    public static ArrayList<AddressListItemBean> parseSearchJson(String jsonData) {
        ArrayList<AddressListItemBean> items = new ArrayList<AddressListItemBean>();
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            if (jsonObject.getString("status").equals("SUCCESS")) {
                JSONArray data = new JSONArray(jsonObject.getString("data"));
                for (int i = 0; i < data.length(); i++) {
                    AddressListItemBean item = new AddressListItemBean();
                    JSONObject json = data.getJSONObject(i);
                    item.setmAddress(json.getString("description"));
                    item.setmPhoneNumber(json.getString("tel"));
                    item.setmProvince(json.getString("province"));
                    item.setmCity(json.getString("city"));
                    item.setmArea(json.getString("area"));
                    item.setmPostalcode(json.getString("zipCode"));
                    item.setSetDefault(Integer.parseInt(json.getString("isdefault")));
                    item.setAddressID(json.getString("addressID"));
                    item.setmConsignee(json.getString("userName"));
                    item.setAddressName(json.getString("addressName"));
                    items.add(item);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }
}
