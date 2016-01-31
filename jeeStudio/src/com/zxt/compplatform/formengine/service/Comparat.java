package com.zxt.compplatform.formengine.service;

import java.util.Comparator;

import com.zxt.compplatform.formengine.entity.view.Tab;

/**
 * tab比较器
 * @author 007
 */
public class Comparat implements Comparator {

	public int compare(Object o1, Object o2) {
		Tab tab1 = (Tab) o1;
		Tab tab2 = (Tab) o2;
		if (tab1.getSortIndex() < tab2.getSortIndex())
			return 1;
		else
			return 0;
	}

}
