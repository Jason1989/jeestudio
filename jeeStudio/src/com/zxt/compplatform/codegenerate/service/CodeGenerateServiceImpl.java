package com.zxt.compplatform.codegenerate.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;
import org.apache.tools.ant.taskdefs.Copy;
import org.apache.tools.ant.taskdefs.Mkdir;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.DocumentType;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.DefaultDocumentType;

import com.zxt.compplatform.codegenerate.codeFactory.CodeGenerateFactory;
import com.zxt.compplatform.codegenerate.codeFactory.CodeUseEntity;
import com.zxt.compplatform.codegenerate.dao.ICodeGenerateDao;
import com.zxt.compplatform.codegenerate.entity.EngCodeLog;
import com.zxt.compplatform.codegenerate.util.CodeGenerateException;
import com.zxt.compplatform.codegenerate.util.CodegenerateUtil;
import com.zxt.compplatform.datacolumn.service.IDataColumnService;
import com.zxt.compplatform.datasource.entity.DataSource;
import com.zxt.compplatform.datasource.service.IDataSourceService;
import com.zxt.compplatform.datatable.entity.DataTable;
import com.zxt.compplatform.datatable.service.IDataTableService;
import com.zxt.compplatform.form.entity.Form;
import com.zxt.compplatform.form.service.IFormService;
import com.zxt.compplatform.formengine.entity.view.BasePage;
import com.zxt.compplatform.formengine.entity.view.Button;
import com.zxt.compplatform.formengine.entity.view.ColumnData;
import com.zxt.compplatform.formengine.entity.view.EditMode;
import com.zxt.compplatform.formengine.entity.view.EditPage;
import com.zxt.compplatform.formengine.entity.view.Event;
import com.zxt.compplatform.formengine.entity.view.Group;
import com.zxt.compplatform.formengine.entity.view.JSFunction;
import com.zxt.compplatform.formengine.entity.view.ListPage;
import com.zxt.compplatform.formengine.entity.view.Param;
import com.zxt.compplatform.formengine.entity.view.QueryColumn;
import com.zxt.compplatform.formengine.entity.view.QueryZone;
import com.zxt.compplatform.formengine.entity.view.Tab;
import com.zxt.compplatform.formengine.entity.view.TextColumn;
import com.zxt.compplatform.formengine.entity.view.ViewPage;
import com.zxt.compplatform.formengine.service.IResolveObjectDefService;
import com.zxt.compplatform.formengine.service.PageService;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

public class CodeGenerateServiceImpl implements ICodeGenerateService {
	private ICodeGenerateDao codeGenerateDao;
	private PageService pageService;
	private static final Logger log = Logger
			.getLogger(CodeGenerateServiceImpl.class);

	public void setCodeGenerateDao(ICodeGenerateDao codeGenerateDao) {
		this.codeGenerateDao = codeGenerateDao;
	}

	private String createSpringXml(String packageName, String moduleName,
			String confDir, List tabs) throws CodeGenerateException {
		String fileName = moduleName;
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("beans",
				"http://www.springframework.org/schema/beans");
		root.addAttribute(
						"xsi:schemaLocation",
						"http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.0.xsd");
		root.addAttribute("xmlns:xsi",
				"http://www.w3.org/2001/XMLSchema-instance");
		if (null != tabs && tabs.size() > 0) {
			for (int i = 0; i < tabs.size(); i++) {
				if (i > 0) {
					moduleName = moduleName + "tab" + i;
				}
				Element actionBean = root.addElement("bean");
				actionBean.addAttribute("id", moduleName + "Action");
				actionBean.addAttribute("class", packageName + ".action."
						+ CodegenerateUtil.transformTableName(moduleName)
						+ "Action");
				Element actionProperty = actionBean.addElement("property");
				actionProperty.addAttribute("name", moduleName + "Service");
				actionProperty.addAttribute("ref", moduleName + "Service");

				Element serviceBean = root.addElement("bean");
				serviceBean.addAttribute("id", moduleName + "Service");
				serviceBean.addAttribute("class", packageName + ".service."
						+ CodegenerateUtil.transformTableName(moduleName)
						+ "ServiceImpl");
				Element serviceProperty = serviceBean.addElement("property");
				serviceProperty.addAttribute("name", moduleName + "Dao");
				serviceProperty.addAttribute("ref", moduleName + "Dao");

				Element daoBean = root.addElement("bean");
				daoBean.addAttribute("id", moduleName + "Dao");
				daoBean.addAttribute("class", packageName + ".dao."
						+ CodegenerateUtil.transformTableName(moduleName)
						+ "DaoImpl");
			}
		}
		XMLWriter output;
		OutputFormat format = OutputFormat.createPrettyPrint();
		String springXmlPath = confDir + File.separator + "spring"
				+ File.separator + "application-" + fileName.toLowerCase()
				+ ".xml";
		try {
			output = new XMLWriter(new FileWriter(springXmlPath), format);
			output.write(document);
			output.close();

		} catch (Exception e) {
			throw new CodeGenerateException("创建spring配置文件出错");
		}

		return springXmlPath;
	}

