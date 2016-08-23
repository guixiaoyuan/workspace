package com.deeal.exchange.Bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/7/10.
 */
public class AddressListItemBean implements Serializable {
    private String addressbuychoose = "";    //孙其勇添加，适配给buyactivity
    private String addresslist = " ";         //地址的集合
    private String mConsignee = " ";      //收货人
    private String mPhoneNumber = " ";    //电话号码
    private String mProvince = " ";       //省份
    private String mCity = " ";           //城市
    private String mArea = " ";           //地区
    private String mAddress = " ";        //地址
    private String mPostalcode = " ";     //邮编
    private int isSetDefault = 0;   //是否设为默认
    private String addressName = " ";
    private String addressID = " ";


    public String getAddresslist() {
        String consignee = getmConsignee();
        String province = getmProvince();
        String city = getmCity();
        String area = getmArea();
        addresslist = consignee + " " + province + " " + city + " " + area;
        return addresslist;
    }

    /**
     * 孙其勇添加
     *
     * @return 省+市+区+具体地址
     */
    public String getAddressbuychoose() {
        String province = getmProvince();
        String city = getmCity();
        String area = getmArea();
        String address = getmAddress();
        addressbuychoose = province + " " + city + " " + area + " " + address;
        return addressbuychoose;
    }



    public String getAddressID() {
        return addressID;
    }

    public void setAddressID(String addressID) {
        this.addressID = addressID;
    }


    public String getmConsignee() {
        return mConsignee;
    }

    public void setmConsignee(String mConsignee) {
        this.mConsignee = mConsignee;
    }


    public String getmPhoneNumber() {
        return mPhoneNumber;
    }

    public void setmPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }

    public String getmProvince() {
        return mProvince;
    }

    public void setmProvince(String mProvince) {
        this.mProvince = mProvince;
    }

    public String getmCity() {
        return mCity;
    }

    public void setmCity(String mCity) {
        this.mCity = mCity;
    }

    public String getmArea() {
        return mArea;
    }

    public void setmArea(String mArea) {
        this.mArea = mArea;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getmPostalcode() {
        return mPostalcode;
    }

    public void setmPostalcode(String mPostalcode) {
        this.mPostalcode = mPostalcode;
    }

    public int getIsSetDefault() {
        return isSetDefault;
    }

    public void setSetDefault(int isSetDefault) {
        this.isSetDefault = isSetDefault;
    }


    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getAddressName() {
        return addressName;
    }



}
