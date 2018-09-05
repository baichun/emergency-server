package com.comtom.bc.server.service.impl;

import com.comtom.bc.common.Constants;
import com.comtom.bc.common.utils.CommonUtil;
import com.comtom.bc.server.repository.dao.DBBackupFileDao;
import com.comtom.bc.server.repository.entity.DBBackupFile;
import com.comtom.bc.server.req.BackupReq;
import com.comtom.bc.server.service.DBBackupFileService;
import com.comtom.bc.server.service.DBBackupService;
import com.comtom.bc.server.service.ParamService;
import com.comtom.bc.server.service.base.BaseService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class DBBackupFileServiceImpl extends BaseService implements DBBackupFileService {

    @Autowired
    private DBBackupFileDao backupFileDao;

   @Autowired
   private DBBackupService backupService;

   @Autowired
   private ParamService paramService;

    /**
     * 分页查询备份文件列表
     * @param req
     * @return
     */
    @Override
    public Page<DBBackupFile> page(BackupReq req) {

        return backupFileDao.findAll(getBackupSpec(req),
                buildPageRequest(req.getPageNum(), req.getPageSize(), getSort()));
    }

    @Override
    public DBBackupFile save(DBBackupFile req) {
        return backupFileDao.save(req);
    }

    @Override
    public DBBackupFile backup(DBBackupFile file) {
        if (backupService.backup(file.getBackupFileName())){
            return save(file);
        }else{
            return null;
        }
    }

    @Override
    public boolean restore(DBBackupFile file) {
        //查询还原的数据库是否存在
       // String databaseName = paramService.findValueByKey(Constants.BACKUP_DATABASE_NAME_KEY);
        //List list = backupFileDao.databaseExists(databaseName);
       /* boolean success = true;
        if(list == null || list.isEmpty()){
            //不存在  创建数据库
            success = backupService.createDatabase(databaseName);
        }
        if(success){
            //还原数据库
            String filePath = paramService.findValueByKey(Constants.BACKUP_FILE_PATH_KEY);
            success = backupService.restore(filePath +file.getFileName());
        }*/
        String filePath = paramService.findValueByKey(Constants.BACKUP_FILE_PATH_KEY);
        boolean success = backupService.restore(filePath +file.getBackupFileName());

        return success;
    }

    @Override
    public DBBackupFile getByFileName(String fileName) {
        return backupFileDao.getByFileName(fileName);
    }


    private Specification<DBBackupFile> getBackupSpec(final BackupReq req) {

        return new Specification<DBBackupFile>() {
            @Override
            public Predicate toPredicate(Root<DBBackupFile> r, CriteriaQuery<?> q, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (CommonUtil.isNotEmpty(req.getFileName())) {
                    predicate.getExpressions().add(
                            cb.like(r.<String> get("fileName"), "%" + StringUtils.trim(req.getFileName())
                                    + "%"));
                }

                if (CommonUtil.isNotEmpty(req.getBackupId())) {
                    predicate.getExpressions().add(cb.equal(r.<Integer> get("backupId"), req.getBackupId()));
                }
                return predicate;
            }
        };
    }

    private Sort getSort() {
        Sort.Order orderTime = new Sort.Order(Sort.Direction.DESC, "createTime");
        List<Sort.Order> orders = new ArrayList<Sort.Order>();
        orders.add(orderTime);
        Sort sort = new Sort(orders);
        return sort;
    }
}
