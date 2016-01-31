/**
* Copyright© 2010 zxt Co. Ltd.
* All right reserved.
* 
*/
package com.zxt.framework.common.log;

import org.apache.log4j.Logger;
/**
 * Title: LogHelper
 * Description:  Log 
 * Create DateTime: 2010-9-10
 * @author xxl
 * @since v1.0
 * 
 */
public class LogHelper {
	private static Logger logger;

	public LogHelper() {
		logger = Logger.getLogger(LogHelper.class);
	}

	public LogHelper(Class clazz) {
		logger = Logger.getLogger(clazz);
	}

	public LogHelper(String className) {
		logger = Logger.getLogger(className);
	}

	public LogHelper(boolean isInit) {
		if (isInit)
			logger = Logger.getLogger(LogHelper.class);
	}

	public static void log(Object obj, Class classz) {
		log(obj, classz, null);
	}

	public static void log(String function, String msg, Class classz) {
		Logger logger = Logger.getLogger(classz);
		logger.info(msg);
	}

	public static void log(String function, String msg, Object obj) {
		log(function, msg, obj.getClass());
	}

	public static void debug(Object obj, Class classz) {
		debug(obj, classz, null);
	}

	public static void debug(Object obj, Class classz, String methodName) {
		if (obj == null)
			return;
		Logger logger = Logger.getLogger(classz);
		logger.debug(getMethodName(methodName) + obj.toString());
	}

	public static void log(Object obj, Class classz, String methodName) {
		if (obj == null)
			return;
		Logger logger = Logger.getLogger(classz);
		logger.info(getMethodName(methodName) + obj.toString());
	}

	private static String getMethodName(String methodName) {
		String isCall = (methodName != null) ? methodName + "is call.  " : "";
		return isCall;
	}

	public static void error(Object obj, Class classz, String methodName) {
		if (obj == null)
			return;
		Logger logger = Logger.getLogger(classz);
		logger.error(getMethodName(methodName) + obj.toString());
	}
	
	public static void warn(String function, String msg, Object obj) {
		if (obj == null)
			return;
		Logger logger = Logger.getLogger(obj.getClass());
		logger.warn(function + obj.toString());
	}
	
	public static void warn(String msg, Class classz) {
		if (msg == null)
			return;
		Logger logger = Logger.getLogger(classz);
		logger.warn(msg);
	}
	
	public static void warn(String msg) {
		if (msg == null)
			return;
		logger.warn(msg);
	}
	
	
	public static void error(String function, String msg, Object obj) {
		if (obj == null)
			return;
		Logger logger = Logger.getLogger(obj.getClass());
		logger.error(function + obj.toString());
	}
	
	public static void error(String msg, Class classz) {
		if (msg == null)
			return;
		Logger logger = Logger.getLogger(classz);
		logger.error(msg);
	}
	
	
	public static String arrToString(Object[] a) {
		if (a == null)
			return "null";
		int iMax = a.length - 1;
		if (iMax == -1) {
			return "[]";
		}
		StringBuffer b = new StringBuffer();
		b.append('[');
		for (int i = 0;; ++i) {
			b.append(String.valueOf(a[i]));
			if (i == iMax)
				return "]";
			b.append(", ");
		}
	}
}