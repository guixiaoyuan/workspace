package com.deeal.exchange.tools;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.ab.view.pullview.AbPullToRefreshView;
import com.deeal.exchange.Bean.CommodityListItemBean;
import com.deeal.exchange.R;
import com.deeal.exchange.adapter.CommodityListAdapter;
import com.deeal.exchange.application.Path;
import com.deeal.exchange.model.MerchandiseInfo;
import com.deeal.exchange.model.MerchandiseSearchOption;
import com.deeal.exchange.view.loadingview.LoadingView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/7/29.
 */
public class MerchandiseListUtils {


    /**
     * @param option   查询条件
     * @param loadtype 加载类型，0：初始化；1：下拉刷新；2：上拉加载
     * @param listView 用于展示的listview
     */
    public static void getMerchandiseFromServer(final Context context, MerchandiseSearchOption option, final AbPullToRefreshView abPullToRefreshView, final LoadingView loadingView, final int loadtype, final ListView listView, final CommodityListAdapter adapter) {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
        try {
            String json = MerchandiseSearchOption.createJson(context, option);
            //如果用户没有登录就前往登录界面而不进行该操作
            if (json == null) {
                return;
            }
            params.addBodyParameter("merchandiseInfo", json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        httpUtils.send(HttpRequest.HttpMethod.POST, Path.getMerchandise, params, new RequestCallBack<String>() {

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
                    itemBeans = CommodityListItemBean.parseJson(responseInfo.result);

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

    /**
     * 查询条件
     *
     * @param commodityListItemBean
     * @param currentPage
     */
    public static void getSrearchOption(CommodityListItemBean commodityListItemBean, int currentPage) {
        commodityListItemBean.setSchoolId("0");
        commodityListItemBean.setCityId("0");
        commodityListItemBean.setCurrentPage(currentPage);
        commodityListItemBean.setMerchandiseTypeID("0");
        commodityListItemBean.setSortTypeId("0");
    }

    /**
     * @param merchandiseID 商品编号
     * @param Token         用户token
     * @param success       成功的回调方法
     */
    public static void getMerchandiseDetailFromServe(String merchandiseID, String Token, final HttpSuccess success) {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
        params.addBodyParameter("merchandiseID", merchandiseID);
        params.addBodyParameter("tokenID", Token);
        httpUtils.send(HttpRequest.HttpMethod.POST, Path.merchandiseDetail, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                MerchandiseInfo info = new MerchandiseInfo();
                parseMerchandise(info, responseInfo.result);
                Log.e("merchandiseifno", responseInfo.result);
                success.onsuccess(info);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                success.onfail();
            }
        });
    }

    /**
     * 解析商品详情封装到MerchandiseInfo
     *
     * @param info
     * @param responseResult
     */
    public static void parseMerchandise(MerchandiseInfo info, String responseResult) {
        try {
            JSONObject result = new JSONObject(responseResult);
            JSONObject data = new JSONObject(result.getString("data"));
            JSONArray imgs = data.getJSONArray("imgList");
            ArrayList<String> imgList = new ArrayList<String>();
            for (int i = 0; i < imgs.length(); i++) {
                imgList.add(imgs.getString(i));
                Log.e("imglist", imgs.getString(i));
            }
            info.setImgList(imgList);
            //info.setCity(data.getString(""));
            info.setCollege(data.getString("college"));
            info.setCarriage(data.getInt("shipmentPrice"));
            info.setDescription(data.getString("info"));
            info.setTitle(data.getString("merchandiseName"));
            info.setMerchandiseTypeID(data.getString("merchandiseTypeID"));
            info.setIncomePrice(data.getInt("oldPrice"));
            info.setPrice(data.getInt("currentPrice"));
            info.setMatching(dataUtils.fromint(data.getInt("matching")));
            info.setInspection(dataUtils.fromint(data.getInt("inspection")));
            info.setRecommendation(dataUtils.fromint(data.getInt("recommendation")));
            info.setSwp(dataUtils.fromint(data.getInt("swap")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析商品详情封装到MerchandiseInfo集合中
     *
     * @param infos
     * @param responseResult
     */
    public static void parseMerchandiseList(ArrayList<MerchandiseInfo> infos, String responseResult) {
        try {
            JSONObject result = new JSONObject(responseResult);
            JSONArray datas = new JSONArray(result.getString("data"));
            for (int i = 0; i < datas.length(); i++) {
                MerchandiseInfo info = new MerchandiseInfo();
                JSONObject data = datas.getJSONObject(i);
                /*JSONArray imgs = data.getJSONArray("imgList");
                ArrayList<String> imgList = new ArrayList<String>();
                for(int j = 0;j<imgs.length();j++){
                    imgList.add(Path.IMGPATH+imgs.getString(i));

                }*/
                info.setTitle(data.getString("merchandiseName"));
                Log.e("title", info.getTitle());
                info.setMerchandiseID(data.getString("merchandiseID"));
                Log.e("title", info.getMerchandiseID() + "");
                /*info.setImgList(imgList);
                info.setCity(data.getString("city"));
                info.setCollege(data.getString("college"));
                info.setCarriage(data.getInt("shipmentPrice"));
                info.setDescription(data.getString("info"));

                info.setMerchandiseTypeID(data.getString("merchandiseTypeID"));
                info.setIncomePrice(data.getInt("oldPrice"));*/
                info.setPrice(data.getInt("currentPrice"));
                Log.e("title", info.getPrice() + "");
                /*info.setMatching(dataUtils.fromint(data.getInt("matching")));
                info.setInspection(dataUtils.fromint(data.getInt("inspection")));
                info.setRecommendation(dataUtils.fromint(data.getInt("recommendation")));
                info.setSwp(dataUtils.fromint(data.getInt("swap")));*/
                infos.add(info);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

