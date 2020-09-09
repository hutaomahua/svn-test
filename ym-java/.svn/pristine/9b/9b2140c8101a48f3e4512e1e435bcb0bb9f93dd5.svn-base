package com.lyht.business.cost.controller;

import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.cost.entity.CostOtherEntity;
import com.lyht.business.cost.service.CostOtherService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cost/other")
@Api(value = "/cost/other", tags = "其他补助（包含困难户补助）费用")
public class CostOtherController {
	@Autowired
	private CostOtherService costOtherService;

	@GetMapping("/by/owner")
	@ApiOperation(value = "按户主内码计算补偿费用（其他补助）", notes = "按户主内码计算补偿费用（其他补助）")
	public LyhtResultBody<List<CostOtherEntity>> costByOwnerNm(String ownerNm) {
		List<CostOtherEntity> findByOwnerNm = costOtherService.costOtherByOwnerNm(ownerNm);
		return new LyhtResultBody<>(findByOwnerNm);
	}

	@GetMapping("/list/by/owner")
	@ApiOperation(value = "按户主内码查询（其他补助）", notes = "按户主内码查询（其他补助）")
	public LyhtResultBody<List<CostOtherEntity>> findOtherCostByOwnerNm(String ownerNm) {
		List<CostOtherEntity> findByOwnerNm = costOtherService.findOtherCostByOwnerNm(ownerNm);
		return new LyhtResultBody<>(findByOwnerNm);
	}

	@GetMapping("/difficult/by/owner")
	@ApiOperation(value = "按户主内码计算补偿费用（困难户补助）", notes = "按户主内码计算补偿费用（困难户补助）")
	public LyhtResultBody<List<CostOtherEntity>> costDifficultByOwnerNm(String ownerNm) {
		List<CostOtherEntity> findByOwnerNm = costOtherService.costDifficultByOwnerNm(ownerNm);
		return new LyhtResultBody<>(findByOwnerNm);
	}

	@GetMapping("/difficult/list/by/owner")
	@ApiOperation(value = "按户主内码查询（困难户补助）", notes = "按户主内码查询（困难户补助）")
	public LyhtResultBody<List<CostOtherEntity>> findDifficultCostByOwnerNm(String ownerNm) {
		List<CostOtherEntity> findByOwnerNm = costOtherService.findDifficultCostByOwnerNm(ownerNm);
		return new LyhtResultBody<>(findByOwnerNm);
	}

//	@ApiOperation(value = "新增（id为空），修改（id必填）", notes = "新增，修改")
//	@PostMapping("/save")
//	public LyhtResultBody<CostOtherEntity> save(CostOtherEntity costOtherEntity) {
//		CostOtherEntity save = costOtherService.save(costOtherEntity);
//		return new LyhtResultBody<>(save);
//	}
//
//	@ApiOperation(value = "批量删除(按id删除,用逗号“,”拼接)", notes = "批量删除(按id删除,用逗号“,”拼接)")
//	@GetMapping("/delete/batch")
//	public LyhtResultBody<String> deleteAll(String ids) {
//		String deleteAll = costOtherService.deleteAll(ids);
//		return new LyhtResultBody<>(deleteAll);
//	}
//
//	@ApiOperation(value = "删除(按id删除)", notes = "删除(按id删除)")
//	@GetMapping("/delete")
//	public LyhtResultBody<String> delete(String id) {
//		String delete = costOtherService.delete(id);
//		return new LyhtResultBody<>(delete);
//	}

}
