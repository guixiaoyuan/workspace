package com.deeal.exchange.model;

/**
 * Created by Administrator on 2015/7/30.
 */
public class LocationInfo {
    /**
     * 位置描述
     */
    private String description;
    /**
     * 城市名称
     */
    private String CityName;
    /**
     * 经纬度
     */
    private double lati;
    /**
     * 经纬度
     */
    private double lonti;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public double getLati() {
        return lati;
    }

    public void setLati(double lati) {
        this.lati = lati;
    }

    public double getLonti() {
        return lonti;
    }

    public void setLonti(double lonti) {
        this.lonti = lonti;
    }
}
