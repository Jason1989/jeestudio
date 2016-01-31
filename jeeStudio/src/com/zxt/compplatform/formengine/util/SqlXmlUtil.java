package com.zxt.compplatform.formengine.util;

import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.zxt.compplatform.formengine.entity.view.Param;

/**
 * 提取xml为sql参数工具类
 * 
 * @author 007
 */
public class SqlXmlUtil {
	/**
	 * XML字符串转对象
	 * 
	 * @return
	 */
	public static List xmlToSqlParamList(String xml) {

		XStream stream = new XStream(new DomDriver());
		List list = null;
		try {
			stream.alias("Param", Param.class);
			list = (List) stream.fromXML(xml);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * 对象转XML字符串
	 * 
	 * @param userDeskSetVo
	 * @return
	 */
	public static String sqlParamListToxml(List list) {
		XStream stream = new XStream();
		try {
			stream.alias("Param", Param.class);
			return stream.toXML(list);
		} catch (Exception e) {
			return "";
		}
	}

}
