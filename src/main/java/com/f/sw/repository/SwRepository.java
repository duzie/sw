package com.f.sw.repository;

import com.f.sw.entity.Sw;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface SwRepository extends JpaRepository<Sw, Long>, JpaSpecificationExecutor<Sw> {

  List<Sw> findByNameStartsWith(String start);

  void deleteByNameStartingWith(String start);
}

