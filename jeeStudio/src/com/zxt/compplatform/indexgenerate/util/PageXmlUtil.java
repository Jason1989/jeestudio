package com.zxt.compplatform.indexgenerate.util;

import java.util.HashMap;
import java.util.Map;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.zxt.compplatform.indexset.entity.ModelPart;



public class PageXmlUtil {
	/**
	 * XML字符串转对象
	 * 
	 * @return
	 */
	public static Map xmlToPage(String xml) {
		
		XStream stream = new XStream(new DomDriver());
		Map map = null;
		try {
			stream.alias("map", HashMap.class);
			stream.alias("key", String.class);
			stream.alias("model", ModelPart.class);
			map = (Map) stream.fromXML(xml);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;
	}

	/**
	 * 对象转XML字符串
	 * 
	 * @param userDeskSetVo
	 * @return
	 */
	public static String PageToxml(Map map) {
		XStream stream = new XStream();
		try {
			stream.alias("map", HashMap.class);
			stream.alias("key", String.class);
			stream.alias("model", ModelPart.class);
			return stream.toXML(map);
		} catch (Exception e) {
			return "";
		}
	}
	
	
	
	/**
	 * new XML Format
	 */
	/*
	 
	 <map>
	  <entry>
	    <key>divID</key>
	    <model id="0">
	      <name>jack</name>
	      <role>
	    </student>
	  </entry>
	  <entry>
	    <key>No.1</key>
	    <student id="1">
	      <name>jack</name>
	      <email>jack@email.com</email>
	      <address>china</address>
	      <birthday birthday="2010-11-22"/>
	    </student>
	  </entry>
	 </map>
	 */
	
	/**
	 * the old XML Format
	 */
	/*
	 * 
	 <list>
		<DivUnit>
			<region></region>
			<width></width>
			<height></height>
	
			<model>
				<name></name>
				<url></url>
			</model>
			
			<list>
				<DivUnit></DivUnit>
			</list>	//可以有多个DivUnit,DivUnit与model不共存
		</DivUnit>
		<DivUnit></DivUnit>
	</list> 
	 * 
	 */
}
