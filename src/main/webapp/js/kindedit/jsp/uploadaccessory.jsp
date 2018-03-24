<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.io.*"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="org.apache.commons.fileupload.*"%>
<%@ page import="org.apache.commons.fileupload.disk.*"%>
<%@ page import="org.apache.commons.fileupload.servlet.*"%>
<%@ page import="org.json.simple.*"%>
<%
	/**
	 * KindEditor JSP
	 * 
	 * 本JSP程序是演示程序，建议不要直接在实际项目中使用。
	 * 如果您确定直接使用本程序，使用之前请仔细确认相关安全设置。
	 * 
	 */

	//文件保存目录路径
	String savePath = pageContext.getServletContext().getRealPath("/")
			+ "uploads/accessory/";
	//文件保存目录URL
	String saveUrl = request.getContextPath() + "/uploads/accessory/";
	//定义允许上传的文件扩展名
	String[] fileTypes = new String[] { "doc", "docx", "txt", "pdf",
			"ppt", "xls", "wps", "zip", "rar" };
	//最大文件大小
	long maxSize = 3000000;

	response.setContentType("text/html; charset=UTF-8");

	if (!ServletFileUpload.isMultipartContent(request)) {
		out.println(getError("请选择文件。"));
		return;
	}
	//检查目录
	File uploadDir = new File(savePath);
	if (!uploadDir.isDirectory()) {
		out.println(getError("上传目录不存在。"));
		return;
	}
	//检查目录写权限
	if (!uploadDir.canWrite()) {
		out.println(getError("上传目录没有写权限。"));
		return;
	}
	FileItemFactory factory = new DiskFileItemFactory();
	ServletFileUpload upload = new ServletFileUpload(factory);
	upload.setHeaderEncoding("UTF-8");
	request.setCharacterEncoding("UTF-8");
	List items = upload.parseRequest(request);
	Map<String, String> paramMap = new HashMap<String, String>();
	FileItem fileItem = null;
	Iterator itr = items.iterator();
	while (itr.hasNext()) {
		FileItem item = (FileItem) itr.next();
		if (item.isFormField()) {
			String fieldName = item.getFieldName();
			String value = item.getString("UTF-8");
			paramMap.put(fieldName, value);
		} else {
			fileItem = item;
		}
	}
	if (fileItem != null) {
		long fileSize = fileItem.getSize();
		String fileName = fileItem.getName();
		//检查文件大小
		if (fileItem.getSize() > maxSize) {
			out.println(getError("上传文件大小超过限制。"));
			return;
		}
		//检查扩展名
		String fileExt = fileName.substring(
				fileName.lastIndexOf(".") + 1).toLowerCase();
		if (!Arrays.<String> asList(fileTypes).contains(fileExt)) {
			out.println(getError("上传文件扩展名是不允许的扩展名。"));
			return;
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String newFileName = df.format(new Date()) + "_"
				+ new Random().nextInt(1000) + "." + fileExt;
		try {
			File uploadedFile = new File(savePath, newFileName);
			fileItem.write(uploadedFile);
		} catch (Exception e) {
			out.println(getError("上传文件失败。"));
			return;
		}
		String title = paramMap.get("imgTitle");
		String id = paramMap.get("id");
		int fn = fileName.lastIndexOf(File.separator);
		if (title == null || title.equals("")){
			title = fileName.substring(fn+1);
		}
		//发送给KE    
		out
				.println("<html><head><title>Insert Image</title><meta http-equiv='content-type' content='text/html; charset=utf-8'/></head><body>");
		out.println("<script type='text/javascript'>");
		out.println("parent.KE.plugin['accessory'].insert('" + id
				+ "','"+saveUrl+ newFileName + "','" + title + "','" + fileExt
				+ "');</script>");
		out.println("</body></html>");
	}
%>
<%!private String getError(String message) {
		JSONObject obj = new JSONObject();
		obj.put("上传失败:", message);
		return obj.toJSONString();
	}%>