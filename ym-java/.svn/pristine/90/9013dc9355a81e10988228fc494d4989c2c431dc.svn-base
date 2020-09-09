package com.lyht.business.abm.landAllocation.controller;

import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.landAllocation.service.LandResolveService;
import com.lyht.business.abm.landAllocation.vo.LandResolveAggregateParamVO;
import com.lyht.business.abm.landAllocation.vo.LandResolveAggregateVO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/resolve")
@Api(value = "/resolve", tags = "土地分解")
public class LandResolveController {

	@Autowired
	private LandResolveService landResolveService;

	@ApiOperation(value = "土地分解联动汇总列表查询", notes = "土地分解联动汇总列表查询")
	@PostMapping(value = "/aggregate/by/level")
	public LyhtResultBody<List<LandResolveAggregateVO>> findLandAggregate(
			LandResolveAggregateParamVO landResolveAggregateParamVO) {
		List<LandResolveAggregateVO> findAggregate = landResolveService.findAggregate(landResolveAggregateParamVO);
		return new LyhtResultBody<>(findAggregate);
	}

}
