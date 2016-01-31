package com.zxt.compplatform.codegenerate.codeFactory.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.zxt.compplatform.formengine.entity.view.BasePage;
import com.zxt.compplatform.formengine.entity.view.EditPage;
import com.zxt.compplatform.formengine.entity.view.ListPage;
import com.zxt.compplatform.formengine.entity.view.Param;
import com.zxt.compplatform.formengine.entity.view.ViewPage;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

/**
 * 控制器层的代码生成工厂
 * @author zxt-hejun
 * @date:2010-9-26 上午09:38:51
 */
public class CodeGenerateActionOperate  implements CodeGenerateOperate {

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.codegenerate.codeFactory.CodeGenerateOperate#codeGenerate(com.zxt.compplatform.codegenerate.codeFactory.CodeUseEntity)
	 */
	public boolean codeGenerate(CodeUseEntity codeUseEntity) throws CodeGenerateException {
		try{
			String className = CodegenerateUtil.transformTableName(codeUseEntity.getJarName());
			BasePage basePage = codeUseEntity.getBasePage();
			ListPage listPage =null;
			EditPage editPage =null;
			ViewPage viewPage =null;
			boolean listOperate = false;
			boolean editOperate = false;
			boolean viewOperate = false;
		    Map map = new HashMap();
		    Set imports = new HashSet();
		    map.put("package", codeUseEntity.getPackageName());
		    map.put("imports", imports);
		    map.put("class", CodegenerateUtil.transformTableName(className));
		    map.put("classProp", CodegenerateUtil.transformColumnName(className));
		    map.put("version", codeUseEntity.getVersionId());
		    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		    map.put("currentTime",sf.format(new Date()));
		    Map switchMap = new HashMap();
		    switchMap.put("int", "Integer");
		    switchMap.put("integer", "Integer");
		    switchMap.put("varchar2", "String");
		    switchMap.put("varchar", "String");
		    switchMap.put("char", "String");
		    switchMap.put("number", "Long");
		    switchMap.put("date", "Date");
		    switchMap.put("blob", "String");
			if(basePage instanceof ListPage){
				listPage = (ListPage) basePage;
				editPage = listPage.getEditPage();
		    	viewPage = listPage.getViewPage();
	    		List queryParams = listPage.getParams();
	    		List queryParamsView = CodegenerateUtil.copyBySerialize(queryParams);
	    		int querySize = queryParamsView.size();
	    		Iterator queryParamsIt = queryParamsView.iterator();
	    		while(queryParamsIt.hasNext()){
	    			Param param = (Param)queryParamsIt.next();
	    			String queryName = CodegenerateUtil.transformColumnName(param.getKey()); 
	    			param.setKey(queryName);
	    			String type = (String) switchMap.get(param.getType().toLowerCase());
	    			param.setType(type);
	    		}
	    		map.put("querySize", querySize+"");
	    		map.put("queryParams", queryParamsView);
	    		//删除
	    		Map deleteParamMap = listPage.getDeleteParam();
	    		Set deleteParams = deleteParamMap.keySet();
                Iterator deleteParamsIt = deleteParams.iterator();
                Set deleteFields = new HashSet();
	    		while(deleteParamsIt.hasNext()){
	    			String tableName = (String) deleteParamsIt.next();
	    			String entityName = CodegenerateUtil.transformTableName(tableName);
	    			String entityParam = CodegenerateUtil.transformColumnName(tableName);
	    			map.put("object", entityName);
	    			map.put("objectParam", entityParam);
	    			List columns = (List) deleteParamMap.get(tableName);
	    			Iterator columnIt = columns.iterator();
	    			while(columnIt.hasNext()){
	    				Param param = (Param)columnIt.next();
	    				String columnName = CodegenerateUtil.transformTableName(param.getKey());
	    				String columnProp = CodegenerateUtil.transformColumnName(param.getKey());
	    				Map importsName = new HashMap();
	    				Map field = new HashMap();
	    				importsName.put("name", codeUseEntity.getPackageName()+".entity."+entityName);
	    				imports.add(importsName);
	    				String type = (String) switchMap.get(param.getType().toLowerCase());
	    				field.put("type", type);
	    				field.put("name", columnProp);
	    				field.put("setName", "set"+columnName);
	    				field.put("parse", type+".valueOf");
	    				deleteFields.add(field);
	    			}
	    		}
	    		map.put("deleteFields", deleteFields);
	    		listOperate = true;
			}
			
			if(null != editPage || basePage instanceof EditPage){
				if(editPage == null){
		    		editPage =(EditPage) basePage;
		    	}
				Map objectColumnOperates = editPage.getInsertParam();
				Set operates = objectColumnOperates.keySet();
                Iterator operateIt = operates.iterator();
                Set fields = new HashSet();
	    		while(operateIt.hasNext()){
	    			String tableName = (String) operateIt.next();
	    			String entityName = CodegenerateUtil.transformTableName(tableName);
	    			String entityParam = CodegenerateUtil.transformColumnName(tableName);
	    			map.put("object", entityName);
	    			map.put("objectParam", entityParam);
	    			List columns = (List) objectColumnOperates.get(tableName);
	    			Iterator columnIt = columns.iterator();
	    			while(columnIt.hasNext()){
	    				Param param = (Param)columnIt.next();
	    				String columnName = CodegenerateUtil.transformTableName(param.getKey());
	    				String columnProp = CodegenerateUtil.transformColumnName(param.getKey());
	    				Map importsName = new HashMap();
	    				Map field = new HashMap();
	    				importsName.put("name", codeUseEntity.getPackageName()+".entity."+entityName);
	    				imports.add(importsName);
	    				String type = (String) switchMap.get(param.getType().toLowerCase());
	    				field.put("type", type);
	    				field.put("name", columnProp);
	    				field.put("setName", "set"+columnName);
	    				field.put("parse", type+".valueOf");
	    				fields.add(field);
	    			}
	    		}
	    		//查询差数
	    		Map findColumnOperates = editPage.getFindParams();
				Set findColumnOperatesSet = findColumnOperates.keySet();
                Iterator findColumnOperatesSetIt = findColumnOperatesSet.iterator();
                Set findFields = new HashSet();
	    		while(findColumnOperatesSetIt.hasNext()){
	    			String tableName = (String) findColumnOperatesSetIt.next();
	    			String entityName = CodegenerateUtil.transformTableName(tableName);
	    			String entityParam = CodegenerateUtil.transformColumnName(tableName);
	    			map.put("object", entityName);
	    			map.put("objectParam", entityParam);
	    			List columns = (List) findColumnOperates.get(tableName);
	    			Iterator columnIt = columns.iterator();
	    			while(columnIt.hasNext()){
	    				Param param = (Param)columnIt.next();
	    				String columnName = CodegenerateUtil.transformTableName(param.getKey());
	    				String columnProp = CodegenerateUtil.transformColumnName(param.getKey());
	    				Map importsName = new HashMap();
	    				Map field = new HashMap();
	    				importsName.put("name", codeUseEntity.getPackageName()+".entity."+entityName);
	    				imports.add(importsName);
	    				String type = (String) switchMap.get(param.getType().toLowerCase());
	    				field.put("type", type);
	    				field.put("name", columnProp);
	    				field.put("setName", "set"+columnName);
	    				field.put("parse", type+".valueOf");
	    				findFields.add(field);
	    			}
	    		}
//	    		String editPara = conditionSb.substring(0,conditionSb.length()-1).toString();
	    		map.put("findFields", findFields);
	    		map.put("fields", fields);
	    		editOperate = true;
			 }
			map.put("imports", imports);
			map.put("list", listOperate+"");
		    map.put("edit", editOperate+"");
		    map.put("view", viewOperate+"");
			Configuration cfg = new Configuration();
			cfg.setDirectoryForTemplateLoading(new File(codeUseEntity.getBasePath()+File.separator+"freemarker"+File.separator+"jdbc"));
			cfg.setObjectWrapper(new DefaultObjectWrapper());
			cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
			Template temp = cfg.getTemplate(CodeUseEntity.CODE_GENERATE_ACTION_FTL,"UTF-8");
			//以流的形式输出到文件系统中
			Writer out = new OutputStreamWriter(new FileOutputStream(codeUseEntity.getOutPath()+File.separator+CodegenerateUtil.transformTableName(className)+"Action.java"),"UTF-8");
			temp.process(map,out);
			out.flush();
			out.close();
		}catch(Exception e){
			throw new CodeGenerateException("控制器层出错");
		}
		return false;
	}

}
