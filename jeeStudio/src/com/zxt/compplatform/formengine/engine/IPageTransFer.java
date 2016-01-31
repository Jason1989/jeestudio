package com.zxt.compplatform.formengine.engine;

import java.util.Map;

import org.jdom.Document;

import com.zxt.compplatform.datacolumn.service.IDataColumnService;
import com.zxt.compplatform.datasource.service.IDataSourceService;
import com.zxt.compplatform.datatable.service.IDataTableService;
import com.zxt.compplatform.formengine.entity.view.ListPage;
import com.zxt.compplatform.formula.service.FurmulaService;
import com.zxt.compplatform.validationrule.service.IValidationRuleService;
import com.zxt.framework.dictionary.service.IDataDictionaryService;

/**
 * 将相应的实体转化成界面
 * 
 * @author 007
 */
public interface IPageTransFer {
	/** transfer to Page html */
	public Map transToPage(final ListPage listPage, final Document doc,
			final IDataColumnService dataColumnService,
			final IDataTableService dataTableService,
			final IDataSourceService dataSourceService,
			final FurmulaService furmulaService,
			final IValidationRuleService validationRuleService,
			final IDataDictionaryService dataDictionaryService)
			throws Exception;

}