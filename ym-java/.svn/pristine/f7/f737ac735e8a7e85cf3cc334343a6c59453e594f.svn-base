package com.lyht.util;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
 
import org.apache.commons.lang3.StringUtils;
 
/**
 * 包装类
 * @author chenlj
 *2019年3月16日19:01:44
 * @param <T>
 */
public class ExportExcelWrapper<T> extends ExportExcelUtil<T> {
	/**
	 * <p>
	 * 导出带有头部标题行的Excel <br>
	 * 时间格式默认：yyyy-MM-dd hh:mm:ss <br>
	 * </p>
	 * 
	 * @param title 表格标题
	 * @param headers 头部标题集合
	 * @param dataset 数据集合
	 * @param out 输出流
	 * @param version 2003 或者 2007，不传时默认生成2003版本
	 */
	@SuppressWarnings("rawtypes")
	public void exportExcel(String fileName, String title, String[] headers,String[] columnNames,List<Map> datalist,  HttpServletResponse response,String version) {
		try {
			response.setContentType("application/vnd.ms-excel");  
			if(StringUtils.isBlank(version) || EXCEL_FILE_2003.equals(version.trim())){
				response.addHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8") + ".xls");
				exportExcel2003(title, headers,columnNames, datalist, response.getOutputStream(), "yyyy-MM-dd hh:mm:ss");
			}else{
				response.addHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8") + ".xlsx");
				exportExcel2007(title, headers,columnNames, datalist, response.getOutputStream(), "yyyy-MM-dd hh:mm:ss");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * <p>
	 * 导出带有头部标题行的Excel <br>
	 * 时间格式默认：yyyy-MM-dd hh:mm:ss <br>
	 * </p>
	 *
	 * @param title 表格标题
	 * @param headers 头部标题集合
	 * @param dataset 数据集合
	 * @param out 输出流
	 * @param version 2003 或者 2007，不传时默认生成2003版本
	 */
	@SuppressWarnings("rawtypes")
	public void exportExcelTwo(String fileName, String title, String[] headers,String[] columnNames,List<Map> datalist,  HttpServletResponse response,String version) {
		try {
			response.setContentType("application/vnd.ms-excel");
			if(StringUtils.isBlank(version) || EXCEL_FILE_2003.equals(version.trim())){
				response.addHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8") + ".xls");
				exportExcel2003Two(title, headers,columnNames, datalist, response.getOutputStream(), "yyyy-MM-dd hh:mm:ss");
			}else{
				response.addHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8") + ".xlsx");
				exportExcel2007(title, headers,columnNames, datalist, response.getOutputStream(), "yyyy-MM-dd hh:mm:ss");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * <p>
	 * 导出带有头部标题行的Excel <br>
	 * 时间格式默认：yyyy-MM-dd hh:mm:ss <br>
	 * </p>
	 *
	 * @param title 表格标题
	 * @param headers 头部标题集合
	 * @param dataset 数据集合
	 * @param out 输出流
	 * @param version 2003 或者 2007，不传时默认生成2003版本
	 */
	@SuppressWarnings("rawtypes")
	public void exportExcelFunds(String fileName, String title, String[] headers,String[] columnNames,List<Map> datalist,  HttpServletResponse response,String version) {
		try {
			response.setContentType("application/vnd.ms-excel");
			if(StringUtils.isBlank(version) || EXCEL_FILE_2003.equals(version.trim())){
				response.addHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8") + ".xls");
				exportExcel2003Funds(title, headers,columnNames, datalist, response.getOutputStream(), "yyyy-MM-dd hh:mm:ss");
			}else{
				response.addHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8") + ".xlsx");
				exportExcel2007Funds(title, headers,columnNames, datalist, response.getOutputStream(), "yyyy-MM-dd hh:mm:ss");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 *  搬迁人口导出
	 * <p>
	 * 导出带有头部标题行的Excel <br>
	 * 时间格式默认：yyyy-MM-dd hh:mm:ss <br>
	 * </p>
	 *
	 * @param title 表格标题
	 * @param headers 头部标题集合
	 * @param dataset 数据集合
	 * @param out 输出流
	 * @param version 2003 或者 2007，不传时默认生成2003版本
	 *
	 */
	@SuppressWarnings("rawtypes")
	public void exportExcelmove(String fileName, String title, String[] headers,String[] columnNames,List<Map> datalist,  HttpServletResponse response,String version) {
		try {
			response.setContentType("application/vnd.ms-excel");
			if(StringUtils.isBlank(version) || EXCEL_FILE_2003.equals(version.trim())){
				response.addHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8") + ".xls");
				exportExcelmove(title, headers,columnNames, datalist, response.getOutputStream(), "yyyy-MM-dd hh:mm:ss");
			}else{
				response.addHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8") + ".xlsx");
				exportExcel2007(title, headers,columnNames, datalist, response.getOutputStream(), "yyyy-MM-dd hh:mm:ss");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
