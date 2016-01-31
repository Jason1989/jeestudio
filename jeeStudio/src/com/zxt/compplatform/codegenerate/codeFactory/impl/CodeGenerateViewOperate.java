package com.zxt.compplatform.codegenerate.codeFactory.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zxt.compplatform.codegenerate.codeFactory.CodeGenerateOperate;
import com.zxt.compplatform.codegenerate.codeFactory.CodeUseEntity;
import com.zxt.compplatform.codegenerate.util.CodeGenerateException;
import com.zxt.compplatform.codegenerate.util.CodegenerateUtil;
import com.zxt.compplatform.codegenerate.util.InputTypeTag;
import com.zxt.compplatform.formengine.entity.view.BasePage;
import com.zxt.compplatform.formengine.entity.view.Button;
import com.zxt.compplatform.formengine.entity.view.EditColumn;
import com.zxt.compplatform.formengine.entity.view.EditPage;
import com.zxt.compplatform.formengine.entity.view.Event;
import com.zxt.compplatform.formengine.entity.view.Field;
import com.zxt.compplatform.formengine.entity.view.ListPage;
import com.zxt.compplatform.formengine.entity.view.Param;
import com.zxt.compplatform.formengine.entity.view.QueryColumn;
import com.zxt.compplatform.formengine.entity.view.QueryZone;
import com.zxt.compplatform.formengine.entity.view.ViewColumn;
import com.zxt.compplatform.formengine.entity.view.ViewPage;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

/**
 * 视图层的代码生成工厂
 * @author zxt-hejun
 * @date:2010-9-26 上午09:40:07
 */
