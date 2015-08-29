package com.deeal.exchange.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.deeal.exchange.Bean.CollegeBean;
import com.deeal.exchange.Bean.CourierListItemBean;
import com.deeal.exchange.Bean.companyNameBean;
import com.deeal.exchange.R;
import com.deeal.exchange.adapter.CityListAdapter;
import com.deeal.exchange.adapter.CollegeAdapter;
import com.deeal.exchange.adapter.companyNameAdapter;
import com.deeal.exchange.application.Path;
import com.deeal.exchange.tools.CityListJson;
import com.deeal.exchange.tools.CollegeJson;
import com.deeal.exchange.tools.CompanyNameJson;
import com.deeal.exchange.tools.CourierJson;
import com.deeal.exchange.view.Titlebar;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class CourierTakeActivity extends Activity {
    CourierListItemBean mitem = new CourierListItemBean();
    CityListAdapter mAdapter;
    CollegeAdapter nameAdapter;
    String takeJson;
    /*
    * 定义寄件人
    * */
    private EditText etuserName;
    /*
    * 定义手机号码
    * */
    private EditText etTel;
    /*
    * 定义取件地址
    * */
    private EditText etAddressInfo;

    /*
    * 定义快递公司
    * */
    private String collegeID;
    private Spinner snCollege;
    /*
    * 定义收件城市
    * */
    private String receiverCityID;
    private Spinner snRecipientCity;
    /*
    * 定义是否超重
    * */
    private Spinner overWeight;
    /*
    *
    * 定义提交按钮
    * */
    private Button btSend;
    private int year, monthOfYear, dayOfMonth, hourOfDay, minute;

    private ArrayList<HashMap<String, String>> cities = new ArrayList<HashMap<String, String>>();


    private ArrayList<CollegeBean> collegeNames = new ArrayList<CollegeBean>();
    private EditText etTime;
    private EditText etDay;
    private String date;
    private String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier_take);
        getID();
        setTitleBar();
        RequestParams params = new RequestParams();
        uploadMethod(params, Path.getcity);
        uploadNameMethod(params, Path.getcolleges);
        getOverweight();
        sendTakeJson();
        setSnMethod();
        setRlTime();
    }


    /*设置titleBar*/
    private void setTitleBar() {
        Titlebar titlebar = new Titlebar(this);
        titlebar.setTitle(getString(R.string.courier_take));
        titlebar.getbtback();
    }

    /*绑定控件ID*/
    private void getID() {
        etuserName = (EditText) findViewById(R.id.etSender);
        etTel = (EditText) findViewById(R.id.etPhoneNumber);
        etAddressInfo = (EditText) findViewById(R.id.etpickAddress);
        btSend = (Button) findViewById(R.id.btCourierTake);
        overWeight = (Spinner) findViewById(R.id.Overweight);
        snCollege = (Spinner) findViewById(R.id.snCollege);
        snRecipientCity = (Spinner) findViewById(R.id.snRecipientCity);
        etTime = (EditText) findViewById(R.id.etTime);
        etDay = (EditText) findViewById(R.id.etDay);

    }

    /*获得控件里编辑的信息*/
    private void getTakeInformation() {
        mitem.setUserName(etuserName.getText().toString());
        mitem.setTel(etTel.getText().toString());
        mitem.setAddressInfo(etAddressInfo.getText().toString());
        mitem.setIsOverWeight(snOverweight());
        mitem.setCollegeID(collegeID);
        mitem.setReceiverCity(receiverCityID);
        mitem.setTakeTime(date + " " + time);
    }

    /*设置时间选择*/
    private void setRlTime() {

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        monthOfYear = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        etDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 实例化一个DatePickerDialog的对象
                 * 第二个参数是一个DatePickerDialog.OnDateSetListener匿名内部类，当用户选择好日期点击done会调用里面的onDateSet方法
                 */
                DatePickerDialog datePickerDialog = new DatePickerDialog(CourierTakeActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        etDay.setText("上门日期：" + year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                    }
                }, year, monthOfYear, dayOfMonth);

                datePickerDialog.show();
            }
        });
        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 实例化一个TimePickerDialog的对象
                 * 第二个参数是一个TimePickerDialog.OnTimeSetListener匿名内部类，当用户选择好时间后点击done会调用里面的onTimeset方法
                 */
                TimePickerDialog timePickerDialog = new TimePickerDialog(CourierTakeActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        etTime.setText("上门时间: " + hourOfDay + ":" + minute);
                        time = hourOfDay + ":" + minute;
                    }
                }, hourOfDay, minute, true);

                timePickerDialog.show();
            }
        });

    }

    /*创建json并上传*/
    private void sendTakeJson() {
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etuserName.getText().toString().length() != 0 && etTel.getText().toString().length() != 0 && etAddressInfo.getText().toString().length() != 0
                        && date.length() != 0 && time.length() != 0 && date != null && time!= null) {
                    getTakeInformation();
                    takeJson = CourierJson.getCreateTakeExpressJson(mitem);
                    Log.e("+++============",takeJson); 
                    RequestParams params = new RequestParams();
                    params.addBodyParameter("data", takeJson);
                    upMethod(params, Path.createExpressPost);
                    CourierTakeActivity.this.finish();
                } else {
                    if (date==null && time==null){
                        Toast.makeText(CourierTakeActivity.this, "请填写上门日期和时间", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(CourierTakeActivity.this, "填写不能为空", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    /*上传方法*/

    private void upMethod(RequestParams params, final String uploadHost) {

        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST, uploadHost, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Toast.makeText(CourierTakeActivity.this, "发布成功,请等工作人员完成任务后支付", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(CourierTakeActivity.this, "无网络连接", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        });

    }

    /*是否超重下拉列表*/
    private void getOverweight() {
        String[] mitems = getResources().getStringArray(R.array.isoverweight);
        ArrayAdapter<String> _Adapter = new ArrayAdapter<String>(CourierTakeActivity.this, R.layout.companyname_item, R.id.tvCompanyName, mitems);
        overWeight.setAdapter(_Adapter);
    }

    /*将是否超重用0和1表示*/
    private int snOverweight() {
        int weight = 0;
        if (overWeight.getSelectedItem().toString().equals("是")) {
            weight = 1;
        }
        return weight;
    }

    /*城市列表的获取*/
    public void uploadMethod(RequestParams params, final String uploadHost) {
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST, uploadHost, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                cities = CityListJson.parseJson(responseInfo.result);
                cities.remove(0);
                mAdapter = new CityListAdapter(CourierTakeActivity.this, cities, "1");  //得到一个mAdapter对象
                snRecipientCity.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(CourierTakeActivity.this, "无法从服务器获取数据", Toast.LENGTH_LONG).show();
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
                collegeNames.remove(0);
                nameAdapter = new CollegeAdapter(CourierTakeActivity.this, collegeNames);  //得到一个mAdapter对象
                snCollege.setAdapter(nameAdapter);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(CourierTakeActivity.this, "无法从服务器获取数据", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        });

    }


    /*下拉列表监听方法*/
    private void setSnMethod() {
        snCollege.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                collegeID = collegeNames.get(position).getCollegeID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        snRecipientCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                receiverCityID = cities.get(position).get("cityID");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
