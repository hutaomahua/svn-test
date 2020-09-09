package com.lyht.business.abm.production.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.business.abm.production.dao.ProduceProcessDao;
import com.lyht.business.abm.production.dao.ProductionAuditDao;
import com.lyht.business.abm.production.dao.ProductionDao;
import com.lyht.business.abm.production.entity.ProduceProcess;
import com.lyht.business.abm.production.entity.ProductionAuditEntity;
import com.lyht.business.abm.production.utils.ExcelOperationUtil;
import com.lyht.business.abm.removal.dao.AbmFamilyDao;
import com.lyht.business.abm.removal.dao.AbmOwnerDao;
import com.lyht.business.abm.removal.entity.AbmFamilyEntity;
import com.lyht.util.CommonUtil;
import com.lyht.util.FileDownUtil;

/**
 * @version: V1.0
 * @author: hjs
 * @className: ProductionAuditService
 * @packageName: com.lyht.business.abm.production.service
 * @description: （类作用）
 * @data: 2020年02月27日 17:42
 * @see []
 **/
@Service
public class ProductionAuditService {

	private static final String FILE_URL = "D:\\Server\\uploads\\tuoba\\word\\";// 导出模板路径

	@Autowired
	private ProductionAuditDao productionAuditDao;

	@Autowired
	private ProduceProcessDao produceProcessDao;

	@Autowired
	private ProductionService productionService;

	@Autowired
	private ProductionDao productionDao;

	@Autowired
	private AbmOwnerDao ownerDao;

	@Autowired
	private AbmFamilyDao familyDao;

	@Transactional
	public void save(ProductionAuditEntity productionAuditEntity) {
		productionAuditDao.save(productionAuditEntity);
	}

