package com.deeal.exchange.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ab.activity.AbActivity;
import com.ab.util.AbDialogUtil;
import com.deeal.exchange.Bean.CollegeBean;
import com.deeal.exchange.Bean.CourierListItemBean;
import com.deeal.exchange.Bean.companyNameBean;
import com.deeal.exchange.R;
import com.deeal.exchange.adapter.CollegeAdapter;
import com.deeal.exchange.adapter.companyNameAdapter;
import com.deeal.exchange.application.Path;
import com.deeal.exchange.model.City;
import com.deeal.exchange.tools.CityListJson;
import com.deeal.exchange.tools.CollegeJson;
import com.deeal.exchange.tools.CompanyNameJson;
import com.deeal.exchange.tools.CourierJson;
import com.deeal.exchange.view.Titlebar;
import com.deeal.exchange.view.wheel.OnWheelChangedListener;
import com.deeal.exchange.view.wheel.WheelView;
import com.deeal.exchange.view.wheel.adapters.ArrayWheelAdapter;
import com.deeal.exchange.view.wheel.adapters.WheelViewAdapter;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;

public class CourierBringActivity extends AbActivity {
    CourierListItemBean mitem = new CourierListItemBean();
    String BringJson;       //创建得到的json字符串
    companyNameAdapter nameAdapter;
    private ArrayList<companyNameBean> companyNames = new ArrayList<companyNameBean>();
    private ArrayList<CollegeBean> collegeNames = new ArrayList<CollegeBean>();
    private int year, monthOfYear, dayOfMonth, hourOfDay, minute;
    private RelativeLayout rladdress;
    private TextView tvHome;
    private LinearLayout dialogChooseHome;
    private String[] items = new String[]{"没有城市"};
    private TextView tvExpressAddress;
    /*
    * 定义电话号码
    * */
    private EditText etTel;
    /*
    * 定义提交按钮
    * */
    private Button btSubmit;
    /*
    定义收货人
    */
    private EditText etReceiver;
    /*
    * 定义快递公司
    * */
    private Spinner snCourierCompany;
    private String CourierCompanyID;
    /*
    * 定义送货地址
    * */
    private EditText etExpressAddress;
    private Spinner snCollege;

    /*
    * 定义拿货地址
    * */
    private EditText etExpressGetAddress;
    CollegeAdapter collegeAdapter;
    private String collegeID;
    private EditText etTime;
    private EditText etDay;
    private String date;
    private String time;
    private EditText etGetTime;
    private LayoutInflater inflater;

    /*
      定义详细地址
  */
    private WheelView wlExpressCity;
    private WheelView WlExpressCollege;

