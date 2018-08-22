package com.f.sw.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.f.sw.BUIPage;
import com.f.sw.IpUtil;
import com.f.sw.RequestUtil;
import com.f.sw.entity.*;
import com.f.sw.goodsapi.Goods;
import com.f.sw.goodsapi.GoodsApi;
import com.f.sw.goodsapi.Response;
import com.f.sw.service.AccessRecordService;
import com.f.sw.service.AreaService;
import com.f.sw.service.GoodsOrderService;
import com.f.sw.service.WebPageService;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Controller
public class IndexController {

    @Autowired
    AccessRecordService accessRecordService;

    @Autowired
    WebPageService webPageService;

    @Autowired
    AreaService areaService;

    @Autowired
    GoodsOrderService goodsOrderService;


    public static Map<String, WebPage> pageMap = new HashMap<>();

    List<Channel> channelList = new ArrayList<>();

    @RequestMapping("")
    public String index(HttpServletRequest request, Model model) throws Exception {

        AccessRecord ar = new AccessRecord();
        ar.setIp(IpUtil.getIpAdrress(request));
        ar.setReferrer(request.getHeader("referer"));
        URL url = new URL(request.getRequestURL().toString());
        String host = url.getHost();
        ar.setHost(host);

        String s = DateFormatUtils.format(new Date(), "yyyyMMdd");
        if (StringUtils.isNoneBlank(ar.getReferrer())) {
            Optional<Channel> otl = channelList.stream().filter(e -> ar.getReferrer().indexOf(e.getHost()) > 0).findFirst();
            if (otl.isPresent()) {
                Channel channel = otl.get();
                ar.setChannel(channel.getName());
                ar.setPrefix(channel.getNm() + s);
            }
        }
        if (StringUtils.isBlank(ar.getChannel())) {
            ar.setChannel("未知");
            ar.setPrefix("NO" + s);
        }


        ar.setOpenDate(new Date());
        ar.setCloseDate(DateUtils.addMinutes(new Date(), 1));
        ar.setIsPulldown(false);
        ar.setIsMobile(RequestUtil.JudgeIsMoblie(request));
        String ua = request.getHeader("User-Agent");

        UserAgent userAgent = UserAgent.parseUserAgentString(ua);
        Browser browser = userAgent.getBrowser();
        OperatingSystem os = userAgent.getOperatingSystem();
        String system = os.getName();
        String browserName = browser.getName();
        ar.setDeviceType(system + " " + browserName);
        accessRecordService.save(ar);

        log.debug(host);
        WebPage wp = pageMap.get(host);
        if (wp == null) wp = pageMap.values().iterator().next();
        log.debug(wp.toString());
        model.addAttribute("wp", wp);
        model.addAttribute("serialNO", ar.getSerialNO());
        model.addAttribute("province", areaService.findProvince());

        List<Goods> list = GoodsApi.getGoods();
        List<GoodsIgnore> ignore = accessRecordService.findGoodsIgnore();
        List<Goods> goodsList = list.stream().filter(
                goods -> !ignore.stream().filter(
                        ig -> goods.getSku().equals(ig.getSku())
                ).findFirst().isPresent()
        ).collect(Collectors.toList());

        model.addAttribute("goodsList", goodsList);
        return "index";
    }

    @PostMapping("order")
    @ResponseBody
    public Map<String, Object> saveOrder(GoodsOrder goodsOrder, double item_price) throws IOException {

        saveOperation(OperationRecord.builder().operation("下单").vistorId(goodsOrder.getVistorId()).build());

        goodsOrder.setProvince(goodsOrder.getProvince().split("\\|")[1]);
        goodsOrder.setCity(goodsOrder.getCity().split("\\|")[1]);
        goodsOrder.setArea(goodsOrder.getArea().split("\\|")[1]);
        goodsOrder.setStreet(goodsOrder.getStreet().split("\\|")[1]);
        goodsOrder.setCreateDate(new Date());
        goodsOrder.setAmount(new BigDecimal(item_price).multiply(new BigDecimal(goodsOrder.getQuantity())));
        if (!"1".equals(goodsOrder.getPayment()))
            goodsOrder.setPayAmount(goodsOrder.getAmount().multiply(new BigDecimal(0.95)));
        else
            goodsOrder.setPayAmount(goodsOrder.getAmount());
        goodsOrder.setState(0);
        goodsOrderService.save(goodsOrder);

        Map<String, Object> map = new HashMap<>();

        Response rp = GoodsApi.createOrder(goodsOrder);

        if (!rp.isExcuteReusult()) {
            map.put("success", false);
            return map;
        }
        goodsOrder.setOrderNo(rp.getOrderId());
        goodsOrderService.updateOrder(goodsOrder);
        map.put("success", true);
        return map;

    }

