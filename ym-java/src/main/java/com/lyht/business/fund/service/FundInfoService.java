package com.lyht.business.fund.service;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.fund.dao.FundInfoDao;
import com.lyht.business.fund.entity.FundInfo;
import com.lyht.util.Randomizer;

@Service
public class FundInfoService {

	@Autowired
	private FundInfoDao dao;

	@SuppressWarnings("unchecked")
	public LyhtResultBody<List<FundInfo>> page(LyhtPageVO lyhtPageVO){
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<FundInfo> page = dao.findAll(pageable);
		String jsonString = JSON.toJSONString(page.getContent());
		List<FundInfo> list = (List<FundInfo>)JSON.parse(jsonString);
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(list,pageVO);
	}
	
	//导入-------------------------------------------------------------------------------------
	/**
	 * 设置公共值
	 */
	public FundInfo setCommonValue(Row row) {
		FundInfo info = new FundInfo();
		info.setNm(Randomizer.generCode(10));
		row.getCell(0).setCellType(CellType.STRING);
		if (StringUtils.isNotBlank(row.getCell(0).getStringCellValue())) {// 序号
			info.setSerialNumber(row.getCell(0).getStringCellValue());
		}
		row.getCell(1).setCellType(CellType.STRING);
		if (StringUtils.isNotBlank(row.getCell(1).getStringCellValue())) {// 项目
			info.setProjectNm(row.getCell(1).getStringCellValue());
		}
		row.getCell(2).setCellType(CellType.STRING);
		if (StringUtils.isNotBlank(row.getCell(2).getStringCellValue())) {// 单位
			info.setUnit(row.getCell(2).getStringCellValue());
		}
		row.getCell(3).setCellType(CellType.STRING);
		if (StringUtils.isNotBlank(row.getCell(3).getStringCellValue())) {// 单价
			String priceStr = row.getCell(3).getStringCellValue();
			Double price = 0.0;
			if (!priceStr.equals("0")&&priceStr!="0") {
				if (priceStr.indexOf("%") == -1) {
					price = Double.parseDouble(priceStr);
				} else {
					NumberFormat nf = NumberFormat.getPercentInstance();
					Number m = null;
					try {
						m = nf.parse(priceStr);
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} // 将百分数转换成Number类型
					price = m.doubleValue();// 通过调用nubmer类默认方法直接转换成double
				}
			}
			info.setUnitPrice(price);
		}
		row.getCell(4).setCellType(CellType.STRING);
		if (StringUtils.isNotBlank(row.getCell(4).getStringCellValue())) {//概算指标
			info.setTarget(Double.parseDouble(row.getCell(4).getStringCellValue()));
		}
		row.getCell(22).setCellType(CellType.STRING);
		if (StringUtils.isNotBlank(row.getCell(22).getStringCellValue())) {// 备注
			info.setRemark(row.getCell(22).getStringCellValue());
		}
		row.getCell(23).setCellType(CellType.STRING);
		if (StringUtils.isNotBlank(row.getCell(23).getStringCellValue())) {// 类别
			info.setType(row.getCell(23).getStringCellValue());
		}
		return info;
	}

