package com.zxt.framework.dictionary.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.zxt.compplatform.datasource.entity.DataSource;
import com.zxt.compplatform.formengine.util.SqlXmlUtil;
import com.zxt.framework.common.util.RandomGUID;
import com.zxt.framework.dictionary.dao.IDataDictionaryDao;
import com.zxt.framework.dictionary.dao.SqlDicDao;
import com.zxt.framework.dictionary.entity.DataDictionary;
import com.zxt.framework.dictionary.entity.DictionaryGroup;
import com.zxt.framework.dictionary.entity.SqlObj;
import com.zxt.framework.dictionary.util.Matching;

public class SqlDicService implements ISqlDicService {
	
	private IDataDictionaryDao dataDictionaryDao;
	
	private SqlDicDao zxtSqlDicDao;
	
	/* (non-Javadoc)
	 * @see com.zxt.framework.dictionary.service.ISqlDicService#getZxtSqlDicDao()
	 */
	public SqlDicDao getZxtSqlDicDao() {
		return zxtSqlDicDao;
	}

	/* (non-Javadoc)
	 * @see com.zxt.framework.dictionary.service.ISqlDicService#setZxtSqlDicDao(com.zxt.framework.dictionary.dao.SqlDicDao)
	 */
	public void setZxtSqlDicDao(SqlDicDao zxtSqlDicDao) {
		this.zxtSqlDicDao = zxtSqlDicDao;
	}

	/* (non-Javadoc)
	 * @see com.zxt.framework.dictionary.service.ISqlDicService#delete(com.zxt.framework.dictionary.entity.DataDictionary)
	 */
	public void delete(DataDictionary dataDictionary) {
		dataDictionaryDao.delete(dataDictionary);
	}

	/* (non-Javadoc)
	 * @see com.zxt.framework.dictionary.service.ISqlDicService#deleteById(java.lang.String)
	 */
	public void deleteById(String id) {
		dataDictionaryDao.delete(findById(id));
	}
	/* (non-Javadoc)
	 * @see com.zxt.framework.dictionary.service.ISqlDicService#deleteAllByDataSourceId(java.lang.String)
	 */
	public void deleteAllByDataSourceId(String dataSourceId) {
		List list = findAllByDataSourceId(dataSourceId);
		if(list!=null&&list.size()>0){
			dataDictionaryDao.deleteAll(list);
		}
	}
	/* (non-Javadoc)
	 * @see com.zxt.framework.dictionary.service.ISqlDicService#deleteAll(java.util.List)
	 */
	public void deleteAll(List paramCollection) {
		dataDictionaryDao.deleteAll(paramCollection);
	}
	/* (non-Javadoc)
	 * @see com.zxt.framework.dictionary.service.ISqlDicService#findAll()
	 */
	public List findAll() {
		String paramString = " from DataDictionary t order by t.sortNo "; 
		return dataDictionaryDao.find(paramString);
	}
	/* (non-Javadoc)
	 * @see com.zxt.framework.dictionary.service.ISqlDicService#isExist(java.lang.String, java.lang.String)
	 */
	public boolean isExist(String id,String name){
		String sql = "select * from eng_dic_sql where sqlid =? or sqlname=?";
		List list = zxtSqlDicDao.queryForList(sql,new Object[]{id,name});
		if(list != null && list.size()>0){
			return true;
		}
		return false;
	}
	/* (non-Javadoc)
	 * @see com.zxt.framework.dictionary.service.ISqlDicService#isExistUpdate(java.lang.String, java.lang.String)
	 */
	public boolean isExistUpdate(String id,String name){
		String paramString = " from DataDictionary t where t.id <> '" + id + "' and t.name = '"+ name +"' ";
		List list = dataDictionaryDao.find(paramString);
		if(list != null && list.size()>0){
			return true;
		}
		return false;
	}
	/* (non-Javadoc)
	 * @see com.zxt.framework.dictionary.service.ISqlDicService#findById(java.lang.String)
	 */
	public DataDictionary findById(String id) {
		String paramString = " from DataDictionary t where t.id = '" + id + "' ";
		List list = dataDictionaryDao.find(paramString);
		if(list != null && list.size()>0){
			return (DataDictionary) list.get(0);
		}
		return null;
	}
	/* (non-Javadoc)
	 * @see com.zxt.framework.dictionary.service.ISqlDicService#findAllByDataSourceId(java.lang.String)
	 */
	public List findAllByDataSourceId(String dataSourceId){
		String paramString = " from DataDictionary t where t.dataSource.id = '" + dataSourceId + "'"; 
		return dataDictionaryDao.find(paramString);
	}
	
