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
@Table(name = "S_WEB_PAGE")
public class WebPage {

    @Id
    Integer id;
    String host;
    @Lob
    String content;
    String title;
    String keywords;
    String description;
    Date updateDate;
}
