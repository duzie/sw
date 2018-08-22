package com.f.sw.service;

import com.f.sw.BUIPage;
import com.f.sw.entity.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AccessRecordService {

    Page<AccessRecord> find(BUIPage page);

    Page<OperationRecord> findOperationRecord(BUIPage page);

    void save(AccessRecord accessRecord);

    void saveOperationRecord(OperationRecord operationRecord);

    List<GoodsIgnore> findGoodsIgnore();

    boolean login(SysAccount account);

    void updateAccountPwd(SysAccount account);

    void pulldown(String vistorId);
}
