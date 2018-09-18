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
@Entity
@Builder
@Table(name = "S_NEW_BUYS")
public class NewBuys {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  String loc;
  String name;
  String time;
  String descr;
  Date createDate;
}
