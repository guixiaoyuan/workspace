package com.deeal.exchange.tools;

import com.deeal.exchange.application.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2015/7/29.
 */
public class CityListJson {


    public static ArrayList<HashMap<String, String>> parseJson(String jsonData) {
        ArrayList<HashMap<String, String>> cityLists = new ArrayList<HashMap<String, String>>();
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            if (jsonObject.getString("status").equals("SUCCESS")) {
                JSONArray data = new JSONArray(jsonObject.getString("data"));
                for (int i = 0; i < data.length(); i++) {
                    HashMap<String, String> city = new HashMap<String, String>();
                    JSONObject json = data.getJSONObject(i);
                    city.put("cityID", json.getString("cityID"));
                    city.put("cityName", json.getString("cityName"));
                    cityLists.add(city);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cityLists;
    }
    public static String createJson(String cityID) throws JSONException {

        String json = "";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("tokenID", MyApplication.mUser.getTokenId());
        jsonObject.put("cityID",cityID);
        jsonObject.put("tag","0");
        json = jsonObject.toString();
        return json;
    }
}
