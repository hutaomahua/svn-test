package com.lyht.business.cost.controller;

import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.cost.entity.CostBuildingEntity;
import com.lyht.business.cost.service.CostBuildingService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cost/building")
@Api(value = "/cost/building", tags = "附属建筑物补偿费用")
public class CostBuildingController {
	@Autowired
	private CostBuildingService costBuildingService;

	@GetMapping("/by/owner")
	@ApiOperation(value = "按户主内码计算补偿费用", notes = "按户主内码计算补偿费用")
	public LyhtResultBody<List<CostBuildingEntity>> costByOwnerNm(String ownerNm) {
		List<CostBuildingEntity> findByOwnerNm = costBuildingService.costByOwnerNm(ownerNm);
		return new LyhtResultBody<>(findByOwnerNm);
	}

	@GetMapping("/list/by/owner")
	@ApiOperation(value = "按户主内码查询", notes = "按户主内码查询")
	public LyhtResultBody<List<CostBuildingEntity>> list(String ownerNm) {
		List<CostBuildingEntity> findByOwnerNm = costBuildingService.findByOwnerNm(ownerNm);
		return new LyhtResultBody<>(findByOwnerNm);
	}

//	@ApiOperation(value = "新增（id为空），修改（id必填）", notes = "新增，修改")
//	@PostMapping("/save")
//	public LyhtResultBody<CostBuildingEntity> save(CostBuildingEntity costBuildingEntity) {
//		CostBuildingEntity save = costBuildingService.save(costBuildingEntity);
//		return new LyhtResultBody<>(save);
//	}
//
//	@ApiOperation(value = "批量删除(按id删除,用逗号“,”拼接)", notes = "批量删除(按id删除,用逗号“,”拼接)")
//	@GetMapping("/delete/batch")
//	public LyhtResultBody<String> deleteAll(String ids) {
//		String deleteAll = costBuildingService.deleteAll(ids);
//		return new LyhtResultBody<>(deleteAll);
//	}
//
//	@ApiOperation(value = "删除(按id删除)", notes = "删除(按id删除)")
//	@GetMapping("/delete")
//	public LyhtResultBody<String> delete(String id) {
//		String delete = costBuildingService.delete(id);
//		return new LyhtResultBody<>(delete);
//	}

}
