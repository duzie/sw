package com.f.sw.service;

import com.f.sw.BUIPage;
import com.f.sw.entity.Channel;
import com.f.sw.entity.WebPage;
import org.springframework.data.domain.Page;

public interface WebPageService {

    Page<WebPage> find(BUIPage page);

    void save(WebPage webPage);

    WebPage find(int id);

    public Iterable<Channel> findChannel();
}
