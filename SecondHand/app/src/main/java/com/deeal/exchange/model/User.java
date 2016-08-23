package com.deeal.exchange.model;


import com.deeal.exchange.Bean.CommodityListItemBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class User {
    private String id;
    private String UserID;
    private String ryTokenID;
    private String TokenId;
    //tel
    private String tel;
    //password
    private String UserPwd;
    //imgPath
    private String UserHeadUrl;
    //userName
    private String UserNickname;
    //gender
    private int gender;
    private List<Address> UserAddress;
    private Boolean isLogin;
    private String UserIntro;
    //residence
    private String UserHome;
    private List<CommodityListItemBean> mySold;
    private List<CommodityListItemBean> myBought;
    private String addressID;



    public User(){
        this.UserIntro = "这个人比较懒什么都没留下";
        this.gender = 1;
        this.UserNickname = "小牛牛";
        this.UserHome = "南京市";
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getUserPwd() {
        return UserPwd;
    }

    public void setUserPwd(String userPwd) {
        UserPwd = userPwd;
    }

    public String getUserHeadUrl() {
        return UserHeadUrl;
    }

    public void setUserHeadUrl(String userHeadUrl) {
        UserHeadUrl = userHeadUrl;
    }

    public String getUserNickname() {
        return UserNickname;
    }

    public void setUserNickname(String userNickname) {
        UserNickname = userNickname;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }


    public Boolean getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(Boolean isLogin) {
        this.isLogin = isLogin;
    }

    public String getUserIntro() {
        return UserIntro;
    }

    public void setUserIntro(String userIntro) {
        UserIntro = userIntro;
    }

    public String getUserHome() {
        return UserHome;
    }

    public void setUserHome(String userHome) {
        UserHome = userHome;
    }

    public String getTokenId() {
        return TokenId;
    }

    public void setTokenId(String tokenId) {
        TokenId = tokenId;
    }


    public List<Address> getUserAddress() {
        return UserAddress;
    }

    public void setUserAddress(List<Address> userAddress) {
        UserAddress = userAddress;
    }

    public String getAddressID() {
        return addressID;
    }

    public void setAddressID(String addressID) {
        this.addressID = addressID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    /**
     * json解析user
     */
    public void getinfofromjson(String json) {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(json);
            this.setUserNickname(jsonObject.get("userName").toString());
            this.setUserIntro(jsonObject.get("info").toString());
            this.setUserHeadUrl(jsonObject.get("portraitPath").toString());
            this.setGender(jsonObject.getInt("gender"));
            this.setUserHome(jsonObject.get("residence").toString());
            this.setUserID(jsonObject.getString("userID"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 把User封装成JSON
     */
    public JSONObject toJson() {
        JSONObject info = new JSONObject();
        try {
            info.put("tokenID", this.TokenId);
            info.put("gender", this.gender);
            info.put("info", this.UserIntro);
            info.put("residence", this.getUserHome());
            info.put("userName", this.UserNickname);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return info;
    }

    public String getRyTokenID() {
        return ryTokenID;
    }

    public void setRyTokenID(String ryTokenID) {
        this.ryTokenID = ryTokenID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
