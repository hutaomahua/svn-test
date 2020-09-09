package com.lyht.business.cost.controller;

import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.cost.entity.CostRelocationAllowanceEntity;
import com.lyht.business.cost.service.CostRelocationAllowanceService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cost/relocation/allowance")
@Api(value = "/cost/relocation/allowance", tags = "搬迁补助费用")
public class CostRelocationAllowanceController {
	@Autowired
	private CostRelocationAllowanceService costRelocationAllowanceService;

	@GetMapping("/by/owner")
	@ApiOperation(value = "按户主内码计算补偿费用", notes = "按户主内码计算补偿费用")
	public LyhtResultBody<List<CostRelocationAllowanceEntity>> costByOwnerNm(String ownerNm) {
		List<CostRelocationAllowanceEntity> findByOwnerNm = costRelocationAllowanceService.costByOwnerNm(ownerNm);
		return new LyhtResultBody<>(findByOwnerNm);
	}

	@GetMapping("/list/by/owner")
	@ApiOperation(value = "按户主内码查询", notes = "按户主内码查询")
	public LyhtResultBody<List<CostRelocationAllowanceEntity>> list(String ownerNm) {
		List<CostRelocationAllowanceEntity> findByOwnerNm = costRelocationAllowanceService.findByOwnerNm(ownerNm);
		return new LyhtResultBody<>(findByOwnerNm);
	}

//	@ApiOperation(value = "新增（id为空），修改（id必填）", notes = "新增，修改")
//	@PostMapping("/save")
//	public LyhtResultBody<CostRelocationAllowanceEntity> save(CostRelocationAllowanceEntity costRelocationAllowanceEntity) {
//		CostRelocationAllowanceEntity save = costRelocationAllowanceService.save(costRelocationAllowanceEntity);
//		return new LyhtResultBody<>(save);
//	}
//
//	@ApiOperation(value = "批量删除(按id删除,用逗号“,”拼接)", notes = "批量删除(按id删除,用逗号“,”拼接)")
//	@GetMapping("/delete/batch")
//	public LyhtResultBody<String> deleteAll(String ids) {
//		String deleteAll = costRelocationAllowanceService.deleteAll(ids);
//		return new LyhtResultBody<>(deleteAll);
//	}
//
//	@ApiOperation(value = "删除(按id删除)", notes = "删除(按id删除)")
//	@GetMapping("/delete")
//	public LyhtResultBody<String> delete(String id) {
//		String delete = costRelocationAllowanceService.delete(id);
//		return new LyhtResultBody<>(delete);
//	}

}
