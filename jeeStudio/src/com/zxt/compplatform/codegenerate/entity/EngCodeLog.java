package com.zxt.compplatform.codegenerate.entity;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * 代码生成日志记录详细信息实体
 * @author zxt-hejun
 * @date:2010-9-19 上午02:34:33
 */

public class EngCodeLog implements java.io.Serializable {

	private static final long serialVersionUID = 3727603030289308433L;
	// Fields
	
	/**
	 * 代码版本号
	 */
	private String codeVersionId;
	/**
	 * 表单的id
	 */
	private String codeFormsId;
	/**
	 * 用户的id
	 */
	private Long codeUserId;
	/**
	 * 路径
	 */
	private String codePath;
	/**
	 * 创建时间
	 */
	private Date codeCreateTime;
	/**
	 * 所有的文件
	 */
	private String codeAllFile;
	/**
	 * 代码标记
	 */
	private String codeRemark;

	// Constructors

	/** default constructor */
	public EngCodeLog() {
	}

	/** minimal constructor */
	public EngCodeLog(String codeFormsId,String codeVersionId) {
		this.codeVersionId = codeVersionId;
		this.codeFormsId = codeFormsId;
	}

	/** full constructor */
	public EngCodeLog(String codeVersionId,String codeFormsId, Long codeUserId, String codePath,
			Date codeCreateTime, String codeAllFile) {
		this.codeVersionId = codeVersionId;
		this.codeFormsId = codeFormsId;
		this.codeUserId = codeUserId;
		this.codePath = codePath;
		this.codeCreateTime = codeCreateTime;
		this.codeAllFile = codeAllFile;
	}

	// Property accessors

	public Long getCodeUserId() {
		return this.codeUserId;
	}

	public void setCodeUserId(Long codeUserId) {
		this.codeUserId = codeUserId;
	}

	public String getCodePath() {
		return this.codePath;
	}

	public void setCodePath(String codePath) {
		this.codePath = codePath;
	}

	public Date getCodeCreateTime() {
		return this.codeCreateTime;
	}

	public void setCodeCreateTime(Date codeCreateTime) {
		this.codeCreateTime = codeCreateTime;
	}

	public String getCodeAllFile() {
		return this.codeAllFile;
	}

	public void setCodeAllFile(String codeAllFile) {
		this.codeAllFile = codeAllFile;
	}

	public String getCodeVersionId() {
		return codeVersionId;
	}

	public void setCodeVersionId(String codeVersionId) {
		this.codeVersionId = codeVersionId;
	}

	public String getCodeFormsId() {
		return codeFormsId;
	}

	public void setCodeFormsId(String codeFormsId) {
		this.codeFormsId = codeFormsId;
	}
	
	 public   boolean  equals(Object obj) {    
         if (!(obj  instanceof  EngCodeLog)) {    
             return   false ;    
        }    
            
         EngCodeLog engCodeLog = (EngCodeLog)obj;    
         return   new  EqualsBuilder()           // EqualsBuilder 和HashCodeBuilder均为apache common lang包中的工具类    
            .appendSuper( super .equals(obj))    
            .append( this .codeVersionId, engCodeLog.codeVersionId)    
            .append( this .codeFormsId, engCodeLog.codeFormsId)    
            .isEquals();            
    }    
        
     public   int  hasCode() {    
         return   new  HashCodeBuilder(- 528253723 , - 475504089 )    
            .appendSuper( super .hashCode())    
            .append( this .codeVersionId).append( this .codeFormsId)    
            .toHashCode();    
    }

	public String getCodeRemark() {
		return codeRemark;
	}

	public void setCodeRemark(String codeRemark) {
		this.codeRemark = codeRemark;
	}   

}