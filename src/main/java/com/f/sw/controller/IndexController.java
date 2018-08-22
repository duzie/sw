package com.f.sw.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
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
    public Map<String, Object> saveOrder(GoodsOrder goodsOrder, double item_price, HttpServletRequest request) throws IOException {

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

        String getContextPath = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + getContextPath;
        if (goodsOrder.getPayment().equals("4")) {
            goodsOrder.setPayAmount(new BigDecimal(0.01));
            String form = alipay(goodsOrder, basePath + "/alipay");
            map.put("aliform", form);
        }
        goodsOrderService.updateOrderNo(goodsOrder);

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
        String basePath = request.getScheme() + "://" + request.getServerName() + getContextPath + "/alipay";
//        return wxUnifiedOrder(goodsOrder, request, basePath);
        return alipay(goodsOrder, basePath);
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

    public String alipay(GoodsOrder goodsOrder, String basePath) {
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", ALIPAY_APP_ID, ALIPAY_APP_PRIVATE_KEY, "json", ALIPAY_CHARSET, ALIPAY_PUBLIC_KEY, "RSA2"); //获得初始化的AlipayClient

        String returnUrl = basePath + "/order/" + goodsOrder.getOrderNo();
        String notifyUrl = basePath + "/notify";

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
            return alipayClient.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }


    @RequestMapping("alipay/notify")
    @ResponseBody
    public String alinotify(HttpServletRequest request) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }
        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        //商户订单号

        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
        //支付宝交易号

        String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

        //交易状态
        String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");

        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
        //计算得出通知验证结果
        //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
        boolean verify_result = AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, ALIPAY_CHARSET, "RSA2");

        if (verify_result) {//验证成功
            //////////////////////////////////////////////////////////////////////////////////////////
            //请在这里加上商户的业务逻辑程序代码

            //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
            GoodsOrder goodsOrder = new GoodsOrder();
            goodsOrder.setPayOrderNo(trade_no);
            goodsOrder.setOrderNo(out_trade_no);
            goodsOrderService.updatePayOrderNo(goodsOrder);

            if (trade_status.equals("TRADE_FINISHED")) {
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //如果签约的是可退款协议，退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
                //如果没有签约可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
            } else if (trade_status.equals("TRADE_SUCCESS")) {
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //如果签约的是可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
            }

            //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
            return "success";    //请不要修改或删除

            //////////////////////////////////////////////////////////////////////////////////////////
        } else {//验证失败
            return "fail";
        }
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
