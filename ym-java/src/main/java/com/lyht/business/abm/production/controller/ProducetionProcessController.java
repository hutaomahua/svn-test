package com.lyht.business.abm.production.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.production.dao.ProductionDao;
import com.lyht.business.abm.production.entity.ProduceProcess;
import com.lyht.business.abm.production.service.ProduceProcessService;
import com.lyht.business.abm.production.vo.ProduceProcessVO;
import com.lyht.business.pub.dao.PubRegionDao;
import com.lyht.business.pub.entity.PubRegionEntity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/production/process")
@Api(value = "生产安置界定确认", tags = "生产安置界定确认")
public class ProducetionProcessController {

	@Autowired
	private ProduceProcessService service;

	@Autowired
	ProductionDao productionDao;
	@Autowired
	private PubRegionDao regionDao;



	@ApiOperation(value = "发起生产安置确认流程", notes = "发起生产安置确认流程")
	@ApiImplicitParams(value = { @ApiImplicitParam(name = "ownerNm", value = "户主NM", paramType = "query"),
			@ApiImplicitParam(name = "remark", value = "备注", paramType = "query", required = false) })
	@PostMapping("/start")
	public LyhtResultBody<ProduceProcess> startProcess(HttpServletRequest request,
			@RequestParam(required = false, value = "file") MultipartFile file, String ownerNm, String remark) {
		ProduceProcess startProcess = service.startProcess(request, file, ownerNm, remark);
		return new LyhtResultBody<>(startProcess);
	}

	@ApiOperation(value = "查询当前流程详情", notes = "查询当前流程详情")
	@ApiImplicitParams(value = { @ApiImplicitParam(name = "ownerNm", value = "户主NM", paramType = "query") })
	@GetMapping("/get")
	public LyhtResultBody<ProduceProcessVO> findByOwnerNm(String ownerNm) {
		ProduceProcessVO findByOwnerNm = service.findByOwnerNm(ownerNm);
		return new LyhtResultBody<>(findByOwnerNm);
	}

	/**
	 * 回调
	 */
	@ApiOperation(value = "回调", notes = "回调")
	@ApiImplicitParams(value = { @ApiImplicitParam(name = "taskId", value = "流程id", paramType = "query") })
	@PostMapping("/callBack")
	public LyhtResultBody<String> callBack(String taskId, Integer type, String senderNm) {
		return service.callBack(taskId, type, senderNm);
	}
	
	@PostMapping("/test")
	@Transactional
	public void test() throws EncryptedDocumentException, IOException {
		// TODO Auto-generated method stub
		File file = new File("D:\\Server\\uploads\\tuoba\\word\\规划报告生产安置人口计算表.xlsx"); // 创建文件对象
		Workbook wb = WorkbookFactory.create(file); // 从文件流中获取Excel工作区对象（WorkBook）
		Sheet sheet = wb.getSheetAt(0); // 从工作区中取得页（Sheet）
		for (int k = 6; k < 127; k++) {
			Map<String, Object> map = new HashMap<String, Object>();
			Cell cell02 = sheet.getRow(k).getCell(1);// 乡
			Cell cell03 = sheet.getRow(k).getCell(2);// 村
			Cell cell04 = sheet.getRow(k).getCell(3);// 组
			Cell cell05 = sheet.getRow(k).getCell(15);// 人口
			String xiang = cell02.getStringCellValue();
			String cun = cell03.getStringCellValue();
			String zu = cell04.getStringCellValue();
			Double count = cell05.getNumericCellValue();
			System.out.println(zu);
			String mergerName = "云南省,迪庆藏族自治州,维西傈僳族自治县," + xiang + "," + cun + "," + zu;// 全名称
			PubRegionEntity region = regionDao.findByNameAndLastLevel(zu);
			if (region != null)
				productionDao.u(region.getCityCode(), count);

		}
	}

}
