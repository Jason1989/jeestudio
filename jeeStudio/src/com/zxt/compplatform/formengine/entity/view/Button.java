package com.zxt.compplatform.formengine.entity.view;

import java.util.List;

import com.zxt.compplatform.codegenerate.util.CodeGenerateException;
import com.zxt.framework.common.entity.BasicEntity;

/**
 * Title: Button
 * Description:  按钮
 * Create DateTime: 2010-9-27
 * @author xxl
 * @since v1.0
 * 
 */
public class Button extends BasicEntity {
//	public static int BUTTON_TYPE_GRID =1;
//	public static int BUTTON_TYPE_ROW =2;
	/**
	 * 按钮类型： 
	 * 多记录操作：1 添加 2 删除 3 编辑 4 查询
	 *  
	 */
	public static final int BUTTON_MUTI_ADD =1;
	public static final int BUTTON_MUTI_DELETE =2;
	public static final int BUTTON_MUTI_EDIT =3;
	public static final int BUTTON_MUTI_QUERY =4;
	public static final int BUTTON_TYPE_EDGE =10;
	public static final int BUTTON_SINGLE_ADD =11;
	public static final int BUTTON_SINGLE_DELETE =12;
	public static final int BUTTON_SINGLE_EDIT =13;
	public static final int BUTTON_SINGLE_QUERY =14;
	
	/**
	 * 图标样式
	 */
	public static final String BUTTON_ICONCLS_DELETE ="icon-cancel";
	public static final String BUTTON_ICONCLS_ADD ="icon-add";
	public static final String BUTTON_ICONCLS_EDIT ="icon-edit";
	public static final String BUTTON_ICONCLS_SEARCH ="icon-search";
	/**
	 * 类型
	 */
	private String type;
	/**
	 * 按钮id
	 */
	private String buttonId;//
	/**
	 * 按钮名称
	 */
	private String buttonName;//
//	private Layout band;//
	/**
	 * 样式名称
	 */
	private String cssName;//
	/**
	 * 图标样式
	 */
	private String iconCls;
	/**
	 * 样式
	 */
	private String style;
	/**
	 * 是否可见
	 */
	private String visible;
	/**
	 * 描述
	 */
	private String desc;
	/**
	 * 按钮参数
	 */
	private String buttonParam;
	/**
	 * 事件
	 */
	private List event;//
	
	
	public String getButtonParam() {
		return buttonParam;
	}
	public void setButtonParam(String buttonParam) {
		this.buttonParam = buttonParam;
	}
	public String getButtonId() {
		return buttonId;
	}
	public void setButtonId(String buttonId) {
		this.buttonId = buttonId;
	}
	public String getButtonName() {
		return buttonName;
	}
	public void setButtonName(String buttonName) {
		this.buttonName = buttonName;
	}
//	public Layout getBand() {
//		return band;
//	}
//	public void setBand(Layout band) {
//		this.band = band;
//	}
//	public String getCssName() {
//		return cssName;
//	}
	public void setCssName(String cssName) {
		this.cssName = cssName;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCssName() {
		return cssName;
	}
	
	public List getEvent() {
		return event;
	}
	public void setEvent(List event) {
		this.event = event;
	}
	public String getIconCls() {
		int typeInt = 0;
		try{
		    typeInt = Integer.parseInt(type);
		}catch(Exception e){
			throw new CodeGenerateException("按钮类型不符合！");
		}
		if(iconCls == null){
			if(typeInt == Button.BUTTON_MUTI_ADD){
				return Button.BUTTON_ICONCLS_ADD;
			}else if(typeInt == Button.BUTTON_MUTI_DELETE){
				return Button.BUTTON_ICONCLS_DELETE;
			}else if(typeInt == Button.BUTTON_MUTI_EDIT){
				return Button.BUTTON_ICONCLS_EDIT;
			}else if(typeInt == Button.BUTTON_MUTI_QUERY){
				return Button.BUTTON_ICONCLS_SEARCH;
			}
		}
		return "";
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public String getVisible() {
		return visible;
	}
	public void setVisible(String visible) {
		this.visible = visible;
	}
}
