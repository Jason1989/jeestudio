package com.zxt.compplatform.formengine.entity.view;
/****
 * 
 * @author GUOWEXIN
 *用于添加动态configSQL.properties操作使用的
 */
public class UserDeptrel {
	private String userid;
	private String oid;
	private String oname;
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getOname() {
		return oname;
	}
	public void setOname(String oname) {
		this.oname = oname;
	}
}
