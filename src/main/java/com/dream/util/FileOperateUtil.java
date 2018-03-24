package com.dream.util;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
* 
* @author maolei
* @date 2015-5-11 10:00
*/
public class FileOperateUtil {
	private static final String REALNAME = "realName";
	private static final String STORENAME = "storeName";
	private static final String SIZE = "size";
	private static final String SUFFIX = "suffix";
	private static final String CONTENTTYPE = "contentType";
	private static final String separator= File.separator;
	public static final String UPLOADDIR = "/uploads/file/";
	public static final String PANDIR="/uploads/pan/";
	public static final long KBSIZE=1024;
	public static final long MBSIZE=1024*1024;
    public static final long GBSIZE=1024*1024*1024;
    public static List<String> imageType=Arrays.asList(new String[]{"jpg","jpeg","gif","png","bmp"});
    public static List<String> txtType=Arrays.asList(new String[]{
    		"txt","java","html","c","ppt","pptx","doc","docx","wps","xls"
    		});
    public static List<String> musicType=Arrays.asList(new String[]{
    		"mp3","wav","midi","cda","wma"
    		});  
    
    public static List<String> videoType=Arrays.asList(new String[]{
    		"avi","rm","rmvb","wmv","vob","mov","mp4","3gp","flash"
    		});
	/**
	 * 将上传的文件进行重命名
	 * 
	 * @author maolei
	 * @date 2015-05-11
	 * @param name
	 * @return
	 */
	private static String rename(String name) {
		Long now = Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmss")
				.format(new Date()));
		String fileName = now + "" + (new Random()).nextInt(100);
		if (name.indexOf(".") != -1) {
			fileName += name.substring(name.lastIndexOf("."));
		}
		return fileName;
	}


	/**
	 * 上传文件
	 * 
	 * @author maolei
	 * @date 2015-05-11 10:55
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> upload(HttpServletRequest request) throws Exception {

		Map<String, String> map = new HashMap<String, String>();

		MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = mRequest.getFileMap();

		String uploadDir = request.getSession().getServletContext()
				.getRealPath("/")+ FileOperateUtil.UPLOADDIR;
		File file = new File(uploadDir);

		if (!file.exists()) {
			file.mkdir();
		}
		

		String fileName = null;
		Iterator<Map.Entry<String, MultipartFile>> it = fileMap.entrySet()
				.iterator();
		while (it.hasNext()) {

			Map.Entry<String, MultipartFile> entry = it.next();
			MultipartFile mFile = entry.getValue();

			fileName = mFile.getOriginalFilename();
			
			String rename=rename(fileName);

			String storeName = uploadDir+rename;

			
			BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(storeName));

			FileCopyUtils.copy(mFile.getInputStream(), bos);
			// 固定参数值对
			long fileLen=new File(storeName).length();
			map.put(FileOperateUtil.REALNAME, fileName);
			map.put(FileOperateUtil.STORENAME, UPLOADDIR+rename);
			map.put(FileOperateUtil.SIZE, (fileLen)+"");
			map.put(FileOperateUtil.SUFFIX,getSuffux(rename));
			map.put(FileOperateUtil.CONTENTTYPE, "application/octet-stream");
			map.put("fileid",rename);
			map.put("sizedesc",getSizeDesc(fileLen));
			map.put("createTime", FormatDate.getYMdHHmmss());
		//	result.add(map);
		}
		return map;
	}
	/**
	 * 下载
	 * 
	 * @author maolei
	 * @date 2015-5-11 10:57
	 * @param request
	 * @param response
	 * @param storeName
	 * @param contentType
	 * @param realName
	 */
	public static void download(HttpServletRequest request,
								HttpServletResponse response, String filePath, String contentType,
								String realName){
		
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		
		try{
				request.setCharacterEncoding("UTF-8");
				String ctxPath = request.getSession().getServletContext()
						.getRealPath("/");
				String downLoadPath = ctxPath + filePath;
		        File file=new File(downLoadPath);
		        if(!file.exists()){
		        	return;
		        }
				long fileLength = file.length();
				response.setContentType(contentType);
				response.setHeader("Content-disposition", "attachment; filename="
						+ java.net.URLEncoder.encode(realName, "UTF-8"));
				response.setHeader("Content-Length", String.valueOf(fileLength));
				bis = new BufferedInputStream(new FileInputStream(downLoadPath));
				bos = new BufferedOutputStream(response.getOutputStream());
				byte[] buff = new byte[2048];
				int bytesRead;
				while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
					bos.write(buff, 0, bytesRead);
				}
				bis.close();
				bos.close();
				bos.flush();
		}catch(Exception e){
			    e.printStackTrace();
		}finally{

		}

	}
	public static void copyFile(String sourceStr, String targetStr) {
		BufferedInputStream inBuff = null;
		BufferedOutputStream outBuff = null;
		try {
			File sourceFile=new File(sourceStr);
			if(!sourceFile.exists()){
				return;
			}
			File targetFile=new File(targetStr);
			if(targetFile.exists()){
				targetFile.delete();
			}
			inBuff = new BufferedInputStream(new FileInputStream(sourceFile));
			outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));
			byte[] buffer = new byte[8192];
			int length;
			while ((length = inBuff.read(buffer)) != -1) {
				outBuff.write(buffer, 0, length);
			}
			outBuff.flush();
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try{
					inBuff.close();
					outBuff.close();
			}catch(Exception e2){
				
			}
		}
	}	
	public static void deleteFile(String filePath){
		File file=new File(filePath);
		if(file.exists()){
			file.delete();
		}
	}
	
	public static void deleteByFileid(HttpServletRequest request, String fileid){
		String uploadDir = request.getSession().getServletContext()
				.getRealPath("/")+ FileOperateUtil.UPLOADDIR;
		String filePath=uploadDir+fileid;
		deleteFile(filePath);
	}
	public static void deleteJxpan(HttpServletRequest request, String filePath){
		String abspath = request.getSession().getServletContext()
				.getRealPath("/")+ FileOperateUtil.PANDIR+filePath;
		deleteFile(abspath);
	}
	
	public static void deleteWidthPandir(String pandir, String filePath){
		String abspath =pandir+filePath;
		deleteFile(abspath);
	}
	
	public static String getSizeDesc(long size){
		StringBuilder sb=new StringBuilder("");
		if(size<KBSIZE){
			//不满足1K
			sb.append(size).append(" 字节");
		}else if(size>=KBSIZE&&size<MBSIZE){
			sb.append(patternPrint("#.##",(1.0*size)/KBSIZE)).append(" KB");
		}else{
			sb.append(patternPrint("#.##",(1.0*size)/MBSIZE)).append(" MB");
		}
		return sb.toString();
	}
	
	public static String patternPrint(String pattern, double number){
		DecimalFormat df = new DecimalFormat(pattern);
		return df.format(number);
	}	
	
	public static String getSuffux(String fileName){
		String suffux=fileName.substring(fileName.lastIndexOf('.')+1).toLowerCase();
		return suffux;
	}
	
	public static Map<String, String> panupload(HttpServletRequest request, String username) throws Exception {

		Map<String, String> map = new HashMap<String, String>();

		MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = mRequest.getFileMap();

		String uploadDir = request.getSession().getServletContext()
				.getRealPath("/")+ FileOperateUtil.PANDIR+separator+username+separator;
		File file = new File(uploadDir);
		
		String userdir=username+separator;

		//创建个人文件目录
		if (!file.exists()) {
			file.mkdir();
		}
		

		String fileName = null;
		Iterator<Map.Entry<String, MultipartFile>> it = fileMap.entrySet()
				.iterator();
		while (it.hasNext()) {

			Map.Entry<String, MultipartFile> entry = it.next();
			MultipartFile mFile = entry.getValue();

			fileName = mFile.getOriginalFilename();
			
			String rename=rename(fileName);
			
			String suffux=getSuffux(rename);
			if("exe".equals(suffux)){
				map.put("error","不能上传可执行文件");
				continue;
			}

			String storeName = uploadDir+rename;

			
			BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(storeName));

			FileCopyUtils.copy(mFile.getInputStream(), bos);
			// 固定参数值对

			long fileLen=new File(storeName).length();
			map.put(FileOperateUtil.REALNAME, fileName);
			map.put(FileOperateUtil.STORENAME, userdir+rename);
			map.put(FileOperateUtil.SIZE, (fileLen)+"");
			map.put(FileOperateUtil.SUFFIX,suffux);
			map.put(FileOperateUtil.CONTENTTYPE, "application/octet-stream");
			map.put("mbsize", patternPrint("#.###",(1.0*fileLen)/MBSIZE));
			map.put("fileid",rename);
			map.put("sizedesc",getSizeDesc(fileLen));
			map.put("createTime", FormatDate.getYMdHHmmss());
			
				//图片
			map.put("filetype",""+getfileType(suffux));
		//	result.add(map);
		}
		return map;
	}	
	public static boolean isimage(String type){
		type=type.toLowerCase();
		if(imageType.contains(type)){
			return true;
		}
		return false;
	}
	public static boolean istxt(String type){
		type=type.toLowerCase();
		if(txtType.contains(type)){
			return true;
		}
		return false;
	}
	public static int getfileType(String type){
		type=type.toLowerCase();
		if(imageType.contains(type)){
			return 1;
		}else if(txtType.contains(type)){
			return 2;
		}else if(musicType.contains(type)){
			return 4;
		}else if(videoType.contains(type)){
			return 5;
		}else{
			return 3;
		}
	}
	public static void main(String[] args){
		System.out.println(getSizeDesc(1926999));
	}
	
}
