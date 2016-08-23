package com.deeal.exchange.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/7/29.
 */
public class Sort {
    private String sortType;
    private String sortTypeID;

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public String getSortTypeID() {
        return sortTypeID;
    }

    public void setSortTypeID(String sortTypeID) {
        this.sortTypeID = sortTypeID;
    }

    /**
     *
     * @param json
     * @return 排序方法列表
     */
    public static ArrayList<Sort> parseJson(String json){
        ArrayList<Sort> sorts = new ArrayList<Sort>();
        try {
            JSONObject result = new JSONObject(json);
            JSONArray data = result.getJSONArray("data");
            for(int i = 0; i<data.length(); i ++){
                Sort sort = new Sort();
                JSONObject sortJSON = new JSONObject(data.get(i).toString());
                sort.setSortType(sortJSON.getString("sortTypeName"));
                sort.setSortTypeID(sortJSON.getString("sortTypeInfo"));
                sorts.add(sort);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  sorts;
    }

    public static ArrayList<String> parseList(ArrayList<Sort> sorts){
        ArrayList<String> list = new ArrayList<String>();
        for(int i = 0 ;i<sorts.size();i++){
            list.add(sorts.get(i).getSortType());
        }
        return list;
    }
}
