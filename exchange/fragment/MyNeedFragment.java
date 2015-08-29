package com.deeal.exchange.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.ab.view.pullview.AbPullToRefreshView;
import com.deeal.exchange.Bean.CollegeBean;
import com.deeal.exchange.Bean.CommodityListItemBean;
import com.deeal.exchange.R;
import com.deeal.exchange.adapter.FramgentMyNeedListAdapter;
import com.deeal.exchange.adapter.MerchandiseTypeAdapter;
import com.deeal.exchange.application.Path;
import com.deeal.exchange.model.City;
import com.deeal.exchange.model.CityModel;
import com.deeal.exchange.model.MerchandiseSearchOption;
import com.deeal.exchange.model.MerchandiseType;
import com.deeal.exchange.model.MyNeedSearchOption;
import com.deeal.exchange.tools.CityListJson;
import com.deeal.exchange.tools.CollegeJson;
import com.deeal.exchange.tools.MerchandiseListUtils;
import com.deeal.exchange.tools.MyNeedListUtils;
import com.deeal.exchange.view.loadingview.LoadingView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/8/19.
 */
public class MyNeedFragment extends Fragment {

    private View rootView;
    private ListView lvMyNeed;
    private Spinner spinnerType, spinnerPlace, spinnerCollege;
    private ArrayList<MerchandiseType> merchandiseType = new ArrayList<>();
    private ArrayList<City> city = new ArrayList<>();
    private ArrayList<CollegeBean> collegeBeans = new ArrayList<>();
    private FramgentMyNeedListAdapter adapter;
    private ArrayList<CommodityListItemBean> items = new ArrayList<>();

    /**
     * 下啦刷新控件
     */
    private AbPullToRefreshView refresh;

    /**
     * 加载 动画
     */
    private LoadingView loadingView;

    private MerchandiseSearchOption merchandiseSearchOption;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        merchandiseSearchOption = new MerchandiseSearchOption();
        merchandiseSearchOption.setSchoolId("0");
        merchandiseSearchOption.setCityId("0");
        merchandiseSearchOption.setMerchandiseTypeId("0");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.frame_myneed, null);

            init();
            initData();
            getSpinnerHttp();
            initRefresh();
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    private void init() {
        spinnerType = (Spinner) rootView.findViewById(R.id.spinnerType);
        spinnerPlace = (Spinner) rootView.findViewById(R.id.spinnerPlace);
        spinnerCollege = (Spinner) rootView.findViewById(R.id.spinnerCollege);
        lvMyNeed = (ListView) rootView.findViewById(R.id.lv_frame_my_need);
        refresh = (AbPullToRefreshView) rootView.findViewById(R.id.ptrCommoditylist);
        loadingView = (LoadingView) rootView.findViewById(R.id.loadView);
    }

    private void initData() {
        adapter = new FramgentMyNeedListAdapter(items, getActivity());
        try {
            MyNeedListUtils.getMyNeedFormServer(getActivity(),merchandiseSearchOption, refresh, loadingView, 0, lvMyNeed, adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        lvMyNeed.setAdapter(adapter);
    }

    private void getSpinnerHttp() {
        final HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.POST, Path.merchandiseHost, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                merchandiseType = MerchandiseType.parseJson(responseInfo.result);
                final ArrayList<String> types = MerchandiseType.parseList(merchandiseType);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                        R.layout.spiner_item, R.id.tv,
                        types);
                spinnerType.setAdapter(adapter);
                spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        merchandiseSearchOption.setMerchandiseTypeId(merchandiseType.get(position).getMerchandiseTypeId());
                        initData();
                        Log.e("merchandiseType",types.get(position).toString());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
        httpUtils.send(HttpRequest.HttpMethod.POST, Path.getCity, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                city = City.parseJson(responseInfo.result);
                ArrayList<String> types = City.parseList(city);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spiner_item, R.id.tv, types);
                spinnerPlace.setAdapter(adapter);
                spinnerPlace.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                        try {
                            merchandiseSearchOption.setCityId(city.get(position).getCityID());
                            initData();
                            String data = CityListJson.createJson(city.get(position).getCityID());
                            RequestParams requestParams = new RequestParams();
                            requestParams.addBodyParameter("params", data);
                            httpUtils.send(HttpRequest.HttpMethod.POST, Path.get_colleges_by_city, requestParams, new RequestCallBack<String>() {
                                @Override
                                public void onSuccess(ResponseInfo<String> responseInfo) {
                                    collegeBeans = CollegeJson.parseJson(responseInfo.result);
                                    ArrayList<String> types = CollegeJson.parseList(collegeBeans);
                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spiner_item, R.id.tv, types);
                                    spinnerCollege.setAdapter(adapter);
                                    spinnerCollege.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            merchandiseSearchOption.setSchoolId(collegeBeans.get(position).getCollegeID());
                                            initData();
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                    });
                                }

                                @Override
                                public void onFailure(HttpException e, String s) {
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });

    }
    /**
     * 获取拉刷新和上拉加载控件并绑定监听
     */
    private void initRefresh(){
        refresh = (AbPullToRefreshView) rootView.findViewById(R.id.ptrCommoditylist);
        refresh.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(AbPullToRefreshView abPullToRefreshView) {
                refresh();
            }
        });
        refresh.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {
            @Override
            public void onFooterLoad(AbPullToRefreshView abPullToRefreshView) {
                getMore();
            }
        });
    }
    /**
     * 下拉刷新更新界面数据
     */
    private void refresh(){
        try {
            MyNeedListUtils.getMyNeedFormServer(getActivity(),merchandiseSearchOption,refresh,loadingView,1,lvMyNeed,adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    /**
     * 上拉加载更多页面
     */
    private void getMore(){
        int currentPage = 0;
        currentPage = currentPage + 10;
        merchandiseSearchOption.setCurrentPage(currentPage);
        try {
            MyNeedListUtils.getMyNeedFormServer(getActivity(), merchandiseSearchOption, refresh,
                    loadingView, 2, lvMyNeed, adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
