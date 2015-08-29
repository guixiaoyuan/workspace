package com.deeal.exchange.model;

import com.deeal.exchange.application.Path;
import com.deeal.exchange.view.CircleImageView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/7/29. 1.9 获取城市列表
 /***
 * 无参数
 * return value : {
 "status": "SUCCESS",
 "data": [
 {
 "cityID": "0",
 "cityName": "南京"
 },
 {
 "cityID": "1",
 "cityName": "北京"
 },
 {
 "cityID": "2",
 "cityName": "上海"
 },
 {
 "cityID": "3",
 "cityName": "广州"
 },
 {
 "cityID": "4",
 "cityName": "深圳"
 }
 ]
 }
 */
public class City {
    private String cityName;
    private String cityID;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityID() {
        return cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }

    /**
     *
     * @param json
     * @return 城市列表
     */
    public static ArrayList<City> parseJson(String json){
        ArrayList<City> cities = new ArrayList<City>();
        try {
            JSONObject result = new JSONObject(json);
            JSONArray data  = result.getJSONArray("data");
            for(int i =0;i<data.length();i++){
                JSONObject cityJSON = new JSONObject(data.get(i).toString());
                City city = new City();
                city.setCityID(cityJSON.get("cityID").toString());
                city.setCityName(cityJSON.getString("cityName"));
                cities.add(city);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cities;
    }

    public static ArrayList<String> parseList(ArrayList<City> cities){
        ArrayList<String> list = new ArrayList<String>();
        for(int i = 0 ;i<cities.size();i++){
            list.add(cities.get(i).getCityName());
        }
        return list;
    }

}
