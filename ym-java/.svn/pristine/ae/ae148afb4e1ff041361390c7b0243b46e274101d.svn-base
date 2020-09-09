package com.lyht.business.cost.controller;

import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.cost.entity.CostInfrastructureEntity;
import com.lyht.business.cost.service.CostInfrastructureService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cost/infrastructure")
@Api(value = "/cost/infrastructure", tags = "搬迁安置基础设施补助费用")
public class CostInfrastructureController {
	@Autowired
	private CostInfrastructureService costInfrastructureService;

	@GetMapping("/by/owner")
	@ApiOperation(value = "按户主内码计算补偿费用", notes = "按户主内码计算补偿费用")
	public LyhtResultBody<List<CostInfrastructureEntity>> costByOwnerNm(String ownerNm) {
		List<CostInfrastructureEntity> findByOwnerNm = costInfrastructureService.costByOwnerNm(ownerNm);
		return new LyhtResultBody<>(findByOwnerNm);
	}

	@GetMapping("/list/by/owner")
	@ApiOperation(value = "按户主内码查询", notes = "按户主内码查询")
	public LyhtResultBody<List<CostInfrastructureEntity>> list(String ownerNm) {
		List<CostInfrastructureEntity> findByOwnerNm = costInfrastructureService.findByOwnerNm(ownerNm);
		return new LyhtResultBody<>(findByOwnerNm);
	}

//	@ApiOperation(value = "新增（id为空），修改（id必填）", notes = "新增，修改")
//	@PostMapping("/save")
//	public LyhtResultBody<CostInfrastructureEntity> save(CostInfrastructureEntity costInfrastructureEntity) {
//		CostInfrastructureEntity save = costInfrastructureService.save(costInfrastructureEntity);
//		return new LyhtResultBody<>(save);
//	}
//
//	@ApiOperation(value = "批量删除(按id删除,用逗号“,”拼接)", notes = "批量删除(按id删除,用逗号“,”拼接)")
//	@GetMapping("/delete/batch")
//	public LyhtResultBody<String> deleteAll(String ids) {
//		String deleteAll = costInfrastructureService.deleteAll(ids);
//		return new LyhtResultBody<>(deleteAll);
//	}
//
//	@ApiOperation(value = "删除(按id删除)", notes = "删除(按id删除)")
//	@GetMapping("/delete")
//	public LyhtResultBody<String> delete(String id) {
//		String delete = costInfrastructureService.delete(id);
//		return new LyhtResultBody<>(delete);
//	}

}
