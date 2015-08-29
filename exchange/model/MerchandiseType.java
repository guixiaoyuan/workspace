package com.deeal.exchange.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/7/29.
 */
public class MerchandiseType {
    private String merchandiseType;
    private String merchandiseTypeId;
    private String imgPath;

    public String getMerchandiseType() {
        return merchandiseType;
    }

    public void setMerchandiseType(String merchandiseType) {
        this.merchandiseType = merchandiseType;
    }

    public String getMerchandiseTypeId() {
        return merchandiseTypeId;
    }

    public void setMerchandiseTypeId(String merchandiseTypeId) {
        this.merchandiseTypeId = merchandiseTypeId;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    /**
     *
     * @param json
     * @return 商品种类的列表
     */
    public static ArrayList<MerchandiseType> parseJson(String json){
        ArrayList<MerchandiseType> types = new ArrayList<MerchandiseType>();
        try {
            JSONObject result = new JSONObject(json);
            JSONArray data  = result.getJSONArray("data");
            for(int i =0;i<data.length();i++){
                JSONObject typeJSON = new JSONObject(data.get(i).toString());
                MerchandiseType type = new MerchandiseType();
                type.setMerchandiseType(typeJSON.getString("merchandiseTypeName"));
                type.setMerchandiseTypeId(typeJSON.getString("merchandiseTypeID"));
                type.setImgPath(typeJSON.getString("imgpath"));
                types.add(type);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return types;
    }

    public static ArrayList<String> parseList(ArrayList<MerchandiseType> merchandiseTypes){
        ArrayList<String> list = new ArrayList<String>();
        for(int i = 0 ;i<merchandiseTypes.size();i++){
            list.add(merchandiseTypes.get(i).getMerchandiseType());
        }
        return list;
    }

}
