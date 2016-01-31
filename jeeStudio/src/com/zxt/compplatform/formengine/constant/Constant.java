package com.zxt.compplatform.formengine.constant;

import com.zxt.compplatform.formengine.util.PropertiesUtil;

/**
 * 系统常量
 * 
 * @author 007
 */
public class Constant {

	/**
	 * tab 标签页 页面类型
	 * 
	 */
	public static final String TAB_LISTPAGE = "listPage";
	public static final String TAB_EDITPAGE = "editPage";
	public static final String TAB_VIEWPAGE = "viewPage";
	/**
	 * 文件序列化的存储地址
	 */
	public static final String XML_ADDRESS = "D:/form_engine/setupXML/";
	public static final String XML_NAME_EDITPAGE = "editPage.xml";
	public static final String XML_NAME_LISTPAGE = "listPage.xml";
	public static final String XML_NAME_QUERYPAGE = "queryPage.xml";
	public static final String XML_NAME_VIEWPAGE = "viewPage.xml";

	/**
	 * 数据源类型
	 */
	public static final String DB_TYPE_ORACLE = "1";
	public static final String DB_TYPE_SQLSERVER = "2";

	/**
	 * 格式化数据
	 */
	public static final String FORMMAT_DATE_TO = "yyyy年MM月d日";
	public static final String FORMMAT_DATE_FROM = "MM/d/yyyy";// 被转换的格式
	/**
	 * 表单域 类型
	 */
	public static final int FORM_FIELD_TYPE_TEXT = 1; // 文本框
	public static final int FORM_FIELD_TYPE_SELECT = 2; // 下拉选
	public static final int FORM_FIELD_TYPE_TEXTAREA = 3; // textarea
	public static final int FORM_FIELD_TYPE_DATA = 4; // 日期控件
	public static final int FORM_FIELD_TYPE_FCKEDITOR = 5; // FCKeditor
	public static final int FORM_FIELD_TYPE_AJAXBOX_TREE = 6; // ajaxbox-tree
	public static final int FORM_FIELD_TYPE_AJAXBOX_LINK = 7; // ajaxbox-link
	public static final int FORM_FIELD_TYPE_AJAXBOX = 8; // 自动补全控件
	public static final int FORM_FIELD_TYPE_UPLOAD = 9; // 上传控件
	public static final int FORM_FIELD_TYPE_HIDDEN = 11; // 上传控件
	public static final int FORM_FIELD_TYPE_RADIO = 12; // ajaxbox-link
	public static final int FORM_FIELD_TYPE_CHECKBOX = 13; // 复选框
	public static final int FORM_FIELD_TYPE_DATETIME = 16; // 时间控件
	public static final int FORM_FIELD_TYPE_AJAXBOX_TREE_ORG = 18; // 带人员的组织机构树控件
	public static final int FORM_FIELD_TYPE_LISTPAGE = 20; // 列表页控件
	public static final int FORM_FIELD_TYPE_AJAXBOX_TREE_ORG_HUMAN = 22;// 人员列表树控件
	public static final int FORM_FIELD_TYPE_CHOOSE_TREE = 21; // 应急选择控件
	/**
	 * 数据字典类型
	 */
	public static final String DICTIONARY_STATIC = "1";
	public static final String DICTIONARY_DYNAMIC = "2";
	public static final String TREE_ROOT = "0";// 树形 数据字典根节点值
	/**
	 * 
	 */
	public static final String TEMP_DIR = "d:/temp/"; // 要在最后加上斜杠:temp/
	public static final String VFILE_DIR = "D:/video/";
	public static final String VIMG_DIR = "D:/img/";
	public static final String Upload = "upload/file";
	public static final String JDBCPROPERTIES_DIR = "D:/workspace6.5/compplatform/src/jdbc.properties";
	/**
	 * 操作列 button Name
	 */
	public static final String UPDATEOPERATOR = "update";
	public static final String VIEWOPERATOR = "view";
	public static final String DELETEOPERATOR = "delete";

	/**
	 * url
	 */
	public static final String DATAGRID_URL = "com_reshData.action?formId=";// 列表页
																			// 数据加载URL
	/**
	 * 数据源类型
	 */
	public static final int DATASOURCE_TYPE_ORACLE = 1;
	public static final int DATASOURCE_TYPE_SQLSERVER = 2;
	public static final int DATASOURCE_TYPE_MYSQL = 3;
	public static final int DATASOURCE_TYPE_DB2 = 4;
	/**
	 * 驱动类型
	 */
	public static final String DATADRIVER_TYPE_ORACLE = "oracle.jdbc.driver.OracleDriver";
	public static final String DATADRIVER_TYPE_SQLSERVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	/**
	 * 菜单数据字典 1=系统,2=系统菜单,3=选项卡,4=功能树菜单
	 */
	public static final String MENU_LEVEL_TREE = "4";
	public static final String MENU_LEVEL_TAB = "3";
	public static final String MENU_LEVEL_SYSMENU = "2";

