package com.f.sw.repository;

import com.f.sw.entity.NewBuys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NewBuysRepository extends JpaRepository<NewBuys, Long>, JpaSpecificationExecutor<NewBuys> {
}

