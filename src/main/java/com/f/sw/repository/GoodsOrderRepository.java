package com.f.sw.repository;

import com.f.sw.entity.GoodsOrder;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface GoodsOrderRepository extends PagingAndSortingRepository<GoodsOrder, Integer>, JpaSpecificationExecutor<GoodsOrder> {

    GoodsOrder findByOrderNo(String orderNo);
}
