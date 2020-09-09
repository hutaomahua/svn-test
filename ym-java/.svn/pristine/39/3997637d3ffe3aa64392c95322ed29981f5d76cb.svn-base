package com.lyht.util;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 * 导出Excel
 *
 * @param <T>
 * @author chenlj
 * 2019年3月16日19:01:44
 */
public class ExportExcelUtil<T> {

    // 2007 版本以上 最大支持1048576行
    public final static String EXCEl_FILE_2007 = "2007";
    // 2003 版本 最大支持65536 行
    public final static String EXCEL_FILE_2003 = "2003";


    /**
     * <p>
     * 通用Excel导出方法,利用反射机制遍历对象的所有字段，将数据写入Excel文件中 <br>
     * 此版本生成2007以上版本的文件 (文件后缀：xlsx)
     * </p>
     *
     * @param title   表格标题名
     * @param headers 表格头部标题集合
     * @param dataset 需要显示的数据集合,集合中一定要放置符合JavaBean风格的类的对象。此方法支持的
     *                JavaBean属性的数据类型有基本数据类型及String,Date
     * @param out     与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     * @param pattern 如果有时间数据，设定输出格式。默认为"yyyy-MM-dd hh:mm:ss"
     */
    @SuppressWarnings({"rawtypes", "resource"})
    public void exportExcel2007(String title, String[] headers, String[] columnNames, List<Map> list, OutputStream out, String pattern) {
        // 声明一个工作薄
    	Workbook workbook = null;
    	if (list.size() < 1000) {
    		workbook = new XSSFWorkbook();
    	} else {
    		workbook = new SXSSFWorkbook();
    	}
        // 生成一个表格
        Sheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth(20);
        // 生成一个样式
        CellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(IndexedColors.BLACK.index);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        // 生成一个字体
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontName("宋体");
        font.setColor(IndexedColors.BLACK.index);
        font.setFontHeightInPoints((short) 11);
        // 把字体应用到当前的样式
        style.setFont(font);
        // 生成并设置另一个样式
        CellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor(IndexedColors.WHITE.index);
        style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style2.setBorderBottom(BorderStyle.THIN);
        style2.setBorderLeft(BorderStyle.THIN);
        style2.setBorderRight(BorderStyle.THIN);
        style2.setBorderTop(BorderStyle.THIN);
        style2.setAlignment(HorizontalAlignment.CENTER);
        style2.setVerticalAlignment(VerticalAlignment.CENTER);
        // 生成另一个字体
        Font font2 = workbook.createFont();
        // 把字体应用到当前的样式
        style2.setFont(font2);

        // 产生表格标题行
        Row row = sheet.createRow(0);
        row.setHeight((short) 400);
        Cell cellHeader;
        for (int i = 0; i < headers.length; i++) {
            cellHeader = row.createCell(i);
            cellHeader.setCellStyle(style);
            cellHeader.setCellValue(new XSSFRichTextString(headers[i]));
        }

        // 遍历集合数据，产生数据行
        Map map;
        XSSFRichTextString richString;
        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
        Cell cell;
        Matcher matcher;
        Object value;
        String textValue;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        for (int index = 0; index < list.size(); index++) {
            row = sheet.createRow(index + 1);
            map = list.get(index);
            String key = "";
            for (int i = 0; i < columnNames.length; i++) {
                key = columnNames[i];
                cell = row.createCell(i);
                cell.setCellStyle(style2);
                try {
                    value = map.get(key);
                    // 判断值的类型后进行强制类型转换
                    textValue = null;
                    if (value instanceof Integer) {
                        cell.setCellValue((Integer) value);
                    } else if (value instanceof Float) {
                        textValue = String.valueOf((Float) value);
                        cell.setCellValue(textValue);
                    } else if (value instanceof Double) {
                        textValue = String.valueOf((Double) value);
                        cell.setCellValue(textValue);
                    } else if (value instanceof Long) {
                        cell.setCellValue((Long) value);
                    }
                    if (value instanceof Date) {
                        textValue = sdf.format((Date) value);
                    } else {
                        // 其它数据类型都当作字符串简单处理
                        if (value != null) {
                            textValue = value.toString();
                        }
                    }
                    if (textValue != null) {
                        matcher = p.matcher(textValue);
                        if (matcher.matches()) {
                            // 是数字当作double处理
                            cell.setCellValue(Double.parseDouble(textValue));
                        } else {
                            richString = new XSSFRichTextString(textValue);
                            cell.setCellValue(richString);
                        }
                    }
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } finally {
                    // 清理资源
                }
            }
        }
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * <p>
     * 通用Excel导出方法,利用反射机制遍历对象的所有字段，将数据写入Excel文件中 <br>
     * 此方法生成2003版本的excel,文件名后缀：xls <br>
     * </p>
     *
     * @param title   表格标题名
     * @param headers 表格头部标题集合
     * @param list    需要显示的数据集合,集合中一定要放置符合JavaBean风格的类的对象。此方法支持的
     *                JavaBean属性的数据类型有基本数据类型及String,Date
     * @param out     与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     * @param pattern 如果有时间数据，设定输出格式。默认为"yyyy-MM-dd hh:mm:ss"
     */
    @SuppressWarnings({"rawtypes" })
    public void exportExcel2003Two(String title, String[] headers, String[] columnNames, List<Map> list, OutputStream out, String pattern) {
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFPalette palette = workbook.getCustomPalette();
        //wb HSSFWorkbook对象
        palette.setColorAtIndex((short) 9, (byte) (255), (byte) (192), (byte) (0));
        palette.setColorAtIndex((short) 10, (byte) (146), (byte) (208), (byte) (80));
        palette.setColorAtIndex((short) 11, (byte) (248), (byte) (77), (byte) (8));
        palette.setColorAtIndex((short) 12, (byte) (255), (byte) (255), (byte) (255));
        palette.setColorAtIndex((short) 13, (byte) (0), (byte) (0), (byte) (0));
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth(20);
        sheet.setColumnWidth(0, 8 * 256);
        sheet.setColumnWidth(1, 8 * 256);
        sheet.setColumnWidth(2, 12 * 256);
        sheet.setColumnWidth(3, 25 * 256);
        sheet.setColumnWidth(4, 10 * 256);
        sheet.setColumnWidth(5, 10 * 256);
        sheet.setColumnWidth(6, 12 * 256);
        sheet.setColumnWidth(7, 12 * 256);
        sheet.setColumnWidth(8, 12 * 256);
        sheet.setColumnWidth(9, 12 * 256);
        sheet.setColumnWidth(10, 8 * 256);
        sheet.setColumnWidth(11, 15 * 256);
        sheet.setColumnWidth(12, 15 * 256);
        sheet.setColumnWidth(13, 15 * 256);
        sheet.setColumnWidth(14, 15 * 256);
        sheet.setColumnWidth(15, 15 * 256);
        sheet.setColumnWidth(16, 15 * 256);
        sheet.setColumnWidth(17, 15 * 256);
        sheet.setColumnWidth(18, 15 * 256);
        sheet.setColumnWidth(19, 15 * 256);
        sheet.setColumnWidth(20, 15 * 256);
        sheet.setColumnWidth(21, 15 * 256);
        sheet.setColumnWidth(22, 15 * 256);
        sheet.setColumnWidth(23, 18 * 256);
        sheet.setColumnWidth(24, 15 * 256);
        sheet.setColumnWidth(25, 15 * 256);
        sheet.setColumnWidth(26, 150 * 256);
        sheet.setColumnWidth(27, 150 * 256);
        sheet.setColumnWidth(28, 150 * 256);
        sheet.setColumnWidth(29, 40 * 256);
        // 生成一个样式
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor((short) 12);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        // 生成一个字体
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontName("宋体");
        font.setColor((short) 12);
        font.setFontHeightInPoints((short) 12);
        // 把字体应用到当前的样式
        style.setFont(font);
        // 生成一个样式
        HSSFCellStyle style1 = workbook.createCellStyle();
        // 设置这些样式
        style1.setFillForegroundColor((short) 12);
        style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style1.setBorderBottom(BorderStyle.THIN);
        style1.setBorderLeft(BorderStyle.THIN);
        style1.setBorderRight(BorderStyle.THIN);
        style1.setBorderTop(BorderStyle.THIN);
        style1.setAlignment(HorizontalAlignment.CENTER);
        style1.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        // 生成一个字体
        HSSFFont font1 = workbook.createFont();
        font1.setBold(true);
        font1.setFontName("宋体");
        font1.setColor((short) 13);
        font1.setFontHeightInPoints((short) 36);
        // 把字体应用到当前的样式
        style1.setFont(font1);
        // 生成并设置另一个样式
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor((short) 12);
        style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style2.setBorderBottom(BorderStyle.THIN);
        style2.setBorderLeft(BorderStyle.THIN);
        style2.setBorderRight(BorderStyle.THIN);
        style2.setBorderTop(BorderStyle.THIN);
        style2.setAlignment(HorizontalAlignment.CENTER);
        style2.setVerticalAlignment(VerticalAlignment.CENTER);
        style2.setWrapText(true);
        // 生成另一个字体
        HSSFFont font2 = workbook.createFont();
        font2.setFontHeightInPoints((short) 12);
        // 把字体应用到当前的样式
        style2.setFont(font2);

        // 生成一个样式
        HSSFCellStyle style3 = workbook.createCellStyle();
        // 设置这些样式
        style3.setFillForegroundColor((short) 12);
        style3.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style3.setBorderBottom(BorderStyle.THIN);
        style3.setBorderLeft(BorderStyle.THIN);
        style3.setBorderRight(BorderStyle.THIN);
        style3.setBorderTop(BorderStyle.THIN);
        style3.setAlignment(HorizontalAlignment.CENTER);
        style3.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        style3.setWrapText(true);
        // 生成一个字体
        HSSFFont font3 = workbook.createFont();
        font3.setBold(true);
        font3.setFontName("宋体");
        font3.setColor((short) 13);
        font3.setFontHeightInPoints((short) 12);
        // 把字体应用到当前的样式
        style3.setFont(font3);

        Date d = new Date();
        SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd");
        String dateNowStr = sdfs.format(d);
        // 产生表格标题行
        HSSFRow row = sheet.createRow(0);
        HSSFCell cellTitle;
        cellTitle = row.createCell(0);
        cellTitle.setCellStyle(style1);
        String titles = "   中南院智慧征地移民平台进度管理报表      ";
        String text = titles + "统计日期："+ dateNowStr;
        //正常标题文字大小
        HSSFFont black = cellTitle.getCellStyle().getFont(workbook);
        //统计日期文字大小
        HSSFFont red =  workbook.createFont();
        red.setBold(true);
        red.setFontName("宋体");
        red.setColor((short) 13);
        red.setFontHeightInPoints((short) 20);

        HSSFRichTextString richStrings = new HSSFRichTextString(text);
        //通过索引指定哪些文字需要什么颜色
        richStrings.applyFont(0, titles.length(), black);
        richStrings.applyFont(titles.length(), text.length(), red);
        cellTitle.setCellValue(richStrings);


        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 27));

