package com.deeal.exchange.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.ab.view.pullview.AbPullToRefreshView;
import com.deeal.exchange.Bean.CommodityListItemBean;
import com.deeal.exchange.R;
import com.deeal.exchange.activity.CommodityDetailActivity;
import com.deeal.exchange.adapter.SquareGridAdapter;
import com.deeal.exchange.application.Path;
import com.deeal.exchange.model.Colledge;
import com.deeal.exchange.model.MerchandiseSearchOption;
import com.deeal.exchange.model.MerchandiseType;
import com.deeal.exchange.model.Sort;
import com.deeal.exchange.view.StaggeredGridView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONException;

import java.util.ArrayList;

import io.rong.common.ResourceUtils;

public class SquarePageFragment extends Fragment {
    private View rootview;
    private StaggeredGridView sgSquare;
    private ArrayList<CommodityListItemBean> items = new ArrayList<CommodityListItemBean>();
    private HttpUtils httpUtils = new HttpUtils();
    private Spinner spType;
    private Spinner spPlace;
    private Spinner spSort;
    private ArrayList<Colledge> collegesTypes = new ArrayList<>();
    private ArrayList<Sort> sortTypes = new ArrayList<>();
    private ArrayList<MerchandiseType> merchandiseType = new ArrayList<>();
    private CommodityListItemBean commodityListItemBean = new CommodityListItemBean();
    private MerchandiseSearchOption option = new MerchandiseSearchOption();
    private int currentPage = 0;
    private String jsonStr = "";
    private SquareGridAdapter adapter;
    private AbPullToRefreshView ptrSquare;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootview == null) {
            rootview = inflater.inflate(R.layout.fragment_square_page, null);
            initsp();
            initlist();
            setoption();
            getdata(0);
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
     * 绑定控件以及点击事件
     */
    private void initlist() {
        sgSquare = (StaggeredGridView) rootview.findViewById(R.id.sgSquare);
        //setStraggeredGridView();
        FragmentActivity activity = getActivity();
        if(null == activity){
            return;
        }
        adapter = new SquareGridAdapter(activity, items);
        sgSquare.setAdapter(adapter);
        sgSquare.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("merchandiseID", ((CommodityListItemBean) adapter.getItem(position))
                        .getMerchandiseID());
                intent.setClass(getActivity(), CommodityDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 加载下拉选择
     */
    private void initsp() {
        spType = (Spinner) rootview.findViewById(R.id.spinnerType);
        spPlace = (Spinner) rootview.findViewById(R.id.spinnerPlace);
        spSort = (Spinner) rootview.findViewById(R.id.spinnerSort);

        httpUtils.send(HttpRequest.HttpMethod.POST, Path.colleageHost, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                collegesTypes = Colledge.parseJson(responseInfo.result);
                ArrayList<String> types = Colledge.parseList(collegesTypes);
                FragmentActivity activity = getActivity();
                if (null == activity){
                    return;
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
                        R.layout.spiner_item, R.id.tv,
                        types);
                spPlace.setAdapter(adapter);
                spPlace.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        commodityListItemBean.setCollegesTypes(collegesTypes.get(position).getColledgeName());
                        option.setSchoolId(collegesTypes.get(position).getColledgeID());
                        getdata(0);
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
        httpUtils.send(HttpRequest.HttpMethod.POST, Path.sortHost, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                sortTypes = Sort.parseJson(responseInfo.result);
                ArrayList<String> types = Sort.parseList(sortTypes);
                FragmentActivity activity = getActivity();
                if (types == null || null == activity){
                    return;
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
                        R.layout.spiner_item,
                        R.id.tv,
                        types);
                spSort.setAdapter(adapter);
                spSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        commodityListItemBean.setSortTypes(sortTypes.get(position).getSortType());
                        option.setSorttypeId(sortTypes.get(position).getSortTypeID());
                        getdata(0);
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
                if (types == null){
                    return;
                }
                if (types.size()!= 0) {
                    FragmentActivity activity = getActivity();
                    if(null == activity){
                        return;
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, R.layout.spiner_item, R.id.tv, types);
                    spType.setAdapter(adapter);
                    spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            commodityListItemBean.setMerchandiseType(merchandiseType.get(position).getMerchandiseType());
                            option.setMerchandiseTypeId(merchandiseType.get(position).getMerchandiseTypeId());
                            getdata(0);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });

    }

    /**
     * 从服务器获取数据
     */
    private void getdata(final int gettype) {
        RequestParams params = new RequestParams();
        try {
            FragmentActivity activity = getActivity();
            if(null == activity){
                return;
            }
            jsonStr = MerchandiseSearchOption.createJson(activity,option);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        params.addBodyParameter("merchandiseInfo", jsonStr);
        httpUtils.send(HttpRequest.HttpMethod.POST, Path.getMerchandise, params, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        ArrayList<CommodityListItemBean> itemBeans = new ArrayList<CommodityListItemBean>();
                        try {
                            itemBeans = CommodityListItemBean.parseJson(responseInfo.result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        switch (gettype) {
                            case 0:
                                adapter.refreshItems(itemBeans);
                                break;
                            case 1:
                                adapter.refreshItems(itemBeans);
                                ptrSquare.onHeaderRefreshFinish();
                                break;
                            case 2:
                                adapter.addItems(itemBeans);
                                ptrSquare.onFooterLoadFinish();
                                break;
                            default:
                                adapter.refreshItems(itemBeans);
                                break;
                        }
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {

                    }
                }

        );


    }


    /**
     * 获取拉刷新和上拉加载控件并绑定监听
     */
    private void initrefresh() {
        ptrSquare = (AbPullToRefreshView) rootview.findViewById(R.id.ptrSquare);
        ptrSquare.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(AbPullToRefreshView abPullToRefreshView) {
                refresh();
            }
        });
        ptrSquare.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {
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
        getdata(1);
    }

    /**
     * 上拉加载更多页面
     */
    private void getmore() {
        currentPage= currentPage+10;
        option.setCurrentPage(currentPage);
        getdata(2);
    }

}

