package com.zxt.framework.common.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
/**
 * 
 * 日期的工具类（格式化，计算日期间隔，得到特定日期）
 * <p>
 *     getCurrentDate 得到当前时间（日期+时间），
 *     getCurrentDateStr 得到当前日期，
 *     getCurrentDateStr 把当前日期转化成指定格式，
 *     parseDate 把指定的日期转换成"yyyy-MM-dd"格式，
 *     parseDateTime 把指定的时间转换成"yyyy-MM-dd HH:mm:ss"格式，
 *     parseDate 把指定的时间转换成指定格式，如果没指定格式，则转换成"yyyy-MM-dd HH:mm:ss"格式，
 *     format 把指定的日期转换成"yyyy-MM-dd"格式，
 *     formatTime 把指定的时间转换成"yyyy-MM-dd HH:mm:ss"格式，
 *     format 把指定的时间转换成指定格式，如果没指定时间或格式，则返回空，
 *     formatTime 把指定的时间转换成指定格式，如果没指定时间或格式，则返回空，
 *     getFormatTime 把指定的时间转换成"HH:mm:ss"格式，
 *     format 格式化时间戳，
 *     addDays 为指定的日期增加若干天，
 *     addHour 为指定的日期增加若干小时，
 *     daysBetween 求两个日期相隔的天数（例如date1是2011-04-05，Date2是2011-05-05，结果为正数），
 *     yearsBetween 求两个日期相隔的年数（例如date1是2009-04-05，Date2是2011-05-05，结果为正数），
 *     getRelativeDays 求指定日期与"1977-12-01"相隔的天数，
 *     getDateBeforTwelveMonth 求十二个月前的当月的第一天，
 *     addDate 为指定的日期增加1天，
 *     addMonth 为指定的日期增加若干月，
 *     addYear 为指定的日期增加若干年，
 *     修改日志说明：<br>
 *          1、Sep 14, 2011 11:10:41 AM（+） 描述
 * </p>
 * @author 005
 * @version 1.00
 */
