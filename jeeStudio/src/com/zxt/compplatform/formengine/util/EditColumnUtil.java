package com.zxt.compplatform.formengine.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.zxt.compplatform.formengine.constant.Constant;
import com.zxt.compplatform.formengine.entity.view.EditColumn;
import com.zxt.compplatform.formengine.entity.view.EditPage;

/**
 * 编辑列操作工具类
 * 
 * @author 007
 */
public class EditColumnUtil {
	/**
	 * 表单赋值
	 * 
	 * @param request
	 * @param editColumnMap
	 * @param key
	 * @return
	 */
	public static String cloumnSetValue(HttpServletRequest request,
			Map<String, EditColumn> editColumnMap, String key) {

		EditColumn editColumn = editColumnMap.get(key);
		int columnType = -1;
		String value = "";
		String[] parameterValues = null;
		String type = editColumn.getFieldDataType();
		// 设置控件类型
		if ("".equals(editColumn.getType()) || (editColumn.getType() == null)) {
			columnType = 0;
		} else {
			columnType = Integer.parseInt(editColumn.getType());
		}

		switch (columnType) {
		case Constant.FORM_FIELD_TYPE_TEXT:
			value = request.getParameter(key);
			break;
		case Constant.FORM_FIELD_TYPE_SELECT:
			value = request.getParameter(key);
			break;
		case Constant.FORM_FIELD_TYPE_CHECKBOX:
			
				parameterValues = request.getParameterValues(key);
				for (int i = 0; i < parameterValues.length; i++) {
					if (i == 0) {
						value = value + parameterValues[i];
					} else {
						value = value + "," + parameterValues[i];
					}
				}
	
			break;
		case Constant.FORM_FIELD_TYPE_AJAXBOX_TREE:
			value = request.getParameter(key);
			if ("".equals(value) || (value == null)) {
				value = "0";
			}
			break;
		default:
			value = request.getParameter(key);
			if (value == null) {
				value = Constant.DB_FIELD_DEFAULT_VALUE;
			}
			break;
		}
		return value;
	}

	/**
	 * 将表单编辑列转换成map
	 * @param editPage
	 * @return
	 */
	public static Map<String, EditColumn> transformEditCloumnListToMap(
			EditPage editPage) {
		Map<String, EditColumn> map = new HashMap<String, EditColumn>();
		List<EditColumn> list = null;
		if (editPage != null) {
			list = editPage.getEditColumn();
			for (int i = 0; i < list.size(); i++) {
				map.put(list.get(i).getName(), list.get(i));
			}
		}

		return map;
	}

}
