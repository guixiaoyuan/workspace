package com.deeal.exchange.tools;

import com.deeal.exchange.model.ISBNmodel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 把服务器返回实例解析成ISBNmodel类
 * Created by Administrator on 2015/7/18.
 */
public class ISBNparse {
    public static ISBNmodel parseISBN(String result) {
        ISBNmodel mISBNmodel = new ISBNmodel();
        JSONObject data;
        try {
            data = new JSONObject(result);
            JSONObject body = data.getJSONObject("showapi_res_body");
            JSONArray codeData = body.getJSONArray("codeData");
            JSONObject info = (JSONObject) codeData.get(0);
            mISBNmodel.setGoodsName(info.getString("goodsName"));
            mISBNmodel.setEngName(info.getString("engName"));
            mISBNmodel.setGoodsType(info.getString("goodsType"));
            mISBNmodel.setProdAddr(info.getString("prodAddr"));
            mISBNmodel.setInprice(info.getString("inprice"));
            mISBNmodel.setPrice(info.getString("price"));
            mISBNmodel.setManuName(info.getString("manuName"));
            mISBNmodel.setNote(info.getString("note"));
            mISBNmodel.setSpec(info.getString("spec"));
            mISBNmodel.setTrademark(info.getString("trademark"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mISBNmodel;
    }
}
