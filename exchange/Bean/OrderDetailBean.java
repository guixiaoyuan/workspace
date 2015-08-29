package com.deeal.exchange.Bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/8/6.
 */


public class OrderDetailBean {


    private String buyerID;
    private String tokenID;
    private String orderState;
    private String merchandiseID;
    private String merchandiseName;
    private String oldPrice;
    private String sellerID;
    private String info;
    private String swap;
    private String autoShipment;
    private String inspection;
    private String addressUserName;
    private String addressDescription;
    private String orderID;
    private String aplipayID;
    private String currentPrice;
    private ArrayList<String> picurls;
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ArrayList<String> getPicurls() {
        return picurls;
    }

    public void setPicurls(ArrayList<String> picurls) {
        this.picurls = picurls;
    }

    public String getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getAplipayID() {
        return aplipayID;
    }

    public void setAplipayID(String aplipayID) {
        this.aplipayID = aplipayID;
    }

    public String getBuyerID() {
        return buyerID;
    }

    public void setBuyerID(String buyerID) {
        this.buyerID = buyerID;
    }

    public String getMerchandiseID() {
        return merchandiseID;
    }

    public void setMerchandiseID(String merchandiseID) {
        this.merchandiseID = merchandiseID;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getTokenID() {
        return tokenID;
    }

    public void setTokenID(String tokenID) {
        this.tokenID = tokenID;
    }

    public String getAddressUserName() {
        return addressUserName;
    }

    public void setAddressUserName(String addressUserName) {
        this.addressUserName = addressUserName;
    }

    public String getAddressDescription() {
        return addressDescription;
    }

    public void setAddressDescription(String addressDescription) {
        this.addressDescription = addressDescription;
    }

    public String getAutoShipment() {
        return autoShipment;
    }

    public void setAutoShipment(String autoShipment) {
        this.autoShipment = autoShipment;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInspection() {
        return inspection;
    }

    public void setInspection(String inspection) {
        this.inspection = inspection;
    }

    public String getMerchandiseName() {
        return merchandiseName;
    }

    public void setMerchandiseName(String merchandiseName) {
        this.merchandiseName = merchandiseName;
    }

    public String getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(String oldPrice) {
        this.oldPrice = oldPrice;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getSellerID() {
        return sellerID;
    }

    public void setSellerID(String sellerID) {
        this.sellerID = sellerID;
    }

    public String getSwap() {
        return swap;
    }

    public void setSwap(String swap) {
        this.swap = swap;
    }


}
