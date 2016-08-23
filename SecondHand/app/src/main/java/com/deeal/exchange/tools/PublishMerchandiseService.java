package com.deeal.exchange.tools;

import com.deeal.exchange.model.MerchandiseInfo;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2015/7/16.
 * used to do something with the model MerchandiseInfo
 */
public class PublishMerchandiseService {
    /**
     * change the MerchandiseInfo class to JSONObject
     *
     * @param info
     * @return jsonObject
     */
    public static JSONObject createjson(MerchandiseInfo info) {
        JSONObject jsoninfo = new JSONObject();
        try {
            jsoninfo.put("tokenID", info.getTokenID());
            jsoninfo.put("college", info.getCollege());
            jsoninfo.put("description", info.getDescription());
            jsoninfo.put("title", info.getTitle());
            jsoninfo.put("price", info.getPrice());
            jsoninfo.put("incomePrice", info.getIncomePrice());
            jsoninfo.put("carriage", info.getCarriage());
            jsoninfo.put("location", info.getLocation());
            jsoninfo.put("recommendation", info.getRecommendation());
            jsoninfo.put("inspection", info.getInspection());
            jsoninfo.put("matching", info.getMatching());
            jsoninfo.put("swap", info.getSwp());
            jsoninfo.put("merchandiseTypeID", info.getMerchandiseTypeID());
            jsoninfo.put("city", info.getCity());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsoninfo;
    }
}
