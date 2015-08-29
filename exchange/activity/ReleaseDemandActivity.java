package com.deeal.exchange.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.deeal.exchange.Bean.CollegeBean;
import com.deeal.exchange.Bean.MerchandiseTypeBean;
import com.deeal.exchange.R;
import com.deeal.exchange.adapter.CityListAdapter;
import com.deeal.exchange.adapter.CollegeAdapter;
import com.deeal.exchange.adapter.MerchandiseTypeAdapter;
import com.deeal.exchange.application.MyApplication;
import com.deeal.exchange.application.Path;
import com.deeal.exchange.tools.CityListJson;
import com.deeal.exchange.tools.CollegeJson;
import com.deeal.exchange.tools.MerchandiseTypeJson;
import com.deeal.exchange.view.Titlebar;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by weixianbin on 2015/7/7.
 * 发布需求
 */
public class ReleaseDemandActivity extends Activity {
    String requirementID = " ";
    String JsonStr;
    Titlebar titlebar;
    HashMap<String, String> aloneItem;
    private String cityID;
    private String collegeID;
    private ArrayList<MerchandiseTypeBean> Names = new ArrayList<MerchandiseTypeBean>();
    MerchandiseTypeAdapter mAdaper;
    private ArrayList<HashMap<String, String>> cities = new ArrayList<HashMap<String, String>>();
    CityListAdapter mAdapter;
    CollegeAdapter nameAdapter;
    private Spinner snCity;
    private Spinner snSchool;
    private ArrayList<CollegeBean> collegeNames = new ArrayList<CollegeBean>();
    /*
    * 定义需求编辑框
    */
    private EditText etDemand;

    /*
    * 定义提交按钮
    */
    private Button btSubmit;

    /*
    * 定义发布类型
    * */
    private Spinner SnMerchandiseType;
    private String merchandiseTypeID ;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_demand);
        getID();
        RequestParams params = new RequestParams();
        uploadTypeMethod(params, Path.merchandiseHost);

        uploadMethod(params, Path.getcity);
        setTitleBar();
        setBtSubmit();
        if (!isRelease()) {
            setEtShow();
        }
        //Slidr.attach(this);
        setMerchandiseTypeID();
    }

    /*设置titleBar*/
    private void setTitleBar() {
        titlebar = new Titlebar(ReleaseDemandActivity.this);
        titlebar.setTitle(getString(R.string.release_demand));
        titlebar.getbtback();
    }

    /*绑定控件ID*/
    private void getID() {
        btSubmit = (Button) findViewById(R.id.btSubmit);
        etDemand = (EditText) findViewById(R.id.etDemand);
        SnMerchandiseType= (Spinner) findViewById(R.id.snMerchandiseType);
        snCity  = (Spinner) findViewById(R.id.snCity);
        snSchool = (Spinner) findViewById(R.id.snSchool);
    }


    /*设置提交按钮方法*/
    protected void setBtSubmit() {

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RequestParams params = new RequestParams();
                if (isRelease()) {
                    if (etDemand.getText().toString().length() != 0) {
                        params.addBodyParameter("params",getEditNeedJson());

                       /* // "tokenID" : "xxxx",
                        "info" : "需求相详细信息",
                                "merchandiseTypeID" : "商品类型ID",
                                "collegeID" : "1", 学校ID
                        "cityID" : "1" 城市ID*/
                        upNeed(params, Path.addRequirement);
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        ReleaseDemandActivity.this.finish();
                    }else {
                        Toast.makeText(ReleaseDemandActivity.this, "填写不能为空", Toast.LENGTH_LONG).show();
                    }
                } else {
                    if (etDemand.getText().toString().length() != 0) {
                        JsonStr = getEditNeedJson();
                        params.addBodyParameter("data", JsonStr);
                        upNeed(params, Path.editRequirement);
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        ReleaseDemandActivity.this.finish();
                    }else {
                        Toast.makeText(ReleaseDemandActivity.this, "填写不能为空", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
    }


    /*编辑后创建的json*/
    private String getEditNeedJson() {
        String JsonReault = " ";
        try {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("tokenID", MyApplication.mUser.getTokenId());
                jsonObject.put("info", etDemand.getText().toString());
                jsonObject.put("merchandiseTypeID",merchandiseTypeID);
                jsonObject.put("cityID",cityID);
                jsonObject.put("collegeID",collegeID);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonReault = jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JsonReault;
    }


    /*上传数据方法*/
    public void upNeed(RequestParams params, final String uploadHost) {

        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST, uploadHost, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(ReleaseDemandActivity.this, "无网络连接", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        });

    }


    /*判断是从哪个点击事件传进来的*/
    private boolean isRelease() {
        boolean release;
        if (getIntent().getExtras() == null){
            release = true;
        }else {
            release = false;
        }
        return release;
    }


    /*显示从item传来的数据*/
    private void setEtShow() {
        aloneItem = (HashMap<String, String>) getIntent().getExtras().getSerializable("NeedItem");
        etDemand.setText(aloneItem.get("info"));
        requirementID = aloneItem.get("requirementID");
        merchandiseTypeID = aloneItem.get("merchandiseType");
    }

    /*商品类型的获取*/
    public void uploadTypeMethod(RequestParams params, final String uploadHost) {
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST, uploadHost, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Names = MerchandiseTypeJson.parseJson(responseInfo.result);
                Names.remove(0);
                mAdaper= new MerchandiseTypeAdapter(ReleaseDemandActivity.this,Names);  //得到一个mAdapter对象
                SnMerchandiseType.setAdapter(mAdaper);
            }
            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(ReleaseDemandActivity.this, "无法从服务器获取数据", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        });

    }

    /*下拉列表方法*/
    private void setMerchandiseTypeID(){
        SnMerchandiseType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                merchandiseTypeID = Names.get(position).getMerchandiseTypeID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        snCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cityID = cities.get(position).get("cityID");
                RequestParams params = new RequestParams();
                JSONObject json = new JSONObject();
                try {
                    json.put("tokenID",MyApplication.mUser.getTokenId());
                    json.put("cityID", cityID);
                    json.put("tag","0");
                    params.addBodyParameter("params",json.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                uploadNameMethod(params, Path.get_colleges_by_city);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        snSchool.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                collegeID = collegeNames.get(position).getCollegeID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    /*城市列表的获取*/
    public void uploadMethod(RequestParams params, final String uploadHost) {
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST, uploadHost, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                cities = CityListJson.parseJson(responseInfo.result);
                cities.remove(0);
                mAdapter = new CityListAdapter(ReleaseDemandActivity.this, cities, "1");  //得到一个mAdapter对象
                snCity.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(ReleaseDemandActivity.this, "无法从服务器获取数据", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        });

    }
    /*学校列表的获取*/
    public void uploadNameMethod(RequestParams params, final String uploadHost) {
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST, uploadHost, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                collegeNames = CollegeJson.parseJson(responseInfo.result);
                if (uploadHost.equals(Path.getcolleges)) {
                    collegeNames.remove(0);
                }
                nameAdapter = new CollegeAdapter(ReleaseDemandActivity.this, collegeNames);  //得到一个mAdapter对象
                snSchool.setAdapter(nameAdapter);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(ReleaseDemandActivity.this, "无法从服务器获取数据", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        });

    }
}



