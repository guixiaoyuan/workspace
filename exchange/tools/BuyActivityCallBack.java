package com.deeal.exchange.tools;

import android.app.Activity;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.deeal.exchange.Bean.AddressListItemBean;
import com.deeal.exchange.Bean.CommodityListItemBean;
import com.deeal.exchange.R;
import com.deeal.exchange.activity.BuyActivity;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Sunqiyong on 2015/7/17.
 */
public class BuyActivityCallBack extends RequestCallBack<String> {


    private Activity activity;
    private BuyActivity.ViewHolder viewHolder;
    private AddressListItemBean itemBean;

    public BuyActivityCallBack(Activity activity, BuyActivity.ViewHolder viewHolder,AddressListItemBean itemBean){
        this.activity = activity;
        this.viewHolder = viewHolder;
        this.itemBean = itemBean;
    }

    public void onSuccess(ResponseInfo<String> responseInfo) {
        try {
            JSONObject jsonObject = new JSONObject(responseInfo.result);
            if(jsonObject.getString("status").equals("SUCCESS")){
                JSONObject jsonData = new JSONObject(jsonObject.getString("data"));
                String addressId = jsonData.getString("addressID");
                itemBean.setAddressID(addressId);
                String namephone = jsonData.getString("userName") + jsonData.getString("tel");
                String detailadress = jsonData.getString("province") + jsonData.getString("city") + jsonData.getString("area") + jsonData.getString("description");
                viewHolder.tv_name_phone.setText(namephone);
                viewHolder.tv_receive_adress.setText(detailadress);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(activity,"网络连接失败！",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(HttpException e, String s) {
        Toast.makeText(activity,"网络连接失败！",Toast.LENGTH_SHORT).show();
    }

}