	private String createStruts2Xml(String packageName, String moduleName,
			String confDir, String pagePath, List tabs) {
		String fileName = moduleName;
		Document document = DocumentHelper.createDocument();
		DocumentType def = new DefaultDocumentType();
		def.setElementName("struts");
		def.setSystemID("http://struts.apache.org/dtds/struts-2.1.dtd");
		document.setDocType(def);
		Element root = document.addElement("struts");
		Element packageElement = root.addElement("package");
		packageElement.addAttribute("name", packageName);
		packageElement.addAttribute("extends", "struts-default");
		packageElement.addAttribute("namespace", "/" + moduleName);
		if (null != tabs && tabs.size() > 0) {
			for (int i = 0; i < tabs.size(); i++) {
				if (i > 0) {
					moduleName = moduleName + "tab" + i;
				}
				// 添加action
				Element actionElement = packageElement.addElement("action");
				actionElement.addAttribute("name", moduleName);
				actionElement.addAttribute("class", moduleName + "Action");
				// 转发success
				Element successResult = actionElement.addElement("result");
				successResult.addAttribute("name", "success");
				successResult.setText("/pages/" + fileName + "/" + moduleName
						+ "_list.jsp");

				Element errorResult = actionElement.addElement("result");
				errorResult.addAttribute("name", "error");
				errorResult.setText(moduleName + File.separator + "ss.jsp");
				Element editResult = actionElement.addElement("result");
				editResult.addAttribute("name", "goEdit");
				editResult.setText("/pages/" + fileName + "/" + moduleName
						+ "_edit.jsp");
				Element viewResult = actionElement.addElement("result");
				viewResult.addAttribute("name", "goView");
				viewResult.setText("/pages/" + fileName + "/" + moduleName
						+ "_view.jsp");
			}
		}
		XMLWriter output;
		OutputFormat format = OutputFormat.createPrettyPrint();
		String struts2XmlPath = confDir + File.separator + "struts2"
				+ File.separator + "struts-" + fileName.toLowerCase() + ".xml";
		try {
			output = new XMLWriter(new FileWriter(struts2XmlPath), format);
			output.write(document);
			output.close();

		} catch (Exception e) {
			throw new CodeGenerateException("创建struts2配置文件出错");
		}

		return struts2XmlPath;
	}

	private void createDir(String path) {
		Project prj = new Project();
		Mkdir mkdir = new Mkdir();
		mkdir.setProject(prj);
		mkdir.setDir(new File(path));
		mkdir.execute();
	}

	private void getTabs(List tabs, BasePage basePage) {
		if (null != basePage && null != basePage.getTabs()) {
			List tabList = basePage.getTabs();
			Iterator tabIt = tabList.iterator();
			while (tabIt.hasNext()) {
				BasePage base = (BasePage) tabIt.next();
				tabs.add(base);
				getTabs(tabs, base);
			}
		}
		return;
	}

	// 封装参数
	public boolean saveGenerateCode(String packageName, String basePath,
			String moduleName, String jarName, String pagePath,
			String userBasePath, String formsId, String versionRemark,
			Long userId) throws CodeGenerateException {
		try {
			StringBuffer sb = new StringBuffer();
			String libDir = userBasePath + File.separator + "lib";
			String fileDir = userBasePath + File.separator
					+ moduleName.toLowerCase();
			String confDir = userBasePath + File.separator
					+ moduleName.toLowerCase() + File.separator + "conf";
			String srcDir = userBasePath + File.separator
					+ moduleName.toLowerCase() + File.separator + "src";
			// 解析xml
			/**
			 * update by hgw sql 形成换行注释形式
			 */
			BasePage basePage = resolveObjectDefService.resolveObject(formsId);

			List tabs = new ArrayList();
			getTabs(tabs, basePage);
			List tabsCopy = CodegenerateUtil.copyBySerialize(tabs);
			tabsCopy.add(0, basePage);
			basePage.setTabs(tabs);
			// //ant创建目录
			createDir(userBasePath);
			// //创建lib路径
			createDir(libDir);
			// //文件路径
			createDir(fileDir);
			// //conf路径
			createDir(confDir);
			createDir(confDir + File.separator + "spring");
			String springPath = createSpringXml(packageName, moduleName,
					confDir, tabsCopy);
			sb.append(springPath + ";");
			createDir(confDir + File.separator + "struts2");
			String struts2Path = createStruts2Xml(packageName, moduleName,
					confDir, pagePath, tabsCopy);
			sb.append(struts2Path + ";");
			// //src路径
			createDir(srcDir);
			// //移动包
			Project prj = new Project();
			Copy copy = new Copy();
			copy.setProject(prj);
			copy.setFile(new File(basePath + File.separator + "WEB-INF"
					+ File.separator + "lib" + File.separator + "tools.jar"));
			copy.setTodir(new File(libDir));
			copy.execute();
			//			
			String[] packages = packageName.split("\\.");
			String srcPackage = srcDir;
			for (int i = 0; i < packages.length; i++) {
				srcPackage += (File.separator + packages[i]);
				createDir(srcPackage);
			}
			String versionId = codeGenerateDao.getEngCodeLogVersionId(formsId);
			CodeUseEntity codeUseEntity = new CodeUseEntity();
			codeUseEntity.setBasePath(basePath);
			codeUseEntity.setPackageName(packageName);
			codeUseEntity.setBasePage(basePage);
			codeUseEntity.setJarName(jarName);
			codeUseEntity.setVersionId("V1." + versionId);
			// //实体类
			String modelPackage = srcPackage + File.separator + "entity";
			createDir(modelPackage);
			codeUseEntity.setOutPath(modelPackage);
			codeUseEntity.setCodeType(CodeUseEntity.CODE_GENERATE_TYPE_MODEL);
			CodeGenerateFactory.getInstance(codeUseEntity).codeGenerate(
					codeUseEntity);
			// //持久层
			String daoPackage = srcPackage + File.separator + "dao";
			createDir(daoPackage);
			codeUseEntity.setOutPath(daoPackage);
			codeUseEntity.setCodeType(CodeUseEntity.CODE_GENERATE_TYPE_DAO);
			CodeGenerateFactory.getInstance(codeUseEntity).codeGenerate(
					codeUseEntity);
			int i = 1;
			if (tabs.size() > 0) {
				Iterator tabsIt = tabs.iterator();
				while (tabsIt.hasNext()) {
					BasePage base = (BasePage) tabsIt.next();
					codeUseEntity.setBasePage(base);
					codeUseEntity.setJarName(jarName + "tab" + i);
					CodeGenerateFactory.getInstance(codeUseEntity)
							.codeGenerate(codeUseEntity);
					i++;
				}
			}
			/**
			 * 转换sql 语句加注释
			 */
			// //业务层
			String servicePackage = srcPackage + File.separator + "service";
			createDir(servicePackage);
			codeUseEntity.setJarName(jarName);
			codeUseEntity.setBasePage(basePage);
			codeUseEntity.setOutPath(servicePackage);
			codeUseEntity.setCodeType(CodeUseEntity.CODE_GENERATE_TYPE_SERVICE);
			CodeGenerateFactory.getInstance(codeUseEntity).codeGenerate(
					codeUseEntity);
			i = 1;
			if (tabs.size() > 0) {
				Iterator tabsIt = tabs.iterator();
				while (tabsIt.hasNext()) {
					BasePage base = (BasePage) tabsIt.next();
					codeUseEntity.setBasePage(base);
					codeUseEntity.setJarName(jarName + "tab" + i);
					CodeGenerateFactory.getInstance(codeUseEntity)
							.codeGenerate(codeUseEntity);
					i++;
				}
			}
			// //控制器层
			String actionPackage = srcPackage + File.separator + "action";
			createDir(actionPackage);
			codeUseEntity.setBasePage(basePage);
			codeUseEntity.setJarName(jarName);
			codeUseEntity.setOutPath(actionPackage);
			codeUseEntity.setCodeType(CodeUseEntity.CODE_GENERATE_TYPE_ACTION);
			CodeGenerateFactory.getInstance(codeUseEntity).codeGenerate(
					codeUseEntity);
			i = 1;
			if (tabs.size() > 0) {
				Iterator tabsIt = tabs.iterator();
				while (tabsIt.hasNext()) {
					BasePage base = (BasePage) tabsIt.next();
					codeUseEntity.setBasePage(base);
					codeUseEntity.setJarName(jarName + "tab" + i);
					CodeGenerateFactory.getInstance(codeUseEntity).codeGenerate(codeUseEntity);
					i++;
				}
			}

			// //视图层
			String viewDir = fileDir + File.separator + pagePath;
			createDir(viewDir);
			codeUseEntity.setBasePage(basePage);
			codeUseEntity.setJarName(jarName);
			codeUseEntity.setClassName(jarName);
			codeUseEntity.setOutPath(viewDir);
			codeUseEntity.setCodeType(CodeUseEntity.CODE_GENERATE_TYPE_VIEW);
			CodeGenerateFactory.getInstance(codeUseEntity).codeGenerate(
					codeUseEntity);
			i = 1;
			if (tabs.size() > 0) {
				Iterator tabsIt = tabs.iterator();
				while (tabsIt.hasNext()) {
					BasePage base = (BasePage) tabsIt.next();
					codeUseEntity.setBasePage(base);
					codeUseEntity.setJarName(jarName);
					codeUseEntity.setClassName(jarName + "tab" + i);
					CodeGenerateFactory.getInstance(codeUseEntity)
							.codeGenerate(codeUseEntity);
					i++;
				}
			}
			//			
			// //生成代码日志记录
			EngCodeLog bpTCodeLog = new EngCodeLog();
			bpTCodeLog.setCodeVersionId(versionId);
			bpTCodeLog.setCodeFormsId(formsId);
			bpTCodeLog.setCodeCreateTime(new Date());
			bpTCodeLog.setCodePath(userBasePath);
			bpTCodeLog.setCodeRemark(versionRemark);
			bpTCodeLog.setCodeUserId(userId);
			bpTCodeLog.setCodeAllFile(sb.toString());
			codeGenerateDao.saveBpTCodeLog(bpTCodeLog);
			// 文件打包
			jarFile(basePath, userBasePath, moduleName);

		} catch (Exception e) {
			e.printStackTrace();
			log.error(" Parameter failure,Check the configuration ");
		}
		return true;
	}

