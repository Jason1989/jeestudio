package com.zxt.compplatform.formengine.upload;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.zxt.compplatform.formengine.constant.Constant;
import com.zxt.compplatform.formengine.dao.impl.UploadDaoImpl;
import com.zxt.compplatform.formengine.entity.dataset.AttachementVo;
import com.zxt.compplatform.formengine.util.ReadConfigSQL;
import com.zxt.compplatform.formengine.util.StrTools;
import com.zxt.framework.common.util.RandomGUID;

/**
 * 文件上传Action
 * 
 * @author 007
 */
public class UploadProAction extends HttpServlet {
	private static final Logger log = Logger.getLogger(UploadProAction.class);

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		String tempDirectory = ReadConfigSQL.readValue("/plat_parameter.properties", "UPLOAD_TMP_PATH");
		String uploadPath = ReadConfigSQL.readValue("/plat_parameter.properties", "UPLOAD_PATH");
		
		if("".equals(tempDirectory)){
			tempDirectory = Constant.TEMP_DIR;
		}
		if("".equals(uploadPath)){
			uploadPath = Constant.Upload.trim();
		}
		
		//String tempDirectory = Constant.TEMP_DIR; // 要在最后加上斜杠:temp/
		String vFileDir = request.getRealPath("/" +uploadPath)+"\\";
		File fileTemp = new File(tempDirectory);
		File fileDir = new File(vFileDir);
		if (!fileTemp.exists()) {
			fileTemp.mkdirs();
		}
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
		try {
			int sizeThreshold = 1024 * 64; // 写满该大小的缓存后，存入硬盘中。
			File repositoryFile = new File(tempDirectory);
			UploadOutputStreamListener uploadOutputStreamListener = new UploadOutputStreamListener(
					request, 1);
			FileItemFactory factory = new MonitoredDiskFileItemFactory(
					sizeThreshold, repositoryFile, uploadOutputStreamListener);

			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setSizeMax(500 * 1024 * 1024); // set every upload
			// file'size less than 500M
			List items = upload.parseRequest(request); // 这里开始执行上传

			Iterator iter = items.iterator();

			/**
			 * 设置appID
			 */
			// while (iter.hasNext()) {
			// FileItem item = (FileItem) iter.next(); //FileItem就是表示一个表单域。
			// if(item.isFormField()){
			// if (item.getFieldName().equals("fileAppID")) {
			// try {
			// attachementVo.setFileAppID(Integer.parseInt(item.getString("gbk")));
			// } catch (Exception e) {
			// 
			// }
			// }else if (item.getFieldName().equals("attachementName")) {
			// attachementVo.setAttachementName(item.getString("UTF-8"));
			// }
			// }
			// }
			iter = items.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next(); // FileItem就是表示一个表单域。
				AttachementVo attachementVo = new AttachementVo();
				String val = request.getParameter("columnidupload");
				String filename = request.getParameter("attachementName");

				if (item.isFormField()) {
					// isFormField方法用于判断FileItem是否代表一个普通表单域(即非file表单域)
					// log.info("***"+item.getFieldName());
					// log.info("***"+item.getString("gbk"));
				} else {
					/**
					 * 设置 附件保存信息
					 */
					String fileName = item.getName(); // 返回该文件在客户机上的文件名。e.g:
					// e:\dianying\\video\1.wmv
					String fileType = fileName.substring(fileName
							.lastIndexOf("."));
					String userId = (String) request.getSession().getAttribute(
							"userId");
					String userName = (String) request.getSession()
							.getAttribute("uName");
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					String column = RandomGUID.geneGuid();
					String filername = new Date().getTime() + "";

					attachementVo.setFileid(RandomGUID.geneGuid());
					DecimalFormat df = new DecimalFormat("#.##");
					attachementVo.setFilesize(df
							.format((item.getSize() / (1024.00)))
							+ "KB");
					attachementVo.setFiletype(fileType);
					attachementVo.setFilename(fileName.substring(0, fileName
							.lastIndexOf(".")));
					attachementVo.setUploaddate(sdf.format(new Date()));
					attachementVo.setUserid(userId);
					attachementVo.setUsername(userName);
					attachementVo.setFilername(filername);
					if (filename != null && (!"".equals(filename))) {
						filename = StrTools.charsetFormat(filename,
								"ISO8859-1", "UTF-8");
						attachementVo.setFilename(filename);
					}
					attachementVo
							.setFilepath((uploadPath +"/"+ filername + fileType));
					File uploadedFile = new File(vFileDir+ filername
							+ fileType);
					item.write(uploadedFile);

					/**
					 * 返回 appID
					 */
					response.setContentType("text/html;charset=gb2312");
					PrintWriter out = response.getWriter();

					if (val != null && (!"".equals(val))) {
						attachementVo.setColumnid(val);
						out.println(val);
					} else {
						attachementVo.setColumnid(column);
						out.println(column);
					}
					out.flush();
					int attachementAppID = UploadDaoImpl.add(attachementVo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}
}