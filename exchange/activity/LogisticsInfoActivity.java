package com.deeal.exchange.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.deeal.exchange.R;
import com.deeal.exchange.adapter.LogisticsInfoListAdapter;
import com.deeal.exchange.application.MyApplication;
import com.deeal.exchange.application.Path;
import com.deeal.exchange.tools.BitmapHelper;
import com.deeal.exchange.view.loadingview.LoadingView;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.show.api.ShowApiRequest;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class LogisticsInfoActivity extends Activity {

    /**
     * 标题栏的两个控件
     */
    private TextView tv_title;
    private Button bt_title;

    private ImageView imv_logistics;
    private TextView tv_logisticsname;
    private TextView tv_logistics_num;
    private TextView tvLogisticsNum;
    private TextView tv_logistics_status;
    private ImageView imv_goods;
    private TextView tv_goods_name;
    private TextView tv_goods_price;

    private BitmapUtils utils1,utils2;

    private String logisticsName;
    private String logisticsNum;

    private ListView listView;
    private LogisticsInfoListAdapter logisticsInfoListAdapter;
    private LayoutInflater inflater;
    private View view;
    private String orderID;
    private LoadingView loadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistics_info);
        loadingView = (LoadingView) findViewById(R.id.loadView);
        /**
         * 初始化标题栏
         */
        inittitle();
        /**
         *初始化lisiview的头
         */
        setListViewHead();
        /**
         *初始化控件
         */
        initWidgetFormID();


        parseLogisticsHeadInfo();

        /**
         *把从第三方api获取的物流详情放到listview中
         */





    }

    private void parseLogisticsHeadInfo() {
        Intent intent = getIntent();
        orderID = intent.getStringExtra("orderID");
        RequestParams params = new RequestParams();
        params.addBodyParameter("tokenID",MyApplication.mUser.getTokenId());
        params.addBodyParameter("orderID",orderID);
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.POST, Path.searchLogisticsPath, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                try {
                    JSONObject jsonObject = new JSONObject(responseInfo.result);
                    if (jsonObject.getString("status").equals("SUCCESS")) {
                        JSONObject data = new JSONObject(jsonObject.getString("data"));

                        String ss1 = Path.IMGPATH + data.getString("logisticsCompanyImgPath");
                        utils1.display(imv_logistics, ss1);

                        JSONArray array = data.getJSONArray("merchandiseImgPath");
                        String ss2 = Path.IMGPATH + array.get(0);
                        utils2.display(imv_goods, ss2);

                        tv_logisticsname.setText(data.getString("companyName"));
                        tv_goods_name.setText(data.getString("merchandiseName"));
                        String s = "￥:" + data.getString("currentPrice");
                        tv_goods_price.setText(s);

                        logisticsName = data.getString("abbr");
                        logisticsNum = data.getString("logisticsNum");
                        if (logisticsName.length()>0){
                            showapi();

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(LogisticsInfoActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initWidgetFormID() {
        imv_logistics = (ImageView) view.findViewById(R.id.iv_logistics_info_head_item);
        tv_logisticsname = (TextView) view.findViewById(R.id.tv_logistics_name_logistics_info_head_item);
        tvLogisticsNum = (TextView) view.findViewById(R.id.tv_logistics_number_logistics_info_head_item);
        tv_logistics_status = (TextView) view.findViewById(R.id.tv_logistics_condition_logistics_info_head_item);
        imv_goods = (ImageView) view.findViewById(R.id.im_commodity_logistics_info_head_item);
        tv_goods_name = (TextView) view.findViewById(R.id.tv_commodity_name_logistics_info_head_item);
        tv_goods_price = (TextView) view.findViewById(R.id.tv_goods_price_logistics_info_head_item);

        utils1 = BitmapHelper.getBitmapUtils(LogisticsInfoActivity.this.getApplicationContext());
        utils2 = BitmapHelper.getBitmapUtils(LogisticsInfoActivity.this.getApplicationContext());
    }

    private void inittitle() {
        tv_title = (TextView) this.findViewById(R.id.tvTitle);
        tv_title.setText("物流详情");
        bt_title = (Button) this.findViewById(R.id.btBack);
        bt_title.setVisibility(View.VISIBLE);
        bt_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setListViewHead() {
        inflater = LayoutInflater.from(this);
        view = inflater.inflate(R.layout.logistics_info_head_item, null);
        listView = (ListView) this.findViewById(R.id.lv_logistics_info);
        listView.addHeaderView(view);
        listView.setDividerHeight(0);
    }

    private void parseLogisticsInfo(String jsondata) {
        try {
            JSONObject jsonObject = new JSONObject(jsondata);
            if(jsonObject.getInt("showapi_res_code") == 0){
                JSONObject jsonObject1 = new JSONObject(jsonObject.getString("showapi_res_body"));
                ArrayList<Map> items = new ArrayList<Map>();
                String s1 = "运单编号："+jsonObject1.getString("mailNo");
                String s2 = "物流状态："+getResources().getStringArray(R.array.logistics_status)[jsonObject1.getInt("status")+1];
                tvLogisticsNum.setText(s1);
                tv_logistics_status.setText(s2);
                JSONArray array = new JSONArray(jsonObject1.getString("data"));
                for(int i=0;i<array.length();i++){
                    Map map = new Hashtable();
                    map.put("adress",array.getJSONObject(i).getString("context"));
                    map.put("time", array.getJSONObject(i).getString("time"));
                    items.add(map);
                }
                logisticsInfoListAdapter = new LogisticsInfoListAdapter(LogisticsInfoActivity.this, items);
                listView.setAdapter(logisticsInfoListAdapter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showapi() {
        final AsyncHttpResponseHandler reshandler = new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                loadingView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                loadingView.setVisibility(View.INVISIBLE);
                String ss = new String(bytes);
                parseLogisticsInfo(ss);

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(LogisticsInfoActivity.this, "网络连接失败！", Toast.LENGTH_LONG).show();

            }
        };

        ShowApiRequest showapi = new ShowApiRequest("http://route.showapi.com/64-19", "5762", "8dbf59e384dd4dd4ad2527766ac38dbc");
        showapi.setResponseHandler(reshandler);
        showapi.addTextPara("com", logisticsName);
        showapi.addTextPara("nu", logisticsNum);


        showapi.post();

    }
}



