package com.zxt.compplatform.formengine.engine;

import java.util.ArrayList;
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
import com.zxt.compplatform.formengine.entity.view.EditPage;
import com.zxt.compplatform.formengine.entity.view.ListPage;
import com.zxt.compplatform.formengine.service.ResolveXmlService;
import com.zxt.compplatform.formula.service.FurmulaService;
import com.zxt.compplatform.validationrule.service.IValidationRuleService;
import com.zxt.framework.dictionary.service.IDataDictionaryService;

/**
 * 编辑页界面生成器
 * 
 * @author 007
 */
public class EditPageTransFer implements IPageTransFer {
	
	private static final Log log = LogFactory.getLog(EditPageTransFer.class);

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.formengine.engine.IPageTransFer#transToPage(com.zxt.compplatform.formengine.entity.view.ListPage, org.jdom.Document, com.zxt.compplatform.datacolumn.service.IDataColumnService, com.zxt.compplatform.datatable.service.IDataTableService, com.zxt.compplatform.datasource.service.IDataSourceService, com.zxt.compplatform.formula.service.FurmulaService, com.zxt.compplatform.validationrule.service.IValidationRuleService, com.zxt.framework.dictionary.service.IDataDictionaryService)
	 */
	public Map transToPage(ListPage listPage, Document doc,
			IDataColumnService dataColumnService,
			IDataTableService dataTableService,
			IDataSourceService dataSourceService,
			FurmulaService furmulaService,
			IValidationRuleService validationRuleService,
			IDataDictionaryService dataDictionaryService) {
		Map map = new HashMap();
		List list = new ArrayList();
		String selectSql = null;
		String insertSql = null;
		String updateSql = null;
		DataSource ds = null;
		StringBuffer sqlSelect = new StringBuffer();
		TableVO ta = new TableVO();
		EditPage editPage = listPage.getEditPage();
		List sqlList = new ArrayList();
		List updataParams = new ArrayList();
		List insertParams = new ArrayList();
		try {
			List tableList = listPage.getEditPage().getTable();
			for (int i = 0; i < tableList.size(); i++) {
				ta = (TableVO) tableList.get(i);
				try {
					sqlSelect = new ResolveXmlService().componentEditSql(ta,
							sqlSelect, ds);
					// sqlSelect = new ResolveXmlService().componentSql(ta,
					// sqlSelect, ds);
					editPage.setFindSql(sqlSelect.toString());
				} catch (Exception e) {
					log.info("editPage spell select sql error!");
				}
				if (ta.getWhereParams() != null
						&& ta.getWhereParams().size() > 0) {
					editPage.setEditPageParams(ta.getWhereParams());
				}
				// if(ta.getWhereList()!=null && ta.getWhereList().size()>0){
				// editPage.setFindSqlParmer(new
				// ResolveXmlService().spitFindSql(sqlSelect.toString()));
				// }
			}
			// ta = new ResolveXmlService().spellSqlQuery(ta);

			for (int i = 0; i < tableList.size(); i++) {
				TableVO table = (TableVO) tableList.get(i);
				list = table.getFromTableList();
				list = new ResolveXmlService().spellSetTabelSql(list);
				for (int k = 0; k < list.size(); k++) {
					TableVO tablevo = (TableVO) list.get(k);
					if (("I,U,D").equals(tablevo.getOprType())) {
						insertSql = tablevo.getInsert();
						updateSql = tablevo.getUpdate();
						editPage.setInsertSql(insertSql);
						editPage.setKeyList(tablevo.getKeyList());
						editPage.setInsertTableName(tablevo.getName());
						insertParams.add(tablevo.getInsertParams());
						editPage.setInsertParams(insertParams);
						editPage.setUpdateSql(updateSql);
						editPage.setUpdataTableName(tablevo.getName());
						updataParams.add(tablevo.getUpdateParams());
						editPage.setUpdateParams(updataParams);
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("editPage", editPage);
		return map;
	}

}