	public static final String RESC_TYPE_EXTENDPAGE = "6";
	/**
	 * 系统ID
	 */
	public static final String SYSTEM_ID = "10";

	/**
	 * 工作流默认分支条件参数
	 */
	// public static final String DEFAULT_WORKFLOW_PARMER_KEY="plat_parmer";
	public static final String DEFAULT_WORKFLOW_PARMER_KEY = "con_param";// 临时
	public static final String DEFAULT_WORKFLOW_PARMER_VALUE = "0";
	public static final String WORKFLOW_ENABLE = "1";
	public static final String WORKFLOW_DISENAbLE = "0";
	public static final String WORKFLOW_QIDONG_STATE = "草稿暂存";
	/**
	 * 工作流 选择提交节点参数名
	 */
	public static final String WORKFLOW_CHOOSE_KEY = "CHOOSE_CONDITION_ID";

	/**
	 * 数据库字段类型
	 */
	public static final String FORM_FIELD_NVARCHAR = "nvarchar";
	public static final String FORM_FIELD_IMAGE = "image";
	public static final String FORM_FIELD_BLOG = "blog";
	public static final String FORM_FIELD_INTEGER = "integer";
	public static final String FORM_FIELD_LONG = "long";
	public static final String FORM_FIELD_NUMBER = "number";
	public static final String FORM_FIELD_DATETIME = "datetime";
	public static final String FORM_FIELD_TIMESTAMP = "timestamp";
	public static final String FORM_FIELD_DATE = "date";

	/**
	 * 表单操作状态
	 */
	public static final String FORM_STATUS_ADD = "0";
	public static final String FORM_STATUS_EDIT = "1";
	public static final String FORM_STATUS_COPY = "2";
	/**
	 * 构件平台初始化皮肤
	 */
	public static final String PLAT_INIT_SKIP = "deepblue";

	/**
	 * 构件平台皮肤列表
	 */
	public static final String FORM_SKIN_LIST = "[{name:\"blue\",url:\"\"},{name:\"green\",url:\"\"},{name:\"dgreen\",url:\"\"},{name:\"deepblue\",url:\"\"},{name:\"desktop\",url:\"\"},{name:\"yl\",url:\"\"},{name:\"gj\",url:\"\"},{name:\"ww\",url:\"\"}]";

	/**
	 * 构件平台初始化皮肤
	 */
	public static final String PLAT_INIT_CLIENTWIDTH = "1024";

	/**
	 * 系统资源级别
	 */
	public static final String RESOURCE_LEVEL_FUNTION_MENU = "4";
	public static final String RESOURCE_LEVEL_SYSTEM_MENUN = "2";

	/**
	 * 是否设置变量
	 */
	public static final String VARIANT_NO = "0"; // 不设置变量
	public static final String VARIANT_YES = "1"; // 设置变量

	/**
	 * 变量类型
	 */
	public static final String CONSTANT_TYPE = "0"; // 常量
	public static final String SYSTEM_VARIANT_TYPE = "1"; // 系统变量
	/**
	 * 数据库类型
	 */
	public static final String DATABASE_ORACLE = "1"; // oracle
	public static final String DATABASE_SQLSERVER = "2"; // sqlserver
	/**
	 * 系统变量
	 */
	public static final String SYSTEM_VARIANT_DATE = "systemDate";
	public static final String SYSTEM_VARIANT_DATETIME = "systemDateTime";
	public static final String SYSTEM_AUTO_CODE = "AUTO_CODE";
	/**
	 * 字段列状态
	 */
	public static final String DATACLOMUN_ZANCUN = "1";
	/**
	 * 是否是主键
	 */
	public static final Integer PARMERYKEY_TRUE = new Integer(1);
	/**
	 * 数据库字段默认值
	 */
	public static final String DB_FIELD_DEFAULT_VALUE = "";
	/**
	 * 
	 */
	public static final String CUSTOM_PROCESSDEFID_ID = "custom_processDefId";
	/**
	 * 工作流状态
	 */
	public static final String WORK_FLOW_TRAN_SAVE_STAUS = "-1";
	public static final String VIEWPAGE_ACTION_PATH = "../formengine/viewPageAction.action?formId=";// 查看页请求路径
	public static final String LISTPAGE_ACTION_PATH = "../formengine/listPageAction.action?formId=";// 查看页请求路径
	public static final String DICTIONARY_CHACHE_PREFIX = "TRIGGER_ENG_CACHE_";
	/**
	 * 加密方式
	 */
	public static final String PASSWORD_TYPE = PropertiesUtil
			.findPropertiesValue("plat_parameter.properties", "PASSWORD_TYPE");
}
