package com.zxt.compplatform.codegenerate.util;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import com.zxt.compplatform.formengine.entity.view.ColumnData;
import com.zxt.compplatform.formengine.service.ComponentsService;
import com.zxt.framework.common.util.SpringContextUtil;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class InputTypeTag implements TemplateDirectiveModel  {

	public void execute(Environment env, Map params, TemplateModel[] loopVars,   
            TemplateDirectiveBody body) throws TemplateException, IOException {
		ComponentsService componentService = (ComponentsService) SpringContextUtil.getBean("zxtcomponentsService");
		int type = ColumnData.DATACOLUMN_UI_TYPE_INPUT;
		try{
	       type = Integer.parseInt(params.get("type")+"");
		}catch(Exception e){
//	       throw new CodeGenerateException("控件类型不符合要求！");
		}
	    String name = params.get("name")+"";
	    String value = params.get("value")+"";
	    String dictionaryId = params.get("dictionaryId")+"";
	    StringBuffer sb = new StringBuffer();
	    if(type == ColumnData.DATACOLUMN_UI_TYPE_INPUT){
	    	env.getOut().write("<input class=\"easyui-validatebox\" name=\""+name+"\" value=\""+value+"\" required=\"true\" validType=\"length[1,32]\">&nbsp;&nbsp;<font size=\"2\" color=\"red\">*</font>");
	    }else if(type == ColumnData.DATACOLUMN_UI_TYPE_COMBOBOX){
	    	sb.append("<select id=\"cc\" class=\"easyui-combobox\" name=\""+name+"\" value=\""+value+"\">");
	    	if(null != dictionaryId && !"".equals(dictionaryId)){
	    		Map map = componentService.load_Dictionary(dictionaryId);
	    		Iterator mapIt = map.entrySet().iterator();
	    		while(mapIt.hasNext()){
	    			Map.Entry entry = (Map.Entry)mapIt.next();
	    			sb.append("<option value="+entry.getKey()+">"+entry.getValue()+"</option>");
	    		}
	    	}
	    	sb.append("</select>");
	    	env.getOut().write(sb.toString());
	    }else if(type == ColumnData.DATACOLUMN_UI_TYPE_NUMBER){
	    	env.getOut().write("<input id=\"nn\" name=\""+name+"\" value=\""+value+"\" class=\"easyui-numberbox\" required=\"true\"/>");
	    }else if(type == ColumnData.DATACOLUMN_UI_TYPE_RADIO){
	    	if(null != dictionaryId && !"".equals(dictionaryId)){
	    		Map map = componentService.load_Dictionary(dictionaryId);
	    		Iterator mapIt = map.entrySet().iterator();
	    		while(mapIt.hasNext()){
	    			Map.Entry entry = (Map.Entry)mapIt.next(); 
	    			sb.append("<input name=\""+name+"\" type=\"radio\" value=\""+entry.getKey()+"\"/>+entry.getValue()");
	    		}
	    	}
	    	env.getOut().write(sb.toString());
	    }else if(type == ColumnData.DATACOLUMN_UI_TYPE_CHECKBOX){
	    	if(null != dictionaryId && !"".equals(dictionaryId)){
	    		Map map = componentService.load_Dictionary(dictionaryId);
	    		Iterator mapIt = map.entrySet().iterator();
	    		while(mapIt.hasNext()){
	    			Map.Entry entry = (Map.Entry)mapIt.next();
	    			sb.append("<input name=\""+name+"\" type=\"checkBox\" value=\""+entry.getKey()+"\"/>+entry.getValue()");
	    		}
	    	}
	    	env.getOut().write(sb.toString());
	    }else
	    env.getOut().write("<input class=\"easyui-validatebox\" name=\""+name+"\" value=\""+value+"\" required=\"true\" validType=\"length[1,32]\">&nbsp;&nbsp;<font size=\"2\" color=\"red\">*</font>");
	}

	

}
