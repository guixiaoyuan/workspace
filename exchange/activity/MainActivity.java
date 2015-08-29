package com.deeal.exchange.activity;


import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.activity.AbActivity;
import com.ab.util.AbDialogUtil;
import com.ab.util.AbToastUtil;
import com.deeal.exchange.R;
import com.deeal.exchange.application.Constant;
import com.deeal.exchange.application.MyApplication;
import com.deeal.exchange.fragment.FirstPageFragment;
import com.deeal.exchange.fragment.MessagePageFragment;
import com.deeal.exchange.fragment.MyPageFragment;
import com.deeal.exchange.fragment.SquarePageFragment;
import com.deeal.exchange.model.User;
import com.deeal.exchange.style.StyleHelper;
import com.deeal.exchange.tools.UserInfoDAO;
import com.deeal.exchange.tools.UserTools;
import com.deeal.exchange.tools.onGetUserInfo;
import com.lidroid.xutils.exception.DbException;
import com.thinkland.common.JsonCallBack;
import com.umeng.update.UmengUpdateAgent;

import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;

/**
 * 首页bottom tab效果实现fragment 切换
 *
 * @author ding
 * @version 1.0
 * @since 2015-7-6
 */
public class MainActivity extends AbActivity {

    /**
     * 标题
     */
    private RelativeLayout rlTitle;
    private TextView tvTitle;
    /**
     * Tab首页
     */
    private RelativeLayout rlGoFirst;
    private ImageView imgGoFirst;
    private TextView tvGoFirst;
    /**
     * Tab广场
     */
    private RelativeLayout rlGoSquare;
    private ImageView imgGoSquare;
    private TextView tvGoSquare;
    /**
     * Tab我的
     */
    private RelativeLayout rlGoMypage;
    private ImageView imgGoMypage;
    private TextView tvGoMypage;
    /**
     * Tab消息
     */
    private RelativeLayout rlGoMessage;
    private ImageView imgGoMessage;
    private TextView tvGoMessage;

    /**
     * 发布
     */
    private ImageButton imgbtGoPublish;
    /**
     * 首页四个分页Fragment
     */
    private Fragment mypageFragment, squarepageFragment,
            firstpageFragment, messagepageFragment;
    private int tab = 1;

    private long exitTime = 0;
    /**
     * 发布按钮弹出菜单
     */
    private PopupWindow mpopupWindow;
    private View view;
    private GridLayout gl;

    private ConversationListFragment mConversationListFragment;

