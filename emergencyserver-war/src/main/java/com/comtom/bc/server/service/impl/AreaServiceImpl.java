package com.comtom.bc.server.service.impl;

import com.comtom.bc.common.Constants;
import com.comtom.bc.common.utils.CommonUtil;
import com.comtom.bc.server.repository.dao.RegionAreaDAO;
import com.comtom.bc.common.utils.GenUUId;
import com.comtom.bc.server.repository.dao.RegionDAO;
import com.comtom.bc.server.repository.dao.SysParamDAO;
import com.comtom.bc.server.repository.entity.Region;
import com.comtom.bc.server.repository.entity.RegionArea;
import com.comtom.bc.server.repository.entity.SysParam;
import com.comtom.bc.server.req.RegionReq;
import com.comtom.bc.server.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * 行政区域-业务逻辑处理接口定义
 *
 * @author zhucanhui
 */
@Service("RegionAreaService")
public class AreaServiceImpl implements AreaService {

    @Autowired
    private SysParamDAO sysParamDAO;

    @Autowired
    private RegionAreaDAO regionAreaDAO;

    @Autowired
    private RegionDAO regionDAO;

    /**
     * 根据字典参数索引KEY获取对应值（集合）
     *
     * @param key
     * @return List<RegionArea>
     */
    public List<RegionArea> findArea(String areaCode, Integer areaLevel) {
        if (CommonUtil.isNotEmpty(areaCode)) {
            return regionAreaDAO.findSubArea(areaCode);
        } else {
            String platAreaCode = sysParamDAO.findValueByKey(Constants.PLATFORM_AREA_CODE);
            RegionArea regionArea = regionAreaDAO.findOne(platAreaCode);
            Integer level = regionArea.getAreaLevel() + areaLevel;
            return regionAreaDAO.findAreaDefault(level);
        }
    }

    /**
     * 根据字典参数索引KEY获取对应值（集合）
     *
     * @param key
     * @return List<RegionArea>
     */
    public List<Region> findAreaAll(RegionReq req, Integer areaLevel) {
        String areaCode = req.getAreaCode();
        if (CommonUtil.isNotEmpty(areaCode)) {
            return regionDAO.findAll(getRegionSpec(req));
        } else{
           /* String platAreaCode = sysParamDAO.findValueByKey(Constants.PLATFORM_AREA_CODE);
            Region region = regionDAO.findOne(platAreaCode);
            Integer level = region.getAreaLevel() + areaLevel;*/
            return regionDAO.findAreaDefault(2);
        }
    }

    /**
     *
     *  查询指定区域以及区域下的所有子区域列表
     * @param key
     * @return List<RegionArea>
     */
    public List<Region> findAreaAllChildren(RegionReq req, Integer areaLevel) {
        String areaCode = req.getAreaCode();
        if (CommonUtil.isNotEmpty(areaCode)) {
            return regionDAO.findAll(getRegionChildrenSpec(req));
        }
        return null;
    }

    private Specification<Region> getRegionSpec(final RegionReq req) {

        final String areaCode = req.getAreaCode();

        return new Specification<Region>() {
            @Override
            public Predicate toPredicate(Root<Region> r, CriteriaQuery<?> q, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();

                if (CommonUtil.isNotEmpty(areaCode)) {
                    predicate.getExpressions().add(
                            cb.equal(r.<String> get("parentAreaCode"), areaCode));
                }
                return predicate;
            }
        };
    }

    private Specification<Region> getRegionChildrenSpec(final RegionReq req) {

        final String areaCode = req.getAreaCode();

        return new Specification<Region>() {
            @Override
            public Predicate toPredicate(Root<Region> r, CriteriaQuery<?> q, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();

                if (CommonUtil.isNotEmpty(areaCode)) {
                    predicate.getExpressions().add(
                            cb.like(r.<String> get("areaCode"), areaCode + "%"));
                }
                return predicate;
            }
        };
    }

    /**
     *
     *  保存初始化区域
     * @param key
     */
    @Transactional
    public void insertAreaAllChildren(RegionReq req) {
        String areaCode = req.getAreaCode();
        if (CommonUtil.isNotEmpty(areaCode)){
            //清空原表
            regionAreaDAO.deleteAllInBatch();
            //插入指定区域及所有下级区域
            regionDAO.insertAreaAllChildren(areaCode);

            //修改系统参数   平台默认区域编码
            Region region = regionDAO.findOne(areaCode);
            SysParam sysParam =  sysParamDAO.findByKey(Constants.PLATFORM_AREA_CODE);
            if(sysParam == null){
                sysParam=new SysParam();
                String uuid = GenUUId.uuid();
                sysParam.setId(uuid);
                sysParam.setKey(Constants.PLATFORM_AREA_CODE);
                sysParam.setValue(areaCode);
                sysParam.setCatalogId("4ee1d6e48b31487b849a72cd03d2512a");
                sysParam.setCatalogCascadeId("0.001.001");
                sysParam.setIsOverwrite("0");
                sysParam.setName("平台默认区域编码");
            }else{
                sysParam.setValue(areaCode);
            }
            sysParamDAO.save(sysParam);

            //修改系统参数   平台默认区域名称
            SysParam sysParam1 =  sysParamDAO.findByKey(Constants.PLATFORM_AREA_NAME);
            if(sysParam1 == null){
                sysParam1=new SysParam();
                String uuid = GenUUId.uuid();
                sysParam1.setId(uuid);
                sysParam1.setKey(Constants.PLATFORM_AREA_NAME);
                sysParam1.setValue(region.getAreaName());
                sysParam1.setCatalogId("4ee1d6e48b31487b849a72cd03d2512a");
                sysParam1.setCatalogCascadeId("0.001.001");
                sysParam1.setIsOverwrite("0");
                sysParam1.setName("平台默认区域名称");
            }else{
                sysParam1.setValue(region.getAreaName());
            }
            sysParamDAO.save(sysParam1);

        }
    }

}