package com.zxt.framework.common.util;

import java.sql.Blob;
import java.sql.SQLException;

public class SQLBlobUtil {
	public static String blobToString(Blob blob){
		int index = 1;
		String newStr = "";
		try {
			if(blob != null){
				long blobLen = blob.length();
				if (blob != null && blobLen != 0) //如果为空，返回空值
				{
					while(index <= blobLen){
						newStr = newStr + new String(blob.getBytes(index,1024));
						index = index + 1024;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return newStr;
	}
}
