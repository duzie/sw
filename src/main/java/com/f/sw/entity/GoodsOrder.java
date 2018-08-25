package com.f.sw.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.time.DateFormatUtils;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "S_GOODS_ORDER")
public class GoodsOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String vistorId;
    private Date createDate;
    private String orderNo;
    private String sysOrderNo;
    private BigDecimal amount;
    private String sku;
    private String goodsName;
    private Integer quantity;
    private String name;
    private String mobile;
    private String province;
    private String city;
    private String area;
    private String street;
    private String address;
    private String remark;
    private String payment;
    private String payOrderNo;
    private BigDecimal payAmount;
    private Integer state;//0下单，1支付
    private Date payDate;

    private String openId;

    public String getSysOrderNo() {
        return DateFormatUtils.format(createDate, "yyMMdd") + String.format("%09d", id);
    }
}
