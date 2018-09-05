package com.comtom.bc.common.datasource;

/**
 * 多数据源设置上下文
 * @author zhucanhui
 *
 */
import java.util.ArrayList;
import java.util.List;

public class DynamicDataSourceContextHolder {

	private static final ThreadLocal<DynamicDatabaseType> contextHolder = new ThreadLocal<DynamicDatabaseType>();
	public static List<String> dataSourceIds = new ArrayList<>();

	public static void setDataSourceType(DynamicDatabaseType dataSourceType) {
		contextHolder.set(dataSourceType);
	}

	public static DynamicDatabaseType getDataSourceType() {
		return contextHolder.get();
	}

	public static void clearDataSourceType() {
		contextHolder.remove();
	}

	/**
	 * 判断指定DataSrouce当前是否存在
	 *
	 * @param dataSourceId
	 */
	public static boolean containsDataSource(DynamicDatabaseType dataSourceType) {
		return dataSourceIds.contains(dataSourceType);
	}
}