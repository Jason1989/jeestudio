package com.zxt.framework.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.MessageFormat;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import com.zxt.framework.common.exceptions.AppException;
/**
 * 
 * 字符串的工具类
 * <p>
 *     containValue 判断一个int数组是否包含某个值，
 *     linkString 将字符串数组和某个字符串拼接成字符串，并去掉最后一次拼接的字符串，例如字符串数组为{"a","b","c"},字符串为"-",结果为"a-b-c"，
 *     linkString 将list和某个字符串拼接成字符串，并去掉最后一次拼接的字符串，
 *     isNull 判断某个字符串是否为null，
 *     isEmpty 判断某个字符串是否为空，
 *     isEmptyTrim 判断某个字符串是否为空字符串(由空格构成)，
 *     isNotNull 判断某个字符串是否不为null，
 *     isNotEmpty 判断某个字符串是否非空，
 *     isNotEmptyTrim 判断某个字符串是否不为空字符串，
 *     link 拼接两个字符串，
 *     link 拼接三个字符串，
 *     link 拼接四个字符串，
 *     link 拼接五个字符串，
 *     linkByStringBuffer 把字符串数组拼接成字符串，
 *     toList 点击数据对象菜单显示数据源列表页面，
 *     format 点击数据对象菜单显示数据源列表页面，
 *     format 点击数据对象菜单显示数据源列表页面，
 *     format 点击数据对象菜单显示数据源列表页面，
 *     format 点击数据对象菜单显示数据源列表页面，
 *     format 点击数据对象菜单显示数据源列表页面，
 *     countMatches 获得字符串匹配次数，
 *     replaceAny 替换一个字符串里指定的字符串，，
 *     replaceAny 点击数据对象菜单显示数据源列表页面，
 *     toString 点击数据对象菜单显示数据源列表页面，
 *     toByteArray 点击数据对象菜单显示数据源列表页面，
 *     toString 点击数据对象菜单显示数据源列表页面，
 *     toObject 点击数据对象菜单显示数据源列表页面，
 *     toUpperCaseFirst 点击数据对象菜单显示数据源列表页面，
 *     format 把整型转换成字符串，
 *     修改日志说明：<br>
 *          1、Sep 14, 2011 3:56:35 PM（+） 添加注释
 *          2、
 * </p>
 * @author 005
 * @version 1.00
 */
