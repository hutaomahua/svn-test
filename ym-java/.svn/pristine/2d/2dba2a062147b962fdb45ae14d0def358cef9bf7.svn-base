package com.lyht.business.cost.controller;

import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.cost.entity.CostAgriculturalFacilitiesEntity;
import com.lyht.business.cost.service.CostAgriculturalFacilitiesService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cost/agricultural/facilities")
@Api(value = "/cost/agricultural/facilities", tags = "农副业设施补偿费用")
public class CostAgriculturalFacilitiesController {
	@Autowired
	private CostAgriculturalFacilitiesService costAgriculturalFacilitiesService;

	@GetMapping("/by/owner")
	@ApiOperation(value = "按户主内码计算补偿费用", notes = "按户主内码计算补偿费用")
	public LyhtResultBody<List<CostAgriculturalFacilitiesEntity>> costByOwnerNm(String ownerNm) {
		List<CostAgriculturalFacilitiesEntity> findByOwnerNm = costAgriculturalFacilitiesService.costByOwnerNm(ownerNm);
		return new LyhtResultBody<>(findByOwnerNm);
	}

	@GetMapping("/list/by/owner")
	@ApiOperation(value = "按户主内码查询", notes = "按户主内码查询")
	public LyhtResultBody<List<CostAgriculturalFacilitiesEntity>> list(String ownerNm) {
		List<CostAgriculturalFacilitiesEntity> findByOwnerNm = costAgriculturalFacilitiesService.findByOwnerNm(ownerNm);
		return new LyhtResultBody<>(findByOwnerNm);
	}

//	@ApiOperation(value = "新增（id为空），修改（id必填）", notes = "新增，修改")
//	@PostMapping("/save")
//	public LyhtResultBody<CostAgriculturalFacilitiesEntity> save(
//			CostAgriculturalFacilitiesEntity costAgriculturalFacilitiesEntity) {
//		CostAgriculturalFacilitiesEntity save = costAgriculturalFacilitiesService
//				.save(costAgriculturalFacilitiesEntity);
//		return new LyhtResultBody<>(save);
//	}
//
//	@ApiOperation(value = "批量删除(按id删除,用逗号“,”拼接)", notes = "批量删除(按id删除,用逗号“,”拼接)")
//	@GetMapping("/delete/batch")
//	public LyhtResultBody<String> deleteAll(String ids) {
//		String deleteAll = costAgriculturalFacilitiesService.deleteAll(ids);
//		return new LyhtResultBody<>(deleteAll);
//	}
//
//	@ApiOperation(value = "删除(按id删除)", notes = "删除(按id删除)")
//	@GetMapping("/delete")
//	public LyhtResultBody<String> delete(String id) {
//		String delete = costAgriculturalFacilitiesService.delete(id);
//		return new LyhtResultBody<>(delete);
//	}

}
