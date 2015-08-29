package com.deeal.exchange.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.deeal.exchange.Bean.CourierListItemBean;
import com.deeal.exchange.R;
import com.deeal.exchange.activity.WriteNumberActivity;
import com.deeal.exchange.alipay.Pay;
import com.deeal.exchange.application.Constant;
import com.deeal.exchange.model.MerchandiseInfo;
import com.deeal.exchange.tools.CourierJson;
import com.deeal.exchange.tools.HttpSuccess;
import com.deeal.exchange.tools.PayAlertDialog;
import com.deeal.exchange.tools.onPayResult;
import com.deeal.exchange.tools.onPaySuccess;

import java.text.ParseException;
import java.util.ArrayList;

import io.rong.imkit.RongIM;

/**
 * Created by weixianbin on 2015/7/10.
 */
public class CourierListAdapter extends BaseAdapter {
    /*构造数据*/
    private ArrayList<CourierListItemBean> mitem = new ArrayList<CourierListItemBean>();
    private Context context;
    private LayoutInflater mInflater;
    private String tag;
    private String expressStatusID;
    onPaySuccess onPaySuccess;


    /*构造函数*/
    public CourierListAdapter(Context context, ArrayList<CourierListItemBean> mitem,onPaySuccess onPaySuccess) {
        this.context = context;
        this.mitem = mitem;
        this.mInflater = LayoutInflater.from(context);
        this.onPaySuccess = onPaySuccess;
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

    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_courier_serve, null, false);
            holder = new ViewHolder();
            /*得到各个空间对象*/
            holder.tvServiceNumber = (TextView) convertView.findViewById(R.id.tvServiceNumber);
            holder.tvExpressCompany = (TextView) convertView.findViewById(R.id.tvExpressCompany);
            holder.tvExpressNum = (TextView) convertView.findViewById(R.id.tvExpressNumber);
            holder.tvUserName = (TextView) convertView.findViewById(R.id.tvIssuer);
            holder.btContact = (Button) convertView.findViewById(R.id.btContact);
            holder.btPay = (Button) convertView.findViewById(R.id.btPay);
            holder.btDelete = (Button) convertView.findViewById(R.id.btDelete);
            holder.llExpressNumber = (LinearLayout) convertView.findViewById(R.id.ExpressNumberLayout);
            holder.tvContact = (TextView) convertView.findViewById(R.id.tvContact);
            convertView.setTag(holder);    //绑定viewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();      //取出holder对象
        }
        final CourierListItemBean item = (CourierListItemBean) getItem(position);
        /*设置textview显示的内容，即是存在动态数组的数据*/
        tag = item.getTag();
        expressStatusID = item.getExpressStatusID();
        holder.tvServiceNumber.setText(item.getLogisticsID());
        holder.tvExpressCompany.setText(item.getExpressName());
        holder.tvExpressNum.setText(item.getExpressNum());
        holder.tvUserName.setText(item.getUserName());
        holder.btContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RongIM.getInstance() != null) {
                    RongIM.getInstance().startPrivateChat(context, "1", "客服");
                }
            }
        });
        holder.tvContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RongIM.getInstance() != null) {
                    RongIM.getInstance().startPrivateChat(context, "1", "客服");
                }
            }
        });
        if (tag.equals("1")) {

            if (expressStatusID.equals("0")) {
                holder.btPay.setText(R.string.pay);
                holder.btPay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PayAlertDialog payAlertDialog = new PayAlertDialog(context,item.getExpressID(), new onPaySuccess() {
                            @Override
                            public void initList() {
                                onPaySuccess.initList();
                            }
                        });
                        item.setPrice(payAlertDialog.showDialog());
                    }
                });
            }else if (expressStatusID.equals("1")){
                holder.btPay.setText(R.string.write_number);
                holder.btPay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent writeNum = new Intent();
                        writeNum.putExtra("from","0");
                        writeNum.putExtra("expressID",item.getExpressID());
                        writeNum.setClass(context, WriteNumberActivity.class);
                        ((Activity)context).startActivityForResult(writeNum, Constant.WRITENUMBER);
                    }
                });
            }else if (expressStatusID.equals("2")){
                holder.btPay.setVisibility(View.GONE);
            }
        }else if (tag.equals("0")){
            holder.llExpressNumber.setVisibility(View.GONE);
            if (expressStatusID.equals("0")) {
                holder.btPay.setText(R.string.pay);
                holder.btPay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PayAlertDialog payAlertDialog = new PayAlertDialog(context,item.getExpressID(), new onPaySuccess() {
                            @Override
                            public void initList() {
                                onPaySuccess.initList();
                            }
                        });
                        item.setPrice(payAlertDialog.showDialog());

                    }
                });
            }else if (expressStatusID.equals("1")){
                holder.btPay.setVisibility(View.GONE);
            }
        }


        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CourierJson courierJson = new CourierJson();
                courierJson.courierJson(item.getExpressID(), item.getTag(), new HttpSuccess() {
                    @Override
                    public void onfail() {

                    }

                    @Override
                    public void onsuccess(MerchandiseInfo info) {

                    }
                });
                mitem.remove(position);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    /*存放控件*/
    public class ViewHolder {
        public TextView tvServiceNumber;
        public TextView tvExpressCompany;
        public TextView tvExpressNum;
        public TextView tvUserName;
        public Button btContact;
        public Button btPay;
        public Button btDelete;
        public LinearLayout llExpressNumber;
       public TextView tvContact;
    }
}