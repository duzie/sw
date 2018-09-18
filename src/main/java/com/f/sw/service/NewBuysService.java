package com.f.sw.service;

import com.f.sw.BUIPage;
import com.f.sw.entity.NewBuys;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface NewBuysService {
  Page<NewBuys> find(BUIPage page);

  void add(NewBuys newBuys);

  void delete(long id);

  Map<String ,String> findSwTimer();

  void saveSwtimer(String timer_price, String timer_price1, String timer_dis, String timer_dis_price, String timer_buy_count, String timer_seconds);
}
