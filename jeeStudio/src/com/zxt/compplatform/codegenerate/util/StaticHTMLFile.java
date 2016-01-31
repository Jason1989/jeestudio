package com.zxt.compplatform.codegenerate.util;

import java.io.*;
import java.net.URL;

/**
 * Title:动态页面静态化
 */
public class StaticHTMLFile {
    /**
     * @param page
     *            存放静态页面的本地文件路径
     * @param url_addr
     *            所要生成的静态页的URL地址
     * @return
     */
    public static boolean PrintPage(String page, String url_addr) {
        URL url;
        String rLine = null;
        PrintWriter fileOut = null;
        InputStream ins = null;
        try {
            url = new URL(url_addr);
            ins = url.openStream();
            BufferedReader bReader = new BufferedReader(new InputStreamReader(
                    ins, "UTF-8"));// 获取编码为UTF-8的文件
            FileOutputStream out = new FileOutputStream(page);
            OutputStreamWriter writer = new OutputStreamWriter(out, "UTF-8");
            fileOut = new PrintWriter(writer);
            fileOut.print("<%@ page language='java' contentType='text/html; charset=utf-8'%>");
            // 循环取取数据,并写入目标文件中
            while ((rLine = bReader.readLine()) != null) {
                String tmp_rLine = rLine;
                int str_len = tmp_rLine.length();
                if (str_len > 0) {
                    fileOut.println(tmp_rLine);
                    fileOut.flush();
                }
                tmp_rLine = null;
            }
            url = null;
            return true;
        } catch (IOException e) {
            System.out.println("error: " + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (Exception es) {
            System.out.println(es.getMessage());
            return false;
        } finally {// 关闭资源
            fileOut.close();
            try {
                ins.close();
            } catch (IOException ex) {
                // 关闭输入流出错
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
//        PrintPage(
//                "c:/sina.html",
//                "http://localhost:8888/compplatform/formengine/editPageAction.action?formId=402881e43addfee7013ade0438aa0003&preview=easyuiview");
    //1第一个参数，页面名称。 2页面前方的basePath + formengine/  + listPate/viewPae/editPage  +所传ID
       // PrintPage("c:/DD.jsp", "http://localhost:8081/compplatform/formengine/listPageAction.action?formId=2c92d5ef3a8d7b43013a8d872c1a0009");
       
       
    	
    }
}
