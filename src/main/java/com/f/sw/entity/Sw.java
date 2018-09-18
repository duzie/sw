package com.f.sw.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "S_SW")
public class Sw {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  String name;
  String value;

}
