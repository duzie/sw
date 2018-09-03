package com.f.sw.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
/**
 * 操作记录
 */
@Table(name = "S_OPERATION_RECORD")
public class OperationRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String vistorId;//访客ID
    private String operation;//操作行为
    private Date createDate;//创建时间
}
