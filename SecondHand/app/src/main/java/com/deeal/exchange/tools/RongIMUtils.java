package com.deeal.exchange.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.deeal.exchange.activity.ChatActivity;

/**
 * Created by Administrator on 2015/8/4.
 */
public class RongIMUtils {
    public RongIMUtils(){

    }

    /**
     * created by dading to connect to rongIM to chat
     * @param context the present activity
     * @param userID to chat with
     * @param title this activity's title
     * @param merchandiseId
     */
    public static void toChatForBuy(Context context,String userID,String title,String merchandiseId,String jsonData){
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("userID",userID);
        bundle.putString("title",title);
        bundle.putString("merchandiseId",merchandiseId);
        bundle.putString("jsonData",jsonData);
        intent.putExtras(bundle);
        intent.setClass(context, ChatActivity.class);
        ((Activity)context).startActivity(intent);
    }
}
