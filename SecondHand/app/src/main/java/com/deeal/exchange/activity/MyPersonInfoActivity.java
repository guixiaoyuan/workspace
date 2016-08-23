package com.deeal.exchange.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ab.fragment.AbAlertDialogFragment;
import com.ab.util.AbDialogUtil;
import com.ab.util.AbFileUtil;
import com.ab.util.AbLogUtil;
import com.ab.util.AbStrUtil;
import com.ab.util.AbToastUtil;
import com.deeal.exchange.R;
import com.deeal.exchange.application.MyApplication;
import com.deeal.exchange.application.Path;
import com.deeal.exchange.model.User;
import com.deeal.exchange.tools.BitmapHelper;
import com.deeal.exchange.tools.PathUtils;
import com.deeal.exchange.view.CircleImageView;
import com.deeal.exchange.view.Titlebar;
import com.deeal.exchange.view.slidr.Slidr;
import com.deeal.exchange.view.wheel.OnWheelChangedListener;
import com.deeal.exchange.view.wheel.WheelView;
import com.deeal.exchange.view.wheel.adapters.ArrayWheelAdapter;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONObject;

import java.io.File;

public class MyPersonInfoActivity extends BaseActivity {

    /**
     * 编辑用户头像
     */
    private CircleImageView imgUserHead;
    private LinearLayout dialogChooseHeader;
    /* 用来标识请求照相功能的activity */
    private static final int CAMERA_WITH_DATA = 3023;
    /* 用来标识请求gallery的activity */
    private static final int PHOTO_PICKED_WITH_DATA = 3021;
    /* 用来标识请求裁剪图片后的activity */
    private static final int CAMERA_CROP_DATA = 3022;

    /* 拍照的照片存储位置 */
    private File PHOTO_DIR = null;
    // 照相机拍照得到的图片
    private File mCurrentPhotoFile;
    private String mFileName;

    /**
     * 选择性别
     */
    private RelativeLayout rlUserSex;
    private TextView tvSex;
    private LinearLayout dialogChooseSex;

    /**
     * 编辑昵称
     */
    private RelativeLayout rlUserNickName;
    private TextView tvNickName;
    private LinearLayout dialogEditNickName;
    /**
     * 编辑个人简介
     */
    private RelativeLayout rlUserIntro;
    private TextView tvIntro;
    private LinearLayout dialogEditIntro;
    /**
     * 编辑常住地址
     */
    private RelativeLayout rlUserHome;
    private TextView tvHome;
    private LinearLayout dialogChooseHome;
    private WheelView wlPrince;
    private WheelView wlCity;
    private WheelView wlArea;

    /**
     * 编辑收货地址
     */
    private RelativeLayout rlUserAddress;


