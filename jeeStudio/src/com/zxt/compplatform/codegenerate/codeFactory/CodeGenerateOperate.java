package com.zxt.compplatform.codegenerate.codeFactory;
/**
 * 代码生成接口
 * @author zxt-hejun
 * @date:2010-9-26 上午09:38:32
 */
public interface CodeGenerateOperate {
	 /**
	  * 生成代码
	 * @param codeUseEntity
	 * @return
	 */
	public boolean codeGenerate(CodeUseEntity codeUseEntity);
}
