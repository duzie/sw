package com.f.sw.goodsapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Goods {
  @JsonProperty("SKU")
  String sku;
  @JsonProperty("GoodsName")
  String goodsName;
  @JsonProperty("ReferPrice")
  double referPrice;

  double price;
  double discount;
}
