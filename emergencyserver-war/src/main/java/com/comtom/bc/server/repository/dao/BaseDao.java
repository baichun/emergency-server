package com.comtom.bc.server.repository.dao;

import java.util.List;
import java.util.Map;


/**
 * 还需在XML文件里，有对应的SQL语句
 * 基础Dao(
 */
public interface BaseDao<T> {
	
	void save(T t);
	
	void save(Map<String, Object> map);

	void saveBatch(List<T> list);

	int update(T t);

	int update(Map<String, Object> map);
	
	int deleteByKey(Object id);
	
	int deleteBatch(Object[] id);

	T queryObject(Object id);
	
	List<T> queryList(Map<String, Object> map);
	
	List<T> queryList(Object id);
	
	int queryTotal(Map<String, Object> map);

	int queryTotal();
}