    private ArrayList<City> city = new ArrayList<>();
    private ArrayList<CollegeBean> collegeBeans = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier_bring);
        inflater = LayoutInflater.from(this);
        tvExpressAddress = (TextView) findViewById(R.id.tv_express_Address);
        setTitleBar();
        getID();
        chooserhome();

        sethome();
        RequestParams params = new RequestParams();
        uploadNameMethod(params, Path.getlogisticscompany);
        sendBringJson();
        setRlTime();
    }


    /*设置titleBar*/
    private void setTitleBar() {
        Titlebar titlebar = new Titlebar(this);
        titlebar.setTitle(getString(R.string.courier_bring));
        titlebar.getbtback();
    }


    private void getID() {
        etReceiver = (EditText) findViewById(R.id.etReceiver);
        etTel = (EditText) findViewById(R.id.etTel);
        etExpressAddress = (EditText) findViewById(R.id.etExpressAddress);
        etExpressGetAddress = (EditText) findViewById(R.id.etExpressGetAddress);
        btSubmit = (Button) findViewById(R.id.btCourierBring);
        snCourierCompany = (Spinner) findViewById(R.id.snCourierCompany);
        snCollege = (Spinner) findViewById(R.id.snCollege);
        etTime = (EditText) findViewById(R.id.etTime);
        etDay = (EditText) findViewById(R.id.etDay);
        etGetTime = (EditText) findViewById(R.id.etGetTime);

    }


    /*获取控件里的信息*/
    private void getInformation() {
        mitem.setReceiver(etReceiver.getText().toString());
        mitem.setTel(etTel.getText().toString());
        mitem.setExpressAddress(etExpressAddress.getText().toString());
        mitem.setExpressGetAddress(etExpressGetAddress.getText().toString());
        mitem.setExpressCompany(CourierCompanyID);
        mitem.setCollegeID(collegeID);
        mitem.setTakeTime(date + " " + time);
        mitem.setGetTime(etGetTime.getText().toString());
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(CourierBringActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                TimePickerDialog timePickerDialog = new TimePickerDialog(CourierBringActivity.this, new TimePickerDialog.OnTimeSetListener() {
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

    /*按钮点击方法创建json并上传*/
    private void sendBringJson() {
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etReceiver.getText().toString().length() != 0 && etTel.getText().toString().length() != 0 &&
                        etExpressAddress.getText().toString().length() != 0 && etExpressGetAddress.getText().toString().length() != 0
                        && date != null && time != null) {
                    getInformation();
                    BringJson = CourierJson.getCreateExpressJson(mitem);
                    RequestParams params = new RequestParams();
                    params.addBodyParameter("data", BringJson);
                    upMethod(params, Path.addexpress);
                    CourierBringActivity.this.finish();
                } else {
                    if (date == null && time == null) {
                        Toast.makeText(CourierBringActivity.this, "请填写上门日期和时间", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(CourierBringActivity.this, "填写不能为空", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });

    }


    /*数据上传方法*/
    private void upMethod(RequestParams params, final String uploadHost) {

        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST, uploadHost, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Toast.makeText(CourierBringActivity.this, "发布成功,请等工作人员完成任务后支付", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(CourierBringActivity.this, "发布失败", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });

    }

    /*快递公司列表的获取*/
    public void uploadNameMethod(RequestParams params, final String uploadHost) {
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST, uploadHost, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                companyNames = CompanyNameJson.parseJson(responseInfo.result);
                companyNames.remove(0);
                nameAdapter = new companyNameAdapter(CourierBringActivity.this, companyNames);  //得到一个mAdapter对象
                snCourierCompany.setAdapter(nameAdapter);
                CourierCompanyID = companyNames.get(0).getCompanyName();
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(CourierBringActivity.this, "无法从服务器获取数据", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        });
    }

    /**
     * 设置详细地址
     */
    private void sethome() {
        rladdress = (RelativeLayout) this.findViewById(R.id.rlCollege);
        tvHome = (TextView) findViewById(R.id.tvUserHome);
        //tvHome.setText(mUser.getUserHome().toString().trim());
        rladdress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AbDialogUtil.removeDialog(dialogChooseHome);
                AbDialogUtil.showAlertDialog(dialogChooseHome);
            }
        });
    }

    /**
     * 选择常住地址
     */
    private void chooserhome() {
        dialogChooseHome = (LinearLayout) inflater.inflate(R.layout.dialog_choose_express, null);
        wlExpressCity = (WheelView) dialogChooseHome.findViewById(R.id.wl_express_city);
        WlExpressCollege = (WheelView) dialogChooseHome.findViewById(R.id.wl_express_college);
        getCityHttp();
        setUpData();
        wlExpressCity.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                //通过城市获取城市列表,该接口返回该城市所有的大学
                try {
                    HttpUtils httpUtils = new HttpUtils();
                    String data = CityListJson.createJson(city.get(wlExpressCity.getCurrentItem()).getCityID());
                    RequestParams requestParams = new RequestParams();
                    requestParams.addBodyParameter("params", data);
                    httpUtils.send(HttpRequest.HttpMethod.POST, Path.get_colleges_by_city, requestParams, new RequestCallBack<String>() {
                        @Override
                        public void onSuccess(ResponseInfo<String> responseInfo) {
                            collegeBeans = CollegeJson.parseJson(responseInfo.result);
                            ArrayList<String> types = CollegeJson.parseList(collegeBeans);
                            if (types.size() == 0) {
                                WlExpressCollege.setViewAdapter(new ArrayWheelAdapter<String>(CourierBringActivity.this, items));
                            } else {
                                String[] college = new String[types.size()];
                                for (int i = 0; i < types.size(); i++) {
                                    college[i] = types.get(i);
                                }
                                WlExpressCollege.setViewAdapter(new ArrayWheelAdapter<String>(CourierBringActivity.this, college));
                            }
                        }

                        @Override
                        public void onFailure(HttpException e, String s) {

                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
        WlExpressCollege.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {

            }
        });

        Button btEnsure = (Button) dialogChooseHome.findViewById(R.id.btEnsure);
        btEnsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvExpressAddress.setText(city.get(wlExpressCity.getCurrentItem()).getCityName()+" "+collegeBeans.get(WlExpressCollege.getCurrentItem()).getCollegeName());
                collegeID = city.get(wlExpressCity.getCurrentItem()).getCityName()+" "+collegeBeans.get(WlExpressCollege.getCurrentItem()).getCollegeName();
                AbDialogUtil.removeDialog(dialogChooseHome);
            }
        });
    }

    private void setUpData() {

        // wlExpressCity.setViewAdapter(new ArrayWheelAdapter<String>(this,items));
        // 设置可见条目数量
        wlExpressCity.setVisibleItems(7);
        wlExpressCity.setCurrentItem(0);
        WlExpressCollege.setViewAdapter(new ArrayWheelAdapter<String>(CourierBringActivity.this, items));
        WlExpressCollege.setVisibleItems(7);
        WlExpressCollege.setCurrentItem(0);

    }

    private void getCityHttp() {
        //城市的获取；
        final HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.POST, Path.getCity, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                city = City.parseJson(responseInfo.result);
                ArrayList<String> types = City.parseList(city);
                String[] it = new String[types.size()];
                for (int i = 0; i < types.size(); i++) {
                    it[i] = types.get(i);
                }
                wlExpressCity.setViewAdapter(new ArrayWheelAdapter<String>(CourierBringActivity.this, it));
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

}
