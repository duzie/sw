package com.f.sw.service;

import com.f.sw.BUIPage;
import com.f.sw.entity.NewBuys;
import com.f.sw.entity.Sw;
import com.f.sw.repository.NewBuysRepository;
import com.f.sw.repository.SwRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NewBuysServiceImpl implements NewBuysService {

  @Autowired
  NewBuysRepository newBuysRepository;

  @Autowired
  SwRepository swRepository;

  @Override
  public Page<NewBuys> find(BUIPage page) {
    Sort sort = new Sort(Sort.Direction.DESC, "createDate");
    page.setSort(sort);
    return newBuysRepository.findAll(page.getPageable());
  }

  @Override
  public void add(NewBuys newBuys) {
    newBuys.setCreateDate(new Date());
    newBuysRepository.save(newBuys);
  }

  @Override
  public void delete(long id) {
    newBuysRepository.deleteById(id);
  }

  @Override
  public Map<String, String> findSwTimer() {
    List<Sw> list = swRepository.findByNameStartsWith("timer");
    return list.stream().collect(Collectors.toMap(Sw::getName, a -> a.getValue()));
  }

  @Transactional
  @Override
  public void saveSwtimer(String timer_price, String timer_price1, String timer_dis, String timer_dis_price, String timer_buy_count, String timer_seconds) {
    swRepository.deleteByNameStartingWith("timer");
    swRepository.save(Sw.builder().name("timer_price").value(timer_price).build());
    swRepository.save(Sw.builder().name("timer_price1").value(timer_price1).build());
    swRepository.save(Sw.builder().name("timer_dis").value(timer_dis).build());
    swRepository.save(Sw.builder().name("timer_dis_price").value(timer_dis_price).build());
    swRepository.save(Sw.builder().name("timer_buy_count").value(timer_buy_count).build());
    swRepository.save(Sw.builder().name("timer_seconds").value(timer_seconds).build());
  }

  @PostConstruct
  void init() {
    if (newBuysRepository.count() == 0) {
      List<NewBuys> list = new ArrayList<>();
      for (int i = 0; i < 30; i++) {
        list.add(NewBuys.builder().loc("广东").name("张*[13472***322]").time("3分钟前").descr("套餐三/1494元：怀天下18瓶（买九瓶送九瓶）").build());
      }
      newBuysRepository.saveAll(list);
    }

    if (swRepository.count() == 0) {
      swRepository.save(Sw.builder().name("timer_price").value("388").build());
      swRepository.save(Sw.builder().name("timer_price1").value("2094").build());
      swRepository.save(Sw.builder().name("timer_dis").value("1.9折").build());
      swRepository.save(Sw.builder().name("timer_dis_price").value("1706").build());
      swRepository.save(Sw.builder().name("timer_buy_count").value("21648").build());
      swRepository.save(Sw.builder().name("timer_seconds").value("8332").build());
    }

  }
}
