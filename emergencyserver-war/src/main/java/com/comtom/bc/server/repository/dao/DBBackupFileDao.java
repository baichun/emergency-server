package com.comtom.bc.server.repository.dao;

import com.comtom.bc.server.repository.entity.DBBackupFile;
import com.comtom.bc.server.repository.support.CustomRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DBBackupFileDao extends CustomRepository<DBBackupFile, Long> {

    @Query(value = "show databases like :databaseName",nativeQuery = true)
    public List<Object> databaseExists(@Param("databaseName") String databaseName);

    @Query(value = "select d from DBBackupFile d where fileName=:fileName")
    public DBBackupFile getByFileName(@Param("fileName") String fileName);
}
