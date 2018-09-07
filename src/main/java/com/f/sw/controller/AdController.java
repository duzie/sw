package com.f.sw.controller;

import com.f.sw.entity.Sohu;
import com.f.sw.service.AdService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Date;

@Log4j2
@Controller
@RequestMapping("ad")
public class AdController {

  @Autowired
  AdService adService;

  @RequestMapping("sohu/{atype}")
  @ResponseBody
  public String sohu(@PathVariable String atype, String idfa, String imei, String gid, String clickts, String clickid, String callback) throws IOException {

    if (StringUtils.isBlank(gid)) {
      return null;
    }
    long rTimestamp = System.currentTimeMillis();
    Sohu sohu = Sohu.builder()
      .atype(atype)
      .idfa(idfa)
      .imei(imei)
      .gid(gid)
      .clickts(clickts)
      .clickid(clickid)
      .callback(callback)
      .rTimestamp(rTimestamp)
      .createDate(new Date()).build();
    adService.addSohu(sohu);
    getSoho(atype, clickid, rTimestamp);
    return "{\"desc\":\"成功\",\"status\":true}";
  }

  /**
   * 搜狐这个接口很垃圾
   *
   * @param atype
   * @param clickid
   * @param rTimestamp
   */
  public void getSoho(String atype, String clickid, long rTimestamp) {
    CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
    String get = "http://t.ads.sohu.com/count/ac?clickid="
      + clickid + "&atype=__"
      + atype + "__&timestamp=__"
      + rTimestamp + "__";
    try {
      httpclient.start();
      HttpGet request = new HttpGet(get);
      httpclient.execute(request, null).get();
    } catch (Exception e) {
    } finally {
      try {
        httpclient.close();
      } catch (IOException e) {

      }
    }

  }


}
