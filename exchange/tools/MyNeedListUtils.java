package com.deeal.exchange.tools;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.ab.view.pullview.AbPullToRefreshView;
import com.deeal.exchange.Bean.CommodityListItemBean;
import com.deeal.exchange.R;
import com.deeal.exchange.adapter.CommodityListAdapter;
import com.deeal.exchange.adapter.FramgentMyNeedListAdapter;
import com.deeal.exchange.application.Path;
import com.deeal.exchange.model.MerchandiseSearchOption;
import com.deeal.exchange.model.MyNeedSearchOption;
import com.deeal.exchange.view.loadingview.LoadingView;
import com.deeal.exchange.view.zxing.view.ViewfinderView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by gxy on 2015/8/24.
 */
public class MyNeedListUtils {
    /**
     * @param loadtype 加载类型，0：初始化；1：下拉刷新；2：上拉加载
     * @param listView 用于展示的listview
     */
    public static void getMyNeedFormServer(final Context context,MerchandiseSearchOption merchandiseSearchOption, final AbPullToRefreshView abPullToRefreshView, final LoadingView loadingView, final int loadtype, final ListView listView, final FramgentMyNeedListAdapter adapter) throws JSONException {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
        String json = MyNeedSearchOption.createJson(context,merchandiseSearchOption);
        if (json == null){
            return;
        }
        params.addBodyParameter("param",json);
        httpUtils.send(HttpRequest.HttpMethod.POST, Path.search_requirement_by_page, params, new RequestCallBack<String>() {

            @Override
            public void onStart() {
                super.onStart();
                loadingView.setVisibility(View.VISIBLE);
                ((Activity) context).findViewById(R.id.nothing).setVisibility(View.GONE);
            }

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                loadingView.setVisibility(View.GONE);
                ArrayList<CommodityListItemBean> itemBeans = new ArrayList<CommodityListItemBean>();
                try {
                    itemBeans = MyNeedSearchOption.parseJson(responseInfo.result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                switch (loadtype) {
                    case 0:
                        adapter.refreshitems(itemBeans);
                        if (itemBeans.size() == 0) {
                            ((Activity) context).findViewById(R.id.nothing).setVisibility(View.VISIBLE);
                        } else {
                            ((Activity) context).
                                    findViewById(R.id.nothing).
                                    setVisibility(View.GONE);
                        }
                        break;
                    case 1:
                        if (itemBeans.size() == 0) {
                            ((Activity) context).findViewById(R.id.nothing).setVisibility(View.VISIBLE);
                        } else {
                            ((Activity) context).findViewById(R.id.nothing).setVisibility(View.GONE);
                        }
                        adapter.refreshitems(itemBeans);
                        abPullToRefreshView.onHeaderRefreshFinish();
                        break;
                    case 2:
                        adapter.additems(itemBeans);
                        abPullToRefreshView.onFooterLoadFinish();
                        break;
                    default:
                        adapter.refreshitems(itemBeans);
                        break;
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(context, "对不起服务器开小差了", Toast.LENGTH_LONG).show();
            }
        });

    }
}
