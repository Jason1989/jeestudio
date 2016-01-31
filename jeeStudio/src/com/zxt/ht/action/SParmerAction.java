package com.zxt.ht.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.zxt.ht.service.PlanLimitSerice;

public class SParmerAction extends ActionSupport {
	private PlanLimitSerice planLimitSerice;
	public PlanLimitSerice getPlanLimitSerice() {
		return planLimitSerice;
	}
	public void setPlanLimitSerice(PlanLimitSerice planLimitSerice) {
		this.planLimitSerice = planLimitSerice;
	}
	public String getHtList() throws Exception {
        String sqlRead=" select SP_SETS from ENG_SYSTEM_PARAMETER where SP_ID='GUO'";
        String strResult=planLimitSerice.findPtXml(sqlRead);
        if(strResult!=null && !strResult.equals("")){
	        String[] splitArr=strResult.toString().split(",");
			for(int j=0;j<splitArr.length;j++){
				String pkey="p"+(j+1);
				String pvalue=splitArr[j];				
				this.getParams().put(pkey, pvalue);
			}
    	} 
		return "parmersucc";
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public String operHtList() throws Exception {
		if(params!=null){			
			StringBuffer strBuffer=new StringBuffer("");
			//int z=params.size();
			for(int i=1;i<=33;i++){		
				Object v=null;
				String str="p"+i;
				v=params.get(str);
				if(v!=null && !v.equals("")) strBuffer.append(v.toString()+",");
				else{
					if(str.equals("p27"))strBuffer.append("0,"); 
					else strBuffer.append("T,");
				}
			} 
			String sqlUpdate="update ENG_SYSTEM_PARAMETER set SP_SETS=? where SP_ID='GUO'";
			planLimitSerice.save(sqlUpdate, new Object[]{strBuffer.toString()});			
		}
		ServletActionContext.getResponse().getWriter().write("sucess");
		return   null; //ActionSupport.SUCCESS;
	}
	protected Map<String,String> params;
	public void setParams(Map<String,String> params) {
		this.params = params;
	}
	public Map<String,String> getParams() {
		if (this.params == null) {this.params = new HashMap<String,String>();}
		return this.params;
	}
}
