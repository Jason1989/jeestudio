package com.zxt.framework.common.util;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import edu.emory.mathcs.backport.java.util.Arrays;

public class RolesCommon {

	public static boolean hasWriteRole(String role1 ,String role2){
		Boolean flag = Boolean.FALSE;
		if( StringUtils.isNotBlank(role1) ){
			List role = Arrays.asList(role1.split(","));
			if( StringUtils.isNotBlank(role2) ){
				List name = Arrays.asList(role2.split(","));
				flag = Boolean.valueOf(!Collections.disjoint(role, name));
			}
		}
		return flag.booleanValue();
	}
	
	public static boolean hasReadRole(String role1, String role2){
		Boolean flag = Boolean.FALSE;
		if( StringUtils.isNotBlank(role1) ){
			List role = Arrays.asList(role1.split(","));
			if( StringUtils.isNotBlank(role2) ){
				List name = Arrays.asList(role2.split(","));
				flag = Boolean.valueOf(!Collections.disjoint(role, name));
			}
		}
		return flag.booleanValue();
	}
	
}
