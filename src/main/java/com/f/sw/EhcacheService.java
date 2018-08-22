package com.f.sw;


import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class EhcacheService {


    @Cacheable(value = "cacheIP", key = "#ip")
    public int getIPSize(String ip) {
        return 0;
    }


    @CachePut(value = "cacheIP", key = "#ip")
    public int setIpSize(String ip, int size) {
        System.out.println(size);
        return size;
    }

}
