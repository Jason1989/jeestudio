package com.zxt.compplatform.skip.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.zxt.compplatform.skip.constant.ConstantForSkip;
import com.zxt.compplatform.skip.entity.MenuSetting;
import com.zxt.compplatform.skip.entity.UserDeskSetVo;

public class DeskTopSetUtil {

	public static int saveToXmlFile(UserDeskSetVo userDeskSetVo) {

		XStream stream = new XStream();
		File file = new File(ConstantForSkip.XML_ROOT_ADDRESS
				+ ConstantForSkip.XML_NAME_USERSET);
		try {
			boolean fileCreate = true;
			if (!file.exists()) {
				fileCreate = file.createNewFile();
				//System.out.println(fileCreate);
				if (!fileCreate) {
					return 0;
				}
			}
			OutputStream out = new FileOutputStream(file);
			OutputStreamWriter writer = new OutputStreamWriter(out, Charset
					.forName("GBK"));
			writer.write("\n");
			stream.alias("userDeskSetVo", UserDeskSetVo.class);
			stream.alias("menuSetting", MenuSetting.class);
			stream.toXML(userDeskSetVo, writer);
			out.close();
			return 1;
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}
	}

	/**
	 * xml 文件转对象
	 * @return
	 */
	public static UserDeskSetVo xmlToUserDeskSetVo() {

		XStream stream = new XStream(new DomDriver());
		UserDeskSetVo userDeskSetVo = null;
		File file = new File(ConstantForSkip.XML_ROOT_ADDRESS
				+ ConstantForSkip.XML_NAME_USERSET);
		BufferedReader reader;
		try {
			if (!file.exists()) {
				file.createNewFile();
				return null;
			}
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), "GBK"));
			stream.alias("userDeskSetVo", UserDeskSetVo.class);
			stream.alias("menuSetting", MenuSetting.class);
			stream.alias("menuSettings", ArrayList.class);
			userDeskSetVo = (UserDeskSetVo) stream.fromXML(reader);
			reader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userDeskSetVo;
	}

	/**
	 * XML字符串转对象
	 * 
	 * @return
	 */
	public static List settingToUserSet(String xml) {

		XStream stream = new XStream(new DomDriver());
		List list = null;
		try {
			stream.alias("List", ArrayList.class);
			stream.alias("menuSetting", MenuSetting.class);
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
	public static String saveToXmlString(List list) {
		XStream stream = new XStream();
		try {
			stream.alias("List", ArrayList.class);
			stream.alias("menuSetting", MenuSetting.class);
			return stream.toXML(list);
		} catch (Exception e) {
			// TODO: handle exception
			return "";
		}
	}

	public static void main(String[] args) {
		UserDeskSetVo userDeskSetVo = new UserDeskSetVo();
		MenuSetting menuSetting1 = new MenuSetting();
		menuSetting1.setIoc("assets/images/icons/icon_32_computer.png");
		menuSetting1.setStyle("left:20px;top:20px;");
		menuSetting1.setUrl("con_dock_computer");
		MenuSetting menuSetting2 = new MenuSetting();
		MenuSetting menuSetting3 = new MenuSetting();
		MenuSetting menuSetting4 = new MenuSetting();
		List menuSetList = new ArrayList();
		userDeskSetVo.setMenuSettings(menuSetList);
		userDeskSetVo.getMenuSettings().add(menuSetting1);
		userDeskSetVo.getMenuSettings().add(menuSetting2);
		userDeskSetVo.getMenuSettings().add(menuSetting3);
		userDeskSetVo.getMenuSettings().add(menuSetting4);
		userDeskSetVo.setSystemId("1");
		saveToXmlFile(userDeskSetVo);
		xmlToUserDeskSetVo();
	}
}