public class StringUtils {
	/**
	  * 
	  * 判断一个int数组是否包含某个值，
	  * <p>
	  *   传入参数：
	  *     compareValue:要包含的值
	  *     intArray:被筛选的数组
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
	public static boolean containValue(int compareValue, int[] intArray) {
		if (intArray == null)
			return false;
		for (int j = 0; j < intArray.length; ++j){
			if (intArray[j] == compareValue)
				return true;
		}
		return false;
	}
	/**
	  * 
	  * 将字符串数组和某个字符串拼接成字符串，并去掉最后一次拼接的字符串
	  * 例如字符串数组为{"a","b","c"},字符串为"-",结果为"a-b-c"
	  * <p>
	  *   传入参数：
	  *     page:页号
	  *     rows:每页显示数据条数
	  *     
	  *   传出参数（名称/类型）：
	  *     1. dataSourceJson/json 
	  *     {"name":"hsl","id":""}或者"exist"[存在]、"unexist"[不存在](中括号解释返回字符串的意义)
	  *     
	  * action访问地址： (相对于当前项目的地址) system/login_login.action
	  * 
	  * 修改记录：
	  *     1. Sep 14, 2011 005 操作说明
	  * </p>
	 */
	public static String linkString(String[] strs, String linkStr) {
		if ((null == strs) || (null == linkStr))
			return null;
		StringBuffer resultStrBuf = new StringBuffer();
		for (int i = 0; i < strs.length; ++i) {
			if (strs[i] != null) {
				resultStrBuf.append(strs[i]);
				resultStrBuf.append(linkStr);
			}
		}
		String linkResult = resultStrBuf.toString();
		int removeLink = linkResult.length() - linkStr.length();
		return (removeLink > 0) ? linkResult.substring(0, removeLink) : "";
	}
	/**
	  * 
	  * 将list和某个字符串拼接成字符串，并去掉最后一次拼接的字符串
	  * 例如list为["a","b","c"],字符串为"-",结果为"a-b-c"
	  * <p>
	  *   传入参数：
	  *     page:页号
	  *     rows:每页显示数据条数
	  *     
	  *   传出参数（名称/类型）：
	  *     1. dataSourceJson/json 
	  *     {"name":"hsl","id":""}或者"exist"[存在]、"unexist"[不存在](中括号解释返回字符串的意义)
	  *     
	  * action访问地址： (相对于当前项目的地址) system/login_login.action
	  * 
	  * 修改记录：
	  *     1. Sep 14, 2011 005 操作说明
	  * </p>
	 */
	public static String linkString(List strList, String linkStr) {
		if ((null == strList) || (null == linkStr)) {
			return null;
		}
		StringBuffer resultStrBuf = new StringBuffer();
		Iterator it = strList.iterator();
		while (it.hasNext()) {
			Object obj = it.next();
			if (obj instanceof String) {
				resultStrBuf.append((String) obj);
				resultStrBuf.append(linkStr);
			}
		}
		String linkResult = resultStrBuf.toString();
		return linkResult.substring(0, linkResult.length() - linkStr.length());
	}
	/**
	  * 
	  * 判断某个字符串是否为null，
	  * <p>
	  *   传入参数：
	  *     str:被操作的字符串
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
	public static boolean isNull(String str) {
		return str == null;
	}

	/**
	  * 
	  * 判断某个字符串是否为空，
	  * <p>
	  *   传入参数：
	  *     str:被操作的字符串
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
	public static boolean isEmpty(String str) {
		return (str == null) || (str.length() == 0);
	}

	/**
	  * 
	  * 判断某个字符串是否为空或空字符串(由空格构成)，
	  * <p>
	  *   传入参数：
	  *     str:被操作的字符串
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
	public static boolean isEmptyTrim(String str) {
		return (str == null) || (isEmpty(str.trim()));
	}
	/**
	  * 
	  * 判断某个字符串是否不为null，
	  * <p>
	  *   传入参数：
	  *     str:被操作的字符串
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
	public static boolean isNotNull(String str) {
		return !isNull(str);
	}

	/**
	  * 
	  * 判断某个字符串是否非空，
	  * <p>
	  *   传入参数：
	  *     str:被操作的字符串
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
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	  * 
	  * 判断某个字符串是否不为空字符串，
	  * <p>
	  *   传入参数：
	  *     str:被操作的字符串
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
	public static boolean isNotEmptyTrim(String str) {
		return !isEmptyTrim(str);
	}
	/**
	  * 
	  * 拼接两个字符串，
	  * <p>
	  *   传入参数：
	  *     str:第一个字符串
	  *     str2:第二个字符串
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
	public static String link(String str, String str2) {
		String[] strs = new String[2];
		strs[0] = str;
		strs[1] = str2;
		return linkByStringBuffer(strs);
	}

	/**
	  * 
	  * 拼接三个字符串，
	  * <p>
	  *   传入参数：
	  *     str:第一个字符串
	  *     str2:第二个字符串
	  *     str3:第三个字符串
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
	public static String link(String str, String str2, String str3) {
		String[] strs = new String[3];
		strs[0] = str;
		strs[1] = str2;
		strs[2] = str3;
		return linkByStringBuffer(strs);
	}

	/**
	  * 
	  * 拼接四个字符串，
	  * <p>
	  *   传入参数：
	  *     str:第一个字符串
	  *     str2:第二个字符串
	  *     str3:第三个字符串
	  *     str4:第四个字符串
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
	public static String link(String str, String str2, String str3, String str4) {
		String[] strs = new String[4];
		strs[0] = str;
		strs[1] = str2;
		strs[2] = str3;
		strs[3] = str4;
		return linkByStringBuffer(strs);
	}

	/**
	  * 
	  * 拼接五个字符串，
	  * <p>
	  *   传入参数：
	  *     str:第一个字符串
	  *     str2:第二个字符串
	  *     str3:第三个字符串
	  *     str4:第四个字符串
	  *     str5:第五个字符串
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
	public static String link(String str, String str2, String str3, String str4, String str5) {
		String[] strs = new String[5];
		strs[0] = str;
		strs[1] = str2;
		strs[2] = str3;
		strs[3] = str4;
		strs[4] = str5;
		return linkByStringBuffer(strs);
	}
	/**
	  * 
	  * 把字符串数组拼接成字符串，
	  * <p>
	  *   传入参数：
	  *     strs:要拼接的字符串数组
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
	public static String linkByStringBuffer(String[] strs) {
		StringBuffer sbf = new StringBuffer();
		for (int i = 0; (strs != null) && (i < strs.length); ++i) {
			if (!isEmpty(strs[i])) {
				sbf.append(strs[i]);
			}
		}
		return sbf.toString();
	}

	public static String format(String pattern, String str) {
		String[] arrStr = new String[1];
		arrStr[0] = str;
		return MessageFormat.format(pattern, arrStr);
	}

	public static String format(String pattern, String str, String str2) {
		String[] arrStr = new String[2];
		arrStr[0] = str;
		arrStr[1] = str2;
		return MessageFormat.format(pattern, arrStr);
	}

	public static String format(String pattern, String str, String str2, String str3) {
		String[] arrStr = new String[3];
		arrStr[0] = str;
		arrStr[1] = str2;
		arrStr[2] = str3;
		return MessageFormat.format(pattern, arrStr);
	}

	public static String format(String pattern, String str, String str2, String str3, String str4) {
		String[] arrStr = new String[4];
		arrStr[0] = str;
		arrStr[1] = str2;
		arrStr[2] = str3;
		arrStr[3] = str4;
		return MessageFormat.format(pattern, arrStr);
	}

	public static String format(String pattern, String str, String str2, String str3, String str4, String str5) {
		String[] arrStr = new String[5];
		arrStr[0] = str;
		arrStr[1] = str2;
		arrStr[2] = str3;
		arrStr[3] = str4;
		arrStr[4] = str5;
		return MessageFormat.format(pattern, arrStr);
	}
	/**
	  * 
	  * 获得字符串匹配次数，
	  * <p>
	  *   传入参数：
	  *     str:源字符串
	  *     sub:要匹配的字符串
	  *     
	  *   传出参数（名称/类型）：
	  *     1. count/int （匹配次数）
	  *     
	  * action访问地址： 无
	  * 
	  * 修改记录：
	  *     1. Sep 14, 2011 005 添加注释
	  * </p>
	 */
	public static int countMatches(String str, String sub) {
		if ((isEmpty(str)) || (isEmpty(sub)))
			return 0;
		int count = 0;
		for (int idx = 0; (idx = str.indexOf(sub, idx)) != -1; idx += sub.length()) {
			++count;
		}
		return count;
	}
	/**
	  * 
	  * 替换一个字符串里指定的字符串，
	  * <p>
	  *   传入参数：
	  *     str:源字符串
	  *     searchStr:被替换的字符串
	  *     replaceStr:就被告替换成的字符串
	  *     
	  *   传出参数（名称/类型）：
	  *     1. str/String （替换后的字符串）
	  *     
	  * action访问地址： 无
	  * 
	  * 修改记录：
	  *     1. Sep 14, 2011 005 添加注释
	  * </p>
	 */
	public static String replaceAny(String str, String searchStr, String replaceStr) {
		if ((isEmpty(str)) || (isEmpty(searchStr)))
			return str;
		if (replaceStr == null)
			replaceStr = "";
		boolean modified = false;
		StringBuffer buf = new StringBuffer(str.length());
		for (int i = 0; i < str.length(); ++i) {
			char ch = str.charAt(i);
			int index = searchStr.indexOf(ch);
			if (index >= 0) {
				modified = true;
				if (index < replaceStr.length())
					buf.append(replaceStr.charAt(index));
			} else {
				buf.append(ch);
			}
		}

		if (modified) {
			return buf.toString();
		}
		return str;
	}

