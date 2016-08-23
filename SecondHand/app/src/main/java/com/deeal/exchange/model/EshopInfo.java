package com.deeal.exchange.model;

/**
 * Created by Administrator on 2015/7/21.
 * 条码查询获取的电商列表信息内容
 * "eshop": [
 * {
 * "name": "绿箭 绿茶薄荷味40粒瓶装",
 * "price": 7.9,
 * "shopname": "1号店",
 * "dsid": "5"
 * },
 */
public class EshopInfo {
    private String name;
    private int price;
    private String shopname;
    private String dsid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getDsid() {
        return dsid;
    }

    public void setDsid(String dsid) {
        this.dsid = dsid;
    }
}
