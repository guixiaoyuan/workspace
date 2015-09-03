package gxy.shanbaytest.tools;

import android.content.Context;

import com.lidroid.xutils.BitmapUtils;

import gxy.shanbaytest.R;

/**
 * Created by Administrator on 2015/7/7.
 */
public class BitmapHelper {

    private BitmapHelper() {
    }

    private static BitmapUtils bitmapUtils;

    /**
     * BitmapUtils不是单例的 根据需要重载多个获取实例的方法
     *
     * @param appContext application context
     * @return bitmaputils
     */
    public static BitmapUtils getBitmapUtils(Context appContext) {
        if (bitmapUtils == null) {
            bitmapUtils = new BitmapUtils(appContext);
            bitmapUtils.configDefaultLoadingImage(R.mipmap.loading_bitmap);
            bitmapUtils.configDefaultLoadFailedImage(R.mipmap.load_bitmap_failed);
        }
        return bitmapUtils;
    }
}
