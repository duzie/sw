package com.f.sw.service;

import com.f.sw.BUIPage;
import com.f.sw.entity.GoodsOrder;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface GoodsOrderService {

    void save(GoodsOrder goodsOrder);

    Optional<GoodsOrder> findById(int id);

    Page<GoodsOrder> find(BUIPage page);

    void updateOrderNo(GoodsOrder goodsOrder);
    void updatePayOrderNo(GoodsOrder goodsOrder);
}