public class CodeGenerateViewOperate implements CodeGenerateOperate {

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.codegenerate.codeFactory.CodeGenerateOperate#codeGenerate(com.zxt.compplatform.codegenerate.codeFactory.CodeUseEntity)
	 */
	public boolean codeGenerate(CodeUseEntity codeUseEntity) throws CodeGenerateException {
		try{
			String jarName = codeUseEntity.getJarName();
			String className = codeUseEntity.getClassName();
		    if(null == className){
		    	className = "ZXT";
		    }
		    BasePage basePage = codeUseEntity.getBasePage();
		    ListPage listPage =null;
		    EditPage editPage =null;
		    ViewPage viewPage =null;
		    Configuration cfg = new Configuration();
			cfg.setDirectoryForTemplateLoading(new File(codeUseEntity.getBasePath()+File.separator+"freemarker"+File.separator+"jdbc"));
			cfg.setObjectWrapper(new DefaultObjectWrapper());
			cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
		    if(basePage instanceof ListPage){
		    	listPage = (ListPage) basePage;
		    	editPage = listPage.getEditPage();
		    	viewPage = listPage.getViewPage();
		    	Map map = new HashMap();
		    	//'news/news!getNewsData.action'
		    	List fields = listPage.getFields();
		    	List fieldsView = CodegenerateUtil.copyBySerialize(fields);
		    	Iterator columnDataIt = fieldsView.iterator();
		    	while(columnDataIt.hasNext()){
		    		Field field = (Field)columnDataIt.next();
		    		field.setDataColumn(CodegenerateUtil.transformColumnName(field.getDataColumn()));
		    		field.setWidth(field.getWidth() == null?"100":field.getWidth());
		    	}
		    	map.put("page", "﻿<%@ page language=\"java\" import=\"java.util.*\" pageEncoding=\"UTF-8\"%>");
		    	map.put("fields", fieldsView);
		    	map.put("url", jarName+"/"+className+"!list.action");
		    	map.put("editUrl", jarName+"/"+className+"!goEdit.action");
		    	map.put("viewUrl", jarName+"/"+className+"!goView.action");
		    	map.put("saveUrl", jarName+"/"+className+"!doSave.action");
		    	map.put("deleteUrl", jarName+"/"+className+"!doDelete.action");
		    	map.put("gridButtons", listPage.getGridButton());
		    	map.put("canBatch", listPage.isCanBatch()+"");
		    	map.put("css", listPage.getCss());
		    	map.put("zxtInput", new InputTypeTag());
		    	map.put("coustomCss", listPage.getCoustomCss());
		    	map.put("className", className);
		    	List rowButton = listPage.getRowButton();
		    	List rowButtonView = CodegenerateUtil.copyBySerialize(rowButton);
		    	Iterator rowButtonIt = rowButtonView.iterator();
		    	Set jsParam = new HashSet();
		    	while(rowButtonIt.hasNext()){
		    		Button button = (Button) rowButtonIt.next();
		    		List events = button.getEvent();
		    		Iterator eventsIt = events.iterator();
		    		while(eventsIt.hasNext()){
		    			Event event = (Event) eventsIt.next();
		    			List params = event.getParas();
		    			Iterator paramsIt = params.iterator();
		    			while(paramsIt.hasNext()){
		    				Param param = (Param) paramsIt.next();
		    				String key = CodegenerateUtil.transformColumnName(param.getKey());
		    				param.setKey(key);
		    				Map jsParamName = new HashMap();
				    		jsParamName.put("name", key);
		    				jsParam.add(jsParamName);
		    			}
		    		}
		    	}
		    	map.put("jsParam", jsParam);
		    	map.put("rowButtons", rowButtonView);
		    	map.put("zxtInput", new InputTypeTag());
		    	map.put("canShowPagination", listPage.isCanShowPagination()+"");
		    	List queryZones = listPage.getQueryZone();
		    	boolean isQueryZone = false;
		    	if(null != queryZones){
			    	Iterator queryZonesIt = queryZones.iterator();
			    	while(queryZonesIt.hasNext()){
			    		QueryZone queryZone = (QueryZone) queryZonesIt.next();
			    		List queryColumns = queryZone.getQueryColumns();
			    		Iterator queryColumnsIt = queryColumns.iterator();
			    		map.put("queryColumns", queryColumns);
			    		StringBuffer queryParamsSb = new StringBuffer();
			    		while(queryColumnsIt.hasNext()){
			    			if(!"".equals(queryParamsSb.toString())){
			    				queryParamsSb.append(",");
			    			}
			    			QueryColumn queryColumn = (QueryColumn) queryColumnsIt.next();
			    			String columnName = CodegenerateUtil.transformColumnName(queryColumn.getName());
			    			queryColumn.setName(columnName);
			    			queryParamsSb.append(columnName+":$('#"+columnName+"').val()");
			    			isQueryZone = true;
			    		}
			    		map.put("queryParams", queryParamsSb.toString());
			    		map.put("isQueryZone",isQueryZone+"");
			    	}
		    	}
		    	map.put("isQueryZone",isQueryZone+"");
		    	Template temp = cfg.getTemplate(CodeUseEntity.CODE_GENERATE_VIEW_LIST_FTL,"UTF-8");
				Writer out = new OutputStreamWriter(new FileOutputStream(codeUseEntity.getOutPath()+File.separator+CodegenerateUtil.transformColumnName(className)+"_list.jsp"),"UTF-8");
				temp.process(map,out);
				out.flush();
			   }
		    if(null != editPage || basePage instanceof EditPage){
				if(editPage == null){
		    		editPage =(EditPage) basePage;
		    	}
		    	Map map = new HashMap();
		    	List editColumns = editPage.getEditColumn();
		    	List editColumnView = CodegenerateUtil.copyBySerialize(editColumns);
		    	Iterator columnDatasIt = editColumnView.iterator();
		    	while(columnDatasIt.hasNext()){
		    		EditColumn editColumn = (EditColumn)columnDatasIt.next();
		    		String columnName = CodegenerateUtil.transformColumnName(editColumn.getName());
		    		editColumn.setName(columnName);
		    		editColumn.setData("${field."+columnName+"}");
		    	}
		    	map.put("page", "﻿<%@ page language=\"java\" import=\"java.util.*\" pageEncoding=\"UTF-8\"%>");
		    	map.put("fields", editColumnView);
		    	map.put("type", "${typeMethod}");
		    	map.put("className", className);
		    	map.put("zxtInput", new InputTypeTag());
				Template temp = cfg.getTemplate(CodeUseEntity.CODE_GENERATE_VIEW_EDIT_FTL,"UTF-8");
				Writer out = new OutputStreamWriter(new FileOutputStream(codeUseEntity.getOutPath()+File.separator+CodegenerateUtil.transformColumnName(className)+"_edit.jsp"),"UTF-8");
				temp.process(map,out);
				out.flush();
		    }
		    if(null != viewPage|| basePage instanceof ViewPage){
		    	if(viewPage == null){
		    		viewPage =(ViewPage) basePage;
		    	}
		    	Map map = new HashMap();
		    	List viewColumns = viewPage.getViewColumn();
		    	List viewColumnView = CodegenerateUtil.copyBySerialize(viewColumns);
		    	Iterator columnDatasIt = viewColumnView.iterator();
		    	while(columnDatasIt.hasNext()){
		    		ViewColumn viewColumn = (ViewColumn)columnDatasIt.next();
		    		String columnName = CodegenerateUtil.transformColumnName(viewColumn.getName());
		    		viewColumn.setName(columnName);
		    		viewColumn.setData("${field."+columnName+"}");
		    	}
		    	map.put("page", "﻿<%@ page language=\"java\" import=\"java.util.*\" pageEncoding=\"UTF-8\"%>");
		    	map.put("fields", viewColumnView);
		    	map.put("className", className);
				Template temp = cfg.getTemplate(CodeUseEntity.CODE_GENERATE_VIEW_VIEW_FTL,"UTF-8");
				Writer out = new OutputStreamWriter(new FileOutputStream(codeUseEntity.getOutPath()+File.separator+CodegenerateUtil.transformColumnName(className)+"_view.jsp"),"UTF-8");
				temp.process(map,out);
				out.flush();
		    }
		}catch(Exception e){
			throw new CodeGenerateException(e.getMessage());
		}
		return false;
	}

}
