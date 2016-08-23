package com.deeal.exchange.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.activity.AbActivity;
import com.ab.util.AbDialogUtil;
import com.deeal.exchange.Bean.AddressListItemBean;
import com.deeal.exchange.Bean.CommodityListItemBean;
import com.deeal.exchange.Bean.OrderDetailBean;
import com.deeal.exchange.R;
import com.deeal.exchange.alipay.Pay;
import com.deeal.exchange.application.Constant;
import com.deeal.exchange.application.MyApplication;
import com.deeal.exchange.application.Path;
import com.deeal.exchange.tools.BitmapHelper;
import com.deeal.exchange.tools.BuyActivityCallBack;
import com.deeal.exchange.tools.OrderDetailJson;
import com.deeal.exchange.tools.PathUtils;
import com.deeal.exchange.tools.UpdateOrderStatus;
import com.deeal.exchange.tools.onPayResult;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.sea_monster.dao.internal.LongHashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;


public class BuyActivity extends AbActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    /**
     * 标题栏的两个控件
     */
    private Boolean isFreeze = false;
    private TextView tv_title;
    private Button bt_title;

    private ImageView imv_goods;
    private TextView tv_goods_name;
    private TextView tv_goods_price;
    private TextView tv_logistics_price;
    private TextView tv_goods_adress;
    private ImageView imv_choose_adress;
    private CheckBox chb_help;
    private TextView tv_price_help;
    private TextView reall_pay_price;
    private Button bt_sure_pay;
    private RelativeLayout rl_activity_buy2;
    private String payID;
    private String orderId = "";

    private CommodityListItemBean comm = new CommodityListItemBean();
    private BitmapUtils bitmapUtils;
    AddressListItemBean item = new AddressListItemBean();

    String json;

    private ViewHolder viewHolder = new ViewHolder();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        /**
         * 初始化上一个activity传过来的值
         */

        /**
         * 初始化标题栏
         */
        inittitle();

        /**
         * 初始化控件
         */
        initWidgetFormID();
        initWidgetViewHolder();

        /**
         * 解析上一个activity传过来的json
         */
        Intent intent = getIntent();
        json = intent.getExtras().getString("jsonData");
        comm = parseSearchJson(json);

        /**
         * 把数据适配到界面中
         */
        dataToActivity(comm);

        /**
         * 解析默认地址的json文件
         */
        parseAddressJson();

        /**
         * 设置监听
         */
        bt_sure_pay.setOnClickListener(this);
        chb_help.setOnCheckedChangeListener(this);
        rl_activity_buy2.setOnClickListener(this);

    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            tv_price_help.setText("￥20");
            Float helpFee = new Float(tv_price_help.getText().toString().substring(1));
            Float sureFee = new Float(reall_pay_price.getText().toString().substring(1));
            sureFee += helpFee;
            reall_pay_price.setText("￥" + sureFee);
        } else {
            Float helpFee = new Float(tv_price_help.getText().toString().substring(1));
            Float sureFee = new Float(reall_pay_price.getText().toString().substring(1));
            sureFee -= helpFee;
            reall_pay_price.setText("￥" + sureFee);
            tv_price_help.setText("￥0");
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_final_ensure_activity_buy:
                //生成订单
//                Log.e("addressid","---"+(comm.getDefaultAddress().trim() =="null")+"---");
//                Log.e("---","--------------------------------");
//                Log.e("defaultAddress","---"+((item.getAddressID().trim() == null || item.getAddressID().trim().isEmpty()))+"----");

                if (comm.getDefaultAddress().trim() == "null"
                        && (item.getAddressID().trim() == null
                        || item.getAddressID().trim().isEmpty())) {
                    Log.e("addressid:", item.getAddressID());
                    Toast.makeText(BuyActivity.this, "收货地址为空", Toast.LENGTH_SHORT).show();
                } else if (isFreeze) {
                    Toast.makeText(BuyActivity.this, "该商品已经被订购", Toast.LENGTH_SHORT).show();
                } else {
                    getOrderHttp();
                    // pay();
                }

                break;
            case R.id.rl_activity_buy2:
                Intent intent = new Intent();
                intent.putExtra("Buyactivity", true);
                intent.setClass(BuyActivity.this, AddressActivity.class);
                startActivityForResult(intent, Constant.SELETEADDRESS);
                break;
        }
    }

    //生产订单的http请求
    private void getOrderHttp() {
        final RequestParams params = new RequestParams();
        OrderDetailJson orderDetailJson = new OrderDetailJson();
        try {
            String data = orderDetailJson.createOrderDetailJson(comm, item);
            params.addBodyParameter("data", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.POST, Path.generate_order, params, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                AbDialogUtil.showLoadDialog(BuyActivity.this, R.drawable.rc_ic_notice_loading, getString(R.string.loading));
            }

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                AbDialogUtil.removeDialog(BuyActivity.this);
                final String data = responseInfo.result;
             /*   Pay myPay = new Pay(BuyActivity.this, new onPayResult() {
                    @Override
                    public void onSuccess() {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            orderId = jsonObject.getString("data");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(BuyActivity.this, "支付成功",
                                Toast.LENGTH_SHORT).show();
                        //更新支付宝交易号。
                        RequestParams updateParams = new RequestParams();
                        updateParams.addBodyParameter("tokenID", MyApplication.mUser.getTokenId());
                        updateParams.addBodyParameter("orderID", orderId);
                        updateParams.addBodyParameter("alipayID", payID);
                        final HttpUtils updateOrderDetailHttpUtils = new HttpUtils();
                        updateOrderDetailHttpUtils.send(HttpRequest.HttpMethod.POST, Path.update_aplipay_id, updateParams, new RequestCallBack<String>() {
                            @Override
                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                Log.e("55555onSuccess", orderId);
                                UpdateOrderStatus updateOrderStatus = new UpdateOrderStatus("2", orderId,BuyActivity.this);
                                updateOrderStatus.updataOrderStatus();
                                Intent intent = new Intent();
                                intent.setClass(BuyActivity.this, OrderDetailActivity.class);
                                intent.putExtra("jsonData", json);
                                intent.putExtra("merchandiseID", comm.getMerchandiseID());
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onFailure(HttpException e, String s) {
                                Log.e("55555onFailure", orderId);
                                UpdateOrderStatus updateOrderStatus = new UpdateOrderStatus("1", orderId,BuyActivity.this);
                                updateOrderStatus.updataOrderStatus();
                                Intent intent = new Intent();
                                intent.setClass(BuyActivity.this, OrderDetailActivity.class);
                                intent.putExtra("jsonData", json);
                                intent.putExtra("merchandiseID", comm.getMerchandiseID());
                                startActivity(intent);
                                finish();
                            }
                        });
                    }

                    @Override
                    public void onFailure() {
                        Toast.makeText(BuyActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.setClass(BuyActivity.this, OrderDetailActivity.class);
                        intent.putExtra("jsonData", json);
                        intent.putExtra("merchandiseID", comm.getMerchandiseID());
                        startActivity(intent);
                        finish();
                    }
                });
                payID = myPay.getOutTradeNo();
                myPay.pay();*/

                try {
                    JSONObject jsonObject = new JSONObject(data);
                    orderId = jsonObject.getString("data");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(BuyActivity.this, "支付成功",
                        Toast.LENGTH_SHORT).show();
                //更新支付宝交易号。
                RequestParams updateParams = new RequestParams();
                updateParams.addBodyParameter("tokenID", MyApplication.mUser.getTokenId());
                updateParams.addBodyParameter("orderID", orderId);
                updateParams.addBodyParameter("alipayID", payID);
                final HttpUtils updateOrderDetailHttpUtils = new HttpUtils();
                updateOrderDetailHttpUtils.send(HttpRequest.HttpMethod.POST, Path.update_aplipay_id, updateParams, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.e("55555onSuccess", orderId);
                        UpdateOrderStatus updateOrderStatus = new UpdateOrderStatus("2", orderId, BuyActivity.this);
                        updateOrderStatus.updataOrderStatus();
                        Intent intent = new Intent();
                        intent.setClass(BuyActivity.this, OrderDetailActivity.class);
                        intent.putExtra("jsonData", json);
                        intent.putExtra("merchandiseID", comm.getMerchandiseID());
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        Log.e("55555onFailure", orderId);
                        UpdateOrderStatus updateOrderStatus = new UpdateOrderStatus("1", orderId, BuyActivity.this);
                        updateOrderStatus.updataOrderStatus();
                        Intent intent = new Intent();
                        intent.setClass(BuyActivity.this, OrderDetailActivity.class);
                        intent.putExtra("jsonData", json);
                        intent.putExtra("merchandiseID", comm.getMerchandiseID());
                        startActivity(intent);
                        finish();
                    }
                });
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.i("onFailure", e.toString());
                Toast.makeText(BuyActivity.this, "服务器开小差了", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * buyactivity
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {

            item = (AddressListItemBean) data.getSerializableExtra("addressList");
            String namephone = item.getmConsignee() + item.getmPhoneNumber();
            viewHolder.tv_name_phone.setText(namephone);
            viewHolder.tv_receive_adress.setText(item.getAddressbuychoose());

        }
    }

    private void inittitle() {
        tv_title = (TextView) this.findViewById(R.id.tvTitle);
        tv_title.setText("购买宝贝");
        bt_title = (Button) this.findViewById(R.id.btBack);
        bt_title.setVisibility(View.VISIBLE);
        bt_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public CommodityListItemBean parseSearchJson(String JsonData) {
        try {
            JSONObject jsonObject = new JSONObject(JsonData);
            if (jsonObject.getString("status").equals("SUCCESS")) {
                JSONObject jsonData = jsonObject.getJSONObject("data");
                if (jsonData.getString("defaultAddress").equals("null")) {
                    comm.setDefaultAddress(jsonData.getString("defaultAddress"));
                    if (Integer.parseInt(jsonData.getString("isFreeze")) == 0) {
                        isFreeze = false;
                    } else {
                        isFreeze = true;
                    }
                    Log.e("5555buyActivity", item.getAddressbuychoose());
                    comm.setMerchandiseName(jsonData.getString("merchandiseName"));
                    comm.setMerchandiseID(jsonData.getString("merchandiseID"));
                    comm.setUsername(jsonData.getString("userName"));
                    comm.setUserid(jsonData.getString("userID"));
                    comm.setInfo(jsonData.getString("info"));
                    comm.setPublishtime(jsonData.getString("publishedTime"));
                    comm.setOriginalCost(jsonData.getString("oldPrice"));
                    comm.setCost(jsonData.getString("currentPrice"));
                    comm.setSchool(jsonData.getString("college"));
                    comm.setVisitedCount(jsonData.getString("visitedCount"));
                    comm.setMerchandiseTypeID(jsonData.getString("merchandiseTypeID"));
                    JSONArray array = new JSONArray(jsonData.getString("imgList"));
                    ArrayList<String> items = new ArrayList<String>();
                    for (int i = 0; i < array.length(); i++) {
                        items.add(array.getString(i));
                    }
                    comm.setPicurls(items);
                } else {
                    if (Integer.parseInt(jsonData.getString("isFreeze")) == 0) {
                        isFreeze = false;
                    } else {
                        isFreeze = true;
                    }
                    JSONObject data = jsonData.getJSONObject("defaultAddress");
                    comm.setDefaultAddress(jsonData.getString("defaultAddress"));
                    comm.setBuyerID(data.getString("userID"));
                    comm.setMerchandiseName(jsonData.getString("merchandiseName"));
                    comm.setMerchandiseID(jsonData.getString("merchandiseID"));
                    comm.setUsername(jsonData.getString("userName"));
                    comm.setUserid(jsonData.getString("userID"));
                    comm.setInfo(jsonData.getString("info"));
                    comm.setPublishtime(jsonData.getString("publishedTime"));
                    comm.setOriginalCost(jsonData.getString("oldPrice"));
                    comm.setCost(jsonData.getString("currentPrice"));
                    comm.setSchool(jsonData.getString("college"));
                    comm.setVisitedCount(jsonData.getString("visitedCount"));
                    comm.setMerchandiseTypeID(jsonData.getString("merchandiseTypeID"));
                    JSONArray array = new JSONArray(jsonData.getString("imgList"));
                    ArrayList<String> items = new ArrayList<String>();
                    for (int i = 0; i < array.length(); i++) {
                        items.add(array.getString(i));
                    }
                    comm.setPicurls(items);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return comm;
    }

    private void parseAddressJson() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("tokenID", MyApplication.mUser.getTokenId());
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST, Path.getSearchAddressDefaultDetailPath, params, new BuyActivityCallBack(BuyActivity.this, viewHolder, item));
    }


    private void dataToActivity(CommodityListItemBean comm) {

        bitmapUtils = BitmapHelper.getBitmapUtils(BuyActivity.this.getApplicationContext());
        Log.e("xianbing11211", imv_goods.toString());
        Log.e("xianbing11111", comm.getPicurls().get(0));
        bitmapUtils.display(imv_goods, PathUtils.createMerchandiseImageUrl(comm.getMerchandiseID(),comm.getPicurls().get(0)));
        tv_goods_name.setText(comm.getMerchandiseName());

        /**activity上显示的价格是商品价加上运费
         * n1:是商品价
         * n2:是运费
         */
        Float fee1 = new Float(comm.getCost());
        Float fee2 = new Float("10");
        fee1 += fee2;//此时n1:商品价加运费
        tv_goods_price.setText("￥" + fee1);
        tv_logistics_price.setText("(含运费：￥" + fee2.toString() + ")");
        tv_goods_adress.setText(comm.getSchool());
        //帮助验货并发货费用
        Float fee3 = new Float(0.0);
        tv_price_help.setText("￥" + fee3);
        //实付金额
        fee1 += fee3;
        reall_pay_price.setText("￥" + fee1);

    }

    private void initWidgetFormID() {
        imv_goods = (ImageView) this.findViewById(R.id.iv_activity_buy);
        tv_goods_name = (TextView) this.findViewById(R.id.tv_trade_name_activity_buy);
        tv_goods_price = (TextView) this.findViewById(R.id.tv_goods_fee_activity_buy);
        tv_logistics_price = (TextView) this.findViewById(R.id.tv_transport_fee_activity_buy);
        tv_goods_adress = (TextView) this.findViewById(R.id.tv_seller_adress_activity_buy);
        imv_choose_adress = (ImageView) this.findViewById(R.id.img_choose_adress_activity_buy);
        chb_help = (CheckBox) this.findViewById(R.id.chb_choose_help_activity_buy);
        tv_price_help = (TextView) this.findViewById(R.id.tv_help_fee_activity_buy);
        reall_pay_price = (TextView) this.findViewById(R.id.tv_really_pay_activity_buy);
        bt_sure_pay = (Button) this.findViewById(R.id.bt_final_ensure_activity_buy);
        rl_activity_buy2 = (RelativeLayout) this.findViewById(R.id.rl_activity_buy2);
    }

    private void initWidgetViewHolder() {
        viewHolder.tv_name_phone = (TextView) this.findViewById(R.id.tv_name_phone_activity_buy);
        viewHolder.tv_receive_adress = (TextView) this.findViewById(R.id.tv_getgoods_adress_activity_buy);
    }

    public class ViewHolder {
        public TextView tv_name_phone;
        public TextView tv_receive_adress;
    }

}
