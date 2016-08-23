package com.deeal.exchange.model;

import java.util.ArrayList;

/**
 * 发布所需要的商品信息
 * Created by Administrator on 2015/7/16.
 * ### 1.1 发布商品
 * imgList : ["file1", "file2"] 图片上传时的参数列表
 * merchandiseInfo : {
 * "description": "Nine",
 * "classification": "0",
 * "title": "camera",
 * "price": 20,
 * "tokenID": "646877eb74094da28df1aef7ade08a02",
 * "userID": "1234",
 * "incomePrice": 60,
 * "carriage": 6,
 * "college": "Nanjing University",
 * "location": "Nanjing",
 * "recommendation": true,
 * "inspection": true,
 * "matching": true,
 * "swp": true
 * }
 * return value :　{status:"SUCCESS/FAILED",data:merchandiseID/"Bad tokenID"}
 * *接口格式post_merchandise()
 */

public class MerchandiseInfo {
    private String merchandiseID;
    private String tokenID;
    /**
     * /描述
     */
    private String description;
    /**
     * 标题
     */
    private String title;
    /**
     * 分类，0,1,2,3。。。。。
     */
    private String merchandiseTypeID;
    /**
     * 价格
     */
    private int price;
    /**
     * 原价
     */
    private int incomePrice;
    /**
     * 快递费
     */
    private int carriage;
    /**
     * 大学
     */
    private String college;
    /**
     * 位置
     */
    private String location;
    /**
     * 推荐
     */
    private Boolean recommendation;
    /**
     * 验货
     */
    private Boolean inspection;
    /**
     * 匹配
     */
    private Boolean matching;
    /**
     * 物物交换
     */
    private Boolean swp;

    /**
     * 城市
     */
    private String city;

    private ArrayList<String> imgList;

    public String getTokenID() {
        return tokenID;
    }

    public void setTokenID(String tokenID) {
        this.tokenID = tokenID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMerchandiseTypeID() {
        return merchandiseTypeID;
    }

    public void setMerchandiseTypeID(String classification) {
        this.merchandiseTypeID = classification;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getIncomePrice() {
        return incomePrice;
    }

    public void setIncomePrice(int incomePrice) {
        this.incomePrice = incomePrice;
    }

    public int getCarriage() {
        return carriage;
    }

    public void setCarriage(int carriage) {
        this.carriage = carriage;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(Boolean recommendation) {
        this.recommendation = recommendation;
    }

    public Boolean getInspection() {
        return inspection;
    }

    public void setInspection(Boolean inspection) {
        this.inspection = inspection;
    }

    public Boolean getMatching() {
        return matching;
    }

    public void setMatching(Boolean matching) {
        this.matching = matching;
    }

    public Boolean getSwp() {
        return swp;
    }

    public void setSwp(Boolean swp) {
        this.swp = swp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public ArrayList<String> getImgList() {
        return imgList;
    }

    public void setImgList(ArrayList<String> imgList) {
        this.imgList = imgList;
    }

    public String getMerchandiseID() {
        return merchandiseID;
    }

    public void setMerchandiseID(String merchandiseID) {
        this.merchandiseID = merchandiseID;
    }
}
