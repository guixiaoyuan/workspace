package com.deeal.exchange.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.deeal.exchange.Bean.companyNameBean;
import com.deeal.exchange.R;
import com.deeal.exchange.adapter.companyNameAdapter;
import com.deeal.exchange.application.MyApplication;
import com.deeal.exchange.application.Path;
import com.deeal.exchange.model.MerchandiseInfo;
import com.deeal.exchange.tools.CompanyNameJson;
import com.deeal.exchange.tools.HttpSuccess;
import com.deeal.exchange.tools.MySoldJson;
import com.deeal.exchange.tools.onWriteSuccess;
import com.deeal.exchange.view.Titlebar;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by weixianbin on 2015/7/7
 * 填写单号.
 */
public class WriteNumberActivity extends Activity {
    onWriteSuccess onWriteSuccess;
    private ArrayList<companyNameBean> companyNames = new ArrayList<companyNameBean>();
    companyNameAdapter nameAdapter;
    /*
    * 定义物流公司
    */
    private Spinner snLogisticsCompany;
    private String mLogisticsCompany;
    /*
    * 定义物流单号
    */
    private EditText etLogisticsNumber;
    private String mLogisticsNumber;
    /*
    * 定义提交按钮
    */
    private Button btLogistics;
    private String expressPostID;
    private String orderID;
    private String CourierCompanyID;
    private String expressID;

    private String Jsonreault;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_number);
        setTitleBar();
        getID();



        RequestParams params = new RequestParams();
        uploadNameMethod(params, Path.getlogisticscompany);
        btLogistics();
        setSnCourierCompany();
    }


    /*设置titleBar*/
    private void setTitleBar() {
        Titlebar titlebar = new Titlebar(this);
        titlebar.setTitle(getString(R.string.write_number));
        titlebar.getbtback();
        titlebar.getScan();
    }

    /*绑定控件ID*/
    private void getID() {
        snLogisticsCompany = (Spinner) findViewById(R.id.snLogisticsCompany);
        etLogisticsNumber = (EditText) findViewById(R.id.etLogisticsNumber);
        btLogistics = (Button) findViewById(R.id.btLogistics);
    }




    /*
     *为提交按钮绑定监听
     */

    public void btLogistics() {

        btLogistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etLogisticsNumber.getText().toString().length()!= 0){
                    RequestParams params = new RequestParams();
                    params.addBodyParameter("tokenID", MyApplication.mUser.getTokenId());
                    params.addBodyParameter("expressCompany", CourierCompanyID);
                    params.addBodyParameter("expressnum", etLogisticsNumber.getText().toString());
                    if (isFrom()) {
                        params.addBodyParameter("expressPostID", expressPostID);
                        upWriteNum(params, Path.createnumberfrompost, new onWriteSuccess() {
                            @Override
                            public void onSuccess() {
                                setResult(RESULT_OK);
                                finish();
                            }
                        });
                    }else {
                        params.addBodyParameter("orderID",orderID);
                        upWriteNum(params, Path.createnumberfromorder, new onWriteSuccess() {
                            @Override
                            public void onSuccess() {
                                MySoldJson updateOrderStatus = new MySoldJson();
                                updateOrderStatus.updateOrderStatus("3",orderID, new HttpSuccess() {
                                    @Override
                                    public void onfail() {

                                    }
                                    @Override
                                    public void onsuccess(MerchandiseInfo info) {
                                        Log.e("填写成功了吗？","填写成功了啊 啊啊");
                                        setResult(RESULT_OK);
                                        finish();
                                    }
                                });

                            }
                        });
                    }
                }else  {
                    Toast.makeText(WriteNumberActivity.this, "填写不能为空", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    /*上传方法*/
    public void upWriteNum(RequestParams params, final String uploadHost,final onWriteSuccess onWriteSuccess) {

        HttpUtils http = new HttpUtils();

        http.send(HttpRequest.HttpMethod.POST, uploadHost, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Toast.makeText(WriteNumberActivity.this, "填写成功", Toast.LENGTH_LONG).show();
                if (isFrom()){
                    setExpressStatus(expressID, "2", new HttpSuccess() {
                        @Override
                        public void onfail() {

                        }

                        @Override
                        public void onsuccess(MerchandiseInfo info) {
                            onWriteSuccess.onSuccess();

                        }
                    });
                }else{
                    setResult(RESULT_OK);
                    finish();
                }

        }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(WriteNumberActivity.this, "无网络连接", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        });

    }



    /*设置快递状态*/
    public void setExpressStatus(String expressID, String expressStatusID,HttpSuccess httpSuccess) {

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tokenID", MyApplication.mUser.getTokenId());
            jsonObject.put("expressID", expressID);
            jsonObject.put("expressStatusID", expressStatusID);
            //jsonObject.put("",);
            Jsonreault = jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestParams params = new RequestParams();
        params.addBodyParameter("status", Jsonreault);
        upMethod(params, Path.setexpressstatus, httpSuccess);
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

    //判断从哪个acitivity跳转过来
    private boolean isFrom() {
        boolean isfrom ;
        Intent values = getIntent();
        if (values.getStringExtra("from").length()!= 0) {
            expressID = values.getStringExtra("expressID");
            expressPostID = values.getStringExtra("expressID");
            isfrom = true;
        } else  {
            orderID = values.getStringExtra("orderID");
            isfrom = false;
        }
        return isfrom;
    }

    /*快递公司列表的获取*/
    public void uploadNameMethod(RequestParams params, final String uploadHost) {
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST, uploadHost, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                companyNames = CompanyNameJson.parseJson(responseInfo.result);
                companyNames.remove(0);
                nameAdapter= new companyNameAdapter(WriteNumberActivity.this, companyNames);  //得到一个mAdapter对象
                snLogisticsCompany.setAdapter(nameAdapter);
            }
            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(WriteNumberActivity.this, "无法从服务器获取数据", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        });
    }

    /*快递公司下拉列表监听事件*/
    private void setSnCourierCompany() {
        snLogisticsCompany.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CourierCompanyID = companyNames.get(position).getCompanyID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            String logisticsNumber = data.getStringExtra("result");
            etLogisticsNumber.setText(logisticsNumber);
        }
    }
}
