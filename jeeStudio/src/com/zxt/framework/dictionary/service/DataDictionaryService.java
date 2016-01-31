package com.zxt.framework.dictionary.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zxt.compplatform.datasource.entity.DataSource;
import com.zxt.framework.common.util.RandomGUID;
import com.zxt.framework.dictionary.dao.IDataDictionaryDao;
import com.zxt.framework.dictionary.entity.DataDictionary;
import com.zxt.framework.dictionary.entity.DictionaryGroup;
import com.zxt.framework.dictionary.util.Matching;

public class DataDictionaryService implements IDataDictionaryService {

	private IDataDictionaryDao dataDictionaryDao;

	/**
	 * 删除方法
	 */
	public void delete(DataDictionary dataDictionary) {
		dataDictionaryDao.delete(dataDictionary);
	}

	/**
	 * 主键删除
	 */
	public void deleteById(String id) {
		dataDictionaryDao.delete(findById(id));
	}

	/**
	 * 删除数据源
	 */
	public void deleteAllByDataSourceId(String dataSourceId) {
		List list = findAllByDataSourceId(dataSourceId);
		if (list != null && list.size() > 0) {
			dataDictionaryDao.deleteAll(list);
		}
	}

	/**
	 * 删除全部
	 */
	public void deleteAll(List paramCollection) {
		dataDictionaryDao.deleteAll(paramCollection);
	}

	/**
	 * 查询全部
	 */
	public List findAll() {
		String paramString = " from DataDictionary t order by t.sortNo ";
		return dataDictionaryDao.find(paramString);
	}
	/**
	 * 按名称查询
	 */
	public DictionaryGroup findGroupByName(String name) {
		String paramString = " from DictionaryGroup t where t.name = '" + name
		+ "' ";
		List list = dataDictionaryDao.find(paramString);
		if (list != null && list.size() > 0) {
			return (DictionaryGroup) list.get(0);
		}
		return null;
	}

