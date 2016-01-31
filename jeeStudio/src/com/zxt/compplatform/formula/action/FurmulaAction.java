package com.zxt.compplatform.formula.action;

import com.zxt.compplatform.formula.entity.Formula;
import com.zxt.compplatform.formula.service.FurmulaService;
/**
 * Title: FurmulaAction
 * Description:  
 * Create DateTime: 2010-10-08
 * @author xxl
 * @since v1.0
 * 
 */
public class FurmulaAction {
	
	private FurmulaService furmulaService;
	private Formula formula;
	/**
	 * 列表入口
	 * @return
	 */
	public String toList(){
		return "list";		
	}
	/**
	 * 列表
	 * @return
	 */
	public String list(){
		return null;
	}
	/**
	 * 跳转添加页面
	 * @return
	 */
	public String toAdd(){
		return "toadd";
	}
	/**
	 * 添加
	 * @return
	 */
	public String add(){
		return null;
	}
	/**
	 * 跳转修改页面
	 * @return
	 */
	public String toUpdate(){
		return "toupdate";
	}
	/**
	 * 修改
	 * @return
	 */
	public String update(){
		return null;
	}
	/**
	 * 删除
	 * @return
	 */
	public String delete(){
		return null;
	}
	
	public String execute(Formula formula){
		return null;
	}
	
	public FurmulaService getFurmulaService() {
		return furmulaService;
	}
	public Formula getFormula() {
		return formula;
	}
	public void setFurmulaService(FurmulaService furmulaService) {
		this.furmulaService = furmulaService;
	}
	public void setFormula(Formula formula) {
		this.formula = formula;
	}
	
	
}
