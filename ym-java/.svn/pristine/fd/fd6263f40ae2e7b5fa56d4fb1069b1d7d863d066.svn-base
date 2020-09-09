package com.lyht.util;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;

import com.alibaba.fastjson.JSON;

import lombok.Data;

/**
 * excel sheet(导出的前提提交，必须有模板，不支持直接生成excel)
 * 
 * @author hxl
 *
 */
@Data
public class ExcelSheet {
	private String sheetName;// excel中sheet的名称
	private String sheetNewName;// 要修改的sheet名称

	private boolean isRemove = false;// 是否删除该sheet（默认不删除）
	private boolean isBorder = false;// 单元格是否有边框（仅作用于表格）
	private short fontSize = 12;// 字体大小（仅作用于表格）
	private short fontColor = Font.COLOR_NORMAL;// 字体颜色（仅作用于表格）

	private Map<String, Object> replaceContent;// 当前sheet替换的内容

	private List<List<Object>> listData;// 当前sheet表格渲染数据
	private int startRowIndex;// sheet开始行下标（默认从0开始）
	private int startColIndex;// sheet开始列下标（默认从0开始）

	private List<CellRangeAddress> mergerList;// 合并的单元格(CellRangeAddress（起始行号, 终止行号, 起始列号, 终止列号）)

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
