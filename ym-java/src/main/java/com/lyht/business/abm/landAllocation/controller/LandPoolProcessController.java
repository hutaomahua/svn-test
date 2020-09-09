package com.lyht.business.abm.landAllocation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.landAllocation.service.LandPoolProcessService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/landPoolProcess")
@RestController
@Api(value = "土地分解池关联业务表", tags = "土地分解池关联业务表")
public class LandPoolProcessController {
	
	@Autowired
	private LandPoolProcessService service;
	
	@PostMapping("/queryDecomposableArea")
    @ApiOperation(value = "查询可分解土地面积", notes = "查询可分解面积")
	public LyhtResultBody<Double> queryDecomposableArea(Double number,String region,String scope,String allType,String typeOne,String typeTwo,String typeThree,String ownerNm){
		return service.queryDecomposableArea(number,region, scope, allType, typeOne, typeTwo, typeThree,ownerNm);
	}
	
}
