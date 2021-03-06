package com.f.sw;

import org.apache.http.HttpEntity;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class RequestUtil {
  public static boolean JudgeIsMoblie(HttpServletRequest request) {
    boolean isMoblie = false;
    String[] mobileAgents = {"iphone", "android", "ipad", "phone", "mobile", "wap", "netfront", "java", "opera mobi",
      "opera mini", "ucweb", "windows ce", "symbian", "series", "webos", "sony", "blackberry", "dopod",
      "nokia", "samsung", "palmsource", "xda", "pieplus", "meizu", "midp", "cldc", "motorola", "foma",
      "docomo", "up.browser", "up.link", "blazer", "helio", "hosin", "huawei", "novarra", "coolpad", "webos",
      "techfaith", "palmsource", "alcatel", "amoi", "ktouch", "nexian", "ericsson", "philips", "sagem",
      "wellcom", "bunjalloo", "maui", "smartphone", "iemobile", "spice", "bird", "zte-", "longcos",
      "pantech", "gionee", "portalmmm", "jig browser", "hiptop", "benq", "haier", "^lct", "320x320",
      "240x320", "176x220", "w3c ", "acs-", "alav", "alca", "amoi", "audi", "avan", "benq", "bird", "blac",
      "blaz", "brew", "cell", "cldc", "cmd-", "dang", "doco", "eric", "hipt", "inno", "ipaq", "java", "jigs",
      "kddi", "keji", "leno", "lg-c", "lg-d", "lg-g", "lge-", "maui", "maxo", "midp", "mits", "mmef", "mobi",
      "mot-", "moto", "mwbp", "nec-", "newt", "noki", "oper", "palm", "pana", "pant", "phil", "play", "port",
      "prox", "qwap", "sage", "sams", "sany", "sch-", "sec-", "send", "seri", "sgh-", "shar", "sie-", "siem",
      "smal", "smar", "sony", "sph-", "symb", "t-mo", "teli", "tim-", "tosh", "tsm-", "upg1", "upsi", "vk-v",
      "voda", "wap-", "wapa", "wapi", "wapp", "wapr", "webc", "winw", "winw", "xda", "xda-",
      "Googlebot-Mobile"};
    if (request.getHeader("User-Agent") != null) {
      String agent = request.getHeader("User-Agent");
      for (String mobileAgent : mobileAgents) {
        if (agent.toLowerCase().indexOf(mobileAgent) >= 0 && agent.toLowerCase().indexOf("windows nt") <= 0 && agent.toLowerCase().indexOf("macintosh") <= 0) {
          isMoblie = true;
          break;
        }
      }
    }
    return isMoblie;
  }

  public static boolean isWechat(HttpServletRequest request) {
    String ua = request.getHeader("User-Agent").toLowerCase();
    if (ua.indexOf("micromessenger") > -1) {
      return true;//微信
    }
    return false;//非微信手机浏览器

  }

  public static void main(String[] args) throws IOException {
    File file = new File("/Users/fei/Downloads/玩头死税.mp3");
    FileBody bin = new FileBody(file);
    HttpEntity reqEntity = MultipartEntityBuilder.create()
      .setCharset(Charset.forName("utf-8"))
      .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
      .addPart("file", bin).build();

    String s = Request
      .Post("http://localhost:9010/music/upload")
      .body(reqEntity)
      .execute().returnContent().asString();
    System.out.println(s);

  }

}
