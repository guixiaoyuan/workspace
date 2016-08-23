package com.deeal.exchange.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.deeal.exchange.Bean.AddressListItemBean;
import com.deeal.exchange.R;

import java.util.ArrayList;

/**
 * Created by weixianbin 2015/7/10.
 */
public class AddressListAdapter extends BaseAdapter {
    /*构造数据*/
    private ArrayList<AddressListItemBean> mitem = new ArrayList<AddressListItemBean>();
    private Context context = null;
    private LayoutInflater mInflater = null;

    /*构造函数*/
    public AddressListAdapter(Context context, ArrayList<AddressListItemBean> mitem) {
        this.context = context;
        this.mitem = mitem;
        this.mInflater = LayoutInflater.from(context);
    }

    /*返回数组长度*/
    @Override
    public int getCount() {
        return mitem.size();
    }

    /**/
    @Override
    public Object getItem(int position) {
        return mitem.get(position);
    }

    /**/
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**/
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_address, null, false);
            holder = new ViewHolder();
                /*得到各个控件对象*/
            holder.tvAddress = (TextView) convertView.findViewById(R.id.tvAddressListItem);
            holder.tvsAddress = (TextView) convertView.findViewById(R.id.tvsAddressListItem);
            holder.ivDefault = (ImageView) convertView.findViewById(R.id.ivDefault);
            convertView.setTag(holder);   //绑定ViewHplder对象

        } else {
            holder = (ViewHolder) convertView.getTag();   //取出Holder对象
        }

        AddressListItemBean item = (AddressListItemBean) getItem(position);
        if (item.getIsSetDefault() == 1) {
            holder.ivDefault.setVisibility(View.VISIBLE);
        } else {
            holder.ivDefault.setVisibility(View.INVISIBLE);
        }
             /*设置TextView显示的内容，即是存在动态数组的数据*/
        holder.tvAddress.setText(item.getAddresslist());
        holder.tvsAddress.setText(item.getmAddress());
        return convertView;
    }

    /*
  * 存放控件
  * */
    public final class ViewHolder {
        public TextView tvAddress;
        public TextView tvsAddress;
        public ImageView ivDefault;
    }

    public void remove(int position) {
        mitem.remove(position);
        notifyDataSetChanged();
    }


    /*服务器*/
}