	/**
	 * 各类型数据设置值
	 */
	public List<FundInfo> setValue(Sheet sheet) {
		List<FundInfo> list = new ArrayList<FundInfo>();
		// 获取每行中的字段
		for (int i = 3; i <= 531; i++) {//设置开始行 结束行
			Row row = sheet.getRow(i); // 获取行
			FundInfo info = setCommonValue(row);// 临时用地
			info.setArea("临时用地");
			row.getCell(6).setCellType(CellType.STRING);
			if (StringUtils.isNotBlank(row.getCell(6).getStringCellValue())
					&& row.getCell(6).getStringCellValue() != "0") {// 指标数量
				info.setQuantity(Double.parseDouble(row.getCell(6).getStringCellValue()));
			}
			row.getCell(15).setCellType(CellType.STRING);
			if (StringUtils.isNotBlank(row.getCell(15).getStringCellValue())
					&& row.getCell(15).getStringCellValue() != "0") {// 费用
				info.setTotal(Double.parseDouble(row.getCell(15).getStringCellValue()));
			}
			list.add(info);
			FundInfo info01 = setCommonValue(row);// 永久用地
			info01.setArea("永久用地");
			row.getCell(7).setCellType(CellType.STRING);
			if (StringUtils.isNotBlank(row.getCell(7).getStringCellValue())
					&& row.getCell(7).getStringCellValue() != "0") {// 指标数量
				info01.setQuantity(Double.parseDouble(row.getCell(7).getStringCellValue()));
			}
			row.getCell(16).setCellType(CellType.STRING);
			if (StringUtils.isNotBlank(row.getCell(16).getStringCellValue())
					&& row.getCell(16).getStringCellValue() != "0") {// 费用
				info01.setTotal(Double.parseDouble(row.getCell(16).getStringCellValue()));
			}
			list.add(info01);
			FundInfo info02 = setCommonValue(row);// 水库淹没区
			info02.setArea("水库淹没区");
			row.getCell(9).setCellType(CellType.STRING);
			if (StringUtils.isNotBlank(row.getCell(9).getStringCellValue())
					&& row.getCell(9).getStringCellValue() != "0") {// 指标数量
				info02.setQuantity(Double.parseDouble(row.getCell(9).getStringCellValue()));
			}
			row.getCell(18).setCellType(CellType.STRING);
			if (StringUtils.isNotBlank(row.getCell(18).getStringCellValue())
					&& row.getCell(18).getStringCellValue() != "0") {// 费用
				info02.setTotal(Double.parseDouble(row.getCell(18).getStringCellValue()));
			}
			list.add(info02);
			FundInfo info03 = setCommonValue(row);// 水库影响区
			info03.setArea("水库影响区");
			row.getCell(10).setCellType(CellType.STRING);
			if (StringUtils.isNotBlank(row.getCell(10).getStringCellValue())
					&& row.getCell(10).getStringCellValue() != "0") {// 指标数量
				info03.setQuantity(Double.parseDouble(row.getCell(10).getStringCellValue()));
			}
			row.getCell(19).setCellType(CellType.STRING);
			if (StringUtils.isNotBlank(row.getCell(19).getStringCellValue())
					&& row.getCell(19).getStringCellValue() != "0") {// 费用
				info03.setTotal(Double.parseDouble(row.getCell(19).getStringCellValue()));
			}
			list.add(info03);
			FundInfo info04 = setCommonValue(row);// 垫高防护区
			info04.setArea("垫高防护区");
			row.getCell(11).setCellType(CellType.STRING);
			if (StringUtils.isNotBlank(row.getCell(11).getStringCellValue())
					&& row.getCell(11).getStringCellValue() != "0") {// 指标数量
				info04.setQuantity(Double.parseDouble(row.getCell(11).getStringCellValue()));
			}
			row.getCell(20).setCellType(CellType.STRING);
			if (StringUtils.isNotBlank(row.getCell(20).getStringCellValue())
					&& row.getCell(20).getStringCellValue() != "0") {// 费用
				info04.setTotal(Double.parseDouble(row.getCell(20).getStringCellValue()));
			}
			list.add(info04);
			FundInfo info05 = setCommonValue(row);// 集镇新址占地区
			info05.setArea("集镇新址占地区");
			row.getCell(12).setCellType(CellType.STRING);
			if (StringUtils.isNotBlank(row.getCell(12).getStringCellValue())
					&& row.getCell(12).getStringCellValue() != "0") {// 指标数量
				info05.setQuantity(Double.parseDouble(row.getCell(12).getStringCellValue()));
			}
			row.getCell(21).setCellType(CellType.STRING);
			if (StringUtils.isNotBlank(row.getCell(21).getStringCellValue())
					&& row.getCell(21).getStringCellValue() != "0") {// 费用
				info05.setTotal(Double.parseDouble(row.getCell(21).getStringCellValue()));
			}
			list.add(info05);
		}
		List<FundInfo> infoList = dao.saveAll(list);
		return infoList;
	}
}
