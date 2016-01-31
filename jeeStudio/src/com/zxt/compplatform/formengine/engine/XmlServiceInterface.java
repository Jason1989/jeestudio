package com.zxt.compplatform.formengine.engine;

import java.util.List;

import org.dom4j.Document;

import com.zxt.framework.common.exceptions.GeneralException;

/**
 * xml操作接口
 * 
 * @author 007
 */
public interface XmlServiceInterface {

	/**
	 * read xml Data from db
	 * 
	 * @param formID
	 * @return
	 * @throws GeneralException
	 */
	public Object readXMLFromDB(String formID) throws GeneralException;

	/**
	 * 将xml转换成dom
	 * 
	 * @param formID
	 * @return
	 * @throws GeneralException
	 */
	public Document readXMLFromDBAsDom(String formID) throws GeneralException;

	/**
	 * Parse xml
	 * 
	 * @param formID
	 * @return
	 * @throws GeneralException
	 */
	public Object PaseXMLFromDB(String formID) throws GeneralException;

	/**
	 * 解析xml
	 * 
	 * @param doc
	 * @return
	 * @throws GeneralException
	 */
	public Object PaseXMLFromDB(Document doc) throws GeneralException;

	/**
	 * 解析xml
	 * 
	 * @param in
	 * @return
	 * @throws GeneralException
	 */
	public Object PaseXMLFromDB(java.io.InputStream in) throws GeneralException;

	/**
	 * Parse xml Element
	 * 
	 * @param doc
	 * @return
	 * @throws GeneralException
	 */
	public Object PaseDataSet(Document doc) throws GeneralException;

	/**
	 * 提取布局
	 * 
	 * @param doc
	 * @return
	 * @throws GeneralException
	 */
	public List PaseLayoutDocument(Document doc) throws GeneralException;

	/**
	 * 提出按钮
	 * 
	 * @param doc
	 * @return
	 * @throws GeneralException
	 */
	public List PaseButtons(Document doc) throws GeneralException;

	/**
	 * 提取标题
	 * 
	 * @param doc
	 * @return
	 * @throws GeneralException
	 */
	public List PaseTitleColumns(Document doc) throws GeneralException;

	/**
	 * 提取表格列
	 * 
	 * @param doc
	 * @return
	 * @throws GeneralException
	 */
	public List PaseListColumns(Document doc) throws GeneralException;

}
