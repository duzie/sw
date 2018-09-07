package com.f.sw;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.fluent.Request;

import javax.servlet.http.HttpServletRequest;

@Log4j2
public class IpUtil {
  static ObjectMapper om = new ObjectMapper();

  public static String getIpAdrress(HttpServletRequest request) {
    String Xip = request.getHeader("X-Real-IP");
    String XFor = request.getHeader("X-Forwarded-For");
    if (StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)) {
      int index = XFor.indexOf(",");
      if (index != -1) {
        return XFor.substring(0, index);
      } else {
        return XFor;
      }
    }
    XFor = Xip;
    if (StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)) {
      return XFor;
    }
    if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
      XFor = request.getHeader("Proxy-Client-IP");
    }
    if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
      XFor = request.getHeader("WL-Proxy-Client-IP");
    }
    if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
      XFor = request.getHeader("HTTP_CLIENT_IP");
    }
    if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
      XFor = request.getHeader("HTTP_X_FORWARDED_FOR");
    }
    if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
      XFor = request.getRemoteAddr();
    }
    return XFor;
  }

  public static String getLocation(String ip) {
    try {
      String s = Request.Get("http://api.map.baidu.com/location/ip?ip=" + ip + "&ak=wWliT70ZLHUoihYrGjr6DW3G")
        .execute()
        .returnContent()
        .asString();
      log.debug(s);
      BaiduLocation location = om.readValue(s, BaiduLocation.class);
      if (location.getStatus() != 2)
        return location.getContent().getAddress();
    } catch (Exception e) {
    }
    return "未知";
  }

  public static void main(String[] args) {
    System.out.println(getLocation("113.91.211.37"));
  }
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class BaiduLocation {
  int status;
  String address;
  Content content;

  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  class Content {
    String address;
  }
}
