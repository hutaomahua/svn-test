package com.lyht.business.abm.landAllocation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.landAllocation.entity.LandPoolEntity;
import com.lyht.business.abm.landAllocation.service.LandPoolService;
import com.lyht.business.abm.landAllocation.vo.LandPoolVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/landPool")
@Api(value = "土地分解池", tags = "土地分解池")
public class LandPoolController {
	
	@Autowired
	private LandPoolService service;
	
	@ApiOperation(value = "查询", notes = "分页 条件 查询")
	@PostMapping("/page")
	public LyhtResultBody<List<LandPoolVO>> page(String region, String scope, String allType, String typeOne,
			String typeTwo, String typeThree, LyhtPageVO lyhtPageVO) {
		return service.page(region, scope, allType, typeOne, typeTwo, typeThree, lyhtPageVO);
	}
	
	@ApiOperation(value = "修改分解状态", notes = "修改")
	@PostMapping("/save")
	public LyhtResultBody<LandPoolEntity> save(LandPoolEntity landPoolEntity){
		return service.save(landPoolEntity);
	}
	
	
	@ApiOperation(value = "新增", notes = "新增")
	@PostMapping("/saveAll")
	public LyhtResultBody<List<LandPoolEntity>> saveAll(LandPoolEntity landPoolEntity) {
		return service.saveAll(landPoolEntity);
	}
}
