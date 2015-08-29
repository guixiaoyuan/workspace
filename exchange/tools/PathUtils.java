package com.deeal.exchange.tools;

import com.deeal.exchange.application.Path;

/**
 * Created by Administrator on 2015/8/26.
 */
public class PathUtils {
    /**
     * 构建头像路径缩略图
     * @param userid
     * @param imgName
     * @return
     */
    public static String createPortriateUrl(String userid , String imgName){
        return Path.PORTRAITPATH+userid+"/thumbnail_portrait/"+imgName;
    }

    /**
     * 构建头像路径高清图
     * @param userid
     * @param imgName
     * @return
     */
    public static String createPortriateUrlHD(String userid , String imgName){
        return Path.PORTRAITPATH+userid+"/hd_portrait/"+imgName;
    }

    /**
     * 构建商品的图片路径，缩略图
     * @param merchandiseID
     * @param imgName
     * @return
     */
    public static String createMerchandiseImageUrl(String merchandiseID, String imgName){
        return Path.MERCHANDISEIMGPATH+merchandiseID+"/thumbnail/"+imgName;
    }

    public static String createMerchandiseImageUrlHD(String merchandiseID,String imgName){
        return Path.MERCHANDISEIMGPATH+merchandiseID+"/origin/"+imgName;
    }
}