    private TextView tvMessageNum;
    /**
     * 经纬度
     */
    private double longitude;
    private double latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reinit();
    }


    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    /**
     * 检查更新
     */
    private void checkForUpdate() {
        UmengUpdateAgent.setUpdateOnlyWifi(false);
        UmengUpdateAgent.update(this);
    }

    /**
     * 初始化备用的界面fragment
     */
    private void initfragment() {
        mypageFragment = new MyPageFragment();
        messagepageFragment = new MessagePageFragment();
        firstpageFragment = new FirstPageFragment();
        squarepageFragment = new SquarePageFragment();
    }

    /**
     * 初始化标题栏
     */
    private void inittitle() {
        tvTitle = (TextView) findViewById(R.id.tvTitle);
    }

    /**
     * 初始化底部控件
     */
    private void initbottom() {
        imgbtGoPublish = (ImageButton) findViewById(R.id.imgbtGoPublish);
        imgbtGoPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                publish();
            }
        });
        rlGoMypage = (RelativeLayout) findViewById(R.id.rlGoMypage);
        rlGoMypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tab = 4;
                getFragemnt(tab);
            }
        });
        rlGoMessage = (RelativeLayout) findViewById(R.id.rlGoMessage);
        rlGoMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tab = 3;
                getFragemnt(tab);
            }
        });
        rlGoSquare = (RelativeLayout) findViewById(R.id.rlGoSquare);
        rlGoSquare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tab = 2;
                getFragemnt(tab);
            }
        });
        rlGoFirst = (RelativeLayout) findViewById(R.id.rlGoFirst);
        rlGoFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tab = 1;
                getFragemnt(tab);
            }
        });
        imgGoFirst = (ImageView) findViewById(R.id.imgGoFirst);
        imgGoMypage = (ImageView) findViewById(R.id.imgGoMypage);
        imgGoMessage = (ImageView) findViewById(R.id.imgGoMessage);
        imgGoSquare = (ImageView) findViewById(R.id.imgGoSquare);
        tvGoFirst = (TextView) findViewById(R.id.tvGoFirst);
        tvGoSquare = (TextView) findViewById(R.id.tvGoSquare);
        tvGoMessage = (TextView) findViewById(R.id.tvGoMessage);
        tvGoMypage = (TextView) findViewById(R.id.tvGoMypage);
    }

    /**
     * 切换fragment
     *
     * @param index
     */
    private void getFragemnt(int index) {
        Fragment fragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (index) {
            case 1:
                fragment = firstpageFragment;
                break;
            case 2:
                fragment = squarepageFragment;
                break;
            case 3:
                if (MyApplication.mUser != null) {
                    if (MyApplication.mUser.getIsLogin()) {
                        fragment = mConversationListFragment;
                    } else {
                        fragment = new MessagePageFragment();
                    }
                } else {
                    fragment = new MessagePageFragment();
                }
                break;
            case 4:
                fragment = mypageFragment;
                break;
            default:
                fragment = firstpageFragment;
        }
        if (!fragment.isAdded()) {
            fragmentTransaction.replace(R.id.flMainContainer, fragment).commit();
        }

        redrawbottom(index);
    }

    /**
     * 根据当前页面重绘底部控件
     */
    private void redrawbottom(int index) {
        StyleHelper styleHelper = MyApplication.styleHelper;
        tvTitle.setVisibility(View.VISIBLE);
        imgGoSquare.setImageResource(R.mipmap.gosquare_n);
        imgGoMypage.setImageResource(R.mipmap.go_my_n);
        imgGoFirst.setImageResource(R.mipmap.gomain_n);
        imgGoMessage.setImageResource(R.mipmap.go_message_n);
        tvGoFirst.setTextColor(getResources().getColor(R.color.gray));
        tvGoSquare.setTextColor(getResources().getColor(R.color.gray));
        tvGoMypage.setTextColor(getResources().getColor(R.color.gray));
        tvGoMessage.setTextColor(getResources().getColor(R.color.gray));
        switch (index) {
            case 1:

                imgGoFirst.setImageResource(styleHelper.getBottomIcons()[0]);
                tvGoFirst.setTextColor(getResources().getColor(styleHelper.getTitleColor()));
                tvTitle.setText(getString(R.string.bottom_text_firstpage));
                tvTitle.setVisibility(View.GONE);
                break;
            case 2:
                imgGoSquare.setImageResource(styleHelper.getBottomIcons()[1]);
                tvGoSquare.setTextColor(getResources().getColor(styleHelper.getTitleColor()));
                tvTitle.setText(getString(R.string.bottom_text_squarepage));
                tvTitle.setVisibility(View.GONE);
                break;
            case 3:
                imgGoMessage.setImageResource(styleHelper.getBottomIcons()[2]);
                tvGoMessage.setTextColor(getResources().getColor(styleHelper.getTitleColor()));
                tvTitle.setText(getString(R.string.bottom_text_messagepage));
                tvMessageNum.setVisibility(View.GONE);
                break;
            case 4:
                imgGoMypage.setImageResource(styleHelper.getBottomIcons()[3]);
                tvGoMypage.setTextColor(getResources().getColor(styleHelper.getTitleColor()));
                tvTitle.setText(getString(R.string.bottom_text_mypage));
                break;
        }
    }

    private long firstTime = 0;
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {
                    Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    firstTime = secondTime;//更新firstTime
                    return true;
                } else {
                    System.exit(0);
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
//            if ((System.currentTimeMillis() - exitTime) > 5000) {
//                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
//                exitTime = System.currentTimeMillis();
//                return true;
//            } else {
//                System.exit(0);
//            }
//
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    /**
     * 获取当前城市
     */
    private void getCity() {
        com.thinkland.barcode.BarCodeData.getCity(this, new JsonCallBack() {
            @Override
            public void jsonLoaded(int i, JSONObject jsonObject) {
                AbDialogUtil.showAlertDialog(MainActivity.this, jsonObject.toString());
            }
        });
    }

    private void publish() {
        if (mpopupWindow == null) {
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    mpopupWindow.dismiss();
                }
            });
            gl = (GridLayout) view.findViewById(R.id.gl);

            mpopupWindow = new PopupWindow(this);
            mpopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            mpopupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            mpopupWindow.setBackgroundDrawable(new BitmapDrawable());
            mpopupWindow.setFocusable(true);
            mpopupWindow.setOutsideTouchable(true);
            RelativeLayout rlPublishMerchandise = (RelativeLayout) view.findViewById(R.id.rlGoPublishMerchandise);
            rlPublishMerchandise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, PublishActivity.class);
                    startActivityForResult(intent, Constant.GOPUBLISH);
                    mpopupWindow.dismiss();
                }
            });
            RelativeLayout rlGoPublishNeed = (RelativeLayout) view.findViewById(R.id.rlGoPublishNeed);
            rlGoPublishNeed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, ReleaseDemandActivity.class);
                    startActivity(intent);
                    mpopupWindow.dismiss();
                }
            });
            RelativeLayout rlPublishCourierBring = (RelativeLayout) view.findViewById(R.id.rlGoPublishcourierbring);
            rlPublishCourierBring.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, CourierBringActivity.class);
                    startActivity(intent);
                    mpopupWindow.dismiss();
                }
            });
            RelativeLayout rlPublishCourierTake = (RelativeLayout) view.findViewById(R.id.rlGoPublishcouriertake);
            rlPublishCourierTake.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, CourierTakeActivity.class);
                    startActivity(intent);
                    mpopupWindow.dismiss();
                }
            });
        }
        gl.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.push_bottom_in));
        mpopupWindow.setContentView(view);
        mpopupWindow.showAtLocation(imgbtGoPublish, Gravity.BOTTOM, 0, 0);
        mpopupWindow.update();
    }

    /**
     * IMKit SDK调用第二步
     * <p/>
     * 建立与服务器的连接
     */
    private void connectRongIM(String Token) {

        RongIM.connect(Token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                //Connect Token 失效的状态处理，需要重新获取 Token
                AbToastUtil.showToast(MainActivity.this, "请重新登陆");
            }

            @Override
            public void onSuccess(String userId) {
                //AbToastUtil.showToast(MainActivity.this, "true");
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                AbDialogUtil.showAlertDialog(MainActivity.this, "false" + errorCode + MyApplication.mUser.getRyTokenID());
            }
        });
        RongIM.setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageListener() {
            @Override
            public boolean onReceived(Message message, int i) {
                receiveMessageHandler.sendEmptyMessage(1100);
                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.GOPUBLISH) {
            if (resultCode == RESULT_OK) {
                getFragemnt(1);
            }
        }

        if (requestCode == Constant.GOTOLOGIN) {
            if (resultCode == RESULT_OK) {
                reinit();
                getFragemnt(1);
            }
        }
    }

    /**
     * 界面重绘
     */
    private void reinit() {
        setContentView(R.layout.activity_main);
        tvMessageNum = (TextView) findViewById(R.id.tvMessageNum);
        if (MyApplication.mUser != null) {
            if (MyApplication.mUser.getRyTokenID().isEmpty()) {

            } else {
                connectRongIM(MyApplication.mUser.getRyTokenID());
                RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
                    @Override
                    public UserInfo getUserInfo(String userid) {
                        UserTools userTools = new UserTools(new onGetUserInfo() {
                            @Override
                            public void onGetUserInfo(User user) {
                                UserInfoDAO userInfoDAO = new UserInfoDAO(getApplicationContext());
                                userInfoDAO.createUser(user);
                            }
                        });
                        userTools.getUserInfoByID(userid);
                        UserInfo userInfo = null;
                        try {
                            UserInfoDAO userInfoDAO = new UserInfoDAO(getApplicationContext());
                            userInfo = userInfoDAO.findUserByUserId(userid);
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                        return userInfo;
                    }
                }, false);
            }
        }
        inittitle();
        initfragment();
        initbottom();
        getFragemnt(tab);
        try {
            checkForUpdate();
        } catch (Exception e) {
            Log.e("update", "error" + e);
        }
        try {
            view = View.inflate(getApplicationContext(), R.layout.popwindow_publish, null);
            mConversationListFragment = new ConversationListFragment();
            Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                    .appendPath("conversationlist")
                    .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示
                    .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")
                    .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")
                    .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")
                    .build();
            mConversationListFragment.setUri(uri);
        } catch (Exception e) {
            Log.e("message", "error" + e);
        }
    }

    private Handler receiveMessageHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            tvMessageNum.setVisibility(View.VISIBLE);
        }
    };

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
