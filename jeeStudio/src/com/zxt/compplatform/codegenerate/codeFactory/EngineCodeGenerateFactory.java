package com.zxt.compplatform.codegenerate.codeFactory;

import com.zxt.compplatform.codegenerate.codeFactory.impl.EngineCodeGenerateViewOperate;

/**
 * 
 * @author lenny
 * @Description TODO 引擎代码生成工厂
 * @created Nov 15, 2012 2:27:41 PM
 * @History 
 * @version v1.0
 */
public class EngineCodeGenerateFactory {
	/**
	 * 代码生成
	 */
	private static EngineCodeGenerateOperate engineCodeGenerate;

	/**
	 * 获取代码生成类的实体
	 * @param codeUseEntity
	 * @return
	 */
	public static EngineCodeGenerateOperate getInstance(EngineCodeUseEntity engineCodeUseEntity) {
		if (null == engineCodeGenerate) {
			//根据不同的代码类型，返回不同的代码生成模版实体
			switch (engineCodeUseEntity.getCodeType()) {
			case 5:
				return new EngineCodeGenerateViewOperate();
			}
		}
		return engineCodeGenerate;
	}
}
