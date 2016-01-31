package com.zxt.compplatform.formengine.util;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.zxt.compplatform.formengine.entity.view.AuthorityButton;
import com.zxt.compplatform.formengine.entity.view.EventForButton;
import com.zxt.compplatform.formengine.entity.view.JSFunction;
import com.zxt.compplatform.formengine.entity.view.Param;

/**
 * 按钮xml操作工具类
 * 
 * @author 007
 */
public class ButtonXmlUtil {
	/**
	 * XML字符串转对象
	 * 
	 * @return
	 */
	public static List xmlToAuthorityButtonList(String xml) {

		XStream stream = new XStream(new DomDriver());
		List list = null;
		try {
			stream.alias("List", ArrayList.class);
			stream.alias("AuthorityButton", AuthorityButton.class);
			stream.alias("EventForButton", EventForButton.class);
			stream.alias("JSFunction", JSFunction.class);
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
	public static String authorityButtonListToxml(List list) {
		XStream stream = new XStream();
		try {
			stream.alias("List", ArrayList.class);
			stream.alias("AuthorityButton", AuthorityButton.class);
			stream.alias("EventForButton", EventForButton.class);
			stream.alias("JSFunction", JSFunction.class);
			stream.alias("Param", Param.class);
			return stream.toXML(list);
		} catch (Exception e) {
			return "";
		}
	}

}
