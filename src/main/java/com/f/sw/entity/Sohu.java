package com.f.sw.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "S_SOHU")
public class Sohu {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  String atype;
  String idfa;
  String imei;
  String gid;
  String clickts;
  String clickid;
  String callback;
  Long rTimestamp;
  Date createDate;

}