    private Activity mActivity;
    private LayoutInflater inflater;
    private Titlebar mTitlebar;
    private User mUser;
    private BitmapUtils bitmapUtils;
    private HttpUtils httpUtils = new HttpUtils();
    private String path = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_person_info);
        mActivity = this;
        mUser = MyApplication.mUser;
        bitmapUtils = BitmapHelper.getBitmapUtils(getApplicationContext());
        inflater = LayoutInflater.from(this);
        inititems();
        updata();
        Slidr.attach(this);
    }


    private void inititems() {
        inittitle();
        setheader();
        setsex();
        setnickname();
        setintro();
        setAddress();
        sethome();
        getdialogs();
        Button btSave = (Button) findViewById(R.id.btSave);
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInfo(mUser);
            }
        });
    }

    private void inittitle() {
        mTitlebar = new Titlebar(this);
        mTitlebar.setTitle(getString(R.string.title_myinfomation));
        mTitlebar.getbtback();
    }

    /**
     * 选择头像
     */
    private void setheader() {
        imgUserHead = (CircleImageView) findViewById(R.id.imgUserHead);
        String headurl = mUser.getUserHeadUrl();
        if (headurl == null) {
            bitmapUtils.display(imgUserHead, String.valueOf(R.mipmap.cam_photo));
        } else {
            bitmapUtils.display(imgUserHead, headurl);
        }

        imgUserHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AbDialogUtil.removeDialog(dialogChooseHeader);
                AbDialogUtil.showAlertDialog(dialogChooseHeader);
            }
        });
    }

    /**
     * 设置性别
     */
    private void setsex() {
        tvSex = (TextView) findViewById(R.id.tvUserSex);
        int gender = mUser.getGender();
        if (gender == 1) {
            tvSex.setText("男");
        } else {
            tvSex.setText("女");
        }

        rlUserSex = (RelativeLayout) findViewById(R.id.rlUserSex);
        rlUserSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AbDialogUtil.removeDialog(dialogChooseSex);
                AbDialogUtil.showAlertDialog(dialogChooseSex);
            }
        });

    }

    /**
     * 设置昵称
     */
    private void setnickname() {
        tvNickName = (TextView) findViewById(R.id.tvUserNickName);
        tvNickName.setText(mUser.getUserNickname().toString().trim());
        rlUserNickName = (RelativeLayout) findViewById(R.id.rlUserNickName);
        rlUserNickName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AbDialogUtil.removeDialog(dialogEditNickName);
                AbDialogUtil.showAlertDialog(dialogEditNickName);
            }
        });

    }

    /**
     * 设置简介
     */
    private void setintro() {
        tvIntro = (TextView) findViewById(R.id.tvUserIntro);
        tvIntro.setText(mUser.getUserIntro().toString().trim());
        rlUserIntro = (RelativeLayout) findViewById(R.id.rlUserIntrp);
        rlUserIntro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AbDialogUtil.removeDialog(dialogEditIntro);
                AbDialogUtil.showAlertDialog(dialogEditIntro);
            }
        });

    }

    /**
     * 设置常住地址
     */
    private void sethome() {
        rlUserHome = (RelativeLayout) findViewById(R.id.rlUserHome);
        tvHome = (TextView) findViewById(R.id.tvUserHome);
        tvHome.setText(mUser.getUserHome().toString().trim());
        rlUserHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AbDialogUtil.removeDialog(dialogChooseHome);
                AbDialogUtil.showAlertDialog(dialogChooseHome);
            }
        });
    }

    /**
     * 设置收货地址
     */
    private void setAddress() {
        rlUserAddress = (RelativeLayout) findViewById(R.id.rlUserAddress);
        rlUserAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MyPersonInfoActivity.this, AddressActivity.class);
                startActivityForResult(intent, 111);
            }
        });
    }

    /**
     * 弹窗选择
     */
    private void getdialogs() {
        choosehead();
        choosesex();
        getnickname();
        editintro();
        chooserhome();
    }

    /**
     * 选择常住地址
     */
    private void chooserhome() {
        dialogChooseHome = (LinearLayout) inflater.inflate(R.layout.dialog_chosse_home, null);
        wlPrince = (WheelView) dialogChooseHome.findViewById(R.id.wlProvince);
        wlCity = (WheelView) dialogChooseHome.findViewById(R.id.wlcity);
        wlArea = (WheelView) dialogChooseHome.findViewById(R.id.wlarea);
        wlPrince.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                updateCities();
            }
        });
        wlCity.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                updateAreas();
            }
        });
        wlArea.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
                mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
            }
        });
        setUpData();
        Button btEnsure = (Button) dialogChooseHome.findViewById(R.id.btEnsure);
        btEnsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUser.setUserHome(mCurrentProviceName + mCurrentCityName + mCurrentDistrictName);
                AbDialogUtil.removeDialog(dialogChooseHome);
                updata();
            }
        });
    }

    private void setUpData() {
        initProvinceDatas();
        wlPrince.setViewAdapter(new ArrayWheelAdapter<String>(this, mProvinceDatas));
        // 设置可见条目数量
        wlPrince.setVisibleItems(7);
        wlCity.setVisibleItems(7);
        wlArea.setVisibleItems(7);
        updateCities();
        updateAreas();
    }

    /**
     * 根据当前的市，更新区WheelView的信息
     */
    private void updateAreas() {
        int pCurrent = wlCity.getCurrentItem();
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
        String[] areas = mDistrictDatasMap.get(mCurrentCityName);
        if (areas == null) {
            areas = new String[]{""};
        }
        wlArea.setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
        wlArea.setCurrentItem(0);
    }

    /**
     * 根据当前的省，更新市WheelView的信息
     */
    private void updateCities() {
        int pCurrent = wlPrince.getCurrentItem();
        mCurrentProviceName = mProvinceDatas[pCurrent];
        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
        if (cities == null) {
            cities = new String[]{""};
        }
        wlCity.setViewAdapter(new ArrayWheelAdapter<String>(this, cities));
        wlCity.setCurrentItem(0);
        updateAreas();
    }


    /**
     * 弹窗编辑获得简介
     */
    private void editintro() {
        dialogEditIntro = (LinearLayout) inflater.inflate(R.layout.dialog_edit_intro, null);
        final EditText etIntro = (EditText) dialogEditIntro.findViewById(R.id.etIntro);
        etIntro.setText(mUser.getUserIntro());
        Button btEnsure = (Button) dialogEditIntro.findViewById(R.id.btEnsure);
        btEnsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String intro = etIntro.getText().toString().trim();
                mUser.setUserIntro(intro);
                updata();
                AbDialogUtil.removeDialog(MyPersonInfoActivity.this);
            }
        });
    }

    /**
     * 获取用户昵称并设置
     */
    private void getnickname() {
        dialogEditNickName = (LinearLayout) inflater.inflate(R.layout.dialog_edit_nickname, null);
        final EditText etNickName = (EditText) dialogEditNickName.findViewById(R.id.etNickName);
        etNickName.setText(mUser.getUserNickname().toString());
        Button btEnsure = (Button) dialogEditNickName.findViewById(R.id.btEnsure);
        btEnsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nickname = etNickName.getText().toString().trim();
                mUser.setUserNickname(nickname);
                updata();
                AbDialogUtil.removeDialog(MyPersonInfoActivity.this);
            }
        });
    }

    /**
     * 单选性别
     */
    private void choosesex() {
        dialogChooseSex = (LinearLayout) inflater.inflate(R.layout.dialog_choose_sex, null);
        RadioGroup rgChooseSex = (RadioGroup) dialogChooseSex.findViewById(R.id.radiogroup);
        final RadioButton rbMan = (RadioButton) dialogChooseSex.findViewById(R.id.rbMan);
        final RadioButton rbWoman = (RadioButton) dialogChooseSex.findViewById(R.id.rbWoman);
        Boolean isboy = mUser.getGender() == 1;
        if (isboy) {
            rbMan.setChecked(true);
        } else {
            rbWoman.setChecked(true);
        }
        rgChooseSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == rbMan.getId()) {
                    mUser.setGender(1);
                } else if (checkedId == rbWoman.getId()) {
                    mUser.setGender(0);
                }
            }
        });
        Button btEnsure = (Button) dialogChooseSex.findViewById(R.id.btEnsure);
        btEnsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AbDialogUtil.removeDialog(MyPersonInfoActivity.this);
                updata();
            }
        });
    }

    /**
     * 打开相册或拍照选择头像
     */
    private void choosehead() {
        //初始化图片保存路径
        String photo_dir = AbFileUtil.getImageDownloadDir(this);
        if (AbStrUtil.isEmpty(photo_dir)) {
            AbToastUtil.showToast(this, "存储卡不存在");
        } else {
            PHOTO_DIR = new File(photo_dir);
        }
        dialogChooseHeader = (LinearLayout) inflater.inflate(R.layout.dialog_choose_photo, null);
        TextView tvSelectPhoto = (TextView) dialogChooseHeader.findViewById(R.id.tvselectphoto);
        tvSelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AbDialogUtil.removeDialog(MyPersonInfoActivity.this);
                // 从相册中去获取
                try {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
                    intent.setType("image/*");
                    startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
                } catch (ActivityNotFoundException e) {
                    AbToastUtil.showToast(MyPersonInfoActivity.this, "没有找到照片");
                }
            }
        });
        TextView tvTakePhoto = (TextView) dialogChooseHeader.findViewById(R.id.tvtakephoto);
        tvTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AbDialogUtil.removeDialog(MyPersonInfoActivity.this);
                String status = Environment.getExternalStorageState();
                //判断是否有SD卡,如果有sd卡存入sd卡在说，没有sd卡直接转换为图片
                if (status.equals(Environment.MEDIA_MOUNTED)) {
                    doTakePhoto();
                } else {
                    AbToastUtil.showToast(MyPersonInfoActivity.this, "没有可用的存储卡");
                }

            }
        });
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
            AbToastUtil.showToast(MyPersonInfoActivity.this, "未找到系统相机程序");
        }
    }


    /**
     * 更新界面
     */
    private void updata() {
        String headurl = mUser.getUserHeadUrl();
        if(path == null){
            if (headurl == null) {
                bitmapUtils.display(imgUserHead, String.valueOf(R.mipmap.cam_photo));
            } else {
                bitmapUtils.display(imgUserHead, PathUtils.createPortriateUrl(mUser.getUserID(),headurl));
            }
        }else{
            bitmapUtils.display(imgUserHead,path);
        }

        tvIntro.setText(mUser.getUserIntro());
        int gender = mUser.getGender();
        if (gender == 1) {
            tvSex.setText("男");
        } else {
            tvSex.setText("女");
        }
        tvNickName.setText(mUser.getUserNickname());
        tvHome.setText(mUser.getUserHome());
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
     * 保存用户信息到服务器
     *
     * @param mUser
     */
    private void saveUserInfo(User mUser) {
        final JSONObject info = mUser.toJson();
        RequestParams params = new RequestParams();
        System.out.print(info.toString());
        params.addBodyParameter("data", info.toString());
        if (path == null) {
            params.addBodyParameter("isFile", "0");
        } else {
            params.addBodyParameter("file", new File(path));
            params.addBodyParameter("isFile", "1");
        }

        httpUtils.send(HttpRequest.HttpMethod.POST, Path.saveuserinfo, params, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                AbDialogUtil.showProgressDialog(MyPersonInfoActivity.this, R.mipmap.loading, info.toString());
            }

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                AbDialogUtil.removeDialog(MyPersonInfoActivity.this);
                AbDialogUtil.showAlertDialog(MyPersonInfoActivity.this, "成功", responseInfo.result.toString(), new AbAlertDialogFragment.AbDialogOnClickListener() {
                    @Override
                    public void onPositiveClick() {
                        setResult(RESULT_OK);
                        finish();
                    }

                    @Override
                    public void onNegativeClick() {

                    }
                });
            }

            @Override
            public void onFailure(HttpException e, String s) {
                //AbDialogUtil.removeDialog(MyPersonInfoActivity.this);
                AbToastUtil.showToast(MyPersonInfoActivity.this, "对不起更新用户信息失败，请检查网络连接");
            }
        });
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
                if (!AbStrUtil.isEmpty(currentFilePath)) {
                    Intent intent1 = new Intent(this, CropImageActivity.class);
                    intent1.putExtra("PATH", currentFilePath);
                    startActivityForResult(intent1, CAMERA_CROP_DATA);
                } else {
                    AbToastUtil.showToast(MyPersonInfoActivity.this, "未在存储卡中找到这个文件");
                }
                break;
            case CAMERA_WITH_DATA:
                AbLogUtil.d(MyPersonInfoActivity.class, "将要进行裁剪的图片的路径是 = " + mCurrentPhotoFile.getPath());
                String currentFilePath2 = mCurrentPhotoFile.getPath();
                Intent intent2 = new Intent(this, CropImageActivity.class);
                intent2.putExtra("PATH", currentFilePath2);
                startActivityForResult(intent2, CAMERA_CROP_DATA);
                break;
            case CAMERA_CROP_DATA:
                path = mIntent.getStringExtra("PATH");
                AbLogUtil.d(MyPersonInfoActivity.class, "裁剪后得到的图片的路径是 = " + path);
                BitmapUtils bitmapUtils = BitmapHelper.getBitmapUtils(getApplicationContext());
                bitmapUtils.display(imgUserHead, path);//保存用户头像
                mUser.setUserHeadUrl(path);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
