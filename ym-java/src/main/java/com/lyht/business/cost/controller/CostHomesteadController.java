package com.lyht.business.cost.controller;

import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.cost.entity.CostHomesteadEntity;
import com.lyht.business.cost.service.CostHomesteadService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cost/homestead")
@Api(value = "/cost/homestead", tags = "宅基地补偿费用")
public class CostHomesteadController {
	@Autowired
	private CostHomesteadService costHomesteadService;

	@GetMapping("/by/owner")
	@ApiOperation(value = "按户主内码计算补偿费用", notes = "按户主内码计算补偿费用")
	public LyhtResultBody<List<CostHomesteadEntity>> costByOwnerNm(String ownerNm) {
		List<CostHomesteadEntity> findByOwnerNm = costHomesteadService.costByOwnerNm(ownerNm);
		return new LyhtResultBody<>(findByOwnerNm);
	}

	@GetMapping("/list/by/owner")
	@ApiOperation(value = "按户主内码查询", notes = "按户主内码查询")
	public LyhtResultBody<List<CostHomesteadEntity>> list(String ownerNm) {
		List<CostHomesteadEntity> findByOwnerNm = costHomesteadService.findByOwnerNm(ownerNm);
		return new LyhtResultBody<>(findByOwnerNm);
	}

//	@ApiOperation(value = "新增（id为空），修改（id必填）", notes = "新增，修改")
//	@PostMapping("/save")
//	public LyhtResultBody<CostHomesteadEntity> save(CostHomesteadEntity costHomesteadEntity) {
//		CostHomesteadEntity save = costHomesteadService.save(costHomesteadEntity);
//		return new LyhtResultBody<>(save);
//	}
//
//	@ApiOperation(value = "批量删除(按id删除,用逗号“,”拼接)", notes = "批量删除(按id删除,用逗号“,”拼接)")
//	@GetMapping("/delete/batch")
//	public LyhtResultBody<String> deleteAll(String ids) {
//		String deleteAll = costHomesteadService.deleteAll(ids);
//		return new LyhtResultBody<>(deleteAll);
//	}
//
//	@ApiOperation(value = "删除(按id删除)", notes = "删除(按id删除)")
//	@GetMapping("/delete")
//	public LyhtResultBody<String> delete(String id) {
//		String delete = costHomesteadService.delete(id);
//		return new LyhtResultBody<>(delete);
//	}

}
