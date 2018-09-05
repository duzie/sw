package com.f.sw.repository;

import com.f.sw.entity.Sohu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SohuRepository extends JpaRepository<Sohu, Long>, JpaSpecificationExecutor<Sohu> {
}

