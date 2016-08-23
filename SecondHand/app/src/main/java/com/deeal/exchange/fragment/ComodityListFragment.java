package com.deeal.exchange.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.ab.view.pullview.AbPullToRefreshView;
import com.deeal.exchange.Bean.CommodityListItemBean;
import com.deeal.exchange.R;
import com.deeal.exchange.adapter.CommodityListAdapter;
import com.deeal.exchange.application.MyApplication;
import com.deeal.exchange.application.Path;
import com.deeal.exchange.model.Colledge;
import com.deeal.exchange.model.MerchandiseSearchOption;
import com.deeal.exchange.model.MerchandiseType;
import com.deeal.exchange.model.Sort;
import com.deeal.exchange.tools.MerchandiseListUtils;
import com.deeal.exchange.view.Titlebar;
import com.deeal.exchange.view.loadingview.LoadingView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;

/**
 * Created by gxy on 2015/8/19.
 */
public class ComodityListFragment extends Fragment {


    private Button btBack;
    private ListView lvCommodity;
    private CommodityListAdapter commodityListAdapter = null;
    private Intent intent;
    private int currentPage = 0;
    private Spinner spType;
    private Spinner spPlace;
    private Spinner spSort;
    private Titlebar mTitlebar;
    private ArrayList<Colledge> collegesTypes = new ArrayList<>();
    private ArrayList<Sort> sortTypes = new ArrayList<>();
    private ArrayList<MerchandiseType> merchandiseType = new ArrayList<>();
    private HttpUtils httpUtils = new HttpUtils();
    private ArrayList<CommodityListItemBean> items = new ArrayList<CommodityListItemBean>();
    private MerchandiseSearchOption option = new MerchandiseSearchOption();
    /**
     * 种类id
     */
    private String typeId;
    private int typeposition;
    /**
     * 下啦刷新控件
     */
    private AbPullToRefreshView ptrCommoditylist;

    /**
     * 加载 动画
     */
    private LoadingView loadingView;


    private View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.frame_commodity, null);
            init();
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }

        return rootView;
    }

    private void init() {
        loadingView = (LoadingView) rootView.findViewById(R.id.loadView);
        setoption();
        initspiner();
        initlist();
        initrefresh();
        if (MyApplication.mUser != null) {
            MyApplication.mUser.getTokenId();
        }
    }



    /**
     * 初始化查询条件
     */
    private void setoption() {
        intent = getActivity().getIntent();
        typeId = intent.getStringExtra("typeId");
        typeposition = intent.getIntExtra("position", 0);
        option.setMerchandiseTypeId(typeId + "");
        option.setSchoolId("0");
        option.setCityId("0");
        option.setSorttypeId("0");
        option.setPageCount(10);
        option.setCurrentPage(currentPage);
    }

    /**
     * 初始化下拉列表
     */
    private void initspiner() {
        spType = (Spinner) rootView.findViewById(R.id.spinnerType);
        spPlace = (Spinner) rootView.findViewById(R.id.spinnerPlace);
        spSort = (Spinner) rootView.findViewById(R.id.spinnerSort);
        httpUtils.send(HttpRequest.HttpMethod.POST, Path.sortHost, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                sortTypes = Sort.parseJson(responseInfo.result);
                final ArrayList<String> types = Sort.parseList(sortTypes);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                        R.layout.spiner_item, R.id.tv,
                        types);
                spSort.setAdapter(adapter);
                spSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        option.setSorttypeId(sortTypes.get(position).getSortTypeID());
                        MerchandiseListUtils.getMerchandiseFromServer(getActivity(), option, ptrCommoditylist,
                                loadingView, 0, lvCommodity, commodityListAdapter);
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

        httpUtils.send(HttpRequest.HttpMethod.POST, Path.colleageHost, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                collegesTypes = Colledge.parseJson(responseInfo.result);
                ArrayList<String> types = Colledge.parseList(collegesTypes);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                        R.layout.spiner_item, R.id.tv,
                        types);
                spPlace.setAdapter(adapter);
                spPlace.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        option.setSchoolId(collegesTypes.get(position).getColledgeID());
                        MerchandiseListUtils.getMerchandiseFromServer(getActivity(), option, ptrCommoditylist,
                                loadingView, 0, lvCommodity, commodityListAdapter);
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

        httpUtils.send(HttpRequest.HttpMethod.POST, Path.merchandiseHost, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                merchandiseType = MerchandiseType.parseJson(responseInfo.result);
                ArrayList<String> types = MerchandiseType.parseList(merchandiseType);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                        R.layout.spiner_item, R.id.tv,
                        types);
                spType.setAdapter(adapter);
                spType.setSelection(typeposition);
                spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        option.setMerchandiseTypeId(merchandiseType.get(position).getMerchandiseTypeId());
                        MerchandiseListUtils.getMerchandiseFromServer(getActivity(), option, ptrCommoditylist,
                                loadingView, 0, lvCommodity, commodityListAdapter);
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
     * 初始化列表
     */
    private void initlist() {
        lvCommodity = (ListView) rootView.findViewById(R.id.lvCommoditylist);
        commodityListAdapter = new CommodityListAdapter(getActivity(), items);
        lvCommodity.setAdapter(commodityListAdapter);
        MerchandiseListUtils.getMerchandiseFromServer(getActivity(), option, ptrCommoditylist,
                loadingView, 0, lvCommodity, commodityListAdapter);
    }

    /**
     * 获取拉刷新和上拉加载控件并绑定监听
     */
    private void initrefresh() {
        ptrCommoditylist = (AbPullToRefreshView) rootView.findViewById(R.id.ptrCommoditylist);
        ptrCommoditylist.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(AbPullToRefreshView abPullToRefreshView) {
                refresh();
            }
        });
        ptrCommoditylist.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {
            @Override
            public void onFooterLoad(AbPullToRefreshView abPullToRefreshView) {
                getmore();
            }
        });

    }

    /**
     * 下拉刷新更新界面数据
     */
    private void refresh() {
        currentPage = 0;
        option.setCurrentPage(currentPage);
        MerchandiseListUtils.getMerchandiseFromServer(getActivity(), option, ptrCommoditylist,
                loadingView, 1, lvCommodity, commodityListAdapter);

    }

    /**
     * 上拉加载更多页面
     */
    private void getmore() {
        currentPage = currentPage + 10;
        option.setCurrentPage(currentPage);
        MerchandiseListUtils.getMerchandiseFromServer(getActivity(), option, ptrCommoditylist,
                loadingView, 2, lvCommodity, commodityListAdapter);
    }


}
