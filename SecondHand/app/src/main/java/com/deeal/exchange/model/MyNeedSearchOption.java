package com.deeal.exchange.model;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.ab.util.dct.IFDCT;
import com.deeal.exchange.Bean.CommodityListItemBean;
import com.deeal.exchange.application.MyApplication;
import com.deeal.exchange.tools.DateUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by gxy on 2015/8/24.
 */
public class MyNeedSearchOption {
   /*
  "tokenID" : "xxx",
    "startOffset" : "0", 查询起始偏移
    "pageCount" : 10, 每次查询页数
    "merchandiseTypeID" : "1", 商品类型
    "cityID" : "1",  城市ID
    "collegeID" : "1", 学校ID
    "searchKey" :  "-1", 搜索关键字，查询所有为"-1"
    假设要查询第11到第20条记录，startOffset, pageCount 分别为: 10, 10*/

    private String tokenID;
    private String startOffset;
    private String pageCount;
    private String searchKey;
    private int currentPage;
    private String merchandiseTypeID;
    private String cityID;
    private String collegeID;

    public String getCityID() {
        return cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }

    public String getCollegeID() {
        return collegeID;
    }

    public void setCollegeID(String collegeID) {
        this.collegeID = collegeID;
    }

    public String getMerchandiseTypeID() {
        return merchandiseTypeID;
    }

    public void setMerchandiseTypeID(String merchandiseTypeID) {
        this.merchandiseTypeID = merchandiseTypeID;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public String getStartOffset() {
        return startOffset;
    }

    public void setStartOffset(String startOffset) {
        this.startOffset = startOffset;
    }

    public String getTokenID() {
        return tokenID;
    }

    public void setTokenID(String tokenID) {
        this.tokenID = tokenID;
    }

    public static String createJson(Context context, MerchandiseSearchOption merchandiseSearchOption) throws JSONException {
        String json = "";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("tokenID", MyApplication.mUser.getTokenId());
        jsonObject.put("startOffset", merchandiseSearchOption.getPageCount());
        jsonObject.put("pageCount", 10);
        jsonObject.put("searchKey", -1);
        jsonObject.put("cityID", merchandiseSearchOption.getCityId());
        jsonObject.put("collegeID", merchandiseSearchOption.getSchoolId());
        jsonObject.put("merchandiseTypeID", merchandiseSearchOption.getMerchandiseTypeId());
        json = jsonObject.toString();
        return json;
    }

    public static ArrayList<CommodityListItemBean> parseJson(String json) throws JSONException {
        ArrayList<CommodityListItemBean> items = new ArrayList<CommodityListItemBean>();
        JSONObject jsonObject = new JSONObject(json);
        if (jsonObject.getString("status").equals("SUCCESS")) {

            JSONArray jsonArray = new JSONArray(jsonObject.getString("data"));
            for (int i = 0; i < jsonArray.length(); i++) {
                CommodityListItemBean commodityListItemBean = new CommodityListItemBean();
                JSONObject data = jsonArray.getJSONObject(i);
                commodityListItemBean.setUserheadurl(data.getString("portraitPath"));
                commodityListItemBean.setUsername(data.getString("userName"));
                commodityListItemBean.setInfo(data.getString("info"));
                commodityListItemBean.setPublishtime(DateUtils.FormatData(data.getString("publishedTime")));
                commodityListItemBean.setUserid(data.getString("userID"));
                items.add(commodityListItemBean);
            }

        } else{
        }
        return items;
    }

}
