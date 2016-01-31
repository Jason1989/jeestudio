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
import com.zxt.compplatform.datasource.entity.DataSource;
import com.zxt.compplatform.formengine.entity.view.BasePage;
import com.zxt.compplatform.formengine.entity.view.EditPage;
import com.zxt.compplatform.formengine.entity.view.Field;
import com.zxt.compplatform.formengine.entity.view.ListPage;
import com.zxt.compplatform.formengine.entity.view.Param;
import com.zxt.compplatform.formengine.entity.view.ViewPage;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
/**
 * DAO层的代码生成工厂
 * @author zxt-hejun
 * @date:2010-9-26 上午09:39:19
 */
public class CodeGenerateDaoOperate implements CodeGenerateOperate{

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
			map.put("package", codeUseEntity.getPackageName());
		    map.put("class", className);
		    map.put("version", codeUseEntity.getVersionId());
		    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		    map.put("currentTime",sf.format(new Date()));
		    Set imports = new HashSet();
		    map.put("imports", imports);
			if(basePage instanceof ListPage){
				listPage = (ListPage) basePage;
				List list = listPage.getTabs();
				editPage = listPage.getEditPage();
				String keyTable = listPage.getKeyTable();
				map.put("object", CodegenerateUtil.transformTableName(keyTable));
		    	viewPage = listPage.getViewPage();
		    	String querySql = listPage.getSql();
		    	Set datasources = new HashSet();
		    	
		    	DataSource listDataSource = listPage.getDataSource();
		    	/**
		    	 * SQLserver 数据语法
		    	 */
		    	if (listDataSource!=null) {
		    		if ("2".equals(listDataSource.getDbType())) {
		    			querySql=querySql.replace("||","+");
					}
				}
		    	/**
		    	 * 查询字段sql
		    	 */
		    	String fieldNotes=querySql.substring(querySql.indexOf("select")+6,querySql.indexOf("from"));
		    	querySql=querySql.substring(querySql.indexOf("from"));
		    	
		    	/**
		    	 * 添加字段注释
		    	 */
		    	Map noteMap=new HashMap();
		    	String fieldArray[]=fieldNotes.split(",");
		    	String  temField[]=null;
		    	
		    	Field field=null;
		    	for (int i = 0; i < fieldArray.length; i++) {
		    		temField=fieldArray[i].split("as ");
		    		for (int j = 0; j < basePage.getFields().size(); j++) {
		    			field=(Field)basePage.getFields().get(j);
						if (temField[1].trim().equals(field.getDataColumn())){
							if (i!=fieldArray.length-1) {
								noteMap.put(fieldArray[i]+",", field.getName());
							} else {
								noteMap.put(fieldArray[i], field.getName());
							}
							
						}
					}
		    	}
		    	/**
		    	 *  update by hgw
		    	 */
		    	map.put("sqlnote", noteMap);
		    	map.put("querySql",querySql);
		    	map.put("queryDS", CodegenerateUtil.transformTableName(listDataSource.getSid()));
		    	listDataSource.setSid(CodegenerateUtil.transformTableName(listDataSource.getSid()));
		    	datasources.add(listDataSource);
		    	if(null != editPage && null != viewPage.getDataSource()){
		    		editPage.getDataSource().setSid(CodegenerateUtil.transformTableName(editPage.getDataSource().getSid()));
		    		datasources.add(editPage.getDataSource());
		    	}
		    	if(null != viewPage && null != viewPage.getDataSource()){
		    		viewPage.getDataSource().setSid(CodegenerateUtil.transformTableName(viewPage.getDataSource().getSid()));
		    		datasources.add(viewPage.getDataSource());
		    	}
		    	Iterator datasourcesIt = datasources.iterator();
		    	Set datasourceNews = new HashSet();
		    	while(datasourcesIt.hasNext()){
		    		DataSource ds = (DataSource) datasourcesIt.next();
		    		Map datasource = new HashMap();
		    		datasource.put("sid", CodegenerateUtil.transformTableName(ds.getSid()));
		    		datasource.put("ipAddress", ds.getIpAddress());
		    		datasource.put("port", String.valueOf(ds.getPort()));
		    		datasource.put("sid", CodegenerateUtil.transformTableName(ds.getSid()));
		    		datasource.put("dbType", ds.getDbType());
		    		datasource.put("username", ds.getUsername());
		    		datasource.put("password", ds.getPassword());
		    		datasourceNews.add(datasource);
		    	}
		    	map.put("datasources", datasourceNews);
	    		List queryParams = listPage.getParams();
	    		int querySize = queryParams.size();
	    		map.put("querySize", querySize+"");
	    		map.put("deleteSql", listPage.getDeleteSql());
	    		listOperate = true;
			}
			if(null != editPage || basePage instanceof EditPage){
				if(editPage == null){
		    		editPage =(EditPage) basePage;
		    	}
				map.put("editDS", CodegenerateUtil.transformTableName(editPage.getDataSource().getSid()));
				List keys = editPage.getKeyList();
				Map objectColumnOperates = editPage.getInsertParam();
				Set operates = objectColumnOperates.keySet();
                Iterator operateIt = operates.iterator();
                StringBuffer insertSb = new StringBuffer();
                String entityName = "";
                String entityParam = "";
	    		while(operateIt.hasNext()){
	    			String tableName = (String) operateIt.next();
	    			entityParam = CodegenerateUtil.transformColumnName(tableName);
    				entityName = CodegenerateUtil.transformTableName(tableName);
	    			List columns = (List) objectColumnOperates.get(tableName);
	    			Iterator columnIt = columns.iterator();
	    			while(columnIt.hasNext()){
	    				if(!"".equals(insertSb.toString())){
	    					insertSb.append(",");
	    				}
	    				Param param = (Param)columnIt.next();
	    				Map importsName = new HashMap();
	    				importsName.put("name", codeUseEntity.getPackageName()+".entity."+entityName);
	    				imports.add(importsName);
	    				String columnName = CodegenerateUtil.transformTableName(param.getKey());
	    				if(keys.contains(param.getKey())){
	    					insertSb.append("RandomGUID.geneGuid()");
	    				}else{
	    					insertSb.append(entityParam + ".get"+columnName+"()");
	    				}
	    			}
	    		}
	    		String insertPara = insertSb.toString();
	    		Map findParamMap = editPage.getFindParams();
	    		Set findParamMapKeys = findParamMap.keySet();
	    		Iterator findParamIt = findParamMapKeys.iterator();
                StringBuffer findParamSb = new StringBuffer();
	    		while(findParamIt.hasNext()){
	    			String tableName = (String) findParamIt.next();
	    			entityParam = CodegenerateUtil.transformColumnName(tableName);
    				entityName = CodegenerateUtil.transformTableName(tableName);
	    			List columnDatas = (List)findParamMap.get(tableName);
	    			Iterator columnDataIt = columnDatas.iterator();
	    			while(columnDataIt.hasNext()){
	    				Param param = (Param)columnDataIt.next();
	    				String columnName = CodegenerateUtil.transformTableName(param.getKey());
	    				findParamSb.append(entityParam + ".get"+columnName+"(),");
	    			}
	    		}
	    		String findPara = findParamSb.substring(0,findParamSb.length()-1).toString();
	    		
	    		Map updateParamMap = editPage.getUpdateParam();
	    		Set updateParamMapKeys = updateParamMap.keySet();
	    		Iterator deleteParamIt = updateParamMapKeys.iterator();
                StringBuffer updateParamSb = new StringBuffer();
	    		while(deleteParamIt.hasNext()){
	    			String tableName = (String) deleteParamIt.next();
	    			List columnDatas = (List)updateParamMap.get(tableName);
	    			Iterator columnDataIt = columnDatas.iterator();
	    			while(columnDataIt.hasNext()){
	    				Param param = (Param)columnDataIt.next();
	    				String columnName = CodegenerateUtil.transformTableName(param.getKey());
	    				updateParamSb.append(entityParam + ".get"+columnName+"(),");
	    			}
	    		}
	    		String updatePara = updateParamSb.substring(0,updateParamSb.length()-1).toString();
	    		map.put("object", entityName);
	    		map.put("findPara", findPara);
	    		map.put("updatePara", updatePara);
	    		map.put("editParams", entityName+" "+entityParam);
	    		map.put("insertPara", insertPara);
	    		map.put("insertSql", editPage.getInsertSql());
	    		map.put("updateSql", editPage.getUpdateSql());
	    		map.put("findSql",editPage.getFindSql());
	    		editOperate = true;
			 }
			
