package com.deeal.exchange.tools;

import com.deeal.exchange.Bean.CollegeBean;
import com.deeal.exchange.Bean.companyNameBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/8/10.
 */
public class CollegeJson {
    public static ArrayList<CollegeBean> parseJson(String jsonData) {
        ArrayList<CollegeBean> names = new ArrayList<CollegeBean>();
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            if (jsonObject.getString("status").equals("SUCCESS")) {
                JSONArray data = new JSONArray(jsonObject.getString("data"));
                for (int i = 0; i < data.length(); i++) {
                    CollegeBean nameItem = new CollegeBean();
                    JSONObject json = data.getJSONObject(i);
                    nameItem.setCollegeID(json.getString("collegeID"));
                    nameItem.setCollegeName(json.getString("collegeName"));
                    names.add(nameItem);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return names;
    }

    public static ArrayList<CollegeBean> parseJsonMyNeed(String jsonData){
        ArrayList<CollegeBean> names = new ArrayList<CollegeBean>();
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            if (jsonObject.getString("status").equals("SUCCESS")) {
                    CollegeBean nameItem = new CollegeBean();
                    nameItem.setCollegeID(jsonObject.getString("collegeID"));
                    nameItem.setCollegeName(jsonObject.getString("collegeName"));
                    names.add(nameItem);
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return names;
    }

    public static ArrayList<String> parseList(ArrayList<CollegeBean> collegeBeans){
        ArrayList<String> list = new ArrayList<String>();
        for(int i = 0 ;i<collegeBeans.size();i++){
            list.add(collegeBeans.get(i).getCollegeName());
        }
        return list;
    }
}
