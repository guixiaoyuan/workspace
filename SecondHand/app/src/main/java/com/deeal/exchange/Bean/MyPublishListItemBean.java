package com.deeal.exchange.Bean;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gxy on 2015/7/13.
 */
public class MyPublishListItemBean {

    private String tv_goods_name;
    private String tv_goods_price;
    private ArrayList<String> imageurls;
    private String tv_mycollect;
    private String tv_date;

    private String merchandiseId;


    public String getMerchandiseId() {
        return merchandiseId;
    }

    public void setMerchandiseId(String merchandiseId) {
        this.merchandiseId = merchandiseId;
    }

    public MyPublishListItemBean(){}

    public String getTv_mycollect() {
        return tv_mycollect;
    }

    public void setTv_mycollect(String tv_mycollect) {
        this.tv_mycollect = tv_mycollect;
    }

    public String getTv_date() {
        return tv_date;
    }

    public void setTv_date(String tv_date) {
        this.tv_date = tv_date;
    }

    public String getTv_goods_name() {
        return tv_goods_name;
    }

    public void setTv_goods_name(String tv_goods_name) {
        this.tv_goods_name = tv_goods_name;
    }

    public String getTv_goods_price() {
        return tv_goods_price;
    }

    public void setTv_goods_price(String tv_goods_price) {
        this.tv_goods_price = tv_goods_price;
    }


    public ArrayList<String> getImageurls() {
        return imageurls;
    }

    public void setImageurls(ArrayList<String> imageurls) {
        this.imageurls = imageurls;
    }
}
