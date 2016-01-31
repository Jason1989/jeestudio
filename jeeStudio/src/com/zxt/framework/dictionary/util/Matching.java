package com.zxt.framework.dictionary.util;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类 匹配带星号的字符串
 * 
 * @author Dexpo
 * 
 */
public class Matching {

	/**
	 * 过滤字符串
	 * 
	 * @param source
	 * @param regular
	 * @return
	 */
	public String match(List source, String regular) {
		Map m = null;
		StringBuffer s = new StringBuffer();
		for (int i = 0; i < source.size(); i++) {
			m = (Map) source.get(i);
			String actstr = (String) m.get("name");
			Pattern p = Pattern.compile(regular);
			Matcher mat = p.matcher(actstr);
			if (mat.matches()) {
				//System.out.println(actstr + "匹配成功");
				s.append(actstr + ",");
			} else {
				//System.out.println(actstr + "匹配失败");
			}
		}
		return s.toString();
	}
}