			if(null != viewPage || basePage instanceof ViewPage){
				if(viewPage == null){
					viewPage =(ViewPage) basePage;
		    	}
				viewOperate = true;
			}
			map.put("list", listOperate+"");
		    map.put("edit", editOperate+"");
		    map.put("view", viewOperate+"");
			Configuration cfg = new Configuration();
			cfg.setDirectoryForTemplateLoading(new File(codeUseEntity.getBasePath()+File.separator+"freemarker"+File.separator+"jdbc"));
			cfg.setObjectWrapper(new DefaultObjectWrapper());
			cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
			Template temp = cfg.getTemplate(CodeUseEntity.CODE_GENERATE_DAO_FTL,"UTF-8");
			Writer out = new OutputStreamWriter(new FileOutputStream(codeUseEntity.getOutPath()+File.separator+CodegenerateUtil.transformTableName(className)+"Dao.java"),"UTF-8");
			temp.process(map,out);
			out.flush();
			//生成接口实现类
			Template tempImpl = cfg.getTemplate(CodeUseEntity.CODE_GENERATE_DAOIMPL_FTL,"UTF-8");
			Writer outImpl = new OutputStreamWriter(new FileOutputStream(codeUseEntity.getOutPath()+File.separator+CodegenerateUtil.transformTableName(className)+"DaoImpl.java"),"UTF-8");
			tempImpl.process(map,outImpl);
			//生成数据源
			Template dsImpl = cfg.getTemplate(CodeUseEntity.CODE_GENERATE_DAOSUPPORT_FTL,"UTF-8");
			Writer outDs = new OutputStreamWriter(new FileOutputStream(codeUseEntity.getOutPath()+File.separator+"ZXTJDBCDaoSupport.java"),"UTF-8");
			dsImpl.process(map,outDs);
			out.flush();
			out.close();
		}catch(Exception e){
			e.printStackTrace();
			throw new CodeGenerateException("数据持久层出错");
		}
		return false;
	}

}
