package com.zxt.compplatform.codegenerate.codeFactory;

import com.zxt.compplatform.codegenerate.codeFactory.impl.CodeGenerateActionOperate;
import com.zxt.compplatform.codegenerate.codeFactory.impl.CodeGenerateDaoOperate;
import com.zxt.compplatform.codegenerate.codeFactory.impl.CodeGenerateModelOperate;
import com.zxt.compplatform.codegenerate.codeFactory.impl.CodeGenerateServiceOperate;
import com.zxt.compplatform.codegenerate.codeFactory.impl.CodeGenerateViewOperate;

/**
 * 代码生成工厂
 * 
 * @author zxt-hejun
 * @date:2010-9-26 上午09:38:14
 */
public class CodeGenerateFactory {
	/**
	 * 代码生成
	 */
	private static CodeGenerateOperate codeGenerate;

	/**
	 * 获取代码生成类的实体
	 * @param codeUseEntity
	 * @return
	 */
	public static CodeGenerateOperate getInstance(CodeUseEntity codeUseEntity) {
		if (null == codeGenerate) {
			//根据不同的代码类型，返回不同的代码生成模版实体
			switch (codeUseEntity.getCodeType()) {
			case 1:
				return new CodeGenerateModelOperate();
			case 2:
				return new CodeGenerateDaoOperate();
			case 3:
				return new CodeGenerateServiceOperate();
			case 4:
				return new CodeGenerateActionOperate();
			case 5:
				return new CodeGenerateViewOperate();
			}
		}
		return codeGenerate;
	}
}
