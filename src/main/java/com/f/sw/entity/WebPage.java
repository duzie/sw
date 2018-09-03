package com.f.sw.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
/**
 * 落地页
 */
@Table(name = "S_WEB_PAGE")
public class WebPage {

    @Id
    Integer id;
    String host;//生效域名
    @Lob
    String content;//内容
    String title;//标题
    String keywords;//关键字
    String description;//描述
    Date updateDate;//更新时间
}
