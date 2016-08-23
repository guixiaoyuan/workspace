package com.deeal.exchange.activity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import com.ab.activity.AbActivity;
import com.ab.util.AbDialogUtil;
import com.ab.util.AbFileUtil;
import com.ab.util.AbLogUtil;
import com.ab.util.AbStrUtil;
import com.ab.util.AbToastUtil;
import com.deeal.exchange.R;
import com.deeal.exchange.adapter.PublishPhotoGridAdapter;
import com.deeal.exchange.application.Constant;
import com.deeal.exchange.application.MyApplication;
import com.deeal.exchange.application.Path;
import com.deeal.exchange.model.City;
import com.deeal.exchange.model.Colledge;
import com.deeal.exchange.model.JuheISBNmodel;
import com.deeal.exchange.model.MerchandiseInfo;
import com.deeal.exchange.model.MerchandiseType;
import com.deeal.exchange.model.User;
import com.deeal.exchange.tools.FeeJson;
import com.deeal.exchange.tools.HttpSuccess;
import com.deeal.exchange.tools.JuHeISBNparse;
import com.deeal.exchange.tools.MerchandiseListUtils;
import com.deeal.exchange.tools.PublishMerchandiseService;
import com.deeal.exchange.view.MyGirdView;
import com.deeal.exchange.view.Titlebar;
import com.deeal.exchange.view.slidr.Slidr;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.thinkland.common.JsonCallBack;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * created by 2015-7-14
 */
public class PublishActivity extends AbActivity implements View.OnClickListener {

    /**
     * 标题
     */
    private Titlebar mTitlebar;
    /**
     * 选择照片并显示的gridview
     */
    private MyGirdView glPics;
    /**
     * 编辑商品标题
     */
    private EditText etTitle;
    /**
     * 编辑商品信息
     */
    private EditText etInfo;
    /**
     * 显示用户发布地理位置
     */
    private TextView tvCity;
    /**
     * 显示用户学校所在
     */
    private TextView tvSchool;
    /**
     * 点击编辑用户想要卖多少钱
     */
    private LinearLayout llCost;
    /**
     * 显示用户出的价格
     */
    private TextView tvCost;
    /**
     * 点击编辑所售商品的原价
     */
    private LinearLayout llOriginalCost;
    /**
     * 显示用户所售商品原价
     */
    private TextView tvOriginalCost;
    /**
     * 运费
     */
    private LinearLayout llYunFei;
    /**
     * 显示运费
     */
    private TextView tvYunFei;
    /**
     * 选择种类
     */
    private LinearLayout llType;
    /**
     * 显示商品种类
     */
    private TextView tvType;
    /**
     * 选中则支持物物交换
     */
    private CheckBox cbChangeable;
    /**
     * 编辑完成进入下一步
     */
    private Button btNext;
    /**
     * 推荐所需价格
     */
    private TextView tvTuijianCost;
    /**
     * 匹配所需价格
     */
    private TextView tvPipeiCost;
    /**
     * 验货所需价格
     */
    private TextView tvYanhuoCost;
    /**
     * 用户上传的图片资源
     */
    private ArrayList<String> imgurls = new ArrayList<String>();
    private User mUser;
    private  HashMap<String, String> fee;
    private CheckBox cbTuijian;
    private CheckBox cbPipei;
    private CheckBox cbYanhuo;

    private ArrayList<String> mPhotoList = null;
    /* 用来标识请求照相功能的activity */
   /* 用来标识请求照相功能的activity */
    private static final int CAMERA_WITH_DATA = 30231;
    /* 用来标识请求gallery的activity */
    private static final int PHOTO_PICKED_WITH_DATA = 3021;
    /* 用来标识请求裁剪图片后的activity */
    private static final int CAMERA_CROP_DATA = 3022;
    private File PHOTO_DIR = null;
    // 照相机拍照得到的图片
    private File mCurrentPhotoFile;
    private String mFileName;
    private int camIndex = 0;
    private PublishPhotoGridAdapter mAdapter;
    private View dialogTakePic;
    private View dialogChooseCost;
    private View dialogChooseOriginalCose;
    private PopupWindow ppChooseCost;
    private PopupWindow ppChooseOriginalCost;
    private View dialogChooseType;

    private Button btEnsure;
    private EditText etCost;
    private int cityID = 1;
    private Boolean paid = true;

