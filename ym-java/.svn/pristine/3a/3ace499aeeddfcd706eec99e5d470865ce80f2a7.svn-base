package com.lyht.util;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by chenlj
 *2019年3月18日00:15:21 
 *     参考：JML(http://blog.csdn.net/jml1437710575/article/details/52051278)
 * poi 实现 excel 导入 工具类
 */
public class ImportExcelUtil {

    //正则表达式 用于匹配属性的第一个字母
    private static final String REGEX = "[a-zA-Z]";
    //日期转换格式
    public static final String PATTERN = "yyyy-MM-dd hh:mm:ss";

    /**
     * Excel数据转 list
     * @param originUrl Excel表的所在路径
     * @param datePattern 日期格式 如"yyyy-MM-dd hh:mm:ss"
     * @param filedsIndex 模板定义的属性名的行位置
     * @param startRow 从第几行开始
     * @param endRow 到第几行结束 (0表示所有行;正数表示到第几行结束;负数表示到倒数第几行结束)
     * @param clazz 要返回的对象集合的类型
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> List<T> importExcel(String originUrl, String datePattern, int filedsIndex, int startRow, int endRow, Class<T> clazz) throws CustomException,IOException {
        // 判断文件是否存在
        File file = new File(originUrl);
        if (!file.exists()) {
            throw new IOException("文件名为" + file.getName() + "Excel文件不存在！");
        }
        InputStream fis = new FileInputStream(file);

        return doImportExcel(fis, datePattern, filedsIndex, startRow, endRow, clazz);
    }

    /**
     * Excel数据转 list
     * @param inputStream Excel文件输入流
     * @param datePattern 日期格式 如"yyyy-MM-dd hh:mm:ss"
     * @param filedsIndex 模板定义的属性名的行位置
     * @param startRow 从第几行开始
     * @param endRow 到第几行结束 (0表示所有行;正数表示到第几行结束;负数表示到倒数第几行结束)
     * @param clazz 要返回的对象集合的类型
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> List<T> importExcel(InputStream inputStream, String datePattern, int filedsIndex, int startRow, int endRow, Class<T> clazz) throws CustomException {
    	return doImportExcel(inputStream, datePattern, filedsIndex, startRow, endRow, clazz);
    }
    /**
     * Excel数据转 list
     * @param inputStream Excel文件输入流
     * @param datePattern 日期格式 如"yyyy-MM-dd hh:mm:ss"
     * @param filedsIndex 模板定义的属性名的行位置
     * @param startRow 从第几行开始
     * @param endRow 到第几行结束 (0表示所有行;正数表示到第几行结束;负数表示到倒数第几行结束)
     * @param clazz 要返回的对象集合的类型
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> List<T> importLetterExcel(InputStream inputStream, String[] newRow, String datePattern, int filedsIndex, int startRow, int endRow, Class<T> clazz) throws CustomException {
        return doImportExcelTwo(inputStream,newRow, datePattern, filedsIndex, startRow, endRow, clazz);
    }
    /**
     * Excel数据转 list
     * @param inputStream Excel文件输入流
     * @param newRow 列名
     * @param datePattern 日期格式 如"yyyy-MM-dd hh:mm:ss"
     * @param filedsIndex 模板定义的属性名的行位置
     * @param startRow 从第几行开始
     * @param endRow 到第几行结束 (0表示所有行;正数表示到第几行结束;负数表示到倒数第几行结束)
     * @param clazz 要返回的对象集合的类型
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> List<T> importExcelTwo(InputStream inputStream, String[] newRow, String datePattern, int filedsIndex, int startRow, int endRow, Class<T> clazz) throws CustomException {
        return doImportExcelTwo(inputStream, newRow, datePattern, filedsIndex, startRow, endRow, clazz);
    }
    /**
     * 真正实现
     * @param inputStream
     * @param datePattern
     * @param filedsIndex
     * @param startRow
     * @param endRow
     * @param clazz
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> List<T> doImportExcel(InputStream inputStream, String datePattern, int filedsIndex, int startRow, int endRow, Class<T> clazz) throws CustomException {
        Workbook wb;
        Sheet sheet;
        Row filedsRow = null;
        List<Row> rowList = new ArrayList<Row>();
        try {
            // 去读Excel
            // HSSFWorkbook wb = new HSSFWorkbook(fis);
            // 使用workbook 支持2003/2007
            wb = WorkbookFactory.create(inputStream);
            sheet = wb.getSheetAt(0);
            // 获取最后行号
            // 获取属性列字段

            int lastRowNum = sheet.getLastRowNum();
            int rowLength = lastRowNum;
            if (endRow > 0) {
                rowLength = endRow;
            } else if (endRow < 0) {
                rowLength = lastRowNum + endRow;
            }

            // 获取属性列字段
            filedsRow = sheet.getRow(filedsIndex);
            // 循环读取
            Row row;
            for (int i = startRow; i <= rowLength; i++) {
                    row = sheet.getRow(i);
                    rowList.add(row);
            }

        } catch (Exception e) {
            throw new CustomException("上传文件为空！");
        }
        
        return returnObjectList(getCheckColumns(sheet),datePattern, filedsRow, rowList, clazz);
    }

    /**
     * 实现表头列名替换
     * @param inputStream
     * @param newRow
     * @param datePattern
     * @param filedsIndex
     * @param startRow
     * @param endRow
     * @param clazz
     * @param <T>
     * @return
     * @throws CustomException
     */
    public static <T> List<T> doImportExcelTwo(InputStream inputStream, String[] newRow, String datePattern, int filedsIndex, int startRow, int endRow, Class<T> clazz) throws CustomException {
        Workbook wb;
        Sheet sheet;
        Row filedsRow = null;
        List<Row> rowList = new ArrayList<Row>();
        try {
            // 去读Excel
            // HSSFWorkbook wb = new HSSFWorkbook(fis);
            // 使用workbook 支持2003/2007
            wb = WorkbookFactory.create(inputStream);
            sheet = wb.getSheetAt(0);
            // 获取最后行号
            // 获取属性列字段

            int lastRowNum = sheet.getLastRowNum();
            int rowLength = lastRowNum;
            if (endRow > 0) {
                rowLength = endRow;
            } else if (endRow < 0) {
                rowLength = lastRowNum + endRow;
            }

            // 获取属性列字段
            filedsRow = sheet.getRow(filedsIndex);
            for(int j=0;j<newRow.length;j++){
                filedsRow.createCell(j).setCellValue(newRow[j]);
            }
            // 循环读取
            Row row;
            for (int i = startRow; i <= rowLength; i++) {
                row = sheet.getRow(i);
                rowList.add(row);
            }

        } catch (Exception e) {
            throw new CustomException("上传文件为空！");
        }

        return returnObjectList(getCheckColumns(sheet),datePattern, filedsRow, rowList, clazz);
    }

    /*
     * 获取必填字段战争
     */
	private static List<String> getCheckColumns(Sheet sheet) {
		List<String> list = new ArrayList<String>();
		Row fieldsRow = sheet.getRow(0);
		Row checkColumnsRow = sheet.getRow(1);
		int cellLength = fieldsRow.getLastCellNum();
		String value;
    	for (int j = 0; j < cellLength; j++) {
    		value = getCellValue(checkColumnsRow.getCell(j));
            if (CommonUtil.isEmpty(value)) {
            	list.add("非必填");
            }else{
            	list.add("必填");
            }
        }
		return list;
	}

	/**
     * 功能:返回指定的对象集合
     */
    private static <T>List<T> returnObjectList(List<String> checkColumns,String datePattern, Row filedsRow, List<Row> rowList,Class<T> clazz) throws CustomException{
        List<T> objectList=new ArrayList<T>();
        try {
            T obj;
            String attribute;
            String value;
            int rowIndex =3;
            for (Row row : rowList) {
            	rowIndex++;
                obj = clazz.newInstance();
                int lastCellNum = filedsRow.getLastCellNum();
                for (int j = 0; j < lastCellNum; j++) {
                    attribute = getCellValue(filedsRow.getCell(j));
                    if (!attribute.equals("")) {
                        value = getCellValue(row.getCell(j));
                        String colors = "FFFFFFFF";
                        XSSFCellStyle cellStyle = null;
                        if(row.getCell(j)!=null){
                            cellStyle = (XSSFCellStyle)row.getCell(j).getCellStyle();
                        }
                        XSSFColor color = null;
                        if(cellStyle!=null){
                            color = cellStyle.getFillForegroundXSSFColor();
                        }
                        if(color!=null){
                            colors = color.getARGBHex();
                        }
                        // 前两位是透明度
                        int r =Integer.parseInt(colors.substring(2, 4),16);
                        int g =Integer.parseInt(colors.substring(4, 6),16);
                        int b =Integer.parseInt(colors.substring(6, 8),16);

//                        String[] array = {"hx","kjt","zdht","fkfa","fkbh","ldbg","jbnt","pfwj1","pfwj2","djfz","swfh","pg","htdf","wbqk","jcsg1","jcsg2"};
//                        boolean flag = Arrays.asList(array).contains("2");
                        if(r==255&&g==192&&b==0){
                            //正在开展
                            value=value+"|0";
                        }
                        if(r==146&&g==208&&b==80){
                            //已完成
                            value=value+"|1";
                        }
                        if(r==248&&g==77&&b==8){
                            //滞后
                            value=value+"|2";
                        }
                        //System.out.println("rgb:r="+r+",g="+g+",b="+b);
//                        if("必填".equals(checkColumns.get(j)) && CommonUtil.isEmpty(value)){
//                        	throw new CustomException("第"+rowIndex+"行，第"+(j+1)+"列  为必填字段，不能为空！");
//                        }
                        try {
                            if(attribute.equals("landOrder")&&!isNumeric(value)){
                                setAttrributeValue(obj, attribute, null, datePattern);
                            }else{
                                if(attribute.equals("gq")&&isNumeric(value)&&!"".equals(value)){
                                    value = Math.round(Double.parseDouble(value))+"";
                                }
                                setAttrributeValue(obj, attribute, value, datePattern);
                            }
						} catch (CustomException e) {
							throw new CustomException("第"+rowIndex+"行，第"+(j+1)+"列 "+e.getMessage());
						}
                        
                    }
                }
                objectList.add(obj);
            }

        } catch (CustomException e) {
        	throw new CustomException(e.getMessage());
        } catch (InstantiationException e1) {
        	throw new CustomException("模板数据导入出错！请联系开发服务商");
		} catch (IllegalAccessException e1) {
			throw new CustomException("模板数据导入出错！请联系开发服务商");
		}

        return objectList;
    }

    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        if(str.indexOf(".")>0){//判断是否有小数点
            if(str.indexOf(".")==str.lastIndexOf(".") && str.split("\\.").length==2){ //判断是否只有一个小数点
                return pattern.matcher(str.replace(".","")).matches();
            }else {
                return false;
            }
        }else {
            return pattern.matcher(str).matches();
        }
    }
    
    /**
     * 功能:获取单元格的值
     */
    private static String getCellValue(Cell cell) {
        Object result = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case STRING:
                    result = cell.getStringCellValue();
                    break;
                case NUMERIC:
                    result = cell.getNumericCellValue();
                    break;
                case BOOLEAN:
                    result = cell.getBooleanCellValue();
                    break;
                case FORMULA:
                    result = cell.getCellFormula();
                    break;
                case ERROR:
                    result = cell.getErrorCellValue();
                    break;
                case BLANK:
                    break;
                default:
                    break;
            }
        }
        return result.toString();
    }

    /**
     * 功能:给指定对象的指定属性赋值
     * @throws CustomException 
     */
    private static void setAttrributeValue(Object obj,String attribute,String value, String datePattern) throws CustomException {
        //得到该属性的set方法名
        String method_name = convertToMethodName(attribute,obj.getClass(),true);
        Method[] methods = obj.getClass().getMethods();
        for (Method method : methods) {
            /**
             * 因为这里只是调用bean中属性的set方法，属性名称不能重复
             * 所以set方法也不会重复，所以就直接用方法名称去锁定一个方法
             * （注：在java中，锁定一个方法的条件是方法名及参数）
             */
            if(method.getName().equals(method_name)) {
                Class<?>[] parameterC = method.getParameterTypes();
                try {
                    /**如果是(整型,浮点型,布尔型,字节型,时间类型),
                     * 按照各自的规则把value值转换成各自的类型
                     * 否则一律按类型强制转换(比如:String类型)
                     */
                    if(parameterC[0] == int.class || parameterC[0]==java.lang.Integer.class) {
                        if(value != null && value.length() > 0) {
                            value = value.substring(0, value.lastIndexOf("."));
                            method.invoke(obj,Integer.valueOf(value));
                        }

                        break;
                    } else if(parameterC[0] == long.class || parameterC[0]==java.lang.Long.class) {
                        if(value != null && value.length() > 0) {
                            value = value.substring(0, value.lastIndexOf("."));
                            method.invoke(obj,Long.valueOf(value));
                        }
                        break;
                    } else if(parameterC[0] == float.class || parameterC[0]==java.lang.Float.class) {
                        if(value != null && value.length() > 0) {
                            method.invoke(obj, Float.valueOf(value));
                        }
                        break;
                    } else if(parameterC[0] == double.class || parameterC[0]==java.lang.Double.class) {
                        if(value != null && value.length() > 0) {
                            try {
                                method.invoke(obj, Double.valueOf(value));
                            }catch (Exception e){
                               // e.printStackTrace();
                            }
                        }
                        break;
                    } else if(parameterC[0] == byte.class || parameterC[0]==java.lang.Byte.class) {
                        if(value != null && value.length() > 0) {
                            method.invoke(obj, Byte.valueOf(value));
                        }
                        break;
                    } else if(parameterC[0] == boolean.class|| parameterC[0]==java.lang.Boolean.class) {
                        if (value != null && value.length() > 0) {
                            method.invoke(obj, Boolean.valueOf(value));
                        }
                        break;
                    } else if(parameterC[0] == java.util.Date.class) {
                        if(value != null && value.length() > 0) {
                            Calendar calendar = new GregorianCalendar(1900,0,-1);
                            Date d = calendar.getTime();
                            Date date=null;
                            try {
                                date= DateUtils.addDays(d,Double.valueOf(value).intValue());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            method.invoke(obj,date);
                        }

                        break;
                    } else if(parameterC[0] == java.math.BigDecimal.class) {
                        if (value != null && value.length() > 0) {
                            method.invoke(obj, new BigDecimal(value));
                        }
                        break;
                    } else {
                        if (value != null && value.length() > 0) {
                            method.invoke(obj,parameterC[0].cast(value));
                        }
                        break;
                    }
                } catch (IllegalAccessException e) {
                	throw new CustomException("字段名称错误！");
                } catch (InvocationTargetException e) {
                	throw new CustomException("字段名称错误！");
                } catch (Exception e){
                	throw new CustomException("字段导入出错！请联系开发服务商");
                }
            }
        }
    }

    /**
     * 功能:根据属性生成对应的set/get方法
     */
    private static String convertToMethodName(String attribute,Class<?> objClass,boolean isSet) {
        /** 通过正则表达式来匹配第一个字符 **/
        Pattern p = Pattern.compile(REGEX);
        Matcher m = p.matcher(attribute);
        StringBuilder sb = new StringBuilder();
        /** 如果是set方法名称 **/
        if(isSet) {
            sb.append("set");
        } else {
            /** get方法名称 **/
            try {
                Field attributeField = objClass.getDeclaredField(attribute);
                /** 如果类型为boolean **/
                if(attributeField.getType() == boolean.class||attributeField.getType() == Boolean.class) {
                    sb.append("is");
                } else {
                    sb.append("get");
                }
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        /** 针对以下划线开头的属性 **/
        if(attribute.charAt(0)!='_' && m.find()) {
            sb.append(m.replaceFirst(m.group().toUpperCase()));
        } else {
            sb.append(attribute);
        }
        return sb.toString();
    }


    /**
     *
     * @param inStr
     * @return
     * @throws Exception
     */
    public static Workbook getWorkbook(InputStream inStr) throws Exception {
        Workbook wb = null;
        wb = WorkbookFactory.create(inStr);
        return wb;
    }
}
