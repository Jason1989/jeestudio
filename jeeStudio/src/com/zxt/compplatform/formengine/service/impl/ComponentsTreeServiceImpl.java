package com.zxt.compplatform.formengine.service.impl;

import java.util.List;

import net.sf.jsqlparser.JSQLParserException;

import org.apache.log4j.Logger;

import com.googlecode.jsonplugin.JSONUtil;
import com.zxt.compplatform.formengine.constant.Constant;
import com.zxt.compplatform.formengine.dao.ComponentsDao;
import com.zxt.compplatform.formengine.dao.ComponentsTreeDao;
import com.zxt.compplatform.formengine.entity.view.TreeOrgData;
import com.zxt.compplatform.formengine.service.ComponentsTreeService;
import com.zxt.compplatform.formengine.util.TreeUtil;
import com.zxt.framework.cache.service.EHCacheService;
import com.zxt.framework.common.exceptions.AppException;
import com.zxt.framework.dictionary.entity.DataDictionary;
import com.zxt.framework.dictionary.entity.DictionaryCache;
import com.zxt.framework.dictionary.service.DictionaryCacheService;

/**
 * 树控件业务操作实现
 * 
 * @author 007
 */
public class ComponentsTreeServiceImpl implements ComponentsTreeService {

	private static final Logger log = Logger
			.getLogger(ComponentsTreeServiceImpl.class);
	/**
	 * 树控件持久化dao
	 */
	private ComponentsTreeDao componentsTreeDao;
	/**
	 * 组件持久化dao
	 */
	private ComponentsDao componentsDao;
	/**
	 * 缓存相关数据
	 */
	private EHCacheService cacheService;
	/**
	 * 查询缓存记录
	 */
	private DictionaryCacheService dictionaryCacheService;
	/**
	 * 存储到eache中的后缀 
	 */
	public static final String DICTIONARY_EHCACHE_KEY_SUFFIX = "_DIC_CACHE";

	public ComponentsTreeDao getComponentsTreeDao() {
		return componentsTreeDao;
	}

	public void setComponentsTreeDao(ComponentsTreeDao componentsTreeDao) {
		this.componentsTreeDao = componentsTreeDao;
	}

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.formengine.service.ComponentsTreeService#treeData(com.zxt.framework.dictionary.entity.DataDictionary, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public String[] treeData(DataDictionary dataDictionary, String defalutValue) {
		String array[] = new String[2];
		List list = null;
		if (dataDictionary.getDataSource() != null
				&& dataDictionary.getDataSource().getSid() != null) {
			Object result = null;
			try {
				// 1、查看缓存中是不是存在（定义主键:dic主键_dic_cache）
				result = cacheService.getCacheElement(dataDictionary.getId()
						+ DICTIONARY_EHCACHE_KEY_SUFFIX);

				// 2、如果缓存中不存在，则直接从数据库中取出相关数据
				if (result == null) {
					list = componentsTreeDao.treeData(dataDictionary
							.getExpression(), componentsDao
							.queryForDataSource(dataDictionary.getDataSource()
									.getId()));
					// 取出后 直接放到缓存中
					cacheService.addToCache(dataDictionary.getId()
							+ DICTIONARY_EHCACHE_KEY_SUFFIX, list);
				} else {
					// 3、如果在缓存中存在，则查看相关状态标识是不是为脏数据，如果是则直接查询数据库
					List cacheList = dictionaryCacheService
							.getDictionaryCacheRecord(dataDictionary
									.getExpression());
					if (cacheList.size() > 0) {
						DictionaryCache dictionaryCache = (DictionaryCache) cacheList
								.get(0);
						if (dictionaryCache.getIsDirty() == 1) {
							list = componentsTreeDao.treeData(dataDictionary
									.getExpression(), componentsDao
									.queryForDataSource(dataDictionary
											.getDataSource().getId()));
							// 取出后 直接放到缓存中
							cacheService.addToCache(dataDictionary.getId()
									+ DICTIONARY_EHCACHE_KEY_SUFFIX, list);
						} else {
							// 4、如果缓存中存在且数据缓存标识不为脏数据，则直接从缓存中获取
							list = (List) result;
						}
					} else {
						// 如果没有配置缓存项，则正常的方式进行
						list = componentsTreeDao.treeData(dataDictionary
								.getExpression(), componentsDao
								.queryForDataSource(dataDictionary
										.getDataSource().getId()));
					}
				}
			} catch (AppException e) {
				e.printStackTrace();
			} catch (JSQLParserException e) {
				// 如果没有配置缓存项，则正常的方式进行
				list = componentsTreeDao.treeData(dataDictionary
						.getExpression(), componentsDao
						.queryForDataSource(dataDictionary.getDataSource()
								.getId()));
			}

		}
		array[1] = TreeUtil.dictionaryValue(list, defalutValue);
	
