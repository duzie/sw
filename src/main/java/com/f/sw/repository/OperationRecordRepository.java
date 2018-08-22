package com.f.sw.repository;

import com.f.sw.entity.OperationRecord;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OperationRecordRepository extends PagingAndSortingRepository<OperationRecord, Integer>, JpaSpecificationExecutor<OperationRecord> {
}
