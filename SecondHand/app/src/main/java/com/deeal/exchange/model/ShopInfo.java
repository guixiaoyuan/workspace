package com.deeal.exchange.model;

/**
 * Created by Administrator on 2015/7/21.
 * 条码查询获取的商店列表信息内容
 * "shop": [
 * {
 * "name": "绿箭 绿茶薄荷味40粒瓶装",
 * "price": 6.8,
 * "shopname": "家乐福"
 * }
 * ],
 */
public class ShopInfo {
    private int price;
    private String shopname;


    public float getPrice() {
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
}
