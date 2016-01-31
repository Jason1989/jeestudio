package com.zxt.framework.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
/**
 * 
 * 集合的工具类
 * <p>
 *     isEmpty 判断一个集合是否为空，如果为空返回true，否则，返回false，
 *     isNotEmpty 判断一个集合是否非空，如果非空返回true，否则，返回false，
 *     group 向指定map的指定键对应的值里添加值（当前key对应的value是一个list），
 *     修改日志说明：<br>
 *          1、Sep 14, 2011 9:46:21 AM（+） 添加注释
 * </p>
 * @author 005
 * @version 1.00
 */
public class CollectionUtils {
	/**
	  * 
	  * 判断一个集合是否为空，如果为空返回true，否则，返回false，
	  * <p>
	  *   传入参数：
	  *     coll:集合名称
	  *     
	  *   传出参数（名称/类型）：
	  *     无 
	  *     
	  * action访问地址： 无
	  * 
	  * 修改记录：
	  *     1. Sep 14, 2011 005 添加注释
	  * </p>
	 */
	public static boolean isEmpty(Collection coll) {
		return (coll == null) || (coll.isEmpty());
	}
	/**
	  * 
	  * 判断一个集合是否非空，如果非空返回true，否则，返回false，
	  * <p>
	  *   传入参数：
	  *     coll:集合名称
	  *     
	  *   传出参数（名称/类型）：
	  *     无 
	  *     
	  * action访问地址： 无
	  * 
	  * 修改记录：
	  *     1. Sep 14, 2011 005 添加注释
	  * </p>
	 */
	public static boolean isNotEmpty(Collection coll) {
		return !isEmpty(coll);
	}
	/**
	  * 
	  * 向指定map的指定键对应的值里添加值（当前key对应的value是一个list），
	  * <p>
	  *   传入参数：
	  *     key:指定的键
	  *     value:要添加的值
	  *     map:被操作的map
	  *     
	  *   传出参数（名称/类型）：
	  *     无
	  *     
	  * action访问地址： 无
	  * 
	  * 修改记录：
	  *     1. Sep 14, 2011 005 添加注释
	  * </p>
	 */
	public static void group(Object key, Object value, Map map) {
		List values = (List) map.get(key);
		if (isEmpty(values)) {
			values = new ArrayList();
			map.put(key, values);
		}
		values.add(value);
	}
}