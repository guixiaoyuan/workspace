package com.deeal.exchange.tools;

import android.util.Log;

import com.deeal.exchange.Bean.CourierListItemBean;
import com.deeal.exchange.application.MyApplication;
import com.deeal.exchange.application.Path;
import com.deeal.exchange.model.MerchandiseInfo;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/7/18.
 */
public class CourierJson {

    /*创建create_express_post*/
    public static String getCreateTakeExpressJson(CourierListItemBean itemBean) {
        String Jsonreault = " ";   //定义返回的Json字符串
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tel", itemBean.getTel());
            jsonObject.put("expressCompany", itemBean.getExpressCompany());
            jsonObject.put("tokenID", MyApplication.mUser.getTokenId());
            jsonObject.put("receiverCity", itemBean.getReceiverCity());
            jsonObject.put("isOverWeight", itemBean.getIsOverWeight());
            jsonObject.put("addressInfo", itemBean.getAddressInfo());
            jsonObject.put("userName", itemBean.getUserName());
            jsonObject.put("expressName", itemBean.getExpressName());
            jsonObject.put("collegeID", itemBean.getCollegeID());
            jsonObject.put("takeTime", itemBean.getTakeTime());
            Jsonreault = jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Jsonreault;
    }


    /*创建add_expressJson*/
    public static String getCreateExpressJson(CourierListItemBean itemBean) {
        String Jsonreault = " ";   //定义返回的Json字符串
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tel", itemBean.getTel());
            jsonObject.put("expressCompany", itemBean.getExpressCompany());
            jsonObject.put("tokenID", MyApplication.mUser.getTokenId());
            jsonObject.put("expressAddress", itemBean.getExpressAddress());
            jsonObject.put("receiver", itemBean.getReceiver());
            jsonObject.put("expressName", itemBean.getExpressName());
            jsonObject.put("expressGetAddress", itemBean.getExpressGetAddress());
            jsonObject.put("collegeID", itemBean.getCollegeID());
            jsonObject.put("takeTime", itemBean.getTakeTime());
            jsonObject.put("getTime",itemBean.getGetTime());
            Jsonreault = jsonObject.toString();
            Log.d("gxy",Jsonreault);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Jsonreault;
    }


    /*解析TakeJson*/
    public static CourierListItemBean parseTakeSearchJson(String jsonData) {
        CourierListItemBean item = new CourierListItemBean();
        try {

            JSONObject jsonObject = new JSONObject(jsonData);
            JSONObject data = new JSONObject(jsonObject.getString("data"));
            item.setTel(data.getString("tel"));
            item.setExpressCompany(data.getString("expressCompany"));
            item.setUserID(data.getString("userID"));
            item.setReceiverCity(data.getString("receiverCity"));
            item.setPublishedTime(data.getString("publishedTime"));
            item.setAddressInfo(data.getString("addressInfo"));
            item.setLogisticsID(data.getString("logisticsID"));
            item.setExpressNum(data.getString("expressNum"));
            item.setIsOverWeight(data.getInt("isOverWeight"));
            item.setUserName(data.getString("userName"));
            item.setExpressNum(data.getString("expressNum"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return item;
    }




    /*解析BringJson*/

    public static CourierListItemBean parseBringSearchJson(String jsonData) {
        CourierListItemBean item = new CourierListItemBean();
        try {

            JSONObject jsonObject = new JSONObject(jsonData);
            JSONObject data = new JSONObject(jsonObject.getString("data"));
            item.setTel(data.getString("tel"));
            item.setExpressNum(data.getString("expressNum"));
            item.setExpressCompany(data.getString("companyName"));
            item.setUserID(data.getString("userID"));
            item.setExpressAddress(data.getString("expressAddress"));
            item.setReceiver(data.getString("receiver"));
            item.setExpressName(data.getString("expressName"));
            item.setExpressGetAddress(data.getString("expressGetAddress"));
            item.setPublishedTime(data.getString("publishedTime"));
            item.setLogisticsID(data.getString("logisticsID"));
            item.setCompanyName(data.getString("companyName"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }





    /**/

    /*解析总Json*/
    public static ArrayList<CourierListItemBean> parseCourierSearchJson(String jsonData) {
        ArrayList<CourierListItemBean> items = new ArrayList<CourierListItemBean>();
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray data = new JSONArray(jsonObject.getString("data"));
            for (int i = 0; i < data.length(); i++) {
                CourierListItemBean item = new CourierListItemBean();
                JSONObject json = data.getJSONObject(i);
                item.setUserName(json.getString("userName"));
                item.setTel(json.getString("tel"));
                item.setExpressNum(json.getString("expressNum"));
                item.setExpressName(json.getString("expressName"));
                item.setExpressID(json.getString("expressID"));
                item.setTag(json.getString("tag"));
                item.setLogisticsID(json.getString("logisticsID"));
                item.setPublishedTime(json.getString("publishedTime"));
                item.setExpressStatusID(json.getString("expressStatusID"));
                items.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    String Jsonreault = " ";   //定义返回的Json字符串

    /*创建删除的json*/
    public void courierJson(String expressID, String tag, HttpSuccess httpSuccess) {

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tokenID", MyApplication.mUser.getTokenId());
            jsonObject.put("expressID", expressID);
            jsonObject.put("tag", tag);
            Jsonreault = jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestParams params = new RequestParams();
        params.addBodyParameter("data", Jsonreault);
        upMethod(params, Path.deleteexpress, httpSuccess);
    }


    /*数据上传方法*/
    public void upMethod(RequestParams params, final String uploadHost, final HttpSuccess httpSuccess) {

        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST, uploadHost, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                httpSuccess.onsuccess(new MerchandiseInfo());
            }

            @Override
            public void onFailure(HttpException e, String s) {
                e.printStackTrace();
                httpSuccess.onfail();
            }
        });

    }

    /*设置快递状态*/
    public void setExpressStatus(String expressID, String expressStatusID, HttpSuccess httpSuccess) {

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tokenID", MyApplication.mUser.getTokenId());
            jsonObject.put("expressID", expressID);
            jsonObject.put("expressStatusID", expressStatusID);
            Jsonreault = jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestParams params = new RequestParams();
        params.addBodyParameter("status", Jsonreault);
        upMethod(params, Path.setexpressstatus, httpSuccess);
    }

}
