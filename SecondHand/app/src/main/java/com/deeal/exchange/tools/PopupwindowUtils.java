package com.deeal.exchange.tools;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.PopupWindow;

import com.deeal.exchange.R;

/**
 * Created by Administrator on 2015/7/30.
 */
public class PopupwindowUtils {
    public static void showWindowCenter(Context context,PopupWindow popupWindow,View view){
        if(popupWindow == null){
            popupWindow = new PopupWindow(context);
            popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setContentView(view);
        }
        view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.abc_fade_in));
        popupWindow.showAtLocation(view, Gravity.CENTER,0,0);
        popupWindow.update();
    }
}
