package com.f.sw.service;

import com.f.sw.BUIPage;
import com.f.sw.entity.AccessRecord;
import com.f.sw.entity.GoodsIgnore;
import com.f.sw.entity.OperationRecord;
import com.f.sw.entity.SysAccount;
import com.f.sw.repository.AccessRecordRepository;
import com.f.sw.repository.AccountRepository;
import com.f.sw.repository.GoodsIgnoreRepository;
import com.f.sw.repository.OperationRecordRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.util.*;

@Service
public class AccessRecordServiceImpl implements AccessRecordService {


    @Autowired
    AccessRecordRepository accessRecordRepository;

    @Autowired
    GoodsIgnoreRepository goodsIgnoreRepository;

    @Autowired
    OperationRecordRepository operationRecordRepository;

    @Autowired
    AccountRepository accountRepository;

    @Override
    public void pulldown(String vistorId) {
      Optional<AccessRecord> opt=  accessRecordRepository.findById(Integer.valueOf(vistorId.substring(10)));
      if(opt.isPresent()){
          AccessRecord ar = opt.get();
          ar.setIsPulldown(true);
          accessRecordRepository.save(ar);
      }
    }

    @Override
    public boolean login(SysAccount account) {
        SysAccount a = accountRepository.findByAccount(account.getAccount());
        return a != null && a.getPassword() != null && a.getPassword().equals(DigestUtils.md5Hex(account.getPassword()));
    }

    @Override
    public void updateAccountPwd(SysAccount account) {
        SysAccount a = accountRepository.findByAccount(account.getAccount());
        a.setPassword(DigestUtils.md5Hex(account.getPassword()));
        a.setUpdateDate(new Date());
        accountRepository.save(a);
    }

    @Override
    public Page<AccessRecord> find(BUIPage page) {
        Sort sort = new Sort(Sort.Direction.DESC, "openDate");
        page.setSort(sort);
        return accessRecordRepository.findAll(new Specification<AccessRecord>() {
            @Override
            public Predicate toPredicate(Root<AccessRecord> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();
                Map<String, String> param = page.getParam();
                String ip = param.get("ip");
                if (StringUtils.isNotBlank(ip)) {
                    list.add(cb.equal(root.get("ip"), ip));
                }

                String host = param.get("host");
                if (StringUtils.isNotBlank(host)) {
                    list.add(cb.equal(root.get("host"), host));
                }

                String channel = param.get("channel");
                if (StringUtils.isNotBlank(channel)) {
                    list.add(cb.equal(root.get("channel"), channel));
                }

                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        }, page.getPageable());
    }

    @Override
    public void save(AccessRecord accessRecord) {
        accessRecordRepository.save(accessRecord);
    }

    @Override
    public List<GoodsIgnore> findGoodsIgnore() {
        return goodsIgnoreRepository.findAll();
    }

    @Override
    public Page<OperationRecord> findOperationRecord(BUIPage page) {
        Sort sort = new Sort(Sort.Direction.DESC, "createDate");
        page.setSort(sort);
        return operationRecordRepository.findAll(new Specification<OperationRecord>() {
            @Override
            public Predicate toPredicate(Root<OperationRecord> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();
                Map<String, String> param = page.getParam();
                String vistorId = param.get("vistorId");
                if (StringUtils.isNotBlank(vistorId)) {
                    list.add(cb.equal(root.get("vistorId"), vistorId));
                }

                String start = param.get("start");
                if (StringUtils.isNotBlank(start)) {
                    try {
                        list.add(cb.greaterThan(root.get("createDate"), DateUtils.parseDate(start, "yyyy-MM-dd HH:mm:ss")));
                    } catch (ParseException e) {
                    }
                }

                String end = param.get("end");
                if (StringUtils.isNotBlank(end)) {
                    try {
                        list.add(cb.lessThan(root.get("createDate"), DateUtils.parseDate(end, "yyyy-MM-dd HH:mm:ss")));
                    } catch (ParseException e) {
                    }
                }


                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        }, page.getPageable());
    }

    @Override
    public void saveOperationRecord(OperationRecord operationRecord) {
        operationRecord.setCreateDate(new Date());
        operationRecordRepository.save(operationRecord);
    }

    @PostConstruct
    void init() {

        if (accountRepository.count() == 0) {
            SysAccount account = new SysAccount();
            account.setAccount("admin");
            account.setPassword(DigestUtils.md5Hex("123456"));
            accountRepository.save(account);
        }
    }
}
