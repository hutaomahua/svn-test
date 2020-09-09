package com.lyht.business.abm.protocol.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.protocol.entity.BankCradInfo;
import com.lyht.business.abm.protocol.service.BankCradInfoService;
import com.lyht.business.pub.service.PubFilesService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/abm/bankCradInfo")
@Api(value = "/abm/bankCradInfo", tags = "银行卡信息表")
public class BankCradInfoController {

	@Autowired 
	private BankCradInfoService service;
	
	@Autowired
	private PubFilesService pubFilesService;
	
	/**
	 * 单个删除
	 *
	 * @param id
	 * @return
	 */
	@GetMapping("/delete")
	@ApiOperation(value = "删除", notes = "删除")
	public LyhtResultBody<Integer> delete(HttpServletRequest request,Integer id) {
		// 参数校验
		if (id == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		BankCradInfo BankCradInfo = service.findOneById(id);
		pubFilesService.deleteBytablePkColumn(request, BankCradInfo.getNm());//删除该条信息下所有附件
		return service.delete(id);
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@GetMapping("/batchDel")
	@ApiOperation(value = "批量删除", notes = "批量删除")
	public LyhtResultBody<String> batchDel(String ids) {
		return service.batchDel(ids);
	}
	
	/**
	 * 添加 修改
	 * @param bankCradInfo
	 * @return
	 */
	@ApiOperation(value = "新增修改", notes = "新增修改 修改必须传入id,nm")
	@PostMapping("/save")
	public LyhtResultBody<BankCradInfo> save(BankCradInfo bankCradInfo) {
		return new LyhtResultBody<>(service.save(bankCradInfo));
	}

	
}
