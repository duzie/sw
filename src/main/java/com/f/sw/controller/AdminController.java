package com.f.sw.controller;

import com.f.sw.BUIPage;
import com.f.sw.entity.SysAccount;
import com.f.sw.entity.WebPage;
import com.f.sw.service.AccessRecordService;
import com.f.sw.service.AreaService;
import com.f.sw.service.GoodsOrderService;
import com.f.sw.service.WebPageService;
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

    @PostMapping("updatePwd")
    @ResponseBody
    public boolean updatePwd(SysAccount account, HttpSession session) {
        SysAccount a=(SysAccount)  session.getAttribute("account");
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
}
