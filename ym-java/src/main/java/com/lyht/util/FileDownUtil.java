package com.lyht.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletResponse;

public class FileDownUtil {
	 /** 下载文件
		 * @return
		 */
		public static boolean getFile(String url,String fileName, HttpServletResponse response){
			//在线打开 true 或者 下载 false
			boolean isOnLine = false;
			// 得到请求下载的文件名
			OutputStream out = null;
			BufferedInputStream br =null;
			try {
			    br = new BufferedInputStream(new FileInputStream(url));
			     //要下载的文件名
			     String filename=fileName;	
			     filename = new String(filename.getBytes("GB2312"), "ISO_8859_1"); 	        	 	        	
			     byte[] buf = new byte[1024];
			     int len = 0;
			     response.reset(); // 非常重要
						if (isOnLine) { // 在线打开方式
							File f = new File(url);  
							String contype= new MimetypesFileTypeMap().getContentType(f);					
							response.setContentType(contype);
							response.setHeader("Content-Disposition", "inline; filename=" + filename);
			        	// 文件名应该编码成UTF-8
			        	} else { // 纯下载方式
			        		response.setContentType("application/x-msdownload");
			        		response.setHeader("Content-Disposition", "attachment; filename=" + filename);
			        	}
			        	out = response.getOutputStream();
			        	while ((len = br.read(buf)) > 0)
			        			out.write(buf, 0, len);
					} catch (Exception e) {
						return false;
					}finally{
							try {
								if(br != null)
									br.close();
								if(out != null)
									out.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
					}
				 return true;
			 }
		
		public static void copyFile(File sourceFile, File targetFile) throws IOException {
	        BufferedInputStream inBuff = null;
	        BufferedOutputStream outBuff = null;
	        try {
	        	
	        	
	            // 新建文件输入流并对它进行缓冲
	            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

	            // 新建文件输出流并对它进行缓冲
	            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

	            // 缓冲数组
	            byte[] b = new byte[1024 * 5];
	            int len;
	            while ((len = inBuff.read(b)) != -1) {
	                outBuff.write(b, 0, len);
	            }
	            // 刷新此缓冲的输出流
	            outBuff.flush();
	        } finally {
	            // 关闭流
	            if (inBuff != null)
	                inBuff.close();
	            if (outBuff != null)
	                outBuff.close();
	        }
	    }
		
		 /**
	     * 删除目录（文件夹）以及目录下的文件
	     * @param   sPath 被删除目录的文件路径
	     * @return  目录删除成功返回true，否则返回false
	     */
	    public static boolean deleteDirectory(String sPath) {
	    	boolean flag = false;
	        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
	        if (!sPath.endsWith(File.separator)) {
	            sPath = sPath + File.separator;
	        }
	        File dirFile = new File(sPath);
	        //如果dir对应的文件不存在，或者不是一个目录，则退出
	        if (!dirFile.exists() || !dirFile.isDirectory()) {
	            return false;
	        }
	        flag = true;
	        //删除文件夹下的所有文件(包括子目录)
	        File[] files = dirFile.listFiles();
	        for (int i = 0; i < files.length; i++) {
	            //删除子文件
	            if (files[i].isFile()) {
	                flag = deleteFile(files[i].getAbsolutePath());
	                if (!flag) break;
	            } //删除子目录
	            else {
	                flag = deleteDirectory(files[i].getAbsolutePath());
	                if (!flag) break;
	            }
	        }
	        if (!flag) return false;
	        //删除当前目录
	        if (dirFile.delete()) {
	            return true;
	        } else {
	            return false;
	        }
	    }
	    
	    /**
	     * 删除单个文件
	     * @param   sPath    被删除文件的文件名
	     * @return 单个文件删除成功返回true，否则返回false
	     */
	    public static boolean deleteFile(String sPath) {
	       boolean flag = false;
	       File file = new File(sPath);
	        // 路径为文件且不为空则进行删除
	        if (file.isFile() && file.exists()) {
	            file.delete();
	            flag = true;
	        }
	        return flag;
	    }

}
