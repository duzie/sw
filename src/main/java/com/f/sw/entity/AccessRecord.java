package com.f.sw.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
/**
 * 访问记录表
 */
@Table(name = "S_ACCESS_RECORD")
public class AccessRecord {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String prefix;//前缀
  private String ip;//
  private String location;//地理位置
  @Lob
  private String referrer;//来源
  private String host;//来源域名
  private String channel;//渠道
  private Date openDate;//页面打开时间
  private Date closeDate;//页面关闭时间
  private Boolean isPulldown;//是否下拉到底
  private Boolean isMobile;//是否是移动端
  private String deviceType;//设置类型

  public String getSerialNO() {
    return prefix + String.format("%08d", id);
  }
}
