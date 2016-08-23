package com.deeal.exchange.fragment;


import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ab.util.AbDialogUtil;
import com.ab.util.AbToastUtil;
import com.ab.view.pullview.AbPullToRefreshView;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.deeal.exchange.Bean.CommodityListItemBean;
import com.deeal.exchange.R;
import com.deeal.exchange.activity.CityListActivity;
import com.deeal.exchange.activity.CommodityActivity;
import com.deeal.exchange.activity.MainActivity;
import com.deeal.exchange.activity.SearchActivity;
import com.deeal.exchange.adapter.CommodityListAdapter;
import com.deeal.exchange.adapter.FirstHeadAdapter;
import com.deeal.exchange.adapter.MyPagerAdapter;
import com.deeal.exchange.application.Constant;
import com.deeal.exchange.application.MyApplication;
import com.deeal.exchange.application.Path;
import com.deeal.exchange.model.City;
import com.deeal.exchange.model.MerchandiseSearchOption;
import com.deeal.exchange.tools.MerchandiseListUtils;
import com.deeal.exchange.view.MyGirdView;
import com.deeal.exchange.view.loadingview.LoadingView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FirstPageFragment extends Fragment implements AMapLocationListener {
    private View rootview;
    private ListView lvFirstList;
    private CommodityListAdapter adapter = null;
    private CommodityListItemBean commodityListItemBean = new CommodityListItemBean();
    /**
     * listview的顶部导航
     */
    private View header;
    private MyGirdView glMyhead;
    private Intent intent = null;
    private TextView tvCity;
    /**
     * 记录分页数量
     */
    private int currentPage = 0;
    private MyCallBack myCallBack = new MyCallBack();
    private FirstHeadAdapter headAdapter;
    private List<HashMap<String, Object>> typeList = new ArrayList<HashMap<String, Object>>();
    private ArrayList<CommodityListItemBean> items = new ArrayList<CommodityListItemBean>();
    private LocationManagerProxy mLocationManagerProxy;
    private LoadingView loadview;
    private AbPullToRefreshView ptrFirst;
    private MerchandiseSearchOption option = new MerchandiseSearchOption();
    private EditText etSearch;

    private ArrayList<View> myGirdViews = new ArrayList<>();
    private LinearLayout llDots;
    private ViewPager viewPager;
    private MyPagerAdapter myPagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = new Intent();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootview == null) {
            rootview = inflater.inflate(R.layout.fragment_first_page, null);
            rootview.findViewById(R.id.first_title).setBackgroundColor(getResources().getColor(MyApplication.styleHelper.getTitleColor()));
            setoption();
            header = inflater.inflate(R.layout.first_head_item, null);
            glMyhead = (MyGirdView) header.findViewById(R.id.glMyhead);
            tvCity = (TextView) rootview.findViewById(R.id.tvCity);
            tvCity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    chooseCity();
                }
            });
            loadview = (LoadingView) rootview.findViewById(R.id.loadView);
            etSearch = (EditText) rootview.findViewById(R.id.etSearch);
            etSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), SearchActivity.class);
                    startActivity(intent);
                }
            });
            init();
            initViewPager();
            initHeader();
            initlist();
            initrefresh();
        }
        ViewGroup parent = (ViewGroup) rootview.getParent();
        if (parent != null) {
            parent.removeView(rootview);
        }
        return rootview;
    }

    /**
     * 初始化查询条件
     */
    private void setoption() {
        option.setMerchandiseTypeId("0");
        option.setSchoolId("0");
        option.setCityId("0");
        option.setSorttypeId("0");
        option.setPageCount(10);
        option.setCurrentPage(currentPage);
    }

    /**
     * 初始化头部Viewpager
     */
    private void initViewPager() {
        viewPager = (ViewPager) header.findViewById(R.id.headerViewPager);
        myPagerAdapter = new MyPagerAdapter(getActivity(), myGirdViews);
        viewPager.setAdapter(myPagerAdapter);
        llDots = (LinearLayout) header.findViewById(R.id.lldots);
    }

    /**
     * 初始化定位
     */
    private void init() {
        FragmentActivity activity = getActivity();
        if (null == activity) {
            return;
        }
        mLocationManagerProxy = LocationManagerProxy.getInstance(activity);
        //此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        //注意设置合适的定位时间的间隔，并且在合适时间调用removeUpdates()方法来取消定位请求
        //在定位结束后，在合适的生命周期调用destroy()方法
        //其中如果间隔时间为-1，则定位只定一次
        mLocationManagerProxy.requestLocationData(
                LocationProviderProxy.AMapNetwork, 60 * 1000, 15, this);
        mLocationManagerProxy.setGpsEnable(false);
    }

    /**
     * 从服务器动态获取头部状态
     */
    private void initHeader() {
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.POST, Path.merchandiseHost, myCallBack);
    }


    private class MyCallBack extends RequestCallBack<String> {
        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            FragmentActivity activity = getActivity();
            if (null == activity) {
                return;
            }
            AbDialogUtil.removeDialog(activity);
            try {
                JSONObject result = new JSONObject(responseInfo.result);
                String status = result.get("status").toString();
                if (status.equals("SUCCESS")) {
                    JSONArray jsonArray = new JSONArray(result.get("data").toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject type = (JSONObject) jsonArray.get(i);
                        HashMap<String, Object> map = new HashMap<String, Object>();
                        map.put("imgType", Path.IMGPATH + type.get("imgpath"));//服务器键
                        map.put("tvType", type.get("merchandiseTypeName"));
                        map.put("typeId", type.get("merchandiseTypeID"));
                        typeList.add(map);
                    }

                    headAdapter = new FirstHeadAdapter(activity, typeList);
                    int n = typeList.size() / 8;
                    Log.e("listsize", typeList.size() + "");
                    Log.e("listsize", n + "");
                    for (int i = 1; i <= n; i++) {
                        if (i == n) {
                            List<HashMap<String, Object>> list = typeList.subList((n - 1) * 8, typeList.size() - 1);
                            View Headitem = LayoutInflater.from(getActivity()).inflate(R.layout.head_item, null);
                            MyGirdView myGirdView = (MyGirdView) Headitem.findViewById(R.id.glMyhead);
                            myGirdView.setAdapter(new FirstHeadAdapter(activity, list));
                            myPagerAdapter.addItem(Headitem);
                            Log.e("count:", i + "");
                        } else {
                            List<HashMap<String, Object>> list = typeList.subList((n - 1) * 8, n * 8 - 1);
                            View Headitem = LayoutInflater.from(getActivity()).inflate(R.layout.head_item, null);
                            MyGirdView myGirdView = (MyGirdView) Headitem.findViewById(R.id.glMyhead);
                            myGirdView.setAdapter(new FirstHeadAdapter(activity, list));
                            myPagerAdapter.addItem(Headitem);
                            Log.e("count:", i + "");
                        }
                        TextView textView = new TextView(getActivity());
                        textView.setText(i + "");
                        llDots.addView(textView);
                    }
                    glMyhead.setAdapter(headAdapter);
                    glMyhead.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Log.i("FirstPage", typeList.get(position).toString());
                            goCommodityActivity(position);
                        }
                    });
                } else {
                    AbDialogUtil.removeDialog(activity);
                    AbToastUtil.showToast(activity, "e");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(HttpException e, String s) {
            FragmentActivity activity = getActivity();
            if (null == activity) {
                return;
            }
            AbDialogUtil.removeDialog(activity);
            AbToastUtil.showToast(activity, "e" + s);
        }
    }

    /**
     * 初始化listview
     */
    private void initlist() {
        lvFirstList = (ListView) rootview.findViewById(R.id.llFirstpagelist);
        if (lvFirstList.getHeaderViewsCount() <= 0) {
            lvFirstList.addHeaderView(header);
        }
        FragmentActivity activity = getActivity();
        if (null == activity) {
            return;
        }
        adapter = new CommodityListAdapter(activity, items);
        lvFirstList.setAdapter(adapter);
        MerchandiseListUtils.getMerchandiseFromServer(activity, option, ptrFirst, loadview, 0, lvFirstList, adapter);
    }


    /**
     * 获取拉刷新和上拉加载控件并绑定监听
     */
    private void initrefresh() {
        ptrFirst = (AbPullToRefreshView) rootview.findViewById(R.id.ptrFirstPage);
        ptrFirst.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(AbPullToRefreshView abPullToRefreshView) {
                refresh();
            }
        });
        ptrFirst.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {
            @Override
            public void onFooterLoad(AbPullToRefreshView abPullToRefreshView) {
                getmore();
            }
        });
    }

    /**
     * 根据选择的type前往商品列表界面
     *
     * @param position
     */
    private void goCommodityActivity(int position) {
        FragmentActivity activity = getActivity();
        if (null == activity) {
            return;
        }
        intent.setClass(activity, CommodityActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("typeId", typeList.get(position).get("typeId").toString());
        startActivity(intent);
    }

    /**
     * 下拉刷新更新界面数据
     */
    private void refresh() {
        currentPage = 0;
        option.setCurrentPage(currentPage);
        FragmentActivity activity = getActivity();
        if (null == activity) {
            return;
        }
        MerchandiseListUtils.getMerchandiseFromServer(activity, option, ptrFirst, loadview,
                1, lvFirstList, adapter);
    }

    /**
     * 上拉加载更多页面
     */
    private void getmore() {
        currentPage = currentPage + 10;
        option.setCurrentPage(currentPage);
        FragmentActivity activity = getActivity();
        if (null == activity) {
            return;
        }
        MerchandiseListUtils.getMerchandiseFromServer(activity, option, ptrFirst, loadview,
                2, lvFirstList, adapter);
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null && aMapLocation.getAMapException().getErrorCode() == 0) {
            //获取位置信息
            tvCity.setText(aMapLocation.getCity());
            double latitude = aMapLocation.getLatitude();
            double longitude = aMapLocation.getLongitude();
            ((MainActivity) getActivity()).setLatitude(latitude);
            ((MainActivity) getActivity()).setLongitude(longitude);
            //定位成功后获取数据
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void stopLocation() {
        if (mLocationManagerProxy != null) {
            mLocationManagerProxy.removeUpdates(this);
            mLocationManagerProxy.destory();
        }
        mLocationManagerProxy = null;
    }

    /**
     * 跳转选择城市界面
     */
    private void chooseCity() {
        Intent intent = new Intent();
        FragmentActivity activity = getActivity();
        if (null == activity) {
            return;
        }
        intent.setClass(activity, CityListActivity.class);
        startActivityForResult(intent, Constant.CHOOSECITY);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.CHOOSECITY) {
            if (requestCode == -1) {
                setCity(MyApplication.currentCity);
            }

        }
    }

    /**
     * 设置城市
     *
     * @param city
     */
    private void setCity(City city) {
        tvCity.setText(city.getCityName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopLocation();
    }
}
