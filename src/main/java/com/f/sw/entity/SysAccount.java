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
/**
 * 管理员
 */
@Table(name = "S_ACCOUNT")
public class SysAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String account;//系统管理员账号
    private String password;//密码
    private Date updateDate;
}
