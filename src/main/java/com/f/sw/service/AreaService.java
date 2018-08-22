package com.f.sw.service;

import com.f.sw.entity.Area;

import java.util.List;

public interface AreaService {
    List<Area> findProvince();

    List<Area> findByParentId(Integer id);
}
