package com.deeal.exchange.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.deeal.exchange.Bean.CommodityListItemBean;
import com.deeal.exchange.R;
import com.deeal.exchange.application.MyApplication;
import com.deeal.exchange.application.Path;
import com.deeal.exchange.tools.BitmapHelper;
import com.deeal.exchange.tools.PathUtils;
import com.deeal.exchange.tools.RongIMUtils;
import com.deeal.exchange.view.CircleImageView;
import com.deeal.exchange.view.MyGirdView;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/7/9.
 */
public class CollectionListAdapter extends BaseAdapter {

    private ArrayList<CommodityListItemBean> items = new ArrayList<CommodityListItemBean>();
    private Context context;
    private LayoutInflater inflater;
    private BitmapUtils bitmapUtils;

    public CollectionListAdapter(Context context , ArrayList<CommodityListItemBean> items){
        this.context = context;
        this.items = items;
        this.bitmapUtils = BitmapHelper.getBitmapUtils(context.getApplicationContext());
        this.inflater = LayoutInflater.from(context);
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
        if(convertView == null){
            convertView = inflater.inflate(R.layout.collection_list_item,null,false);
            viewHolder =new ViewHolder();
            viewHolder.glPics = (MyGirdView) convertView.findViewById(R.id.glPics);
            viewHolder.imgSolderHeader = (CircleImageView) convertView.findViewById(R.id.imgSolderHeader);
            viewHolder.tvCost = (TextView) convertView.findViewById(R.id.tvCost);
            viewHolder.tvInfo = (TextView) convertView.findViewById(R.id.tvInfo);
            viewHolder.tvSolderName = (TextView) convertView.findViewById(R.id.tvSolderName);
            viewHolder.imgbtDelete = (ImageButton) convertView.findViewById(R.id.imgbtDelete);
            viewHolder.rlGotoTalk = (RelativeLayout) convertView.findViewById(R.id.rlGoTalk);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final CommodityListItemBean item = (CommodityListItemBean) getItem(position);
        PicsGirdAdapter adapter = new PicsGirdAdapter(context,item.getPicurls(),9,item.getMerchandiseID());
        viewHolder.imgbtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestParams params = new RequestParams();
                params.addBodyParameter("tokenID", MyApplication.mUser.getTokenId());
                params.addBodyParameter("merchandiseID", item.getMerchandiseID());
                Toast.makeText(context, item.getFavoriteID(), Toast.LENGTH_SHORT).show();
                HttpUtils httpUtils = new HttpUtils();
                httpUtils.send(HttpRequest.HttpMethod.POST, Path.deleteCollectionPATH, params, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        removeItems(position);
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        Toast.makeText(context,"网络连接失败！",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        viewHolder.glPics.setAdapter(adapter);
        viewHolder.tvSolderName.setText(item.getUsername());
        bitmapUtils.display(viewHolder.imgSolderHeader, PathUtils.createPortriateUrl(item.getUserid(),item.getUserheadurl()));
        viewHolder.tvCost.setText(item.getCost());
        viewHolder.tvInfo.setText(item.getInfo());
        viewHolder.rlGotoTalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RongIMUtils.toChatForBuy(context,item.getUserid(),item.getUsername(),item.getMerchandiseID(),null);
            }
        });
        return convertView;
    }

    private static  class  ViewHolder{
        CircleImageView imgSolderHeader;
        TextView tvSolderName;
        TextView tvInfo;
        TextView tvCost;
        ImageButton imgbtDelete;
        MyGirdView glPics;
        RelativeLayout rlGotoTalk;
    }

    /**
     * 删除指定位置的条目
     *
     * @param position
     */
    public void removeItems(int position) {
        items.remove(position);
        notifyDataSetChanged();
    }
}
