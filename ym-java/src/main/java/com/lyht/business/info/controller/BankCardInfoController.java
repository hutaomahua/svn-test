package com.lyht.business.info.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.info.entity.BankCardInfoEntity;
import com.lyht.business.info.service.BankCardInfoService;
import com.lyht.business.info.to.Msg;
import com.lyht.business.info.vo.BankVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "/bankcardinfo", tags = "银行卡信息")
@RestController
@RequestMapping("/bankcardinfo")
public class BankCardInfoController {
	
	@Autowired
	private BankCardInfoService service;
	
	@GetMapping("/list")
	public LyhtResultBody<BankCardInfoEntity> list() {
		
		return null;
	}
	

    @ApiOperation(value = "添加银行卡", notes = "添加银行卡")
	@GetMapping("/saveOrUpdata")
	public LyhtResultBody<Msg<BankCardInfoEntity>> saveOrUpdata(BankCardInfoEntity bankCardInfo) {
		Msg<BankCardInfoEntity> msg =  service.saveOrUpdata(bankCardInfo);
		return new LyhtResultBody<Msg<BankCardInfoEntity>>(msg);
	}
	
    @ApiOperation(value = "银行字典列表", notes = "银行字典列表")
	@GetMapping("/bankdict")
	public LyhtResultBody<List<BankVO>> bankdict() {
		List<BankVO> list = service.getBankDict();
		return new LyhtResultBody<List<BankVO>>(list);
	}
}
