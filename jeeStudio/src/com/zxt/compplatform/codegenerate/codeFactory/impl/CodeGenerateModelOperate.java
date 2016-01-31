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
import com.zxt.compplatform.datacolumn.entity.DataColumn;
import com.zxt.compplatform.formengine.entity.view.BasePage;
import com.zxt.compplatform.formengine.entity.view.ListPage;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

/**
 * 实体层的代码生成工厂
 * @author zxt-hejun
 * @date:2010-9-26 上午09:39:37
 */
public class CodeGenerateModelOperate implements CodeGenerateOperate {
	
	/**
	 * 从集合中获取基路径
	 * @param tableMaps
	 * @param basePages
	 */
	private void getBasePages(Set tableMaps,List basePages){
		if(null != basePages){
			Iterator basePageIt = basePages.iterator();
			while(basePageIt.hasNext()){
				BasePage basePage = (BasePage) basePageIt.next();
				if(basePage instanceof ListPage){
					ListPage listPage = (ListPage)basePage;
					Map objectColumns = listPage.getObjectColumn();
					tableMaps.add(objectColumns);
					if(null != listPage.getEditPage()){
						tableMaps.add(listPage.getEditPage().getObjectColumn());
					}
					if(null != listPage.getViewPage()){
						tableMaps.add(listPage.getViewPage().getObjectColumn());
					}
				}else{
					Map objectColumns = basePage.getObjectColumn();
					tableMaps.add(objectColumns);
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.codegenerate.codeFactory.CodeGenerateOperate#codeGenerate(com.zxt.compplatform.codegenerate.codeFactory.CodeUseEntity)
	 */
	public boolean codeGenerate(CodeUseEntity codeUseEntity) throws CodeGenerateException {
		try{
			BasePage basePage = codeUseEntity.getBasePage();
			Set tableMaps = new HashSet();
			List basePages = basePage.getTabs();
			List tabs = CodegenerateUtil.copyBySerialize(basePages);
			if(null != basePage && tabs.size() == 0){
				tabs.add(basePage);
			}
			getBasePages(tableMaps,tabs);
			Iterator objectsIt = tableMaps.iterator();
			while(objectsIt.hasNext()){
				Map objectColumns = (Map)objectsIt.next();
				Set objects = objectColumns.keySet();
				Iterator objectIt = objects.iterator();
				while(objectIt.hasNext()){
					String tableName = (String) objectIt.next();
					List columns = (List) objectColumns.get(tableName);
					String className = CodegenerateUtil.transformTableName(tableName); 
					Map map = new HashMap();
				    Map switchMap = new HashMap();
				    Set properties = new HashSet();
				    Set typeSet = new HashSet();
				    Set imports = new HashSet();
				    map.put("imports", imports);
				    map.put("properties", properties);
				    map.put("package", codeUseEntity.getPackageName());
				    map.put("class", className);
				    map.put("version", codeUseEntity.getVersionId());
				    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				    map.put("currentTime",sf.format(new Date())); 
				    switchMap.put("int", "Integer");
				    switchMap.put("integer", "Integer");
				    switchMap.put("varchar2", "String"); 
				    switchMap.put("varchar", "String");
				    switchMap.put("nvarchar", "String");
				    switchMap.put("nvarchar2", "String");
				    switchMap.put("char", "String");
				    switchMap.put("number", "Long");
				    switchMap.put("blob", "String");
				    switchMap.put("date", "Date");
				    switchMap.put("image", "InputStream");
				  
				    Iterator columnIt = columns.iterator();
				    while(columnIt.hasNext()){
				    	   DataColumn dataColumn = (DataColumn) columnIt.next();
						   Map columnName = new HashMap();
						   columnName.put("name", CodegenerateUtil.transformColumnName(dataColumn.getName()+""));
						   columnName.put("type", switchMap.get((dataColumn.getDataType()+"").toLowerCase()));
						   typeSet.add(switchMap.get((dataColumn.getDataType()+"").toLowerCase()));
						   properties.add(columnName);
				    }
				    if(typeSet.contains("Date")){
						Map importsName = new HashMap();
						importsName.put("name", "java.sql.Date");
						imports.add(importsName);
					}
				    if(typeSet.contains("Blob")){
						Map importsName = new HashMap();
						importsName.put("name", "java.sql.Blob");
						imports.add(importsName);
					}
				    /**
				     * update by  hgw
				     */
				    Configuration cfg = new Configuration();
					cfg.setDirectoryForTemplateLoading(new File(codeUseEntity.getBasePath()+File.separator+"freemarker"+File.separator+"jdbc"));
					cfg.setObjectWrapper(new DefaultObjectWrapper());
					cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
					Template temp = cfg.getTemplate(CodeUseEntity.CODE_GENERATE_MODEL_FTL,"UTF-8");
					//System.out.println("**********************"+properties);
					Writer out = new OutputStreamWriter(new FileOutputStream(codeUseEntity.getOutPath()+File.separator+className+".java"),"UTF-8");
					temp.process(map,out);
				    /**
				     * update by  hgw
				     */
					out.flush();
					out.close();
				}
			}
			
		   
		}catch(Exception e){
			throw new CodeGenerateException("实体层出错");
		}
		return true;
	}

}