	/* (non-Javadoc)
	 * @see com.zxt.framework.dictionary.service.ISqlDicService#findAllByIds(java.lang.String)
	 */
	public List findAllByIds(String ids){
		String paramString = " from DataDictionary t where t.id in (" + ids + ")  order by t.sortNo "; 
		return dataDictionaryDao.find(paramString);
	}
	/* (non-Javadoc)
	 * @see com.zxt.framework.dictionary.service.ISqlDicService#findAllByGroupIds(java.lang.String)
	 */
	public List findAllByGroupIds(String ids){
		String paramString = " from DataDictionary t where t.dictionaryGroup.id in (" + ids + ")  order by t.sortNo "; 
		return dataDictionaryDao.find(paramString);
	}
	/* (non-Javadoc)
	 * @see com.zxt.framework.dictionary.service.ISqlDicService#findByDictGroupId(java.lang.String)
	 */
	public List findByDictGroupId(String dictGroupId){
		String paramString = " from DataDictionary t where t.dictionaryGroup.id = '" + dictGroupId + "'  order by t.sortNo  ";
		return dataDictionaryDao.find(paramString);
	}
	/* (non-Javadoc)
	 * @see com.zxt.framework.dictionary.service.ISqlDicService#findDictLikeName(java.lang.String)
	 */
	public List findDictLikeName(String dictName){
		String paramString = " from DataDictionary t where t.name like '%" + dictName + "%'  order by t.sortNo  ";
		return dataDictionaryDao.find(paramString);
	}
	/* (non-Javadoc)
	 * @see com.zxt.framework.dictionary.service.ISqlDicService#findTotalRows()
	 */
	public int findTotalRows(){
		String queryString = " select count(1) as countoo from eng_dic_sql t "; 
		return zxtSqlDicDao.findTotalRows(queryString);
	}
	
	/* (non-Javadoc)
	 * @see com.zxt.framework.dictionary.service.ISqlDicService#findAllByPage(int, int)
	 */
	public List findAllByPage(int page,int rows){
		String paramString = "select sqlid as id,sqlname as name,sqltype as type,sqlvalue as value,params,datasourceid,remark from eng_dic_sql";
		return zxtSqlDicDao.findAllByPage(paramString,page,rows);
	}
	/* (non-Javadoc)
	 * @see com.zxt.framework.dictionary.service.ISqlDicService#findTotalRowsByCondition(java.lang.String, java.lang.String)
	 */
	public int findTotalRowsByCondition(String dictName,String dictGroup){
		
		String queryString = " select count(id) from DataDictionary t where t.name like '%"+dictName+"%' ";
		if(!dictGroup.equals("-1")){
			queryString += " and t.dictionaryGroup.id = '" + dictGroup + "' ";
		}
		return dataDictionaryDao.findTotalRows(queryString);
	}
	/* (non-Javadoc)
	 * @see com.zxt.framework.dictionary.service.ISqlDicService#findAllByCondition(int, int, java.lang.String, java.lang.String)
	 */
	public List findAllByCondition(int page,int rows,String dictName,String dictGroup){
		String paramString = " from DataDictionary t where t.name like '%"+dictName+"%' ";
		if(!dictGroup.equals("-1")){
			paramString += " and t.dictionaryGroup.id = '" + dictGroup + "' ";
		}
		paramString += " order by t.sortNo  ";
		return dataDictionaryDao.findAllByPage(paramString,page,rows);
	}



