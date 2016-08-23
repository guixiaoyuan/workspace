package com.deeal.exchange.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.deeal.exchange.Bean.AddressListItemBean;
import com.deeal.exchange.R;
import com.deeal.exchange.application.Path;
import com.deeal.exchange.tools.AddressJson;
import com.deeal.exchange.view.Titlebar;
import com.deeal.exchange.view.slidr.Slidr;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

public class EditAddressActivity extends BaseActivity {
    private AddressListItemBean aloneItem = new AddressListItemBean();

    /**
     * 定义收货人编辑框
     */
    private EditText etConsignee;
    /**
     * 定义手机号码编辑框
     */
    private EditText etPhoneNumber;

    /**
     * 定义详细地址编辑框
     */
    private EditText etAddress;

    /**
     * 定义邮政编码编辑框
     */
    private EditText etPostalcode;

    /**
     * 定义省份下拉列表框
     */
    private Spinner snProvince;

    /**
     * 定义城市下拉列表框
     */
    private Spinner snCity;

    /**
     * 定义地区下拉列表框
     */
    private Spinner snArea;
    /**
     * 定义复选框
     */
    private CheckBox cbSetDefault;
    /*
    *
    * 定义警告框
    * */
    private boolean isSelect = false;
    private AlertDialog.Builder warning;
    AddressListItemBean addressitem = new AddressListItemBean();

    /*定义host*/
    private Titlebar titlebar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isAdd()) {
            setContentView(R.layout.activity_add_address);
            setId();
            titlebar = new Titlebar(EditAddressActivity.this);
            titlebar.setTitle(getString(R.string.add_address));
        } else {
            setContentView(R.layout.activity_edit_address);
            setId();
            titlebar = new Titlebar(EditAddressActivity.this);
            titlebar.setTitle(getString(R.string.edit_address));
            setshow();
        }
        setBtSave();
        setBtBack();
        chooseAddress();
        //Slidr.attach(this);
    }

    /*设置控件显示内容*/
    protected void setshow() {
        aloneItem = (AddressListItemBean) getIntent().getSerializableExtra("addressList");
        etConsignee.setText(aloneItem.getmConsignee());
        etAddress.setText(aloneItem.getmAddress());
        etPostalcode.setText(aloneItem.getmPostalcode());
        etPhoneNumber.setText(aloneItem.getmPhoneNumber());
        addressitem.setAddressID(aloneItem.getAddressID());
        if (aloneItem.getIsSetDefault() == 1) {
            isSelect = true;
        }
        cbSetDefault.setChecked(isSelect);
        snProvince.setPrompt(aloneItem.getmProvince());
        snArea.setPrompt(aloneItem.getmArea());
        snCity.setPrompt(aloneItem.getmCity());
    }


    /*得到地址信息item*/
    private void getAddressItem() {
        addressitem.setmConsignee(etConsignee.getText().toString());
        addressitem.setmAddress(etAddress.getText().toString());
        addressitem.setmPostalcode(etPostalcode.getText().toString());
        addressitem.setmPhoneNumber(etPhoneNumber.getText().toString());
        addressitem.setSetDefault(getCbSetDefault());
        addressitem.setmProvince(snProvince.getSelectedItem().toString());
        addressitem.setmCity(snCity.getSelectedItem().toString());
        addressitem.setmArea(snArea.getSelectedItem().toString());
    }


    /*绑定控件ID*/
    private void setId() {
        etConsignee = (EditText) findViewById(R.id.etConsignee);
        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        etAddress = (EditText) findViewById(R.id.etAddress);
        etPostalcode = (EditText) findViewById(R.id.etPostalcode);
        snProvince = (Spinner) findViewById(R.id.snProvince);
        snCity = (Spinner) findViewById(R.id.snCity);
        snArea = (Spinner) findViewById(R.id.snArea);
        cbSetDefault = (CheckBox) findViewById(R.id.cbSetDefault);
    }


    /*设置右上保存按钮*/
    String jsonStr;  //json数组

    protected void setBtSave() {
        titlebar.getbtRight().setText(R.string.save_address);
        titlebar.getbtRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etConsignee.getText().toString().length() == 0 || etAddress.getText().toString().length() == 0
                        || etPostalcode.getText().toString().length() == 0 || etPhoneNumber.getText().toString().length() == 0) {
                    Toast.makeText(EditAddressActivity.this, "填写不能为空！", Toast.LENGTH_LONG).show();
                } else {
                    getAddressItem();
                    RequestParams params = new RequestParams();
                    if (isAdd()) {
                        jsonStr = AddressJson.getCreateJson(addressitem);
                        params.addBodyParameter("data", jsonStr);
                        upMethod(params, Path.addaddress);
                    } else {
                        jsonStr = AddressJson.getEditJson(addressitem);
                        params.addBodyParameter("data", jsonStr);
                        upMethod(params, Path.editadresshost);
                    }
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    EditAddressActivity.this.finish();
                }
            }


        });
    }

    /* 设置左上返回按钮*/
    protected void setBtBack() {

        titlebar.setbtback().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                warning = new AlertDialog.Builder(EditAddressActivity.this);
                warning.setCancelable(false);
                warning.setMessage("是否放弃本次编辑？");
                warning.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditAddressActivity.this.finish();
                    }
                });
                warning.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                warning.show();
            }
        });
    }

    /*复选框处理逻辑,得到复选框时候被选中*/
    protected int getCbSetDefault() {
        cbSetDefault = (CheckBox) findViewById(R.id.cbSetDefault);
        int isChecked = 0;
        if (cbSetDefault.isChecked()) {
            isChecked = 1;
        }
        return isChecked;
    }

    /*判断是从Address中哪个控件点击跳过来的*/
    public boolean isAdd() {
        boolean add = false;
        //取得从上一个activity传来的数据
        Intent intent = getIntent();
        //从intent中取得传来的数据
        String value = intent.getStringExtra("add");
        if (value != null) {
            add = true;
        }
        return add;
    }


    /*数据上传方法*/
    public void upMethod(RequestParams params, final String uploadHost) {

        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST, uploadHost, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(EditAddressActivity.this, "无网络连接", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        });

    }


    /*三级联动的实现*/

    private void chooseAddress() {
        snProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateCities(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        snCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateAreas(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        snArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[position];
                mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        setUpData();
    }


    private void setUpData() {
        initProvinceDatas();
        ArrayAdapter<String> provinceAdapter = new ArrayAdapter<String>(this, R.layout.spiner_item, R.id.tv, mProvinceDatas);
        snProvince.setAdapter(provinceAdapter);
        updateCities(0);
        updateAreas(0);
    }

    /**
     * 根据当前的市，更新区WheelView的信息
     */
    private void updateAreas(int position) {
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[position];
        String[] areas = mDistrictDatasMap.get(mCurrentCityName);
        if (areas == null) {
            areas = new String[]{""};
        }
        snArea.setAdapter(new ArrayAdapter<String>(EditAddressActivity.this, R.layout.spiner_item, R.id.tv, areas));
    }

    /**
     * 根据当前的省，更新市WheelView的信息
     */
    private void updateCities(int position) {

        mCurrentProviceName = mProvinceDatas[position];
        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
        if (cities == null) {
            cities = new String[]{""};
        }
        snCity.setAdapter(new ArrayAdapter<String>(this, R.layout.spiner_item, R.id.tv, cities));
        updateAreas(0);
    }

}
