package com.deeal.exchange.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.deeal.exchange.Bean.MyBoughtItemBean;
import com.deeal.exchange.R;
import com.deeal.exchange.activity.LogisticsInfoActivity;
import com.deeal.exchange.alipay.Pay;
import com.deeal.exchange.application.Path;
import com.deeal.exchange.model.MerchandiseInfo;
import com.deeal.exchange.tools.BitmapHelper;
import com.deeal.exchange.tools.HttpSuccess;
import com.deeal.exchange.tools.MyBoughtJson;
import com.deeal.exchange.tools.PathUtils;
import com.deeal.exchange.tools.RongIMUtils;
import com.deeal.exchange.tools.onPayResult;
import com.deeal.exchange.tools.onPaySuccess;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;

/**
 * Created by Sunqiyong on 2015/7/12.
 */
public class MyBoughtListAdapter extends BaseAdapter  {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<MyBoughtItemBean> myBoughtItemBeans;
    private BitmapUtils bitmapUtils;
    onPaySuccess onPaySuccess;

    public MyBoughtListAdapter(Context context, ArrayList<MyBoughtItemBean> myBoughtItemBeans, onPaySuccess onPaySuccess) {
        this.context = context;
        this.myBoughtItemBeans = new ArrayList<MyBoughtItemBean>();
        this.myBoughtItemBeans = myBoughtItemBeans;
        bitmapUtils = BitmapHelper.getBitmapUtils(context.getApplicationContext());
        inflater = LayoutInflater.from(context);
        this.onPaySuccess = onPaySuccess;
    }

