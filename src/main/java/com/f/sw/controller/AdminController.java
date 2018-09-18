package com.f.sw.controller;

import com.f.sw.BUIPage;
import com.f.sw.entity.NewBuys;
import com.f.sw.entity.SysAccount;
import com.f.sw.entity.WebPage;
import com.f.sw.service.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@Controller
@RequestMapping("a")
public class AdminController {

  @Autowired
  AccessRecordService accessRecordService;

  @Autowired
  WebPageService webPageService;

  @Autowired
  AreaService areaService;

  @Autowired
  GoodsOrderService goodsOrderService;

  @Autowired
  NewBuysService newBuysService;

  @PostMapping("updatePwd")
  @ResponseBody
  public boolean updatePwd(SysAccount account, HttpSession session) {
    SysAccount a = (SysAccount) session.getAttribute("account");
    account.setAccount(a.getAccount());
    accessRecordService.updateAccountPwd(account);
    return true;
  }

  @RequestMapping("goodsOrder/list")
  @ResponseBody
  public Object orderList(BUIPage page) {
    return goodsOrderService.find(page);
  }

  @RequestMapping("accessRecord/list")
  @ResponseBody
  public Object accessRecordList(BUIPage page) {
    return accessRecordService.find(page);
  }

  @RequestMapping("webPage/list")
  @ResponseBody
  public Object webPageList(BUIPage page) {
    return webPageService.find(page);
  }

  @RequestMapping("webPage/{id}")
  @ResponseBody
  public Object webPageList(@PathVariable int id) {
    return webPageService.find(id);
  }

  @PostMapping("webPage")
  @ResponseBody
  public Object saveWebPage(WebPage webPage) {
    webPageService.save(webPage);
    Map<String, Object> map = new HashMap<>();
    Page<WebPage> page = webPageService.find(BUIPage.builder().pageIndex(0).limit(5).build());
    IndexController.pageMap = page.getContent().stream().collect(Collectors.toMap(WebPage::getHost, WebPage -> WebPage));
    map.put("success", true);
    return map;
  }

  @RequestMapping("operation/list")
  @ResponseBody
  public Object operationList(BUIPage page) {
    return accessRecordService.findOperationRecord(page);
  }

  @GetMapping("channel/list")
  @ResponseBody
  public Object channelList() {
    return webPageService.findChannel();
  }


  @RequestMapping("newBuys/list")
  @ResponseBody
  public Object newBuysList(BUIPage page) {
    return newBuysService.find(page);
  }

  @RequestMapping("newBuys/add")
  @ResponseBody
  public boolean addNewBuys(NewBuys newBuys) {
    newBuysService.add(newBuys);
    return true;
  }

  @RequestMapping("newBuys/delete")
  @ResponseBody
  public boolean newBuysList(long id) {
    newBuysService.delete(id);
    return true;
  }


  @GetMapping("sw/timer")
  @ResponseBody
  public Map<String, String> timer() {
    return newBuysService.findSwTimer();
  }

  @PostMapping("sw/timer")
  @ResponseBody
  public boolean savetimer(String timer_price,String timer_price1,String timer_dis,String timer_dis_price,String timer_buy_count,String timer_seconds) {
    newBuysService.saveSwtimer(timer_price, timer_price1, timer_dis, timer_dis_price, timer_buy_count, timer_seconds);
    return true;
  }
}
