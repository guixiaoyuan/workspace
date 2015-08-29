package com.deeal.exchange.model;

/**
 * 条形码返回实例的模型
 * Created by Administrator on 2015/7/18.
 * {
 * "showapi_res_code": 0,
 * "showapi_res_error": "",
 * "showapi_res_body": {
 * "flag": "true",
 * "codeData": [
 * "code":"条形码 条码位数:8 13 14位",
 * "goodsName":"商品名称",
 * "engName":"简称或英文名称",
 * "spec":"规格",
 * "units":"单位",
 * "prodAddr":"产地",
 * "weight":"重量",
 * "price":"参考价格 零售价格",
 * "inPrice":"进货价格",
 * "trademark":"商标名",
 * "manufacturer_Code":"厂商/发布商/代理商",
 * "manuName":"厂商",
 * "goodsType":"分类",
 * "packNum":"包装数量",
 * "inStorageDate":"入库日期",
 * "note":"备注"
 * ]
 * }
 * }
 */
public class ISBNmodel {
    private String goodsName;
    private String engName;
    private String spec;
    private String prodAddr;
    private String price;
    private String inprice;
    private String trademark;
    private String manuName;
    private String goodsType;
    private String note;

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getProdAddr() {
        return prodAddr;
    }

    public void setProdAddr(String prodAddr) {
        this.prodAddr = prodAddr;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getInprice() {
        return inprice;
    }

    public void setInprice(String inprice) {
        this.inprice = inprice;
    }

    public String getTrademark() {
        return trademark;
    }

    public void setTrademark(String trademark) {
        this.trademark = trademark;
    }

    public String getManuName() {
        return manuName;
    }

    public void setManuName(String manuName) {
        this.manuName = manuName;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }
}