    @PostMapping("operation")
    @ResponseBody
    public void saveOperation(OperationRecord operationRecord) {
        accessRecordService.saveOperationRecord(operationRecord);
    }

    @PostMapping("scrollBottom")
    @ResponseBody
    public void scrollBottom(String vistorId) {
        accessRecordService.pulldown(vistorId);
    }

    @GetMapping("channel/init")
    public void loadChannel() {
        Iterable<Channel> iter = webPageService.findChannel();
        channelList.clear();
        iter.forEach(single -> {
            channelList.add(single);
        });
    }

    @RequestMapping("region/{id}")
    @ResponseBody
    public Object region(@PathVariable int id) {
        return areaService.findByParentId(id);
    }

    @RequestMapping("pay")
    @ResponseBody
    public Object testpay(HttpServletRequest request) throws WxPayException {
        GoodsOrder goodsOrder = new GoodsOrder();
        goodsOrder.setOrderNo("1");
        goodsOrder.setPayAmount(new BigDecimal(0.01));
        goodsOrder.setGoodsName("iphone");
        String getContextPath = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + getContextPath + "/";
        return wxUnifiedOrder(goodsOrder, request, basePath);
//        return alipay(goodsOrder, basePath, basePath);
    }

    @Autowired
    private WxPayService wxService;

    public WxPayUnifiedOrderResult wxUnifiedOrder(GoodsOrder goodsOrder, HttpServletRequest request, String notifyUrl) throws WxPayException {
        WxPayUnifiedOrderRequest orderRequest = new WxPayUnifiedOrderRequest();
        orderRequest.setBody(goodsOrder.getGoodsName());
        orderRequest.setOutTradeNo(goodsOrder.getOrderNo());
        orderRequest.setTotalFee(goodsOrder.getPayAmount().multiply(new BigDecimal(100)).intValue());
        orderRequest.setSpbillCreateIp(IpUtil.getIpAdrress(request));
        orderRequest.setTradeType(WxPayConstants.TradeType.MWEB);
        orderRequest.setNotifyUrl(notifyUrl);

        return this.wxService.unifiedOrder(orderRequest);
    }


    @Value("${web.alipay.appid}")
    String ALIPAY_APP_ID;
    @Value("${web.alipay.privatekey}")
    String ALIPAY_APP_PRIVATE_KEY;
    @Value("${web.alipay.publickey}")
    String ALIPAY_PUBLIC_KEY;
    String ALIPAY_CHARSET = "utf-8";

    public String alipay(GoodsOrder goodsOrder, String returnUrl, String notifyUrl) {
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", ALIPAY_APP_ID, ALIPAY_APP_PRIVATE_KEY, "json", ALIPAY_CHARSET, ALIPAY_PUBLIC_KEY, "RSA2"); //获得初始化的AlipayClient
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();//创建API对应的request
        alipayRequest.setReturnUrl(returnUrl);
        alipayRequest.setNotifyUrl(notifyUrl);//在公共参数中设置回跳和通知地址
        alipayRequest.setBizContent("{" +
                " \"out_trade_no\":\"" + goodsOrder.getOrderNo() + "\"," +
                " \"total_amount\":\"" + goodsOrder.getPayAmount().doubleValue() + "\"," +
                " \"subject\":\"" + goodsOrder.getGoodsName() + "\"," +
                " \"product_code\":\"QUICK_WAP_PAY\"" +
                " }");//填充业务参数
        String form = "";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return form;
    }

    @PostMapping("login")
    @ResponseBody
    public boolean login(SysAccount account, HttpSession session) {
        if (accessRecordService.login(account)) {
            session.setAttribute("account", account);
            return true;
        }
        return false;
    }

    @GetMapping("admin")
    public String admin() {
        return "redirect:/admin/index.html";
    }


    @PostConstruct
    void init() {
        Page<WebPage> page = webPageService.find(BUIPage.builder().pageIndex(0).limit(5).build());
        pageMap = page.getContent().stream().collect(Collectors.toMap(WebPage::getHost, WebPage -> WebPage));
        loadChannel();
    }


}
