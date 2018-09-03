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
 * 地区
 */
@Table(name = "S_AREA")
public class Area {

    @Id
    private Integer id;
    private String areaName; // 地区名称
    private int isDel;
    private Integer parentId;
    private String cityCode;
    private String adCode;
    private String center;
    private String level;
    private String historyId;

}
