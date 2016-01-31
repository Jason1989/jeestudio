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
import com.zxt.compplatform.formengine.entity.dataset.TableVO;
import com.zxt.compplatform.formengine.entity.view.ListPage;
import com.zxt.compplatform.formengine.entity.view.ViewPage;
import com.zxt.compplatform.formengine.service.ResolveXmlService;
import com.zxt.compplatform.formula.service.FurmulaService;
import com.zxt.compplatform.validationrule.service.IValidationRuleService;
import com.zxt.framework.dictionary.service.IDataDictionaryService;

/**
 * 查看页转换实现
 * 
 * @author 007
 */
public class ViewPageTransFer implements IPageTransFer {
	private static final Log log = LogFactory.getLog(ViewPageTransFer.class);

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.formengine.engine.IPageTransFer#transToPage(com.zxt.compplatform.formengine.entity.view.ListPage, org.jdom.Document, com.zxt.compplatform.datacolumn.service.IDataColumnService, com.zxt.compplatform.datatable.service.IDataTableService, com.zxt.compplatform.datasource.service.IDataSourceService, com.zxt.compplatform.formula.service.FurmulaService, com.zxt.compplatform.validationrule.service.IValidationRuleService, com.zxt.framework.dictionary.service.IDataDictionaryService)
	 */
	public Map transToPage(ListPage listPage, Document doc,
			IDataColumnService dataColumnService,
			IDataTableService dataTableService,
			IDataSourceService dataSourceService,
			FurmulaService furmulaService,
			IValidationRuleService validationRuleService,
			IDataDictionaryService dataDictionaryService) throws Exception {
		Map map = new HashMap();
		ViewPage viewPage = listPage.getViewPage();
		StringBuffer selectSql = new StringBuffer();
		TableVO table = new TableVO();

		List tableList = listPage.getViewPage().getTable();
		for (int i = 0; i < tableList.size(); i++) {
			table = (TableVO) tableList.get(i);
			DataSource ds = new DataSource();
			// table = new ResolveXmlService().spellSqlQuery(table);
			try {
				selectSql = new ResolveXmlService().componentSql(table,
						selectSql, ds);
			} catch (Exception e) {
				log.error("viewPage spell select sql error!");
			}

			// selectSql = table.getSelect();
			if (table.getWhereParams() != null
					&& table.getWhereParams().size() > 0) {
				viewPage.setViewPageParams(table.getWhereParams());
			}
			viewPage.setFindSql(selectSql.toString());
		}
		map.put("viewPage", viewPage);
		return map;
	}
}