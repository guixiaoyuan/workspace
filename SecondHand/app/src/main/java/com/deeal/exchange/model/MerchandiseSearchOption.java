package com.deeal.exchange.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.deeal.exchange.activity.LoginActivity;
import com.deeal.exchange.application.Constant;
import com.deeal.exchange.application.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 */
public class MerchandiseSearchOption {
    /**
     * 城市ID，0为全部
     */
    private String cityId;
    /**
     * 学校ID，0为全部
     */
    private String schoolId;
    /**
     * 排序方式ID，0为默认
     */
    private String sorttypeId;
    /**
     * 每页获取商品个数
     */
    private int pageCount;
    /**
     * 当前页数
     */
    private int currentPage;
    /**
     * 商品种类，0为全部
     */
    private String merchandiseTypeId;

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getSorttypeId() {
        return sorttypeId;
    }

    public void setSorttypeId(String sorttypeId) {
        this.sorttypeId = sorttypeId;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public String getMerchandiseTypeId() {
        return merchandiseTypeId;
    }

    public void setMerchandiseTypeId(String merchandiseTypeId) {
        this.merchandiseTypeId = merchandiseTypeId;
    }

    /**
     * 构建用于查询商品列表的json
     * @param option
     * @return
     * @throws JSONException
     */
    public static String createJson(Context context ,MerchandiseSearchOption option) throws JSONException {
        String jsonResult = "";
        JSONObject jsonObject = new JSONObject();
        // sortType在服务器端是发布时间
        jsonObject.put("sortType", option.getSorttypeId());
        //type在服务器端是id
        jsonObject.put("merchandiseType", option.getMerchandiseTypeId());
        jsonObject.put("college", option.getSchoolId());
        jsonObject.put("pageCount", 10);
        if(MyApplication.mUser == null){
            Intent intent = new Intent();
            intent.setClass(context, LoginActivity.class);
            ((Activity)context).startActivityForResult(intent, Constant.GOTOLOGIN);
            return null;
        }else{
            if(MyApplication.mUser.getIsLogin()){
                jsonObject.put("tokenID", MyApplication.mUser.getTokenId());
            }else{
                Intent intent = new Intent();
                intent.setClass(context, LoginActivity.class);
                ((Activity)context).startActivityForResult(intent, Constant.GOTOLOGIN);
                return null;
            }
        }
        jsonObject.put("start", option.getCurrentPage());
        jsonObject.put("city", option.getCityId());
        jsonResult = jsonObject.toString();
        return jsonResult;
    }
}
