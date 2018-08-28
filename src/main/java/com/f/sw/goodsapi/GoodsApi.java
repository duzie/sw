package com.f.sw.goodsapi;

import com.f.sw.entity.GoodsOrder;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;

import java.util.*;

@Log4j2
public class GoodsApi {

    private static final String CB_GOODS_QUERY = "http://xxjrwinefy1.f3322.net:9000/api/GoodsInfo/index";
    private static final String ORDER_RECORD = "http://xxjrwinefy1.f3322.net:9000/api/OrderRecord/index";
    private static String accesskey = "XXJR_Drink";
    private static String accessSecret = "06a0553bd270c8b446481885331b56c3";
    static ObjectMapper om = new ObjectMapper();


    public static List<Goods> getGoods() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("Timestamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        map.put("Accesskey", accesskey);
        map.put("IsCBGoods", 0);

        String str = accesskey + "IsCBGoods" + 0 + "Timestamp" + map.get("Timestamp") + accessSecret;
        String token = DigestUtils.sha1Hex(str);
        map.put("Token", token.toUpperCase());
        log.debug(om.writeValueAsString(map));
        String s = Request.Post(CB_GOODS_QUERY)
                .setHeader("Content-Type", "application/json")
                .bodyString(om.writeValueAsString(map), ContentType.APPLICATION_JSON)
                .execute().returnContent().asString();
        log.debug(s);
        JsonNode node = om.readTree(s);
        String goodsStr = node.get("GoodsInfo").asText();
        JavaType javaType = getCollectionType(ArrayList.class, Goods.class);
        return (List<Goods>) om.readValue(goodsStr, javaType);

    }

    public static Response createOrder(GoodsOrder o) {
        Map<String, String> map = new HashMap<>();
        map.put("Timestamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        map.put("OuterCode", o.getSysOrderNo());
        map.put("Sku", o.getSku());
        map.put("Product", o.getGoodsName());
        map.put("Qty", o.getQuantity().toString());
        map.put("TotalMoney", Double.valueOf(o.getPayAmount().doubleValue()).toString());
        map.put("UserName", o.getName());
        map.put("Phone", o.getMobile());
        map.put("Province", o.getProvince());
        map.put("City", o.getCity());
        map.put("Zone", o.getArea());
        map.put("Street", o.getStreet());
        map.put("DetailAddr", o.getAddress());
        map.put("PayMethod", StringUtils.equals("1", o.getPayment()) ? "货到付款" : "在线支付");

        List<String> plist = new ArrayList<>(map.keySet());
        Collections.sort(plist);
        StringBuffer sb = new StringBuffer();
        sb.append(accesskey);
        for (String s : plist) {
            sb.append(s);
            sb.append(map.get(s));
        }
        sb.append(accessSecret);
        log.debug(sb.toString());
        String token = DigestUtils.sha1Hex(sb.toString());
        map.put("Token", token.toUpperCase());
        map.put("Accesskey", accesskey);

        try {
            log.debug(om.writeValueAsString(map));
            String s = Request.Post(ORDER_RECORD)
                    .setHeader("Content-Type", "application/json")
                    .bodyString(om.writeValueAsString(map), ContentType.APPLICATION_JSON)
                    .execute().returnContent().asString();
            log.debug(s);
            return om.readValue(s, Response.class);
        } catch (Exception e) {

        }
        return Response.builder().ExcuteReusult(false).expMsg("接口调用失败").build();

    }

    private static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return om.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    public static void main(String[] args) throws Exception {
        System.out.println(getGoods().size());
    }
}

