package com.deeal.exchange.tools;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2015/8/14.
 */
public class FeeJson {
    public static HashMap<String, String> parseJson(String jsonData) {
        HashMap<String, String> fee = new HashMap<String, String>();
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            if (jsonObject.getString("status").equals("SUCCESS")) {
                    JSONObject json = new JSONObject(jsonObject.getString("data"));
                    fee.put("recommendPrice", json.getString("recommendPrice"));
                    fee.put("matchPrice", json.getString("matchPrice"));
                    fee.put("inspectPrice", json.getString("inspectPrice"));
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fee;
    }
}
