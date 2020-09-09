package com.lyht.util;

import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelUtils {
	private static Logger log = LoggerFactory.getLogger(ExcelUtils.class);

	public static void main(String[] args) throws InvalidFormatException, IOException {
		String path = "D:\\Server\\uploads\\tuoba\\word\\test.xlsx";
		String outPath = "D:\\Server\\uploads\\tuoba\\word\\test1.xlsx";

		List<ExcelSheet> list = new ArrayList<>();
		ExcelSheet excelSheet = new ExcelSheet();
		excelSheet.setSheetName("Sheet1");
		excelSheet.setStartRowIndex(4);

		List<List<Object>> listData = new ArrayList<>();
		List<Object> rowData = new ArrayList<>();
		rowData.add("11111");
		rowData.add("阿斯蒂啊");
		rowData.add("333");
		rowData.add("44");
		rowData.add("阿斯顿撒");
		rowData.add("请问请问");
		listData.add(rowData);
		List<Object> rowData1 = new ArrayList<>();
		rowData1.add("22222");
		rowData1.add("阿斯蒂啊1");
		rowData1.add("3331");
		rowData1.add("441");
		rowData1.add("阿斯顿撒1");
		rowData1.add("请问请问1");
		listData.add(rowData1);
		excelSheet.setListData(listData);

		list.add(excelSheet);

		ExcelUtils.writeAndReplaceExcel(path, outPath, list);
	}

	/**
	 * 导出到指定路径（替换模板内容、遍历表格数据、删除空白行、修改sheet名称）
	 * 
	 * @param inputPath      excel模板路径
	 * @param outputPath     输出的excel路径
	 * @param excelSheetList 替换的内容
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	public static void writeAndReplaceExcel(String inputPath, String outputPath, List<ExcelSheet> excelSheetList)
			throws IOException, InvalidFormatException {

		InputStream is = new FileInputStream(new File(inputPath));
		Workbook workbook = WorkbookFactory.create(is);

		replaceExcel(workbook, excelSheetList);// 替换excel中内容

		OutputStream out = new FileOutputStream(new File(outputPath));
		workbook.write(out);
		is.close();
		out.close();
	}

	/**
	 * 浏览器导出（替换模板内容、遍历表格数据、删除空白行、修改sheet名称）
	 * 
	 * @param inputPath      excel模板路径
	 * @param fileName       导出的文件名称
	 * @param response       HttpServletResponse
	 * @param excelSheetList 替换的内容
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	public static void exportAndReplaceExcel(String inputPath, String fileName, HttpServletResponse response,
			List<ExcelSheet> excelSheetList) throws IOException, InvalidFormatException {

		File file = new File(inputPath);
		String name = file.getName();
		String[] split = name.split("\\.");
		String type = split[1];

		InputStream is = new FileInputStream(file);
		Workbook workbook = WorkbookFactory.create(is);

		replaceExcel(workbook, excelSheetList);// 替换excel中内容

		response.setContentType("application/vnd.ms-excel");
		response.addHeader("Content-Disposition",
				"attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + "." + type);

		OutputStream out = response.getOutputStream();
		workbook.write(out);
		is.close();
		out.close();
	}
	
	public static void exportAndReplaceExcel(String inputPath, String fileName, HttpServletResponse response,
			List<ExcelSheet> excelSheetList,String qecodePath) throws IOException, InvalidFormatException {

		File file = new File(inputPath);
		String name = file.getName();
		String[] split = name.split("\\.");
		String type = split[1];

		InputStream is = new FileInputStream(file);
		Workbook workbook = WorkbookFactory.create(is);
		
		replaceExcel(workbook, excelSheetList);// 替换excel中内容
		
		XSSFWorkbook book = (XSSFWorkbook) workbook;
		//加入二维码
		BufferedImage bufferedImage = ImageIO.read(new File(qecodePath));
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
		XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0, 255, 255, (short) 3, 1, (short) 4, 5);
		ExcelUtilImg.AddPictureToExcel(book, byteArrayOutputStream,anchor);
		
		response.setContentType("application/vnd.ms-excel");
		response.addHeader("Content-Disposition",
				"attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + "." + type);

		OutputStream out = response.getOutputStream();
		workbook.write(out);
		is.close();
		out.close();
	}
	
	

	/**
	 * 替换excel内容
	 * 
	 * @param workbook       要替换的Workbook（excel）
	 * @param excelSheetList 替换的内容
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	public static void replaceExcel(Workbook workbook, List<ExcelSheet> excelSheetList)
			throws IOException, InvalidFormatException {
		if (excelSheetList != null && !excelSheetList.isEmpty()) {
			for (ExcelSheet excelSheet : excelSheetList) {
				if (excelSheet != null) {
					// 获取Excel的工作表对应名称的sheet以及下标
					String sheetName = excelSheet.getSheetName();
					Integer sheetIndex = null;
					Sheet sheet = null;
					try {
						sheetIndex = workbook.getSheetIndex(sheetName);
						sheet = workbook.getSheetAt(sheetIndex);
					} catch (Exception e) {
						log.error("=====ExcelUtils=====Method：replaceExcel=====名称为：" + sheetName + "的sheet不存在", e);
					}

					// 判断是否删除对应的sheet
					boolean remove = excelSheet.isRemove();
					if (remove) {// 是，则删除sheet
						if (sheetIndex != null) {
							workbook.removeSheetAt(sheetIndex);
						}
					} else {// 否，则正常替换与遍历列表
						// 替换sheet内容
						replaceSheet(sheet, excelSheet);
						// 遍历列表
						replaceSheetTable(workbook, sheet, excelSheet);
						// 合并单元格
						mergerCell(workbook, sheet, excelSheet);
						// 删除sheet中的空行
						removeBlankLine(sheet);
						// 修改sheet名称
						String sheetNewName = excelSheet.getSheetNewName();
						updateSheetName(workbook, sheetName, sheetNewName);

					}
				}
			}
		}
	}

	/**
	 * 遍历列表中的数据,并渲染到sheet对应的表格中
	 * 
	 * @param workbook
	 * 
	 * @param sheet
	 * @param excelSheet
	 */
	private static void replaceSheetTable(Workbook workbook, Sheet sheet, ExcelSheet excelSheet) {
		if (workbook == null || sheet == null || excelSheet == null) {// 参数校验
			return;
		}
		List<List<Object>> listData = excelSheet.getListData();// sheet表格渲染数据
		if (listData == null || listData.isEmpty()) {
			return;
		}
		String sheetName = excelSheet.getSheetName();// sheet名称
		int startRowIndex = excelSheet.getStartRowIndex();// sheet行下标
		int startColIndex = excelSheet.getStartColIndex();// sheet列下标
		boolean isBorder = excelSheet.isBorder();// 是否设置边框

		// 对齐方式
		CellStyle centerStyle = workbook.createCellStyle();
		centerStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 单元格内容垂直居中对齐
		centerStyle.setAlignment(HorizontalAlignment.CENTER);// 单元格内容水平居中对齐
		//设置字体
		Font font = workbook.createFont();
		short fontSize = excelSheet.getFontSize();
		font.setFontHeightInPoints(fontSize);
		short fontColor = excelSheet.getFontColor();
		font.setColor(fontColor);
		centerStyle.setFont(font);
		try {
			// 遍历渲染表格
			int size = listData.size();
			for (int rowIndex = 0; rowIndex < size; rowIndex++) {
				// 行
				List<Object> rowData = listData.get(rowIndex);
				if (rowData != null) {
					int lastRowNum = sheet.getLastRowNum();
					sheet.shiftRows(rowIndex + startRowIndex, lastRowNum, 1, true, false);// 当前行下的所有行动态下移一行
					Row row = sheet.createRow(rowIndex + startRowIndex);
					for (int colIndex = 0; colIndex < rowData.size(); colIndex++) {
						// 列
						Cell cell = row.createCell(colIndex + startColIndex);
						if (isBorder) {
							setBorder(workbook, cell);// 设置有边框的样式
						} else {
							cell.setCellStyle(centerStyle);// 设置样式
						}
						// 给单元格赋值
						Object object = rowData.get(colIndex);
						if (object != null) {
							String value = null;
							if (object instanceof Date) {// 日期转换为 yyyy-MM-dd HH:mm:ss
								value = DateUtil.parseDate((Date) object);
								cell.setCellValue(value);
							}else if(object instanceof BigDecimal){
								cell.setCellValue(Double.parseDouble(String.valueOf(object)));
								CellStyle cellStyle = workbook.createCellStyle();
								cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.0000"));
								cell.setCellStyle(cellStyle);
							}else {// 其他类型一律直接转字符串
								try {
									value = String.valueOf(object);
									cell.setCellValue(value);
								} catch (Exception e) {
									int rowNumber = rowIndex + startRowIndex + 1;
									int colNumber = colIndex + startColIndex + 1;
									log.error("=====ExcelUtils=====Method：replaceSheetTable=====sheet名称：" + sheetName
											+ ",行：" + rowNumber + "，列：" + colNumber + "=====表格的内容无法转换成字符串", e);
									continue;
								}
							}
						}
					}
				} else {
					rowIndex--;
					size--;
				}
			}
			// 解决poi-3.1.7以上版本shiftRows位移引用不变问题
			if (sheet instanceof XSSFSheet) {
				XSSFSheet xSSFSheet = (XSSFSheet) sheet;
				for (int r = xSSFSheet.getFirstRowNum(); r < sheet.getLastRowNum() + 1; r++) {
					XSSFRow row = xSSFSheet.getRow(r);
					if (row != null) {
						long rRef = row.getCTRow().getR();
						for (Cell cell : row) {
							String cRef = ((XSSFCell) cell).getCTCell().getR();
							((XSSFCell) cell).getCTCell().setR(cRef.replaceAll("[0-9]", "") + rRef);
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("=====ExcelUtils=====Method：replaceSheetTable=====sheet名称：" + sheetName + "=====表格渲染失败", e);
		}
	}

	/**
	 * 替换sheet中的内容(占位符：${属性名})
	 * 
	 * @param wb         Workbook
	 * @param excelSheet sheet对应的属性
	 */
	private static void replaceSheet(Sheet sheet, ExcelSheet excelSheet) {
		if (sheet == null || excelSheet == null) {// 参数校验
			return;
		}
		String sheetName = excelSheet.getSheetName();// sheet名称
		if (StringUtils.isBlank(sheetName)) {
			return;
		}
		Map<String, Object> replaceContent = excelSheet.getReplaceContent();// 替换内容
		if (replaceContent == null) {
			return;
		}

		try {
			// 获取Excel的行数
			int trLength = sheet.getLastRowNum() + 1;
			// 遍历sheet的行
			for (int i = 0; i < trLength; i++) {
				Row row = sheet.getRow(i);// 获取Excel的行，下标从0开始
				if (row == null) {// 若行为空，则遍历下一行
					continue;
				}
				int minColIx = row.getFirstCellNum();
				int maxColIx = row.getLastCellNum();
				for (int colIx = minColIx; colIx < maxColIx; colIx++) {
					Cell cell = row.getCell(colIx);// 获取指定单元格，单元格从左到右下标从0开始
					if (cell != null) {
						String celValue = parseCell(cell);
						if (StringUtils.isNotBlank(celValue)) {
							// 正则校验：单元格中内容是否需要替换
							Matcher matcher = matcher(celValue);
							if (matcher.find()) {
								Object object = replaceContent.get(matcher.group(1));
								String value = null;
								if (object != null) {
									if (object instanceof Date) {// 日期转换为 yyyy-MM-dd HH:mm:ss
										value = DateUtil.parseDate((Date) object);
										if (StringUtils.isBlank(value)) {
											value = "";
										}
										while ((matcher = matcher(celValue)).find()) {
											celValue = matcher.replaceFirst(value);
										}
										cell.setCellValue(celValue);
									}else if(object instanceof BigDecimal) {
										cell.setCellValue(new BigDecimal(String.valueOf(object)).toPlainString());
									}else {// 其他类型一律直接转字符串
										try {
											value = String.valueOf(object);
											if (StringUtils.isBlank(value)) {
												value = "";
											}
											while ((matcher = matcher(celValue)).find()) {
												celValue = matcher.replaceFirst(value);
											}
											cell.setCellValue(celValue);
										} catch (Exception e) {
											int rowNumber = i + 1;
											int colNumber = colIx + 1;
											log.error("=====ExcelUtils=====Method：replaceSheet=====sheet名称：" + sheetName
													+ ",行：" + rowNumber + "，列：" + colNumber + "=====单元格替换的内容无法转换成字符串",
													e);
										}
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("=====ExcelUtils=====Method：replaceSheet=====名称为：" + sheetName + "的sheet内容替换失败", e);
		}
	}

	/**
	 * 合并单元格
	 * 
	 * @param sheet
	 * @param excelSheet
	 */
	public static void mergerCell(Workbook workbook, Sheet sheet, ExcelSheet excelSheet) {
		List<CellRangeAddress> mergerList = excelSheet.getMergerList();
		if (sheet == null || mergerList == null || mergerList.isEmpty()) {
			return;
		}
		try {
			boolean border = excelSheet.isBorder();
			for (CellRangeAddress cellRangeAddress : mergerList) {
				sheet.addMergedRegion(cellRangeAddress);
				if (border) {
					RegionUtil.setBorderTop(BorderStyle.THIN, cellRangeAddress, sheet);
					RegionUtil.setBorderBottom(BorderStyle.THIN, cellRangeAddress, sheet);
					RegionUtil.setBorderLeft(BorderStyle.THIN, cellRangeAddress, sheet);
					RegionUtil.setBorderRight(BorderStyle.THIN, cellRangeAddress, sheet);
				}
			}
		} catch (Exception e) {
			log.error("=====ExcelUtils=====Method：mergerCell=====合并单元格失败", e);
		}
	}

	/**
	 * 修改sheet名称
	 * 
	 * @param workbook
	 * @param sheetName
	 * @param sheetNewName
	 */
	public static void updateSheetName(Workbook workbook, String sheetName, String sheetNewName) {
		if (workbook == null || StringUtils.isBlank(sheetName) || StringUtils.isBlank(sheetNewName)) {
			return;
		}
		try {
			int sheetIndex = workbook.getSheetIndex(sheetName);
			workbook.setSheetName(sheetIndex, sheetNewName);
		} catch (Exception e) {
			log.error("=====ExcelUtils=====Method：updateSheetName=====" + sheetName + "的sheet名称修改失败", e);
		}
	}

	/**
	 * 设置单元格的边框
	 * 
	 * @param sheet
	 */
	public static void setBorder(Workbook workbook, Cell cell) {
		// 边框
		CellStyle borderStyle = workbook.createCellStyle();
		borderStyle.setBorderTop(BorderStyle.THIN);
		borderStyle.setBorderBottom(BorderStyle.THIN);
		borderStyle.setBorderLeft(BorderStyle.THIN);
		borderStyle.setBorderRight(BorderStyle.THIN);

		borderStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 单元格内容垂直居中对齐
		borderStyle.setAlignment(HorizontalAlignment.CENTER);// 单元格内容水平居中对齐

		cell.setCellStyle(borderStyle);
	}

	/**
	 * 删除sheet中的空行
	 * 
	 * @param sheet
	 */
	public static void removeBlankLine(Sheet sheet) {
		int rowNum = sheet.getLastRowNum();
		int lastRowNum = sheet.getLastRowNum();
		while (rowNum > 0) {
			rowNum--;
			Row row = sheet.getRow(rowNum);
			if (row == null) {
				// 删除空白行
				sheet.shiftRows(rowNum + 1, lastRowNum, -1);
			}
			// 删除没有内容的行
		}
	}

	public static void getCenterCellStyle() {

	}

	/**
	 * 正则匹配字符串
	 * 
	 * @param str
	 * @return
	 */
	public static Matcher matcher(String str) {
		Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);
		return matcher;
	}

	/**
	 * 解析单元格内容（只解析字符）
	 * 
	 * @param cell
	 * @return
	 */
	public static String parseCell(Cell cell) {
		String str = null;
		if (cell.getCellType() == CellType.STRING) {
			str = cell.getStringCellValue();
		}
		return str;
	}

}
