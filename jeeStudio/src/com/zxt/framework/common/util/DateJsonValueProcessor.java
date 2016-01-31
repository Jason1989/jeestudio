package com.zxt.framework.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * 日期转换的工具类
 * <p>
 *     DateJsonValueProcessor 构造方法，
 *     processArrayValue --------------，
 *     processObjectValue -----------------------，
 *     process 格式化日期，把传入的值转换成指定的日期格式，
 *     修改日志说明：<br>
 *          1、Sep 14, 2011 10:38:44 AM（+） 添加注释
 * </p>
 * @author 005
 * @version 1.00
 */
public class DateJsonValueProcessor implements JsonValueProcessor {
    private static Log logger = LogFactory.getLog(DateJsonValueProcessor.class);

    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";

    private DateFormat dateFormat;
    /**
      * 
      * 构造方法，
      * <p>
      *   传入参数：
      *     datePattern:日期格式
      *     
      *   传出参数（名称/类型）：
      *     1. 无
      *     
      * action访问地址： 无
      * 
      * 修改记录：
      *     1. Sep 14, 2011 005 添加注释
      * </p>
     */
    public DateJsonValueProcessor(String datePattern) {
        try {
            dateFormat = new SimpleDateFormat(datePattern);
        } catch (Exception ex) {
            logger.info(ex);
            dateFormat = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
        }
    }

    /**
     * 转换数组---------------------------------
     *
     */
    public Object processArrayValue(Object value, JsonConfig jsonConfig) {
        return process(value);
    }

    /**
     * 转换对象----------------------------
     *
     */
    public Object processObjectValue(String key, Object value,
        JsonConfig jsonConfig) {
        return process(value);
    }
    
    /**
      * 
      * 格式化日期，把传入的值转换成指定的日期格式，
      * <p>
      *   传入参数：
      *     value:要被转换成指定日期格式的数据
      *     
      *   传出参数（名称/类型）：
      *     1. 无
      *     
      * action访问地址： 无
      * 
      * 修改记录：
      *     1. Sep 14, 2011 005 添加注释
      * </p>
     */
    private Object process(Object value) {
        try {
            return dateFormat.format((Date) value);
        } catch (Exception ex) {
            return null;
        }
    }
}
