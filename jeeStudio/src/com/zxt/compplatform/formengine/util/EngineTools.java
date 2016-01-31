package com.zxt.compplatform.formengine.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 引擎工具类
 * 
 * @author 007
 */
public class EngineTools {

	/** 日志记录对象 */
	private static Log logger = LogFactory.getLog(EngineTools.class);
	private static final String charSet = "GBK";
	private static final String newline = System.getProperty("line.separator");

	/**
	 * 获得页面内容
	 * 
	 * @param httpUrl
	 * @return
	 */
	public static String getHtmlContent(String httpUrl) {
		Date before = new Date();
		long start = before.getTime();
		String htmlContent = "";
		InputStream in = null;
		try {

			URL url = new java.net.URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/4.0");
			connection.connect();
			in = connection.getInputStream();
			java.io.BufferedReader breader = new BufferedReader(
					new InputStreamReader(in, charSet));
			String currentLine;
			while ((currentLine = breader.readLine()) != null) {
				htmlContent += currentLine;
				htmlContent += newline;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				logger.error("IO出错...", e);
			}
			Date after = new Date();
			long end = after.getTime();
			long useTime = end - start;
			logger.info("执行时间:" + useTime / 1000.0 + "秒");
		}
		logger.error("htmlContent: " + htmlContent);
		return htmlContent;
	}

	/**
	 * 存储静态页面
	 * 
	 * @param filePath
	 *            存储路径
	 * @param content
	 *            文件内容
	 * @param flag
	 *            是否删除原文件[YES|NO]
	 */
	public static synchronized void writeHtml(String filePath, String fileName,
			String content, String flag) {
		// if (StrTools.isNotBlank(filePath,fileName, content, flag)) {
		FileOutputStream fos = null;
		BufferedWriter writer = null;
		String fullPath = filePath + fileName;// 被创建的文件路径
		try {
			File writeFile = new File(fullPath);
			if (!writeFile.exists()) {
				File baseFile = new File(filePath);
				if (!baseFile.exists()) {
					baseFile.mkdirs();
				}
				writeFile.createNewFile();
			} else {
				if (!flag.equalsIgnoreCase("NO")) {
					writeFile.delete();
					writeFile.createNewFile();
				}
			}

			fos = new FileOutputStream(fullPath);
			writer = new BufferedWriter(new OutputStreamWriter(fos, "GBK"));
			writer.write(content);
			writer.close();
			logger.info("生成文件 : " + fullPath);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				logger.error("close writer error..");
			}
		}
		// } else {
		// logger.debug("parameter error.");
		// }
	}

	/**
	 * 获得页面内容
	 * 
	 * @param httpUrl
	 * @return
	 */
	public static String getHtmlContent(String httpUrl, String startTag,
			String endTag) {
		String htmlContent = "";
		InputStream in = null;
		// if (StrTools.isNotBlank(httpUrl, startTag, endTag)) {
		Date before = new Date();
		long start = before.getTime();

		try {
			URL url = new java.net.URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/4.0");
			connection.connect();
			in = connection.getInputStream();
			java.io.BufferedReader breader = new BufferedReader(
					new InputStreamReader(in, charSet));
			String currentLine;
			boolean typein = false;
			while ((currentLine = breader.readLine()) != null) {
				if (startTag.equalsIgnoreCase(currentLine.trim())) {
					typein = true;
					continue;
				}
				if (endTag.equalsIgnoreCase(currentLine.trim())) {
					typein = false;
					break;
				}
				if (typein) {
					htmlContent += currentLine;
					htmlContent += newline;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			Date after = new Date();
			long end = after.getTime();
			long useTime = end - start;
			logger.info("提取路径:" + httpUrl);
			logger.info("执行时间:" + useTime / 1000.0 + "秒");
		}
		// } else {
		// logger.debug("parameter error.");
		// }

		return htmlContent;
	}

	public static void main(String[] args) throws IllegalAccessException {

		writeHtml(
				"D:/engine-code/jsp/",
				"list_402887a32d78747c012d789ef6e20001.jsp",
				getHtmlContent("http://localhost:8080/compplatform/formengine/listPageAction.action?formId=402887a32d78747c012d789ef6e20001"),
				"yes");

	}

}
