package com.zxt.compplatform.formengine.entity.view;


/**
 * 拷贝页面
 * @author 007
 */
public class CopyPage extends BasePage {
	
	/**
	 * 编辑页实体
	 */
	private EditPage editPage = new EditPage();

	public EditPage getEditPage() {
		return editPage;
	}

	public void setEditPage(EditPage editPage) {
		this.editPage = editPage;
	}
	
}
