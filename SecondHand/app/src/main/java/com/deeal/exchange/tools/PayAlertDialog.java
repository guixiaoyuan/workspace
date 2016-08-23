package com.deeal.exchange.tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.deeal.exchange.R;
import com.deeal.exchange.alipay.Pay;
import com.deeal.exchange.model.MerchandiseInfo;

/**
 * Created by Administrator on 2015/8/26.
 */
public class PayAlertDialog {
    private String taskID;
    private onPaySuccess onPaySuccess;
    private String price;
    private Context context;
    private String expressID;

    public PayAlertDialog(Context context,String expressID,onPaySuccess onPaySuccess){
        this.context = context;
        this.expressID = expressID;
        this.onPaySuccess = onPaySuccess;
        this.taskID = expressID;
    }
    public  String showDialog(){
        LayoutInflater inflater = LayoutInflater.from(context);
        final View textEntryView = inflater.inflate(
                R.layout.dialoglayout, null);
        final EditText edtInput=(EditText)textEntryView.findViewById(R.id.edtInput);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle("请输入支付金额");
        builder.setView(textEntryView);
        builder.setPositiveButton("确认",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        price = edtInput.getText().toString();
                        Pay takePay = new Pay(context, new onPayResult() {
                            @Override
                            public void onSuccess() {
                                Price mPrice = new Price();
                                mPrice.upPrice(taskID,price);
                                CourierJson setExpressStatus = new CourierJson();
                                setExpressStatus.setExpressStatus(expressID,"1", new HttpSuccess() {
                                    @Override
                                    public void onfail() {
                                        Toast.makeText(context, "支付失败", Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onsuccess(MerchandiseInfo info) {
                                        onPaySuccess.initList();
                                    }
                                });
                            }

                            @Override
                            public void onFailure() {

                            }
                        });
                        takePay.pay();
                    }
                });
        builder.setNegativeButton("取消", null);
        builder.show();
        return price;
    }


}
