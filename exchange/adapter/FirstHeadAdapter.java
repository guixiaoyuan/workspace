package com.deeal.exchange.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.deeal.exchange.R;
import com.deeal.exchange.tools.BitmapHelper;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2015/7/17.
 * used to getFirstHeadview
 */
public class FirstHeadAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<HashMap<String, Object>> items = new ArrayList<HashMap<String, Object>>();
    private BitmapUtils bitmapUtils;

    public FirstHeadAdapter(Context context, List<HashMap<String, Object>> items) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.items = items;
        bitmapUtils = BitmapHelper.getBitmapUtils(context.getApplicationContext());
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.head_grid, null);
            holder.imgType = (ImageView) convertView.findViewById(R.id.imgType);
            holder.tvType = (TextView) convertView.findViewById(R.id.tvType);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvType.setText(items.get(position).get("tvType").toString());
        bitmapUtils.display(holder.imgType, items.get(position).get("imgType").toString());
        return convertView;
    }

    private static class ViewHolder {
        ImageView imgType;
        TextView tvType;
    }
}
