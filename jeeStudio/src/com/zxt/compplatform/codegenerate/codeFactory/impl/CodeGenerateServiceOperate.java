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
import java.util.Map;
import java.util.Set;

import com.zxt.compplatform.codegenerate.codeFactory.CodeGenerateOperate;
import com.zxt.compplatform.codegenerate.codeFactory.CodeUseEntity;
import com.zxt.compplatform.codegenerate.util.CodeGenerateException;
import com.zxt.compplatform.codegenerate.util.CodegenerateUtil;
import com.zxt.compplatform.formengine.entity.view.BasePage;
import com.zxt.compplatform.formengine.entity.view.EditPage;
import com.zxt.compplatform.formengine.entity.view.ListPage;
import com.zxt.compplatform.formengine.entity.view.ViewPage;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

/**
 * 业务层的代码生成工厂
 * @author zxt-hejun
 * @date:2010-9-26 上午09:39:51
 */
public class CodeGenerateServiceOperate implements CodeGenerateOperate {

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
		    map.put("class", CodegenerateUtil.transformTableName(className));
		    map.put("classProp", CodegenerateUtil.transformColumnName(className));
		    map.put("version", codeUseEntity.getVersionId());
		    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		    map.put("currentTime",sf.format(new Date()));
		    if(basePage instanceof ListPage){
		    	listPage = (ListPage) basePage;
				editPage = listPage.getEditPage();
		    	viewPage = listPage.getViewPage();
		    	String keyTable = listPage.getKeyTable();
				map.put("object", CodegenerateUtil.transformTableName(keyTable));
		    	Map queryColumnsTable = listPage.getQueryColumnsTable();
		    	if(null != queryColumnsTable){
		    		Set objects = queryColumnsTable.keySet();
	                Iterator objectIt = objects.iterator();
	                StringBuffer queryParamSb = new StringBuffer();
	                StringBuffer queryParaSb = new StringBuffer();
		    		while(objectIt.hasNext()){
		    			String tableName = (String) objectIt.next();
		    			String entityName = CodegenerateUtil.transformTableName(tableName);
		    			String entityParam = CodegenerateUtil.transformColumnName(tableName);
		    			Map importsName = new HashMap();
		    			importsName.put("name", codeUseEntity.getPackageName()+".entity."+entityName);
		    			imports.add(importsName);
		    			queryParamSb.append(entityName + " "+entityParam+",");
		    			queryParaSb.append(entityParam+",");
		    		}
		    		String queryParam = queryParamSb.substring(0,queryParamSb.length()-1).toString();
		    		String queryPara = queryParaSb.substring(0,queryParaSb.length()-1).toString();
		    		map.put("queryParams", queryParam);
		    		map.put("queryPara", queryPara);
		    	}else{
		    		map.put("queryParams", "");
		    		map.put("queryPara", "");
		    	}
	    		
	    		listOperate = true;
		    }
		    //编辑页
		    if(null != editPage || basePage instanceof EditPage){
				if(editPage == null){
		    		editPage =(EditPage) basePage;
		    	}
				Map objectColumnOperates = editPage.getInsertParam();
				Set operates = objectColumnOperates.keySet();
                Iterator operateIt = operates.iterator();
                String entityName = "";
                String entityParam = "";
	    		while(operateIt.hasNext()){
	    			String tableName = (String) operateIt.next();
	    			entityName = CodegenerateUtil.transformTableName(tableName);
	    			entityParam = CodegenerateUtil.transformColumnName(tableName);
	    			Map importsName = new HashMap();
	    			importsName.put("name", codeUseEntity.getPackageName()+".entity."+entityName);
	    			imports.add(importsName);
	    		}
	    		map.put("editPara", entityParam);
	    		map.put("editParams", entityName+" "+entityParam);
	    		map.put("object", entityName);
	    		editOperate = true;
			 }
    		map.put("imports", imports);
		    map.put("list", listOperate+"");
		    map.put("edit", editOperate+"");
		    map.put("view", viewOperate+"");
		    //生成接口
			Configuration cfg = new Configuration();
			cfg.setDirectoryForTemplateLoading(new File(codeUseEntity.getBasePath()+File.separator+"freemarker"+File.separator+"jdbc"));
			cfg.setObjectWrapper(new DefaultObjectWrapper());
			cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
			Template temp = cfg.getTemplate(CodeUseEntity.CODE_GENERATE_SERVICE_FTL,"UTF-8");
			Writer out = new OutputStreamWriter(new FileOutputStream(codeUseEntity.getOutPath()+File.separator+CodegenerateUtil.transformTableName(className)+"Service.java"),"UTF-8");
			temp.process(map,out);
			out.flush();
			//生成接口实现类
			Template tempImpl = cfg.getTemplate(CodeUseEntity.CODE_GENERATE_SERVICEIMPL_FTL,"UTF-8");
			Writer outImpl = new OutputStreamWriter(new FileOutputStream(codeUseEntity.getOutPath()+File.separator+CodegenerateUtil.transformTableName(className)+"ServiceImpl.java"),"UTF-8");
			tempImpl.process(map,outImpl);
			out.flush();
			out.close();
			
			
		}catch(Exception e){
			throw new CodeGenerateException("业务层出错");
		}
		return false;
	}

}
