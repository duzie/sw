package com.f.sw.repository;

import com.f.sw.entity.AccessRecord;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AccessRecordRepository extends PagingAndSortingRepository<AccessRecord, Integer> , JpaSpecificationExecutor<AccessRecord> {

}
