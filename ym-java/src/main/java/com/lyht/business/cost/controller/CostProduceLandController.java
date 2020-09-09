package com.lyht.business.cost.controller;

import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.cost.entity.CostProduceLandEntity;
import com.lyht.business.cost.service.CostProduceLandService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cost/produce/land")
@Api(value = "/cost/produce/land", tags = "生产安置土地补偿费用（非兰永）")
public class CostProduceLandController {
	@Autowired
	private CostProduceLandService costProduceLandService;

	@GetMapping("/by/owner")
	@ApiOperation(value = "按户主内码计算补偿费用", notes = "按户主内码计算补偿费用")
	public LyhtResultBody<List<CostProduceLandEntity>> costByOwnerNm(String ownerNm) {
		List<CostProduceLandEntity> findByOwnerNm = costProduceLandService.costByOwnerNm(ownerNm);
		return new LyhtResultBody<>(findByOwnerNm);
	}

	@GetMapping("/list/by/owner")
	@ApiOperation(value = "按户主内码查询", notes = "按户主内码查询")
	public LyhtResultBody<List<CostProduceLandEntity>> list(String ownerNm) {
		List<CostProduceLandEntity> findByOwnerNm = costProduceLandService.findByOwnerNm(ownerNm);
		return new LyhtResultBody<>(findByOwnerNm);
	}

}