    @Override
    public int getCount() {
        return myBoughtItemBeans.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        MyBoughtViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.my_bought_item, null);
            holder = new MyBoughtViewHolder();
            holder.img = (ImageView) convertView.findViewById(R.id.imv_my_bought_item);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_goods_name_my_bought_item);
            holder.tv_cureent_price = (TextView) convertView.findViewById(R.id.tv_goods_money);
            holder.tv_old_price = (TextView) convertView.findViewById(R.id.tv_old_price_goods);
            holder.tv_condiction = (TextView) convertView.findViewById(R.id.tv_goods_condition);
            holder.bt_1 = (Button) convertView.findViewById(R.id.bt_my_bought1);
            holder.bt_2 = (Button) convertView.findViewById(R.id.bt_my_bought2);
            holder.llConnect = (LinearLayout) convertView.findViewById(R.id.llConnect2Seller);
            holder.connect1 = (ImageView) convertView.findViewById(R.id.imv_contace_customer);
            holder.connect2 = (TextView) convertView.findViewById(R.id.tv_contace_customer);
            convertView.setTag(holder);
        } else {
            holder = (MyBoughtViewHolder) convertView.getTag();
        }
        final MyBoughtItemBean itemBean = myBoughtItemBeans.get(position);
        bitmapUtils.display(holder.img, PathUtils.createMerchandiseImageUrl(itemBean.getMerchandiseID(),itemBean.getImv_goods()));
        holder.tv_title.setText(itemBean.getName_goods());
        holder.tv_cureent_price.setText("￥" + itemBean.getPrice_goods());
        holder.tv_old_price.setText("原价:￥" + itemBean.getOld_price());
        holder.llConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RongIM.getInstance().startPrivateChat(context,itemBean.getBuyerID(),"买家");
            }
        });
        if (itemBean.getCondition_goods().equals("1")) {
            holder.tv_condiction.setText("交易等待");
            holder.bt_1.setVisibility(View.VISIBLE);
            holder.bt_1.setText("立即付款");
            holder.bt_2.setText("取消订单");
            holder.bt_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Pay pay = new Pay(context, new onPayResult() {
                        @Override
                        public void onSuccess() {
                            Log.e("支付成功","支付成功了   这里进去了");
                            MyBoughtJson updateOrderStatus = new MyBoughtJson();
                            updateOrderStatus.updateOrderStatus("2", itemBean.getOrderID(),new HttpSuccess() {
                                @Override
                                public void onfail() {

                                }

                                @Override
                                public void onsuccess(MerchandiseInfo info) {
                                    onPaySuccess.initList();
                                }
                            });
                        }

                        @Override
                        public void onFailure() {
                            Toast.makeText(context, "支付失败", Toast.LENGTH_LONG).show();
                        }
                    });
                    pay.pay();
                }
            });
            holder.bt_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyBoughtJson cancelOrder = new MyBoughtJson();
                    cancelOrder.cancelOrder(itemBean.getOrderID(), itemBean.getContact_seller(), new HttpSuccess() {
                        @Override
                        public void onfail() {
                            Toast.makeText(context, "取消订单失败", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onsuccess(MerchandiseInfo info) {
                            myBoughtItemBeans.remove(position);
                            notifyDataSetChanged();
                            Toast.makeText(context, "取消成功", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
        } else if (itemBean.getCondition_goods().equals("2")) {
            holder.tv_condiction.setText("交易进行");
            holder.bt_1.setVisibility(View.INVISIBLE);
            holder.bt_2.setText("提醒发货");
            holder.bt_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyBoughtJson remindDelivery = new MyBoughtJson();
                    remindDelivery.remindDelivery(itemBean.getBuyerID(),
                            itemBean.getSellerID(), new HttpSuccess() {
                                @Override
                                public void onfail() {

                                }

                                @Override
                                public void onsuccess(MerchandiseInfo info) {
                                    Toast.makeText(context, "提醒成功", Toast.LENGTH_SHORT).show();
                                }
                            });

                }
            });
        } else if (itemBean.getCondition_goods().equals("3")) {
            holder.tv_condiction.setText("交易进行");
            holder.bt_1.setVisibility(View.VISIBLE);
            holder.bt_1.setText("查看物流");
            holder.bt_2.setText("确认收货");
            holder.bt_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent logistics = new Intent();
                    logistics.putExtra("orderID",itemBean.getOrderID());
                    logistics.setClass(context, LogisticsInfoActivity.class);
                    context.startActivity(logistics);
                }
            });

            final MyBoughtViewHolder finalHolder = holder;
            final MyBoughtViewHolder finalHolder1 = holder;
            final MyBoughtViewHolder finalHolder2 = holder;
            holder.bt_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    MyBoughtJson confirm = new MyBoughtJson();
                    confirm.updateOrderStatus("4",itemBean.getOrderID(), new HttpSuccess() {
                        @Override
                        public void onfail() {
                            Toast.makeText(context, "确认失败", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onsuccess(MerchandiseInfo info) {
                            finalHolder1.bt_1.setVisibility(View.GONE);
                            finalHolder1.bt_2.setVisibility(View.GONE);
                            Toast.makeText(context, "确认成功", Toast.LENGTH_LONG).show();
                            finalHolder2.tv_condiction.setText("交易成功");
                        }
                    });
                }
            });
        } else if (itemBean.getCondition_goods().equals("4")) {
            holder.tv_condiction.setText("交易成功");
            holder.bt_1.setVisibility(View.INVISIBLE);
            holder.bt_2.setText("删除");
            holder.bt_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyBoughtJson deleteOrder = new MyBoughtJson();
                    deleteOrder.deleteOrder(itemBean.getOrderID(), new HttpSuccess() {
                        @Override
                        public void onfail() {
                            Toast.makeText(context, "删除失败", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onsuccess(MerchandiseInfo info) {
                            myBoughtItemBeans.remove(position);
                            notifyDataSetChanged();
                            Toast.makeText(context, "删除成功", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
        }

        return convertView;
    }


    class MyBoughtViewHolder {
        ImageView img;
        TextView tv_title;
        TextView tv_cureent_price;
        TextView tv_old_price;
        TextView tv_condiction;
        Button bt_1;
        Button bt_2;
        LinearLayout llConnect;
        ImageView connect1;
        TextView connect2;
    }
    /**
     *  商品交易状态服务器返回的值    activity显示的数据     button1显示数据   button2显示的数据
     *          1                       交易等待                立即付款            取消订单
     *          2                       交易进行（未发货）      （不显示）          提醒发货
     *          3                       交易进行 （发货）        查看物流            确认收货
     *          4                       交易成功                （不显示）           删除
     */
}
