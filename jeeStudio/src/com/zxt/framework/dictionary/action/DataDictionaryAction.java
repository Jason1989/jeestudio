package com.zxt.framework.dictionary.action;

import java.io.IOException;
import java.io.StringReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.zxt.compplatform.datasource.service.DataSourceService;
import com.zxt.compplatform.datasource.service.IDataSourceService;
import com.zxt.compplatform.formengine.entity.view.Param;
import com.zxt.compplatform.formengine.util.SqlXmlUtil;
import com.zxt.framework.common.util.RandomGUID;
import com.zxt.framework.dictionary.entity.DataDictionary;
import com.zxt.framework.dictionary.entity.SqlObj;
import com.zxt.framework.dictionary.service.IDataDictionaryService;
import com.zxt.framework.dictionary.service.ISqlDicService;

public class DataDictionaryAction {
	private IDataDictionaryService dataDictionaryService;
	private ISqlDicService sqlDicService;
	private DataDictionary dataDictionary;
	private SqlObj sqlObj;
	private static final Logger log = Logger.getLogger(DataDictionaryAction.class);
	/**
	 * 列表入口
	 * @return
	 */
	public String toList(){
		return "main";//GUOWEIXIN 对数据字典分组进行的修改	如果回到原来，只需return 'list'
		//return "list"; //原始数据字典
	}
	/**
	 * 列表入口
	 * @return
	 */
	public String toSqlList(){
		return "sqllist";		
	}
	
	
	/**
	 * 列表
	 * @return
	 */
	public String list(){
		HttpServletResponse res = ServletActionContext.getResponse();
		HttpServletRequest req = ServletActionContext.getRequest();
		//GUOWEXIN 添加：
		String groupId=req.getParameter("dictionaryGroup");
		
		int page = 1;
		if(req.getParameter("page") != null && !req.getParameter("page").equals("")){
			page = Integer.parseInt(req.getParameter("page"));
		}
		int rows = 0;
		if(req.getParameter("rows") != null && !req.getParameter("rows").equals("")){
			rows = Integer.parseInt(req.getParameter("rows"));
		}
		int totalRows=0;
		List dataDictionaryList=null;
		if(null==groupId || "".equals(groupId)){
		 totalRows = dataDictionaryService.findTotalRows();
		 dataDictionaryList = dataDictionaryService.findAllByPage(page,rows);
		}else{
			 totalRows = dataDictionaryService.findTotalRowsByGroupId(groupId);
			 dataDictionaryList = dataDictionaryService.findAllByPageByGroupId(page,rows,groupId);
		}
		 Map map = new HashMap();
		if(dataDictionaryList != null){
			map.put("rows", dataDictionaryList);
			map.put("total", new Integer(totalRows));
		}
		String dataJson = JSONObject.fromObject(map).toString();
		try {
			res.getWriter().write(dataJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}	
	/**
	 * sql字典列表
	 * @return
	 */
	public String sqlDicList(){
		HttpServletResponse res = ServletActionContext.getResponse();
		HttpServletRequest req = ServletActionContext.getRequest();
		int page = 1;
		if(req.getParameter("page") != null && !req.getParameter("page").equals("")){
			page = Integer.parseInt(req.getParameter("page"));
		}
		int rows = 0;
		if(req.getParameter("rows") != null && !req.getParameter("rows").equals("")){
			rows = Integer.parseInt(req.getParameter("rows"));
		}
		int totalRows = sqlDicService.findTotalRows();
		List dataDictionaryList = sqlDicService.findAllByPage(page,rows);
		Map map = new HashMap();
		if(dataDictionaryList != null){
			map.put("rows", dataDictionaryList);
			map.put("total", new Integer(totalRows));
		}
		String dataJson = JSONObject.fromObject(map).toString();
		try {
			res.getWriter().write(dataJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}	
	/**
	 *表单编辑 保存后执行sql语句 的combobox读取
	 * @return
	 */
	public String sqlEditPageAfter(){
		HttpServletResponse res = ServletActionContext.getResponse();
		HttpServletRequest req = ServletActionContext.getRequest();
		List dataDictionaryList = sqlDicService.findAllByPage(0,0);
		Map map = new HashMap();
		List resultList = new ArrayList();
		if(dataDictionaryList != null){
			for(int i=0;i<dataDictionaryList.size();i++){
				ListOrderedMap lmap=(ListOrderedMap) dataDictionaryList.get(i);
				Map map2=new HashMap();
				map2.put("id",lmap.getValue(0));
				map2.put("name",lmap.getValue(1));
				resultList.add(map2);
			}
		}
		String dataJson=JSONArray.fromObject(resultList).toString();
		try {
			res.getWriter().write(dataJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String search(){
		HttpServletResponse res = ServletActionContext.getResponse();
		HttpServletRequest req = ServletActionContext.getRequest();
		int page = 1;
		if(req.getParameter("pageNumer") != null && !req.getParameter("pageNumer").equals("")){
			page = Integer.parseInt(req.getParameter("pageNumer"));
		}
		int rows = 0;
		if(req.getParameter("pageSize") != null && !req.getParameter("pageSize").equals("")){
			rows = Integer.parseInt(req.getParameter("pageSize"));
		}
		String dictionaryName = "";
		if(null != req.getParameter("dictionaryName"))
			dictionaryName= req.getParameter("dictionaryName");
		String dictionaryGroup = "-1";
		if(null != req.getParameter("dictionaryGroup") && !"".equals(req.getParameter("dictionaryGroup")))
			dictionaryGroup = req.getParameter("dictionaryGroup");
		int totalRows = dataDictionaryService.findTotalRowsByCondition(dictionaryName,dictionaryGroup);
		List dataDictionaryList = dataDictionaryService.findAllByCondition(page,rows,dictionaryName,dictionaryGroup);
		Map map = new HashMap();
		if(dataDictionaryList != null){
			map.put("rows", dataDictionaryList);
			map.put("total", new Integer(totalRows));
		}
		String dataJson = JSONObject.fromObject(map).toString();
		try {
			res.getWriter().write(dataJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 跳转添加页面
	 * @return
	 */
	public String toAdd(){
		return "toadd";
	}
	/**
	 * 添加
	 * @return
	 */
	public String add(){
		HttpServletResponse res = ServletActionContext.getResponse();
		HttpServletRequest request=ServletActionContext.getRequest();
		if(dataDictionary.getId() == null || dataDictionary.getId().equals("")){
			dataDictionary.setId(RandomGUID.geneGuid());
		}
		//@GUOWEIXIN 添加父节点
		String dic_root_id=request.getParameter("dic_root_id");
		if(!"".equals(dic_root_id) && null!=dic_root_id){
			dataDictionary.setDic_root_id(dic_root_id);
		}
		
		
		try {
			if(dataDictionaryService.isExist(dataDictionary.getId(),dataDictionary.getName())){				
				res.getWriter().write("exist");
			}else{
				//20120817add判断表达式选项sql语句是否正确 动态的时候
			if (!"1".equals(dataDictionary.getType())) {
				PlainSelect plainSelect = null;
				try {
					String  sqlExpression = dataDictionary.getExpression();
					CCJSqlParserManager sqlParserManager = new CCJSqlParserManager();
					plainSelect = (PlainSelect)((Select)sqlParserManager.parse(new StringReader(sqlExpression))).getSelectBody();
				} catch (JSQLParserException e) {
					res.getWriter().write("sqlError");
					return null;
				}
				//20120817
			}
				
				
				
				dataDictionaryService.insert(dataDictionary);
				res.getWriter().write("success");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 添加sql字典
	 * @return
	 */
	public String addsql(){
		HttpServletResponse res = ServletActionContext.getResponse();
		if(sqlObj.getSqldicid() == null || sqlObj.getSqldicid().equals("")){
			sqlObj.setSqldicid(RandomGUID.geneGuid());
		}
		try {
			String sql_isupdate_hidden = ServletActionContext.getRequest().getParameter("sql_isupdate_hidden");
			if("1".equals(sql_isupdate_hidden)&&sqlDicService.isExist(sqlObj.getSqldicid(),sqlObj.getSqldicname())){				
				res.getWriter().write("exist");
			}else{
				String params = ServletActionContext.getRequest().getParameter("sql_prams_hidden");
				if(params!=null && !params.equals("")){
					JSONArray paramsarr = JSONArray.fromObject(params);
					List list = new ArrayList();
					Param param;
					for (int i = 0; i < paramsarr.size(); i++) {
						JSONObject sss = JSONObject.fromObject(paramsarr.get(i));
						String paramname = sss.get("key").toString();
						String paramtype = sss.get("type").toString();
						String paramvalue = sss.get("value").toString();
						param = new Param();
						param.setKey(paramname);
						param.setType(paramtype);
						param.setValue(paramvalue);
						list.add(param);
					}

					params = SqlXmlUtil.sqlParamListToxml(list);
				}
				boolean b = false;
				if(sql_isupdate_hidden!=null && "1".equals(sql_isupdate_hidden)){//新增
					b = sqlDicService.insert(sqlObj,params);
				}else if (sql_isupdate_hidden!=null && "2".equals(sql_isupdate_hidden)){//更新
					b = sqlDicService.updateSql(sqlObj,params);
				}
				
				if(b){
					res.getWriter().write("success");
				}else{
					res.getWriter().write("failed");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 跳转修改页面
	 * @return
	 */
	public String toUpdate(){
		HttpServletRequest req = ServletActionContext.getRequest();
		String dictionaryId = req.getParameter("dictionaryId");
		if(dictionaryId!= null && !dictionaryId.equals("")){
			dataDictionary = dataDictionaryService.findById(dictionaryId);
		}
		return "toupdate";
	}
	/**
	 * 修改
	 * @return
	 */
	public String update(){
		HttpServletResponse res = ServletActionContext.getResponse();
		HttpServletRequest request=ServletActionContext.getRequest();
		//@GUOWEIXIN 修改功能的  父节点
		String dic_root_id=request.getParameter("dic_root_id");
		if(!"".equals(dic_root_id) && null!=dic_root_id){
			dataDictionary.setDic_root_id(dic_root_id);
		}
		
		try {
//			if(dataDictionaryService.isExistUpdate(dataDictionary.getId(),dataDictionary.getName())){				
//				res.getWriter().write("exist");
//			}else{
			//20120817add判断表达式选项sql语句是否正确
			if (!"1".equals(dataDictionary.getType())) {
				PlainSelect plainSelect = null;
				try {
					String  sqlExpression = dataDictionary.getExpression();
					CCJSqlParserManager sqlParserManager = new CCJSqlParserManager();
					plainSelect = (PlainSelect)((Select)sqlParserManager.parse(new StringReader(sqlExpression))).getSelectBody();
				} catch (JSQLParserException e) {
					res.getWriter().write("sqlError");
					return null;
				}
			}	
			//20120817add
			dataDictionaryService.update(dataDictionary);
			res.getWriter().write("success");
//			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 删除
	 * @return
	 */
	public String delete(){
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		String dictionaryId = req.getParameter("dictionaryId");
		String dictionaryIds = req.getParameter("dictionaryIds");
		try {
			if(dictionaryId!= null && !dictionaryId.equals("")){
				dataDictionaryService.deleteById(dictionaryId);
				res.getWriter().write("success");
			}
			if(dictionaryIds!= null && !dictionaryIds.equals("")){
				dataDictionaryService.deleteAll(dataDictionaryService.findAllByIds(dictionaryIds));
				res.getWriter().write("success");
			}
		} catch (IOException e) {				
			e.printStackTrace();
		}
		return null;
	}
	public String getDictItem(){
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		String dictionaryId = req.getParameter("dictionaryId");
		List list = dataDictionaryService.findDictItemById(dictionaryId);
		List resultList = new ArrayList();
		if(list != null){
			for(int i=0;i<list.size();i++){
				Map map = (Map) list.get(i);
				Iterator it = map.entrySet().iterator();
				Map jsonMap = new HashMap();
				while(it.hasNext()){
					Map.Entry entry = (Map.Entry)it.next();
					String key = entry.getKey().toString();
					String value = entry.getValue().toString();
					jsonMap.put("id", key);
					jsonMap.put("text", value);
				}
				resultList.add(jsonMap);
			}
		}
		String dataJson = JSONArray.fromObject(resultList).toString();
		try {
			res.getWriter().write(dataJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 获取对应数据源下所有符合对应查询规则的数据字典表
	 * 
	 * @return
	 */
	public String getAllDictionaryTables(){
		
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		
		
		return null;
	}
	
	/**
	 * 快速批量生成动态字典
	 * @return
	 */
	public String magicAdd(){
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		List result=null;
		String dbSource = req.getParameter("dbSource");
		String dicGroup = req.getParameter("dicGroup");
		String tableKey = req.getParameter("tableKey");
		String primaryKey = req.getParameter("primaryKey");
		String nameKey = req.getParameter("nameKey");
		result=dataDictionaryService.magicMake(dbSource,dicGroup,tableKey,primaryKey,nameKey);
		Map map = new HashMap();
		if(result != null){
			map.put("rows", result);
			map.put("total", new Integer(result.size()));
		}
		String dataJson = JSONObject.fromObject(map).toString();
		try {
			res.getWriter().write(dataJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 保存生成的字典
	 * @return
	 */
	public String saveMagicDic(){
		HttpServletResponse res = ServletActionContext.getResponse();
		HttpServletRequest req = ServletActionContext.getRequest();
		String data = req.getParameter("data");
		JSONArray array = JSONArray.fromObject(data);
		List list=new ArrayList();
		for (int i = 0; i <array.size(); i++) {
			Map map=(Map) JSONObject.toBean(array.getJSONObject(i),Map.class);
			list.add(map);
		}
		//dataDictionaryService.insertAll(list);
		boolean flag =dataDictionaryService.insertAllDataDictionary(list);
		if(flag){
			try {
				res.getWriter().write("success");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else{
			try {
				res.getWriter().write("failure");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * 修改某一表的字段详情
	 * @return
	 */
	public String tableDicDetail(){
		HttpServletRequest req = ServletActionContext.getRequest();
		String id = req.getParameter("id");
		if(id!=null){
			DataDictionary dic = dataDictionaryService.findById(id);
			String str = dic.getDescription();
			String[] split = str.split("=");
			String[] key = split[0].split(",");
			String[] value = split[1].split(",");
			req.setAttribute("key", key);
			req.setAttribute("value", value);
			req.setAttribute("dic", dic);
			req.setAttribute("id", dic.getId());
		}
		return "toconfig";
	}
	//根据sql id获取sql参数json数据 
	public void getParamsDataById() {
		String id = ServletActionContext.getRequest().getParameter("id");
		String jsonData = sqlDicService.getParamsDataById(id);
		try {
			ServletActionContext.getResponse().getWriter().write(jsonData);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//删除sql字典数据
	public void deleteSqlDataById() {
		// TODO Auto-generated method stub
		String ids = ServletActionContext.getRequest().getParameter("ids");
		sqlDicService.deleteSql(ids);
	}
	//根据sql id执行sql
	public void execteSqlDic() {
		// TODO Auto-generated method stub
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String id = request.getParameter("sqlid");
		SqlObj sqlobj= sqlDicService.findSqlById(id);
		Object obj = sqlDicService.execteSqlDic(sqlobj,request,response);
	}
	
	
	
	public void setDataDictionaryService(
			IDataDictionaryService dataDictionaryService) {
		this.dataDictionaryService = dataDictionaryService;
	}
	public DataDictionary getDataDictionary() {
		return dataDictionary;
	}
	public void setDataDictionary(DataDictionary dataDictionary) {
		this.dataDictionary = dataDictionary;
	}
	public ISqlDicService getSqlDicService() {
		return sqlDicService;
	}
	public void setSqlDicService(ISqlDicService sqlDicService) {
		this.sqlDicService = sqlDicService;
	}
	public SqlObj getSqlObj() {
		return sqlObj;
	}
	public void setSqlObj(SqlObj sqlObj) {
		this.sqlObj = sqlObj;
	}
}
