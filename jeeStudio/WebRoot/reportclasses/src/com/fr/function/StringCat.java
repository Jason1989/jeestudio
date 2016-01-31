package com.fr.function;

import com.fr.report.script.NormalFunction;

public class StringCat extends NormalFunction {
	public Object run(Object[] args) {
		String result = "";
		Object para;
		for (int i = 0; i < args.length; i++) {
			para = args[i];
			result += para.toString();
		}
		return result;
	}
}
