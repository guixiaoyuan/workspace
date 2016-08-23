package com.deeal.exchange.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.activity.AbActivity;
import com.ab.util.AbDialogUtil;
import com.deeal.exchange.Bean.CommodityListItemBean;
import com.deeal.exchange.Bean.OrderDetailBean;
import com.deeal.exchange.R;
import com.deeal.exchange.alipay.Pay;
import com.deeal.exchange.application.MyApplication;
import com.deeal.exchange.application.Path;
import com.deeal.exchange.fragment.FirstPageFragment;
import com.deeal.exchange.tools.BitmapHelper;
import com.deeal.exchange.tools.RongIMUtils;
import com.deeal.exchange.tools.UpdateOrderStatus;
import com.deeal.exchange.tools.onPayResult;
import com.deeal.exchange.view.slidr.Slidr;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.rong.imkit.RongIM;
import io.rong.imkit.utils.BitmapUtil;

import static com.deeal.exchange.R.color.gray;
import static com.deeal.exchange.R.color.switch_thumb_disabled_material_dark;
import static com.deeal.exchange.R.layout.dialog_close;

public class OrderDetailActivity extends AbActivity {

    private TextView tvTitle;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;
    private TextView tvDetail;
    private TextView tvPrice;
    private TextView tvSellerName;

    private TextView buyerName;
    private TextView kickName;
    private TextView receiveAddress;
    private TextView orderID;

    private TextView contactBuyer;
    private TextView tvPayID;

    private Button btBack;
    private Button btStop;
    private Button btPay;
    private Button btCancle;
    private Button btEnsure;