	/* (non-Javadoc)
	 * @see com.zxt.framework.dictionary.service.ISqlDicService#insertAll(java.util.List)
	 */
	public void insertAll(List list) {
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			DataDictionary dataDictionary=new DataDictionary();
			dataDictionary.setId(RandomGUID.geneGuid());
			dataDictionary.setState("1");
			dataDictionary.setType("2");
			dataDictionary.setName((String) map.get("name"));
			dataDictionary.setExpression((String) map.get("expression"));
			String dataSource=(String) map.get("dataSource");
			String dictionaryGroup=(String) map.get("dictionaryGroup");
			DataSource source = dataDictionaryDao.getDataSourceById(dataSource);
			if(source!=null){
				dataDictionary.setDataSource(dataDictionaryDao.getDataSourceById(dataSource));
			}
			DictionaryGroup group = dataDictionaryDao.getDicGroupById(dictionaryGroup);
			if(group!=null){
				dataDictionary.setDictionaryGroup(group);
			}
			dataDictionaryDao.create(dataDictionary);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.zxt.framework.dictionary.service.ISqlDicService#update(com.zxt.framework.dictionary.entity.DataDictionary)
	 */
	public void update(DataDictionary dataDictionary) {
		dataDictionaryDao.update(dataDictionary);
	}

	//group
	/* (non-Javadoc)
	 * @see com.zxt.framework.dictionary.service.ISqlDicService#deleteDictGroup(com.zxt.framework.dictionary.entity.DictionaryGroup)
	 */
	public void deleteDictGroup(DictionaryGroup dictionaryGroup) {
		dataDictionaryDao.delete(dictionaryGroup);
	}

	/* (non-Javadoc)
	 * @see com.zxt.framework.dictionary.service.ISqlDicService#deleteDictGroupById(java.lang.String)
	 */
	public void deleteDictGroupById(String id) {
		dataDictionaryDao.delete(findDictGroupById(id));
	}
	/* (non-Javadoc)
	 * @see com.zxt.framework.dictionary.service.ISqlDicService#deleteAllDictGroup(java.util.List)
	 */
	public void deleteAllDictGroup(List paramCollection) {
		dataDictionaryDao.deleteAll(paramCollection);
	}
	/* (non-Javadoc)
	 * @see com.zxt.framework.dictionary.service.ISqlDicService#findAllDictGroup()
	 */
	public List findAllDictGroup() {
		String paramString = " from DictionaryGroup t "; 
		return dataDictionaryDao.find(paramString);
	}
	
	/* (non-Javadoc)
	 * @see com.zxt.framework.dictionary.service.ISqlDicService#findAllOrgGroup()
	 */
	public List findAllOrgGroup() {
		String paramString = " from T_ORGANIZATION t,T_ORG_ORG o where t.OID = o.DOWNID "; 
		return dataDictionaryDao.find(paramString);
	}

	/* (non-Javadoc)
	 * @see com.zxt.framework.dictionary.service.ISqlDicService#findDictGroupById(java.lang.String)
	 */
	public DictionaryGroup findDictGroupById(String id) {
		String paramString = " from DictionaryGroup t where t.id = '" + id + "' ";
		List list = dataDictionaryDao.find(paramString);
		if(list != null && list.size()>0){
			return (DictionaryGroup) list.get(0);
		}
		return null;
	}
	/* (non-Javadoc)
	 * @see com.zxt.framework.dictionary.service.ISqlDicService#findAllDictGroupByIds(java.lang.String)
	 */
	public List findAllDictGroupByIds(String ids){
		String paramString = " from DictionaryGroup t where t.id in (" + ids + ") order by t.sortNo "; 
		return dataDictionaryDao.find(paramString);
	}
	/* (non-Javadoc)
	 * @see com.zxt.framework.dictionary.service.ISqlDicService#findDictGroupTotalRows()
	 */
	public int findDictGroupTotalRows(){
		String queryString = " select count(id) from DictionaryGroup t "; 
		return dataDictionaryDao.findTotalRows(queryString);
	}
	/* (non-Javadoc)
	 * @see com.zxt.framework.dictionary.service.ISqlDicService#findAllDictGroupByPage(int, int)
	 */
	public List findAllDictGroupByPage(int page,int rows){
		String paramString = " from DictionaryGroup t order by t.sortNo ";
		return dataDictionaryDao.findAllByPage(paramString,page,rows);
	}
	/* (non-Javadoc)
	 * @see com.zxt.framework.dictionary.service.ISqlDicService#findDictItemById(java.lang.String)
	 */
	public List findDictItemById(String id){
		String paramString = " from DataDictionary t where t.id='" + id + "' and t.state = '1' "; 
		List list = dataDictionaryDao.find(paramString);
		Map dictMap = new HashMap();
		List dictList = null;
		if(list != null){
			DataDictionary dataDictionary = (DataDictionary) list.get(0);
			if(dataDictionary.getType().equals("1")){
				String expression = dataDictionary.getExpression();
				String[] itemArray = expression.split(";");
				if(itemArray != null){
					dictList = new ArrayList();
					for(int i=0;i<itemArray.length;i++){
						Map map = new HashMap();
						String dictStr = itemArray[i];
						String[] dict = dictStr.split(",");
						if(dict != null && dict.length == 2){
							String key = dict[0];
							String value = dict[1];
							map.put(key, value);
							dictList.add(map);
						}
					}
					//dictMap.put(dataDictionary.getId(), dictList);
				}
			}else{
				//动态
			}
		}
		return dictList;
	}

	/* (non-Javadoc)
	 * @see com.zxt.framework.dictionary.service.ISqlDicService#insertDictGroup(com.zxt.framework.dictionary.entity.DictionaryGroup)
	 */
	public void insertDictGroup(DictionaryGroup dictionaryGroup) {
		dataDictionaryDao.create(dictionaryGroup);
	}

	/* (non-Javadoc)
	 * @see com.zxt.framework.dictionary.service.ISqlDicService#updateDictGroup(com.zxt.framework.dictionary.entity.DictionaryGroup)
	 */
	public void updateDictGroup(DictionaryGroup dictionaryGroup) {
		dataDictionaryDao.update(dictionaryGroup);
	}
	/* (non-Javadoc)
	 * @see com.zxt.framework.dictionary.service.ISqlDicService#setDataDictionaryDao(com.zxt.framework.dictionary.dao.IDataDictionaryDao)
	 */
	public void setDataDictionaryDao(IDataDictionaryDao dataDictionaryDao) {
		this.dataDictionaryDao = dataDictionaryDao;
	}

	/* (non-Javadoc)
	 * @see com.zxt.framework.dictionary.service.ISqlDicService#magicMake(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public List magicMake(String dbSource, String dicGroup, String tableKey,
			String primaryKey, String nameKey) {
		List result=new ArrayList();
		DataSource dataSource=dataDictionaryDao.getDataSourceById(dbSource);
		DictionaryGroup dictionaryGroup=dataDictionaryDao.getDicGroupById(dicGroup);
		//从数据源中提取所有的表名
		List tablename=dataDictionaryDao.findTableName(dataSource);
		//根据tableKey过滤表名
		Matching m=new Matching();
		String matchTable = m.match(tablename, tableKey);
		//根据primaryKey,nameKey过滤键值
		String[] split = matchTable.split(",");
		for (int i = 0; i < split.length; i++) {
			if(split[i]!=null && !"".equals(split[i])){
				//查询所有列名
				List colNames=dataDictionaryDao.findColNames(dataSource,split[i]);
				StringBuffer allcol=new StringBuffer();
				for (int j = 0; j < colNames.size(); j++) {
					Map map=(Map) colNames.get(j);
					allcol.append(map.get("name")+",");
				}
				//从所有列中提取出键与值列
				String matchDicKey=m.match(colNames, primaryKey);
				String matchDicVal= m.match(colNames, nameKey);
				//如果列或值为空则跳过这个表
				if("".equals(matchDicKey)||"".equals(matchDicVal)){
					continue;
				}
				//封装成字典类型
				Map map=new HashMap();
				map.put("dictionaryGroup",dictionaryGroup);
				map.put("dataSource", dataSource);
				map.put("name",split[i]);
				map.put("type", "2");
				map.put("state","1");
				map.put("columnKey", matchDicKey.split(",")[0]);
				map.put("columnVal", matchDicVal.split(",")[0]);
				map.put("extra",matchDicKey+"&"+matchDicVal+"&"+allcol.toString());
				map.put("expression", "select "+matchDicKey.split(",")[0]+","+matchDicVal.split(",")[0]+" from "+split[i]);
				result.add(map);
			}
		}
		return result;
	}

	@Override
	public boolean insert(SqlObj sqlObj, String params) {
		// TODO Auto-generated method stub
		return zxtSqlDicDao.addSql(sqlObj,params);
	}

	@Override
	public String getParamsDataById(String id) {
		// TODO Auto-generated method stub
		String sql = "select params from dbo.ENG_DIC_SQL where sqlid=?";
		Map map = zxtSqlDicDao.queryForObject(sql, new Object[]{id});
		String dataJSon = map.get("params").toString();
		Map json = new HashMap();
		if(dataJSon!=null && !dataJSon.equals("")){
			List list = new ArrayList();
			list = SqlXmlUtil.xmlToSqlParamList(dataJSon);
			json.put("rows", list);
			json.put("total",list.size());
			dataJSon = JSONArray.fromObject(list).toString();
		}else{
			json.put("rows", new ArrayList());
			json.put("total", 0);
		}
		dataJSon = JSONObject.fromObject(json).toString();
		return dataJSon;
	}

	@Override
	public boolean updateSql(SqlObj sqlObj, String params) {
		// TODO Auto-generated method stub
		return  zxtSqlDicDao.updateSql(sqlObj,params);
	}

	@Override
	public void deleteSql(String ids) {
		// TODO Auto-generated method stub
		String sql = "delete from dbo.ENG_DIC_SQL where sqlid in("+ids+")";
		zxtSqlDicDao.update(sql, new Object[]{});
	}

	@Override
	public SqlObj findSqlById(String id) {
		// TODO Auto-generated method stub
		String sql = "select * from dbo.ENG_DIC_SQL where sqlid=?";
		Map map = zxtSqlDicDao.queryForObject(sql, new Object[]{id});
		SqlObj sqlobj = new SqlObj();
		sqlobj.setSqldicid(map.get("sqlid").toString());
		sqlobj.setSqldicname(map.get("sqlname").toString());
		sqlobj.setSqldic_type(map.get("sqltype").toString());
		sqlobj.setSqldic_dataSourceid(map.get("datasourceid").toString());
		sqlobj.setSqldic_expression(map.get("sqlvalue").toString());
		sqlobj.setSqldic_remark(map.get("remark").toString());
		List list = new ArrayList();
		if(map.get("params")!=null &&!map.get("params").toString().equals("")){
			String params = map.get("params").toString();
			list = SqlXmlUtil.xmlToSqlParamList(params);
			
		}
		sqlobj.setSqlParam(list);
		return sqlobj;
	}

	@Override
	public Object execteSqlDic(SqlObj sqlobj,HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return zxtSqlDicDao.execteSqlDic(sqlobj,request,response);
	}
	
}
