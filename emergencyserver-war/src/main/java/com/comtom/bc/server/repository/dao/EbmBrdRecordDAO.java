package com.comtom.bc.server.repository.dao;

import com.comtom.bc.server.repository.entity.Ebm;
import com.comtom.bc.server.repository.entity.EbmBrdRecord;
import com.comtom.bc.server.repository.support.CustomRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 消息播发记录
 * @author zhucanhui
 *
 */
public interface EbmBrdRecordDAO extends CustomRepository<EbmBrdRecord, String> {

    /**
     * 根据消息id集合查询EBM消息记录
     * @param ebmIds
     * @return
     */
    @Query(nativeQuery = true, value = "select * from bc_ebm_brd_record where ebmId in  (:ebmIds) ORDER by createTime desc")
    public List<EbmBrdRecord> findEbmRecordByEmbIds(@Param("ebmIds") List<String> ebmIds);
}