    private City mCity = new City();
    private ArrayList<City> cities = new ArrayList<City>();
    private Colledge mCollledge = new Colledge();
    private ArrayList<Colledge> colledges = new ArrayList<Colledge>();
    private MerchandiseType merchandiseType = new MerchandiseType();
    private ArrayList<MerchandiseType> merchandiseTypes = new ArrayList<MerchandiseType>();

    private PopupWindow ppChooseColledge;
    private View ColledgeView;
    private PopupWindow ppChooseCity;
    private View CityView;
    private PopupWindow ppChooseType;
    private View TypeView;
    private MerchandiseInfo mInfo;

    private static String ERR_SEARCH_INFO = "未查询到信息，请手动收入，谢谢！";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        mUser = MyApplication.mUser;
        inittitle();
        inititems();
        getimgs();
        getDialog();
        getTypes();
        getCity();
        getColledge();
        setFee();
        if(isEdit()){
            getInfo();
        }
    }

    /**
     * 加载标题栏
     */
    private void inittitle() {
        mTitlebar = new Titlebar(this);
        mTitlebar.getbtback();
        mTitlebar.setTitle("发布");
        mTitlebar.getScan();
    }

    /**
     * 初始化控件
     */
    private void inititems() {
        ColledgeView = LayoutInflater.from(this).inflate(R.layout.dialog_choose_type, null);
        TypeView = LayoutInflater.from(this).inflate(R.layout.dialog_choose_type, null);
        CityView = LayoutInflater.from(this).inflate(R.layout.dialog_choose_type, null);
        glPics = (MyGirdView) findViewById(R.id.glPics);
        etTitle = (EditText) findViewById(R.id.etTitle);
        etInfo = (EditText) findViewById(R.id.etInfo);
        tvCity = (TextView) findViewById(R.id.tvCity);
        tvCity.setOnClickListener(this);
        tvSchool = (TextView) findViewById(R.id.tvSchool);
        tvSchool.setOnClickListener(this);
        tvCost = (TextView) findViewById(R.id.tvCost);
        tvOriginalCost = (TextView) findViewById(R.id.tvOriginCost);
        tvYanhuoCost = (TextView) findViewById(R.id.tvYunfei);
        tvType = (TextView) findViewById(R.id.tvType);
        tvPipeiCost = (TextView) findViewById(R.id.tvPipeiCost);
        tvYanhuoCost = (TextView) findViewById(R.id.tvYanhuoCost);
        tvTuijianCost = (TextView) findViewById(R.id.tvTuijianCost);
        llCost = (LinearLayout) findViewById(R.id.llCost);
        llCost.setOnClickListener(this);
        llOriginalCost = (LinearLayout) findViewById(R.id.llOriginalCost);
        llOriginalCost.setOnClickListener(this);
        llYunFei = (LinearLayout) findViewById(R.id.llYunFei);
        llYunFei.setOnClickListener(this);
        llType = (LinearLayout) findViewById(R.id.llType);
        llType.setOnClickListener(this);
        cbChangeable = (CheckBox) findViewById(R.id.cbChangeable);
        cbPipei = (CheckBox) findViewById(R.id.cbPipei);
        cbTuijian = (CheckBox) findViewById(R.id.cbTuijian);
        cbYanhuo = (CheckBox) findViewById(R.id.cbYanhuo);
        btNext = (Button) findViewById(R.id.btNext);
        btNext.setOnClickListener(this);
    }

    private void change() {

    }

    /**
     * 创建用于选择价格的dialog的内容布局
     */
    private void getDialog() {
        dialogChooseCost = View.inflate(this, R.layout.dialog_choose_cost, null);//选择价格的dialog
        btEnsure = (Button) dialogChooseCost.findViewById(R.id.btEnsure);
        etCost = (EditText) dialogChooseCost.findViewById(R.id.etCost);
    }

    /**
     * 从服务器获取商品种类
     */
    private void getTypes() {
        TypeView = View.inflate(this, R.layout.dialog_choose_type, null);
        final ListView lvType = (ListView) TypeView.findViewById(R.id.lvType);
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.POST, Path.merchandiseHost, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                ArrayList<String> typeList;
                merchandiseTypes = MerchandiseType.parseJson(responseInfo.result);
                merchandiseTypes.remove(0);
                typeList = MerchandiseType.parseList(merchandiseTypes);
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(PublishActivity.this,
                        R.layout.spiner_item, R.id.tv, typeList);
                lvType.setAdapter(adapter);
                lvType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                        tvType.setText(merchandiseTypes.get(position).getMerchandiseType());
                        merchandiseType.setMerchandiseTypeId(merchandiseTypes.get(position).getMerchandiseTypeId());
                        ppChooseType.dismiss();
                    }
                });
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llCost:
                chooseCost();
                break;
            case R.id.llOriginalCost:
                chooserOriginalCost();
                break;
            case R.id.llType:
                chooseType();
                break;
            case R.id.llYunFei:
                break;
            case R.id.btNext:
                tonext();
                break;
            case R.id.tvSchool:
                chooseColledge();
                break;
            case R.id.tvCity:
                chooseCity();
        }
    }

    /**
     * 获取学校列表
     */
    private void getColledge() {
        final ListView lvColledge = (ListView) ColledgeView.findViewById(R.id.lvType);
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.POST, Path.colleageHost, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                colledges = Colledge.parseJson(responseInfo.result);
                colledges.remove(0);
                final ArrayList<String> colledgeNames = Colledge.parseList(colledges);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(PublishActivity.this,
                        R.layout.spiner_item, R.id.tv, colledgeNames);
                lvColledge.setAdapter(adapter);
                lvColledge.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        tvSchool.setText(colledgeNames.get(position));
                        mCollledge.setColledgeID(colledges.get(position).getColledgeID());
                        ppChooseColledge.dismiss();
                    }
                });
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    /**
     * 弹窗选择学校
     */
    private void chooseColledge() {
        if (ppChooseColledge == null) {
            ppChooseColledge = new PopupWindow(this);
            ppChooseColledge.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
            ppChooseColledge.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            ppChooseColledge.setBackgroundDrawable(new BitmapDrawable());
            ppChooseColledge.setFocusable(true);
            ppChooseColledge.setOutsideTouchable(true);
            ppChooseColledge.setContentView(ColledgeView);
        }
        ppChooseColledge.showAsDropDown(tvSchool);
        ppChooseColledge.update();
    }

    /**
     * 获取城市列表
     */
    private void getCity() {
        final ListView lvCity = (ListView) CityView.findViewById(R.id.lvType);
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.POST, Path.getcity, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                cities = City.parseJson(responseInfo.result);
                cities.remove(0);
                final ArrayList<String> cityName = City.parseList(cities);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(PublishActivity.this,
                        R.layout.spiner_item, R.id.tv, cityName);
                lvCity.setAdapter(adapter);
                lvCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        tvCity.setText(cityName.get(position));
                        mCity.setCityID(cities.get(position).getCityID());
                        ppChooseCity.dismiss();
                    }
                });
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    /**
     * 弹窗选择城市
     */
    private void chooseCity() {
        if (ppChooseCity == null) {
            ppChooseCity = new PopupWindow(this);
            ppChooseCity.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
            ppChooseCity.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            ppChooseCity.setBackgroundDrawable(new BitmapDrawable());
            ppChooseCity.setFocusable(true);
            ppChooseCity.setOutsideTouchable(true);
            ppChooseCity.setContentView(CityView);

        }
        ppChooseCity.showAsDropDown(tvCity);
        ppChooseCity.update();
    }

    /**
     * 弹窗选择售出价格
     */
    private void chooseCost() {
        AbDialogUtil.removeDialog(dialogChooseCost);
        AbDialogUtil.showAlertDialog(dialogChooseCost);
        btEnsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvCost.setText(etCost.getText().toString().trim());
                AbDialogUtil.removeDialog(PublishActivity.this);
            }
        });
    }

    /**
     * 弹窗选择原价
     */
    private void chooserOriginalCost() {
        AbDialogUtil.removeDialog(dialogChooseCost);
        AbDialogUtil.showAlertDialog(dialogChooseCost);
        Button btEnsure = (Button) dialogChooseCost.findViewById(R.id.btEnsure);
        btEnsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvOriginalCost.setText(etCost.getText().toString().trim());
                AbDialogUtil.removeDialog(PublishActivity.this);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            }
        });
    }

    /**
     * 弹窗选择种类
     */
    private void chooseType() {
        if (ppChooseType == null) {
            ppChooseType = new PopupWindow(this);
            ppChooseType.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
            ppChooseType.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            ppChooseType.setBackgroundDrawable(new BitmapDrawable());
            ppChooseType.setFocusable(true);
            ppChooseType.setOutsideTouchable(true);
            ppChooseType.setContentView(TypeView);
        }
        TypeView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.abc_popup_enter));
        ppChooseType.showAsDropDown(tvType);
        ppChooseType.update();
    }


    /**
     * 从相册或者照相机选择照片进行裁剪并显示在girdView之中
     */
    private void getimgs() {
        //初始化图片保存路径
        String photo_dir = AbFileUtil.getImageDownloadDir(this);
        if (AbStrUtil.isEmpty(photo_dir)) {
            AbToastUtil.showToast(this, "存储卡不存在");
        } else {
            PHOTO_DIR = new File(photo_dir);
        }
        dialogTakePic = View.inflate(this, R.layout.dialog_choose_photo, null);
        TextView tvCam = (TextView) dialogTakePic.findViewById(R.id.tvselectphoto);
        tvCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 从相册中去获取
                try {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
                    intent.setType("image/*");
                    startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
                    AbDialogUtil.removeDialog(dialogTakePic);
                } catch (ActivityNotFoundException e) {
                    AbToastUtil.showToast(PublishActivity.this, "没有找到照片");
                }
            }
        });
        TextView tvTake = (TextView) dialogTakePic.findViewById(R.id.tvtakephoto);
        tvTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AbDialogUtil.removeDialog(dialogTakePic);
                doPickPhotoAction();
            }
        });
        mPhotoList = new ArrayList<String>();
        mPhotoList.add(String.valueOf(R.mipmap.camer_n));
        mAdapter = new PublishPhotoGridAdapter(this, mPhotoList);
        glPics.setAdapter(mAdapter);
        //选择照片为主图
        glPics.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == mAdapter.getCount() - 1) {
                    //选择照片
                    AbDialogUtil.removeDialog(dialogTakePic);
                    AbDialogUtil.showAlertDialog(dialogTakePic);
                } else {
                    for (int i = 0; i < mAdapter.getCount() - 1; i++) {
                        PublishPhotoGridAdapter.ViewHolder viewHolder = (PublishPhotoGridAdapter.ViewHolder) glPics.getChildAt(position).getTag();
                        if (viewHolder != null) {

                        }
                    }
                }
            }
        });
    }

    /**
     * 从照相机获取
     */
    private void doPickPhotoAction() {
        String status = Environment.getExternalStorageState();
        //判断是否有SD卡,如果有sd卡存入sd卡在说，没有sd卡直接转换为图片
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            doTakePhoto();
        } else {
            AbToastUtil.showToast(this, "没有可用的存储卡");
        }
    }

    /**
     * 拍照获取图片
     */
    protected void doTakePhoto() {
        try {
            mFileName = System.currentTimeMillis() + ".jpg";
            mCurrentPhotoFile = new File(PHOTO_DIR, mFileName);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCurrentPhotoFile));
            startActivityForResult(intent, CAMERA_WITH_DATA);
        } catch (Exception e) {
            AbToastUtil.showToast(this, "未找到系统相机程序");
        }
    }

    /**
     * 从相册得到的url转换为SD卡中图片路径
     */
    public String getPath(Uri uri) {
        if (AbStrUtil.isEmpty(uri.getAuthority())) {
            return null;
        }
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(column_index);
        return path;
    }

    /**
     * 描述：因为调用了Camera和Gally所以要判断他们各自的返回情况,
     * 他们启动时是这样的startActivityForResult
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent mIntent) {
        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case PHOTO_PICKED_WITH_DATA:
                Uri uri = mIntent.getData();
                String currentFilePath = getPath(uri);
                mAdapter.addItem(mAdapter.getCount() - 1, currentFilePath);
                camIndex++;
                /*if (!AbStrUtil.isEmpty(currentFilePath)) {
                    Intent intent1 = new Intent(this, CropImageActivity.class);
                    intent1.putExtra("PATH", currentFilePath);
                    startActivityForResult(intent1, CAMERA_CROP_DATA);
                } else {
                    AbToastUtil.showToast(PublishActivity.this, "未在存储卡中找到这个文件");
                }*/
                break;
            case CAMERA_WITH_DATA:
                AbLogUtil.d(PublishActivity.class, "将要进行裁剪的图片的路径是 = " + mCurrentPhotoFile.getPath());
                String currentFilePath2 = mCurrentPhotoFile.getPath();
                mAdapter.addItem(mAdapter.getCount() - 1, currentFilePath2);
                camIndex++;
                /*Intent intent2 = new Intent(this, CropImageActivity.class);
                intent2.putExtra("PATH", currentFilePath2);
                startActivityForResult(intent2, CAMERA_CROP_DATA);*/
                break;
            case CAMERA_CROP_DATA:
                String path = mIntent.getStringExtra("PATH");
                AbLogUtil.d(PublishActivity.class, "裁剪后得到的图片的路径是 = " + path);
                mAdapter.addItem(mAdapter.getCount() - 1, path);
                camIndex++;
                break;
            case Constant.SCAN:
                String result = mIntent.getExtras().get("result").toString();
                //Toast.makeText(this,result,Toast.LENGTH_LONG).show();
                getInfoByISBN(result, cityID);
                break;
        }
    }

    /**
     * 从界面信息获取商品详情并封装到商品详情类中
     *
     * @return
     */
    private MerchandiseInfo getMerchandiseInfo() {
        MerchandiseInfo info = new MerchandiseInfo();
        //用户信息
        info.setTokenID(MyApplication.mUser.getTokenId());
        //商品详情
        info.setTitle(etTitle.getText().toString().trim());
        info.setDescription(etInfo.getText().toString().trim());
        info.setPrice(Integer.parseInt(tvCost.getText().toString().trim()));
        info.setIncomePrice(Integer.parseInt(tvOriginalCost.getText().toString().trim()));
        //基本分类信息
        info.setCollege(mCollledge.getColledgeID());
        info.setMerchandiseTypeID(merchandiseType.getMerchandiseTypeId());
        info.setCity(mCity.getCityID());
        //增值服务
        Boolean isYanhuo = cbYanhuo.isChecked();
        Boolean isPipei = cbPipei.isChecked();
        Boolean isTuijian = cbTuijian.isChecked();
        Boolean ischangeable = cbChangeable.isChecked();
        info.setRecommendation(isTuijian);
        info.setInspection(isYanhuo);
        info.setMatching(isPipei);
        info.setSwp(ischangeable);
        info.setLocation(tvCity.getText().toString());
        return info;
    }

    /**
     * 下一步
     */
    private void tonext() {
        if (etTitle.getText().toString().isEmpty()) {
            AbToastUtil.showToast(this, "对不起请输入标题");
        } else if (etInfo.getText().toString().isEmpty()) {
            AbToastUtil.showToast(this, "对不起请描述该商品的相关信息");
        } else if (mAdapter.getItems().size() < 2) {
            AbToastUtil.showToast(this, "请选择一两张该商品的照片上传");
        } else if (tvCost.getText().toString().isEmpty()) {
            AbToastUtil.showToast(this, "对不起请输入你想卖的价格");
        } else if (tvOriginalCost.getText().toString().isEmpty()) {
            AbToastUtil.showToast(this, "对不起请输入该商品的原价");
        } else if (tvType.getText().toString().isEmpty()) {
            AbToastUtil.showToast(this, "对不起请选择商品分类");
        } else if (mCity.getCityID()== null) {
            AbToastUtil.showToast(this, "对不起请选择城市");
        } else if (mCollledge.getColledgeID() == null ) {
            AbToastUtil.showToast(this, "对不起请选择学校");
        } else {
            Boolean isYanhuo = cbYanhuo.isChecked();
            Boolean isPipei = cbPipei.isChecked();
            Boolean isTuijian = cbTuijian.isChecked();
            if (isYanhuo || isPipei || isTuijian) {
                //先支付再回调
                AbDialogUtil.showAlertDialog(this, "请先支付");
            } else {
                publish(getMerchandiseInfo());
            }
        }
    }

    /**
     * 发布商品
     *
     * @param info
     */
    private void publish(MerchandiseInfo info) {
        JSONObject json = PublishMerchandiseService.createjson(info);
        String mInfo = json.toString();
        List<String> imgs = mAdapter.getItems().subList(0, mAdapter.getCount() - 1);
        final RequestParams params = new RequestParams();
        JSONArray array = new JSONArray(imgs);
        params.addBodyParameter("imgList", array.toString());
        params.addBodyParameter("merchandiseInfo", mInfo);
        for (int i = 0; i < imgs.size(); i++) {
            params.addBodyParameter(imgs.get(i), new File(imgs.get(i)));
        }
        Log.d("gxy",params.toString());
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.POST, Path.postMerchandise, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.d("gxy","responseInfo = " + responseInfo.result);
                AbToastUtil.showToast(PublishActivity.this, "发布成功");
                finish();
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.d("gxy","onFailure = " + s);
                AbToastUtil.showToast(PublishActivity.this, s);
            }
        });
    }


    /**
     * 调用第三方api//聚合数据
     *
     * @param code
     */
    private void getInfoByISBN(String code, int cityID) {
        com.thinkland.barcode.BarCodeData.getBar(this, code, cityID, new ISBNcallBack());
        AbDialogUtil.showLoadPanel(this, R.mipmap.loading, "");
    }

    /**
     * 查询条形码信息的回调接口
     */
    private class ISBNcallBack implements JsonCallBack {
        @Override
        public void jsonLoaded(int i, JSONObject jsonObject) {
            AbDialogUtil.removeDialog(PublishActivity.this);
            JuheISBNmodel info = new JuheISBNmodel();
            JuHeISBNparse.jsonToInfo(jsonObject.toString(), info);
            String interval = info.getInterval();
            tvOriginalCost.setText(interval);
            String name = info.getName();
            etTitle.setText(name);
            String imgurl = info.getImgurl();
            if (null == imgurl){
                AbToastUtil.showToast(PublishActivity.this, ERR_SEARCH_INFO);
            }else if (!imgurl.isEmpty()) {
                mAdapter.addItem(mAdapter.getCount() - 1, imgurl);
            }
        }
    }


    /**
     * 根据intent传值判断是为编辑
     * @return
     */
    private Boolean isEdit(){
        Intent intent = getIntent();
        if(intent .getExtras() != null){
            return  true;
        }else{
            return false;
        }
    }

    /**
     *根据商品id获取商品详情
     */
    private void getInfo(){
        String merchandiseId= getIntent().getStringExtra("merchandiseId");
        MerchandiseListUtils.getMerchandiseDetailFromServe(merchandiseId, MyApplication.mUser.getTokenId(), new HttpSuccess() {
            @Override
            public void onfail() {
                finish();
            }

            @Override
            public void onsuccess(MerchandiseInfo info) {
                EditInfo(info);
            }
        });
    }

    private void EditInfo(MerchandiseInfo info){
        etTitle.setText(info.getTitle());
        etInfo.setText(info.getDescription());
        //mCity = cities.get(Integer.parseInt(info.getCity()));
        //tvCity.setText(mCity.getCityName());
        mCollledge  = colledges.get(Integer.parseInt(info.getCollege()));
        tvSchool.setText(mCollledge.getColledgeName());
        tvCost.setText(info.getPrice()+"");
        tvOriginalCost.setText(info.getIncomePrice()+"");
        merchandiseType = merchandiseTypes.get(Integer.parseInt(info.getMerchandiseTypeID()));
        tvType.setText(merchandiseType.getMerchandiseType());
        ArrayList<String> imgList = info.getImgList();
        Log.e("imglist",imgList.get(0));
        mAdapter.addItems(imgList);
        cbChangeable.setChecked(info.getSwp());
        cbPipei.setChecked(info.getMatching());
        cbTuijian.setChecked(info.getRecommendation());
        cbYanhuo.setChecked(info.getInspection());
    }

    /**
     * 服务费用的获取
     *
     *
     *
     */
    public void setFee() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("tokenID", MyApplication.mUser.getTokenId());
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST, Path.serviceprice, params, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                fee= FeeJson.parseJson(responseInfo.result);
                Log.e("%^&*()_+#$$%^&*(",responseInfo.result);
                tvPipeiCost.setText(fee.get("matchPrice"));
                tvYanhuoCost.setText(fee.get("inspectPrice"));
                tvTuijianCost.setText(fee.get("recommendPrice"));

            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(PublishActivity.this, "解析失败",Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });

    }
}
