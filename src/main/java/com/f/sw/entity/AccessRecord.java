package com.f.sw.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "S_ACCESS_RECORD")
public class AccessRecord {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String prefix;
    private String ip;
    @Lob
    private String referrer;
    private String host;
    private String channel;
    private Date openDate;
    private Date closeDate;
    private Boolean isPulldown;
    private Boolean isMobile;
    private String deviceType;

    public String getSerialNO() {
        return prefix + String.format("%08d", id);
    }
}
