package com.comtom.bc.server.repository.mapper;


import com.comtom.bc.server.repository.dao.BaseDao;
import com.comtom.bc.server.repository.vo.UserDetailVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseDao<UserDetailVo> {
}
