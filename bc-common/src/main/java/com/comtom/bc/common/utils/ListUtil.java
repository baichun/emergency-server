package com.comtom.bc.common.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Iterator转换为List集合工具
 * @author zhucanhui
 *
 */
public class ListUtil {
	
	public static <T> List<T> copyIterator(Iterator<T> iter) {
	    List<T> copy = new ArrayList<T>();
	    while (iter.hasNext())
	        copy.add(iter.next());
	    return copy;
	}

}
