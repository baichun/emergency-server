package com.comtom.bc.server.service.impl;

import com.comtom.bc.common.utils.CommonUtil;
import com.comtom.bc.common.utils.GenUUId;
import com.comtom.bc.server.repository.dao.SysModuleRoleDAO;
import com.comtom.bc.server.repository.dao.SysRoleDAO;
import com.comtom.bc.server.repository.dao.UserRoleDAO;
import com.comtom.bc.server.repository.entity.*;
import com.comtom.bc.server.req.MenuReq;
import com.comtom.bc.server.req.RoleQueryReq;
import com.comtom.bc.server.req.SysRoleGrantReq;
import com.comtom.bc.server.service.RoleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private SysRoleDAO sysRoleDAO;

    @Autowired
    private SysModuleRoleDAO sysModuleRoleDAO;

    @Autowired
    private UserRoleDAO sysRoleUserDAO;


    @Override
    public Page<SysRole> find(RoleQueryReq req) {
        SysRole sysRole=new SysRole();
        sysRole.setType("4");
        if(CommonUtil.isNotEmpty(req.getName())){
            sysRole.setName(req.getName());
        }
        Pageable pageable = new PageRequest(req.getPageNum() - 1,req.getPageSize());
        Page<SysRole> page = sysRoleDAO.findByAuto(sysRole, pageable);
        return page;
    }

    @Override
    public SysRole saveSysRole(SysRole sysRole) {
        return sysRoleDAO.save(sysRole);
    }

    @Override
    public SysRole findById(String id) {
        return sysRoleDAO.findOne(id);
    }

    @Override
    public void delRoleInfo(SysRole sysRole) {
        sysRoleDAO.delete(sysRole.getId());
    }

    @Override
    public List<SysModuleRole> getResourceIdsByRoleId(SysModuleRole sysModuleRole) {

        return sysModuleRoleDAO.findAll(getSpc(sysModuleRole));
    }


    private Specification<SysModuleRole> getSpc(final SysModuleRole req) {
        return new Specification<SysModuleRole>() {
            @Override
            public Predicate toPredicate(Root<SysModuleRole> r, CriteriaQuery<?> q, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (CommonUtil.isNotEmpty(req.getRoleId())) {
                    predicate.getExpressions().add(cb.equal(r.<Integer> get("roleId"), req.getRoleId()));
                }
                return predicate;
            }
        };
    }

    @Transactional
    @Override
    public void grant(SysRoleGrantReq req,String account) {
        //1.先删除原数据，2.再插入数据
        sysModuleRoleDAO.deleteByRoleId(req.getRoleId());
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<String> menuIdList = req.getMenuIdList();
        for (String menuId:menuIdList) {
            SysModuleRole s=new SysModuleRole();
            s.setRoleId(req.getRoleId());
            s.setGrantType("1");
            s.setOperateTime(sdf.format(new Date()));
            s.setOperatorId(account);
            s.setModuleId(menuId);
            sysModuleRoleDAO.save(s);
        }

    }

    @Override
    public List<SysUserRole> findListByUserId(String userId) {
        return sysRoleUserDAO.findByUserId(userId);
    }
}
