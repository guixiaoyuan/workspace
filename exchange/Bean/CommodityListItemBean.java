package com.deeal.exchange.Bean;

import com.deeal.exchange.application.MyApplication;
import com.deeal.exchange.application.Path;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ding on 2015/7/6.
 */
public class CommodityListItemBean implements Serializable {
    private String Title;
    private String userid;
    private String[] imageurls;
    private ArrayList<String> picurls;
    private String username;
    private String userheadurl;
    private String publishtime;

    public String getBuyerID() {
        return buyerID;
    }

    public void setBuyerID(String buyerID) {
        this.buyerID = buyerID;
    }

    private String cost;
    private String info;
    private String collegesTypes;
    private String merchandiseID;
    private String originalCost;
    private String visitedCount;
    private String sortTypes;
    private String sortTypeId;
    private String merchandiseType;
    private String merchandiseName;

    private Boolean iscollection;
    private String CommodityId;
    private String school;
    private String schoolId;
    private String city;
    private String cityId;
    private int currentPage;
    private Integer favorite;
    private String favoriteID;
    private Boolean isFreeze;
    private Boolean isBought;

    private String buyerID;

    public String getDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(String defaultAddress) {
        this.defaultAddress = defaultAddress;
    }

    private String defaultAddress;


    public String getMerchandiseName() {
        return merchandiseName;
    }

    public void setMerchandiseName(String merchandiseName) {
        this.merchandiseName = merchandiseName;
    }

    public String getFavoriteID() {
        return favoriteID;
    }

    public void setFavoriteID(String favoriteID) {
        this.favoriteID = favoriteID;
    }

    public String getMerchandiseTypeID() {
        return merchandiseTypeID;
    }

    public void setMerchandiseTypeID(String merchandiseTypeID) {
        this.merchandiseTypeID = merchandiseTypeID;
    }

    public String getOriginalCost() {
        return originalCost;
    }

    public void setOriginalCost(String originalCost) {
        this.originalCost = originalCost;
    }

    public String getVisitedCount() {
        return visitedCount;
    }

    public void setVisitedCount(String visitedCount) {
        this.visitedCount = visitedCount;
    }

    private String merchandiseTypeID;


    public String getMerchandiseID() {
        return merchandiseID;
    }

    public void setMerchandiseID(String merchandiseID) {
        this.merchandiseID = merchandiseID;
    }

    public String getCollegesTypes() {
        return collegesTypes;
    }

    public void setCollegesTypes(String collegesTypes) {
        this.collegesTypes = collegesTypes;
    }

    public String getSortTypes() {
        return sortTypes;
    }

    public void setSortTypes(String sortTypes) {
        this.sortTypes = sortTypes;
    }

    public String getMerchandiseType() {
        return merchandiseType;
    }

    public void setMerchandiseType(String merchandiseType) {
        this.merchandiseType = merchandiseType;
    }

    public String[] getImageurls() {
        return imageurls;
    }

    public void setImageurls(String[] imageurls) {
        this.imageurls = imageurls;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserheadurl() {
        return userheadurl;
    }

    public void setUserheadurl(String userheadurl) {
        this.userheadurl = userheadurl;
    }

    public String getPublishtime() {
        return publishtime;
    }

    public void setPublishtime(String publishtime) {
        this.publishtime = publishtime;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Boolean getIscollection() {
        return iscollection;
    }

    public void setIscollection(Boolean iscollection) {
        this.iscollection = iscollection;
    }

    public String getCommodityId() {
        return CommodityId;
    }

    public void setCommodityId(String commodityId) {
        CommodityId = commodityId;
    }

    public ArrayList<String> getPicurls() {
        return picurls;
    }

    public void setPicurls(ArrayList<String> picurls) {
        this.picurls = picurls;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    private void jsontostring(String str) {
    }

    public Integer getFavorite() {
        return favorite;
    }

    public void setFavorite(Integer favorite) {
        this.favorite = favorite;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getSortTypeId() {
        return sortTypeId;
    }

    public void setSortTypeId(String sortTypeId) {
        this.sortTypeId = sortTypeId;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * 构建用于查询商品列表的json
     *
     * @param bean
     * @return
     * @throws JSONException
     */
    public static String createJson(CommodityListItemBean bean) throws JSONException {
        String jsonResult = "";
        JSONObject jsonObject = new JSONObject();
       /* jsonObject.put("sortType",bean.getSortTypes());*/
        // sortType在服务器端是发布时间
        jsonObject.put("sortType", bean.getSortTypeId());
        //type在服务器端是id
        jsonObject.put("merchandiseType", bean.getMerchandiseTypeID());
        jsonObject.put("college", bean.getSchoolId());
        jsonObject.put("pageCount", 10);
        jsonObject.put("tokenID", MyApplication.mUser.getTokenId());
        jsonObject.put("start", bean.getCurrentPage());
        jsonObject.put("city", bean.getCityId());
        jsonResult = jsonObject.toString();
        return jsonResult;
    }

    public static ArrayList<CommodityListItemBean> parseJson(String jsonData) throws JSONException {
        ArrayList<CommodityListItemBean> items = new ArrayList<CommodityListItemBean>();
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONArray jsonArray = new JSONArray(jsonObject.getString("data"));
        for (int i = 0; i < jsonArray.length(); i++) {
            CommodityListItemBean commodityListItemBean = new CommodityListItemBean();
            JSONObject json = jsonArray.getJSONObject(i);
            commodityListItemBean.setUserheadurl(json.getString("portraitPath"));
            commodityListItemBean.setUsername(json.getString("userName"));
            commodityListItemBean.setUserid(json.getString("userID"));
            commodityListItemBean.setInfo(json.getString("merchandiseName"));
            commodityListItemBean.setCost(json.getString("currentPrice"));
            commodityListItemBean.setPublishtime(json.getString("publishedTime"));
            commodityListItemBean.setCollegesTypes(json.getString("college"));
            commodityListItemBean.setMerchandiseID(json.getString("merchandiseID"));
            commodityListItemBean.setFavorite(json.getInt("favorite"));
            JSONArray imgPathJsonArray = json.getJSONArray("imgPath");
            ArrayList<String> arrayList = new ArrayList<String>();
            for (int j = 0; j < imgPathJsonArray.length(); j++) {
                arrayList.add(imgPathJsonArray.get(j).toString());
            }
            commodityListItemBean.setPicurls(arrayList);
            items.add(commodityListItemBean);
        }
        return items;
    }


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Boolean getIsFreeze() {
        return isFreeze;
    }

    public void setIsFreeze(Boolean isFreeze) {
        this.isFreeze = isFreeze;
    }

    public Boolean getIsBought() {
        return isBought;
    }

    public void setIsBought(Boolean isBought) {
        this.isBought = isBought;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
