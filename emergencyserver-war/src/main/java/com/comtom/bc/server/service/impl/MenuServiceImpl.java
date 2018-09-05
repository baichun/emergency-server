package com.comtom.bc.server.service.impl;

import com.comtom.bc.common.utils.CommonUtil;
import com.comtom.bc.server.repository.dao.UserMenuViewDAO;
import com.comtom.bc.server.repository.dao.SysModuleDAO;
import com.comtom.bc.server.repository.entity.UserMenuView;
import com.comtom.bc.server.repository.entity.SysModule;
import com.comtom.bc.server.req.MenuReq;
import com.comtom.bc.server.service.MenuService;
import com.comtom.bc.server.service.base.BaseService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class MenuServiceImpl extends BaseService implements MenuService {

    @Autowired
    private SysModuleDAO sysModuleDAO;

    @Autowired
    private UserMenuViewDAO menuDAO;


    @Override
    public Page<SysModule> page(MenuReq req) {
        return sysModuleDAO.findAll(getMenuSpc(req),buildPageRequest(req.getPageNum(),req.getPageSize(),getMenuSort()));
    }

    @Override
    public SysModule save(SysModule menu) {
        return sysModuleDAO.save(menu);
    }

    @Override
    public SysModule findById(String id) {
        return sysModuleDAO.findOne(id);
    }

    @Override
    public List<SysModule> list(MenuReq req) {
        return sysModuleDAO.findAll(getMenuSpc(req),getMenuSort());
    }

    @Override
    public List<SysModule> treeList(MenuReq req) {
        List<SysModule> list =  sysModuleDAO.findAll(getMenuSpc(req),getMenuSort());
        if(list != null && list.size() > 0){
            for(SysModule m : list){
                MenuReq r = new MenuReq();
                r.setPortalType(4);
                r.setParentId(m.getId());
                List<SysModule> subList = this.treeList(r);
                if(subList!= null){
                    m.setChildren(subList);
                }else{
                    m.setChildren(new ArrayList<SysModule>());
                }
            }
        }
        return list;
    }

    @Override
    public List<UserMenuView> grantedList(MenuReq req) {
        List<UserMenuView> list = menuDAO.findAll(getGrantedMenuSpc(req),getMenuSort());
        if(req.isNeedChild()){
            for(UserMenuView m : list){
                MenuReq r = new MenuReq();
                r.setPortalType(4);
                r.setUserId(req.getUserId());
                r.setParentId(m.getId());
                r.setNeedChild(req.isNeedChild());
                List<UserMenuView> subList = this.grantedList(r);
                if(subList!= null){
                    m.setChildren(subList);
                }else{
                    m.setChildren(new ArrayList<UserMenuView>());
                }
            }
        }
        return list;

    }

    private Specification<UserMenuView> getGrantedMenuSpc(final MenuReq req) {

        return new Specification<UserMenuView>() {
            @Override
            public Predicate toPredicate(Root<UserMenuView> r, CriteriaQuery<?> q, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (CommonUtil.isNotEmpty(req.getName())) {
                    predicate.getExpressions().add(
                            cb.like(r.<String> get("name"), "%" + StringUtils.trim(req.getName())
                                    + "%"));
                }
                if (CommonUtil.isNotEmpty(req.getParentName())) {
                    predicate.getExpressions().add(
                            cb.like(r.<String> get("parentName"), "%" + StringUtils.trim(req.getParentName())
                                    + "%"));
                }
                if (CommonUtil.isNotEmpty(req.getId())) {
                    predicate.getExpressions().add(cb.equal(r.<Integer> get("id"), req.getId()));
                }
                if (CommonUtil.isNotEmpty(req.getUserId())) {
                    predicate.getExpressions().add(cb.equal(r.<Integer> get("userId"), req.getUserId()));
                }
                if (CommonUtil.isNotEmpty(req.getParentId())) {
                    predicate.getExpressions().add(cb.equal(r.<Integer> get("parentId"), req.getParentId()));
                }
                if (CommonUtil.isNotEmpty(req.getPortalType())) {
                    predicate.getExpressions().add(cb.equal(r.<Integer> get("portalType"), req.getPortalType()));
                }
                if (CommonUtil.isNotEmpty(req.getStatus())) {
                    predicate.getExpressions().add(cb.equal(r.<Integer> get("status"), req.getStatus()));
                }
                return predicate;
            }
        };
    }

    private Specification<SysModule> getMenuSpc(final MenuReq req) {

        return new Specification<SysModule>() {
            @Override
            public Predicate toPredicate(Root<SysModule> r, CriteriaQuery<?> q, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (CommonUtil.isNotEmpty(req.getName())) {
                    predicate.getExpressions().add(
                            cb.like(r.<String> get("name"), "%" + StringUtils.trim(req.getName())
                                    + "%"));
                }
                if (CommonUtil.isNotEmpty(req.getParentName())) {
                    predicate.getExpressions().add(
                            cb.like(r.<String> get("parentName"), "%" + StringUtils.trim(req.getParentName())
                                    + "%"));
                }
                if (CommonUtil.isNotEmpty(req.getId())) {
                    predicate.getExpressions().add(cb.equal(r.<Integer> get("id"), req.getId()));
                }
                if (CommonUtil.isNotEmpty(req.getParentId())) {
                    predicate.getExpressions().add(cb.equal(r.<Integer> get("parentId"), req.getParentId()));
                }
                if (CommonUtil.isNotEmpty(req.getPortalType())) {
                    predicate.getExpressions().add(cb.equal(r.<Integer> get("portalType"), req.getPortalType()));
                }
                if (CommonUtil.isNotEmpty(req.getStatus())) {
                    predicate.getExpressions().add(cb.equal(r.<Integer> get("status"), req.getStatus()));
                }
                return predicate;
            }
        };
    }

    private Sort getMenuSort() {
        Sort.Order order = new Sort.Order(Sort.Direction.ASC, "sortNo");
        List<Sort.Order> orders = new ArrayList<Sort.Order>();
        orders.add(order);
        Sort sort = new Sort(orders);
        return sort;
    }
}