        String[] headersLast = {"", "", "", "", "永久", "临时", "工程用地", "公司节点", "政府交地", "设计图纸", "", "红线", "勘界图", "租地合同","规划意见", "复垦方案", "复垦保函", "林地报告", "基本农田", "林地批复", "临时用地批复", "定界放桩", "实物复核", "评估", "合同兑付", "围闭情况", "进场施工", "工作进展", "存在问题", "解决措施"};

        HSSFRow rowtwo = sheet.createRow(1);
        HSSFRow rowThree = sheet.createRow(2);
        rowtwo.setHeight((short)600);
        rowThree.setHeight((short)600);
        HSSFCell cellHeader;
        HSSFCell cellHeaderLast;
        for (int i = 0; i < headers.length; i++) {
            cellHeader = rowtwo.createCell(i);
            cellHeader.setCellStyle(style3);
            cellHeader.setCellValue(new HSSFRichTextString(headers[i]));

            cellHeaderLast = rowThree.createCell(i);
            cellHeaderLast.setCellStyle(style3);
            cellHeaderLast.setCellValue(new HSSFRichTextString(headersLast[i]));
//			if(headers[i].equals("总序")){
//				sheet.addMergedRegion(new CellRangeAddress(1, 2, 0, 0));
//			}else if(headers[i].equals("序号")){
//				sheet.addMergedRegion(new CellRangeAddress(1, 2, 1, 1));
//			}else
            if (headers[i].equals("总序")) {
                sheet.addMergedRegion(new CellRangeAddress(1, 2, 0, 0));
            } else  if (headers[i].equals("分序")) {
                sheet.addMergedRegion(new CellRangeAddress(1, 2, 1, 1));
            } else if (headers[i].equals("地区")) {
                sheet.addMergedRegion(new CellRangeAddress(1, 2, 2, 2));
            } else if (headers[i].equals("施工编号（标段）所属地块")) {
                sheet.addMergedRegion(new CellRangeAddress(1, 2, 3, 3));
            } else if (headers[i].equals("征地面积(亩)")) {
                sheet.addMergedRegion(new CellRangeAddress(1, 1, 4, 5));
            } else if (headers[i].equals("控制节点")) {
                sheet.addMergedRegion(new CellRangeAddress(1, 1, 6, 9));
            } else if (headers[i].equals("工期(月)")) {
                sheet.addMergedRegion(new CellRangeAddress(1, 2, 10, 10));
//			}else if(headers[i].equals("责任领导")){
//				sheet.addMergedRegion(new CellRangeAddress(1, 2, 10, 10));
//			}else if(headers[i].equals("部门负责人")){
//				sheet.addMergedRegion(new CellRangeAddress(1, 2, 11, 11));
//			}else if(headers[i].equals("责任人")){
//				sheet.addMergedRegion(new CellRangeAddress(1, 2, 12, 12));
            } else if (headers[i].equals("临时用地报批进展")) {
                sheet.addMergedRegion(new CellRangeAddress(1, 1, 11, 19));
            } else if (headers[i].equals("征地交地进展")) {
                sheet.addMergedRegion(new CellRangeAddress(1, 1, 20, 25));
            } else if (headers[i].equals("工作进展、存在问题、解决措施")) {
                sheet.addMergedRegion(new CellRangeAddress(1, 1, 26, 28));
            }
        }