	/**
	 * 查询是否存在
	 */
	public boolean isExist(String id, String name) {
		String paramString = " from DataDictionary t where t.id = '" + id
				+ "' or t.name = '" + name + "' ";
		List list = dataDictionaryDao.find(paramString);
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否更新
	 */
	public boolean isExistUpdate(String id, String name) {
		String paramString = " from DataDictionary t where t.id <> '" + id
				+ "' and t.name = '" + name + "' ";
		List list = dataDictionaryDao.find(paramString);
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}
	public boolean isDicGroupExistUpdate(String id,String name){
		String paramString = " from DictionaryGroup t where t.id <> '" + id
		+ "' and t.name = '" + name + "' ";
		List list = dataDictionaryDao.find(paramString);
		if (list != null && list.size() > 0) {
		return true;
		}
		return false;	
	}
	/**
	 * 查询数据字典
	 */
	public DataDictionary findById(String id) {
		String paramString = " from DataDictionary t where t.id = '" + id
				+ "' ";
		List list = dataDictionaryDao.find(paramString);
		if (list != null && list.size() > 0) {
			return (DataDictionary) list.get(0);
		}
		return null;
	}

	/**
	 * 查询全部数据源
	 */
	public List findAllByDataSourceId(String dataSourceId) {
		String paramString = " from DataDictionary t where t.dataSource.id = '"
				+ dataSourceId + "'";
		return dataDictionaryDao.find(paramString);
	}

	/**
	 * 查询全部数据字典
	 */
	public List findAllByIds(String ids) {
		String paramString = " from DataDictionary t where t.id in (" + ids
				+ ")  order by t.sortNo ";
		return dataDictionaryDao.find(paramString);
	}

	/**
	 * 查询全部数据分组
	 */
	public List findAllByGroupIds(String ids) {
		String paramString = " from DataDictionary t where t.dictionaryGroup.id in ("
				+ ids + ")  order by t.sortNo ";
		return dataDictionaryDao.find(paramString);
	}

	/**
	 * 查询数据对象分组
	 */
	public List findByDictGroupId(String dictGroupId) {
		String paramString = " from DataDictionary t where t.dictionaryGroup.id = '"
				+ dictGroupId + "'  order by t.sortNo  ";
		return dataDictionaryDao.find(paramString);
	}

	/**
	 * 查询数据字典
	 */
	public List findDictLikeName(String dictName) {
		String paramString = " from DataDictionary t where t.name like '%"
				+ dictName + "%'  order by t.sortNo  ";
		return dataDictionaryDao.find(paramString);
	}
	/**
	 * 根据字典分组ID得到下方字典行。
	 * GUOWEIXIN
	 */
	public int findTotalRowsByGroupId(String groupId) {
		String queryString = " select count(id) from DataDictionary t  where t.dictionaryGroup.id='"+groupId+"'";
		return dataDictionaryDao.findTotalRows(queryString);
	}
	/**
	 * 查询当前页数据
	 * GUOWEIXIN
	 */
	public List findAllByPageByGroupId(int page, int rows,String groupId) {
		String paramString = " from DataDictionary t  where t.dictionaryGroup.id='"+groupId+"'"+  "order by t.sortNo  ";
		return dataDictionaryDao.findAllByPage(paramString, page, rows);
	}
	/**
	 * 查询全部行
	 */
	public int findTotalRows() {
		String queryString = " select count(id) from DataDictionary t ";
		return dataDictionaryDao.findTotalRows(queryString);
	}

	/**
	 * 查询分页行
	 * 
	 * @return
	 */
	public int findSqlTotalRows() {
		String queryString = " select count(id) from DataDictionary t ";
		return dataDictionaryDao.findTotalRows(queryString);
	}

	/**
	 * 查询当前页数据
	 */
	public List findAllByPage(int page, int rows) {
		String paramString = " from DataDictionary t  order by t.sortNo  ";
		return dataDictionaryDao.findAllByPage(paramString, page, rows);
	}

	/**
	 * 查询数据字典行号
	 */
	public int findTotalRowsByCondition(String dictName, String dictGroup) {

		String queryString = " select count(id) from DataDictionary t where t.name like '%"
				+ dictName + "%' ";
		if (!dictGroup.equals("-1")) {
			queryString += " and t.dictionaryGroup.id = '" + dictGroup + "' ";
		}
		return dataDictionaryDao.findTotalRows(queryString);
	}

	/**
	 * 查询当前页全部数据字典
	 */
	public List findAllByCondition(int page, int rows, String dictName,
			String dictGroup) {
		String paramString = " from DataDictionary t ";
		//GUOWEIXIN 加此处目的在于 当不填写分组下的 字典名称时，默认查出其分组下的所有
		if(!"".equals(dictName) || !dictGroup.equals("-1")){
			paramString+=" where ";
		}
		//if(!"".equals(dictName)){
		//paramString+=" t.name like '%"+ dictName + "%' ";
		//}
		//添加查询时包含特殊字符"_"的情况
		if(!"".equals(dictName)){
			if(dictName.contains("_")){
				dictName=dictName.replace("_", "\\"+"_");
				paramString+=" t.name like '%"+ dictName + "%' escape '\\' ";
			}
			else{
				paramString+=" t.name like '%"+ dictName + "%' ";	
			}
		}
		if(!"".equals(dictName)&& !"-1".equals(dictGroup)){
			paramString+=" and  ";
		}
		if (!dictGroup.equals("-1")) {
			paramString += "  t.dictionaryGroup.id = '" + dictGroup + "' ";
		}
		paramString += " order by t.sortNo  ";
		return dataDictionaryDao.findAllByPage(paramString, page, rows);
	}

	/**
	 * 插入数据
	 */
	public void insert(DataDictionary dataDictionary) {
		dataDictionaryDao.create(dataDictionary);
	}

	/**
	 * 插入全部数据
	 */
	public void insertAll(List list) {
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			/**
			 * 设置数据字典
			 */
			DataDictionary dataDictionary = new DataDictionary();
			dataDictionary.setId(RandomGUID.geneGuid());
			dataDictionary.setState("1");
			dataDictionary.setType("2");
			dataDictionary.setName((String) map.get("name"));
			dataDictionary.setExpression((String) map.get("expression"));
			String dataSource = (String) map.get("dataSource");
			String dictionaryGroup = (String) map.get("dictionaryGroup");
			/**
			 * 构建数据源
			 */
			DataSource source = dataDictionaryDao.getDataSourceById(dataSource);
			if (source != null) {
				dataDictionary.setDataSource(dataDictionaryDao
						.getDataSourceById(dataSource));
			}
			DictionaryGroup group = dataDictionaryDao
					.getDicGroupById(dictionaryGroup);
			if (group != null) {
				dataDictionary.setDictionaryGroup(group);
			}
			dataDictionaryDao.create(dataDictionary);
		}
	}
	/**
	 * 插入全部数据
	 */
	public boolean insertAllDataDictionary(List list) {
		boolean flag =true;
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			/**
			 * 设置数据字典
			 */
			DataDictionary dataDictionary = new DataDictionary();
			dataDictionary.setId(RandomGUID.geneGuid());
			dataDictionary.setState("1");
			dataDictionary.setType("2");
			dataDictionary.setName((String) map.get("name"));
			dataDictionary.setExpression((String) map.get("expression"));
			String dataSource = (String) map.get("dataSource");
			String dictionaryGroup = (String) map.get("dictionaryGroup");
			String expression = (String) map.get("expression");
			//查询是否数据库存在相同数据字典记录
			flag = findByGroupAndExpress(dataSource,expression);
			if(flag){
				flag=false;
				break;
			}
			else{
				//构建数据源
				DataSource source = dataDictionaryDao.getDataSourceById(dataSource);
				if (source != null) {
					dataDictionary.setDataSource(dataDictionaryDao
							.getDataSourceById(dataSource));
				}
				DictionaryGroup group = dataDictionaryDao
				.getDicGroupById(dictionaryGroup);
				if (group != null) {
					dataDictionary.setDictionaryGroup(group);
				}
				dataDictionaryDao.create(dataDictionary);
				flag=true;
			}
		}
		return flag;
	}

	/**
	 * 更新数据字典
	 */
	public void update(DataDictionary dataDictionary) {
		dataDictionaryDao.update(dataDictionary);
	}

	/**
	 * 删除数据字典分组
	 */
	// group
	public void deleteDictGroup(DictionaryGroup dictionaryGroup) {
		dataDictionaryDao.delete(dictionaryGroup);
	}

	/**
	 * 删除数据字典分组
	 */
	public void deleteDictGroupById(String id) {
		dataDictionaryDao.delete(findDictGroupById(id));
	}

	/**
	 * 删除全部数据字典分组
	 */
	public void deleteAllDictGroup(List paramCollection) {
		dataDictionaryDao.deleteAll(paramCollection);
	}

	/**
	 * 查询全部数据字典分组
	 */
	public List findAllDictGroup() {
		String paramString = " from DictionaryGroup t ";
		return dataDictionaryDao.find(paramString);
	}

	/**
	 * 查询全部分组
	 */
	public List findAllOrgGroup() {
		String paramString = " from T_ORGANIZATION t,T_ORG_ORG o where t.OID = o.DOWNID ";
		return dataDictionaryDao.find(paramString);
	}

	/**
	 * 查询字典分组
	 */
	public DictionaryGroup findDictGroupById(String id) {
		String paramString = " from DictionaryGroup t where t.id = '" + id
				+ "' ";
		List list = dataDictionaryDao.find(paramString);
		if (list != null && list.size() > 0) {
			return (DictionaryGroup) list.get(0);
		}
		return null;
	}

	/**
	 * 查询选中的分组
	 */
	public List findAllDictGroupByIds(String ids) {
		String paramString = " from DictionaryGroup t where t.id in (" + ids
				+ ") order by t.sortNo ";
		return dataDictionaryDao.find(paramString);
	}

	/**
	 * 查询数据字典分组行数
	 */
	public int findDictGroupTotalRows() {
		String queryString = " select count(id) from DictionaryGroup t ";
		return dataDictionaryDao.findTotalRows(queryString);
	}

	/**
	 * 查询数据字典分页
	 */
	public List findAllDictGroupByPage(int page, int rows) {
		String paramString = " from DictionaryGroup t order by t.sortNo ";
		return dataDictionaryDao.findAllByPage(paramString, page, rows);
	}

	/**
	 * 查询字典动态字典
	 */
	public List findDictItemById(String id) {
		String paramString = " from DataDictionary t where t.id='" + id
				+ "' and t.state = '1' ";
		List list = dataDictionaryDao.find(paramString);
		Map dictMap = new HashMap();
		List dictList = null;
		if (list != null) {
			/**
			 * 获取字典
			 */
			DataDictionary dataDictionary = (DataDictionary) list.get(0);
			if (dataDictionary.getType().equals("1")) {
				String expression = dataDictionary.getExpression();
				String[] itemArray = expression.split(";");
				if (itemArray != null) {
					dictList = new ArrayList();
					for (int i = 0; i < itemArray.length; i++) {
						Map map = new HashMap();
						/**
						 * 封装字典值
						 */
						String dictStr = itemArray[i];
						String[] dict = dictStr.split(",");
						if (dict != null && dict.length == 2) {
							String key = dict[0];
							String value = dict[1];
							map.put(key, value);
							dictList.add(map);
						}
					}
					// dictMap.put(dataDictionary.getId(), dictList);
				}
			} else {
				// 动态
			}
		}
		return dictList;
	}

	public void insertDictGroup(DictionaryGroup dictionaryGroup) {
		dataDictionaryDao.create(dictionaryGroup);
	}

	public void updateDictGroup(DictionaryGroup dictionaryGroup) {
		dataDictionaryDao.update(dictionaryGroup);
	}

	public void setDataDictionaryDao(IDataDictionaryDao dataDictionaryDao) {
		this.dataDictionaryDao = dataDictionaryDao;
	}

	/**
	 * 查询封装字典表
	 */
	public List magicMake(String dbSource, String dicGroup, String tableKey,
			String primaryKey, String nameKey) {
		List result = new ArrayList();
		DataSource dataSource = dataDictionaryDao.getDataSourceById(dbSource);
		DictionaryGroup dictionaryGroup = dataDictionaryDao
				.getDicGroupById(dicGroup);
		// 从数据源中提取所有的表名
		List tablename = dataDictionaryDao.findTableName(dataSource);
		// 根据tableKey过滤表名
		Matching m = new Matching();
		String matchTable = m.match(tablename, tableKey);
		// 根据primaryKey,nameKey过滤键值
		String[] split = matchTable.split(",");
		for (int i = 0; i < split.length; i++) {
			if (split[i] != null && !"".equals(split[i])) {
				// 查询所有列名
				List colNames = dataDictionaryDao.findColNames(dataSource,
						split[i]);
				StringBuffer allcol = new StringBuffer();
				for (int j = 0; j < colNames.size(); j++) {
					Map map = (Map) colNames.get(j);
					allcol.append(map.get("name") + ",");
				}
				// 从所有列中提取出键与值列
				String matchDicKey = m.match(colNames, primaryKey);
				String matchDicVal = m.match(colNames, nameKey);
				// 如果列或值为空则跳过这个表
				if ("".equals(matchDicKey) || "".equals(matchDicVal)) {
					continue;
				}
				// 封装成字典类型
				Map map = new HashMap();
				map.put("dictionaryGroup", dictionaryGroup);
				map.put("dataSource", dataSource);
				map.put("name", split[i]);
				map.put("type", "2");
				map.put("state", "1");
				map.put("columnKey", matchDicKey.split(",")[0]);
				map.put("columnVal", matchDicVal.split(",")[0]);
				map.put("extra", matchDicKey + "&" + matchDicVal + "&"
						+ allcol.toString());
				map
						.put("expression", "select "
								+ matchDicKey.split(",")[0] + ","
								+ matchDicVal.split(",")[0] + " from "
								+ split[i]);
				result.add(map);
			}
		}
		return result;
	}

	//查询数据库是否存在数据字典记录，根据数据源和表达式查询
	private  boolean findByGroupAndExpress(String dataSource,String expression) {
		boolean flag =false;
		String paramString = " from DataDictionary t where t.expression = '"+ expression+"' and t.dataSource = '"+dataSource+"' ";
		List list =dataDictionaryDao.find(paramString);
		if(null!=list&&list.size()>0){
			flag=true;
		}
		return flag;
	}
	
}