	/**
	 * 查询在人口表中是界定中的 并且在流程表里面不是处理中状态 改变在流程中通过或者拒绝的数据
	 */
	@Transactional
	public void examine() {
		try {
			List<Map<String, Object>> incomplete = productionAuditDao.findIncomplete();
			for (Map<String, Object> map : incomplete) {
				String status = map.get("status") != null ? map.get("status").toString() : null,
						ownerNm = map.get("ownerNm") != null ? map.get("ownerNm").toString() : null,
						place = map.get("place") != null ? map.get("place").toString() : null;
				if ("Approved".equals(status)) { // 流程通过了
					Map<String, Object> processData = findDataByProcessId(map.get("processId").toString());
					Map<String, Object> processOperateData = (Map<String, Object>) processData
							.get("processOperateData");
					List<Map<String, Object>> personnelData = (List<Map<String, Object>>) processOperateData
							.get("personnelData");
					for (Map<String, Object> personnelDatum : personnelData) {
						String nm = personnelDatum.get("nm").toString();
						String isProduce = personnelDatum.get("isProduce").toString();
						productionDao.updateUserisProduce(isProduce, nm);
					}
					productionDao.updateUser("2", ownerNm);
					productionDao.updateOwner("2", ownerNm);
					if (StringUtils.isNotBlank(place)) {
						String[] spilt = place.split(",");
						String producePlace = "";
						for (int i = 2; i < spilt.length; i++) {
							producePlace += spilt[i];
						}
						ownerDao.updateProducePlace(producePlace, ownerNm);
						familyDao.updateProducePlace(producePlace, ownerNm);
					}
//					ownerDao.updateProducePlace(place, ownerNm);
//					familyDao.updateProducePlace(place, ownerNm);
				} else if ("Rejected".equals(status)) { // 流程被拒绝了
					productionDao.updateUser("1", ownerNm);
					productionDao.updateOwner("1", ownerNm);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void export(String ownerNm, HttpServletResponse response) {
		ProduceProcess produceProcess = produceProcessDao.queryByOwnerNm(ownerNm);
		String jsonData = "";
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> sTableColumns = new ArrayList<Map<String, Object>>();
		if (produceProcess != null) {
			jsonData = produceProcess.getJsonData();
			map = (Map<String, Object>) JSON.parse(jsonData);
			sTableColumns = (List<Map<String, Object>>) map.get("popuData");
		}

		if (ownerNm == null && ownerNm.trim().length() == 0) {
			return;
		}
		Map<String, Object> userData = productionService.findUserData(ownerNm);
		try {
			Map<String, Object> userDatas = (Map<String, Object>) userData.get("basicsData");
			Map<String, Object> basicsData = new HashMap<String, Object>(userDatas);
			InputStream inputStream = new FileInputStream(FILE_URL + "mb.xlsx");
			XSSFWorkbook xssfSheets = new XSSFWorkbook(inputStream);
			XSSFSheet sheet = xssfSheets.getSheetAt(0);
			// 插入头部第一行数据
			sheet.getRow(0).getCell(0).setCellValue("托巴水电站生产安置人口界定、安置方式及去向到户确认表");
			// 插入第二行行政区
			sheet.getRow(1).getCell(0).setCellValue(basicsData.get("county") + "-" + basicsData.get("country") + "-"
					+ basicsData.get("village") + "村-" + basicsData.get("group") + "组");
			// 建设征地范围
			sheet.getRow(1).getCell(4)
					.setCellValue(basicsData.get("scopeName") != null ? basicsData.get("scopeName").toString() : "不详");
			// 家庭人口数
			sheet.getRow(1).getCell(6)
					.setCellValue(basicsData.get("zrk") != null ? basicsData.get("zrk").toString() : "0");
			// 农业人口数
			sheet.getRow(1).getCell(10)
					.setCellValue(basicsData.get("nNum") != null ? basicsData.get("nNum").toString() : "0");
			// 非农人口数
			sheet.getRow(1).getCell(8)
					.setCellValue(basicsData.get("fyNum") != null ? basicsData.get("fyNum").toString() : "0");
			// 是否搬迁户
			sheet.getRow(3).getCell(9)
					.setCellValue(basicsData.get("isMove") != null ? basicsData.get("isMove").toString() : "不详");
			List<Map<String, Object>> landData = (List<Map<String, Object>>) userData.get("landData");
			Double stArea = 0.0, stzsArea = 0.0, // 水田面积
					htcyArea = 0.0, htcyzsArea = 0.0, // 核桃成园地面积
					hdArea = 0.0, hdzsArea = 0.0, // 旱地面积
					htyyArae = 0.0, htyyzsArea = 0.0, // 核桃幼园地面积
					dpArea = 0.0, dpzsArea = 0.0, // 陡坡地面积
					ptcyArea = 0.0, ptcyzsArea = 0.0, // 葡萄成园地面积
					blcyArae = 0.0, blcyzsArea = 0.0, // 板栗成园地面积
					ptyyArea = 0.0, ptyyzsArea = 0.0, // 葡萄幼园地面积
					blyyArea = 0.0, blyyzsArea = 0.0, // 板栗幼园地面积
					otherArea = 0.0, otherzsArea = 0.0; // 其他园地面积
			if (landData != null) {
				Double sunzsArea = 0.00; // 征收耕地当量
				for (Map<String, Object> landDatum : landData) {
					Object name = landDatum.get("name");
					BigDecimal zsArea = (BigDecimal) landDatum.get("zsArea");
					BigDecimal area = (BigDecimal) landDatum.get("area");
//					String zsArea = landDatum.get("zsArea") != null
//							? "" + ((BigDecimal) landDatum.get("zsArea")).doubleValue()
//							: "0",
//							area = landDatum.get("area") != null
//									? "" + ((BigDecimal) landDatum.get("area")).doubleValue()
//									: "0";
//					sunzsArea += landDatum.get("zsArea") != null ? ((BigDecimal) landDatum.get("zsArea")).doubleValue()
//							: 0;
					if ("水田".equals(name)) {
						stArea += zsArea.doubleValue();
						stzsArea += area.doubleValue();
						sunzsArea = sunzsArea + zsArea.doubleValue();
					} else if ("核桃成园地".equals(name)) {
						htcyArea += area.doubleValue();
						htcyzsArea += zsArea.doubleValue();
						sunzsArea = sunzsArea + zsArea.doubleValue();
					} else if ("旱地".equals(name)) {
						hdArea += area.doubleValue();
						hdzsArea += zsArea.doubleValue();
						sunzsArea = sunzsArea + zsArea.doubleValue();
					} else if ("核桃幼园地".equals(name)) {
						htyyArae += area.doubleValue();
						htyyzsArea += zsArea.doubleValue();
						sunzsArea = sunzsArea + zsArea.doubleValue();
					} else if ("陡坡地".equals(name)) {
						dpArea += area.doubleValue();
						dpzsArea += zsArea.doubleValue();
						sunzsArea = sunzsArea + zsArea.doubleValue();
					} else if ("葡萄成园地".equals(name)) {
						ptcyArea += area.doubleValue();
						ptcyzsArea += zsArea.doubleValue();
						sunzsArea = sunzsArea + zsArea.doubleValue();
					} else if ("板栗成园地".equals(name)) {
						blcyArae += area.doubleValue();
						blcyzsArea += zsArea.doubleValue();
						sunzsArea = sunzsArea + zsArea.doubleValue();
					} else if ("葡萄幼园地".equals(name)) {
						ptyyArea += area.doubleValue();
						ptyyzsArea += zsArea.doubleValue();
						sunzsArea = sunzsArea + zsArea.doubleValue();
					} else if ("板栗幼园地".equals(name)) {
						blyyArea += area.doubleValue();
						blyyzsArea += zsArea.doubleValue();
						sunzsArea = sunzsArea + zsArea.doubleValue();
					} else if ("其他园地".equals(name)) {
						otherArea += area.doubleValue();
						otherzsArea += zsArea.doubleValue();
						sunzsArea = sunzsArea + zsArea.doubleValue();
					}
				}
				// 征收耕地当量
				sheet.getRow(3).getCell(7).setCellValue("" + String.format("%.2f", sunzsArea));
			}
			// 水田
			sheet.getRow(3).getCell(2).setCellValue(stArea);
			sheet.getRow(3).getCell(3).setCellValue(stzsArea);

			// 核桃成园地
			sheet.getRow(3).getCell(5).setCellValue(htcyArea);
			sheet.getRow(3).getCell(6).setCellValue(htcyzsArea);

			// 旱地
			sheet.getRow(4).getCell(2).setCellValue(hdArea);
			sheet.getRow(4).getCell(3).setCellValue(hdzsArea);

			// 核桃幼园地
			sheet.getRow(4).getCell(5).setCellValue(htyyArae);
			sheet.getRow(4).getCell(6).setCellValue(htyyzsArea);

			// 陡坡地
			sheet.getRow(5).getCell(2).setCellValue(dpArea);
			sheet.getRow(5).getCell(3).setCellValue(dpzsArea);

			// 葡萄成园地
			sheet.getRow(5).getCell(5).setCellValue(ptcyArea);
			sheet.getRow(5).getCell(6).setCellValue(ptcyzsArea);

			// 板栗成园地
			sheet.getRow(6).getCell(2).setCellValue(blcyArae);
			sheet.getRow(6).getCell(3).setCellValue(blcyzsArea);

			// 葡萄幼园地
			sheet.getRow(6).getCell(5).setCellValue(ptyyArea);
			sheet.getRow(6).getCell(6).setCellValue(ptyyzsArea);

			// 板栗幼园地
			sheet.getRow(7).getCell(2).setCellValue(blyyArea);
			sheet.getRow(7).getCell(3).setCellValue(blyyzsArea);

			// 其他园地
			sheet.getRow(7).getCell(5).setCellValue(otherArea);
			sheet.getRow(7).getCell(6).setCellValue(otherzsArea);

			// 是否搬迁户
			sheet.getRow(3).getCell(8).setCellValue("是");

			List<Map<String, Object>> data = (List<Map<String, Object>>) userData.get("personnelData");
			String jsonString = JSON.toJSONString(data);
			List<Map<String, Object>> personnelData = (List<Map<String, Object>>) JSON.parse(jsonString);
			// 家庭成员（获取成员数据条数）
			int size = personnelData.size();
			int hdrk = 0;
			// sTableColumns
			for (int i = 0; i < sTableColumns.size(); i++) {
				String nm = data.get(i).get("nm") + "";//
				String odlIsProduce = data.get(i).get("isProduce") + "";// 原是否为生产安置人口
				String nm2 = sTableColumns.get(i).get("nm") + "";//
				String newIsProduce = sTableColumns.get(i).get("isProduce") + "";

				Map<String, Object> hashMap = personnelData.get(i);
				if (hashMap != null) {
					if (nm.equals(nm2)) {
						hashMap.put("isProduce", newIsProduce);
					}
				}
				if (odlIsProduce.equals("1")) {
					hdrk++;
				}
				for (Map<String, Object> map2 : personnelData) {
					System.out.println(JSON.toJSONString(map2));
				}
			}
			String relocationName = map.get("place") != null ? map.get("place").toString() : null;
			basicsData.put("placeName", relocationName);

			/*
			 * 下移行数 shiftRows(int startRow, int endRow, int n, boolean copyRowHeight,
			 * boolean resetOriginalRowHeight) startRow 要移动的开始行 endRow 要移动的结束行 n 要移动的行数
			 * n为负数代表向上移动 copyRowHeight 是否复制行高 resetOriginalRowHeigth 是否充值行高
			 */

			for (int i = 0; i < size; i++) {// 创建行
				XSSFRow row = createRow(sheet, 9 + i);
				XSSFCell cell = row.createCell((short) 0);
				CellStyle cellStyle = xssfSheets.createCellStyle();// 添加背景色
				cellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
				cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				cellStyle.setBorderBottom(BorderStyle.THIN);
				cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
				cellStyle.setBorderLeft(BorderStyle.THIN);
				cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
				cellStyle.setBorderRight(BorderStyle.THIN);
				cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
				cellStyle.setBorderTop(BorderStyle.THIN);
				cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
				for (int k = 0; k < 11; k++) {
					cell = row.createCell(k);
					cell.setCellStyle(cellStyle);
				}

				row.setHeight((short) 600);
			}
			// 下移行数
			// sheet.shiftRows(10, sheet.getLastRowNum(), size-1, true, false);
			// 修改合并单元格大小 家庭成员
			if (size > 0) {
				CellRangeAddress cra = new CellRangeAddress(8, 8 + size, 0, 0);
				sheet.addMergedRegion(cra);
			}
			if (size > 0) {
				sheet.addMergedRegion(new CellRangeAddress(10 + size - 1, 10 + size - 1, 1, 4));// 本组人均耕地当量（亩）（前）
				sheet.addMergedRegion(new CellRangeAddress(10 + size - 1, 10 + size - 1, 5, 10));// 本组人均耕地当量（亩）（后）
				sheet.addMergedRegion(new CellRangeAddress(11 + size - 1, 12 + size - 1, 0, 0));// 生产安置人口数
				sheet.addMergedRegion(new CellRangeAddress(11 + size - 1, 11 + size - 1, 1, 4));// 测算生产安置人口（人）（上）
				sheet.addMergedRegion(new CellRangeAddress(12 + size - 1, 12 + size - 1, 1, 4));// 测算生产安置人口（人）（下）
				sheet.addMergedRegion(new CellRangeAddress(11 + size - 1, 11 + size - 1, 6, 10));// 核定生产安置人口（人）（上）
				sheet.addMergedRegion(new CellRangeAddress(12 + size - 1, 12 + size - 1, 6, 10));// 核定生产安置人口（人）（下）
				sheet.addMergedRegion(new CellRangeAddress(13 + size - 1, 13 + size - 1, 1, 10));// 自谋职业
				sheet.addMergedRegion(new CellRangeAddress(14 + size - 1, 15 + size - 1, 0, 0));// 安置去向
				sheet.addMergedRegion(new CellRangeAddress(14 + size - 1, 14 + size - 1, 1, 2));// 县（上）
				sheet.addMergedRegion(new CellRangeAddress(14 + size - 1, 14 + size - 1, 3, 4));// 乡（上）
				sheet.addMergedRegion(new CellRangeAddress(14 + size - 1, 14 + size - 1, 5, 6));// 村（上）
				sheet.addMergedRegion(new CellRangeAddress(14 + size - 1, 14 + size - 1, 7, 8));// 组（上）
				sheet.addMergedRegion(new CellRangeAddress(14 + size - 1, 14 + size - 1, 9, 10));// 分类（上）
				sheet.addMergedRegion(new CellRangeAddress(15 + size - 1, 15 + size - 1, 1, 2));// 县（下）
				sheet.addMergedRegion(new CellRangeAddress(15 + size - 1, 15 + size - 1, 3, 4));// 乡（下）
				sheet.addMergedRegion(new CellRangeAddress(15 + size - 1, 15 + size - 1, 5, 6));// 村（下）
				sheet.addMergedRegion(new CellRangeAddress(15 + size - 1, 15 + size - 1, 7, 8));// 组（下）
				sheet.addMergedRegion(new CellRangeAddress(15 + size - 1, 15 + size - 1, 9, 10));// 分类（下）3
				sheet.addMergedRegion(new CellRangeAddress(16 + size - 1, 16 + size - 1, 1, 10));// 分类（下）
			} else {
				sheet.addMergedRegion(new CellRangeAddress(10 + size, 10 + size, 1, 4));// 本组人均耕地当量（亩）（前）
				sheet.addMergedRegion(new CellRangeAddress(10 + size, 10 + size, 5, 10));// 本组人均耕地当量（亩）（后）
				sheet.addMergedRegion(new CellRangeAddress(11 + size, 12 + size, 0, 0));// 生产安置人口数
				sheet.addMergedRegion(new CellRangeAddress(11 + size, 11 + size, 1, 4));// 测算生产安置人口（人）（上）
				sheet.addMergedRegion(new CellRangeAddress(12 + size, 12 + size, 1, 4));// 测算生产安置人口（人）（下）
				sheet.addMergedRegion(new CellRangeAddress(11 + size, 11 + size, 6, 10));// 核定生产安置人口（人）（上）
				sheet.addMergedRegion(new CellRangeAddress(12 + size, 12 + size, 6, 10));// 核定生产安置人口（人）（下）

				sheet.addMergedRegion(new CellRangeAddress(13 + size, 13 + size, 1, 10));// 自谋职业
				sheet.addMergedRegion(new CellRangeAddress(14 + size, 15 + size, 0, 0));// 安置去向
				sheet.addMergedRegion(new CellRangeAddress(14 + size, 14 + size, 1, 2));// 县（上）
				sheet.addMergedRegion(new CellRangeAddress(14 + size, 14 + size, 3, 4));// 乡（上）
				sheet.addMergedRegion(new CellRangeAddress(14 + size, 14 + size, 5, 6));// 村（上）
				sheet.addMergedRegion(new CellRangeAddress(14 + size, 14 + size, 7, 8));// 组（上）
				sheet.addMergedRegion(new CellRangeAddress(14 + size, 14 + size, 9, 10));// 分类（上）
				sheet.addMergedRegion(new CellRangeAddress(15 + size, 15 + size, 1, 2));// 县（下）
				sheet.addMergedRegion(new CellRangeAddress(15 + size, 15 + size, 3, 4));// 乡（下）
				sheet.addMergedRegion(new CellRangeAddress(15 + size, 15 + size, 5, 6));// 村（下）
				sheet.addMergedRegion(new CellRangeAddress(15 + size, 15 + size, 7, 8));// 组（下）
				sheet.addMergedRegion(new CellRangeAddress(15 + size, 15 + size, 9, 10));// 分类（下）
				sheet.addMergedRegion(new CellRangeAddress(16 + size, 16 + size, 1, 10));// 分类（下）
			}
//            for (int i = 0; i < size; i++) {//身份证号列合并 指定生产安置人口
//            	 sheet.addMergedRegion(new CellRangeAddress(9+i,9+i, 7, 8));
//            	 sheet.addMergedRegion(new CellRangeAddress(9+i,9+i, 9, 10));
//			}
//            sheet.addMergedRegion(new CellRangeAddress(9,9+size, 9, 10));
			int i = 0;
			// 插入家庭成员数据
			for (Map<String, Object> personnelDatum : personnelData) {
				if (i < size - 1) {
					ExcelOperationUtil.copyRows(9, 9, 10 + i, sheet);
				}
				// 循环数据第二条开始copy上一行格式(row依次递增)
				// 姓名
				sheet.getRow(9 + i).getCell(1).setCellValue(
						personnelDatum.get("name") != null ? personnelDatum.get("name").toString() : "不详");
				// 户口类型
				sheet.getRow(9 + i).getCell(2).setCellValue(
						personnelDatum.get("hkType") != null ? personnelDatum.get("hkType").toString() : "不详");
				// 与户主关系
				sheet.getRow(9 + i).getCell(3)
						.setCellValue(personnelDatum.get("gx") != null ? personnelDatum.get("gx").toString() : "不详");
				// 名族
				sheet.getRow(9 + i).getCell(4)
						.setCellValue(personnelDatum.get("mz") != null ? personnelDatum.get("mz").toString() : "不详");
				// 性别
				sheet.getRow(9 + i).getCell(5)
						.setCellValue(personnelDatum.get("xb") != null ? personnelDatum.get("xb").toString() : "不详");
				// 年龄
				sheet.getRow(9 + i).getCell(6)
						.setCellValue(personnelDatum.get("age") != null ? personnelDatum.get("age").toString() : "不详");
				// 身份证号码
				sheet.getRow(9 + i).getCell(7).setCellValue(
						personnelDatum.get("idCard") != null ? personnelDatum.get("idCard").toString() : "不详");
				String isProduce = "否";
				if (Integer.parseInt(personnelDatum.get("isProduce") + "") == 1
						&& personnelDatum.get("hkType").equals("农业")) {
					isProduce = "是";
				}
				// 指定生产安置人口
				sheet.getRow(9 + i).getCell(9).setCellValue(isProduce);
				i++;
			}
			Integer people = getCountPeople(ownerNm, basicsData.get("region") + "");
			String nNum = basicsData.get("nNum") + "";
			Integer agricultural = Integer.parseInt(nNum);
			if (people > agricultural) {// 不能大于家庭农业人口
				people = agricultural;
			}
			String placeType = basicsData.get("isLY") + "";
			boolean isLY = Boolean.getBoolean(placeType);
			if (isLY) {// 如果是搬迁到兰永的 则 测算人口为 家庭农业人口
				if (people < agricultural) {
					people = agricultural;
				}
			}
			// 测算标准（本地人均耕地当量）
			String region = basicsData.get("region") + "";
			Double equivalent = productionDao.getCultivatedLandEquivalentGroup(region);
			if (CommonUtil.isEmpty(equivalent)) {
				equivalent = 0.00;
			}
			sheet.getRow(10 + size).getCell(5).setCellValue(equivalent);
			// 11加成员数据大小
			// sheet.getRow(9 + size).getCell(5).setCellValue("11-4");

			// 测算安置人口
			Integer rk = people != null ? people : 0;

			sheet.getRow(12 + size).getCell(1).setCellValue(rk);
			// 是否搬迁安置
			sheet.getRow(12 + size).getCell(5)
					.setCellValue(basicsData.get("isMove") != null ? basicsData.get("isMove").toString() : "不详");
			// 核定安置人口
			if ((hdrk + "").equals(basicsData.get("hdrk") + "")) {
				sheet.getRow(12 + size).getCell(6)
						.setCellValue(basicsData.get("hdrk") != null ? basicsData.get("hdrk").toString() : "0");
			} else {
				sheet.getRow(12 + size).getCell(6).setCellValue(hdrk);
			}
			// 安置方式
			sheet.getRow(13 + size).getCell(1)
					.setCellValue(basicsData.get("placeType") != null ? basicsData.get("placeType").toString() : "不详");
			// 安置去向
			String azqx = basicsData.get("placeName") != null ? basicsData.get("placeName").toString() : "不详";
			Object obasicsData = basicsData.get("placeName");
			if (obasicsData != null) {
				String placeName = obasicsData.toString();
				String[] split = placeName.split(",");
				if (split.length > 4) {
					for (int x = 0; x < split.length - 2; x++) {
						int j = x + 2;
						String temps = split[j] != null ? split[j] : "不详";
						// 县 --乡 --村 --组
						sheet.getRow(15 + size).getCell(x * 2 + 1).setCellValue(temps);
					}
				} else if (split.length == 4) {
					for (int x = 0; x < split.length; x++) {
						String temps = split[x] != null ? split[x] : "不详";
						// 县 --乡 --村 --组
						sheet.getRow(15 + size).getCell(x * 2 + 1).setCellValue(temps);
					}
				} else {
					sheet.getRow(15 + size).getCell(0 * 2 + 1).setCellValue("不详");
				}
//				if (split.length > 0) {
//					for (int x = 0; x < split.length; x++) {
//						String temps = split[x] != null ? split[x] : "不详";
//						// 县 --乡 --村 --组
//						sheet.getRow(15 + size).getCell(x * 2 + 1).setCellValue(temps);
//					}
//				} else {
//					
//				}
			} else {
				String bx = "不详";
				sheet.getRow(15 + size).getCell(1).setCellValue(bx);
				sheet.getRow(15 + size).getCell(3).setCellValue(bx);
				sheet.getRow(15 + size).getCell(5).setCellValue(bx);
				sheet.getRow(15 + size).getCell(7).setCellValue(bx);
			}
			for (int j = 0; j < size; j++) {
				sheet.addMergedRegion(new CellRangeAddress(9 + j, 9 + j, 7, 8));// 身份证
				sheet.addMergedRegion(new CellRangeAddress(9 + j, 9 + j, 9, 10));// 指定生产安置人口
			}
			// 分类
			sheet.getRow(15 + size).getCell(9)
					.setCellValue(basicsData.get("placeType") != null ? basicsData.get("placeType").toString() : "不详");
			/* 未对多线程情况进行处理 */
			// 设置文件名称 xxx乡xxx村xxx组-朱友华 生产安置人口界定表
			String title = "";
			if (StringUtils.isBlank(region)) {
				title = basicsData.get("name") + " 生产安置人口界定表";
			} else {
				title = basicsData.get("country") + "-" + basicsData.get("village") + "村-" + basicsData.get("group")
						+ "组" + "-" + basicsData.get("name") + " 生产安置人口界定表";
			}
			String name = title + ".xlsx";
			String download = FILE_URL + name;
			OutputStream outputStream = new FileOutputStream(download);
			xssfSheets.write(outputStream);
			FileDownUtil.getFile(download, name, response);
			File file = new File(download);
			if (file.exists()) {
				file.delete();
			}
		} catch (

		IOException e) {
			e.printStackTrace();
		}
	}

	public Integer getCountPeople(String ownerNm, String region) {
		List<Map<String, Object>> list = productionDao.getLandInfoByOwnerNm(ownerNm);
		// 测算生产安置人口
		List<Map<String, Object>> coefficients = productionDao.getConvertCoefficient();
		Integer people = 0;
		// 获取小组人均根底当量
		Double equivalen = productionDao.getCultivatedLandEquivalentGroup(region);
		if (!CommonUtil.isEmpty(equivalen)) {
			BigDecimal landEquivalen = new BigDecimal(equivalen).setScale(2, BigDecimal.ROUND_HALF_UP);
			BigDecimal man = new BigDecimal(0);// 人口数量
			/**
			 * 测算生产安置人口
			 */
			for (Map<String, Object> m : list) {// 遍历土地
				if ((m.get("scope") + "").equals("CE81C0FA94") || (m.get("scope") + "") == "CE81C0FA94") {// 永久占地
																											// POW((1+0.013),2.33)
					for (Map<String, Object> map : coefficients) {// 折算系数
						if ((m.get("type") + "").equals(map.get("landType") + "")) {// 类型相同
							BigDecimal area = new BigDecimal(Double.parseDouble(m.get("area") + "")).setScale(2,
									BigDecimal.ROUND_HALF_UP);// 取到面积
							BigDecimal coefficient = new BigDecimal(Double.parseDouble(map.get("coefficient") + ""));// 取到折算系数
							BigDecimal convert = area.multiply(coefficient).setScale(2, BigDecimal.ROUND_HALF_UP);// 折算后面积
							BigDecimal landEquivalenFamily = convert.divide(landEquivalen, 2, BigDecimal.ROUND_HALF_UP);// 计算家庭人均根底当量
																														// 并保留两位小数
							BigDecimal powCount = new BigDecimal(Math.pow((1 + 0.013), 2.33));
							// 根据征地范围 及 系数算出最终结果
							BigDecimal count = landEquivalenFamily.multiply(powCount).setScale(2,
									BigDecimal.ROUND_HALF_UP);// 得到最终人数值
							man = man.add(count);// 累加保存
						}
					}
				} else if ((m.get("scope") + "").equals("D8D5AAD9DC") || (m.get("scope") + "") == "D8D5AAD9DC") {// 水库淹没POW((1+0.013),8)
					for (Map<String, Object> map : coefficients) {// 折算系数
						if ((m.get("type") + "").equals(map.get("landType") + "")) {// 类型相同
							BigDecimal area = new BigDecimal(Double.parseDouble(m.get("area") + "")).setScale(2,
									BigDecimal.ROUND_HALF_UP);// 取到面积
							BigDecimal coefficient = new BigDecimal(Double.parseDouble(map.get("coefficient") + ""));// 取到折算系数
							BigDecimal convert = area.multiply(coefficient).setScale(2, BigDecimal.ROUND_HALF_UP);// 折算后面积
							BigDecimal landEquivalenFamily = convert.divide(landEquivalen, 2, BigDecimal.ROUND_HALF_UP);// 计算家庭人均根底当量
																														// 并保留两位小数
							BigDecimal powCount = new BigDecimal(Math.pow((1 + 0.013), 8)).setScale(2,
									BigDecimal.ROUND_HALF_UP);
							// 根据征地范围 及 系数算出最终结果
							BigDecimal count = landEquivalenFamily.multiply(powCount).setScale(2,
									BigDecimal.ROUND_HALF_UP);// 得到最终人数值
							man = man.add(count);// 累加保存
						}
					}
				} else if ((m.get("scope") + "") == "348F5B68BA" || (m.get("scope") + "").equals("348F5B68BA")) {// 集镇新址占地区POW((1+0.013),8)
					for (Map<String, Object> map : coefficients) {// 折算系数
						if ((m.get("type") + "").equals(map.get("landType") + "")) {// 类型相同
							BigDecimal area = new BigDecimal(Double.parseDouble(m.get("area") + "")).setScale(2,
									BigDecimal.ROUND_HALF_UP);// 取到面积
							BigDecimal coefficient = new BigDecimal(Double.parseDouble(map.get("coefficient") + ""));// 取到折算系数
							BigDecimal convert = area.multiply(coefficient).setScale(2, BigDecimal.ROUND_HALF_UP);// 折算后面积
							BigDecimal landEquivalenFamily = convert.divide(landEquivalen, 2, BigDecimal.ROUND_HALF_UP);// 计算家庭人均根底当量
																														// 并保留两位小数
							BigDecimal powCount = new BigDecimal(Math.pow((1 + 0.013), 8)).setScale(2,
									BigDecimal.ROUND_HALF_UP);
							// 根据征地范围 及 系数算出最终结果
							BigDecimal count = landEquivalenFamily.multiply(powCount).setScale(2,
									BigDecimal.ROUND_HALF_UP);// 得到最终人数值
							man = man.add(count);// 累加保存
						}
					}
				} else if ((m.get("scope") + "") == "E78D14E7BE" || (m.get("scope") + "").equals("E78D14E7BE")) {// 水库影响POW((1+0.013),8)
					for (Map<String, Object> map : coefficients) {// 折算系数
						if ((m.get("type") + "").equals(map.get("landType") + "")) {// 类型相同
							BigDecimal area = new BigDecimal(Double.parseDouble(m.get("area") + "")).setScale(2,
									BigDecimal.ROUND_HALF_UP);// 取到面积
							BigDecimal coefficient = new BigDecimal(Double.parseDouble(map.get("coefficient") + ""));// 取到折算系数
							BigDecimal convert = area.multiply(coefficient).setScale(2, BigDecimal.ROUND_HALF_UP);// 折算后面积
							BigDecimal landEquivalenFamily = convert.divide(landEquivalen, 2, BigDecimal.ROUND_HALF_UP);// 计算家庭人均根底当量
																														// 并保留两位小数
							BigDecimal powCount = new BigDecimal(Math.pow((1 + 0.013), 8)).setScale(2,
									BigDecimal.ROUND_HALF_UP);
							// 根据征地范围 及 系数算出最终结果
							BigDecimal count = landEquivalenFamily.multiply(powCount).setScale(2,
									BigDecimal.ROUND_HALF_UP);// 得到最终人数值
							man = man.add(count);// 累加保存
						}
					}
				}
			}
			Double manCount = man.doubleValue();
			people = manCount.intValue();// 最后取整
		}
		return people;
	}

	/**
	 * 新建行
	 * 
	 * @param sheet
	 * @param rowIndex
	 * @return
	 */
	public XSSFRow createRow(XSSFSheet sheet, Integer rowIndex) {
		XSSFRow row = null;
		if (sheet.getRow(rowIndex) != null) {
			int lastRowNo = sheet.getLastRowNum();
			sheet.shiftRows(rowIndex, lastRowNo, 1);
		}
		row = sheet.createRow(rowIndex);
		return row;
	}

	/**
	 * 创建要出入的行中单元格
	 * 
	 * @param row
	 * @return
	 */
	private XSSFCell createCell(XSSFRow row) {
		XSSFCell cell = row.createCell((short) 0);
		cell.setCellValue(999999);
		for (int i = 0; i < 11; i++) {
			row.createCell(i);
		}
		return cell;
	}

	/**
	 *
	 * @param processId
	 * @return
	 */
	public Map<String, Object> findDataByProcessId(String processId) {
		if (processId == null || processId.trim().length() == 0) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		try {
			Map<String, Object> dataByProcessId = productionAuditDao.findDataByProcessId(processId);
			if (dataByProcessId == null || dataByProcessId.size() == 0) {
				throw new LyhtRuntimeException("未找到该流程相关的数据！");
			}
			String sData = dataByProcessId.get("processOperateData").toString();
			JSONObject jsonObject = (JSONObject) JSON.parse(sData);
			String tempData = jsonObject.get("data") != null ? jsonObject.get("data").toString() : "{}";
			// 进行参数转换
			Map<String, Object> map = JSON.parseObject(tempData, Map.class);
			Map<String, Object> rData = new HashMap<>();
			rData.putAll(dataByProcessId);
			rData.put("processOperateData", map);
			return rData;
		} catch (LyhtRuntimeException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new LyhtRuntimeException(LyhtExceptionEnums.SERVER_ERROR);
		}
	}

	/**
	 * 重置
	 */
	public boolean reset(String ownerNm) {
		Integer count = productionAuditDao.deleteByOwnerNm(ownerNm);
		Integer row = ownerDao.resetState(ownerNm);
		familyDao.resetState(ownerNm);
		if (count > 0 && row > 0) {
			return true;
		}
		return false;
	}

	public void export01(String ownerNm, HttpServletResponse response) {
		ProduceProcess produceProcess = produceProcessDao.queryByOwnerNm(ownerNm);
		List<Map<String, Object>> sTableColumns = null;
		Map<String, Object> map = null;
		if (produceProcess != null) {
			String jsonData = produceProcess.getJsonData();
			map = (Map<String, Object>) JSON.parse(jsonData);
			sTableColumns = (List<Map<String, Object>>) map.get("popuData");
		}

		Double count = 0.00;// 测算标准
		if (ownerNm == null && ownerNm.trim().length() == 0) {
			return;
		}
		Map<String, Object> userData = productionService.findUserData(ownerNm);
		if (userData == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		try {
			Map<String, Object> userDatas = (Map<String, Object>) userData.get("basicsData");
			Map<String, Object> basicsData = new HashMap<String, Object>(userDatas);
			InputStream inputStream = new FileInputStream(FILE_URL + "逐年补偿人口到户核定表.xlsx");
			XSSFWorkbook xssfSheets = new XSSFWorkbook(inputStream);
			XSSFSheet sheet = xssfSheets.getSheetAt(0);
			// 插入头部第一行数据
			sheet.getRow(0).getCell(0 + 1).setCellValue("托巴水电站逐年补偿安置人口分户界定表");
			// 插入第二行行政区
			sheet.getRow(1).getCell(0).setCellValue(basicsData.get("county") + "-" + basicsData.get("country") + "-"
					+ basicsData.get("village") + "村-" + basicsData.get("group") + "组");
			// 建设征地范围
			sheet.getRow(1).getCell(6)
					.setCellValue(basicsData.get("scopeName") != null ? basicsData.get("scopeName").toString() : "不详");
			// 家庭人口数
			sheet.getRow(1).getCell(7)
					.setCellValue(basicsData.get("zrk") != null ? "家庭人口数：" + basicsData.get("zrk").toString() : "0");
			// 农业人口数
			sheet.getRow(1).getCell(8)
					.setCellValue(basicsData.get("nNum") != null ? "农业人口数：" + basicsData.get("nNum").toString() : "0");
			// 非农人口数
			sheet.getRow(1).getCell(9).setCellValue(
					basicsData.get("fyNum") != null ? "非农业人口数：" + basicsData.get("fyNum").toString() : "0");
			// 是否搬迁户
			sheet.getRow(3).getCell(9)
					.setCellValue(basicsData.get("isMove") != null ? basicsData.get("isMove").toString() : "不详");
			List<Map<String, Object>> landData = (List<Map<String, Object>>) userData.get("landData");
			String stArea = "0", stzsArea = "0", // 水田面积
					htcyArea = "0", htcyzsArea = "0", // 核桃成园地面积
					hdArea = "0", hdzsArea = "0", // 旱地面积
					htyyArae = "0", htyyzsArea = "0", // 核桃幼园地面积
					dpArea = "0", dpzsArea = "0", // 陡坡地面积
					ptcyArea = "0", ptcyzsArea = "0", // 葡萄成园地面积
					blcyArae = "0", blcyzsArea = "0", // 板栗成园地面积
					ptyyArea = "0", ptyyzsArea = "0", // 葡萄幼园地面积
					blyyArea = "0", blyyzsArea = "0", // 板栗幼园地面积
					otherArea = "0", otherzsArea = "0"; // 其他园地面积
			if (landData != null) {
				double sunzsArea = 0; // 征收耕地当量
				for (Map<String, Object> landDatum : landData) {
					Object name = landDatum.get("name");
					String zsArea = landDatum.get("zsArea") != null
							? "" + ((BigDecimal) landDatum.get("zsArea")).doubleValue()
							: "0",
							area = landDatum.get("area") != null
									? "" + ((BigDecimal) landDatum.get("area")).doubleValue()
									: "0";
					if (!name.equals("轮闲地")) {
						sunzsArea += landDatum.get("zsArea") != null
								? ((BigDecimal) landDatum.get("zsArea")).doubleValue()
								: 0;
					}
					if ("水田".equals(name)) {
						stArea = zsArea;
						stzsArea = area;
					} else if ("核桃成园地".equals(name)) {
						htcyArea = area;
						htcyzsArea = zsArea;
					} else if ("旱地".equals(name)) {
						hdArea = area;
						hdzsArea = zsArea;
					} else if ("核桃幼园地".equals(name)) {
						htcyArea = area;
						htcyzsArea = zsArea;
					} else if ("陡坡地".equals(name)) {
						dpArea = area;
						dpzsArea = zsArea;
					} else if ("葡萄成园地".equals(name)) {
						ptcyArea = area;
						ptcyzsArea = zsArea;
					} else if ("板栗成园地".equals(name)) {
						blcyArae = area;
						blcyzsArea = zsArea;
					} else if ("葡萄幼园地".equals(name)) {
						ptyyArea = area;
						ptyyzsArea = zsArea;
					} else if ("板栗幼园地".equals(name)) {
						blyyArea = area;
						blyyzsArea = zsArea;
					} else if ("其他园地".equals(name)) {
						otherArea = area;
						otherzsArea = zsArea;
					}
				}
				// 征收耕地当量
				sheet.getRow(3).getCell(7 + 1).setCellValue("" + String.format("%.2f", sunzsArea));
				Double temp = sunzsArea / 1.042;
				count = temp;
			}
			// 水田
			sheet.getRow(3).getCell(2 + 1).setCellValue(stArea);
			sheet.getRow(3).getCell(3 + 1).setCellValue(stzsArea);

			// 核桃成园地
			sheet.getRow(3).getCell(5 + 1).setCellValue(htcyArea);
			sheet.getRow(3).getCell(6 + 1).setCellValue(htcyzsArea);

			// 旱地
			sheet.getRow(4).getCell(2 + 1).setCellValue(hdArea);
			sheet.getRow(4).getCell(3 + 1).setCellValue(hdzsArea);

			// 核桃幼园地
			sheet.getRow(4).getCell(5 + 1).setCellValue(htyyArae);
			sheet.getRow(4).getCell(6 + 1).setCellValue(htyyzsArea);

			// 陡坡地
			sheet.getRow(5).getCell(2 + 1).setCellValue(dpArea);
			sheet.getRow(5).getCell(3 + 1).setCellValue(dpzsArea);

			// 葡萄成园地
			sheet.getRow(5).getCell(5 + 1).setCellValue(ptcyArea);
			sheet.getRow(5).getCell(6 + 1).setCellValue(ptcyzsArea);

			// 板栗成园地
			sheet.getRow(6).getCell(2 + 1).setCellValue(blcyArae);
			sheet.getRow(6).getCell(3 + 1).setCellValue(blcyzsArea);

			// 葡萄幼园地
			sheet.getRow(6).getCell(5 + 1).setCellValue(ptyyArea);
			sheet.getRow(6).getCell(6 + 1).setCellValue(ptyyzsArea);

			// 板栗幼园地
			sheet.getRow(7).getCell(2 + 1).setCellValue(blyyArea);
			sheet.getRow(7).getCell(3 + 1).setCellValue(blyyzsArea);

			// 其他园地
			sheet.getRow(7).getCell(5 + 1).setCellValue(otherArea);
			sheet.getRow(7).getCell(6 + 1).setCellValue(otherzsArea);

			// 是否搬迁户
			sheet.getRow(3).getCell(8 + 1).setCellValue("是");

			List<Map<String, Object>> data = (List<Map<String, Object>>) userData.get("personnelData");
			String jsonString = JSON.toJSONString(data);
			List<Map<String, Object>> personnelData = (List<Map<String, Object>>) JSON.parse(jsonString);
			// 家庭成员（获取成员数据条数）
			int size = personnelData.size();
			if (size == 0) {
				throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
			}
			// sTableColumns
			if (produceProcess != null) {// 已界定
				for (int i = 0; i < sTableColumns.size(); i++) {
					String nm = data.get(i).get("nm") + "";//
					String odlIsProduce = data.get(i).get("isProduce") + "";// 原是否为生产安置人口
					String nm2 = sTableColumns.get(i).get("nm") + "";//
					String newIsProduce = sTableColumns.get(i).get("isProduce") + "";

					Map<String, Object> hashMap = personnelData.get(i);
					if (hashMap != null) {
						if (nm.equals(nm2)) {
							hashMap.put("isProduce", newIsProduce);
						}
					}
				}
			}

			/*
			 * 下移行数 shiftRows(int startRow, int endRow, int n, boolean copyRowHeight,
			 * boolean resetOriginalRowHeight) startRow 要移动的开始行 endRow 要移动的结束行 n 要移动的行数
			 * n为负数代表向上移动 copyRowHeight 是否复制行高 resetOriginalRowHeigth 是否充值行高
			 */

			for (int i = 0; i < size; i++) {// 创建行
				XSSFRow row = createRow(sheet, 9 + i);
				XSSFCell cell = row.createCell((short) 0);
				CellStyle cellStyle = xssfSheets.createCellStyle();
				cellStyle.setBorderBottom(BorderStyle.THIN);
				cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
				cellStyle.setBorderLeft(BorderStyle.THIN);
				cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
				cellStyle.setBorderRight(BorderStyle.THIN);
				cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
				cellStyle.setBorderTop(BorderStyle.THIN);
				cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
				for (int k = 0; k < 10; k++) {
					cell = row.createCell(k);
					cell.setCellStyle(cellStyle);
				}
				row.setHeight((short) 600);
				// sheet.addMergedRegion(new CellRangeAddress(9 + i, 9 + i, 8, 9));// 身份证号 合并
			}

			// 修改合并单元格大小 家庭成员
			CellRangeAddress cra = new CellRangeAddress(8, 8 + size, 0, 0);
			CellRangeAddress cra1 = new CellRangeAddress(8, 8 + size, 1, 1);
			sheet.addMergedRegion(cra);
			sheet.addMergedRegion(cra1);
			int i = 0;
			// 插入家庭成员数据
			for (Map<String, Object> personnelDatum : personnelData) {
				if (i < size - 1) {
					ExcelOperationUtil.copyRows(9, 9, 10 + i, sheet);
				}
				// 循环数据第二条开始copy上一行格式(row依次递增)
				// 姓名
				sheet.getRow(9 + i).getCell(1 + 1).setCellValue(
						personnelDatum.get("name") != null ? personnelDatum.get("name").toString() : "不详");
				// 户口类型
				sheet.getRow(9 + i).getCell(2 + 1).setCellValue(
						personnelDatum.get("hkType") != null ? personnelDatum.get("hkType").toString() : "不详");
				// 与户主关系
				sheet.getRow(9 + i).getCell(3 + 1)
						.setCellValue(personnelDatum.get("gx") != null ? personnelDatum.get("gx").toString() : "不详");
				// 名族
				sheet.getRow(9 + i).getCell(4 + 1)
						.setCellValue(personnelDatum.get("mz") != null ? personnelDatum.get("mz").toString() : "不详");
				// 性别
				sheet.getRow(9 + i).getCell(5 + 1)
						.setCellValue(personnelDatum.get("xb") != null ? personnelDatum.get("xb").toString() : "不详");
				// 年龄
				sheet.getRow(9 + i).getCell(6 + 1)
						.setCellValue(personnelDatum.get("age") != null ? personnelDatum.get("age").toString() : "不详");
				// 身份证号码
				sheet.getRow(9 + i).getCell(7 + 1).setCellValue(
						personnelDatum.get("idCard") != null ? personnelDatum.get("idCard").toString() : "不详");
				i++;
			}
			for (int j = 0; j < size; j++) {
				sheet.addMergedRegion(new CellRangeAddress(9 + j, 9 + j, 8, 9));// 身份证
			}
			sheet.addMergedRegion(new CellRangeAddress(9 + size, 9 + size, 2, 9));// 测算标准
			sheet.addMergedRegion(new CellRangeAddress(10 + size, 10 + size + 1, 0, 0));// 四
			sheet.addMergedRegion(new CellRangeAddress(10 + size, 10 + size + 1, 1, 1));// 逐年补偿人口数（人）
			sheet.addMergedRegion(new CellRangeAddress(10 + size, 10 + size, 2, 5));// 测算逐年补偿安置人口
			sheet.addMergedRegion(new CellRangeAddress(10 + size, 10 + size, 7, 9));// 核定逐年补偿安置人口
			sheet.addMergedRegion(new CellRangeAddress(10 + size + 1, 10 + size + 1, 2, 5));// 测算逐年补偿安置人口
			sheet.addMergedRegion(new CellRangeAddress(10 + size + 1, 10 + size + 1, 7, 9));// 核定逐年补偿安置人口
			sheet.addMergedRegion(new CellRangeAddress(12 + size, 14 + size, 0, 9));// 说明
			if(count>0) {
				sheet.getRow(12 + size).getCell(2).setCellValue(String.format("%.2f", count));
			}else {
				sheet.getRow(12 + size).getCell(2).setCellValue(count);
			}
			String place = basicsData.get("isLY") + "";
			Boolean isLY = Boolean.valueOf(place);
			if (isLY) {// 安置点兰永
				sheet.getRow(12 + size).getCell(6).setCellValue("是");

			} else {
				sheet.getRow(12 + size).getCell(6).setCellValue("否");
			}
			sheet.getRow(12 + size).getCell(7).setCellValue(basicsData.get("znbcrk") + "");
			// 设置文件名称 xxx乡xxx村xxx组-xxx 生产安置人口界定表
			String region = basicsData.get("region") + "";
			String title = "";
			if (StringUtils.isNotBlank(region)) {
				title = basicsData.get("country") + "-" + basicsData.get("village") + "村-" + basicsData.get("group")
						+ "组" + "-" + basicsData.get("name") + " 逐年补偿人口到户核定表";
			} else {
				title = basicsData.get("name") + " 逐年补偿人口到户核定表";
			}

			String name = title + ".xlsx";
			String download = FILE_URL + name;
			OutputStream outputStream = new FileOutputStream(download);
			xssfSheets.write(outputStream);
			FileDownUtil.getFile(download, name, response);
			File file = new File(download);
			if (file.exists()) {
				file.delete();
			}
		} catch (

		IOException e) {
			e.printStackTrace();
		}
	}

}
