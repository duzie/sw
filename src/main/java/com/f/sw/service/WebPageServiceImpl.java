package com.f.sw.service;

import com.f.sw.BUIPage;
import com.f.sw.entity.Channel;
import com.f.sw.entity.WebPage;
import com.f.sw.repository.ChannelRepository;
import com.f.sw.repository.WebPageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Optional;

@Service
public class WebPageServiceImpl implements WebPageService {

    @Autowired
    WebPageRepository webPageRepository;

    @Autowired
    ChannelRepository channelRepository;

    @Override
    public Page<WebPage> find(BUIPage page) {
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        page.setSort(sort);
        return webPageRepository.findAll(page.getPageable());
    }

    @Override
    public void save(WebPage webPage) {
        Optional<WebPage> owg = webPageRepository.findById(webPage.getId());
        if (owg.isPresent()) {
            webPage.setHost(owg.get().getHost());
            webPage.setUpdateDate(new Date());
            webPageRepository.save(webPage);
        }
    }

    @Override
    public WebPage find(int id) {
        return webPageRepository.findById(id).get();
    }

    @Override
    public Iterable<Channel> findChannel() {
        return channelRepository.findAll();
    }

    @PostConstruct
    public void init() {
        if (webPageRepository.count() > 0)
            return;
        {
            WebPage webPage = new WebPage();
            webPage.setId(1);
            webPage.setHost("jxtx1.baijiu-world.com");
            webPage.setContent("欢迎");
            webPageRepository.save(webPage);
        }
        {
            WebPage webPage = new WebPage();
            webPage.setId(2);
            webPage.setHost("jxtx2.baijiu-world.com");
            webPage.setContent("欢迎");
            webPageRepository.save(webPage);
        }
        {
            WebPage webPage = new WebPage();
            webPage.setId(3);
            webPage.setHost("jxtx3.baijiu-world.com");
            webPage.setContent("欢迎");
            webPageRepository.save(webPage);
        }
        {
            WebPage webPage = new WebPage();
            webPage.setId(4);
            webPage.setHost("jxtx4.baijiu-world.com");
            webPage.setContent("欢迎");
            webPageRepository.save(webPage);
        }
        {
            WebPage webPage = new WebPage();
            webPage.setId(5);
            webPage.setHost("jxtx5.baijiu-world.com");
            webPage.setContent("欢迎");
            webPageRepository.save(webPage);
        }

    }

    @PostConstruct
    public void initChannel() {
        if (channelRepository.count() > 0)
            return;
        {
            Channel c = new Channel();
            c.setId(1);
            c.setName("百度");
            c.setHost("baidu.com");
            c.setNm("BD");
            channelRepository.save(c);
        }
        {
            Channel c = new Channel();
            c.setId(2);
            c.setName("腾讯");
            c.setHost("qq.com");
            c.setNm("QQ");
            channelRepository.save(c);
        }

    }
}