        // 遍历集合数据，产生数据行
        Map map;
        HSSFRichTextString richString;
        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
        Matcher matcher;
        HSSFCell cell;
        Object value;
        String textValue;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String[] hLast = {"hx", "kjt", "zdht","ghyj", "fkfa", "fkbh", "ldbg", "jbnt", "pfwj", "djfz", "swfh", "pg", "htdf", "wbqk", "jcsg"};
        String region = "";
        int rows = 0;
        for (int index = 0; index < list.size(); index++) {
            map = list.get(index);//获取每一行数据
            row = sheet.createRow(index + 3);//创建excel行，从第四行开始
            //row.setHeight((short)600);
            String key = "";
            for (int i = 0; i < columnNames.length; i++) {
                key = columnNames[i];
                cell = row.createCell(i);
                if (Arrays.asList(hLast).contains(key)) {
                    String type = map.get(key + "type") + "";
                    HSSFCellStyle cellStyle = workbook.createCellStyle();
                    // 设置这些样式
                    cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                    cellStyle.setBorderBottom(BorderStyle.THIN);
                    cellStyle.setBorderLeft(BorderStyle.THIN);
                    cellStyle.setBorderRight(BorderStyle.THIN);
                    cellStyle.setBorderTop(BorderStyle.THIN);
                    cellStyle.setAlignment(HorizontalAlignment.CENTER);
                    cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
                    cellStyle.setWrapText(true);
                    cellStyle.setFont(font2);
                    if (type.equals("0")) {
                        cellStyle.setFillForegroundColor((short) 9);
                        cell.setCellStyle(cellStyle);
                    } else if (type.equals("1")) {
                        cellStyle.setFillForegroundColor((short) 10);
                        cell.setCellStyle(cellStyle);
                    } else if (type.equals("2")) {
                        cellStyle.setFillForegroundColor((short) 11);
                        cell.setCellStyle(cellStyle);
                    } else {
                        cell.setCellStyle(style2);
                    }
                } else {
                    cell.setCellStyle(style2);
                }
                try {
                    value = map.get(key);
                    // 判断值的类型后进行强制类型转换
                    textValue = null;
                    if (value instanceof Integer) {
                        cell.setCellValue((Integer) value);
                    } else if (value instanceof Float) {
                        textValue = String.valueOf((Float) value);
                        cell.setCellValue(textValue);
                    } else if (value instanceof Double) {
                        textValue = String.valueOf((Double) value);
                        cell.setCellValue(textValue);
                    } else if (value instanceof Long) {
                        cell.setCellValue((Long) value);
                    }
                    if (value instanceof Date) {
                        textValue = sdf.format((Date) value);
                    } else {
                        // 其它数据类型都当作字符串简单处理
                        if (value != null) {
                            textValue = value.toString();
                        }
                    }
                    if (textValue != null) {
                        matcher = p.matcher(textValue);
                        if (matcher.matches()) {
                            // 是数字当作double处理
                            cell.setCellValue(Double.parseDouble(textValue));
                        } else {
                            richString = new HSSFRichTextString(textValue);
                            cell.setCellValue(richString);
                        }
                    }
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } finally {
                    // 清理资源
                }
//				if(key.equals("pfwj")){
//					sheet.addMergedRegion(new CellRangeAddress(index+3, index+3, i, i+1));
//				}else if(key.equals("jcsg")){
//					sheet.addMergedRegion(new CellRangeAddress(index+3, index+3, i, i+1));
//				}
                if (StringUtils.equals(key, "tbname")) {
                    if (StringUtils.isBlank(region)) {
                        region = map.get(key) + "";
                        rows++;
                    } else if (StringUtils.equals(region, map.get(key) + "")) {
                        rows++;
                    } else if (!StringUtils.equals(region, map.get(key) + "")) {
                        sheet.addMergedRegion(new CellRangeAddress(index - rows + 3, index + 2, i, i));
                        rows = 1;
                        region = map.get(key) + "";
                    }
                    if ((list.size() - index) == 1) {
                        sheet.addMergedRegion(new CellRangeAddress(index - rows + 4, index + 3, i, i));
                    }
                }
//                if (StringUtils.equals(key, "land_order")) {
//                    if (StringUtils.isBlank(landOrder)) {
//                        landOrder = map.get(key) + "";
//                        landRows++;
//                    } else if (StringUtils.equals(landOrder, map.get(key) + "")) {
//                        landRows++;
//                    } else if (!StringUtils.equals(landOrder, map.get(key) + "")) {
//                        sheet.addMergedRegion(new CellRangeAddress(index - landRows + 3, index + 2, i, i));
//                        landRows = 1;
//                        landOrder = map.get(key) + "";
//                    }
//                    if ((list.size() - index) == 1) {
//                        sheet.addMergedRegion(new CellRangeAddress(index - landRows + 4, index + 3, i, i));
//                    }
//                }
//                if (StringUtils.equals(key, "sgbh")) {
//                    if (StringUtils.isBlank(sgbh)) {
//                        sgbh = map.get(key) + "";
//                        sgbhRows++;
//                    } else if (StringUtils.equals(sgbh, map.get(key) + "")) {
//                        sgbhRows++;
//                    } else if (!StringUtils.equals(sgbh, map.get(key) + "")) {
//                        sheet.addMergedRegion(new CellRangeAddress(index - sgbhRows + 3, index + 2, i, i));
//                        sgbhRows = 1;
//                        sgbh = map.get(key) + "";
//                    }
//                    if ((list.size() - index) == 1) {
//                        sheet.addMergedRegion(new CellRangeAddress(index - sgbhRows + 4, index + 3, i, i));
//                    }
//                }
//				if(key == "tabdiming" && (region.equals("") || region.equals(map.get(key)))){
//					rows++;
//					region=map.get(key)+"";
//				}else if(key == "tabdiming" ){
//					sheet.addMergedRegion(new CellRangeAddress(index-rows+1, index, i, i));
//					rows=0;
//					region=map.get(key)+"";
//				}
            }
        }
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>
     * 通用Excel导出方法,利用反射机制遍历对象的所有字段，将数据写入Excel文件中 <br>
     * 此方法生成2003版本的excel,文件名后缀：xls <br>
     * </p>
     *
     * @param title   表格标题名
     * @param headers 表格头部标题集合
     * @param list    需要显示的数据集合,集合中一定要放置符合JavaBean风格的类的对象。此方法支持的
     *                JavaBean属性的数据类型有基本数据类型及String,Date
     * @param out     与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     * @param pattern 如果有时间数据，设定输出格式。默认为"yyyy-MM-dd hh:mm:ss"
     */
    @SuppressWarnings({"rawtypes", "resource"})
    public void exportExcel2003(String title, String[] headers, String[] columnNames, List<Map> list, OutputStream out, String pattern) {
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为20个字节
        sheet.setDefaultColumnWidth(20);
        // 生成一个样式
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.index);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        // 生成一个字体
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontName("宋体");
        font.setColor(IndexedColors.WHITE.index);
        font.setFontHeightInPoints((short) 11);
        // 把字体应用到当前的样式
        style.setFont(font);
        // 生成并设置另一个样式
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor(IndexedColors.WHITE.index);
        style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style2.setBorderBottom(BorderStyle.THIN);
        style2.setBorderLeft(BorderStyle.THIN);
        style2.setBorderRight(BorderStyle.THIN);
        style2.setBorderTop(BorderStyle.THIN);
        style2.setAlignment(HorizontalAlignment.CENTER);
        style2.setVerticalAlignment(VerticalAlignment.CENTER);
        // 生成另一个字体
        HSSFFont font2 = workbook.createFont();
        // 把字体应用到当前的样式
        style2.setFont(font2);

