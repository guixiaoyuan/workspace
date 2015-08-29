package com.deeal.exchange.tools;

import com.deeal.exchange.Bean.companyNameBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/8/1.
 */
public class CompanyNameJson {
    public static ArrayList<companyNameBean> parseJson(String jsonData) {
        ArrayList<companyNameBean> names = new ArrayList<companyNameBean>();
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            if (jsonObject.getString("status").equals("SUCCESS")) {
                JSONArray data = new JSONArray(jsonObject.getString("data"));
                for (int i = 0; i < data.length(); i++) {
                    companyNameBean nameItem = new companyNameBean();
                    JSONObject json = data.getJSONObject(i);
                    nameItem.setCompanyName(json.getString("companyName"));
                    nameItem.setCompanyID(json.getString("companyID"));
                    nameItem.setAbbr(json.getString("abbr"));
                    nameItem.setImgPath(json.getString("imgPath"));
                    names.add(nameItem);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return names;
    }
}
