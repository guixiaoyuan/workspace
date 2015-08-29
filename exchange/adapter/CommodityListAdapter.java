package com.deeal.exchange.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.deeal.exchange.Bean.CommodityListItemBean;
import com.deeal.exchange.R;
import com.deeal.exchange.activity.CommodityDetailActivity;
import com.deeal.exchange.application.MyApplication;
import com.deeal.exchange.application.Path;
import com.deeal.exchange.tools.BitmapHelper;
import com.deeal.exchange.tools.DateUtils;
import com.deeal.exchange.tools.PathUtils;
import com.deeal.exchange.view.GridViewUtil;
import com.deeal.exchange.view.MyGirdView;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 首页商品列表适配器
 * Created by Administrator on 2015/7/6.
 */
public class CommodityListAdapter extends BaseAdapter {

    private ArrayList<CommodityListItemBean> items = new ArrayList<CommodityListItemBean>();
    private Context context = null;
    private LayoutInflater inflater = null;
    private BitmapUtils bitmapUtils = null;


    public CommodityListAdapter(Context context, ArrayList<CommodityListItemBean> items) {
        this.items = items;
        this.context = context;
        inflater = LayoutInflater.from(context);
        bitmapUtils = BitmapHelper.getBitmapUtils(context.getApplicationContext());
        bitmapUtils.configDefaultLoadingImage(R.mipmap.default_pic);
        bitmapUtils.configDefaultLoadFailedImage(R.mipmap.testuserhead);
    }


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.first_list_item, null);
            viewHolder.imgbtCollection = (ImageButton) convertView.findViewById(R.id.imgbtCollection);
            viewHolder.imgUserHead = (ImageView) convertView.findViewById(R.id.imgUserHead);
            viewHolder.tvCost = (TextView) convertView.findViewById(R.id.tvCost);
            viewHolder.tvInfo = (TextView) convertView.findViewById(R.id.tvInfo);
            viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
            viewHolder.tvTime = (TextView) convertView.findViewById(R.id.tvPublishTime);
            viewHolder.glPics = (MyGirdView) convertView.findViewById(R.id.glPics);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final ImageButton img = viewHolder.imgbtCollection;
        int iscollected = items.get(position).getFavorite();
        if (iscollected == 1) {
            img.setImageResource(R.mipmap.mycollection);
        } else {
            img.setImageResource(R.mipmap.uncollect);
        }
        viewHolder.imgbtCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int iscollect = items.get(position).getFavorite();
                if (iscollect == 0) {
                    addFavorite(items.get(position).getMerchandiseID(), position, items, img);
                    //需要进行网络操作上传至服务器，并监听是否收藏成功
                } else {
                    deleteFavorite(items.get(position).getMerchandiseID(), position, items, img);
                }

            }
        });
        //根据点击时间跳转
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, CommodityDetailActivity.class);
                intent.putExtra("merchandiseID", items.get(position).getMerchandiseID());
                context.startActivity(intent);
            }
        });

        ArrayList<String> picnames = items.get(position).getPicurls();
        ArrayList<String> picurls = new ArrayList<>();
        for(int i = 0;i<picnames.size();i++){
            String url = Path.MERCHANDISEIMGPATH +items.get(position).getMerchandiseID()+"/thumbnail/"+picnames.get(i);
            picurls.add(url);
        }
        //加载缩略图
        CommodityListItemBean item = items.get(position);
        PicsGirdAdapter adapter = new PicsGirdAdapter(context, picnames, 3,item.getMerchandiseID());//最大显示三个图片
        viewHolder.glPics.setAdapter(adapter);
        viewHolder.tvUserName.setText(item.getUsername());
        viewHolder.tvInfo.setText(item.getInfo());
        viewHolder.tvCost.setText(item.getCost());
        viewHolder.tvTime.setText(DateUtils.FormatData(item.getPublishtime()));
        bitmapUtils.display(viewHolder.imgUserHead, PathUtils.createPortriateUrl(item.getUserid(),item.getUserheadurl()));
        return convertView;
    }

    /**
     * 添加数据
     *
     * @param items
     */
    public void additems(ArrayList<CommodityListItemBean> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    /**
     * 刷新数据
     *
     * @param items
     */
    public void refreshitems(ArrayList<CommodityListItemBean> items) {
        this.items.clear();
        this.items.addAll(items) ;
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        public ImageView imgUserHead;
        public TextView tvUserName;
        public TextView tvInfo;
        public TextView tvCost;
        public ImageButton imgbtCollection;
        public TextView tvTime;
        public MyGirdView glPics;
    }

    /**
     * create by sunqiyong 主页增加收藏
     * @param merchandiseID
     * @param position
     * @param items
     * @param img
     */
    private void addFavorite(String merchandiseID,final int position,final ArrayList<CommodityListItemBean> items,final ImageButton img){
        RequestParams params = new RequestParams();
        params.addBodyParameter("tokenID", MyApplication.mUser.getTokenId());
        params.addBodyParameter("merchandiseID", merchandiseID);
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST, Path.add_favoritePath, params, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                items.get(position).setFavorite(1);
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(responseInfo.result);
                    if(jsonObject.getString("status").equals("SUCCESS")){
                        items.get(position).setIscollection(true);
                        img.setImageResource(R.mipmap.mycollection);
                        Toast.makeText(context, "收藏成功" , Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(context, "收藏失败！" , Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * create by sunqiyong 主页删除收藏
     * @param merchandiseID
     * @param position
     * @param items
     * @param img
     */
    private void deleteFavorite(String merchandiseID,final int position,final ArrayList<CommodityListItemBean> items,final ImageButton img){
        RequestParams params = new RequestParams();
        params.addBodyParameter("tokenID", MyApplication.mUser.getTokenId());
        params.addBodyParameter("merchandiseID", merchandiseID);
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST, Path.delete_favoritePath, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                items.get(position).setFavorite(0);
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(responseInfo.result);
                    if(jsonObject.getString("status").equals("SUCCESS")){
                        Toast.makeText(context, "取消收藏" , Toast.LENGTH_LONG).show();
                        items.get(position).setIscollection(false);
                        img.setImageResource(R.mipmap.uncollect);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(context, "取消收藏失败！" , Toast.LENGTH_LONG).show();
            }
        });
    }
}