        // 产生表格标题行
        HSSFRow row = sheet.createRow(0);
        HSSFCell cellHeader;
        for (int i = 0; i < headers.length; i++) {
            cellHeader = row.createCell(i);
            cellHeader.setCellStyle(style);
            cellHeader.setCellValue(new HSSFRichTextString(headers[i]));
        }

        // 遍历集合数据，产生数据行
        Map map;
        HSSFRichTextString richString;
        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
        Matcher matcher;
        HSSFCell cell;
        Object value;
        String textValue;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        for (int index = 0; index < list.size(); index++) {
            row = sheet.createRow(index + 1);
            map = list.get(index);
            String key = "";
            for (int i = 0; i < columnNames.length; i++) {
                key = columnNames[i];
                cell = row.createCell(i);
                cell.setCellStyle(style2);
                try {
                    value = map.get(key);
                    // 判断值的类型后进行强制类型转换
                    textValue = null;
                    if (value instanceof Integer) {
                        cell.setCellValue((Integer) value);
                    } else if (value instanceof Float) {
                        textValue = String.valueOf((Float) value);
                        cell.setCellValue(textValue);
                    } else if (value instanceof Double) {
                        textValue = String.valueOf((Double) value);
                        cell.setCellValue(textValue);
                    } else if (value instanceof Long) {
                        cell.setCellValue((Long) value);
                    }
                    if (value instanceof Date) {
                        textValue = sdf.format((Date) value);
                    } else {
                        // 其它数据类型都当作字符串简单处理
                        if (value != null) {
                            textValue = value.toString();
                        }
                    }
                    if (textValue != null) {
                        matcher = p.matcher(textValue);
                        if (matcher.matches()) {
                            // 是数字当作double处理
                            cell.setCellValue(Double.parseDouble(textValue));
                        } else {
                            richString = new HSSFRichTextString(textValue);
                            cell.setCellValue(richString);
                        }
                    }
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } finally {
                    // 清理资源
                }
            }
        }
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * <p>
     * 通用Excel导出方法,利用反射机制遍历对象的所有字段，将数据写入Excel文件中 <br>
     * 此方法生成2003版本的excel,文件名后缀：xls <br>
     * </p>
     *
     * @param title   表格标题名
     * @param headers 表格头部标题集合
     * @param list    需要显示的数据集合,集合中一定要放置符合JavaBean风格的类的对象。此方法支持的
     *                JavaBean属性的数据类型有基本数据类型及String,Date
     * @param out     与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     * @param pattern 如果有时间数据，设定输出格式。默认为"yyyy-MM-dd hh:mm:ss"
     */
    @SuppressWarnings({"rawtypes", "resource"})
    public void exportExcelmove(String title, String[] headers, String[] columnNames, List<Map> list, OutputStream out, String pattern) {
        // 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("招标");


        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setVerticalAlignment(VerticalAlignment.CENTER);//垂直
        style.setAlignment(HorizontalAlignment.CENTER);//水平

        // 表头标题样式
        HSSFFont headfont = wb.createFont();
        headfont.setFontName("宋体");
        headfont.setFontHeightInPoints((short) 20);// 字体大小
        HSSFCellStyle headstyle = wb.createCellStyle();
        headstyle.setFont(headfont);
        headstyle.setAlignment(HorizontalAlignment.CENTER);// 左右居中
        headstyle.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中
        headstyle.setLocked(true);

        // 普通单元格样式（中文）
        HSSFFont font2 = wb.createFont();
        font2.setFontName("宋体");
        font2.setFontHeightInPoints((short) 12);
        HSSFCellStyle style2 = wb.createCellStyle();
        style2.setBorderBottom(BorderStyle.THIN); //下边框
        style2.setBorderLeft(BorderStyle.THIN);//左边框
        style2.setBorderTop(BorderStyle.THIN);//上边框
        style2.setBorderRight(BorderStyle.THIN);//右边框
        style2.setFont(font2);
        style2.setAlignment(HorizontalAlignment.CENTER);// 左右居中
        style2.setWrapText(true); // 换行
        style2.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中

        // 普通单元格样式（中文） 无边框
        HSSFCellStyle style3 = wb.createCellStyle();
        style3.setFont(font2);
        style3.setWrapText(true); // 换行
        style3.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中

        // 普通单元格样式（中文） 无边框
        HSSFCellStyle style4 = wb.createCellStyle();
        style4.setFont(font2);
        style4.setWrapText(true); // 换行
        style4.setVerticalAlignment(VerticalAlignment.TOP);// 顶端对齐
        style4.setBorderRight(BorderStyle.THIN);//右边框

        // 下边框
        HSSFCellStyle styleBottom = wb.createCellStyle();
        styleBottom.setBorderBottom(BorderStyle.THIN); //下边框
        // 右边框
        HSSFCellStyle styleRight = wb.createCellStyle();
        styleRight.setBorderRight(BorderStyle.THIN);//右边框
        // 右——下边框
        HSSFCellStyle style_RB = wb.createCellStyle();
        style_RB.setBorderRight(BorderStyle.THIN);//右边框
        style_RB.setBorderBottom(BorderStyle.THIN); //下边框

        // 设置列宽 （第几列，宽度）
        sheet.setColumnWidth(0, 4200);
        sheet.setColumnWidth(1, 4200);
        sheet.setColumnWidth(2, 4200);
        sheet.setColumnWidth(3, 4200);
        sheet.setColumnWidth(4, 4200);
        sheet.setColumnWidth(5, 4200);
        sheet.setColumnWidth(6, 4200);
        sheet.setColumnWidth(7, 4200);
        sheet.setDefaultRowHeight((short) 0x270);//设置行高----貌似没有效果，所以下面每行独立设置行高
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 0));
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 1, 1));
//							 String[] headers = new String[] { " ", "招标范围", "招标范围", "招标组织形式", "招标组织形式", "招标方式",
//							 "招标方式", "不采用招标方式" };//在excel中的第4行每列的参数
        String[] head1 = new String[]{"","初设过渡人口", "完成过渡人口", "初设搬迁人口", "完成搬迁人口", "初设安置人口", "完成安置人口"};//在excel中的第5行每列（合并列）的参数
        String[] headnum0 = new String[]{"0,0,2,3", "0,0,4,5", "0,0,6,7"};//对应excel中的行和列，下表从0开始{"开始行,结束行,开始列,结束列"}
        String[] headnum1 = new String[]{"1,1,2,2", "1,1,3,3", "1,1,4,4"};
          //第一行
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        // 第四行表头列名
        row = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            cell = row.createCell(i);
            row.setHeight((short) 0x270);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(style2);
        }

        //动态合并单元格
        for (int i = 0; i < headnum0.length; i++) {
            String[] temp = headnum0[i].split(",");
            Integer startrow = Integer.parseInt(temp[0]);
            Integer overrow = Integer.parseInt(temp[1]);
            Integer startcol = Integer.parseInt(temp[2]);
            Integer overcol = Integer.parseInt(temp[3]);
            sheet.addMergedRegion(new CellRangeAddress(startrow, overrow,
                    startcol, overcol));
        }


        //设置合并单元格的参数并初始化带边框的表头（这样做可以避免因为合并单元格后有的单元格的边框显示不出来）
        row = sheet.createRow(1);//因为下标从0开始，所以这里表示的是excel中的第五行
        for (int i = 0; i < headers.length; i++) {
            cell = row.createCell(i);
            cell.setCellStyle(style2);//设置excel中第五行的1、8列的边框 ，即合并单元的上下单元格都要添加边框
        }

        for (int j = 0; j < head1.length; j++) {
            cell = row.createCell(j + 1);
            row.setHeight((short) 0x270);
            cell.setCellValue(head1[j]);
            cell.setCellStyle(style2);
        }
        //动态合并单元格
        for (int i = 0; i < headnum1.length; i++) {
            String[] temp = headnum1[i].split(",");
            Integer startrow = Integer.parseInt(temp[0]);
            Integer overrow = Integer.parseInt(temp[1]);
            Integer startcol = Integer.parseInt(temp[2]);
            Integer overcol = Integer.parseInt(temp[3]);
            sheet.addMergedRegion(new CellRangeAddress(startrow, overrow,
                    startcol, overcol));
        }
        Map map;
        HSSFRichTextString richString;
        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
        Matcher matcher;
        Object value;
        String textValue;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        for (int index = 0; index < list.size(); index++) {
            row = sheet.createRow(index + 2);
            map = list.get(index);
            String key = "";
            for (int i = 0; i < columnNames.length; i++) {
                key = columnNames[i];
                cell = row.createCell(i);
                cell.setCellStyle(style2);
                try {
                    value = map.get(key);
                    // 判断值的类型后进行强制类型转换
                    textValue = null;
                    if (value instanceof Integer) {
                        cell.setCellValue((Integer) value);
                    } else if (value instanceof Float) {
                        textValue = String.valueOf((Float) value);
                        cell.setCellValue(textValue);
                    } else if (value instanceof Double) {
                        textValue = String.valueOf((Double) value);
                        cell.setCellValue(textValue);
                    } else if (value instanceof Long) {
                        cell.setCellValue((Long) value);
                    }
                    if (value instanceof Date) {
                        textValue = sdf.format((Date) value);
                    } else {
                        // 其它数据类型都当作字符串简单处理
                        if (value != null) {
                            textValue = value.toString();
                        }
                    }
                    if (textValue != null) {
                        matcher = p.matcher(textValue);
                        if (matcher.matches()) {
                            // 是数字当作double处理
                            cell.setCellValue(Double.parseDouble(textValue));
                        } else {
                            richString = new HSSFRichTextString(textValue);
                            cell.setCellValue(richString);
                        }
                    }
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } finally {
                    // 清理资源
                }
            }
        }
        //表格底部添加下边框
        // 第五步，写入实体数据 实际应用中这些数据从数据库得到，
        try {
            wb.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    @SuppressWarnings({ "rawtypes", "resource" })
	public void exportExcel2003Funds(String title, String[] headers, String[] columnNames, List<Map> list,
			OutputStream out, String pattern) {
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFPalette palette = workbook.getCustomPalette();
		// wb HSSFWorkbook对象
		palette.setColorAtIndex((short) 9, (byte) (255), (byte) (192), (byte) (0));
		palette.setColorAtIndex((short) 10, (byte) (146), (byte) (208), (byte) (80));
		palette.setColorAtIndex((short) 11, (byte) (248), (byte) (77), (byte) (8));
		palette.setColorAtIndex((short) 12, (byte) (255), (byte) (255), (byte) (255));
		palette.setColorAtIndex((short) 13, (byte) (0), (byte) (0), (byte) (0));
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth(30);
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor((short) 12);
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setAlignment(HorizontalAlignment.CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontName("宋体");
		font.setColor((short) 12);
		font.setFontHeightInPoints((short) 12);
		// 把字体应用到当前的样式
		style.setFont(font);
		// 生成一个样式
		HSSFCellStyle style1 = workbook.createCellStyle();
		// 设置这些样式
		style1.setFillForegroundColor((short) 12);
		style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style1.setBorderBottom(BorderStyle.THIN);
		style1.setBorderLeft(BorderStyle.THIN);
		style1.setBorderRight(BorderStyle.THIN);
		style1.setBorderTop(BorderStyle.THIN);
		style1.setAlignment(HorizontalAlignment.CENTER);
		style1.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
		// 生成一个字体
		HSSFFont font1 = workbook.createFont();
		font1.setBold(true);
		font1.setFontName("宋体");
		font1.setColor((short) 13);
		font1.setFontHeightInPoints((short) 36);
		// 把字体应用到当前的样式
		style1.setFont(font1);
		// 生成并设置另一个样式
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor((short) 12);
		style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style2.setBorderBottom(BorderStyle.THIN);
		style2.setBorderLeft(BorderStyle.THIN);
		style2.setBorderRight(BorderStyle.THIN);
		style2.setBorderTop(BorderStyle.THIN);
		style2.setAlignment(HorizontalAlignment.CENTER);
		style2.setVerticalAlignment(VerticalAlignment.CENTER);
		style2.setWrapText(true);
		// 生成另一个字体
		HSSFFont font2 = workbook.createFont();
		font2.setFontHeightInPoints((short) 12);
		// 把字体应用到当前的样式
		style2.setFont(font2);

		// 生成一个样式
		HSSFCellStyle style3 = workbook.createCellStyle();
		// 设置这些样式
		style3.setFillForegroundColor((short) 12);
		style3.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style3.setBorderBottom(BorderStyle.THIN);
		style3.setBorderLeft(BorderStyle.THIN);
		style3.setBorderRight(BorderStyle.THIN);
		style3.setBorderTop(BorderStyle.THIN);
		style3.setAlignment(HorizontalAlignment.CENTER);
		style3.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
		style3.setWrapText(true);
		// 生成一个字体
		HSSFFont font3 = workbook.createFont();
		font3.setBold(true);
		font3.setFontName("宋体");
		font3.setColor((short) 13);
		font3.setFontHeightInPoints((short) 12);
		// 把字体应用到当前的样式
		style3.setFont(font3);

//		Date d = new Date();
//		SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd");
//		String dateNowStr = sdfs.format(d);
		// 产生表格标题行
		HSSFRow row = sheet.createRow(0);
		HSSFCell cellTitle;
		cellTitle = row.createCell(0);
		cellTitle.setCellStyle(style1);
		// 统计日期文字大小
		HSSFFont red = workbook.createFont();
		red.setBold(true);
		red.setFontName("宋体");
		red.setColor((short) 13);
		red.setFontHeightInPoints((short) 20);

		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 27));

		String[] headersLast = { "", "", "", "", "初设(万元)", "动态(万元)", "完成(万元)", "初设(万元)", "动态(万元)", "完成(万元)", "初设(万元)",
				"动态(万元)", "完成(万元)", "初设(万元)", "动态(万元)", "完成(万元)", "初设(万元)", "动态(万元)", "完成(万元)", "初设(万元)", "动态(万元)",
				"完成(万元)" };
		String[] headerTwo = { "", "", "", "", "顺德区", "", "", "南沙区", "", "", "番禺区", "", "", "", "", "", "宝安区", "", "",
				"光明区", "", "" };

		HSSFRow rowone = sheet.createRow(0);
		HSSFRow rowtwo = sheet.createRow(1);
		HSSFRow rowThree = sheet.createRow(2);
		rowone.setHeight((short) 600);
		rowtwo.setHeight((short) 600);
		rowThree.setHeight((short) 600);
		HSSFCell header;
		HSSFCell cellHeader;
		HSSFCell cellHeaderLast;
		HSSFCellStyle cellStyle = workbook.createCellStyle(); 

		cellStyle.setAlignment(HorizontalAlignment.CENTER); 
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		for (int i = 0; i < headers.length; i++) {
			header = rowone.createCell(i);
			header.setCellStyle(cellStyle);
			header.setCellValue(new HSSFRichTextString(headers[i]));
			cellHeader = rowtwo.createCell(i);
			cellHeader.setCellValue(new HSSFRichTextString(headerTwo[i]));
			cellHeaderLast = rowThree.createCell(i);
			cellHeaderLast.setCellStyle(cellStyle);
			cellHeaderLast.setCellValue(new HSSFRichTextString(headersLast[i]));
			if (headers[i].equals("项目")) {
				sheet.addMergedRegion(new CellRangeAddress(0, 2, 0, 0));
			} else if (headers[i].equals("初设概算(万元)")) {
				sheet.addMergedRegion(new CellRangeAddress(0, 2, 1, 1));
			} else if (headers[i].equals("动态概算(万元)")) {
				sheet.addMergedRegion(new CellRangeAddress(0, 2, 2, 2));
			}  else if (headers[i].equals("完成概算(万元)")) {
				sheet.addMergedRegion(new CellRangeAddress(0, 2, 3, 3));
			} else if (headers[i].equals("佛山市")) {
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 4, 6));
				sheet.addMergedRegion(new CellRangeAddress(1, 1, 4, 6));
			} else if (headers[i].equals("广州市")) {
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 7, 12));
				sheet.addMergedRegion(new CellRangeAddress(1, 1, 7, 9));
				sheet.addMergedRegion(new CellRangeAddress(1, 1, 10, 12));
			} else if (headers[i].equals("东莞市")) {
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 13, 15));
				sheet.addMergedRegion(new CellRangeAddress(1, 1, 13, 15));
			} else if (headers[i].equals("深圳市")) {
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 16, 21));
				sheet.addMergedRegion(new CellRangeAddress(1, 1, 16, 18));
				sheet.addMergedRegion(new CellRangeAddress(1, 1, 19, 21));
			}
		}
		

		// 遍历集合数据，产生数据行
		Map map;
		HSSFRichTextString richString;
		Pattern p = Pattern.compile("^//d+(//.//d+)?$");
		Matcher matcher;
		HSSFCell cell;
		Object value;
		String textValue;
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		for (int index = 0; index < list.size(); index++) {
			map = list.get(index);// 获取每一行数据
			row = sheet.createRow(index + 3);// 创建excel行，从第四行开始
			// row.setHeight((short)600);
			String key = "";
			for (int i = 0; i < columnNames.length; i++) {
				key = columnNames[i];
				cell = row.createCell(i);
				try {
					value = map.get(key);
					// 判断值的类型后进行强制类型转换
					textValue = null;
					if (value instanceof Integer) {
						cell.setCellValue((Integer) value);
					} else if (value instanceof Float) {
						textValue = String.valueOf((Float) value);
						cell.setCellValue(textValue);
					} else if (value instanceof Double) {
						textValue = String.valueOf((Double) value);
						cell.setCellValue(textValue);
					} else if (value instanceof Long) {
						cell.setCellValue((Long) value);
					}
					if (value instanceof Date) {
						textValue = sdf.format((Date) value);
					} else {
						// 其它数据类型都当作字符串简单处理
						if (value != null) {
							textValue = value.toString();
						}
					}
					if (textValue != null) {
						matcher = p.matcher(textValue);
						if (matcher.matches()) {
							// 是数字当作double处理
							cell.setCellValue(Double.parseDouble(textValue));
						} else {
							richString = new HSSFRichTextString(textValue);
							cell.setCellValue(richString);
						}
					}
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} finally {
				}
			}
		}
		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * <p>
	 * 通用Excel导出方法,利用反射机制遍历对象的所有字段，将数据写入Excel文件中 <br>
	 * 此版本生成2007以上版本的文件 (文件后缀：xlsx)
	 * </p>
	 *
	 * @param title   表格标题名
	 * @param headers 表格头部标题集合
	 * @param dataset 需要显示的数据集合,集合中一定要放置符合JavaBean风格的类的对象。此方法支持的
	 *                JavaBean属性的数据类型有基本数据类型及String,Date
	 * @param out     与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
	 * @param pattern 如果有时间数据，设定输出格式。默认为"yyyy-MM-dd hh:mm:ss"
	 */
	@SuppressWarnings({ "rawtypes", "resource" })
	public void exportExcel2007Funds(String title, String[] headers, String[] columnNames, List<Map> list,
			OutputStream out, String pattern) {
		// 声明一个工作薄
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 生成一个表格
		XSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth(20);
		// 生成一个样式
		XSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(new XSSFColor(java.awt.Color.black));
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setAlignment(HorizontalAlignment.CENTER);
		// 生成一个字体
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontName("宋体");
		font.setColor(new XSSFColor(java.awt.Color.BLACK));
		font.setFontHeightInPoints((short) 11);
		// 把字体应用到当前的样式
		style.setFont(font);
		// 生成并设置另一个样式
		XSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(new XSSFColor(java.awt.Color.WHITE));
		style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style2.setBorderBottom(BorderStyle.THIN);
		style2.setBorderLeft(BorderStyle.THIN);
		style2.setBorderRight(BorderStyle.THIN);
		style2.setBorderTop(BorderStyle.THIN);
		style2.setAlignment(HorizontalAlignment.CENTER);
		style2.setVerticalAlignment(VerticalAlignment.CENTER);
		// 生成另一个字体
		XSSFFont font2 = workbook.createFont();
		// 把字体应用到当前的样式
		style2.setFont(font2);

		String[] headersLast = { "", "", "", "", "初设(万元)", "动态(万元)", "完成(万元)", "初设(万元)", "动态(万元)", "完成(万元)", "初设(万元)",
				"动态(万元)", "完成(万元)", "初设(万元)", "动态(万元)", "完成(万元)", "初设(万元)", "动态(万元)", "完成(万元)", "初设(万元)", "动态(万元)",
				"完成(万元)" };
		String[] headerTwo = { "", "", "", "", "顺德区", "", "", "南沙区", "", "", "番禺区", "", "", "", "", "", "宝安区", "", "",
				"光明区", "", "" };

		// 产生表格标题行
		XSSFRow rowone = sheet.createRow(0);
		XSSFRow rowtwo = sheet.createRow(1);
		XSSFRow rowThree = sheet.createRow(2);
		rowone.setHeight((short) 600);
		rowtwo.setHeight((short) 600);
		rowThree.setHeight((short) 600);
		XSSFCell header;
		XSSFCell cellHeader;
		XSSFCell cellHeaderLast;
		XSSFCellStyle cellStyle = workbook.createCellStyle(); 

		cellStyle.setAlignment(HorizontalAlignment.CENTER); 
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		for (int i = 0; i < headers.length; i++) {
			header = rowone.createCell(i);
			header.setCellStyle(cellStyle);
			header.setCellValue(new XSSFRichTextString(headers[i]));
			cellHeader = rowtwo.createCell(i);
			cellHeader.setCellValue(new XSSFRichTextString(headerTwo[i]));
			cellHeader.setCellStyle(cellStyle);
			cellHeaderLast = rowThree.createCell(i);
			//cellHeaderLast.setCellStyle(style);
			cellHeaderLast.setCellValue(new XSSFRichTextString(headersLast[i]));
			
			if (headers[i].equals("项目")) {
				sheet.addMergedRegion(new CellRangeAddress(0, 2, 0, 0));
			} else if (headers[i].equals("初设概算(万元)")) {
				sheet.addMergedRegion(new CellRangeAddress(0, 2, 1, 1));
			} else if (headers[i].equals("动态概算(万元)")) {
				sheet.addMergedRegion(new CellRangeAddress(0, 2, 2, 2));
			}  else if (headers[i].equals("完成概算(万元)")) {
				sheet.addMergedRegion(new CellRangeAddress(0, 2, 3, 3));
			} else if (headers[i].equals("佛山市")) {
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 4, 6));
				sheet.addMergedRegion(new CellRangeAddress(1, 1, 4, 6));
			} else if (headers[i].equals("广州市")) {
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 7, 12));
				sheet.addMergedRegion(new CellRangeAddress(1, 1, 7, 9));
				sheet.addMergedRegion(new CellRangeAddress(1, 1, 10, 12));
			} else if (headers[i].equals("东莞市")) {
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 13, 15));
				sheet.addMergedRegion(new CellRangeAddress(1, 1, 13, 15));
			} else if (headers[i].equals("深圳市")) {
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 16, 21));
				sheet.addMergedRegion(new CellRangeAddress(1, 1, 16, 18));
				sheet.addMergedRegion(new CellRangeAddress(1, 1, 19, 21));
			}
		}


		// 遍历集合数据，产生数据行
		Map map;
		XSSFRichTextString richString;
		Pattern p = Pattern.compile("^//d+(//.//d+)?$");
		XSSFCell cell;
		Matcher matcher;
		Object value;
		String textValue;
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		for (int index = 0; index < list.size(); index++) {
			rowone = sheet.createRow(index + 3);
			map = list.get(index);
			String key = "";
			for (int i = 0; i < columnNames.length; i++) {
				key = columnNames[i];
				cell = rowone.createCell(i);
				cell.setCellStyle(style2);
				try {
					value = map.get(key);
					// 判断值的类型后进行强制类型转换
					textValue = null;
					if (value instanceof Integer) {
						cell.setCellValue((Integer) value);
					} else if (value instanceof Float) {
						textValue = String.valueOf((Float) value);
						cell.setCellValue(textValue);
					} else if (value instanceof Double) {
						textValue = String.valueOf((Double) value);
						cell.setCellValue(textValue);
					} else if (value instanceof Long) {
						cell.setCellValue((Long) value);
					}
					if (value instanceof Date) {
						textValue = sdf.format((Date) value);
					} else {
						// 其它数据类型都当作字符串简单处理
						if (value != null) {
							textValue = value.toString();
						}
					}
					if (textValue != null) {
						matcher = p.matcher(textValue);
						if (matcher.matches()) {
							// 是数字当作double处理
							cell.setCellValue(Double.parseDouble(textValue));
						} else {
							richString = new XSSFRichTextString(textValue);
							cell.setCellValue(richString);
						}
					}
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} finally {
					// 清理资源
				}
			}
		}
		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

