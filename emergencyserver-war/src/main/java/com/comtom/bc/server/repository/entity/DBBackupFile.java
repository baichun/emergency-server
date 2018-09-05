package com.comtom.bc.server.repository.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 备份文件
 */
@Entity
@Table(name = "bc_backup_file")
public class DBBackupFile {

    @Id
    @Column(name = "backup_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long backupId;

    /**
     * 自定义备份文件名称
     */
    private String fileName;
    /**
     * 备份文件名称
     */
    private String backupFileName;
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 备注
     */
    private String remark;

    /**
     * 备份方式 ：1 自动备份 2 手动备份
     */
    private String backupType;

    public String getBackupFileName() {
        return backupFileName;
    }

    public void setBackupFileName(String backupFileName) {
        this.backupFileName = backupFileName;
    }

    public String getBackupType() {
        return backupType;
    }

    public void setBackupType(String backupType) {
        this.backupType = backupType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getBackupId() {
        return backupId;
    }

    public void setBackupId(Long backupId) {
        this.backupId = backupId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
