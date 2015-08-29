package com.deeal.exchange.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.deeal.exchange.Bean.CommodityListItemBean;
import com.deeal.exchange.R;
import com.deeal.exchange.application.MyApplication;
import com.deeal.exchange.application.Path;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Sunqiyong on 2015/7/13.
 */
public class MyNeedListAdapter extends BaseAdapter {
    private ArrayList<HashMap<String, String>> items;
    private Context context = null;
    private LayoutInflater minflater = null;

    public MyNeedListAdapter(Context context, ArrayList<HashMap<String, String>> items) {
        this.context = context;
        this.items = items;
        minflater = LayoutInflater.from(context);
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = minflater.inflate(R.layout.my_need_item, null, false);
            holder = new ViewHolder();
            holder.tvMyNeed = (TextView) convertView.findViewById(R.id.tv_need_content_my_need_item);
            holder.btDelete = (Button) convertView.findViewById(R.id.bt_delete_my_need_item);
            convertView.setTag(holder);    //绑定holder对象
        } else {
            holder = (ViewHolder) convertView.getTag();   //取出holder对象
        }
        HashMap<String, String> item = (HashMap<String, String>) getItem(position);
        holder.tvMyNeed.setText(item.get("info"));
        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String requirementID = items.get(position).get("requirementID");
                RequestParams params = new RequestParams();
                params.addBodyParameter("tokenID", MyApplication.mUser.getTokenId());
                params.addBodyParameter("requirementID", requirementID);
                upMethod(params, Path.deleterequirement);
                items.remove(position);
                notifyDataSetChanged();
            }
        });
        return convertView;

    }


    public final class ViewHolder {
        public TextView tvMyNeed;
        public Button btDelete;
    }

    /*数据上传方法*/
    public void upMethod(RequestParams params, final String uploadHost) {

        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST, uploadHost, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
            }

            @Override
            public void onFailure(HttpException e, String s) {
                e.printStackTrace();
            }
        });
    }


}
