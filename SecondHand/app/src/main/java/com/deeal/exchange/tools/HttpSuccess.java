package com.deeal.exchange.tools;

import com.deeal.exchange.model.MerchandiseInfo;

/**
 * Created by Administrator on 2015/8/1.
 *
 */
public interface HttpSuccess {
    void onfail();
    /**
     * http请求获取到商品详情
     * @param info
     */
    void onsuccess(MerchandiseInfo info);
}
