package com.deeal.exchange.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/7/29.
 */
public class Colledge {
    private String  colledgeName;
    private String colledgeID;

    public String getColledgeName() {
        return colledgeName;
    }

    public void setColledgeName(String colledgeName) {
        this.colledgeName = colledgeName;
    }

    public String getColledgeID() {
        return colledgeID;
    }

    public void setColledgeID(String colledgeID) {
        this.colledgeID = colledgeID;
    }

    /**
     * j解析 返回大学列表
     * @param json
     * @return
     */
    public static ArrayList<Colledge> parseJson(String json){
        ArrayList<Colledge> colledges = new ArrayList<Colledge>();
        try {
            JSONObject result = new JSONObject(json);
            JSONArray data  = result.getJSONArray("data");
            for(int i = 0; i<data.length();i++){
                JSONObject colledgeJSON = new JSONObject(data.get(i).toString());
                Colledge colledge = new Colledge();
                colledge.setColledgeID(colledgeJSON.getString("collegeID"));
                colledge.setColledgeName(colledgeJSON.getString("collegeName"));
                colledges.add(colledge);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return colledges;
    }

    public static ArrayList<String> parseList(ArrayList<Colledge> colledges){
        ArrayList<String> list = new ArrayList<String>();
        for(int i = 0 ;i<colledges.size();i++){
            list.add(colledges.get(i).getColledgeName());
        }
        return list;
    }
}
