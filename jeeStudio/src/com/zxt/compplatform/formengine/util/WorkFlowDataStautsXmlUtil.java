package com.zxt.compplatform.formengine.util;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.zxt.compplatform.workflow.entity.WorkFlowDataStauts;

public class WorkFlowDataStautsXmlUtil {
	private static final Logger log = Logger.getLogger(WorkFlowDataStautsXmlUtil.class);
	public static String workFlowDataStautsToXml(
			WorkFlowDataStauts workFlowDataStauts) {
		XStream stream = new XStream();
		try {
			stream.alias("workFlowDataStauts", WorkFlowDataStauts.class);
			return stream.toXML(workFlowDataStauts);
		} catch (Exception e) {
			return "";
		}

	}

	public static WorkFlowDataStauts xmlToWorkFlowDataStauts(String xml) {
		WorkFlowDataStauts workFlowDataStauts = null;
		XStream stream = new XStream(new DomDriver());
		try {
			stream.alias("workFlowDataStauts", WorkFlowDataStauts.class);
			workFlowDataStauts = (WorkFlowDataStauts) stream.fromXML(xml);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//log.error("工作流字段配置出错...");
			return null;
		}
		return workFlowDataStauts;
	}

	public static List transferListWorkFlowStauts(List list) {
		String mid = "-1";
		String status = "-1";
		Map map = null;
		WorkFlowDataStauts workFlowDataStauts = null;
		for (int i = 0; i < list.size(); i++) {
			map = (Map) list.get(i);
			if (map.get("ENV_DATAMETER") != null) {
				workFlowDataStauts = xmlToWorkFlowDataStauts(map.get(
						"ENV_DATAMETER").toString());
				if (workFlowDataStauts==null) {
					continue;
				}
				((Map) list.get(i)).put("eng_envmid", workFlowDataStauts.getMid());
				((Map) list.get(i)).put("eng_envstatus", workFlowDataStauts
						.getToTransferDefStautsValue());
			}

		}
		return list;
	}
}
