package com.deeal.exchange.tools;

import android.annotation.TargetApi;
import android.os.Build;

import com.deeal.exchange.model.EshopInfo;
import com.deeal.exchange.model.JuheISBNmodel;
import com.deeal.exchange.model.ShopInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 聚合数据接口条形码扫描接口信息解析封装
 * Created by Administrator on 2015/7/21.
 */
public class JuHeISBNparse {
    /**
     * 把获取的json解析为信息
     *
     * @param json
     * @param info
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void jsonToInfo(String json, JuheISBNmodel info) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject result = (JSONObject) jsonObject.get("result");
            JSONObject summary = (JSONObject) result.get("summary");
            info.setBarcode(summary.getString("barcode"));
            info.setName(summary.getString("name"));
            info.setImgurl(summary.getString("imgurl"));
            info.setInterval(summary.getString("interval"));
            List<ShopInfo> shopInfos = new ArrayList<ShopInfo>();
            JSONArray shops = new JSONArray(result.get("shop"));
            for (int i = 0; i < shops.length(); i++) {
                ShopInfo shopinfo = new ShopInfo();
                JSONObject shop = (JSONObject) shops.get(i);
                shopinfo.setPrice((Integer) shop.get("price"));
                shopinfo.setShopname((String) shop.get("shopname"));
                shopInfos.add(shopinfo);
            }
            info.setShop(shopInfos);
            List<EshopInfo> eshopInfos = new ArrayList<EshopInfo>();
            JSONArray eshops = new JSONArray(result.get("eshop"));
            for (int j = 0; j < eshops.length(); j++) {
                EshopInfo eshopInfo = new EshopInfo();
                JSONObject eshop = (JSONObject) eshops.get(j);
                eshopInfo.setPrice((Integer) eshop.get("price"));
                eshopInfo.setShopname(eshop.getString("shopname"));
                eshopInfos.add(eshopInfo);
            }
            info.setEshop(eshopInfos);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
