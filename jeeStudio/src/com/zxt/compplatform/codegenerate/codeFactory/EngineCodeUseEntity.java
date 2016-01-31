package com.zxt.compplatform.codegenerate.codeFactory;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.zxt.compplatform.formengine.entity.dataset.TableVO;
import com.zxt.compplatform.formengine.entity.view.BasePage;

/**
 * 
 * @author lenny
 * @Description  引擎代码生成实体类，对freemarker模版进行设置
 * @created Nov 15, 2012 2:23:10 PM
 * @History 
 * @version v1.0
 */
public class EngineCodeUseEntity implements Serializable {
	
	
	private static final long serialVersionUID = -3761381550261469666L;
	/**
	 * 实体MODEL
	 */
	public static int CODE_GENERATE_TYPE_MODEL =1;
	/**
	 * 持久化 DAO
	 */
	public static int CODE_GENERATE_TYPE_DAO =2;
	/**
	 * 业务逻辑SERVICE
	 */
	public static int CODE_GENERATE_TYPE_SERVICE =3;
	/**
	 * Contrlor
	 */
	public static int CODE_GENERATE_TYPE_ACTION =4;
	/**
	 * 视图View
	 */
	public static int CODE_GENERATE_TYPE_VIEW =5;
	
	//public static Map typeMap = new HashMap();
	
	/**
	 * 实体模版
	 */
	public static String CODE_GENERATE_MODEL_FTL = "model.ftl";
	/**
	 * dao代码生成模版生成模版文件名称
	 */
	public static String CODE_GENERATE_DAO_FTL = "dao.ftl";
	/**
	 * dao实现代码生成模版生成模版文件名称
	 */
	public static String CODE_GENERATE_DAOIMPL_FTL = "daoImpl.ftl";
	/**
	 * 业务逻辑接口层代码生成模版文件名称
	 */
	public static String CODE_GENERATE_SERVICE_FTL = "service.ftl";
	/**
	 * 业务逻辑接口层实现代码生成模版文件名称
	 */
	public static String CODE_GENERATE_SERVICEIMPL_FTL = "serviceImpl.ftl";
	/**
	 * action模版文件
	 */
	public static String CODE_GENERATE_ACTION_FTL = "action.ftl";
	/**
	 * dao帮助类实现模版文件
	 */
	public static String CODE_GENERATE_DAOSUPPORT_FTL = "daosupport.ftl";
	/**
	 * 列表页代码生成模版
	 */
	public static String CODE_GENERATE_VIEW_LIST_FTL = "listpage.ftl";
	/**
	 * 编辑页代码生成模版
	 */
	public static String CODE_GENERATE_VIEW_EDIT_FTL = "editpage.ftl";
	/**
	 * 查看页代码生成模版
	 */
	public static String CODE_GENERATE_VIEW_VIEW_FTL = "viewpage.ftl";
	
	public static Set tempSet = new HashSet();
	/**
	 * 类名称
	 */
	private String className;
	/**
	 * 报名称
	 */
	private String packageName;
	/**
	 * 版本号
	 */
	private String versionId;
	/**
	 * 当前时间
	 */
	private String currentTime;
	/**
	 * 根目录
	 */
	private String basePath;
	/**
	 * jar包名称
	 */
	private String jarName;
	/**
	 * 输出路径
	 */
	private String outPath;
	/**
	 * 类型
	 */
	private int codeType;
	/**
	 * sql
	 */
	private String sql;
	/**
	 * 列表
	 */
	private List tables;
	/**
	 * 基础页面
	 */
	private BasePage basePage;
	/**
	 * 表格VO
	 */
	private TableVO tableVo;
	
	public EngineCodeUseEntity(){
		//typeMap.put(Integer.valueOf(CODE_GENERATE_TYPE_MODEL), CODE_GENERATE_MODEL_FTL);
		//typeMap.put(Integer.valueOf(CODE_GENERATE_TYPE_DAO), CODE_GENERATE_DAO_FTL);
	}

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public String getOutPath() {
		return outPath;
	}

	public void setOutPath(String outPath) {
		this.outPath = outPath;
	}

	public int getCodeType() {
		return codeType;
	}

	public void setCodeType(int codeType) {
		this.codeType = codeType;
	}

	public TableVO getTableVo() {
		return tableVo;
	}

	public void setTableVo(TableVO tableVo) {
		this.tableVo = tableVo;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getVersionId() {
		return versionId;
	}

	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}

	public String getCurrentTime() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return sf.format(new Date());
	}

	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getJarName() {
		return jarName;
	}

	public void setJarName(String jarName) {
		this.jarName = jarName;
	}

	public List getTables() {
		return tables;
	}

	public void setTables(List tables) {
		this.tables = tables;
	}

	public BasePage getBasePage() {
		return basePage;
	}

	public void setBasePage(BasePage basePage) {
		this.basePage = basePage;
	}

	

	
}