	private boolean jarFile(String basePath, String userBasePath,
			String moduleName) {
		File buildFile = new File(basePath + File.separator + "freemarker"
				+ File.separator + "build.xml");
		Project p = new Project();
		p.setProperty("basedir", userBasePath);
		p.setProperty("moduledir", userBasePath + File.separator
				+ moduleName.toLowerCase());
		p.setProperty("jar.file", moduleName + ".jar");
		p.setProperty("antdir", basePath + File.separator + "ant");
		DefaultLogger consoleLogger = new DefaultLogger();
		consoleLogger.setErrorPrintStream(System.err);
		consoleLogger.setOutputPrintStream(System.out);
		consoleLogger.setMessageOutputLevel(Project.MSG_INFO);
		p.addBuildListener(consoleLogger);
		try {
			p.fireBuildStarted();
			p.init();
			ProjectHelper helper = ProjectHelper.getProjectHelper();
			helper.parse(p, buildFile);
			p.executeTarget(p.getDefaultTarget());
			p.fireBuildFinished(null);
		} catch (BuildException e) {
			p.fireBuildFinished(e);
		}
		return true;

	}

	private boolean templateUtil(String baseDir, String template, Map map,
			String outDir) {
		try {
			Configuration cfg = new Configuration();
			cfg.setDirectoryForTemplateLoading(new File(baseDir));
			cfg.setObjectWrapper(new DefaultObjectWrapper());
			cfg
					.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
			Template temp = cfg.getTemplate(template, "UTF-8");
			Writer out = new OutputStreamWriter(new FileOutputStream(outDir),
					"UTF-8");
			temp.process(map, out);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public List findCodeLogList(String formId) {
		return codeGenerateDao.getCodeLogList(formId);
	}

	public EngCodeLog findBpTCodeLog(String codeFormsId, String codeVersionId) {

		return codeGenerateDao.getBpTCodeLog(codeFormsId, codeVersionId);
	}

	private IDataSourceService dataSourceService;
	private IDataTableService dataTableService;
	private IDataColumnService dataColumnService;
	private IFormService formService;
	private IResolveObjectDefService resolveObjectDefService;

	public void setResolveObjectDefService(
			IResolveObjectDefService resolveObjectDefService) {
		this.resolveObjectDefService = resolveObjectDefService;
	}

	public void setDataColumnService(IDataColumnService dataColumnService) {
		this.dataColumnService = dataColumnService;
	}

	public void setDataTableService(IDataTableService dataTableService) {
		this.dataTableService = dataTableService;
	}

	public void setDataSourceService(IDataSourceService dataSourceService) {
		this.dataSourceService = dataSourceService;
	}

	public void setFormService(IFormService formService) {
		this.formService = formService;
	}

	public BasePage parseXmlForCodeGenerate(String formId) {
		BasePage basePage = resolveObjectDefService.resolveObject(formId);
		return basePage;
	}

	/**
	 * 解析数据源
	 * 
	 * @param dataSetNode
	 * @return
	 */
	private DataSource parseDataSource(Element dataSetNode) {
		DataSource dataSource = null;
		List dataSourceNodes = dataSetNode
				.selectNodes("./DataSources/DataSource");
		if (null != dataSourceNodes && dataSourceNodes.size() > 0) {
			Element dataSourceNode = (Element) dataSourceNodes.get(0);
			Element dsNameNode = (Element) dataSourceNode
					.selectSingleNode("./DSName");
			if (null != dsNameNode) {
				dataSource = dataSourceService.findById(dsNameNode.getText()
						.trim());
			}
		}
		return dataSource;
	}

	/**
	 * 解析数据对象
	 * 
	 * @param tableNode
	 * @param dataObjAlias
	 * @param objectColumn
	 * @param isCodeGenerate
	 * @return
	 */
	private String parseFromTable(Element tableNode, Map dataObjAlias,
			Map objectColumn, boolean isCodeGenerate) {
		String[] alias = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j" };
		List fromTableNodes = tableNode
				.selectNodes("./GetTable/FromTables/FromTable");
		Element firstFromTableNode = (Element) tableNode
				.selectSingleNode("./GetTable/FromTables/FromTable[Index=0]");
		fromTableNodes.remove(firstFromTableNode);
		String firstTableName = firstFromTableNode.element("TableName")
				.getText().trim();
		if (isCodeGenerate) {
			DataTable dataTable = dataTableService.findByName(firstTableName);
			List dataColumns = dataColumnService
					.findAllByTableName(firstTableName);
			objectColumn.put(dataTable, dataColumns);
		}
		int i = 0;
		dataObjAlias.put(firstTableName, alias[i]);
		StringBuffer fromSb = new StringBuffer();
		fromSb.append(firstTableName + " " + alias[i]);
		Iterator fromTableIt = fromTableNodes.iterator();
		while (fromTableIt.hasNext()) {
			i++;
			Element fromTableNode = (Element) fromTableIt.next();
			Element tableNameNode = (Element) fromTableNode
					.selectSingleNode("./TableName");
			if (null != tableNameNode) {
				String tableName = tableNameNode.getTextTrim().toUpperCase();
				if (isCodeGenerate) {
					DataTable dataTable = dataTableService
							.findByName(tableName);
					List dataColumns = dataColumnService
							.findAllByTableName(tableName);
					objectColumn.put(dataTable, dataColumns);
				}
				dataObjAlias.put(tableNameNode.getText().trim(), alias[i]);
				Element joinConditionNode = (Element) fromTableNode
						.selectSingleNode("./JoinCondition");
				if (null != joinConditionNode) {
					String joinType = joinConditionNode.attribute("type")
							.getValue().trim();
					if ("INNER".equalsIgnoreCase(joinType)) {
						fromSb.append("	INNER JOIN " + tableName + " "
								+ alias[i] + " 	ON ");
					} else if ("LEFT".equalsIgnoreCase(joinType)) {
						fromSb.append("	LEFT JOIN " + tableName + " "
								+ alias[i] + " 	ON ");
					} else if ("RIGHT".equalsIgnoreCase(joinType)) {
						fromSb.append("	RIGHT JOIN " + tableName + " "
								+ alias[i] + " 	ON ");
					}
					List operateNodes = (List) joinConditionNode
							.selectNodes("./Condition/Operate");
					Iterator operateNodesIt = operateNodes.iterator();
					while (operateNodesIt.hasNext()) {
						Element operateNode = (Element) operateNodesIt.next();
						String operateType = operateNode.attribute("type")
								.getValue().trim();
						Element operandLeftNode = (Element) operateNode
								.selectSingleNode(
										"./Operands/Operand/OprName[Index=0]")
								.getParent();
						Element leftFieldNameNode = (Element) operandLeftNode
								.selectSingleNode("./OprValue/FieldName");
						String leftFieldName = leftFieldNameNode
								.element("Name").getTextTrim();
						String leftTableName = leftFieldNameNode.element(
								"TableName").getTextTrim();
						Element operandRightNode = (Element) operateNode
								.selectSingleNode(
										"./Operands/Operand/OprName[Index=1]")
								.getParent();
						Element rightFieldNameNode = (Element) operandRightNode
								.selectSingleNode("./OprValue/FieldName");
						String rightFieldName = rightFieldNameNode.element(
								"Name").getTextTrim();
						String rightTableName = rightFieldNameNode.element(
								"TableName").getTextTrim();
						fromSb.append(dataObjAlias.get(leftTableName) + "."
								+ leftFieldName + operateType
								+ dataObjAlias.get(rightTableName) + "."
								+ rightFieldName + " AND");
					}
					String tempFromSb = fromSb
							.substring(0, fromSb.length() - 3);
					fromSb.delete(0, fromSb.length());
					fromSb.append(tempFromSb);
				} else {
					fromSb.append("," + tableName + " " + alias[i]);
				}
			}
		}
		return fromSb.toString();
	}

	/**
	 * 解析where
	 * 
	 * @param tableNode
	 * @param whereParamMap
	 * @param dataObjAlias
	 * @return
	 */
	private String parseWhere(Element tableNode, Map whereParamMap,
			Map dataObjAlias) {
		StringBuffer whereSb = new StringBuffer();
		Element whereNode = (Element) tableNode
				.selectSingleNode("./GetTable/Where");
		if (null != whereNode) {
			whereSb.append(" WHERE ");
			List operateNodes = (List) whereNode
					.selectNodes("./Condition/Operate");
			Iterator operateNodesIt = operateNodes.iterator();
			while (operateNodesIt.hasNext()) {
				Element operateNode = (Element) operateNodesIt.next();
				String operateType = operateNode.attribute("type").getValue()
						.trim();
				Element operandLeftNode = (Element) operateNode
						.selectSingleNode("./Operands/Operand/OprName[Index=0]")
						.getParent();
				Element leftOprValueNode = (Element) operandLeftNode
						.selectSingleNode("./OprValue");
				Element operandRightNode = (Element) operateNode
						.selectSingleNode("./Operands/Operand/OprName[Index=1]")
						.getParent();
				Element rightOprValueNode = (Element) operandRightNode
						.selectSingleNode("./OprValue");
				String leftOprValueType = leftOprValueNode.attribute("type")
						.getValue().trim();
				String rightOprValueType = rightOprValueNode.attribute("type")
						.getValue().trim();
				if ("PARAM".equalsIgnoreCase(leftOprValueType)
						|| "VALUE".equalsIgnoreCase(leftOprValueType)) {
					String leftFieldValue = leftOprValueNode.element("Value")
							.getTextTrim();
					Element rightFieldNameNode = (Element) operandRightNode
							.selectSingleNode("./OprValue/FieldName");
					String rightFieldName = rightFieldNameNode.element("Name")
							.getTextTrim();
					String rightTableName = rightFieldNameNode.element(
							"TableName").getTextTrim();
					if ("VALUE".equalsIgnoreCase(leftOprValueType)) {
						whereSb.append("'" + leftFieldValue + "'" + operateType
								+ dataObjAlias.get(rightTableName) + "."
								+ rightFieldName + " AND");
					} else {
						whereSb.append("?" + operateType
								+ dataObjAlias.get(rightTableName) + "."
								+ rightFieldName + " AND");
						Param param = new Param();
						param.setKey(rightFieldName);
						param.setType("varchar2");
						if (whereParamMap.keySet().contains(rightTableName)) {
							List params = (List) whereParamMap
									.get(rightTableName);
							params.add(param);
						} else {
							List whereParams = new ArrayList();
							whereParams.add(param);
							whereParamMap.put(rightTableName, whereParams);
						}
					}
				} else {
					String rightFieldValue = rightOprValueNode.element("Value")
							.getTextTrim();
					Element leftFieldNameNode = (Element) operandLeftNode
							.selectSingleNode("./OprValue/FieldName");
					String leftFieldName = leftFieldNameNode.element("Name")
							.getTextTrim();
					String leftTableName = leftFieldNameNode.element(
							"TableName").getTextTrim();
					if ("VALUE".equalsIgnoreCase(rightOprValueType)) {
						whereSb.append(dataObjAlias.get(leftTableName) + "."
								+ leftFieldName + operateType + "'"
								+ rightFieldValue + "' AND");
					} else {
						whereSb.append("?" + operateType
								+ dataObjAlias.get(leftTableName) + "."
								+ leftFieldName + " AND");
						Param param = new Param();
						param.setKey(leftFieldName);
						param.setType("varchar2");
						if (whereParamMap.keySet().contains(leftTableName)) {
							List params = (List) whereParamMap
									.get(leftTableName);
							params.add(param);
						} else {
							List whereParams = new ArrayList();
							whereParams.add(param);
							whereParamMap.put(leftTableName, whereParams);
						}
					}

				}
			}
		}
		return whereSb.substring(0, whereSb.length() - 3);
	}

	/**
	 * 解析字段
	 * 
	 * @param tableNode
	 * @param dataObjAlias
	 * @return
	 */
	private String parseSelectField(Element tableNode, Map dataObjAlias) {
		StringBuffer selectSb = new StringBuffer();
		List FieldNodes = tableNode.selectNodes("./FieldDef/Field");
		Iterator fieldIt = FieldNodes.iterator();
		while (fieldIt.hasNext()) {
			Element fieldNode = (Element) fieldIt.next();
			Element tableName = (Element) fieldNode
					.selectSingleNode("./FromField/FieldName/TableName");
			Element fieldName = (Element) fieldNode
					.selectSingleNode("./FromField/FieldName/Name");
			if (null != tableName && null != fieldName) {
				selectSb.append(dataObjAlias.get(tableName.getText().trim())
						+ "." + fieldName.getText().trim() + ",");
			}
		}
		return selectSb.toString().substring(0,
				selectSb.toString().length() - 1);
	}

	/**
	 * 解析标签
	 * 
	 * @param root
	 * @return
	 */
	private List parseTabs(Element root) {
		List tabs = new ArrayList();
		Element tabNode = (Element) root.selectSingleNode("./Tabs");
		List pageNodes = tabNode.selectNodes("./Page");
		if (null != tabNode && null != pageNodes) {
			Iterator pageIt = pageNodes.iterator();
			while (pageIt.hasNext()) {
				Element pageNode = (Element) pageIt.next();
				Tab tab = new Tab();
				tab.setUrl(pageNode.attribute("url").getValue());
				if ("true".equalsIgnoreCase(pageNode.attribute("lazyLading")
						.getValue().trim()))
					// tab.setLazyLoading(true);
					// tab.setSortIndex(Integer.parseInt(pageNode.attribute("sortIndex").getValue().trim()));
					tabs.add(tab);
			}
		}
		return tabs;
	}

	/**
	 * 解析按钮
	 * 
	 * @param root
	 * @param canBatch
	 * @param gridButton
	 * @param rowsButton
	 * @param deleteParams
	 * @param type
	 * @return
	 */
	private Map parseButton(Element root, List gridButton, List rowsButton,
			List deleteParams, String type) {
		StringBuffer deleteParamSb = new StringBuffer();
		boolean canBatch = false;
		List buttonNodes = root.selectNodes("./Buttons/Button");
		Iterator buttonIt = buttonNodes.iterator();
		while (buttonIt.hasNext()) {
			Element buttonNode = (Element) buttonIt.next();
			String buttonTypes = buttonNode.attribute("type").getValue().trim();
			int buttonType = Integer.parseInt(buttonTypes);
			Button button = new Button();
			button.setButtonId(buttonNode.attribute("id") == null ? ""
					: buttonNode.attribute("id").getValue().trim());
			button.setButtonName(buttonNode.attribute("name") == null ? ""
					: buttonNode.attribute("name").getValue().trim());
			button.setIconCls(buttonNode.attribute("style") == null ? ""
					: buttonNode.attribute("style").getValue().trim());
			button.setType(buttonTypes);
			// boolean updateParamFlag = false;
			boolean deleteParamFlag = false;
			if (buttonType < Button.BUTTON_TYPE_EDGE) {
				canBatch = true;
				gridButton.add(button);
			} else {
				if (buttonType == Button.BUTTON_SINGLE_EDIT
						&& "listPage".equalsIgnoreCase(type)) {
					deleteParamFlag = true;
				}
				// if(buttonType == Button.BUTTON_SINGLE_DELETE &&
				// "listPage".equalsIgnoreCase(type)){
				// updateParamFlag = true;
				// }
				rowsButton.add(button);
			}
			List eventNodes = buttonNode.selectNodes("./Event");
			List events = new ArrayList();

			Iterator eventNodesIt = eventNodes.iterator();
			while (eventNodesIt.hasNext()) {
				Element eventNode = (Element) eventNodesIt.next();
				Event event = new Event();
				JSFunction jsFunction = new JSFunction();
				jsFunction.setId(eventNode.attribute("JSId").getValue().trim());
				event.setFunctions(jsFunction);
				List paramNodes = eventNode.selectNodes("./Param");
				Iterator paramNodesIt = paramNodes.iterator();
				List params = new ArrayList();
				while (paramNodesIt.hasNext()) {
					Element paramNode = (Element) paramNodesIt.next();
					Param param = new Param();
					param.setKey(paramNode.attribute("key").getValue().trim());
					param.setType(paramNode.attribute("fieldDataType")
							.getValue().trim());
					param.setValue(paramNode.attribute("value").getValue()
							.trim());
					params.add(param);
					if (deleteParamFlag) {
						deleteParamSb.append(" "
								+ paramNode.attribute("key").getValue().trim()
								+ "   ");
						// deleteParamSb.append("
						// "+paramNode.attribute("key").getValue().trim()+"= ?
						// and");
						Param deleteParam = new Param();
						deleteParam.setKey(paramNode.attribute("key")
								.getValue().trim());
						deleteParam.setType(paramNode
								.attribute("fieldDataType").getValue().trim());
						deleteParam.setValue(paramNode.attribute("value")
								.getValue().trim());
						deleteParams.add(deleteParam);
					}
				}
				event.setParas(params);
				events.add(event);
			}
			button.setEvent(events);
		}
		String deleteStr = "";
		if (deleteParamSb.length() > 3)
			deleteStr = deleteParamSb.substring(0, deleteParamSb.length() - 3);
		Map map = new HashMap();
		map.put("sql", deleteStr);
		map.put("canBatch", canBatch + "");
		return map;
	}

	/**
	 * 解析数据列
	 * 
	 * @param root
	 * @param columnDatas
	 */
	private void parseColumnData(Element root, List columnDatas) {
		List columnNodes = root.selectNodes("./Columns/Column");
		Iterator columnIt = columnNodes.iterator();
		while (columnIt.hasNext()) {
			Element columnNode = (Element) columnIt.next();
			Element textColumnNode = (Element) columnNode
					.selectSingleNode("./Text");
			Element dataColumnNode = (Element) columnNode
					.selectSingleNode("./Data");
			Element editModeNode = (Element) columnNode
					.selectSingleNode("./EditMode");
			ColumnData columnData = new ColumnData();
			TextColumn textColumn = new TextColumn();
			EditMode editMode = new EditMode();
			textColumn.setName(textColumnNode.attribute("name").getValue()
					.trim());
			// textColumn.setId(textColumnNode.attribute("id").getValue().trim());
			// textColumn.setStyle(textColumnNode.attribute("style").getValue().trim());
			// textColumn.setSortIndex(Integer.parseInt(textColumnNode.attribute("sortIndex").getValue().trim()));
			// textColumn.setVisible(Boolean.valueOf(textColumnNode.attribute("visible").getValue().trim()));
			columnData.setName(dataColumnNode.attribute("name").getValue()
					.trim());
			columnData.setFieldDataType(dataColumnNode
					.attribute("fieldDataType") == null ? "" : dataColumnNode
					.attribute("fieldDataType").getValue().trim());
			textColumn
					.setAlign(textColumnNode.attribute("align") == null ? "left"
							: textColumnNode.attribute("align").getValue()
									.trim());
			textColumn
					.setWidth(textColumnNode.attribute("width") == null ? "100"
							: textColumnNode.attribute("width").getValue()
									.trim());
			columnData.setType(Integer.parseInt(dataColumnNode
					.attribute("type") == null ? "1" : (dataColumnNode
					.attribute("type").getValue().trim())));
			columnData.setText(textColumn);
			columnDatas.add(columnData);
		}
	}

	/**
	 * 解析setTable块
	 * 
	 * @param tableNode
	 * @param editPage
	 */
	private void parseSetTable(Element tableNode, EditPage editPage) {
		Element setTableNode = (Element) tableNode.element("SetTable");
		Element tableNameNode = (Element) setTableNode
				.selectSingleNode("./SetOption/SetOptionItem/ToTable/TableName");
		String tableName = tableNameNode.getTextTrim();
		StringBuffer insertSB = new StringBuffer();
		StringBuffer insertParamSB = new StringBuffer();
		List insertParams = new ArrayList();
		List updateParams = new ArrayList();
		List keyParams = new ArrayList();
		Map insertParamMap = new HashMap();
		StringBuffer updateSB = new StringBuffer();
		StringBuffer updateKeySB = new StringBuffer();
		Map updateParamMap = new HashMap();
		insertSB.append("INSERT INTO " + tableName.toUpperCase() + "(");
		updateSB.append("UPDATE " + tableName.toUpperCase() + " SET ");
		List keyFieldMapNodes = tableNameNode
				.selectNodes("./FieldMap[@type='KEY']");
		List fieldMapNodes = tableNameNode
				.selectNodes("./FieldMap[@type='IN']");
		keyFieldMapNodes.addAll(fieldMapNodes);
		Iterator keyFieldMapNodesIt = keyFieldMapNodes.iterator();
		while (keyFieldMapNodesIt.hasNext()) {
			Element fieldMapNode = (Element) keyFieldMapNodesIt.next();
			String FieldMapType = fieldMapNode.attribute("type").getValue()
					.trim();
			String fieldType = fieldMapNode.element("Operand")
					.attribute("type").getValue().trim();
			String fieldName = fieldMapNode.selectSingleNode(
					"./Operand/OprName/Name").getText().trim();
			Param param = new Param();
			param.setKey(fieldName);
			param.setType(fieldType);
			insertParams.add(param);
			insertSB.append(fieldName + ",");
			insertParamSB.append("?,");
			if ("KEY".equals(FieldMapType)) {
				updateKeySB.append(fieldName + "=?,");
				Param keyParam = new Param();
				keyParam.setKey(fieldName);
				keyParam.setType(fieldType);
				keyParams.add(keyParam);
			} else {
				updateSB.append(fieldName + "=?,");
				Param updateParam = new Param();
				updateParam.setKey(fieldName);
				updateParam.setType(fieldType);
				updateParams.add(updateParam);
			}
		}
		updateParams.addAll(keyParams);
		insertParamMap.put(tableName, insertParams);
		updateParamMap.put(tableName, updateParams);
		String updateSql = updateSB.substring(0, updateSB.length() - 1)
				+ " WHERE "
				+ updateKeySB.substring(0, updateKeySB.length() - 1);
		editPage.setUpdateSql(updateSql);
		editPage.setUpdateParam(updateParamMap);
		String insertSql = insertSB.substring(0, insertSB.length() - 1)
				+ ") VALUES("
				+ insertParamSB.substring(0, insertParamSB.length() - 1) + ")";
		editPage.setInsertSql(insertSql);
		editPage.setInsertParam(insertParamMap);
	}

	public BasePage parseXml(String formId, boolean isCodeGenerate) {
		try {
			Form engFormForm = formService.findById(formId);
			SAXReader saxReader = new SAXReader();
			Document doc = saxReader.read(new InputStreamReader(engFormForm
					.getSettings().getBinaryStream()));
			Element root = doc.getRootElement();
			String type = root.attribute("type").getValue().trim();

			String title = root.attribute("title").getValue().trim();
			// 数据源
			Element dataSetNode = (Element) root
					.selectSingleNode("./DataMapping/DataSet");
			DataSource dataSource = parseDataSource(dataSetNode);

			// fromTable数据对象
			Map dataObjAlias = new HashMap();
			Map objectColumn = new HashMap();
			Element tableNode = (Element) dataSetNode
					.selectSingleNode("./Table");
			String fromStr = parseFromTable(tableNode, dataObjAlias,
					objectColumn, isCodeGenerate);

			// where
			Map whereParamMap = new HashMap();
			String whereStr = parseWhere(tableNode, whereParamMap, dataObjAlias);

			// 字段
			String selectStr = parseSelectField(tableNode, dataObjAlias);
			// 标签
			List tabs = parseTabs(root);
			// 按钮
			List gridButton = new ArrayList();
			List rowsButton = new ArrayList();
			List deleteParams = new ArrayList();
			Map deleteMap = parseButton(root, gridButton, rowsButton,
					deleteParams, type);
			String deleteStr = (String) deleteMap.get("sql");
			boolean canBatch = Boolean.valueOf(
					deleteMap.get("canBatch").toString()).booleanValue();
			// 数据列
			List columnDatas = new ArrayList();
			parseColumnData(root, columnDatas);

			if ("listPage".equalsIgnoreCase(type)) {
				// 取主表
				DataTable mainDataTable = engFormForm.getDataTable();
				String mainTableName = mainDataTable.getName().toUpperCase();
				ListPage listPage = new ListPage();
				listPage.setDataSource(dataSource);
				listPage.setTabs(tabs);
				listPage.setTitle(title);
				listPage.setObjectColumn(objectColumn);
				listPage.setRowButton(rowsButton);
				listPage.setCanBatch(canBatch);
				listPage.setGridButton(gridButton);
				listPage.setTitle(root.attribute("title").getValue());
				String deleteSql = "DELETE FROM " + mainTableName + " WHERE "
						+ deleteStr;
				listPage.setDeleteSql(deleteSql);
				Map deleteParamMap = new HashMap();
				deleteParamMap.put(mainTableName, deleteParams);
				listPage.setDeleteParam(deleteParamMap);
				// 分页
				Element paginationNode = (Element) root
						.selectSingleNode("./Pagination");
				if (null != paginationNode
						&& "true".equalsIgnoreCase(paginationNode.attribute(
								"visible").getValue())) {
					listPage.setCanShowPagination(true);
				}
				// 数据列
				listPage.setColumnDatas(columnDatas);
				// 操作列
				Map queryColumnsTable = new HashMap();
				List queryColumnsNodes = root
						.selectNodes("./QueryZone/QueryColumn");
				Iterator queryColumnsIt = queryColumnsNodes.iterator();
				QueryZone queryZone = new QueryZone();
				List queryZones = new ArrayList();
				Map queryColumnType = new HashMap();
				String queryCodition = "";
				List queryColumnsView = new ArrayList();
				while (queryColumnsIt.hasNext()) {
					StringBuffer queryConditionSb = new StringBuffer();
					Element queryColumnsNode = (Element) queryColumnsIt.next();
					String columnName = queryColumnsNode.attribute("name")
							.getValue().trim();
					String tableName = queryColumnsNode.attribute("tableName")
							.getValue().trim();
					String dataColumnType = queryColumnsNode.attribute(
							"fieldDataType").getValue().trim();
					int queryType = Integer.parseInt(queryColumnsNode
							.attribute("type").getValue().trim());

					QueryColumn queryColumn = new QueryColumn();
					queryColumn.setName(queryColumnsNode.attribute("name")
							.getValue().trim());
					queryColumn.setText(queryColumnsNode.attribute("text")
							.getValue().trim());
					queryColumn.setTableName(tableName);
					queryColumn.setType(queryType + "");
					queryColumn.setFieldDataType(queryColumnsNode.attribute(
							"fieldDataType").getValue().trim());
					queryColumnType.put(queryColumnsNode.attribute("id")
							.getValue().trim(), queryColumnsNode.attribute(
							"type").getValue().trim());
					queryColumnsView.add(queryColumn);
					if (!queryColumnsTable.containsKey(tableName)) {
						List queryColumns = new ArrayList();
						if (queryType == ColumnData.DATACOLUMN_UI_TYPE_INPUT) {
							queryConditionSb.append(" "
									+ dataObjAlias.get(tableName) + "."
									+ columnName + " LIKE '%'||?||'%' and");
						} else {
							queryConditionSb.append(" "
									+ dataObjAlias.get(tableName) + "."
									+ columnName + " =? and");
						}
						queryColumns.add(queryColumn);
						queryColumnsTable.put(tableName, queryColumns);
					} else {
						if ("input".equalsIgnoreCase(dataColumnType)) {
							queryConditionSb.append(" "
									+ dataObjAlias.get(tableName) + "."
									+ columnName + " LIKE '%'||?||'%' and");
						} else {
							queryConditionSb.append(" "
									+ dataObjAlias.get(tableName) + "."
									+ columnName + " =? and");
						}
						List queryColumns = (List) queryColumnsTable
								.get(tableName);
						queryColumns.add(queryColumn);
					}
					queryCodition = queryConditionSb.substring(0,
							queryConditionSb.length() - 3);
				}
				queryZone.setQueryColumns(queryColumnsView);
				queryZones.add(queryZone);
				listPage.setQueryZone(queryZones);
				listPage.setQueryColumnsTable(queryColumnsTable);
				String querySql = "SELECT " + selectStr + " FROM " + fromStr
						+ whereStr;
				if (!"".equals(whereStr)) {
					querySql += "  AND " + queryCodition;
				} else {
					querySql += "  WHERE " + queryCodition;

				}
				listPage.setSql(querySql);
				Element pagesNode = (Element) root.selectSingleNode("./Pages");
				Element editPageNode = (Element) pagesNode
						.selectSingleNode("./EditPage");
				String editPageFormId = editPageNode == null ? null
						: editPageNode.attribute("formId").getValue().trim();
				if (null != editPageFormId && !"".equals(editPageFormId)
						&& isCodeGenerate) {
					EditPage editPage = (EditPage) parseXml(editPageFormId,
							true);
					listPage.setEditPage(editPage);
				} else {
					EditPage editPage = new EditPage();
					editPage.setId(editPageFormId);
					listPage.setEditPage(editPage);
				}
				Element viewPageNode = (Element) pagesNode
						.selectSingleNode("./ViewPage");
				String viewPageFormId = viewPageNode == null ? null
						: viewPageNode.attribute("formId").getValue().trim();
				if (null != viewPageFormId && !"".equals(viewPageFormId)
						&& isCodeGenerate) {
					ViewPage viewPage = (ViewPage) parseXml(viewPageFormId,
							true);
					listPage.setViewPage(viewPage);
				} else {
					ViewPage viewPage = new ViewPage();
					viewPage.setId(viewPageFormId);
					listPage.setViewPage(viewPage);
				}
				return listPage;
			} else if ("editPage".equalsIgnoreCase(type)) {
				EditPage editPage = new EditPage();
				editPage.setTabs(tabs);
				editPage.setColumnDatas(columnDatas);
				editPage.setTitle(title);
				String findSql = "SELECT " + selectStr + " FROM " + fromStr
						+ whereStr;
				editPage.setFindSql(findSql);
				editPage.setFindParams(whereParamMap);
				editPage.setDataSource(dataSource);
			  //editPage.setButton(editButton);
			  // 插入语句
				parseSetTable(tableNode, editPage);
				List groups = new ArrayList();
				// 分组
				Element groupsNode = (Element) root
						.selectSingleNode("./Groups");
				List groupNodes = groupsNode.selectNodes("./Group");
				Iterator groupIt = groupNodes.iterator();
				while (groupIt.hasNext()) {
					Element groupNode = (Element) groupIt.next();
					Group group = new Group();
					group.setTitle(groupNode.attribute("title").getValue()
							.trim());
					group.setSortIndex(Integer.parseInt(groupNode.attribute(
							"sortIndex").getValue().trim()));
					groups.add(group);
				}
				editPage.setGroups(groups);
				editPage.setColumnDatas(columnDatas);
				return editPage;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public PageService getPageService() {
		return pageService;
	}

	public void setPageService(PageService pageService) {
		this.pageService = pageService;
	}
}