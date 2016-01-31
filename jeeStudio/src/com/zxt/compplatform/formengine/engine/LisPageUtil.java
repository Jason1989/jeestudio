package com.zxt.compplatform.formengine.engine;

import java.util.ArrayList;
import java.util.List;

import com.zxt.compplatform.formengine.entity.view.Layout;

/**
 * 将实体转换成列表页
 * 
 * @author 李朋
 */
public class LisPageUtil {

	/**
	 * @param listvo
	 *            </table>
	 */
	String layoutString = null;

	public String spellTabs(List tabList, String url) throws Exception {
		return null;
	}

	/**
	 * 拼接布局
	 * 
	 * @param listAll
	 * @return
	 * @throws Exception
	 */
	private String spellLayout1(List listAll) throws Exception {
		// 数据整理
		StringBuffer bufferTd = new StringBuffer();
		StringBuffer bufferTr = new StringBuffer();
		Layout layoutx = null;
		// 这个list是同一行的layout
		bufferTr.append("<tr>");
		for (int j = 0; j < listAll.size(); j++) {
			layoutx = (Layout) listAll.get(j);
			String layoutId = layoutx.getId();
			String layoutIndex = layoutx.getXindex();
			bufferTr
					.append("<td width=\"200\">")
					.append(
							"<div title=\"Tab2\" closable=\"true\" style=\"overflow:auto;padding:20px;display:none;\" cache=\"false\" href=\"come2.jsp\"></div>")
					.append("</td>");
		}
		bufferTr.append("</tr>").append("\n");
		return bufferTr.toString();
	}

	/**
	 * 处理布局
	 * 
	 * @param listLayout
	 * @return
	 */
	private static List dealLayout(List listLayout) {
		List listAll = new ArrayList();
		List listcopy = new ArrayList();
		listcopy.addAll(listLayout);
		for (int i = 0; i < listLayout.size(); i++) {
			Layout layout = (Layout) listLayout.get(i);
			List list = new ArrayList();
			for (int j = 0; j < listcopy.size(); j++) {
				Layout layout1 = (Layout) listcopy.get(j);
				if (layout.getXindex().equals(layout1.getXindex())) {
					list.add(layout1);
				}

			}
			if (list.size() > 0) {
				for (int k = 0; k < list.size(); k++) {
					listcopy.remove(list.get(k));
				}
				listAll.add(list);
			}
		}
		return listAll;
	}

	/**
	 * 处理布局指数
	 * 
	 * @param listLayout
	 * @return
	 */
	private static List dealLayoutXindex(List listLayout) {
		List listAll = new ArrayList();
		List listcopy = new ArrayList();
		listcopy.addAll(listLayout);
		for (int i = 0; i < listLayout.size(); i++) {
			Layout layout = (Layout) listLayout.get(i);
			List list = new ArrayList();
			for (int j = 0; j < listcopy.size(); j++) {
				Layout layout1 = (Layout) listcopy.get(j);
				if (layout.getXindex().equals(layout1.getXindex())) {
					list.add(layout1);
				}

			}
			if (list.size() > 0) {
				for (int k = 0; k < list.size(); k++) {
					listcopy.remove(list.get(k));
				}
				listAll.add(list);
			}
		}
		return listAll;
	}
}
