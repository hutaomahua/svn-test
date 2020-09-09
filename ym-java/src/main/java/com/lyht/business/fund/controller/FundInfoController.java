package com.lyht.business.fund.controller;

import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.fund.entity.FundInfo;
import com.lyht.business.fund.service.FundInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/fund/info")
@Api(value = "/fund/info", tags = "资金管理 可研阶段概算数据拆分表")
public class FundInfoController {

	private static Logger logger = LoggerFactory.getLogger(FundInfoController.class);

	@Autowired
	private FundInfoService service;
	
	@ApiOperation(value = "查询所有", notes = "查询")
	@PostMapping("/page")
	public LyhtResultBody<List<FundInfo>> page(LyhtPageVO lyhtPageVO){
		return service.page(lyhtPageVO);
	}

	@ApiOperation(value = "按Excel对应模板导入数据", notes = "Excel导入")
	@PostMapping("/importE")
	public List<FundInfo> importE(@RequestParam("file") MultipartFile multipartFile) {
		Workbook workbook = null;
		try {
			workbook = WorkbookFactory.create(multipartFile.getInputStream());
		} catch (Exception e) {
			logger.error("导入失败");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 获取第一个张表
		Sheet sheet = workbook.getSheetAt(0);
		List<FundInfo> list = service.setValue(sheet);
		return list;
	}

}
