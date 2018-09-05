package com.comtom.bc.server.repository.dao;

import com.comtom.bc.server.repository.support.CustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.comtom.bc.server.repository.entity.FileLibrary;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 文件目录-数据访问层接口定义
 * 
 * @author zhucanhui
 *
 */
public interface LibraryDAO extends CustomRepository<FileLibrary, Long> {

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "delete from bc_file_library  where libId in  (:libIds)")
    public void deleteByIdsBatch(@Param("libIds") List<Long> libIds);
}
