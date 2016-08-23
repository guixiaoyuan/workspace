package com.deeal.exchange.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.deeal.exchange.Bean.CourierListItemBean;
import com.deeal.exchange.R;
import com.deeal.exchange.application.MyApplication;
import com.deeal.exchange.application.Path;
import com.deeal.exchange.tools.CourierJson;
import com.deeal.exchange.view.Titlebar;
import com.deeal.exchange.view.loadingview.LoadingView;
import com.deeal.exchange.view.slidr.Slidr;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * Created by weixianbin on 2015/7/8.
 */
public class ServiceInformationActivity extends Activity {
    private CourierListItemBean item = new CourierListItemBean();
    /*
    * 定义服务编号
    */
    private TextView tvServiceNumber;
    /*
    * 定义快递公司
    */
    private TextView tvExpressCompany;
    /*
    *定义快递编号
    */
    private TextView tvExpressNumber;
    /*
    * 定义寄件人
    */
    private TextView tvSender;
    /*
    * 定义手机号号码
    */
    private TextView tvPhoneNum;
    /*
    * 定义快递公司   下面部分显示
    */
    private TextView tvCompany;
    /*
    * 定义收件城市
    */
    private TextView tvRecipientCity;
    /*
    * 定义是否超重
    */
    private TextView tvOverWeight;
    /*
    * 定义取件地址
    */
    private TextView tvPickAddress;
    /*
    * 定义收货地址
    */
    private TextView tvDeliveryAddress;
    /*
    * 定义收件人
    */
    private TextView tvConsignee;
    private TextView tvTakeAddress;
    private TextView tvShippingAddress;
    private String Tag;
    private String expressPostID;   //代发
    private String expressGetID;    //代拿
    private LoadingView loadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isBringorTake()) {
            setContentView(R.layout.activity_service_information_bring);
        } else {
            setContentView(R.layout.activity_service_information_take);
        }
        loadingView = (LoadingView) findViewById(R.id.loadView);
        setTitleBar();
        getID();
        setView();
        //Slidr.attach(this);
    }

    /*绑定控件ID*/
    private void getID() {
        tvServiceNumber = (TextView) findViewById(R.id.tvServiceNumber);
        tvExpressCompany = (TextView) findViewById(R.id.tvExpressCompany);

        tvPhoneNum = (TextView) findViewById(R.id.tvPhoneNum);
        tvCompany = (TextView) findViewById(R.id.tvCompany);
        if (isBringorTake()) {
            tvConsignee = (TextView) findViewById(R.id.tvConsignee);    //收货人
            tvTakeAddress = (TextView) findViewById(R.id.tvTakeAddress);
            tvShippingAddress = (TextView) findViewById(R.id.tvShippingAddress);

        } else {
            tvExpressNumber = (TextView) findViewById(R.id.tvExpressNumber);
            tvSender = (TextView) findViewById(R.id.tvSender);
            tvRecipientCity = (TextView) findViewById(R.id.tvRecipientCity);
            tvOverWeight = (TextView) findViewById(R.id.tvOverWeight);
            tvPickAddress = (TextView) findViewById(R.id.tvPickAddress);

        }
    }


    /*设置titleBar*/
    private void setTitleBar() {
        Titlebar titlebar = new Titlebar(this);
        titlebar.setTitle(getString(R.string.ServiceInformation));
        titlebar.getbtback();
    }

    private void setView() {
        RequestParams params = new RequestParams();
        if (isBringorTake()) {
            params.addBodyParameter("tokenID", MyApplication.mUser.getTokenId());
            params.addBodyParameter("expressGetID", expressGetID);
            uploadMethod(params, Path.getexpressgetdetail, ServiceInformationActivity.this);
        } else {
            params.addBodyParameter("tokenID", MyApplication.mUser.getTokenId());
            params.addBodyParameter("expressPostID", expressPostID);
            uploadMethod(params, Path.getexpresspostdetail, ServiceInformationActivity.this);
        }

    }

/*用xutils从服务器获得信息*/

    public void uploadMethod(RequestParams params, final String uploadHost, final Context context) {

        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST, uploadHost, params, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                loadingView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                loadingView.setVisibility(View.GONE);
                if (isBringorTake()) {
                    item = CourierJson.parseBringSearchJson(responseInfo.result);

                    tvConsignee.setText(item.getReceiver());
                    tvTakeAddress.setText(item.getExpressGetAddress());
                    tvShippingAddress.setText(item.getExpressAddress());
                    tvServiceNumber.setText(item.getLogisticsID());
                    tvExpressCompany.setText(item.getCompanyName());
                    tvPhoneNum.setText(item.getTel());
                    tvCompany.setText(item.getCompanyName());

                } else {

                    item = CourierJson.parseTakeSearchJson(responseInfo.result);
                    Log.e("0=-9-076=0", responseInfo.result);
                    tvSender.setText(item.getUserName());
                    tvRecipientCity.setText(item.getReceiverCity());
                    tvOverWeight.setText(setIsoverWeight());
                    tvPickAddress.setText(item.getAddressInfo());
                    tvServiceNumber.setText(item.getLogisticsID());
                    if (item.getExpressNum().equals("0")) {
                        tvExpressNumber.setText("尚未填写");
                    } else {
                        tvExpressNumber.setText(item.getExpressNum());
                    }
                    tvPhoneNum.setText(item.getTel());

                    if (item.getExpressCompany().equals("0")) {
                        tvCompany.setText("尚未填写");
                        tvExpressCompany.setText("尚未填写");
                    } else {
                        tvCompany.setText(item.getExpressCompany());
                        tvExpressCompany.setText(item.getExpressCompany());
                    }
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                loadingView.setVisibility(View.GONE);
                ((Activity) context).findViewById(R.id.nothing).setVisibility(View.VISIBLE);
                Toast.makeText(ServiceInformationActivity.this, "无网络连接", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        });

    }


    /*判断是代拿还是代发并得到信息*/
    /*0代表代拿，1代表代发*/
    private boolean isBringorTake() {
        boolean is = false;
        Intent intent = getIntent();
        Tag = intent.getStringExtra("tag");
        if (Tag.equals("0")) {
            is = true;
            expressGetID = intent.getStringExtra("expressID");
        } else {
            expressPostID = intent.getStringExtra("expressID");
        }
        return is;
    }

    private String isoverWeight;

    private String setIsoverWeight() {
        if (item.getIsOverWeight() == 0) {
            isoverWeight = "否";
        } else {
            isoverWeight = "是";
        }
        return isoverWeight;
    }

}
