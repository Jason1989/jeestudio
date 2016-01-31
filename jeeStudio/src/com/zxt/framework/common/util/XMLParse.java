package com.zxt.framework.common.util;

import java.io.IOException;
import java.io.StringReader;

import org.dom4j.DocumentHelper;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class XMLParse {
	public static org.jdom.Document jdomXmlToDoc(String xmlDoc) {
		try {
			if(xmlDoc != null && !xmlDoc.equals("")){
				org.jdom.Document doc = new SAXBuilder().build(new StringReader(xmlDoc));
				return doc;
			}
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public static org.dom4j.Document dom4jXmlToDoc(String xmlDoc) {
		try {
			//return new SAXReader().read(new StringReader(xmlDoc));// 从STRING对象读入XML数据

			return DocumentHelper.parseText(xmlDoc);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
