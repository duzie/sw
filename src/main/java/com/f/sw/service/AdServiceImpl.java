package com.f.sw.service;

import com.f.sw.entity.Sohu;
import com.f.sw.repository.SohuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdServiceImpl implements AdService {

  @Autowired
  SohuRepository sohuRepository;
  @Override
  public void addSohu(Sohu sohu) {
    sohuRepository.save(sohu);
  }
}