public class DateUtil {
	public static final String C_DATE_DIVISION = "-";
	public static final String C_TIME_PATTON_DEFAULT = "yyyy-MM-dd HH:mm:ss";
	public static final String C_DATE_PATTON_DEFAULT = "yyyy-MM-dd";
	public static final String C_DATA_PATTON_YYYYMMDD = "yyyyMMdd";
	public static final String C_TIME_PATTON_HHMMSS = "HH:mm:ss";
	public static final int C_ONE_SECOND = 1000;
	public static final int C_ONE_MINUTE = 60000;
	public static final int C_ONE_HOUR = 3600000;
	public static final long C_ONE_DAY = 86400000L;
	/**
	  * 
	  * 得到当前时间（日期+时间），
	  * <p>
	  *   传入参数：
	  *     无
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
	public static Date getCurrentDate() {
		Calendar cal = Calendar.getInstance();
		Date currDate = cal.getTime();
		return currDate;
	}
	/**
	  * 
	  * 得到当前日期，
	  * <p>
	  *   传入参数：
	  *     无
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
	public static String getCurrentDateStr() {
		Calendar cal = Calendar.getInstance();
		Date currDate = cal.getTime();

		return format(currDate);
	}

	/**
	  * 
	  * 把当前日期转化成指定格式，
	  * <p>
	  *   传入参数：
	  *     strFormat：日期格式
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
	public static String getCurrentDateStr(String strFormat) {
		Calendar cal = Calendar.getInstance();
		Date currDate = cal.getTime();

		return format(currDate, strFormat);
	}

	/**
	  * 
	  * 把指定的日期转换成"yyyy-MM-dd"格式，
	  * <p>
	  *   传入参数：
	  *     dateValue：要格式化的日期
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
	public static Date parseDate(String dateValue) {
		return parseDate("yyyy-MM-dd", dateValue);
	}

	/**
	  * 
	  * 把指定的时间转换成"yyyy-MM-dd HH:mm:ss"格式，
	  * <p>
	  *   传入参数：
	  *     dateValue：要格式化的时间
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
	public static Date parseDateTime(String dateValue) {
		return parseDate("yyyy-MM-dd HH:mm:ss", dateValue);
	}
	/**
	  * 
	  * 把指定的时间转换成指定格式，如果没指定格式，则转换成"yyyy-MM-dd HH:mm:ss"格式
	  * <p>
	  *   传入参数：
	  *     strFormat：时间格式
	  *     dateValue：要格式化的时间
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
	public static Date parseDate(String strFormat, String dateValue) {
		if (dateValue == null) {
			return null;
		}
		if (strFormat == null) {
			strFormat = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(strFormat);
		Date newDate = null;
		try {
			newDate = dateFormat.parse(dateValue);
		} catch (ParseException pe) {
			newDate = null;
		}

		return newDate;
	}

	/**
	  * 
	  * 把指定的日期转换成"yyyy-MM-dd"格式，
	  * <p>
	  *   传入参数：
	  *     aTs_Datetime：要格式化的日期
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
	public static String format(Date aTs_Datetime) {
		return format(aTs_Datetime, "yyyy-MM-dd");
	}

	/**
	  * 
	  * 把指定的时间转换成"yyyy-MM-dd HH:mm:ss"格式，
	  * <p>
	  *   传入参数：
	  *     aTs_Datetime：要格式化的时间
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
	public static String formatTime(Date aTs_Datetime) {
		return format(aTs_Datetime, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	  * 
	  * 把指定的时间转换成指定格式，如果没指定时间或格式，则返回空
	  * <p>
	  *   传入参数：
	  *     aTs_Datetime：要格式化的时间
	  *     as_Pattern：时间格式
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
	public static String format(Date aTs_Datetime, String as_Pattern) {
		if ((aTs_Datetime == null) || (as_Pattern == null)) {
			return null;
		}
		SimpleDateFormat dateFromat = new SimpleDateFormat();
		dateFromat.applyPattern(as_Pattern);

		return dateFromat.format(aTs_Datetime);
	}

	/**
	  * 
	  * 把指定的时间转换成指定格式，如果没指定时间或格式，则返回空
	  * <p>
	  *   传入参数：
	  *     aTs_Datetime：要格式化的时间
	  *     as_Format：时间格式
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
	public static String formatTime(Date aTs_Datetime, String as_Format) {
		if ((aTs_Datetime == null) || (as_Format == null)) {
			return null;
		}
		SimpleDateFormat dateFromat = new SimpleDateFormat();
		dateFromat.applyPattern(as_Format);

		return dateFromat.format(aTs_Datetime);
	}

	/**
	  * 
	  * 把指定的时间转换成"HH:mm:ss"格式，
	  * <p>
	  *   传入参数：
	  *     dateTime：要格式化的时间
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
	public static String getFormatTime(Date dateTime) {
		return formatTime(dateTime, "HH:mm:ss");
	}
	/**
	  * 
	  * 格式化时间戳
	  * <p>
	  *   传入参数：
	  *     aTs_Datetime:被操作的时间戳
	  *     as_Pattern:指定的格式
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
	public static String format(Timestamp aTs_Datetime, String as_Pattern) {
		if ((aTs_Datetime == null) || (as_Pattern == null)) {
			return null;
		}
		SimpleDateFormat dateFromat = new SimpleDateFormat();
		dateFromat.applyPattern(as_Pattern);

		return dateFromat.format(aTs_Datetime);
	}
	/**
	  * 
	  * 为指定的日期增加若干天，
	  * <p>
	  *   传入参数：
	  *     date：被操作的日期，
	  *     days:累加的天数（可为正数，负数，0），
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
	public static Date addDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		cal.add(5, days);

		return cal.getTime();
	}
	/**
	  * 
	  * 为指定的日期增加若干小时，
	  * <p>
	  *   传入参数：
	  *     date：被操作的日期，
	  *     hour:累加的小时数（可为正数，负数，0），
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
	public static Date addHour(Date date, int hour) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		cal.add(11, hour);

		return cal.getTime();
	}
	/**
	  * 
	  * 求两个日期相隔的天数（例如date1是2011-04-05，Date2是2011-05-05，结果为正数），
	  * <p>
	  *   传入参数：
	  *     date1:日期1
	  *     date2:日期2
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
	public static int daysBetween(Date date1, Date date2) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date1);
		long time1 = cal.getTimeInMillis();
		cal.setTime(date2);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / 86400000L;

		return Integer.parseInt(String.valueOf(between_days));
	}
	/**
	  * 
	  * 求两个日期相隔的年数（例如date1是2009-04-05，Date2是2011-05-05，结果为正数），
	  * <p>
	  *   传入参数：
	  *     date1:日期1
	  *     date2:日期2
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
	public static int yearsBetween(Date date1, Date date2) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date1);
		int year1 = cal.get(1);
		cal.setTime(date2);
		int year2 = cal.get(1);

		return Integer.parseInt(String.valueOf(year2 - year1));
	}
	/**
	  * 
	  * 求指定日期与"1977-12-01"相隔的天数，
	  * <p>
	  *   传入参数：
	  *     date:指定的日期
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
	public static long getRelativeDays(Date date) {
		Date relativeDate = parseDate("yyyy-MM-dd", "1977-12-01");

		return daysBetween(relativeDate, date);
	}
	
	/**
	  * 
	  * 求十二个月前的当月的第一天，
	  * <p>
	  *   传入参数：
	  *     无
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
	public static Date getDateBeforTwelveMonth() {
		String date = "";
		Calendar cla = Calendar.getInstance();
		cla.setTime(getCurrentDate());
		int year = cla.get(1) - 1;
		int month = cla.get(2) + 1;
		if (month > 9) {
			date = String.valueOf(year) + "-" + String.valueOf(month) + "-" + "01";
		} else {
			date = String.valueOf(year) + "-" + "0" + String.valueOf(month) + "-" + "01";
		}

		Date dateBefore = parseDate(date);
		return dateBefore;
	}

	/**
	  * 
	  * 为指定的日期增加1天，
	  * <p>
	  *   传入参数：
	  *     date：被操作的日期，
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
	public static Date addDate(String date) {
		if (date == null) {
			return null;
		}

		Date tempDate = parseDate("yyyy-MM-dd", date);
		String year = format(tempDate, "yyyy");
		String month = format(tempDate, "MM");
		String day = format(tempDate, "dd");

		GregorianCalendar calendar = new GregorianCalendar(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer
				.parseInt(day));

		calendar.add(5, 1);
		return calendar.getTime();
	}
	/**
	  * 
	  * 为指定的日期增加若干月，
	  * <p>
	  *   传入参数：
	  *     date：被操作的日期，
	  *     count:累加的月数（可为正数，负数，0），
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
	public static Date addMonth(String date, int count) {
		if (date == null) {
			return null;
		}
		Date tempDate = parseDate("yyyy-MM-dd", date);
		String year = format(tempDate, "yyyy");
		String month = format(tempDate, "MM");
		String day = format(tempDate, "dd");

		GregorianCalendar calendar = new GregorianCalendar(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer
				.parseInt(day));

		calendar.add(2, count);
		return calendar.getTime();
	}
	
	/**
	  * 
	  * 为指定的日期增加若干年，
	  * <p>
	  *   传入参数：
	  *     date：被操作的日期，
	  *     count:累加的年数（可为正数，负数，0），
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
	public static Date addYear(String date, int count) {
		if (date == null) {
			return null;
		}
		Date tempDate = parseDate("yyyy-MM-dd", date);
		String year = format(tempDate, "yyyy");
		String month = format(tempDate, "MM");
		String day = format(tempDate, "dd");

		GregorianCalendar calendar = new GregorianCalendar(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer
				.parseInt(day));
		calendar.add(1, count);
		return calendar.getTime();
	}

	public static void main(String[] args) {
		Date date = addMonth(getCurrentDateStr(), -1);
		System.out.println(formatTime(addHour(date, 1)));
	}
}