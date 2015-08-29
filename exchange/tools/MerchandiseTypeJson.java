package com.deeal.exchange.tools;

import com.deeal.exchange.Bean.MerchandiseTypeBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/8/3.
 */
public class MerchandiseTypeJson {
    public static ArrayList<MerchandiseTypeBean> parseJson(String jsonData) {
        ArrayList<MerchandiseTypeBean> names = new ArrayList<MerchandiseTypeBean>();
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            if (jsonObject.getString("status").equals("SUCCESS")) {
                JSONArray data = new JSONArray(jsonObject.getString("data"));
                for (int i = 0; i < data.length(); i++) {
                    MerchandiseTypeBean nameItem = new MerchandiseTypeBean();
                    JSONObject json = data.getJSONObject(i);
                    nameItem.setMerchandiseTypeName(json.getString("merchandiseTypeName"));
                    nameItem.setMerchandiseTypeID(json.getString("merchandiseTypeID"));
                    nameItem.setImgpath(json.getString("imgpath"));
                    names.add(nameItem);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return names;
    }
}
