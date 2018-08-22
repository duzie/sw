package com.f.sw.service;

import com.f.sw.BUIPage;
import com.f.sw.entity.GoodsOrder;
import org.springframework.data.domain.Page;

public interface GoodsOrderService {

    void save(GoodsOrder goodsOrder);

    Page<GoodsOrder> find(BUIPage page);

    void updateOrderNo(GoodsOrder goodsOrder);
    void updatePayOrderNo(GoodsOrder goodsOrder);
}
