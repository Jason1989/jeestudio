package com.zxt.framework.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 
 * 对象的工具类
 * <p>
 *     copy 复制一个对象，
 *     修改日志说明：<br>
 *          1、Sep 14, 2011 3:54:17 PM（+） 添加注释
 * </p>
 * @author 005
 * @version 1.00
 */
public class ObjectUtils {
	/**
	  * 
	  * 复制一个对象，
	  * <p>
	  *   传入参数：
	  *     inObject:被复制的对象
	  *     
	  *   传出参数（名称/类型）：
	  *     1. ret/Object（复制后生成的对象）
	  *     
	  * action访问地址： 无
	  * 
	  * 修改记录：
	  *     1. Sep 14, 2011 005 添加注释
	  * </p>
	 */
	public static Object copy(Object inObject) {
		ByteArrayOutputStream bout = null;
		ObjectOutputStream out = null;
		ByteArrayInputStream bin = null;
		ObjectInputStream in = null;
		try {
			bout = new ByteArrayOutputStream();
			out = new ObjectOutputStream(bout);
			out.writeObject(inObject);
			out.flush();
			bout.flush();

			bin = new ByteArrayInputStream(bout.toByteArray());
			in = new ObjectInputStream(bin);
			Object ret = in.readObject();
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (null != in)
					in.close();
				if (null != bin)
					bin.close();
				if (null != out)
					out.close();
				if (null != bout)
					bout.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		
	}

}
