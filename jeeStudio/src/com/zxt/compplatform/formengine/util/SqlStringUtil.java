package com.zxt.compplatform.formengine.util;

/**
 * 原生sql处理工具类
 * 
 * @author 007
 */
public class SqlStringUtil {
	/**
	 * 替换 变量'$xxx$' 为 ?
	 * 
	 * @param sql
	 * @return
	 */
	public static String spliceParmer(String selectSql) {

		// String selectSql=" select ENG_FORM_TAB.PAGE_ID as PAGE_ID ,
		// ENG_FORM_TAB.TAB_ID as TAB_ID , ENG_FORM_TAB.TAB_LAZYLOADING as
		// TAB_LAZYLOADING , ENG_FORM_TAB.TAB_STYLE as TAB_STYLE ,
		// ENG_FORM_TAB.TAB_TITLE as TAB_TITLE , ENG_FORM_FORM.FO_DESCRIPTION as
		// FO_DESCRIPTION , ENG_FORM_FORM.FO_DO_ID as FO_DO_ID ,
		// ENG_FORM_FORM.FO_FTYPE as FO_FTYPE , ENG_FORM_FORM.FO_ID as FO_ID ,
		// ENG_FORM_FORM.FO_NAME as FO_NAME , ENG_FORM_FORM.FO_SETTINGS as
		// FO_SETTINGS , ENG_FORM_FORM.FO_SORTINDEX as FO_SORTINDEX ,
		// ENG_FORM_FORM.FO_STATE as FO_STATE from ENG_FORM_TAB,ENG_FORM_FORM
		// where 1=1 and ENG_FORM_FORM.FO_ID = '$PFO_ID$' and
		// ENG_FORM_TAB.TAB_ID = '$PTAB_ID$' and ENG_FORM_FORM.FO_NAME = '7' and
		// ENG_FORM_FORM.FO_STATE = ENG_FORM_TAB.TAB_ID";

		String returnSql = "";// 替换后的sql
		String a[] = null;// 存储所有条件

		if (selectSql.indexOf("1=1") < 0) {
			return selectSql;
		} else {
			returnSql = selectSql.substring(0, selectSql.indexOf("1=1") + 3);

			selectSql = selectSql.substring(selectSql.indexOf("1=1") + 3);
			selectSql = selectSql.substring(selectSql.indexOf("and") + 3);
			String array[] = selectSql.split("and");

			String temString[] = null;
			a = new String[array.length];
			for (int i = 0; i < array.length; i++) {

				temString = array[i].split("=");
				if (temString[1].indexOf("$") >= 0) {
					returnSql = returnSql + " and " + temString[0] + "=? ";
				} else {
					returnSql = returnSql + " and " + array[i];
				}
			}

		}

		return returnSql;
	}
}
