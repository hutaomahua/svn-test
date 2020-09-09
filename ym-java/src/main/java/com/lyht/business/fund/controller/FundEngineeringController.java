package com.lyht.business.fund.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.fund.entity.FundEngineering;
import com.lyht.business.fund.service.FundEngineeringService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "/fund/engineering", tags = "移民工程类资金")
@RestController
@RequestMapping("/fund/engineering")
public class FundEngineeringController {
	
	@Autowired
	private FundEngineeringService service;
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@GetMapping("/batchDel")
	@ApiOperation(value = "批量删除", notes = "批量删除")
	public LyhtResultBody<String> batchDel(String ids){
		return service.batchDel(ids);
	}
	
	/**
	 * 单个删除
	 *
	 * @param id
	 * @return
	 */
	@GetMapping("/delete")
	@ApiOperation(value = "删除", notes = "删除")
	public LyhtResultBody<Integer> delete(Integer id){
		return service.delete(id);
	}
	
	/**
	 * 添加 修改
	 * @param FundEngineering
	 * @return
	 */
	@ApiOperation(value = "新增修改", notes = "新增修改 修改必须传入id,nm")
	@PostMapping("/save")
	public LyhtResultBody<FundEngineering> save(FundEngineering fundEngineering) {
		return service.save(fundEngineering);
	}
	
}
