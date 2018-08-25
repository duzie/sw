package com.f.sw.controller.wx.pay;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "web.wx.pay")
public class WxPayProperties {
    private String appId;
    private String mchId;
    private String mchKey;
    private String oauth2Url;
}
