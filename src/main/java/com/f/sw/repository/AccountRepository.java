package com.f.sw.repository;

import com.f.sw.entity.SysAccount;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<SysAccount, Integer> {
    SysAccount findByAccount(String account);
}
