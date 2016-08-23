package com.deeal.exchange.model;

import java.util.List;

/**
 * Created by Administrator on 2015/7/21.
 * summary	Object	返回商品基本信息
 * barcode	String	商品条码值
 * name	String	商品名称（可能为空）
 * imgurl	string	商品图片地址（可能为空）
 * shopNum	int	商品电商价格信息条数
 * eshopNum	int	商品电商价格信息条数
 * interval	string	价格区间
 * shop	List	返回超市价格信息集合
 * （可能为空，根据上面得到的shopunm判断）
 * name	String	商品名称（可能为空）
 * price	int	商品在该超市的价格
 * shopname	String	超市名称
 * eshop	List	返回电商价格信息集合
 * （可能为空，根据上面得到的eshopunm判断）
 * name	String	商品名称（可能为空）
 * price	int	商品在该电商上的价格
 * shopname	String	电商名称
 * dsid	String	电商id（可能为空）
 */
public class JuheISBNmodel {
    private String barcode;
    private String name;
    private String imgurl;
    private int shopNum;
    private int eshopNum;
    private String interval;
    private List shop;
    private List eshop;

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public int getShopNum() {
        return shopNum;
    }

    public void setShopNum(int shopNum) {
        this.shopNum = shopNum;
    }

    public int getEshopNum() {
        return eshopNum;
    }

    public void setEshopNum(int eshopNum) {
        this.eshopNum = eshopNum;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public List getShop() {
        return shop;
    }

    public void setShop(List shop) {
        this.shop = shop;
    }

    public List getEshop() {
        return eshop;
    }

    public void setEshop(List eshop) {
        this.eshop = eshop;
    }
}
