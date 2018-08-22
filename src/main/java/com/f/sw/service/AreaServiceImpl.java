package com.f.sw.service;

import com.f.sw.entity.Area;
import com.f.sw.repository.AreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaServiceImpl implements  AreaService {

    @Autowired
    AreaRepository areaRepository;
    @Override
    public List<Area> findProvince() {
        return areaRepository.findAllByLevelOrderByAdCode("province");
    }
    @Override
    public List<Area> findByParentId(Integer id) {
        return areaRepository.findAllByParentIdOrderByAdCode(id);
    }
}
