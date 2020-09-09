package com.lyht.business.abm.removal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.removal.service.AbmAggregateService;
import com.lyht.business.abm.removal.vo.AbmMoveAggregateCardVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value = "/abm/move/aggregate", tags = "实施管理--搬迁安置汇总api")
@RestController
@RequestMapping("/abm/move/aggregate")
public class AbmMoveAggregateController {

	@Autowired
	private AbmAggregateService abmAggregateService;

	@ApiOperation(value = "搬迁安置--汇总卡片", notes = "搬迁安置--汇总卡片")
	@PostMapping("/card")
	public LyhtResultBody<List<AbmMoveAggregateCardVO>> findMoveAggregateCard(String mergerName,String type) {
		List<AbmMoveAggregateCardVO> findMoveAggregateCard = abmAggregateService.findMoveAggregateCard(mergerName,type);
		return new LyhtResultBody<>(findMoveAggregateCard);
	}
	
}