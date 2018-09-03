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
/**
 * 操作记录
 */
@Table(name = "S_GOODS_ORDER")
public class GoodsOrder {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String vistorId;//访客ID
  private Date createDate;//创建时间
  private String orderNo;//接口订单ID
  private String sysOrderNo;//系统订单ID
  private BigDecimal amount;//金额
  private String sku;//
  private String goodsName;//商品名
  private Integer quantity;//数量
  private String name;//收货人
  private String mobile;//收货人手机
  private String province;//省
  private String city;//市
  private String area;//区
  private String street;//街道
  private String address;//地址
  private String remark;//备注
  private String payment;//支付方式
  private String payOrderNo;//支付订单号
  private BigDecimal payAmount;//支付金额
  private Integer state;//0下单，1支付
  private Date payDate;//支付时间

  private String openId;//公众号ID

  public String getSysOrderNo() {
    return DateFormatUtils.format(createDate, "yyMMdd") + String.format("%09d", id);
  }
}
