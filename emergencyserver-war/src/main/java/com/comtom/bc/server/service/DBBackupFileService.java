package com.comtom.bc.server.service;

import com.comtom.bc.server.repository.entity.DBBackupFile;
import com.comtom.bc.server.req.BackupReq;
import org.springframework.data.domain.Page;

public interface DBBackupFileService {

    public Page<DBBackupFile> page(BackupReq req);

    public DBBackupFile save(DBBackupFile req);

    public DBBackupFile backup(DBBackupFile file);

    public boolean restore(DBBackupFile file);

    public DBBackupFile getByFileName(String fileName);
}