		//GUOWEIXIN 自定义树的 父节点，权显示：所传节点和其子节点信息
		String root = Constant.TREE_ROOT;
		String dataRootId=dataDictionary.getDic_root_id();
		if(!"".equals(dataRootId) && null!=dataRootId){
			root=dataRootId;
		}
		/**
		 * 加载时选中节点
		 */
		list = TreeUtil.treeChecked(list, defalutValue);
		/**
		 * 基于根节点 封装
		 */
		if(!"".equals(dataRootId) && null!=dataRootId){
			list = TreeUtil.treeAlgorithmForTreeData(list, root);
		}else{	
			list = TreeUtil.treeAlgorithm(list, root);
			if (list == null) {
				list = TreeUtil
						.treeAlgorithm(list, Constant.DB_FIELD_DEFAULT_VALUE);
			}
		}	
		/**
		 * 
		 */
		try {
			array[0] = JSONUtil.serialize(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return array;
	}

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.formengine.service.ComponentsTreeService#treeData(com.zxt.framework.dictionary.entity.DataDictionary, java.lang.String, java.lang.String)
	 */
	public String[] treeData(DataDictionary dataDictionary,
			String defalutValue, String parentId) {
		// TODO Auto-generated method stub
		String array[] = new String[2];
		List list = null;
		if (dataDictionary.getDataSource() != null
				&& dataDictionary.getDataSource().getSid() != null) {
			list = componentsTreeDao.treeData(dataDictionary.getExpression(),
					componentsDao.queryForDataSource(dataDictionary
							.getDataSource().getId()));
		}

		array[1] = TreeUtil.dictionaryValue(list, defalutValue);

		String root = Constant.TREE_ROOT;
		/**
		 * 加载时选中节点
		 */
		list = TreeUtil.treeChecked(list, defalutValue);
		/**
		 * 基于某节点 封装
		 */
		list = TreeUtil.treeAlgorithm(list, parentId);
		if (list == null) {
			list = TreeUtil
					.treeAlgorithm(list, Constant.DB_FIELD_DEFAULT_VALUE);
		}
		/**
		 * 
		 */
		try {
			array[0] = JSONUtil.serialize(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return array;
	}

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.formengine.service.ComponentsTreeService#treeOrgData(com.zxt.framework.dictionary.entity.DataDictionary, java.lang.String, java.lang.String)
	 */
	public String[] treeOrgData(DataDictionary dataDictionary,
			String defalutValue, String oid) {
		// TODO Auto-generated method stub
		String array[] = new String[2];
		List list = null;
		String sql = "";
		if ("1".equals(oid)) {
			sql = "select t.oid as id ,t.oname as text,t_o.upid as parent_i_d,0 as isuser from t_organization t left join t_org_org t_o on t.oid = t_o.downid "
					+ "union select userid as id,uname as text,oid as parent_i_d,1 as isuser from user_union_view where oid in (select t.oid as oid  from t_organization t left join t_org_org t_o on t.oid = t_o.downid)";
		} else {
			sql = "select t.oid as id ,t.oname as text,t_o.upid as parent_i_d,0 as isuser from t_organization t left join t_org_org t_o on t.oid = t_o.downid where  t.OID = '"
					+ oid
					+ "' or t_o.UPID = '"
					+ oid
					+ "'"
					+ "union select userid as id,uname as text,oid as parent_i_d,1 as isuser from user_union_view where oid in (select t.oid as oid  from t_organization t left join t_org_org t_o on t.oid = t_o.downid where  t.OID = '"
					+ oid + "' or t_o.UPID = '" + oid + "')";
		}
		list = componentsTreeDao.treeOrgData(sql, componentsDao
				.queryForDataSource(dataDictionary.getDataSource().getId()));

		/*
		 * if (dataDictionary.getDataSource() != null &&
		 * dataDictionary.getDataSource().getSid() != null) { Object result =
		 * null; try { // 1、查看缓存中是不是存在（定义主键:dic主键_dic_cache） result =
		 * cacheService.getCacheElement(dataDictionary.getId() +
		 * DICTIONARY_EHCACHE_KEY_SUFFIX);
		 *  // 2、如果缓存中不存在，则直接从数据库中取出相关数据 if (result == null) { list =
		 * componentsTreeDao.treeOrgData(sql,
		 * componentsDao.queryForDataSource(dataDictionary
		 * .getDataSource().getId())); //取出后 直接放到缓存中
		 * cacheService.addToCache(dataDictionary.getId()+DICTIONARY_EHCACHE_KEY_SUFFIX,
		 * list); } else { // 3、如果在缓存中存在，则查看相关状态标识是不是为脏数据，如果是则直接查询数据库 List
		 * cacheList = dictionaryCacheService .getDictionaryCacheRecord(sql);
		 * if(cacheList.size()>0){ DictionaryCache dictionaryCache =
		 * (DictionaryCache)cacheList.get(0); if(dictionaryCache.getIsDirty() ==
		 * 1){ list = componentsTreeDao.treeOrgData(sql,
		 * componentsDao.queryForDataSource(dataDictionary
		 * .getDataSource().getId())); //取出后 直接放到缓存中
		 * cacheService.addToCache(dataDictionary.getId()+DICTIONARY_EHCACHE_KEY_SUFFIX,
		 * list); }else{ // 4、如果缓存中存在且数据缓存标识不为脏数据，则直接从缓存中获取 list = (List)result; }
		 * }else{ //如果没有配置缓存项，则正常的方式进行 list = (List)result; } } } catch
		 * (AppException e) { e.printStackTrace(); } catch (JSQLParserException
		 * e) { e.printStackTrace(); }
		 *  }
		 */
		array[1] = TreeUtil.dictionaryOrgValue(list, defalutValue);
		sql = "select * from t_organization o where o.OID = (select t.UPID from T_ORG_ORG t where t.DOWNID = '"
				+ oid + "' )";
		List plist = componentsTreeDao.treeOrgData(sql, componentsDao
				.queryForDataSource(dataDictionary.getDataSource().getId()));
		String rootid = "";
		if (plist != null && plist.size() > 0) {
			for (int i = 0; i < plist.size(); i++) {
				TreeOrgData temData = (TreeOrgData) plist.get(i);
				rootid = temData.getId();
			}
		}
		if ("1".equals(oid)) {
			rootid = "1";
		}
		String root = rootid;
		/**
		 * s 加载时选中节点
		 */
		list = TreeUtil.treeChecked(list, defalutValue);
		/**
		 * 基于根节点 封装
		 */
		list = TreeUtil.treeOrgAlgorithm(list, root);
		if (list == null) {
			list = TreeUtil.treeOrgAlgorithm(list,
					Constant.DB_FIELD_DEFAULT_VALUE);
		}
		/**
		 * 
		 */
		try {
			array[0] = JSONUtil.serialize(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return array;
	}

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.formengine.service.ComponentsTreeService#treeHumanData(com.zxt.framework.dictionary.entity.DataDictionary, java.lang.String, java.lang.String)
	 */
	public String[] treeHumanData(DataDictionary dataDictionary,
			String defaultValue, String oid) {
		// TODO Auto-generated method stub
		String array[] = new String[2];
		List list = null;
		List humanlist = null;
		String sql = "";
		if ("1".equals(oid)) {
			sql = "select t.oid as id ,t.oname as text,t_o.upid as parent_i_d,0 as isuser from t_organization t left join t_org_org t_o on t.oid = t_o.downid ";
		} else {
			sql = "select t.oid as id ,t.oname as text,t_o.upid as parent_i_d,0 as isuser from t_organization t left join t_org_org t_o on t.oid = t_o.downid where  t.OID = '"
					+ oid + "' or t_o.UPID = '" + oid + "'";
		}

		list = componentsTreeDao.treeOrgData(sql, componentsDao
				.queryForDataSource(dataDictionary.getDataSource().getId()));
		String humansql = "";
		if ("1".equals(oid)) {
			humansql = "select t.oid as id ,t.oname as text,t_o.upid as parent_i_d,0 as isuser from t_organization t left join t_org_org t_o on t.oid = t_o.downid "
					+ "union select userid as id,uname as text,oid as parent_i_d,1 as isuser from user_union_view where oid in (select t.oid as oid  from t_organization t left join t_org_org t_o on t.oid = t_o.downid)";
		} else {
			humansql = "select t.oid as id ,t.oname as text,t_o.upid as parent_i_d,0 as isuser from t_organization t left join t_org_org t_o on t.oid = t_o.downid where  t.OID = '"
					+ oid
					+ "' or t_o.UPID = '"
					+ oid
					+ "'"
					+ "union select userid as id,uname as text,oid as parent_i_d,1 as isuser from user_union_view where oid in (select t.oid as oid  from t_organization t left join t_org_org t_o on t.oid = t_o.downid where  t.OID = '"
					+ oid + "' or t_o.UPID = '" + oid + "')";
		}
		humanlist = componentsTreeDao.treeOrgData(humansql, componentsDao
				.queryForDataSource(dataDictionary.getDataSource().getId()));
		array[1] = TreeUtil.dictionaryOrgValue(humanlist, defaultValue);
		sql = "select * from t_organization o where o.OID = (select t.UPID from T_ORG_ORG t where t.DOWNID = '"
				+ oid + "' )";
		List plist = componentsTreeDao.treeOrgData(sql, componentsDao
				.queryForDataSource(dataDictionary.getDataSource().getId()));
		String rootid = "";
		if (plist != null && plist.size() > 0) {
			for (int i = 0; i < plist.size(); i++) {
				TreeOrgData temData = (TreeOrgData) plist.get(i);
				rootid = temData.getId();
			}
		}
		if ("1".equals(oid)) {
			rootid = "1";
		}
		String root = rootid;
		/**
		 * s 加载时选中节点
		 */
		list = TreeUtil.treeChecked(list, defaultValue);
		/**
		 * 基于根节点 封装
		 */
		list = TreeUtil.treeHumanAlgorithmRoot(list, root);
		if (list == null) {
			list = TreeUtil.treeHumanAlgorithm(list,
					Constant.DB_FIELD_DEFAULT_VALUE);
		}
		/**
		 * 
		 */
		try {
			array[0] = JSONUtil.serialize(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return array;
	}

	public ComponentsDao getComponentsDao() {
		return componentsDao;
	}

	public void setComponentsDao(ComponentsDao componentsDao) {
		this.componentsDao = componentsDao;
	}

	public EHCacheService getCacheService() {
		return cacheService;
	}

	public void setCacheService(EHCacheService cacheService) {
		this.cacheService = cacheService;
	}

	public DictionaryCacheService getDictionaryCacheService() {
		return dictionaryCacheService;
	}

	public void setDictionaryCacheService(
			DictionaryCacheService dictionaryCacheService) {
		this.dictionaryCacheService = dictionaryCacheService;
	}

}
