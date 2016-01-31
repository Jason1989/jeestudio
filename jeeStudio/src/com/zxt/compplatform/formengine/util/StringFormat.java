package com.zxt.compplatform.formengine.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串格式化工具类
 * 
 * @author 007
 */
public class StringFormat {

	/**
	 * 替换字符串中的空格
	 * 
	 * @param string
	 * @return
	 */
	public static String replaceBlank(String string) {

		Pattern p = Pattern.compile("\r|\n");
		Matcher m = p.matcher(string);
		String formatString = m.replaceAll("");

		return formatString;
	}

	public static void main(String[] args) {
		replaceBlank("I am a, I am Hello ok, \n new line ffdsa!");
	}

}
