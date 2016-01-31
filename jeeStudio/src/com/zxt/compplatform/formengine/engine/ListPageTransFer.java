package com.zxt.compplatform.formengine.engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;

import com.zxt.compplatform.datacolumn.service.IDataColumnService;
import com.zxt.compplatform.datasource.entity.DataSource;
import com.zxt.compplatform.datasource.service.IDataSourceService;
import com.zxt.compplatform.datatable.service.IDataTableService;
import com.zxt.compplatform.formengine.constant.Constant;
import com.zxt.compplatform.formengine.entity.dataset.FieldDefVO;
import com.zxt.compplatform.formengine.entity.dataset.TableVO;
import com.zxt.compplatform.formengine.entity.view.Button;
import com.zxt.compplatform.formengine.entity.view.Event;
import com.zxt.compplatform.formengine.entity.view.ListPage;
import com.zxt.compplatform.formengine.entity.view.Param;
import com.zxt.compplatform.formengine.service.ResolveXmlService;
import com.zxt.compplatform.formula.service.FurmulaService;
import com.zxt.compplatform.validationrule.service.IValidationRuleService;
import com.zxt.framework.dictionary.service.IDataDictionaryService;

/**
 * 列表页转换类
 * 
 * @author 007
 */
public class ListPageTransFer implements IPageTransFer {
	private static final Log log = LogFactory.getLog(ListPageTransFer.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.formengine.engine.IPageTransFer#transToPage(com.zxt.compplatform.formengine.entity.view.ListPage,
	 *      org.jdom.Document,
	 *      com.zxt.compplatform.datacolumn.service.IDataColumnService,
	 *      com.zxt.compplatform.datatable.service.IDataTableService,
	 *      com.zxt.compplatform.datasource.service.IDataSourceService,
	 *      com.zxt.compplatform.formula.service.FurmulaService,
	 *      com.zxt.compplatform.validationrule.service.IValidationRuleService,
	 *      com.zxt.framework.dictionary.service.IDataDictionaryService)
	 */
	public Map transToPage(ListPage listPage, Document doc,
			IDataColumnService dataColumnService,
			IDataTableService dataTableService,
			IDataSourceService dataSourceService,
			FurmulaService furmulaService,
			IValidationRuleService validationRuleService,
			IDataDictionaryService dataDictionaryService) throws Exception {
		Map map = new HashMap();
		List tableList = listPage.getTable();
		TableVO table = null;
		StringBuffer joinSql = new StringBuffer();
		DataSource ds = new DataSource();
		if (tableList != null) {
			for (int i = 0; i < tableList.size(); i++) {
				table = (TableVO) tableList.get(i);
				ds = table.getDataSource();
				joinSql = new ResolveXmlService().componentSql(table, joinSql,
						ds);
				listPage.setSql(joinSql.toString());
			}
			// String selectSql = joinSql.toString();
			if (table.getWhereParams() != null
					&& table.getWhereParams().size() > 0) {
				listPage.setListPageParams(table.getWhereParams());
			}

			// if(table.getWhereList()!=null && table.getWhereList().size()>0){
			//				
			// listPage.setSelectSqlParam(new
			// ResolveXmlService().spitFindSql(selectSql));
			// }else{
			// }

			/**
			 * 从rowbutton找到删除功能键的参数名 用参数名匹配列表页字段 通过该字段查找到表名 删除语句 主表表名获取
			 */

			List list = listPage.getTable();
			TableVO tablevo = (TableVO) list.get(0);

			/**
			 * 设置临时变量
			 */
			FieldDefVO fielddefvo = null;
			Button button = null;
			Event event = null;
			Param param = null;
			String buttonName = "";
			/**
			 * 判断查找表名
			 */

			List rowButtonsList = listPage.getRowButton();

			try {
				for (int i = 0; i < rowButtonsList.size(); i++) {
					button = (Button) rowButtonsList.get(i);
					buttonName = button.getButtonName().trim();

					if (button.getButtonName().equals(Constant.DELETEOPERATOR)) {
						event = (Event) button.getEvent().get(0);
						param = (Param) event.getParas().get(0);
						String[] temString = null;
						for (int j = 0; j < tablevo.getFieldList().size(); j++) {
							fielddefvo = (FieldDefVO) tablevo.getFieldList()
									.get(j);
							if (param.getValue() != null) {
								temString = param.getValue().split(",");
							} else {
								continue;
							}
							if (fielddefvo.getFromFieldName().equals(
									temString[0])) {
								listPage.setKeyTable(fielddefvo.getFromTable()
										.getName());
							}
						}
					}
				}
			} catch (Exception e) {
				log.info("rowButtonlist  id null");
			}
			/**
			 * 获取主表名结束
			 */
			map.put("listPage", listPage);
		}
		return map;
	}
}
