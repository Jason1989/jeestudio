package com.zxt.compplatform.formengine.engine;

/**
 * 条件工厂
 * 
 * @author 007
 */
public class ConditionFactory {

	/**
	 * 根据不同的字符串返回不同的表单转化器实体
	 * 
	 * @param str
	 * @return
	 */
	public static IPageTransFer creator(String str) {
		if (str.equals("listPage")) {
			return new ListPageTransFer();
		} else if (str.equals("viewPage")) {
			return new ViewPageTransFer();
		} else if (str.equals("editPage")) {
			return new EditPageTransFer();
			// gongchang
		}
		return null;
	}

}
