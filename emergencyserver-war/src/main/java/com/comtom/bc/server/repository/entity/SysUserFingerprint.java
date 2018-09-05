package com.comtom.bc.server.repository.entity;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户指纹数据
 */
@Data
@Entity
@Table(name = "aos_sys_user_fingerprint")
public class SysUserFingerprint {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "reg_template_")
    private String regTemplate;


    @Column(name = "finger_position_")
    private Integer fingerPosition;

    @Column(name = "create_time_")
    private String createTime;


    @Column(name = "update_time_")
    private String updateTime;
}
