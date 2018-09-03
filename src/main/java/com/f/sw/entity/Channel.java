package com.f.sw.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
/**
 * 渠道配置
 */
@Table(name = "S_CHANNEL")
public class Channel {

    @Id
    Integer id;
    String name;//渠道名
    String host;//渠道域名
    String nm;//渠道简写

}
