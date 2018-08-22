package com.f.sw.repository;

import com.f.sw.entity.WebPage;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface WebPageRepository extends PagingAndSortingRepository<WebPage, Integer> {

}
