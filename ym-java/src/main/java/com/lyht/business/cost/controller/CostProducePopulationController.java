package com.lyht.business.cost.controller;

import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.cost.entity.CostProducePopulationEntity;
import com.lyht.business.cost.service.CostProducePopulationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cost/produce/population")
@Api(value = "/cost/produce/population", tags = "生产安置人口补偿费用（兰永）")
public class CostProducePopulationController {
	@Autowired
	private CostProducePopulationService costProducePopulationService;

	@GetMapping("/by/owner")
	@ApiOperation(value = "按户主内码计算补偿费用", notes = "按户主内码计算补偿费用")
	public LyhtResultBody<List<CostProducePopulationEntity>> costByOwnerNm(String ownerNm) {
		List<CostProducePopulationEntity> findByOwnerNm = costProducePopulationService.costByOwnerNm(ownerNm);
		return new LyhtResultBody<>(findByOwnerNm);
	}

	@GetMapping("/list/by/owner")
	@ApiOperation(value = "按户主内码查询", notes = "按户主内码查询")
	public LyhtResultBody<List<CostProducePopulationEntity>> list(String ownerNm) {
		List<CostProducePopulationEntity> findByOwnerNm = costProducePopulationService.findByOwnerNm(ownerNm);
		return new LyhtResultBody<>(findByOwnerNm);
	}

}
