package com.f.sw.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
/**
 * 商品过滤
 */
@Table(name = "S_GOODS_IGNORE")
public class GoodsIgnore {
  @Id
  Integer id;
  String sku;//接口API中的SKU
  BigDecimal discount;//优惠
}
