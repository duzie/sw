package com.f.sw.service;

import com.f.sw.BUIPage;
import com.f.sw.entity.GoodsOrder;
import com.f.sw.repository.GoodsOrderRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

@Service
public class GoodsOrderServiceImpl implements GoodsOrderService {

    @Autowired
    GoodsOrderRepository goodsOrderRepository;

    @Override
    public Optional<GoodsOrder> findById(int id) {
        return goodsOrderRepository.findById(id);
    }

    @Override
    public void save(GoodsOrder goodsOrder) {
        goodsOrderRepository.save(goodsOrder);
    }

    @Override
    public Page<GoodsOrder> find(BUIPage page) {
        Sort sort = new Sort(Sort.Direction.DESC, "createDate");
        page.setSort(sort);
        return goodsOrderRepository.findAll(new Specification<GoodsOrder>() {
            @Override
            public Predicate toPredicate(Root<GoodsOrder> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();
                Map<String, String> param = page.getParam();
                String orderNo = param.get("orderNo");
                if (StringUtils.isNotBlank(orderNo)) {
                    list.add(cb.equal(root.get("orderNo"), orderNo));
                }

                String name = param.get("name");
                if (StringUtils.isNotBlank(name)) {
                    list.add(cb.equal(root.get("name"), name));
                }

                String mobile = param.get("mobile");
                if (StringUtils.isNotBlank(mobile)) {
                    list.add(cb.equal(root.get("mobile"), mobile));
                }

                String payment = param.get("payment");
                if (StringUtils.isNotBlank(payment)) {
                    if (payment.equals("0"))
                        list.add(cb.or(cb.equal(root.get("payment"), "4"), cb.equal(root.get("payment"), "5")));
                    else
                        list.add(cb.equal(root.get("payment"), "1"));
                }
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        }, page.getPageable());
    }

    @Override
    public void updateOrderNoAndPayOrderNo(GoodsOrder goodsOrder) {
        Optional<GoodsOrder> ot = goodsOrderRepository.findById(goodsOrder.getId());
        if (ot.isPresent()) {
            GoodsOrder o = ot.get();
            o.setOrderNo(goodsOrder.getOrderNo());
            o.setPayOrderNo(goodsOrder.getPayOrderNo());
            if (!StringUtils.equals(o.getPayment(), "1")){
                goodsOrder.setState(1);
                goodsOrder.setPayDate(new Date());
            }

            goodsOrderRepository.save(o);
        }
    }


}