    private View dialogClose;
    private ImageView ivDetail;
    private LayoutInflater layoutInflater;
    private ListView lvReson;
    private BitmapUtils bitmapUtil;
    private CommodityListItemBean comm;
    private OrderDetailBean orderDetailBean;
    private int flag;
    private String merId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        inittitle();
        init();
        Intent intent = getIntent();
        merId = intent.getStringExtra("merchandiseID");
        getOrderDetail(merId);
        Slidr.attach(this);
    }

    private void init() {
        layoutInflater = LayoutInflater.from(this);
        dialogClose = layoutInflater.inflate(R.layout.dialog_close, null);
        lvReson = (ListView) dialogClose.findViewById(R.id.lv_reson);
        btCancle = (Button) dialogClose.findViewById(R.id.btn_cancel);
        btEnsure = (Button) dialogClose.findViewById(R.id.btn_ensure);

        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);
        tv5 = (TextView) findViewById(R.id.tv5);
        tvDetail = (TextView) findViewById(R.id.tv_detail);
        tvSellerName = (TextView) findViewById(R.id.tv_seller_name);
        buyerName = (TextView) findViewById(R.id.buyer_name);
        kickName = (TextView) findViewById(R.id.tv_kickname);
        receiveAddress = (TextView) findViewById(R.id.receiver_address);
        orderID = (TextView) findViewById(R.id.tv_id);

        contactBuyer = (TextView) findViewById(R.id.tv_contact_buyer);
        tvPayID = (TextView) findViewById(R.id.tv_payId);

        ivDetail = (ImageView) findViewById(R.id.iv_detail);
        tvPrice = (TextView) findViewById(R.id.tv_price);

        tv1.setBackgroundResource(R.drawable.round);
        tv2.setBackgroundResource(R.drawable.round);

        btStop = (Button) findViewById(R.id.btn_stop);
        btPay = (Button) findViewById(R.id.btn_pay);


        /*ArrayAdapter adapter = new ArrayAdapter(OrderDetailActivity.this, R.layout.reson_item, R.id.tv, list);
        lvReson.setAdapter(adapter);*/


    }

    private void inittitle() {
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(getString(R.string.MycommodityDetail));
        btBack = (Button) findViewById(R.id.btBack);
        btBack.setVisibility(View.VISIBLE);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
            }
        });
    }

    private void initButton(final String merId) {
        //初始化button
        Log.e("5555initButton", flag + " ");
        switch (flag) {
            case 1:
                break;
            case 2:
                tv3.setBackgroundResource(R.drawable.round);
                btStop.setText("联系小二");
                btPay.setText("提醒发货");
                break;
            case 3:
                tv3.setBackgroundResource(R.drawable.round);
                tv4.setBackgroundResource(R.drawable.round);
                btStop.setText("查看物流");
                btPay.setText("确认收货");
                break;
            case 4:
                tv3.setBackgroundResource(R.drawable.round);
                tv4.setBackgroundResource(R.drawable.round);
                tv5.setBackgroundResource(R.drawable.round);
                btStop.setVisibility(View.GONE);
                btPay.setVisibility(View.GONE);
                break;
        }


        btStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (flag) {
                    case 1:// 代付款 关闭交易
                        AbDialogUtil.removeDialog(dialogClose);
                        AbDialogUtil.showAlertDialog(dialogClose);
                        break;
                    case 2:// 代发货 联系小二
                        Toast.makeText(OrderDetailActivity.this, "小二暂忙", Toast.LENGTH_SHORT).show();
                        break;
                    case 3://查看物流
                        Intent intent = new Intent();
                        intent.setClass(OrderDetailActivity.this, LogisticsInfoActivity.class);
                        startActivity(intent);
                }

            }
        });
        btPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (flag) {
                    case 1://代付款
                        Pay pay = new Pay(OrderDetailActivity.this, new onPayResult() {

                            @Override
                            public void onSuccess() {
                                btStop.setText("联系小二");
                                btPay.setText("提醒发货");
                                tv3.setBackgroundResource(R.drawable.round);
                                flag = 2;
                                UpdateOrderStatus updateOrderStatus = new UpdateOrderStatus(flag+"",orderDetailBean.getOrderID(),OrderDetailActivity.this);
                                updateOrderStatus.updataOrderStatus();
                            }

                            @Override
                            public void onFailure() {
                                Toast.makeText(OrderDetailActivity.this, "付款失败", Toast.LENGTH_SHORT).show();
                                flag = 1;
                            }
                        });
                        pay.pay();
                        break;
                    case 2://提醒发货
                        RequestParams requestParams = new RequestParams();
                        requestParams.addBodyParameter("tokenID", MyApplication.mUser.getTokenId());
                        requestParams.addBodyParameter("buyerID", orderDetailBean.getAddressUserName());
                        requestParams.addBodyParameter("sellerID", orderDetailBean.getUserName());
                        HttpUtils httpUtils = new HttpUtils();
                        httpUtils.send(HttpRequest.HttpMethod.POST, Path.remind_delivery, requestParams, new RequestCallBack<String>() {
                            @Override
                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                Toast.makeText(OrderDetailActivity.this, "提醒成功", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(HttpException e, String s) {
                                Toast.makeText(OrderDetailActivity.this, "提醒失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    case 3://确认发货
                        btStop.setText("查看物流");
                        btPay.setText("确认收货");
                        tv4.setBackgroundResource(R.drawable.round);
                        flag = 4;
                        UpdateOrderStatus updateOrderStatus = new UpdateOrderStatus(flag+"",orderDetailBean.getOrderID(),OrderDetailActivity.this);
                        updateOrderStatus.updataOrderStatus();
                    case 4://确认收货
                        btStop.setVisibility(View.GONE);
                        btPay.setVisibility(View.GONE);

                }

            }
        });
        //关闭交易的dialogue的两个点击事件。
        btCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AbDialogUtil.removeDialog(dialogClose);
                AbDialogUtil.showAlertDialog(dialogClose).dismiss();
                flag = 1;
            }
        });
        btEnsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 2;
                Intent mIntent = getIntent();
                RequestParams params = new RequestParams();

                params.addBodyParameter("tokenID", MyApplication.mUser.getTokenId());
                params.addBodyParameter("orderID", orderDetailBean.getOrderID());
                HttpUtils httpUtils = new HttpUtils();
                httpUtils.send(HttpRequest.HttpMethod.POST, Path.delete_order, params, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Toast.makeText(OrderDetailActivity.this, "交易已经取消", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        Toast.makeText(OrderDetailActivity.this, "网络访问失败", Toast.LENGTH_SHORT).show();
                    }
                });
                mIntent.setClass(OrderDetailActivity.this, MainActivity.class);
                startActivity(mIntent);
            }
        });
        //联系卖家
        contactBuyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RongIMUtils rongIMUtils = new RongIMUtils();
                //rongIMUtils.toChatForBuy(OrderDetailActivity.this, orderDetailBean.getSellerID(), orderDetailBean.getUserName(), merId, "");
                RongIM.getInstance().startPrivateChat(OrderDetailActivity.this, orderDetailBean.getSellerID(), orderDetailBean.getUserName());
            }
        });
    }


    //所有跳转到这个界面的Activity，通过merchandiseID获取订单详情及状态（也就是说跳转过来的Activity必须传merchandiseID）

    private void getOrderDetail(String id) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("tokenID", MyApplication.mUser.getTokenId());
        params.addBodyParameter("merchandiseID", id);

        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.POST, Path.get_merchandise_order_state, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.i("OrderDetailActivity", responseInfo.result);
                parseOrderDetailJson(responseInfo.result);
                Log.e("5555order", merId);
                initButton(merId);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.i("201508061624", e.toString());
            }
        });
    }


    //匹配接口，解析订单详情。适配到订单页面。
    private void parseOrderDetailJson(String jsonData) {
        orderDetailBean = new OrderDetailBean();
        try {
            Log.e("5555parseOrder", jsonData);
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONObject orderJsonData = new JSONObject(jsonObject.getString("data"));
            orderDetailBean.setOrderID(orderJsonData.getString("orderID"));
            orderDetailBean.setSellerID(orderJsonData.getString("sellerID"));
            orderDetailBean.setAddressUserName(orderJsonData.getString("addressUserName"));
            orderDetailBean.setAddressDescription(orderJsonData.getString("addressDescription"));
            orderDetailBean.setAplipayID(orderJsonData.getString("alipayID"));
            orderDetailBean.setInfo(orderJsonData.getString("info"));
            orderDetailBean.setCurrentPrice(orderJsonData.getString("currentPrice"));
            orderDetailBean.setUserName(orderJsonData.getString("userName"));
            orderDetailBean.setOrderState(orderJsonData.getString("orderState"));

            Log.e("jjjjjj2222", orderJsonData.getString("orderState").toString());

            JSONArray array = new JSONArray(orderJsonData.getString("imgPath"));
            ArrayList<String> items = new ArrayList<String>();
            for (int i = 0; i < array.length(); i++) {
                items.add(Path.IMGPATH + array.getString(i));
            }
            orderDetailBean.setPicurls(items);

            flag = Integer.parseInt(orderDetailBean.getOrderState());

            bitmapUtil = BitmapHelper.getBitmapUtils(OrderDetailActivity.this.getApplicationContext());
            bitmapUtil.display(ivDetail, orderDetailBean.getPicurls().get(0));
            tvSellerName.setText(orderDetailBean.getUserName());
            tvPrice.setText("$:" + orderDetailBean.getCurrentPrice());
            tvDetail.setText(orderDetailBean.getInfo());
            buyerName.setText(orderDetailBean.getAddressUserName());
            kickName.setText(orderDetailBean.getAddressUserName());
            tvPayID.setText(orderDetailBean.getAplipayID());
            receiveAddress.setText(orderDetailBean.getAddressDescription());
            orderID.setText(orderDetailBean.getOrderID());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    };

}
