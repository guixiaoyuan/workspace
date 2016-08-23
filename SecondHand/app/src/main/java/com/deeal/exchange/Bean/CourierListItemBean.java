package com.deeal.exchange.Bean;

/**
 * Created by weixianbin on 2015/7/10.
 */
public class CourierListItemBean {
    private String ServiceNum ;      //服务编号
    private String ExpressCompany= "0"  ;     //快递公司
    private String ExpressNum="0";      //快递编号

    private String tel ;                 //手机号码
    private String expressID ;
    private String expressName = " " ;
    private String expressAddress ;       //送货地址
    private String expressGetAddress ;   //拿货地址
    private String receiver ;            //收件人
    private String expressGetID;        //代拿发布后服务器生成的ID
    /*代发*/
    private String userName ;            //寄件人
    private String receiverCity;        //收件城市
    private String addressInfo ;         //取件地址
    private String logisticsID = " ";
    private int isOverWeight = 0;
    private String tag = " ";                 //0表示代拿，1表示代发
    private String publishedTime = " ";
    private String userID = " ";
    private String companyID;
    private String cityID;
    private String logisticsCompany;
    private String companyName;
    private String expressStatusID;
    private String collegeName;
    private String collegeID;
    private String takeTime;
    private String getTime;
    private String price;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }


    public String getPublishedTime() {
        return publishedTime;
    }

    public void setPublishedTime(String publishedTime) {
        this.publishedTime = publishedTime;
    }


    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }


    public int getIsOverWeight() {
        return isOverWeight;
    }

    public void setIsOverWeight(int isOverWeight) {
        this.isOverWeight = isOverWeight;
    }

    public String getLogisticsID() {
        return logisticsID;
    }

    public void setLogisticsID(String logisticsID) {
        this.logisticsID = logisticsID;
    }


    public String getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(String addressInfo) {
        this.addressInfo = addressInfo;
    }


    public String getReceiverCity() {
        return receiverCity;
    }

    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getExpressGetID() {
        return expressGetID;
    }

    public void setExpressGetID(String expressGetID) {
        this.expressGetID = expressGetID;
    }




    public String getExpressName() {
        return expressName;
    }

    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getExpressID() {
        return expressID;
    }

    public void setExpressID(String expressID) {
        this.expressID = expressID;
    }

    public String getExpressGetAddress() {
        return expressGetAddress;
    }

    public void setExpressGetAddress(String expressGetAddress) {
        this.expressGetAddress = expressGetAddress;
    }

    public String getExpressAddress() {
        return expressAddress;
    }

    public void setExpressAddress(String expressAddress) {
        this.expressAddress = expressAddress;
    }


    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getServiceNumber() {
        return ServiceNum;
    }

    public void setServiceNumber(String ServiceNumber) {
        this.ServiceNum = ServiceNumber;
    }

    public String getExpressCompany() {
        return ExpressCompany;
    }

    public void setExpressCompany(String ExpressCompany) {
        this.ExpressCompany = ExpressCompany;
    }

    public String getExpressNum() {
        return ExpressNum;
    }

    public void setExpressNum(String ExpressNumber) {
        this.ExpressNum =ExpressNumber;
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public String getCityID() {
        return cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }

    public String getLogisticsCompany() {
        return logisticsCompany;
    }

    public void setLogisticsCompany(String logisticsCompany) {
        this.logisticsCompany = logisticsCompany;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getExpressStatusID() {
        return expressStatusID;
    }

    public void setExpressStatusID(String expressStatusID) {
        this.expressStatusID = expressStatusID;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getCollegeID() {
        return collegeID;
    }

    public void setCollegeID(String collegeID) {
        this.collegeID = collegeID;
    }

    public String getTakeTime() {
        return takeTime;
    }

    public void setTakeTime(String takeTime) {
        this.takeTime = takeTime;
    }

    public String getGetTime() {
        return getTime;
    }

    public void setGetTime(String getTime) {
        this.getTime = getTime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