	public static String replaceAny(String str, String[] searchStr, String replaceStr) {
		String strNew = new String(str);
		for (int i = 0; i < searchStr.length; ++i) {
			strNew = strNew.replaceAll(String.valueOf(searchStr[i]), replaceStr);
		}

		return strNew;
	}

	public static String toString(byte[] arrByte) {
		if (arrByte == null)
			return "";
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < arrByte.length; ++i) {
			buffer.append(arrByte[i] + ",");
		}
		return buffer.toString();
	}

	public static byte[] toByteArray(String str) {
		if (str == null)
			return null;
		String[] arrStr = str.split(",");
		byte[] arrByte = new byte[arrStr.length];
		for (int i = 0; i < arrStr.length; ++i) {
			arrByte[i] = Byte.parseByte(arrStr[i]);
		}
		return arrByte;
	}

	public static String toString(Object obj) throws AppException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		String str = null;
		try {
			ObjectOutputStream oos = new ObjectOutputStream(out);
			oos.writeObject(obj);
			str = toString(out.toByteArray());
			out.close();
			oos.close();
		} catch (IOException e) {
			throw new AppException("0", "�첽�������" + e.getCause(), "");
		}
		ObjectOutputStream oos;
		return str;
	}

	public static Object toObject(String str) throws AppException {
		Object obj = null;
		try {
			InputStream in = new ByteArrayInputStream(toByteArray(str));
			ObjectInputStream oin = new ObjectInputStream(in);
			obj = oin.readObject();
			in.close();
			oin.close();
		} catch (NullPointerException npe) {
			throw new AppException("0", "�첽�������" + npe.getCause());
		} catch (IOException e) {
			throw new AppException("0", "�첽�������" + e.getCause());
		} catch (ClassNotFoundException ce) {
			throw new AppException("0", "�첽�������" + ce.getCause());
		}

		return obj;
	}

	public static String toUpperCaseFirst(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	public static int getTime() {
		GregorianCalendar gc = new GregorianCalendar();
		int month = gc.get(2) + 1;
		int day = gc.get(5);
		int hour = gc.get(11);
		int mits = gc.get(12);
		int secd = gc.get(13);

		int random = Integer.parseInt(format(month) + format(day) + format(hour) + format(mits) + format(secd));

		return random;
	}
	/**
	  * 
	  * 把整型转换成字符串，
	  * <p>
	  *   传入参数：
	  *     i:被转换的整型
	  *     
	  *   传出参数（名称/类型）：
	  *     无
	  *     
	  * action访问地址： 无
	  * 
	  * 修改记录：
	  *     1. Sep 15, 2011 005 添加注释
	  * </p>
	 */
	private static String format(int i) {
		return "" + i;
	}
	
	
	
	public static void main(String[] args) {
		String b[] = {"ccc","ddd"};
		System.out.println(getTime());
	}
}