package com.f.sw.repository;

import com.f.sw.entity.Area;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AreaRepository  extends CrudRepository<Area,Integer> {

    List<Area> findAllByLevelOrderByAdCode(String level);
    List<Area> findAllByParentIdOrderByAdCode(Integer id);
}
